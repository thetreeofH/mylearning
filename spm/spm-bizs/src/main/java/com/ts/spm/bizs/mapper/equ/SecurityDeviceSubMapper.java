package com.ts.spm.bizs.mapper.equ;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.equ.SecurityDeviceSub;
import com.ts.spm.bizs.vo.equ.SecurityDeviceVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SecurityDeviceSubMapper extends CommonMapper<SecurityDeviceSub> {
    Integer getId();

    List<SecurityDeviceVo> selectCountByPoint(@Param("type") String type, @Param("pointIds") List<String> pointIds);

    List<SecurityDeviceSub> selectList(@Param("type") String type, @Param("pointIds") List<String> pointIds);

    SecurityDeviceSub getById(String id);

    List<SecurityDeviceSub> gis_selectList(@Param("stationId") String stationId);
}