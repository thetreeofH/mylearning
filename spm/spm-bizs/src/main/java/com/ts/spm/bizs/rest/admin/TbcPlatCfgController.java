package com.ts.spm.bizs.rest.admin;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ts.spm.bizs.biz.admin.TbcPlatCfgBiz;
import com.ts.spm.bizs.entity.admin.TbcPlatCfg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author luoyu
 * @Date 2020/9/10 16:08
 * @Version 1.0
 */
@RestController
@RequestMapping("platCfg")
@CheckUserToken
@CheckClientToken
@Api(tags="c端配置管理")
public class TbcPlatCfgController extends BaseController<TbcPlatCfgBiz, TbcPlatCfg,String> {

    @IgnoreClientToken
    @IgnoreUserToken
    @ApiOperation("查询")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ObjectRestResponse platCfgQuery(){

        Example example = new Example(TbcPlatCfg.class);
        Example.Criteria criteria = example.createCriteria();
        List<TbcPlatCfg> list=baseBiz.selectByExample(example);

        return ObjectRestResponse.ok(list.get(0));
    }

    @ApiOperation("编辑")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ObjectRestResponse platCfgUpdate(@RequestBody TbcPlatCfg tbcPlatCfg){

        baseBiz.updateSelectiveById2(tbcPlatCfg);
        return  ObjectRestResponse.ok();
    }
}
