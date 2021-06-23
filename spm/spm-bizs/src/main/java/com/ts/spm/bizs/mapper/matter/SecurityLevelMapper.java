package com.ts.spm.bizs.mapper.matter;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.matter.SecurityLevel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SecurityLevelMapper extends CommonMapper<SecurityLevel> {
    List<SecurityLevel> selectListByParam(@Param("name") String name, @Param("deptIds") List<String> deptIds);

    List<SecurityLevel> selectCurrentDate(@Param("str") String str);
}