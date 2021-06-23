package com.ts.spm.bizs.biz.stat;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.util.DateUtil;
import com.ts.spm.bizs.entity.stat.TblInnerCheck;
import com.ts.spm.bizs.entity.stat.TblInnerCheckProblem;
import com.ts.spm.bizs.entity.stat.TblInnerCheckProblemPerson;
import com.ts.spm.bizs.entity.stat.TblInnerCheckProblemPhoto;
import com.ts.spm.bizs.mapper.stat.TblInnerCheckMapper;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import com.ts.spm.bizs.rest.admin.DepartController;
import com.ts.spm.bizs.rest.admin.UserController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @Author luoyu
 * @Date 2020/6/12 10:25
 * @Version 1.0
 * updater 马居朝
 * 新增 public List<Map<String, Object>> statisByDepart(List<String> departIds, Date startTime, Date endTime){}
 * （原本的statisByDepart改成了statisByDepart1，不用了）
 */
@Service
public class InnerCheckBiz extends BusinessBiz<TblInnerCheckMapper, TblInnerCheck> {

    @Autowired
    private InnerCheckProblemBiz innerCheckProblemBiz;
    @Autowired
    private InnerCheckProblemPhotoBiz innerCheckProblemPhotoBiz;
    @Autowired
    private  PersonCheckBiz personCheckBiz;
    @Autowired
    private UserController userCtr;
    @Autowired
    private CheckPointController checkPointCtr;
    @Autowired
    private DepartController departCtr;

