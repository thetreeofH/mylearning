package com.ts.spm.bizs.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.DateUtil;
import com.github.wxiaoqi.security.common.util.JDKBase64Util;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.jzpmq.DealResultInfoBiz;
import com.ts.spm.bizs.entity.jzpmq.Alarm;
import com.ts.spm.bizs.entity.jzpmq.DealResultInfo;
import com.ts.spm.bizs.rest.msg.DangerInfoController;
import com.ts.spm.bizs.rest.up.UpController;
import com.ts.spm.bizs.util.FtpUtil;
import com.ts.spm.bizs.vo.msg.PointDangerVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by fengzheng on 2020/7/13.
 */

/**
 * @ClassName HandleResultMqReceiver
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/7/13 15:14
 * @Version 1.0
 * updater 马居朝
 * 新增	alarm.setPicPath(map.get("naturalLightPictures"));
 * System.out.println("有违禁品，需报警！报警类型为："+handleResult);的原位置在if( "3".equals(handleResult)){}后，现位置移动到了if(map != null && !map.isEmpty()){}后面
 **/
@Component
@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "${mq.config.handle.result.queue}", autoDelete = "false"), exchange = @Exchange(value = "${mq.config.handle.result.exchange}", type = ExchangeTypes.TOPIC), key = "*.handle.result.add"), containerFactory = "rabbitListenerContainerFactory")
public class HandleResultMqReceiver extends BaseController<DealResultInfoBiz, DealResultInfo, String> {

    @Value("${oipftp.local}")
    private String local;

    @Value("${oipftp.prot}")
    private String prot;

    @Value("${oipftp.userName}")
    private String userName;

    @Value("${oipftp.passWord}")
    private String passWord;

    @Value("${oipftp.suspectedItemsPicturePath}")
    private String suspectedItemsPicturePath;

//	@Autowired
//	AdminFeign adminFeign;

    @Autowired
    DangerInfoController dangerInfoController;
    @Autowired
    DealResultInfoBiz dealResultInfoBiz;

    @Autowired
    UpController upCtr;

