package com.ts.spm.bizs.mapper.matter;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.matter.SecurityLevelPerson;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SecurityLevelPersonMapper extends CommonMapper<SecurityLevelPerson> {
    public List<SecurityLevelPerson> selectBySecrityLevelPerson(@Param("levelId") List<String> levelId,@Param("gateSum") Integer gateSum);
}