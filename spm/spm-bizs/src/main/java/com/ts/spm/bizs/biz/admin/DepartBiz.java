package com.ts.spm.bizs.biz.admin;

import com.alibaba.fastjson.JSONObject;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.merge.annonation.MergeResult;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.TreeUtil;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.entity.admin.Depart;
import com.ts.spm.bizs.entity.admin.Group;
import com.ts.spm.bizs.entity.admin.User;
import com.ts.spm.bizs.entity.admin.UserDepart;
import com.ts.spm.bizs.entity.person.AjyInfo;
import com.ts.spm.bizs.mapper.admin.DepartExMapper;
import com.ts.spm.bizs.mapper.admin.DepartMapper;
import com.ts.spm.bizs.mapper.admin.UserDepartMapper;
import com.ts.spm.bizs.mapper.admin.UserMapper;
import com.ts.spm.bizs.rest.dict.DictValueController;
import com.ts.spm.bizs.service.TableResultParser;
import com.ts.spm.bizs.vo.admin.DepartTree;
import com.ts.spm.bizs.vo.admin.DepartVo;
import com.ts.spm.bizs.vo.admin.OperatorTree;
import com.ts.spm.bizs.vo.admin.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Mr.AG
 * @version 2018-02-04 19:06:43
 * @email 463540703@qq.com
 */
@Service
public class DepartBiz extends BusinessBiz<DepartMapper, Depart> {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DictValueController dictValueCtr;

    @Autowired
    private UserBiz userBiz;
    @Autowired
    private GroupBiz groupBiz;

    @Autowired
    private UserDepartMapper userDepartMapper;
    @Autowired
    private DepartExMapper departExMapper;

    public AjyInfo getDepartIdByName(@Param("name") String name){
        return  mapper.getDepartIdByName(name);
    }
    public Depart selectById(String id){
        return departExMapper.selectByPrimaryKey(id);
    }
    @MergeResult(resultParser = TableResultParser.class)
    public TableResultResponse<User> getDepartUsers(String departId, String userName) {
        List<User> users = this.mapper.selectDepartUsers(departId, userName);
        return new TableResultResponse<User>(users.size(), users);
    }


    public void addDepartUser(String departId, String userIds) {
        if (!StringUtils.isEmpty(userIds)) {
            String[] uIds = userIds.split(",");
            for (String uId : uIds) {
                this.mapper.insertDepartUser(UUIDUtils.generateUuid(), departId, uId, BaseContextHandler.getTenantID());
            }
        }
    }

    /**
     * 根据ID批量获取部门值
     *
     * @param departIDs
     * @return
     */
    public Map<String, String> getDeparts(String departIDs) {
        if (StringUtils.isBlank(departIDs)) {
            return new HashMap<>();
        }
        departIDs = "'" + departIDs.replaceAll(",", "','") + "'";
        List<Depart> departs = mapper.selectByIds(departIDs);
        return departs.stream().collect(Collectors.toMap(Depart::getId, depart -> JSONObject.toJSONString(depart)));
    }

