package com.ts.spm.bizs.rest.jzpftgmnt;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.DateUtil;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.jzpmq.TblVideoOnDutyCheckBiz;
import com.ts.spm.bizs.entity.jzpmq.TblVideoOnDutyCheck;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @ClassName VideoOnDutyCheckController
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/9/21 19:27
 * @Version 1.0
 **/
@RestController
@RequestMapping("videoOnDutyCheck2")
@CheckClientToken
@CheckUserToken
public class VideoOnDutyCheckController2 extends BaseController<TblVideoOnDutyCheckBiz, TblVideoOnDutyCheck,String> {
    @Autowired
    TblVideoOnDutyCheckBiz tblVideoOnDutyCheckBiz;

    /**
     * 新增视频值岗检测
     * @param tblVideoOnDutyCheck
     * @return
     */
    @RequestMapping(value = "/information/add",method = RequestMethod.POST)
    public ObjectRestResponse add(@RequestBody TblVideoOnDutyCheck tblVideoOnDutyCheck){
        tblVideoOnDutyCheck.setId(UUIDUtils.generateUuid());
        tblVideoOnDutyCheck.setCreateTime(new Date());
        int result = baseBiz.insertSelective2(tblVideoOnDutyCheck);
        if(result == 1){
            return ObjectRestResponse.ok();
        }
        return ObjectRestResponse.error("保存失败，请联系系统管理员！");
    }

    /**
     * 视频值岗检测列表
     * @param limit
     * @param page
     * @param startTime
     * @param endTime
     * @param userName
     * @param captureDescribe
     * @return
     */
    @RequestMapping(value = "/information/query",method = RequestMethod.GET)
    public TableResultResponse query(@RequestParam(defaultValue = "10") int limit,
                                     @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "") String startTime,
                                     @RequestParam(defaultValue = "") String endTime,
                                     @RequestParam(defaultValue = "") String userName,
                                     @RequestParam(defaultValue = "") String captureDescribe){
        Example example = new Example(TblVideoOnDutyCheck.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(userName)){
            criteria.andLike("judgePictureUserName","%"+userName+"%");
        }

        if(StringUtils.isNotBlank(captureDescribe)){
            criteria.andLike("captureDescribe","%"+captureDescribe+"%");
        }

        if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
            criteria.andBetween("captureTime", DateUtil.stringToDate(startTime),DateUtil.stringToDate(endTime));
        }

        Page<TblVideoOnDutyCheck> tblVideoOnDutyCheckPage = PageHelper.startPage(page,limit);
        List<TblVideoOnDutyCheck> tblVideoOnDutyCheckList = baseBiz.selectByExample(example);
        return new TableResultResponse(tblVideoOnDutyCheckPage.getTotal(),tblVideoOnDutyCheckList);

    }

    @RequestMapping(value = "/information/statistics",method = RequestMethod.GET)
    public ObjectRestResponse statistics(@RequestParam String startTime,
                                         @RequestParam String endTime,
                                         @RequestParam(value = "judgePictureUserName",required = false) String  judgePictureUserName){

        return null;
    }
}
