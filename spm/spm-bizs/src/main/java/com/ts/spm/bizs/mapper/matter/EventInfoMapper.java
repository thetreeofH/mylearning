package com.ts.spm.bizs.mapper.matter;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.matter.EventInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface EventInfoMapper extends CommonMapper<EventInfo> {
    List<Map<String, Object>> statis1(@Param("pointIds") List<String> pointIds, @Param("start")Date start, @Param("end")Date end, @Param("type")int type);
    List<Map<String, Object>> statis2(@Param("pointIds") List<String> pointIds, @Param("start")Date start, @Param("end")Date end, @Param("type")int type);

    List<Map<String, Object>> chartSite1(@Param("pointIds") List<String> pointIds, @Param("start")Date start, @Param("end")Date end, @Param("type")int type);
    List<Map<String, Object>> chartSite2(@Param("pointIds") List<String> pointIds, @Param("start")Date start, @Param("end")Date end, @Param("type")int type);

    List<Map<String, Object>> chartDanger(@Param("pointIds") List<String> pointIds, @Param("start")Date start, @Param("end")Date end, @Param("type")int type);

    List<EventInfo> selectByParams(@Param("type")int type, @Param("dealType")String dealType, @Param("start")Date start, @Param("end")Date end, @Param("pointIds")List<String> pointIds,@Param("dealResult")int dealResult);

    EventInfo selectObjectById(String id);

    Integer getId();

    List<EventInfo> selectAlarm(@Param("pointIds")List<String> pointIds);

    int selectCountFromAlarm(@Param("start") Date start, @Param("end")Date end, @Param("pointIds")List<String> pointIds);
}