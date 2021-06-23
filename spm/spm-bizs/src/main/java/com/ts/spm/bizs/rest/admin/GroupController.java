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

import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.TreeUtil;
import com.ts.spm.bizs.biz.admin.GroupBiz;
import com.ts.spm.bizs.biz.admin.ResourceAuthorityBiz;
import com.ts.spm.bizs.util.AdminCommonConstant;
import com.ts.spm.bizs.entity.admin.Element;
import com.ts.spm.bizs.entity.admin.Group;
import com.ts.spm.bizs.entity.admin.Menu;
import com.ts.spm.bizs.vo.admin.AuthorityMenuTree;
import com.ts.spm.bizs.vo.admin.GroupTree;
import com.ts.spm.bizs.vo.admin.GroupUsers;
import com.ts.spm.bizs.vo.admin.MenuTree;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @version 2017-06-12 8:49
 */
@RestController
@RequestMapping("group")
@Api("角色模块")
@CheckUserToken
@CheckClientToken
public class GroupController extends BaseController<GroupBiz, Group,String> {
    @Autowired
    private ResourceAuthorityBiz resourceAuthorityBiz;
    @ApiOperation("获取角色列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<Group> list(String name, String groupType) {
        if (StringUtils.isBlank(name) && StringUtils.isBlank(groupType)) {
            return new ArrayList<Group>();
        }
        Example example = new Example(Group.class);
        if (StringUtils.isNotBlank(name)) {
            example.createCriteria().andLike("name", "%" + name + "%");
        }
        if (StringUtils.isNotBlank(groupType)) {
            example.createCriteria().andEqualTo("groupType", groupType);
        }

        return baseBiz.selectByExample(example);
    }

