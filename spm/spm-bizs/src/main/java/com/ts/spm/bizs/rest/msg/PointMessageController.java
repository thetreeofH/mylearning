package com.ts.spm.bizs.rest.msg;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.msg.PointMessageBiz;
import com.ts.spm.bizs.entity.msg.PointMessage;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by zhoukun on 2020/4/17.
 */
@RestController
@RequestMapping("pointMessage")
@CheckClientToken
@CheckUserToken
public class PointMessageController extends BaseController<PointMessageBiz, PointMessage, String> {

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ObjectRestResponse addEntity(@RequestBody PointMessage o){
        o.setPmId(UUIDUtils.generateUuid());
        o.setPmDate(new Date());
//        o.setCrtTime(new Date());
//        o.setCrtUserId(BaseContextHandler.getUserID());
//        o.setCrtUserName(BaseContextHandler.getUsername());
//        o.setCrtDeptId(BaseContextHandler.getDepartID());
        baseBiz.insertSelective(o);
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ObjectRestResponse getEntity(@PathVariable String id) {
        return ObjectRestResponse.ok(baseBiz.selectById(id));
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ObjectRestResponse updateEntity(@PathVariable String id, @RequestBody PointMessage o) {
//        o.setUpdTime(new Date());
//        o.setUpdUserId(BaseContextHandler.getUserID());
//        o.setUpdUserName(BaseContextHandler.getUsername());
//        o.setUpdDeptId(BaseContextHandler.getDepartID());
        baseBiz.updateSelectiveById(o);
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse delEntity(@PathVariable String id) {
        baseBiz.deleteById(id);
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/getpage", method = RequestMethod.GET)
    public TableResultResponse page(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "") String stationId, @RequestParam(defaultValue = "") String pointId,
                                    @RequestParam(defaultValue = "") String content, @RequestParam(defaultValue = "") String sender,
                                    @RequestParam(defaultValue = "") String start, @RequestParam(defaultValue = "") String end) throws ParseException {
        Example exa=new Example(PointMessage.class);
        Example.Criteria cr=exa.createCriteria();
        if(!"".equals(content))
            cr.andLike("pmContent", "%"+content+"%");
        if(!"".equals(sender))
            cr.andLike("messageTitle", "%"+sender+"%");
        if(!"".equals(stationId))
            cr.andEqualTo("stationId", stationId);
        if(!"".equals(pointId))
            cr.andEqualTo("pointId", pointId);
        if(!start.isEmpty() && !end.isEmpty()){
            Date st= DateUtils.parseDate(start,new String[]{"yyyy-MM-dd HH:mm:ss"});
            Date et= DateUtils.parseDate(end,new String[]{"yyyy-MM-dd HH:mm:ss"});
            cr.andBetween("pmDate", st, et);
        }
        Page<PointMessage> result = PageHelper.startPage(page, limit);
        List<PointMessage> list = baseBiz.selectByExample(exa);
        return  new TableResultResponse<>(result.getTotal(),list);
    }

}
