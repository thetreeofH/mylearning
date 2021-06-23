package com.ts.spm.bizs.rest.jzpoip;

import com.github.ag.core.constants.OperLogConstants;
import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.DateUtil;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.jzpoip.TblOpenInspectionInfoBiz;
import com.ts.spm.bizs.entity.jzpoip.TblOpenInspectionInfo;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import com.ts.spm.bizs.rest.admin.DepartController;
import com.ts.spm.bizs.rest.admin.UserController;
import com.ts.spm.bizs.rest.dict.DictValueController;
import com.ts.spm.bizs.util.FtpUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName OpenInspectionController
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/7/20 11:32
 * @Version 1.0
 **/
@RestController
@RequestMapping(value = "openInspection")
@CheckClientToken
@CheckUserToken
public class OpenInspectionController extends BaseController<TblOpenInspectionInfoBiz, TblOpenInspectionInfo,String> {
    @Value("${oipftp.local}")
    private String local;

    @Value("${oipftp.prot}")
    private String prot;

    @Value("${oipftp.userName}")
    private String userName;

    @Value("${oipftp.passWord}")
    private String passWord;

    @Value("${oipftp.suspectedItemsPicturePath}")
    private String suspectedItemsPicturePath;

    @Autowired
    TblOpenInspectionInfoBiz tblOpenInspectionInfoBiz;

    @Autowired
    CheckPointController checkPointCtr;
    @Autowired
    private UserController userCtr;
    @Autowired
    private DepartController departCtr;
    @Autowired
    private DictValueController dictValueCtr;

    /**
     * 新增开检核录
     * @param info
     * @return
     */
    @RequestMapping(value = "/information/add",method = RequestMethod.POST)
//    @OperLog(operModul = "开检管理-新增开检核录",operDesc = "新增开检核录",operType = OperLogConstants.ADD)
    public ObjectRestResponse add(@RequestBody TblOpenInspectionInfo info){

            info.setId(UUIDUtils.generateUuid());
            info.setMissionId(info.getPointid() + DateUtil.date2Str(info.getFirstTime(), "yyyyMMddHHmmss"));
            info.setOperatorUserId(BaseContextHandler.getUserID());
            info.setOperatorUserName(BaseContextHandler.getUsername());
            info.setCreateTime(new Date());
            baseBiz.insertSelective(info);// 插入数据库
        return ObjectRestResponse.ok();
    }

