
/*
 *
 *  *  Copyright (C) 2018  Wanghaobin<463540703@qq.com>
 *
 *  *  AG-Enterprise 企业版源码
 *  *  郑重声明:
 *  *  如果你从其他途径获取到，请告知老A传播人，奖励1000。
 *  *  老A将追究授予人和传播人的法律责任!
 *
 *  *  This program is free software; you can redistribute it and/or modify
 *  *  it under the terms of the GNU General Public License as published by
 *  *  the Free Software Foundation; either version 2 of the License, or
 *  *  (at your option) any later version.
 *
 *  *  This program is distributed in the hope that it will be useful,
 *  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *  GNU General Public License for more details.
 *
 *  *  You should have received a copy of the GNU General Public License along
 *  *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 */

package com.ts.spm.bizs.rest.dict;

import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.Query;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.DictTypeBiz;
import com.ts.spm.bizs.biz.DictValueBiz;
import com.ts.spm.bizs.biz.LogBiz;
import com.ts.spm.bizs.entity.DictType;
import com.ts.spm.bizs.entity.DictValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("dictValue")
@CheckClientToken
@CheckUserToken
@Api(tags = "字典值服务",description = "字典值服务")
public class DictValueController extends BaseController<DictValueBiz, DictValue,String> {
    @Autowired
    private DictTypeBiz dictTypeBiz;
    @Autowired
    LogBiz logBiz;

    @IgnoreClientToken
    @IgnoreUserToken
    @RequestMapping(value = "/type/{code}",method = RequestMethod.GET)
    public TableResultResponse<DictValue> getDictValueByDictTypeCode(@PathVariable("code") String code){
        Example example = new Example(DictValue.class);
        example.createCriteria().andLike("code",code+"%");
        List<DictValue> dictValues = this.baseBiz.selectByExample(example).stream().sorted(new Comparator<DictValue>() {
            @Override
            public int compare(DictValue o1, DictValue o2) {
                return o1.getOrderNum() - o2.getOrderNum();
            }
        }).collect(Collectors.toList());
        return new TableResultResponse<DictValue>(dictValues.size(),dictValues);
    }

    @IgnoreClientToken
    @IgnoreUserToken
    @RequestMapping(value = "/feign/{code}",method = RequestMethod.GET)
    public Map<String,String> getDictValueByCode(@PathVariable("code") String code){
        Example example = new Example(DictValue.class);
        example.createCriteria().andLike("code",code+"%");
        List<DictValue> dictValues = this.baseBiz.selectByExample(example);
        Map<String, String> result = dictValues.stream().collect(
                Collectors.toMap(DictValue::getValue, DictValue::getLabelDefault));
        return result;
    }


    @RequestMapping(value = "/type/bycode",method = RequestMethod.GET)
    @IgnoreClientToken
    @IgnoreUserToken
    public TableResultResponse<DictValue> getByDictTypeCode(String code){
        Example example = new Example(DictValue.class);
        example.createCriteria().andLike("code",code+"%");
        List<DictValue> dictValues = this.baseBiz.selectByExample(example).stream().sorted(new Comparator<DictValue>() {
            @Override
            public int compare(DictValue o1, DictValue o2) {
                return o1.getOrderNum() - o2.getOrderNum();
            }
        }).collect(Collectors.toList());
        return new TableResultResponse<DictValue>(dictValues.size(),dictValues);
    }

    @GetMapping(value = "/type/dictValueByCode")
    @IgnoreClientToken
    @IgnoreUserToken
    public List<Map<String,Object>> dictValueByCode(String code){
        List<Map<String,Object>> dictMapList=new ArrayList<>();
        Example example = new Example(DictValue.class);
        example.createCriteria().andLike("code",code+"%");
        List<DictValue> dictValues = this.baseBiz.selectByExample(example);
        for(DictValue dictValue:dictValues){
            Map<String,Object> dicMap=new HashMap<>();
            dicMap.put("id",dictValue.getId());
            dicMap.put("value",dictValue.getValue());
            dicMap.put("code",dictValue.getCode());
            dicMap.put("labelZhCh",dictValue.getLabelZhCh());
            dicMap.put("labelDefault",dictValue.getLabelDefault());
            dictMapList.add(dicMap);
        }
        return dictMapList;
    }

