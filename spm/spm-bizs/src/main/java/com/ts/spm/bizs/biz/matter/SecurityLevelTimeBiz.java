package com.ts.spm.bizs.biz.matter;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.matter.SecurityLevelTime;
import com.ts.spm.bizs.mapper.matter.SecurityLevelTimeMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author luoyu
 * @Date 2020/6/4 17:45
 * @Version 1.0
 */
@Service
public class SecurityLevelTimeBiz extends BusinessBiz<SecurityLevelTimeMapper, SecurityLevelTime>{
    public List<SecurityLevelTime> selectCurrentTime(String str, List<String> levelCodes) {

        return mapper.selectCurrentTime(str,levelCodes);
    }

//    public List<Map<String, Integer>> statis(List<String> pointIds, Date start, Date end){
//        return mapper.statis(pointIds, start, end);
//    }
}
