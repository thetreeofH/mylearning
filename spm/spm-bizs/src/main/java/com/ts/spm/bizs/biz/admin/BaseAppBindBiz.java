package com.ts.spm.bizs.biz.admin;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.admin.AppBind;
import com.ts.spm.bizs.mapper.admin.AppBindMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseAppBindBiz extends BusinessBiz<AppBindMapper, AppBind> {
    public List<AppBind> getpage(String username){
        return this.mapper.getpage(username);
    }
}
