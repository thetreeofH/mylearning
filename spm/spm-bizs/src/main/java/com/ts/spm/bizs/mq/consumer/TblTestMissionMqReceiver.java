package com.ts.spm.bizs.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.DateUtil;
import com.github.wxiaoqi.security.common.util.JDKBase64Util;
import com.ts.spm.bizs.biz.jzpmq.TblTestMissionBiz;
import com.ts.spm.bizs.entity.jzpmq.TblTestMission;
import com.ts.spm.bizs.util.FtpUtil;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
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
 * @ClassName FictitiousJudgePictureResultMqReceiver
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/7/13 15:41
 * @Version 1.0
 **/
@Component
@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "${mq.config.fictitious.judge.picture.result.queue}", autoDelete = "false"), exchange = @Exchange(value = "${mq.config.fictitious.judge.picture.result.exchange}", type = ExchangeTypes.TOPIC), key = "*.worker.judge.picture.result.update"), containerFactory = "rabbitListenerContainerFactory")
public class TblTestMissionMqReceiver
		extends BaseController<TblTestMissionBiz, TblTestMission, String> {
	@Value("${oipftp.local}")
	private String local;

	@Value("${oipftp.prot}")
	private String prot;

	@Value("${oipftp.userName}")
	private String userName;

	@Value("${oipftp.passWord}")
	private String passWord;

	@Value("${oipftp.capturePicturePath}")
	private String capturePicturePath;

	@RabbitHandler
	public void process(byte[] result) {
		try {
			ScheduledExecutorService executorService = ContextLoaderServlet.getExecutorService();
			executorService.schedule(new Runnable() {
				public void run() {
					String result1 = null;
					try {
						result1 = new String(result, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					TblTestMission info = new TblTestMission();
					JSONObject jsonObject = JSONObject.parseObject(result1);// 转换json对象

					info.setId(jsonObject.getString("id"));
					//System.out.println("主键id:"+jsonObject.getString("id"));
					info.setMissionId(jsonObject.getString("mission_id"));
					info.setIfDistinguish(jsonObject.getInteger("if_distinguish"));
					//System.out.println("是否识别:"+new BigDecimal(jsonObject.getInteger("if_distinguish")));
					try{
						String filePath1 = capturePicturePath + "/"
								+ DateUtil.date2Str(new Date(), "yyyy/MM/dd/HH/mm/ss");
						String capturePicture = jsonObject.getString("capture_picture");
						if(capturePicture != null){
							String fileName1 = "capturePicturePath" + DateUtil.getRevTime() + ".jpg";
							byte[] bytes = null;
							try {
								bytes = JDKBase64Util.decryptBASE64(capturePicture.trim());
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
							Map<String, Object> map1 = FtpUtil.uploadFile(local, userName, passWord, Integer.parseInt(prot),
									filePath1, fileName1, inputStream);
							if ((boolean) map1.get("status") == true) {
								info.setCapturePictureUrl(map1.get("url").toString());
							}
						}

						info.setCaptureDescribe(jsonObject.getString("capture_describe"));
						String captureTime = jsonObject.getString("capture_time");
						if(captureTime != null && "".equals(captureTime)){
							info.setCaptureTime(DateUtil.parse(captureTime,"yyyy-MM-dd HH:mm:ss"));
						}
						info.setUpdateTime(new Date());
						baseBiz.updateSelectiveById2(info);// 更新数据库
					}catch (Exception e){
						e.printStackTrace();
					}

				}
			}, 0, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
