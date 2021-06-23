/*
 *  Copyright (C) 2018  Wanghaobin<463540703@qq.com>

 *  AG-Enterprise 企业版源码
 *  郑重声明:
 *  如果你从其他途径获取到，请告知老A传播人，奖励1000。
 *  老A将追究授予人和传播人的法律责任!

 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.ts.spm.bizs.biz.admin;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.admin.Depart;
import com.ts.spm.bizs.entity.admin.GateLog;
import com.ts.spm.bizs.mapper.admin.GateLogMapper;
import com.ts.spm.bizs.vo.admin.GateLogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @version 2017-07-01 14:36
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GateLogBiz extends BusinessBiz<GateLogMapper,GateLog> {
  @Autowired
  private  DepartBiz departBiz;

    @Override
    public void insertSelective(GateLog entity) {
        mapper.insertSelective(entity);
    }

    public List<GateLogVo> allpage(String begintime, String endtime, String crtName, String opt, String addr, List<String> depatids){
      List<GateLogVo> gateLogs=mapper.allpage(begintime,endtime,crtName,opt,addr,depatids);
      for(GateLogVo gateLogVo:gateLogs){
        Depart depart=departBiz.selectById(gateLogVo.getDepartId());
        if(depart!=null){
          gateLogVo.setDepartName(depart.getName());
        }
      }
      return gateLogs;
    }
}
