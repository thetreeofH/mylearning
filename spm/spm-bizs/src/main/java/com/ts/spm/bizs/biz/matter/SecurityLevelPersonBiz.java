package com.ts.spm.bizs.biz.matter;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.matter.SecurityLevelPerson;
import com.ts.spm.bizs.mapper.matter.SecurityLevelPersonMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author luoyu
 * @Date 2020/6/4 17:45
 * @Version 1.0
 */
@Service
public class SecurityLevelPersonBiz extends BusinessBiz<SecurityLevelPersonMapper, SecurityLevelPerson>{

//    public List<Map<String, Integer>> statis(List<String> pointIds, Date start, Date end){
//        return mapper.statis(pointIds, start, end);
//    }
    public List<SecurityLevelPerson> selectBySecrityLevelPerson(List<String> levelId,Integer gateSum){
        return  mapper.selectBySecrityLevelPerson(levelId,gateSum);
    }
}
