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

package com.ts.spm.bizs.biz.admin;

import com.ace.cache.annotation.CacheClear;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.merge.annonation.MergeResult;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.util.AdminCommonConstant;
import com.ts.spm.bizs.entity.admin.*;
import com.ts.spm.bizs.mapper.admin.*;
import com.ts.spm.bizs.vo.admin.AuthorityMenuTree;
import com.ts.spm.bizs.vo.admin.GroupUsers;
import com.ts.spm.bizs.vo.admin.MenuTree;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @version 2017-06-12 8:48
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GroupBiz extends BusinessBiz<GroupMapper, Group> {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ResourceAuthorityBiz resourceAuthorityBiz;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private ElementMapper elementMapper;
    @Autowired
    private GroupUserMapper groupUserMapper;

    @Override
    public void insertSelective(Group entity) {
        if (AdminCommonConstant.ROOT.equals(entity.getParentId())) {
            entity.setPath("/" + entity.getCode());
        } else {
            Group parent = this.selectById(entity.getParentId());
            entity.setPath(parent.getPath() + "/" + entity.getCode());
        }
        super.insertSelective(entity);
    }

    @Override
    public void updateById(Group entity) {
        if (AdminCommonConstant.ROOT == entity.getParentId()) {
            entity.setPath("/" + entity.getCode());
        } else {
            Group parent = this.selectById(entity.getParentId());
            entity.setPath(parent.getPath() + "/" + entity.getCode());
        }
        super.updateById(entity);
    }

    /**
     * 获取群组关联用户
     *
     * @param groupId
     * @return
     */
    public GroupUsers getGroupUsers(String groupId) {
        return new GroupUsers(userMapper.selectMemberByGroupId(groupId), userMapper.selectLeaderByGroupId(groupId));
    }

    /**
     * 变更群主所分配用户
     *
     * @param groupId
     * @param members
     * @param leaders
     */
    @CacheClear(pre = "permission")
    public void modifyGroupUsers(String groupId, String members, String leaders) {
        mapper.deleteGroupLeadersById(groupId);
        mapper.deleteGroupMembersById(groupId);
        if (!StringUtils.isEmpty(members)) {
            String[] mem = members.split(",");
            for (String m : mem) {
                mapper.insertGroupMembersById(UUIDUtils.generateUuid(), groupId, m, BaseContextHandler.getTenantID());
            }
        }
        if (!StringUtils.isEmpty(leaders)) {
            String[] mem = leaders.split(",");
            for (String m : mem) {
                mapper.insertGroupLeadersById(UUIDUtils.generateUuid(), groupId, m, BaseContextHandler.getTenantID());
            }
        }
    }

    @CacheClear(pre = "permission")
    public void newmodifyGroupUsers(String groupId,String leaders) {
        //先删除当前用户的所有角色
        mapper.deleteGroupLeadersByUserid(leaders);
        //判断角色id不能为空
        if (!StringUtils.isEmpty(groupId)) {
            String[] mem = groupId.split(",");
            for (String m : mem) {
                mapper.insertGroupLeadersById(UUIDUtils.generateUuid(), m, leaders, BaseContextHandler.getTenantID());
            }
        }
    }

    /**
     * 变更群组关联的菜单
     *
     * @param groupId
     * @param menus
     */
    @CacheClear(keys = {"permission:menu", "permission:u"})
    public void modifyAuthorityMenu(String groupId, String[] menus, String type) {
        resourceAuthorityBiz.deleteByAuthorityIdAndResourceType(groupId + "", AdminCommonConstant.RESOURCE_TYPE_MENU, type);
        List<Menu> menuList = menuMapper.selectAll();
        Map<String, String> map = new HashMap<String, String>();
        for (Menu menu : menuList) {
            map.put(menu.getId().toString(), menu.getParentId().toString());
        }
        Set<String> relationMenus = new HashSet<String>();
        relationMenus.addAll(Arrays.asList(menus));
        ResourceAuthority authority = null;
        for (String menuId : menus) {
            findParentID(map, relationMenus, menuId);
        }
        for (String menuId : relationMenus) {
            authority = new ResourceAuthority(AdminCommonConstant.AUTHORITY_TYPE_GROUP, AdminCommonConstant.RESOURCE_TYPE_MENU);
            authority.setAuthorityId(groupId + "");
            authority.setResourceId(menuId);
            authority.setParentId("-1");
            authority.setType(type);
            resourceAuthorityBiz.insertSelective(authority);
        }
    }

    private void findParentID(Map<String, String> map, Set<String> relationMenus, String id) {
        String parentId = map.get(id);
        if (String.valueOf(AdminCommonConstant.ROOT).equals(id) || parentId == null) {
            return;
        }
        relationMenus.add(parentId);
        findParentID(map, relationMenus, parentId);
    }

    /**
     * SimpleRouteLocator
     * 分配资源权限
     *
     * @param groupId
     * @param menuId
     * @param elementId
     */
    @CacheClear(keys = {"permission:ele", "permission:u"})
    public void modifyAuthorityElement(String groupId, String menuId, String elementId, String type) {
        ResourceAuthority authority = new ResourceAuthority(AdminCommonConstant.AUTHORITY_TYPE_GROUP, AdminCommonConstant.RESOURCE_TYPE_BTN);
        authority.setAuthorityId(groupId + "");
        authority.setResourceId(elementId + "");
        authority.setParentId("-1");
        authority.setType(type);
        resourceAuthorityBiz.insertSelective(authority);
    }

    /**
     * 移除资源权限
     *
     * @param groupId
     * @param elementId
     */
    @CacheClear(keys = {"permission:ele", "permission:u"})
    public void removeAuthorityElement(String groupId, String elementId, String type) {
        ResourceAuthority authority = new ResourceAuthority();
        authority.setAuthorityId(groupId + "");
        authority.setResourceId(elementId + "");
        authority.setParentId("-1");
        authority.setType(type);
        resourceAuthorityBiz.delete(authority);
    }


    /**
     * 获取群主关联的菜单
     *
     * @param groupId
     * @return
     */
    public List<AuthorityMenuTree> getAuthorityMenu(String groupId, String type) {
        List<Menu> menus = menuMapper.selectMenuByAuthorityId(String.valueOf(groupId), AdminCommonConstant.AUTHORITY_TYPE_GROUP, type);
        List<AuthorityMenuTree> trees = new ArrayList<AuthorityMenuTree>();
        AuthorityMenuTree node = null;
        for (Menu menu : menus) {
            node = new AuthorityMenuTree();
            node.setText(menu.getTitle());
            BeanUtils.copyProperties(menu, node);
            trees.add(node);
        }
        return trees;
    }

    /**
     * 获取群组关联的资源
     *
     * @param groupId
     * @return
     */
    public List<String> getAuthorityElement(String groupId, String type) {
        ResourceAuthority authority = new ResourceAuthority(AdminCommonConstant.AUTHORITY_TYPE_GROUP, AdminCommonConstant.RESOURCE_TYPE_BTN);
        authority.setAuthorityId(groupId);
        authority.setType(type);
        List<ResourceAuthority> authorities = resourceAuthorityBiz.selectList(authority);
        List<String> ids = new ArrayList<String>();
        for (ResourceAuthority auth : authorities) {
            ids.add(auth.getResourceId());
        }
        return ids;
    }

    /**
     * 获取当前管理员可以分配的菜单
     * @return
     */
    public List<MenuTree> getAuthorizeMenus() {
//        if (BooleanUtil.BOOLEAN_TRUE.equals(userMapper.selectByPrimaryKey(BaseContextHandler.getUserID()).getIsSuperAdmin())) {
//            return MenuTree.buildTree(menuMapper.selectAll(), AdminCommonConstant.ROOT);
//        }
//        return MenuTree.buildTree(menuMapper.selectAuthorityMenuByUserId(BaseContextHandler.getUserID(), AdminCommonConstant.RESOURCE_TYPE_AUTHORISE), AdminCommonConstant.ROOT);
      return MenuTree.buildTree(menuMapper.selectAll(), AdminCommonConstant.ROOT);
    }

    /**
     * 获取当前管理员可以分配的资源
     * @param menuId
     * @return
     */
    @MergeResult
    public List<Element> getAuthorizeElements(String menuId) {
//        if (BooleanUtil.BOOLEAN_TRUE.equals(userMapper.selectByPrimaryKey(BaseContextHandler.getUserID()).getIsSuperAdmin())) {
//            Example example = new Example(Element.class);
//            Example.Criteria criteria = example.createCriteria();
//            criteria.andEqualTo("menuId", menuId);
//            return elementMapper.selectByExample(example);
//        }
//        return elementMapper.selectAuthorityMenuElementByUserId(BaseContextHandler.getUserID(),menuId,AdminCommonConstant.RESOURCE_TYPE_AUTHORISE);
            Example example = new Example(Element.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("menuId", menuId);
            return elementMapper.selectByExample(example);
    }

    public  List<Group> groupByuserid(String id){
        return mapper.groupByuserid(id);
    }

    public List<Group> getGroups(String name, String status){
        return mapper.getUsersRole(BaseContextHandler.getUserID(),name,status);
    }

    public List<Menu> getGroupMenus(String groupId){
        return menuMapper.getGroupMenu(groupId);
    }

    @CacheClear(keys = {"permission:menu", "permission"})
    public void delGroupMenu(String groupId){
        resourceAuthorityBiz.delGroupMenu(groupId);
    }

    @CacheClear(keys = {"permission:menu", "permission"})
    public void addGroupMenu(String groupId,String menuIds){
        String[] menus = menuIds.split(",");
        for(String menuId:menus){
            ResourceAuthority ud=new ResourceAuthority();
            ud.setId(UUIDUtils.generateUuid());
            ud.setType(AdminCommonConstant.RESOURCE_TYPE_VIEW);
            ud.setAuthorityType(AdminCommonConstant.AUTHORITY_TYPE_GROUP);
            ud.setResourceType(AdminCommonConstant.RESOURCE_TYPE_MENU);
            ud.setCrtTime(new Date());
            ud.setCrtUser(BaseContextHandler.getUserID());
            ud.setCrtName(BaseContextHandler.getUsername());
            ud.setTenantId(BaseContextHandler.getTenantID());
            ud.setAuthorityId(groupId + "");
            ud.setResourceId(menuId);
            ud.setParentId("-1");

            resourceAuthorityBiz.insertSelective(ud);
        }
    }

    public int getUserByRoleId(String roleId){
        return mapper.getUserByRoleId(roleId);
    }

    List<Group> getUsersRole(String userId, String name,String status){
        return mapper.getUsersRole(userId,name,status);
    }

    @CacheClear(keys = {"permission:menu", "permission"})
    public void addGroup(String[] groupList,String userId){
        for(String group :groupList){
            GroupUser groupUser=new GroupUser();
            groupUser.setId(UUIDUtils.generateUuid());
            groupUser.setCrtTime(new Date());
            groupUser.setCrtUser(BaseContextHandler.getUserID());
            groupUser.setCrtName(BaseContextHandler.getUsername());
            groupUser.setTenantId(BaseContextHandler.getTenantID());
            groupUser.setGroupId(group);
            groupUser.setUserId(userId);
            groupUserMapper.addGroupUser(groupUser);
        }

    }

    @CacheClear(keys = {"permission:menu", "permission"})
    public void updateGroup(List<Group> groupList,String userId){
        for(Group group :groupList){
            GroupUser groupUser=new GroupUser();
            groupUser.setUpdTime(new Date());
            groupUser.setUpdUser(BaseContextHandler.getUserID());
            groupUser.setUpdName(BaseContextHandler.getUsername());
            groupUser.setGroupId(group.getId());
            groupUser.setTenantId(BaseContextHandler.getTenantID());
            groupUserMapper.updateGroupUser(groupUser,userId);
        }

    }

    @CacheClear(keys = {"permission:menu", "permission"})
    public void updateGroup(String[] groupList,String userId){
        for(String group :groupList){
            GroupUser groupUser=new GroupUser();
            groupUser.setUpdTime(new Date());
            groupUser.setUpdUser(BaseContextHandler.getUserID());
            groupUser.setUpdName(BaseContextHandler.getUsername());
            groupUser.setGroupId(group);
            groupUser.setTenantId(BaseContextHandler.getTenantID());
            groupUserMapper.updateGroupUser(groupUser,userId);
        }

    }

    @CacheClear(keys = {"permission:menu", "permission"})
    public void delUserGroup(String userId){
        groupUserMapper.delGroupUser(userId);
    }


}
