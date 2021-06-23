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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.Sha256PasswordEncoder;
import com.github.wxiaoqi.security.common.util.TreeUtil;
import com.ts.spm.bizs.biz.admin.*;
import com.ts.spm.bizs.util.UserConstant;
import com.ts.spm.bizs.entity.admin.*;
import com.ts.spm.bizs.rest.dict.DictValueController;
import com.ts.spm.bizs.service.PermissionService;
import com.ts.spm.bizs.vo.admin.*;
import com.ts.spm.bizs.vo.admin.api.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @version 2017-06-08 11:51
 */
@RestController
@RequestMapping("user")
@CheckUserToken
@CheckClientToken
@Api(tags = "用户模块")
public class UserController extends BaseController<UserBiz, User, String> {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PositionBiz positionBiz;

    @Autowired
    private MenuBiz menuBiz;
    @Autowired
    private GroupBiz groupBiz;
    @Autowired
    private DepartBiz departBiz;
    @Autowired
    private EmployeeBiz employeeBiz;
    @Autowired
    private DictValueController dictValueCtr;

  private Sha256PasswordEncoder encoder = new Sha256PasswordEncoder();

    @IgnoreUserToken
    @ApiOperation("账户密码验证")
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public ObjectRestResponse<UserInfo> validate(String username, String password) {
        return new ObjectRestResponse<UserInfo>().data(permissionService.validate(username, password));
    }

    @IgnoreUserToken
    @ApiOperation("根据账户名获取用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public ObjectRestResponse<AuthUser> validate(String username) {
        AuthUser user = new AuthUser();
        User baseUser = baseBiz.getUserByUsername(username);

        if(baseUser!=null) {
            BeanUtils.copyProperties(baseUser, user);
            Employee employee = employeeBiz.selectById(baseUser.getEmployId());
            if(employee!=null){
                user.setName(employee.getName());
            }

        }

        return new ObjectRestResponse<AuthUser>().data(user);
    }


    @ApiOperation("账户修改密码")
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public ObjectRestResponse<Boolean> changePassword(String oldPass, String newPass) {
        if (baseBiz.changePassword(oldPass, newPass)) {
            return new ObjectRestResponse<Boolean>().data(baseBiz.changePassword(oldPass, newPass));
        } else {
            return ObjectRestResponse.error("旧密码有误");
        }

    }


