package com.ts.spm.bizs.mapper.equ;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.equ.SecurityDeviceRepair;
import com.ts.spm.bizs.vo.equ.RepairVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SecurityDeviceRepairMapper extends CommonMapper<SecurityDeviceRepair> {
    List<RepairVo> selectGroup(@Param("start") Date start, @Param("end") Date end, @Param("type") String type, @Param("pointIds")List<String> pointIds);
}