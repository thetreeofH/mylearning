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
import com.ts.spm.bizs.biz.matter.SecurityLevelBiz;
import com.ts.spm.bizs.biz.matter.SecurityLevelTimeBiz;
import com.ts.spm.bizs.entity.matter.SecurityLevel;
import com.ts.spm.bizs.entity.matter.SecurityLevelTime;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * Created by zhoukun on 2020/4/17.
 */
@RestController
@RequestMapping("levelTime")
@CheckClientToken
@CheckUserToken
public class SecurityLevelTimeController extends BaseController<SecurityLevelTimeBiz, SecurityLevelTime, String> {

    @Autowired
    SecurityLevelBiz securityLevelBiz;
    @Autowired
    LogBiz logBiz;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ObjectRestResponse addEntity(@RequestBody SecurityLevelTime o){
        o.setId(UUIDUtils.generateUuid());
        o.setCrtTime(new Date());
        o.setCrtUserId(BaseContextHandler.getUserID());
        o.setCrtUserName(BaseContextHandler.getUsername());
        baseBiz.insertSelective(o);
        logBiz.saveLog("安检等级","时间段配置", "api/matter/levelTime/add");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ObjectRestResponse getEntity(@PathVariable String id) {
        logBiz.saveLog("安检等级","时间段配置详情", "api/matter/levelTime/get");
        return ObjectRestResponse.ok(baseBiz.selectById(id));
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ObjectRestResponse updateEntity(@PathVariable String id,@RequestBody SecurityLevelTime o) {
        o.setUpdTime(new Date());
        o.setUpdUserId(BaseContextHandler.getUserID());
        o.setUpdUserName(BaseContextHandler.getUsername());
        baseBiz.updateSelectiveById(o);
        logBiz.saveLog("安检等级","时间段配置更新", "api/matter/levelTime/update");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse delEntity(@PathVariable String id) {
        baseBiz.deleteById(id);
        logBiz.saveLog("安检等级","时间段配置删除", "api/matter/levelTime/delete");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/getlist", method = RequestMethod.GET)
    public ObjectRestResponse getlist(@RequestParam(defaultValue = "") String level) {
        SecurityLevel SecurityLevel=securityLevelBiz.selectById(level);

        Example exa = new Example(SecurityLevelTime.class);
        Example.Criteria crit = exa.createCriteria();
        crit.andEqualTo("levelId", level);
        List<SecurityLevelTime> list = baseBiz.selectByExample(exa);

        List<Map<String, Object>> res=new ArrayList<>();
        for (SecurityLevelTime o: list){
            Map<String, Object> m=new HashMap<>();
            m.put("id", o.getId());
            m.put("levelName", SecurityLevel.getName());
            m.put("levelId", o.getLevelId());
            m.put("startTime", o.getStarttime());
            m.put("endTime", o.getEndtime());
            if(o.getRank()==2)
                m.put("rank", "高峰");
            else if(o.getRank()==1)
                m.put("rank", "平峰");
            else
                m.put("rank","低峰");
            res.add(m);
        }
        logBiz.saveLog("安检等级","时间段配置查询", "api/matter/levelTime/getlist");
        return  ObjectRestResponse.ok(res);
    }

}
