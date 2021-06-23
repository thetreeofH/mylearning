package com.ts.spm.bizs.mapper.jzpitgcfg;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.jzpitgcfg.TblTimePeriodCfg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TblTimePeriodCfgMapper extends CommonMapper<TblTimePeriodCfg> {
    List<TblTimePeriodCfg> query(@Param("pointIds") List<String> pointIds, @Param("pointId") String pointId);
}