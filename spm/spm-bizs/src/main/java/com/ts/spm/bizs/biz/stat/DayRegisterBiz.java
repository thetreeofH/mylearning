package com.ts.spm.bizs.biz.stat;


import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.util.DateUtil;
import com.ts.spm.bizs.biz.matter.PointDangerBiz;
import com.ts.spm.bizs.biz.person.AjyBiz;
import com.ts.spm.bizs.entity.stat.DayProblem;
import com.ts.spm.bizs.entity.stat.DayRegister;
import com.ts.spm.bizs.mapper.stat.DayRegisterMapper;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import com.ts.spm.bizs.rest.admin.CompanyController;
import com.ts.spm.bizs.rest.admin.DepartController;
import com.ts.spm.bizs.rest.admin.UserController;
import com.ts.spm.bizs.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @Author luoyu
 * @Date 2020/6/3 11:3811
 * @Version 1.0
 */
@Service
public class DayRegisterBiz extends BusinessBiz<DayRegisterMapper, DayRegister> {
    @Autowired
    private DayProblemBiz dayProblemBiz;
    @Autowired
    private PackageBiz packageBiz;
    @Autowired
    private PointDangerBiz pointDangerBiz;
    @Autowired
    private AutoTipsBiz autoTipsBiz;
    @Autowired
    private AjyBiz ajyBiz;
    @Autowired
    private CompanyController companyCtr;
    @Autowired
    private UserController userCtr;
    @Autowired
    private CheckPointController checkPointCtr;
    @Autowired
    private DepartController departCtr;

    public List<Map> getDayRegisterList(List<String> pointIds,String startTime,String endTime,String probType,String checkOrg,String checker,String checkType,String operatingCompanyId,String securityCompanyId){
//        List<DayRegister> list=mapper.getDayRegisterList(pointIds, startTime,endTime,probType,checkOrg,checker,checkType,operatingCompanyId,securityCompanyId);
//
//        for(DayRegister dayRegister:list){
//            dayRegister.setDepartName(adminFeign.getDepartInfo(dayRegister.getPointId()).get("name"));
//            dayRegister.setOperatingCompany(adminFeign.getDepartDetail(dayRegister.getOperatingCompany()).get("name"));
//            dayRegister.setSecurityCompany(adminFeign.getCompanyDetail(dayRegister.getSecurityCompany()).get("name"));
//            dayRegister.setPointName(adminFeign.getPointInfo(dayRegister.getPointId()).get("name"));
//        }
        List<Map> list=mapper.getDayRegisterList(pointIds, startTime,endTime,probType,checkOrg,checker,checkType,operatingCompanyId,securityCompanyId);

        for(Map dayRegister:list){
            dayRegister.replace("checkOrg",departCtr.getDepartDetail(dayRegister.get("checkOrg").toString()).get("name"));
            dayRegister.put("departName",checkPointCtr.getDepartInfo(dayRegister.get("pointId").toString()).get("name"));
            dayRegister.replace("operatingCompany",departCtr.getDepartDetail(dayRegister.get("operatingCompany").toString()).get("name"));
            dayRegister.replace("securityCompany", companyCtr.getCompanyDetail(dayRegister.get("securityCompany").toString()).get("name"));
            dayRegister.put("pointName",checkPointCtr.getPointInfo(dayRegister.get("pointId").toString()).get("name"));
        }
        return list;
    }

    public List<Map> getDayCheck(List<String> pointIds,String startTime,String endTime,String probType,String checkOrg,String checker,String checkType,String operatingCompanyId,String securityCompanyId){
        List<Map> list=getDayRegisterList(pointIds, startTime,endTime,probType,checkOrg,checker,checkType,operatingCompanyId,securityCompanyId);
        for(Map dayRegister:list){
            if(dayRegister.get("insertCount")!=null||!dayRegister.get("insertCount").equals("")){
                Integer insertCnt=Integer.parseInt(dayRegister.get("insertCount").toString());
                dayRegister.put("insertCount", insertCnt);
                if(dayRegister.get("detectCount")!=null||!dayRegister.get("detectCount").equals("")){
                    Integer detectCnt=Integer.parseInt(dayRegister.get("detectCount").toString());
                    if(insertCnt==0||detectCnt==0||detectCnt>insertCnt){
                        dayRegister.put("manualCount", 0);
                    }else{
                        dayRegister.put("manualCount", insertCnt- detectCnt);
                    }
                }else{
                    dayRegister.put("manualCount", 0);
                }
            }else{
                dayRegister.put("insertCount", 0);
                dayRegister.put("manualCount", 0);
            }

            String problemDesc = "";
            dayRegister.put("faultCondition", "无");
            List<DayProblem> dayProblemList=dayProblemBiz.getDayProblemByRegisterId(dayRegister.get("id").toString());
            for (DayProblem p : dayProblemList){
                if(p.getIsProb()==1 || p.getProblemTypeId() == 11
                        || p.getProblemTypeId() == 5){
                    int proType=p.getProblemTypeId();
                    switch(proType){
                        case 2:
                            if(p.getProblemNote()!=null||p.getProblemNote()!=""){
                                dayRegister.replace("faultCondition", p.getProblemNote());
                            }else{
                                dayRegister.replace("faultCondition", "无");
                            }

                            break;
                        default:
                            if(DataUtil.hasValue(p.getProblemNote())==true){
                                problemDesc+=p.getProblemNote()+";";
                            }
                            break;
                    }
                }
            }
            if(problemDesc==""){
                dayRegister.put("otherProblems", "无");
            }else{
                dayRegister.put("otherProblems", problemDesc.substring(0,problemDesc.length()-1));
            }

            String inspectionType=dayRegister.get("inspectionType").toString();
            if(inspectionType.equals("0")){
                dayRegister.replace("inspectionType","对抗性检查");
            }else if(inspectionType.equals("1")){
                dayRegister.replace("inspectionType","明查");
            }else if(inspectionType.equals("2")){
                dayRegister.replace("inspectionType","暗访");
            }else{
                dayRegister.replace("inspectionType","");
            }


        }
        return list;

    }

