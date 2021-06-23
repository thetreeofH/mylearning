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

package com.ts.spm.bizs.mapper.admin;

import com.github.wxiaoqi.security.common.data.Tenant;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.admin.User;
import com.ts.spm.bizs.vo.admin.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Tenant()
public interface UserMapper extends CommonMapper<User> {
    public List<User> selectMemberByGroupId(@Param("groupId") String groupId);
    public List<User> selectLeaderByGroupId(@Param("groupId") String groupId);
    List<String> selectUserDataDepartIds(@Param("userId") String userId);
    UserVo userPositionbyID(@Param("userId") String userid);
    List<UserVo> userPage(@Param("item") List<String> item, @Param("jobcode") String jobcode, @Param("name") String name, @Param("type") String type, @Param("groupid") String groupid);
    List<UserVo> userbyjobcode(@Param("item") List<String> item, @Param("jobCategoryCode1") String jobCategoryCode1, @Param("jobCategoryCode2") String jobCategoryCode2, @Param("jobCategoryCode3") String jobCategoryCode3, @Param("jobCategoryCode4") String jobCategoryCode4, @Param("username") String username);


    //new
    List<UserVo> getUserList(@Param("employIds") List<String> employIds, @Param("loginname") String loginname, @Param("isDisabled") String isDisabled, @Param("departid") List<String> departid, @Param("roleName") String roleName, @Param("roleId") String roleId);

    public void updateUserStatus(@Param("id") String id, @Param("isDisabled") String isDisabled, @Param("updTime") Date updTime, @Param("updUserId") String updUserId, @Param("updUserName") String updUserName);

    public void addUser(@Param("user") User user);

    public void updateUser(@Param("user") UserVo user, @Param("userId") String userId);

    public void delUser(@Param("userId") String userId, @Param("updUserId") String updUserId, @Param("updUserName") String updUserName, @Param("updTime") Date updTime);

    public void updatePassword(@Param("userId") String userId, @Param("password") String password, @Param("updTime") Date updTime, @Param("updUserId") String updUserId, @Param("updUserName") String updUserName);


    List<User> getUserName(@Param("userName") String userName);
}
