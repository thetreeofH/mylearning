package com.ts.spm.bizs.biz.up;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.up.SecurityDeviceInfo;
import com.ts.spm.bizs.mapper.up.SecurityDeviceInfoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhoukun on 2021/4/16.
 */
@Service
public class SecurityDeviceInfoBiz extends BusinessBiz<SecurityDeviceInfoMapper, SecurityDeviceInfo> {
    public List<SecurityDeviceInfo> selectList(String pointId) {
        return mapper.selectList(pointId);
    }
}
