package com.ts.spm.bizs.mapper.stat;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.stat.TblInnerCheckProblem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TblInnerCheckProblemMapper extends CommonMapper<TblInnerCheckProblem> {

    public Integer addInnerCheckProblem(@Param("tblInnerCheckProblem") TblInnerCheckProblem tblInnerCheckProblem);

    public void deleInnerCheckProblem(@Param("checkId") Integer checkId);

    List<TblInnerCheckProblem> getProblemByCheckId(@Param("checkId") Integer checkId);

    public int getId();
}