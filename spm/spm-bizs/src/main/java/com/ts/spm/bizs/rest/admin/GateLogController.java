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

package com.ts.spm.bizs.rest.admin;

import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ts.spm.bizs.biz.admin.DepartBiz;
import com.ts.spm.bizs.biz.admin.EmployeeBiz;
import com.ts.spm.bizs.biz.admin.GateLogBiz;
import com.ts.spm.bizs.biz.admin.UserBiz;
import com.ts.spm.bizs.entity.admin.Depart;
import com.ts.spm.bizs.entity.admin.Employee;
import com.ts.spm.bizs.entity.admin.GateLog;
import com.ts.spm.bizs.entity.admin.User;
import com.ts.spm.bizs.vo.admin.GateLogTrend;
import com.ts.spm.bizs.vo.admin.GateLogVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @version 2017-07-01 20:32
 */
@RestController
@RequestMapping("gateLog")
@CheckClientToken
@CheckUserToken
@Api(tags = "日志管理")
public class GateLogController extends BaseController<GateLogBiz, GateLog,Integer> {
  @Autowired
  private UserBiz userBiz;
  @Autowired
  private DepartBiz departBiz;
  @Autowired
  private EmployeeBiz employeeBiz;

  static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 日志列表
     * @param limit
     * @param page
     * @return
     */
    @GetMapping(value = "logpage")
    public TableResultResponse logpage(@RequestParam(name = "limit", defaultValue = "10") int limit, @RequestParam(name = "page", defaultValue = "1") int page,String begintime,String endtime,String crtName,String opt,String addr){
      List<String> depatids = null;
      String userid = BaseContextHandler.getUserID();
      User user = userBiz.selectById(userid);

//        if (!"1".equals(user.getIsSuperAdmin())) {
//          depatids=new ArrayList<>();
//          Map usermap = JSON.parseObject(user.getDepartId(), Map.class);
//          String departId1 = usermap.get("id").toString();
//          Depart dp = departBiz.selectById(departId1);
//          depatids.add(dp.getId());
//          Depart depart = new Depart();
//          depart.setParentId(departId1);
//          List<Depart> departs = departBiz.selectList(depart);
//          if (departs != null && departs.size() > 0) {
//            depatids = departids(departs, depatids);
//          }
//        }

        Page<Object> result = PageHelper.startPage(page, limit);
        List<GateLogVo> getpage=baseBiz.allpage(begintime,endtime,crtName,opt,addr,depatids);
        return  new TableResultResponse(result.getTotal(),getpage);
    }

    @GetMapping(value = "trend")
    public ObjectRestResponse trend(String date) throws ParseException {
      String[] arr=date.split(",");
      List<GateLogTrend> list=new ArrayList<>();
      for (String day:arr){
        Example exa = new Example(GateLog.class);
        Example.Criteria cri=exa.createCriteria();
        cri.andBetween("crtTime", sdf.parse(day+" 00:00:00"), sdf.parse(day+" 23:59:59"));
        int count=baseBiz.selectCountByExample(exa);
        GateLogTrend trend=new GateLogTrend(count, day);
        list.add(trend);
      }
      return ObjectRestResponse.ok(list);
    }

    @PostMapping(value = "savelog")
    public void savelog(String menu,String opt,String url){
      String ip=request.getRemoteAddr();
      String userid=BaseContextHandler.getUserID();
      String tenantid =BaseContextHandler.getTenantID();
      GateLog gateLog=new GateLog();
      gateLog.setMenu(menu);
      gateLog.setOpt(opt);
      gateLog.setUri(url);
      gateLog.setCrtTime(new Date());
      gateLog.setCrtUser(userid);
      gateLog.setTenantId(tenantid);
      User cretuser=userBiz.selectById(userid);
      //gateLog.setCrtName(cretuser.getName());
      Employee ele = employeeBiz.selectById(cretuser.getEmployId());
      if(ele == null)
        gateLog.setCrtName(cretuser.getName());
      else
        gateLog.setCrtName(ele.getName());
      gateLog.setCrtHost(ip);
      gateLog.setDepartId(BaseContextHandler.getDepartID());
      baseBiz.insertSelective(gateLog);
    }

  @PostMapping(value = "sign/savelog")
  @IgnoreUserToken
  public void savelog(String menu,String opt,String url,String userid,String tenantid,String departid){
    String ip=request.getRemoteAddr();
    GateLog gateLog=new GateLog();
    gateLog.setMenu(menu);
    gateLog.setOpt(opt);
    gateLog.setUri(url);
    gateLog.setCrtTime(new Date());
    gateLog.setCrtUser(userid);
    gateLog.setTenantId(tenantid);
    User cretuser=userBiz.selectById(userid);
    //gateLog.setCrtName(cretuser.getName());
    Employee ele = employeeBiz.selectById(cretuser.getEmployId());
    if(ele == null)
      gateLog.setCrtName(cretuser.getName());
    else
      gateLog.setCrtName(ele.getName());
    gateLog.setCrtHost(ip);
    gateLog.setDepartId(departid);
    baseBiz.insertSelective(gateLog);
  }

  public List<String> departids(List<Depart> list, List<String> depatids) {
    List<Depart> sondepat = new ArrayList<>();
    for (Depart dp : list) {
      Map<String, Object> map = new HashMap<>();
      Depart depart2 = new Depart();
      depart2.setParentId(dp.getId());
      List<Depart> departList = departBiz.selectList(depart2);
      if (departList != null && departList.size() > 0) {
        sondepat.add(dp);

        depatids.add(dp.getId());
      } else {

        depatids.add(dp.getId());
      }
    }
    for (Depart dps : sondepat) {
      Depart depart2 = new Depart();
      depart2.setParentId(dps.getId());
      List<Depart> departList = departBiz.selectList(depart2);
      departids(departList, depatids);
    }
    return depatids;
  }
}
