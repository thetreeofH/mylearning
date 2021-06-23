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
import com.ts.spm.bizs.biz.matter.ContingencyPlanBiz;
import com.ts.spm.bizs.entity.Attachment;
import com.ts.spm.bizs.entity.matter.ContingencyPlan;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by zhoukun on 2020/4/17.
 */
@RestController
@RequestMapping("contingencyPlan")
@CheckClientToken
@CheckUserToken
public class ContingencyPlanController extends BaseController<ContingencyPlanBiz, ContingencyPlan, String> {
    @Autowired
    CheckPointController checkPointCtr;
    @Autowired
    AttachmentBiz attachmentBiz;
    @Autowired
    LogBiz logBiz;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ObjectRestResponse addEntity(@RequestBody ContingencyPlan o){
        String id=UUIDUtils.generateUuid();
        o.setId(id);
        o.setCrtTime(new Date());
        o.setCrtUserId(BaseContextHandler.getUserID());
        o.setCrtUserName(BaseContextHandler.getUsername());
        o.setCrtDeptId(BaseContextHandler.getDepartID());
        baseBiz.insertSelective(o);

        if(o.getAttachments()!=null){
            for (Attachment attachment:o.getAttachments()){
                attachment.setId(UUIDUtils.generateUuid());
                attachment.setPid(id);
                attachmentBiz.insertSelective(attachment);
            }
        }

        logBiz.saveLog("应急预案","添加", "api/matter/contingencyPlan/add");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ObjectRestResponse getEntity(@PathVariable String id) {
        ContingencyPlan o=baseBiz.selectById(id);
        Attachment attachment=new Attachment();
        attachment.setPid(id);
        o.setAttachments(attachmentBiz.selectList(attachment));
        logBiz.saveLog("应急预案","查看详情", "api/matter/contingencyPlan/get");
        return ObjectRestResponse.ok(o);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ObjectRestResponse updateEntity(@PathVariable String id,@RequestBody ContingencyPlan o) {
        o.setUpdTime(new Date());
        o.setUpdUserId(BaseContextHandler.getUserID());
        o.setUpdUserName(BaseContextHandler.getUsername());
        o.setUpdDeptId(BaseContextHandler.getDepartID());
        baseBiz.updateSelectiveById(o);

        Attachment attachment=new Attachment();
        attachment.setPid(id);
        attachmentBiz.delete(attachment);

        if(o.getAttachments()!=null){
            for (Attachment att:o.getAttachments()){
                att.setId(UUIDUtils.generateUuid());
                att.setPid(id);
                attachmentBiz.insertSelective(att);
            }
        }

        logBiz.saveLog("应急预案","更新", "api/matter/contingencyPlan/update");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse delEntity(@PathVariable String id) {
        String[] ids=id.split(",");
        for (String str:ids){
            baseBiz.deleteById(str);
            Attachment attachment=new Attachment();
            attachment.setPid(str);
            attachmentBiz.delete(attachment);
        }
        logBiz.saveLog("应急预案","删除", "api/matter/contingencyPlan/delete");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/getpage", method = RequestMethod.GET)
    public TableResultResponse page(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "") String departId,
                                    @RequestParam(defaultValue = "") String title, @RequestParam(defaultValue = "") String level,
                                    @RequestParam(defaultValue = "") String type, @RequestParam(defaultValue = "") String measure) {
        List<Map<String,String>> departs;
//        if(departId.isEmpty())
//            departs=adminFeign.//getStation(BaseContextHandler.getUserID());
//        else
//            departs=adminFeign.stations(departId);
        departs=checkPointCtr.stations(departId);

        if(departs.isEmpty())
            return  new TableResultResponse<>(0,departs);
        List<String> departIds=departs.stream().map(o->o.get("id")).collect(Collectors.toList());
        Example exa=new Example(ContingencyPlan.class);
        Example.Criteria cr=exa.createCriteria();
        if(!"".equals(title))
            cr.andLike("title", "%" + title + "%");
        if(!"".equals(level))
            cr.andEqualTo("planLevel", level);
        if(!"".equals(type))
            cr.andEqualTo("type", type);
        if(!"".equals(measure))
            cr.andLike("measure", "%"+measure+"%");
//        if(!"".equals(departId)){
//            List<Map<String, String>> depts=adminFeign.stations(departId);
//            List<String> deptIds=depts.stream().map(o->o.get("id")).collect(Collectors.toList());
//            cr.andIn("departId", deptIds);
//        }
        cr.andIn("departId", departIds);
        exa.setOrderByClause("crt_time desc");

        Page<ContingencyPlan> result = PageHelper.startPage(page, limit);
        List<ContingencyPlan> list = baseBiz.selectByExample(exa);

        logBiz.saveLog("应急预案","分页查询", "api/matter/contingencyPlan/getpage");
        return  new TableResultResponse<>(result.getTotal(),list);
    }

    @RequestMapping(value = "/getAttachment/{id}", method = RequestMethod.GET)
    public ObjectRestResponse getAttachment(@PathVariable String id) {
        Attachment o=new Attachment();
        o.setPid(id);
        return ObjectRestResponse.ok(attachmentBiz.selectList(o));
    }

}
