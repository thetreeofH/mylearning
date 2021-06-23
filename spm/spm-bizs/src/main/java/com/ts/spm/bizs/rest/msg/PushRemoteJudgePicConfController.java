package com.ts.spm.bizs.rest.msg;

import com.alibaba.fastjson.JSON;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.ts.spm.bizs.config.WebSocketUtil;
import com.ts.spm.bizs.entity.jzpitgcfg.SwitchConfPush;
import com.ts.spm.bizs.entity.msg.WebSocketInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pushRemoteJudgePicConf")
@IgnoreClientToken
@IgnoreUserToken
public class PushRemoteJudgePicConfController {
    @ApiOperation("集中判图配置")
    @RequestMapping(value = "send",method = RequestMethod.POST)
    @CrossOrigin
    public ObjectRestResponse send(@RequestBody SwitchConfPush switchConfPush, @RequestParam("pointId") String pointId){
        try {
            WebSocketInfo info = new WebSocketInfo();
            info.setInfo(switchConfPush);
            info.setType("remoteJudgePicConf");
            WebSocketUtil.sendMessage(JSON.toJSONString(info), pointId);
            return ObjectRestResponse.ok();
        }catch (Exception e){
            e.printStackTrace();
            return ObjectRestResponse.error("推送集中判图配置失败，错误:"+e.getMessage());
        }

    }

}