    @ApiOperation("获取用户信息接口")
    @RequestMapping(value = "/front/info", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getUserInfo() throws Exception {
        FrontUser userInfo = permissionService.getUserInfo();

        if (userInfo == null) {
            return ResponseEntity.status(401).body(false);
        } else {

            //设置机构名称
            Depart depart=departBiz.selectById(userInfo.getDepartId());
            if (depart!=null){
                userInfo.setDepartName(depart.getName());
                userInfo.setDepartType(depart.getType());
            }
            return ResponseEntity.ok(userInfo);
        }
    }

    @ApiOperation("获取用户访问菜单")
    @RequestMapping(value = "/front/menus", method = RequestMethod.GET)
    public
    @ResponseBody
    List<MenuTree> getMenusByUsername() throws Exception {
        return permissionService.getMenusByUsername();
    }

    @ApiOperation("获取所有菜单")
    @RequestMapping(value = "/front/menu/all", method = RequestMethod.GET)
    public @ResponseBody
    List<Menu> getAllMenus() throws Exception {
        return menuBiz.selectListAll();
    }

    @ApiOperation("获取用户可管辖部门id列表")
    @RequestMapping(value = "/dataDepart", method = RequestMethod.GET)
    public List<String> getUserDataDepartIds() {
        return baseBiz.getUserDataDepartIds(BaseContextHandler.getUserID());
    }

    @ApiOperation("获取用户流程审批岗位")
    @RequestMapping(value = "/flowPosition", method = RequestMethod.GET)
    public List<String> getUserFlowPositions(String userId) {
        if (BaseContextHandler.getUserID().equals(userId)) {
            return positionBiz.getUserFlowPosition(userId).stream().map(Position::getName).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     * app获取当前用户信息
     *
     * @return
     */
    @GetMapping(value = "info/userinfo")
    public ObjectRestResponse userinfo() {
        //String userid="f84bfeb68c8d4782a894bc2957835e64";
        String userid = BaseContextHandler.getUserID();
        if (StringUtils.isEmpty(userid)) {
            return ObjectRestResponse.error("请登录");
        }
        User user = baseBiz.selectById(userid);
        Map map = JSON.parseObject(user.getDepartId(), Map.class);
        List<Group> groups = groupBiz.groupByuserid(userid);
        List<String> appmeusList=null;
        Map<String, Object> usermap = new HashMap<>();
        if (groups != null&&groups.size()>0) {
           for(int i=0;i<groups.size();i++){
               if(i==0){
                   appmeusList=new ArrayList<>(Arrays.asList(groups.get(i).getAppmenus().split(",")));
               }else{
                   List<String> A= new ArrayList<>(Arrays.asList(groups.get(i).getAppmenus().split(",")));
                   appmeusList.removeAll(A);
                   appmeusList.addAll(A);
               }
           }
            String appmenus = StringUtils.join(appmeusList.toArray(), ",");
            usermap.put("appMenus", appmenus);
        } else {
            usermap.put("appMenus", user.getAppMenus());
        }
        if(map.get("nameOfOffice")!=null){
            if(UserConstant.depart_type_AQBHJG.equals(map.get("nameOfOffice"))){
                usermap.put("ifPolice",1);
            }else{
                usermap.put("ifPolice",0);
            }
        }
        usermap.put("id", user.getId());
        usermap.put("name", user.getName());
        usermap.put("username", user.getUsername());
        usermap.put("depatName", map.get("name"));
        usermap.put("depatId", map.get("id"));
        usermap.put("sex", user.getSex());
        usermap.put("imgurl", user.getImgurl());
        usermap.put("password", user.getPassword());
        return ObjectRestResponse.ok(usermap);
    }

    /**
     * 获取userid 查询userID
     *
     * @return
     */
    @GetMapping(value = "info/userinfoByUserid")
    @IgnoreUserToken
    public Map<String, Object> userinfoByUserid(String userId) {
        Map<String, Object> usermap = new HashMap<>();
        if (StringUtils.isEmpty(userId)) {
            usermap.put("status", -1);
            return usermap;
        }
        User user = baseBiz.selectById(userId);
        if (user != null) {
            if (!"1".equals(user.getIsSuperAdmin())) {
                Map map = JSON.parseObject(user.getDepartId(), Map.class);
                usermap.put("depatName", map.get("name"));
                usermap.put("depatId", map.get("id"));
            }else{
                usermap.put("depatId", user.getDepartId());
            }
           // Position position = positionBiz.positionuser(userId);
            String jobcodename="";
            if(StringUtils.isNotEmpty(user.getAttr3())){
                List<String> jobcodeList= Arrays.asList(user.getAttr3().split(","));

                for(int i=0;i<jobcodeList.size();i++){
                    jobcodename+=dictValueCtr.getDictValueByCode("comm_job_category_"+jobcodeList.get(i)).get(jobcodeList.get(i))+",";
                }
                jobcodename=jobcodename.substring(0,jobcodename.length()-1);
            }
            usermap.put("positionName", jobcodename);
            usermap.put("status", 1);
            usermap.put("id", user.getId());
            usermap.put("appMenus", user.getAppMenus());
            usermap.put("name", user.getName());
            usermap.put("username", user.getUsername());
            usermap.put("password", user.getPassword());
            usermap.put("sex", user.getSex());
            usermap.put("userstatus",user.getStatus());
            usermap.put("imgurl", user.getImgurl());
            usermap.put("mobilePhone", user.getMobilePhone());
            usermap.put("telPhone", user.getTelPhone());
            usermap.put("attr1", user.getAttr1());
        } else {
            usermap.put("status", -1);
            return usermap;
        }
        return usermap;
    }

    @GetMapping(value = "info/userNamLike")
    @IgnoreUserToken
    public String userNamLike(String name) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(name)) {
            criteria.andLike("name", "%" + name + "%");
        }
        List<User> users = baseBiz.selectByExample(example);
        String sql = "";
        if (users != null && users.size() > 0) {
            for (int i = 0; i < users.size(); i++) {
                if (i == 0) {
                    sql = "and (crt_user_id='" + users.get(i).getId() + "'";
                } else {
                    sql = sql + "or crt_user_id='" + users.get(i).getId() + "'";
                }
            }
            sql = sql + ")";
        }
        return sql;
    }


    /**
     * 通过用户名称获取对应的用户IDS
     *
     * @param name
     * @return
     */
    @GetMapping(value = "info/userIDS")
    @IgnoreUserToken
    public List<String> userIDS(String name) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(name)) {
            criteria.andLike("name", "%" + name + "%");
        }
        List<User> users = baseBiz.selectByExample(example);
        List<String> userids = users.stream().map(User::getId).collect(Collectors.toList());
        return userids;
    }

    /**
     * 添加用户（岗位挂钩）
     *
     * @param userVo
     * @return
     */
    @PostMapping(value = "/info/addUser")
    public ObjectRestResponse addUser(@RequestBody UserVo userVo) {
        String ip=request.getRemoteAddr();
        String userid = BaseContextHandler.getUserID();
        String tenantID = BaseContextHandler.getTenantID();
        if (StringUtils.isEmpty(userVo.getUsername())) {
            return ObjectRestResponse.error("用户名不能为空");
        }
        if (StringUtils.isEmpty(userVo.getPassword())) {
            return ObjectRestResponse.error("用户密码不能为空");
        }
        if (StringUtils.isEmpty(userVo.getName())) {
            return ObjectRestResponse.error("用户昵称不能为空");
        }
        if (StringUtils.isEmpty(userVo.getDepartId())) {
            return ObjectRestResponse.error("组织机构不能为空");
        }
        if ( StringUtils.isEmpty(userVo.getAttr3())) {
            return ObjectRestResponse.error("岗位不能为空");
        }
        if (StringUtils.isEmpty(userVo.getGroupid())) {
            return ObjectRestResponse.error("角色不能为空");
        }
        User user =new User();
        user.setUsername(userVo.getUsername());
        List<User> users=baseBiz.selectList(user);
        if(users!=null&&users.size()>0){
            return ObjectRestResponse.error("帐号已存在请勿重复添加");
        }
        return baseBiz.addUser(userVo, userid, tenantID,ip);
    }


    /**
     * 添加用户（岗位挂钩）
     *
     * @param userVo
     * @return
     */
    @PostMapping(value = "/info/updatUser")
    public ObjectRestResponse updatUser(@RequestBody UserVo userVo) {
        String ip=request.getRemoteAddr();
        String userid = BaseContextHandler.getUserID();
        String tenantID = BaseContextHandler.getTenantID();
        if(StringUtils.isEmpty(userVo.getId())){
            return ObjectRestResponse.error("用户ID不能为空");
        }
        if (StringUtils.isEmpty(userVo.getUsername())) {
            return ObjectRestResponse.error("用户名不能为空");
        }
//        if (StringUtils.isEmpty(userVo.getPassword())) {
//            return ObjectRestResponse.error("用户密码不能为空");
//        }
        if (StringUtils.isEmpty(userVo.getName())) {
            return ObjectRestResponse.error("用户昵称不能为空");
        }
        if (StringUtils.isEmpty(userVo.getDepartId())) {
            return ObjectRestResponse.error("组织机构不能为空");
        }
        if (StringUtils.isEmpty(userVo.getAttr3())) {
            return ObjectRestResponse.error("岗位不能为空");
        }
        if (StringUtils.isEmpty(userVo.getGroupid())) {
            return ObjectRestResponse.error("角色不能为空");
        }
        User user =new User();
        user.setUsername(userVo.getUsername());
        User user1=baseBiz.selectOne(user);
        if(user1!=null){
            if(!user1.getId().equals(userVo.getId())){
                return ObjectRestResponse.error("帐号已存在请勿重复添加");
            }
        }
        return baseBiz.upUser(userVo, userid, tenantID,ip);
    }



    /**
     * 通过机构ID查询用户
     *
     * @param params
     * @return
     */
    @GetMapping(value = "/departid/userListbyname")
    @ResponseBody
    public ObjectRestResponse userListbydepatid(@RequestParam Map<String, Object> params) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(params.get("name").toString())) {
            criteria.andLike("name", "%" + params.get("name").toString() + "%");
        }
        if (StringUtils.isNotEmpty(params.get("username").toString())) {
            criteria.andLike("username", "%" + params.get("username").toString() + "%");
        }
        if (StringUtils.isNotEmpty(params.get("departId").toString())) {
            criteria.andEqualTo("departId", params.get("departId").toString());
        }
        List<User> users = baseBiz.selectByExample(example);
        return ObjectRestResponse.ok(users);
    }

    /**
     * 用户详情
     * @param id
     * @return
     */
    @GetMapping(value = "userPositionByid")
    public ObjectRestResponse userPositionByid(String id) {
        if (StringUtils.isEmpty(id)) {
            return ObjectRestResponse.error("用户ID不能为空");
        }
        UserVo userVo = baseBiz.userPositionbyID(id);
        String jobcodename="";
        if(StringUtils.isNotEmpty(userVo.getAttr3())){
            List<String> jobcodeList= Arrays.asList(userVo.getAttr3().split(","));

            for(int i=0;i<jobcodeList.size();i++){
                jobcodename+=dictValueCtr.getDictValueByCode("comm_job_category_"+jobcodeList.get(i)).get(jobcodeList.get(i))+",";
            }
            jobcodename=jobcodename.substring(0,jobcodename.length()-1);
        }
        userVo.setPname(jobcodename);
        List<Group> groups=groupBiz.groupByuserid(id);
        List<String> groupid=groups.stream().map(Group::getId).collect(Collectors.toList());
        String gid="";
        if(groupid!=null&&groupid.size()>0){
            for(int i=0;i<groupid.size();i++){
                gid+=groupid.get(i)+",";
            }
            gid=gid.substring(0,gid.length()-1);
        }
        userVo.setGroupid(gid);
        Depart depart = departBiz.selectById(userVo.getDepartId());
        userVo.setDepatName(depart.getName());
        userVo.setCode(depart.getNameOfOffice());
        return ObjectRestResponse.ok(userVo);
    }

    /**
     * 用户列表
     *
     * @param limit
     * @param page
     * @param name
     * @param jobcode
     * @param departId
     * @return
     */
    @GetMapping(value = "/userpage")
    public TableResultResponse userPage(@RequestParam(name = "limit", defaultValue = "10") int limit, @RequestParam(name = "page", defaultValue = "1") int page, String name, String jobcode, String departId,String type,String groupid) {
        List<String> depatids = null;
        String userid = BaseContextHandler.getUserID();
        User user = baseBiz.selectById(userid);
        if(StringUtils.isEmpty(departId)) {
            if (!"1".equals(user.getIsSuperAdmin())) {
                depatids=new ArrayList<>();
                Map usermap = JSON.parseObject(user.getDepartId(), Map.class);
                String departId1 = usermap.get("id").toString();
                Depart dp = departBiz.selectById(departId1);
                depatids.add(dp.getId());
                Depart depart = new Depart();
                depart.setParentId(departId1);
                List<Depart> departs = departBiz.selectList(depart);
                if (departs != null && departs.size() > 0) {
                    depatids = departids(departs, depatids);
                }
            }
        }else{
            depatids=new ArrayList<>();
            depatids.add(departId);
        }
        Page<Object> result = PageHelper.startPage(page, limit);
        List<UserVo> userVoList = baseBiz.userpage(depatids, jobcode, name,type,groupid);
        return new TableResultResponse(result.getTotal(), userVoList);
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


    @PostMapping(value = "info/resetPassword")
    public ObjectRestResponse resetPassword(String userid){
        if(StringUtils.isEmpty(userid)){
          return ObjectRestResponse.error("请选择用户");
        }
        return baseBiz.restPassword(userid);
    }

    @ApiOperation("获取用户列表")
    @RequestMapping(value = "/getUserpage", method = RequestMethod.GET)
    public TableResultResponse getPage(String name,String loginname,String isDisabled,String departid,String roleName,String roleId,
                                       @RequestParam(name = "limit", defaultValue = "10") int limit,
                                       @RequestParam(name = "page", defaultValue = "1") int page) {

        List<String> eplIds=new ArrayList<String>();
        if ( StringUtils.isNotBlank(name)) {
            Example example = new Example(Employee.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andLike("name", "%" + name + "%");
            criteria.andEqualTo("delFlag", "0");
            example.setOrderByClause("crt_time desc");

            List<Employee> list = employeeBiz.selectByExample(example);

            if ( list.size() > 0 ) {
                list.forEach(epl -> eplIds.add(epl.getId()));
            }else{
                eplIds.add("00");
            }
        }

        List<String> stationIds=new ArrayList<>();
        if(!"".equals(departid)&&departid!=null){
            stationIds=departBiz.selectChildren(departid);
            if(stationIds.size()==0)    return new TableResultResponse(0,stationIds);
        }

        Page<Object> result = PageHelper.startPage(page, limit);
        List<UserVo> userVoList=baseBiz.getUserList(eplIds,loginname,isDisabled,stationIds,roleName,roleId);


        return new TableResultResponse(result.getTotal(), userVoList);
    }

    @ApiOperation("查询用户详细信息")
    @RequestMapping(value = "/userInfo/{id}", method = RequestMethod.GET)
    public ObjectRestResponse getUserInfo(@PathVariable String id) {

        return ObjectRestResponse.ok(baseBiz.getUserInfo(id));
    }

    @RequestMapping(value = "/getUserSimple/{id}", method = RequestMethod.GET)
    public JSONObject getUserSimpleInfo(@PathVariable String id) {

        User user = baseBiz.selectById(id);
        Employee employee = employeeBiz.getEmployById(user.getEmployId());
        if(employee!=null){
            user.setName(employee.getName());
        }
        return user!=null ? JSON.parseObject(JSON.toJSONString(user)) : null;
    }


    @ApiOperation("添加用户")
    @RequestMapping(value = "/userInfo", method = RequestMethod.POST)
    public ObjectRestResponse addUserVO(@RequestBody UserVo userVO){
//        try {
        List<User> userList=baseBiz.getUserName(userVO.getLoginName());
        if(userList.size()>0){
            return ObjectRestResponse.error(30101,"该用户已存在！");
        }
            boolean flag=baseBiz.getUserCnt();
            if ( flag ){
                String userId=baseBiz.addUserVO(userVO);
                String groups=userVO.getGroups();
                String[] group = groups.split(",");
                if(groups!=null&&groups!=""){
                    groupBiz.addGroup(group,userId);
                }
                userVO.setId(userId);
                return ObjectRestResponse.ok(userVO);
            }else {
                return ObjectRestResponse.error("该系统用户数已达上限！");
            }

//        } catch (DuplicateKeyException e) {//捕获违反唯一性约束异常
//            return ObjectRestResponse.error(30101,"该用户已存在！");
//        }

    }

    @ApiOperation("修改用户")
    @RequestMapping(value = "/userInfo/{id}", method = RequestMethod.PUT)
    public ObjectRestResponse updateUser(@PathVariable String id,@RequestBody UserVo userVO){
        baseBiz.updateUser(userVO,id);
//        List<Group> groupList=userVO.getGroup();
        String groups=userVO.getGroups();
        String[] group = groups.split(",");
        if(groups!=null&&groups!=""){
            groupBiz.delUserGroup(id);
            groupBiz.addGroup(group,id);
        }

        userVO.setId(id);

        return ObjectRestResponse.ok(userVO);
    }

    @ApiOperation("删除用户")
    @RequestMapping(value = "/userInfo/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse delUser(@PathVariable String id){
        if(id.indexOf(BaseContextHandler.getUserID())!=-1){
            return ObjectRestResponse.error(30101,"不可删除自己！");
        }
        String[] ids = id.split(",");
        for(String userId:ids){
                baseBiz.delUser(userId);

        }
        return ObjectRestResponse.ok();

    }

    @ApiOperation("查询用户数据权限")
    @RequestMapping(value = "/scpoe", method = RequestMethod.GET)
    public ObjectRestResponse getUserDepart(@RequestParam String userId){
        UserVo userVo=baseBiz.getUserInfo(userId);
        String ifSuperAdmin=userVo.getIsSuperAdmin();
        List<Depart> departs=new ArrayList<>();
        if(ifSuperAdmin.equals("1")) {
            departs = departBiz.allDepartNoUser(Arrays.asList("1,2,3".split(",")),"","");
        }else{
            departs = departBiz.getUserMapDepart(userId);
        }
        List<DepartTree> trees = new ArrayList<>();
        departs.forEach(dictType -> {
            trees.add(new DepartTree(dictType.getId(),dictType.getName(),dictType.getParentId(), dictType.getCode(),dictType.getType()));
        });

        List<DepartTree> departTree= TreeUtil.bulid(trees, "-1", null);

        return ObjectRestResponse.ok(departTree);
    }

    @ApiOperation("用户数据权限添加")
    @RequestMapping(value = "/scpoe/{id}/{departIds}", method = RequestMethod.POST)
    public ObjectRestResponse addUserDepart(@PathVariable String departIds,@PathVariable String id){
        UserVo userVo=baseBiz.getUserInfo(id);
        String ifSuperAdmin=userVo.getIsSuperAdmin();
        if(ifSuperAdmin.equals("1")) {
            List<Depart> departs = departBiz.allDepartNoUser(Arrays.asList("1,2,3".split(",")),"","");
            List<String> stationIds=departs.stream().map(u->u.getId()).collect(Collectors.toList());
            stationIds.add("-1");
            departIds= String.join(",", stationIds);
        }
        departBiz.addUserDepart(departIds,id);

        List<Depart> departs = departBiz.getUserMapDepart(id);
        List<DepartTree> trees = new ArrayList<>();
        departs.forEach(dictType -> {
            trees.add(new DepartTree(dictType.getId(),dictType.getName(),dictType.getParentId(), dictType.getCode(),dictType.getType()));
        });

        List<DepartTree> departTree= TreeUtil.bulid(trees, "-1", null);
        return ObjectRestResponse.ok(departTree);
    }

    @ApiOperation("按照条件获取用户列表")
    @RequestMapping(value = "/getUserList", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse getUserList(String params) {

        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        if ( StringUtils.isNotBlank(params)) {
            criteria.andLike("username", "%"+params+"%");
        }
        criteria.andEqualTo("isDeleted", 0);
        criteria.andEqualTo("isDisabled", 0);

        example.setOrderByClause("CRT_TIME desc");

        List<User> list = baseBiz.selectByExample(example);
        List<UserVo> trees = new ArrayList<>();
        for(User user:list){
            UserVo u=new UserVo();
            u.setId(user.getId());
            u.setUsername(user.getUsername());
            u.setMobilePhone(user.getMobilePhone());

            Depart station=departBiz.selectById(user.getDepartId());
//            if(station.getType().equals("3")){
//                u.setStation(station.getName());
//                Depart area=departBiz.selectById(station.getParentId());
//                u.setArea(area.getName());
//                if(area.getType().equals("2")){
//                    Depart line=departBiz.selectById(area.getParentId());
//                    u.setLine(line.getName());
//                }else{
//                    u.setLine(area.getName());
//                }
//            }else if(station.getType().equals("2")){
//                u.setStation("");
//                Depart area=departBiz.selectById(user.getDepartId());
//                u.setArea(area.getName());
//                if(area.getType().equals("2")){
//                    Depart line=departBiz.selectById(area.getParentId());
//                    u.setLine(line.getName());
//                }else{
//                    u.setLine(area.getName());
//                }
//            }else if(station.getType().equals("1")){
//                u.setStation("");
//                u.setArea("");
//                Depart line=departBiz.selectById(user.getDepartId());
//                u.setLine(line.getName());
//
//            }

            u.setDepart(departBiz.getDepartNameById(user.getDepartId()));


            trees.add(u);
        }

        return ObjectRestResponse.ok(trees);
    }

    @ApiOperation("修改用户状态")
    @RequestMapping(value = "/{id}/{status}", method = RequestMethod.POST)
    public ObjectRestResponse updateStatus(@PathVariable String id,@PathVariable String status){
        baseBiz.updateUserState(id,status);
        return ObjectRestResponse.ok();
    }

    @ApiOperation("重置密码")
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public ObjectRestResponse resetPassword(String id, String password){
        baseBiz.resetPassword(id,password);
        return ObjectRestResponse.ok();
    }

    @Autowired
    private BasePointBiz basePointBiz;

    @IgnoreClientToken
    @IgnoreUserToken
    @ApiOperation("获取用户有权限的部门")
    @RequestMapping(value = "/dataDepartMap", method = RequestMethod.GET)
    public List<Map<String, String>> dataDepartMap(@RequestParam String userId){
        String ifSuperAdmin=BaseContextHandler.getIsSuperAdmin();
        List<Depart> departs =null;
        if("".equals(userId)){
            departs = departBiz.selectListAll();
        }else if(ifSuperAdmin.equals("1")){
            departs = departBiz.allDepartNoUser(Arrays.asList("1,2,3".split(",")),"","");
        }else{
            departs = departBiz.getUserMapDepart(userId);
        }

        List<Map<String, String>> list=new ArrayList<>();
        for (Depart d:departs){
            Map<String, String> map=new HashMap<>();
            map.put("id",d.getId());
            map.put("parentId",d.getParentId());
            map.put("type",d.getType());
            map.put("name",d.getName());
            list.add(map);
        }
        return list;
    }

//    @IgnoreClientToken
    @ApiOperation("获取用户有权限的车站")
    @RequestMapping(value = "/dataStation", method = RequestMethod.GET)
    public List<Map<String, String>> getStation(@RequestParam String userId){
        String ifSuperAdmin=BaseContextHandler.getIsSuperAdmin();

        List<Depart> departs =null;
        if("".equals(userId)){
            departs = departBiz.selectListAll();
        }else if(ifSuperAdmin.equals("1")){
            departs = departBiz.allDepartNoUser(Arrays.asList("1,2,3".split(",")),"","");
        }else{
            departs = departBiz.getUserMapDepart(userId);
        }
        List<Map<String, String>> list=new ArrayList<>();
        for (Depart d:departs){
            if(d.getType().equals("3")){
            Map<String, String> map=new HashMap<>();
            map.put("id",d.getId());
            map.put("type",d.getType());
            map.put("name",d.getName());
            list.add(map);}
        }
        return list;
    }

    @IgnoreClientToken
    @IgnoreUserToken
    @ApiOperation("获取用户有权限的安检点")
    @RequestMapping(value = "/dataPoint", method = RequestMethod.GET)
    public List<String> getPoint(@RequestParam String userId){
        String ifSuperAdmin=BaseContextHandler.getIsSuperAdmin();

        List<Depart> departs =null;
        if("".equals(userId)){
            departs = departBiz.selectListAll();
        }else if(ifSuperAdmin.equals("1")){
            departs = departBiz.allDepartNoUser(Arrays.asList("1,2,3".split(",")),"","");
        }else{
            departs = departBiz.getUserMapDepart(userId);
        }
        List<String> stationIds=departs.stream().filter(u->u.getType().equals("3")).map(Depart::getId).collect(Collectors.toList());
        List<Map<String, Object>> points=basePointBiz.selectByDepartids("", "","", stationIds);
        List<String> pointIds=points.stream().map(u->u.get("id").toString()).collect(Collectors.toList());
        return pointIds;
    }

//    @IgnoreClientToken
//    @IgnoreUserToken
    @ApiOperation("获取用户有权限的部门-向上")
    @RequestMapping(value = "/dataLocation", method = RequestMethod.GET)
    public String dataLocation(@RequestParam String deptId){
        return departBiz.getDepartNameById(deptId);
    }

}