        /**
         * 问题率
         * @param departIds
         * @param startTime
         * @param endTime
         * @return
         */
    public double getProblemRate(List<String> departIds,List<String> pointIds, String startTime, String endTime){
        Double problemRate=0.00;

        List<Map<String,String>> personControlList=ajyBiz.getPersonInfoByDepartId(departIds,"","","");
        int totalNumber=personControlList.size();
        List<DayProblem> dayProblemList=dayProblemBiz.getDayProblemByPointId(pointIds,startTime,endTime);
        int countProblem=dayProblemList.size();
        if(countProblem==0){
            problemRate=0.00;
        }else{
            problemRate=DataUtil.getPercentValue(countProblem,totalNumber);
        }
        return problemRate;
    }

    public double getProblemRateByDepart(List<String> departIds,String startTime, String endTime){
        Double problemRate=0.00;

        List<Map<String,String>> personControlList=ajyBiz.getPersonInfoByDepartId(departIds,"","","");
        int totalNumber=personControlList.size();
        List<DayProblem> dayProblemList=dayProblemBiz.getDayProblemByDepartId(departIds,startTime,endTime);
        int countProblem=dayProblemList.size();
        if(countProblem==0){
            problemRate=0.00;
        }else{
            problemRate=DataUtil.getPercentValue(countProblem,totalNumber);
        }
        return problemRate;
    }

    /**
     * 查获率
     * @param points
     * @param startTime
     * @param endTime
     * @return
     */
    public double getSeizedRate(List<String> points,String startTime,String endTime){
        int count=packageBiz.getPackageByPointId(points,startTime, endTime);
        int sum= pointDangerBiz.getPointDangerCountByPointId(points,startTime, endTime);
        if(count==0){
            return 0.00;
        }
        double d=(sum*1.0) / (count*1.0) * 100;
//                (double)sum/((double)count/1000000);
        DecimalFormat df = new DecimalFormat("#.00");
        //可以设置精确几位小数
        df.setMaximumFractionDigits(2);
        //模式 例如四舍五入
        df.setRoundingMode(RoundingMode.HALF_UP);
        return Double.parseDouble(df.format(d));
    }

    public double getSeizedRateByDepart(List<String> departIds,String startTime,String endTime){
        int count=packageBiz.getPackageByDepartId(departIds,startTime, endTime);
        int sum= pointDangerBiz.getPointDangerCountByDepartId(departIds,startTime, endTime);
        if(count==0){
            return 0.00;
        }
        double d=(sum*1.0) / (count*1.0) * 100;
//                (double)sum/((double)count/1000000);
        DecimalFormat df = new DecimalFormat("#.00");
        //可以设置精确几位小数
        df.setMaximumFractionDigits(2);
        //模式 例如四舍五入
        df.setRoundingMode(RoundingMode.HALF_UP);
        return Double.parseDouble(df.format(d));
    }

    /**
     * 漏检率
     * @param points
     * @param startTime
     * @param endTime
     * @return
     */
    public double getUndetectedRate(List<String> points,String startTime,String endTime){
//        Map<String,BigDecimal> m1=autoTipsBiz.getAutoTipsCountByPointIds(points,startTime, endTime);
        Map<String,BigDecimal> m2=mapper.getTipsCountByPointIds(points,startTime, endTime);

//        int insertCount1=m1.get("insertCount").intValue();
//        int detectCount1=m1.get("detectCount").intValue();
        int insertCount2=m2.get("insertCount").intValue();
        int detectCount2=m2.get("detectCount").intValue();
//        int undetectedCnt = insertCount1+insertCount2 - (detectCount1+detectCount2);
        int undetectedCnt = insertCount2 - detectCount2;
        if (undetectedCnt < 0){
            undetectedCnt = 0;
        }
//        return DataUtil.getPercentValue(undetectedCnt,insertCount1+insertCount2);
        return DataUtil.getPercentValue(undetectedCnt,insertCount2);
    }

