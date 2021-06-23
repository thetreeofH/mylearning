package com.ts.spm.bizs.mapper.jzpmq;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.jzpmq.TblTestMission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TblTestMissionMapper extends CommonMapper<TblTestMission> {
    List<Map<String,String>> statistics(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("judgePictureUserName") String judgePictureUserName, @Param("ifDistinguish") Integer ifDistinguish);
}