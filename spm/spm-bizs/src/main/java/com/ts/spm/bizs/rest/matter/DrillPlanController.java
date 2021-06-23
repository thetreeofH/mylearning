package com.ts.spm.bizs.rest.matter;

import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.AttachmentBiz;
import com.ts.spm.bizs.biz.LogBiz;
import com.ts.spm.bizs.biz.admin.DepartBiz;
import com.ts.spm.bizs.biz.matter.DrillPlanBiz;
import com.ts.spm.bizs.entity.Attachment;
import com.ts.spm.bizs.entity.matter.DrillPlan;
import com.ts.spm.bizs.rest.admin.UserController;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by zhoukun on 2020/4/17.
 */
@RestController
@RequestMapping("drillPlan")
@CheckClientToken
@CheckUserToken
public class DrillPlanController extends BaseController<DrillPlanBiz, DrillPlan, String> {
    @Autowired
    DepartBiz departBiz;
    @Autowired
    AttachmentBiz attachmentBiz;
    @Autowired
    UserController userCtr;
    @Autowired
    LogBiz logBiz;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ObjectRestResponse addEntity(@RequestBody DrillPlan o){
        String id= UUIDUtils.generateUuid();
        o.setId(id);
        o.setCrtTime(new Date());
        o.setCrtUserId(BaseContextHandler.getUserID());
        o.setCrtUserName(BaseContextHandler.getUsername());
        o.setCrtDeptId(BaseContextHandler.getDepartID());
        o.setDepart(departBiz.getDepartNameById(o.getDepartId()));
        baseBiz.insertSelective(o);

//        if(o.getActions()!=null){
//            for (DrillPlanAction action:o.getActions()){
//                action.setId(UUIDUtils.generateUuid());
//                action.setPid(id);
//                action.setCrtTime(new Date());
//                action.setCrtUserId(BaseContextHandler.getUserID());
//                action.setCrtUserName(BaseContextHandler.getUsername());
//                action.setCrtDeptId(BaseContextHandler.getDepartID());
//                drillPlanActionBiz.insertSelective(action);
//            }
//        }
//
//        if(o.getDrillAttach()!=null){
//            for (Attachment attachment:o.getDrillAttach()){
//                attachment.setId(UUIDUtils.generateUuid());
//                attachment.setPid(id);
//                attachment.setType("dirll");
//                attachmentBiz.insertSelective(attachment);
//            }
//        }
//        if(o.getAssessAttach()!=null){
//            for (Attachment attachment:o.getAssessAttach()){
//                attachment.setId(UUIDUtils.generateUuid());
//                attachment.setPid(id);
//                attachment.setType("assess");
//                attachmentBiz.insertSelective(attachment);
//            }
//        }

        logBiz.saveLog("模拟演练","添加", "api/matter//drillPlan/add");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ObjectRestResponse getEntity(@PathVariable String id) {
        DrillPlan o=baseBiz.selectById(id);

//        Example exa=new Example(DrillPlanAction.class);
//        Example.Criteria cr=exa.createCriteria();
//        cr.andEqualTo("pid", o.getId());
//        o.setActions(drillPlanActionBiz.selectByExample(exa));

        Example exa=new Example(Attachment.class);
        Example.Criteria cr=exa.createCriteria();
        cr.andEqualTo("pid", o.getId());
        cr.andEqualTo("type", "drill");
        o.setDrillAttach(attachmentBiz.selectByExample(exa));

        exa=new Example(Attachment.class);
        cr=exa.createCriteria();
        cr.andEqualTo("pid", o.getId());
        cr.andEqualTo("type", "drill-assess");
        o.setAssessAttach(attachmentBiz.selectByExample(exa));
        logBiz.saveLog("模拟演练","查询详情", "api/matter//drillPlan/get");
        return ObjectRestResponse.ok(o);
    }

//    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
//    public ObjectRestResponse updateEntity(@PathVariable String id,@RequestBody DrillPlan o) {
//        baseBiz.updateSelectiveById(o);
//        return ObjectRestResponse.ok();
//    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse delEntity(@PathVariable String id) {
        String[] ids=id.split(",");
        for (String str:ids){
            DrillPlan entity=baseBiz.selectById(str);
            entity.setState(3);
            baseBiz.updateSelectiveById(entity);
        }
        logBiz.saveLog("模拟演练","删除", "api/matter//drillPlan/delete");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/getpage", method = RequestMethod.GET)
    public TableResultResponse page(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "") String title, @RequestParam(defaultValue = "") String departId,
                                    @RequestParam(defaultValue = "") String month, @RequestParam(defaultValue = "0") int verify,
                                    @RequestParam(defaultValue = "") String start, @RequestParam(defaultValue = "") String end) throws ParseException {
        List<String> deptIds=new ArrayList<>();
        if(departId.isEmpty()) {
            List<Map<String,String>> departs = userCtr.dataDepartMap(BaseContextHandler.getUserID());
            deptIds=departs.stream().map(o->o.get("id")).collect(Collectors.toList());
        }else{
            deptIds.add(departId);
        }

        if(deptIds.isEmpty())
            return  new TableResultResponse<>(0,deptIds);

        Date st=null, et=null;
        if(!start.isEmpty() && !end.isEmpty()){
            st=DateUtils.parseDate(start,new String[]{"yyyy-MM-dd HH:mm:ss"});
            et=DateUtils.parseDate(end,new String[]{"yyyy-MM-dd HH:mm:ss"});
        }

        Example exa=new Example(DrillPlan.class);
        Example.Criteria cr=exa.createCriteria();
        if(!title.isEmpty())
            cr.andLike("title", "%"+title+"%");
        if(!month.isEmpty())
            cr.andEqualTo("planMonth", month);
        if(st!=null && et!=null)
            cr.andBetween("planStartTime",st,et);
        if(!deptIds.isEmpty())
            cr.andIn("departId",deptIds);
        if(verify!=0)
            cr.andEqualTo("verify",verify);
        exa.setOrderByClause(" CRT_TIME desc");

        Page<DrillPlan> result = PageHelper.startPage(page, limit);
//        List<DrillPlan> list = baseBiz.getList(title, month, st, et,deptIds);
        List<DrillPlan> list=baseBiz.selectByExample(exa);

        logBiz.saveLog("模拟演练","查询分页", "api/matter/drillPlan/getpage");
        return  new TableResultResponse<>(result.getTotal(),list);
    }

