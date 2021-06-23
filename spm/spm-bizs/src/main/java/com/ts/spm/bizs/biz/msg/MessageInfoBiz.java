package com.ts.spm.bizs.biz.msg;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.msg.MessageInfo;
import com.ts.spm.bizs.mapper.msg.MessageInfoMapper;
import org.springframework.stereotype.Service;

@Service
public class MessageInfoBiz extends BusinessBiz<MessageInfoMapper, MessageInfo> {
}
