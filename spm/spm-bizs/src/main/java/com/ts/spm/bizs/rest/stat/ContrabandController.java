package com.ts.spm.bizs.rest.stat;

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
import com.ts.spm.bizs.biz.LogBiz;
import com.ts.spm.bizs.biz.matter.PointDangerBiz;
import com.ts.spm.bizs.entity.matter.PointDanger;
import com.ts.spm.bizs.service.DictService;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import com.ts.spm.bizs.rest.admin.DepartController;
import com.ts.spm.bizs.rest.admin.UserController;
import com.ts.spm.bizs.rest.dict.DictValueController;
import com.ts.spm.bizs.util.DateUtil;
import com.ts.spm.bizs.util.PoiUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author luoyu
 * @Date 2020/6/8 17:10
 * @Version 1.0
 */
@RestController
@RequestMapping("contraband")
@CheckClientToken
@CheckUserToken
@Api(tags = "违禁品管理")
public class ContrabandController extends BaseController<PointDangerBiz, PointDanger, String> {

    @Autowired
    private DictValueController dictValueCtr;
    @Autowired
    private CheckPointController checkPointCtr;
    @Autowired
    private UserController userCtr;
    @Autowired
    private DepartController departCtr;

//    @Value("${picPath}")
//    private String ftpPath;

    @Autowired
    private DictService dictUtil;
    @Autowired
    LogBiz logBiz;

    @ApiOperation("违禁品查询")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public TableResultResponse contrabandQuery(String departId,String startTime, String endTime,String mType,String ajyName,
                                               String dangerId,String ajyType,String psgName,String psgIdno,Integer itemWay,
                                               @RequestParam(name = "limit", defaultValue = "10") int limit,
                                               @RequestParam(name = "page", defaultValue = "1") int page){

        List<String> pointIds = getUserPointId(departId);
        Page<Object> result = PageHelper.startPage(page, limit);
        List<PointDanger> list=baseBiz.pointDangerList(pointIds,startTime,endTime, mType,ajyName,dangerId,ajyType,psgName,psgIdno,itemWay);

        logBiz.saveLog("违禁品管理","违禁品查询", "api/stat/contraband/query");
        return new TableResultResponse(result.getTotal(),list);
    }

    @ApiOperation("违禁品统计(时间维度)")
    @RequestMapping(value = "/statistics/byTime", method = RequestMethod.GET)
    public ObjectRestResponse contrabandStatisticsbyTime(String departId, String startTime, String endTime,String timeType){
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
            List<String> timeList= DateUtil.findDaysStr(startTime,endTime);
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

        logBiz.saveLog("违禁品管理","违禁品统计(时间维度)", "api/stat/contraband/statistics/byTime");

        return ObjectRestResponse.ok(list);

    }

    @ApiOperation("违禁品统计(车站维度)")
    @RequestMapping(value = "/statistics/byDepart", method = RequestMethod.GET)
    public ObjectRestResponse contrabandStatisticsbyDepart(String departId,String departType, String startTime, String endTime){
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

        logBiz.saveLog("违禁品管理","违禁品统计(车站维度)", "api/stat/contraband/statistics/byDepart");

        return ObjectRestResponse.ok(list);
    }

    @ApiOperation("违禁品统计(类型维度)")
    @RequestMapping(value = "/statistics/byType", method = RequestMethod.GET)
    public ObjectRestResponse contrabandStatisticsbyType(String departId, String startTime, String endTime){
        List<String> pointIds=new ArrayList<String>();
        if(!"".equals(departId)&&departId!=null){
            pointIds=checkPointCtr.getAllPointByDepart(departId);
        }else {
            pointIds=userCtr.getPoint(BaseContextHandler.getUserID());
        }
        if(pointIds.size()<=0){
            pointIds.add("");
        }

        List<Map<String,String>> list=baseBiz.statisByType(pointIds,startTime,endTime);
        for(Map<String,String> statis:list){
            List<Map<String,Object>> dicvalueList=dictValueCtr.dictValueByCode("DOT");
            String type;
            for(Map<String,Object> dicvalue:dicvalueList){
                if(dicvalue.get("value").equals(statis.get("typeId")))
                    statis.put("typeName",dicvalue.get("labelZhCh").toString());
            }
        }

        logBiz.saveLog("违禁品管理","违禁品统计(类型维度)", "api/stat/contraband/statistics/byType");

        return ObjectRestResponse.ok(list);
    }

