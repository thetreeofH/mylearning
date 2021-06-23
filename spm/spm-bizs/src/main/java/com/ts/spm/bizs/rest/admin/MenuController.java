/*
 *  Copyright (C) 2018  Wanghaobin<463540703@qq.com>

 *  AG-Enterprise 企业版源码
 *  郑重声明:
 *  如果你从其他途径获取到，请告知老A传播人，奖励1000。
 *  老A将追究授予人和传播人的法律责任!

 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.ts.spm.bizs.rest.admin;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.TreeUtil;
import com.ts.spm.bizs.biz.admin.MenuBiz;
import com.ts.spm.bizs.biz.admin.UserBiz;
import com.ts.spm.bizs.util.AdminCommonConstant;
import com.ts.spm.bizs.entity.admin.Menu;
import com.ts.spm.bizs.vo.admin.MenuTree;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @version 2017-06-12 8:49
 */
@RestController
@RequestMapping("menu")
@CheckUserToken
@CheckClientToken
@Api(tags="菜单模块")
public class MenuController extends BaseController<MenuBiz, Menu,String> {
    @Autowired
    private UserBiz userBiz;

    @ApiOperation("获取菜单列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<Menu> list(String title) {
        Example example = new Example(Menu.class);
        if (StringUtils.isNotBlank(title)) {
            example.createCriteria().andLike("title", "%" + title + "%");
        }
        return baseBiz.selectByExample(example);
    }

    @ApiOperation("获取菜单树")
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    @ResponseBody
    public List<MenuTree> getTree(String title) {
        Example example = new Example(Menu.class);
        if (StringUtils.isNotBlank(title)) {
            example.createCriteria().andLike("title", "%" + title + "%");
        }
        return MenuTree.buildTree(baseBiz.selectByExample(example), AdminCommonConstant.ROOT);
    }



  @RequestMapping(value = "/{id}",method = RequestMethod.GET)
  @ResponseBody
  @ApiOperation("查询单个对象")
  public ObjectRestResponse<Menu> get(@PathVariable String id){
    ObjectRestResponse<Menu> entityObjectRestResponse = new ObjectRestResponse<>();
    Object o = baseBiz.selectById(id);
    entityObjectRestResponse.data((Menu)o);
    return entityObjectRestResponse;
  }

    @ApiOperation("菜单树查询")
    @RequestMapping(value = "/menuTree", method = RequestMethod.GET)
    public ObjectRestResponse getMenuTree(String name,String type) {

        Example example = new Example(Menu.class);
        example.setOrderByClause("order_num asc");
        List<Menu> all=baseBiz.selectByExample(example);
        List<Menu> matched=all.stream().filter(menu->menu.getTitle().contains(name)&&menu.getType().contains(type)).collect(Collectors.toList());

        List<Menu> result=new ArrayList<>();
        if(all.size()==matched.size()){
            result=all;
        }else{
            for(Menu o:matched){
                boolean flag=false;
                for (Menu dt:result){
                    if(dt.getId().equals(o.getId())){
                        flag=true;
                        break;
                    }
                }
                if(!flag){
                    result.add(o);
                    handlePid(o, all, result);
                }
            }
        }

        List<MenuTree> trees = new ArrayList<>();
        result.forEach(Menu -> {
            trees.add(new MenuTree(Menu.getId(),  Menu.getTitle(),Menu.getParentId(),Menu.getIcon(),Menu.getOrderNum(),Menu.getCode(), Menu.getType(),Menu.getHref(),Menu.getCrtTime(),Menu.getFunType()));
        });
        List<MenuTree> list= TreeUtil.bulid(trees, "-1", null);
        return ObjectRestResponse.ok(list);

    }

    @ApiOperation("菜单信息查询")
    @RequestMapping(value = "/menulist", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse getBaseMenuList(String id) {
        Menu menu=baseBiz.selectById(id);
        return ObjectRestResponse.ok(menu);
    }

    public void handlePid(Menu arg, List<Menu> all, List<Menu> result){
        for(Menu item:all){
            if(item.getId().equals(arg.getParentId())){
                boolean flag=false;
                for (Menu o:result){
                    if(o.getId().equals(item.getId())){
                        flag=true;
                        break;
                    }
                }
                if(!flag)   result.add(item);
                if(!"-1".equals(item.getParentId()))    handlePid(item,all,result);
                break;
            }
        }
    }

}
