package com.ts.spm.bizs.rest.matter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.ts.spm.bizs.biz.DictTypeBiz;
import com.ts.spm.bizs.biz.DictValueBiz;
import com.ts.spm.bizs.biz.LogBiz;
import com.ts.spm.bizs.biz.matter.EventInfoBiz;
import com.ts.spm.bizs.biz.matter.EventResubmitBiz;
import com.ts.spm.bizs.biz.matter.PointDangerBiz;
import com.ts.spm.bizs.biz.msg.MsgInfoBiz;
import com.ts.spm.bizs.biz.msg.MsgReceiveBiz;
import com.ts.spm.bizs.entity.Attachment;
import com.ts.spm.bizs.entity.DictType;
import com.ts.spm.bizs.entity.DictValue;
import com.ts.spm.bizs.entity.matter.EventInfo;
import com.ts.spm.bizs.entity.matter.EventResubmit;
import com.ts.spm.bizs.entity.matter.PointDanger;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import com.ts.spm.bizs.rest.admin.UserController;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by zhoukun on 2020/4/17.
 */
@RestController
@RequestMapping("event")
@CheckClientToken
@CheckUserToken
public class EventInfoController extends BaseController<EventInfoBiz, EventInfo, String> {
    @Autowired
    CheckPointController checkPointCtr;
    @Autowired
    UserController userCtr;
//    @Autowired
//    MsgInfoBiz msgInfoBiz;
//    @Autowired
//    MsgReceiveBiz msgReceiveBiz;
    @Autowired
    AttachmentBiz attachmentBiz;
    @Autowired
    EventResubmitBiz resubmitBiz;
    @Autowired
    PointDangerBiz dangerBiz;
    @Autowired
    PointDangerController dangerController;
//    @Autowired
//    MsgFeign msgFeign;
    @Autowired
    DictTypeBiz dictTypeBiz;
    @Autowired
    DictValueBiz dictValueBiz;
    @Autowired
    LogBiz logBiz;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ObjectRestResponse addEntity(@RequestBody EventInfo o) {
        if(o.getType()==1){
            PointDanger pd=o.getDanger();
            if(pd!=null){
                o.setDangerTypeId(pd.getMtype()); //违禁品大类
                DictType mtype = new DictType();
                mtype.setCode(pd.getMtype());
                DictType dt0 = dictTypeBiz.selectOne(mtype);
                o.setDangerType(dt0.getName());

                if(pd.getStype() != null && pd.getStype().length() > 0) {
                    o.setDangerSubId(pd.getStype());
                    DictValue stype = new DictValue();
                    stype.setCode(pd.getStype());
                    stype.setType(dt0.getId());
                    DictValue dt1 = dictValueBiz.selectOne(stype);
                    o.setDangerSub(dt1.getLabelDefault());
                }
                o.setXpicPath(pd.getxPicPath());
                o.setPicPath(pd.getPicPath());

                if(pd.getId()==null || "".equals(pd.getId())) {
                    o.setDangerId(dangerController.save(pd));
                }else{
                    dangerController.edit(pd);
                }
            }
        }

        Integer eventId=baseBiz.getId();
        o.setId(eventId);
        baseBiz.insertSelective(o);

        if(o.getInfoAttachs()!=null){
            for (Attachment att:o.getInfoAttachs()){
                att.setId(UUIDUtils.generateUuid());
                att.setPid(eventId.toString());
                att.setType("evt");
                attachmentBiz.insertSelective(att);
            }
        }
//        if(o.getDangerAttachs()!=null){
//            for (Attachment att:o.getDangerAttachs()){
//                att.setId(UUIDUtils.generateUuid());
//                att.setPid(id);
//                att.setType(1);
//                attachmentBiz.insertSelective(att);
//            }
//        }

        //send msg
//        Integer msgId=msgInfoBiz.getId();
//        MsgInfo msg=new MsgInfo();
//        msg.setId(msgId);
//        msg.setTitle(o.getTitle());
//        msg.setType(o.getType());
//        msg.setContent(o.getRemark());
//        msgInfoBiz.insertSelective(msg);

        //send user
//        String stationId=o.getStationId();
//        List<Map<String, String>> list=adminFeign.stations(stationId);
//        List<Map<String, String>> departs=list.stream().filter(m->m.get("type").equals("4")).collect(Collectors.toList());
//        for (Map<String, String> dept:departs){
//            List<Map<String, String>> users=adminFeign.departUsers(dept.get("id"));
//            for (Map<String, String> user:users){
//                MsgReceive receive=new MsgReceive();
//                receive.setId(UUIDUtils.generateUuid());
//                receive.setMsgId(msgId.toString());
//                receive.setReceiveUserId(user.get("id"));
//                receive.setReceiveUserName(user.get("name"));
//                msgReceiveBiz.insertSelective(receive);
//            }
//        }
//        //push msg
//        ObjectMapper om=new ObjectMapper();
//        msgFeign.send(om.writeValueAsString(msg));

        logBiz.saveLog("事件管理","添加", "api/matter/event/add");

        Map<String, String> map=new HashMap<>();
        map.put("id", eventId.toString());
        return ObjectRestResponse.ok(map);
    }

