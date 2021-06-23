package com.ts.spm.bizs.mapper.up;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.up.SecurityDeviceInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhoukun on 2021/4/16.
 */
public interface SecurityDeviceInfoMapper extends CommonMapper<SecurityDeviceInfo> {
    List<SecurityDeviceInfo> selectList(@Param("pointId") String pointIds);
}
