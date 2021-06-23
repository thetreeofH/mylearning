package com.ts.spm.bizs.biz.admin;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.admin.TbcPlatCfg;
import com.ts.spm.bizs.mapper.admin.TbcPlatCfgMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author luoyu
 * @Date 2020/9/10 16:06
 * @Version 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TbcPlatCfgBiz extends BusinessBiz<TbcPlatCfgMapper, TbcPlatCfg> {
}
