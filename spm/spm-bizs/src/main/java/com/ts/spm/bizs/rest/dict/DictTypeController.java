
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
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.constant.RestCodeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.TreeUtil;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.DictTypeBiz;
import com.ts.spm.bizs.biz.LogBiz;
import com.ts.spm.bizs.entity.DictType;
import com.ts.spm.bizs.vo.dict.DictTree;
import com.ts.spm.bizs.vo.dict.DictTree2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("dictType")
@CheckClientToken
@CheckUserToken
public class DictTypeController extends BaseController<DictTypeBiz, DictType,String> {
    @Autowired
    LogBiz logBiz;
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public List<DictTree> getTree() {
        List<DictType> dictTypes = this.baseBiz.selectListAll();
        List<DictTree> trees = new ArrayList<>();
        dictTypes.forEach(dictType -> {
            trees.add(new DictTree(dictType.getId(), dictType.getParentId(), dictType.getName(),dictType.getCode()));
        });
        return TreeUtil.bulid(trees, "-1", null);
    }

    @RequestMapping(value = "/newTree", method = RequestMethod.GET)
    public ObjectRestResponse getnewTree() {
        List<DictType> dictTypes = this.baseBiz.selectListAll();
        List<DictTree> trees = new ArrayList<>();
        dictTypes.forEach(dictType -> {
            trees.add(new DictTree(dictType.getId(), dictType.getParentId(), dictType.getName(),dictType.getCode()));
        });
        return ObjectRestResponse.ok(TreeUtil.bulid(trees, "-1", null));
    }

    @IgnoreClientToken
    @IgnoreUserToken
    @RequestMapping(value = "/feign/{code}",method = RequestMethod.GET)
    public List<Map<String,Object>> getDictTypeByCode(@PathVariable("code") String code){
        Example example = new Example(DictType.class);
        Example.Criteria crit=example.createCriteria();
        crit.andEqualTo("code",code);
//        crit.andLike("code", code+"%");
        List<DictType> dictTypes = this.baseBiz.selectByExample(example);

        DictType dt=dictTypes.get(0);
        example = new Example(DictType.class);
        crit=example.createCriteria();
        crit.andEqualTo("parentId",dt.getId());
//        crit.andEqualTo("code",Arrays.asList("2","8","150","151","152","153","154","155"));
        dictTypes = this.baseBiz.selectByExample(example);

        List<Map<String,Object>> maps=new ArrayList<>();
        for(DictType dictType:dictTypes){
            Map<String,Object> map=new HashMap<>();
            map.put("id",dictType.getId());
            map.put("code",dictType.getCode());
            map.put("name",dictType.getName());
            maps.add(map);
        }
        return maps;
    }

    @IgnoreClientToken
    @IgnoreUserToken
    @RequestMapping(value = "/getDangers",method = RequestMethod.GET)
    public List<Map<String,String>> getDangers(){
        Example example = new Example(DictType.class);
        Example.Criteria crit=example.createCriteria();
        crit.andIn("code", Arrays.asList("GUN","BOMB","POLICEUSE","POISON","BURN","KNIFE","TOOLS","OTHERS"));
        List<DictType> dictTypes = this.baseBiz.selectByExample(example);
        List<Map<String,String>> maps=new ArrayList<>();
        for(DictType dictType:dictTypes){
            Map<String,String> map=new HashMap<>();
            map.put("id",dictType.getId());
            map.put("code",dictType.getCode());
            map.put("name",dictType.getName());
            maps.add(map);
        }
        return maps;
    }

    ////////////////////////////////////////////////////////
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ObjectRestResponse<DictType> addObj(@RequestBody DictType o){
        DictType dt=new DictType();
        dt.setCode(o.getCode());
        if(baseBiz.selectCount(dt)>0)
            return ObjectRestResponse.error(RestCodeConstants.EX_BUSINESS_BASE_CODE,"编码已存在");
        
        o.setId(UUIDUtils.generateUuid());
        o.setCrtTime(new Date());
        o.setCrtUserId(BaseContextHandler.getUserID());
        o.setCrtUserName(BaseContextHandler.getUsername());
        baseBiz.insertSelective(o);
        logBiz.saveLog("字典管理","添加字典项", "api/dict/dictType/add");
        return new ObjectRestResponse<DictType>().data(null);
    }

