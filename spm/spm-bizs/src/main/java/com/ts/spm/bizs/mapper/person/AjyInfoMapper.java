package com.ts.spm.bizs.mapper.person;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.person.AjyInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AjyInfoMapper extends CommonMapper<AjyInfo> {
    List<Map> getPersonInfo(@Param("departIds") List<String> departIds, @Param("name") String name, @Param("cardID") String cardID,
                                           @Param("company") String company, @Param("securityId") String securityId, @Param("ifCard") String ifCard,
                                           @Param("ifYoung") String ifYoung, @Param("ifWork") String ifWork);

    public void deletePersonCard(@Param("idCard") String idCard);

    AjyInfo getPersonByIdCard(@Param("idCard") String idCard);

    List<Map<String,String>> getMonthPerson(@Param("departIds") List<String> departIds, @Param("name") String name, @Param("cardID") String cardID, @Param("ifWork") String ifWork);

    List<Map<String,String>> getNewPerson(@Param("departIds") List<String> departIds, @Param("name") String name, @Param("cardID") String cardID, @Param("ifWork") String ifWork);

    public void addAjyPerson(@Param("ajyInfo") AjyInfo ajyInfo);

    public int getId();

    public void blockAjy(@Param("id") int id,@Param("blackFlag") Integer blackFlag,@Param("updTime") Date updTime,@Param("updUserId") String updUserId,@Param("updUserName") String updUserName);

    List<Map<String,String>> getPersonInfoByDepartId(@Param("departIds") List<String> departIds, @Param("name") String name, @Param("company") String company, @Param("securityId") String securityId);
}