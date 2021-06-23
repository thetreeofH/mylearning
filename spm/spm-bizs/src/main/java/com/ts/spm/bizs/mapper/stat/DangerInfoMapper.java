package com.ts.spm.bizs.mapper.stat;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.stat.DangerInfo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DangerInfoMapper extends CommonMapper<DangerInfo> {
    List<Map<String, Integer>> statis(List<String> pointIds, Date start, Date end);
}