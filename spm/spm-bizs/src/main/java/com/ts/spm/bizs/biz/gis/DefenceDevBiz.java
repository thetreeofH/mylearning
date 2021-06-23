package com.ts.spm.bizs.biz.gis;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.gis.DefenceDev;
import com.ts.spm.bizs.mapper.gis.DefenceDevMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhoukun 2020/4/18.
 */
@Service
public class DefenceDevBiz extends BusinessBiz<DefenceDevMapper, DefenceDev> {
    public List<DefenceDev> getDevInfo(String stationId) {
        return mapper.getDevInfo(stationId);
    }
}
