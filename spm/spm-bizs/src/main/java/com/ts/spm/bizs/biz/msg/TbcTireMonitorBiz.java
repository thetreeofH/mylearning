package com.ts.spm.bizs.biz.msg;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.msg.TbcTireMonitor;
import com.ts.spm.bizs.mapper.msg.TbcTireMonitorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName TbcTireMonitorBiz
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/11/13 11:21
 * @Version 1.0
 **/
@Service
public class TbcTireMonitorBiz extends BusinessBiz<TbcTireMonitorMapper, TbcTireMonitor> {


    public Map<String, Object> selectById(Integer id){
        return mapper.selectById(id);
    }

    public Map<String,Object> selectByPointsId(List<String> pointIds){
        return mapper.selectByPointsId(pointIds);
    }
}
