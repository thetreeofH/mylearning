package com.ts.spm.bizs.biz.matter;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.matter.PointDanger;
import com.ts.spm.bizs.mapper.matter.PointDangerMapper;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import com.ts.spm.bizs.rest.admin.DepartController;
import com.ts.spm.bizs.rest.admin.UserController;
import com.ts.spm.bizs.service.DictService;
import com.ts.spm.bizs.util.DateUtil;
import com.ts.spm.bizs.vo.stat.StatTypeCntVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author luoyu
 * @Date 2020/6/4 17:45
 * @Version 1.0
 * updater 马居朝
 * 新增Map  :selectById(){}
 */
@Service
public class PointDangerBiz extends BusinessBiz<PointDangerMapper, PointDanger>{
    @Autowired
    private CheckPointController checkPointCtr;
    @Autowired
    private UserController userCtr;
    @Autowired
    private DepartController departCtr;
    @Autowired
    private DictService dictUtil;

    public List<StatTypeCntVo> statis(Date start, Date end, List<String> pointIds){
        return mapper.statis(start, end, pointIds);
    }

    public Integer getId() {
        return mapper.getId();
    }
    public Integer getDno() {
        return mapper.getDno();
    }

    public PointDanger getById(Integer id) {
        return mapper.getById(id);
    }

    public Map<String,String> selectById(String id){
        return mapper.selectById(id);
    }

