package com.ts.spm.bizs.rest.admin;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.admin.BaseUnicodeBiz;
import com.ts.spm.bizs.biz.admin.DepartBiz;
import com.ts.spm.bizs.entity.admin.BaseUnicode;
import com.ts.spm.bizs.entity.admin.Depart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ace
 */
@RestController
@RequestMapping("unicode")
@CheckClientToken
@CheckUserToken
public class UnicodeController extends BaseController<BaseUnicodeBiz, BaseUnicode, String> {
    @Autowired
    private DepartBiz departBiz;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ObjectRestResponse getUnicode(@RequestParam Map<String,String> map){
        String target=map.get("target");
        Assert.hasText(target,"请指定编码对象");
        String station=map.get("station");
        String prefix;
        if("dev".equals(target)){    //设备编码
            Assert.hasText(map.get("category"),"请指定设备大类别");
            Assert.hasText(map.get("type"),"请指定设备小类别");

            String stationId=map.get("stationId");
            if(station!=null && !"".equals(station)){
                prefix=station+map.get("category")+map.get("type");
            }else if(stationId!=null && !"".equals(stationId)){
                Depart depart=departBiz.selectById(stationId);
                prefix=depart.getCode()+map.get("category")+map.get("type");
            }else{
                return ObjectRestResponse.error("请指定车站");
            }
        }else if("point".equals(target)){ //安检点编码
            Assert.hasText(map.get("entry"),"请指定车站出入口");
            String stationId=map.get("stationId");
            if(station!=null && !"".equals(station)){
                prefix=station+"21"+map.get("entry");
            }else if(stationId!=null && !"".equals(stationId)){
                Depart depart=departBiz.selectById(stationId);
                prefix=depart.getCode()+"21"+"0"+map.get("entry");
            }else{
                return ObjectRestResponse.error("请指定车站");
            }
        }else if("area".equals(target)){  //站区编码
            Assert.hasText(map.get("line"),"请指定线路");
            prefix=map.get("line")+"20";
        }else {
            throw new RuntimeException("编码失败");
        }

        map=new HashMap<>();
        map.put("unicode", prefix+getSeq(prefix));
        return ObjectRestResponse.ok(map);
    }

    private String getSeq(String prefix) {
        int sn=1;
        BaseUnicode o=new BaseUnicode();
        o.setPrefix(prefix);
        BaseUnicode entity=this.baseBiz.selectOne(o);
        if(entity==null){
            o.setId(UUIDUtils.generateUuid());
            o.setSn(sn);
            baseBiz.insertSelective(o);
        }else{
            sn=entity.getSn()+1;
            entity.setSn(sn);
            baseBiz.updateById(entity);
        }

        String seq=sn+"";
        if(seq.length()==1)
            seq = "000" + seq;
        else if(seq.length()==2)
            seq = "00" + seq;
        else if(seq.length()==3)
            seq = "0" + seq;
        return seq;
    }
}
