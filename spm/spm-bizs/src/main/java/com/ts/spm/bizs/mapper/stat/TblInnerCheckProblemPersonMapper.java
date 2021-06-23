package com.ts.spm.bizs.mapper.stat;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.stat.TblInnerCheckProblemPerson;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TblInnerCheckProblemPersonMapper extends CommonMapper<TblInnerCheckProblemPerson> {

    public Integer addInnerCheckProblemPerson(@Param("tblInnerCheckProblemPerson") TblInnerCheckProblemPerson tblInnerCheckProblemPerson);

    public void deleInnerCheckProblemPerson(@Param("checkId") Integer checkId);

    public int getId();

    List<Map<String,Object>> getPersonCheckList(@Param("pointIds") List<String> pointIds, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("ajyIdcard") String ajyIdcard, @Param("ajyName") String ajyName, @Param("ajyType") String ajyType);
}