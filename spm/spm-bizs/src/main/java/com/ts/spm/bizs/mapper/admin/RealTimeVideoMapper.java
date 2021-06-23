package com.ts.spm.bizs.mapper.admin;

import com.github.wxiaoqi.security.common.data.Tenant;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.admin.Employee;
import com.ts.spm.bizs.entity.admin.RealTimeVideo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by lihaiping on 2021/5/31.
 */
@Tenant()
public interface RealTimeVideoMapper extends CommonMapper<RealTimeVideo> {

    List<RealTimeVideo> selectRealTimeByPointId(@Param("pointId") String pointId);

    List<RealTimeVideo> getRealTimeVideoList(@Param("stationId") String stationId,
                                             @Param("pointId") String pointId,
                                             @Param("lineId") String lineId);
    public void delRealTimeVideo(@Param("id") String id);

    public RealTimeVideo selectDeviceByDevId(@Param("id") String id);

    public Integer isDeviceExist(@Param("pointId") String pointId,@Param("alarmType") String alarmType);

    public RealTimeVideo selectDeviceById(@Param("id") String id);

    public String selectDeviceByPointIdAndAlarmType(@Param("pointId") String pointId,@Param("alarmType") String alarmType);
}
