package com.ts.spm.bizs.biz.equ;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.equ.SysMsgReceive;
import com.ts.spm.bizs.mapper.equ.SysMsgReceiveMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author luoyu
 * @Date 2020/6/4 17:45
 * @Version 1.0
 */
@Service
public class SysMsgReceiveBiz extends BusinessBiz<SysMsgReceiveMapper, SysMsgReceive> {
    public List<String> selectUserByDepart(String departId) {
        return mapper.selectUserByDepart(departId);
    }
}
