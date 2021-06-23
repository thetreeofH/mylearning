package com.ts.spm.bizs.rest.msg;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.DateUtil;
import com.github.wxiaoqi.security.common.util.HuaweiPush;
import com.github.wxiaoqi.security.common.util.HuaweiPushMessage;
import com.github.wxiaoqi.security.common.util.StringUtil;
import com.ts.spm.bizs.biz.admin.RealTimeVideoBiz;
import com.ts.spm.bizs.biz.jzpmq.DealResultInfoBiz;
import com.ts.spm.bizs.biz.matter.EventInfoBiz;
import com.ts.spm.bizs.biz.msg.TblAppPushUserMapBiz;
import com.ts.spm.bizs.config.WebSocketUtil;
import com.ts.spm.bizs.entity.admin.RealTimeVideo;
import com.ts.spm.bizs.entity.matter.EventInfo;
import com.ts.spm.bizs.entity.matter.PointDanger;
import com.ts.spm.bizs.entity.msg.*;
import com.ts.spm.bizs.rest.matter.TbcPointDangerController;
import com.ts.spm.bizs.rest.stat.EventInfoController2;
import com.ts.spm.bizs.util.ContextLoaderServlet;
import com.ts.spm.bizs.vo.stat.EventVo;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.util.JSONUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName PushController
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/11/6 9:25
 * @Version 1.0
 * updater 马居朝
 * 新增  private final static Logger logger = LoggerFactory.getLogger(PushMsgController.class);
 * 新增  logger.info("推送消息。实现方式为，推送消息到指定手机设备");
 **/
