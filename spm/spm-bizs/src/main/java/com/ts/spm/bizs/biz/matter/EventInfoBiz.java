package com.ts.spm.bizs.biz.matter;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.matter.EventInfo;
import com.ts.spm.bizs.mapper.matter.EventInfoMapper;
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
public class EventInfoBiz extends BusinessBiz<EventInfoMapper, EventInfo>{

    public List<Map<String, Object>> statis1(List<String> pointIds, Date start, Date end, int type){
        return mapper.statis1(pointIds, start, end,type);
    }
    public List<Map<String, Object>> statis2(List<String> pointIds, Date start, Date end, int type){
        return mapper.statis2(pointIds, start, end,type);
    }

    public List<Map<String, Object>> chartSite1(List<String> pointIds, Date start, Date end, int type) {
        return mapper.chartSite1(pointIds, start, end,type);
    }
    public List<Map<String, Object>> chartSite2(List<String> pointIds, Date start, Date end, int type) {
        return mapper.chartSite2(pointIds, start, end,type);
    }

    public List<Map<String, Object>> chartDanger(List<String> pointIds, Date start, Date end, int type) {
        return mapper.chartDanger(pointIds, start, end,type);
    }

    public List<EventInfo> selectByParams(int type, String dealType, Date start, Date end, List<String> pointIds,int dealResult) {
        return mapper.selectByParams(type, dealType, start, end, pointIds,dealResult);
    }

    public EventInfo selectObjectById(String id) {
        return mapper.selectObjectById(id);
    }

    public Integer getId() {
        return mapper.getId();
    }

    public List<EventInfo> selectAlarm(List<String> pointIds) {
        return mapper.selectAlarm(pointIds);
    }

    public int selectCountFromAlarm(Date start, Date end, List<String> pointIds) {
        return mapper.selectCountFromAlarm(start,end,pointIds);
    }
}
