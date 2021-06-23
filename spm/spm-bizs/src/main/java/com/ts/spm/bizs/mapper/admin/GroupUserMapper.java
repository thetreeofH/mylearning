package com.ts.spm.bizs.mapper.admin;

import com.github.wxiaoqi.security.common.data.Tenant;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.admin.GroupUser;
import org.apache.ibatis.annotations.Param;

@Tenant
public interface GroupUserMapper extends CommonMapper<GroupUser> {

    public void addGroupUser(@Param("groupUser") GroupUser groupUser);

    public void updateGroupUser(@Param("groupUser") GroupUser groupUser, @Param("userId") String userId);

    public void delGroupUser(@Param("userId") String userId);
}