@RestController
@RequestMapping("pushMsg")
@IgnoreClientToken
@IgnoreUserToken
@Slf4j
public class PushMsgController extends BaseController<TblAppPushUserMapBiz, TblAppPushUserMap,String> {
    private final static Logger logger = LoggerFactory.getLogger(PushMsgController.class);
    @Autowired
    TblAppPushUserMapBiz tblAppPushUserMapBiz;
    @Autowired
    TbcPointDangerController TbcPointDangerControl;
    @Autowired
    EventInfoController2 eventInfoController;
    @Autowired
    RealTimeVideoBiz realTimeVideoBiz;
    @Autowired
    EventInfoBiz eventInfoBiz;
    @Autowired
    DealResultInfoBiz dealResultInfoBiz;
    /**
     * 推送消息。实现方式为，推送消息到指定手机设备
     */
    @RequestMapping(value = "/PushMsgToClient",method = RequestMethod.GET)
    public ObjectRestResponse pushMsgToTag2(@RequestParam(value = "id",required = true) String id, @RequestParam(value = "checkType",required = true) String checkType){
        logger.info("推送消息。实现方式为，推送消息到指定手机设备");
        int intId = 0;
        int iType = 0;
        //  1.开包精检（有X光机图片X_PIC_PATH）
        //2.手检处置（有可见光抓拍图片 PIC_PATH）
        //3.查获登记（ you可见光）
        //4.一键报警（有可见光抓拍图片 PIC_PATH）
        //5.web录入
        //6.app录入
        int devType = 1;//运营

        if (!StringUtil.isNumber(id)){
            return ObjectRestResponse.error("请求参数id非数值："+id);
        }else{
            intId = Integer.parseInt(id);
        }

        if (true == StringUtil.isNumber(checkType)){
            iType = Integer.parseInt(checkType);
        }

        com.ts.spm.bizs.entity.matter.PointDanger tbcPointDanger = TbcPointDangerControl.getById(intId);
        if(tbcPointDanger != null ){
            Map<String, String> point = tblAppPushUserMapBiz.selectById(tbcPointDanger.getPointId());
            if(point != null && !point.isEmpty()){
                MsgPointDanger pointDanger = new MsgPointDanger();
                pointDanger.setPointId(point.get("id"));
                pointDanger.setPointName(point.get("name"));
                pointDanger.setDepartId(point.get("departId"));
                pointDanger.setDepartName(point.get("departName"));
                pointDanger.setDangerId(tbcPointDanger.getId());
                //APP推送
                try {
                    ScheduledExecutorService executorService = ContextLoaderServlet.getExecutorService();
                    executorService.schedule(new Runnable() {
                        public void run() {
                            AppPushToken pushTokens = tblAppPushUserMapBiz.getPushToken(tbcPointDanger,devType);

                            if(pushTokens!=null){
                                List<AppPushUserMap> appPushUserMaps = pushTokens.getTokens();
                                for(AppPushUserMap dev : appPushUserMaps){
//					                PushMsgToSingleDeviceResponse res = BaiduPush.pushToDevice(dev, new BaiduPushMessage("警情通知：",
//							        "["+pushDevs.getStation()+"]车站["+pushDevs.getPoint()+"]发现危险品！查看详情>>"));

                                    //华为通知栏消息
                                    String res= HuaweiPush.sendPushMessage(dev.getPushCode(), new HuaweiPushMessage("警情通知：",
                                            "["+pushTokens.getStation()+"]-["+pushTokens.getPoint()+"]发现危险品！查看详情>>"));
                                    //华为透传消息
                                    String res1= HuaweiPush.sendPushMessage1(dev.getPushCode(), new HuaweiPushMessage("警情通知：",
                                            "["+pushTokens.getStation()+"]-["+pushTokens.getPoint()+"]发现危险品！查看详情>>"));
                                    System.out.println(res);
                                    System.out.println(res1);
                                    if(res!=null&&res1!=null){
                                        System.out.println("华为通知栏消息:"+res+""+" 华为透传消息:"+res1+" 设备id为："+dev.getPushCode() + "["+pushTokens.getStation()+"]-["+pushTokens.getPoint() );
                                    }else{
                                        //一般表明服务器中就没有这个映射关系， 将ID从数据库中去除
                                        System.out.println("一般表明服务器中就没有这个映射关系， 将ID从数据库中去除");
                                        baseBiz.deleteById2(dev.getId());

                                    }
                                }
                            }else{
                                System.out.println("方法：/PushMsgToClient id is :" + id + " checkType: " + checkType + " 无相应用户登录或者是checkType和数据库不符");

                            }
                        }
                    }, 0, TimeUnit.SECONDS);
                }catch (Exception e){
                    e.printStackTrace();
                }


                pointDanger.setAlarmTime(DateUtil.date2Str(tbcPointDanger.getTime()));

                pointDanger.setHandleResult(0);//默认值
                pointDanger.setHandleUserName(tbcPointDanger.getAjyName());
                pointDanger.setPassengerIdCard(tbcPointDanger.getPsgIdno());
                pointDanger.setPassengerName(tbcPointDanger.getPsgName());
                if(iType == 1 || iType == 2 || iType == 3){

                    pointDanger.setSuspectedForbiddenSubtype(Integer.valueOf(String.valueOf(tbcPointDanger.getStype())));
                    Map<String,String> forbiddenNameMap = tblAppPushUserMapBiz.selectForbiddenName(tbcPointDanger.getId());
                    if(forbiddenNameMap != null && !forbiddenNameMap.isEmpty()){
                        pointDanger.setSuspectedForbiddenSubtypeName(forbiddenNameMap.get("suspectedForbiddenSubtypeName"));
                        pointDanger.setSuspectedForbiddenTypeName(forbiddenNameMap.get("suspectedForbiddenTypeName"));
                    }

                    pointDanger.setSuspectedForbiddenType(Integer.parseInt(tbcPointDanger.getMtype()));
                    if(iType == 1){//1 有X光机图片X_PIC_PATH
                        pointDanger.setSuspectedItemsPicture(tbcPointDanger.getxPicPath());
                    }else{//2,3有可见光抓拍图片 PIC_PATH
                        pointDanger.setSuspectedItemsPicture(tbcPointDanger.getPicPath());
                    }

                }else{//有可见光抓拍图片 PIC_PATH
                    pointDanger.setSuspectedItemsPicture(tbcPointDanger.getPicPath());
                }


                //一键视频报警
                if(iType==4){
                    String devId=realTimeVideoBiz.selectDeviceByPointIdAndAlarmType(tbcPointDanger.getPointId(),"4");
                    if(null!=devId&&devId.length()>0){
                        String[] devIds=devId.split(",");
                        if(null!=devIds&&devIds.length>0) {
                            List<RealTimeVideo> list=new ArrayList<>();
                            for (String r : devIds) {
                                RealTimeVideo realTimeVideo=realTimeVideoBiz.selectDeviceByDevId(r);
                                realTimeVideo.setAlarmType("4");
                                realTimeVideo.setAlarmName("一键报警");
                                list.add(realTimeVideo);
                            }
                            pointDanger.setAlarmType("4");
                            pointDanger.setVideoInfo(JSONArray.toJSONString(list));
                        }
                    }
                    //获取站点的视频信息end
                }


                //插入警情事件表TBL_EVENT_INFO
                ObjectRestResponse objectRestResponse = eventInfoController.save(pointDanger.getAlarmTime(),
                        pointDanger.getPointId(),pointDanger.getDepartId(),pointDanger.getJudgePictureSource(),
                        pointDanger.getHandleUserName(),pointDanger.getHandleResult(),
                        pointDanger.getSuspectedForbiddenType(),
                        pointDanger.getSuspectedForbiddenTypeName(),
                        pointDanger.getSuspectedForbiddenSubtype(),
                        pointDanger.getSuspectedForbiddenSubtypeName(),
                        pointDanger.getDangerId(),iType,tbcPointDanger.getPicPath(),tbcPointDanger.getxPicPath());
                if(objectRestResponse.getStatus() == 200){
                    //乘客自弃或者报警推动到大屏
                    if(iType==1||iType==3){
                        List<String> pointIds=new ArrayList<>();
                        pointIds.add(tbcPointDanger.getPointId());
                        pointDanger.setAlarmInfo(alarm(pointIds));
                        pointDanger.setContrabandXian(contrabandXian(pointIds));
                    }
                    WebSocketInfo info = new WebSocketInfo();
                    info.setInfo(pointDanger);
                    info.setType("danger");
                    try {
                        WebSocketUtil.sendMessage(JSON.toJSONString(info), "center");
                        return ObjectRestResponse.ok();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return ObjectRestResponse.error("推送警情事件到web前端失败，错误:"+e.getMessage());
                    }

                }else{
                    return ObjectRestResponse.error("保存事件表失败，请联系管理员！！");
                }

            }else{
                return ObjectRestResponse.error("没有查询到pointId："+tbcPointDanger.getPointId()+" 对应的记录，请确认pointId是否正确！！");
            }

        }
        return ObjectRestResponse.error("没有查询到与请求参数id："+id+" 对应的记录，请确认id是否正确！！");
//		BaiduPush.pushToTag("admin", new BaiduPushMessage("警情通知：", "当前车站发现危险品,[点击查看详情]"+id));
//		log.info("消息已推送！车站ID为："+id);
    }

