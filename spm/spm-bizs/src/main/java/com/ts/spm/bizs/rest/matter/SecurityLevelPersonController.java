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
import com.ts.spm.bizs.biz.matter.SecurityLevelPersonBiz;
import com.ts.spm.bizs.entity.matter.SecurityLevel;
import com.ts.spm.bizs.entity.matter.SecurityLevelPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * Created by zhoukun on 2020/4/17.
 */
@RestController
@RequestMapping("levelPerson")
@CheckClientToken
@CheckUserToken
public class SecurityLevelPersonController extends BaseController<SecurityLevelPersonBiz, SecurityLevelPerson, String> {

    @Autowired
    SecurityLevelBiz securityLevelBiz;
    @Autowired
    LogBiz logBiz;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ObjectRestResponse addEntity(@RequestBody SecurityLevelPerson o){
        o.setId(UUIDUtils.generateUuid());
        baseBiz.insertSelective(o);
        logBiz.saveLog("安检等级","人员配置", "api/matter/levelPerson/add");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ObjectRestResponse getEntity(@PathVariable String id) {
        logBiz.saveLog("安检等级","人员配置详情", "api/matter/levelPerson/get");
        return ObjectRestResponse.ok(baseBiz.selectById(id));
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ObjectRestResponse updateEntity(@PathVariable String id,@RequestBody SecurityLevelPerson o) {
        baseBiz.updateSelectiveById(o);
        logBiz.saveLog("安检等级","人员配置更新", "api/matter/levelPerson/update");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse delEntity(@PathVariable String id) {
        baseBiz.deleteById(id);
        logBiz.saveLog("安检等级","人员配置删除", "api/matter/levelPerson/delete");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/getlist", method = RequestMethod.GET)
    public ObjectRestResponse getlist(@RequestParam(defaultValue = "") String level) {
        SecurityLevel SecurityLevel=securityLevelBiz.selectById(level);

        Example exa = new Example(SecurityLevelPerson.class);
        Example.Criteria crit = exa.createCriteria();
        crit.andEqualTo("levelId", level);
        List<SecurityLevelPerson> list = baseBiz.selectByExample(exa);

        List<Map<String, String>> res=new ArrayList<>();
        for (SecurityLevelPerson o: list){
            Map<String, String> m=new HashMap<>();
            m.put("id", o.getId());
            m.put("levelName", SecurityLevel.getName());
            m.put("levelId", o.getLevelId());
            m.put("gateSum", o.getGateSum().toString());
            m.put("highPersonCount", o.getHighPersonCount().toString());
            m.put("commonPersonCount", o.getCommonPersonCount().toString());
            m.put("lowerPersonCount", o.getLowerPersonCount().toString());
            res.add(m);
        }
        logBiz.saveLog("安检等级","人员配置查询", "api/matter/levelPerson/getlist");
        return  ObjectRestResponse.ok(res);
    }

}
