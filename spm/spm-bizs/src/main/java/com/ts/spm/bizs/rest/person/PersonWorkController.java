package com.ts.spm.bizs.rest.person;

import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ts.spm.bizs.biz.LogBiz;
import com.ts.spm.bizs.biz.person.PersonWorkBiz;
import com.ts.spm.bizs.entity.person.AjyWorkInfo;
import com.ts.spm.bizs.rest.admin.DepartController;
import com.ts.spm.bizs.rest.admin.UserController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author luoyu
 * @Date 2020/7/2 16:42
 * @Version 1.0
 */
@RestController
@RequestMapping("work")
@CheckClientToken
@CheckUserToken
@Api(tags = "安检员考勤管理")
public class PersonWorkController extends BaseController<PersonWorkBiz, AjyWorkInfo, String> {
    @Autowired
    private UserController userCtr;
    @Autowired
    private DepartController departCtr;
    @Autowired
    private LogBiz logBiz;

    @Value("${work.interday}")
    private boolean interDay;
    @Value("${work.interval}")
    private int interVal;
    @Value("${work.offtime}")
    private String offTime;

    @ApiOperation("考勤记录查询")
    @RequestMapping(value = "/attend/query", method = RequestMethod.GET)
    public TableResultResponse attendQuery(String departId, String startTime,String endTime, String idCard,
                                           String ajyName,String ifCard,String ifYoung,String workType,String ajyType,
                                           @RequestParam(name = "limit", defaultValue = "10") int limit,
                                           @RequestParam(name = "page", defaultValue = "1") int page){

        List<String> departIds = getUserDepartId(departId);
        Page<Object> result = PageHelper.startPage(page, limit);
        String ifDay="1";
        List<Map<String,String>> list=baseBiz.getPersonWorkList(departIds, startTime, endTime, idCard, ajyName, ifCard, ifYoung,ifDay,workType,ajyType);
        logBiz.saveLog("安检员考勤管理","考勤查询", "api/person/ajy/work/attend/query");

        return new TableResultResponse(result.getTotal(),list);
    }

    @ApiOperation("考勤统计")
    @RequestMapping(value = "/attend/statistic", method = RequestMethod.GET)
    public ObjectRestResponse attendStatistic(String departId, String time){
        Map<String,Object> statisList=new HashMap<String,Object>();

        List<Map<String,Object>> rst = new ArrayList();

        List<String> departIds= getUserDepartId(departId);

        int planCount=0;
        int onJobCount=0;
        int cardCount=0;
        int noCardCount=0;
        int minorCount=0;

        try {
            Map<String, Object> info = baseBiz.workcfg();
            if (info != null) {
                interDay = info.get("interDay").toString().equalsIgnoreCase("1");
                interVal = Integer.valueOf(info.get("interVal").toString());
                offTime = info.get("offTime").toString();
            }
        } catch (Exception e) {}

        if(departIds.size()>0){
            for(String dpId:departIds){
                Map<String,Object> ajyMap=baseBiz.personWorkStatistic(dpId, getUserDepartId(dpId), time, interDay, interVal, offTime);
                planCount+=(int)ajyMap.get("planCount");
                onJobCount+=(int)ajyMap.get("onJobCount");
                cardCount+=(int)ajyMap.get("cardCount");
                noCardCount+=(int)ajyMap.get("noCardCount");
                minorCount+=(int)ajyMap.get("minorCount");
                ajyMap.put("departId",dpId);
                ajyMap.put("departName",departCtr.getStationDetail(dpId).get("name"));
                rst.add(ajyMap);
            }
        }


        statisList.put("ajyWorkList",rst);

//        Map<String,Object> map=baseBiz.personWorkStatistic(departId, time);
        statisList.put("onJobCount",onJobCount);
        statisList.put("planCount",planCount);
        statisList.put("cardCount",cardCount);
        statisList.put("noCardCount",noCardCount);
        statisList.put("minorCount",minorCount);

        logBiz.saveLog("安检员考勤管理","考勤统计", "api/person/ajy/work/attend/statistic");

        return ObjectRestResponse.ok(statisList);
    }

    @ApiOperation("考勤统计详情")
    @RequestMapping(value = "/attend/statisticDetail", method = RequestMethod.GET)
    public TableResultResponse clockQuery(String departId, String time,@RequestParam(name = "limit", defaultValue = "10") int limit,
                                         @RequestParam(name = "page", defaultValue = "1") int page){
        List<String> departIds = getUserDepartId(departId);
        try {
            Map<String, Object> info = baseBiz.workcfg();
            if (info != null) {
                interDay = info.get("interDay").toString().equalsIgnoreCase("1");
                interVal = Integer.valueOf(info.get("interVal").toString());
                offTime = info.get("offTime").toString();
            }
        } catch (Exception e) {}
        Page<Map<String,Object>> result = PageHelper.startPage(page, limit);
//        List<Map<String,Object>> ajyList=baseBiz.personWorkQuery(departIds, time);
        List<Map<String,Object>> ajyList = baseBiz.getOnWorkData(departIds, time, interDay, interVal, offTime);

        logBiz.saveLog("安检员考勤管理","考勤统计详情", "api/person/ajy/work/attend/statisticDetail");
        return new TableResultResponse(result.getTotal(),ajyList);
    }
    @ApiOperation("工作时长查询")
    @RequestMapping(value = "/attend/getWorkTime", method = RequestMethod.GET)
    public TableResultResponse getWorkTiem(String departId, String startTime,String endTime, @RequestParam(name = "limit", defaultValue = "10") int limit,
                                          @RequestParam(name = "page", defaultValue = "1") int page){
        List<String> departIds = getUserDepartId(departId);
        Page<Map<String,Object>> result = PageHelper.startPage(page, limit);
        List<Map<String,Object>> list=baseBiz.getPersonTime(departIds,startTime,endTime,"");
        logBiz.saveLog("安检员考勤管理","工作时长查询", "api/person/ajy/work/attend/getWorkTime");
        return new TableResultResponse(result.getTotal(),list);
    }

    private List<String> getUserDepartId(String departId) {
        List<String> departIds=new ArrayList<String>();
        if(!"".equals(departId)&&departId!=null){
            List<Map<String,String>> departMap=departCtr.getDepartChildren(departId);
            if(departMap.size()>0){
                departIds=departMap.stream().map(u->u.get("id")).collect(Collectors.toList());
            }else{
                departIds.add(departId);
            }
        }else {
            List<Map<String,String>> departMap=userCtr.getStation(BaseContextHandler.getUserID());
            departIds=departMap.stream().map(u->u.get("id")).collect(Collectors.toList());
        }
        return departIds;
    }
}
