package com.ts.spm.bizs.biz.jzpoip;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.jzpoip.TblOpenInspectionInfo;
import com.ts.spm.bizs.mapper.jzpoip.TblOpenInspectionInfoMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName TblOpenInspectionInfo
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/7/22 15:33
 * @Version 1.0
 **/
@Service
public class TblOpenInspectionInfoBiz extends BusinessBiz<TblOpenInspectionInfoMapper, TblOpenInspectionInfo> {
    public List<Map<String,String>> query(String startTime,String endTime,Integer confirmForbiddenType,Integer confirmForbiddenSubtype,Integer ifContraband,Integer ifCheck,List<String> pointIds,String pointId,Integer handleResult){
        return mapper.query(startTime,endTime,confirmForbiddenType,confirmForbiddenSubtype,ifContraband,ifCheck,pointIds,pointId,handleResult);
    }

    public List<Map<String,String>> pointQuery(String startTime,String endTime,Integer confirmForbiddenType,String pointId){
        return mapper.pointQuery(startTime,endTime,confirmForbiddenType,pointId);
    }

    public List<Map<String,String>> statistics(String startTime,String endTime,Integer confirmForbiddenType,Integer confirmForbiddenSubtype,String openInspectionName,String dataType,List<String> pointIds,String pointId) {
        if ("day".equals(dataType)) {
            return mapper.statisticsByDay(startTime, endTime, confirmForbiddenType, confirmForbiddenSubtype, openInspectionName, pointIds, pointId);
        } else if ("week".equals(dataType)) {
            return mapper.statisticsByWeek(startTime, endTime, confirmForbiddenType, confirmForbiddenSubtype, openInspectionName, pointIds, pointId);
        } else if ("month".equals(dataType)) {
            return mapper.statisticsByMonth(startTime, endTime, confirmForbiddenType, confirmForbiddenSubtype, openInspectionName, pointIds, pointId);
        } else{
            return mapper.statisticsByYear(startTime, endTime, confirmForbiddenType, confirmForbiddenSubtype, openInspectionName, pointIds, pointId);
        }

    }

    public Map<String,String> todayStatistics(String day,String today,String userId){
        return mapper.toDayStatistics(day,today,userId);
    }

    public List<Map<String,String>> statisByPoint(String pointId,String startTime,String endTime){
        return mapper.statisByPoint(pointId,startTime,endTime);
    }

    public List<Map<String,String>> statisticsByPointList(List<String> pointIds,String startTime,String endTime){
        return mapper.statisticsByPointList(pointIds,startTime,endTime);
    }
    public List<Map<String,String>> statisByDepart(List<String> pointIds,String startTime,String endTime){
        return mapper.statisByDepart(pointIds,startTime,endTime);
    }

    public List<Map<String,String>> statisByType(List<String> pointId,String startTime,String endTime){
        return mapper.statisByType(pointId,startTime,endTime);
    }
    public Map selectByUserId(String handleUserId){
        return mapper.selectByUserId(handleUserId);
    }

    public Map<String,String> selectByOipId(String id){
        return mapper.selectByOipId(id);
    }

    public Map<String,String> selectById(String id){
        return mapper.selectById(id);
    }
}
