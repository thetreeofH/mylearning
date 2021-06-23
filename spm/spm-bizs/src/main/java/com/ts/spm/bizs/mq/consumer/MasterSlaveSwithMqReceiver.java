package com.ts.spm.bizs.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.jzpmq.MasterBackMachineInfoBiz;
import com.ts.spm.bizs.entity.jzpmq.MasterBackMachineInfo;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by fengzheng on 2020/7/13.
 */

/**
 * @ClassName MasterSlaveSwithMqReceiver
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/7/13 15:31
 * @Version 1.0
 **/
@Component
@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "${mq.config.master.slave.switch.queue}", autoDelete = "false"), exchange = @Exchange(value = "${mq.config.master.slave.switch.exchange}", type = ExchangeTypes.TOPIC), key = "*.server.master.slave.switch.exchange.add"), containerFactory = "rabbitListenerContainerFactory")
public class MasterSlaveSwithMqReceiver
		extends BaseController<MasterBackMachineInfoBiz, MasterBackMachineInfo, String> {

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
					MasterBackMachineInfo info = new MasterBackMachineInfo();
					info.setId(UUIDUtils.generateUuid());
					info.setMasterIp(jsonObject.getString("master_ip"));
					info.setCreateTime(new Date());
					baseBiz.insertSelective(info);// 插入数据库
				}
			}, 0, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
