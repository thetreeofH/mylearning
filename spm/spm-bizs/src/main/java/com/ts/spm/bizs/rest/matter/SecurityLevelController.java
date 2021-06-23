package com.ts.spm.bizs.rest.matter;

import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.constant.RestCodeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.LogBiz;
import com.ts.spm.bizs.biz.matter.SecurityLevelBiz;
import com.ts.spm.bizs.entity.matter.SecurityLevel;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import com.ts.spm.bizs.util.PoiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by zhoukun on 2020/4/17.
 */
@RestController
@RequestMapping("level")
@CheckClientToken
@CheckUserToken
public class SecurityLevelController extends BaseController<SecurityLevelBiz, SecurityLevel, String> {

    @Autowired
    CheckPointController checkPointCtr;
    @Autowired
    LogBiz logBiz;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ObjectRestResponse addEntity(@RequestBody SecurityLevel o){
        o.setId(UUIDUtils.generateUuid());
        baseBiz.insertSelective(o);
        logBiz.saveLog("安检级别","添加", "api/matter/level/add");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ObjectRestResponse getEntity(@PathVariable String id) {
        logBiz.saveLog("安检级别","添加", "api/matter/level/add");
        return ObjectRestResponse.ok(baseBiz.selectById(id));
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ObjectRestResponse updateEntity(@PathVariable String id,@RequestBody SecurityLevel o) {
//        SecurityLevel level=baseBiz.selectById(id);
//        level.setName(o.getName());
//        level.setMemo(o.getMemo());
//        baseBiz.updateSelectiveById(level);
        baseBiz.updateSelectiveById(o);
        logBiz.saveLog("安检级别","更新", "api/matter/level/update");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse delEntity(@PathVariable String id) {
        String[] ids=id.split(",");
        for (String str:ids)
            baseBiz.deleteById(str);
        logBiz.saveLog("安检级别","删除", "api/matter/level/delete");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/getlist", method = RequestMethod.GET)
    public ObjectRestResponse getlist(@RequestParam(defaultValue = "") String deptId,@RequestParam(defaultValue = "") String name) {
        List<Map<String, String>> list=checkPointCtr.stations(deptId);//[id,name]
        List<String> deptIds=list.stream().map(x->x.get("id")).collect(Collectors.toList());
        logBiz.saveLog("安检级别","查询列表", "api/matter/level/getlist");
        return  ObjectRestResponse.ok(baseBiz.selectListByParam(name,deptIds));
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(HttpServletResponse res, @RequestParam(defaultValue = "") String deptId, @RequestParam(defaultValue = "") String name) throws IOException {
        List<Map<String, String>> stations=checkPointCtr.stations(deptId);//[id,name]
        List<String> deptIds=stations.stream().map(x->x.get("id")).collect(Collectors.toList());
        List<SecurityLevel> data=baseBiz.selectListByParam(name,deptIds);

        String colHeads[] = { "序号", "所属部门", "安检等级", "高峰开始时间", "高峰结束时间", "说明" };
        String keys[] = { "index", "dept", "name", "startTime", "endTime", "memo" };

        List<Map> list=new ArrayList(data.size());
        for (int i = 0; i < data.size(); i++) {
            BeanMap bm=BeanMap.create(data.get(i));
            Map hm=new HashMap();
            hm.putAll(bm);
            hm.put("index",(i+1));
            list.add(hm);
        }
        PoiUtil.start_download(request,res,System.currentTimeMillis()+".xls", list, colHeads, keys);
        logBiz.saveLog("安检级别","导出", "api/matter/level/export");
    }

}
