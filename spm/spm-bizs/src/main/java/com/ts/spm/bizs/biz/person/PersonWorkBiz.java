package com.ts.spm.bizs.biz.person;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.person.AjyInfo;
import com.ts.spm.bizs.entity.person.AjyWorkInfo;
import com.ts.spm.bizs.mapper.person.AjyWorkInfoMapper;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import com.ts.spm.bizs.rest.admin.DepartController;
import com.ts.spm.bizs.rest.admin.UserController;
import com.ts.spm.bizs.rest.stat.HomeController;
import com.ts.spm.bizs.util.DataUtil;
import com.ts.spm.bizs.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author luoyu
 * @Date 2020/7/2 19:16
 * @Version 1.0
 */
@Service
public class PersonWorkBiz extends BusinessBiz<AjyWorkInfoMapper, AjyWorkInfo> {
    @Autowired
    private HomeController homeController;
    @Autowired
    private CheckPointController checkPointCtr;
    @Autowired
    private DepartController departCtr;
    @Autowired
    UserController userCtr;
    @Autowired
    private AjyBiz ajyBiz;

    public List<Map<String,String>> getPersonWorkList(List<String> departIds, String startTime, String endTime,String idCard,
                                                      String ajyName,String ifCard,String ifYoung,String ifDay,String workType,String ajyType){

        List<Map<String,String>> list=mapper.getPersonWorkList(departIds,ajyName,idCard,ifCard,ifYoung,startTime,endTime,ifDay,workType,ajyType);
        for(Map<String,String> map:list){
            String ajyIdno=map.get("ajyIdno");
            AjyInfo ajyInfo=ajyBiz.getPersonCard(ajyIdno);
            DataUtil.checkNull(ajyInfo);
            map.put("departName",departCtr.getDepartDetail(map.get("departId")).get("name"));
            map.put("pointName",checkPointCtr.getPointInfo(map.get("pointId")).get("name"));
            map.put("picPath",ajyInfo.getPicPath());
            map.put("securityid",ajyInfo.getSecurityid());
            map.put("ajCom",ajyInfo.getAjcom());
        }
        return list;
    }

    public List<Map<String,Object>> getPersonWorkStatis(List<String> departIds, String startTime, String endTime){

        List<Map<String,Object>> list=mapper.getPersonWorkStatis(departIds,startTime,endTime);
        for(Map<String,Object> map:list){
            String ajyIdno=map.get("ajyIdno").toString();
            AjyInfo ajyInfo=ajyBiz.getPersonCard(ajyIdno);
            DataUtil.checkNull(ajyInfo);
            map.put("departName",departCtr.getDepartDetail(map.get("departId").toString()).get("name"));
            //map.put("pointName",checkPointCtr.getPointInfo(map.get("pointId").toString()).get("name"));
            map.put("picPath",ajyInfo.getPicPath());
            map.put("securityid",ajyInfo.getSecurityid());
            map.put("ajCom",ajyInfo.getAjcom());
        }
        return list;
    }

