package com.ts.spm.bizs.biz.jzparrange;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ts.spm.bizs.entity.jzparrange.TblDutyPersonSchedule;
import com.ts.spm.bizs.entity.jzparrange.TblDutyPlan;
import com.ts.spm.bizs.entity.jzparrange.TblDutySchedule;
import com.ts.spm.bizs.mapper.jzparrange.TblDutyPlanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.util.DateUtil;
import com.github.wxiaoqi.security.common.util.UUIDUtils;

@Service
public class TblDutyPlanBiz extends BusinessBiz<TblDutyPlanMapper, TblDutyPlan> {

	@Autowired
	private TblDutyScheduleBiz tblDutyScheduleBiz;

	@Autowired
	private TblDutyPersonScheduleBiz tblDutyPesrsonScheduleBiz;

	@Autowired
	private TblDutyPlanBiz tblDutyPlanBiz;

	public void automaticSave(Map<String, Object> map) {

		String begintime = (String) map.get("begintime");// 自动排班开始时间
		String endtime = (String) map.get("endtime");// 自动排班结束时间
		Integer count = Integer.parseInt(map.get("count").toString());// 自动排班班次
		List<List> ids = (List<List>) map.get("ids");
		List<List> dates = (List<List>) map.get("dates");// 班次时间
		Date startDate = DateUtil.parse(begintime, "yyy-MM-dd");// 转换日期类型
		Date closingDate = DateUtil.parse(endtime, "yyy-MM-dd");
		long dayInterval = DateUtil.dayInterval(startDate, closingDate) + 1;// 计算出要拍班的总天数
		String begintime3 = begintime;
		Integer flag = Integer.parseInt(map.get("flag").toString());
		if (flag == 1) {
			ArrayList<String> dateList = new ArrayList<String>();
			for (int i = 0; i < dayInterval; i++) {
				dateList.add(begintime3);
				begintime3 = DateUtil.addDay(begintime3, 1);// 当前天数+1
			}
			mapper.delectDate(dateList);
		}
		String begintime2 = begintime;
		int a = 0;
		for (int i = 0; i < dayInterval; i++) {// 遍历排班天数
			for (int j = 0; j < count; j++) {
				if (a == ids.size()) {
					a = 0;
				}
				String scheduleId = UUIDUtils.generateUuid();// 班次ID
				TblDutySchedule tblDutySchedule = new TblDutySchedule();// 班次列表
				tblDutySchedule.setId(scheduleId);
				Date[] stimeEtime = stimeEtime(count, j, begintime2, dates);// 查询当前班次的开始结束时间
				tblDutySchedule.setStime(stimeEtime[0]);
				tblDutySchedule.setEtime(stimeEtime[1]);
				tblDutySchedule.setCrtUserId(BaseContextHandler.getUserID());
				tblDutySchedule.setCrtUserName(BaseContextHandler.getUsername());
				tblDutySchedule.setDelFlag((short) 0);
				tblDutyScheduleBiz.insertSelective(tblDutySchedule);
				List<String> idList = ids.get(a);
				for (int k = 0; k < idList.size(); k++) {// 插入数组
					TblDutyPersonSchedule tblDutyPesrsonSchedule = new TblDutyPersonSchedule();// 班次人员表
					tblDutyPesrsonSchedule.setId(UUIDUtils.generateUuid());
					tblDutyPesrsonSchedule.setScheduleId(scheduleId);// 班次ID
					tblDutyPesrsonSchedule.setUserId(idList.get(k));
					tblDutyPesrsonSchedule.setCrtUserId(BaseContextHandler.getUserID());
					tblDutyPesrsonSchedule.setCrtUserName(BaseContextHandler.getUsername());
					tblDutyPesrsonSchedule.setDelFlag((short) 0);
					tblDutyPesrsonScheduleBiz.insertSelective(tblDutyPesrsonSchedule);
				}
				TblDutyPlan tblDutyPlan = new TblDutyPlan();// 排班计划
				tblDutyPlan.setId(UUIDUtils.generateUuid());
				tblDutyPlan.setScheduleId(scheduleId);// 班次ID
				tblDutyPlan.setDutyDate(begintime2);
				tblDutyPlan.setStime(stimeEtime[0]);
				tblDutyPlan.setEtime(stimeEtime[1]);
				tblDutyPlan.setCrtUserId(BaseContextHandler.getUserID());
				tblDutyPlan.setCrtUserName(BaseContextHandler.getUsername());
				tblDutyPlan.setDelFlag((short) 0);
				tblDutyPlanBiz.insertSelective(tblDutyPlan);
				a++;
			}
			begintime2 = DateUtil.addDay(begintime2, 1);// 当前天数+1
		}
	}
	
	public Date[] stimeEtime(int count, int i, String begintime, List<List> dates) {// 返回当前班次的开始结束时间
		Date date[] = new Date[2];
		date[0] = DateUtil.parse(begintime + " " + dates.get(i % count).get(0), DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS);// 班次开始时间
		if(DateUtil.compareDate(dates.get(i).get(0).toString(), dates.get(i).get(1).toString(), "HH:mm:ss") == 1) {
			date[1] = DateUtil.parse(DateUtil.addDay(begintime, 1) + " " + dates.get(i % count).get(1), DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS);// 班次开始时间
		}else {
			date[1] = DateUtil.parse(begintime + " " + dates.get(i % count).get(1), DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS);// 班次开始时间
		}
		return date;
	}

	public List<Map> selectDateStatus(Map<String,String> map) {
		return mapper.selectDateStatus(map.get("beginTime"),map.get("endTime"));
	}

	public List<Map> selectInfo(String startTime, String endTime, String userName, String userType) {
		return mapper.selectInfo(startTime,endTime,userName,userType);
	}

	public void delect(String id) {
		mapper.delect(id);
	}
}
