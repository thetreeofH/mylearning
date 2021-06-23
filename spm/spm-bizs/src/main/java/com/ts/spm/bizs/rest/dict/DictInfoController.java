
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

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.ts.spm.bizs.biz.DictTypeBiz;
import com.ts.spm.bizs.biz.DictValueBiz;
import com.ts.spm.bizs.entity.DictType;
import com.ts.spm.bizs.entity.DictValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("dictInfo")
@CheckClientToken
@CheckUserToken
public class DictInfoController {
    @Autowired
    DictTypeBiz dictTypeBiz;
    @Autowired
    DictValueBiz dictValueBiz;

    @RequestMapping(value = "get/{code}", method = RequestMethod.GET)
    public ObjectRestResponse getByCode(@PathVariable String code) {
        DictType o=new DictType();
        o.setCode(code);
        DictType dt0=dictTypeBiz.selectOne(o);

        o=new DictType();
        o.setParentId(dt0.getId());
        List<DictType> dictTypes = dictTypeBiz.selectList(o);

        for (DictType dt:dictTypes){
            DictValue dv=new DictValue();
            dv.setType(dt.getId());
            dt.setDictValues(dictValueBiz.selectList(dv));
        }

        return ObjectRestResponse.ok(dictTypes);
    }
}