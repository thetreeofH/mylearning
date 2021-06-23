package com.ts.spm.bizs.rest.admin;

import com.github.ag.core.context.BaseContextHandler;
import com.ts.spm.bizs.biz.admin.BasePointBiz;
import com.ts.spm.bizs.biz.admin.DepartBiz;
import com.ts.spm.bizs.entity.admin.BasePoint;
import com.ts.spm.bizs.entity.admin.Depart;
import com.ts.spm.bizs.vo.admin.StatVo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ace
 */
@RestController
@RequestMapping("index")
@CheckClientToken
@CheckUserToken
public class IndexController {
    @Autowired
    private BasePointBiz basePointBiz;
    @Autowired
    private DepartBiz departBiz;

    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public ObjectRestResponse getCheckPoint() {
        StatVo data=new StatVo();
        List<Depart> departs = departBiz.getUserMapDepart(BaseContextHandler.getUserID());
        List<String> departIds=departs.stream().map(Depart::getId).collect(Collectors.toList());
        data.setCheckpoint(0);
        data.setLine(departs.stream().filter(o->o.getType().equals("1")).count());
        data.setStation(departs.stream().filter(o->o.getType().equals("3")).count());
        if(departIds.size()>0){
            Example exa=new Example(BasePoint.class);
            Example.Criteria cr=exa.createCriteria();
            cr.andNotEqualTo("deleteFlag", 1);
            cr.andIn("departId", departIds);
            data.setCheckpoint(basePointBiz.selectCountByExample(exa));
        }
        return ObjectRestResponse.ok(data);
    }
}
