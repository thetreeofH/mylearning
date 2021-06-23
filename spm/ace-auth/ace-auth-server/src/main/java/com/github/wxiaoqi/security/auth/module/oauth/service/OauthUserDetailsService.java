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

package com.github.wxiaoqi.security.auth.module.oauth.service;

import com.github.wxiaoqi.security.auth.constant.ClientAccountConstant;
import com.github.wxiaoqi.security.auth.feign.IUserService;
import com.github.wxiaoqi.security.auth.module.client.service.AuthService;
import com.github.wxiaoqi.security.auth.module.oauth.bean.OauthUser;
import com.github.wxiaoqi.security.auth.module.oauth.custom.exception.AppBindedException;
import com.github.wxiaoqi.security.auth.module.oauth.custom.exception.AppLoginBindParamsException;
import com.github.wxiaoqi.security.auth.module.oauth.custom.exception.LoginException;
import com.github.wxiaoqi.security.auth.module.oauth.vo.AppBind;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by ace on 2017/8/11.
 */
@Slf4j
@Component
public class OauthUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserService userService;


    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private AuthService authService;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("用户名为空");
        }
        ObjectRestResponse<Map<String, String>> response = userService.getUserInfoByUsername(username);
        Map<String, String> data = response.getData();
        if (StringUtils.isEmpty(data.get("id"))) {
            throw new UsernameNotFoundException("用户名不合法");
        }

        if("-1".equals(data.get("status"))){
            throw new LockedException("帐号已冻结");
        }

        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();


        //设置登陆后，原有的token全部失效
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        redisTokenStore.setPrefix("AG:OAUTH:");
        String authorization = request.getHeader("Authorization");
        final String[] clients = extractAndDecodeHeader(authorization, request);
        String clientAccount = clients[0];

        // 移动端设备绑定校验
        this.checkAppDeviceBindInfo(username, data, request, clientAccount);

        Collection<OAuth2AccessToken> oAuth2AccessTokens = redisTokenStore.findTokensByClientIdAndUserName(clients[0], data.get("username"));
        for (OAuth2AccessToken token : oAuth2AccessTokens) {
            redisTokenStore.removeAccessToken(token);
            try {
                authService.invalid(token.getValue().toString());
            }catch (Exception e){
                throw new LoginException("清空用户失败");
            }
        }
        //设置登陆后，原有的token全部失效   end
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        userService.savelog("用户","登录","api/auth/oauth/token",data.get("id"),data.get("tenantId"),data.get("departId"));
        OauthUser user = new OauthUser(data.get("id"), data.get("username"), data.get("password"), data.get("name"), data.get("departId"), data.get("isSuperAdmin"), data.get("tenantId"),
                authorities);
        return user;
    }

    /**
     * 移动端设备绑定校验
     * @param username
     * @param data
     * @param request
     * @param clientAccount
     * @throws Exception
     */
    private void checkAppDeviceBindInfo(String username, Map<String, String> data, HttpServletRequest request, String clientAccount) throws Exception {
        // 如果是APP端则校验设备编码
        if(ClientAccountConstant.APP.equals(clientAccount)){
            String deviceId = request.getParameter("deviceId");
            if(StringUtils.isEmpty(deviceId)){
                throw new AppLoginBindParamsException("设备编码不能为空！");

            }
            ObjectRestResponse<AppBind> appBindInfoResponse = userService.getAppBindingInfoByUserId(data.get("id"));
            AppBind appBind =  appBindInfoResponse.getData();
            if(appBind==null){
                // 初次绑定设备编码
                ObjectRestResponse<Boolean> appDeviceBindResponse = userService.appDeviceBind(deviceId,data.get("id"));
                if(!appDeviceBindResponse.getData()){
                    log.warn("用户：{}，设备编码初次绑定失败",username);
                }else{
                    log.info("用户{}，设备编码初次绑定完成。",username);
                }
            }else{
                // 非初次绑定，对编码进行校验
                if(!appBind.getDeviceId().equals(deviceId)){
                    throw new AppBindedException("该账号已有绑定设备，请联系管理员！");
                }
            }



        }
    }


    /**
     *将标题解码为用户名和密码。
     *
     * @throws BadCredentialsException 如果基本头不存在或无效
     * Base64
     */
    private String[] extractAndDecodeHeader(String header, HttpServletRequest request)
            throws IOException {

        byte[] base64Token = header.substring(6).getBytes("UTF-8");
        byte[] decoded;
        try {
            decoded = Base64.decode(base64Token);
        }
        catch (IllegalArgumentException e) {
            throw new BadCredentialsException(
                    "解码基本身份验证令牌失败");
        }

        String token = new String(decoded, "UTF-8");

        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new BadCredentialsException("无效的基本身份验证令牌");
        }
        return new String[] { token.substring(0, delim), token.substring(delim + 1) };
    }
}
