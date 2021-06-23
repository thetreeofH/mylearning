package com.ts.spm.bizs.mapper.equ;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.equ.SysMsgReceive;

import java.util.List;

public interface SysMsgReceiveMapper extends CommonMapper<SysMsgReceive> {
    List<String> selectUserByDepart(String departId);
}