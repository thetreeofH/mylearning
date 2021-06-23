package com.ts.spm.bizs.biz.stat;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.stat.TblInnerCheckProblemPhoto;
import com.ts.spm.bizs.mapper.stat.TblInnerCheckProblemPhotoMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author luoyu
 * @Date 2020/6/28 16:19
 * @Version 1.0
 */
@Service
public class InnerCheckProblemPhotoBiz extends BusinessBiz<TblInnerCheckProblemPhotoMapper, TblInnerCheckProblemPhoto> {
    public Integer addInnerCheckProblemPhoto(TblInnerCheckProblemPhoto tblInnerCheckProblemPhoto){
        return mapper.addInnerCheckProblemPhoto(tblInnerCheckProblemPhoto);
    }

    public void deleInnerCheckProblemPhoto(Integer checkId){
        mapper.deleInnerCheckProblemPhoto(checkId);
    }

    public List<TblInnerCheckProblemPhoto> getPhotoByCheckID(Integer checkId,Integer problemId){
        return mapper.getPhotoByCheckID(checkId,problemId);
    }

    public void addInnerCheckProblemPhoto(Integer checkId, Integer problemId, TblInnerCheckProblemPhoto tblInnerCheckProblemPhoto){
        tblInnerCheckProblemPhoto.setCheckId(checkId);
        tblInnerCheckProblemPhoto.setProbId(problemId);
        tblInnerCheckProblemPhoto.setCrtTime(new Date());
        tblInnerCheckProblemPhoto.setCrtUserId(BaseContextHandler.getUserID());
        tblInnerCheckProblemPhoto.setCrtUserName(BaseContextHandler.getUsername());
        tblInnerCheckProblemPhoto.setTenantId(BaseContextHandler.getTenantID());
        mapper.addInnerCheckProblemPhoto(tblInnerCheckProblemPhoto);
    }

    public int getId(){
        return mapper.getId();
    }


}
