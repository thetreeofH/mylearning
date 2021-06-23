package com.ts.spm.bizs.biz.msg;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import com.ts.spm.bizs.entity.msg.PointMessage;
import com.ts.spm.bizs.mapper.msg.PointMessageMapper;
import org.springframework.stereotype.Service;

@Service
public class PointMessageBiz extends BusinessBiz<PointMessageMapper, PointMessage> {
}
