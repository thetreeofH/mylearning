package com.ts.spm.bizs.mapper.stat;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.stat.AutoTipsCount;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AutoTipsCountMapper extends CommonMapper<AutoTipsCount> {
    Map<String, BigDecimal> getAutoTipsCountByPointIds(@Param("points") List<String> points, @Param("startTime") String startTime, @Param("endTime") String endTime);
}