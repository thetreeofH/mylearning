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

package com.github.wxiaoqi.security.auth.client.interceptor;

import com.alibaba.fastjson.JSON;
import com.github.ag.core.constants.CommonConstants;
import com.github.ag.core.context.BaseContextHandler;
import com.github.ag.core.util.jwt.IJWTInfo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.auth.client.config.UserAuthConfig;
import com.github.wxiaoqi.security.auth.client.jwt.UserAuthUtil;
import com.github.wxiaoqi.security.common.constant.RequestHeaderConstants;
import com.github.wxiaoqi.security.common.exception.auth.NonLoginException;
import com.github.wxiaoqi.security.common.msg.BaseResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户token拦截认证
 *
 * @author ace
 * @version 2017/9/10
 */
public class UserAuthRestInterceptor extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger(UserAuthRestInterceptor.class);

    @Autowired
    private UserAuthUtil userAuthUtil;

    @Autowired
    private UserAuthConfig userAuthConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        if (HttpMethod.OPTIONS.matches(method)) {
            return super.preHandle(request, response, handler);
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 配置该注解，说明不进行用户拦截。class级别定义
        CheckUserToken annotation = handlerMethod.getBeanType().getAnnotation(CheckUserToken.class);

        //配置忽略用户身份验证注解，仅限在method级别定义
        IgnoreUserToken ignoreUserToken = handlerMethod.getMethodAnnotation(IgnoreUserToken.class);
        //如果CheckUserToken注解未配置在类上，则找对应的方法上有无该注解
        if (annotation == null) {
            annotation = handlerMethod.getMethodAnnotation(CheckUserToken.class);
        }
        //如果CheckUserToken注解未配置在类上,且方法上有IgnoreUserToken
        if (annotation == null || ignoreUserToken != null) {
            //设置上下文的用户信息，如有
            try {
                setBaseContextHandlerUser(request);
            } catch (NonLoginException ex) {
                //异常捕获不处理，BaseContextHandler则无数据
            }

            return super.preHandle(request, response, handler);
        }
        //CheckUserToken注解在类、方法上，并且没有IgnoreUserToken
        else {
            try {
                setBaseContextHandlerUser(request);
            } catch (NonLoginException ex) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                // logger.error(ex.getMessage(), ex);
                response.setContentType("UTF-8");
                response.getOutputStream().println(JSON.toJSONString(new BaseResponse(ex.getStatus(), ex.getMessage())));
                return false;
            }

//            String token = request.getHeader(userAuthConfig.getTokenHeader());
//            logger.info("获取的token为："+token);
//            if (StringUtils.isEmpty(token)) {
//                if (request.getCookies() != null) {
//                    for (Cookie cookie : request.getCookies()) {
//                        if (cookie.getName().equals(userAuthConfig.getTokenHeader())) {
//                            token = cookie.getValue();
//                        }
//                    }
//                }
//            }
//
//            if (token != null && token.startsWith(RequestHeaderConstants.JWT_TOKEN_TYPE)) {
//                token = token.replaceAll("%20"," ");
//                logger.info("替换后的token为："+token);
//                token = token.substring(RequestHeaderConstants.JWT_TOKEN_TYPE.length(),token.length());
//            }
//            try {
//                String tenantID = BaseContextHandler.getTenantID();
//                IJWTInfo infoFromToken = userAuthUtil.getInfoFromToken(token);
//                BaseContextHandler.setToken(token);
//                BaseContextHandler.setUsername(infoFromToken.getUniqueName());
//                BaseContextHandler.setName(infoFromToken.getName());
//                BaseContextHandler.setUserID(infoFromToken.getId());
//                BaseContextHandler.setDepartID(infoFromToken.getOtherInfo().get(CommonConstants.JWT_KEY_DEPART_ID));
//                String userTenantId = infoFromToken.getOtherInfo().get(CommonConstants.JWT_KEY_TENANT_ID);
//                if(StringUtils.isNoneBlank(tenantID)){
//                    if(!tenantID.equals(userTenantId)){
//                        throw new NonLoginException("用户不合法!");
//                    }
//                }
//                BaseContextHandler.setTenantID(userTenantId);
//
//                setBaseContextHandlerUser(request);
//            }catch(NonLoginException ex){
//                response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                logger.error(ex.getMessage(),ex);
//                response.setContentType("UTF-8");
//                response.getOutputStream().println(JSON.toJSONString(new BaseResponse(ex.getStatus(), ex.getMessage())));
//                return false;
//            }

            return super.preHandle(request, response, handler);
        }
    }

    //设置上下文菜单里的用户信息
    private void setBaseContextHandlerUser(HttpServletRequest request) throws Exception {
        String token = request.getHeader(userAuthConfig.getTokenHeader());
        //logger.info("获取的token为：" + token);
        if (StringUtils.isEmpty(token)) {
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if (cookie.getName().equals(userAuthConfig.getTokenHeader())) {
                        token = cookie.getValue();
                    }
                }
            }
        }

        if (token != null && token.startsWith(RequestHeaderConstants.JWT_TOKEN_TYPE)) {
            token = token.replaceAll("%20", " ");
            //logger.info("替换后的token为：" + token);
            token = token.substring(RequestHeaderConstants.JWT_TOKEN_TYPE.length(), token.length());
        }

        String tenantID = BaseContextHandler.getTenantID();
        IJWTInfo infoFromToken = userAuthUtil.getInfoFromToken(token);
        BaseContextHandler.setToken(token);
        BaseContextHandler.setUsername(infoFromToken.getUniqueName());
        BaseContextHandler.setName(infoFromToken.getName());
        BaseContextHandler.setUserID(infoFromToken.getId());
        BaseContextHandler.setDepartID(infoFromToken.getOtherInfo().get(CommonConstants.JWT_KEY_DEPART_ID));
        BaseContextHandler.setIsSuperAdmin(infoFromToken.getOtherInfo().get(CommonConstants.JWT_KEY_IS_SUPER_ADMIN));
        String userTenantId = infoFromToken.getOtherInfo().get(CommonConstants.JWT_KEY_TENANT_ID);
        if (StringUtils.isNoneBlank(tenantID)) {
            if (!tenantID.equals(userTenantId)) {
                throw new NonLoginException("用户不合法!");
            }
        }
        BaseContextHandler.setTenantID(userTenantId);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContextHandler.remove();
        super.afterCompletion(request, response, handler, ex);
    }
}