    //stat use
    public List<Map<String,Object>> statisByHour(List<String> pointIds, String startTime, String endTime){
        List<Map<String, Object>> list=new ArrayList<>();

        for(int j=01;j<21;j++){
            Map<String, Object> map=new HashMap<>();
            map.put("axisTime",Integer.toString(j+3));
            map.put("count",0);
            list.add(map);
        }
        List<Map<String, String>> hourList=mapper.statisByHour(pointIds, startTime, endTime);
        if(hourList.size()>0){
            for(Map<String, String> hourMap:hourList){
                Integer h = Integer.parseInt(hourMap.get("axisTime")); //时间
                if (h >= 4 && h <= 23){
                    Map<String,Object> map1=list.get(h-4);
                    map1.replace("count",map1.get("count"),hourMap.get("count"));
                }
            }
        }

        for(Map<String,Object> map:list){
            try {
                Integer h = Integer.parseInt(map.get("axisTime").toString()) ;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(startTime);
                String day= DateFormatUtils.format(date, "MM-dd");
//                if(h==4){
                map.replace("axisTime",map.get("axisTime"),day+" "+map.get("axisTime"));
//                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    //stat use
    public List<Map<String,Object>> statisByDay(List<String> pointIds, String startTime, String endTime){
        List<Map<String, Object>> list=new ArrayList<>();

        List<String> timeList= DateUtil.findDaysStr(startTime,endTime);
        for(String time:timeList){
            Map<String, Object> map=new HashMap<>();
            map.put("axisTime",time);
            map.put("count","0");
            list.add(map);
            List<Map<String, String>> dayList=mapper.statisByDay(pointIds, time, time);
            if(dayList.size()>0){
                for(Map<String, String> dayMap:dayList){
                    String day = dayMap.get("axisTime"); //时间
                    map.replace("count",map.get("count"),dayMap.get("count"));
                }
            }
        }
        return list;
    }

    //stat use
    public List<Map<String,String>> statisByPoint(List<String> pointIds, String startTime, String endTime){
        return mapper.statisByPoint(pointIds, startTime, endTime);
    }

    //stat use
    public List<Map<String,String>> statisByDepart(List<String> pointIds, String startTime, String endTime){
        return mapper.statisByDepart(pointIds, startTime, endTime);
    }

    //stat use
    public List<Map<String,String>> statisByType(List<String> pointIds, String startTime, String endTime){
        return mapper.statisByType(pointIds, startTime, endTime);
    }

    //stat use
    public List<Map> contrabandList(String departId,String startTime, String endTime,String mType,String ajyName,
                                    String dangerId,String ajyType,String psgName,String psgIdno,Integer itemWay){
        List<String> pointIds=new ArrayList<String>();
        if(!"".equals(departId)&&departId!=null){
            pointIds=checkPointCtr.getAllPointByDepart(departId);
        }else {
            pointIds=userCtr.getPoint(BaseContextHandler.getUserID());
        }
        if(pointIds.size()<=0){
            pointIds.add("");
        }

        List<Map> list=mapper.contrabandList(pointIds,startTime,endTime,mType,ajyName,dangerId,ajyType,psgName,psgIdno,itemWay);
        List<Map<String,String>> dicTypeList= dictUtil.getDicTypeList("PHT");
        List<Map<String,String>> itemWayDicTypeList= dictUtil.getDicValueList("DDT");
        List<Map<String,String>> itemResultDicTypeList= dictUtil.getDicValueList("DDR");
        List<Map<String,String>> personResultDicTypeList= dictUtil.getDicValueList("PHR");


        for(Map<String,Object> pointDanger:list){

            //违禁品类型转换为中文
            if(pointDanger.get("mtype")!=null){
                String mTypeId=pointDanger.get("mtype").toString();
                if(pointDanger.get("stype")!=null&&pointDanger.get("mtype")!=null){
                    String sTypeId=pointDanger.get("stype").toString();
                    List<Map<String,String>> dicValueList= dictUtil.getdicValueList("PHT",mTypeId);
                    for(Map<String,String> dicMap:dicValueList){
                        if(sTypeId.equals(dicMap.get("code"))){
                            pointDanger.replace("stype",dicMap.get("name"));
                        }
                    }
                }
                for(Map<String,String> dicMap:dicTypeList){
                    if(mTypeId.equals(dicMap.get("code"))){
                        pointDanger.replace("mtype",dicMap.get("name"));
                    }
                }
            }


            //违禁品子类型转换为中文



            //物品处置结果转换为中文
            if(pointDanger.get("itemResult")!=null){
                String itemResultId=pointDanger.get("itemResult").toString();
                for(Map<String,String> dicMap:itemResultDicTypeList){
                    if(itemResultId.equals(dicMap.get("value"))){
                        pointDanger.replace("itemResult",dicMap.get("name"));
                    }
                }
            }

            //处置方式转换为中文
            if(pointDanger.get("itemWay")!=null){
                String itemResultId=pointDanger.get("itemWay").toString();
                for(Map<String,String> dicMap:itemWayDicTypeList){
                    if(itemResultId.equals(dicMap.get("value"))){
                        pointDanger.replace("itemWay",dicMap.get("name"));
                    }
                }
            }

            //乘客处理结果转换为中文
            if(pointDanger.get("personResult")!=null){
                String itemResultId=pointDanger.get("personResult").toString();
                for(Map<String,String> dicMap:personResultDicTypeList){
                    if(itemResultId.equals(dicMap.get("value"))){
                        pointDanger.replace("personResult",dicMap.get("name"));
                    }
                }
            }

            pointDanger.put("pointName",checkPointCtr.getPointInfo(pointDanger.get("pointId").toString()).get("name"));
            pointDanger.put("departId",checkPointCtr.getDepartInfo(pointDanger.get("pointId").toString()).get("id"));
            pointDanger.put("departName",checkPointCtr.getDepartInfo(pointDanger.get("pointId").toString()).get("name"));
        }

        return list;
    }

    //stat use
    public int getPointDangerCountByPointId(List<String> pointIds,String startTime,String endTime){
        return mapper.getPointDangerCountByPointId( pointIds,startTime,endTime);
    }

    //stat use
    public int getPointDangerCountByDepartId(List<String> departIds,String startTime,String endTime){
        return mapper.getPointDangerCountByDepartId( departIds,startTime,endTime);
    }

    //stat use
    public List<PointDanger> pointDangerList(List<String> pointIds,String startTime, String endTime,String mType,String ajyName,
                                             String dangerId,String ajyType,String psgName,String psgIdno,Integer itemWay){
        Example example = new Example(PointDanger.class);

        Example.Criteria criteria = example.createCriteria();

        criteria.andIn("pointId",pointIds);

        if ( StringUtils.isNotBlank(mType)) {
            criteria.andEqualTo("mtype", mType);
        }
        if ( null != itemWay) {
            criteria.andEqualTo("itemWay",itemWay);
        }
        if ( StringUtils.isNotBlank(ajyName)) {
            criteria.andLike("ajyName", "%" + ajyName + "%");
        }

        if ( StringUtils.isNotBlank(dangerId)) {
            criteria.andLike("dno","%" + dangerId + "%");
        }
        if ( StringUtils.isNotBlank(ajyType)) {
            criteria.andEqualTo("ajyType",ajyType);
        }
        if ( StringUtils.isNotBlank(psgName)) {
            criteria.andLike("psgName", "%" + ajyName + "%");
        }
        if ( StringUtils.isNotBlank(psgIdno)) {
            criteria.andLike("psgIdno","%" + psgIdno + "%");
        }

        if(!"".equals(startTime)&&startTime!=null&&!"".equals(endTime)&&endTime!=null){
            criteria.andBetween("time", DateUtil.parse(startTime+" 00:00:00","yyyy-MM-dd HH:mm:ss"), DateUtil.parse(endTime+" 23:59:59","yyyy-MM-dd HH:mm:ss"));
        }
        criteria.andNotEqualTo("inputType","4");

        example.setOrderByClause("time desc");

        List<PointDanger> list=selectByExample(example);
        for(PointDanger pointDanger:list){
            pointDanger.setPointName(checkPointCtr.getPointInfo(pointDanger.getPointId()).get("name"));
            pointDanger.setDepartId(checkPointCtr.getDepartInfo(pointDanger.getPointId()).get("id"));
            pointDanger.setDepartName(checkPointCtr.getDepartInfo(pointDanger.getPointId()).get("name"));
        }

        return list;
    }
}