    public List<TblInnerCheck> getInnerCheckList(List<String> pointIds, String startTime, String endTime, String checkName,String hasProb,String probSource,String checkType){
        Example example = new Example(TblInnerCheck.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andIn("pointId",pointIds);

        if(!"".equals(startTime)&&startTime!=null&&!"".equals(endTime)&&endTime!=null){
            criteria.andBetween("checkTime", com.ts.spm.bizs.util.DateUtil.parse(startTime+" 00:00:00","yyyy-MM-dd HH:mm:ss"), com.ts.spm.bizs.util.DateUtil.parse(endTime+" 23:59:59","yyyy-MM-dd HH:mm:ss"));
        }

        if( StringUtils.isNotBlank(checkName)){
            criteria.andLike("checkName","%" + checkName + "%");
        }

        if( StringUtils.isNotBlank(checkType)){
            criteria.andEqualTo("checkType", checkType );
        }

        if(StringUtils.isNotBlank(hasProb)){
            criteria.andEqualTo("hasProb",hasProb);
        }

        if(StringUtils.isNotBlank(probSource)){
            criteria.andEqualTo("probSource",probSource);
        }

        example.setOrderByClause("CHECK_TIME desc");

        List<TblInnerCheck> list=selectByExample(example);

        for(TblInnerCheck tblInnerCheck:list){
            tblInnerCheck.setStationName(checkPointCtr.getDepartInfo(tblInnerCheck.getPointId()).get("name"));
            List<TblInnerCheckProblem> problemList=innerCheckProblemBiz.getProblemByCheckId(tblInnerCheck.getCheckId());
            if ( problemList.size()>0 ){
                for(TblInnerCheckProblem tblInnerCheckProblem:problemList){
                    List<TblInnerCheckProblemPhoto> tblInnerCheckProblemPhotoList=innerCheckProblemPhotoBiz.getPhotoByCheckID(tblInnerCheck.getCheckId(),tblInnerCheckProblem.getProbId());

                    tblInnerCheckProblem.setTblInnerCheckProblemPhotoList(tblInnerCheckProblemPhotoList);

                    List<TblInnerCheckProblemPerson> tblInnerCheckProblemPersonList=personCheckBiz.getPersonByCheckId(tblInnerCheck.getCheckId(),tblInnerCheckProblem.getProbId());

                    tblInnerCheckProblem.setTblInnerCheckProblemPersonList(tblInnerCheckProblemPersonList);

                }
            }

            tblInnerCheck.setTblInnerCheckProblemList(problemList);
        }
        return list;
    }

    public int addInnerCheck(TblInnerCheck tblInnerCheck){

        tblInnerCheck.setCrtTime(new Date());
        tblInnerCheck.setCrtUserId(BaseContextHandler.getUserID());
        tblInnerCheck.setCrtUserName(BaseContextHandler.getUsername());
        tblInnerCheck.setTenantId(BaseContextHandler.getTenantID());
        Integer checkId=mapper.addInnerCheck(tblInnerCheck);

        return checkId;
    }

    public List<Map<String, Object>> statisByType(List<String> pointIds, String startTime, String endTime){
        return mapper.statisByType(pointIds, startTime,endTime);
    }

    public List<Map<String, Object>> statisByDepart1(List<String> departIds, Date startTime, Date endTime){
        List<Map<String,Object>> rst = new ArrayList();
        if(departIds.size()>0){
            for(String departId:departIds){
                List<String> points=checkPointCtr.getAllPointByDepart(departId);
                if(points.size()<=0){
                    points.add("");
                }
                Map<String,Object> map=new HashMap<>();

//                List<Map<String,String>> list=mapper.statisByDepart(points, DateUtil.date2Str(startTime,"yyyy-MM-dd"),DateUtil.date2Str(endTime,"yyyy-MM-dd"));

                map.put("count",mapper.countByPoint(points, DateUtil.date2Str(startTime,"yyyy-MM-dd"),DateUtil.date2Str(endTime,"yyyy-MM-dd")));
                map.put("id",departId);
                map.put("name",departCtr.getStationDetail(departId).get("name"));
                map.put("startTime",startTime);
                map.put("endTime",endTime);
//                for(Map<String,String> statisDepart:list){
//                    if(statisDepart.get("departId").equals(departId)){
//                        map.replace("count",statisDepart.get("count"));
//                        break;
//                    }
//                }


                rst.add(map);


            }

        }

        return rst;
    }
    public List<Map<String, Object>> statisByDepart(List<String> departIds,List<String> pointIds, Date startTime, Date endTime){
        List<Map<String,Object>> rst = new ArrayList();
        if(departIds.size()>0){
//            for(String departId:departIds){
//                List<String> points=adminFeign.getAllPointByDepart(departId);
//                if(points.size()<=0){
//                    points.add("");
//                }
//                Map<String,Object> map=new HashMap<>();
//
////                List<Map<String,String>> list=mapper.statisByDepart(points, DateUtil.date2Str(startTime,"yyyy-MM-dd"),DateUtil.date2Str(endTime,"yyyy-MM-dd"));
//
//                map.put("count",mapper.countByPoint(points, DateUtil.date2Str(startTime,"yyyy-MM-dd"),DateUtil.date2Str(endTime,"yyyy-MM-dd")));
//                map.put("id",departId);
//                map.put("name",adminFeign.getStationDetail(departId).get("name"));
//                map.put("startTime",startTime);
//                map.put("endTime",endTime);
//                rst.add(map);
//            }
            List<Map<String,String>> list=mapper.statisByDepart(pointIds, DateUtil.date2Str(startTime,"yyyy-MM-dd"),DateUtil.date2Str(endTime,"yyyy-MM-dd"));
            for(String departId:departIds){
                Map<String,Object> map=new HashMap<>();
                map.put("count",0);
                map.put("id",departId);
                map.put("name",departCtr.getStationDetail(departId).get("name"));
                map.put("startTime",startTime);
                map.put("endTime",endTime);
                rst.add(map);
                for(Map<String,String> resultList:list){
                    if(resultList.get("departId").equals(departId)){
                        map.replace("count",map.get("count"),resultList.get("count"));
                    }

                }
            }

        }else{
            Map<String,Object> map=new HashMap<>();
            map.put("count",0);
            map.put("id","");
            map.put("name","");
            map.put("startTime",startTime);
            map.put("endTime",endTime);
            rst.add(map);
        }

        return rst;
    }


    public List<Map<String, Object>> statisByPoint(List<String> pointIds, Date startTime, Date endTime){
        List<Map<String,Object>> rst = new ArrayList();

        if(pointIds.size()>0){
            List<Map<String,String>> list=mapper.statisByPoint(pointIds, DateUtil.date2Str(startTime,"yyyy-MM-dd"),DateUtil.date2Str(endTime,"yyyy-MM-dd"));
            for(String pointId:pointIds){
                Map<String,Object> map=new HashMap<>();
                map.put("count",0);
                map.put("id",pointId);
                map.put("name",checkPointCtr.getDepartInfo(pointId).get("name")+"-"+checkPointCtr.getPointInfo(pointId).get("name"));
                map.put("startTime",startTime);
                map.put("endTime",endTime);
                rst.add(map);
                for(Map<String,String> resultList:list){
                    if(resultList.get("pointId").equals(pointId)){
                        map.replace("count",map.get("count"),resultList.get("count"));
                    }

                }
            }
        }else{
            Map<String,Object> map=new HashMap<>();
            map.put("count",0);
            map.put("id","");
            map.put("name","");
            map.put("startTime",startTime);
            map.put("endTime",endTime);
            rst.add(map);
        }

        return rst;
    }

    public int countByPoint(List<String> pointIds, Date startTime, Date endTime){
        if(pointIds.size()>0){
            return mapper.countByPoint(pointIds,DateUtil.date2Str(startTime,"yyyy-MM-dd"), DateUtil.date2Str(endTime,"yyyy-MM-dd"));
        }else{
            return 0;
        }
    }

    public int getId(){
        return mapper.getId();
    }
}
