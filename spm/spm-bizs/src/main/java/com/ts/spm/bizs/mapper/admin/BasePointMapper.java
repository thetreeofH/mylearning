package com.ts.spm.bizs.mapper.admin;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.admin.BasePoint;
import com.ts.spm.bizs.vo.admin.PointVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BasePointMapper extends CommonMapper<BasePoint> {
    List<Map<String, String>> selectByDepartid(BasePoint o);

    List<Map<String, Object>> selectByDepartids(@Param("name") String name, @Param("entry") String entry, @Param("status") String status, @Param("stationIds") List<String> stationIds);

    PointVo selectObjectById(String id);

    List<Map<String, String>> getAllPointByDepart(@Param("userId") String userId, @Param("departId") String departId);

}