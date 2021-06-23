package com.ts.spm.bizs.biz.matter;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.matter.SecurityLevelPoint;
import com.ts.spm.bizs.mapper.matter.SecurityLevelPointMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author luoyu
 * @Date 2020/6/4 17:45
 * @Version 1.0
 */
@Service
public class SecurityLevelPointBiz extends BusinessBiz<SecurityLevelPointMapper, SecurityLevelPoint>{
    public List<SecurityLevelPoint> selectListByLevel(String level, List<String> pointIds) {
        return mapper.selectListByLevel(level, pointIds);
    }

//    public List<Map<String, Integer>> statis(List<String> pointIds, Date start, Date end){
//        return mapper.statis(pointIds, start, end);
//    }
}
