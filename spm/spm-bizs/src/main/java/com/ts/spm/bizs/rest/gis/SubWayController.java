package com.ts.spm.bizs.rest.gis;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhoukun on 2020/4/16.
 */
@RestController
@RequestMapping("subway")
@CheckClientToken
@CheckUserToken
@Api(tags = "线路管理")
public class SubWayController {

    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public String getTest() {
        return "123456";
    }
}
