package com.ts.spm.bizs.biz.admin;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.admin.UploadApp;
import com.ts.spm.bizs.mapper.admin.UploadAppMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author luoyu
 * @Date 2020/11/3 16:23
 * @Version 1.0
 */
@Service
public class UploadAppBiz extends BusinessBiz<UploadAppMapper, UploadApp> {

    public int getId(){
        return mapper.getId();
    }

    public List<UploadApp> getLastApp(){
        return mapper.getLastApp();
    }
}
