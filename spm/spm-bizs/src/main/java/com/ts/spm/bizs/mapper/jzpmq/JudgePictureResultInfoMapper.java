package com.ts.spm.bizs.mapper.jzpmq;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.jzpmq.JudgePictureResultInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface JudgePictureResultInfoMapper extends CommonMapper<JudgePictureResultInfo> {
    List<Map<String, String>> query(@Param("startTime") String startTime, @Param("endTime") String endTime,
                                    @Param("judgePictureName") String judgePictureName,
                                    @Param("suspectedForbiddenType") Integer suspectedForbiddenType,
                                    @Param("pointIds") List<String> pointIds,
                                    @Param("pointId") String pointId);
    JudgePictureResultInfo selectByMissionId(@Param("missionId") String missionId);
}