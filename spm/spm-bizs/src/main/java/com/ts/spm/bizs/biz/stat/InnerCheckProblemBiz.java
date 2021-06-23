package com.ts.spm.bizs.biz.stat;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.stat.TblInnerCheckProblem;
import com.ts.spm.bizs.mapper.stat.TblInnerCheckProblemMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author luoyu
 * @Date 2020/6/28 16:18
 * @Version 1.0
 */
@Service
public class InnerCheckProblemBiz extends BusinessBiz<TblInnerCheckProblemMapper, TblInnerCheckProblem> {

    public void deleInnerCheckProblem(Integer checkId){
        mapper.deleInnerCheckProblem(checkId);
    }

    public List<TblInnerCheckProblem> getProblemByCheckId(Integer checkId){
        return mapper.getProblemByCheckId(checkId);
    }

    public int addInnerCheckProblem(Integer checkId,TblInnerCheckProblem tblInnerCheckProblem){

        tblInnerCheckProblem.setCheckId(checkId);
        tblInnerCheckProblem.setCrtTime(new Date());
        tblInnerCheckProblem.setCrtUserId(BaseContextHandler.getUserID());
        tblInnerCheckProblem.setCrtUserName(BaseContextHandler.getUsername());
        tblInnerCheckProblem.setTenantId(BaseContextHandler.getTenantID());
        Integer problemId=mapper.addInnerCheckProblem(tblInnerCheckProblem);

        return problemId;
    }

    public int getId(){
        return mapper.getId();
    }

}
