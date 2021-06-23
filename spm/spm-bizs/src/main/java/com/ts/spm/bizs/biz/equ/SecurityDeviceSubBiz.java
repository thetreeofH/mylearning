package com.ts.spm.bizs.biz.equ;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.equ.SecurityDeviceSub;
import com.ts.spm.bizs.mapper.equ.SecurityDeviceSubMapper;
import com.ts.spm.bizs.vo.equ.SecurityDeviceVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityDeviceSubBiz extends BusinessBiz<SecurityDeviceSubMapper, SecurityDeviceSub> {
    public Integer getId() {
        return mapper.getId();
    }
    public List<SecurityDeviceVo> selectCountByPoint(String type, List<String> pointIds) {
        return mapper.selectCountByPoint(type, pointIds);
    }
    public List<SecurityDeviceSub> selectList(String type, List<String> pointIds) {
        return mapper.selectList(type,pointIds);
    }

    public SecurityDeviceSub getById(String id) {
        return mapper.getById(id);
    }

    public List<SecurityDeviceSub> gis_selectList(String stationId) {
        return mapper.gis_selectList(stationId);
    }
}
