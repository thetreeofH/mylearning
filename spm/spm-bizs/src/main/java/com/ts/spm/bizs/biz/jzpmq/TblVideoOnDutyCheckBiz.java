package com.ts.spm.bizs.biz.jzpmq;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.jzpmq.TblVideoOnDutyCheck;
import com.ts.spm.bizs.mapper.jzpmq.TblVideoOnDutyCheckMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName TblVideoOnDutyCheckBiz
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/9/24 14:04
 * @Version 1.0
 **/
@Service
public class TblVideoOnDutyCheckBiz extends BusinessBiz<TblVideoOnDutyCheckMapper, TblVideoOnDutyCheck> {
    public List<TblVideoOnDutyCheck> query(String startTime,String endTime,String JudgePictureUserName){
        return mapper.query(startTime,endTime,JudgePictureUserName);
//        return null;
    }
}
