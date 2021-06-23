package com.ts.spm.bizs.mapper.jzparrange;

import java.util.List;
import java.util.Map;

import com.ts.spm.bizs.entity.jzparrange.TblDutyMan;
import com.ts.spm.bizs.vo.jzparrange.TblDutyManVo;
import org.apache.ibatis.annotations.Param;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;

public interface TblDutyManMapper extends CommonMapper<TblDutyMan> {

	List<String> selectGroup(@Param("userType") int userType);

	List<TblDutyManVo> selectGroupList(@Param("groupId") String groupId, @Param("userType") String userType);

	List<TblDutyManVo> selectNoGroupList(@Param("userType") int userType);

	void updateGroupId(@Param("id") String id);
	
	void delGroupPerson(@Param("id") String id);

	void updateDelFlag(@Param("id") String id);

	void updateGroupSave(@Param("id") String id, @Param("groupId") String groupId);

	List<Map> getPersonnelList(@Param("userType") String userType);

	List<String> selectGroupName(@Param("group") String group);

}