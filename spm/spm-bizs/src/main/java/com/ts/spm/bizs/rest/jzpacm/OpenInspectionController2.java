package com.ts.spm.bizs.rest.jzpacm;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ts.spm.bizs.biz.jzpftgmnt.TblDutyManAttendBiz;
import com.ts.spm.bizs.entity.jzpftgmnt.TblDutyManAttend;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import com.ts.spm.bizs.util.PoiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.ag.core.context.BaseContextHandler;
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
@RequestMapping("openInspection2")
@CheckClientToken
@CheckUserToken
@Api(tags = "开检员考勤管理")
public class OpenInspectionController2 extends BaseController<TblDutyManAttendBiz, TblDutyManAttend, String> {

	@Autowired
	private TblDutyManAttendBiz tblDutyManAttendBiz;

	@Autowired
	CheckPointController checkPointCtr;
//	AdminFeign adminFeign;

	@ApiOperation("考勤统计列表")
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public TableResultResponse query(@RequestParam(defaultValue = "") String beginTime, String endTime,
			@RequestParam(defaultValue = "") String userName, @RequestParam(defaultValue = "") String departId,
			@RequestParam(name = "limit", defaultValue = "10") int limit,
			@RequestParam(name = "page", defaultValue = "1") int page) {
		Page<Object> result = PageHelper.startPage(page, limit);
		/*
		 * List<Map<String, String>> points = adminFeign.getpoint(departId,
		 * BaseContextHandler.getUserID()); if (CollectionUtils.isEmpty(points)) {
		 * return new TableResultResponse<>(0, points); } List<String> pointIds =
		 * points.stream().map(u -> u.get("id")).collect(Collectors.toList());
		 */
		List<Map> list = tblDutyManAttendBiz.openInspectionQuery(beginTime, endTime, userName,null);
		return new TableResultResponse(result.getTotal(), list);
	}

	@ApiOperation("考勤统计列表导出")
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public void exportdata(HttpServletRequest req, HttpServletResponse res, @RequestParam(defaultValue = "") String beginTime, String endTime,
						   @RequestParam(defaultValue = "") String userName, @RequestParam(defaultValue = "") String departId) {
		try {
			String colHeads[] = { "车站", "安检点", "安检员姓名", "平均开检时长", "开检率"};
			String keys[] = { "userName", "totalHours", "onDutyCount", "recessCount", "beLateCount",
					"beLateMinuteCount", "earlierLeaveCount", "earlierLeaveMinuteCount", "neglectWorkCount" };
			List<Map<String, Object>> points = checkPointCtr.getpoint(departId, BaseContextHandler.getUserID());
			if (CollectionUtils.isEmpty(points)) {
				PoiUtil.start_download(req, res, DateUtil.date2Str(new Date()), null, colHeads, keys);
			}else {
				List<String> pointIds = points.stream().map(u -> u.get("id").toString()).collect(Collectors.toList());
				List<Map> list = tblDutyManAttendBiz.openInspectionQuery(beginTime, endTime, userName,pointIds);
				PoiUtil.start_download(req, res, DateUtil.date2Str(new Date()), list, colHeads, keys);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
