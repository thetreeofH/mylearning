package com.ts.spm.bizs.rest.jzparrange;

import java.util.List;
import java.util.Map;

import com.ts.spm.bizs.biz.jzparrange.TblDutyPlanBiz;
import com.ts.spm.bizs.entity.jzparrange.TblDutyPlan;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("arrange")
@CheckClientToken
@CheckUserToken
@Api(tags = "排班管理")
public class TblDutyPlanController extends BaseController<TblDutyPlanBiz, TblDutyPlan, String> {

	@Autowired
	private TblDutyPlanBiz tblDutyPlanBiz;
	
	@ApiOperation("按时间和用户姓名查询排班记录")
	@RequestMapping(value = "/automatic/selectInfo", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public TableResultResponse selectInfo(
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "userName", required = false) String userName,
			@RequestParam(value = "userType", required = false) String userType) {
		try {
			List<Map> list =tblDutyPlanBiz.selectInfo(startTime,endTime,userName,userType);
			return new TableResultResponse(0, list);
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return TableResultResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation("查询当前时间有没有排班记录")
	@RequestMapping(value = "/automatic/selectDateStatus", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public ObjectRestResponse selectDateStatus(@RequestBody Map<String,String> map) {
		try {
			List<Map> list= tblDutyPlanBiz.selectDateStatus(map);
			Object flag = "0";
			if(list.size() != 0) {
				flag = "1";
			}
			return ObjectRestResponse.ok(flag);
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ObjectRestResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation("自动排班")
	@RequestMapping(value = "/automatic/save", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public ObjectRestResponse save(@RequestBody Map<String,Object> map) {
		try {
			tblDutyPlanBiz.automaticSave(map);
			return ObjectRestResponse.ok();
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ObjectRestResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation("删除值班人员")
	@RequestMapping(value = "/automatic/delect/{id}", method = RequestMethod.DELETE)
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public ObjectRestResponse delect(@PathVariable String id) {
		try {
			tblDutyPlanBiz.delect(id);
			return ObjectRestResponse.ok();
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ObjectRestResponse.error(e.getMessage());
		}
	}
}