    /**
     * 上传图片
     * @param file
     * @return
     */
    @RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
//    @OperLog(operModul = "开检管理-上传图片",operDesc = "上传图片",operType = OperLogConstants.UPLOAD)
    public ObjectRestResponse uploadFile(@RequestParam(value = "file")MultipartFile file){
        String filePath = suspectedItemsPicturePath + "/"
                + DateUtil.date2Str(new Date(), "yyyy/MM/dd/HH/mm/ss");

        String fileName = "suspectedItemsPicturePath" + DateUtil.getRevTime() + ".jpg";

        Map<String, Object> map2 = null;
        try {
            map2 = FtpUtil.uploadFile(local, userName, passWord, Integer.parseInt(prot),
                    filePath, fileName, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if ((boolean) map2.get("status") == true) {
            return new ObjectRestResponse<>().data(map2.get("url").toString());
        }
        return new ObjectRestResponse<>().error("上传失败");
    }

    /**
     * 开检记录查询
     * @param limit
     * @param page
     * @param startTime
     * @param endTime
     * @param confirmForbiddenType
     * @param ifContraband
     * @param ifCheck
     * @return
     */
    @RequestMapping(value = "/information/query",method = RequestMethod.GET)
    @IgnoreUserToken
//    @OperLog(operModul = "开检管理-开检记录查询",operDesc = "开检记录查询",operType = OperLogConstants.QUERY)
    public TableResultResponse query(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(value = "startTime",required = false) String startTime,@RequestParam(value = "endTime",required = false) String endTime,
                                     @RequestParam(value = "confirmForbiddenType",required = false) Integer confirmForbiddenType,
                                     @RequestParam(value = "confirmForbiddenSubtype",required = false) Integer confirmForbiddenSubtype,
                                     @RequestParam(value = "ifContraband",required = false) Integer ifContraband,
                                     @RequestParam(value = "ifCheck",required = false) Integer ifCheck,
                                     @RequestParam(defaultValue = "") String departId,
                                     @RequestParam(defaultValue = "") String pointId,
                                     @RequestParam(defaultValue = "") Integer handleResult){
        if(StringUtils.isNoneBlank(startTime)){
            startTime = startTime + " 00:00:00";
        }
        if(StringUtils.isNoneBlank(endTime)){
            endTime = endTime + " 23:59:59";
        }


        List<Map<String, Object>> points=checkPointCtr.getpoint(departId,BaseContextHandler.getUserID());
        if(CollectionUtils.isEmpty(points)) {
            return new TableResultResponse<>(0, points);
        }
        List<String>  pointIds = points.stream().map(u->u.get("id").toString()).collect(Collectors.toList());


        Page<Object> result = PageHelper.startPage(page, limit);
        List<Map<String,String>> oipList = tblOpenInspectionInfoBiz.query(startTime,endTime,confirmForbiddenType,confirmForbiddenSubtype,ifContraband,ifCheck,pointIds,pointId,handleResult);
        if(oipList != null && oipList.size() > 0){
            for(Map map : oipList){
                //补全开检信息表
                /*TblOpenInspectionInfo tblOpenInspectionInfo = new TblOpenInspectionInfo();
                tblOpenInspectionInfo.setId((String) map.get("id"));
                tblOpenInspectionInfo.setOperatorUserId((String) map.get("operatorUserId"));
                tblOpenInspectionInfo.setOperatorUserName((String) map.get("operatorUserName"));
                tblOpenInspectionInfo.setAttr2(DateUtil.date2Str((Date) map.get("judgePictureTime"),"yyyy-MM-dd HH:mm:ss"));
                tblOpenInspectionInfo.setAttr3(String.valueOf(map.get("handleResult")));
                tblOpenInspectionInfo.setAttr4((String) map.get("CONFIRM_FORBIDDEN_TYPE_NAME"));
                tblOpenInspectionInfo.setAttr5((String) map.get("CONFIRM_FORBIDDEN_SUBTYPE_NAME"));*/

                if(map.containsKey("handleUserId")){
                    if(!"".equals(map.get("handleUserId")) && map.get("handleUserId") != null){
                        String handleUserId = (String) map.get("handleUserId");
                        Map handleUserNameMap = tblOpenInspectionInfoBiz.selectByUserId(handleUserId);
                        if(handleUserNameMap != null){
                            if(handleUserNameMap.containsKey("handle_user_name")){
                                if(!"".equals(handleUserNameMap.get("handle_user_name")) && handleUserNameMap.get("handle_user_name") != null) {
                                    map.put("handleUserName", handleUserNameMap.get("handle_user_name"));

                                   /* tblOpenInspectionInfo.setHandleUserId(handleUserId);
                                    tblOpenInspectionInfo.setAttr1((String) handleUserNameMap.get("handle_user_name"));

                                    baseBiz.updateSelectiveById2(tblOpenInspectionInfo);*/
                                }else {
                                    map.put("handleUserName", "");
                                }
                            }/*else{
                                tblOpenInspectionInfo.setHandleUserId(handleUserId);
                                tblOpenInspectionInfo.setAttr1("");
                                baseBiz.updateSelectiveById2(tblOpenInspectionInfo);
                            }*/
                        }
                    }
                }/*else{
                    tblOpenInspectionInfo.setHandleUserId("");
                    tblOpenInspectionInfo.setAttr1("");
                    baseBiz.updateSelectiveById2(tblOpenInspectionInfo);
                }*/

            }

        }

        return new TableResultResponse(result.getTotal(),oipList);
    }

    /**
     * 违禁品统计(时间维度)
     * @param startTime
     * @param endTime
     * @param confirmForbiddenType
     * @param openInspectionName
     * @param dateType
     * @return
     */
//    @OperLog(operModul = "违禁品统计(时间维度)",operDesc = "开检统计",operType = OperLogConstants.QUERY)
    @RequestMapping(value = "/information/statistics",method = RequestMethod.GET)
    public ObjectRestResponse statistics(@RequestParam String startTime,
                                         @RequestParam String endTime,
                                         @RequestParam(value = "confirmForbiddenType",required = false) Integer confirmForbiddenType,
                                         @RequestParam(value = "confirmForbiddenSubtype",required = false) Integer confirmForbiddenSubtype,
                                         @RequestParam(value = "openInspectionName",required = false) String openInspectionName,
                                         @RequestParam String dateType,@RequestParam(defaultValue = "") String departId,
                                         @RequestParam(defaultValue = "") String pointId){
        if(StringUtils.isNoneBlank(startTime)){
            startTime = startTime + " 00:00:00";
        }
        if(StringUtils.isNoneBlank(endTime)){
            endTime = endTime + " 23:59:59";
        }
        List<Map<String, Object>> points=checkPointCtr.getpoint(departId,BaseContextHandler.getUserID());
        if(CollectionUtils.isEmpty(points)) {
            return ObjectRestResponse.error("该组织无安检点");
        }
        List<String>  pointIds = points.stream().map(u->u.get("id").toString()).collect(Collectors.toList());
        List<Map<String,String>> statistics = tblOpenInspectionInfoBiz.statistics(startTime,endTime,confirmForbiddenType,confirmForbiddenSubtype,openInspectionName,dateType,pointIds,pointId);

        Map data = new HashMap<String,String>();

        List date = new ArrayList();
        List count = new ArrayList();
        if(statistics != null && statistics.size() > 0){
            for(Map<String,String> map : statistics){

                for(String key : map.keySet()){
                    Object ob = map.get(key);
                    if("dates".equals(key)){
                        date.add(ob.toString());
                    }else{
                        count.add(ob.toString());
                    }
                }
            }

        }
        data.put("dates",date);
        data.put("counts",count);

        return ObjectRestResponse.ok(data);
    }

    @ApiOperation("违禁品统计(车站维度)")
    @RequestMapping(value = "/statistics/byDepart", method = RequestMethod.GET)
    public ObjectRestResponse contrabandStatisticsbyDepart(String departId,String departType, String startTime, String endTime){
        if(StringUtils.isNoneBlank(startTime)){
            startTime = startTime + " 00:00:00";
        }
        if(StringUtils.isNoneBlank(endTime)){
            endTime = endTime + " 23:59:59";
        }
        List<String> pointIds=new ArrayList<String>();
        if(!"".equals(departId)&&departId!=null){
            pointIds=checkPointCtr.getAllPointByDepart(departId);
        }else {
            pointIds=userCtr.getPoint(BaseContextHandler.getUserID());
        }


        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        if(departType.equals("byPoint")){
            list=baseBiz.statisByPoint(departId,startTime,endTime);
            for(Map<String,String> statis:list){
                statis.put("name",checkPointCtr.getDepartInfo(statis.get("pointId")).get("name")+"-"+checkPointCtr.getPointInfo(statis.get("pointId")).get("name"));
            }
        }
        if(departType.equals("byDepart")){
            list=baseBiz.statisByDepart(pointIds,startTime,endTime);
            for(Map<String,String> statis:list){
                statis.put("name",departCtr.getDepartDetail(statis.get("departId")).get("name"));
            }
        }
        return ObjectRestResponse.ok(list);
    }

    @ApiOperation("违禁品统计(机构列表)")
    @RequestMapping(value = "/statistics/byDepartList", method = RequestMethod.GET)
    public ObjectRestResponse contrabandStatistics(String departId,String departType, String startTime, String endTime){
        if(StringUtils.isNoneBlank(startTime)){
            startTime = startTime + " 00:00:00";
        }
        if(StringUtils.isNoneBlank(endTime)){
            endTime = endTime + " 23:59:59";
        }

        List<String> pointIds=new ArrayList<String>();
        if(!"".equals(departId)&&departId!=null){
            pointIds=checkPointCtr.getAllPointByDepart(departId);
        }else {
            pointIds=userCtr.getPoint(BaseContextHandler.getUserID());
        }


        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        if(departType.equals("byPoint")){
            list=baseBiz.statisticsByPointList(pointIds,startTime,endTime);
            for(Map<String,String> statis:list){
                statis.put("name",checkPointCtr.getDepartInfo(statis.get("pointId")).get("name")+"-"+checkPointCtr.getPointInfo(statis.get("pointId")).get("name"));
            }
        }
        if(departType.equals("byDepart")){
            list=baseBiz.statisByDepart(pointIds,startTime,endTime);
            for(Map<String,String> statis:list){
                statis.put("name",departCtr.getDepartDetail(statis.get("departId")).get("name"));
            }
        }
        return ObjectRestResponse.ok(list);
    }

    @ApiOperation("违禁品统计(类型维度)")
    @RequestMapping(value = "/statistics/byType", method = RequestMethod.GET)
    public ObjectRestResponse contrabandStatisticsbyType(String departId, String startTime, String endTime){
        if(StringUtils.isNoneBlank(startTime)){
            startTime = startTime + " 00:00:00";
        }
        if(StringUtils.isNoneBlank(endTime)){
            endTime = endTime + " 23:59:59";
        }
        List<String> pointIds=new ArrayList<String>();
        if(!"".equals(departId)&&departId!=null){
            pointIds=checkPointCtr.getAllPointByDepart(departId);
        }else {
            pointIds=userCtr.getPoint(BaseContextHandler.getUserID());
        }

        List<Map<String,String>> list=baseBiz.statisByType(pointIds,startTime,endTime);
        for(Map<String,String> statis:list){
            List<Map<String,Object>> dicvalueList=dictValueCtr.dictValueByCode("DOT");

            for(Map<String,Object> dicvalue:dicvalueList){
                String value = dicvalue.get("value").toString();
                String typeId = String.valueOf(statis.get("typeId"));
                if(value.equals(typeId))
                    if(dicvalue.containsKey("labelDefault")){
                        statis.put("typeName",dicvalue.get("labelDefault").toString());
                    }

            }
        }
        return ObjectRestResponse.ok(list);
    }

    /**
     * 查询开检详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/information/get/{id}",method = RequestMethod.GET)
//    @OperLog(operModul = "开检管理-开检详情",operDesc = "开检详情",operType = OperLogConstants.QUERY)
    public ObjectRestResponse get(@PathVariable String id){

        return ObjectRestResponse.ok(baseBiz.selectByOipId(id));
    }

    /**
     * 更新核查
     * @param info
     * @return
     */
    @RequestMapping(value = "/information/update",method = RequestMethod.PUT)
//    @OperLog(operModul = "开检管理-更新核查",operDesc = "更新核查",operType = OperLogConstants.UPDATE)
    public ObjectRestResponse update(@RequestBody TblOpenInspectionInfo info){

        info.setUpdateTime(new Date());
        int result = baseBiz.updateSelectiveById2(info);
        if(result == 1){
            return ObjectRestResponse.ok();
        }else {
            return ObjectRestResponse.error("更新失败!");
        }


    }
    @GetMapping(value = "/information/todayStatistics")
//    @OperLog(operModul = "开检管理-开检量统计",operDesc = "开检量统计",operType = OperLogConstants.QUERY)
    public ObjectRestResponse todayStatistics(){
        Map<String,String> map = tblOpenInspectionInfoBiz.todayStatistics(DateUtil.getCurrentDay(),DateUtil.getCurrentTimeStr("yyyy-MM-dd HH:mm:ss"),BaseContextHandler.getUserID());
        return ObjectRestResponse.ok(map);
    }


}
