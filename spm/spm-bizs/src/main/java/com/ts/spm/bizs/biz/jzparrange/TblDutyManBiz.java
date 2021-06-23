package com.ts.spm.bizs.biz.jzparrange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ts.spm.bizs.entity.jzparrange.TblDutyMan;
import com.ts.spm.bizs.mapper.jzparrange.TblDutyManMapper;
import com.ts.spm.bizs.vo.jzparrange.TblDutyManVo;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;

@Service
public class TblDutyManBiz extends BusinessBiz<TblDutyManMapper, TblDutyMan> {

	//查询未分组的
	public List<TblDutyManVo> getNoGroupsList(int userType) {
		List<TblDutyManVo> list = mapper.selectNoGroupList(userType);
		if(userType == 1) {
			List<Map> list2 = mapper.getPersonnelList("YCZJY");
			delect(list,list2);
		}if(userType == 2) {
			List<Map> list2 = mapper.getPersonnelList("ZXZBY");
			delect(list,list2);
		}
		List<TblDutyManVo> list3 = mapper.selectNoGroupList(userType);
		return list3;
	}
	
	private void delect(List<TblDutyManVo> list, List<Map> list2) {
		for (int i = 0; i < list.size(); i++) {
			TblDutyManVo tblDutyManVo = list.get(i);
			String id = "";
			for (int j = 0; j < list2.size(); j++) {
				Map map = list2.get(j);
				if (map.get("userId").toString().equals(tblDutyManVo.getUserId())) {
					id = "";
					break;
				}
				id = tblDutyManVo.getId();
			}
			if (id != "") {
				updateDelFlag(id);
			}
		}
	}

	//查询已分组的
	public List<Map> selectGroupsList(int userType) {
		ArrayList<Map> arrayList = new ArrayList<Map>();
		List<String> groups = mapper.selectGroup(userType);//查询当前多少组
		for (int i = 0; i < groups.size(); i++) {
			Map map = new HashMap<>();
			List<TblDutyManVo> list = mapper.selectGroupList(groups.get(i),String.valueOf(userType));
			map.put("id", i+1);
			map.put("groupId", groups.get(i));
			map.put("data", list);
			arrayList.add(map);
		}
		return arrayList;
	}

	public void updateGroupId(String id) {
		mapper.updateGroupId(id);
	}

	public void updateDelFlag(String id) {
		mapper.updateDelFlag(id);
	}

	public void updateGroupSave(List ids, String groupId) {
		for (int i = 0; i < ids.size(); i++) {
			mapper.updateGroupSave(ids.get(i).toString(),groupId);
		}
	}

	public List<Map> getPersonnelList(String userType) {
		List<Map> list = mapper.getPersonnelList(userType);
		return list;
	}

	public List<String> selectGroupName(String group) {
		return mapper.selectGroupName(group);
	}

	public void delGroupPerson(String id) {
		mapper.delGroupPerson(id);
	}
}
