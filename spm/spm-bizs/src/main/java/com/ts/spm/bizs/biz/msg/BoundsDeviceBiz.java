package com.ts.spm.bizs.biz.msg;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.msg.BoundsDevice;
import com.ts.spm.bizs.mapper.msg.BoundsDeviceMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoundsDeviceBiz extends BusinessBiz<BoundsDeviceMapper, BoundsDevice> {
    public Integer getId() {
        return mapper.getId();
    }

    public List<BoundsDevice> selectByLine(String lineId, Integer code, String ip, String port) {
        return mapper.selectByLine(lineId,code,ip,port);
    }
}
