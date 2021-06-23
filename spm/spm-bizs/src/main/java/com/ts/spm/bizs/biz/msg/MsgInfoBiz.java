package com.ts.spm.bizs.biz.msg;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.msg.MsgInfo;
import com.ts.spm.bizs.mapper.msg.MsgInfoMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MsgInfoBiz extends BusinessBiz<MsgInfoMapper, MsgInfo> {
    public Integer getId() {
        return mapper.getId();
    }

    public List<MsgInfo> selectPage(int flag, int type, String title, String receiveDeptId, String crtUserId, Date st, Date et, List<Integer> ids) {
        return mapper.selectPage(flag, type, title, receiveDeptId, crtUserId, st, et, ids);
    }
}
