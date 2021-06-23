package com.ts.spm.bizs.biz.msg;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.msg.MessageStatus;
import com.ts.spm.bizs.mapper.msg.MessageStatusMapper;
import org.springframework.stereotype.Service;

@Service
public class MessageStatusBiz extends BusinessBiz<MessageStatusMapper, MessageStatus> {
}
