package com.ts.spm.bizs.rest.stat;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.matter.EventInfoBiz;
import com.ts.spm.bizs.entity.matter.EventInfo;
import com.ts.spm.bizs.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by Administrator on 2020/11/2.
 * updater 马居朝
 * @RequestParam(value = "dangerId",required = false) Integer dangerId,中新增了required = false
 * 新增   if(type == 1 || type == 3){
 *        info.setType(1); //【0：无违禁品  1：放行 2：没收 3：报警】
 *        info.setTitle("违禁品事件");
 *        }
 * 原来的if(){}注释掉了
 */
@RestController
@RequestMapping("eventInfo")
@CheckClientToken
@CheckUserToken
public class EventInfoController2 extends BaseController<EventInfoBiz, EventInfo, String> {
    @Autowired
    EventInfoBiz eventInfoBiz;
    @IgnoreUserToken
    @RequestMapping(value = "/save")
    public ObjectRestResponse save(@RequestParam(value = "AlarmTime",required = false) String AlarmTime,
                                   @RequestParam(value = "pointId",required = false) String pointId,
                                   @RequestParam(value = "departId",required = false) String departId,
                                   @RequestParam(value = "judgePictureSource",required = false) Integer judgePictureSource,
                                   @RequestParam(value = "handleUserName",required = false) String handleUserName,
                                   @RequestParam(value = "handleResult",required = false) Integer handleResult,
                                   @RequestParam(value = "suspectedForbiddenType",required = false) Integer suspectedForbiddenType,
                                   @RequestParam(value = "suspectedForbiddenTypeName",required = false) String suspectedForbiddenTypeName,
                                   @RequestParam(value = "suspectedForbiddenSubtype",required = false) Integer suspectedForbiddenSubtype,
                                   @RequestParam(value = "suspectedForbiddenSubtypeName",required = false) String suspectedForbiddenSubtypeName,
                                   @RequestParam(value = "dangerId",required = false) Integer dangerId,
                                   @RequestParam(value = "type") Integer type,
                                   @RequestParam(value = "picPath",required = false) String picPath,
                                   @RequestParam(value = "xPicPath",required = false) String xPicPath) {

        try {
            EventInfo info=new EventInfo();
            info.setId(eventInfoBiz.getId());
//            if(type == 1 || type == 2 || type == 3){// 1.开包精检（有X光机图片X_PIC_PATH）
                //2.手检处置（有可见光抓拍图片 PIC_PATH）
                //3.查获登记（有可见光抓拍图片 PIC_PATH）
                //4.一键报警（有可见光抓拍图片 PIC_PATH）
                //5.web录入(B端使用)
                //6.app录入(B端使用)
//            info.setType(1); //【1违禁品事件、2一键报警、3其他报警】
//            info.setTitle("违禁品事件");
            if(type == 1 || type == 3){
                info.setType(1); //【0：无违禁品  1：放行 2：没收 3：报警】
                info.setTitle("违禁品事件");
            }else{
                info.setType(2); //【1违禁品事件、2一键报警、3其他报警】
                info.setTitle("一键报警");
            }


            info.setCheckerName(handleUserName);//检查人姓名
            info.setCheckerResult(handleResult);//检查结果
            info.setPointId(pointId);//安检点ID
            info.setStationId(departId);//车站id
            if(String.valueOf(judgePictureSource) != null){
                info.setSource(String.valueOf(judgePictureSource));//判图来源
            }

            info.setFoundTime(DateUtil.parse(AlarmTime,"yyyy-MM-dd HH:mm:ss"));
            if(String.valueOf(suspectedForbiddenType) != null){
                info.setDangerTypeId(String.valueOf(suspectedForbiddenType));//危险品大类ID
            }

            info.setDangerType(suspectedForbiddenTypeName);//危险品大类名称
            if(String.valueOf(suspectedForbiddenSubtype) != null){
                info.setDangerSubId(String.valueOf(suspectedForbiddenSubtype));//危险品二级分类ID
            }

            info.setDangerSub(suspectedForbiddenSubtypeName);//危险品二级分类名称
            info.setDealResult(0);//【0：未处理，1：已处理】
            info.setCrtTime(new Date());
            info.setDelFlag(0);//【0：未删除，1：已删除】
            info.setDangerId(dangerId);
            if(type == 1){
                info.setXpicPath(xPicPath);
            }else{
                info.setPicPath(picPath);
            }

            info.setCrtUserId(BaseContextHandler.getUserID());
            info.setCrtUserName(BaseContextHandler.getUsername());
            int result = baseBiz.insertSelective2(info);
            if(result == 1){
                return ObjectRestResponse.ok();
            }
            return ObjectRestResponse.error("保存事件失败，请联系管理员！！");
        }catch (Exception e){
            return ObjectRestResponse.error(e.getMessage());
        }

    }

}
