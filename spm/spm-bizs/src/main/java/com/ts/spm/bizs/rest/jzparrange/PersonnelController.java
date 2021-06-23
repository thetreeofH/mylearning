package com.ts.spm.bizs.rest.jzparrange;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ts.spm.bizs.biz.jzparrange.TblDutyManBiz;
import com.ts.spm.bizs.entity.jzparrange.TblDutyMan;
import com.ts.spm.bizs.vo.jzparrange.TblDutyManVo;
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

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.UUIDUtils;

import io.swagger.annotations.ApiOperation;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@RestController
@RequestMapping("personnel")
@IgnoreClientToken
@CheckUserToken
public class PersonnelController extends BaseController<TblDutyManBiz, TblDutyMan, String> {

	@Autowired
	private TblDutyManBiz tblDutyManBiz;

	@ApiOperation("查询值机开检员列表")
	@RequestMapping(value = "/getPersonnelList", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public TableResultResponse getPersonnelList(@RequestParam(value = "userType", required = false) String userType) {
		try {
			List<Map> list = tblDutyManBiz.getPersonnelList(userType);
			return new TableResultResponse(list.size(), list);
		} catch (Exception e) {
			return TableResultResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation("查询未分组的值班人员")
	@RequestMapping(value = "/getNoGroupsList", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public TableResultResponse getNoGroupsList(@RequestParam(value = "userType", required = false) int userType) {
		try {
			List<TblDutyManVo> list = tblDutyManBiz.getNoGroupsList(userType);
			return new TableResultResponse(list.size(), list);
		} catch (Exception e) {
			return TableResultResponse.error(e.getMessage());
		}
	}

	@ApiOperation("查询已分组的值班人员")
	@RequestMapping(value = "/getGroupsList", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public TableResultResponse getGroupsList(@RequestParam(value = "userType") int userType) {
		try {
			List<Map> list = tblDutyManBiz.selectGroupsList(userType);
			return new TableResultResponse(list.size(), list);
		} catch (Exception e) {
			return TableResultResponse.error(e.getMessage());
		}
	}

	@ApiOperation("保存值班人员")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public ObjectRestResponse save(@RequestBody TblDutyMan tblDutyMan) {
		try {
			Example example = new Example(TblDutyMan.class);
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("userName", tblDutyMan.getUserName());
			criteria.andEqualTo("delFlag", 0);
			List<TblDutyMan> selectByExample = baseBiz.selectByExample(example);
			if (selectByExample.size() == 0) {
				tblDutyMan.setId(UUIDUtils.generateUuid());
				tblDutyMan.setCrtTime(new Date());
				tblDutyMan.setCrtUserId(BaseContextHandler.getUserID());
				tblDutyMan.setCrtUserName(BaseContextHandler.getUsername());
				tblDutyMan.setDelFlag((short) 0);
				baseBiz.insertSelective(tblDutyMan);
				return ObjectRestResponse.ok();
			} else {
				return ObjectRestResponse.error(500, "当前用户已经存在！");
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ObjectRestResponse.error(e.getMessage());
		}
	}

	@ApiOperation("删除值班人员")
	@RequestMapping(value = "/delect/{id}", method = RequestMethod.DELETE)
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public ObjectRestResponse delect(@PathVariable String id) {
		try {
			tblDutyManBiz.updateDelFlag(id);
			return ObjectRestResponse.ok();
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ObjectRestResponse.error(e.getMessage());
		}
	}

	@ApiOperation("值班分组")
	@RequestMapping(value = "/group/save", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public ObjectRestResponse groupSave(@RequestBody Map map) {
		try {
			if (map.get("groupflag").equals("1")) {
				List<String> groups = tblDutyManBiz.selectGroupName(map.get("groupName").toString());
				if (groups.size() != 0) {
					return ObjectRestResponse.error("当前组名已存在！");
				}
			}
			Object object = map.get("ids");
			tblDutyManBiz.updateGroupSave((List)object, map.get("groupName").toString());
			return ObjectRestResponse.ok();
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ObjectRestResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation("删除值班分组")
	@RequestMapping(value = "/delect/group", method = RequestMethod.DELETE)
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public ObjectRestResponse delectGroup(@RequestParam(value = "groupId") String groupId) {
		try {
			Example example = new Example(TblDutyMan.class);
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("groupId", groupId);
			List<TblDutyMan> list = baseBiz.selectByExample(example);
			for (int i = 0; i < list.size(); i++) {
				TblDutyMan tblDutyMan = list.get(i);
				tblDutyManBiz.updateGroupId(tblDutyMan.getId());
			}
			return ObjectRestResponse.ok();
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ObjectRestResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation("删除值班分组中人员")
	@RequestMapping(value = "/delect/group/person", method = RequestMethod.DELETE)
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public ObjectRestResponse delGroupPerson(@RequestParam(value = "id") String id) {
		try {
			tblDutyManBiz.delGroupPerson(id);
			return ObjectRestResponse.ok();
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ObjectRestResponse.error(e.getMessage());
		}
	}
}
