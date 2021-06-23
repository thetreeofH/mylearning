package com.ts.spm.bizs.mapper.admin;

import com.github.wxiaoqi.security.common.data.Tenant;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.admin.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Tenant()
public interface EmployeeMapper extends CommonMapper<Employee> {

//    List<EmployVO> getEmployeeList(@Param("name") String name, @Param("cardno") String cardno, @Param("sex") Integer sex, @Param("position") String position, @Param("departid") String departid);

    List<Employee> getEmployeeList(@Param("name") String name, @Param("cardno") String cardno, @Param("sex") Integer sex, @Param("position") String position, @Param("departids") List<String> departids);


    public void delEmployee(@Param("id") String id, @Param("delUserId") String delUserId, @Param("delUserName") String delUserName, @Param("delTime") Date delTime);

    List<Employee> getUserEmployId(@Param("id") String id);
}