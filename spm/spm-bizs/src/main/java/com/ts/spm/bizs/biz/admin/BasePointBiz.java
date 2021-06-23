package com.ts.spm.bizs.biz.admin;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.admin.BasePoint;
import com.ts.spm.bizs.mapper.admin.BasePointMapper;
import com.ts.spm.bizs.service.LicenceService;
import com.ts.spm.bizs.util.LicencePara;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BasePointBiz extends BusinessBiz<BasePointMapper, BasePoint> {
    @Autowired
    private LicenceService licenceService;

    public List<Map<String, String>> selectByDepartid(BasePoint o) {
        return mapper.selectByDepartid(o);
    }

    public List<Map<String, Object>> selectByDepartids(String name, String entry, String status, List<String> stationIds) {
        return mapper.selectByDepartids(name, entry, status,stationIds);
    }

    //query details not use it , delete
//    public PointVo selectObjectById(String id) {
//        return mapper.selectObjectById(id);
//    }

    public List<Map<String, String>> getAllPointByDepart(String userId, String departId){
        return mapper.getAllPointByDepart(userId, departId);
    }

    public boolean getPointCnt(){
        BasePoint p=new BasePoint();
        p.setDeleteFlag(0);
        p.setStatus("0");
        int realCnt=mapper.selectCount(p);
        LicencePara lp=licenceService.getLicenceResult();
        int upCnt=lp.getPointCnt();
        if(realCnt>upCnt){
            return false;
        }
        return true;
    }
}