    @ApiOperation("违禁品列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public TableResultResponse contrabandList(String departId,String startTime, String endTime,String mType,String ajyName,
                                              String dangerId,String ajyType,String psgName,String psgIdno,Integer itemWay){

        List<String> pointIds = getUserPointId(departId);
        List<PointDanger> list=baseBiz.pointDangerList(pointIds,startTime,endTime,mType,ajyName,dangerId,ajyType,psgName,psgIdno,itemWay);

        logBiz.saveLog("违禁品管理","违禁品列表", "api/stat/contraband/list");

        return new TableResultResponse(list.size(),list);

    }

    @ApiOperation("违禁品导出")
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public void contrabandExport(HttpServletRequest request,HttpServletResponse res, String departId, String startTime, String endTime, String mType, String ajyName,
                                 String dangerId, String ajyType, String psgName, String psgIdno,Integer itemWay){
        try {
            String colHeads[] = { "序号", "车站","安检点", "时间", "违禁品类型","违禁品子类型", "违禁品编号","处置方式", "安检员姓名","安检员身份证号","乘客姓名","乘客身份证号","受理民警姓名","受理民警身份证号","物品处理结果","物品数量","备注" };
            String keys[] = { "id", "departName","pointName", "time", "mtype","stype","dno","itemWay","ajyName","ajyIdno","psgName","psgIdno","pceName","pceIdno","itemResult","dangerCount","note"};
            List<Map> pointDangerList=baseBiz.contrabandList(departId,startTime,endTime, mType,ajyName,dangerId,ajyType,psgName,psgIdno,itemWay);

            PoiUtil.start_download(request,res,com.github.wxiaoqi.security.common.util.DateUtil.getCurrentDate()+".xlsx", pointDangerList, colHeads, keys);
        } catch (Exception e) {
            e.printStackTrace();
        }

        logBiz.saveLog("违禁品管理","违禁品导出", "api/stat/contraband/export");
    }

    @IgnoreClientToken
    @IgnoreUserToken
    @ApiOperation("图片预览（相对路径）")
    @GetMapping(value = "/getPicPath/{filepath}/**")
    public void download(@PathVariable String filepath, HttpServletRequest request, HttpServletResponse resp) {
        String picPath =dictUtil.dangerPath();
        System.out.println(picPath);

        final String path =
                request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE).toString();
        final String bestMatchingPattern =
                request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString();
        String arguments = new AntPathMatcher().extractPathWithinPattern(bestMatchingPattern, path);
        String filePath;
        if (null != arguments && !arguments.isEmpty()) {
            filePath = filepath + '/' + arguments;
        } else {
            filePath = filepath;
        }
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        String lx = fileName.substring(fileName.lastIndexOf(".") + 1);
        if(lx.length() > 0) {
            switch (lx) {
                case "jpg":
                    resp.setContentType("image/jpeg");
                    break;
                case "png":
                    resp.setContentType("image/png");
                    break;
                case "gif":
                    resp.setContentType("image/gif");
                    break;
                case "bmp":
                    resp.setContentType("application/x-bmp");
                    break;
                default:
                    resp.setContentType("image/jpeg");
            }

            try {
                resp.reset();
                FileInputStream is = new FileInputStream(picPath+"/" + filePath);
                OutputStream os=resp.getOutputStream();

                byte[] imgByte = new byte[is.available()];
                is.read(imgByte);
                os.write(imgByte);
                os.close();
                is.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }

//        logBiz.saveLog("违禁品管理","图片预览（相对路径）", "api/stat/contraband/getPicPath/{filepath}/**");

    }

    @IgnoreClientToken
    @IgnoreUserToken
    @ApiOperation("图片预览（绝对路径）")
    @GetMapping(value = "/getPicAllPath")
    public void downloadPic(@RequestParam String filepath, HttpServletRequest request, HttpServletResponse resp) {
        try {
            resp.reset();
            File file = new File(filepath);
            FileInputStream is = new FileInputStream(file);
            OutputStream os=resp.getOutputStream();

            byte[] imgByte = new byte[is.available()];
            is.read(imgByte);
            os.write(imgByte);
            os.close();
            is.close();
        }catch(Exception e){
            e.printStackTrace();
        }

//        logBiz.saveLog("违禁品管理","图片预览（绝对路径）", "api/stat/contraband/getPicAllPath");

    }

    private List<String> getUserPointId(String departId) {
        List<String> pointIds = new ArrayList<String>();
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
