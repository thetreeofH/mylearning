package com.ts.spm.bizs.rest.matter;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.LogBiz;
import com.ts.spm.bizs.biz.matter.SecurityLevelBiz;
import com.ts.spm.bizs.biz.matter.SecurityLevelPersonBiz;
import com.ts.spm.bizs.biz.matter.SecurityLevelPointBiz;
import com.ts.spm.bizs.biz.matter.SecurityLevelTimeBiz;
import com.ts.spm.bizs.entity.matter.SecurityLevelPoint;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by zhoukun on 2020/4/17.
 */
@RestController
@RequestMapping("levelPoint")
@CheckClientToken
@CheckUserToken
public class SecurityLevelPointController extends BaseController<SecurityLevelPointBiz, SecurityLevelPoint, String> {
    @Autowired
    CheckPointController checkPointCtr;
    @Autowired
    SecurityLevelBiz securityLevelBiz;
    @Autowired
    SecurityLevelTimeBiz securityLevelTimeBiz;
    @Autowired
    SecurityLevelPersonBiz securityLevelPersonBiz;
    @Autowired
    LogBiz logBiz;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ObjectRestResponse addEntity(@RequestBody SecurityLevelPoint o){
        List<String> pointIds=o.getPointIds();
        if(pointIds==null)
            return ObjectRestResponse.ok();

        for (String pointId:pointIds){
            o.setId(UUIDUtils.generateUuid());
            o.setPointId(pointId);
            o.setCrtTime(new Date());
            o.setCrtUserId(BaseContextHandler.getUserID());
            o.setCrtUserName(BaseContextHandler.getUsername());
            baseBiz.insertSelective(o);
        }
        logBiz.saveLog("安检等级","安检点配置", "api/matter/levelPoint/add");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ObjectRestResponse getEntity(@PathVariable String id) {
        logBiz.saveLog("安检等级","安检点配置详情", "api/matter/levelPoint/get");
        return ObjectRestResponse.ok(baseBiz.selectById(id));
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ObjectRestResponse updateEntity(@PathVariable String id,@RequestBody SecurityLevelPoint o) {
        o.setUpdTime(new Date());
        o.setUpdUserId(BaseContextHandler.getUserID());
        o.setUpdUserName(BaseContextHandler.getUsername());
        baseBiz.updateSelectiveById(o);
        logBiz.saveLog("安检等级","安检点配置更新", "api/matter/levelPoint/update");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse delEntity(@PathVariable String id) {
        baseBiz.deleteById(id);
        logBiz.saveLog("安检等级","安检点配置删除", "api/matter/levelPoint/delete");
        return ObjectRestResponse.ok();
    }


    @RequestMapping(value = "/getlist", method = RequestMethod.GET)
    public ObjectRestResponse getlist(@RequestParam(defaultValue = "") String deptId, @RequestParam(defaultValue = "") String level) {
//        List<Map<String, String>> list2=adminFeign.getStation(BaseContextHandler.getUserID());//[id,name]
        List<Map<String, Object>> list2=checkPointCtr.getpoint(deptId,BaseContextHandler.getUserID());//[id,name]
        if(list2.isEmpty())
            return  ObjectRestResponse.ok(list2);

        List<String> pointIds=list2.stream().map(m->m.get("id").toString()).collect(Collectors.toList());

        List<SecurityLevelPoint> list = baseBiz.selectListByLevel(level, pointIds);
        logBiz.saveLog("安检等级","安检点配置查询", "api/matter/levelPoint/getlist");
        return ObjectRestResponse.ok(list);
    }

    private String getStationName(String id, List<Map<String, String>> list2) {
        for (Map<String, String> map:list2){
            if(id.equals(map.get("id")))
                return map.get("name");
        }
        return null;
    }
}