    public double getUndetectedRateByDepart(List<String> departIds,String startTime,String endTime){
        Map<String,BigDecimal> m2=mapper.getTipsCountByDepartIds(departIds,startTime, endTime);

        int insertCount2=m2.get("insertCount").intValue();
        int detectCount2=m2.get("detectCount").intValue();
        int undetectedCnt = insertCount2 - detectCount2;
        if (undetectedCnt < 0){
            undetectedCnt = 0;
        }
        return DataUtil.getPercentValue(undetectedCnt,insertCount2);
    }

    /**
     * 在岗率
     * @param points
     * @param startTime
     * @param endTime
     * @return
     */
    public double getOnJobRate(List<String> points,String startTime,String endTime){
        Map<String,BigDecimal> m=mapper.getPersonCountByPointIds(points,startTime, endTime);
        int planCount=m.get("planCount").intValue();
        int realCount=m.get("realCount").intValue();
        if(planCount==0){
            return 0.00;
        }
        return DataUtil.getPercentValue(realCount, planCount);
    }

    public double getOnJobRateByDepart(List<String> departIds,String startTime,String endTime){
        Map<String,BigDecimal> m=mapper.getPersonCountByDepartIds(departIds,startTime, endTime);
        int planCount=m.get("planCount").intValue();
        int realCount=m.get("realCount").intValue();
        if(planCount==0){
            return 0.00;
        }
        return DataUtil.getPercentValue(realCount, planCount);
    }

    /**
     * 持证率
     * @param points
     * @param startTime
     * @param endTime
     * @return
     */
    public double getCertifiedRate(List<String> points,String startTime,String endTime){
        int cardCount=0;
        int realCount=0;
        Map<String,BigDecimal> m=mapper.getCardCountByPointIds(points,startTime, endTime);
        if(m.size()>0){
            cardCount=m.get("cardCount").intValue();
            realCount=m.get("realCount").intValue();
        }

        if(realCount==0){
            return 0.00;
        }
        return DataUtil.getPercentValue(cardCount, realCount);
    }

    public double getCertifiedRateByDepart(List<String> departIds,String startTime,String endTime){
        int cardCount=0;
        int realCount=0;
        Map<String,BigDecimal> m=mapper.getCardCountByDepartIds(departIds,startTime, endTime);
        if(m.size()>0){
            cardCount=m.get("cardCount").intValue();
            realCount=m.get("realCount").intValue();
        }

        if(realCount==0){
            return 0.00;
        }
        return DataUtil.getPercentValue(cardCount, realCount);
    }

    public List<Map<String,Object>> getStatisticList(List<String> departIds, Date startTime, Date endTime,String type){
        List<Map<String,Object>> rst = new ArrayList();
        if(departIds.size()>0){
            for(String departId:departIds){
                List<String> points=checkPointCtr.getAllPointByDepart(departId);
                if(points.size()<=0){
                    points.add("");
                }
                Map<String,Object> map=new HashMap<>();
                if(type.equals("prob")){
                    map.put("rate",getProblemRate(departIds,points, DateUtil.date2Str(startTime,"yyyy-MM-dd"),DateUtil.date2Str(endTime,"yyyy-MM-dd")));
                }
                if ( type.equals("job") ){
                    map.put("rate",getOnJobRate(points, DateUtil.date2Str(startTime,"yyyy-MM-dd"),DateUtil.date2Str(endTime,"yyyy-MM-dd")));
                }
                if ( type.equals("seized") ){
                    map.put("rate",getSeizedRate(points, DateUtil.date2Str(startTime,"yyyy-MM-dd"),DateUtil.date2Str(endTime,"yyyy-MM-dd")));
                }
                if ( type.equals("undetected") ){
                    map.put("rate",getUndetectedRate(points, DateUtil.date2Str(startTime,"yyyy-MM-dd"),DateUtil.date2Str(endTime,"yyyy-MM-dd")));

                }
                if ( type.equals("certified") ){
                    map.put("rate",getCertifiedRate(points, DateUtil.date2Str(startTime,"yyyy-MM-dd"),DateUtil.date2Str(endTime,"yyyy-MM-dd")));
                }
                map.put("departId",departId);
                map.put("departName",departCtr.getStationDetail(departId).get("name"));

                rst.add(map);
            }

        }

        return rst;
    }

    public int getId(){
        return mapper.getId();
    }
}
