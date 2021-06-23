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
import com.ts.spm.bizs.biz.stat.InnerCheckBiz;
import com.ts.spm.bizs.biz.stat.InnerCheckProblemBiz;
import com.ts.spm.bizs.biz.stat.InnerCheckProblemPhotoBiz;
import com.ts.spm.bizs.biz.stat.PersonCheckBiz;
import com.ts.spm.bizs.entity.stat.TblInnerCheck;
import com.ts.spm.bizs.entity.stat.TblInnerCheckProblem;
import com.ts.spm.bizs.entity.stat.TblInnerCheckProblemPerson;
import com.ts.spm.bizs.entity.stat.TblInnerCheckProblemPhoto;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import com.ts.spm.bizs.rest.admin.DepartController;
import com.ts.spm.bizs.rest.admin.UserController;
import com.ts.spm.bizs.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author luoyu
 * @Date 2020/6/11 17:52
 * @Version 1.0
 */
@RestController
@RequestMapping("innerCheck")
@CheckClientToken
@CheckUserToken
@Api(tags = "内部检查管理")
public class InnerCheckController extends BaseController<InnerCheckBiz, TblInnerCheck, String> {

    @Autowired
    private UserController userCtr;
    @Autowired
    private DepartController departCtr;
    @Autowired
    private CheckPointController checkPointCtr;
    @Autowired
    private PersonCheckBiz personCheckBiz;
    @Autowired
    private InnerCheckProblemPhotoBiz innerCheckProblemPhotoBiz;
    @Autowired
    private InnerCheckProblemBiz innerCheckProblemBiz;
    @Autowired
    private LogBiz logBiz;


    @ApiOperation("内部检查查询")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public TableResultResponse packageQuery(String departId, String startTime, String endTime, String checkName,String hasProb,String probSource,String checkType,
                                            @RequestParam(name = "limit", defaultValue = "10") int limit,
                                            @RequestParam(name = "page", defaultValue = "1") int page){

        List<String> pointIds = getUserPointId(departId);
        Page<Object> result = PageHelper.startPage(page, limit);
        List<TblInnerCheck> list=baseBiz.getInnerCheckList(pointIds,startTime,endTime,checkName,hasProb,probSource,checkType);

        logBiz.saveLog("内部检查管理","内部检查查询", "api/stat/innerCheck/query");

        return new TableResultResponse(result.getTotal(),list);
    }


    @IgnoreClientToken
    @IgnoreUserToken
    @ApiOperation("内部检查添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ObjectRestResponse add(@RequestBody TblInnerCheck tblInnerCheck){
        Integer checkId=baseBiz.getId();
        tblInnerCheck.setCheckId(checkId);
        tblInnerCheck.setPointName(checkPointCtr.getPointInfo(tblInnerCheck.getPointId()).get("name"));
//        int id=baseBiz.addInnerCheck(tblInnerCheck);
        int id=baseBiz.insertSelective2(tblInnerCheck);
        if(tblInnerCheck.getHasProb()&&tblInnerCheck.getTblInnerCheckProblemList()!=null){
            for (TblInnerCheckProblem tblInnerCheckProblem:tblInnerCheck.getTblInnerCheckProblemList()){
                Integer problemId= innerCheckProblemBiz.getId();
                tblInnerCheckProblem.setProbId(problemId);
                tblInnerCheckProblem.setCheckId(checkId);
//                innerCheckProblemBiz.addInnerCheckProblem(checkId,tblInnerCheckProblem);
                innerCheckProblemBiz.insertSelective2(tblInnerCheckProblem);
                if (tblInnerCheck.getHasLackPerson()&& tblInnerCheckProblem.getTblInnerCheckProblemPersonList()!=null){
                    for (TblInnerCheckProblemPerson tblInnerCheckProblemPerson:tblInnerCheckProblem.getTblInnerCheckProblemPersonList()){
                        int personId=personCheckBiz.getId();
                        tblInnerCheckProblemPerson.setId(personId);
                        tblInnerCheckProblemPerson.setProbId(problemId);
                        tblInnerCheckProblemPerson.setCheckId(checkId);
                        personCheckBiz.insertSelective2(tblInnerCheckProblemPerson);
//                        personCheckBiz.addInnerCheckProblemPeson(checkId,problemId,tblInnerCheckProblemPerson);
                    }
                }

                if(tblInnerCheckProblem.getTblInnerCheckProblemPhotoList().size()>0){
                    for (TblInnerCheckProblemPhoto tblInnerCheckProblemPhoto:tblInnerCheckProblem.getTblInnerCheckProblemPhotoList()){
                        int photoId=innerCheckProblemPhotoBiz.getId();
                        tblInnerCheckProblemPhoto.setPicId(photoId);
                        tblInnerCheckProblemPhoto.setProbId(problemId);
                        tblInnerCheckProblemPhoto.setCheckId(checkId);
                        innerCheckProblemPhotoBiz.insertSelective2(tblInnerCheckProblemPhoto);
//                        innerCheckProblemPhotoBiz.addInnerCheckProblemPhoto(checkId,problemId,tblInnerCheckProblemPhoto);
                    }
                }
            }

        }

        return ObjectRestResponse.ok();
    }

