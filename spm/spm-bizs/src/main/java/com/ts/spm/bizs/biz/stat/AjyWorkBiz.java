package com.ts.spm.bizs.biz.stat;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.stat.AjyWork;
import com.ts.spm.bizs.mapper.stat.AjyWorkMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author luoyu
 * @Date 2020/6/4 17:45
 * @Version 1.0
 */
@Service
public class AjyWorkBiz extends BusinessBiz<AjyWorkMapper, AjyWork>{
    List<Map<String,String>> getPersonInfoByDepartId(List<String> departIds, String name, String company, String securityId){
//        return mapper.getPersonInfoByDepartId(departIds,name,company,securityId);
        return null;
    }

}
