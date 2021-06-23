package com.ts.spm.bizs.rest.stat;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ts.spm.bizs.biz.stat.PersonCheckBiz;
import com.ts.spm.bizs.entity.stat.TblInnerCheckProblemPerson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author luoyu
 * @Date 2020/6/12 16:43
 * @Version 1.0
 */
@RestController
@RequestMapping("personCheck")
@CheckClientToken
@CheckUserToken
@Api(tags = "人员问题管理")
public class PersonCheckController extends BaseController<PersonCheckBiz, TblInnerCheckProblemPerson, String> {

    @ApiOperation("人员问题查询")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public TableResultResponse queryPersonCheck(String departId, String startTime, String endTime, String ajyIdcard, String ajyName, String ajyType,
                                                @RequestParam(name = "limit", defaultValue = "10") int limit,
                                                @RequestParam(name = "page", defaultValue = "1") int page){
        Page<Object> result = PageHelper.startPage(page, limit);
        List<Map<String,Object>> list=baseBiz.getPersonCheck(departId,startTime,endTime,ajyIdcard,ajyName,ajyType);
        return new TableResultResponse(result.getTotal(),list);
    }

    @ApiOperation("人员问题统计")
    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public ObjectRestResponse statisticsPersonCheck(){
        return ObjectRestResponse.ok();
    }


}
