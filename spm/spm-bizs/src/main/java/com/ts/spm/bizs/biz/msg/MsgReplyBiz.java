package com.ts.spm.bizs.biz.msg;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.msg.MsgReply;
import com.ts.spm.bizs.mapper.msg.MsgReplyMapper;
import org.springframework.stereotype.Service;

@Service
public class MsgReplyBiz extends BusinessBiz<MsgReplyMapper, MsgReply> {
    public Integer getId() {
        return mapper.getId();
    }
}
