package com.ts.spm.bizs.mapper.admin;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.admin.UploadApp;

import java.util.List;

public interface UploadAppMapper extends CommonMapper<UploadApp> {
    int getId();

    List<UploadApp> getLastApp();
}