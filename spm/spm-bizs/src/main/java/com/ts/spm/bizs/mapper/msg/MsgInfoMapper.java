package com.ts.spm.bizs.mapper.msg;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.msg.MsgInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface MsgInfoMapper extends CommonMapper<MsgInfo> {
    Integer getId();

    List<MsgInfo> selectPage(@Param("flag") int flag, @Param("type") int type,
                             @Param("title") String title, @Param("receiveDeptId") String receiveDeptId, @Param("crtUserId")  String crtUserId,
                             @Param("st") Date st, @Param("et") Date et, @Param("ids") List<Integer> ids);
}