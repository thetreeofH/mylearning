package com.ts.spm.bizs.mapper.gis;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.gis.DefenceDev;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DefenceDevMapper extends CommonMapper<DefenceDev> {
    List<DefenceDev> getDevInfo(@Param("stationId") String stationId);
}