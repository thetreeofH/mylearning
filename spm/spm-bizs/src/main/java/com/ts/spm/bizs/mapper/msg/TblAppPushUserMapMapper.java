package com.ts.spm.bizs.mapper.msg;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.msg.TblAppPushUserMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TblAppPushUserMapMapper extends CommonMapper<TblAppPushUserMap> {
    List<TblAppPushUserMap> getPushCodes(@Param("stationId") String stationId, @Param("appType") Integer appType);

    Map<String,String> selectForbiddenName(@Param("id") Integer id);

    int deleteByPushCode(@Param("userId") String userId, @Param("pushCode") String pushCode, @Param("appType") String appType);

    Map<String,String> selectById(@Param("id") String id);

    List<TblAppPushUserMap> selectByUserIdAndDevCode(@Param("userId") String userId, @Param("devCode") String devCode);
}