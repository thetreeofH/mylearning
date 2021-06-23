package com.ts.spm.bizs.biz.admin;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.admin.Employee;
import com.ts.spm.bizs.entity.admin.Tenant;
import com.ts.spm.bizs.mapper.admin.EmployeeMapper;
import com.ts.spm.bizs.vo.admin.EmployVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author luoyu
 * @Date 2020/5/12 14:30
 * @Version 1.0
 */
@Service
public class EmployeeBiz extends BusinessBiz<EmployeeMapper, Employee> {

    @Autowired
    private DepartBiz departBiz;

    @Autowired
    private TenantBiz tenantBiz;


//    public List<EmployVO> getEmployeeList(String name, String  cardno, Integer sex, String position, String departid){
//        return mapper.getEmployeeList(name, cardno, sex, position, departid);
//    }

    public List<EmployVO> getEmployeeList(String name, String  cardno, Integer sex, String position, List<String> departids){
        List<Employee> employees=mapper.getEmployeeList(name, cardno, sex, position, departids);

        List<EmployVO> employVOList=new ArrayList<EmployVO>();

        for(Employee employ:employees){
            EmployVO employVO=new EmployVO();

            employVO.setDepart(departBiz.getDepartNameById(employ.getDepartId()));
            employVO.setId(employ.getId());
            employVO.setName(employ.getName());
            employVO.setCardno(employ.getCardno());
            employVO.setSex(employ.getSex());

            if(employ.getTenantId()!=null&&employ.getTenantId()!=""){
                Tenant tenant=tenantBiz.selectById(employ.getTenantId());
                employVO.setLevelName(tenant.getName());
            }
            employVO.setPosition(employ.getPosition());

            employVO.setIfuse(employ.getIfuse());


            employVOList.add(employVO);

        }

        return employVOList;
    }

    public void delEmployee(String id){
        mapper.delEmployee(id,BaseContextHandler.getUserID(),BaseContextHandler.getName(), new Date());
    }

    public int getUserByEmployId(String employId){
        List<Employee> employeeList=mapper.getUserEmployId(employId);
        return employeeList.size();
    }
    public Employee getEmployById(String employId){
        Employee employee = new Employee();
        employee.setId(employId);
        employee=mapper.selectOne(employee);
        return employee;
    }
}
