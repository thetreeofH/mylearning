package com.ts.spm.bizs.biz.jzpftgmnt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ts.spm.bizs.entity.jzpftgmnt.TblDutyManAttend;
import com.ts.spm.bizs.mapper.jzpftgmnt.TblDutyManAttendMapper;
import org.springframework.stereotype.Service;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.util.DateUtil;

@Service
public class TblDutyManAttendBiz extends BusinessBiz<TblDutyManAttendMapper, TblDutyManAttend> {

	public Map<String,String> selectByUserId(String userId){
		return mapper.selectByUserId(userId);

	}

	/************** 用户打卡 *****************/

	public void upPunchCards(String id) {
		TblDutyManAttend tblDutyManAttend = new TblDutyManAttend();
		String date = DateUtil.date2Str(new Date(), "");// 获取当前时间
		Map planInfo1 = mapper.selectInfo(date, DateUtil.date2Str(new Date(), "yyyy-MM-dd"));// 查询当前时间属于哪个班次
		List<String> list = mapper.selectUserCar(BaseContextHandler.getUserID());
		if (list.size() != 0) {
			String tid = list.get(0);
			if (planInfo1 != null) {
				int compareDate = DateUtil.compareDate(planInfo1.get("eTime").toString(), DateUtil.date2Str(new Date()),
						"yyyy-MM-dd HH:mm:ss");
				if (compareDate == -1) {
					mapper.updateStatus(tid, planInfo1.get("eTime").toString());
				} else {
					mapper.updateStatus(tid, DateUtil.date2Str(new Date()));
				}
			} else {
				mapper.updateStatus(tid, DateUtil.date2Str(new Date()));
			}
		}
		Integer userType = 3; // 其他用户
		if (mapper.selsectUserType(BaseContextHandler.getUserID(), "YCZJY") != 0) {
			userType = 1;
		} else if (mapper.selsectUserType(BaseContextHandler.getUserID(), "KJY") != 0) {
			userType = 2;
		}
		tblDutyManAttend.setId(id);
		if (planInfo1 != null) {
			tblDutyManAttend.setScheduleId(planInfo1.get("scheduleId").toString());
		}
		tblDutyManAttend.setUserId(BaseContextHandler.getUserID());
		tblDutyManAttend.setDutyDate(DateUtil.date2Str(new Date(), "yyyy-MM-dd"));
		tblDutyManAttend.setStime(new Date());
		tblDutyManAttend.setUserType(userType);
		tblDutyManAttend.setCrtTime(new Date());
		tblDutyManAttend.setState(1);
		mapper.insertSelective(tblDutyManAttend);
	}

	public void downPunchCards(String id) {
		mapper.updateStatus(id, DateUtil.date2Str(new Date()));
	}

