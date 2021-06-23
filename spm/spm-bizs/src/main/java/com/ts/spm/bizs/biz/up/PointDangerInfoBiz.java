package com.ts.spm.bizs.biz.up;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.up.PackageInfo;
import com.ts.spm.bizs.entity.up.PointDangerInfo;
import com.ts.spm.bizs.mapper.up.PointDangerInfoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhoukun on 2021/4/19.
 */
@Service
public class PointDangerInfoBiz extends BusinessBiz<PointDangerInfoMapper, PointDangerInfo> {
    public List<PointDangerInfo> getLastInfo() {
        return mapper.getLastInfo();
    }

    public PointDangerInfo getHandleInfo(String id) {
        return mapper.getHandleInfo(id);
    }

    public PackageInfo getPackageInfo(String id) {
        return mapper.getPackageInfo(id);
    }

    public PackageInfo getPackageAlarmInfo(String id) {
        return mapper.getPackageAlarmInfo(id);
    }

    public PointDangerInfo getDoorAlarmInfo(String id) {
        return mapper.getDoorAlarmInfo(id);
    }

    public int updatetime(long time) {
        return mapper.updatetime(time);
    }
}
