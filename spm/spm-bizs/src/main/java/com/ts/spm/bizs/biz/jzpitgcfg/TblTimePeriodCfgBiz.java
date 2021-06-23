package com.ts.spm.bizs.biz.jzpitgcfg;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.jzpitgcfg.TblTimePeriodCfg;
import com.ts.spm.bizs.mapper.jzpitgcfg.TblTimePeriodCfgMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName TblTimePeriodCfgBiz
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/8/24 10:55
 * @Version 1.0
 **/
@Service
public class TblTimePeriodCfgBiz extends BusinessBiz<TblTimePeriodCfgMapper, TblTimePeriodCfg> {
    public List<TblTimePeriodCfg> query(List<String> pointIds,String pointId){
        return mapper.query(pointIds,pointId);
    }
}
