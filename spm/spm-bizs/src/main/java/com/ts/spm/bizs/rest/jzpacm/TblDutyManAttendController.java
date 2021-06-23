package com.ts.spm.bizs.rest.jzpacm;

import java.util.Map;

import com.ts.spm.bizs.biz.jzpftgmnt.TblDutyManAttendBiz;
import com.ts.spm.bizs.entity.jzpftgmnt.TblDutyManAttend;
import com.ts.spm.bizs.entity.jzplog.TblOnlineTime;
import com.ts.spm.bizs.rest.jzplog.OnlineTimeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("attend")
@CheckClientToken
@CheckUserToken
@Api(tags = "上下班打卡管理")
public class TblDutyManAttendController extends BaseController<TblDutyManAttendBiz, TblDutyManAttend, String> {

	@Autowired
	private TblDutyManAttendBiz tblDutyManAttendBiz;
	
	@Autowired
	OnlineTimeController onlineTimeCtr;
//	private JzpLogFeign jzpLogFeign;
	
	@Autowired
    private RedisTemplate<String, String> redisTemplate;
	
	@ApiOperation("保存上班打卡记录")
	@RequestMapping(value = "/upPunchCards", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public ObjectRestResponse upPunchCards() {
		try {
//			Map<String, String> map = onlineTimeCtr.add(null,null);
//			redisTemplate.opsForValue().set(BaseContextHandler.getUserID(), map.get("message"));
//			tblDutyManAttendBiz.upPunchCards(map.get("message"));
//			return  ObjectRestResponse.ok("上班打卡成功！");

			ObjectRestResponse res=onlineTimeCtr.add(null,null);
			redisTemplate.opsForValue().set(BaseContextHandler.getUserID(), res.getMessage());
			tblDutyManAttendBiz.upPunchCards(res.getMessage());
			return  ObjectRestResponse.ok("上班打卡成功！");
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ObjectRestResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation("保存下班打卡记录")
	@RequestMapping(value = "/downPunchCards", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public ObjectRestResponse downPunchCards() {
		try {
			String id = redisTemplate.opsForValue().get(BaseContextHandler.getUserID());
//			JSONObject jsonObject=new JSONObject();
//			jsonObject.put("id", id);
//			Map<String, String> update = jzpLogFeign.update(jsonObject);
//			tblDutyManAttendBiz.downPunchCards(id);
//			redisTemplate.delete(BaseContextHandler.getUserID());//销毁
//			return  ObjectRestResponse.ok("下班打卡成功！");

			TblOnlineTime o=new TblOnlineTime();
			o.setId(id);
			ObjectRestResponse res=onlineTimeCtr.update(o);
			tblDutyManAttendBiz.downPunchCards(id);
			redisTemplate.delete(BaseContextHandler.getUserID());//销毁
			return  ObjectRestResponse.ok("下班打卡成功！");
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ObjectRestResponse.error(e.getMessage());
		}
	}
}
