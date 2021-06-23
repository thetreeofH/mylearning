package com.ts.spm.bizs.biz.jzpacm;

/**
* @author 作者biguopeng:
* @version 创建时间：2020年9月8日 
* 说明:
*/

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ts.spm.bizs.mapper.jzpacm.HomeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeManBiz {

	@Autowired
	HomeMapper homeMapper;

	public List<Object> selectCount() {
		List<Object> list = new ArrayList();
		// 查询过包树人工开包树 ai开包数
		List<Map> list1 = homeMapper.selectCount();
		list.add(list1);
		// 查询安检量统计
		List<Map> list2 = homeMapper.selectInspectCount();
		if (list2.size() == 0) {
			list2 = homeMapper.selectInfo();
		}
		list.add(list2);
		// 时段过包统计
		List<Map> list3 = homeMapper.selectWrapCount();
		list.add(list3);
		// 时段过包统计
		List<Map> list4 = homeMapper.selectWrapCount();
		list.add(list4);
		// 查出违禁品统计
		List<Map> list5 = homeMapper.selectProhibitCount();
		list.add(list5);
		return list;
	}

} 
