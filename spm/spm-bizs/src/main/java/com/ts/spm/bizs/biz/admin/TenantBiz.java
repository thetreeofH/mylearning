package com.ts.spm.bizs.biz.admin;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.admin.Tenant;
import com.ts.spm.bizs.entity.admin.User;
import com.ts.spm.bizs.mapper.admin.TenantMapper;
import com.ts.spm.bizs.mapper.admin.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 租户表
 *
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-02-08 21:42:09
 */
@Service
public class TenantBiz extends BusinessBiz<TenantMapper, Tenant> {
    @Autowired
    private UserMapper userMapper;

    public void updateUser(String id, String userId) {
        Tenant tenant = this.mapper.selectByPrimaryKey(id);
        tenant.setOwner(userId);
        updateSelectiveById(tenant);
        User user = userMapper.selectByPrimaryKey(userId);
        user.setTenantId(id);
        userMapper.updateByPrimaryKeySelective(user);
    }
}