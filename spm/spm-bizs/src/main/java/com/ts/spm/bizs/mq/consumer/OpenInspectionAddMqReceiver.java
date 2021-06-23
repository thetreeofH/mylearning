package com.ts.spm.bizs.mq.consumer;

/**
 * Created by fengzheng on 2020/7/10.
 */

import com.alibaba.fastjson.JSONObject;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.DateUtil;
import com.github.wxiaoqi.security.common.util.JDKBase64Util;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.jzpmq.DealResultInfoBiz;
import com.ts.spm.bizs.biz.jzpoip.TblOpenInspectionInfoBiz;
import com.ts.spm.bizs.entity.jzpmq.DealResultInfo;
import com.ts.spm.bizs.entity.jzpoip.TblOpenInspectionInfo;
import com.ts.spm.bizs.rest.up.UpController;
import com.ts.spm.bizs.util.FtpUtil;
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
 * @ClassName MqReceive
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/7/10 19:38
 * @Version 1.0
 * updater 马居朝
 * 新增判断 if(jsonObject.getString("confirm_contraband_subtype") != null) {}
 * 			if(jsonObject.getString("passenger_name") != null){}
 * 			if(jsonObject.getString("passenger_id_card") != null){}
 * 			if(jsonObject.getString("memo") != null){}
 * 			if(jsonObject.getString("passenger_phone_num") != null){}
 * 			Date first_time = null;
 * 			if(jsonObject.getString("first_time") != null) {}
 * 			if(jsonObject.getString("confirm_contraband_subtype") != null){}
 * 以及	    //更新违禁品主类型
 * 			dealResultInfo.setSuspectedForbiddenType(new BigDecimal(jsonObject.getInteger("confirm_contraband_type")));
 * 			//更新违禁品子类型
 * 			dealResultInfo.setSuspectedForbiddenSubtype(new BigDecimal(jsonObject.getString("confirm_contraband_subtype")));
 **/
@Component
@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "${mq.config.openInspection.information.add.queue}", autoDelete = "false"), exchange = @Exchange(value = "${mq.config.openInspection.information.add.exchange}", type = ExchangeTypes.TOPIC), key = "*.information.add"), containerFactory = "rabbitListenerContainerFactory")
public class OpenInspectionAddMqReceiver extends BaseController<TblOpenInspectionInfoBiz, TblOpenInspectionInfo, String> {

	@Value("${oipftp.local}")
	private String local;

	@Value("${oipftp.prot}")
	private String prot;

	@Value("${oipftp.userName}")
	private String userName;

	@Value("${oipftp.passWord}")
	private String passWord;

	@Value("${oipftp.xRayMachinePicturePath}")
	private String xRayMachinePicturePath;

	@Value("${oipftp.openInspectionPicturesPath}")
	private String openInspectionPicturesPath;

