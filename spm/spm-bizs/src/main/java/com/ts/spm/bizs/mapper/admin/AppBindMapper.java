package com.ts.spm.bizs.mapper.admin;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.admin.AppBind;

import java.util.List;

public interface AppBindMapper extends CommonMapper<AppBind> {
    public List<AppBind> getpage(String username);
}