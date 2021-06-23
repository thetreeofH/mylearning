package com.ts.spm.bizs.biz.admin;

import com.ace.cache.annotation.CacheClear;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.entity.admin.Depart;
import com.ts.spm.bizs.entity.admin.Group;
import com.ts.spm.bizs.entity.admin.Position;
import com.ts.spm.bizs.entity.admin.User;
import com.ts.spm.bizs.mapper.admin.PositionMapper;
import com.ts.spm.bizs.rest.dict.DictValueController;
import com.ts.spm.bizs.vo.admin.DepartTree;
import com.ts.spm.bizs.vo.admin.GroupTree;
import com.ts.spm.bizs.vo.admin.UserVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 *
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-02-04 19:06:43
 */
@Service
public class PositionBiz extends BusinessBiz<PositionMapper,Position> {
    @Autowired
    private DictValueController dictValueCtr;
    /**
     * 更改岗位用户
     * @param positionId
     * @param users
     */
    @CacheClear(pre = "permission")
    public void modifyPositionUsers(String positionId, String users) {
        mapper.deletePositionUsers(positionId);
        if(StringUtils.isNotBlank(users)){
            for (String uId : users.split(",")) {
                mapper.insertPositionUser(UUIDUtils.generateUuid(),positionId,uId, BaseContextHandler.getTenantID());
            }
        }
    }


    @CacheClear(pre = "permission")
    public void newmodifyPositionUsers(String positionId, String userID) {
        mapper.deletePositionbyUsers(userID);
        if(StringUtils.isNotBlank(userID)){
            for (String uId : userID.split(",")) {
                mapper.insertPositionUser(UUIDUtils.generateUuid(),positionId,uId, BaseContextHandler.getTenantID());
            }
        }
    }

    /**
     * 获取用户流程关联岗位
     * @param userId
     * @return
     */
    public List<Position> getUserFlowPosition(String userId){
        return mapper.selectUserFlowPosition(userId);
    }

    /**
     * 获取岗位用户
     * @param positionId
     * @return
     */
    public List<User> getPositionUsers(String positionId) {
        return mapper.selectPositionUsers(positionId);
    }

    public void modifyPositionGroups(String positionId, String groups) {
        mapper.deletePositionGroups(positionId);
        if(StringUtils.isNotBlank(groups)) {
            for (String groupId : groups.split(",")) {
                mapper.insertPositionGroup(UUIDUtils.generateUuid(),positionId,groupId, BaseContextHandler.getTenantID());
            }
        }
    }

    public List<GroupTree> getPositionGroups(String positionId) {
        List<Group> groups = mapper.selectPositionGroups(positionId);
        List<GroupTree> trees = new ArrayList<GroupTree>();
        GroupTree node = null;
        for (Group group : groups) {
            node = new GroupTree();
            node.setLabel(group.getName());
            BeanUtils.copyProperties(group, node);
            trees.add(node);
        }
        return trees;
    }

    public void modifyPositionDeparts(String positionId, String departs) {
        mapper.deletePositionDeparts(positionId);
        if(StringUtils.isNotBlank(departs)) {
            for (String groupId : departs.split(",")) {
                mapper.insertPositionDepart(UUIDUtils.generateUuid(),positionId,groupId, BaseContextHandler.getTenantID());
            }
        }
    }

    public List<DepartTree> getPositionDeparts(String positionId) {
        List<Depart> departs = mapper.selectPositionDeparts(positionId);
        List<DepartTree> trees = new ArrayList<>();
        departs.forEach(depart -> {
            trees.add(new DepartTree(depart.getId(), depart.getParentId(), depart.getName(),depart.getCode()));
        });
        return trees;
    }

    @Override
    public void insertSelective(Position entity) {
        String departId = entity.getDepartId();
        entity.setId(UUIDUtils.generateUuid());
        super.insertSelective(entity);
        entity.setDepartId(departId);
        updateSelectiveById(entity);
    }

    public List<Position> departPositionList(List<String> departIds,String XZ,String FZR){
        return  mapper.departPositionList(departIds,XZ,FZR);
    }

    public Position positionuser(String userid){
        return  mapper.positionuser(userid);
    }

    public List<UserVo> PositionUsers(String depatId,String jobCategoryCode,String username){
        List<UserVo> userVoList=mapper.positionUsers(depatId,jobCategoryCode,username);
        for(UserVo userVo:userVoList){
            String jobcodename="";
            if(org.apache.commons.lang3.StringUtils.isNotEmpty(userVo.getAttr3())){
                List<String> jobcodeList= Arrays.asList(userVo.getAttr3().split(","));

                for(int i=0;i<jobcodeList.size();i++){
                    jobcodename+=dictValueCtr.getDictValueByCode("comm_job_category_"+jobcodeList.get(i)).get(jobcodeList.get(i))+",";
                }
                jobcodename=jobcodename.substring(0,jobcodename.length()-1);
            }
            userVo.setPname(jobcodename);
        }
        return userVoList;

    }
}