    public Map<String,Object> personWorkStatistic(String departId, List<String> departIds, String time, boolean interDay, int interval, String offTime){

        Map<String,Object> ajyWorkStatistic=new HashMap<String,Object>();
        int onJobCount=0;
        int planCount=0;
        int cardCount=0;
        int noCardCount=0;
        int minorCount=0;

        String endTime = time;
        Date Time = DateUtil.parse(time,"yyyy-MM-dd HH:mm:ss");
        String startTime = "";
        if(interDay) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(Time);
            cal.add(Calendar.HOUR, -interval);
            startTime = com.github.wxiaoqi.security.common.util.DateUtil.date2Str(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
        } else {
            startTime = com.github.wxiaoqi.security.common.util.DateUtil.date2Str(Time, "yyyy-MM-dd") + " 00:00:00";
            Date off = DateUtil.parse(com.github.wxiaoqi.security.common.util.DateUtil.date2Str(Time,"yyyy-MM-dd") + " " + offTime, "yyyy-MM-dd HH:mm:ss");
            if(Time.after(off)) {
                endTime = com.github.wxiaoqi.security.common.util.DateUtil.date2Str(off, "yyyy-MM-dd HH:mm:ss");
            }
        }

        List<Map<String,Object>> allList = getPersonWorkStatis(departIds, startTime, endTime);

        if(allList.size()>0){
            onJobCount=allList.size();
            for(Map<String,Object> map:allList){
                //ifCard 是否持证（0：持证，1：不持证）
                //cardFlag 是否持证（数据库中）持证：1 未持证：0
                Integer cardFlag=Integer.parseInt(map.get("ifCard").toString());
                if(cardFlag==1){
                    cardCount++;
                }
                if(cardFlag==0){
                    noCardCount++;
                }
                if(map.get("ifYoung").equals("1")){
                    minorCount++;
                }
            }
        }
        if(time!=null&&!time.equals("")){
//            String hour=DateFormatUtils.format(Time, "HH:mm:ss");
            planCount=homeController.getPlanPersonCount(departId, time);
        }else{
//            String hour=DateFormatUtils.format(new Date(), "HH:mm:ss");
            planCount=homeController.getPlanPersonCount(departId, time);
        }

        ajyWorkStatistic.put("onJobCount",onJobCount);
        ajyWorkStatistic.put("planCount",planCount);
        ajyWorkStatistic.put("cardCount",cardCount);
        ajyWorkStatistic.put("noCardCount",noCardCount);
        ajyWorkStatistic.put("minorCount",minorCount);

        return ajyWorkStatistic;
    }

//    public List<Map<String,Object>> personWorkQuery(List<String> departIds, String time){
////        List<String> departIds=new ArrayList<String>();
////        if(!"".equals(departId)&&departId!=null){
////            List<Map<String,String>> departMap=departCtr.getDepartChildren(departId);
////            if(departMap.size()>0){
////                departIds=departMap.stream().map(u->u.get("id")).collect(Collectors.toList());
////            }else{
////                departIds.add(departId);
////            }
////        }else {
////            List<Map<String,String>> departMap=userCtr.getStation(BaseContextHandler.getUserID());
////            departIds=departMap.stream().map(u->u.get("id")).collect(Collectors.toList());
////        }
//        String startTime = "";
//        String endTime=time;
//        String ifDay="1";
//        Date Time=DateUtil.parse(time,"yyyy-MM-dd HH:mm:ss");
//        if(time!=null&&!time.equals("")){
//            startTime=com.github.wxiaoqi.security.common.util.DateUtil.date2Str(Time,"yyyy-MM-dd")+" 00:00:00";
//        }else{
//            startTime = endTime;
//        }
//        String today= DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
//        if(today.equals(time)){
//            ifDay="0";
//        }
//        List<Map<String,Object>> allList=getPersonWorkStatis(departIds, startTime, endTime, ifDay);
//        for(Map<String,Object> map:allList){
//            List<Map<String,Object>> personList=mapper.getPersonByIdoTime(map.get("ajyIdno").toString(),time);
//            if(null!=personList&&personList.size()>0){
//                Map<String,Object> person=personList.get(0);
//                String stime=person.get("stime").toString();
//                String etime=person.get("etime").toString();
//                Long workSecond;
//                if(stime.equals(etime)){
//                    workSecond=DateUtil.dateDiff(stime,time,"yyyy-MM-dd HH:mm:ss","sec");
//                }else {
//                    workSecond=DateUtil.dateDiff(stime,etime,"yyyy-MM-dd HH:mm:ss","sec");
//                }
//                map.put("workSecond",workSecond);
//                map.put("workTime",DateUtil.secondToTime(workSecond));
//                map.put("pointName",checkPointCtr.getPointInfo(map.get("pointId").toString()).get("name"));
//                map.put("departName",departCtr.getDepartDetail(map.get("departId").toString()).get("name"));
//                map.put("stime",stime);
//                map.put("etime",etime);
//            }
//
//        }
//        return allList;
//    }

    public List<Map<String,Object>> getPersonTime(List<String> departIds, String startTime, String endTime,String ifDay){
        List<Map<String,Object>> list=mapper.getPersonWorkTime(departIds,startTime,endTime+" 23:59:59",ifDay);
        for(Map<String,Object> map:list){
            map.put("departName",departCtr.getDepartDetail(map.get("departId").toString()).get("name"));
            map.put("pointName",checkPointCtr.getPointInfo(map.get("pointId").toString()).get("name"));
            String workSecond=map.get("workSecond").toString();
            long workTimeLong=Long.parseLong(workSecond);
            map.put("workTime",DateUtil.secondToTime(workTimeLong));
        }
        return list;
    }

    public List<Map<String,Object>> getOnWorkData(List<String> departIds, String time, boolean interDay, int interval, String offTime) {
        String endTime = time;
        Date Time = DateUtil.parse(time,"yyyy-MM-dd HH:mm:ss");
        String startTime = "";
        if(interDay) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(Time);
            cal.add(Calendar.HOUR, -interval);
            startTime = com.github.wxiaoqi.security.common.util.DateUtil.date2Str(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
        } else {
            startTime = com.github.wxiaoqi.security.common.util.DateUtil.date2Str(Time, "yyyy-MM-dd") + " 00:00:00";
            Date off = DateUtil.parse(com.github.wxiaoqi.security.common.util.DateUtil.date2Str(Time,"yyyy-MM-dd") + " " + offTime, "yyyy-MM-dd HH:mm:ss");
            if(Time.after(off)) {
                endTime = com.github.wxiaoqi.security.common.util.DateUtil.date2Str(off, "yyyy-MM-dd HH:mm:ss");
            }
        }
        List<Map<String,Object>> allList = mapper.getOnWorkData(departIds, startTime, endTime);
        for(Map<String,Object> map : allList) {
//            if(map.get("etime") != null) {
//                Date etime = (Date)map.get("etime");
//                if(Time.before(etime)) {
//                    map.remove("workType");
//                    map.put("workType", 1);
//                    map.remove("workSecond");
//                    Date stime = (Date)map.get("stime");
//                    map.put("workSecond", (Time.getSeconds() - stime.getSeconds()));
//                }
//            }
            map.put("workTime",DateUtil.secondToTime(Long.valueOf(map.get("workSecond").toString())));
            map.put("pointName",checkPointCtr.getPointInfo(map.get("pointId").toString()).get("name"));
            map.put("departName",departCtr.getDepartDetail(map.get("departId").toString()).get("name"));
        }
        return allList;
    }

    public Map<String,Object> workcfg() {
        return mapper.workcfg();
    }
}
