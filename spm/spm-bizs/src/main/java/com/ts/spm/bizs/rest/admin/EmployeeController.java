package com.ts.spm.bizs.rest.admin;

import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ts.spm.bizs.biz.admin.DepartBiz;
import com.ts.spm.bizs.biz.admin.EmployeeBiz;
import com.ts.spm.bizs.entity.admin.Employee;
import com.ts.spm.bizs.vo.admin.EmployVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author luoyu
 * @Date 2020/5/12 14:33
 * @Version 1.0
 */
@RestController
@RequestMapping("employee")
@CheckUserToken
@CheckClientToken
@Api(tags="员工模块")
public class EmployeeController extends BaseController<EmployeeBiz, Employee,String> {

    @Autowired
    private DepartBiz departBiz;

    @ApiOperation("员工列表查询")
    @RequestMapping(value = "/employpage", method = RequestMethod.GET)
    public TableResultResponse getEmployeePage(String name, String cardno,String sex, String position,String departid,
                                                       @RequestParam(name = "limit", defaultValue = "10") int limit,
                                                       @RequestParam(name = "page", defaultValue = "1") int page) {
        Integer sex1 = null;
        if ( StringUtils.isNotBlank(sex)) {
            sex1=Integer.parseInt(sex);
        }

        List<String> stationIds=new ArrayList<>();
        if(!"".equals(departid)&&departid!=null){
            stationIds=departBiz.selectChildren(departid);
            if(stationIds.size()==0)    return new TableResultResponse(0,stationIds);
        }

        Page<Object> result = PageHelper.startPage(page, limit);
        List<EmployVO> points=baseBiz.getEmployeeList(name,cardno,sex1,position,stationIds);

        return new TableResultResponse(result.getTotal(),points);

    }


    @ApiOperation("员工删除")
    @RequestMapping(value = "/delemploy/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse delEmploy(@PathVariable String id) {
        String[] ids = id.split(",");
        for(String employId:ids){
            baseBiz.delEmployee(employId);
        }

        return ObjectRestResponse.ok();
    }

    @ApiOperation("修改员工状态")
    @RequestMapping(value = "/{id}/{ifuse}", method = RequestMethod.POST)
    public ObjectRestResponse updateEmployState(@PathVariable String id,@PathVariable String ifuse){
        Employee entity=this.baseBiz.selectById(id);
        entity.setIfuse(ifuse);
        entity.setUpdTime(new Date());
        entity.setUpdUserId(BaseContextHandler.getUserID());
        entity.setUpdUserName(BaseContextHandler.getUsername());
        baseBiz.updateSelectiveById(entity);
        return ObjectRestResponse.ok();
    }

    @ApiOperation("按照条件获取员工列表")
    @RequestMapping(value = "/getEmployList", method = RequestMethod.GET)
    public ObjectRestResponse getEmployList(String departId){
        Example example = new Example(Employee.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(departId)) {
            criteria.andEqualTo("departId",departId);
        }

        criteria.andEqualTo("delFlag", 0);
        criteria.andEqualTo("ifuse", 0);

        example.setOrderByClause("CRT_TIME desc");

        List<Employee> list = baseBiz.selectByExample(example);

        List<EmployVO> employVOList=new ArrayList<EmployVO>();
        for(Employee employee:list){
            EmployVO employVO=new EmployVO();
            BeanUtils.copyProperties(employee, employVO);
            employVO.setDepart(departBiz.getDepartNameById(employee.getDepartId()));
            employVOList.add(employVO);
        }

        return ObjectRestResponse.ok(employVOList);

    }

    @ApiOperation("查询该员工下是否有用户")
    @RequestMapping(value = "/getUserByEmployId", method = RequestMethod.GET)
    public ObjectRestResponse getUserByEmployId(String employId){
        int count=baseBiz.getUserByEmployId(employId);

        Boolean ifUser=false;
        if(count>0){
            ifUser=true;
        }
        return ObjectRestResponse.ok(ifUser);
    }

}
