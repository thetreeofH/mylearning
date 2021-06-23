package com.ts.spm.bizs.rest.equ;

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
import com.ts.spm.bizs.biz.equ.NetDeviceBiz;
import com.ts.spm.bizs.entity.equ.NetDevice;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import com.ts.spm.bizs.rest.admin.UserController;
import com.ts.spm.bizs.util.PoiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by zhoukun on 2020/4/17.
 */
@RestController
@RequestMapping("netdev")
@CheckClientToken
@CheckUserToken
public class NetDeviceController extends BaseController<NetDeviceBiz, NetDevice, String> {
    @Autowired
    CheckPointController checkPointCtr;
    @Autowired
    UserController userCtr;
    @Autowired
    LogBiz logBiz;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ObjectRestResponse addEntity(@RequestBody NetDevice o) {
        o.setId(UUIDUtils.generateUuid());
//        o.setTypeName("安检物联机");
//        o.setType("SDT_8");
        o.setCrtTime(new Date());
        o.setCrtUserId(BaseContextHandler.getUserID());
        o.setCrtUserName(BaseContextHandler.getUsername());
        baseBiz.insertSelective(o);
        logBiz.saveLog("设备管理","安检点工作站添加", "api/equ/netdev/add");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ObjectRestResponse getEntity(@PathVariable String id) {
        logBiz.saveLog("设备管理","安检点工作站详情", "api/equ/netdev/get");
        return ObjectRestResponse.ok(baseBiz.selectById(id));
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ObjectRestResponse updateEntity(@PathVariable String id, @RequestBody NetDevice o) {
        o.setUpdTime(new Date());
        o.setUpdUserId(BaseContextHandler.getUserID());
        o.setUpdUserName(BaseContextHandler.getUsername());
        o.setUpdDeptId(BaseContextHandler.getDepartID());
        baseBiz.updateSelectiveById(o);
        logBiz.saveLog("设备管理","安检点工作站更新", "api/equ/netdev/update");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse delEntity(@PathVariable String id) {
        String[] arr=id.split(",");
        for (String str:arr){
            baseBiz.deleteById(str);
        }
        logBiz.saveLog("设备管理","安检点工作站删除", "api/equ/netdev/get");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/getpage", method = RequestMethod.GET)
    public TableResultResponse page(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "") String deptId, @RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "") String type,
                                    @RequestParam(defaultValue = "") String ip, @RequestParam(defaultValue = "0") int port, @RequestParam(defaultValue = "-1") int actModel) {
        Example exa = new Example(NetDevice.class);
        Example.Criteria crit = exa.createCriteria();
        if (!name.equals(""))
            crit.andLike("deviceName",  "%"+name+"%");
        if (!type.equals(""))
            crit.andEqualTo("type",  type);
        if (!ip.equals(""))
            crit.andEqualTo("ip", ip);
        if (port!=0)
            crit.andEqualTo("port", port);
        if (actModel!=-1)
            crit.andEqualTo("actMode", actModel);

        List<Map<String, Object>> points=checkPointCtr.getpoint(deptId,"");
        if(CollectionUtils.isEmpty(points))
            return new TableResultResponse<>(0, points);

        List<String> pointIds=points.stream().map(u->u.get("id").toString()).collect(Collectors.toList());
        crit.andIn("pointId", pointIds);

        Page<NetDevice> result = PageHelper.startPage(page, limit);
        List<NetDevice> list = baseBiz.selectByExample(exa);
        logBiz.saveLog("设备管理","安检点工作站分页", "api/equ/netdev/getpage");
        return new TableResultResponse<>(result.getTotal(), list);
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export2(HttpServletResponse res, @RequestParam(defaultValue = "") String deptId, @RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "") String type,
                       @RequestParam(defaultValue = "") String ip, @RequestParam(defaultValue = "0") int port, @RequestParam(defaultValue = "-1") int actModel) throws IOException {
        Example exa = new Example(NetDevice.class);
        Example.Criteria crit = exa.createCriteria();
        if (!name.equals(""))
            crit.andLike("deviceName",  "%"+name+"%");
        if (!type.equals(""))
            crit.andEqualTo("type",  type);
        if (!ip.equals(""))
            crit.andEqualTo("ip", ip);
        if (port!=0)
            crit.andEqualTo("port", port);
        if (actModel!=-1)
            crit.andEqualTo("actMode", actModel);

        List<Map<String, Object>> points=checkPointCtr.getpoint(deptId,"");
        if(CollectionUtils.isEmpty(points)) return;

        List<String> pointIds=points.stream().map(u->u.get("id").toString()).collect(Collectors.toList());
        crit.andIn("pointId", pointIds);
        List<NetDevice> data = baseBiz.selectByExample(exa);

        String colHeads[] = { "序号", "车站", "安检点", "IP", "端口", "设备名称", "设备类型" };
        String keys[] = { "index", "station", "pointName", "ip", "port", "deviceName", "typeName" };

        List<Map> list=new ArrayList(data.size());
        for (int i = 0; i < data.size(); i++) {
            BeanMap bm=BeanMap.create(data.get(i));
            Map hm=new HashMap();
            hm.putAll(bm);
            hm.put("index",(i+1));
            list.add(hm);
        }

        PoiUtil.start_download(request,res,System.currentTimeMillis()+".xls", list, colHeads, keys);
        logBiz.saveLog("设备管理","安检点工作站导出", "api/equ/netdev/export");
    }

    /**
     * 通过用户id获取安检点pointId和ip
     * @param userId
     * @return
     */
    @RequestMapping(value = "/authDevice/query/{userId}",method = RequestMethod.GET)
    public TableResultResponse query(@PathVariable String userId){
        List<String>  pointIds =  userCtr.getPoint(userId);
        if(CollectionUtils.isEmpty(pointIds)){
            return new TableResultResponse<>(0,pointIds);
        }
        Example exa = new Example(NetDevice.class);
        Example.Criteria crit = exa.createCriteria();
        crit.andIn("pointId",pointIds);
        List<NetDevice> list = baseBiz.selectByExample(exa);
        logBiz.saveLog("设备管理","安检点工作站查询pointId和ip", "api/equ/netdev/export");
        return new TableResultResponse<>(list.size(),list);
    }

}
