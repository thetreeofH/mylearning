package com.ts.spm.bizs.mapper.jzparrange;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ts.spm.bizs.entity.jzparrange.TblDutyPlan;
import org.apache.ibatis.annotations.Param;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;

public interface TblDutyPlanMapper extends CommonMapper<TblDutyPlan> {

	List<Map> selectDateStatus(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

	void delectDate(@Param("dateList") ArrayList<String> dateList);

	List<Map> selectInfo(@Param("beginTime") String beginTime, @Param("endTime") String endTime,
                         @Param("userName") String userName, @Param("userType") String userType);

	void delect(@Param("id") String id);
}