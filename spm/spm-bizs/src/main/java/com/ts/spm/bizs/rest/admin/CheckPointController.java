package com.ts.spm.bizs.rest.admin;

import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.constant.RestCodeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.google.common.collect.Maps;
import com.ts.spm.bizs.biz.admin.BasePointBiz;
import com.ts.spm.bizs.biz.admin.DepartBiz;
import com.ts.spm.bizs.entity.admin.BasePoint;
import com.ts.spm.bizs.entity.admin.Depart;
import com.ts.spm.bizs.service.LicenceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ace
 */
@RestController
@RequestMapping("checkpoint")
@CheckClientToken
@CheckUserToken
@Api(tags = "安检点管理")
public class CheckPointController extends BaseController<BasePointBiz, BasePoint, String> {
    @Autowired
    private DepartBiz departBiz;
    @Autowired
    private BasePointBiz basePointBiz;
    @Autowired
    private LicenceService licenceService;



    @IgnoreUserToken
    @RequestMapping(value = "/stationsByUser", method = RequestMethod.GET)
    public List<Map<String, String>> stationsByUser() {
        List<Map<String, String>> res = new ArrayList<>();

        String ifSuperAdmin = BaseContextHandler.getIsSuperAdmin();
        List<Depart> stations;
        if (ifSuperAdmin.equals("1")) {
            stations = departBiz.allDepartNoUser(Arrays.asList("1,2,3".split(",")), "", "");
        } else {
            stations = departBiz.getUserMapDepart(BaseContextHandler.getUserID());
        }
        for (Depart dept : stations) {
            Map<String, String> map = new HashMap<>();
            map.put("id", dept.getId());
            map.put("name", dept.getName());
            map.put("type", dept.getType());
            res.add(map);
        }
        return res;
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ObjectRestResponse addPoint(@RequestBody BasePoint o){
        if(StringUtils.hasText(o.getCode()))
            o.setId(o.getCode());
        else
            return ObjectRestResponse.error(RestCodeConstants.EX_BUSINESS_BASE_CODE,"编码不能为空");

        BasePoint row=new BasePoint();
        row.setName(o.getName());
        row.setDepartId(o.getDepartId());
        if(baseBiz.selectCount(row)>0)
            return ObjectRestResponse.error(RestCodeConstants.EX_BUSINESS_BASE_CODE,"安检点已存在");

        boolean flag=baseBiz.getPointCnt();
        if ( flag ){
            List<Depart> departs=departBiz.selectParent(o.getDepartId());
            for (Depart d:departs){
                if("1".equals(d.getType())){
                    o.setLineId(d.getId());
                    o.setLine(d.getName());
                }else if("2".equals(d.getType())){
                    o.setAreaId(d.getId());
                    o.setArea(d.getName());
                }else if("3".equals(d.getType())){
                    o.setDepart(d.getName());
                }
            }

            o.setValidFlag(licenceService.getValidFlag(o.getLineId()+"_"+o.getDepartId()+"_"+o.getId()));
            o.setDateFlag(licenceService.getValidFlag(o.getId()+"_2038_01_01 01:01:01"));

            baseBiz.insertSelective(o);
            return ObjectRestResponse.ok();
        }else{
            return ObjectRestResponse.error("该系统用户数已达上限！");
        }
    }

    @IgnoreUserToken
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ObjectRestResponse getCheckPoint(@PathVariable String id) {
//        return ObjectRestResponse.ok(baseBiz.selectObjectById(id));
        return ObjectRestResponse.ok(baseBiz.selectById(id));
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ObjectRestResponse editCheckPoint(@PathVariable String id,@RequestBody BasePoint o) {
        BasePoint entity=this.baseBiz.selectById(id);
        entity.setCode(o.getCode());
        entity.setName(o.getName());
        entity.setEntry(o.getEntry());
        entity.setDepartId(o.getDepartId());
        entity.setStatus(o.getStatus());
        entity.setGatesum(o.getGatesum());

        List<Depart> departs=departBiz.selectParent(entity.getDepartId());
        for (Depart d:departs){
            if("1".equals(d.getType())){
                entity.setLineId(d.getId());
                entity.setLine(d.getName());
            }else if("2".equals(d.getType())){
                entity.setAreaId(d.getId());
                entity.setArea(d.getName());
            }else if("3".equals(d.getType())){
                entity.setDepart(d.getName());
            }
        }

        entity.setValidFlag(licenceService.getValidFlag(o.getLineId()+"_"+o.getDepartId()+"_"+o.getId()));
        entity.setDateFlag(licenceService.getValidFlag(o.getId()+"_2038_01_01 01:01:01"));

        this.baseBiz.updateSelectiveById(entity);
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse delCheckPoint(@PathVariable String id) {
        String[] ids=id.split(",");
        for(String str:ids){
            BasePoint o=baseBiz.selectById(str);
            o.setDeleteFlag(1);
            baseBiz.updateSelectiveById(o);
        }
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/getpage", method = RequestMethod.GET)
    public TableResultResponse page(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "") String departid,
                                    @RequestParam(defaultValue = "")  String entry, @RequestParam(defaultValue = "") String status) {
        List<String> stationIds=new ArrayList<>();
        if(!"".equals(departid)){
            stationIds=departBiz.selectChildren(departid);
        }else{
            List<Depart> departs=new ArrayList<>();
            String ifSuperAdmin=BaseContextHandler.getIsSuperAdmin();
            if(ifSuperAdmin.equals("1")) {
                Example example = new Example(Depart.class);
                Example.Criteria criteria = example.createCriteria();

                criteria.andIn("type", Arrays.asList("1,2,3".split(",")));
                example.setOrderByClause("order_num asc");
                departs = departBiz.selectByExample(example);
            }else{
                departs = departBiz.getUserMapDepart(BaseContextHandler.getUserID());
            }
            stationIds=departs.stream().map(Depart::getId).collect(Collectors.toList());
        }
        if(stationIds.size()==0)    return new TableResultResponse(0,stationIds);

        Page<Object> result = PageHelper.startPage(page, limit);
        List<Map<String, Object>> points=basePointBiz.selectByDepartids(name, entry,status, stationIds);
        for (Map<String, Object> p:points)
            p.put("line", getLine(p.get("depart_id").toString()).getName());
        return new TableResultResponse(result.getTotal(),points);
    }

    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    public ObjectRestResponse getall(@RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "") String departid,
                                    @RequestParam(defaultValue = "")  String entry, @RequestParam(defaultValue = "") String status) {
        String ifSuperAdmin=BaseContextHandler.getIsSuperAdmin();
        List<String> stationIds=new ArrayList<>();
        if(!"".equals(departid)){
            stationIds=departBiz.selectChildren(departid);
        }else{
            List<Depart> departs=new ArrayList<>();
            if(ifSuperAdmin.equals("1")){
                departs = departBiz.allDepartNoUser(Arrays.asList("1,2,3".split(",")),"","");
            }else{
                departs = departBiz.getUserMapDepart(BaseContextHandler.getUserID());
            }
//            List<Depart> departs = departBiz.getUserMapDepart(BaseContextHandler.getUserID());
            stationIds=departs.stream().map(Depart::getId).collect(Collectors.toList());
        }
        if(stationIds.size()==0)    return ObjectRestResponse.ok(stationIds);

        List<Map<String, Object>> points=basePointBiz.selectByDepartids(name, entry,status, stationIds);
        for (Map<String, Object> p:points)
            p.put("line", getLine(p.get("depart_id").toString()).getName());
//        return new TableResultResponse(points);
        return ObjectRestResponse.ok(points);
    }

    public Depart getLine(String id){
        Depart line=departBiz.selectById(id);
        if(!line.getParentId().equals("-1"))
            line=getLine(line.getParentId());
        return line;
    }

    @IgnoreUserToken
    @RequestMapping(value = "/getpoint", method = RequestMethod.GET)
    public List<Map<String, Object>> getpoint(@RequestParam(defaultValue = "") String deptId, @RequestParam(defaultValue = "") String userId) {
        List<Map<String, Object>> points=new ArrayList<>();
        String ifSuperAdmin=BaseContextHandler.getIsSuperAdmin();
        List<String> stationIds;
        if(StringUtils.isEmpty(deptId)){
            List<Depart> list=new ArrayList<>();
            if(ifSuperAdmin.equals("1")){
                list = departBiz.allDepartNoUser(Arrays.asList("1,2,3".split(",")),"","");
            }else{
                list = departBiz.getUserMapDepart(BaseContextHandler.getUserID());
            }
            stationIds=list.stream().map(o->o.getId()).collect(Collectors.toList());
        }else{
            List<Depart> list=departBiz.selectChildrenDepart(deptId);
            stationIds=list.stream().map(o->o.getId()).collect(Collectors.toList());
        }

        if(stationIds.size()==0){
            return points;
        }

        points=basePointBiz.selectByDepartids("", "","", stationIds);
        for (Map<String, Object> p:points){
            BasePoint o=baseBiz.selectById(p.get("id"));
            p.put("upd_time","");
            p.put("gateSum",Integer.toString(o.getGatesum()));
//            p.put("line", getLine(p.get("depart_id")).getName());
        }
        return points;
    }

    @IgnoreUserToken
    @RequestMapping(value = "/getpointByUserId", method = RequestMethod.GET)
    public List<Map<String, Object>> getpointByUserId(@RequestParam(defaultValue = "") String deptId,
                                                      @RequestParam(defaultValue = "") String userId) {
        List<Map<String, Object>> points=new ArrayList<>();
        String ifSuperAdmin=BaseContextHandler.getIsSuperAdmin();
        List<String> stationIds;
        if(StringUtils.isEmpty(deptId)){
            List<Depart> list=new ArrayList<>();
            if(ifSuperAdmin.equals("1")){
                list = departBiz.allDepartNoUser(Arrays.asList("1,2,3".split(",")),"","");
            }else{
                list = departBiz.getUserMapDepart(userId);
            }
            stationIds=list.stream().map(o->o.getId()).collect(Collectors.toList());
        }else{
            List<Depart> list=departBiz.selectChildrenDepartByUserId(deptId,userId);
            stationIds=list.stream().map(o->o.getId()).collect(Collectors.toList());
        }

        if(stationIds.size()==0){
            return points;
        }

        points=basePointBiz.selectByDepartids("", "","", stationIds);
        for (Map<String, Object> p:points){
            BasePoint o=baseBiz.selectById(p.get("id"));
            p.put("upd_time","");
            p.put("gateSum",Integer.toString(o.getGatesum()));
//            p.put("line", getLine(p.get("depart_id")).getName());
        }
        return points;
    }

    @IgnoreUserToken
    @RequestMapping(value = "/stations", method = RequestMethod.GET)
    public List<Map<String, String>> stations(@RequestParam(defaultValue = "") String deptId) {
        List<Map<String, String>> res=new ArrayList<>();

        String ifSuperAdmin=BaseContextHandler.getIsSuperAdmin();
        List<Depart> stations;

        if(StringUtils.isEmpty(deptId))
            if(ifSuperAdmin.equals("1")){
                stations = departBiz.allDepartNoUser(Arrays.asList("1,2,3".split(",")),"","");
            }else{
                stations = departBiz.getUserMapDepart(BaseContextHandler.getUserID());
            }
        else{
            stations=departBiz.selectChildrenDepart(deptId);
        }

        for (Depart dept:stations){
            Map<String, String> map=new HashMap<>();
            map.put("id", dept.getId());
            map.put("name", dept.getName());
            map.put("type", dept.getType());
            res.add(map);
        }
        return res;

    }
    @IgnoreUserToken
    @RequestMapping(value = "/stationsByUserId", method = RequestMethod.GET)
    public List<Map<String, String>> stationsByUserId(@RequestParam(defaultValue = "") String deptId,@RequestParam(defaultValue = "") String userId) {
        List<Map<String, String>> res=new ArrayList<>();

        String ifSuperAdmin=BaseContextHandler.getIsSuperAdmin();
        List<Depart> stations;

        if(StringUtils.isEmpty(deptId))
            if(ifSuperAdmin.equals("1")){
                stations = departBiz.allDepartNoUser(Arrays.asList("1,2,3".split(",")),"","");
            }else{
                stations = departBiz.getUserMapDepart(userId);
            }
        else{
            stations=departBiz.selectChildrenDepartByUserId(deptId,userId);
        }
        if(null!=stations&&stations.size()>0){
            for (Depart dept:stations){
                Map<String, String> map=new HashMap<>();
                map.put("id", dept.getId());
                map.put("name", dept.getName());
                map.put("type", dept.getType());
                res.add(map);
            }
        }

        return res;

    }

    @ApiOperation("根据安检点查询部门")
//    @IgnoreClientToken
//    @IgnoreUserToken
    @RequestMapping(value = "/getDepart", method = RequestMethod.GET)
    public Map<String,String> getDepartInfo(@RequestParam String pointId) {
        Map<String,String> depart = new HashMap<>();
        BasePoint point=baseBiz.selectById(pointId);
        depart.put("id",point.getDepartId());
//        depart.put("name",departBiz.getDepartNameById(point.getDepartId()));
        depart.put("name",departBiz.selectById(point.getDepartId()).getName());
        return depart;
    }

    @ApiOperation("根据编号查询安检点信息")
//    @IgnoreClientToken
    @IgnoreUserToken
    @RequestMapping(value = "/getPoint", method = RequestMethod.GET)
    public Map<String,String> getPointInfo(@RequestParam String id) {
        Map<String,String> map= Maps.newHashMap();
        BasePoint point=baseBiz.selectById(id);
        if(point == null){
            map.put("name", "");
        } else {
            map.put("code", point.getCode());
            map.put("id", point.getId());
            map.put("name", point.getName());
            map.put("departId", point.getDepartId());
            map.put("departName", departBiz.getDepartNameById(point.getDepartId()));
        }
        return map;
    }

    @IgnoreUserToken
    @ApiOperation("根据部门查询下级所有安检点")
    @RequestMapping(value = "/getAllPointByDepart", method = RequestMethod.GET)
    public List<String> getAllPointByDepart(@RequestParam String departId) {
        List<Map<String, String>> pointList=baseBiz.getAllPointByDepart(BaseContextHandler.getUserID(),departId);
        List<String> pointIds=pointList.stream().map(u->u.get("id")).collect(Collectors.toList());
        return pointIds;
    }

    @IgnoreUserToken
    @ApiOperation("根据编号查询部门信息")
    @RequestMapping(value = "/departDetail", method = RequestMethod.GET)
    public Map<String, String> departDetail(@RequestParam String id) {
        Map<String,String> map= new HashMap<>();
        Depart depart=departBiz.selectById(id);
        map.put("id",depart.getId());
        map.put("name",departBiz.getDepartNameById(depart.getId()));
        map.put("code",depart.getCode());
        map.put("parentId",depart.getParentId());

        return  map;
    }

}
