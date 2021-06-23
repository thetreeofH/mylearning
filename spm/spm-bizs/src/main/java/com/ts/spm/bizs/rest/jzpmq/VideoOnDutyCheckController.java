package com.ts.spm.bizs.rest.jzpmq;

import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.DateUtil;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.jzpmq.TblVideoOnDutyCheckBiz;
import com.ts.spm.bizs.entity.jzpmq.TblVideoOnDutyCheck;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.CheckForNull;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("videoOnDutyCheck")
@IgnoreClientToken
@CheckUserToken
public class VideoOnDutyCheckController extends BaseController<TblVideoOnDutyCheckBiz, TblVideoOnDutyCheck,String> {
    @Autowired
    TblVideoOnDutyCheckBiz tblVideoOnDutyCheckBiz;


    @RequestMapping(value = "/information/query",method = RequestMethod.GET)
    public TableResultResponse query(@RequestParam(defaultValue = "10") int limit,
                                     @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(value = "startTime", required = false) String startTime,
                                     @RequestParam(value = "endTime", required = false) String endTime,
                                     @RequestParam(value = "JudgePictureUserName",defaultValue = "") String JudgePictureUserName){
        try {
            if(StringUtils.isNoneBlank(startTime)){
                startTime = startTime + " 00:00:00";
            }
            if(StringUtils.isNoneBlank(endTime)){
                endTime = endTime + " 23:59:59";
            }

            Page<Object> result = PageHelper.startPage(page, limit);
            List<TblVideoOnDutyCheck> tblVideoOnDutyChecks = tblVideoOnDutyCheckBiz.query(startTime,endTime,JudgePictureUserName);
            return new TableResultResponse(result.getTotal(),tblVideoOnDutyChecks);
        }catch(Exception e){
            e.printStackTrace();
            return TableResultResponse.error(e.getMessage());
        }

    }

}
