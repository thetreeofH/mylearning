package com.ts.spm.bizs.biz.equ;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.equ.SecurityDeviceRepair;
import com.ts.spm.bizs.mapper.equ.SecurityDeviceRepairMapper;
import com.ts.spm.bizs.vo.equ.RepairVo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SecurityDeviceRepairBiz extends BusinessBiz<SecurityDeviceRepairMapper, SecurityDeviceRepair> {
    public List<RepairVo> selectGroup(Date start, Date end, String type, List<String> pointIds) {
        return mapper.selectGroup(start, end, type, pointIds);
    }
}
