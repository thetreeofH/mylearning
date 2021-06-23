package com.ts.spm.bizs.mapper.jzpmq;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.jzpmq.DealResultInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DealResultInfoMapper extends CommonMapper<DealResultInfo> {
    DealResultInfo selectByMissionId(@Param("missionId") String missionId);

    Map<String,String> selectDealResult(@Param("missionId") String missionId);
    List<Map<String,String>> query(Map<String,Object> map);
    Integer queryTotal(Map<String,Object> map);
//    List<Map<String,String>> query(@Param("startTime") String startTime,
//                                   @Param("endTime") String endTime,
//                                   @Param("judgePictureSource") Integer judgePictureSource,
//                                   @Param("handleResult") Integer handleResult,
//                                   @Param("suspectedForbiddenType") Integer suspectedForbiddenType,
//                                   @Param("pointIds") List<String> pointIds,
//                                   @Param("pointId") String pointId,
//                                   @Param("ifCheck") Integer ifCheck,
//                                   @Param("suspectedForbiddenSubtype") Integer suspectedForbiddenSubtype);
    Map<String,String> toDayStatistics(@Param("day") String day, @Param("today") String today, @Param("userId") String userId);
    List<Map<String,String>> todayContraband(@Param("pointIds") List<String> pointIds);
    Map<String,String> todayContrabandSum(@Param("pointIds") List<String> pointIds);
}