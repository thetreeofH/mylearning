package com.ts.spm.bizs.rest.admin;

import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.google.common.collect.Maps;
import com.ts.spm.bizs.biz.admin.CompanyBiz;
import com.ts.spm.bizs.entity.admin.SecurityCompany;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ace
 */
@RestController
@RequestMapping("company")
@CheckClientToken
@CheckUserToken
@Api(tags = "安检公司管理")
public class CompanyController extends BaseController<CompanyBiz, SecurityCompany, String> {

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ObjectRestResponse addCompany(@RequestBody SecurityCompany o) {
        o.setId(UUIDUtils.generateUuid());
        o.setCrtTime(new Date());
        o.setCrtUserId(BaseContextHandler.getUserID());
        o.setCrtUser(BaseContextHandler.getUsername());
        o.setCrtDeptId(BaseContextHandler.getDepartID());

        baseBiz.insertSelective(o);
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ObjectRestResponse getCompany(@PathVariable String id) {
        return ObjectRestResponse.ok(this.baseBiz.selectById(id));
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ObjectRestResponse updateCompany(@PathVariable String id,@RequestBody SecurityCompany o) {
        SecurityCompany entity=this.baseBiz.selectById(id);
        entity.setCode(o.getCode());
        entity.setName(o.getName());
        entity.setUpdTime(new Date());
        entity.setUpdUserId(BaseContextHandler.getUserID());
        entity.setUpdUser(BaseContextHandler.getUsername());
        entity.setUpdDeptId(BaseContextHandler.getDepartID());
        baseBiz.updateSelectiveById(entity);
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse delDictValue(@PathVariable String id) {
        String[] ids=id.split(",");
        for(String str:ids){
            SecurityCompany o=baseBiz.selectById(str);
            o.setDelFlag(1);
            o.setDelTime(new Date());
            o.setDelUserId(BaseContextHandler.getUserID());
            o.setDelUser(BaseContextHandler.getUsername());
            o.setDelDeptId(BaseContextHandler.getDepartID());
            baseBiz.updateSelectiveById(o);
        }
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/getpage", method = RequestMethod.GET)
    public TableResultResponse page(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "")  String code, @RequestParam(defaultValue = "") String name) {
        Example example = new Example(SecurityCompany.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("code", "%"+code+"%");
        criteria.andLike("name", "%"+name+"%");
        criteria.andNotEqualTo("delFlag", 1);
        example.setOrderByClause("crt_time desc");

        Page<SecurityCompany> result = PageHelper.startPage(page, limit);
        List<SecurityCompany> list = baseBiz.selectByExample(example);
        return  new TableResultResponse<>(result.getTotal(),list);
    }

    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    public ObjectRestResponse getall() {
        Example example = new Example(SecurityCompany.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andNotEqualTo("delFlag", 1);
        return ObjectRestResponse.ok(baseBiz.selectByExample(example));
    }

    @RequestMapping(value = "/getCompanyDetail", method = RequestMethod.GET)
    public Map<String, String> getCompanyDetail(@RequestParam String id) {
        Map<String,String> map= Maps.newHashMap();
        SecurityCompany company=baseBiz.selectById(id);
        map.put("id",company.getId());
        map.put("name",company.getName());
        map.put("code",company.getCode());
        return  map;

    }

}
