package com.ts.spm.bizs.biz.up;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.up.ThermalInfo;
import com.ts.spm.bizs.mapper.up.ThermalMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhoukun on 2021/4/28.
 */
@Service
public class ThermalBiz extends BusinessBiz<ThermalMapper, ThermalInfo> {
    public List<ThermalInfo> getLastInfo() {
        return mapper.getLastInfo();
    }
    public int updatetime() {
        return mapper.updatetime();
    }
}
