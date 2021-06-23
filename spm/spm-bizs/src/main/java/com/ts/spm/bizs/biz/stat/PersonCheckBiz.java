package com.ts.spm.bizs.biz.stat;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.stat.TblInnerCheckProblemPerson;
import com.ts.spm.bizs.mapper.stat.TblInnerCheckProblemPersonMapper;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import com.ts.spm.bizs.rest.admin.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @Author luoyu
 * @Date 2020/6/12 16:45
 * @Version 1.0
 */
@Service
public class PersonCheckBiz extends BusinessBiz<TblInnerCheckProblemPersonMapper, TblInnerCheckProblemPerson> {

    @Autowired
    private UserController userCtr;
    @Autowired
    private CheckPointController checkPointCtr;

    public void addInnerCheckProblemPeson(Integer checkId, Integer problemId, TblInnerCheckProblemPerson tblInnerCheckProblemPerson){
        tblInnerCheckProblemPerson.setCheckId(checkId);
        tblInnerCheckProblemPerson.setProbId(problemId);
        tblInnerCheckProblemPerson.setCrtTime(new Date());
        tblInnerCheckProblemPerson.setCrtUserId(BaseContextHandler.getUserID());
        tblInnerCheckProblemPerson.setCrtUserName(BaseContextHandler.getUsername());
        tblInnerCheckProblemPerson.setTenantId(BaseContextHandler.getTenantID());
        mapper.addInnerCheckProblemPerson(tblInnerCheckProblemPerson);

    }

    public void deleInnerCheckProblemPeson(Integer checkId){
        mapper.deleInnerCheckProblemPerson(checkId);
    }

    public List<TblInnerCheckProblemPerson> getPersonByCheckId(Integer checkId, Integer probId){

        Example example = new Example(TblInnerCheckProblemPerson.class);
        Example.Criteria criteria = example.createCriteria();

        if(checkId!=null){
            criteria.andEqualTo("checkId",checkId);
        }

        if(probId!=null){
            criteria.andEqualTo("probId",probId);
        }


        List<TblInnerCheckProblemPerson> list=selectByExample(example);
        return list;
    }

    public List<Map<String,Object>> getPersonCheck(String departId, String startTime, String endTime, String ajyIdcard, String ajyName, String ajyType){
        List<String> pointIds=new ArrayList<String>();
        if(!"".equals(departId)&&departId!=null){
            pointIds=checkPointCtr.getAllPointByDepart(departId);
        }else {
            pointIds=userCtr.getPoint(BaseContextHandler.getUserID());
        }
        if(pointIds.size()<=0){
            pointIds.add("");
        }

        List<Map<String,Object>> personList=mapper.getPersonCheckList(pointIds,startTime+" 00:00:00",endTime+" 23:59:59",ajyIdcard,ajyName,ajyType);

        return personList;
    }

    public int getId(){
        return mapper.getId();
    }
}