    public void delDepartUser(String departId, String userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user.getDepartId().equals(departId)) {
            throw new BusinessException("无法移除用户的默认关联部门,若需移除,请前往用户模块更新用户部门!");
        }
        this.mapper.deleteDepartUser(departId, userId);
    }

    @Override
    public void insertSelective(Depart entity) {
        entity.setId(UUIDUtils.generateUuid());
        super.insertSelective(entity);
    }

    public List<UserVo> usersByDepart(List<String> departIDS, String jobCode,List<String> userids) {
        List<UserVo> userVoList=mapper.userByDepart(departIDS, jobCode,userids);
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
            Depart depart=mapper.selectByPrimaryKey(userVo.getDepartId());
            userVo.setDepatName(depart.getName());
        }
        return userVoList;
    }

    public List<Depart> organListByPolice(List<String> policeIds, List<String> types) {
        DepartVo departVo = new DepartVo();
        departVo.setPoliceIds(policeIds);
        departVo.setTypes(types);
        return mapper.organListByPolice(departVo);
    }


    public List<Depart> parentAllDeparts(String deptId) {
        Depart depart = new Depart();
        depart.setId(deptId);
        return mapper.parentAllDeparts(depart);
    }

    public List<Depart> childAllDeparts(String deptId) {
        Depart depart = new Depart();
        depart.setId(deptId);
        return mapper.childAllDeparts(depart);
    }

    /**
     * 新修改部门 添加默认岗位和权限
     *
     * @param depart
     * @param userid
     * @param tenantid
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ObjectRestResponse upDepart(Depart depart, String userid, String tenantid) {
        User user = userBiz.selectById(userid);
        List<Group> groups = groupBiz.selectListAll();
        Depart dp = mapper.selectByPrimaryKey(depart.getId());
        if (dp == null) {
            return ObjectRestResponse.error("机构不存在");
        }
        dp.setName(depart.getName());
        dp.setAddress(depart.getAddress());
        dp.setNameOfOffice(depart.getNameOfOffice());
        dp.setType(depart.getType());
        dp.setAddress(depart.getAddress());
        dp.setX(depart.getX());
        dp.setY(depart.getY());
        dp.setPoliceId(depart.getPoliceId());
        if (StringUtils.isNotEmpty(depart.getRemark())) {
            dp.setRemark(depart.getRemark());
        }
        if (depart.getSort() != null) {
            dp.setSort(depart.getSort());
        }
        if(StringUtils.isNotEmpty(depart.getAttr1())) {
            dp.setAttr1(depart.getAttr1());
        }if(StringUtils.isNotEmpty(depart.getAttr2())) {
            dp.setAttr2(depart.getAttr2());
        }if(StringUtils.isNotEmpty(depart.getAttr3())) {
            dp.setAttr3(depart.getAttr3());
        }if(StringUtils.isNotEmpty(depart.getAttr4())) {
            dp.setAttr4(depart.getAttr4());
        }
        if(StringUtils.isNotEmpty(depart.getAttr5())) {
          dp.setAttr5(depart.getAttr5());
        }
        if (mapper.updateByPrimaryKeySelective(dp) <= 0) {
            return ObjectRestResponse.error("机构修改有误");
        }
        //smartFeign.updatedevicesSchoolName(depart.getId(), depart.getName());
//        Position position = new Position();
//        position.setDepartId(depart.getId());
//        List<Position> positions = positionBiz.selectList(position);
//        if (positions == null || positions.size() == 0) {
//            List<Map<String, Object>> jobList = dictFeign.dictValueByCode("comm_job_category");
//            if (jobList != null && jobList.size() > 0) {
//                for (int i = 0; i < jobList.size(); i++) {
//                    //判断是否是学校机构
//                    if (UserConstant.org_depart_school.equals(depart.getType()) || UserConstant.org_depart_municipalSchools.equals(depart.getType())) {
//                        if (!UserConstant.comm_job_category_GAYH.equals(jobList.get(i).get("code").toString())) {
//                            Position schoolPosition = new Position();
//                            schoolPosition.setJobCategoryCode(jobList.get(i).get("code").toString());
//                            schoolPosition.setDepartId(depart.getId());
//                            schoolPosition.setCode(UUIDUtils.generateShortUuid());
//                            schoolPosition.setCrtTime(new Date());
//                            schoolPosition.setName(jobList.get(i).get("labelZhCh").toString());
//                            schoolPosition.setType("role");
//                            schoolPosition.setCrtUserId(userid);
//                            schoolPosition.setCrtUserName(user.getName());
//                            schoolPosition.setTenantId(tenantid);
//                            String posiionid = UUIDUtils.generateUuid();
//                            schoolPosition.setId(posiionid);
//                            if (positionBiz.insertSelective2(schoolPosition) <= 0) {
//                                return ObjectRestResponse.error("岗位添加失败");
//                            }
//                            for (Group schoolg : groups) {
//                                if (schoolg.getCode().equals(jobList.get(i).get("code").toString())) {
//                                    positionBiz.modifyPositionGroups(posiionid, schoolg.getId());
//                                }
//                            }
//                        }
//                    } else if (UserConstant.org_depart_Police.equals(depart.getType()) || UserConstant.org_depart_provincePolice.equals(depart.getType()) || UserConstant.org_depart_cityPolice.equals(depart.getType()) || UserConstant.org_depart_areaPolice.equals(depart.getType())) {//判断是否是公安机构
//                        if (UserConstant.comm_job_category_GLY.equals(jobList.get(i).get("code").toString()) || UserConstant.comm_job_category_GAYH.equals(jobList.get(i).get("code").toString())) {
//                            Position GAPosition = new Position();
//                            GAPosition.setJobCategoryCode(jobList.get(i).get("code").toString());
//                            GAPosition.setDepartId(depart.getId());
//                            GAPosition.setCode(UUIDUtils.generateShortUuid());
//                            GAPosition.setCrtTime(new Date());
//                            GAPosition.setName(jobList.get(i).get("labelZhCh").toString());
//                            GAPosition.setType("role");
//                            GAPosition.setCrtUserId(userid);
//                            GAPosition.setCrtUserName(user.getName());
//                            GAPosition.setTenantId(tenantid);
//                            String posiionid = UUIDUtils.generateUuid();
//                            GAPosition.setId(posiionid);
//                            if (positionBiz.insertSelective2(GAPosition) <= 0) {
//                                return ObjectRestResponse.error("岗位添加失败");
//                            }
//                            for (Group schoolg : groups) {
//                                if (schoolg.getCode().equals(jobList.get(i).get("code").toString())) {
//                                    positionBiz.modifyPositionGroups(posiionid, schoolg.getId());
//                                }
//                            }
//                        }
//                    } else {
//                        if (UserConstant.comm_job_category_GLY.equals(jobList.get(i).get("code").toString()) || UserConstant.comm_job_category_AQZR.equals(jobList.get(i).get("code").toString())) {
//                            Position JYPosition = new Position();
//                            JYPosition.setJobCategoryCode(jobList.get(i).get("code").toString());
//                            JYPosition.setDepartId(depart.getId());
//                            JYPosition.setCode(UUIDUtils.generateShortUuid());
//                            JYPosition.setCrtTime(new Date());
//                            JYPosition.setName(jobList.get(i).get("labelZhCh").toString());
//                            JYPosition.setType("role");
//                            JYPosition.setCrtUserId(userid);
//                            JYPosition.setCrtUserName(user.getName());
//                            JYPosition.setTenantId(tenantid);
//                            String posiionid = UUIDUtils.generateUuid();
//                            JYPosition.setId(posiionid);
//                            if (positionBiz.insertSelective2(JYPosition) <= 0) {
//                                return ObjectRestResponse.error("岗位添加失败");
//                            }
//                            for (Group schoolg : groups) {
//                                if (schoolg.getCode().equals(jobList.get(i).get("code").toString())) {
//                                    positionBiz.modifyPositionGroups(posiionid, schoolg.getId());
//                                }
//                            }
//                        }
//                    }
//
//                }
//            }
//        }
        return ObjectRestResponse.ok("修改成功");


    }

    public Depart selectByParent(Depart depart) {
        return mapper.selectByParent(depart);
    }

    public List<Depart> selectAllSchool() {
        return mapper.selectAllSchool();
    }

    public List<String> selectChildren(String departId) {
//        Depart depart = new Depart();
//        depart.setId(deptId);
        return mapper.selectChildren(departId);
    }

    public List<Depart> selectChildrenDepart(String departId) {
//        Depart depart = new Depart();
//        depart.setId(deptId);
        return mapper.selectChildrenDepart(departId,BaseContextHandler.getUserID());
    }

    public List<Depart> selectChildrenDepartByUserId(String departId,String userId) {
        return mapper.selectChildrenDepart(departId,userId);
    }

    public int getDepartUsers(String id){
        return userDepartMapper.getDepartUsers(id);
    }

    public List<Depart> getDepartList(String name,String type,String status,String userId){
        return mapper.getDepartList(name,type,status,userId);
    }

    public List<Depart> getDepartTree(String userId,List<String> typeList){
        return mapper.getDepartTree(userId,typeList);
    }

    public List<DepartTree> createTree(List<Depart> all,List<Depart> matched){
        List<Depart> result=new ArrayList<>();
        if(all.size()==matched.size()){
            result=all;
        }else{
            for(Depart o:matched){
                boolean flag=false;
                for (Depart dt:result){
                    if(dt.getId().equals(o.getId())){
                        flag=true;
                        break;
                    }
                }
                if(!flag){
                    result.add(o);
                    handlePid(o, all, result);
                }
            }
        }

        List<DepartTree> trees = new ArrayList<>();
        result.forEach(Depart -> {
            trees.add(new DepartTree(Depart.getId(), Depart.getName(), Depart.getParentId(), Depart.getCode(), Depart.getType(), Depart.getStatus(), Depart.getCrtTime(), Depart.getOrderNum(),Depart.isDisabled()));
        });
        List<DepartTree> list= TreeUtil.bulid(trees, "-1", null);
        return list;
    }

    public List<OperatorTree> createOperatorTree(List<Depart> all,List<Depart> matched){
        List<Depart> result=new ArrayList<>();
        if(all.size()==matched.size()){
            result=all;
        }else{
            for(Depart o:matched){
                boolean flag=false;
                for (Depart dt:result){
                    if(dt.getId().equals(o.getId())){
                        flag=true;
                        break;
                    }
                }
                if(!flag){
                    result.add(o);
                    handlePid(o, all, result);
                }
            }
        }


        List<OperatorTree> trees = new ArrayList<>();
        result.forEach(Depart -> {
            trees.add(new OperatorTree(Depart.getId(), Depart.getName(), Depart.getParentId(), Depart.getCode(), Depart.getType(), Depart.getStatus(), Depart.getCrtTime(), Depart.getOrderNum(),Depart.isDisabled(),userBiz.getUserList(null,"","0",selectChildren(Depart.getId()),"值机员","")));
        });
        List<OperatorTree> list= TreeUtil.bulid(trees, "-1", null);
        return list;
    }

    public void handlePid(Depart arg, List<Depart> all, List<Depart> result){
        for(Depart item:all){
            if(item.getId().equals(arg.getParentId())){
                boolean flag=false;
                for (Depart o:result){
                    if(o.getId().equals(item.getId())){
                        flag=true;
                        break;
                    }
                }
                if(!flag)   result.add(item);
                if(!"-1".equals(item.getParentId()))    handlePid(item,all,result);
                break;
            }
        }
    }

    public String getDepartNameById(String departId){

        return mapper.getDepartNameById(departId);
    }

    public String getDepartName(String departId,String type){
        return mapper.getDepartName(departId,type);
    }

    public List<Depart> getUserMapDepart(String userId){
        return mapper.getUserMapDepart(userId);
    }

    public void addUserDepart(String departIds,String userId){

        userDepartMapper.delDepartUsers(userId);

        String[] depart = departIds.split(",");
        for(String dep:depart){
            UserDepart ud=new UserDepart();
            ud.setId(UUIDUtils.generateUuid());
            ud.setUserId(userId);
            ud.setDepartId(dep);
            ud.setCrtTime(new Date());
            ud.setCrtUserId(BaseContextHandler.getUserID());
            ud.setCrtUserName(BaseContextHandler.getUsername());

            userDepartMapper.insertSelective(ud);
        }
    }

    public List<DepartTree> getAgencyTree(String type,String name){
        List<String> typeList = Arrays.asList(type.split(","));

        Example example = new Example(Depart.class);
        example.setOrderByClause("order_num asc");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status","0");
        if (StringUtils.isNotBlank(name)) {
            criteria.andLike("name", "%" + name + "%");
        }
        criteria.andIn("type",typeList);
        List<Depart> all=selectByExample(example);
        UserVo user=userBiz.getUserInfo(BaseContextHandler.getUserID());
//        criteria.andEqualTo("id",user.getDepartId());
        List<Depart> matched=selectByExample(example);

        List<DepartTree> list=createTree(all,matched);

        return list;
    }

    public List<Depart> selectParent(String id) {
        return mapper.getParent(id);
    }

    /**
     * 与用户权限无关的获取整颗某类型的树
     * @param typeList
     * @return
     */
    public List<DepartTree> allTreeNoUser(List<String> typeList){
        Example example = new Example(Depart.class);
        example.setOrderByClause("order_num asc");
        List<Depart> all=selectByExample(example);
        all.forEach(Depart->Depart.setDisabled(false));

        Example.Criteria criteria = example.createCriteria();

        criteria.andIn("type",typeList);
        List<Depart> matched=selectByExample(example);
        matched.forEach(Depart->Depart.setDisabled(false));

        List<DepartTree> list=createTree(all,matched);

        return list;
    }

    public List<Depart> allDepartNoUser(List<String> typeList,String name,String status){
        Example example = new Example(Depart.class);
        example.setOrderByClause("order_num asc");
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(status)) {
            criteria.andEqualTo("status",status);
        }

        if (StringUtils.isNotBlank(name)) {
            criteria.andLike("name", "%" + name + "%");
        }
        if(typeList.size()>0){
            criteria.andIn("type",typeList);
        }

        List<Depart> departs=selectByExample(example);

        return departs;
    }
}
