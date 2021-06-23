package com.ts.spm.bizs.mapper.msg;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.msg.BoundsAlarm;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BoundsAlarmMapper extends CommonMapper<BoundsAlarm> {
    Integer getId();

    List<BoundsAlarm> selectByLine(String lineId, Integer status, Date start, Date end, String ip, String port);

    List<Map<String, Object>> statis1(String lineId, Integer status, Date start, Date end, String ip, String port);
    List<Map<String, Object>> statis2(String lineId, Integer status, Date start, Date end, String ip, String port);

    List<Map<String, Object>> chartSite2(String lineId, Integer status, Date start, Date end, String ip, String port);

    List<Map<String, Object>> chartType(String lineId, Integer status, Date start, Date end, String ip, String port);
}