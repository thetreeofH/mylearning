package com.ts.spm.bizs.rest.jzpacm;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ts.spm.bizs.biz.jzpftgmnt.TblDutyManAttendBiz;
import com.ts.spm.bizs.entity.jzpftgmnt.TblDutyManAttend;
import com.ts.spm.bizs.util.PoiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.DateUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("operator")
@CheckClientToken
@CheckUserToken
@Api(tags = "值机员考勤管理")
public class AttendanceController extends BaseController<TblDutyManAttendBiz, TblDutyManAttend, String> {

	@Autowired
	private TblDutyManAttendBiz tblDutyManAttendBiz;
	
	@ApiOperation("考勤统计列表")
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public TableResultResponse query(String beginTime, String endTime, String userName,
			@RequestParam(name = "limit", defaultValue = "10") int limit,
			@RequestParam(name = "page", defaultValue = "1") int page) {
		Page<Object> result = PageHelper.startPage(page, limit);
		List<Map> list = tblDutyManAttendBiz.query(beginTime, endTime, userName);
		return new TableResultResponse(result.getTotal(), list);
	}

	@ApiOperation("值机员考勤详情")
	@RequestMapping(value = "/queryInfo", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public TableResultResponse queryInfo(String userId, String period) {
		List<Map> list = tblDutyManAttendBiz.queryInfo(userId, period);
		return new TableResultResponse(0, list);
	}

	@ApiOperation("考勤统计列表导出")
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public void exportdata(HttpServletRequest req, HttpServletResponse res, String beginTime, String endTime, String userName) {
		try {
			String colHeads[] = { "用户姓名", "总工时", "出勤班次", "休息天数", "迟到次数", "迟到时间总分", "早退次数", "早退时间总分", "旷工次数" };
			String keys[] = { "userName", "totalHours", "onDutyCount", "recessCount", "beLateCount",
					"beLateMinuteCount", "earlierLeaveCount", "earlierLeaveMinuteCount", "neglectWorkCount" };
			List<Map> list = tblDutyManAttendBiz.query(beginTime, endTime, userName);
			PoiUtil.start_download(req, res, DateUtil.date2Str(new Date()), list, colHeads, keys);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@ApiOperation("值机员绩效考核列表")
	@RequestMapping(value = "/queryPerformance", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public TableResultResponse queryPerformance(String startTime, String endTime, String userName,
			@RequestParam(name = "limit", defaultValue = "10") int limit,
			@RequestParam(name = "page", defaultValue = "1") int page) {
		Page<Object> result = PageHelper.startPage(page, limit);
		String date2Str = DateUtil.date2Str(new Date(), "yyyy-MM-dd");
		if (DateUtil.compareDate(endTime, date2Str, "yyyy-MM-dd") == 1) {
			endTime = date2Str;
		}
		List<Map> list = tblDutyManAttendBiz.queryPerformance(startTime, endTime, userName);
		return new TableResultResponse(result.getTotal(), list);
	}

	@ApiOperation("值机员绩效考核导出")
	@RequestMapping(value = "/exportPerformance", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public void exportPerformance(HttpServletRequest req, HttpServletResponse res, String beginTime, String endTime, String userName) {
		try {
			String date2Str = DateUtil.date2Str(new Date(), "yyyy-MM-dd");
			if (DateUtil.compareDate(endTime, date2Str, "yyyy-MM-dd") == -1) {
				endTime = date2Str;
			}
			String colHeads[] = { "用户姓名", "是否旷工", "是否迟到", "判图任务总数", "平均判图时长(分钟)", "平均班次离岗时间", "危险品插图命中概率" };
			String keys[] = { "userName", "ifNeglectWork", "ifBeLate", "assignmentCount", "averageTime",
					"averageLeaveTime", "probability" };
			List<Map> list = tblDutyManAttendBiz.queryPerformance(beginTime, endTime, userName);
			PoiUtil.start_download(req, res, DateUtil.date2Str(new Date()), list, colHeads, keys);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
