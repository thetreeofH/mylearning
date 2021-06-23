package com.ts.spm.bizs.mapper.jzpmq;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.jzpmq.PictureFigureTaskInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PictureFigureTaskInfoMapper extends CommonMapper<PictureFigureTaskInfo> {
  /*  List<Map<String, String>> query(@Param("startTime") String startTime,
                                    @Param("endTime") String endTime,
                                    @Param("pointIds") List<String> pointIds,
                                    @Param("pointId") String pointId,
                                    @Param("page") String pointId,
                                    @Param("pages") String pointId);*/
    public int selectTotalCount(Map<String,Object> map);
    public List<Map<String, String>> query(Map<String,Object> map);
}