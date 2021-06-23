package com.ts.spm.bizs.mapper.admin;

import com.github.wxiaoqi.security.common.data.Tenant;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.admin.Depart;
import com.ts.spm.bizs.entity.admin.User;
import com.ts.spm.bizs.entity.person.AjyInfo;
import com.ts.spm.bizs.vo.admin.DepartVo;
import com.ts.spm.bizs.vo.admin.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Mr.AG
 * @version 2018-02-04 19:06:43
 * @email 463540703@qq.com
 */
@Tenant()
public interface DepartMapper extends CommonMapper<Depart> {

    public AjyInfo getDepartIdByName(@Param("name") String name);

    List<User> selectDepartUsers(@Param("departId") String departId, @Param("userName") String userName);

    void deleteDepartUser(@Param("departId") String departId, @Param("userId") String userId);

    void insertDepartUser(@Param("id") String id, @Param("departId") String departId, @Param("userId") String userId, @Param("tenantId") String tenantId);

    List<UserVo> userByDepart(@Param("item") List<String> item, @Param("jobCode") String jobCode, @Param("useritem") List<String> useritem);

    List<Depart> organListByPolice(@Param("pd") DepartVo departVo);

    List<Depart> parentAllDeparts(@Param("pd") Depart depart);

    List<Depart> childAllDeparts(@Param("pd") Depart depart);

    Depart selectByParent(Depart depart);

    List<Depart> selectAllSchool();
    List<String> selectChildren(String deptId);
    List<Depart> selectChildrenDepart(@Param("deptId") String deptId, @Param("userId") String userId);

    //new
    List<Depart> getUserMapDepart(@Param("id") String id);

    List<Depart> getDepartList(@Param("name") String name, @Param("type") String type, @Param("status") String status, @Param("userId") String userId);

    List<Depart> getDepartTree(@Param("userId") String userId, @Param("typeList") List<String> typeList);

    public String getDepartNameById(@Param("departId") String departId);

    public String getDepartName(@Param("departId") String departId, @Param("type") String type);

    List<Depart> getParent(String id);
}
