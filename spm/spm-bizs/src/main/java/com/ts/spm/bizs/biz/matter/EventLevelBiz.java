package com.ts.spm.bizs.biz.matter;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.matter.EventLevel;
import com.ts.spm.bizs.mapper.matter.EventLevelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author luoyu
 * @Date 2020/6/4 17:45
 * @Version 1.0
 */
@Service
public class EventLevelBiz extends BusinessBiz<EventLevelMapper, EventLevel>{

    public List<Map<String, Integer>> statis(List<String> pointIds, Date start, Date end){
//        return mapper.statis(pointIds, start, end);
        return null;
    }
}
