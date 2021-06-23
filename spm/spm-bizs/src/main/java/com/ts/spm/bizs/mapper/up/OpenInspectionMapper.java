package com.ts.spm.bizs.mapper.up;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.up.OpenInspection;

/**
 * Created by zhoukun on 2021/4/28.
 */
public interface OpenInspectionMapper extends CommonMapper<OpenInspection> {
    OpenInspection GetOpenInspection(String id);
}