package com.ts.spm.bizs.mapper.stat;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.stat.TblInnerCheckProblemPhoto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TblInnerCheckProblemPhotoMapper extends CommonMapper<TblInnerCheckProblemPhoto> {
    Integer addInnerCheckProblemPhoto(@Param("tblInnerCheckProblemPhoto") TblInnerCheckProblemPhoto tblInnerCheckProblemPhoto);

    public void deleInnerCheckProblemPhoto(@Param("checkId") Integer checkId);

    public int getId();


    List<TblInnerCheckProblemPhoto> getPhotoByCheckID(@Param("checkId") Integer checkId, @Param("problemId") Integer problemId);
}