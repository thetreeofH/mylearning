package com.ts.spm.bizs.mapper.stat;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.stat.PackageCount;
import com.ts.spm.bizs.vo.stat.StatDayCntVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PackageCountMapper extends CommonMapper<PackageCount> {
    public int getPackageCountByPointIds(@Param("pointIds") List<String> pointIds,@Param("startTime") String startTime,@Param("endTime") String endTime);

    public int getPackageCountByDepartIds(@Param("departIds") List<String> departIds,@Param("startTime") String startTime,@Param("endTime") String endTime);


    List<StatDayCntVo> statis(@Param("pointIds") List<String> pointIds, @Param("start") Date start, @Param("end") Date end);

    List<Map<String, String>> statisByHour(@Param("pointIds") List<String> pointIds,@Param("startTime") String startTime,@Param("endTime") String endTime);

    List<Map<String, String>> statisByDay(@Param("pointIds") List<String> pointIds,@Param("startTime") String startTime,@Param("endTime") String endTime);

    List<Map<String, String>> statisByDaysHour(@Param("pointIds") List<String> pointIds,@Param("startTime") String startTime,@Param("endTime") String endTime);

    List<Map<String, String>> statisByDepart(@Param("pointIds") List<String> pointIds,@Param("startTime") String startTime,@Param("endTime") String endTime);

    List<Map<String, String>> statisByPoint(@Param("pointIds") List<String> pointIds,@Param("startTime") String startTime,@Param("endTime") String endTime);

    List<Map> getPackageList(@Param("pointIds") List<String> pointIds,@Param("startTime") String startTime,@Param("endTime") String endTime);


    Integer statis2(@Param("pointIds") List<String> pointIds,@Param("start") Date start, @Param("end") Date end);

}