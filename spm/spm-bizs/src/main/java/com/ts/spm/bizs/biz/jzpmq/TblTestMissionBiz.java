package com.ts.spm.bizs.biz.jzpmq;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.jzpmq.TblTestMission;
import com.ts.spm.bizs.mapper.jzpmq.TblTestMissionMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName TblTestMissionBiz
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/9/17 16:34
 * @Version 1.0
 **/
@Service
public class TblTestMissionBiz extends BusinessBiz<TblTestMissionMapper, TblTestMission> {
    public List<Map<String,String>> statistics(String startTime, String endTime, String judgePictureUserName, Integer ifDistinguish){
        return mapper.statistics(startTime,endTime,judgePictureUserName,ifDistinguish);
    }
}
