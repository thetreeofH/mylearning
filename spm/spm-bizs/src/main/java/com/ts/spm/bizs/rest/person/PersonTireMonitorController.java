package com.ts.spm.bizs.rest.person;

import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ts.spm.bizs.biz.LogBiz;
import com.ts.spm.bizs.biz.person.TireMonitorBiz;
import com.ts.spm.bizs.entity.person.TireMonitor;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import com.ts.spm.bizs.rest.admin.DepartController;
import com.ts.spm.bizs.rest.admin.UserController;
import com.ts.spm.bizs.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.util.StringUtil;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author luoyu
 * @Date 2020/7/2 14:01
 * @Version 1.0
 */
@RestController
@RequestMapping("personTireMonitor")
@CheckClientToken
@CheckUserToken
@Api(tags = "疲劳监测管理")

public class PersonTireMonitorController extends BaseController<TireMonitorBiz, TireMonitor, String> {
    @Autowired
    private UserController userCtr;
    @Autowired
    private DepartController departCtr;
    @Autowired
    private CheckPointController checkPointCtr;
    @Autowired
    private LogBiz logBiz;

    @ApiOperation("疲劳监测查询")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public TableResultResponse tireMonitorQuery(String departId, String startTime, String endTime,
                                               @RequestParam(name = "limit", defaultValue = "10") int limit,
                                               @RequestParam(name = "page", defaultValue = "1") int page){

        List<String> pointIds = getUserPointId(departId);
        Page<Object> result = PageHelper.startPage(page, limit);
        List<Map<String,String>> list=baseBiz.getTireMonitorList(pointIds,startTime,endTime);

        logBiz.saveLog("疲劳监测管理","疲劳监测查询", "api/person/ajy/tireMonitor/query");

        return new TableResultResponse(result.getTotal(),list);
    }

    @ApiOperation("疲劳监测详情")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public TableResultResponse tireMonitorDetail(String pointId,String time,
                                                 @RequestParam(name = "limit", defaultValue = "10") int limit,
                                                 @RequestParam(name = "page", defaultValue = "1") int page){
        Page<Object> result = PageHelper.startPage(page, limit);
        if( "".equals(time)||time==null ){
            SimpleDateFormat format  = new SimpleDateFormat("YYYY-MM-dd");
            time=format.format(new Date());
        }
        List<TireMonitor> list=baseBiz.getTireMonitorDetail(time,pointId);
        logBiz.saveLog("疲劳监测管理","疲劳监测详情", "api/person/ajy/tireMonitor/detail");

        return new TableResultResponse(result.getTotal(),list);
    }

    public ObjectRestResponse tireMonitorStatisByTime(String departId, String startTime, String endTime,String timeType,String dataType){

        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

        SimpleDateFormat format  = new SimpleDateFormat("YYYY-MM-dd");

        if("".equals(startTime)||startTime==null){
            startTime=format.format(new Date());
        }
        if("".equals(endTime)||endTime==null){
             endTime=format.format(new Date());
        }

        if(timeType.equals("byHour")){
            List<String> timeList= DateUtil.findDaysStr(startTime,endTime);
            for(int i=0;i<timeList.size();i++){
                Map<String,Object> map=new HashMap<>();
                if(dataType.equals("byRate")){
                    map.put("hourList",baseBiz.getTireMonitorByHourRate(departId,timeList.get(i),timeList.get(i)));
                }else if(dataType.equals("byCount")){
                    map.put("hourList",baseBiz.getTireMonitorByHourCount(departId,timeList.get(i),timeList.get(i)));
                }

                map.put("day",timeList.get(i));
                list.add(map);
            }
        }
        if(timeType.equals("byDay")){
            if(dataType.equals("byRate")){
                list=baseBiz.getTireMonitorByDayRate(departId,startTime,endTime);
            }else if(dataType.equals("byCount")){
                list=baseBiz.getTireMonitorByDayCount(departId,startTime,endTime);
            }
        }

        return ObjectRestResponse.ok(list);
    }

