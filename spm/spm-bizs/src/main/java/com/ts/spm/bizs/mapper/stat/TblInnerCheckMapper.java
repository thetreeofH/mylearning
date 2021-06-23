package com.ts.spm.bizs.mapper.stat;
/**
 * updater 马居朝
 *  public List<Map<String, String>> statisByDepart(@Param("pointIds") List<String> pointIds, @Param("startTime") String startTime, @Param("endTime") String endTime);
 *  改为
 *  public List<Map<String, String>> statisByDepart(@Param("departIds") List<String> departIds, @Param("startTime") String startTime, @Param("endTime") String endTime);
 * **/
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.stat.TblInnerCheck;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

public interface TblInnerCheckMapper extends CommonMapper<TblInnerCheck> {

    public Integer addInnerCheck(@Param("tblInnerCheck") TblInnerCheck tblInnerCheck);

    public List<Map<String, Object>> statisByType(@Param("pointIds") List<String> pointIds, @Param("startTime") String startTime, @Param("endTime") String endTime);

    public List<Map<String, String>> statisByDepart(@Param("pointIds") List<String> pointIds, @Param("startTime") String startTime, @Param("endTime") String endTime);

    public List<Map<String, String>> statisByPoint(@Param("pointIds") List<String> pointIds, @Param("startTime") String startTime, @Param("endTime") String endTime);


    int countByPoint(@Param("pointIds") List<String> pointIds, @Param("startTime") String startTime, @Param("endTime") String endTime);


    public int getId();

}