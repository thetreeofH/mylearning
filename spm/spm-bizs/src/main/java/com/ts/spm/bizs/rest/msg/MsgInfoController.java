package com.ts.spm.bizs.rest.msg;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.AttachmentBiz;
import com.ts.spm.bizs.biz.LogBiz;
import com.ts.spm.bizs.biz.msg.MsgInfoBiz;
import com.ts.spm.bizs.biz.msg.MsgReceiveBiz;
import com.ts.spm.bizs.biz.msg.MsgReplyBiz;
import com.ts.spm.bizs.config.WebSocketUtil;
import com.ts.spm.bizs.entity.Attachment;
import com.ts.spm.bizs.entity.msg.MsgInfo;
import com.ts.spm.bizs.entity.msg.MsgReceive;
import com.ts.spm.bizs.entity.msg.MsgReply;
import com.ts.spm.bizs.entity.msg.WebSocketInfo;
import com.ts.spm.bizs.mapper.msg.MsgInfoMapper;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import com.ts.spm.bizs.rest.admin.DepartController;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by zhoukun on 2020/4/17.
 *
 * updater  马居朝
 * info/add
 * Line100
 * receive.setMsgId(id);改为
 * receive.setMsgId(msgInfoBiz.getId());
 */
@RestController
@RequestMapping("info")
@CheckClientToken
@CheckUserToken
public class MsgInfoController extends BaseController<MsgInfoBiz, MsgInfo, Integer> {
    @Autowired
    CheckPointController checkPointCtr;
    @Autowired
    DepartController departCtr;
    @Autowired
    MsgReceiveBiz msgReceiveBiz;
    @Autowired
    MsgInfoBiz msgInfoBiz;
    @Autowired
    MsgReplyBiz msgReplyBiz;
    @Autowired
    AttachmentBiz attachmentBiz;
    @Autowired
    LogBiz logBiz;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ObjectRestResponse addEntity(@RequestBody MsgInfo o) throws IOException {
        Integer id=baseBiz.getId();
        o.setId(id);

        Map<String, String> depart=departCtr.getDepartDetail(BaseContextHandler.getDepartID());
        if(depart!=null)
            o.setDepartName(depart.get("name"));

        baseBiz.insertSelective(o);

        if(o.getAttachments()!=null){
            for (Attachment attachment:o.getAttachments()){
                attachment.setId(UUIDUtils.generateUuid());
                attachment.setPid(id.toString());
                attachment.setType("msg");
                attachmentBiz.insertSelective(attachment);
            }
        }

        //信息类别，安检点等同于一个人，所以都是一个流程了
//        List<MsgReceive> receives=o.getMsgReceives();
//        for (MsgReceive receive:receives){
//            receive.setId(msgReceiveBiz.getId());
//            receive.setMsgId(id);
//            msgReceiveBiz.insertSelective(receive);
//        }
        if(o.getType()==0){
            //todo 普通信息
            List<MsgReceive> receives=o.getMsgReceives();
            for (MsgReceive receive:receives){
                receive.setId(msgReceiveBiz.getId());
                receive.setMsgId(id);
                msgReceiveBiz.insertSelective(receive);
            }
        }else{
            //todo 警情、任务
            String receiveDeptIds=o.getReceiveDeptId();
            String[] arr=receiveDeptIds.split(",");
            for (String deptId:arr){
                List<Map<String, Object>> users=checkPointCtr.getpoint(deptId,"");
                for(Map<String, Object> user:users){
                    MsgReceive rec=new MsgReceive();
                    rec.setId(msgReceiveBiz.getId());
                    rec.setMsgId(id);
                    rec.setReceiveUserId(user.get("id").toString());
                    rec.setReceiveUserName(user.get("name").toString());
                    rec.setDepartId(o.getDepartId());
                    rec.setDepartName(o.getDepartName());
                    msgReceiveBiz.insertSelective(rec);
                }
            }
        }

        String[] arr=new String[]{"msg","event","task"};
        ObjectMapper om=new ObjectMapper();
        WebSocketInfo ws=new WebSocketInfo();
        ws.setType(arr[o.getType()]);
        MsgReceive mr=new MsgReceive();
        mr.setReceiveUserId(BaseContextHandler.getUserID());
        mr.setReadState(0);
        ws.setInfo(msgReceiveBiz.selectCount(mr));
        send(om.writeValueAsString(ws));

        logBiz.saveLog("信息管理","添加", "api/msg/info/add");

        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ObjectRestResponse getEntity(@PathVariable Integer id) {
        MsgInfo o=baseBiz.selectById(id);
        MsgReceive mr=new MsgReceive();
        mr.setMsgId(o.getId());
        o.setMsgReceives(msgReceiveBiz.selectList(mr));

        Attachment att=new Attachment();
        att.setPid(o.getId().toString());
        att.setType("msg");
        o.setAttachments(attachmentBiz.selectList(att));

        MsgReply r=new MsgReply();
        r.setMsgId(o.getId());
        List<MsgReply> replyList=msgReplyBiz.selectList(r);
        o.setReplies(replyList);
        for (MsgReply re:replyList){
            att=new Attachment();
            att.setPid(re.getId().toString());
            att.setType("reply");
            re.setAttaches(attachmentBiz.selectList(att));
        }

        logBiz.saveLog("信息管理","查询", "api/msg/info/get");
        return ObjectRestResponse.ok(o);
    }

    public ObjectRestResponse getEntity2(Integer id, String pointId) {
        MsgInfo o=baseBiz.selectById(id);

        MsgReceive mr=new MsgReceive();
        mr.setMsgId(o.getId());
        List<MsgReceive> receives=msgReceiveBiz.selectList(mr);
        o.setMsgReceives(receives);

//        for (MsgReceive receive:receives){
//            if(pointId.equals(receive.getReceiveUserId())){
//                o.setCompleteTime(receive.getFinishTime());
//                break;
//            }
//        }

        Attachment att=new Attachment();
        att.setPid(o.getId().toString());
        att.setType("msg");
        o.setAttachments(attachmentBiz.selectList(att));

        MsgReply r=new MsgReply();
        r.setMsgId(o.getId());
        r.setReplyUserId(pointId);
        List<MsgReply> replyList=msgReplyBiz.selectList(r);
        o.setReplies(replyList);
        for (MsgReply re:replyList){
            att=new Attachment();
            att.setPid(re.getId().toString());
            att.setType("reply");
            re.setAttaches(attachmentBiz.selectList(att));
        }
        return ObjectRestResponse.ok(o);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse delEntity(@PathVariable Integer id) {
        MsgInfo o=new MsgInfo();
        o.setId(id);
        o.setDelFlag(1);
        o.setDelTime(new Date());
        o.setDelUserId(BaseContextHandler.getUserID());
        o.setDelUserName(BaseContextHandler.getUsername());
        baseBiz.updateSelectiveById(o);

        MsgReceive ms=new MsgReceive();
        ms.setMsgId(id);
        msgReceiveBiz.delete(ms);

        Attachment att=new Attachment();
        att.setPid(id.toString());
        att.setType("msg");
        attachmentBiz.delete(att);

        logBiz.saveLog("信息管理","删除", "api/msg/info/delete");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/getpage", method = RequestMethod.GET)
    public TableResultResponse page(@RequestParam(defaultValue = "0") int flag, @RequestParam(defaultValue = "-1") int type,
                @RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int page,
                @RequestParam(defaultValue = "") String title, @RequestParam(defaultValue = "") String receiveDeptId,
                @RequestParam(defaultValue = "") String start, @RequestParam(defaultValue = "") String end) throws ParseException {
        Example exa=new Example(MsgInfo.class);
        Example.Criteria cr=exa.createCriteria();
        cr.andEqualTo("delFlag",0);
        if(type>=0)
            cr.andEqualTo("type", type);
        if(!title.isEmpty())
            cr.andLike("title", "%"+title+"%");
        if(!receiveDeptId.isEmpty())
            cr.andLike("receiveDeptId", "%"+receiveDeptId+"%");
        Date st=null,et=null;
        if(!start.isEmpty() && !end.isEmpty()){
            st=DateUtils.parseDate(start,new String[]{"yyyy-MM-dd HH:mm:ss"});
            et=DateUtils.parseDate(end,new String[]{"yyyy-MM-dd HH:mm:ss"});
            cr.andBetween("crtTime", st, et);
        }
        exa.setOrderByClause(" CRT_TIME desc");

        List<MsgReceive> rs=null;
        List<Integer> ids=null;

        Page<MsgInfo> result =null;
        List<MsgInfo> list =null;

        if(flag==0) {
            cr.andEqualTo("crtUserId", BaseContextHandler.getUserID());
            result = PageHelper.startPage(page, limit);
            list = baseBiz.selectByExample(exa);
        }else if(flag==1){
            MsgReceive mr=new MsgReceive();
            mr.setReceiveUserId(BaseContextHandler.getUserID());
            rs=msgReceiveBiz.selectList(mr);
            if (!rs.isEmpty()){
                cr.andIn("id", rs.stream().map(MsgReceive::getMsgId).collect(Collectors.toList()));
                ids=rs.stream().map(MsgReceive::getMsgId).collect(Collectors.toList());
            }
            result = PageHelper.startPage(page, limit);
            list = baseBiz.selectPage(flag,type,title,receiveDeptId,BaseContextHandler.getUserID(),st,et,ids);
        }else {
            return new TableResultResponse<>(0,null);
        }

        System.out.println("list = " + list.size());

        for (MsgInfo msg:list){
            MsgReceive arg=new MsgReceive();
            arg.setMsgId(msg.getId());
            List<MsgReceive> res=msgReceiveBiz.selectList(arg);
            msg.setMsgReceives(res);
            for (MsgReceive mr: res){
                MsgReply reply=new MsgReply();
                reply.setMsgId(msg.getId());
                reply.setReplyUserId(mr.getReceiveUserId());
                List<MsgReply> replies=msgReplyBiz.selectList(reply);
                mr.setReplies(replies);

                for (MsgReply o:replies){
                    Attachment att=new Attachment();
                    att.setPid(o.getId().toString());
                    att.setType("reply");
                    o.setAttaches(attachmentBiz.selectList(att));
                }
            }

            Attachment att=new Attachment();
            att.setPid(msg.getId().toString());
            att.setType("msg");
            msg.setAttachments(attachmentBiz.selectList(att));
        }

        logBiz.saveLog("信息管理","分页查询", "api/msg/info/getpage");
        return new TableResultResponse<>(result.getTotal(),list);
    }

    @IgnoreClientToken
    @IgnoreUserToken
    @RequestMapping(value = "/getpage2", method = RequestMethod.GET)
    public TableResultResponse page2(@RequestParam(defaultValue = "1") int flag, @RequestParam(defaultValue = "-1") int type,
                @RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int page,
                @RequestParam(defaultValue = "") String title, @RequestParam(defaultValue = "") String pointId,
                @RequestParam(defaultValue = "") String start, @RequestParam(defaultValue = "") String end) throws ParseException {
        Example exa=new Example(MsgInfo.class);
        Example.Criteria cr=exa.createCriteria();

        if(flag==0) {
            cr.andEqualTo("crtUserId", BaseContextHandler.getUserID());
        }else if(flag==1){
            MsgReceive mr=new MsgReceive();
            mr.setReceiveUserId(pointId);
            List<MsgReceive> list=msgReceiveBiz.selectList(mr);
            if (!list.isEmpty())
                cr.andIn("id", list.stream().map(MsgReceive::getMsgId).collect(Collectors.toList()));
            else
                return new TableResultResponse<>(0,null);
        }else {
            return new TableResultResponse<>(0,null);
        }

        cr.andEqualTo("delFlag",0);
        if(type>=0)
            cr.andEqualTo("type", type);
        if(!title.isEmpty())
            cr.andLike("title", "%"+title+"%");

        if(!start.isEmpty() && !end.isEmpty()){
            Date st=DateUtils.parseDate(start,new String[]{"yyyy-MM-dd HH:mm:ss"});
            Date et=DateUtils.parseDate(end,new String[]{"yyyy-MM-dd HH:mm:ss"});
            cr.andBetween("crtTime", st, et);
        }
        exa.setOrderByClause(" CRT_TIME desc");

        Page<MsgInfo> result = PageHelper.startPage(page, limit);
        List<MsgInfo> list = baseBiz.selectByExample(exa);

        for (MsgInfo msg:list){
            MsgReceive arg=new MsgReceive();
            arg.setMsgId(msg.getId());
            List<MsgReceive> res=msgReceiveBiz.selectList(arg);
            msg.setMsgReceives(res);
            for (MsgReceive mr: res){
                MsgReply reply=new MsgReply();
                reply.setMsgId(msg.getId());
                reply.setReplyUserId(mr.getReceiveUserId());
                List<MsgReply> replies=msgReplyBiz.selectList(reply);
                mr.setReplies(replies);

                for (MsgReply o:replies){
                    Attachment att=new Attachment();
                    att.setPid(o.getId().toString());
                    att.setType("reply");
                    o.setAttaches(attachmentBiz.selectList(att));
                }
            }

            Attachment att=new Attachment();
            att.setPid(msg.getId().toString());
            att.setType("msg");
            msg.setAttachments(attachmentBiz.selectList(att));
        }

//        logBiz.saveLog("信息管理","分页查询", "api/msg/info/getpage2");
        return new TableResultResponse<>(result.getTotal(),list);
    }

    @RequestMapping(value = "/receive/get/{id}", method = RequestMethod.GET)
    public ObjectRestResponse readed(@PathVariable Integer id) {
        MsgReceive mr=new MsgReceive();
        mr.setMsgId(id);
        mr.setReceiveUserId(BaseContextHandler.getUserID());
        List<MsgReceive> list=msgReceiveBiz.selectList(mr);
        if(list.size()>0){
            mr=list.get(0);
            if(mr.getReadState()==0){
                mr.setReadState(1);
                mr.setReadTime(new Date());
                msgReceiveBiz.updateSelectiveById(mr);
            }
        }

        //details
        MsgInfo o=baseBiz.selectById(id);
        //接收人
//        MsgReceive mr=new MsgReceive();
//        mr.setMsgId(o.getId());
//        List<MsgReceive> receives=msgReceiveBiz.selectList(mr);
//        o.setMsgReceives(receives);
        //附件
        Attachment att=new Attachment();
        att.setPid(o.getId().toString());
        att.setType("msg");
        o.setAttachments(attachmentBiz.selectList(att));
        //回复
        MsgReply r=new MsgReply();
        r.setMsgId(o.getId());
        r.setReplyUserId(BaseContextHandler.getUserID());
        List<MsgReply> replyList=msgReplyBiz.selectList(r);
        o.setReplies(replyList);
        for (MsgReply re:replyList){
            att=new Attachment();
            att.setPid(re.getId().toString());
            att.setType("reply");
            re.setAttaches(attachmentBiz.selectList(att));
        }

        logBiz.saveLog("信息管理","详情", "api/msg/info/receive/get");
        return ObjectRestResponse.ok(o);
    }

    @IgnoreClientToken
    @IgnoreUserToken
    @RequestMapping(value = "/receive/get/{id}/{pointId}", method = RequestMethod.GET)
    public ObjectRestResponse readed(@PathVariable Integer id,@PathVariable String pointId) {
        MsgReceive mr=new MsgReceive();
        mr.setMsgId(id);
        mr.setReceiveUserId(pointId);
        List<MsgReceive> list=msgReceiveBiz.selectList(mr);
        if(list.size()>0){
            mr=list.get(0);
            if(mr.getReadState()==0){
                mr.setReadState(1);
                mr.setReadTime(new Date());
                msgReceiveBiz.updateSelectiveById(mr);
            }
        }

        return getEntity2(id, pointId);
    }

    @RequestMapping(value = "/receive/update/{id}", method = RequestMethod.PUT)
    public ObjectRestResponse receiveUpdate(@PathVariable Integer id, @RequestBody MsgReceive mr) {
        msgReceiveBiz.updateSelectiveById(mr);
        logBiz.saveLog("信息管理","更新接收人", "api/msg/info/receive/update/{id}");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/receive/count", method = RequestMethod.GET)
    public ObjectRestResponse unReadedCount() {
        MsgReceive mr=new MsgReceive();
        mr.setReceiveUserId(BaseContextHandler.getUserID());
        mr.setReadState(0);

        Map<String,Object> map=new HashMap<>(1);
        map.put("cnt",msgReceiveBiz.selectCount(mr));

        logBiz.saveLog("信息管理","查询未读数", "api/msg/info/receive/count");
        return ObjectRestResponse.ok(map);
    }

    @RequestMapping(value = "/reply/add", method = RequestMethod.POST)
    public ObjectRestResponse addReply(@RequestBody MsgReply o) {
        Integer replyId=msgReplyBiz.getId();
        o.setId(replyId);
        o.setReplyUserId(BaseContextHandler.getUserID());
        o.setReplyUserName(BaseContextHandler.getUsername());
        msgReplyBiz.insertSelective(o);

        if(o.getAttaches()!=null){
            for (Attachment att:o.getAttaches()){
                att.setId(UUIDUtils.generateUuid());
                att.setPid(replyId.toString());
                att.setType("reply");
                attachmentBiz.insertSelective(att);
            }
        }
        logBiz.saveLog("信息管理","添加回复", "api/msg/info/reply/add");
        return ObjectRestResponse.ok();
    }

    @IgnoreClientToken
    @IgnoreUserToken
    @RequestMapping(value = "/reply/add2", method = RequestMethod.POST)
    public ObjectRestResponse addReply2(@RequestBody MsgReply o) {
        Integer replyId=msgReplyBiz.getId();
        o.setId(replyId);
//        o.setReplyUserId(BaseContextHandler.getUserID());
//        o.setReplyUserName(BaseContextHandler.getUsername());
        msgReplyBiz.insertSelective(o);

        if(o.getAttaches()!=null){
            for (Attachment att:o.getAttaches()){
                att.setId(UUIDUtils.generateUuid());
                att.setPid(replyId.toString());
                att.setType("reply");
                attachmentBiz.insertSelective(att);
            }
        }
        logBiz.saveLog("信息管理","添加回复", "api/msg/info/reply/add2");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/reply/update/{id}", method = RequestMethod.PUT)
    public ObjectRestResponse updateReply(@PathVariable Integer id) {
        MsgReply mr=new MsgReply();
        mr.setId(id);
        mr.setReadState(1);
        mr.setReadTime(new Date());
        msgReplyBiz.updateSelectiveById(mr);

        logBiz.saveLog("信息管理","更新回复", "api/msg/info/reply/add");
        return ObjectRestResponse.ok();
    }

    @IgnoreClientToken
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public void send(String msg) throws IOException {
        WebSocketUtil.sendMessage(msg,"center");
    }
}