    @RequestMapping(value = "process", method = RequestMethod.PUT)
    public ObjectRestResponse process(@RequestBody EventInfo o) {
        //事件处置完成提交时，将dealResult改为1：已处置
        o.setDealResult(1);
        o.setUpdTime(new Date());
        o.setUpdUserId(BaseContextHandler.getUserID());
        o.setUpdUserName(BaseContextHandler.getUsername());
        baseBiz.updateSelectiveById(o);

        Attachment arg=new Attachment();
        arg.setPid(o.getId().toString());
        arg.setType("process");
        attachmentBiz.delete(arg);

        if(o.getProcessAttachs()!=null){
            for (Attachment att:o.getProcessAttachs()){
                att.setId(UUIDUtils.generateUuid());
                att.setPid(o.getId().toString());
                att.setType("process");
                attachmentBiz.insertSelective(att);
            }
        }
        logBiz.saveLog("事件管理","处置", "api/matter/event/process");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ObjectRestResponse getEntity(@PathVariable String id) {
        EventInfo evt=baseBiz.selectObjectById(id);
        if(evt==null)
            return ObjectRestResponse.ok(evt);

        if(evt.getDangerId()!=null)
            evt.setDanger(dangerController.getDanger(evt.getDangerId()));

        Attachment att=new Attachment();
        att.setPid(id);
        att.setType("evt");
        evt.setInfoAttachs(attachmentBiz.selectList(att));
        att.setType("process");
        evt.setProcessAttachs(attachmentBiz.selectList(att));

        Example exa=new Example(EventResubmit.class);
        Example.Criteria c=exa.createCriteria();
        c.andEqualTo("evtId",id);
        exa.setOrderByClause("crt_time");
        evt.setResubmits(resubmitBiz.selectByExample(exa));

        logBiz.saveLog("事件管理","详情查询", "api/matter/event/get");
        return ObjectRestResponse.ok(evt);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ObjectRestResponse updateEntity(@PathVariable String id,@RequestBody EventInfo o) {
        o.setUpdTime(new Date());
        o.setUpdUserId(BaseContextHandler.getUserID());
        o.setUpdUserName(BaseContextHandler.getUsername());
        baseBiz.updateSelectiveById(o);

        Attachment arg=new Attachment();
        arg.setPid(o.getId().toString());
        arg.setType("evt");
        attachmentBiz.delete(arg);
        arg.setType("process");
        attachmentBiz.delete(arg);
        arg.setPid(o.getDangerId().toString());
        attachmentBiz.delete(arg);

        if(o.getInfoAttachs()!=null){
            for (Attachment att:o.getInfoAttachs()){
                att.setId(UUIDUtils.generateUuid());
                att.setPid(o.getId().toString());
                att.setType("evt");
                attachmentBiz.insertSelective(att);
            }
        }
        if(o.getDanger()!=null){
            dangerController.edit(o.getDanger());
//            for (Attachment att:o.getDanger().getAttachs()){
//                att.setId(UUIDUtils.generateUuid());
//                att.setPid(o.getDangerId()+"");
//                attachmentBiz.insertSelective(att);
//            }
        }

        logBiz.saveLog("事件管理","更新", "api/matter/event/update");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse delEntity(@PathVariable Integer id) {
        EventInfo o=baseBiz.selectById(id);
        o.setDelFlag(1);
        o.setDelTime(new Date());
        o.setDelUserId(BaseContextHandler.getUserID());
        o.setDelUserName(BaseContextHandler.getUsername());
        baseBiz.updateSelectiveById(o);

        Attachment att=new Attachment();
        att.setPid(id.toString());
        att.setType("evt");
        attachmentBiz.delete(att);
        att.setType("process");
        attachmentBiz.delete(att);

//        att=new Attachment();
//        att.setPid(o.getDangerId().toString());
//        attachmentBiz.delete(att);
///** o.getDangerId()为null时，dangerController.del（）会报错**/
        if(o.getDangerId() !=null) {
            dangerController.del(o.getDangerId());
        }else {

            EventResubmit resubmit = new EventResubmit();
            resubmit.setEvtId(id.toString());
            List<EventResubmit> list = resubmitBiz.selectList(resubmit);
            for (EventResubmit e : list) {
                e.setDelFlag(1);
                e.setDelTime(new Date());
                e.setDelUserId(BaseContextHandler.getUserID());
                e.setDelUserName(BaseContextHandler.getUsername());
                resubmitBiz.updateSelectiveById(e);
            }
        }
        logBiz.saveLog("事件管理","删除", "api/matter/event/delete");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/getpage", method = RequestMethod.GET)
    public TableResultResponse page(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "0") int type, @RequestParam(defaultValue = "") String dealType,
                                    @RequestParam(defaultValue = "") String deptId,@RequestParam(defaultValue = "-1") int dealResult,
                                    @RequestParam(defaultValue = "") String start, @RequestParam(defaultValue = "") String end) throws ParseException {
        //System.out.println(BaseContextHandler.getUserID());
        List<Map<String, Object>> points=checkPointCtr.getpoint(deptId, BaseContextHandler.getUserID());
        if(points.size()==0)
            return  new TableResultResponse<>(0,points);

        List<String> pointIds=points.stream().map(m->m.get("id").toString()).collect(Collectors.toList());
        Date st=null;
        Date et=null;
        if(!start.isEmpty() && !end.isEmpty()){
            st= DateUtils.parseDate(start,new String[]{"yyyy-MM-dd HH:mm:ss"});
            et=DateUtils.parseDate(end,new String[]{"yyyy-MM-dd HH:mm:ss"});
        }

        Page<EventInfo> result = PageHelper.startPage(page, limit);
        List<EventInfo> list = baseBiz.selectByParams(type, dealType, st, et, pointIds,dealResult);

        for (EventInfo o:list){
            Attachment att=new Attachment();
            att.setPid(o.getId().toString());
            att.setType("evt");
            o.setInfoAttachs(attachmentBiz.selectList(att));
        }

        logBiz.saveLog("事件管理","分页查询", "api/matter/event/getpage");
        return  new TableResultResponse<>(result.getTotal(),list);
    }

    @RequestMapping(value = "resubmit/add", method = RequestMethod.POST)
    public ObjectRestResponse addResubmit(@RequestBody EventResubmit o) {
        o.setId(UUIDUtils.generateUuid());
        o.setCrtTime(new Date());
        o.setCrtUserId(BaseContextHandler.getUserID());
        o.setCrtUserName(BaseContextHandler.getUsername());
        resubmitBiz.insertSelective(o);

        logBiz.saveLog("事件管理","添加评论", "api/matter/event/resubmit/add");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "resubmit/delete/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse delResubmit(@PathVariable String id) {
        EventResubmit o=resubmitBiz.selectById(id);
        o.setDelFlag(1);
        o.setDelTime(new Date());
        o.setDelUserId(BaseContextHandler.getUserID());
        o.setDelUserName(BaseContextHandler.getUsername());
        resubmitBiz.updateById(o);

        logBiz.saveLog("事件管理","删除评论", "api/matter/event/resubmit/delete");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "resubmit/{id}", method = RequestMethod.GET)
    public ObjectRestResponse getResubmit(@PathVariable String id) {
        Example e=new Example(EventResubmit.class);
        Example.Criteria c=e.createCriteria();
        c.andEqualTo("evtId", id);

        logBiz.saveLog("事件管理","查看评论", "api/matter/event/resubmit");
        return ObjectRestResponse.ok(resubmitBiz.selectByExample(e));
    }

    @RequestMapping(value = "/chartTime", method = RequestMethod.GET)
    public ObjectRestResponse chartByTime(@RequestParam(defaultValue = "")  String deptId,
                                          @RequestParam(defaultValue = "-1") int type, @RequestParam(defaultValue = "0") int line,
                                          @RequestParam(defaultValue = "")  String start, @RequestParam(defaultValue = "") String end) throws ParseException {
        List<Map<String, Object>> points=checkPointCtr.getpoint(deptId,BaseContextHandler.getUserID());
        if(points.isEmpty())
            return ObjectRestResponse.ok(points);

        Date st=DateUtils.parseDate(start, new String[]{"yyyy-MM-dd HH:mm:ss"});
        Date ed=DateUtils.parseDate(end, new String[]{"yyyy-MM-dd HH:mm:ss"});
        long diff=(ed.getTime()-st.getTime())/(60*60*24*1000);

        List<Map<String, Object>> result=new ArrayList<>();
        List<Map<String, Object>> query;
        List<String> pointIds=points.stream().map(u->u.get("id").toString()).collect(Collectors.toList());
        if(line==0){
            for (int i = 0; i < diff + 1; i++) {
                Date d=DateUtils.addDays(st,i);
                for (int j=0; j<24; j++){
                    d=DateUtils.addHours(d, 1);
                    Map m=new HashMap();
                    m.put("STAT",DateFormatUtils.format(d, "yyyy-MM-dd HH"));
                    m.put("CNT", "0");
                    result.add(m);
                }
            }
            query=baseBiz.statis1(pointIds, st, ed, type);
        }else{
            for (int i = 0; i < diff + 1; i++) {
                Date d=DateUtils.addDays(st,i);
                Map m=new HashMap();
                m.put("STAT",DateFormatUtils.format(d, "yyyy-MM-dd"));
                m.put("CNT", "0");
                result.add(m);
            }
            query=baseBiz.statis2(pointIds, st, ed, type);
        }
        //check list
        for (Map<String, Object> res:result){
            for (Map<String, Object> item:query){
                if(res.get("STAT").equals(item.get("stat"))){
                    res.put("CNT", item.get("cnt"));
                    break;
                }
            }
        }

        logBiz.saveLog("事件管理","时间统计图", "api/matter/event/chartTime");
        return ObjectRestResponse.ok(result);
    }

    @RequestMapping(value = "/chartSite", method = RequestMethod.GET)
    public ObjectRestResponse chartBySite(@RequestParam(defaultValue = "")  String deptId,
                                          @RequestParam(defaultValue = "-1") int type, @RequestParam(defaultValue = "0") int site,
                                          @RequestParam(defaultValue = "")  String start, @RequestParam(defaultValue = "") String end) throws ParseException {
        List<Map<String, Object>> points=checkPointCtr.getpoint(deptId,BaseContextHandler.getUserID());
        if(points.isEmpty())
            return ObjectRestResponse.ok(points);

        Date st=DateUtils.parseDate(start, new String[]{"yyyy-MM-dd HH:mm:ss"});
        Date ed=DateUtils.parseDate(end, new String[]{"yyyy-MM-dd HH:mm:ss"});

        List<Map<String, Object>> result;
        List<String> pointIds=points.stream().map(u->u.get("id").toString()).collect(Collectors.toList());
        if(site==0){
            result=baseBiz.chartSite1(pointIds, st, ed, type);
            for (Map<String, Object> item:result){
                String pointId=item.get("DEPT").toString();
                Map<String, String> depart=checkPointCtr.getDepartInfo(pointId);
                item.put("NAME",depart.get("name")+"-"+item.get("NAME"));
            }
        }else{
            result=baseBiz.chartSite2(pointIds, st, ed, type);
            for (Map<String, Object> item:result){
                String stationId=item.get("DEPT").toString();
                Map<String, String> depart=checkPointCtr.departDetail(stationId);
                item.put("NAME",depart.get("name"));
            }
        }
        logBiz.saveLog("事件管理","站点统计图", "api/matter/event/chartSite");
        return ObjectRestResponse.ok(result);
    }

    @RequestMapping(value = "/chartDanger", method = RequestMethod.GET)
    public ObjectRestResponse chartByDanger(@RequestParam(defaultValue = "")  String deptId, @RequestParam(defaultValue = "-1") int type,
                                          @RequestParam(defaultValue = "")  String start, @RequestParam(defaultValue = "") String end) throws ParseException {
        List<Map<String, Object>> points=checkPointCtr.getpoint(deptId,BaseContextHandler.getUserID());
        if(points.isEmpty())
            return ObjectRestResponse.ok(points);

        Date st=DateUtils.parseDate(start, new String[]{"yyyy-MM-dd HH:mm:ss"});
        Date ed=DateUtils.parseDate(end, new String[]{"yyyy-MM-dd HH:mm:ss"});

        List<Map<String, Object>> result=new ArrayList<>();
        List<Map<String, Object>> query;
        List<String> pointIds=points.stream().map(u->u.get("id").toString()).collect(Collectors.toList());
        query=baseBiz.chartDanger(pointIds, st, ed, type);

        //check list
//        for (Map<String, Object> res:result){
           /* for (Map<String, Object> item:query){
                if(item.get("STAT")==null)
                    item.put("DANGER", "未知分类");
                else if("1".equals(item.get("STAT")))
                    item.put("DANGER", "枪支子弹");
                else if(item.get("STAT").equals("2"))
                    item.put("DANGER", "爆炸物品");
                else if(item.get("STAT").equals("3"))
                    item.put("DANGER", "管制器具");
                else if(item.get("STAT").equals("4"))
                    item.put("DANGER", "易燃易爆");
                else if(item.get("STAT").equals("5"))
                    item.put("DANGER", "毒害品");
                else if(item.get("STAT").equals("6"))
                    item.put("DANGER", "腐蚀性物品");
                else if(item.get("STAT").equals("7"))
                    item.put("DANGER", "放射性物品");
                else if(item.get("STAT").equals("8"))
                    item.put("DANGER", "传染病病原体");
                else if(item.get("STAT").equals("9"))
                    item.put("DANGER", "其他物品");
                else if(item.get("STAT").equals("10"))
                    item.put("DANGER", "法律法规禁止物品");
                else
                    item.put("DANGER", "未知");
            }*/
//        }
        result=query;
        logBiz.saveLog("事件管理","违禁品统计图", "api/matter/event/chartDanger");
        return ObjectRestResponse.ok(result);
    }

    ///////////////////////////报警信息接口
    @RequestMapping(value = "/getAlarm", method = RequestMethod.GET)
    public TableResultResponse getAlarm(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "") String deptId) {
        List<Map<String, Object>> points=checkPointCtr.getpoint(deptId, BaseContextHandler.getUserID());
        if(points.size()==0)
            return  new TableResultResponse<>(0,points);

        List<String> pointIds=points.stream().map(m->m.get("id").toString()).collect(Collectors.toList());

        Page<EventInfo> result = PageHelper.startPage(page, limit);
        List<EventInfo> list = baseBiz.selectAlarm(pointIds);

        logBiz.saveLog("事件管理","报警查询", "api/matter/event/getAlarm");
        return  new TableResultResponse<>(result.getTotal(),list);
    }

    @RequestMapping(value = "/readAlarm/{id}", method = RequestMethod.PUT)
    public ObjectRestResponse readAlarm(@PathVariable Integer id) {
        EventInfo evt=baseBiz.selectById(id);
        if(evt!=null && evt.getReaded()==0){
            evt.setReaded(1);
            evt.setUpdTime(new Date());
            evt.setUpdUserId(BaseContextHandler.getUserID());
            evt.setUpdUserName(BaseContextHandler.getUsername());
            baseBiz.updateSelectiveById(evt);
        }
        logBiz.saveLog("事件管理","查看报警", "api/matter/event/readAlarm");
        return ObjectRestResponse.ok(evt);
    }

    @ApiOperation("获取待办事件数量")
    @RequestMapping(value = "/getCount", method = RequestMethod.GET)
    public ObjectRestResponse getCount(@RequestParam(defaultValue = "") String dealType){
//        List<String> points=adminFeign.getPoint( BaseContextHandler.getUserID());
        List<String> points=userCtr.getPoint( BaseContextHandler.getUserID());

        List<EventInfo> list = baseBiz.selectByParams(1, dealType, null, null, points,0);

        logBiz.saveLog("事件管理","查询待办事件数量", "api/matter/event/getCount");
        return ObjectRestResponse.ok(list.size());
    }

}
