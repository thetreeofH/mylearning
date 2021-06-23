package com.ts.spm.bizs.rest.msg;

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
import com.ts.spm.bizs.biz.msg.MessageInfoBiz;
import com.ts.spm.bizs.biz.msg.MessageStatusBiz;
import com.ts.spm.bizs.config.WebSocketUtil;
import com.ts.spm.bizs.entity.msg.MessageInfo;
import com.ts.spm.bizs.entity.msg.MessageStatus;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by zhoukun on 2020/4/17.
 */
@RestController
@RequestMapping("message")
@CheckClientToken
@CheckUserToken
public class MessageInfoController extends BaseController<MessageInfoBiz, MessageInfo, String> {
    @Autowired
    MessageStatusBiz messageStatusBiz;
    @Autowired
    AttachmentBiz attachmentBiz;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ObjectRestResponse addEntity(@RequestBody MessageInfo o) throws IOException {
        String id= UUIDUtils.generateUuid();
        o.setId(id);
        o.setMessageDate(new Date());
        o.setCrtTime(new Date());
        o.setCrtUserId(BaseContextHandler.getUserID());
        o.setCrtUserName(BaseContextHandler.getUsername());
        o.setCrtDeptId(BaseContextHandler.getDepartID());
        baseBiz.insertSelective(o);

//        if(o.getAttachments()!=null){
//            for (Attachment attachment:o.getAttachments()){
//                attachment.setId(UUIDUtils.generateUuid());
//                attachment.setPid(id);
//                attachmentBiz.insertSelective(attachment);
//            }
//        }

        String str=o.getReceiveId();
        String[] arr_id=str.split(",");
        String[] arr_name=str.split(",");
        for (int i = 0; i < arr_id.length; i++) {
            MessageStatus ms=new MessageStatus();
            ms.setId(UUIDUtils.generateUuid());
            ms.setMessageId(id);
            ms.setMessageDate(DateFormatUtils.format(o.getMessageDate(),"yyyy-MM-dd HH:mm:ss"));
            ms.setReceiveId(arr_id[i]);
            ms.setReceiveName(arr_name[i]);
            ms.setReadStatus("0");

            ms.setCrtTime(new Date());
            ms.setCrtUserId(BaseContextHandler.getUserID());
            ms.setCrtUserName(BaseContextHandler.getUsername());
            ms.setCrtDeptId(BaseContextHandler.getDepartID());
            messageStatusBiz.insertSelective(ms);
        }

        ObjectMapper om=new ObjectMapper();
        WebSocketUtil.sendMessage(om.writeValueAsString(o),null);
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ObjectRestResponse getEntity(@PathVariable String id) {
        MessageInfo o=baseBiz.selectById(id);
        setChildren(o);
        return ObjectRestResponse.ok(o);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ObjectRestResponse updateEntity(@PathVariable String id, @RequestBody MessageInfo o) {
        o.setUpdTime(new Date());
        o.setUpdUserId(BaseContextHandler.getUserID());
        o.setUpdUserName(BaseContextHandler.getUsername());
        o.setUpdDeptId(BaseContextHandler.getDepartID());
        baseBiz.updateSelectiveById(o);
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse delEntity(@PathVariable String id) {
        baseBiz.deleteById(id);
//        Attachment attachment=new Attachment();
//        attachment.setPid(id);
//        attachmentBiz.delete(attachment);

        MessageStatus ms=new MessageStatus();
        ms.setMessageId(id);
        messageStatusBiz.delete(ms);
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/getpage", method = RequestMethod.GET)
    public TableResultResponse page(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "") String title, @RequestParam(defaultValue = "") String receiveId,
                                    @RequestParam(defaultValue = "") String sender,
                                    @RequestParam(defaultValue = "") String start, @RequestParam(defaultValue = "") String end) throws ParseException {
        Example exa=new Example(MessageInfo.class);
        Example.Criteria cr=exa.createCriteria();
        if(!"".equals(title))
            cr.andLike("messageName", "%"+sender+"%");
        if(!"".equals(title))
            cr.andLike("messageTitle", "%"+title+"%");
        if(!"".equals(receiveId))
            cr.andLike("receiveId", "%"+receiveId+"%");
        if(!start.isEmpty() && !end.isEmpty()){
            Date st= DateUtils.parseDate(start,new String[]{"yyyy-MM-dd HH:mm:ss"});
            Date et= DateUtils.parseDate(end,new String[]{"yyyy-MM-dd HH:mm:ss"});
            cr.andBetween("messageDate", st, et);
        }

        Page<MessageInfo> result = PageHelper.startPage(page, limit);
        List<MessageInfo> list = baseBiz.selectByExample(exa);
        list.forEach(this::setChildren);
        return  new TableResultResponse<>(result.getTotal(),list);
    }

    private void setChildren(MessageInfo o) {
        Example exa=new Example(MessageStatus.class);
        Example.Criteria cr=exa.createCriteria();
        cr.andEqualTo("messageId", o.getId());
        o.setStatuses(messageStatusBiz.selectByExample(exa));
    }

    @RequestMapping(value = "/readed/{id}", method = RequestMethod.PUT)
    public ObjectRestResponse readed(@PathVariable String id) {
        MessageStatus ms=new MessageStatus();
        ms.setId(id);
        ms.setReadStatus("1");
        messageStatusBiz.updateSelectiveById(ms);
        return ObjectRestResponse.ok();
    }

}