    @RabbitHandler
    public void process(byte[] result) {
        try {
            ScheduledExecutorService executorService = ContextLoaderServlet.getExecutorService();
            executorService.schedule(new Runnable() {
                public void run() {
                    String result1 = null;

                    try {
                        result1 = new String(result, "UTF-8");
                        //System.out.println("res1:"+result1);
                    } catch (UnsupportedEncodingException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    try {
                        JSONObject jsonObject = JSONObject.parseObject(result1);// 转换json对象
                        DealResultInfo info = new DealResultInfo();
                        info.setId(UUIDUtils.generateUuid());
                        String ft = jsonObject.getString("first_time");
                        String pointId = jsonObject.getString("pointId");
                        Date first_time = null;
                        String missionId = null;
                        if (StringUtils.isNotEmpty(ft)) {
                            if (ft.indexOf(".") != -1) {//如果是带毫秒数
                                first_time = DateUtil.stringToDate2(jsonObject.getString("first_time"));
                                missionId = pointId + DateUtil.date2Str(first_time, "yyyyMMddHHmmssSSS");
                            } else {
                                first_time = DateUtil.stringToDate(jsonObject.getString("first_time"));
                                missionId = pointId + DateUtil.date2Str(first_time, "yyyyMMddHHmmss") + "000";
                            }
                        } else {
                            missionId = pointId;
                        }
                        info.setMissionId(missionId);
                        info.setFirstTime(first_time);
                        info.setPointid(pointId);
                        info.setSn(jsonObject.getString("sn"));
                        info.setJudgePictureSource(new BigDecimal(jsonObject.getString("judge_picture_source")));
                        info.setHandleUserId(jsonObject.getString("handle_user_id"));
                        String handleResult = jsonObject.getString("handle_result");

                        info.setHandleResult(new BigDecimal(handleResult));
                        if ("0".equals(handleResult) || "2".equals(handleResult)) {
                            info.setIfCheck(new BigDecimal(2));//【0：放行（无违禁品）和2: 转乘其他交通工具】无需核录
                        } else {
                            info.setIfCheck(new BigDecimal(0));
                        }
                        info.setLocationOfSuspectedItems(jsonObject.getString("location_of_suspected_items"));

                        info.setSuspectedForbiddenType(new BigDecimal(jsonObject.getString("suspected_forbidden_type")));
                        info.setSuspectedForbiddenSubtype(new BigDecimal(jsonObject.getString("suspected_forbidden_subtype")));
                        info.setPassengerName(jsonObject.getString("passenger_name"));
                        info.setPassengerIdCard(jsonObject.getString("passenger_id_card"));


                        String filePath1 = suspectedItemsPicturePath + "/"
                                + DateUtil.date2Str(new Date(), "yyyy/MM/dd/HH/mm/ss");
                        String suspected_items_picture = jsonObject.getString("suspected_items_picture");
                        /*System.out.println("====================================test3=======================");
						System.out.println(suspected_items_picture);
						System.out.println("====================================test4=======================");*/
                        String fileName1 = "suspectedItemsPicturePath" + DateUtil.getRevTime() + ".jpg";
                        byte[] bytes = JDKBase64Util.decryptBASE64(suspected_items_picture.trim());
                        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
                        Map<String, Object> map1 = FtpUtil.uploadFile(local, userName, passWord, Integer.parseInt(prot),
                                filePath1, fileName1, inputStream);
                        if ((boolean) map1.get("status") == true) {
                            info.setSuspectedItemsPicture(map1.get("url").toString());
                        }


                        //新增suspected_items_visual_picture图片 begin
                        String filePath2 = suspectedItemsPicturePath + "/"
                                + DateUtil.date2Str(new Date(), "yyyy/MM/dd/HH/mm/ss");
                        String suspected_items_visual_picture = jsonObject.getString("suspected_items_visual_picture");
					/*	System.out.println("====================================test1=======================");
						System.out.println(suspected_items_visual_picture);
						System.out.println("====================================test2=======================");*/
                        String fileName2 = "suspectedItemsVisualPicture" + DateUtil.getRevTime() + ".jpg";
                        byte[] bytes1 = null;
                        if (suspected_items_visual_picture != null) {
                            bytes1 = JDKBase64Util.decryptBASE64(suspected_items_visual_picture.trim());
                            ByteArrayInputStream inputStream1 = new ByteArrayInputStream(bytes1);
                            Map<String, Object> map2 = FtpUtil.uploadFile(local, userName, passWord, Integer.parseInt(prot),
                                    filePath2, fileName2, inputStream1);
                            if ((boolean) map2.get("status") == true) {
                                info.setSuspectedItemsVisualPicture(map2.get("url").toString());
                            }
                            //新增suspected_items_picture图片 end
                        }

                        //新增aux_suspected_items_picture图片 begin
                        String aux_suspected_items_picture = jsonObject.getString("aux_suspected_items_picture");
                        if (StringUtils.isNotEmpty(aux_suspected_items_picture)) {
                            String filePath3 = suspectedItemsPicturePath + "/"
                                    + DateUtil.date2Str(new Date(), "yyyy/MM/dd/HH/mm/ss");
					/*	System.out.println("====================================test1=======================");
						System.out.println(suspected_items_visual_picture);
						System.out.println("====================================test2=======================");*/
                            String fileName3 = "auxSuspectedItemsPicture" + DateUtil.getRevTime() + ".jpg";
                            byte[] bytes2 = null;
                            if (aux_suspected_items_picture != null) {
                                bytes2 = JDKBase64Util.decryptBASE64(aux_suspected_items_picture.trim());
                                ByteArrayInputStream inputStream2 = new ByteArrayInputStream(bytes2);
                                Map<String, Object> map3 = FtpUtil.uploadFile(local, userName, passWord, Integer.parseInt(prot),
                                        filePath3, fileName3, inputStream2);
                                if ((boolean) map3.get("status") == true) {
                                    info.setAuxSuspectedItemsPicture(map3.get("url").toString());
                                }
                            }
                        }
                        //新增aux_suspected_items_picture图片 end
                        String judge_user_id = jsonObject.getString("judge_user_id");
                        if (StringUtils.isNotEmpty(judge_user_id)) {
                            info.setJudgeUserId(judge_user_id);
                        }

                        info.setCreateTime(new Date());
                        info.setHandleMode(jsonObject.getString("handle_mode"));

                        int result = baseBiz.insertSelective2(info);// 插入数据库
                        if (result == 1) {
                            System.out.println("处置结果保存成功！！");
                            System.out.println("处置方式为：" + handleResult);
                            //3:报警 推送报警到安检信息化首页
                            if ("3".equals(handleResult)) {

                                //websocket推送至安检信息化首页
                                Map<String, String> map = dealResultInfoBiz.selectDealResult(info.getMissionId());
                                if (map != null && !map.isEmpty()) {
                                    //3:报警 推送报警到安检信息化首页
                                    System.out.println("有违禁品，需报警！报警类型为：" + handleResult);
                                    //websocket推送至安检信息化首页
//										Alarm alarm = new Alarm();
//										alarm.setAlarmTime(info.getFirstTime());
                                    PointDangerVo alarm = new PointDangerVo();
                                    alarm.setPicPath(map.get("naturalLightPictures"));
                                    alarm.setAlarmTime(info.getFirstTime().toString());
                                    alarm.setHandleMode(info.getHandleMode());
                                    alarm.setHandleResult(Integer.parseInt(info.getHandleResult().toString()));
                                    alarm.setHandleUserId(info.getHandleUserId());
                                    alarm.setHandleUserName(map.get("handleUserName"));
                                    alarm.setJudgePictureSource(Integer.parseInt(info.getJudgePictureSource().toString()));
                                    alarm.setMemo(map.get("memo"));
                                    alarm.setPassengerIdCard(info.getPassengerIdCard());
                                    alarm.setPassengerName(info.getPassengerName());
                                    alarm.setPointId(info.getPointid());
                                    alarm.setSuspectedForbiddenType(Integer.parseInt(info.getSuspectedForbiddenType().toString()));
                                    alarm.setSuspectedForbiddenTypeName(map.get("suspectedForbiddenTypeName"));
                                    alarm.setSuspectedForbiddenSubtype(Integer.parseInt(info.getSuspectedForbiddenSubtype().toString()));
                                    alarm.setSuspectedForbiddenSubtypeName(map.get("suspectedForbiddenSubtypeName"));
                                    alarm.setSuspectedItemsPicture(info.getSuspectedItemsPicture());

                                    ObjectRestResponse objectRestResponse = dangerInfoController.Alarm(alarm);
                                    if (objectRestResponse.getStatus() == 200) {

                                        System.out.println("【3:报警】,报警发送成功！！");

                                    }
									/*ObjectMapper om=new ObjectMapper();
									try {
										WebSocketUtil.sendMessage(om.writeValueAsString(alarm),null);
									} catch (IOException e) {
										e.printStackTrace();
									}*/
                                } else {
                                    System.out.println("通过MissionId未查询到相关处置结果数据！！查询失败！！！");
                                }
                            }
                            upCtr.PackageAlarmInfo(info.getId(), suspected_items_picture, suspected_items_visual_picture);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 0, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
