package com.ts.spm.bizs.biz.msg;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.msg.MsgReceive;
import com.ts.spm.bizs.mapper.msg.MsgReceiveMapper;
import org.springframework.stereotype.Service;

@Service
public class MsgReceiveBiz extends BusinessBiz<MsgReceiveMapper, MsgReceive> {
    public Integer getId() {
        return mapper.getId();
    }
}
