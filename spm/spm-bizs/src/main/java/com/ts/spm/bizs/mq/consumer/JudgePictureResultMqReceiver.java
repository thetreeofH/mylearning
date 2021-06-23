package com.ts.spm.bizs.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.DateUtil;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.jzpmq.JudgePictureResultInfoBiz;
import com.ts.spm.bizs.entity.jzpmq.JudgePictureResultInfo;
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
 * @ClassName JudgePictureResultMqReceiver
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/7/13 15:41
 * @Version 1.0
 * updater 马居朝
 * 新增LINE49-74 try{JSONObject *****}catch{****}
 * 原内容为LINE76-90,已注释
 **/
@Component
@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "${mq.config.judge.picture.result.queue}", autoDelete = "false"), exchange = @Exchange(value = "${mq.config.judge.picture.result.exchange}", type = ExchangeTypes.TOPIC), key = "*.judge.picture.result.update"), containerFactory = "rabbitListenerContainerFactory")
public class JudgePictureResultMqReceiver
		extends BaseController<JudgePictureResultInfoBiz, JudgePictureResultInfo, String> {
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
					try{
						JSONObject jsonObject = JSONObject.parseObject(result1);// 转换json对象
						JudgePictureResultInfo info = new JudgePictureResultInfo();
						info.setId(UUIDUtils.generateUuid());
						Date first_time = DateUtil.stringToDate(jsonObject.getString("first_time"));
						info.setFirstTime(first_time);
						String pointId = jsonObject.getString("pointId");
						info.setPointid(pointId);
						info.setSn(jsonObject.getString("sn"));
						info.setMissionId(pointId + DateUtil.date2Str(first_time, "yyyyMMddHHmmss"));
						info.setJudgePictureUserId(jsonObject.getString("judge_picture_user_id"));
						if(jsonObject.getString("location_of_suspected_items") != null){
							info.setLocationOfSuspectedItems(jsonObject.getString("location_of_suspected_items"));
						}
						if(jsonObject.getString("suspected_forbidden_type") != null){
							info.setSuspectedForbiddenType(new BigDecimal(jsonObject.getString("suspected_forbidden_type")));
						}
						if(jsonObject.getString("suspected_forbidden_subtype") != null){
							info.setSuspectedForbiddenSubtype(new BigDecimal(jsonObject.getString("suspected_forbidden_subtype")));
						}

						info.setCreateTime(new Date());
						baseBiz.insertSelective(info);// 插入数据库
					}catch (Exception e){
						e.printStackTrace();
					}
//					JSONObject jsonObject = JSONObject.parseObject(result1);// 转换json对象
//					JudgePictureResultInfo info = new JudgePictureResultInfo();
//					info.setId(UUIDUtils.generateUuid());
//					Date first_time = DateUtil.stringToDate(jsonObject.getString("first_time"));
//					info.setFirstTime(first_time);
//					String pointId = jsonObject.getString("pointId");
//					info.setPointid(pointId);
//					info.setSn(jsonObject.getString("sn"));
//					info.setMissionId(pointId + DateUtil.date2Str(first_time, "yyyyMMddHHmmss"));
//					info.setJudgePictureUserId(jsonObject.getString("judge_picture_user_id"));
//					info.setLocationOfSuspectedItems(jsonObject.getString("location_of_suspected_items"));
//					info.setSuspectedForbiddenType(new BigDecimal(jsonObject.getString("suspected_forbidden_type")));
//					info.setSuspectedForbiddenSubtype(new BigDecimal(jsonObject.getString("suspected_forbidden_subtype")));
//					info.setCreateTime(new Date());
//					baseBiz.insertSelective(info);// 插入数据库
				}
			}, 0, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
