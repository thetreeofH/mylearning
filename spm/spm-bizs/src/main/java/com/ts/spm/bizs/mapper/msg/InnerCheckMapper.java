package com.ts.spm.bizs.mapper.msg;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.msg.InnerCheck;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @ClassName InnerCheckMapper
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/11/16 14:10
 * @Version 1.0
 **/
public interface InnerCheckMapper extends CommonMapper<InnerCheck> {
    Map<String,String> getDepartByUserName(@Param("userName") String userName);
}
