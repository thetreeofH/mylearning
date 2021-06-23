package com.ts.spm.bizs.rest.jzpoip;

import com.github.ag.core.constants.OperLogConstants;
import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ts.spm.bizs.biz.jzpoip.TblOpenInspectionInfoBiz;
import com.ts.spm.bizs.entity.jzpoip.TblOpenInspectionInfo;
import com.ts.spm.bizs.rest.dict.DictValueController;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("pointOpenInspection")
@IgnoreClientToken
@IgnoreUserToken
public class PointOpenInspectionController extends BaseController<TblOpenInspectionInfoBiz, TblOpenInspectionInfo,String> {
    @Autowired
    TblOpenInspectionInfoBiz tblOpenInspectionInfoBiz;
    @Autowired
    private DictValueController dictValueCtr;
    /**
     * 开检记录查询
     * @param limit
     * @param page
     * @param startTime
     * @param endTime
     * @param confirmForbiddenType
     * @return
     */
    @RequestMapping(value = "/information/query",method = RequestMethod.GET)
    public TableResultResponse query(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(value = "startTime",required = false) String startTime, @RequestParam(value = "endTime",required = false) String endTime,
                                     @RequestParam(value = "confirmForbiddenType",required = false) Integer confirmForbiddenType,
                                     @RequestParam(defaultValue = "") String pointId){
        if(StringUtils.isNoneBlank(startTime)){
            startTime = startTime + " 00:00:00";
        }
        if(StringUtils.isNoneBlank(endTime)){
            endTime = endTime + " 23:59:59";
        }

        Page<Object> result = PageHelper.startPage(page, limit);
        List<Map<String,String>> oipList = tblOpenInspectionInfoBiz.pointQuery(startTime,endTime,confirmForbiddenType,pointId);
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
    @ApiOperation("违禁品统计(pointId)")
    @RequestMapping(value = "/statistics/byPoint", method = RequestMethod.GET)
    public ObjectRestResponse contrabandStatistics(String pointId,String startTime, String endTime){
        if(StringUtils.isNoneBlank(startTime)){
            startTime = startTime + " 00:00:00";
        }
        if(StringUtils.isNoneBlank(endTime)){
            endTime = endTime + " 23:59:59";
        }
        List<String> pointIds = new ArrayList<>();
        pointIds.add(pointId);
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();

        list=baseBiz.statisticsByPointList(pointIds,startTime,endTime);
        for(Map<String,String> statis:list){
            statis.put("name",tblOpenInspectionInfoBiz.selectById(pointId).get("departName")+"-"+tblOpenInspectionInfoBiz.selectById(pointId).get("name"));
        }


        return ObjectRestResponse.ok(list);
    }

    @ApiOperation("违禁品统计(类型维度)")
    @RequestMapping(value = "/statistics/byType", method = RequestMethod.GET)
    public ObjectRestResponse contrabandStatisticsbyType(String pointId, String startTime, String endTime){
        if(StringUtils.isNoneBlank(startTime)){
            startTime = startTime + " 00:00:00";
        }
        if(StringUtils.isNoneBlank(endTime)){
            endTime = endTime + " 23:59:59";
        }
        List<String> pointIds = new ArrayList<>();
        pointIds.add(pointId);
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
}
