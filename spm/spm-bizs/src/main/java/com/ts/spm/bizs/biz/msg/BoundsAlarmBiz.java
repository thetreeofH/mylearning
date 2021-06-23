package com.ts.spm.bizs.biz.msg;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.msg.BoundsAlarm;
import com.ts.spm.bizs.mapper.msg.BoundsAlarmMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class BoundsAlarmBiz extends BusinessBiz<BoundsAlarmMapper, BoundsAlarm> {
    public Integer getId() {
        return mapper.getId();
    }

    public List<BoundsAlarm> selectByLine(String lineId, Integer status, Date start, Date end, String ip, String port) {
        return mapper.selectByLine(lineId,status,start,end,ip,port);
    }

    public List<Map<String, Object>> statis1(String lineId, Integer status, Date start, Date end, String ip, String port) {
        return mapper.statis1(lineId,status,start,end,ip,port);
    }
    public List<Map<String, Object>> statis2(String lineId, Integer status, Date start, Date end, String ip, String port) {
        return mapper.statis2(lineId,status,start,end,ip,port);
    }

    public List<Map<String, Object>> chartSite2(String lineId, Integer status, Date start, Date end, String ip, String port) {
        return mapper.chartSite2(lineId,status,start,end,ip,port);
    }

    public List<Map<String, Object>> chartType(String lineId, Integer status, Date start, Date end, String ip, String port) {
        return mapper.chartType(lineId,status,start,end,ip,port);
    }
}
