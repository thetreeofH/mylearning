package com.ts.spm.bizs.mapper.up;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.up.PassPerson;

import java.util.List;

/**
 * Created by zhoukun on 2021/4/16.
 */
public interface PassPersonMapper extends CommonMapper<PassPerson> {
    List<PassPerson> getLastInfo();
    int updatetime(String time);
}
