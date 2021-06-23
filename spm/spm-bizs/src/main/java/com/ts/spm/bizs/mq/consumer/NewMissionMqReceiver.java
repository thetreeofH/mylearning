package com.ts.spm.bizs.mq.consumer;

/**
* Created by fengzheng on 2020/7/10.
*/

import com.alibaba.fastjson.JSONObject;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.DateUtil;
import com.github.wxiaoqi.security.common.util.JDKBase64Util;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.jzpmq.PictureFigureTaskInfoBiz;
import com.ts.spm.bizs.entity.jzpmq.PictureFigureTaskInfo;
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
 **/
@Component
@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "${mq.config.new.mission.queue}", autoDelete = "false"), exchange = @Exchange(value = "${mq.config.new.mission.exchange}", type = ExchangeTypes.TOPIC), key = "*.handle.mission.add"), containerFactory = "rabbitListenerContainerFactory")
public class NewMissionMqReceiver extends BaseController<PictureFigureTaskInfoBiz, PictureFigureTaskInfo, String> {

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

	@Value("${oipftp.naturalLlightPicturesPath}")
	private String naturalLlightPicturesPath;

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
						result1 = new String(result,"UTF-8");
					} catch (Exception e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					JSONObject jsonObject = JSONObject.parseObject(result1);// 转换json对象
					//System.out.println("------>"+result1);
					PictureFigureTaskInfo info = new PictureFigureTaskInfo();
					info.setId(UUIDUtils.generateUuid());
					Date first_time = DateUtil.stringToDate(jsonObject.getString("first_time"));
					info.setFirstTime(first_time);
					String pointId = jsonObject.getString("pointId");
					info.setPointid(pointId);
					info.setSn(jsonObject.getString("sn"));
					info.setMissionId(pointId + DateUtil.date2Str(first_time, "yyyyMMddHHmmss"));


					//X-ray_machine_picture
					String filePath1 = xRayMachinePicturePath + "/"
							+ DateUtil.date2Str(new Date(), "yyyy/MM/dd/HH/mm/ss");
					String Xray_machine_picture = jsonObject.getString("X-ray_machine_picture");
					String fileName1 = "xRayMachinePicturePath" + DateUtil.getRevTime() + ".jpg";
					byte[] bytes = null;
					try {
						bytes = JDKBase64Util.decryptBASE64(Xray_machine_picture.trim());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
					Map<String, Object> map1 = FtpUtil.uploadFile(local, userName, passWord, Integer.parseInt(prot),
							filePath1, fileName1, inputStream);
					if ((boolean) map1.get("status") == true) {
						info.setxRayMachinePicture(map1.get("url").toString());
					}
					//X-ray_machine_picture


					//natural_light_pictures
					String filePath2 = naturalLlightPicturesPath + "/"
							+ DateUtil.date2Str(new Date(), "yyyy/MM/dd/HH/mm/ss");
					String natural_light_pictures = jsonObject.getString("natural_light_pictures");
					String fileName2 = "naturalLlightPicturesPath" + DateUtil.getRevTime() + ".jpg";
					byte[] bytes2 = null;
					try {
						bytes2 = JDKBase64Util.decryptBASE64(natural_light_pictures.trim());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ByteArrayInputStream inputStream2 = new ByteArrayInputStream(bytes2);
					Map<String, Object> map2 = FtpUtil.uploadFile(local, userName, passWord, Integer.parseInt(prot),
							filePath2, fileName2, inputStream2);
					if ((boolean) map2.get("status") == true) {
						info.setNaturalLightPictures(map2.get("url").toString());
					}
					//natural_light_pictures




					//X-aux_ray_machine_picture
					String x_aux_ray_machine_picture = jsonObject.getString("X-aux_ray_machine_picture");
					if(StringUtils.isNotEmpty(x_aux_ray_machine_picture)) {
						String filePath3 = naturalLlightPicturesPath + "/"
								+ DateUtil.date2Str(new Date(), "yyyy/MM/dd/HH/mm/ss");
						String fileName3 = "XAuxRayMachinePicture" + DateUtil.getRevTime() + ".jpg";
						byte[] bytes3 = null;
						try {
							bytes3 = JDKBase64Util.decryptBASE64(x_aux_ray_machine_picture.trim());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						ByteArrayInputStream inputStream3 = new ByteArrayInputStream(bytes3);
						Map<String, Object> map3 = FtpUtil.uploadFile(local, userName, passWord, Integer.parseInt(prot),
								filePath3, fileName3, inputStream3);
						if ((boolean) map3.get("status") == true) {
							info.setXAuxRayMachinePicture(map3.get("url").toString());
						}
					}
					//X-aux_ray_machine_picture

					info.setCreateTime(new Date());
					baseBiz.insertSelective(info);// 插入数据库

					upCtr.PackageInfo(info.getId(), Xray_machine_picture, natural_light_pictures);
				}
			}, 0, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
