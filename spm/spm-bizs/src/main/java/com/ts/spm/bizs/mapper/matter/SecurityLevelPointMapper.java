package com.ts.spm.bizs.mapper.matter;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.matter.SecurityLevelPoint;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SecurityLevelPointMapper extends CommonMapper<SecurityLevelPoint> {
    List<SecurityLevelPoint> selectListByLevel(@Param("level") String level, @Param("pointIds") List<String> pointIds);
}