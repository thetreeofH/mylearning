package com.ts.spm.bizs.mapper.person;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.person.TireMonitor;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

public interface TireMonitorMapper extends CommonMapper<TireMonitor> {
    List<Map<String,String>> getTireMonitorList(@Param("pointIds") List<String> pointIds, @Param("startTime") String startTime, @Param("endTime") String endTime);

    Map<String,String> getTireMonitorAll(@Param("pointIds") List<String> pointIds, @Param("startTime") String startTime, @Param("endTime") String endTime);

    List<Map<String,String>> getTireMonitorByDay(@Param("pointIds") List<String> pointIds, @Param("startTime") String startTime, @Param("endTime") String endTime);

    List<Map<String,String>> getTireMonitorByHour(@Param("pointIds") List<String> pointIds, @Param("startTime") String startTime, @Param("endTime") String endTime);


    List<Map<String,String>> getTireMonitorByPoint(@Param("pointIds") List<String> pointIds, @Param("startTime") String startTime, @Param("endTime") String endTime);

    List<Map<String,String>> getTireMonitorByDepart(@Param("pointIds") List<String> pointIds, @Param("startTime") String startTime, @Param("endTime") String endTime);

    List<TireMonitor> getTireMonitorDetail(@Param("time") String time,@Param("hitType") String hitType,@Param("pointIds") List<String> pointIds);

}