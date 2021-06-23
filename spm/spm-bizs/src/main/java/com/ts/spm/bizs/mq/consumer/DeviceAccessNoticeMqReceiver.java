package com.ts.spm.bizs.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.DateUtil;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.jzpmq.DevJoinInfoBiz;
import com.ts.spm.bizs.entity.jzpmq.DevJoinInfo;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by fengzheng on 2020/7/13.
 */

/**
 * @ClassName DeviceAccessNoticeMqReceiver
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/7/13 15:37
 * @Version 1.0
 **/
@Component
@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "${mq.config.device.access.notice.queue}", autoDelete = "false"), exchange = @Exchange(value = "${mq.config.device.access.notice.exchange}", type = ExchangeTypes.TOPIC), key = "*.server.device.access.notice.add"), containerFactory = "rabbitListenerContainerFactory")
public class DeviceAccessNoticeMqReceiver extends BaseController<DevJoinInfoBiz, DevJoinInfo, String> {
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
					DevJoinInfo info = new DevJoinInfo();
					info.setId(UUIDUtils.generateUuid());
					info.setServiceIp(jsonObject.getString("service_ip"));
					info.setServiceStatus(new BigDecimal(jsonObject.getString("service_status")));
					info.setServiceSource(new BigDecimal(jsonObject.getString("service_source")));
					info.setMissionDistributeServerIp(jsonObject.getString("mission_distribute_server_ip"));
					info.setDiscription(jsonObject.getString("description"));
					info.setNoticeTime(DateUtil.stringToDate(jsonObject.getString("notice_time")));
					info.setCreateTime(new Date());
					baseBiz.insertSelective(info);// 插入数据库
				}
			}, 0, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}