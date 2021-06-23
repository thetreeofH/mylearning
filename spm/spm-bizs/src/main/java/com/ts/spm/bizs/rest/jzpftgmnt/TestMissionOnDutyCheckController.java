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
import com.ts.spm.bizs.biz.jzpftgmnt.TblTestMissionOnDutyCheckBiz;
import com.ts.spm.bizs.entity.jzpftgmnt.TblTestMissionOnDutyCheck;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;


/**
 * @ClassName OperatorTreeController
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/9/7 14:51
 * @Version 1.0
 **/
@RestController
@RequestMapping("fatigueMonitoring")
@CheckClientToken
@CheckUserToken
public class TestMissionOnDutyCheckController extends BaseController<TblTestMissionOnDutyCheckBiz, TblTestMissionOnDutyCheck,String> {
    @Autowired
     TblTestMissionOnDutyCheckBiz tblTestMissionOnDutyCheckBiz;

    /**
     * 新增未执行虚拟任务
     * @param tblTestMissionOnDutyCheck
     * @return
     */
    @RequestMapping(value = "/information/add",method = RequestMethod.POST)
    public ObjectRestResponse add(@RequestBody TblTestMissionOnDutyCheck tblTestMissionOnDutyCheck){
        tblTestMissionOnDutyCheck.setId(UUIDUtils.generateUuid());
        tblTestMissionOnDutyCheck.setCreateTime(new Date());
        int result = baseBiz.insertSelective2(tblTestMissionOnDutyCheck);
        if(result == 1){
            return ObjectRestResponse.ok();
        }
        return ObjectRestResponse.error("保存失败，请联系管理员！");
    }

    /**
     * 未执行虚拟判图任务列表
     * @param limit
     * @param page
     * @param startTime
     * @param endTime
     * @param userName
     * @param suspectedForbiddenType
     * @param suspectedForbiddenSubtype
     * @param testMissionName
     * @return
     */
    @RequestMapping(value = "/information/query",method = RequestMethod.GET)
    public TableResultResponse query(@RequestParam(defaultValue = "10") int limit,
                                     @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "") String startTime,
                                     @RequestParam(defaultValue = "") String endTime,
                                     @RequestParam(defaultValue = "") String userName,
                                     @RequestParam(defaultValue = "") Integer suspectedForbiddenType,
                                     @RequestParam(defaultValue = "") Integer suspectedForbiddenSubtype,
                                     @RequestParam(defaultValue = "") String testMissionName){
        Example example = new Example(TblTestMissionOnDutyCheck.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(userName)){
            criteria.andLike("judgePictureUserName","%"+userName+"%");
        }

        if(!"".equals(suspectedForbiddenType)){
            criteria.andEqualTo("suspectedForbiddenType",suspectedForbiddenType);
        }
        if(!"".equals(suspectedForbiddenSubtype)){
            criteria.andEqualTo("suspectedForbiddenSubtype",suspectedForbiddenSubtype);
        }
        if(StringUtils.isNotBlank(testMissionName)){
            criteria.andEqualTo("testMissionName",testMissionName);
        }

        if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
            criteria.andBetween("testTime", DateUtil.stringToDate(startTime),DateUtil.stringToDate(endTime));
        }

        Page<TblTestMissionOnDutyCheck> testMissionPage = PageHelper.startPage(page,limit);
        List<TblTestMissionOnDutyCheck> tblTestMissionOnDutyCheckList = baseBiz.selectByExample(example);

        return new TableResultResponse(testMissionPage.getTotal(),tblTestMissionOnDutyCheckList);
    }




}