    @ApiOperation("用户关联角色")
    @RequestMapping(value = "/{id}/user", method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse modifiyUsers(@PathVariable String id, String members, String leaders) {
        baseBiz.modifyGroupUsers(id, members, leaders);
        return new ObjectRestResponse();
    }

    @ApiOperation("获取角色关联用户")
    @RequestMapping(value = "/{id}/user", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<GroupUsers> getUsers(@PathVariable String id) {
        return new ObjectRestResponse<GroupUsers>().data(baseBiz.getGroupUsers(id));
    }

    @ApiOperation("分配角色可访问菜单")
    @RequestMapping(value = "/{id}/authority/menu", method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse modifyMenuAuthority(@PathVariable String id, String menuTrees) {
        String[] menus = menuTrees.split(",");
        baseBiz.modifyAuthorityMenu(id, menus, AdminCommonConstant.RESOURCE_TYPE_VIEW);
        return new ObjectRestResponse();
    }

    @ApiOperation("获取角色可访问菜单")
    @RequestMapping(value = "/{id}/authority/menu", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<List<AuthorityMenuTree>> getMenuAuthority(@PathVariable String id) {
        return new ObjectRestResponse().data(baseBiz.getAuthorityMenu(id, AdminCommonConstant.RESOURCE_TYPE_VIEW));
    }

    @ApiOperation("角色分配菜单可访问资源")
    @RequestMapping(value = "/{id}/authority/element/add", method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse addElementAuthority(@PathVariable String id, String menuId, String elementId) {
        baseBiz.modifyAuthorityElement(id, menuId, elementId, AdminCommonConstant.RESOURCE_TYPE_VIEW);
        return new ObjectRestResponse();
    }

    @ApiOperation("角色移除菜单可访问资源")
    @RequestMapping(value = "/{id}/authority/element/remove", method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse removeElementAuthority(@PathVariable String id, String menuId, String elementId) {
        baseBiz.removeAuthorityElement(id, elementId, AdminCommonConstant.RESOURCE_TYPE_VIEW);
        return new ObjectRestResponse();
    }

    @ApiOperation("获取角色可访问资源")
    @RequestMapping(value = "/{id}/authority/element", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<List<String>> getElementAuthority(@PathVariable String id) {
        return new ObjectRestResponse().data(baseBiz.getAuthorityElement(id, AdminCommonConstant.RESOURCE_TYPE_VIEW));
    }

    @ApiOperation("分配角色可授权菜单")
    @RequestMapping(value = "/{id}/authorize/menu", method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse modifyMenuAuthorize(@PathVariable String id, String menuTrees) {
        String[] menus = menuTrees.split(",");
        baseBiz.modifyAuthorityMenu(id, menus, AdminCommonConstant.RESOURCE_TYPE_AUTHORISE);
        return new ObjectRestResponse();
    }

    @ApiOperation("获取角色可授权菜单")
    @RequestMapping(value = "/{id}/authorize/menu", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<List<AuthorityMenuTree>> getMenuAuthorize(@PathVariable String id) {
        return new ObjectRestResponse().data(baseBiz.getAuthorityMenu(id, AdminCommonConstant.RESOURCE_TYPE_AUTHORISE));
    }

    @ApiOperation("角色分配菜单可授权资源")
    @RequestMapping(value = "/{id}/authorize/element/add", method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse addElementAuthorize(@PathVariable String id, String menuId, String elementId) {
        baseBiz.modifyAuthorityElement(id, menuId, elementId, AdminCommonConstant.RESOURCE_TYPE_AUTHORISE);
        return new ObjectRestResponse();
    }

    @ApiOperation("角色移除菜单可授权资源")
    @RequestMapping(value = "/{id}/authorize/element/remove", method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse removeElementAuthorize(@PathVariable String id, String menuId, String elementId) {
        baseBiz.removeAuthorityElement(id, elementId, AdminCommonConstant.RESOURCE_TYPE_AUTHORISE);
        return new ObjectRestResponse();
    }

    @ApiOperation("获取角色可授权资源")
    @RequestMapping(value = "/{id}/authorize/element", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<List<String>> getElementAuthorize(@PathVariable String id) {
        return new ObjectRestResponse().data(baseBiz.getAuthorityElement(id, AdminCommonConstant.RESOURCE_TYPE_AUTHORISE));
    }

    @ApiOperation("获取角色树")
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    @ResponseBody
    public List<GroupTree> tree(String name, String groupType) {
        Example example = new Example(Group.class);
        if (StringUtils.isNotBlank(name)) {
            example.createCriteria().andLike("name", "%" + name + "%");
        }
        if (StringUtils.isNotBlank(groupType)) {
            example.createCriteria().andEqualTo("groupType", groupType);
        }
        return getTree(baseBiz.selectByExample(example), AdminCommonConstant.ROOT);
    }

    /**
     * 获取可管理的资源
     * @param menuId
     * @return
     */
    @ApiOperation("获取用户可管理资源")
    @RequestMapping(value = "/element/authorize/list", method = RequestMethod.GET)
    public TableResultResponse<Element> getAuthorizeElement(String menuId) {
        List<Element> elements = baseBiz.getAuthorizeElements(menuId);
        return new TableResultResponse<Element>(elements.size(), elements);
    }

    /**
     * 获取可管理的菜单
     * @return
     */
    @ApiOperation("获取用户可管理菜单")
    @RequestMapping(value = "/menu/authorize/list", method = RequestMethod.GET)
    public List<MenuTree> getAuthorizeMenus() {
        return TreeUtil.bulid(baseBiz.getAuthorizeMenus(), AdminCommonConstant.ROOT, null);
    }

    private List<GroupTree> getTree(List<Group> groups, String root) {
        List<GroupTree> trees = new ArrayList<GroupTree>();
        GroupTree node = null;
        for (Group group : groups) {
            node = new GroupTree();
            node.setLabel(group.getName());
            BeanUtils.copyProperties(group, node);
            trees.add(node);
        }
        return TreeUtil.bulid(trees, root, null);
    }


    /**
     *
     * @return
     */
    @GetMapping(value = "info/groupList")
    public ObjectRestResponse groupList(){
        Group group=new Group();
        group.setParentId("-1");
        group.setGroupType("org");
        List<Group> groups=baseBiz.selectList(group);
        return ObjectRestResponse.ok(groups);
    }

    @ApiOperation("权限列表查询")
    @RequestMapping(value = "/list2", method = RequestMethod.GET)
    public TableResultResponse getRoleList(String name,String status,
                                           @RequestParam(name = "limit", defaultValue = "10") int limit,
                                           @RequestParam(name = "page", defaultValue = "1") int page){

        String isSuperAdmin= BaseContextHandler.getIsSuperAdmin();
        if(isSuperAdmin.equals("1")){
            Example example = new Example(Group.class);
            Example.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(name)) {
                criteria.andLike("name", "%" + name + "%");
            }
            if(StringUtils.isNotBlank(status)){
                criteria.andEqualTo("status",status);
            }
            criteria.andNotEqualTo("isDeleted", 1);
            example.setOrderByClause("crt_time desc");

            Page<Group> result = PageHelper.startPage(page, limit);
            List<Group> list = baseBiz.selectByExample(example);
            return  new TableResultResponse(result.getTotal(),list);
        }else{
            Page<Object> result = PageHelper.startPage(page, limit);

            List<Group> list=baseBiz.getGroups(name,status);
            return new TableResultResponse(result.getTotal(),list);
        }

    }

    @ApiOperation("权限新增")
    @RequestMapping(value = "/roleInfo", method = RequestMethod.POST)
    public ObjectRestResponse addRole(@RequestBody Group group){
        group.setIsDeleted("0");
        group.setCrtTime(new Date());
        group.setStatus("0");
        group.setCrtUserId(BaseContextHandler.getUserID());
        group.setCrtUserName(BaseContextHandler.getUsername());
        group.setGroupType("org");
        group.setParentId("-1");
        group.setTenantId(BaseContextHandler.getTenantID());
        baseBiz.insertSelective(group);

        return ObjectRestResponse.ok();
    }

    @ApiOperation("权限修改")
    @RequestMapping(value = "/roleInfo/{id}", method = RequestMethod.PUT)
    public ObjectRestResponse updateRole(@RequestBody Group group,@PathVariable String id){
        Group entity=this.baseBiz.selectById(id);
        entity.setCode(group.getCode());
        entity.setName(group.getName());
        entity.setDescription(group.getDescription());
        entity.setUpdTime(new Date());
        entity.setUpdUserId(BaseContextHandler.getUserID());
        entity.setUpdUserName(BaseContextHandler.getUsername());
        baseBiz.updateSelectiveById(entity);
        return ObjectRestResponse.ok();
    }

    @ApiOperation("权限删除")
    @RequestMapping(value = "/roleInfo/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse delRole(@PathVariable String id){
        String[] ids = id.split(",");
        for(String roleIds:ids){
            Group entity=this.baseBiz.selectById(roleIds);
            entity.setIsDeleted("1");
            entity.setUpdTime(new Date());
            entity.setUpdUserId(BaseContextHandler.getUserID());
            entity.setUpdUserName(BaseContextHandler.getUsername());
            baseBiz.updateSelectiveById(entity);
        }

        return ObjectRestResponse.ok();
    }

    @ApiOperation("权限状态修改")
    @RequestMapping(value = "/{id}/{status}", method = RequestMethod.POST)
    public ObjectRestResponse updateRoleState(@PathVariable String id,@PathVariable String status){
        Group entity=this.baseBiz.selectById(id);
        entity.setStatus(status);
        entity.setUpdTime(new Date());
        entity.setUpdUserId(BaseContextHandler.getUserID());
        entity.setUpdUserName(BaseContextHandler.getUsername());
        baseBiz.updateSelectiveById(entity);
        return ObjectRestResponse.ok();
    }

    @ApiOperation("权限菜单查询")
    @RequestMapping(value = "/menulist/{id}", method = RequestMethod.GET)
    public ObjectRestResponse getRoleMenu(@PathVariable String id){

        List<Menu> menus = baseBiz.getGroupMenus(id);
        List<MenuTree> trees = new ArrayList<>();
        menus.forEach(dictType -> {
            trees.add(new MenuTree(dictType.getId(),dictType.getTitle(),dictType.getParentId(), dictType.getCode()));
        });

        List<MenuTree> menuTree= TreeUtil.bulid(trees, "-1", null);

        return ObjectRestResponse.ok(menuTree);
    }

    @ApiOperation("获取所有角色")
    @RequestMapping(value = "/rolelist", method = RequestMethod.GET)
    public ObjectRestResponse getRoleList(){

        String isSuperAdmin= BaseContextHandler.getIsSuperAdmin();
        if(isSuperAdmin.equals("1")){
            Example example = new Example(Group.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("status","0");
            criteria.andNotEqualTo("isDeleted", 1);
            example.setOrderByClause("crt_time desc");
            List<Group> list = baseBiz.selectByExample(example);

            for(Group group:list){
                group.setDisabled(false);
            }
            return ObjectRestResponse.ok(list);
        }else{
            Example example = new Example(Group.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("status","0");
            criteria.andNotEqualTo("isDeleted", 1);
            example.setOrderByClause("crt_time desc");
            List<Group> list = baseBiz.selectByExample(example);

            List<Group> groupList=baseBiz.getGroups("","0");

            for(Group group:list){
                for(Group group1:groupList){
                    if(group1.getId().equals(group.getId())){
                        group.setDisabled(false);
                    }else{
                        group.setDisabled(true);
                    }

                }
            }
            return ObjectRestResponse.ok(list);
        }
    }


    @ApiOperation("用户菜单保存")
    @RequestMapping(value = "/menu/{id}/{menuIds}", method = RequestMethod.POST)
    public ObjectRestResponse addUserMenu(@PathVariable String id,@PathVariable String menuIds){

        baseBiz.delGroupMenu(id);
        baseBiz.addGroupMenu(id,menuIds);

        List<Menu> menus = baseBiz.getGroupMenus(id);
        List<MenuTree> trees = new ArrayList<>();
        menus.forEach(dictType -> {
            trees.add(new MenuTree(dictType.getId(),dictType.getTitle(),dictType.getParentId(), dictType.getCode()));
        });

        List<MenuTree> menuTree= TreeUtil.bulid(trees, "-1", null);

        return ObjectRestResponse.ok(menuTree);
    }

    @ApiOperation("获取角色是否存在用户")
    @RequestMapping(value = "/getUserByRoleId", method = RequestMethod.GET)
    public ObjectRestResponse getUserByRoleId(String roleId){
        int count=baseBiz.getUserByRoleId(roleId);
        Boolean ifUser=false;
        if(count>0){
            ifUser=true;
        }
        return ObjectRestResponse.ok(ifUser);
    }
}
