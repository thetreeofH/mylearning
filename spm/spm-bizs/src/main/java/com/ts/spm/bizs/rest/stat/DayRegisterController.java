package com.ts.spm.bizs.rest.stat;

import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ts.spm.bizs.biz.LogBiz;
import com.ts.spm.bizs.biz.stat.DayProblemBiz;
import com.ts.spm.bizs.biz.stat.DayRegisterBiz;
import com.ts.spm.bizs.entity.stat.DayProblem;
import com.ts.spm.bizs.entity.stat.DayRegister;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import com.ts.spm.bizs.rest.admin.CompanyController;
import com.ts.spm.bizs.rest.admin.DepartController;
import com.ts.spm.bizs.rest.admin.UserController;
import com.ts.spm.bizs.util.DateUtil;
import com.ts.spm.bizs.util.PoiUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author luoyu
 * @Date 2020/6/3 11:37
 * @Version 1.0
 */
@RestController
@RequestMapping("dayRegister")
@CheckClientToken
@CheckUserToken
@Api(tags = "日常督导管理")
public class DayRegisterController extends BaseController<DayRegisterBiz, DayRegister, String> {
    @Autowired
    private DayProblemBiz dayProblemBiz;
    @Autowired
    private CompanyController companyCtr;
    @Autowired
    private UserController userCtr;
    @Autowired
    private CheckPointController checkPointCtr;
    @Autowired
    private DepartController departCtr;
    @Autowired
    LogBiz logBiz;


    @ApiOperation("日常督导查询")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public TableResultResponse dayRegisterQuery(String departId,String startTime,String endTime,String probType,String checkOrg,String checker,
                                               String checkType,String operatingCompanyId,String securityCompanyId,
                                               @RequestParam(name = "limit", defaultValue = "10") int limit,
                                               @RequestParam(name = "page", defaultValue = "1") int page){

        List<String> pointIds = getUserPointId(departId);
        Page<Object> result = PageHelper.startPage(page, limit);

        List<Map> list=baseBiz.getDayRegisterList(pointIds,startTime,endTime,probType,checkOrg,checker,checkType,operatingCompanyId,securityCompanyId);

        logBiz.saveLog("日常督导管理","日常督导查询", "api/stat/dayRegister/query");

        return new TableResultResponse(result.getTotal(),list);
    }

    @ApiOperation("日常督导列表")
    @RequestMapping(value = "/queryList", method = RequestMethod.GET)
    public TableResultResponse dayRegisterQueryList(String departId,String startTime,String endTime,String probType,String checkOrg,String checker,String checkType,String operatingCompanyId,String securityCompanyId){

        List<String> pointIds = getUserPointId(departId);
        List<Map> list = baseBiz.getDayRegisterList(pointIds,startTime,endTime,probType,checkOrg,checker,checkType,operatingCompanyId,securityCompanyId);

        logBiz.saveLog("日常督导管理","日常督导列表", "api/stat/dayRegister/queryList");

        return new TableResultResponse(list.size(),list);
    }

