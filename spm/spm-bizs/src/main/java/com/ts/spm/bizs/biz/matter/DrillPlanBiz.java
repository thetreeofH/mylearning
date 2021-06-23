package com.ts.spm.bizs.biz.matter;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.matter.DrillPlan;
import com.ts.spm.bizs.mapper.matter.DrillPlanMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author luoyu
 * @Date 2020/6/4 17:45
 * @Version 1.0
 */
@Service
public class DrillPlanBiz extends BusinessBiz<DrillPlanMapper, DrillPlan> {
    public List<DrillPlan> getList(String title, String month, Date st, Date et, List<String> departIds) {
        return mapper.getList(title, month, st, et, departIds);
    }
}
