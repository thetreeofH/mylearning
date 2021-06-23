package com.ts.spm.bizs.rest.msg;

import com.alibaba.fastjson.JSON;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.DateUtil;
import com.github.wxiaoqi.security.common.util.StringUtil;
import com.ts.spm.bizs.biz.msg.TbcTireMonitorBiz;
import com.ts.spm.bizs.biz.msg.TblAppPushUserMapBiz;
import com.ts.spm.bizs.config.WebSocketUtil;
import com.ts.spm.bizs.entity.msg.TbcTireMonitor;
import com.ts.spm.bizs.entity.msg.TireMonitor;
import com.ts.spm.bizs.entity.msg.WebSocketInfo;
//import com.ts.spm.msg.feign.AdminFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * @ClassName TireMonitorController
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/11/13 11:19
 * @Version 1.0
 **/
@RestController
@RequestMapping("tireMonitor")
@IgnoreUserToken
@IgnoreClientToken
public class TireMonitorController extends BaseController<TbcTireMonitorBiz, TbcTireMonitor,String> {
    @Autowired
    TblAppPushUserMapBiz tblAppPushUserMapBiz;
    @Autowired
    TbcTireMonitorBiz tbcTireMonitorBiz;
//    @Autowired
//    AdminFeign adminFeign;

    @RequestMapping(value = "/pushTireMonitorAlarm",method = RequestMethod.GET)
    public void pushTireMonitorAlarm(@RequestParam("id") String id){
        int intId = 0;
        if (!StringUtil.isNumber(id)){
            System.out.println("方法：/PushMsgToClient的参数id is not number :" + id);

        }else{
            intId = Integer.parseInt(id);
        }

        Map<String,Object> tbcTireMonitor = tbcTireMonitorBiz.selectById(intId);
        if(tbcTireMonitor!= null){
            Map<String, String> point = tblAppPushUserMapBiz.selectById(tbcTireMonitor.get("pointId").toString());
            if(point != null && !point.isEmpty()) {
                TireMonitor tireMonitor = new TireMonitor();
                tireMonitor.setPointId(tbcTireMonitor.get("pointId").toString());
                tireMonitor.setPointName(point.get("name"));
                tireMonitor.setStationId(point.get("departId"));
                tireMonitor.setStationName(point.get("departName"));
                tireMonitor.setHitPic(tbcTireMonitor.get("hitPic").toString());
                tireMonitor.setHitTime(DateUtil.date2Str(DateUtil.parse(tbcTireMonitor.get("hitTime").toString(),"yyyy-MM-dd HH:mm:ss")));
                //推送
                WebSocketInfo info = new WebSocketInfo();
                info.setInfo(tireMonitor);
                info.setType("tireMonitor");
                try {
                    WebSocketUtil.sendMessage(JSON.toJSONString(info), "center");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
