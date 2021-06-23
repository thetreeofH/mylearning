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
import com.ts.spm.bizs.biz.stat.PackageBiz;
import com.ts.spm.bizs.entity.stat.PackageCount;
import com.ts.spm.bizs.rest.admin.CheckPointController;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author luoyu
 * @Date 2020/6/8 16:57
 * @Version 1.0
 */
@RestController
@RequestMapping("package")
@CheckClientToken
@CheckUserToken
@Api(tags = "过包数管理")
public class PackageController extends BaseController<PackageBiz, PackageCount, String> {

    @Autowired
    private UserController userCtr;
    @Autowired
    private CheckPointController checkPointCtr;
    @Autowired
    private DepartController departCtr;
    @Autowired
    private LogBiz logBiz;


    @ApiOperation("过包数查询")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public TableResultResponse packageQuery(String departId,String startTime,String endTime,
                                           @RequestParam(name = "limit", defaultValue = "10") int limit,
                                           @RequestParam(name = "page", defaultValue = "1") int page){

        List<String> pointIds = getUserPointId(departId);
        Page<Object> result = PageHelper.startPage(page, limit);
//        List<PackageCount> list=baseBiz.packageCountList(departId,startTime,endTime);
        List<Map> list=baseBiz.getPackageList(pointIds,startTime,endTime);

        logBiz.saveLog("过包数管理","过包数查询", "api/stat/package/query");

        return new TableResultResponse(result.getTotal(),list);
    }

    @ApiOperation("过包数统计（时间维度）")
    @RequestMapping(value = "/statisticsByTime", method = RequestMethod.GET)
    public ObjectRestResponse packageStatisticsByTime(String departId,String timeType,String startTime,String endTime){

        List<String> pointIds=new ArrayList<String>();
        if(!"".equals(departId)&&departId!=null){
            pointIds=checkPointCtr.getAllPointByDepart(departId);
        }else {
            pointIds=userCtr.getPoint(BaseContextHandler.getUserID());
        }
        if(pointIds.size()<=0){
            pointIds.add("");
        }

        SimpleDateFormat format  = new SimpleDateFormat("YYYY-MM-dd");

        if("".equals(startTime)||startTime==null){
            startTime=format.format(new Date());
        }
        if("".equals(endTime)||endTime==null){
            endTime=format.format(new Date());
        }

        List<Map<String,Object>> list=new ArrayList<>();
        if(timeType.equals("byHour")){
            List<String> timeList=DateUtil.findDaysStr(startTime,endTime);
            for(int i=0;i<timeList.size();i++){
                List<Map<String,Object>> hourList=baseBiz.statisByHour(pointIds,timeList.get(i),timeList.get(i));
                for(Map<String,Object> map:hourList){
                    list.add(map);
                }
            }
        }
        if(timeType.equals("byDay")){
            list=baseBiz.statisByDay(pointIds,startTime,endTime);
        }

        logBiz.saveLog("过包数管理","过包数统计（时间维度）", "api/stat/package/statisticsByTime");

        return ObjectRestResponse.ok(list);
    }

    @ApiOperation("过包数统计（小时维度）")
    @RequestMapping(value = "/statisticsByHour", method = RequestMethod.GET)
    public ObjectRestResponse packageStatisticsByHour(String departId,String startTime,String endTime){

        List<String> pointIds=new ArrayList<String>();
        if(!"".equals(departId)&&departId!=null){
            pointIds=checkPointCtr.getAllPointByDepart(departId);
        }else {
            pointIds=userCtr.getPoint(BaseContextHandler.getUserID());
        }
        if(pointIds.size()<=0){
            pointIds.add("");
        }

        logBiz.saveLog("过包数管理","过包数统计（小时维度）", "api/stat/package/statisticsByHour");

        return ObjectRestResponse.ok(baseBiz.statisByDaysHour(pointIds,startTime,endTime));
    }

    @ApiOperation("过包数统计（部门维度）")
    @RequestMapping(value = "/statisticsByDepart", method = RequestMethod.GET)
    public ObjectRestResponse packageStatisticsByDepart(String departId,String startTime,String endTime,String departType){

        List<String> pointIds=new ArrayList<String>();
        if(!"".equals(departId)&&departId!=null){
            pointIds=checkPointCtr.getAllPointByDepart(departId);
        }else {
            pointIds=userCtr.getPoint(BaseContextHandler.getUserID());
        }
        if(pointIds.size()<=0){
            pointIds.add("");
        }

        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        if(departType.equals("byPoint")){
            list=baseBiz.statisByPoint(pointIds,startTime,endTime);
            for(Map<String,String> statis:list){
                statis.put("name",checkPointCtr.getDepartInfo(statis.get("pointId")).get("name")+"-"+checkPointCtr.getPointInfo(statis.get("pointId")).get("name"));
            }
        }
        if(departType.equals("byDepart")){
            list=baseBiz.statisByDepart(pointIds,startTime,endTime);
            for(Map<String,String> statis:list){
                statis.put("name",departCtr.getStationDetail(statis.get("departId")).get("name"));
            }
        }

        logBiz.saveLog("过包数管理","过包数统计（部门维度）", "api/stat/package/statisticsByDepart");

        return ObjectRestResponse.ok(list);
    }

    @ApiOperation("过包数列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public TableResultResponse packageList(String departId, String startTime, String endTime){
        List<PackageCount> list=baseBiz.packageCountList(departId,startTime,endTime);
        logBiz.saveLog("过包数管理","过包数列表", "api/stat/package/list");

        return new TableResultResponse(list.size(),list);
    }

    @ApiOperation("过包数导出")
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public void packageExport(HttpServletResponse res,String departId, String startTime, String endTime){
        try {
            String colHeads[] = { "序号","车站","安检点","时间","检查物品数量" };
            String keys[] = { "index","stationName","pointName","time","count" };
            List<String> pointIds = getUserPointId(departId);
            List<Map> list=baseBiz.getPackageList(pointIds,startTime,endTime);

            PoiUtil.start_download(request,res,com.github.wxiaoqi.security.common.util.DateUtil.getCurrentDate()+".xls", list, colHeads, keys);
        } catch (Exception e) {
            e.printStackTrace();
        }

        logBiz.saveLog("过包数管理","过包数导出", "api/stat/package/export");
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
