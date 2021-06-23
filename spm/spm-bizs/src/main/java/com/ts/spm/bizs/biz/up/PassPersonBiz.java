package com.ts.spm.bizs.biz.up;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.up.PassPerson;
import com.ts.spm.bizs.mapper.up.PassPersonMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zhoukun on 2021/4/16.
 */
@Service
public class PassPersonBiz extends BusinessBiz<PassPersonMapper, PassPerson> {
    public List<PassPerson> getLastInfo() {
        return mapper.getLastInfo();
    }

    public int updatetime(String time) {
        return mapper.updatetime(time);
    }
}