    @ApiOperation("日常督导统计(总)")
    @RequestMapping(value = "/statisAll", method = RequestMethod.GET)
    public ObjectRestResponse dayRegisterAllStatistics(String departId,String startTime,String endTime,String cmpType,String rateType){

        List<String> pointIds=new ArrayList<String>();
        if(!"".equals(departId)&&departId!=null){
            pointIds=checkPointCtr.getAllPointByDepart(departId);
        }else {
            pointIds=userCtr.getPoint(BaseContextHandler.getUserID());
        }
        if(pointIds.size()<=0){
            pointIds.add("");
        }

        List<String> departIds=new ArrayList<String>();
        if(!"".equals(departId)&&departId!=null){
            List<Map<String,String>> departMap=departCtr.getDepartChildren(departId);
            if(departMap.size()<=0){
                departIds.add(departId);
            }else{
                departIds=departMap.stream().map(u->u.get("id")).collect(Collectors.toList());
            }

        }else{
            List<Map<String,String>> departMap=userCtr.getStation(BaseContextHandler.getUserID());
            departIds=departMap.stream().map(u->u.get("id")).collect(Collectors.toList());
        }

        Date startDate=null;
        if(!"".equals(startTime)&&startTime!=null){
            startDate= DateUtil.parse(startTime+" 00:00:00","yyyy-MM-dd hh:mm:ss");
        }

        Date endDate=null;
        if(!"".equals(endTime)&&endTime!=null){
            endDate= DateUtil.parse(endTime+" 23:59:59","yyyy-MM-dd hh:mm:ss");
        }

        Map<String,Object> map=new HashMap<String,Object>();
//        map.put("problemRate",baseBiz.getProblemRate(departIds,pointIds,startTime,endTime));
//        map.put("seizedRate",baseBiz.getSeizedRate(pointIds,startTime,endTime));
//        map.put("undetectedRate",baseBiz.getUndetectedRate(pointIds,startTime,endTime));
//        map.put("onJobRate",baseBiz.getOnJobRate(pointIds,startTime,endTime));
//        map.put("certifiedRate",baseBiz.getCertifiedRate(pointIds,startTime,endTime));
        map.put("problemRate",baseBiz.getProblemRateByDepart(departIds,startTime,endTime));
        map.put("seizedRate",baseBiz.getSeizedRateByDepart(departIds,startTime,endTime));
        map.put("undetectedRate",baseBiz.getUndetectedRateByDepart(departIds,startTime,endTime));
        map.put("onJobRate",baseBiz.getOnJobRateByDepart(departIds,startTime,endTime));
        map.put("certifiedRate",baseBiz.getCertifiedRateByDepart(departIds,startTime,endTime));

        Date timePeriod[] = new Date[4];
        if(!"".equals(cmpType)&&cmpType!=null){
            if(cmpType.equals("near")) timePeriod=DateUtil.getNearNTime(2,startDate,endDate);
            if(cmpType.equals("next")) timePeriod=DateUtil.getNextNTime(2,startDate,endDate);
        }

        //以车站为个数封装list,每个车站四个周期数据
        List<Map<String,Object>>[] rateList=new ArrayList[2];

        for(int i=0; i<2; i++){
            rateList[i]=baseBiz.getStatisticList(departIds,timePeriod[i], timePeriod[i+2],rateType);
        }

        map.put("rateList",rateList);

        logBiz.saveLog("日常督导管理","日常督导统计(总)", "api/stat/dayRegister/statisAll");

        return ObjectRestResponse.ok(map);
    }

    @ApiOperation("日常督导详情")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public ObjectRestResponse getDayRegisterById(@PathVariable String id){

        Map<String,Object> dataMap = new HashMap<String,Object>();

        DayRegister loNode = baseBiz.selectById(Integer.parseInt(id));
//        dataMap.put("checkOrg", loNode.getCheckOrg());
        dataMap.put("checkOrg", departCtr.getDepartDetail(loNode.getCheckOrg()).get("name"));

        Calendar c = Calendar.getInstance();
        c.setTime(loNode.getTime());
        String year=Integer.toString(c.get(Calendar.YEAR));
        dataMap.put("time",loNode.getTime());
        dataMap.put("departName", checkPointCtr.getDepartInfo(loNode.getPointId()).get("name"));
        dataMap.put("operatingCompanyName", departCtr.getDepartDetail(loNode.getOperatingCompany()).get("name"));
        dataMap.put("securityCompanyName", companyCtr.getCompanyDetail(loNode.getSecurityCompany()).get("name"));
        dataMap.put("pointName", checkPointCtr.getPointInfo(loNode.getPointId()).get("name"));
        dataMap.put("operatPerson", loNode.getOperatPerson());
        dataMap.put("operatSignPath", loNode.getOperatSignPath());
        dataMap.put("operatSignPath", "值班站长图片");

        dataMap.put("planCount", loNode.getPlanCount());
        dataMap.put("insertCount", loNode.getInsertCount());
        dataMap.put("securityPerson", loNode.getSecurityPerson());
        dataMap.put("securitySignPath", loNode.getSecuritySignPath());
        dataMap.put("securitySignPath", "安检班长图片");
        dataMap.put("realCount", loNode.getRealCount());
        dataMap.put("detectCount", loNode.getDetectCount());
        dataMap.put("cardCount", loNode.getCardCount());
        dataMap.put("manualCount", loNode.getInsertCount()-loNode.getDetectCount());

        dataMap.put("checkUser", loNode.getCheckUser());
        dataMap.put("checkSign",loNode.getSignPath());
        dataMap.put("checkSign", "图片");

        List<DayProblem> dayProblemList=dayProblemBiz.getDayProblemByRegisterId(id);
        dataMap.put("problemList",dayProblemList);

        logBiz.saveLog("日常督导管理","日常督导详情", "api/stat/dayRegister/detail");

        return ObjectRestResponse.ok(dataMap);
    }

