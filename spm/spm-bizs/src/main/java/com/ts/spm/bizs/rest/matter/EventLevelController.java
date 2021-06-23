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
import com.ts.spm.bizs.biz.LogBiz;
import com.ts.spm.bizs.biz.matter.EventLevelBiz;
import com.ts.spm.bizs.entity.matter.EventLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by zhoukun on 2020/4/17.
 */
@RestController
@RequestMapping("eventLevel")
@CheckClientToken
@CheckUserToken
public class EventLevelController extends BaseController<EventLevelBiz, EventLevel, String> {
    @Autowired
    LogBiz logBiz;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ObjectRestResponse addEntity(@RequestBody EventLevel o){
        o.setId(UUIDUtils.generateUuid());
        o.setCrtTime(new Date());
        o.setCrtUserId(BaseContextHandler.getUserID());
        o.setCrtUserName(BaseContextHandler.getUsername());
        o.setCrtDeptId(BaseContextHandler.getDepartID());
        baseBiz.insertSelective(o);
        logBiz.saveLog("事件级别","添加", "api/matter/eventLevel/add");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ObjectRestResponse getEntity(@PathVariable String id) {
        logBiz.saveLog("事件级别","查看详情", "api/matter/eventLevel/get");
        return ObjectRestResponse.ok(baseBiz.selectById(id));
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ObjectRestResponse updateEntity(@PathVariable String id,@RequestBody EventLevel o) {
        o.setUpdTime(new Date());
        o.setUpdUserId(BaseContextHandler.getUserID());
        o.setUpdUserName(BaseContextHandler.getUsername());
        o.setUpdDeptId(BaseContextHandler.getDepartID());
        baseBiz.updateSelectiveById(o);
        logBiz.saveLog("事件级别","更新", "api/matter/eventLevel/update");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse delEntity(@PathVariable String id) {
        baseBiz.deleteById(id);
        logBiz.saveLog("事件级别","删除", "api/matter/eventLevel/delete");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/getpage", method = RequestMethod.GET)
    public TableResultResponse page(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int page) {
        Page<EventLevel> result = PageHelper.startPage(page, limit);
        List<EventLevel> list = baseBiz.selectListAll();
        logBiz.saveLog("事件级别","分页查询", "api/matter/eventLevel/getpage");
        return  new TableResultResponse<>(result.getTotal(),list);
    }

}
