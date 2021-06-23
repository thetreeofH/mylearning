package com.ts.spm.bizs.biz.stat;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.stat.PassPersonCount;
import com.ts.spm.bizs.mapper.stat.PassPersonCountMapper;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import com.ts.spm.bizs.rest.admin.UserController;
import com.ts.spm.bizs.util.DateUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author luoyu
 * @Date 2020/6/4 17:46
 * @Version 1.0
 */
@Service
public class PassFlowBiz extends BusinessBiz<PassPersonCountMapper, PassPersonCount> {

    @Autowired
    UserController userCtr;
    @Autowired
    CheckPointController checkPointCtr;

    public List<Map<String, String>> statis(List<String> pointIds, Date start, Date end){
        return mapper.statis(pointIds, start, end);
    }

    public List<Map<String, Object>> statisByHour(List<String> pointIds,String startTime, String endTime){
        List<Map<String, Object>> list=new ArrayList<>();

        for(int j=01;j<21;j++){
            Map<String, Object> map=new HashMap<>();
            map.put("axisTime",Integer.toString(j+3));
            map.put("count",0);
            list.add(map);
        }
        List<Map<String, String>> hourList=mapper.statisByDaysHour(pointIds, startTime, endTime);
        if(hourList.size()>0){
            for(Map<String, String> hourMap:hourList){
                Integer h = Integer.parseInt(hourMap.get("axisTime")); //时间
                if (h >= 4 && h <= 23){
                    Map<String,Object> map1=list.get(h-4);
                    map1.replace("count",map1.get("count"),hourMap.get("count"));
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

    public List<Map<String, Object>> statisByDay(List<String> pointIds,String startTime, String endTime){
        List<Map<String, Object>> list=new ArrayList<>();

        List<String> timeList= DateUtil.findDaysStr(startTime,endTime);
        for(String time:timeList){
            Map<String, Object> map=new HashMap<>();
            map.put("axisTime",time);
            map.put("count","0");
            list.add(map);
            List<Map<String, String>> dayList=mapper.statisByDay(pointIds, time, time);
            if(dayList.size()>0){
                for(Map<String, String> dayMap:dayList){
                    String day = dayMap.get("axisTime"); //时间
                    map.replace("count",map.get("count"),dayMap.get("count"));
                }
            }
        }



        return list;

//        return mapper.statisByDay(pointIds, startTime, endTime);
    }

    public List<Map<String, String>> statisByDaysHour(List<String> pointIds,String startTime, String endTime){
        List<Map<String, String>> list=new ArrayList<>();

        List time=new ArrayList();
        for(int j=01;j<21;j++){
            Map<String, String> map=new HashMap<>();
            map.put("axisTime",Integer.toString(j+3));
            map.put("count","0");
            list.add(map);
        }
        List<Map<String, String>> hourList=mapper.statisByDaysHour(pointIds, startTime, endTime);
        if(hourList.size()>0){
            for(Map<String, String> hourMap:hourList){
                Integer h = Integer.parseInt(hourMap.get("axisTime")); //时间
                if (h >= 4 && h <= 23){
                    Map<String,String> map1=list.get(h-4);
                    map1.replace("count",map1.get("count"),hourMap.get("count"));
                }
            }
        }


        return list;


//        return mapper.statisByDaysHour(pointIds, startTime, endTime);
    }

    public List<Map<String, String>> statisByDepart(List<String> pointIds,String startTime, String endTime){
        return mapper.statisByDepart(pointIds, startTime, endTime);
    }

    public List<Map<String, String>> statisByPoint(List<String> pointIds,String startTime, String endTime){
        return mapper.statisByPoint(pointIds, startTime, endTime);
    }
    public Integer statis2(List<String> pointIds, Date start, Date end){
        return mapper.statis2(pointIds, start, end);
    }

    public List<PassPersonCount> passList(String departId,String startTime, String endTime){
        Example example = new Example(PassPersonCount.class);
        Example.Criteria criteria = example.createCriteria();

        List<String> pointIds=new ArrayList<String>();
        if(!"".equals(departId)&&departId!=null){
            pointIds=checkPointCtr.getAllPointByDepart(departId);
        }else {
            pointIds=userCtr.getPoint(BaseContextHandler.getUserID());
        }
        if(pointIds.size()<=0){
            pointIds.add("");
        }

        criteria.andIn("pointId",pointIds);

        if(!"".equals(startTime)&&startTime!=null&&!"".equals(endTime)&&endTime!=null){
            criteria.andBetween("countDate", DateUtil.parse(startTime+" 00:00:00","yyyy-MM-dd HH:mm:ss"),DateUtil.parse(endTime+" 23:59:59","yyyy-MM-dd HH:mm:ss"));
        }

        example.setOrderByClause("COUNT_DATE desc");


        List<PassPersonCount> list=selectByExample(example);
        for(PassPersonCount passPersonCount:list){
            passPersonCount.setPointName(checkPointCtr.getPointInfo(passPersonCount.getPointId()).get("name"));
            passPersonCount.setStationName(checkPointCtr.getDepartInfo(passPersonCount.getPointId()).get("name"));
        }
        return list;
    }

    public List<Map> getPassList(List<String> pointIds,String startTime, String endTime){
        List<Map> list=mapper.getPassList(pointIds,startTime,endTime);
        int index = 1;
        for(Map passPersonCount:list){
            passPersonCount.put("index",index++);
            passPersonCount.put("pointName",checkPointCtr.getPointInfo(passPersonCount.get("pointId").toString()).get("name"));
            passPersonCount.put("departName",checkPointCtr.getDepartInfo(passPersonCount.get("pointId").toString()).get("name"));
        }
        return list;
    }
}
