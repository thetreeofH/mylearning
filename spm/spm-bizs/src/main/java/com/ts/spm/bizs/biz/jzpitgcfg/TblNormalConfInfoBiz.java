package com.ts.spm.bizs.biz.jzpitgcfg;


import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import com.ts.spm.bizs.entity.jzpitgcfg.TblNormalConfInfo;
import com.ts.spm.bizs.mapper.jzpitgcfg.TblNormalConfInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Service
public class TblNormalConfInfoBiz extends BusinessBiz<TblNormalConfInfoMapper, TblNormalConfInfo> {
    public Map<String,String> selectByPointId(String pId){
        return mapper.selectById(pId);
    }

    public Map<String, Object> selectMaxCreateTime(String pointId, Integer tag){
        return mapper.selectMaxCreateTime(pointId,tag);
    }
}
