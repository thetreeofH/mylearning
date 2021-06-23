package com.ts.spm.bizs.mapper.person;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.person.PersonBlack;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.prefs.AbstractPreferences;

public interface PersonBlackMapper extends CommonMapper<PersonBlack> {
    public Integer addPersonBlack(@Param("personBlack") PersonBlack personBlack);

    public void deletePersonBlack(@Param("idCard") String idCard);
}