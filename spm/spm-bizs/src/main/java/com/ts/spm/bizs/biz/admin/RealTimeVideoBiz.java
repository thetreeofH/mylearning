package com.ts.spm.bizs.biz.admin;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.admin.Employee;
import com.ts.spm.bizs.entity.admin.RealTimeVideo;
import com.ts.spm.bizs.mapper.admin.EmployeeMapper;
import com.ts.spm.bizs.mapper.admin.RealTimeVideoMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lihaiping on 2021/5/31.
 */
@Service
public class RealTimeVideoBiz extends BusinessBiz<RealTimeVideoMapper, RealTimeVideo> {

    public List<RealTimeVideo> getRealTimeVideoList(String stationId,String pointId,String lineId){
       return mapper.getRealTimeVideoList(stationId,pointId,lineId);
    }


    public void delRealTimeVideo(String id){
       mapper.delRealTimeVideo(id);
    }

    public List<RealTimeVideo> selectRealTimeByPointId(String pointId){
        return mapper.selectRealTimeByPointId(pointId);
    }
    public RealTimeVideo selectDeviceByDevId(String id){
        return mapper.selectDeviceByDevId(id);
    }
    public Integer isDeviceExist(String pointId,String alarmType){
        return mapper.isDeviceExist(pointId,alarmType);
    }
    public RealTimeVideo selectDeviceById(String id){
        return mapper.selectDeviceById(id);
    }
    public String selectDeviceByPointIdAndAlarmType(String pointId,String alarmType){
        return mapper.selectDeviceByPointIdAndAlarmType(pointId,alarmType);
    }
}
