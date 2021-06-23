package com.ts.spm.bizs.biz.jzpmq;

import java.util.List;
import java.util.Map;

import com.ts.spm.bizs.entity.jzpmq.JudgePictureResultInfo;
import com.ts.spm.bizs.mapper.jzpmq.JudgePictureResultInfoMapper;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;

@Service
public class JudgePictureResultInfoBiz extends BusinessBiz<JudgePictureResultInfoMapper, JudgePictureResultInfo> {

	public List<Map<String, String>> query(String startTime, String endTime, String judgePictureName,
			Integer suspectedForbiddenType, List<String> pointIds, String pointId) {
		// TODO Auto-generated method stub
		return mapper.query(startTime, endTime, judgePictureName, suspectedForbiddenType, pointIds, pointId);
	}

	public JudgePictureResultInfo selectByMissionId(String missionId){
		return mapper.selectByMissionId(missionId);
	}

}
