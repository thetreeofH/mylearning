package com.github.wxiaoqi.security.auth.module.oauth.config;

import com.github.wxiaoqi.security.auth.constant.BizCodeConstant;
import com.github.wxiaoqi.security.auth.module.oauth.custom.exception.AppBindedException;
import com.github.wxiaoqi.security.auth.module.oauth.custom.exception.AppLoginBindParamsException;
import com.github.wxiaoqi.security.auth.module.oauth.custom.exception.LoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

/**
 * 当执行 CustomAuthenticationProvider#retrieveUser抛出异常时，会被这个异常解析器处理，
 * 可以在这里构造返回{@link ResponseEntity}，加入code、message等字段，
 *
 * @author zhangjw
 * @version 1.0
 */
@Slf4j
@Configuration
public class Oauth2ExceptionTranslatorConfiguration {
    @Bean
    public WebResponseExceptionTranslator oauth2ResponseExceptionTranslator() {
        return new DefaultWebResponseExceptionTranslator() {
            @Override
            public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
                OAuth2Exception body = OAuth2Exception.create(OAuth2Exception.ACCESS_DENIED, e.getMessage());
                HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
                // 捕获后在返回值添加code、message
                if(e instanceof AppBindedException){
                    body.addAdditionalInformation("status", BizCodeConstant.LoginBizCode.APP_BIND_EX.getCode()+"");
                    body.addAdditionalInformation("message", BizCodeConstant.LoginBizCode.APP_BIND_EX.getMsg());
                }else if(e instanceof AppLoginBindParamsException){
                    body.addAdditionalInformation("status", BizCodeConstant.LoginBizCode.APP_BIND_PARAMS_EX.getCode()+"");
                    body.addAdditionalInformation("message", BizCodeConstant.LoginBizCode.APP_BIND_PARAMS_EX.getMsg());
                }else if(e instanceof InvalidGrantException){
                    body.addAdditionalInformation("status", BizCodeConstant.LoginBizCode.USER_PWD_ERROR.getCode()+"");
                    body.addAdditionalInformation("message", BizCodeConstant.LoginBizCode.USER_PWD_ERROR.getMsg());
                    // 设置状态兼容旧的对接逻辑
                    httpStatus = HttpStatus.UNAUTHORIZED;
                }else if(e instanceof UsernameNotFoundException){
                    body.addAdditionalInformation("status", BizCodeConstant.LoginBizCode.USER_NAME_NOT_FOIND.getCode()+"");
                    body.addAdditionalInformation("message", BizCodeConstant.LoginBizCode.USER_NAME_NOT_FOIND.getMsg());
                    // 设置状态兼容旧的对接逻辑
                    httpStatus = HttpStatus.UNAUTHORIZED;
                }else if(e instanceof LockedException){
                    body.addAdditionalInformation("status", BizCodeConstant.LoginBizCode.ACCOUNT_DISABLE.getCode()+"");
                    body.addAdditionalInformation("message", BizCodeConstant.LoginBizCode.ACCOUNT_DISABLE.getMsg());
                }else if(e instanceof LoginException){
                    body.addAdditionalInformation("status", BizCodeConstant.CommonBIzCode.COMMON_BIZ_EX.getCode()+"");
                    body.addAdditionalInformation("message", BizCodeConstant.CommonBIzCode.COMMON_BIZ_EX.getMsg());
                    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

                }else{
                    return  super.translate(e);
                }
                System.out.println(body.getHttpErrorCode());
                HttpHeaders headers = new HttpHeaders();
                return new ResponseEntity<>(body, headers, httpStatus);
            }
        };
    }
}
