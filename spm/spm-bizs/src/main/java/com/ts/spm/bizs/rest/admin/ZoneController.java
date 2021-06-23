package com.ts.spm.bizs.rest.admin;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.admin.BaseZoneBiz;
import com.ts.spm.bizs.entity.admin.BaseZone;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author ace
 */
@RestController
@RequestMapping("zone")
@CheckClientToken
@CheckUserToken
public class ZoneController extends BaseController<BaseZoneBiz, BaseZone, String> {

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ObjectRestResponse addZone(@RequestBody BaseZone o){
        o.setId(UUIDUtils.generateUuid());
        o.setCrtTime(new Date());
        o.setCrtUserId(BaseContextHandler.getUserID());
        o.setCrtUserName(BaseContextHandler.getUsername());
        baseBiz.insertSelective(o);
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ObjectRestResponse getZone(@PathVariable String id) {
        return new ObjectRestResponse<>().data(this.baseBiz.selectById(id));
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ObjectRestResponse editZone(@PathVariable String id,@RequestBody BaseZone o) {
        BaseZone entity=this.baseBiz.selectById(id);
        entity.setCode(o.getCode());
        entity.setName(o.getName());
        entity.setMemo(o.getMemo());

        entity.setUpdTime(new Date());
        entity.setUpdUserId(BaseContextHandler.getUserID());
        entity.setUpdUserName(BaseContextHandler.getUsername());
        this.baseBiz.updateSelectiveById(entity);
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse delDictValue(@PathVariable String id) {
        this.baseBiz.deleteById(id);
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/getlist", method = RequestMethod.GET)
    public ObjectRestResponse list(@RequestParam(defaultValue = "") String name){
        Example example = new Example(BaseZone.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(name))
            criteria.andLike("name", "%"+name+"%");

        List<BaseZone> list = baseBiz.selectByExample(example);
        return ObjectRestResponse.ok(list);
    }

}
