package com.ts.spm.bizs.biz.msg;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.msg.InnerCheck;
import com.ts.spm.bizs.mapper.msg.InnerCheckMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName InnerCheckBiz
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/11/16 14:10
 * @Version 1.0
 * updater 马居朝
 * 原类名InnerCheckBiz,因类名冲突，先改为InnerCheckerBiz
 *
 **/
@Service
public class InnerCheckerBiz extends BusinessBiz<InnerCheckMapper, InnerCheck> {
    public Map<String,String> getDepartByUserName(String userName){
        return mapper.getDepartByUserName(userName);
    }
}
