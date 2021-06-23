package com.ts.spm.bizs.biz.person;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.person.TireMonitor;
import com.ts.spm.bizs.mapper.person.TireMonitorMapper;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import com.ts.spm.bizs.rest.admin.DepartController;
import com.ts.spm.bizs.rest.admin.UserController;
import com.ts.spm.bizs.util.DataUtil;
import com.ts.spm.bizs.util.DateUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author luoyu
 * @Date 2020/7/2 14:03
 * @Version 1.0
 */
@Service
public class TireMonitorBiz extends BusinessBiz<TireMonitorMapper, TireMonitor> {
    @Autowired
    private UserController userCtr;
    @Autowired
    private CheckPointController checkPointCtr;
    @Autowired
    private DepartController departCtr;

    public List<Map<String,String>> getTireMonitorList(List<String> pointIds, String startTime, String endTime){

//        if(!"".equals(startTime)&&startTime!=null&&!"".equals(endTime)&&endTime!=null){
//
//        }

        List<Map<String,String>> tireMonitorList=mapper.getTireMonitorList(pointIds,startTime,endTime);

        for(Map<String,String> list:tireMonitorList){
            list.put("pointName",checkPointCtr.getPointInfo(list.get("pointId")).get("name"));
            list.put("departId",checkPointCtr.getDepartInfo(list.get("pointId")).get("id"));
            list.put("departName",checkPointCtr.getDepartInfo(list.get("pointId")).get("name"));
        }
        return tireMonitorList;
    }

    public Map<String,String> getTireMonitorAll(String departId, String startTime, String endTime){
        List<String> pointIds=new ArrayList<String>();
        if(!"".equals(departId)&&departId!=null){
            pointIds=checkPointCtr.getAllPointByDepart(departId);
        }else {
            pointIds=userCtr.getPoint(BaseContextHandler.getUserID());
        }

        Map<String,String> tireMonitorList=mapper.getTireMonitorAll(pointIds,startTime,endTime);

        return tireMonitorList;
    }

    public List<Map<String,Object>> getTireMonitorByDayCount(String departId, String startTime, String endTime){
        List<String> pointIds=new ArrayList<String>();
        if(!"".equals(departId)&&departId!=null){
            pointIds=checkPointCtr.getAllPointByDepart(departId);
        }else {
            pointIds=userCtr.getPoint(BaseContextHandler.getUserID());
        }

        List<Map<String, Object>> list=new ArrayList<>();

        List<String> timeList= DateUtil.findDaysStr(startTime,endTime);
        for(String time:timeList){
            Map<String, Object> map=new HashMap<>();
            map.put("axisTime",time);
            map.put("data",0);
            list.add(map);
            List<Map<String, String>> dayList=mapper.getTireMonitorByDay(pointIds, time, time);
            if(dayList.size()>0){
                for(Map<String, String> dayMap:dayList){
                    String day = dayMap.get("axisTime"); //时间
                    map.replace("data",map.get("data"),dayMap.get("warnCount"));
                }
            }
        }

        return list;
    }

    public List<Map<String,Object>> getTireMonitorByDayRate(String departId, String startTime, String endTime){
        List<String> pointIds=new ArrayList<String>();
        if(!"".equals(departId)&&departId!=null){
            pointIds=checkPointCtr.getAllPointByDepart(departId);
        }else {
            pointIds=userCtr.getPoint(BaseContextHandler.getUserID());
        }

        List<Map<String, Object>> list=new ArrayList<>();

        List<String> timeList= DateUtil.findDaysStr(startTime,endTime);
        for(String time:timeList){
            Map<String, Object> map=new HashMap<>();
            map.put("axisTime",time);
            map.put("data",0.00);
            list.add(map);
            List<Map<String, String>> dayList=mapper.getTireMonitorByDay(pointIds, time, time);
            if(dayList.size()>0){
                for(Map<String, String> dayMap:dayList){
                    String day = dayMap.get("axisTime"); //时间
                    String acount=String.valueOf(dayMap.get("allCount"));
                    if(acount!="0"){
                        String wcount=String.valueOf(dayMap.get("warnCount"));
                        int warnCount=Integer.parseInt(wcount);
                        int allCount=Integer.parseInt(acount);
                        Double warnRate= DataUtil.getPercentValue(warnCount,allCount);
                        map.replace("data",map.get("data"),warnRate);
                    }
                }
            }
        }

        return list;

    }

