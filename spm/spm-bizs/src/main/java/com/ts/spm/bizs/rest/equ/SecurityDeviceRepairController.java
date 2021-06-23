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
import com.ts.spm.bizs.biz.admin.DepartBiz;
import com.ts.spm.bizs.biz.equ.SecurityDeviceRepairBiz;
import com.ts.spm.bizs.biz.equ.SecurityDeviceSubBiz;
import com.ts.spm.bizs.entity.equ.SecurityDeviceRepair;
import com.ts.spm.bizs.entity.equ.SecurityDeviceSub;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import com.ts.spm.bizs.rest.admin.UserController;
import com.ts.spm.bizs.rest.dict.DictTypeController;
import com.ts.spm.bizs.rpc.LogRest;
import com.ts.spm.bizs.util.PoiUtil;
import com.ts.spm.bizs.vo.equ.RepairChartVo;
import com.ts.spm.bizs.vo.equ.RepairVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.format.annotation.DateTimeFormat;
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
@RequestMapping("securitydevrepair")
@CheckClientToken
@CheckUserToken
public class SecurityDeviceRepairController extends BaseController<SecurityDeviceRepairBiz, SecurityDeviceRepair, String> {
    @Autowired
    CheckPointController checkPointCtr;
    @Autowired
    DepartBiz departBiz;
    @Autowired
    DictTypeController dictTypeCtr;
    @Autowired
    UserController userCtr;
    @Autowired
    SecurityDeviceSubBiz deviceBiz;
    @Autowired
    LogBiz logBiz;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ObjectRestResponse addEntity(@RequestBody SecurityDeviceRepair o) {
        SecurityDeviceSub dev=deviceBiz.selectById(o.getDeviceId());
        o.setDeviceCode(dev.getDevId());
//        o.setDeviceTypeId(dev.getDevTypeId());
        o.setDeviceType(dev.getDevType());
//        o.setDeviceModelId(dev.getDeviceModelId());
        o.setDeviceModel(dev.getDeviceModel());
        o.setPointId(dev.getPointId());

        o.setId(UUIDUtils.generateUuid());
        baseBiz.insertSelective(o);
        logBiz.saveLog("设备管理","设备报修", "api/equ/securitydevrepair/add");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ObjectRestResponse getEntity(@PathVariable String id) {
//        SecurityDevice dev=baseBiz.selectById(id);
//        dev.getPointId();
//        List<Map<String, String>> points=pointFeign.getpoint(deptId);
        logBiz.saveLog("设备管理","设备报修详情", "api/equ/securitydevrepair/get");
        return ObjectRestResponse.ok(baseBiz.selectById(id));
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ObjectRestResponse updateEntity(@PathVariable String id, @RequestBody SecurityDeviceRepair o) {
        o.setUpdTime(new Date());
        o.setUpdUserId(BaseContextHandler.getUserID());
        o.setUpdUserName(BaseContextHandler.getUsername());
        o.setUpdDeptId(BaseContextHandler.getDepartID());
        baseBiz.updateSelectiveById(o);
        logBiz.saveLog("设备管理","设备报修修改", "api/equ/securitydevrepair/update");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse delEntity(@PathVariable String id) {
        String[] arr=id.split(",");
        for (String str:arr){
            baseBiz.deleteById(str);
        }
        logBiz.saveLog("设备管理","设备报修删除", "api/equ/securitydevrepair/delete");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/getpage", method = RequestMethod.GET)
    public TableResultResponse page(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "") String typeId, @RequestParam(defaultValue = "") String modelId, @RequestParam(defaultValue = "") String deptId,
                                    @RequestParam(defaultValue = "") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date start, @RequestParam(defaultValue = "") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date end) {
        Example exa = new Example(SecurityDeviceRepair.class);
        Example.Criteria crit = exa.createCriteria();
        if (!typeId.equals(""))
            crit.andEqualTo("deviceTypeId",  typeId);
        if (!modelId.equals(""))
            crit.andEqualTo("deviceModelId", modelId);
        if (start!=null && end!=null)
            crit.andBetween("repairTime", start, end);

        List<Map<String, Object>> points=checkPointCtr.getpoint(deptId,"");
        if(CollectionUtils.isEmpty(points))
            return new TableResultResponse<>(0, points);

        List<String> pointIds=points.stream().map(u->u.get("id").toString()).collect(Collectors.toList());
        crit.andIn("pointId", pointIds);

        Page result = PageHelper.startPage(page, limit);
        List<SecurityDeviceRepair> list = baseBiz.selectByExample(exa);
        logBiz.saveLog("设备管理","设备报修分页查询", "api/equ/securitydevrepair/getpage");
        return new TableResultResponse<>(result.getTotal(), list);
    }

    @RequestMapping(value = "/export")
    public void export(HttpServletResponse res,
                        @RequestParam(defaultValue = "") String typeId, @RequestParam(defaultValue = "") String modelId, @RequestParam(defaultValue = "") String deptId,
                        @RequestParam(defaultValue = "") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date start, @RequestParam(defaultValue = "") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date end) throws IOException {
        Example exa = new Example(SecurityDeviceRepair.class);
        Example.Criteria crit = exa.createCriteria();
        if (!typeId.equals(""))
            crit.andEqualTo("deviceTypeId",  typeId);
        if (!modelId.equals(""))
            crit.andEqualTo("deviceModelId", modelId);
        if (start!=null && end!=null)
            crit.andBetween("repairTime", start, end);

        List<Map<String, Object>> points=checkPointCtr.getpoint(deptId,"");
        if(CollectionUtils.isEmpty(points))
            return;

        List<String> pointIds=points.stream().map(u->u.get("id").toString()).collect(Collectors.toList());
        crit.andIn("pointId", pointIds);
        List<SecurityDeviceRepair> data = baseBiz.selectByExample(exa);

        String colHeads[] = { "序号", "车站", "设备类型", "设备型号", "设备编码", "报修人", "报修原因", "报修时间", "修复时间", "修复方式" };
        String keys[] = { "index", "station", "deviceType", "deviceModel", "deviceId", "callPerson", "repairReason", "repairTime", "mendingTime", "mendingType" };

        List<Map> list=new ArrayList(data.size());
        for (int i = 0; i < data.size(); i++) {
            BeanMap bm=BeanMap.create(data.get(i));
            Map hm=new HashMap();
            hm.putAll(bm);
            hm.put("index",(i+1));
            list.add(hm);
        }

        PoiUtil.start_download(request,res,System.currentTimeMillis()+".xls", list, colHeads, keys);
        logBiz.saveLog("设备管理","设备报修列表导出", "api/equ/securitydevrepair/export");
    }

    @RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
    public ObjectRestResponse getEntityByPoint(@PathVariable String id) {
        Example exa = new Example(SecurityDeviceSub.class);
        Example.Criteria crit = exa.createCriteria();
        crit.andEqualTo("pointId",  id);
        List<SecurityDeviceSub> list=deviceBiz.selectByExample(exa);

        List res=new ArrayList();
        for (SecurityDeviceSub dev:list){
            Map map=new HashMap();
            map.put("id",dev.getDevId());
            map.put("deviceType",dev.getDevType());
            res.add(map);
        }
        logBiz.saveLog("设备管理","查询安检点设备报修列表", "api/equ/securitydevrepair/list");
        return ObjectRestResponse.ok(list);
    }

    @RequestMapping(value = "/statis", method = RequestMethod.GET)
    public ObjectRestResponse statis(@RequestParam(defaultValue = "") String deptId,
                                    @RequestParam(defaultValue = "") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date start1,
                                    @RequestParam(defaultValue = "") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date end1,
                                    @RequestParam(defaultValue = "") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date start2,
                                    @RequestParam(defaultValue = "") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date end2) {
        if(!"".equals(deptId)){
            String str=departBiz.getDepartNameById(deptId);
            String[] arr=str.split("-");
            List<Map<String, Object>> points;
            if("1".equals(BaseContextHandler.getIsSuperAdmin()))
                points=checkPointCtr.getpoint(deptId, "");
            else
                points=checkPointCtr.getpoint(deptId, BaseContextHandler.getUserID());
            List<String> pointIds=points.stream().map(u->u.get("id").toString()).collect(Collectors.toList());
            List<RepairVo> list1=baseBiz.selectGroup(start1, end1, "", pointIds);
            check(list1);
            List<RepairVo> list2=baseBiz.selectGroup(start2, end2, "", pointIds);
            check(list2);
            for (RepairVo vo1:list1){
                vo1.setLine(arr[0]);
                for (RepairVo vo2:list1){
                    if(vo1.getDeviceType().equals(vo2.getDeviceType())){
                        vo1.setCnt2(vo2.getCnt1());
                        break;
                    }
                }
            }
            return ObjectRestResponse.ok(list1);
        }

        List<Map<String, String>> departs;//id,type,name
        if("1".equals(BaseContextHandler.getIsSuperAdmin())) {
            departs=userCtr.dataDepartMap("");
        }
        else {
            departs=userCtr.dataDepartMap(BaseContextHandler.getUserID());
        }
        List<Map<String, String>> lines=departs.stream().filter(o->o.get("type").equals("1")).collect(Collectors.toList());
        List<RepairVo> list=new ArrayList<>();
        for(Map<String, String> line:lines){
            System.out.println(line.get("id")+line.get("name")+line.get("type"));

            List<Map<String, Object>> points;
            if("1".equals(BaseContextHandler.getIsSuperAdmin())){
                points=checkPointCtr.getpoint(line.get("id"), "");
                System.out.println("point="+points);
            }
            else{
                points=checkPointCtr.getpoint(line.get("id"), BaseContextHandler.getUserID());
            }

            List<String> pointIds=points.stream().map(u->u.get("id").toString()).collect(Collectors.toList());

            List<RepairVo> list1;
            List<RepairVo> list2;
            if(pointIds.size()==0){
                list1=new ArrayList<>();
                list2=new ArrayList<>();
            }else{
                list1=baseBiz.selectGroup(start1, end1, "", pointIds);
                list2=baseBiz.selectGroup(start2, end2, "", pointIds);
            }
            check(list1);
            check(list2);
            for (RepairVo vo1:list1){
                vo1.setLine(line.get("name"));
                for (RepairVo vo2:list1){
                    if(vo1.getDeviceType().equals(vo2.getDeviceType())){
                        vo1.setCnt2(vo2.getCnt1());
                        break;
                    }
                }
            }
            list.addAll(list1);
        }

        logBiz.saveLog("设备管理","设备报修统计列表", "api/equ/securitydevrepair/statis");
        return ObjectRestResponse.ok(list);
    }

    //设备类型
//    String[] deviceType= {"X光机","手持液检","炸检","台式液检","金属探测门","手持金属探测仪","其它"};
    private void check(List<RepairVo> list) {
        List<Map<String, Object>> types=dictTypeCtr.getDictTypeByCode("SDT");
        List<String> deviceType=types.stream().map(x->x.get("name").toString()).collect(Collectors.toList());
        for (String type:deviceType){
            boolean flag=false;
            for (RepairVo o:list){
                if(type.equals(o.getDeviceType()))
                    flag=true;
                    break;
            }
            if(!flag){
                RepairVo o=new RepairVo();
                o.setDeviceType(type);
                o.setCnt1(0);
                o.setCnt2(0);
                list.add(o);
            }
        }
    }


    @RequestMapping(value = "/statisChart", method = RequestMethod.GET)
    public ObjectRestResponse statisChart(
            @RequestParam(defaultValue = "") String deviceType, @RequestParam(defaultValue = "") String deptId,
            @RequestParam(defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date start1,
            @RequestParam(defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date end1,
            @RequestParam(defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date start2,
            @RequestParam(defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date end2) {
        List<RepairChartVo> list=new ArrayList<>();

        if(!"".equals(deptId)){
            String str=departBiz.getDepartNameById(deptId);
            String[] arr=str.split("-");
            RepairChartVo rvo=new RepairChartVo();
            rvo.setLine(arr[0]);

            List<Map<String, Object>> points;
            if("1".equals(BaseContextHandler.getIsSuperAdmin()))
                points=checkPointCtr.getpoint(deptId, "");
            else
                points=checkPointCtr.getpoint(deptId, BaseContextHandler.getUserID());
            List<String> pointIds=points.stream().map(u->u.get("id").toString()).collect(Collectors.toList());

            List<RepairVo> list1;
            List<RepairVo> list2;
            if(pointIds.size()==0){
                list1=new ArrayList<>();
                list2=new ArrayList<>();
            }else{
                list1=baseBiz.selectGroup(start1, end1, deviceType, pointIds);
                list2=baseBiz.selectGroup(start2, end2, deviceType, pointIds);
            }

            if(list1.size()==0){
                RepairVo x=new RepairVo();
//                x.setLine(line.get("name"));
                x.setDeviceType(deviceType);
                x.setCnt1(0);
                list1.add(x);
            }
            if(list2.size()==0){
                RepairVo x=new RepairVo();
//                x.setLine(line.get("name"));
                x.setDeviceType(deviceType);
                x.setCnt1(0);
                list2.add(x);
            }

//            List<RepairVo> list1=baseBiz.selectGroup(start1, end1, "", pointIds);
//            check(list1);
//            List<RepairVo> list2=baseBiz.selectGroup(start2, end2, "", pointIds);
//            check(list2);

            for (RepairVo vo1:list1){
//                vo1.setLine(arr[0]);
                for (RepairVo vo2:list1){
                    if(vo1.getDeviceType().equals(vo2.getDeviceType())){
                        vo1.setCnt2(vo2.getCnt1());
                        break;
                    }
                }
            }
            rvo.setData(list1.get(0));
            list.add(rvo);
            return ObjectRestResponse.ok(list);
        }

        List<Map<String, String>> departs;//id,type,name
        if("1".equals(BaseContextHandler.getIsSuperAdmin())) {
            departs=userCtr.dataDepartMap("");
        }
        else {
            departs=userCtr.dataDepartMap(BaseContextHandler.getUserID());
        }
        List<Map<String, String>> lines=departs.stream().filter(o->o.get("type").equals("1")).collect(Collectors.toList());

        for(Map<String, String> line:lines){
            System.out.println(line.get("id")+line.get("name")+line.get("type"));
            RepairChartVo rvo=new RepairChartVo();
            rvo.setLine(line.get("name"));

            List<Map<String, Object>> points;
            if("1".equals(BaseContextHandler.getIsSuperAdmin())){
                points=checkPointCtr.getpoint(line.get("id"), "");
            }
            else{
                points=checkPointCtr.getpoint(line.get("id"), BaseContextHandler.getUserID());
            }

            List<String> pointIds=points.stream().map(u->u.get("id").toString()).collect(Collectors.toList());

            List<RepairVo> list1;
            List<RepairVo> list2;
            if(pointIds.size()==0){
                list1=new ArrayList<>();
                list2=new ArrayList<>();
            }else{
                list1=baseBiz.selectGroup(start1, end1, deviceType, pointIds);
                list2=baseBiz.selectGroup(start2, end2, deviceType, pointIds);
            }

            if(list1.size()==0){
                RepairVo x=new RepairVo();
//                x.setLine(line.get("name"));
                x.setDeviceType(deviceType);
                x.setCnt1(0);
                list1.add(x);
            }
            if(list2.size()==0){
                RepairVo x=new RepairVo();
//                x.setLine(line.get("name"));
                x.setDeviceType(deviceType);
                x.setCnt1(0);
                list2.add(x);
            }
//            check(list1);
//            check(list2);
            for (RepairVo vo1:list1){
//                vo1.setLine(line.get("name"));
                for (RepairVo vo2:list1){
                    if(vo1.getDeviceType().equals(vo2.getDeviceType())){
                        vo1.setCnt2(vo2.getCnt1());
                        break;
                    }
                }
            }
            rvo.setData(list1.get(0));
            list.add(rvo);
        }

        logBiz.saveLog("设备管理","设备报修统计图形", "api/equ/securitydevrepair/statisChart");
        return ObjectRestResponse.ok(list);
    }

}
