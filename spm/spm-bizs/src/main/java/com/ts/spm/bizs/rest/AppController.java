package com.ts.spm.bizs.rest;

import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhoukun on 2021/5/7.
 */
@RestController
@RequestMapping("version")
@IgnoreClientToken
@IgnoreUserToken
public class AppController {
    /**
     * 当前版本
     */
    @Value("${app.version}")
    private String version;
    /**
     * 打包时间
     */
    @Value("${app.build.time}")
    private String buildTime;

    @GetMapping
    public ObjectRestResponse uploadImg() {
        Map<String,String> ret = new HashMap<>();
        ret.put("version",version);
        ret.put("buildTime",buildTime);
        return ObjectRestResponse.ok(ret);
    }
}