    public List<Map<String,Object>> getTireMonitorByHourCount(String departId, String startTime, String endTime){
        List<String> pointIds=new ArrayList<String>();
        if(!"".equals(departId)&&departId!=null){
            pointIds=checkPointCtr.getAllPointByDepart(departId);
        }else {
            pointIds=userCtr.getPoint(BaseContextHandler.getUserID());
        }

        List<Map<String, Object>> list=new ArrayList<>();

        for(int j=01;j<21;j++){
            Map<String, Object> map=new HashMap<>();
            map.put("axisTime",Integer.toString(j+3));
            map.put("data",0);
            list.add(map);
        }
        List<Map<String, String>> hourList=mapper.getTireMonitorByHour(pointIds, startTime, endTime);
        if(hourList.size()>0){
            for(Map<String, String> hourMap:hourList){
                Integer h = Integer.parseInt(hourMap.get("axisTime")); //时间
                if (h >= 4 && h <= 23){
                    Map<String,Object> map1=list.get(h-4);
                    map1.replace("data",map1.get("data"),hourMap.get("warnCount"));
                }
            }
        }

        for(Map<String,Object> map:list){
//            map.replace("axisTime",map.get("axisTime"),startTime+" "+map.get("axisTime"));
            try {
                Integer h = Integer.parseInt(map.get("axisTime").toString()) ;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(startTime);
                String day= DateFormatUtils.format(date, "MM-dd");
                if(h==4){
                    map.replace("axisTime",map.get("axisTime"),day+" "+map.get("axisTime"));
                }
            } catch (ParseException e) {
                e.printStackTrace();

            }
        }

        return list;
    }

    public List<Map<String,Object>> getTireMonitorByHourRate(String departId, String startTime, String endTime){
        List<String> pointIds=new ArrayList<String>();
        if(!"".equals(departId)&&departId!=null){
            pointIds=checkPointCtr.getAllPointByDepart(departId);
        }else {
            pointIds=userCtr.getPoint(BaseContextHandler.getUserID());
        }

        List<Map<String, Object>> list=new ArrayList<>();

        for(int j=01;j<21;j++){
            Map<String, Object> map=new HashMap<>();
            map.put("axisTime",Integer.toString(j+3));
            map.put("data",0.00);
            list.add(map);
        }
        List<Map<String, String>> hourList=mapper.getTireMonitorByHour(pointIds, startTime, endTime);
        if(hourList.size()>0){
            for(Map<String, String> hourMap:hourList){
                Integer h = Integer.parseInt(hourMap.get("axisTime")); //时间
                if (h >= 4 && h <= 23){
                    Map<String,Object> map1=list.get(h-4);
                    String acount=String.valueOf(hourMap.get("allCount"));
                    if(acount!="0"){
                        String wcount=String.valueOf(hourMap.get("warnCount"));
                        int warnCount=Integer.parseInt(wcount);
                        int allCount=Integer.parseInt(acount);
                        Double warnRate=DataUtil.getPercentValue(warnCount,allCount);
                        map1.replace("data",map1.get("data"),warnRate);
                    }

                }
            }
        }

        for(Map<String,Object> map:list){
//            map.replace("axisTime",map.get("axisTime"),startTime+" "+map.get("axisTime"));
            try {
                Integer h = Integer.parseInt(map.get("axisTime").toString()) ;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(startTime);
                String day= DateFormatUtils.format(date, "MM-dd");
//                if(h==4){
                    map.replace("axisTime",map.get("axisTime"),day+" "+map.get("axisTime"));
//                }
            } catch (ParseException e) {
                e.printStackTrace();

            }
        }

        return list;
    }


    public List<Map<String,String>> getTireMonitorByPoint(String departId, String startTime, String endTime){
        List<String> pointIds=new ArrayList<String>();
        if(!"".equals(departId)&&departId!=null){
            pointIds=checkPointCtr.getAllPointByDepart(departId);
        }else {
            pointIds=userCtr.getPoint(BaseContextHandler.getUserID());
        }

        List<Map<String,String>> tireMonitorList=mapper.getTireMonitorByPoint(pointIds,startTime,endTime);

        for(Map<String,String> list:tireMonitorList){
            String pointId=list.get("pointId");
            String pointName=checkPointCtr.getPointInfo(pointId).get("name");
            Map<String,String> departMap=checkPointCtr.getDepartInfo(pointId);
            list.put("name",departMap.get("name")+"-"+pointName);
        }
        return tireMonitorList;
    }

    public List<Map<String,String>> getTireMonitorByDepart(String departId, String startTime, String endTime){
        List<String> pointIds=new ArrayList<String>();
        if(!"".equals(departId)&&departId!=null){
            pointIds=checkPointCtr.getAllPointByDepart(departId);
        }else {
            pointIds=userCtr.getPoint(BaseContextHandler.getUserID());
        }

        List<Map<String,String>> tireMonitorList=mapper.getTireMonitorByDepart(pointIds,startTime,endTime);

        for(Map<String,String> list:tireMonitorList){
            list.put("name",departCtr.getDepartDetail(list.get("departId")).get("name"));
        }
        return tireMonitorList;
    }

    public List<TireMonitor> getTireMonitorDetail(String time,String pointId){
        List<String> pointIds=new ArrayList<String>();
//        if(!"".equals(departId)&&departId!=null){
//            pointIds=adminFeign.getAllPointByDepart(departId);
//        }else {
//            pointIds=adminFeign.getPoint(BaseContextHandler.getUserID());
        if(!"".equals(pointId)&&pointId!=null){
            pointIds.add(pointId);
        }

        List<TireMonitor> tireMonitorList=mapper.getTireMonitorDetail(time,"0",pointIds);

        for(TireMonitor tireMonitor:tireMonitorList){
            tireMonitor.setPointName(checkPointCtr.getPointInfo(tireMonitor.getPointId()).get("name"));
            DataUtil.checkNull(tireMonitor);
        }

        return tireMonitorList;
    }

}
