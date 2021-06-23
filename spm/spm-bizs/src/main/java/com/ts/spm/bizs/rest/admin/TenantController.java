/*
 *
 *  *  Copyright (C) 2018  Wanghaobin<463540703@qq.com>
 *
 *  *  AG-Enterprise 企业版源码
 *  *  郑重声明:
 *  *  如果你从其他途径获取到，请告知老A传播人，奖励1000。
 *  *  老A将追究授予人和传播人的法律责任!
 *
 *  *  This program is free software; you can redistribute it and/or modify
 *  *  it under the terms of the GNU General Public License as published by
 *  *  the Free Software Foundation; either version 2 of the License, or
 *  *  (at your option) any later version.
 *
 *  *  This program is distributed in the hope that it will be useful,
 *  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *  GNU General Public License for more details.
 *
 *  *  You should have received a copy of the GNU General Public License along
 *  *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 */

package com.ts.spm.bizs.rest.admin;

import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.constant.RestCodeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.admin.TenantBiz;
import com.ts.spm.bizs.biz.admin.UserBiz;
import com.ts.spm.bizs.entity.admin.Tenant;
import com.ts.spm.bizs.entity.admin.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * 租户管理
 * @author ace
 */
@RestController
@RequestMapping("tenant")
@CheckClientToken
@CheckUserToken
@Api(tags = "租户模块")
public class TenantController extends BaseController<TenantBiz, Tenant,String> {
    @Autowired
    private UserBiz userBiz;
    @ApiOperation("租户授予用户")
    @RequestMapping(value = "/{id}/user",method = RequestMethod.PUT)
    public ObjectRestResponse<Boolean> updateUser(@PathVariable("id") String id, String userId){
        baseBiz.updateUser(id,userId);
        return new ObjectRestResponse<>();
    }

    @ApiOperation("获取租户授予用户")
    @RequestMapping(value = "/{id}/user",method = RequestMethod.GET)
    public ObjectRestResponse<User> updateUser(@PathVariable("id") String id){
        Tenant tenant = baseBiz.selectById(id);
        return new ObjectRestResponse<>().data(userBiz.selectById(tenant.getOwner()));
    }

//    @ApiOperation("根据code获取租户id")
//    @RequestMapping(value = "/{code}",method = RequestMethod.GET)
//    public Map<String,String> selectTenantId(@PathVariable("code") String code){
//        Tenant tenant = new Tenant();
//        tenant.setCode(code);
//        tenant = baseBiz.selectOne(tenant);
//        Map<String,String> map = new HashMap<>();
//        map.put("tenantId",tenant.getId());
//        return map;
//    }

    @RequestMapping(value = "add",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse addTenant(@RequestBody Tenant o){
        Tenant tenant = new Tenant();
        tenant.setCode(o.getCode());
        if(baseBiz.selectCount(tenant)>0)
            return ObjectRestResponse.error(RestCodeConstants.EX_BUSINESS_BASE_CODE,"区域编码已存在");
        tenant = new Tenant();
        tenant.setName(o.getName());
        if(baseBiz.selectCount(tenant)>0)
            return ObjectRestResponse.error(RestCodeConstants.EX_BUSINESS_BASE_CODE,"区域名称已存在");
        o.setId(UUIDUtils.generateUuid());
        o.setCrtTime(new Date());
        o.setCrtUserId(BaseContextHandler.getUserID());
        o.setCrtUserName(BaseContextHandler.getUsername());

        baseBiz.insertSelective(o);
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "getall",method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse getall(){
        return ObjectRestResponse.ok(baseBiz.selectListAll());
    }

    @RequestMapping(value = "getpage",method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<Tenant> getpage(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int page,
                                               @RequestParam(defaultValue = "") String name){
        Example exa = new Example(Tenant.class);
        Example.Criteria cr=exa.createCriteria();
        cr.andLike("name", "%"+name+"%");
        exa.setOrderByClause("crt_time desc");
        Page<Object> result = PageHelper.startPage(page, limit);
        List list = baseBiz.selectByExample(exa);
        return new TableResultResponse(result.getTotal(), list);
    }

}
