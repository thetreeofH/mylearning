package com.ts.spm.bizs.rest.jzpmq;

import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.DateUtil;
import com.ts.spm.bizs.biz.jzpmq.DealResultInfoBiz;
import com.ts.spm.bizs.entity.jzpmq.DealResultInfo;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName HandleResultController
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/8/13 17:33
 * @Version 1.0
 **/
@RestController
@RequestMapping(value = "handleResult")
@IgnoreClientToken
@CheckUserToken
public class HandleResultController extends BaseController<DealResultInfoBiz, DealResultInfo,String> {

    @Autowired
    CheckPointController checkPointCtr;
    @Autowired
    DealResultInfoBiz dealResultInfoBiz;
    @GetMapping(value = "/information/query")
    public TableResultResponse query(@RequestParam(defaultValue = "10") int limit,
                                     @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(value = "startTime", required = false) String startTime,
                                     @RequestParam(value = "endTime", required = false) String endTime,
                                     @RequestParam(value = "judgePictureSource",defaultValue = "") Integer judgePictureSource,
                                     @RequestParam(value = "handleResult",defaultValue = "") Integer handleResult,
                                     @RequestParam(value = "suspectedForbiddenType",defaultValue = "") Integer suspectedForbiddenType,
                                     @RequestParam(value = "suspectedForbiddenSubtype",defaultValue = "") Integer suspectedForbiddenSubtype,
                                     @RequestParam(defaultValue = "") String departId,
                                     @RequestParam(defaultValue = "") String pointId,
                                     @RequestParam(defaultValue = "") Integer ifCheck){
        try {
            if(StringUtils.isNoneBlank(startTime)){
                startTime = startTime + " 00:00:00";
            }
            if(StringUtils.isNoneBlank(endTime)){
                endTime = endTime + " 23:59:59";
            }
            List<String>  pointIds =null;
            if(StringUtils.isNotEmpty(departId)){
                List<Map<String, Object>> points=checkPointCtr.getpoint(departId, BaseContextHandler.getUserID());
                if(CollectionUtils.isEmpty(points)) {
                    return new TableResultResponse<>(0, points);
                }
                pointIds = points.stream().map(u->u.get("id").toString()).collect(Collectors.toList());
            }


            Map<String,Object> map=new HashMap<>();
            map.put("startTime",startTime);
            map.put("endTime",endTime);
            map.put("judgePictureSource",judgePictureSource);
            map.put("handleResult",handleResult);
            map.put("suspectedForbiddenType",suspectedForbiddenType);
            map.put("pointIds",pointIds);
            map.put("pointId",pointId);
            map.put("ifCheck",ifCheck);
            map.put("suspectedForbiddenSubtype",suspectedForbiddenSubtype);
            map.put("page",(page-1)*limit);
            map.put("pagesize",limit);
            // Page<Object> result = PageHelper.startPage(page, limit);
            List<Map<String,String>> handleResultList =
                    dealResultInfoBiz.query(map);
            Integer total=
                    dealResultInfoBiz.queryTotal(map);
//            List<Map<String,String>> handleResultList =
//                    dealResultInfoBiz.query(startTime,endTime,judgePictureSource,handleResult,
//                            suspectedForbiddenType,pointIds,pointId,ifCheck,suspectedForbiddenSubtype);
//            Integer total=
//                    dealResultInfoBiz.queryTotal(startTime,endTime,judgePictureSource,handleResult,
//                            suspectedForbiddenType,pointIds,pointId,ifCheck,suspectedForbiddenSubtype);
            return new TableResultResponse(total,handleResultList);
        }catch(Exception e){
            e.printStackTrace();
            return TableResultResponse.error(e.getMessage());
        }

    }

    /**
     * 当日处置结果统计
     * @return
     */
    @GetMapping(value = "/information/todayStatistics")
    public ObjectRestResponse todayStatistics(){
        Map<String,String> map = dealResultInfoBiz.todayStatistics(DateUtil.getCurrentDay(),DateUtil.getCurrentTimeStr("yyyy-MM-dd HH:mm:ss"),BaseContextHandler.getUserID());
        return ObjectRestResponse.ok(map);
    }

    /**
     * 当日查获违禁品统计
     * @return
     */
    @IgnoreUserToken
    @GetMapping("/information/todayContraband")
    public ObjectRestResponse todayContraband(@RequestParam(defaultValue = "") String departId){
        List<Map<String,Object>> departs=checkPointCtr.getpoint
                (departId,BaseContextHandler.getUserID());
        List<String> pointIds=departs.stream().map(o->o.get("id").toString()).collect(Collectors.toList());
        Map<String,Integer> resultMap = new HashMap<>();
        List<Map<String,String>> list = dealResultInfoBiz.todayContraband(pointIds);
        if(list != null && list.size() > 0){
            for(Map<String,String> map : list){
                if(map != null && !map.isEmpty()){
                    Object object = map.get("counts");

                    resultMap.put(map.get("flName"),Integer.parseInt(String.valueOf(object)));
                }else {
                    resultMap.put("管制器具",0);
                    resultMap.put("爆炸物品",0);
                    resultMap.put("枪支弹药",0);
                    resultMap.put("易燃易爆",0);
                    resultMap.put("腐蚀性物品",0);
                    resultMap.put("其他",0);
                }

            }
        }else{
            resultMap.put("管制器具",0);
            resultMap.put("爆炸物品",0);
            resultMap.put("枪支弹药",0);
            resultMap.put("易燃易爆",0);
            resultMap.put("腐蚀性物品",0);
            resultMap.put("其他",0);
        }
        Map<String,String> sumMap = dealResultInfoBiz.todayContrabandSum(pointIds);
        if(sumMap != null && !sumMap.isEmpty()){
            Object o = sumMap.get("sum");
            resultMap.put("总数",Integer.parseInt(String.valueOf(o)));

        }else{
            resultMap.put("总数",0);
        }

        return ObjectRestResponse.ok(resultMap);
    }
}
