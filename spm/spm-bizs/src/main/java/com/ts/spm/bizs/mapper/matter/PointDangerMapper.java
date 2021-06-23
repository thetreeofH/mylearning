package com.ts.spm.bizs.mapper.matter;
/**
 *updater 马居朝
 * 新增Map<String,String> selectById(@Param("id") String id);
 * **/

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.matter.PointDanger;
import com.ts.spm.bizs.vo.stat.StatTypeCntVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PointDangerMapper extends CommonMapper<PointDanger> {
    Integer getId();
    Integer getDno();

    PointDanger getById(Integer id);

    Map<String,String> selectById(@Param("id") String id);

    //stat use
    List<StatTypeCntVo> statis(@Param("start") Date start, @Param("end") Date end, @Param("pointIds") List<String> pointIds);
    List<Map<String,String>>  statisByHour(@Param("pointIds") List<String> pointIds, @Param("startTime") String startTime, @Param("endTime") String endTime);
    List<Map<String,String>>  statisByDay(@Param("pointIds") List<String> pointIds,@Param("startTime") String startTime,@Param("endTime") String endTime);
    List<Map<String,String>>  statisByPoint(@Param("pointIds") List<String> pointIds,@Param("startTime") String startTime,@Param("endTime") String endTime);
    List<Map<String,String>>  statisByDepart(@Param("pointIds") List<String> pointIds,@Param("startTime") String startTime,@Param("endTime") String endTime);
    List<Map<String,String>>  statisByType(@Param("pointIds") List<String> pointIds,@Param("startTime") String startTime,@Param("endTime") String endTime);

    List<Map> contrabandList(@Param("pointIds") List<String> pointIds,@Param("startTime") String startTime,@Param("endTime") String endTime,
                             @Param("mType") String mType,@Param("ajyName") String ajyName,@Param("dangerId") String dangerId,@Param("ajyType") String ajyType,
                             @Param("psgName") String psgName,@Param("psgIdno") String psgIdno,@Param("itemWay") Integer itemWay);
    int getPointDangerCountByPointId(@Param("pointIds") List<String> pointIds, @Param("startTime") String startTime, @Param("endTime") String endTime);

    int getPointDangerCountByDepartId(@Param("departIds") List<String> departIds, @Param("startTime") String startTime, @Param("endTime") String endTime);
}