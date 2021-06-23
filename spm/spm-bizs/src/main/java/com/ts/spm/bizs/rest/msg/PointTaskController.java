package com.ts.spm.bizs.rest.msg;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.msg.PointTaskBiz;
import com.ts.spm.bizs.biz.msg.PointTaskPushBiz;
import com.ts.spm.bizs.config.WebSocketUtil;
import com.ts.spm.bizs.entity.msg.PointTask;
import com.ts.spm.bizs.entity.msg.PointTaskPush;
import org.apache.commons.lang.time.DateUtils;
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
@RequestMapping("task")
@CheckClientToken
@CheckUserToken
public class PointTaskController extends BaseController<PointTaskBiz, PointTask, String> {
    @Autowired
    PointTaskPushBiz pointTaskPushBiz;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ObjectRestResponse addEntity(@RequestBody PointTask o) throws IOException {
        String id= UUIDUtils.generateUuid();
        o.setId(id);
        o.setSendTime(new Date());
        baseBiz.insertSelective(o);

//        List<PointTaskPush> pushes=o.getPushes();
//        for (PointTaskPush pwp:pushes){
//            pwp.setId(UUIDUtils.generateUuid());
//            pwp.setTaskId(id);
//            pointTaskPushBiz.insertSelective(pwp);
//        }

        ObjectMapper om=new ObjectMapper();
        WebSocketUtil.sendMessage(om.writeValueAsString(o),"pt");
        return ObjectRestResponse.ok(o);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse delEntity(@PathVariable String id) {
        baseBiz.deleteById(id);
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/getpage", method = RequestMethod.GET)
    public TableResultResponse page(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "") String text,
                                    @RequestParam(defaultValue = "") String start, @RequestParam(defaultValue = "") String end) throws ParseException {
        Example exa=new Example(PointTask.class);
        Example.Criteria cr=exa.createCriteria();
        if(!"".equals(text))
            cr.andLike("taskText", "%"+text+"%");
        if(!start.isEmpty() && !end.isEmpty()){
            Date st= DateUtils.parseDate(start,new String[]{"yyyy-MM-dd HH:mm:ss"});
            Date et= DateUtils.parseDate(end,new String[]{"yyyy-MM-dd HH:mm:ss"});
            cr.andBetween("sendTime", st, et);
        }
        Page<PointTask> result = PageHelper.startPage(page, limit);
        List<PointTask> list = baseBiz.selectByExample(exa);
        list.forEach(this::setChildren);
        return  new TableResultResponse<>(result.getTotal(),list);
    }

    private void setChildren(PointTask o) {
        Example exa=new Example(PointTaskPush.class);
        Example.Criteria cr=exa.createCriteria();
        cr.andEqualTo("taskId", o.getId());
        o.setPushes(pointTaskPushBiz.selectByExample(exa));
    }

    @RequestMapping(value = "/push", method = RequestMethod.GET)
    public TableResultResponse push(@RequestParam(defaultValue = "") String id, @RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int page) {
        Example exa=new Example(PointTaskPush.class);
        Example.Criteria cr=exa.createCriteria();
        cr.andEqualTo("taskId", id);

        Page<PointTaskPush> result = PageHelper.startPage(page, limit);
        List<PointTaskPush> list=pointTaskPushBiz.selectByExample(exa);
        return  new TableResultResponse<>(result.getTotal(),list);
    }

    @RequestMapping(value = "/push/{id}", method = RequestMethod.GET)
    public ObjectRestResponse getEntity(@PathVariable String id) {
        PointTaskPush o=pointTaskPushBiz.selectById(id);
        return ObjectRestResponse.ok(o);
    }

    @RequestMapping(value = "/push/update/{id}", method = RequestMethod.PUT)
    public ObjectRestResponse updatePush(@PathVariable String id, @RequestBody PointTaskPush o) {
        o.setFinishTime(new Date());
        o.setIsFinish(1);
        o.setIsSend(1);
        pointTaskPushBiz.updateSelectiveById(o);
        return ObjectRestResponse.ok();
    }


}
