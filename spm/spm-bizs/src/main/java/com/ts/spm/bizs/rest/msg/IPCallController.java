package com.ts.spm.bizs.rest.msg;

import com.alibaba.fastjson.JSON;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.ts.spm.bizs.config.WebSocketUtil;
import com.ts.spm.bizs.entity.msg.IpCall;
import com.ts.spm.bizs.entity.msg.WebSocketInfo;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhoukun on 2020/6/22.
 */
@RestController
@RequestMapping("ipcall")
@IgnoreClientToken
@IgnoreUserToken
@Api(tags = "IP对讲")
public class IPCallController {
    @Autowired
    private CheckPointController checkPointCtr;

    @ApiOperation("Webrtc对讲")
    @RequestMapping(value = "/start", method = RequestMethod.POST)
    @CrossOrigin
    public ObjectRestResponse Call(@RequestBody IpCall ipcall) {
        try {
            WebSocketInfo info = new WebSocketInfo();
            info.setInfo(ipcall.getRemoteUrl());
            info.setType("ipcall");
            WebSocketUtil.sendMessage(JSON.toJSONString(info), ipcall.getToId());
            return ObjectRestResponse.ok();
        } catch (Exception e) {
            return ObjectRestResponse.error(e.getMessage());
        }
    }

    @ApiOperation("广播")
    @RequestMapping(value = "/broadcast", method = RequestMethod.POST)
    public ObjectRestResponse Broadcast(@RequestBody IpCall ipcall) {
        List<String> listp = new ArrayList<>();
        for (String departId : ipcall.getDepartId()) {
            List<Map<String, Object>> list = checkPointCtr.getpoint(departId, "");
            for(Map<String, Object> item : list) {
                listp.add(item.get("id").toString());
            }
        }
        try {
            for(String id : listp) {
                WebSocketInfo info = new WebSocketInfo();
                info.setInfo(ipcall.getRemoteUrl());
                info.setType("broadcast");
                WebSocketUtil.sendMessage(JSON.toJSONString(info), id);
            }
            return ObjectRestResponse.ok();
        } catch (Exception e) {
            return ObjectRestResponse.error(e.getMessage());
        }
    }

    @ApiOperation("实时广播")
    @RequestMapping(value = "/bc", method = RequestMethod.POST)
    @CrossOrigin
    public ObjectRestResponse bc(@RequestBody IpCall ipcall) {
        String[] pointid = ipcall.getToId().split(",");
        try {
            for(String id : pointid) {
                WebSocketInfo info = new WebSocketInfo();
                if(ipcall.getRemoteUrl().equalsIgnoreCase("close"))
                    info.setInfo(ipcall.getRemoteUrl());
                else
                    info.setInfo(ipcall.getRemoteUrl() + "/" + id);
                info.setType("realbroadcast");
                WebSocketUtil.sendMessage(JSON.toJSONString(info), id);
            }
            return ObjectRestResponse.ok();
        } catch (Exception e) {
            return ObjectRestResponse.error(e.getMessage());
        }
    }
}