    @RequestMapping(value = "/finish/{id}", method = RequestMethod.PUT)
    public ObjectRestResponse finish(@PathVariable String id, @RequestBody DrillPlan o) {
        DrillPlan entity=baseBiz.selectById(o.getId());
        entity.setRealStartTime(o.getRealStartTime());
        entity.setRealEndTime(o.getRealEndTime());
        entity.setRealPersonNum(o.getRealPersonNum());
        entity.setState(2);
        entity.setVerify(1);
        baseBiz.updateSelectiveById(entity);

        Attachment attach=new Attachment();
        attach.setPid(o.getId());
        attachmentBiz.delete(attach);


        for (Attachment attachment:o.getDrillAttach()){
            attachment.setId(UUIDUtils.generateUuid());
            attachment.setPid(o.getId());
            attachment.setType("drill");
            attachmentBiz.insertSelective(attachment);
        }

        for (Attachment attachment:o.getAssessAttach()){
            attachment.setId(UUIDUtils.generateUuid());
            attachment.setPid(o.getId());
            attachment.setType("drill-assess");
            attachmentBiz.insertSelective(attachment);
        }

        logBiz.saveLog("模拟演练","完成", "api/matter//drillPlan/finish");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/verify/{id}", method = RequestMethod.PUT)
    public ObjectRestResponse verify(@PathVariable String id, @RequestBody DrillPlan o) {
        DrillPlan entity=baseBiz.selectById(o.getId());
        entity.setVerify(o.getVerify());

        if(o.getVerify()==3)
            entity.setState(4);

        if(o.getVerify()==2){
            entity.setState(5);
            entity.setScore(o.getScore());
        }

        baseBiz.updateSelectiveById(entity);

        logBiz.saveLog("模拟演练","审核", "api/matter//drillPlan/verify");
        return ObjectRestResponse.ok();
    }

}
