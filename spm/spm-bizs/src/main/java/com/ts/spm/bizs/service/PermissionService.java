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

package com.ts.spm.bizs.service;

import com.ace.cache.annotation.Cache;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.util.Sha256PasswordEncoder;
import com.github.wxiaoqi.security.common.util.TreeUtil;
import com.ts.spm.bizs.biz.admin.EmployeeBiz;
import com.ts.spm.bizs.biz.admin.MenuBiz;
import com.ts.spm.bizs.biz.admin.UserBiz;
import com.ts.spm.bizs.util.AdminCommonConstant;
import com.ts.spm.bizs.entity.admin.Element;
import com.ts.spm.bizs.entity.admin.Employee;
import com.ts.spm.bizs.entity.admin.Menu;
import com.ts.spm.bizs.entity.admin.User;
import com.ts.spm.bizs.vo.admin.FrontUser;
import com.ts.spm.bizs.vo.admin.MenuTree;
import com.ts.spm.bizs.vo.admin.api.PermissionInfo;
import com.ts.spm.bizs.vo.admin.api.UserInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ace on 2017/9/12.
 */
@Service
public class PermissionService {
    @Autowired
    private UserBiz userBiz;
    @Autowired
    private MenuBiz menuBiz;
    @Autowired
    private EmployeeBiz employeeBiz;

    private Sha256PasswordEncoder encoder = new Sha256PasswordEncoder();

    public UserInfo getUserByUsername(String username) {
        UserInfo info = new UserInfo();
        User user = userBiz.getUserByUsername(username);
        user.setName(employeeBiz.selectById(user.getEmployId()).getName());
        BeanUtils.copyProperties(user, info);
        info.setId(user.getId().toString());
        return info;
    }

    public UserInfo validate(String username, String password) {
        UserInfo info = new UserInfo();
        User user = userBiz.getUserByUsername(username);
        Employee employee = employeeBiz.selectById(user.getEmployId());
        if(employee!=null){
            user.setName(employee.getName());
        }
        if (encoder.matches(password, user.getPassword())) {
            BeanUtils.copyProperties(user, info);
            info.setId(user.getId().toString());
        }
        return info;
    }

    public List<PermissionInfo> getAllPermission() {
        List<Menu> menus = menuBiz.selectListAll();
        List<PermissionInfo> result = new ArrayList<PermissionInfo>();
        menu2permission(menus, result);
//        List<Element> elements = elementBiz.getAllElementPermissions();
//        element2permission(result, elements);
        return result;
    }

    private void menu2permission(List<Menu> menus, List<PermissionInfo> result) {
        PermissionInfo info;
        for (Menu menu : menus) {
//            if (StringUtils.isBlank(menu.getHref())) {
//                menu.setHref("/" + menu.getCode());
//            }
            info = new PermissionInfo();
            info.setCode(menu.getCode());
            if(menu.getType().equalsIgnoreCase(AdminCommonConstant.RESOURCE_TYPE_BTN) || menu.getType().equalsIgnoreCase(AdminCommonConstant.RESOURCE_TYPE_URI)) {
                info.setType(menu.getType());
            } else {
                info.setType(AdminCommonConstant.RESOURCE_TYPE_MENU);
            }
            info.setName(AdminCommonConstant.RESOURCE_ACTION_VISIT);
            String uri = menu.getHref();
            if (uri != null && !uri.startsWith("/")) {
                uri = "/" + uri;
            }
            info.setUri(uri);
            info.setMethod(AdminCommonConstant.RESOURCE_REQUEST_METHOD_GET);
            info.setMenu(menu.getTitle());
            result.add(info);
        }
    }

    @Cache(key="permission:u{1}")
    public List<PermissionInfo> getPermissionByUsername(String username) {
        List<Menu> menus = menuBiz.getUserAuthorityMenuByUserId(BaseContextHandler.getUserID());
        List<PermissionInfo> result = new ArrayList<PermissionInfo>();
        menu2permission(menus, result);
//        List<Element> elements = elementBiz.getAuthorityElementByUserId(BaseContextHandler.getUserID());
//        element2permission(result, elements);
        return result;
    }

    private void element2permission(List<PermissionInfo> result, List<Element> elements) {
        PermissionInfo info;
        for (Element element : elements) {
            info = new PermissionInfo();
            info.setCode(element.getCode());
            info.setType(element.getType());
            info.setUri(element.getUri());
            info.setMethod(element.getMethod());
            info.setName(element.getName());
            info.setMenu(element.getMenuId());
            result.add(info);
        }
    }


    private List<MenuTree> getMenuTree(List<Menu> menus, String root) {
        List<MenuTree> trees = new ArrayList<MenuTree>();
        MenuTree node = null;
        for (Menu menu : menus) {
            node = new MenuTree();
            BeanUtils.copyProperties(menu, node);
            trees.add(node);
        }
        return TreeUtil.bulid(trees, root, new Comparator<MenuTree>() {
            @Override
            public int compare(MenuTree o1, MenuTree o2) {
                return o1.getOrderNum() - o2.getOrderNum();
            }
        });
    }

    public FrontUser getUserInfo() throws Exception {
        String username = BaseContextHandler.getUsername();
        if (username == null) {
            return null;
        }
        UserInfo user = this.getUserByUsername(username);
        FrontUser frontUser = new FrontUser();
        BeanUtils.copyProperties(user, frontUser);
        List<PermissionInfo> permissionInfos = this.getPermissionByUsername(username);
        Stream<PermissionInfo> menus = permissionInfos.parallelStream().filter((permission) -> {
            return permission.getType().equals(AdminCommonConstant.RESOURCE_TYPE_MENU);
        });
        frontUser.setMenus(menus.collect(Collectors.toList()));
        Stream<PermissionInfo> elements = permissionInfos.parallelStream().filter((permission) -> {
            return !permission.getType().equals(AdminCommonConstant.RESOURCE_TYPE_MENU);
        });
        frontUser.setElements(elements.collect(Collectors.toList()));

        User userInfo = userBiz.getUserByUsername(username);
        frontUser.setEmployId(userInfo.getEmployId());
        return frontUser;
    }

    public List<MenuTree> getMenusByUsername() throws Exception {
        List<Menu> menus = menuBiz.getUserAuthorityMenuByUserId(BaseContextHandler.getUserID());
        return getMenuTree(menus, AdminCommonConstant.ROOT);
    }
}