    @ApiOperation("疲劳监测统计（时间维度）")
    @RequestMapping(value = "/statisByTime", method = RequestMethod.GET)
    public ObjectRestResponse tireMonitorStatisByTime1(String departId, String startTime, String endTime,String timeType,String dataType){

        List list = new ArrayList<>();

        SimpleDateFormat format  = new SimpleDateFormat("YYYY-MM-dd");

        if("".equals(startTime)||startTime==null){
            startTime=format.format(new Date());
        }
        if("".equals(endTime)||endTime==null){
            endTime=format.format(new Date());
        }

        if(timeType.equals("byHour")){
            List<String> timeList= DateUtil.findDaysStr(startTime,endTime);
            for(int i=0;i<timeList.size();i++){
                if(dataType.equals("byRate")){
                    List<Map<String,Object>> hourList=baseBiz.getTireMonitorByHourRate(departId,timeList.get(i),timeList.get(i));
                    for(Map<String,Object> map:hourList){
                        list.add(map);
                    }
                }else if(dataType.equals("byCount")){
                    List<Map<String,Object>> hourList=baseBiz.getTireMonitorByHourCount(departId,timeList.get(i),timeList.get(i));
                    for(Map<String,Object> map:hourList){
                        list.add(map);
                    }
                }
            }
        }
        if(timeType.equals("byDay")){
            if(dataType.equals("byRate")){
                list=baseBiz.getTireMonitorByDayRate(departId,startTime,endTime);
            }else if(dataType.equals("byCount")){
                list=baseBiz.getTireMonitorByDayCount(departId,startTime,endTime);
            }
        }

        logBiz.saveLog("疲劳监测管理","疲劳监测统计（时间维度）", "api/person/ajy/tireMonitor/statisByTime");

        return ObjectRestResponse.ok(list);
    }

    @ApiOperation("疲劳监测统计（车站维度）")
    @RequestMapping(value = "/statisByDepart", method = RequestMethod.GET)
    public ObjectRestResponse tireMonitorStatisByDepart(String departId, String startTime, String endTime,String departType){

        List<String> departIds=new ArrayList<String>();
        if(!"".equals(departId)&&departId!=null){
            List<Map<String,String>> departMap=departCtr.getDepartChildren(departId);
            departIds=departMap.stream().map(u->u.get("id")).collect(Collectors.toList());
        }else {
            List<Map<String,String>> departMap=userCtr.getStation(BaseContextHandler.getUserID());
            departIds=departMap.stream().map(u->u.get("id")).collect(Collectors.toList());
        }

        List<String> pointIds=new ArrayList<String>();
        if(!"".equals(departId)&&departId!=null){
            pointIds=checkPointCtr.getAllPointByDepart(departId);
        }else {
            pointIds=userCtr.getPoint(BaseContextHandler.getUserID());
        }

        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        if ( departType.equals("byDepart")){
            list=baseBiz.getTireMonitorByDepart(departId,startTime,endTime);
        }else if(departType.equals("byPoint")){
            list=baseBiz.getTireMonitorByPoint(departId,startTime,endTime);
        }

        logBiz.saveLog("疲劳监测管理","疲劳监测统计（车站维度）", "api/person/ajy/tireMonitor/statisByDepart");

        return ObjectRestResponse.ok(list);
    }

    @ApiOperation("疲劳监测统计（总）")
    @RequestMapping(value = "/statisAll", method = RequestMethod.GET)
    public ObjectRestResponse tireMonitorStatisAll(String departId, String startTime, String endTime){
        Map<String, String> map=baseBiz.getTireMonitorAll(departId,startTime,endTime);

        logBiz.saveLog("疲劳监测管理","疲劳监测统计（总）", "api/person/ajy/tireMonitor/statisAll");

        return ObjectRestResponse.ok(map);
    }

    @ApiOperation("疲劳监测列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public TableResultResponse tireMonitorList(String departId, String startTime, String endTime){
        List<String> pointIds = getUserPointId(departId);
        List<Map<String,String>> list=baseBiz.getTireMonitorList(pointIds,startTime,endTime);

        logBiz.saveLog("疲劳监测管理","疲劳监测列表", "api/person/ajy/tireMonitor/list");

        return new TableResultResponse(list.size(),list);
    }

    private List<String> getUserPointId(String departId) {
        List<String> pointIds=new ArrayList<String>();
        if(!"".equals(departId)&&departId!=null){
            pointIds=checkPointCtr.getAllPointByDepart(departId);
        }else {
            pointIds=userCtr.getPoint(BaseContextHandler.getUserID());
        }
        if(pointIds.size()<=0){
            pointIds.add("");
        }
        return pointIds;
    }
}
