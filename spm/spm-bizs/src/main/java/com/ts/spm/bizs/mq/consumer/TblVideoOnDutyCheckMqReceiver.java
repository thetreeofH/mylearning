package com.ts.spm.bizs.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.DateUtil;
import com.github.wxiaoqi.security.common.util.JDKBase64Util;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.jzpmq.TblVideoOnDutyCheckBiz;
import com.ts.spm.bizs.entity.jzpmq.TblVideoOnDutyCheck;
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
@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "${mq.config.video.duty.check.queue}", autoDelete = "false"), exchange = @Exchange(value = "${mq.config.video.duty.check.exchange}", type = ExchangeTypes.TOPIC), key = "*.on.duty.check.add"), containerFactory = "rabbitListenerContainerFactory")
public class TblVideoOnDutyCheckMqReceiver
		extends BaseController<TblVideoOnDutyCheckBiz, TblVideoOnDutyCheck, String> {
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
					JSONObject jsonObject = JSONObject.parseObject(result1);// 转换json对象
					TblVideoOnDutyCheck info = new TblVideoOnDutyCheck();
					info.setId(UUIDUtils.generateUuid());
					info.setJudgePictureUserId(jsonObject.getString("judge_picture_user_id"));
					info.setJudgePictureUserName(jsonObject.getString("judge_picture_user_name"));
					info.setPointId(jsonObject.getString("point_id"));
					info.setCaptureAddr(new BigDecimal(jsonObject.getInteger("capture_addr")));
					info.setCreateTime(new Date());
					String filePath1 = capturePicturePath + "/"
							+ DateUtil.date2Str(new Date(), "yyyy/MM/dd/HH/mm/ss");
					String capturePicture = jsonObject.getString("capture_picture");
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
					info.setCaptureDescribe(jsonObject.getString("capture_describe"));
					info.setCaptureTime(DateUtil.parse(jsonObject.getString("capture_time"),"yyyy-MM-dd HH:mm:ss"));
					info.setUpdateTime(new Date());
					baseBiz.insertSelective2(info);// 插入数据库
				}
			}, 0, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