    @RequestMapping(value = "/tree2", method = RequestMethod.GET)
    public ObjectRestResponse getTree2(@RequestParam String name, @RequestParam String code) {
        Example example = new Example(DictType.class);
        example.setOrderByClause("order_num");
        List<DictType> all = baseBiz.selectByExample(example);
        List<DictType> matched=all.stream().filter(dt->dt.getName().contains(name)&&dt.getCode().contains(code)).collect(Collectors.toList());

        List<DictType> result=new ArrayList<>();
        if(all.size()==matched.size()){
            result=all;
        }else{
            for(DictType o:matched){
                boolean flag=false;
                for (DictType dt:result){
                    if(dt.getId().equals(o.getId())){
                        flag=true;
                        break;
                    }
                }
                if(!flag){
                    result.add(o);
                    handlePid(o, all, result);
                }
            }
        }

        List<DictTree2> trees = new ArrayList<>();
        result.forEach(dictType -> {
            trees.add(new DictTree2(dictType.getId(), dictType.getParentId(), dictType.getName(),dictType.getCode(), dictType.getOrderNum(), dictType.getCrtTime()));
        });
        List<DictTree2> list=TreeUtil.bulid(trees, "-1", null);
        return ObjectRestResponse.ok(list);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ObjectRestResponse getObj(@PathVariable String id) {
        Example example = new Example(DictType.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",id);
        example.or().andEqualTo("parentId", id);
        example.setOrderByClause("order_num");
        List<DictType> dictTypes = baseBiz.selectByExample(example);

        List<DictTree2> trees = new ArrayList<>();
        dictTypes.forEach(dictType -> {
            trees.add(new DictTree2(dictType.getId(), dictType.getParentId(), dictType.getName(),dictType.getCode(), dictType.getOrderNum(), dictType.getCrtTime()));
        });
        DictType dt=this.baseBiz.selectById(id);
        List<DictTree2> list=TreeUtil.bulid(trees, dt.getParentId(), null);
        return ObjectRestResponse.ok(list);
    }

    @RequestMapping(value = "/getcode/{code}", method = RequestMethod.GET)
    public ObjectRestResponse getcode(@PathVariable String code) {
        DictType dt=new DictType();
        dt.setCode(code);
        List<DictType> dictTypes = baseBiz.selectList(dt);
        DictType dt0=dictTypes.get(0);

//        dt=new DictType();
//        dt.setParentId(dt0.getId());
//        dictTypes = baseBiz.selectList(dt);
//        dictTypes.add(dt0);
        dictTypes=baseBiz.selectChildren(dt0.getId());

        List<DictTree2> trees = new ArrayList<>();
        dictTypes.forEach(dictType -> {
            trees.add(new DictTree2(dictType.getId(), dictType.getParentId(), dictType.getName(),dictType.getCode(), dictType.getOrderNum(), dictType.getCrtTime()));
        });
//        DictType dt=this.baseBiz.selectById(id);
        List<DictTree2> list=TreeUtil.bulid(trees, dt0.getParentId(), null);
        return ObjectRestResponse.ok(list);

//        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ObjectRestResponse<DictType> updateObj(@PathVariable String id,@RequestBody DictType o) {
        DictType dt=new DictType();
        dt.setCode(o.getCode());
        if(baseBiz.selectCount(dt)>0)
            return ObjectRestResponse.error(RestCodeConstants.EX_BUSINESS_BASE_CODE,"编码已存在");

        DictType entity=this.baseBiz.selectById(id);
        entity.setName(o.getName());
        entity.setCode(o.getCode());
        entity.setOrderNum(o.getOrderNum());
        entity.setParentId(o.getParentId());
        entity.setUpdTime(new Date());
        entity.setUpdUserId(BaseContextHandler.getUserID());
        o.setUpdUserName(BaseContextHandler.getUsername());
        this.baseBiz.updateSelectiveById(entity);
        logBiz.saveLog("字典管理","更新字典项", "api/dict/dictType/update");
        return new ObjectRestResponse<DictType>().data(null);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse<DictType> deleteObj(@PathVariable String id) {
        this.baseBiz.deleteById(id);
        logBiz.saveLog("字典管理","删除字典项", "api/dict/dictType/delete");
        return new ObjectRestResponse<DictType>().data(null);
    }

    public void handlePid(DictType arg, List<DictType> all, List<DictType> result){
        for(DictType item:all){
            if(item.getId().equals(arg.getParentId())){
                boolean flag=false;
                for (DictType o:result){
                    if(o.getId().equals(item.getId())){
                        flag=true;
                        break;
                    }
                }
                if(!flag){
                    result.add(item);
                    if(!"-1".equals(item.getParentId()))    handlePid(item,all,result);
                }
                break;
            }
        }
    }

}