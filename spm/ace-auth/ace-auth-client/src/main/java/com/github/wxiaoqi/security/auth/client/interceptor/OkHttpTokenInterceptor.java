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

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.auth.client.config.ServiceAuthConfig;
import com.github.wxiaoqi.security.auth.client.config.UserAuthConfig;
import com.github.wxiaoqi.security.auth.client.jwt.ServiceAuthUtil;
import com.github.wxiaoqi.security.common.constant.RestCodeConstants;
import lombok.extern.java.Log;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @author ace
 */
@Component
@Log
public class OkHttpTokenInterceptor implements Interceptor {
	@Autowired
	@Lazy
	private ServiceAuthUtil serviceAuthUtil;
	@Autowired
	@Lazy
	private ServiceAuthConfig serviceAuthConfig;
	@Autowired
	@Lazy
	private UserAuthConfig userAuthConfig;


	@Override
	public Response intercept(Chain chain) throws IOException {
		Request newRequest = chain.request()
				.newBuilder()
				.header(userAuthConfig.getTokenHeader(), BaseContextHandler.getToken())
				.build();
		Response response = chain.proceed(newRequest);
		if(HttpStatus.FORBIDDEN.value()==response.code()){
			if(response.body().string().contains(String.valueOf(RestCodeConstants.EX_CLIENT_INVALID_CODE))){
				log.info("Client Token Expire,Retry to request...");
				serviceAuthUtil.refreshClientToken();
				newRequest = chain.request()
						.newBuilder()
						.header(userAuthConfig.getTokenHeader(), BaseContextHandler.getToken())
						.header(serviceAuthConfig.getTokenHeader(),serviceAuthUtil.getClientToken())
						.build();
				response = chain.proceed(newRequest);
			}
		}
	    return response;
	}

}
