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

package com.github.wxiaoqi.security.auth.feign;

import com.github.wxiaoqi.security.auth.configuration.FeignConfiguration;
import com.github.wxiaoqi.security.auth.module.oauth.vo.AppBind;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @version 2017-06-21 8:11
 */
@FeignClient(value = "${jwt.user-service}",configuration = FeignConfiguration.class)
public interface IUserService {
  /**
   * 通过账户\密码的方式登陆
   * @param username
   * @param password
   * @return
     */
  @RequestMapping(value = "/user/validate", method = RequestMethod.POST)
  public ObjectRestResponse<Map<String,String>> validate(@RequestParam("username") String username, @RequestParam("password") String password);
  @RequestMapping(value = "/user/info", method = RequestMethod.POST)
  public ObjectRestResponse<Map<String,String>>  getUserInfoByUsername(@RequestParam("username") String username);

  /**
   * 日志添加
   * @param menu（模块名称 如：用户管理）
   * @param opt（操作内容 如：用户添加）
   * @param url （接口地址 如：user/info/addUser）
   */
  @PostMapping(value = "gateLog/sign/savelog")
  void  savelog(@RequestParam("menu")String menu,@RequestParam("opt")String opt,@RequestParam("url")String url,@RequestParam("userid")String userid,@RequestParam("tenantid")String tenantid,@RequestParam("departid")String departid);

  /**
   * 通过用户ID获取APP绑定信息
   * @param userId
   * @return
   */
  @RequestMapping(value = "/app/binding/byuser", method = RequestMethod.GET)
  public ObjectRestResponse<AppBind>  getAppBindingInfoByUserId(@RequestParam("userId") String userId);

  /**
   * 绑定用户设备编码
   * @param deviceId
   * @param userId
   * @return
   */
  @RequestMapping(value = "/app/binding/add", method = RequestMethod.POST)
  public ObjectRestResponse<Boolean>  appDeviceBind(@RequestParam("deviceId") String deviceId,@RequestParam("userId") String userId);


}