    @ApiOperation("内部检查编辑")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ObjectRestResponse update(@PathVariable String id,@RequestBody TblInnerCheck tblInnerCheck){
        Integer checkId=Integer.parseInt(id);
        tblInnerCheck.setPointName(checkPointCtr.getPointInfo(tblInnerCheck.getPointId()).get("name"));
        if(tblInnerCheck.getHasProb()&&tblInnerCheck.getTblInnerCheckProblemList()!=null){
            List<TblInnerCheckProblem> problemList=innerCheckProblemBiz.getProblemByCheckId(checkId);
            if(problemList.size()>0){
                logBiz.saveLog("内部检查管理","内部检查问题删除", "api/stat/innerCheck/ProblemDelete");
                innerCheckProblemBiz.deleInnerCheckProblem(checkId);
                for(TblInnerCheckProblem pList:problemList){
                    List<TblInnerCheckProblemPerson> personList=personCheckBiz.getPersonByCheckId(checkId,pList.getProbId());
                    List<TblInnerCheckProblemPhoto> photoList=innerCheckProblemPhotoBiz.getPhotoByCheckID(checkId,pList.getProbId());
                    if(personList.size()>0){
                        personCheckBiz.deleInnerCheckProblemPeson(checkId);
                        logBiz.saveLog("内部检查管理","内部检查人员删除", "api/stat/innerCheck/PersonDelete");
                    }
                    if(photoList.size()>0){
                        innerCheckProblemPhotoBiz.deleInnerCheckProblemPhoto(checkId);
                        logBiz.saveLog("内部检查管理","内部检查图片删除", "api/stat/innerCheck/PhotoDlete");
                    }
                }
            }
            for (TblInnerCheckProblem tblInnerCheckProblem:tblInnerCheck.getTblInnerCheckProblemList()){
                Integer problemId= innerCheckProblemBiz.getId();
                tblInnerCheckProblem.setProbId(problemId);
                tblInnerCheckProblem.setCheckId(checkId);
                innerCheckProblemBiz.insertSelective2(tblInnerCheckProblem);
                logBiz.saveLog("内部检查管理","内部检查问题添加", "api/stat/innerCheck/ProblemAdd");
                if (tblInnerCheck.getHasLackPerson()&& tblInnerCheckProblem.getTblInnerCheckProblemPersonList()!=null){
                    for (TblInnerCheckProblemPerson tblInnerCheckProblemPerson:tblInnerCheckProblem.getTblInnerCheckProblemPersonList()){
                        int personId=personCheckBiz.getId();
                        tblInnerCheckProblemPerson.setId(personId);
                        tblInnerCheckProblemPerson.setProbId(problemId);
                        tblInnerCheckProblemPerson.setCheckId(checkId);
                        personCheckBiz.insertSelective2(tblInnerCheckProblemPerson);
                        logBiz.saveLog("内部检查管理","内部检查人员添加", "api/stat/innerCheck/PersonAdd");
                    }
                }
                if(tblInnerCheckProblem.getTblInnerCheckProblemPhotoList().size()>0){
                    for (TblInnerCheckProblemPhoto tblInnerCheckProblemPhoto:tblInnerCheckProblem.getTblInnerCheckProblemPhotoList()){
                        int photoId=innerCheckProblemPhotoBiz.getId();
                        tblInnerCheckProblemPhoto.setPicId(photoId);
                        tblInnerCheckProblemPhoto.setProbId(problemId);
                        tblInnerCheckProblemPhoto.setCheckId(checkId);
                        innerCheckProblemPhotoBiz.insertSelective2(tblInnerCheckProblemPhoto);
                        logBiz.saveLog("内部检查管理","内部检查图片添加", "api/stat/innerCheck/PhotoAdd");
                    }
                }
            }

        }
        baseBiz.updateSelectiveById(tblInnerCheck);
        logBiz.saveLog("内部检查管理","内部检查", "api/stat/innerCheck/update");

        return ObjectRestResponse.ok();
    }

