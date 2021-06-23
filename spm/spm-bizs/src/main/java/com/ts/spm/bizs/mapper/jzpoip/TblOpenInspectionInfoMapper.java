package com.ts.spm.bizs.mapper.jzpoip;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.jzpoip.TblOpenInspectionInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TblOpenInspectionInfoMapper extends CommonMapper<TblOpenInspectionInfo> {
    List<Map<String,String>> query(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("confirmForbiddenType") Integer confirmForbiddenType, @Param("confirmForbiddenSubtype") Integer confirmForbiddenSubtype, @Param("ifContraband") Integer ifContraband, @Param("ifCheck") Integer ifCheck, @Param("pointIds") List<String> pointIds, @Param("pointId") String pointId, @Param("handleResult") Integer handleResult);

    List<Map<String,String>> pointQuery(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("confirmForbiddenType") Integer confirmForbiddenType, @Param("pointId") String pointId);

    List<Map<String,String>> statisticsByDay(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("confirmForbiddenType") Integer confirmForbiddenType, @Param("confirmForbiddenSubtype") Integer confirmForbiddenSubtype, @Param("openInspectionName") String openInspectionName, @Param("pointIds") List<String> pointIds, @Param("pointId") String pointId);
    List<Map<String,String>> statisticsByWeek(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("confirmForbiddenType") Integer confirmForbiddenType, @Param("confirmForbiddenSubtype") Integer confirmForbiddenSubtype, @Param("openInspectionName") String openInspectionName, @Param("pointIds") List<String> pointIds, @Param("pointId") String pointId);
    List<Map<String,String>> statisticsByMonth(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("confirmForbiddenType") Integer confirmForbiddenType, @Param("confirmForbiddenSubtype") Integer confirmForbiddenSubtype, @Param("openInspectionName") String openInspectionName, @Param("pointIds") List<String> pointIds, @Param("pointId") String pointId);
    List<Map<String,String>> statisticsByYear(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("confirmForbiddenType") Integer confirmForbiddenType, @Param("confirmForbiddenSubtype") Integer confirmForbiddenSubtype, @Param("openInspectionName") String openInspectionName, @Param("pointIds") List<String> pointIds, @Param("pointId") String pointId);
    Map<String,String> toDayStatistics(@Param("day") String day, @Param("today") String today, @Param("userId") String userId);

    List<Map<String,String>> statisByPoint(@Param("pointId") String pointId, @Param("startTime") String startTime, @Param("endTime") String endTime);
    List<Map<String,String>> statisticsByPointList(@Param("pointIds") List<String> pointIds, @Param("startTime") String startTime, @Param("endTime") String endTime);
    List<Map<String,String>> statisByDepart(@Param("pointIds") List<String> pointIds, @Param("startTime") String startTime, @Param("endTime") String endTime);
    List<Map<String,String>> statisByType(@Param("pointIds") List<String> pointIds, @Param("startTime") String startTime, @Param("endTime") String endTime);
    Map selectByUserId(@Param("handleUserId") String handleUserId);
    Map<String,String>selectByOipId(@Param("id") String id);

    Map<String,String> selectById(@Param("id") String id);
}