package com.ts.spm.bizs.biz.jzplog;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.jzplog.TblOnlineTime;
import com.ts.spm.bizs.mapper.jzplog.TblOnlineTimeMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName TblOnlineTimeBiz
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/8/12 11:30
 * @Version 1.0
 **/
@Service
public class TblOnlineTimeBiz extends BusinessBiz<TblOnlineTimeMapper, TblOnlineTime> {

    public TblOnlineTime selectMaxUpdateTime(String userId){
        return mapper.selectMaxUpdateTime(userId);
    }

    public List<Map<String,String>> statistics(String startDate,String endDate, String userId){
        System.out.println("startDate:"+startDate+"-------------endDate:"+endDate);
        return mapper.statistics(startDate,endDate,userId);
    }
}
