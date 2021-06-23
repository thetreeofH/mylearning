package com.ts.spm.bizs.mapper.up;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.up.PackageInfo;
import com.ts.spm.bizs.entity.up.PointDangerInfo;

import java.util.List;

/**
 * Created by zhoukun on 2021/4/19.
 */
public interface PointDangerInfoMapper extends CommonMapper<PointDangerInfo> {
    List<PointDangerInfo> getLastInfo();
    PointDangerInfo getHandleInfo(String id);
    PointDangerInfo getDoorAlarmInfo(String id);
    PackageInfo getPackageInfo(String id);
    PackageInfo getPackageAlarmInfo(String id);
    int updatetime(long time);
}