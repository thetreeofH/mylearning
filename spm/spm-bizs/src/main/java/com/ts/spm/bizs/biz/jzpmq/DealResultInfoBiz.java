package com.ts.spm.bizs.biz.jzpmq;

import com.ts.spm.bizs.entity.jzpmq.DealResultInfo;
import com.ts.spm.bizs.mapper.jzpmq.DealResultInfoMapper;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.util.List;
import java.util.Map;

@Service
public class DealResultInfoBiz extends BusinessBiz<DealResultInfoMapper, DealResultInfo>  {

    public DealResultInfo selectByMissionId(String missionId){
        return mapper.selectByMissionId(missionId);
    }

    public Map<String,String> selectDealResult(String missionId){
        return mapper.selectDealResult(missionId);
    }

    public List<Map<String,String>> query(Map<String,Object> map){
        return mapper.query(map);
    }
    public Integer queryTotal(Map<String,Object> map){
        return mapper.queryTotal(map);
    }

    public Map<String,String> todayStatistics(String day,String today,String userId){
        return mapper.toDayStatistics(day,today,userId);
    }

    public List<Map<String,String>> todayContraband(List<String> pointIds){
        return mapper.todayContraband(pointIds);
    }

    public Map<String,String> todayContrabandSum(List<String> pointIds){
        return mapper.todayContrabandSum(pointIds);
    }
}