    @ApiOperation("日常督导删除")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse deleDayRegister(@PathVariable String id){
        baseBiz.deleteById(id);
        dayProblemBiz.deleteDayProbelmByRegisterId(id);

        logBiz.saveLog("日常督导管理","日常督导删除", "api/stat/dayRegister/delete");

        return ObjectRestResponse.ok();
    }

    @ApiOperation("日常督导添加和修改")
    @RequestMapping(value = "/addAndUpdate", method = RequestMethod.POST)
    public ObjectRestResponse addAndUpdateDayRegister(@RequestBody DayRegister dayRegister){
        Integer registerId=dayRegister.getId();
        if(registerId!=null){
            dayRegister.setTime(new Date());
            baseBiz.updateSelectiveById(dayRegister);
            dayProblemBiz.deleteDayProbelmByRegisterId(registerId.toString());
            logBiz.saveLog("日常督导管理","日常督导修改", "api/stat/dayRegister/update");
        }else{
            registerId=baseBiz.getId();
            dayRegister.setId(registerId);
            dayRegister.setTime(new Date());
            dayRegister.setDelTag(0);
            baseBiz.insertSelective2(dayRegister);
            logBiz.saveLog("日常督导管理","日常督导添加", "api/stat/dayRegister/add");
        }
        if(dayRegister.getDayProblemList().size()>0){
            for(DayProblem dayProblem:dayRegister.getDayProblemList()){
                Integer dayProblemId=dayProblemBiz.getId();
                dayProblem.setProblemId(dayProblemId);
                dayProblem.setRegisterId(registerId);
                dayProblem.setCheckDate(new Date());
                dayProblem.setCheckUser(dayRegister.getCheckUser());
                dayProblem.setPointId(dayRegister.getPointId());
                dayProblemBiz.insertSelective2(dayProblem);
            }
            logBiz.saveLog("日常督导管理","日常督导问题添加", "api/stat/dayRegister/problemAdd");
        }
        return ObjectRestResponse.ok();
    }

    @ApiOperation("日常督导导出")
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public void contrabandExport(HttpServletResponse res, String departId,String startTime,String endTime,String probType,String checkOrg,String checker,
                                 String checkType,String operatingCompanyId,String securityCompanyId){

        try {
            String colHeads[] = { "序号", "检查单位","检查人","时间","车站","安检点","运营公司","安检公司","检查类型","应在岗人数","实际在岗人数","持证人数","对抗性检查携带物品件数","漏检件数","设备故障情况","其他问题或突出情况" };
            String keys[] = { "id", "checkOrg","checkUser", "time", "departName","pointName","operatingCompany","securityCompany","inspectionType","planCount","realCount","cardCount","insertCount","manualCount","faultCondition","otherProblems"};
            List<String> pointIds = getUserPointId(departId);
            List<Map> list=baseBiz.getDayCheck(pointIds,startTime,endTime,probType,checkOrg,checker,checkType,operatingCompanyId,securityCompanyId);

            PoiUtil.start_download(request,res,com.github.wxiaoqi.security.common.util.DateUtil.getCurrentDate()+".xls", list, colHeads, keys);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logBiz.saveLog("日常督导管理","日常督导导出", "api/stat/dayRegister/export");
    }

    private List<String> getUserPointId(String departId) {
        List<String> pointIds=new ArrayList<String>();
        if(!"".equals(departId)&&departId!=null){
            pointIds=checkPointCtr.getAllPointByDepart(departId);
        }else {
            pointIds=userCtr.getPoint(BaseContextHandler.getUserID());
        }
        if(pointIds.size()<=0){
            pointIds.add("");
        }
        return pointIds;
    }
}
