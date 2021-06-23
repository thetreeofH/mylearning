package com.ts.spm.bizs.rest.equ;

import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.constant.RestCodeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ts.spm.bizs.biz.LogBiz;
import com.ts.spm.bizs.biz.equ.SecurityDeviceSubBiz;
import com.ts.spm.bizs.entity.equ.SecurityDeviceSub;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import com.ts.spm.bizs.rest.admin.UserController;
import com.ts.spm.bizs.rest.dict.DictTypeController;
import com.ts.spm.bizs.util.PoiUtil;
import com.ts.spm.bizs.vo.equ.SecurityDeviceVo;
import com.ts.spm.bizs.vo.equ.StationStatVo;
import com.ts.spm.bizs.vo.equ.StationStatVo2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
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
@RequestMapping("device")
@CheckClientToken
@CheckUserToken
public class SecurityDeviceSubController extends BaseController<SecurityDeviceSubBiz, SecurityDeviceSub, String> {
    @Autowired
    LogBiz logBiz;
    @Autowired
    CheckPointController checkPointCtr;
    @Autowired
    UserController userCtr;
    @Autowired
    DictTypeController dictTypeCtr;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ObjectRestResponse addEntity(@RequestBody SecurityDeviceSub o) {
        o.setDevId("zx."+baseBiz.getId());
        if(o.getDeviceSource()==0)
            return ObjectRestResponse.error(RestCodeConstants.EX_BUSINESS_BASE_CODE,"禁止添加联网设备");
        baseBiz.insertSelective(o);
        logBiz.saveLog("设备管理","添加安检设备", "api/equ/device/add");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ObjectRestResponse getEntity(@RequestParam("id") String id) {
        logBiz.saveLog("设备管理","安检设备详情", "api/equ/device/get");
        return ObjectRestResponse.ok(baseBiz.getById(id));
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ObjectRestResponse updateEntity(@RequestBody SecurityDeviceSub o) {
        baseBiz.updateSelectiveById(o);
        logBiz.saveLog("设备管理","安检设备更新", "api/equ/device/update");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ObjectRestResponse delEntity(@RequestParam("id") String id) {
        String[] arr=id.split(",");
        for (String str:arr){
            baseBiz.deleteById(str);
        }
        logBiz.saveLog("设备管理","安检设备删除", "api/equ/device/delete");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/getpage", method = RequestMethod.GET)
    public TableResultResponse page(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "") String type, @RequestParam(defaultValue = "") String model, @RequestParam(defaultValue = "") String deptId,
                                    @RequestParam(defaultValue = "") String start, @RequestParam(defaultValue = "") String end) {
        List<Map<String, Object>> points=checkPointCtr.getpoint(deptId,"");
        if(CollectionUtils.isEmpty(points))
            return new TableResultResponse<>(0, points);

        List<String> pointIds=points.stream().map(u->u.get("id").toString()).collect(Collectors.toList());
        if(pointIds.isEmpty())
            return new TableResultResponse<>(pointIds.size(), pointIds);

        Page result = PageHelper.startPage(page, limit);
        List<SecurityDeviceSub> list = baseBiz.selectList(type,pointIds);

        logBiz.saveLog("设备管理","安检设备分页查询", "api/equ/device/getpage");
        return new TableResultResponse<>(result.getTotal(), list);
    }

    @RequestMapping(value = "/getdev", method = RequestMethod.GET)
    public ObjectRestResponse getdev(@RequestParam(defaultValue = "") String pointId) {
        Example exa = new Example(SecurityDeviceSub.class);
        Example.Criteria crit = exa.createCriteria();
        crit.andEqualTo("pointId",  pointId);
        logBiz.saveLog("设备管理","查询安检点的安检设备", "api/equ/device/getdev");
        return ObjectRestResponse.ok(baseBiz.selectByExample(exa));
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(HttpServletResponse res, @RequestParam(defaultValue = "") String type, @RequestParam(defaultValue = "") String model, @RequestParam(defaultValue = "") String deptId,
                       @RequestParam(defaultValue = "") String start, @RequestParam(defaultValue = "") String end) throws IOException {
        List<Map<String, Object>> points=checkPointCtr.getpoint(deptId,"");
        if(CollectionUtils.isEmpty(points))
            return;

        List<String> pointIds=points.stream().map(u->u.get("id").toString()).collect(Collectors.toList());
        if(pointIds.isEmpty())
            return;

        List<SecurityDeviceSub> data = baseBiz.selectList(type,pointIds);

        String colHeads[] = { "序号", "车站", "安检点", "设备编码", "设备名称", "设备类型", "IP", "端口", "联网类型", "用户名", "通道号", "录像类型", "是否在线", "状态" };
        String keys[] = { "index", "station", "point", "devId", "devName", "devType", "devIp", "devPort", "deviceSource", "devUser", "devChl", "recType", "isOnline", "faultType" };

        List<Map> list=new ArrayList(data.size());
        for (int i = 0; i < data.size(); i++) {
            BeanMap bm=BeanMap.create(data.get(i));
            Map hm=new HashMap();
            hm.putAll(bm);
            hm.put("index",(i+1));
            list.add(hm);
        }
        PoiUtil.start_download(request,res,System.currentTimeMillis()+".xls", list, colHeads, keys);

        logBiz.saveLog("设备管理","安检设备列表导出", "api/equ/device/export");
    }

    //统计
    @RequestMapping(value = "/chart", method = RequestMethod.GET)
    public ObjectRestResponse getStat(@RequestParam(defaultValue = "") String deptId, @RequestParam(defaultValue = "") String typeId) {
        List<Map<String,Object>> types=dictTypeCtr.getDictTypeByCode("SDT");
        if(!typeId.isEmpty()){
            types=types.stream().filter(o->typeId.equals(o.get("code"))).collect(Collectors.toList());
        }
        List<String> names=types.stream().map(o->o.get("name").toString()).collect(Collectors.toList());
        Collections.sort(names);
        String category= StringUtils.collectionToCommaDelimitedString(names);

//        List<Map<String, String>> departs;
//        if(deptId.isEmpty())
//            departs=adminFeign.getStation(BaseContextHandler.getUserID());
//        else
//            departs=adminFeign.stations(deptId);

        List<Map<String, String>> departs=checkPointCtr.stations(deptId);

        List<Map<String, String>> stations=departs.stream().filter(m->m.get("type").equals("3")).collect(Collectors.toList());
        System.out.println("stations="+stations);

        List<Object> res=new ArrayList<>();
        for (Map<String, String> station:stations){
            StationStatVo2 vo=new StationStatVo2();
            vo.setTitle(station.get("name"));
            vo.setCategory(category);
//            List<Map<String, String>> points2=adminFeign.getpoint(station.get("id"),"");
            List<Map<String, Object>> points2=checkPointCtr.getpoint(station.get("id"),"");
            if(points2.size()==0){
                for(Map<String,Object> type:types){
                    SecurityDeviceVo sdv=new SecurityDeviceVo();
                    sdv.setName(type.get("name").toString());
                    sdv.setValue(0);
                    vo.getList().add(sdv);
                }
            }
            if(points2.size()>0){
                List<String> pointIds=points2.stream().map(m->m.get("id").toString()).collect(Collectors.toList());
                List<SecurityDeviceVo> devs = baseBiz.selectCountByPoint(typeId, pointIds);//type,value
//                for (SecurityDeviceVo dev: devs){
//                    vo.getList().add(dev);
//                }
                for(Map<String,Object> type:types){
                    String tc=type.get("code").toString();//150
                    String t=type.get("name").toString();//x光机

                    SecurityDeviceVo sdv=new SecurityDeviceVo();
                    sdv.setName(t);
                    sdv.setValue(0);
                    for (SecurityDeviceVo dev: devs){
                        if(tc.equals(dev.getName())){
                            sdv.setValue(dev.getValue());
                        }
                    }
                    vo.getList().add(sdv);

//                    boolean flag=false;
//                    for (SecurityDeviceVo dev: devs){
//                        if(tc.equals(dev.getName())){//
//                            flag=true;
//                            break;
//                        }
//                    }
//                    if(!flag){
//                        SecurityDeviceVo sdv=new SecurityDeviceVo();
//                        sdv.setName(t);
//                        sdv.setValue(0);
//                        vo.getList().add(sdv);
//                    }
                }
            }
            vo.getList().sort(Comparator.comparing(o->o.getName()));
            res.add(vo);
        }
        logBiz.saveLog("设备管理","安检设备图形统计", "api/equ/device/chart");
        return ObjectRestResponse.ok(res);
    }

    @RequestMapping(value = "/status2", method = RequestMethod.GET)
    public ObjectRestResponse getStatus2(@RequestParam(defaultValue = "") String deptId, @RequestParam(defaultValue = "0") int typeId,
                                        @RequestParam(defaultValue = "3") int status, @RequestParam(defaultValue = "1") int group) {
        List<Map<String, Object>> points=checkPointCtr.getpoint(deptId,"");

//        List<String> pointIds= adminFeign.getPoint(BaseContextHandler.getUserID());
        List<String> pointIds2=new ArrayList<>();
        for (Map<String, Object> map:points){
            String id=map.get("id").toString();
            pointIds2.add(id);
//            for (String uid:pointIds){
//                if(id.equals(uid)){
//                    pointIds2.add(id);
//                }
//            }
        }

        if (pointIds2.size()==0)
            return ObjectRestResponse.ok(pointIds2);

        Example exa = new Example(SecurityDeviceSub.class);
        Example.Criteria crit = exa.createCriteria();
        if (typeId!=0)
            crit.andEqualTo("devType",  typeId);
        if (status!=3)
            crit.andEqualTo("faultType", status);
        if (pointIds2.size()==0)
            crit.andIn("pointId", pointIds2);
        List<SecurityDeviceSub> devs = baseBiz.selectByExample(exa);

        List<StationStatVo> res=new ArrayList<>();

        List<Map<String, String>> stations=checkPointCtr.stations(deptId);
        List<Map<String, String>> stations2=stations.stream().filter(m->m.get("type").equals("3")).collect(Collectors.toList());
        System.out.println("stations="+stations2);

        for (Map<String, String> station:stations2){
            StationStatVo vo=new StationStatVo();
            List<Map<String, Object>> points2=checkPointCtr.getpoint(station.get("id"),"");
            for (Map<String, Object> p:points2){
                vo.setTitle(station.get("name")+"--"+p.get("name"));
                for (SecurityDeviceSub device:devs){
                    if(p.get("id").equals(device.getPointId())){
                        System.out.println("add....."+p.get("id")+"==="+device.getPointId()+"---"+device.getDevId());
                        vo.getList().add(device);
                        res.add(vo);
                    }
                }
            }
        }
        logBiz.saveLog("设备管理","安检设备状态统计", "api/equ/device/status2");
        return ObjectRestResponse.ok(res);
    }


    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public ObjectRestResponse getStatus(@RequestParam(defaultValue = "") String deptId, @RequestParam(defaultValue = "") String typeId,
                                        @RequestParam(defaultValue = "-1") int status, @RequestParam(defaultValue = "0") int group) {
        List<StationStatVo> res=new ArrayList<>();

        if (deptId.isEmpty()){
            System.out.println("deptId1="+deptId);
            List<Map<String, String>> stations=userCtr.getStation(BaseContextHandler.getUserID());
            for (Map<String, String> station : stations){
                List<Map<String, Object>> points=checkPointCtr.getpoint(station.get("id"),"");
                System.out.println("points="+points);
                if(points.size()>0){
                    if(group==0){
                        StationStatVo vo=new StationStatVo();
                        vo.setTitle(station.get("name"));
                        List<SecurityDeviceSub> devs=getDeviceSub(typeId, status, points);
                        for (SecurityDeviceSub dev: devs)
                            vo.getList().add(dev);
                        res.add(vo);
                    }else{
                        for (Map<String, Object> point : points){
                            StationStatVo vo=new StationStatVo();
                            vo.setTitle(station.get("name")+"--"+point.get("name"));
                            List<Map<String, Object>> plist=new ArrayList<>();
                            plist.add(point);
                            List<SecurityDeviceSub> devs=getDeviceSub(typeId, status, plist);
                            for (SecurityDeviceSub dev: devs)
                                vo.getList().add(dev);
                            res.add(vo);
                        }
                    }
                }
            }
        }else{
            List<Map<String, String>> depts=checkPointCtr.stations(deptId);
            List<Map<String, String>> stations=depts.stream().filter(m->m.get("type").equals("3")).collect(Collectors.toList());
            for (Map<String, String> station : stations){
                List<Map<String, Object>> points=checkPointCtr.getpoint(station.get("id"),"");
                System.out.println("points="+points);
                if(points.size()>0){
                    if(group==0){
                        StationStatVo vo=new StationStatVo();
                        vo.setTitle(station.get("name"));
                        List<SecurityDeviceSub> devs=getDeviceSub(typeId, status, points);
                        for (SecurityDeviceSub dev: devs)
                            vo.getList().add(dev);
                        res.add(vo);
                    }else{
                        for (Map<String, Object> point : points){
                            StationStatVo vo=new StationStatVo();
                            vo.setTitle(station.get("name")+"--"+point.get("name"));
                            List<Map<String, Object>> plist=new ArrayList<>();
                            plist.add(point);
                            List<SecurityDeviceSub> devs=getDeviceSub(typeId, status, plist);
                            for (SecurityDeviceSub dev: devs)
                                vo.getList().add(dev);
                            res.add(vo);
                        }
                    }
                }
            }
        }

        logBiz.saveLog("设备管理","安检设备状态统计", "api/equ/device/status");
        return ObjectRestResponse.ok(res);
    }

    List<SecurityDeviceSub> getDeviceSub(String typeId, int status, List<Map<String, Object>> points){
        Example exa = new Example(SecurityDeviceSub.class);
        Example.Criteria crit = exa.createCriteria();
        if (!typeId.equals(""))
            crit.andEqualTo("devType",  typeId);
        if (status>0)
            crit.andEqualTo("faultType", status);

        List<String> pointIds=points.stream().map(m->m.get("id").toString()).collect(Collectors.toList());
        System.out.println("pointIds="+pointIds);
        crit.andIn("pointId", pointIds);
        List<SecurityDeviceSub> devs = baseBiz.selectByExample(exa);
        return devs;
    }

    @RequestMapping(value = "/statusCount", method = RequestMethod.GET)
    public ObjectRestResponse getStatusCount(@RequestParam(defaultValue = "") String deptId,
                                             @RequestParam(defaultValue = "") String typeId,
                                             @RequestParam(defaultValue = "3") int status) {
        List<Map<String, Object>> points=checkPointCtr.getpoint(deptId,"");
        System.out.println("points="+points);

//        List<String> pointIds= adminFeign.getPoint(BaseContextHandler.getUserID());
//        System.out.println(pointIds);
        List<String> pointIds2=new ArrayList<>();
        for (Map<String, Object> map:points){
            String id=map.get("id").toString();
            pointIds2.add(id);
//            for (String uid:pointIds){
//                if(id.equals(uid)){
//                    pointIds2.add(id);
//                }
//            }
        }
        System.out.println("pointIds2="+pointIds2);

        List<SecurityDeviceSub> devs = new ArrayList<>();

        if (pointIds2.size()>0) {
            Example exa = new Example(SecurityDeviceSub.class);
            Example.Criteria crit = exa.createCriteria();
            if(!"".equals(typeId))
                crit.andEqualTo("devType", typeId);
            if(status!=3)
                crit.andEqualTo("faultType", status);
            crit.andIn("pointId", pointIds2);
            devs = baseBiz.selectByExample(exa);
        }

        Map<String,String> map=new HashMap<>();
        int devCount=devs.size();
        map.put("devCount",devCount+"");
        long devBadCount=devs.stream().filter(o->o.getFaultType()==1).count();
        long onlineCount=devs.stream().filter(o->o.getIsOnline()==1).count();
        if(devCount==0) {
            map.put("devBadCount", "0");
            map.put("devBadRate", "0");
        }else{
            map.put("devBadCount",devBadCount+"");
            map.put("devOnlineRate", onlineCount*1.0/devCount*1.0+"");
        }

//        map.put("pointCount",pointIds2.size()+"");
        map.put("pointCount",points.size()+"");

        logBiz.saveLog("设备管理","安检设备状态数量统计", "api/equ/device/statusCount");
        return ObjectRestResponse.ok(map);
    }

    @RequestMapping(value = "/statis", method = RequestMethod.GET)
    public TableResultResponse statis(@RequestParam(defaultValue = "") String typeId, @RequestParam(defaultValue = "") String deptId) {
        List<Map<String, String>> stations=checkPointCtr.stations(deptId);
        //点击了车站
        if(stations.size()==1){
//            sql=stationId in(stations) group by pointId
        }
        //点击了线路、站区
        else{
//            sql=stationId in(stations) group by stationId
        }
        return new TableResultResponse<>(0, stations);

//        Example exa = new Example(SecurityDevice.class);
//        Example.Criteria crit = exa.createCriteria();
//        if (!typeId.equals("")) {
//            crit.andEqualTo("typeId",  typeId);
//        }
//
//
//        List<String> pointIds=points.stream().map(u->u.get("id")).collect(Collectors.toList());
//        crit.andIn("pointId", pointIds);
//
//        Page<SecurityDevice> result = PageHelper.startPage(page, limit);
//        List<SecurityDevice> list = baseBiz.selectByExample(exa);
////        List<String> ids=list.stream().map(SecurityDevice::getPointId).collect(Collectors.toList());
//
//        List<Map<String, String>> devs=new ArrayList<>();
//        for (SecurityDevice dev:list){
//            Map<String, String> map=new HashMap<>();
//            map.put("id",dev.getId());
//            map.put("code",dev.getCode());
//            map.put("model",dev.getModel());
//            map.put("type",dev.getType());
//            map.put("pointId",dev.getPointId());
//
//            for (Map<String, String> p:points){
//                if(dev.getPointId().equals(p.get("id"))){
//                    map.put("point", p.get("name"));
//                    map.put("line", p.get("line"));
//                    break;
//                }
//            }
//
//            devs.add(map);
//        }
//
//        return new TableResultResponse<>(result.getTotal(), devs);
    }

}
