package com.ts.spm.bizs.biz.stat;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.stat.AutoTipsCount;
import com.ts.spm.bizs.mapper.stat.AutoTipsCountMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author luoyu
 * @Date 2020/6/5 17:10
 * @Version 1.0
 */
@Service
public class AutoTipsBiz extends BusinessBiz<AutoTipsCountMapper, AutoTipsCount> {
    public Map<String, BigDecimal> getAutoTipsCountByPointIds(List<String> points, String startTime, String endTime){
        return mapper.getAutoTipsCountByPointIds(points,startTime+" 00:00:00", endTime+" 23:59:59");
    }
}
