package com.ts.spm.bizs.biz.stat;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.stat.PackageCount;
import com.ts.spm.bizs.mapper.stat.PackageCountMapper;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import com.ts.spm.bizs.rest.admin.UserController;
import com.ts.spm.bizs.util.DateUtil;
import com.ts.spm.bizs.vo.stat.StatDayCntVo;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author luoyu
 * @Date 2020/6/4 17:45
 * @Version 1.0
 */
@Service
public class PackageBiz  extends BusinessBiz<PackageCountMapper, PackageCount>{

    @Autowired
    private UserController userCtr;
    @Autowired
    private CheckPointController checkPointCtr;

    public int getPackageByPointId(List<String> pointIds, String startTime, String endTime){
        return mapper.getPackageCountByPointIds(pointIds, startTime,endTime);
    }

    public int getPackageByDepartId(List<String> departIds, String startTime, String endTime){
        return mapper.getPackageCountByDepartIds(departIds, startTime,endTime);
    }

    public List<StatDayCntVo> statis(List<String> pointIds, Date start, Date end){
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
        List<Map<String, String>> hourList=mapper.statisByHour(pointIds, startTime, endTime);
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
    }

    public List<Map<String, String>> statisByDepart(List<String> pointIds,String startTime, String endTime){
        return mapper.statisByDepart(pointIds, startTime, endTime);
    }

    public List<Map<String, String>> statisByPoint(List<String> pointIds,String startTime, String endTime){
        return mapper.statisByPoint(pointIds, startTime, endTime);
    }
    public Integer statis2(List<String> pointIds, Date start, Date end) {
        return mapper.statis2(pointIds, start, end);
    }

    public List<PackageCount> packageCountList(String departId,String startTime,String endTime){
        Example example = new Example(PackageCount.class);
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
            criteria.andBetween("time", DateUtil.parse(startTime+" 00:00:00","yyyy-MM-dd HH:mm:ss"),DateUtil.parse(endTime+" 23:59:59","yyyy-MM-dd HH:mm:ss"));
        }

        example.setOrderByClause("time desc");

        List<PackageCount> list=selectByExample(example);

        for(PackageCount packageCount:list){
            packageCount.setPointName(checkPointCtr.getPointInfo(packageCount.getPointId()).get("name"));
            packageCount.setStationName(checkPointCtr.getDepartInfo(packageCount.getPointId()).get("name"));

        }

        return list;
    }

    public List<Map> getPackageList(List<String> pointIds,String startTime,String endTime){
        List<Map> list=mapper.getPackageList(pointIds,startTime,endTime);
        int index = 1;
        for(Map packageCount:list){
            packageCount.put("index",index++);
            packageCount.put("pointName",checkPointCtr.getPointInfo(packageCount.get("pointId").toString()).get("name"));
            packageCount.put("stationName",checkPointCtr.getDepartInfo(packageCount.get("pointId").toString()).get("name"));
        }
        return list;
    }
}
