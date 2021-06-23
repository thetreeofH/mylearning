package com.ts.spm.bizs.mapper.jzplog;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.jzplog.TblOnlineTime;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TblOnlineTimeMapper extends CommonMapper<TblOnlineTime> {
    TblOnlineTime selectMaxUpdateTime(@Param("userId") String userId);
    List<Map<String,String>> statistics(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("userId") String userId);
}