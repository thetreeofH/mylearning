package com.ts.spm.bizs.mapper.stat;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.stat.DayProblem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DayProblemMapper extends CommonMapper<DayProblem> {
    List<DayProblem> getProblemByDepartIds(@Param("departs") List<String> departs, @Param("startTime") String startTime, @Param("endTime") String endTime);

    public int getId();
}