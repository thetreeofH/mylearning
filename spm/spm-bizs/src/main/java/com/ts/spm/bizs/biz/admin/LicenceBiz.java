package com.ts.spm.bizs.biz.admin;

import com.ts.spm.bizs.entity.admin.Licence;
import com.ts.spm.bizs.mapper.admin.LicenceMapper;
import com.ts.spm.bizs.service.LicenceService;
import com.ts.spm.bizs.util.LicencePara;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LicenceBiz extends BusinessBiz<LicenceMapper, Licence> {
    @Autowired
    private LicenceService licenceService;

    public Map<String,Object> getLicenceInfo(){
        LicencePara licencePara=licenceService.getLicenceResult();
        Map<String,Object> map=new HashMap<>();
        map.put("lineCnt",licencePara.getLineCnt());
        map.put("stationCnt",licencePara.getStationCnt());
        map.put("pointCnt",licencePara.getPointCnt());
        map.put("userCnt",licencePara.getUserCnt());
        return  map;
    }
}
