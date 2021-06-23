package com.ts.spm.bizs.mapper.jzpitgcfg;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.jzpitgcfg.TblNormalConfInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface TblNormalConfInfoMapper extends CommonMapper<TblNormalConfInfo> {
    Map<String,String> selectById(@Param("pId") String pId);
    Map<String,Object> selectMaxCreateTime(@Param("pointId") String pointId, @Param("tag") Integer tag);
}