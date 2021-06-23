package com.ts.spm.bizs.mapper.admin;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.admin.UserDepart;
import org.apache.ibatis.annotations.Param;

public interface UserDepartMapper extends CommonMapper<UserDepart> {

    public int getDepartUsers(@Param("id") String id);

    public void delDepartUsers(@Param("userId") String userId);
}