    @ApiOperation("内部检查删除")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse delete(@PathVariable String id){
        Integer checkId=Integer.parseInt(id);
        baseBiz.deleteById(checkId);
        logBiz.saveLog("内部检查管理","内部检查删除", "api/stat/innerCheck/delete");
        innerCheckProblemBiz.deleInnerCheckProblem(checkId);
        logBiz.saveLog("内部检查管理","内部检查问题删除", "api/stat/innerCheck/ProblemDlete");
        innerCheckProblemPhotoBiz.deleInnerCheckProblemPhoto(checkId);
        logBiz.saveLog("内部检查管理","内部检查图片删除", "api/stat/innerCheck/PhotoDlete");
        personCheckBiz.deleInnerCheckProblemPeson(checkId);
        logBiz.saveLog("内部检查管理","内部检查人员删除", "api/stat/innerCheck/Persondelete");

        return ObjectRestResponse.ok();
    }

    @ApiOperation("内部检查获取详情")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public ObjectRestResponse detail(@PathVariable String id){
        Integer checkId=Integer.parseInt(id);

        TblInnerCheck tblInnerCheck=baseBiz.selectById(checkId);
        tblInnerCheck.setStationId(checkPointCtr.getDepartInfo(tblInnerCheck.getPointId()).get("id"));
        tblInnerCheck.setStationName(checkPointCtr.getDepartInfo(tblInnerCheck.getPointId()).get("name"));
        List<TblInnerCheckProblem> problemList=innerCheckProblemBiz.getProblemByCheckId(checkId);
        if ( problemList.size()>0 ){
            for (TblInnerCheckProblem problem:problemList){
                List<TblInnerCheckProblemPerson> personList=personCheckBiz.getPersonByCheckId(checkId,problem.getProbId());
                List<TblInnerCheckProblemPhoto> photoList=innerCheckProblemPhotoBiz.getPhotoByCheckID(checkId,problem.getProbId());
                problem.setTblInnerCheckProblemPersonList(personList);
                problem.setTblInnerCheckProblemPhotoList(photoList);
            }
            tblInnerCheck.setTblInnerCheckProblemList(problemList);
        }
        logBiz.saveLog("内部检查管理","内部检查获取详情", "api/stat/innerCheck/detail");

        return ObjectRestResponse.ok(tblInnerCheck);
    }

