package com.ts.spm.bizs.biz.stat;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.stat.DayProblem;
import com.ts.spm.bizs.mapper.stat.DayProblemMapper;
import com.ts.spm.bizs.util.DateUtil;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author luoyu
 * @Date 2020/6/5 15:41
 * @Version 1.0
 */
@Service
public class DayProblemBiz extends BusinessBiz<DayProblemMapper, DayProblem> {

    public List<DayProblem> getDayProblemByPointId(List<String> pointIds, String startTime, String endTime){
        Example example = new Example(DayProblem.class);
        Example.Criteria criteria = example.createCriteria();
        if(pointIds.size()>0){
            criteria.andIn("pointId",pointIds);
        }

        criteria.andEqualTo("isProb", "1");

        if(!"".equals(startTime)&&startTime!=null&&!"".equals(endTime)&&endTime!=null){
            criteria.andBetween("checkDate", DateUtil.parse(startTime+" 00:00:00","yyyy-MM-dd HH:mm:ss"), DateUtil.parse(endTime+" 23:59:59","yyyy-MM-dd HH:mm:ss"));
        }
        return  selectByExample(example);
    }

    public List<DayProblem> getDayProblemByDepartId(List<String> departIds, String startTime, String endTime){

        return  mapper.getProblemByDepartIds(departIds,startTime,endTime);
    }

    public List<DayProblem> getDayProblemByRegisterId(String registerId){
        Example example = new Example(DayProblem.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("registerId", registerId);
        return  selectByExample(example);
    }

    public void deleteDayProbelmByRegisterId(String registerId){
        Example example = new Example(DayProblem.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("registerId", registerId);
        mapper.deleteByExample(example);
    }

    public int getId(){
        return mapper.getId();
    }
}
