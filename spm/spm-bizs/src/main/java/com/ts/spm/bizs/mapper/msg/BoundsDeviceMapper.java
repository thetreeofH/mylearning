package com.ts.spm.bizs.mapper.msg;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.msg.BoundsDevice;

import java.util.List;

public interface BoundsDeviceMapper extends CommonMapper<BoundsDevice> {
    Integer getId();

    List<BoundsDevice> selectByLine(String lineId, Integer code, String ip, String port);
}