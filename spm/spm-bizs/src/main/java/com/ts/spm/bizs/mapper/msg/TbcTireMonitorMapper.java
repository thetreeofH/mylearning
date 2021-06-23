package com.ts.spm.bizs.mapper.msg;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.msg.TbcTireMonitor;
import feign.Param;

import java.util.List;
import java.util.Map;

public interface TbcTireMonitorMapper extends CommonMapper<TbcTireMonitor> {
    Map<String,Object> selectById(@Param("id") Integer id);
    Map<String,Object> selectByPointsId(@Param("pointIds") List<String> pointIds);
}