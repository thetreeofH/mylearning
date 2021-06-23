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

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.merge.core.MergeCore;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.*;
import com.ts.spm.bizs.entity.admin.*;
import com.ts.spm.bizs.mapper.admin.DepartMapper;
import com.ts.spm.bizs.mapper.admin.UserMapper;
import com.ts.spm.bizs.rest.dict.DictValueController;
import com.ts.spm.bizs.service.LicenceService;
import com.ts.spm.bizs.vo.admin.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @version 2017-06-08 16:23
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserBiz extends BaseBiz<UserMapper, User> {
    @Autowired
    private MergeCore mergeCore;

    @Autowired
    private DepartMapper departMapper;

    @Autowired
    DictValueController dictValueCtr;
    @Autowired
    private  GroupBiz groupBiz;
    @Autowired
    private GateLogBiz gateLogBiz;

    @Autowired
    private EmployeeBiz employeeBiz;

    @Autowired
    private DepartBiz departBiz;

    @Autowired
    private LicenceService licenceService;

    private Sha256PasswordEncoder encoder = new Sha256PasswordEncoder();


    @Override
    public User selectById(Object id) {
        User user = super.selectById(id);
        try {
            mergeCore.mergeOne(User.class, user);
            return user;
        } catch (Exception e) {
            return super.selectById(id);
        }
    }

    public Boolean changePassword(String oldPass, String newPass) {
        User user = this.getUserByUsername(BaseContextHandler.getUsername());
        if (encoder.matches(oldPass, user.getPassword())) {
            String password = encoder.encode(newPass);
            user.setPassword(password);
            this.updateSelectiveById(user);
            return true;
        }
        return false;
    }

    @Override
    public void insertSelective(User entity) {
        String password = encoder.encode(entity.getPassword());
        String departId = entity.getDepartId();
        EntityUtils.setCreatAndUpdatInfo(entity);
        entity.setPassword(password);
        entity.setDepartId(departId);
        entity.setIsDeleted(BooleanUtil.BOOLEAN_FALSE);
        entity.setIsDisabled(BooleanUtil.BOOLEAN_FALSE);
        String userId = UUIDUtils.generateUuid();
        entity.setTenantId(BaseContextHandler.getTenantID());
        entity.setId(userId);
        entity.setIsSuperAdmin(BooleanUtil.BOOLEAN_FALSE);
        // 如果非超级管理员,无法修改用户的租户信息
        //if (BooleanUtil.BOOLEAN_FALSE.equals(mapper.selectByPrimaryKey(BaseContextHandler.getUserID()).getIsSuperAdmin())) {
        if (BooleanUtil.BOOLEAN_FALSE.equals(BaseContextHandler.getIsSuperAdmin())) {
            entity.setIsSuperAdmin(BooleanUtil.BOOLEAN_FALSE);
        }
        departMapper.insertDepartUser(UUIDUtils.generateUuid(), entity.getDepartId(), entity.getId(), BaseContextHandler.getTenantID());
        super.insertSelective(entity);
    }

    @Override
    public void updateSelectiveById(User entity) {
        EntityUtils.setUpdatedInfo(entity);
        User user = mapper.selectByPrimaryKey(entity.getId());
        if (!user.getDepartId().equals(entity.getDepartId())) {
            departMapper.deleteDepartUser(user.getDepartId(), entity.getId());
            departMapper.insertDepartUser(UUIDUtils.generateUuid(), entity.getDepartId(), entity.getId(),BaseContextHandler.getTenantID());
        }
        // 如果非超级管理员,无法修改用户的租户信息
        if (BooleanUtil.BOOLEAN_FALSE.equals(mapper.selectByPrimaryKey(BaseContextHandler.getUserID()).getIsSuperAdmin())) {
            entity.setTenantId(BaseContextHandler.getTenantID());
        }
        // 如果非超级管理员,无法修改用户的租户信息
        if (BooleanUtil.BOOLEAN_FALSE.equals(mapper.selectByPrimaryKey(BaseContextHandler.getUserID()).getIsSuperAdmin())) {
            entity.setIsSuperAdmin(BooleanUtil.BOOLEAN_FALSE);
        }
        super.updateSelectiveById(entity);
    }

    @Override
    public void deleteById(Object id) {
        User user = mapper.selectByPrimaryKey(id);
        user.setIsDeleted(BooleanUtil.BOOLEAN_TRUE);
        this.updateSelectiveById(user);
    }

    @Override
    public List<User> selectByExample(Object obj) {
        Example example = (Example) obj;
        example.createCriteria().andEqualTo("isDeleted", BooleanUtil.BOOLEAN_FALSE);
        List<User> users = super.selectByExample(example);
        try {
            mergeCore.mergeResult(User.class, users);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return users;
        }
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */
    public User getUserByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        user.setIsDeleted(BooleanUtil.BOOLEAN_FALSE);
        user.setIsDisabled(BooleanUtil.BOOLEAN_FALSE);
        return mapper.selectOne(user);
    }

    @Override
    public void query2criteria(Query query, Example example) {
        if (query.entrySet().size() > 0) {
            for (Map.Entry<String, Object> entry : query.entrySet()) {
                Example.Criteria criteria = example.createCriteria();
                criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
                criteria.andEqualTo(entry.getKey(),entry.getValue().toString());
                example.or(criteria);
            }
        }
    }

    public List<String> getUserDataDepartIds(String userId) {
        return mapper.selectUserDataDepartIds(userId);
    }

    /**
     * 添加用户和对应机构角色
     * @param userVo
     * @param userid
     * @param tenantid
     * @return
     */
    public ObjectRestResponse addUser(UserVo userVo,String userid,String tenantid,String ip){
        String id=UUIDUtils.generateUuid();
        User user= new User();
        user.setId(id);
        user.setName(userVo.getName());
        user.setUsername(userVo.getUsername());
        user.setPassword(encoder.encode(userVo.getPassword()));
        user.setDepartId(userVo.getDepartId());
        user.setDescription(userVo.getDescription());
        user.setCrtUserId(userid);
        user.setTenantId(tenantid);
        user.setCrtTime(new Date());
        user.setIsDeleted("0");
        user.setIsDisabled("0");
        user.setIsSuperAdmin("0");
        user.setImgurl(userVo.getImgurl());
        if(StringUtils.isNotEmpty(userVo.getStatus())){
            user.setStatus(userVo.getStatus());
        }else{
            user.setStatus("1");
        }
        user.setTelPhone(userVo.getTelPhone());
        if(StringUtils.isNotEmpty(userVo.getAttr1())){
            user.setAttr1(userVo.getAttr1());
        }
        if(StringUtils.isNotEmpty(userVo.getAttr2())){
            user.setAttr2(userVo.getAttr2());
        }
        if(StringUtils.isNotEmpty(userVo.getAttr3())){
            user.setAttr3(userVo.getAttr3());
        }
        if(StringUtils.isNotEmpty(userVo.getAttr4())){
            user.setAttr4(userVo.getAttr4());
        }
        if(StringUtils.isNotEmpty(userVo.getAttr5())){
            user.setAttr5(userVo.getAttr5());
        }
        if(StringUtils.isNotEmpty(userVo.getAttr6())){
            user.setAttr6(userVo.getAttr6());
        }
        if(StringUtils.isNotEmpty(userVo.getAttr7())){
            user.setAttr7(userVo.getAttr7());
        }
        String sex=dictValueCtr.getDictValueByCode("comm_sex_"+userVo.getSex()).get(userVo.getSex());
        user.setSex(sex);
        if(mapper.insertSelective(user)<0){
            return ObjectRestResponse.error("添加失败");
        }
        //添加岗位
        groupBiz.newmodifyGroupUsers(userVo.getGroupid(),id);
        GateLog gateLog=new GateLog();
        gateLog.setMenu("用户管理");
        gateLog.setOpt("新增用户");
        gateLog.setUri("user/info/addUser");
        gateLog.setCrtTime(new Date());
        gateLog.setCrtUser(userid);
        gateLog.setTenantId(tenantid);
        User cretuser=mapper.selectByPrimaryKey(userid);
        gateLog.setCrtName(cretuser.getName());
        gateLog.setCrtHost(ip);
        gateLog.setDepartId(cretuser.getDepartId());
        gateLogBiz.insertSelective(gateLog);
        return ObjectRestResponse.ok("添加成功");
    }

    /**
     * 添加用户和对硬件机构岗位
     * @param userVo
     * @param userid
     * @param tenantid
     * @return
     */
    public ObjectRestResponse upUser(UserVo userVo,String userid,String tenantid,String ip){
        User user= mapper.selectByPrimaryKey(userVo.getId());
        user.setName(userVo.getName());
        user.setUsername(userVo.getUsername());
        user.setPassword(encoder.encode(userVo.getPassword()));
        user.setDepartId(userVo.getDepartId());
        user.setDescription(userVo.getDescription());
        user.setTelPhone(userVo.getTelPhone());
        user.setUpdUserId(userid);
        user.setTenantId(tenantid);
        user.setUpdTime(new Date());
        user.setIsDeleted("0");
        user.setIsDisabled("0");
        user.setIsSuperAdmin("0");
        if(StringUtils.isNotEmpty(userVo.getImgurl())){
            user.setImgurl(userVo.getImgurl());
        }else{
            user.setImgurl("");
        }
        if(StringUtils.isNotEmpty(userVo.getStatus())){
            user.setStatus(userVo.getStatus());
        }else{
            user.setStatus("1");
        }
        if(StringUtils.isNotEmpty(userVo.getAttr1())){
            user.setAttr1(userVo.getAttr1());
        }
        if(StringUtils.isNotEmpty(userVo.getAttr2())){
            user.setAttr2(userVo.getAttr2());
        }
        if(StringUtils.isNotEmpty(userVo.getAttr3())){
            user.setAttr3(userVo.getAttr3());
        }
        if(StringUtils.isNotEmpty(userVo.getAttr4())){
            user.setAttr4(userVo.getAttr4());
        }
        if(StringUtils.isNotEmpty(userVo.getAttr5())){
            user.setAttr5(userVo.getAttr5());
        }
        if(StringUtils.isNotEmpty(userVo.getAttr6())){
            user.setAttr6(userVo.getAttr6());
        }
        if(StringUtils.isNotEmpty(userVo.getAttr7())){
            user.setAttr7(userVo.getAttr7());
        }
        String sex=dictValueCtr.getDictValueByCode("comm_sex_"+userVo.getSex()).get(userVo.getSex());
        user.setSex(sex);
        if(mapper.updateByPrimaryKeySelective(user)<0){
            return ObjectRestResponse.error("修改失败");
        }
        //添加岗位
        groupBiz.newmodifyGroupUsers(userVo.getGroupid(),user.getId());
        GateLog gateLog=new GateLog();
        gateLog.setMenu("用户管理");
        gateLog.setOpt("修改用户");
        gateLog.setUri("user/info/updatUser");
        gateLog.setCrtTime(new Date());
        gateLog.setCrtUser(userid);
        gateLog.setTenantId(tenantid);
        User cretuser=mapper.selectByPrimaryKey(userid);
        gateLog.setCrtName(cretuser.getName());
        gateLog.setCrtHost(ip);
        gateLog.setDepartId(cretuser.getDepartId());
        gateLogBiz.insertSelective(gateLog);
        return ObjectRestResponse.ok("修改成功");
    }

    public UserVo userPositionbyID(String id){
        return mapper.userPositionbyID(id);
    }

    public List<UserVo> userpage(List<String> departIds,String jobcode,String name,String type,String groupid){
        List<UserVo> userVos=mapper.userPage(departIds,jobcode,name,type,groupid);
        for(UserVo userVo:userVos){
            String jobcodename="";
            if(StringUtils.isNotEmpty(userVo.getAttr3())){
                List<String> jobcodeList= Arrays.asList(userVo.getAttr3().split(","));

                for(int i=0;i<jobcodeList.size();i++){
                    jobcodename+=dictValueCtr.getDictValueByCode("comm_job_category_"+jobcodeList.get(i)).get(jobcodeList.get(i))+",";

                }
                jobcodename=jobcodename.substring(0,jobcodename.length()-1);
            }
            userVo.setPname(jobcodename);
            List<Group> groupList=groupBiz.groupByuserid(userVo.getId());
            String gname="";
            if(groupList!=null&&groupList.size()>0){
                for(Group group:groupList){
                    gname+=group.getName()+",";
                }
                gname=gname.substring(0,gname.length()-1);
            }
            userVo.setGname(gname);
            Depart depart=departMapper.selectByPrimaryKey(userVo.getDepartId());
            if(depart!=null){
                userVo.setDepatName(depart.getName());
            }
        }
        return userVos;

    }

    public List<UserVo> userbyjobcode(List<String> item,String jobcode1,String jobcode2,String jobcode3,String jobcode4,String name){
        List<UserVo> userVoList=mapper.userbyjobcode(item,jobcode1,jobcode2,jobcode3,jobcode4,name);
        for(UserVo userVo:userVoList){
            String jobcodename="";
            if(StringUtils.isNotEmpty(userVo.getAttr3())){
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

    public ObjectRestResponse restPassword(String userid){
      User user=mapper.selectByPrimaryKey(userid);
      if(user==null){
        return ObjectRestResponse.error("用户不存在");
      }
      String password=encoder.encode("123456");
      user.setPassword(password);
      if(mapper.updateByPrimaryKey(user)<=0){
        return ObjectRestResponse.error("重置失败");
      }
      return  ObjectRestResponse.ok("重置成功");
    }

    public UserVo getUserInfo(String id){
        User userInfo=mapper.selectByPrimaryKey(id);

        UserVo user=new UserVo();
        user.setId(userInfo.getId());

        Employee employee=employeeBiz.selectById(userInfo.getEmployId());
        user.setName(employee.getName());

        user.setEmployId(userInfo.getEmployId());
        user.setLoginName(userInfo.getUsername());
//        user.setDepartId(userInfo.getDepartId());
        user.setDepartId(employee.getDepartId());
        Depart depart=departBiz.selectById(employee.getDepartId());
        user.setDepart(depart.getName());
        user.setPassword(userInfo.getPassword());
        user.setStatus(userInfo.getStatus());
        user.setMobile(userInfo.getMobilePhone());
        user.setUserStartDate(userInfo.getUserStartDate());
        user.setUserEndDate(userInfo.getUserEndDate());
        user.setIsDisabled(userInfo.getIsDisabled());
        user.setDescription(userInfo.getDescription());
        user.setIsSuperAdmin(userInfo.getIsSuperAdmin());
        List<Group> roles=groupBiz.getUsersRole(id,"","0");
        if(roles.size()>0){
            String role = "";
            for(int i=0;i<roles.size();i++){
                role += roles.get(i).getId();
                if(i != roles.size() -1){
                    role += ",";
                }

            }
            user.setGroups(role);
        }

        return user;
    }

    public List<UserVo> getUserList(List<String> name,String loginname,String isDisabled, List<String> departid,String roleName,String roleId){

        List<UserVo> userList=new ArrayList<UserVo>();

        userList=mapper.getUserList(name,loginname,isDisabled,departid,roleName,roleId);


        for(UserVo user:userList){
            user.setDepart(departBiz.getDepartNameById(user.getDepartId()));
            List<Group> groupList=groupBiz.groupByuserid(user.getId());
            String role = "";
            for(int i=0;i<groupList.size();i++){
                role += groupList.get(i).getName();
                if(i != groupList.size() -1){
                    role += ",";
                }

            }
            user.setName(role);

        }

        return userList;

    }

    public String addUserVO(UserVo userVO){
        User user=new User();

        user.setId(UUIDUtils.generateUuid());
        user.setUsername(userVO.getLoginName());
//        if(userVO.getName()!=null&&userVO.getName()!=""){
//            user.setName(userVO.getName());
//        }else{
//            user.setName("");
//        }
        Employee employee=employeeBiz.selectById(userVO.getEmployId());
        user.setName(employee.getName());

        user.setEmployId(userVO.getEmployId());
        user.setDepartId(userVO.getDepartId());
        user.setPassword(encoder.encode(userVO.getPassword()));

        if(userVO.getUserStartDate()!=null&&userVO.getUserStartDate()!=""){
            user.setUserStartDate(userVO.getUserStartDate());
        }else{
            user.setUserStartDate("");
        }

        if(userVO.getUserEndDate()!=null&&userVO.getUserEndDate()!=""){
            user.setUserEndDate(userVO.getUserEndDate());
        }else{
            user.setUserEndDate("");
        }


//        user.setIsDisabled(userVO.getIsDisabled());
        user.setIsDisabled("0");

        if(userVO.getDescription()!=null&&userVO.getDescription()!=""){
            user.setDescription(userVO.getDescription());
        }else{
            user.setDescription("");
        }

        user.setStatus(userVO.getStatus());
        user.setCrtTime(new Date());
        user.setCrtUserId(BaseContextHandler.getUserID());
        user.setCrtUserName(BaseContextHandler.getUsername());
        user.setIsDeleted("0");
        user.setTenantId(BaseContextHandler.getTenantID());

        //判断所属角色是否包含超级管理员
        String groups=userVO.getGroups();
        String[] groupIds = groups.split(",");
        String isSuperAdmin="0";
        for(String temp:groupIds){
            Group group=groupBiz.selectById(temp);
            if(group.getCode().equals("CJGLY")){
                isSuperAdmin="1";
                break;
            }
        }
        user.setIsSuperAdmin(isSuperAdmin);

        String validFlag=licenceService.getValidFlag(user.getId()+"_"+user.getUsername());
        user.setValidFlag(validFlag);

        String dateFlag="";
        if(user.getStatus().equals("1")){
            dateFlag=licenceService.getValidFlag(user.getId()+"_2050_12_30 23:59:59");
        }else{
            String userEndDate=user.getUserEndDate();
            dateFlag=licenceService.getValidFlag(user.getId()+"_"+userEndDate.replace("-","_"));
        }
        user.setDateFlag(dateFlag);

//        mapper.addUser(user);
        mapper.insertSelective(user);
        return user.getId();
    }

    public void updateUserState(String id,String isDisabled){
        String updUserId=BaseContextHandler.getUserID();
        String updUserName=BaseContextHandler.getUsername();
        Date updTime=new Date();
        mapper.updateUserStatus(id,isDisabled,updTime,updUserId,updUserName);
    }

    public void resetPassword(String userId,String password){
        Date updTime=new Date();
        String updUserId=BaseContextHandler.getUserID();
        String updUserName=BaseContextHandler.getUsername();
        mapper.updatePassword(userId,encoder.encode(password),updTime,updUserId,updUserName);
    }

    public void updateUser(UserVo userVO,String userId){

        userVO.setId(userId);
        userVO.setUsername(userVO.getLoginName());

        if(userVO.getName()!=null&&userVO.getName()!=""){
            userVO.setName(userVO.getName());
        }else{
            userVO.setName("");
        }

        if(userVO.getUserStartDate()!=null&&userVO.getUserStartDate()!=""){
            userVO.setUserStartDate(userVO.getUserStartDate());
        }

        if(userVO.getUserEndDate()!=null&&userVO.getUserEndDate()!=""){
            userVO.setUserEndDate(userVO.getUserEndDate());
        }

        if(userVO.getDescription()!=null&&userVO.getDescription()!=""){
            userVO.setDescription(userVO.getDescription());
        }else{
            userVO.setDescription("");
        }

        userVO.setUpdTime(new Date());
        userVO.setUpdUserId(BaseContextHandler.getUserID());
        userVO.setUpdUserName(BaseContextHandler.getUsername());

        //判断所属角色是否包含超级管理员
//        userVO.setIsSuperAdmin("0");
        String groups=userVO.getGroups();
        String[] groupIds = groups.split(",");
        String isSuperAdmin="0";
        for(String temp:groupIds){
            Group group=groupBiz.selectById(temp);
            if(group.getCode().equals("CJGLY")){
                isSuperAdmin="1";
                break;
            }
        }
        userVO.setIsSuperAdmin(isSuperAdmin);

        userVO.setTenantId(BaseContextHandler.getTenantID());

        String validFlag=licenceService.getValidFlag(userVO.getId()+"_"+userVO.getLoginName());
        userVO.setValidFlag(validFlag);

        String dateFlag="";
        if(userVO.getStatus().equals("1")){
            dateFlag=licenceService.getValidFlag(userVO.getId()+"_2050_12_30 23:59:59");
        }else{
            String userEndDate=userVO.getUserEndDate();
            dateFlag=licenceService.getValidFlag(userVO.getId()+"_"+userEndDate.replace("-","_"));
        }
        userVO.setDateFlag(dateFlag);

//        mapper.updateUser(userVO,userId);
        updateSelectiveById(userVO);
    }

    public void delUser(String userId){
        Date updTime=new Date();
        String updUserId=BaseContextHandler.getUserID();
        String updUserName=BaseContextHandler.getUsername();
        mapper.delUser(userId,updUserId,updUserName,updTime);
    }

    public boolean getUserCnt(){
        List<UserVo> userVOList=getUserList(new ArrayList<>(),"","",new ArrayList<>(),"","");
        int userRealCnt=userVOList.size();
        int userUpCnt=licenceService.getLicenceResult().getUserCnt();
        if(userRealCnt>userUpCnt){
            return false;
        }
        return true;
    }

    public List<User> getUserName(String userName){
        return mapper.getUserName(userName);
    }
}
