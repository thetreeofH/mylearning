package com.ts.spm.bizs.rest.matter;

import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.constant.RestCodeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.AttachmentBiz;
import com.ts.spm.bizs.biz.LogBiz;
import com.ts.spm.bizs.biz.matter.EventInfoBiz;
import com.ts.spm.bizs.biz.matter.PointDangerBiz;
import com.ts.spm.bizs.entity.Attachment;
import com.ts.spm.bizs.entity.matter.EventInfo;
import com.ts.spm.bizs.entity.matter.PointDanger;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import com.ts.spm.bizs.rest.dict.DictTypeController;
import com.ts.spm.bizs.vo.matter.PointDangerVo;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by zhoukun on 2020/4/17.
 */
@RestController
@RequestMapping("danger")
@CheckClientToken
@CheckUserToken
public class PointDangerController extends BaseController<PointDangerBiz, PointDanger, Integer> {
    @Autowired
    CheckPointController checkPointCtr;
    @Autowired
    DictTypeController dictTypeCtr;
    @Autowired
    PointDangerBiz pointDangerBiz;
    @Autowired
    AttachmentBiz attachmentBiz;
    @Autowired
    EventInfoBiz eventInfoBiz;
    @Autowired
    LogBiz logBiz;

    static List<Map<String, String>> dangerTypes=null;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ObjectRestResponse addEntity(@RequestBody PointDanger o){
        if(o.getMtype()==null || o.getMtype().equals(""))
            return ObjectRestResponse.error(RestCodeConstants.EX_BUSINESS_BASE_CODE, "违禁品大类型缺失");
        Integer id = save(o);

        Map<String, Object> map=new HashMap<>();
        map.put("id",id);
        return ObjectRestResponse.ok(map);
    }

    protected Integer save(PointDanger o) {
        Integer id=pointDangerBiz.getId();
        Integer dno=pointDangerBiz.getDno();
        o.setId(id);
        o.setDno(dno.toString());
        if(o.getAttachs()!=null){
            for (Attachment att:o.getAttachs()){
                att.setId(UUIDUtils.generateUuid());
                att.setPid(id.toString());
                att.setType("danger");
                attachmentBiz.insertSelective(att);
            }
        }
        baseBiz.insertSelective(o);
        logBiz.saveLog("违禁品管理","添加", "api/matter/danger/add");
        return id;
    }


    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ObjectRestResponse getEntity(@PathVariable Integer id) {
        PointDanger o = getDanger(id);
        logBiz.saveLog("违禁品管理","查看详情", "api/matter/danger/get");
        return ObjectRestResponse.ok(o);
    }

    PointDanger getDanger(Integer id) {
        PointDanger o=baseBiz.getById(id);
        if(o!=null){
            Attachment att=new Attachment();
            att.setPid(id.toString());
            att.setType("danger");
            o.setAttachs(attachmentBiz.selectList(att));
        }
        return o;
    }

    @IgnoreUserToken
    @IgnoreClientToken
    @RequestMapping(value = "/getById/{id}",method = RequestMethod.GET)
    public PointDanger getById(@PathVariable Integer id) {
        PointDanger pointDanger = getDanger(id);
        logBiz.saveLog("违禁品管理","查看详情", "api/matter/danger/getById");
        return pointDanger;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ObjectRestResponse updateEntity(@PathVariable String id,@RequestBody PointDanger o) {
        edit(o);
        logBiz.saveLog("违禁品管理","更新", "api/matter/danger/update");
        return ObjectRestResponse.ok();
    }

    void edit(PointDanger o){
        Attachment att=new Attachment();
        att.setPid(o.getId().toString());
        att.setType("danger");
        attachmentBiz.delete(att);

        if(o.getAttachs()!=null){
            for (Attachment atta:o.getAttachs()){
                atta.setId(UUIDUtils.generateUuid());
                atta.setPid(o.getId().toString());
                atta.setType("danger");
                attachmentBiz.insertSelective(atta);
            }
        }
        baseBiz.updateSelectiveById(o);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse delEntity(@PathVariable Integer id) {
        del(id);
        logBiz.saveLog("违禁品管理","删除", "api/matter/danger/delete");
        return ObjectRestResponse.ok();
    }

    void del(@PathVariable Integer id) {
        baseBiz.deleteById(id);
        Attachment att=new Attachment();
        att.setPid(id.toString());
        att.setType("danger");
        attachmentBiz.delete(att);

        EventInfo evt=new EventInfo();
        evt.setDangerId(id);
        List<EventInfo> list=eventInfoBiz.selectList(evt);
        if(list.size()>0){
            evt=list.get(0);
            evt.setDelFlag(1);
            evt.setDelUserId(BaseContextHandler.getUserID());
            evt.setDelUserName(BaseContextHandler.getUsername());
            evt.setDelTime(new Date());
            eventInfoBiz.updateSelectiveById(evt);
        }
    }

    @RequestMapping(value = "/event/{id}", method = RequestMethod.GET)
    public ObjectRestResponse existEvt(@PathVariable Integer id) {
        EventInfo evt=new EventInfo();
        evt.setDangerId(id);
        long cnt=eventInfoBiz.selectCount(evt);
        if(cnt>0)
            return ObjectRestResponse.error(RestCodeConstants.EX_BUSINESS_BASE_CODE,"该违禁品与有关联事件！");
        logBiz.saveLog("违禁品管理","查询关联事件", "api/matter/danger/event");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/getpage", method = RequestMethod.GET)
    public TableResultResponse page(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "") String start, @RequestParam(defaultValue = "") String end,
                                    @RequestParam(defaultValue = "") String ajyIdno, @RequestParam(defaultValue = "") String ajyName,
                                    @RequestParam(defaultValue = "") String deptId) throws ParseException {
        List<Map<String, Object>> points=checkPointCtr.getpoint(deptId,"");
        if(CollectionUtils.isEmpty(points))
            return new TableResultResponse<>(0, points);

        Example exa = new Example(PointDanger.class);
        Example.Criteria cri = exa.createCriteria();
        if(!"".equals(ajyIdno))
            cri.andEqualTo("ajyIdno", ajyIdno);
        if(!"".equals(ajyName))
            cri.andLike("ajyName", "%"+ajyName+"%");
        if(!start.isEmpty() && !end.isEmpty()){
            Date st= DateUtils.parseDate(start, new String[]{"yyyy-MM-dd HH:mm:ss"});
            Date et= DateUtils.parseDate(end, new String[]{"yyyy-MM-dd HH:mm:ss"});
            cri.andBetween("time", st, et);
        }
        exa.setOrderByClause("time desc");

        List<String> pointIds=points.stream().map(u->u.get("id").toString()).collect(Collectors.toList());
        cri.andIn("pointId", pointIds);

        Page<PointDanger> result = PageHelper.startPage(page, limit);
        List<PointDanger> list = baseBiz.selectByExample(exa);

        if(dangerTypes==null)
            dangerTypes=dictTypeCtr.getDangers();//[id,name,code]

        List<PointDangerVo> list2 = new ArrayList<>();
        for (PointDanger pd:list){
            PointDangerVo vo=new PointDangerVo();
            BeanUtils.copyProperties(pd,vo);
            for (Map<String, String> map:dangerTypes){
                if(pd.getMtype().equals(map.get("code"))){
                    vo.setMtypeName(map.get("name"));
                    break;
                }
            }
            list2.add(vo);
        }

        logBiz.saveLog("违禁品管理","分页查询", "api/matter/danger/getpage");
        return  new TableResultResponse<>(result.getTotal(),list2);
    }

}
