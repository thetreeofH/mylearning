package com.ts.spm.bizs.service;

import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.ts.spm.bizs.entity.DictValue;
import com.ts.spm.bizs.rest.admin.TbcPlatCfgController;
import com.ts.spm.bizs.rest.dict.DictTypeController;
import com.ts.spm.bizs.rest.dict.DictValueController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author luoyu
 * @Date 2020/9/4 16:27
 * @Version 1.0
 */
@Service
public class DictService {
    @Autowired
    private DictTypeController dictTypeCtr;
    @Autowired
    private DictValueController dictValueCtr;
    @Autowired
    private TbcPlatCfgController tbcPlatCfgCtr;

    public List<Map<String,String>> getDicTypeList(String code){
        List<Map<String,String>> list=new ArrayList<>();

//        ObjectRestResponse info = dictTypeCtr.getcode(code);
//        if(info.getData() != null) {
//            List<Map<String,Object>> dicTypeList = (List<Map<String,Object>>)info.getData();
//            List<Map<String,String>> childrenlist=(List)dicTypeList.get(0).get("children");
//            for(Map<String,String> map:childrenlist){
//                Map<String,String> resultMap=new HashMap<>();
//                resultMap.put("code",map.get("code"));
//                resultMap.put("name",map.get("name"));
//                list.add(resultMap);
//            }
//        }
//        return list;

        List<Map<String,Object>> l=dictTypeCtr.getDictTypeByCode(code);
        for(Map<String,Object> map:l){
            Map<String,String> resultMap=new HashMap<>();
            resultMap.put("code",map.get("code").toString());
            resultMap.put("name",map.get("name").toString());
            list.add(resultMap);
        }
        return list;
    }

    public List<Map<String,String>> getdicValueList(String pcode,String code){
        List<Map<String,String>> list=new ArrayList<>();
        ObjectRestResponse info = dictValueCtr.getObj3(pcode,code);
        if(info.getData() != null) {
            List<DictValue> dicTypeList = (List<DictValue>)info.getData();
            for(DictValue map:dicTypeList){
                Map<String,String> resultMap=new HashMap<>();
                resultMap.put("code",map.getCode());
                resultMap.put("name",map.getLabelDefault());
                list.add(resultMap);
            }
        }
        return list;
    }

    public List<Map<String,String>> getDicValueList(String code){
        List<Map<String,String>> list=new ArrayList<>();
        ObjectRestResponse info = dictValueCtr.getObj2(code);
        if(info.getData() != null) {
            List<DictValue> dicTypeList = (List<DictValue>)info.getData();
            for(DictValue map:dicTypeList){
                Map<String,String> resultMap=new HashMap<>();
                resultMap.put("code",map.getCode());
                resultMap.put("name",map.getLabelDefault());
                resultMap.put("value",map.getValue());
                list.add(resultMap);
            }
        }
        return list;
    }

    public String dangerPath(){
        ObjectRestResponse info =tbcPlatCfgCtr.platCfgQuery();
        if(info.getData() != null) {
            Map<String,Object> platCfg = (Map<String,Object>)info.getData();
            String dangerPicPath=platCfg.get("sharePicHome").toString();

            System.out.println(dangerPicPath);
            return dangerPicPath;
        }
        return null;
    }
}
