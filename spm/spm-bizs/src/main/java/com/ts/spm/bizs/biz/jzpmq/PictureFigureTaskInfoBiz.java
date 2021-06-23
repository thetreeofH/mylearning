package com.ts.spm.bizs.biz.jzpmq;

import com.ts.spm.bizs.entity.jzpmq.PictureFigureTaskInfo;
import com.ts.spm.bizs.mapper.jzpmq.PictureFigureTaskInfoMapper;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.util.List;
import java.util.Map;

@Service
public class PictureFigureTaskInfoBiz extends BusinessBiz<PictureFigureTaskInfoMapper, PictureFigureTaskInfo>  {
    public List<Map<String,String>> query(Map<String,Object> map){
        return mapper.query(map);
    }
    public Integer selectTotalCount(Map<String,Object> map){
        return mapper.selectTotalCount(map);
    }
}