    @ApiOperation("内部检查统计（环比、同比、部门）")
    @RequestMapping(value = "/statisByDepart", method = RequestMethod.GET)
    public ObjectRestResponse statisByDepart(String departId,String startTime,String endTime,String cmpType,String departType){
        List<String> pointIds=new ArrayList<String>();
        if(!"".equals(departId)&&departId!=null){
            pointIds=checkPointCtr.getAllPointByDepart(departId);
        }else {
            pointIds=userCtr.getPoint(BaseContextHandler.getUserID());
        }
        System.out.println(pointIds.size());
        List<String> departIds=new ArrayList<String>();
        if(!"".equals(departId)&&departId!=null){
            List<Map<String,String>> departMap=departCtr.getDepartChildren(departId);
            departIds=departMap.stream().map(u->u.get("id")).collect(Collectors.toList());

        }else{
            List<Map<String,String>> departMap=userCtr.getStation(BaseContextHandler.getUserID());
            departIds=departMap.stream().map(u->u.get("id")).collect(Collectors.toList());
        }

        Date startDate=new Date();
        if(!"".equals(startTime)&&startTime!=null){
            startDate= DateUtil.parse(startTime+" 00:00:00","yyyy-MM-dd hh:mm:ss");
        }

        Date endDate=new Date();
        if(!"".equals(endTime)&&endTime!=null){
            endDate= DateUtil.parse(endTime+" 23:59:59","yyyy-MM-dd hh:mm:ss");
        }

        Date timePeriod[] = new Date[8];
        if(!"".equals(cmpType)&&cmpType!=null){
            if(cmpType.equals("near")) timePeriod=DateUtil.getNearNTime(4,startDate,endDate);
            if(cmpType.equals("next")) timePeriod=DateUtil.getNextNTime(4,startDate,endDate);
        }

        List<Map<String,Object>>[] rateList=new ArrayList[4];
        for(int i=0; i<4; i++){
            if(departType.equals("byDepart")){
                rateList[i]=baseBiz.statisByDepart(departIds,pointIds,timePeriod[i],timePeriod[i+4]);
            }
            if(departType.equals("byPoint")){
                rateList[i]=baseBiz.statisByPoint(pointIds,timePeriod[i],timePeriod[i+4]);
            }

        }
        logBiz.saveLog("内部检查管理","内部检查统计（环比、同比、部门）", "api/stat/innerCheck/statisByDepart");

        return ObjectRestResponse.ok(rateList);
    }

    @RequestMapping(value = "testList", method = RequestMethod.GET)
    public ObjectRestResponse testList(){
        Map resultList=new HashMap();
        ObjectRestResponse oldList=statisByDepart("","2020-08-01","2020-09-30","near","byDepart");
        if(oldList.getData() != null) {
            List<Map<String,Object>>[] rateList = (List<Map<String,Object>>[])oldList.getData();
            for(int j=0;j<rateList.length;j++){
                List list1=new ArrayList();
                List list2=new ArrayList();
                List list3=new ArrayList();
                List list4=new ArrayList();
                List list5=new ArrayList();
                List<Map<String,Object>> i=rateList[j];
                for(Map<String,Object> map:i){
                       list1.add(map.get("count"));
                       list2.add(map.get("name"));
                       list3.add(map.get("startTime"));
                       list4.add(map.get("id"));
                       list5.add(map.get("endTime"));

                }
                resultList.put("count",list1);
                resultList.put("name",list2);
                resultList.put("startTime",list3);
                resultList.put("id",list4);
                resultList.put("endTime",list5);

            }

        }
        return ObjectRestResponse.ok(resultList);
    }

    @ApiOperation("内部检查统计（类型）")
    @RequestMapping(value = "/statisByType", method = RequestMethod.GET)
    public ObjectRestResponse statisByType(String departId,String startTime,String endTime){
        List<String> pointIds=new ArrayList<String>();
        if(!"".equals(departId)&&departId!=null){
            pointIds=checkPointCtr.getAllPointByDepart(departId);
        }else {
            pointIds=userCtr.getPoint(BaseContextHandler.getUserID());
        }
        if(pointIds.size()<=0){
            pointIds.add("");
        }

        logBiz.saveLog("内部检查管理","内部检查统计（类型）", "api/stat/innerCheck/statisByType");
        return ObjectRestResponse.ok(baseBiz.statisByType(pointIds,startTime,endTime));
    }

    @ApiOperation("获取所有符合查询条件的内部检查列表")
    @RequestMapping(value = "/innerCheckList", method = RequestMethod.GET)
    public TableResultResponse getInnerCheckList(String departId, String startTime, String endTime, String checkName,String hasProb,String probSource,String checkType){
        List<String> pointIds = getUserPointId(departId);
        List<TblInnerCheck> list=baseBiz.getInnerCheckList(pointIds,startTime,endTime,checkName,hasProb,probSource,checkType);
        logBiz.saveLog("内部检查管理","获取所有符合查询条件的内部检查列表", "api/stat/innerCheck/innerCheckList");
        return new TableResultResponse<TblInnerCheck>(list.size(),list);
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
