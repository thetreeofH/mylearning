package com.ts.spm.bizs.biz.person;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.person.PersonBlack;
import com.ts.spm.bizs.mapper.person.PersonBlackMapper;
import com.ts.spm.bizs.util.DataUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author luoyu
 * @Date 2020/7/2 10:11
 * @Version 1.0
 */
@Service
public class PersonBlackBiz extends BusinessBiz<PersonBlackMapper, PersonBlack> {
    public List<PersonBlack> getPersonBlack(String name,String cardID){
        Example example = new Example(PersonBlack.class);
        Example.Criteria criteria = example.createCriteria();
        if( StringUtils.isNotBlank(name)){
            criteria.andLike("name","%" + name + "%");
        }

        if( StringUtils.isNotBlank(cardID)){
            criteria.andLike("cardid","%" + cardID + "%");
        }

        example.setOrderByClause("TIME desc");

        List<PersonBlack> list=selectByExample(example);
        for(PersonBlack personBlack:list){
            DataUtil.checkNull(personBlack);
        }
        return list;
    }

    public void addPersonBlack(PersonBlack personBlack){
        personBlack.setTime(new Date());
        mapper.addPersonBlack(personBlack);
    }

    public void deletePersonBlack(String idCard){
        mapper.deletePersonBlack(idCard);
    }

    public List<Map> getPersonBlackList(String name, String cardID){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Map> personBlackList= new ArrayList<>();
        List<PersonBlack> blackList=getPersonBlack(name,cardID);
        int index=1;
        for(PersonBlack personBlack:blackList){
            Map map=new HashMap();
            map.put("id",index++);
            map.put("cardid",personBlack.getCardid());
            map.put("name",personBlack.getName());
            map.put("time", format.format(personBlack.getTime()));
            map.put("remark",personBlack.getRemark());
            personBlackList.add(map);
        }
        return personBlackList;

    }
}
