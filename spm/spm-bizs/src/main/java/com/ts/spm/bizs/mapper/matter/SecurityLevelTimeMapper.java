package com.ts.spm.bizs.mapper.matter;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.matter.SecurityLevelTime;
import org.apache.ibatis.annotations.Param;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.List;
import java.util.Map;

public interface SecurityLevelTimeMapper extends CommonMapper<SecurityLevelTime> {
    List<SecurityLevelTime> selectCurrentTime(@Param("time") String time, @Param("levelCodes") List<String> levelCodes);
    //List<SecurityLevelTime> selectCurrentTime(SecurityLevelTime securityLevelTime);
}