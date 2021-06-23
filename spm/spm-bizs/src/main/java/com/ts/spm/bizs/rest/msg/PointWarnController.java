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
import com.ts.spm.bizs.biz.msg.PointWarnBiz;
import com.ts.spm.bizs.biz.msg.PointWarnPushBiz;
import com.ts.spm.bizs.config.WebSocketUtil;
import com.ts.spm.bizs.entity.msg.PointWarn;
import com.ts.spm.bizs.entity.msg.PointWarnPush;
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
@RequestMapping("warn")
@CheckClientToken
@CheckUserToken
public class PointWarnController extends BaseController<PointWarnBiz, PointWarn, String> {
    @Autowired
    PointWarnPushBiz pointWarnPushBiz;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ObjectRestResponse addEntity(@RequestBody PointWarn o) throws IOException {
        String id= UUIDUtils.generateUuid();
        o.setWarnId(id);
        o.setSendTime(new Date());
        baseBiz.insertSelective(o);

        List<PointWarnPush> pushes=o.getPushes();
        for (PointWarnPush pwp:pushes){
            pwp.setWarnId(id);
            pointWarnPushBiz.insertSelective(pwp);
        }

        ObjectMapper om=new ObjectMapper();
        WebSocketUtil.sendMessage(om.writeValueAsString(o),"pw");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ObjectRestResponse getEntity(@PathVariable String id) {
        PointWarn o=baseBiz.selectById(id);
//        setChildren(o);
        Example exa=new Example(PointWarnPush.class);
        Example.Criteria cr=exa.createCriteria();
        cr.andEqualTo("warnId", o.getWarnId());
        o.setPushes(pointWarnPushBiz.selectByExample(exa));
        return ObjectRestResponse.ok(o);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ObjectRestResponse updateEntity(@PathVariable String id, @RequestBody PointWarn o) {
        baseBiz.updateSelectiveById(o);
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse delEntity(@PathVariable String id) {
        baseBiz.deleteById(id);
        PointWarnPush pwp=new PointWarnPush();
        pwp.setWarnId(id);
        pointWarnPushBiz.delete(pwp);
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/getpage", method = RequestMethod.GET)
    public TableResultResponse page(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "") String place,
                                    @RequestParam(defaultValue = "") String stationId, @RequestParam(defaultValue = "") String lineId,
                                    @RequestParam(defaultValue = "") String start, @RequestParam(defaultValue = "") String end) throws ParseException {
        Example exa=new Example(PointWarn.class);
        Example.Criteria cr=exa.createCriteria();
        if(!"".equals(place))
            cr.andLike("warnPlace", "%"+place+"%");
        if(!"".equals(stationId))
            cr.andLike("stationId", "%"+stationId+"%");
        if(!"".equals(lineId))
            cr.andLike("lineId", "%"+lineId+"%");
        if(!start.isEmpty() && !end.isEmpty()){
            Date st= DateUtils.parseDate(start,new String[]{"yyyy-MM-dd HH:mm:ss"});
            Date et= DateUtils.parseDate(end,new String[]{"yyyy-MM-dd HH:mm:ss"});
            cr.andBetween("sendTime", st, et);
        }
        Page<PointWarn> result = PageHelper.startPage(page, limit);
        List<PointWarn> list = baseBiz.selectByExample(exa);
        return  new TableResultResponse<>(result.getTotal(),list);
    }

//    private void setChildren(PointWarn o) {
//        Example exa=new Example(PointWarnPush.class);
//        Example.Criteria cr=exa.createCriteria();
//        cr.andEqualTo("warnId", o.getWarnId());
//        o.setPushes(pointWarnPushBiz.selectByExample(exa));
//    }

    @RequestMapping(value = "/push", method = RequestMethod.GET)
    public TableResultResponse push(@RequestParam(defaultValue = "") String id, @RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int page) {
        Example exa=new Example(PointWarnPush.class);
        Example.Criteria cr=exa.createCriteria();
        cr.andEqualTo("warnId", id);

        Page<PointWarnPush> result = PageHelper.startPage(page, limit);
        List<PointWarnPush> list=pointWarnPushBiz.selectByExample(exa);
        return  new TableResultResponse<>(result.getTotal(),list);
    }

    @RequestMapping(value = "/readed/{id}", method = RequestMethod.PUT)
    public ObjectRestResponse readed(@PathVariable String id) {
        PointWarnPush ms=new PointWarnPush();
        ms.setWarnId(id);
        ms.setIsRead(1);;
        pointWarnPushBiz.updateSelectiveById(ms);
        return ObjectRestResponse.ok();
    }

}
