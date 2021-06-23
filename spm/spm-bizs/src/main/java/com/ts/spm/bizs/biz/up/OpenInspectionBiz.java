package com.ts.spm.bizs.biz.up;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.up.OpenInspection;
import com.ts.spm.bizs.mapper.up.OpenInspectionMapper;
import org.springframework.stereotype.Service;

/**
 * Created by zhoukun on 2021/4/28.
 */
@Service
public class OpenInspectionBiz extends BusinessBiz<OpenInspectionMapper, OpenInspection> {
    public OpenInspection GetOpenInspection(String id) {
        return mapper.GetOpenInspection(id);
    }
}