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

package com.github.wxiaoqi.security.auth.module.client.controller;

import com.github.wxiaoqi.security.auth.module.client.service.AuthService;
import com.github.wxiaoqi.security.common.constant.RequestHeaderConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ace
 * @create 2018/3/27.
 */
@FrameworkEndpoint
public class RevokeTokenEndpoint {

    @Autowired
    @Qualifier("consumerTokenServices")
    ConsumerTokenServices consumerTokenServices;
    @Autowired
    private AuthService authService;

    @RequestMapping(method = RequestMethod.DELETE, value = "/oauth/token")
    @ResponseBody
    public ObjectRestResponse revokeToken(String access_token) throws Exception {
        String realToken = getRealToken(access_token);
        if (consumerTokenServices.revokeToken(realToken)){
            authService.invalid(realToken);
            return new ObjectRestResponse<Boolean>().data(true);
        }else{
            return new ObjectRestResponse<Boolean>().data(false);
        }
    }

    private String getRealToken(String originToken) {
        if (originToken != null && originToken.startsWith(RequestHeaderConstants.JWT_TOKEN_TYPE)) {
            originToken = originToken.substring(RequestHeaderConstants.JWT_TOKEN_TYPE.length(), originToken.length());
        }
        return originToken;
    }
}
