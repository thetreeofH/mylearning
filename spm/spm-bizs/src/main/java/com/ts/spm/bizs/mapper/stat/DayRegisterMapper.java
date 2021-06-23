package com.ts.spm.bizs.mapper.stat;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.stat.DayRegister;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface DayRegisterMapper extends CommonMapper<DayRegister> {
//    List<DayRegister> getDayRegisterList(@Param("pointIds") List<String> pointIds, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("probType") String probType, @Param("checkOrg") String checkOrg, @Param("checker") String checker,@Param("checkType") String checkType,@Param("operatingCompanyId") String operatingCompanyId,@Param("securityCompanyId") String securityCompanyId);

    List<Map> getDayRegisterList(@Param("pointIds") List<String> pointIds, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("probType") String probType, @Param("checkOrg") String checkOrg, @Param("checker") String checker,@Param("checkType") String checkType,@Param("operatingCompanyId") String operatingCompanyId,@Param("securityCompanyId") String securityCompanyId);

    Map<String,BigDecimal> getTipsCountByPointIds(@Param("points") List<String> points, @Param("startTime") String startTime, @Param("endTime") String endTime);

    Map<String,BigDecimal> getPersonCountByPointIds(@Param("points") List<String> points, @Param("startTime") String startTime, @Param("endTime") String endTime);

    Map<String, BigDecimal> getCardCountByPointIds(@Param("points") List<String> points, @Param("startTime") String startTime, @Param("endTime") String endTime);

    Map<String,BigDecimal> getTipsCountByDepartIds(@Param("departs") List<String> departs, @Param("startTime") String startTime, @Param("endTime") String endTime);

    Map<String,BigDecimal> getPersonCountByDepartIds(@Param("departs") List<String> departs, @Param("startTime") String startTime, @Param("endTime") String endTime);

    Map<String, BigDecimal> getCardCountByDepartIds(@Param("departs") List<String> departs, @Param("startTime") String startTime, @Param("endTime") String endTime);

    public int getId();
}