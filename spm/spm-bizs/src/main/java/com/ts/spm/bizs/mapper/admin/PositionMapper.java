package com.ts.spm.bizs.mapper.admin;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.admin.Depart;
import com.ts.spm.bizs.entity.admin.Group;
import com.ts.spm.bizs.entity.admin.Position;
import com.ts.spm.bizs.entity.admin.User;
import com.ts.spm.bizs.vo.admin.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-02-04 19:06:43
 */
public interface PositionMapper extends CommonMapper<Position> {
    /**
     * 批量删除岗位中得用户
     * @param positionId
     */
    void deletePositionUsers(String positionId);

    /**
     * 删除用户关联的岗位
     * @param userID
     */
    void deletePositionbyUsers(String userID);

    /**
     * 岗位增加用户
     * @param id
     * @param positionId
     * @param userId
     */
    void insertPositionUser(@Param("id") String id, @Param("positionId") String positionId, @Param("userId") String userId, @Param("tenantId") String tenantId);

    /**
     * 获取岗位关联的用户
     * @param positionId
     * @return
     */
    List<User> selectPositionUsers(String positionId);

    /**
     * 删除岗位关联的角色
     * @param positionId
     */
    void deletePositionGroups(String positionId);

    /**
     * 插入岗位关联的角色
     * @param id
     * @param positionId
     * @param groupId
     */
    void insertPositionGroup(@Param("id") String id, @Param("positionId") String positionId, @Param("groupId") String groupId, @Param("tenantId") String tenantId);

    /**
     * 获取岗位关联的角色
     * @param positionId
     * @return
     */
    List<Group> selectPositionGroups(@Param("positionId") String positionId);

    /**
     * 移除岗位下授权的部门
     * @param positionId
     */
    void deletePositionDeparts(String positionId);

    /**
     * 添加岗位下授权的部门
     * @param id
     * @param positionId
     * @param departId
     */
    void insertPositionDepart(@Param("id") String id, @Param("positionId") String positionId, @Param("departId") String departId, @Param("tenantId") String tenantId);

    /**
     * 获取岗位授权的部门
     * @param positionId
     * @return
     */
    List<Depart> selectPositionDeparts(String positionId);

    /**
     * 获取用户的流程岗位
     * @return
     */
    List<Position> selectUserFlowPosition(String userId);

    /**
     * 查看对应部门下的人
     * @param item
     * @param XZ
     * @param FZR
     * @return
     */
    List<Position> departPositionList(@Param("item") List<String> item, @Param("XZ") String XZ, @Param("FZR") String FZR);

    /**
     * 查看当前用户岗位
     * @param userid
     * @return
     */
    Position positionuser(@Param("userId") String userid);

    List<UserVo> positionUsers(@Param("depatId") String depatId, @Param("jobCategoryCode") String jobCategoryCode, @Param("username") String username);

}