  @ApiOperation("分页获取数据")
  @RequestMapping(value = "/page",method = RequestMethod.GET)
  @ResponseBody
  @Override
  public TableResultResponse<DictValue> list(@RequestParam Map<String, Object> params){
    //查询列表数据
    Query query = new Query(params);
    Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
    Example example = new Example(DictValue.class);
    if(params.get("code")!=null) {
        example.createCriteria().andLike("code", "%" + params.get("code") + "%");
    }
    example.createCriteria().andEqualTo("typeId",params.get("typeId"));
    example.orderBy("orderNum");
      List<DictValue> dictValueList=baseBiz.selectByExample(example);
      return new TableResultResponse<DictValue>(result.getTotal(),dictValueList);
  }

  /////////////////////////////////////////////////////////////////////////////
//  @Autowired
//  private DictTypeBiz dictTypeBiz;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ObjectRestResponse addObj(@RequestBody DictValue o){
        o.setId(UUIDUtils.generateUuid());
        o.setCrtTime(new Date());
        o.setCrtUserId(BaseContextHandler.getUserID());
        o.setCrtUserName(BaseContextHandler.getUsername());
        baseBiz.insertSelective(o);
        logBiz.saveLog("字典管理","添加字典值", "api/dict/dictType/update");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ObjectRestResponse getObj(@PathVariable String id) {
        return ObjectRestResponse.ok(this.baseBiz.selectById(id));
    }

    @RequestMapping(value = "/type/get/{code}", method = RequestMethod.GET)
    public ObjectRestResponse getObj2(@PathVariable String code) {
        DictType o=new DictType();
        o.setCode(code);
        DictType dictType=dictTypeBiz.selectOne(o);

        Example example = new Example(DictValue.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotEmpty(code))
            criteria.andEqualTo("type",dictType.getId());
        example.setOrderByClause("order_num");

        List<DictValue> list = baseBiz.selectByExample(example);
        return ObjectRestResponse.ok(list);
    }

    @RequestMapping(value = "/type/get/{pcode}/{code}", method = RequestMethod.GET)
    public ObjectRestResponse getObj3(@PathVariable String pcode, @PathVariable String code) {
        DictType o=new DictType();
        o.setCode(pcode);
        DictType dictType=dictTypeBiz.selectOne(o);

        o=new DictType();
        o.setParentId(dictType.getId());
        o.setCode(code);
        dictType=dictTypeBiz.selectOne(o);

        Example example = new Example(DictValue.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotEmpty(code))
            criteria.andEqualTo("type",dictType.getId());
        example.setOrderByClause("order_num");

        List<DictValue> list = baseBiz.selectByExample(example);
        return ObjectRestResponse.ok(list);
    }


    @RequestMapping(value = "/get/type/{type}", method = RequestMethod.GET)
    public ObjectRestResponse getByType(@PathVariable String type) {
//        Example example = new Example(DictValue.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("type",type);
//        List<DictValue> list = baseBiz.selectByExample(example);

        DictValue dv=new DictValue();
        dv.setType(type);
        List l=baseBiz.selectList(dv);
        return ObjectRestResponse.ok(l);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ObjectRestResponse updateObj(@PathVariable String id,@RequestBody DictValue o) {
        DictValue entity=this.baseBiz.selectById(id);
        entity.setCode(o.getCode());
        entity.setType(o.getType());
        entity.setValue(o.getValue());
        entity.setLabelDefault(o.getLabelDefault());
        entity.setLabelZhCh(o.getLabelZhCh());
        entity.setLabelEnUs(o.getLabelEnUs());
        entity.setOrderNum(o.getOrderNum());
        entity.setUpdTime(new Date());
        entity.setUpdUserId(BaseContextHandler.getUserID());
        entity.setUpdUserName(BaseContextHandler.getUsername());
        this.baseBiz.updateSelectiveById(entity);
        logBiz.saveLog("字典管理","更新字典值", "api/dict/dictValue/update");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse deleteObj(@PathVariable String id) {
        String[] ids=id.split(",");
        for(String str:ids){
            this.baseBiz.deleteById(str);
        }
        logBiz.saveLog("字典管理","删除字典值", "api/dict/dictValue/delete");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/getpage", method = RequestMethod.GET)
    public TableResultResponse page(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "") String type,
                                    @RequestParam(defaultValue = "")  String code, @RequestParam(defaultValue = "") String labeldefault) {
        Example example = new Example(DictValue.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotEmpty(type))
            criteria.andEqualTo("type",type);
        criteria.andLike("code", "%"+code+"%");
        criteria.andLike("labelDefault", "%"+labeldefault+"%");
        example.setOrderByClause("order_num");

        Page<Object> result = PageHelper.startPage(page, limit);
        List<DictValue> list = baseBiz.selectByExample(example);
        return  new TableResultResponse<>(result.getTotal(),list);
    }

}
