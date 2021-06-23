package com.ts.spm.bizs.rest.msg;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.AttachmentBiz;
import com.ts.spm.bizs.entity.Attachment;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by zhoukun on 2020/4/17.
 */
@RestController
@RequestMapping("attachment")
@CheckClientToken
@CheckUserToken
public class AttachmentController extends BaseController<AttachmentBiz, Attachment, String> {

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ObjectRestResponse addEntity(@RequestBody Attachment o) throws IOException {
        o.setId(UUIDUtils.generateUuid());
        baseBiz.insertSelective(o);
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse delEntity(@PathVariable String id) {
        baseBiz.deleteById(id);
        return ObjectRestResponse.ok();
    }
    @RequestMapping(value = "/pid/delete/{pid}", method = RequestMethod.DELETE)
    public ObjectRestResponse delEntityByPid(@PathVariable String pid) {
        Attachment attachment=new Attachment();
        attachment.setPid(pid);
        baseBiz.delete(attachment);
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ObjectRestResponse page(@RequestParam(defaultValue = "") String pid) {
        Attachment attachment=new Attachment();
        attachment.setPid(pid);
        return ObjectRestResponse.ok(baseBiz.selectList(attachment));
    }

}
