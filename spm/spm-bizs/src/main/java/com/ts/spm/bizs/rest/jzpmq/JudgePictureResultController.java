package com.ts.spm.bizs.rest.jzpmq;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.ts.spm.bizs.biz.jzpmq.JudgePictureResultInfoBiz;
import com.ts.spm.bizs.entity.jzpmq.JudgePictureResultInfo;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;

@RestController
@RequestMapping(value = "judgePicture")
@IgnoreClientToken
@CheckUserToken
public class JudgePictureResultController
		extends BaseController<JudgePictureResultInfoBiz, JudgePictureResultInfo, String> {

	@Autowired
	JudgePictureResultInfoBiz judgePictureResultInfoBiz;

	@Autowired
	CheckPointController checkPointCtr;

	/**
	 * 判图结果查询
	 * 
	 * @param limit
	 * @param page
	 * @param startTime
	 * @param endTime
	 * @param judgePictureName
	 * @param suspectedForbiddenType
	 * @param departId
	 * @param pointId
	 * @return
	 */
	@RequestMapping(value = "/information/query", method = RequestMethod.GET)
	public TableResultResponse query(@RequestParam(defaultValue = "10") int limit,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "judgePictureName", required = false) String judgePictureName,
			@RequestParam(value = "suspectedForbiddenType", required = false) Integer suspectedForbiddenType,
			@RequestParam(defaultValue = "") String departId, @RequestParam(defaultValue = "") String pointId) {
		if (StringUtils.isNoneBlank(startTime)) {
			startTime = startTime + " 00:00:00";
		}
		if (StringUtils.isNoneBlank(endTime)) {
			endTime = endTime + " 23:59:59";
		}

		List<Map<String, Object>> points = checkPointCtr.getpoint(departId, BaseContextHandler.getUserID());
		if(CollectionUtils.isEmpty(points)) {
            return new TableResultResponse<>(0, points);
        }
		List<String> pointIds = points.stream().map(u -> u.get("id").toString()).collect(Collectors.toList());

		Page<Object> result = PageHelper.startPage(page, limit);
		List<Map<String, String>> oipList = judgePictureResultInfoBiz.query(startTime, endTime, judgePictureName,
				suspectedForbiddenType, pointIds, pointId);

		return new TableResultResponse(result.getTotal(), oipList);
	}
}