    public String alarm(List<String> pointIds) {
        EventVo vo=new EventVo();
        Date end=new Date();
        Date start=new Date();
        start= DateUtils.setHours(start, 0);
        start=DateUtils.setMinutes(start, 0);
        start=DateUtils.setSeconds(start, 0);
        start=DateUtils.setMilliseconds(start,0);
        end=DateUtils.setHours(end, 23);
        end=DateUtils.setMinutes(end, 59);
        end=DateUtils.setSeconds(end, 59);
        end=DateUtils.setMilliseconds(end, 999);
        Example exa = new Example(EventInfo.class);
        Example.Criteria cri = exa.createCriteria();
        cri.andBetween("crtTime", start, end);
        if(null!=pointIds&&pointIds.size()>0){
            cri.andIn("pointId", pointIds);
        }
        cri.andEqualTo("type",1);
        vo.setDanger(eventInfoBiz.selectCountByExample(exa));

        exa = new Example(EventInfo.class);
        cri = exa.createCriteria();
        cri.andBetween("crtTime", start, end);
        if(null!=pointIds&&pointIds.size()>0){
            cri.andIn("pointId", pointIds);
        }
        cri.andEqualTo("type",2);
        vo.setOther(eventInfoBiz.selectCountByExample(exa));

        //logBiz.saveLog("首页","违禁品事件统计", "api/stat/home/alarm");

        return JSON.toJSONString(vo);
    }


    //违禁品统计

    public String contrabandXian(List<String> pointIds) {
        Map<String,Integer> resultMap = new HashMap<>();
        List<Map<String,String>> list = dealResultInfoBiz.todayContraband(pointIds);
        if(list != null && list.size() > 0){
            for(Map<String,String> map : list){
                if(map != null && !map.isEmpty()){
                    Object object = map.get("counts");

                    resultMap.put(map.get("flName"),Integer.parseInt(String.valueOf(object)));
                }else {
                    resultMap.put("管制器具",0);
                    resultMap.put("爆炸物品",0);
                    resultMap.put("枪支弹药",0);
                    resultMap.put("易燃易爆",0);
                    resultMap.put("腐蚀性物品",0);
                    resultMap.put("其他",0);
                }

            }
        }else{
            resultMap.put("管制器具",0);
            resultMap.put("爆炸物品",0);
            resultMap.put("枪支弹药",0);
            resultMap.put("易燃易爆",0);
            resultMap.put("腐蚀性物品",0);
            resultMap.put("其他",0);
        }
        Map<String,String> sumMap = dealResultInfoBiz.todayContrabandSum(pointIds);
        if(sumMap != null && !sumMap.isEmpty()){
            Object o = sumMap.get("sum");
            resultMap.put("总数",Integer.parseInt(String.valueOf(o)));

        }else{
            resultMap.put("总数",0);
        }

        return JSON.toJSONString(resultMap);
    }

    //获取站点的视频信息begin
    //1.违禁品报警（有X光机图片X_PIC_PATH）
    //2.手检处置（有可见光抓拍图片 PIC_PATH）
    //3.查获登记（ you可见光）
    //4.一键报警（有可见光抓拍图片 PIC_PATH）
    //5.web录入
    //6.app录入
    public static String changeTypeName(String type){
        String msg="其他类型";
        if(StringUtils.isNotEmpty(type)){
            switch (type){
                case "1":
                    msg="违禁品报警";
                    break;
                case "2":
                    msg="手检处置";
                    break;
                case "3":
                    msg="查获登记";
                    break;
                case "4":
                    msg="一键报警";
                    break;
                case "5":
                    msg="web录入";
                    break;
                case "6":
                    msg="app录入";
                    break;
            }
        }
        return msg;
    }


}
