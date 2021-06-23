package com.ts.spm.bizs.rest.admin;

import com.alibaba.fastjson.JSON;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.TreeUtil;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.admin.DepartBiz;
import com.ts.spm.bizs.biz.admin.PositionBiz;
import com.ts.spm.bizs.biz.admin.UserBiz;
import com.ts.spm.bizs.entity.person.AjyInfo;
import com.ts.spm.bizs.util.UserConstant;
import com.ts.spm.bizs.entity.admin.Depart;
import com.ts.spm.bizs.entity.admin.User;
import com.ts.spm.bizs.service.LicenceService;
import com.ts.spm.bizs.util.LicencePara;
import com.ts.spm.bizs.vo.admin.DepartTree;
import com.ts.spm.bizs.vo.admin.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ace
 */
@RestController
@RequestMapping("depart")
@CheckClientToken
@CheckUserToken
@Api(tags = "部门管理")
public class DepartController extends BaseController<DepartBiz, Depart, String> {
    @Autowired
    private UserBiz userBiz;
    @Autowired
    private DepartBiz departBiz;
    @Autowired
    private PositionBiz positionBiz;

    @Autowired
    private LicenceService licenceService;

    @IgnoreUserToken
    @ApiOperation("根据站名查询编号")
    public AjyInfo getDepartIdByName(@RequestParam String name) {
        AjyInfo ajyInfo = departBiz.getDepartIdByName(name);
        return ajyInfo;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("新增单个对象")
    public ObjectRestResponse<Depart> add(@RequestBody Depart entity) {
        Depart depart = baseBiz.selectById(entity.getParentId());
        if (depart.getType() == null) {
            return ObjectRestResponse.error("请完善部门信息");
        }
        if (UserConstant.depart_type_AQBHJG.equals(depart.getNameOfOffice())) {
            if (UserConstant.org_depart_Police.equals(depart.getType())) {
                return ObjectRestResponse.error("不可再添加子集");
            }
        } else {
            if (Integer.valueOf(depart.getType()) > 3) {
                return ObjectRestResponse.error("不可再添加子集");
            }
        }
        entity.setId(UUIDUtils.generateUuid());
        baseBiz.insertSelective(entity);
        return new ObjectRestResponse<Depart>().data(entity);
    }


    @ApiOperation("获取部门树")
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public List<DepartTree> getTree() {
        List<Depart> departs = baseBiz.selectListAll();
        List<DepartTree> trees = new ArrayList<>();
        departs.forEach(dictType -> {
            trees.add(new DepartTree(dictType.getId(), dictType.getParentId(), dictType.getName(), dictType.getCode()));
        });
        return TreeUtil.bulid(trees, "-1", null);
    }

    @ApiOperation("根据类型获取部门树")
    @RequestMapping(value = "/treeByType", method = RequestMethod.GET)
    public List<DepartTree> treeByType(Integer type) {
        Depart depart = new Depart();
        if (type == 1) {
            depart.setNameOfOffice(UserConstant.depart_type_JYZGJG);

        } else {
            depart.setNameOfOffice(UserConstant.depart_type_AQBHJG);

        }
        List<Depart> departs = baseBiz.selectList(depart);
        List<DepartTree> trees = new ArrayList<>();
        departs.forEach(dictType -> {
            trees.add(new DepartTree(dictType.getId(), dictType.getParentId(), dictType.getName(), dictType.getCode()));
        });
        return TreeUtil.bulid(trees, "-1", null);
    }


    @ApiOperation("获取部门关联用户")
    @RequestMapping(value = "user", method = RequestMethod.GET)
    public TableResultResponse<User> getDepartUsers(String departId, String userName) {
        return this.baseBiz.getDepartUsers(departId, userName);
    }

    @ApiOperation("部门添加用户")
    @RequestMapping(value = "user", method = RequestMethod.POST)
    public ObjectRestResponse<Boolean> addDepartUser(String departId, String userIds) {
        this.baseBiz.addDepartUser(departId, userIds);
        return new ObjectRestResponse<>().data(true);
    }

    @ApiOperation("部门移除用户")
    @RequestMapping(value = "user", method = RequestMethod.DELETE)
    public ObjectRestResponse<Boolean> delDepartUser(String departId, String userId) {
        this.baseBiz.delDepartUser(departId, userId);
        return new ObjectRestResponse<>().data(true);
    }

    @ApiOperation("获取部门信息")
    @RequestMapping(value = "getByPK/{id}", method = RequestMethod.GET)
    public Map<String, String> getDepart(@PathVariable String id) {
        return this.baseBiz.getDeparts(id);
    }


    /**
     * 当前用户下部门列表
     *
     * @param
     * @return
     */
    @GetMapping(value = "user/departList")
    public List<Map<String, Object>> departList(String type) {
        List<Map<String, Object>> depatids = new ArrayList<>();
        String userid = BaseContextHandler.getUserID();
        User user = userBiz.selectById(userid);
        if ("1".equals(user.getIsSuperAdmin())) {
            Map<String, Object> rootmap = new HashMap<>();
//            rootmap.put("id","root");
//            rootmap.put("name","特殊机构");
//            depatids.add(rootmap);
            Depart depart = new Depart();
            if (StringUtils.isEmpty(type)) {
                depart.setNameOfOffice(UserConstant.depart_type_JYZGJG);
            } else {
                depart.setNameOfOffice(UserConstant.depart_type_AQBHJG);
            }
            List<Depart> departList = baseBiz.selectList(depart);
            for (Depart dp : departList) {
                if ("-1".equals(dp.getParentId())) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", dp.getId());
                    map.put("name", dp.getName());
                    Depart depart2 = new Depart();
                    depart2.setParentId(dp.getId());
                    List<Depart> dpList = baseBiz.selectList(depart2);
                    if (dpList != null && dpList.size() > 0) {
                        depatids = departids(dpList, depatids);
                    }
                    depatids.add(map);
                }
            }

        } else {
            Map usermap = JSON.parseObject(user.getDepartId(), Map.class);
            String departId = usermap.get("id").toString();
            Depart dp = baseBiz.selectById(departId);
            Map<String, Object> map = new HashMap<>();
            map.put("id", dp.getId());
            map.put("name", dp.getName());
            depatids.add(map);
            Depart depart = new Depart();
            depart.setParentId(departId);
            List<Depart> departs = baseBiz.selectList(depart);
            if (departs != null && departs.size() > 0) {
                depatids = departids(departs, depatids);
            }
        }

        return depatids;
    }

    /**
     * 当前用户下部门列表
     *
     * @param
     * @return
     */
    @GetMapping(value = "app/user/departList")
    public ObjectRestResponse appDepartList() {
        String userid = BaseContextHandler.getUserID();
        User user = userBiz.selectById(userid);
        Map usermap = JSON.parseObject(user.getDepartId(), Map.class);
        String departId = usermap.get("id").toString();
        List<Map<String, Object>> depatids = new ArrayList<>();
        Depart dp = baseBiz.selectById(departId);
        Map<String, Object> map = new HashMap<>();
        map.put("id", dp.getId());
        map.put("name", dp.getName());
        depatids.add(map);
        Depart depart = new Depart();
        depart.setParentId(departId);
        List<Depart> departs = baseBiz.selectList(depart);
        if (departs != null && departs.size() > 0) {
            depatids = departids(departs, depatids);
        }
        return ObjectRestResponse.ok(depatids);
    }


    public List<Map<String, Object>> departids(List<Depart> list, List<Map<String, Object>> depatids) {
        List<Depart> sondepat = new ArrayList<>();
        for (Depart dp : list) {
            Map<String, Object> map = new HashMap<>();
            Depart depart2 = new Depart();
            depart2.setParentId(dp.getId());
            List<Depart> departList = baseBiz.selectList(depart2);
            if (departList != null && departList.size() > 0) {
                sondepat.add(dp);
                map.put("id", dp.getId());
                map.put("name", dp.getName());
                depatids.add(map);
            } else {
                map.put("id", dp.getId());
                map.put("name", dp.getName());
                depatids.add(map);
            }
        }
        for (Depart dps : sondepat) {
            Depart depart2 = new Depart();
            depart2.setParentId(dps.getId());
            List<Depart> departList = baseBiz.selectList(depart2);
            departids(departList, depatids);
        }
        return depatids;
    }