	@Autowired
	DealResultInfoBiz dealResultInfoBiz;

//	@Autowired
//	JudgePictureResultInfoBiz judgePictureResultInfoBiz;
//
//	@Autowired
//	TblOpenInspectionInfoBiz tblOpenInspectionInfoBiz;

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
						//System.out.println("收到信息核录数据："+result1);
					} catch (UnsupportedEncodingException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					JSONObject jsonObject = JSONObject.parseObject(result1);// 转换json对象
					//System.out.println(jsonObject.toJSONString());
					TblOpenInspectionInfo info = new TblOpenInspectionInfo();
					String open_inspection_mode = jsonObject.getString("open_inspection_mode");
					if(jsonObject.getString("check_source") != null) {
						info.setCheckSource(jsonObject.getString("check_source"));
					}
					System.out.println("开检方式"+open_inspection_mode);
					//如何是：4：人工录入，则不需要填first_time和sn
					if("4".equals(open_inspection_mode)){
						info.setOpenInspectionMode(open_inspection_mode);

						String pointId = jsonObject.getString("pointId");
						info.setPointid(pointId);

						info.setConfirmForbiddenType(jsonObject.getInteger("confirm_contraband_type"));
						if(jsonObject.getString("confirm_contraband_subtype") != null) {
							info.setConfirmForbiddenSubtype(new BigDecimal(jsonObject.getString("confirm_contraband_subtype")));
						}
						info.setHandleResult(jsonObject.getShort(""));
						info.setId(UUIDUtils.generateUuid());


						String filePath1 = openInspectionPicturesPath + "/"
								+ DateUtil.date2Str(new Date(), "yyyy/MM/dd/HH/mm/ss");

						String fileName1 = "openInspectionPicturesPath" + DateUtil.getRevTime() + ".jpg";
						String pictures = jsonObject.getString("pictures");
						byte[] bytes = null;
						try {
							bytes = JDKBase64Util.decryptBASE64(pictures.trim());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try{
							ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
							Map<String, Object> map1 = FtpUtil.uploadFile(local, userName, passWord, Integer.parseInt(prot),
									filePath1, fileName1, inputStream);
							if ((boolean) map1.get("status") == true) {
								info.setPictures(map1.get("url").toString());
							}
							if(jsonObject.getString("passenger_name") != null) {
								info.setPassengerName(jsonObject.getString("passenger_name"));
							}
							if(jsonObject.getString("passenger_id_card") != null) {
								info.setPassengerIdCard(jsonObject.getString("passenger_id_card"));
							}
							info.setIfContraband(jsonObject.getShort("if_contraband"));
							Short ifCheck = jsonObject.getShort("if_check");
							info.setIfCheck(ifCheck);
							if(jsonObject.getString("memo") != null) {
								info.setMemo(jsonObject.getString("memo"));
							}
							if(jsonObject.getString("passenger_phone_num") != null) {
								info.setPassengerPhoneNum(jsonObject.getString("passenger_phone_num"));
							}
							info.setCreateTime(new Date());
							int result = baseBiz.insertSelective2(info);// 插入数据库
							if(result == 1){
								try {
									if (info.getCheckSource().equalsIgnoreCase("1"))
										upCtr.DoorAlarm(info.getId());
								} catch (Exception e) {
									System.out.println("上传数据失败:" + e.getMessage());
								}
								System.out.println("保存开检数据成功！！");
								System.out.println("开检方式为："+open_inspection_mode+" 无需更新处置结果表！");

							}else{
								System.out.println("保存开检数据失败！！，请联系系统管理员！！");
							}
						}catch (Exception e){
							e.printStackTrace();
						}
					} else {

						info.setOpenInspectionMode(open_inspection_mode);
						String pointId = jsonObject.getString("pointId");
						info.setPointid(pointId);
						info.setSn(jsonObject.getString("sn"));
						String missionId = null;//pointId + DateUtil.date2Str(info.getFirstTime(), "yyyyMMddHHmmss");
						String ft=jsonObject.getString("first_time");
						if(StringUtils.isNotEmpty(ft)){
							Date first_time =null;
							if(ft.indexOf(".")!=-1){//如果是带毫秒数
								first_time = DateUtil.stringToDate2(jsonObject.getString("first_time"));
								missionId = pointId + DateUtil.date2Str(first_time, "yyyyMMddHHmmssSSS");
							}else{
								first_time = DateUtil.stringToDate(jsonObject.getString("first_time"));
								missionId = pointId + DateUtil.date2Str(first_time, "yyyyMMddHHmmss")+"000";
							}

							info.setFirstTime(first_time);

						}else{
							missionId = pointId;
						}
						info.setMissionId(missionId);
						info.setConfirmForbiddenType(jsonObject.getInteger("confirm_contraband_type"));
						if(jsonObject.getString("confirm_contraband_subtype") != null) {
							info.setConfirmForbiddenSubtype(new BigDecimal(jsonObject.getString("confirm_contraband_subtype")));
						}
						info.setHandleResult(jsonObject.getShort(""));
						info.setId(UUIDUtils.generateUuid());


						String filePath1 = openInspectionPicturesPath + "/"
								+ DateUtil.date2Str(new Date(), "yyyy/MM/dd/HH/mm/ss");

						String fileName1 = "openInspectionPicturesPath" + DateUtil.getRevTime() + ".jpg";
						String pictures = jsonObject.getString("pictures");
						byte[] bytes = null;
						try {
							bytes = JDKBase64Util.decryptBASE64(pictures.trim());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try{
							ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
							Map<String, Object> map1 = FtpUtil.uploadFile(local, userName, passWord, Integer.parseInt(prot),
									filePath1, fileName1, inputStream);
							if ((boolean) map1.get("status") == true) {
								info.setPictures(map1.get("url").toString());
							}
							info.setPassengerName(jsonObject.getString("passenger_name"));
							info.setPassengerIdCard(jsonObject.getString("passenger_id_card"));
							info.setIfContraband(jsonObject.getShort("if_contraband"));
							Short ifCheck = jsonObject.getShort("if_check");
							info.setIfCheck(ifCheck);
							info.setMemo(jsonObject.getString("memo"));
							info.setPassengerPhoneNum(jsonObject.getString("passenger_phone_num"));
							info.setCreateTime(new Date());
							int result = baseBiz.insertSelective2(info);// 插入数据库
							if(result == 1){
								System.out.println("保存开检数据成功！！，根据missionId:"+missionId+"更新处置结果表！");
								DealResultInfo dealResultInfo = dealResultInfoBiz.selectByMissionId(missionId);
								if(dealResultInfo != null){
									dealResultInfo.setIfCheck(new BigDecimal(1));
									//更新违禁品主类型
									dealResultInfo.setSuspectedForbiddenType(new BigDecimal(jsonObject.getInteger("confirm_contraband_type")));
									//更新违禁品子类型
									dealResultInfo.setSuspectedForbiddenSubtype(new BigDecimal(jsonObject.getString("confirm_contraband_subtype")));
									dealResultInfo.setUpdateTime(new Date());
									int result2 = dealResultInfoBiz.updateSelectiveById2(dealResultInfo);
									if(result2 == 1){
										System.out.println("根据missionId:"+missionId+"更新处置结果表成功！");
									}else{
										System.out.println("根据missionId:"+missionId+"更新处置结果表失败！，请联系系统管理员！！");
									}
								}
								try {
									if (info.getCheckSource().equalsIgnoreCase("0") && open_inspection_mode.equalsIgnoreCase("3"))
										upCtr.OpenInspection(info.getId());
									if (info.getCheckSource().equalsIgnoreCase("1"))
										upCtr.DoorAlarm(info.getId());
								} catch (Exception e) {
									System.out.println("上传数据失败:" + e.getMessage());
								}
							}
						}catch (Exception e){
							e.printStackTrace();
						}
					}
				}
			}, 0, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
