package com.ts.spm.bizs.mapper.jzpmq;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.jzpmq.TblVideoOnDutyCheck;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TblVideoOnDutyCheckMapper extends CommonMapper<TblVideoOnDutyCheck> {
    List<TblVideoOnDutyCheck> query(@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("JudgePictureUserName") String JudgePictureUserName);
}