    /**
     * 通过userid 获取部门树形结构
     *
     * @param
     * @return
     */
    @GetMapping(value = "user/departTree")
    public List<Map<String, Object>> departTree(String type) {
        String userid = BaseContextHandler.getUserID();
        List<Map<String, Object>> depatids = new ArrayList<>();
        User user = userBiz.selectById(userid);
        if ("1".equals(user.getIsSuperAdmin())) {
            Depart depart = new Depart();
            if (StringUtils.isEmpty(type)) {
                depart.setNameOfOffice(UserConstant.depart_type_JYZGJG);
            }
            if ("2".equals(type)) {
                depart.setNameOfOffice(UserConstant.depart_type_AQBHJG);
            }
            List<Depart> departList = baseBiz.selectList(depart);
            for (Depart dp : departList) {
                if ("-1".equals(dp.getParentId())) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", dp.getId());
                    map.put("label", dp.getName());
                    map.put("code", dp.getNameOfOffice());
                    map.put("type", dp.getType());
                    Depart depart2 = new Depart();
                    depart2.setParentId(dp.getId());
                    List<Depart> dpList = baseBiz.selectList(depart2);
                    if (dpList != null && dpList.size() > 0) {
                        map.put("child", labeldepartidTree(dpList));
                    }
                    depatids.add(map);
                }
            }
        } else {
            Map usermap = JSON.parseObject(user.getDepartId(), Map.class);
            String departId = usermap.get("id").toString();
            Map<String, Object> map = new HashMap<>();
            Depart dp = baseBiz.selectById(departId);
            map.put("id", dp.getId());
            map.put("label", dp.getName());
            map.put("code", dp.getNameOfOffice());
            map.put("type", dp.getType());
            Depart depart = new Depart();
            depart.setParentId(departId);
            List<Depart> departList = baseBiz.selectList(depart);
            if (departList != null && departList.size() > 0) {
                map.put("child", labeldepartidTree(departList));
            }
            depatids.add(map);
        }
        return depatids;
    }

    public List<Map<String, Object>> labeldepartidTree(List<Depart> list) {
        List<Map<String, Object>> depatids = new ArrayList<>();
        for (Depart dp : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", dp.getId());
            map.put("label", dp.getName());
            map.put("code", dp.getNameOfOffice());
            map.put("type", dp.getType());
            Depart depart2 = new Depart();
            depart2.setParentId(dp.getId());
            List<Depart> departList = baseBiz.selectList(depart2);
            if (departList != null && departList.size() > 0) {
                map.put("child", labeldepartidTree(departList));
            }
            depatids.add(map);
        }
        return depatids;
    }

    /**
     * 通过userid 获取部门树形结构
     *
     * @param
     * @return
     */
    @GetMapping(value = "app/user/departTree")
    public ObjectRestResponse appDepartTree() {
        String userid = BaseContextHandler.getUserID();
        List<Map<String, Object>> depatids = new ArrayList<>();
        User user = userBiz.selectById(userid);
        Map usermap = JSON.parseObject(user.getDepartId(), Map.class);
        String departId = usermap.get("id").toString();
        Map<String, Object> map = new HashMap<>();
        Depart dp = baseBiz.selectById(departId);
        map.put("id", dp.getId());
        map.put("name", dp.getName());
        Depart depart = new Depart();
        depart.setParentId(departId);
        List<Depart> departList = baseBiz.selectList(depart);
        if (departList != null && departList.size() > 0) {
            map.put("child", departidTree(departList));
        }
        depatids.add(map);
        return ObjectRestResponse.ok(depatids);
    }

    public List<Map<String, Object>> departidTree(List<Depart> list) {
        List<Map<String, Object>> depatids = new ArrayList<>();
        for (Depart dp : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", dp.getId());
            map.put("name", dp.getName());
            Depart depart2 = new Depart();
            depart2.setParentId(dp.getId());
            List<Depart> departList = baseBiz.selectList(depart2);
            if (departList != null && departList.size() > 0) {
                map.put("child", departidTree(departList));
            }
            depatids.add(map);
        }
        return depatids;
    }

    /**
     * app(隐患上报)对上用户
     *
     * @return
     */
    @GetMapping(value = "app/user/upDepartUserList")
    public ObjectRestResponse appUpDepartUserList(String departId) {
        String userid = BaseContextHandler.getUserID();
        //String userid="251592e82f2248828247d92c6001597c";
        String dpid = null;
//        List<String> positionids = new ArrayList<>();
        User user = userBiz.selectById(userid);
        if ("1".equals(user.getIsSuperAdmin())) {
            List<User> users = userBiz.selectListAll();
            return ObjectRestResponse.ok(users);
        } else {
            if (StringUtils.isEmpty(departId)) {
                Map usermap = JSON.parseObject(user.getDepartId(), Map.class);
//                Position positionUser = positionBiz.positionuser(userid);

                String jobCateGory1 = null;
                //判断当前用户是否有岗位
                if (StringUtils.isNotEmpty(user.getAttr3())) {
                    //判断当前用户机构是学校
                    if (UserConstant.org_depart_school.equals(usermap.get("type").toString()) || UserConstant.org_depart_municipalSchools.equals(usermap.get("type").toString())) {
                        //如果是学校判断是否是总务主任
                        jobCateGory1 = UserConstant.comm_job_category_ZWZR;
                        //当前机构
                        dpid = usermap.get("id").toString();
                    } else {
                        //如果不是学校传安全负责人
                        jobCateGory1 = UserConstant.comm_job_category_JYJAQK;
                        //上级机构
                        Depart depart = baseBiz.selectById(usermap.get("id").toString());
                        dpid = depart.getId();
                    }
                } else {
                    jobCateGory1 = UserConstant.comm_job_category_ZWZR;
                    dpid = usermap.get("id").toString();
                }


                List<UserVo> userVos = positionBiz.PositionUsers(dpid, null, null);
                return ObjectRestResponse.ok(userVos);
            } else {
                Depart depart = baseBiz.selectById(departId);
                if (depart != null) {
                    if (UserConstant.depart_type_JYZGJG.equals(depart.getNameOfOffice())) {
                        if (!"-1".equals(depart.getParentId())) {
                            List<UserVo> userVos = positionBiz.PositionUsers(depart.getParentId(), null, null);
                            return ObjectRestResponse.ok(userVos);
                        } else {
                            return ObjectRestResponse.error("机构有误");
                        }
                    } else {
                        return ObjectRestResponse.error("组织机构有误");
                    }
                } else {
                    return ObjectRestResponse.error("组织机构不存在");
                }

            }

        }
    }

    /**
     * 对内(隐患上报)的向上人员
     *
     * @return
     */
    @GetMapping(value = "user/upDepartUserList")
    public List<Map<String, Object>> upDepartUserList(String departId) {
        List<Map<String, Object>> userVoList = new ArrayList<>();
        String userid = BaseContextHandler.getUserID();
        //String userid="251592e82f2248828247d92c6001597c";
        String dpid = null;
//        List<String> positionids = new ArrayList<>();
        User user = userBiz.selectById(userid);

        if (StringUtils.isEmpty(departId)) {
            Map usermap = JSON.parseObject(user.getDepartId(), Map.class);
//                Position positionUser = positionBiz.positionuser(userid);

            String jobCateGory1 = null;
            //判断当前用户是否有岗位
            if (StringUtils.isNotEmpty(user.getAttr3())) {
                //判断当前用户机构是学校
                if (UserConstant.org_depart_school.equals(usermap.get("type").toString()) || UserConstant.org_depart_municipalSchools.equals(usermap.get("type").toString())) {
                    //如果是学校判断是否是总务主任
                    jobCateGory1 = UserConstant.comm_job_category_ZWZR;
                    //当前机构
                    dpid = usermap.get("id").toString();
                } else {
                    //如果不是学校传安全负责人
                    jobCateGory1 = UserConstant.comm_job_category_JYJAQK;
                    //上级机构
                    Depart depart = baseBiz.selectById(usermap.get("id").toString());
                    dpid = depart.getParentId();
                }
            } else {
                jobCateGory1 = UserConstant.comm_job_category_ZWZR;
                dpid = usermap.get("id").toString();
            }


            List<UserVo> userVos = positionBiz.PositionUsers(dpid, jobCateGory1, null);
            for (UserVo userVo : userVos) {
                Map<String, Object> uservoMap = new HashMap<>();
                uservoMap.put("jobCategoryCode", userVo.getJobCategoryCode());
                uservoMap.put("id", userVo.getId());
                uservoMap.put("name", userVo.getName());
                uservoMap.put("pname", userVo.getPname());
                userVoList.add(uservoMap);
            }

        } else {
            Depart depart = baseBiz.selectById(departId);
            if (depart != null) {
                if (UserConstant.depart_type_JYZGJG.equals(depart.getNameOfOffice())) {
                    if (!"-1".equals(depart.getParentId())) {
                        List<UserVo> userVos = positionBiz.PositionUsers(depart.getParentId(), UserConstant.comm_job_category_JYJAQK, null);
                        for (UserVo userVo : userVos) {
                            Map<String, Object> uservoMap = new HashMap<>();
                            uservoMap.put("jobCategoryCode", userVo.getJobCategoryCode());
                            uservoMap.put("id", userVo.getId());
                            uservoMap.put("name", userVo.getName());
                            uservoMap.put("pname", userVo.getPname());
                            userVoList.add(uservoMap);
                        }
                    }
                }
            }
        }
        return userVoList;

    }

    /**
     * app发送邮件人物
     *
     * @return
     */
    @GetMapping(value = "app/user/downDepartUserList")
    public ObjectRestResponse appDownDepartUserList(String type, String depatId, String jobCategoryCode, String username) {
        String userid = BaseContextHandler.getUserID();
        List<String> departIds = new ArrayList<>();
        User user = userBiz.selectById(userid);
        if ("1".equals(user.getIsSuperAdmin())) {
            if (StringUtils.isEmpty(depatId)) {
                User user1 = new User();
                user1.setStatus("1");
                user1.setIsDeleted("0");
                List<User> users = userBiz.selectList(user1);
                return ObjectRestResponse.ok(users);
            } else {
                departIds.add(depatId);
                if (StringUtils.isNotEmpty(type)) {
                    List<UserVo> userVos = userBiz.userbyjobcode(departIds, UserConstant.comm_job_category_XZ, UserConstant.comm_job_category_XXGLY, UserConstant.comm_job_category_JYJGLY, UserConstant.comm_job_category_ZWZR, username);
                    return ObjectRestResponse.ok(userVos);
                } else {
                    List<UserVo> userVos = userBiz.userbyjobcode(departIds, jobCategoryCode, null, null, null, username);
                    return ObjectRestResponse.ok(userVos);
                }

            }
        } else {
            Map usermap = JSON.parseObject(user.getDepartId(), Map.class);
            //判断是否是学校
            if (UserConstant.org_depart_school.equals(usermap.get("type").toString()) || UserConstant.org_depart_municipalSchools.equals(usermap.get("type").toString())) {
                //是否是校长
                if (user.getAttr3().contains(UserConstant.comm_job_category_XZ)) {
                    Depart depart = baseBiz.selectById(usermap.get("id").toString());
                    departIds.add(depart.getParentId());
                }
            }
            if (StringUtils.isEmpty(depatId)) {
                String departId = usermap.get("id").toString();
                List<Map<String, Object>> depatids = new ArrayList<>();
                Depart dp = baseBiz.selectById(departId);
                Map<String, Object> map = new HashMap<>();
                map.put("id", dp.getId());
                map.put("name", dp.getName());
                depatids.add(map);
                Depart depart = new Depart();
                depart.setParentId(departId);
                List<Depart> departs = baseBiz.selectList(depart);
                if (departs != null && departs.size() > 0) {
                    depatids = departids(departs, depatids);
                }
                for (int i = 0; i < depatids.size(); i++) {
                    departIds.add(depatids.get(i).get("id").toString());
                }
            } else {
                departIds.add(depatId);
            }

            if (StringUtils.isNotEmpty(type)) {
                List<UserVo> userVos = userBiz.userbyjobcode(departIds, UserConstant.comm_job_category_XZ, UserConstant.comm_job_category_XXGLY, UserConstant.comm_job_category_JYJGLY, UserConstant.comm_job_category_ZWZR, username);
                return ObjectRestResponse.ok(userVos);
            } else {
                List<UserVo> userVos = userBiz.userbyjobcode(departIds, jobCategoryCode, null, null, null, username);
                return ObjectRestResponse.ok(userVos);
            }
        }

    }

    /**
     * 对内发送邮件人物
     *
     * @return
     */
    @GetMapping(value = "user/downDepartUserList")
    public List<Map<String, Object>> downDepartUserList(String type, String depatId, String jobCategoryCode, String username) {
        List<Map<String, Object>> userVoList = new ArrayList<>();
        String userid = BaseContextHandler.getUserID();
        List<String> departIds = new ArrayList<>();
        User user = userBiz.selectById(userid);
        Map usermap = JSON.parseObject(user.getDepartId(), Map.class);
        if (UserConstant.org_depart_school.equals(usermap.get("type").toString()) || UserConstant.org_depart_municipalSchools.equals(usermap.get("type").toString())) {
            if (user.getAttr3().contains(UserConstant.comm_job_category_XZ)) {
                Depart depart = baseBiz.selectById(usermap.get("id").toString());
                departIds.add(depart.getParentId());
            }
        }
        String departId = usermap.get("id").toString();
        List<Map<String, Object>> depatids = new ArrayList<>();
        Depart dp = baseBiz.selectById(departId);
        Map<String, Object> map = new HashMap<>();
        map.put("id", dp.getId());
        map.put("name", dp.getName());
        depatids.add(map);
        Depart depart = new Depart();
        depart.setParentId(departId);
        List<Depart> departs = baseBiz.selectList(depart);
        if (departs != null && departs.size() > 0) {
            depatids = departids(departs, depatids);
        }
        for (int i = 0; i < depatids.size(); i++) {
            departIds.add(depatids.get(i).get("id").toString());
        }
        if (StringUtils.isNotEmpty(type)) {
            List<UserVo> userVos = userBiz.userbyjobcode(departIds, UserConstant.comm_job_category_XZ, UserConstant.comm_job_category_XXGLY, UserConstant.comm_job_category_JYJGLY, UserConstant.comm_job_category_ZWZR, username);
            for (UserVo userVo : userVos) {
                Map<String, Object> userVoMap = new HashMap<>();
                userVoMap.put("jobCategoryCode", userVo.getJobCategoryCode());
                userVoMap.put("id", userVo.getId());
                userVoMap.put("name", userVo.getName());
                userVoMap.put("pname", userVo.getPname());
                userVoList.add(userVoMap);
            }
            return userVoList;
        } else {
            List<UserVo> userVos = userBiz.userbyjobcode(departIds, jobCategoryCode, null, null, null, username);
            for (UserVo userVo : userVos) {
                Map<String, Object> userVoMap = new HashMap<>();
                userVoMap.put("jobCategoryCode", userVo.getJobCategoryCode());
                userVoMap.put("id", userVo.getId());
                userVoMap.put("name", userVo.getName());
                userVoMap.put("pname", userVo.getPname());
                userVoList.add(userVoMap);
            }
            return userVoList;
        }

    }


    /**
     * 部门信息（模块调用）
     *
     * @param departId
     * @return
     */
    @GetMapping(value = "info/departInfo")
    @IgnoreUserToken
    public Map<String, Object> departInfo(String departId) {
        Map<String, Object> departMap = new HashMap<>();
        Depart depart = baseBiz.selectById(departId);
        if (depart == null) {
            departMap.put("status", "-1");
        } else {
            departMap.put("status", "1");
            departMap.put("name", depart.getName());
            departMap.put("parentId", depart.getParentId());
            departMap.put("id", depart.getId());
            departMap.put("nameOfOffice", depart.getNameOfOffice());
            departMap.put("policeId", depart.getPoliceId());
            departMap.put("x", depart.getX());
            departMap.put("y", depart.getY());
            departMap.put("type", depart.getType());
        }
        return departMap;
    }

    /**
     * 获取公安部门下所有学校
     *
     * @Param:departs 公安机构部门ids
     * @Param:departs 教育机构类型
     * @Author: Yongkui Hu
     * @Date: 2019/5/31 0031
     * @return:
     */
    @GetMapping(value = "info/organListByPolice")
    @IgnoreUserToken
    public List<Map<String, Object>> organListByPolice(String departIds, String types) {
        List<String> ids = null;
        if (StringUtils.isNotEmpty(departIds)) {
            ids = Arrays.asList(departIds.split(","));
        }
        List<String> typesArr = null;
        if (StringUtils.isNotEmpty(types)) {
            typesArr = Arrays.asList(types.split(","));
        }
        List<Depart> list = departBiz.organListByPolice(ids, typesArr);
        List<Map<String, Object>> res = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Depart depart = list.get(i);
            Map<String, Object> map = new HashMap<>();
            map.put("id", depart.getId());
            map.put("name", depart.getName());
            map.put("x", depart.getX());
            map.put("y", depart.getY());
            res.add(map);
        }
        return res;
    }

    @GetMapping(value = "info/departNameLike")
    @IgnoreUserToken
    public String departNameLike(String name) {
        Example example = new Example(Depart.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(name)) {
            criteria.andLike("name", "%" + name + "%");
        }
        List<Depart> departs = baseBiz.selectByExample(example);
        String sql = "";
        if (departs != null && departs.size() > 0) {
            for (int i = 0; i < departs.size(); i++) {
                if (i == 0) {
                    sql = "and (depart_id='" + departs.get(i).getId() + "'";
                } else {
                    sql = sql + "or depart_id='" + departs.get(i).getId() + "'";
                }
            }
            sql = sql + ")";
        }
        return sql;
    }

    /**
     * 通过名称模糊查询获取对应的部门IDS
     *
     * @param departName
     * @return
     */
    @GetMapping(value = "info/departIDS")
    @IgnoreUserToken
    public List<String> departIDS(String departName) {
        Example example = new Example(Depart.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(departName)) {
            criteria.andLike("name", "%" + departName + "%");
        }
        List<Depart> departs = baseBiz.selectByExample(example);
        List<String> departIds = departs.stream().map(Depart::getId).collect(Collectors.toList());
        return departIds;
    }

    /**
     * 当前用户下添加对应
     *
     * @return
     */
    @GetMapping(value = "user/schoolByUser")
    public List<Map<String, Object>> schoolByUser() {
        String userid = BaseContextHandler.getUserID();
        List<Map<String, Object>> schoolList = new ArrayList<>();
        User user = userBiz.selectById(userid);
        if ("1".equals(user.getIsSuperAdmin())) {
            Example example = new Example(Depart.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("nameOfOffice", UserConstant.depart_type_JYZGJG);
            criteria.andGreaterThanOrEqualTo("type", UserConstant.org_depart_school);
            List<Depart> departs = baseBiz.selectByExample(example);
            for (Depart depart : departs) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", depart.getId());
                map.put("label", depart.getName());
                schoolList.add(map);
            }
            return schoolList;
        } else {
            Map usermap = JSON.parseObject(user.getDepartId(), Map.class);
            String departId = usermap.get("id").toString();
            Depart userDepart = baseBiz.selectById(departId);
            Depart depart = new Depart();
            depart.setParentId(departId);
            List<Depart> departs = baseBiz.selectList(depart);

            Map<String, Object> map = new HashMap<>();
            if (departs != null && departs.size() > 0) {
                return shcoolDepatList(departs, schoolList);
            } else {
                if (UserConstant.org_depart_school.equals(userDepart.getType()) || UserConstant.org_depart_municipalSchools.equals(userDepart.getType())) {
                    map.put("id", userDepart.getId());
                    map.put("label", userDepart.getName());
                    schoolList.add(map);
                }
            }
            return schoolList;
        }
    }

    public List<Map<String, Object>> shcoolDepatList(List<Depart> list, List<Map<String, Object>> depatids) {
        List<Depart> sondepat = new ArrayList<>();
        for (Depart dp : list) {
            Map<String, Object> map = new HashMap<>();
            Depart depart2 = new Depart();
            depart2.setParentId(dp.getId());
            List<Depart> departList = baseBiz.selectList(depart2);
            if (departList != null && departList.size() > 0) {
                sondepat.add(dp);
                if (UserConstant.org_depart_school.equals(dp.getType()) || UserConstant.org_depart_municipalSchools.equals(dp.getType())) {
                    map.put("id", dp.getId());
                    map.put("label", dp.getName());
                    depatids.add(map);
                }
            } else {
                if (UserConstant.org_depart_school.equals(dp.getType()) || UserConstant.org_depart_municipalSchools.equals(dp.getType())) {
                    map.put("id", dp.getId());
                    map.put("label", dp.getName());
                    depatids.add(map);
                }
            }
        }
        for (Depart dps : sondepat) {
            Depart depart2 = new Depart();
            depart2.setParentId(dps.getId());
            List<Depart> departList = baseBiz.selectList(depart2);
            shcoolDepatList(departList, depatids);
        }
        return depatids;
    }

    /**
     * 通过部门集合查询所有用户
     *
     * @param departIds
     * @return
     */
    @GetMapping(value = "user/usersByDeparts")
    @IgnoreUserToken
    public ObjectRestResponse usersByDeparts(String departIds, String jobCode, String name) {
        if (StringUtils.isEmpty(departIds)) {
            return ObjectRestResponse.error("机构ID不能为空");
        }
        if ("root".equals(departIds)) {
            return ObjectRestResponse.error("您没有资源权限");
        }
        List<String> departIdList = Arrays.asList(departIds.split(","));
        List<UserVo> userList = baseBiz.usersByDepart(departIdList, jobCode, null);
        return ObjectRestResponse.ok(userList);
    }

    /**
     * （内部）通过部门集合查询所有用户
     *
     * @param departIds
     * @return
     */
    @GetMapping(value = "user/insideUsersByDeparts")
    @IgnoreUserToken
    public List<Map<String, Object>> insideUsersByDeparts(String departIds, String jobCode) {
        List<Map<String, Object>> userMapList = new ArrayList<>();
        if (StringUtils.isEmpty(departIds)) {
            Map<String, Object> map = new HashMap<>();
            map.put("state", -1);
            userMapList.add(map);
            return userMapList;
        }
        List<String> departIdList = Arrays.asList(departIds.split(","));
        List<UserVo> userList = baseBiz.usersByDepart(departIdList, jobCode, null);
        if (userList != null && userList.size() > 0) {
            for (UserVo u : userList) {
                Map<String, Object> map = new HashMap<>();
                map.put("state", 1);
                map.put("id", u.getId());
                map.put("name", u.getName());
                userMapList.add(map);
            }
        }

        return userMapList;
    }

    /**
     * 部门修改添加默认岗位和权限
     *
     * @param depart
     * @return
     */
    @PostMapping(value = "/info/updatDeart")
    public ObjectRestResponse updatDepart(@RequestBody Depart depart) {
        String userid = BaseContextHandler.getUserID();
        String tenantID = BaseContextHandler.getTenantID();
        if (StringUtils.isEmpty(depart.getId())) {
            return ObjectRestResponse.error("组织机构id不能为空");
        }
        if (StringUtils.isEmpty(depart.getName())) {
            return ObjectRestResponse.error("机构名称不能为空");
        }
        if (depart.getX() == null) {
            return ObjectRestResponse.error("经度不能为空");
        }
        if (depart.getY() == null) {
            return ObjectRestResponse.error("纬度不能为空");
        }
        if (StringUtils.isEmpty(depart.getType())) {
            return ObjectRestResponse.error("部门类型不能为空");
        }
//        if(StringUtils.isEmpty(depart.getAddress())){
//            return ObjectRestResponse.error("地址不能为空");
//        }
        if (StringUtils.isEmpty(depart.getNameOfOffice())) {
            return ObjectRestResponse.error("办别名称不能为空");
        }
        if (StringUtils.isEmpty(depart.getCode())) {
            return ObjectRestResponse.error("编码不能为空");
        }
        if (UserConstant.depart_type_JYZGJG.equals(depart.getNameOfOffice())) {
            if (StringUtils.isEmpty(depart.getPoliceId())) {
                return ObjectRestResponse.error("警务id不能为空");
            }
        }
        if (StringUtils.isEmpty(depart.getCode())) {
            return ObjectRestResponse.error("机构code不能为空");
        }
        if (StringUtils.isEmpty(depart.getParentId())) {
            return ObjectRestResponse.error("父类ID不能为空");
        }
        return baseBiz.upDepart(depart, userid, tenantID);
    }

    /**
     * 查询安保机构
     *
     * @return
     */
    @GetMapping(value = "policDepatList")
    public ObjectRestResponse policDepatList() {
        Depart depart = new Depart();
        depart.setType(UserConstant.org_depart_Police);
        List<Depart> departs = baseBiz.selectList(depart);
        if (departs != null && departs.size() > 0) {
            return ObjectRestResponse.ok(departs);
        } else {
            return ObjectRestResponse.error("还未录入安保机构");
        }
    }

    /**
     * 通过点击的组织id
     *
     * @param departID
     * @return
     */
    @GetMapping(value = "info/childDepart")
    public List<Map<String, Object>> childDepart(String departID) {
        List<Map<String, Object>> depatids = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        Depart dp = baseBiz.selectById(departID);
        map.put("id", dp.getId());
        map.put("name", dp.getName());
        depatids.add(map);
        Depart depart = new Depart();
        depart.setParentId(departID);
        List<Depart> departs = baseBiz.selectList(depart);
        if (departs != null && departs.size() > 0) {
            depatids = departids(departs, depatids);
        }
        return depatids;
    }

    /**
     * 获取上级所有教育局
     *
     * @param deptId
     * @return
     */
    @GetMapping(value = "info/parentAllDeparts")
    public List<Map<String, String>> parentAllDeparts(String deptId) {
        List<Map<String, String>> res = new ArrayList<>();
        List<Depart> list = baseBiz.parentAllDeparts(deptId);
        for (int i = 0; i < list.size(); i++) {
            Depart depart = list.get(i);
            String nameOfOffice = depart.getNameOfOffice();
            if (!"JYZGJG".equals(nameOfOffice)) {
                continue;
            }
            int inx = 5;
            String type = depart.getType();
            if (null != type && StringUtils.isNotEmpty(type)) {
                inx = Integer.valueOf(type).intValue();
            }
            Map<String, String> map = new HashMap<>();
            if (inx <= 3) {
                map.put("id", depart.getId());
                map.put("name", depart.getName());
            }
            res.add(map);
        }
        return res;
    }

    /**
     * 获取下级所有学校
     *
     * @param deptId
     * @return
     */
    @GetMapping(value = "info/childAllDeparts")
    public List<Map<String, String>> childAllDeparts(String deptId) {
        List<Map<String, String>> res = new ArrayList<>();
        List<Depart> list = baseBiz.parentAllDeparts(deptId);
        for (int i = 0; i < list.size(); i++) {
            Depart depart = list.get(i);
            String nameOfOffice = depart.getNameOfOffice();
            if (!"JYZGJG".equals(nameOfOffice)) {
                continue;
            }
            int inx = 5;
            String type = depart.getType();
            if (null != type && StringUtils.isNotEmpty(type)) {
                inx = Integer.valueOf(type).intValue();
            }
            Map<String, String> map = new HashMap<>();
            if (inx > 3) {
                map.put("id", depart.getId());
                map.put("name", depart.getName());
            }
            res.add(map);
        }
        return res;
    }

    /**
     * 获取下级所有学校
     *
     * @param deptId
     * @return
     */
    @GetMapping(value = "info/childAllDeparts1")
    public List<String> childAllDeparts1(String deptId) {
        List<String> res = new ArrayList<>();
        Depart depart = baseBiz.selectById(deptId);
        String type = null;
        if (StringUtils.isNotEmpty(depart.getType())) {
            type = depart.getType();
        }
        if ("1".equals(type)) {
            Example example = new Example(Depart.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("parentId", deptId);
            List<Depart> departs = baseBiz.selectByExample(example);

            for (Depart depart1 : departs) {
                Example example1 = new Example(Depart.class);
                Example.Criteria criteria1 = example1.createCriteria();
                criteria1.andEqualTo("parentId", depart1.getId());
                List<Depart> departs2 = baseBiz.selectByExample(example1);

                for (Depart depart2 : departs2) {
                    Example example2 = new Example(Depart.class);
                    Example.Criteria criteria2 = example2.createCriteria();
                    criteria2.andEqualTo("parentId", depart2.getId());
                    List<Depart> departs3 = baseBiz.selectByExample(example2);
                    for (Depart depart3 : departs3) {
                        res.add(depart3.getId());
                    }
                }
            }

        } else if ("2".equals(type)) {
            Example example = new Example(Depart.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("parentId", deptId);
            List<Depart> departs = baseBiz.selectByExample(example);

            for (Depart depart1 : departs) {
                Example example1 = new Example(Depart.class);
                Example.Criteria criteria1 = example1.createCriteria();
                criteria1.andEqualTo("parentId", depart1.getId());
                List<Depart> departs2 = baseBiz.selectByExample(example1);

                for (Depart departs3 : departs2) {
                    res.add(departs3.getId());
                }
            }


        } else if ("3".equals(type)) {
            Example example = new Example(Depart.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("parentId", deptId);
            List<Depart> departs = baseBiz.selectByExample(example);

            for (Depart departs1 : departs) {
                res.add(departs1.getId());
            }
        }

        return res;
    }

    /**
     * 获取上级所有教育局
     *
     * @param deptId
     * @return
     */
    @GetMapping(value = "info/parentAllDeparts1")
    @IgnoreUserToken
    public List<String> parentAllDeparts1(String deptId) {
        List<String> res = new ArrayList<>();
        Depart depart = baseBiz.selectById(deptId);

        Depart depart1 = baseBiz.selectById(depart.getParentId());
        if (depart1 != null) {
            res.add(depart1.getId());
            Depart depart2 = baseBiz.selectById(depart1.getParentId());
            if (depart2 != null) {
                res.add(depart2.getId());
                Depart depart3 = baseBiz.selectById(depart2.getParentId());
                if (depart3 != null) {
                    res.add(depart3.getId());
                }

            }
        }

        return res;
    }

    /**
     * 获取所有学校
     *
     * @param
     * @return
     */
    @GetMapping(value = "info/selectAllSchool")
    @IgnoreUserToken
    public List<String> selectAllSchool() {
        List<String> res = new ArrayList<>();
        List<Depart> list = baseBiz.selectAllSchool();
        if (list.size() != 0) {
            res = list.stream().map(Depart::getId).collect(Collectors.toList());
        }

        return res;
    }

    /**
     * 隐患移交
     * 学校用户如果是管理员可以移交给当前机构或者上级机构 如果不是管理员移交给管理员
     * 不是教育局机构
     *
     * @return
     */
    @GetMapping(value = "user/potentialHandover")
    public ObjectRestResponse potentialHandover(String departid, String jobcode, String name) {
        List<String> departIdList = null;
        String userid = BaseContextHandler.getUserID();
        User user = userBiz.selectById(userid);
        String departId = BaseContextHandler.getDepartID();
        //Position position = positionBiz.positionuser(userid);
        Depart depart = baseBiz.selectById(departId);
        List<UserVo> userList = new ArrayList<>();
        if (StringUtils.isEmpty(departid) && StringUtils.isEmpty(jobcode) && StringUtils.isEmpty(name)) {
            if (depart != null) {
                departIdList = new ArrayList<>();
                if (UserConstant.org_depart_school.equals(depart.getType()) || UserConstant.org_depart_municipalSchools.equals(depart.getType())) {
                    departIdList.add(departId);
                    userList = baseBiz.usersByDepart(departIdList, null, null);
                    //判断是否是校长如果是校长把上级教育局安全科主任加入进去
                    if (user.getAttr3().contains(UserConstant.comm_job_category_XZ) || user.getAttr3().contains(UserConstant.comm_job_category_XXGLY)) {
                        List<String> departids = new ArrayList<>();
                        departids.add(depart.getParentId());
                        List<UserVo> jyjuser = baseBiz.usersByDepart(departids, null, null);
                        if (jyjuser != null && jyjuser.size() > 0) {
                            for (UserVo userVo : jyjuser) {
                                userList.add(userVo);
                            }
                        }
                    }
                } else {
                    //当前自己部门
                    departIdList.add(departId);
                    //上级部门
                    departIdList.add(depart.getParentId());

                    Depart dp = new Depart();
                    dp.setParentId(departId);
                    //下级部门
                    List<Depart> departList = baseBiz.selectList(dp);
                    List<Map<String, Object>> depatids = new ArrayList<>();
                    depatids = departids(departList, depatids);
                    if (depatids != null && depatids.size() > 0) {
                        for (int i = 0; i < depatids.size(); i++) {
                            departIdList.add(depatids.get(i).get("id").toString());
                        }
                    }
                    userList = baseBiz.usersByDepart(departIdList, null, null);
                    //所属公安部门
                    if (StringUtils.isNotEmpty(depart.getPoliceId())) {
                        List<String> departids = new ArrayList<>();
                        departids.add(depart.getPoliceId());
                        List<UserVo> gauser = baseBiz.usersByDepart(departids, null, null);
                        if (gauser != null && gauser.size() > 0) {
                            for (UserVo userVo : gauser) {
                                userList.add(userVo);
                            }
                        }

                    }
                }
            }
            if (departIdList.size() == 0) {
                return ObjectRestResponse.error("当前角色有误");
            }
            return ObjectRestResponse.ok(userList);
        } else {
            List<String> userids = null;
            departIdList = new ArrayList<>();
            //如果传了 机构id 按照机构id查询 如果没有  就按照当前登录者要求看到机构
            if (StringUtils.isNotEmpty(departid)) {
                departIdList.add(departid);
            } else {
                if (UserConstant.org_depart_school.equals(depart.getType()) || UserConstant.org_depart_municipalSchools.equals(depart.getType())) {
                    departIdList.add(departId);
                    //判断是否是校长如果是校长把上级教育局安全科主任加入进去
                    if (user.getAttr3().contains(UserConstant.comm_job_category_XZ) || user.getAttr3().contains(UserConstant.comm_job_category_XXGLY)) {
                        departIdList.add(depart.getParentId());
                    }
                } else {
                    //当前自己部门
                    departIdList.add(departId);
                    //上级部门
                    departIdList.add(depart.getParentId());

                    Depart dp = new Depart();
                    dp.setParentId(departId);
                    //下级部门
                    List<Depart> departList = baseBiz.selectList(dp);
                    List<Map<String, Object>> depatids = new ArrayList<>();
                    depatids = departids(departList, depatids);
                    if (depatids != null && depatids.size() > 0) {
                        for (int i = 0; i < depatids.size(); i++) {
                            departIdList.add(depatids.get(i).get("id").toString());
                        }
                    }
                    //所属公安部门
                    if (StringUtils.isNotEmpty(depart.getPoliceId())) {
                        departIdList.add(depart.getPoliceId());
                    }
                }
            }
            if (StringUtils.isNotEmpty(name)) {
                userids = new ArrayList<>();
                Example example = new Example(User.class);
                example.createCriteria().andLike("name", "%" + name + "%");
                List<User> userList1 = userBiz.selectByExample(example);
                for (User user1 : userList1) {
                    userids.add(user1.getId());
                }
            }
            userList = baseBiz.usersByDepart(departIdList, jobcode, userids);
            return ObjectRestResponse.ok(userList);
        }
    }


    /**
     * 处理人
     * 学校用户如果是管理员可以移交给当前机构或者上级机构 教育机构选择当前机构和下面机构或者公安内科
     * 不是教育局机构
     *
     * @return
     */
    @GetMapping(value = "user/handler")
    public ObjectRestResponse handler(String departid, String jobcode, String name) {
        List<String> departIdList = new ArrayList<>();
        String userid = BaseContextHandler.getUserID();
        User user = userBiz.selectById(userid);
        String departId = BaseContextHandler.getDepartID();
        List<UserVo> userList = new ArrayList<>();
        Depart depart = baseBiz.selectById(departId);
        //什么都没有传
        if (StringUtils.isEmpty(departid) && StringUtils.isEmpty(jobcode) && StringUtils.isEmpty(name)) {
            if (depart != null) {
                //判断是否是学校
                if (UserConstant.org_depart_school.equals(depart.getType()) || UserConstant.org_depart_municipalSchools.equals(depart.getType())) {
                    departIdList.add(departId);
                    userList = baseBiz.usersByDepart(departIdList, null, null);
                    //判断是否是校长如果是校长可以加入教育局安全科
                    if (user.getAttr3().contains(UserConstant.comm_job_category_XZ) || user.getAttr3().contains(UserConstant.comm_job_category_XXGLY)) {
                        List<String> departids = new ArrayList<>();
                        departids.add(depart.getParentId());
                        List<UserVo> jyjuser = baseBiz.usersByDepart(departids, null, null);
                        if (jyjuser != null && jyjuser.size() > 0) {
                            for (UserVo userVo : jyjuser) {
                                userList.add(userVo);
                            }
                        }
                    }
                } else {
                    //当前自己部门
                    departIdList.add(departId);

                    Depart dp = new Depart();
                    dp.setParentId(departId);
                    //下级部门
                    List<Depart> departList = baseBiz.selectList(dp);
                    List<Map<String, Object>> depatids = new ArrayList<>();
                    depatids = departids(departList, depatids);
                    if (depatids != null && depatids.size() > 0) {
                        for (int i = 0; i < depatids.size(); i++) {
                            departIdList.add(depatids.get(i).get("id").toString());
                        }
                    }
                    userList = baseBiz.usersByDepart(departIdList, null, null);
                    //所属公安部门
                    if (StringUtils.isNotEmpty(depart.getPoliceId())) {
                        List<String> departids = new ArrayList<>();
                        departids.add(depart.getPoliceId());
                        List<UserVo> gauser = baseBiz.usersByDepart(departids, null, null);
                        if (gauser != null && gauser.size() > 0) {
                            for (UserVo userVo : gauser) {
                                userList.add(userVo);
                            }
                        }
                    }
                }
            }
            if (departIdList.size() == 0) {
                return ObjectRestResponse.error("当前角色有误");
            }
            return ObjectRestResponse.ok(userList);
        } else {
            List<String> userids = null;
            //如果传了 机构id 按照机构id查询 如果没有  就按照当前登录者要求看到机构
            if (StringUtils.isNotEmpty(departid)) {
                departIdList.add(departid);
            } else {
                if (UserConstant.org_depart_school.equals(depart.getType()) || UserConstant.org_depart_municipalSchools.equals(depart.getType())) {
                    departIdList.add(departId);
                    //判断是否是校长如果是校长把上级教育局安全科主任加入进去
                    if (user.getAttr3().contains(UserConstant.comm_job_category_XZ) || user.getAttr3().contains(UserConstant.comm_job_category_XXGLY)) {
                        departIdList.add(depart.getParentId());
                    }
                } else {
                    //当前自己部门
                    departIdList.add(departId);
                    Depart dp = new Depart();
                    dp.setParentId(departId);
                    //下级部门
                    List<Depart> departList = baseBiz.selectList(dp);
                    List<Map<String, Object>> depatids = new ArrayList<>();
                    depatids = departids(departList, depatids);
                    if (depatids != null && depatids.size() > 0) {
                        for (int i = 0; i < depatids.size(); i++) {
                            departIdList.add(depatids.get(i).get("id").toString());
                        }
                    }
                    //所属公安部门
                    if (StringUtils.isNotEmpty(depart.getPoliceId())) {
                        departIdList.add(depart.getPoliceId());
                    }
                }
            }
            if (StringUtils.isNotEmpty(name)) {
                userids = new ArrayList<>();
                Example example = new Example(User.class);
                example.createCriteria().andLike("name", "%" + name + "%");
                List<User> userList1 = userBiz.selectByExample(example);
                for (User user1 : userList1) {
                    userids.add(user1.getId());
                }
            }
            userList = baseBiz.usersByDepart(departIdList, jobcode, userids);
            return ObjectRestResponse.ok(userList);
        }

    }


    /**
     * 移交隐患/处理人  机构选择  type=1(处理人）
     *
     * @param type
     * @return
     */
    @GetMapping(value = "info/dangerDepartment")
    public ObjectRestResponse dangerDepartment(String type) {
        List<Depart> departIdList = new ArrayList<>();
        String userid = BaseContextHandler.getUserID();
        User user = userBiz.selectById(userid);
        String departId = BaseContextHandler.getDepartID();
        //Position position = positionBiz.positionuser(userid);
        Depart depart = baseBiz.selectById(departId);
        List<UserVo> userList = new ArrayList<>();
        if (depart != null) {
            if (UserConstant.org_depart_school.equals(depart.getType()) || UserConstant.org_depart_municipalSchools.equals(depart.getType())) {
                departIdList.add(depart);
//              userList=baseBiz.usersByDepart(departIdList, null, null);
                //判断是否是校长如果是校长把上级教育局安全科主任加入进去
                if (user.getAttr3().contains(UserConstant.comm_job_category_XZ) || user.getAttr3().contains(UserConstant.comm_job_category_XXGLY)) {
                    Depart departxz = baseBiz.selectById(depart.getParentId());
                    departIdList.add(departxz);
                }
            } else {
                //当前自己部门
                departIdList.add(depart);
                if (StringUtils.isEmpty(type)) {
                    //上级机构
                    Depart sjdepart = baseBiz.selectById(depart.getParentId());
                    departIdList.add(sjdepart);
                }
                //上级部门

                Depart dp = new Depart();
                dp.setParentId(departId);
                //下级部门
                List<Depart> departList = baseBiz.selectList(dp);
                List<Map<String, Object>> depatids = new ArrayList<>();
                depatids = departids(departList, depatids);
                if (departList != null && departList.size() > 0) {
                    departIdList = messageDownDepateList(departList, departIdList);
                }
                //所属公安部门
                if (StringUtils.isNotEmpty(depart.getPoliceId())) {
                    Depart plcDepart = baseBiz.selectById(depart.getPoliceId());
                    departIdList.add(plcDepart);
                }
            }
        }
        if (departIdList.size() == 0) {
            return ObjectRestResponse.error("当前角色有误");
        }
        return ObjectRestResponse.ok(departIdList);

    }


    /**
     * 查看当前用户上级部门和当前部门
     *
     * @return
     */
    @GetMapping(value = "info/messageUpdateDepate")
    public ObjectRestResponse messageUpdateDepate() {
        List<Depart> departs = new ArrayList<>();
        String userid = BaseContextHandler.getUserID();
        String departId = BaseContextHandler.getDepartID();
        if (!"root".equals(departId)) {
            //向上
            Depart depart = baseBiz.selectById(departId);
            departs = departs(depart, departs);
            //向下组织机构
            Depart depart1 = new Depart();
            depart1.setParentId(departId);
            List<Depart> departList = baseBiz.selectList(depart1);
            if (departList != null && departList.size() > 0) {
                departs = messageDownDepateList(departList, departs);
            }
            departs = departs.stream().sorted(Comparator.comparing(Depart::getCrtTime)).collect(Collectors.toList());
            return ObjectRestResponse.ok(departs);
        } else {
            List<Depart> departList = baseBiz.selectListAll();
            departList = departList.stream().sorted(Comparator.comparing(Depart::getCrtTime)).collect(Collectors.toList());
            return ObjectRestResponse.ok(departList);
        }

    }

    /**
     * 当前用户下部门列表
     *
     * @param
     * @return
     */
    @GetMapping(value = "user/messagedepartList")
    public List<Map<String, Object>> messagedepartList() {
        List<Map<String, Object>> depatids = new ArrayList<>();
        String userid = BaseContextHandler.getUserID();
        User user = userBiz.selectById(userid);
        if ("1".equals(user.getIsSuperAdmin())) {
            List<Depart> departList = baseBiz.selectListAll();
            for (Depart dp : departList) {
                if ("-1".equals(dp.getParentId())) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", dp.getId());
                    map.put("name", dp.getName());
                    map.put("type", dp.getType());
                    map.put("x", dp.getX());
                    map.put("y", dp.getY());
                    Depart depart2 = new Depart();
                    depart2.setParentId(dp.getId());
                    List<Depart> dpList = baseBiz.selectList(depart2);
                    if (dpList != null && dpList.size() > 0) {
                        depatids = departids(dpList, depatids);
                    }
                    depatids.add(map);
                }
            }

        } else {
            Map usermap = JSON.parseObject(user.getDepartId(), Map.class);
            String departId = usermap.get("id").toString();
            Depart dp = baseBiz.selectById(departId);
            Map<String, Object> map = new HashMap<>();
            map.put("id", dp.getId());
            map.put("name", dp.getName());
            depatids.add(map);
            Depart depart = new Depart();
            depart.setParentId(departId);
            List<Depart> departs = baseBiz.selectList(depart);
            if (departs != null && departs.size() > 0) {
                depatids = departids(departs, depatids);
            }
        }

        return depatids;
    }

    public List<Depart> departs(Depart depart, List<Depart> departs) {
        if (!"-1".equals(depart.getParentId())) {
            departs.add(depart);
            Depart depart1 = new Depart();
            depart1.setId(depart.getParentId());
            Depart dp = baseBiz.selectOne(depart1);
            departs(dp, departs);
        } else {
            departs.add(depart);
        }
        return departs;
    }

    public List<Depart> messageDownDepateList(List<Depart> list, List<Depart> depatids) {
        List<Depart> sondepat = new ArrayList<>();
        for (Depart dp : list) {
            Depart depart2 = new Depart();
            depart2.setParentId(dp.getId());
            List<Depart> departList = baseBiz.selectList(depart2);
            if (departList != null && departList.size() > 0) {
                sondepat.add(dp);
                depatids.add(dp);
            } else {
                depatids.add(dp);
            }
        }
        for (Depart dps : sondepat) {
            Depart depart2 = new Depart();
            depart2.setParentId(dps.getId());
            List<Depart> departList = baseBiz.selectList(depart2);
            messageDownDepateList(departList, depatids);
        }
        return depatids;
    }


    /**
     * 后台筛选
     *
     * @param type
     * @return
     */
    @GetMapping(value = "pc/pcDepartTree")
    public ObjectRestResponse pcDepartTree(String type) {
        String userid = BaseContextHandler.getUserID();
        List<Map<String, Object>> depatids = new ArrayList<>();
        User user = userBiz.selectById(userid);
        if ("1".equals(user.getIsSuperAdmin())) {
            Depart depart = new Depart();
            if (StringUtils.isEmpty(type)) {
                depart.setNameOfOffice(UserConstant.depart_type_JYZGJG);
            }
            if ("2".equals(type)) {
                depart.setNameOfOffice(UserConstant.depart_type_AQBHJG);
            }
            List<Depart> departList = baseBiz.selectList(depart);
            for (Depart dp : departList) {
                if ("-1".equals(dp.getParentId())) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("value", dp.getId());
                    map.put("label", dp.getName());
                    Depart depart2 = new Depart();
                    depart2.setParentId(dp.getId());
                    List<Depart> dpList = baseBiz.selectList(depart2);
                    if (dpList != null && dpList.size() > 0) {
                        map.put("children", pcdepartidTree(dpList));
                    }
                    depatids.add(map);
                }
            }
        } else {
            Map usermap = JSON.parseObject(user.getDepartId(), Map.class);
            String departId = usermap.get("id").toString();
            Map<String, Object> map = new HashMap<>();
            Depart dp = baseBiz.selectById(departId);
            map.put("value", dp.getId());
            map.put("label", dp.getName());
            Depart depart = new Depart();
            depart.setParentId(departId);
            List<Depart> departList = baseBiz.selectList(depart);
            if (departList != null && departList.size() > 0) {
                map.put("children", pcdepartidTree(departList));
            }
            depatids.add(map);
        }
        return ObjectRestResponse.ok(depatids);
    }

    public List<Map<String, Object>> pcdepartidTree(List<Depart> list) {
        List<Map<String, Object>> depatids = new ArrayList<>();
        for (Depart dp : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("value", dp.getId());
            map.put("label", dp.getName());
            Depart depart2 = new Depart();
            depart2.setParentId(dp.getId());
            List<Depart> departList = baseBiz.selectList(depart2);
            if (departList != null && departList.size() > 0) {
                map.put("children", pcdepartidTree(departList));
            }
            depatids.add(map);
        }
        return depatids;
    }


    /**
     * 公安用户查看关联学校
     *
     * @return
     */
    @GetMapping(value = "info/policbySchool")
    public ObjectRestResponse policbySchool() {
        String userid = BaseContextHandler.getUserID();
        User user = userBiz.selectById(userid);
        List<Depart> departs = new ArrayList<>();
        if ("-1".equals(user.getIsSuperAdmin())) {
            Depart depart = new Depart();
            depart.setNameOfOffice(UserConstant.depart_type_JYZGJG);
            List<Depart> departList = baseBiz.selectList(depart);
            if (departList != null && departList.size() > 0) {
                for (Depart dp : departList) {
                    if (Integer.valueOf(dp.getType()) > 3) {
                        departs.add(dp);
                    }
                }
                return ObjectRestResponse.ok(departs);
            } else {
                return ObjectRestResponse.error("机构有误");
            }
        } else {
            User user1 = userBiz.selectById(userid);
            Map usermap = JSON.parseObject(user1.getDepartId(), Map.class);
            String departId = usermap.get("id").toString();
            Depart dp = baseBiz.selectById(departId);
            if (UserConstant.depart_type_AQBHJG.equals(dp.getNameOfOffice())) {
                Depart depart = new Depart();
                depart.setPoliceId(departId);
                depart.setNameOfOffice(UserConstant.depart_type_JYZGJG);
                List<Depart> policedps = baseBiz.selectList(depart);
                if (policedps != null && policedps.size() > 0) {
                    for (Depart dps : policedps) {
                        if (Integer.valueOf(dps.getType()) > 3) {
                            departs.add(dps);
                        }
                    }
                    return ObjectRestResponse.ok(departs);
                } else {
                    return ObjectRestResponse.error("机构有误");
                }
            } else {
                return ObjectRestResponse.error("当前登录用户非公安用户");
            }

        }
    }

    @ApiOperation("部门详细信息树查询服务")
    @RequestMapping(value = "/departTree", method = RequestMethod.GET)
    public ObjectRestResponse getDepartTree(String name, String type, String status) {

        List<Depart> all = new ArrayList<Depart>();
        List<Depart> matched = new ArrayList<Depart>();
        String ifSuperAdmin = BaseContextHandler.getIsSuperAdmin();
        if (ifSuperAdmin.equals("1")) {
            Example example = new Example(Depart.class);
            example.setOrderByClause("order_num asc");
            all = baseBiz.selectByExample(example);

            Example.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(name)) {
                criteria.andLike("name", "%" + name + "%");
            }
            if (StringUtils.isNotBlank(type)) {
                criteria.andEqualTo("type", type);
            }
            if (StringUtils.isNotBlank(status)) {
                criteria.andEqualTo("status", status);
            }

            matched = baseBiz.selectByExample(example);
        } else {
            all = baseBiz.getDepartList("", "", "", BaseContextHandler.getUserID());
            matched = baseBiz.getDepartList(name, type, status, BaseContextHandler.getUserID());
        }

        List<DepartTree> list = baseBiz.createTree(all, matched);

        if (!ifSuperAdmin.equals("1")) {
            //在已有的线路车站树中加入其它部门树
            List<DepartTree> agencyList = baseBiz.getAgencyTree("4,5", name);

            for (DepartTree departTree : agencyList) {
                list.add(departTree);
            }
        }

        return ObjectRestResponse.ok(list);
    }

    @ApiOperation("查询用户所属机构")
    @RequestMapping(value = "/getOrganizeByUser", method = RequestMethod.GET)
    public ObjectRestResponse getOrganizeByUser(String type) {

        List<DepartTree> agencyList = baseBiz.getAgencyTree(type, null);

        return ObjectRestResponse.ok(agencyList);
    }

    @ApiOperation("部门树查询服务")
    @RequestMapping(value = "/departTreeByType", method = RequestMethod.GET)
    public ObjectRestResponse departTreeByType(String type) {

        List<String> typeList = Arrays.asList(type.split(","));

        String ifSuperAdmin = BaseContextHandler.getIsSuperAdmin();

        if (ifSuperAdmin.equals("1")) {
            List<DepartTree> list = baseBiz.allTreeNoUser(typeList);
            return ObjectRestResponse.ok(list);
        } else {
            List<Depart> all = baseBiz.allDepartNoUser(typeList, "", "0");

            List<Depart> matched = baseBiz.getDepartTree(BaseContextHandler.getUserID(), typeList);
            for (Depart depart : all) {
                for (Depart depart1 : matched) {
                    if (depart1.getId().equals(depart.getId())) {
                        depart.setDisabled(false);
                        break;
                    } else {
                        depart.setDisabled(true);
                    }

                }
            }

            List<DepartTree> list = baseBiz.createTree(all, all);
            return ObjectRestResponse.ok(list);

        }

    }

    @ApiOperation("所属机构树查询")
    @RequestMapping(value = "/getAgencyTree", method = RequestMethod.GET)
    public ObjectRestResponse getAgencyTree(String type) {

        List<String> typeList = Arrays.asList(type.split(","));

        String ifSuperAdmin = BaseContextHandler.getIsSuperAdmin();

        if (ifSuperAdmin.equals("1")) {
            List<DepartTree> list = baseBiz.allTreeNoUser(typeList);
            return ObjectRestResponse.ok(list);
        } else {
            Example example = new Example(Depart.class);
            example.setOrderByClause("order_num asc");
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("status", "0");
            criteria.andIn("type", typeList);
            List<Depart> all = baseBiz.selectByExample(example);
            UserVo user = userBiz.getUserInfo(BaseContextHandler.getUserID());
            criteria.andEqualTo("id", user.getDepartId());
            List<Depart> matched = baseBiz.selectByExample(example);

            for (Depart depart : all) {
                for (Depart depart1 : matched) {
                    if (depart1.getId().equals(depart.getId())) {
                        depart.setDisabled(false);
                        break;
                    } else {
                        depart.setDisabled(true);
                    }

                }
            }

            List<DepartTree> list = baseBiz.createTree(all, all);
            return ObjectRestResponse.ok(list);

        }

    }

    @ApiOperation("部门添加")
    @RequestMapping(value = "/departInfo", method = RequestMethod.POST)
    public ObjectRestResponse addDepart(@RequestBody Depart depart) {
        List<String> typeList = new ArrayList();
        LicencePara licencePara = licenceService.getLicenceResult();

        if (depart.getType().equals("3")) {
            typeList.add("3");
            List<DepartTree> stationList = baseBiz.allTreeNoUser(typeList);
            int stationRealCnt = stationList.size();
            int stationUpCnt = licencePara.getStationCnt();
            if (stationRealCnt >= stationUpCnt) {
                return ObjectRestResponse.error("该系统车站数量已达上限！");
            }
        } else if (depart.getType().equals("1")) {
            typeList.add("1");
            List<DepartTree> lineList = baseBiz.allTreeNoUser(typeList);
            int lineRealCnt = lineList.size();
            int lineUpCnt = licencePara.getLineCnt();
            if (lineRealCnt >= lineUpCnt) {
                return ObjectRestResponse.error("该系统线路数量已达上限！");
            }
        }

        depart.setCrtTime(new Date());
        depart.setCrtUserId(BaseContextHandler.getUserID());
        depart.setCrtUserName(BaseContextHandler.getUsername());
        depart.setId(UUIDUtils.generateUuid());
        baseBiz.insertSelective2(depart);
        return ObjectRestResponse.ok();
    }


    @ApiOperation("查询部门是否存在用户和员工")
    @RequestMapping(value = "/{id}/user", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse getDepartUsers(@PathVariable String id) {
        int userCount = baseBiz.getDepartUsers(id);
        Boolean ifUser = false;
        if (userCount > 0) {
            ifUser = true;
        }
        return ObjectRestResponse.ok(ifUser);
    }

    @ApiOperation("获取完整的部门树")
    @RequestMapping(value = "/alltree", method = RequestMethod.GET)
    public ObjectRestResponse getAllTree(String type) {
        Example example = new Example(Depart.class);
        example.setOrderByClause("order_num asc");
        List<Depart> all = baseBiz.selectByExample(example);

        Example.Criteria criteria = example.createCriteria();
        List<String> typeList = new ArrayList<String>();
        typeList.add("1");
        typeList.add("2");
        typeList.add("3");

        criteria.andIn("type", typeList);

        List<Depart> matched = baseBiz.selectByExample(example);


        List<DepartTree> list = baseBiz.createTree(all, matched);
        return ObjectRestResponse.ok(list);
    }


    @RequestMapping(value = "/getAllTreeNoUser", method = RequestMethod.GET)
    public ObjectRestResponse getAllTreeNoUser(String type) {
        List<String> typeList = Arrays.asList(type.split(","));
        return ObjectRestResponse.ok(baseBiz.allTreeNoUser(typeList));
    }

    @IgnoreUserToken
    @ApiOperation("根据编号查询部门信息")
    @RequestMapping(value = "/getDepartDetail", method = RequestMethod.GET)
    public Map<String, String> getDepartDetail(@RequestParam String id) {
        Map<String, String> map = new HashMap<>();
        Depart depart = baseBiz.selectById(id);
        map.put("id", depart.getId());
        map.put("name", departBiz.getDepartNameById(depart.getId()));
        map.put("code", depart.getCode());
        map.put("parentId", depart.getParentId());

        return map;
    }

    @ApiOperation("根据编号查询部门信息")
    @RequestMapping(value = "/getStationDetail", method = RequestMethod.GET)
    public Map<String, String> getStationDetail(@RequestParam String id) {
        Map<String, String> map = new HashMap<>();
        Depart depart = baseBiz.selectById(id);
        map.put("id", depart.getId());
        map.put("name", depart.getName());
        map.put("code", depart.getCode());
        map.put("parentId", depart.getParentId());

        return map;
    }

    @IgnoreUserToken
    @ApiOperation("根据编号查询所有子部门")
    @RequestMapping(value = "/getDepartChildren", method = RequestMethod.GET)
    public List<Map<String, String>> getDepartChildren(@RequestParam String id) {
        List<Depart> departs = departBiz.selectChildrenDepart(id);
        List<Map<String, String>> list = new ArrayList<>();
        for (Depart d : departs) {
            if (d.getType().equals("3")) {
                Map<String, String> map = new HashMap<>();
                map.put("id", d.getId());
                map.put("type", d.getType());
                map.put("name", d.getName());
                list.add(map);
            }
        }
        return list;
    }


    @IgnoreUserToken
    @ApiOperation("根据部门ID查询所有子部门ID")
    @RequestMapping(value = "/getDepartChildrenIds", method = RequestMethod.GET)
    public List<String> getDepartChildrenIds(@RequestParam String id) {
        List<String> idList = departBiz.selectChildren(id);
        return idList;
    }

    @ApiOperation("获取部门关联用户")
    @RequestMapping(value = "dataDepartUsers", method = RequestMethod.GET)
    public List<Map<String, String>> dataDepartUsers(String deptId) {
        List<String> deptIds = baseBiz.selectChildren(deptId);
        Example exa = new Example(User.class);
        Example.Criteria cr = exa.createCriteria();
        cr.andIn("departId", deptIds);
//        User u=new User();
//        u.setDepartId(deptId);
//        List<User> users=userBiz.selectList(u);
        List<User> users = userBiz.selectByExample(exa);

        List<Map<String, String>> list = new ArrayList<>();
        for (User o : users) {
            Map<String, String> map = new HashMap<>();
            map.put("id", o.getId());
            map.put("name", o.getName() == null ? o.getUsername() : o.getName());
            list.add(map);
        }
        return list;
    }

    @RequestMapping(value = "/upDepart", method = RequestMethod.GET)
    public List<Map<String, String>> getUpDepart(@RequestParam String id) {
        List<Depart> departs = departBiz.selectParent(id);
        List<Map<String, String>> list = new ArrayList<>();
        for (Depart d : departs) {
            Map<String, String> map = new HashMap<>();
            map.put("id", d.getId());
            map.put("type", d.getType());
            map.put("name", d.getName());
            list.add(map);
        }
        return list;
    }

    @RequestMapping(value = "/getDepartByType", method = RequestMethod.GET)
    public Map<String, String> getDepartByType(@RequestParam String departId, @RequestParam String type) {
        Map<String, String> map = new HashMap<>();
        Depart depart = baseBiz.selectById(departId);
        map.put("id", depart.getId());
        map.put("name", departBiz.getDepartName(departId, type));
        map.put("code", depart.getCode());
        map.put("parentId", depart.getParentId());
        return map;
    }

}
