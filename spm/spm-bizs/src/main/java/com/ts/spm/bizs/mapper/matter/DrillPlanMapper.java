package com.ts.spm.bizs.mapper.matter;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.matter.DrillPlan;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface DrillPlanMapper extends CommonMapper<DrillPlan> {
    List<DrillPlan> getList(@Param("title") String title, @Param("month") String month, @Param("start") Date st, @Param("end") Date et, @Param("departIds") List<String> departIds);
}