	/************** 值机员考勤 *****************/
	// 查询值班人员考勤统计
	public List<Map> query(String beginTime, String endTime, String userName) {
		// oracle mapper
		// return mapper.query(beginTime, endTime, userName);

		List<Map> list = mapper.mysqlQuery(beginTime, endTime, userName);
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			int daysxiangcha = DateUtil.daysxiangcha(endTime, beginTime);
			List<BigDecimal> res = mapper.queryRecess(beginTime, endTime, map.get("userId").toString());
			if (daysxiangcha - res.size() == -1) {
				map.put("recessCount", "0");
			} else {
				map.put("recessCount", daysxiangcha - res.size());
			}
			BigDecimal parseInt2 = (BigDecimal) map.get("totalHours");
			if (parseInt2.compareTo(new BigDecimal(0)) == -1) {
				map.put("totalHours", "0");
			}
			Map<String, BigDecimal> map2 = mapper.mysqlQuery2(map.get("userId").toString(), beginTime, endTime);
			if (map2 == null) {
				map.put("beLateCount", "0");
				map.put("beLateMinuteCount", "0");
			} else {
				map.put("beLateCount", map2.get("beLateCount").toString());
				map.put("beLateMinuteCount", map2.get("beLateMinuteCount").toString());
			}
			Map<String, BigDecimal> map3 = mapper.mysqlQuery3(map.get("userId").toString(), beginTime, endTime);
			if (map3 == null) {
				map.put("earlierLeaveCount", "0");
				map.put("earlierLeaveMinuteCount", "0");
			} else {
				map.put("earlierLeaveCount", map3.get("earlierLeaveCount").toString());
				map.put("earlierLeaveMinuteCount", map3.get("earlierLeaveMinuteCount").toString());
			}
			Integer query4 = mapper.mysqlQuery4(map.get("userId").toString(), beginTime, endTime);
			if (query4 < 0) {
				map.put("neglectWorkCount", "0");
			} else {
				map.put("neglectWorkCount", query4 + "");
			}
		}
		return list;

	}

	public List<Map> queryInfo(String userId, String period) {
		Date parse = DateUtil.parse(period, "yyyy-MM");
		String monthBegin = DateUtil.getMonthBegin(parse);// 获取月数开始时间
		String monthEnd = DateUtil.getMonthEnd(parse);// 获取月数结束时间
		long dayInterval = DateUtil.dayInterval(DateUtil.parse(monthBegin, "yyy-MM-dd"),
				DateUtil.parse(monthEnd, "yyy-MM-dd"));
		ArrayList<Map> arrayList = new ArrayList<Map>();
		for (int i = 0; i < dayInterval; i++) {
			List<Map> list = mapper.queryInfoAttend(userId, monthBegin);
			if (list.size() != 0) {
				Map map1 = list.get(0);
				Map map2 = mapper.queryInfo(map1.get("scheduleId").toString(), map1.get("userId").toString());
				if (map2 == null) {
					map1.put("status", 1);// 加班
				} else {
					int compareDate1 = DateUtil.compareDate(map1.get("workDate").toString(),
							map2.get("stime").toString(), "yyyy-MM-dd HH:mm:ss");
					int compareDat2 = DateUtil.compareDate(map1.get("offDutyDate").toString(),
							map2.get("etime").toString(), "yyyy-MM-dd HH:mm:ss");
					if (compareDate1 == 1) {
						map1.put("status", 4);// 迟到
					} else if (compareDat2 == -1) {
						map1.put("status", 5);// 早退
					} else if (compareDate1 == -1 && compareDat2 == 1) {
						map1.put("status", 3);// 正常
					}
				}
				String classType = mapper.queryClassType(map1.get("scheduleId").toString());
				map1.put("classType", classType);
				map1.remove("scheduleId");
				map1.remove("userId");
				arrayList.add(map1);
			}
			monthBegin = DateUtil.addDay(monthBegin, 1);
		}
		return arrayList;
	}

	public List<Map> queryPerformance(String beginTime, String endTime, String userName) {
		List<Map> queryPerformance = mapper.queryPerformance(beginTime, endTime, userName);
		/*
		 * for (int i = 0; i < queryPerformance.size(); i++) { Map map1 =
		 * queryPerformance.get(i); Double probability =
		 * mapper.queryProbability(beginTime, endTime, map1.get("userId").toString());
		 * map1.put("probability", probability); Map<String, String> map2 =
		 * mapper.queryifBeLate(beginTime, endTime, map1.get("userId").toString()); if
		 * (map2 != null) { int neglectWorkCount =
		 * Integer.parseInt(map2.get("neglectWorkCount")); if (neglectWorkCount < 0) {
		 * map1.put("ifNeglectWork", 0); } else { map1.put("ifNeglectWork",
		 * neglectWorkCount); } int beLateCount =
		 * Integer.parseInt(map2.get("beLateCount")); map1.put("ifBeLate", beLateCount);
		 * int beLateMinuteCount = Integer.parseInt(map2.get("beLateMinuteCount")); int
		 * earlierLeaveCount = Integer.parseInt(map2.get("earlierLeaveCount")); int
		 * earlierLeaveMinuteCount =
		 * Integer.parseInt(map2.get("earlierLeaveMinuteCount"));
		 * map1.put("averageLeaveTime", (earlierLeaveMinuteCount + beLateMinuteCount) /
		 * (earlierLeaveCount + beLateCount)); } else { map1.put("ifNeglectWork", 0);
		 * map1.put("ifBeLate", 0); map1.put("averageLeaveTime", 0); }
		 * 
		 * }
		 */

		return queryPerformance;
	}

	/**************
	 * 开检员考勤
	 * 
	 * @param pointIds
	 *****************/
	public List<Map> openInspectionQuery(String beginTime, String endTime, String userName, List<String> pointIds) {
		return mapper.openInspectionQuery(beginTime, endTime, userName, pointIds);
	}

}
