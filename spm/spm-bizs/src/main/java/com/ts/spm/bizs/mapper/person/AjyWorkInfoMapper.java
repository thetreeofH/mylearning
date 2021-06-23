package com.ts.spm.bizs.mapper.person;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.person.AjyWorkInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AjyWorkInfoMapper extends CommonMapper<AjyWorkInfo> {
    List<Map<String,String>> getPersonWorkList(@Param("departIds") List<String> departIds, @Param("name") String name, @Param("idCard") String idCard,
                                               @Param("ifCard") String ifCard, @Param("ifYoung") String ifYoung,@Param("startTime") String startTime,
                                               @Param("endTime") String endTime,@Param("ifDay") String ifDay,@Param("workType") String workType,@Param("ajyType") String ajyType);
    List<Map<String,Object>> getPersonWorkStatis(@Param("departIds") List<String> departIds, @Param("startTime") String startTime,
                                               @Param("endTime") String endTime);

    List<Map<String,Object>> getPersonWorkTime(@Param("departIds") List<String> departIds, @Param("startTime") String startTime,
                                               @Param("endTime") String endTime,@Param("ifDay") String ifDay);

    List<Map<String,Object>> getPersonByIdoTime(@Param("ajyIdno") String ajyIdno,@Param("time") String time);

    List<Map<String,Object>> getOnWorkData(@Param("departIds") List<String> departIds, @Param("startTime") String startTime, @Param("endTime") String endTime);

    Map<String,Object> workcfg();
}