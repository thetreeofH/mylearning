package com.ts.spm.bizs.rest.msg;

import com.alibaba.fastjson.JSON;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateUtil;
import com.ts.spm.bizs.config.WebSocketUtil;
import com.ts.spm.bizs.entity.msg.WebSocketInfo;
import com.ts.spm.bizs.vo.msg.PointDangerVo;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import com.ts.spm.bizs.rest.stat.EventInfoController2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * Created by zhoukun on 2020/10/12.
 * updater 马居朝
 * 新增 pointDanger.getPicPath()
 */

@RestController
@RequestMapping("danger")
@IgnoreClientToken
@IgnoreUserToken
@Api(tags = "违禁品")
public class DangerInfoController{
    @Autowired
    private CheckPointController checkPointCtr;
    @Autowired
    private EventInfoController2 eventInfoCtr;

    @ApiOperation("违禁品报警")
    @RequestMapping(value = "/alarm", method = RequestMethod.POST)
    @CrossOrigin
    public ObjectRestResponse Alarm(@RequestBody PointDangerVo pointDanger) {
        try {
            Map<String, String> point = checkPointCtr.getPointInfo(pointDanger.getPointId());
            pointDanger.setPointName(point.get("name"));
            pointDanger.setDepartId(point.get("departId"));
            pointDanger.setDepartName(point.get("departName"));
            pointDanger.setAlarmTime(getStringDate(pointDanger.getAlarmTime()));
            WebSocketInfo info = new WebSocketInfo();
            info.setInfo(pointDanger);
            info.setType("danger");
            WebSocketUtil.sendMessage(JSON.toJSONString(info), "center");
            //插入警情事件表TBL_EVENT_INFO
            ObjectRestResponse objectRestResponse = eventInfoCtr.save(pointDanger.getAlarmTime(),
                    pointDanger.getPointId(),pointDanger.getDepartId(),pointDanger.getJudgePictureSource(),
                    pointDanger.getHandleUserName(),pointDanger.getHandleResult(),
                    pointDanger.getSuspectedForbiddenType(),
                    pointDanger.getSuspectedForbiddenTypeName(),
                    pointDanger.getSuspectedForbiddenSubtype(),
                    pointDanger.getSuspectedForbiddenSubtypeName(),
                    pointDanger.getDangerId(),1,pointDanger.getPicPath(),pointDanger.getSuspectedItemsPicture());
            if(objectRestResponse.getStatus() == 200){
                return ObjectRestResponse.ok();
            }
            return ObjectRestResponse.error("保存事件失败，请联系管理员！！");
        } catch (Exception e) {
            return ObjectRestResponse.error(e.getMessage());
        }
    }
    public static String getStringDate(String time) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        //java.util.Date对象
        Date date = (Date) sdf.parse(time);
        //2009-09-16 11:26:23
        String formatStr2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        //System.out.println(formatStr2);
        return formatStr2;
    }
}
