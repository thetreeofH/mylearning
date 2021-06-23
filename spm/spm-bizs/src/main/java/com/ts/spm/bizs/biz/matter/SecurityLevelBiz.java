package com.ts.spm.bizs.biz.matter;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.matter.SecurityLevel;
import com.ts.spm.bizs.mapper.matter.SecurityLevelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author luoyu
 * @Date 2020/6/4 17:45
 * @Version 1.0
 */
@Service
public class SecurityLevelBiz extends BusinessBiz<SecurityLevelMapper, SecurityLevel>{
    public List<SecurityLevel> selectListByParam(String name, List<String> deptIds) {
        return mapper.selectListByParam(name,deptIds);
    }

    public List<SecurityLevel> selectCurrentDate(String str){
        return mapper.selectCurrentDate(str);
    }
}
