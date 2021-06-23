package com.ts.spm.bizs.biz.person;

import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.ts.spm.bizs.entity.person.AjyInfo;
import com.ts.spm.bizs.mapper.person.AjyInfoMapper;
import com.ts.spm.bizs.rest.admin.DepartController;
import com.ts.spm.bizs.rest.admin.UserController;
import com.ts.spm.bizs.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author luoyu
 * @Date 2020/6/4 17:45
 * @Version 1.0
 */
@Service
public class AjyBiz extends BusinessBiz<AjyInfoMapper, AjyInfo>{
    @Autowired
    private UserController userCtr;
    @Autowired
    private DepartController departCtr;


    public List<Map> getPersonInfoList(List<String> departIds, String name, String cardID, String company, String securityId,
                                                      String ifCard, String ifYoung){

        List<Map> list=mapper.getPersonInfo(departIds,name,cardID,company,securityId,ifCard,ifYoung,null);
        for(Map<String,String> map:list){
            DataUtil.nullToEmpty(map);
            map.put("stationName",departCtr.getStationDetail(map.get("departId")).get("name"));
            map.put("lineName",departCtr.getDepartByType(map.get("departId"),"1").get("name"));

        }
        return list;
    }

    public void deletePersonCard(String idCard){
        mapper.deletePersonCard(idCard);
    }

    public AjyInfo getPersonCard(String idCard){
        return mapper.getPersonByIdCard(idCard);
    }

    public List<Map> getPersonInfo(List<String> departIds, String name, String cardID,String ifWork){
        return mapper.getPersonInfo( departIds, name,cardID,null,null,null,null,ifWork);
    }

    public List<Map<String,String>> getMonthPerson(List<String> departIds, String name, String cardID,String ifWork){
        return mapper.getMonthPerson(departIds,name,cardID,ifWork);
    }

    public List<Map<String,String>> getNewPerson(List<String> departIds, String name, String cardID,String ifWork){
        return mapper.getNewPerson(departIds,name,cardID,ifWork);
    }

    public TableResultResponse getAjyList(List<String> departIds, String name, String cardID, String ifWork, int page, int limit){
        Page<Map<String,String>> result = PageHelper.startPage(page, limit);
        List<Map> list=mapper.getPersonInfo( departIds, name,cardID,null,null,null,null,ifWork);

        for(Map<String,String> map:list){
            String idCard=map.get("idcard").toString();
            String idCardHide=idCard.replace(idCard.substring(6,14), "********");
            map.replace("idcard",idCard,idCardHide);
        }
        TableResultResponse t=new TableResultResponse(result.getTotal(),list);
        return t;
    }


    public void addAjyPerson(AjyInfo ajyInfo){
       mapper.addAjyPerson(ajyInfo);
    }

    public int getId(){
        return mapper.getId();
    }

    public void blockAjy(int id,Integer blackFlag){
        mapper.blockAjy(id,blackFlag,new Date(),BaseContextHandler.getUserID(),BaseContextHandler.getUsername());
    }

    public List<Map<String,String>> getPersonInfoByDepartId(List<String> departIds, String name, String company, String securityId){
        return mapper.getPersonInfoByDepartId(departIds,name,company,securityId);
    }
}
