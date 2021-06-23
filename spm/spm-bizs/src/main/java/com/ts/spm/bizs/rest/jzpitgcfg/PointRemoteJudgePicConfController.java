package com.ts.spm.bizs.rest.jzpitgcfg;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.DateUtil;
import com.github.wxiaoqi.security.common.util.UUIDUtils;

import com.ts.spm.bizs.biz.jzpitgcfg.TblNormalConfInfoBiz;
import com.ts.spm.bizs.entity.jzpitgcfg.NormalConfInfo;
import com.ts.spm.bizs.entity.jzpitgcfg.SwitchConfPush;
import com.ts.spm.bizs.entity.jzpitgcfg.TblNormalConfInfo;
import com.ts.spm.bizs.entity.jzpitgcfg.TimeConf;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import com.ts.spm.bizs.rest.msg.DangerInfoController;
import com.ts.spm.bizs.rest.msg.PushRemoteJudgePicConfController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("pointRemoteJudgePicConf")
@IgnoreUserToken
@IgnoreClientToken
public class PointRemoteJudgePicConfController extends BaseController<TblNormalConfInfoBiz, TblNormalConfInfo,String> {
    @Autowired
    TblNormalConfInfoBiz tblNormalConfInfoBiz;
    @Autowired
    CheckPointController checkPointCtr;

    @Autowired
    PushRemoteJudgePicConfController PushRemoteJudgePicConf;
    @Autowired
    DangerInfoController dangerInfoController;

    @RequestMapping(value = "/information/add",method = RequestMethod.POST)
    public ObjectRestResponse add(@RequestParam(value = "times",required = false) String times,
                                  @RequestParam(defaultValue = "") String pointId,
                                  @RequestParam(defaultValue = "") Integer tag,
                                  @RequestParam(defaultValue = "") Integer state){
        try {
            if(!pointId.equals("") && pointId != null){
                if(tag == 0){//常态
                    //查询远程判图配置表是否已存在该安检点id，若存在则提示前端进行编辑，若不存在则新增
                    Example example = new Example(TblNormalConfInfo.class);
                    Example.Criteria criteria = example.createCriteria();
                    criteria.andEqualTo("pointId",pointId);
                    criteria.andEqualTo("tag",tag);
                    List<TblNormalConfInfo> tblNormalConfInfos = baseBiz.selectByExample(example);
                    if(tblNormalConfInfos != null && tblNormalConfInfos.size() > 0){

                        return ObjectRestResponse.error("安检点id为："+pointId+"已进行过常态配置，如需要重新配置，请编辑！");

                    }else{
                        Map<String, String> point = tblNormalConfInfoBiz.selectByPointId(pointId);
                        if(point != null && !point.isEmpty()){
                            TblNormalConfInfo tblNormalConfInfo = new TblNormalConfInfo();
                            tblNormalConfInfo.setId(UUIDUtils.generateUuid());
                            tblNormalConfInfo.setPointId(point.get("id"));
                            tblNormalConfInfo.setPointName(point.get("name"));
                            tblNormalConfInfo.setDepartId(point.get("departId"));
                            tblNormalConfInfo.setDepartName(point.get("departName"));
                            tblNormalConfInfo.setTimes(times);
                            tblNormalConfInfo.setTag(tag);//标识1：临时配置 0：常态配置
                            tblNormalConfInfo.setStateFlag(state);//开关状态 0：关 1：开

                            tblNormalConfInfo.setDescribes("常态配置");
                            tblNormalConfInfo.setLevelFlag(0);

                            tblNormalConfInfo.setCreateTime(new Date());
                            int result = baseBiz.insertSelective2(tblNormalConfInfo);
                        }
                        if(!times.equals("") && times != null){
                            String[]  time = times.split(",");
                            if(time.length > 0){
                                List<TimeConf> timeConfs = new ArrayList<>();
                                for(String str : time){
                                    int endLength = str.indexOf("~",0);
                                    String beginTime = str.substring(0,endLength);
                                    String endTime = str.substring(endLength+1,str.length());
                                    TimeConf timeConf = new TimeConf();
                                    timeConf.setBeginTime(beginTime);
                                    timeConf.setEndTime(endTime);
                                    timeConfs.add(timeConf);
                                }
                                //推送
                                SwitchConfPush info = new SwitchConfPush();
                                info.setPointId(pointId);
                                info.setState(state);
                                info.setTag(tag);
                                info.setTimes(timeConfs);
                               /* try {
                                    WebSocketUtil.sendMessage(JSON.toJSONString(info), pointId);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }*/
                                PushRemoteJudgePicConf.send(info,pointId);
                            }
                        }
                    }
                }else{//临时配置
                    Map<String, String> point = tblNormalConfInfoBiz.selectByPointId(pointId);
                    if(point != null && !point.isEmpty()){
                        TblNormalConfInfo tblNormalConfInfo = new TblNormalConfInfo();
                        tblNormalConfInfo.setId(UUIDUtils.generateUuid());
                        tblNormalConfInfo.setPointId(point.get("id"));
                        tblNormalConfInfo.setPointName(point.get("name"));
                        tblNormalConfInfo.setDepartId(point.get("departId"));
                        tblNormalConfInfo.setDepartName(point.get("departName"));
                        tblNormalConfInfo.setTimes(times);
                        tblNormalConfInfo.setTag(tag);//标识1：临时配置 0：常态配置
                        tblNormalConfInfo.setStateFlag(state);//开关状态 0：关 1：开

                        tblNormalConfInfo.setDescribes("临时配置");
                        tblNormalConfInfo.setLevelFlag(1);

                        tblNormalConfInfo.setCreateTime(new Date());
                        int result = baseBiz.insertSelective2(tblNormalConfInfo);
                    }
                    if(!times.equals("") && times != null){
                        String[]  time = times.split(",");
                        if(time.length > 0){
                            List<TimeConf> timeConfs = new ArrayList<>();
                            for(String str : time){
                                int endLength = str.indexOf("~",0);
                                String beginTime = str.substring(0,endLength);
                                String endTime = str.substring(endLength+1,str.length());
                                TimeConf timeConf = new TimeConf();
                                timeConf.setBeginTime(beginTime);
                                timeConf.setEndTime(endTime);
                                timeConfs.add(timeConf);
                            }
                            //推送
                            SwitchConfPush info = new SwitchConfPush();
                            info.setPointId(pointId);
                            info.setState(state);
                            info.setTag(tag);
                            info.setTimes(timeConfs);
                            /*try {
                                WebSocketUtil.sendMessage(JSON.toJSONString(info), pointId);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }*/
                            PushRemoteJudgePicConf.send(info,pointId);
                        }
                    }
                }


            }
            return ObjectRestResponse.ok();
        }catch (Exception e){
            e.printStackTrace();
            return ObjectRestResponse.error(e.getMessage());
        }

    }

    @RequestMapping(value = "/information/query",method = RequestMethod.GET)
    public TableResultResponse query(@RequestParam(defaultValue = "10") int limit,
                                     @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "") String startTime,
                                     @RequestParam(defaultValue = "") String endTime,
                                     @RequestParam(defaultValue = "") String pointId,
                                     @RequestParam(defaultValue = "") Integer tag,
                                     @RequestParam(defaultValue = "") Integer state,
                                     @RequestParam(defaultValue = "") String requestSource){
        if(StringUtils.isNoneBlank(startTime)){
            startTime = startTime + " 00:00:00";
        }
        if(StringUtils.isNoneBlank(endTime)){
            endTime = endTime + " 23:59:59";
        }
        Example example = new Example(TblNormalConfInfo.class);
        Example.Criteria criteria = example.createCriteria();
        /*if(!startTime.equals("") && startTime != null && !endTime.equals("") && endTime != null){
            criteria.andBetween("createTime",DateUtil.stringToDate(startTime),DateUtil.stringToDate(endTime));
        }*/
        if(!pointId.equals("") && pointId != null){
            criteria.andEqualTo("pointId",pointId);
        }
        if(tag != null){
            criteria.andEqualTo("tag",tag);
        }
        if(state != null){
            criteria.andEqualTo("stateFlag",state);
        }
        example.setOrderByClause("tag asc,create_time desc");
        Page<TblNormalConfInfo> result = PageHelper.startPage(page, limit);

        List<TblNormalConfInfo> tblNormalConfInfos = new ArrayList<>();//临时配置集合
        List<TblNormalConfInfo> normalConfInfoList = tblNormalConfInfoBiz.selectByExample(example);//常态配置集合
        if(tag != null && !"".equals(tag)){
            if(tag == 1){
                if(normalConfInfoList != null && normalConfInfoList.size() > 0) {
                    if(StringUtils.isNoneBlank(startTime) && StringUtils.isNoneBlank(endTime)) {
                        for (TblNormalConfInfo tblNormalConfInfo : normalConfInfoList) {

                            String times = tblNormalConfInfo.getTimes();
                            if (!times.equals("") && times != null) {
                                String[] time = times.split(",");
                                if (time.length > 0 && time.length == 1) {

                                    for (String str : time) {
                                        String[] temporaryTime = str.split("~");

                                        String sTime = temporaryTime[0];
                                        String eTime = temporaryTime[1];
                                        //数据库存储的临时配置开始时间大于等于查询开始时间并且数据库存储的临时配置结束时间小于等于查询结束时间

                                        if (DateUtil.stringToDate(startTime).getTime() <= DateUtil.stringToDate(sTime).getTime() && DateUtil.stringToDate(endTime).getTime() >= DateUtil.stringToDate(eTime).getTime()) {
                                            tblNormalConfInfos.add(tblNormalConfInfo);
                                        }

                                    }
                                } else if (time.length > 0 && time.length > 1) {
                                    int l = time.length;
                                    //取出数组第一个临时配置时间段和最后一个临时配置时间段
                                    String first = time[0];
                                    String end = time[l - 1];
                                    String[] firstTemporaryTime = first.split("~");
                                    String[] endTemporaryTime = end.split("~");
                                    if (firstTemporaryTime.length > 0 && endTemporaryTime.length > 0) {
                                        //第一个临时配置时间段的开始时间
                                        String sTime = firstTemporaryTime[0];
                                        //最后一个临时配置时间段的结束时间
                                        String eTime = endTemporaryTime[1];
                                        //数据库存储的第一个临时配置开始时间大于等于查询开始时间并且数据库存储的最后一个临时配置结束时间小于等于查询结束时间

                                        if (DateUtil.stringToDate(startTime).getTime() <= DateUtil.stringToDate(sTime).getTime() && DateUtil.stringToDate(endTime).getTime() >= DateUtil.stringToDate(eTime).getTime()) {
                                            tblNormalConfInfos.add(tblNormalConfInfo);
                                        }

                                    }
                                }
                            }
                        }
                        //C端返回数据list
                        List<NormalConfInfo> normalConfInfos = new ArrayList<>();
                        if (requestSource.equals("C")) {
                            if (tblNormalConfInfos != null && tblNormalConfInfos.size() > 0) {
                                for (TblNormalConfInfo tblNormalConfInfo : tblNormalConfInfos) {
                                    String times = tblNormalConfInfo.getTimes();
                                    if (!times.equals("") && times != null) {
                                        String[] time = times.split(",");
                                        if (time.length > 0) {
                                            List<TimeConf> timeConfs = new ArrayList<>();
                                            for (String str : time) {
                                                int endLength = str.indexOf("~", 0);
                                                String sTime = str.substring(0, endLength);
                                                String eTime = str.substring(endLength + 1, str.length());
                                                TimeConf timeConf = new TimeConf();
                                                timeConf.setBeginTime(sTime);
                                                timeConf.setEndTime(eTime);
                                                timeConfs.add(timeConf);
                                            }
                                            NormalConfInfo normalConfInfo = new NormalConfInfo();
                                            normalConfInfo.setId(tblNormalConfInfo.getId());
                                            normalConfInfo.setPointId(tblNormalConfInfo.getPointId());
                                            normalConfInfo.setCreateTime(tblNormalConfInfo.getCreateTime());
                                            normalConfInfo.setDepartId(tblNormalConfInfo.getDepartId());
                                            normalConfInfo.setDepartName(tblNormalConfInfo.getDepartName());
                                            normalConfInfo.setDescribes(tblNormalConfInfo.getDescribes());
                                            normalConfInfo.setLevelFlag(tblNormalConfInfo.getLevelFlag());
                                            normalConfInfo.setMemo(tblNormalConfInfo.getMemo());
                                            normalConfInfo.setPointName(tblNormalConfInfo.getPointName());
                                            normalConfInfo.setStateFlag(tblNormalConfInfo.getStateFlag());
                                            normalConfInfo.setTag(tblNormalConfInfo.getTag());
                                            normalConfInfo.setTimes(timeConfs);
                                            normalConfInfo.setUpdateTime(tblNormalConfInfo.getUpdateTime());

                                            normalConfInfos.add(normalConfInfo);
                                        }
                                    }
                                }
                            }
                            return new TableResultResponse<>(normalConfInfos.size(), normalConfInfos);
                        }else{
                            return new TableResultResponse<>(tblNormalConfInfos.size(),tblNormalConfInfos);
                        }
                    }else{
                        //C端返回数据list
                        List<NormalConfInfo> normalConfInfos = new ArrayList<>();
                        if (requestSource.equals("C")) {
                            if (normalConfInfoList != null && normalConfInfoList.size() > 0) {
                                for (TblNormalConfInfo tblNormalConfInfo : normalConfInfoList) {
                                    String times = tblNormalConfInfo.getTimes();
                                    if (!times.equals("") && times != null) {
                                        String[] time = times.split(",");
                                        if (time.length > 0) {
                                            List<TimeConf> timeConfs = new ArrayList<>();
                                            for (String str : time) {
                                                int endLength = str.indexOf("~", 0);
                                                String sTime = str.substring(0, endLength);
                                                String eTime = str.substring(endLength + 1, str.length());
                                                TimeConf timeConf = new TimeConf();
                                                timeConf.setBeginTime(sTime);
                                                timeConf.setEndTime(eTime);
                                                timeConfs.add(timeConf);
                                            }
                                            NormalConfInfo normalConfInfo = new NormalConfInfo();
                                            normalConfInfo.setId(tblNormalConfInfo.getId());
                                            normalConfInfo.setPointId(tblNormalConfInfo.getPointId());
                                            normalConfInfo.setCreateTime(tblNormalConfInfo.getCreateTime());
                                            normalConfInfo.setDepartId(tblNormalConfInfo.getDepartId());
                                            normalConfInfo.setDepartName(tblNormalConfInfo.getDepartName());
                                            normalConfInfo.setDescribes(tblNormalConfInfo.getDescribes());
                                            normalConfInfo.setLevelFlag(tblNormalConfInfo.getLevelFlag());
                                            normalConfInfo.setMemo(tblNormalConfInfo.getMemo());
                                            normalConfInfo.setPointName(tblNormalConfInfo.getPointName());
                                            normalConfInfo.setStateFlag(tblNormalConfInfo.getStateFlag());
                                            normalConfInfo.setTag(tblNormalConfInfo.getTag());
                                            normalConfInfo.setTimes(timeConfs);
                                            normalConfInfo.setUpdateTime(tblNormalConfInfo.getUpdateTime());

                                            normalConfInfos.add(normalConfInfo);
                                        }
                                    }
                                }
                            }
                            return new TableResultResponse<>(result.getTotal(), normalConfInfos);
                        }
                        return new TableResultResponse<>(result.getTotal(),normalConfInfoList);
                    }
                }

                return new TableResultResponse<>(result.getTotal(),normalConfInfoList);

            }else{//常态配置
                //C端返回数据list
                List<NormalConfInfo> normalConfInfos = new ArrayList<>();
                if(requestSource.equals("C")){
                    if(normalConfInfoList != null && normalConfInfoList.size() > 0){
                        for(TblNormalConfInfo tblNormalConfInfo : normalConfInfoList){
                            String times = tblNormalConfInfo.getTimes();
                            if(!times.equals("") && times != null) {
                                String[] time = times.split(",");
                                if (time.length > 0) {
                                    List<TimeConf> timeConfs = new ArrayList<>();
                                    for (String str : time) {
                                        int endLength = str.indexOf("~", 0);
                                        String sTime = str.substring(0, endLength);
                                        String eTime = str.substring(endLength + 1, str.length());
                                        TimeConf timeConf = new TimeConf();
                                        timeConf.setBeginTime(sTime);
                                        timeConf.setEndTime(eTime);
                                        timeConfs.add(timeConf);
                                    }
                                    NormalConfInfo normalConfInfo = new NormalConfInfo();
                                    normalConfInfo.setId(tblNormalConfInfo.getId());
                                    normalConfInfo.setPointId(tblNormalConfInfo.getPointId());
                                    normalConfInfo.setCreateTime(tblNormalConfInfo.getCreateTime());
                                    normalConfInfo.setDepartId(tblNormalConfInfo.getDepartId());
                                    normalConfInfo.setDepartName(tblNormalConfInfo.getDepartName());
                                    normalConfInfo.setDescribes(tblNormalConfInfo.getDescribes());
                                    normalConfInfo.setLevelFlag(tblNormalConfInfo.getLevelFlag());
                                    normalConfInfo.setMemo(tblNormalConfInfo.getMemo());
                                    normalConfInfo.setPointName(tblNormalConfInfo.getPointName());
                                    normalConfInfo.setStateFlag(tblNormalConfInfo.getStateFlag());
                                    normalConfInfo.setTag(tblNormalConfInfo.getTag());
                                    normalConfInfo.setTimes(timeConfs);
                                    normalConfInfo.setUpdateTime(tblNormalConfInfo.getUpdateTime());

                                    normalConfInfos.add(normalConfInfo);
                                }
                            }
                        }
                    }
                    return new TableResultResponse<>(result.getTotal(),normalConfInfos);
                }
                return new TableResultResponse<>(result.getTotal(),normalConfInfoList);
            }
        }
        return TableResultResponse.error("参数错误！tag参数为必填！");
    }

    @RequestMapping(value = "/information/get/{id}",method = RequestMethod.GET)
    public ObjectRestResponse get(@PathVariable String id){
        return ObjectRestResponse.ok(baseBiz.selectById(id));
    }


    @RequestMapping(value = "/information/delete/{id}",method = RequestMethod.DELETE)
    public ObjectRestResponse delete(@PathVariable String id){
        int result = baseBiz.deleteById2(id);
        if (result == 1){
            return ObjectRestResponse.ok();
        }
        return ObjectRestResponse.error("删除失败！请联系系统管理员！");
    }

    @RequestMapping(value = "/information/update",method = RequestMethod.PUT)
    public ObjectRestResponse update(@RequestBody TblNormalConfInfo tblNormalConfInfo){
        if(tblNormalConfInfo != null){
            String times = tblNormalConfInfo.getTimes();
            List<TimeConf> timeConfs = null;
            if(!times.equals("") && times != null) {
                String[] time = times.split(",");
                if (time.length > 0) {
                    timeConfs = new ArrayList<>();
                    for (String str : time) {
                        int endLength = str.indexOf("~", 0);
                        String sTime = str.substring(0, endLength);
                        String eTime = str.substring(endLength + 1, str.length());
                        TimeConf timeConf = new TimeConf();
                        timeConf.setBeginTime(sTime);
                        timeConf.setEndTime(eTime);
                        timeConfs.add(timeConf);
                    }

                }
            }
            //推送
            SwitchConfPush info = new SwitchConfPush();
            info.setPointId(tblNormalConfInfo.getPointId());
            info.setState(tblNormalConfInfo.getStateFlag());
            info.setTag(tblNormalConfInfo.getTag());
            info.setTimes(timeConfs);
            /*try {
                WebSocketUtil.sendMessage(JSON.toJSONString(info),tblNormalConfInfo.getPointId());

            } catch (IOException e) {
                e.printStackTrace();

            }*/
            PushRemoteJudgePicConf.send(info,tblNormalConfInfo.getPointId());
            tblNormalConfInfo.setUpdateTime(new Date());
            int result = baseBiz.updateSelectiveById2(tblNormalConfInfo);
            if(result == 1){
                return ObjectRestResponse.ok();
            }
            return ObjectRestResponse.error("更新失败！");
        }
        return ObjectRestResponse.error("参数错误！");
    }
    @RequestMapping(value = "/timesCheck",method = RequestMethod.GET)
    public ObjectRestResponse timesCheck(@RequestParam("pointId") String pointId,
                                         @RequestParam("time") String time,
                                         @RequestParam("tag") Integer tag,
                                         @RequestParam("state") Integer state){
        Example example = new Example(TblNormalConfInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("pointId",pointId);
        criteria.andEqualTo("tag",tag);//标识：0：常态配置 1：临时配置
        List<TblNormalConfInfo> tblNormalConfInfos = null;
        Map<String,Object> maxCreateTimeMap = null;
        if(tag == 1){//临时配置
            maxCreateTimeMap = tblNormalConfInfoBiz.selectMaxCreateTime(pointId,tag);
        }else{
            tblNormalConfInfos = baseBiz.selectByExample(example);
        }

        Long beginTime = null;//开始时间毫秒值
        Long endTime = null;//结束时间毫秒值
        if(tag == 0){//常态配置
            if(StringUtils.isNoneBlank(time)){

                String[] checkTimes = time.split("~");
                if(checkTimes.length == 2){
                    beginTime = DateUtil.stringToDate(DateUtil.getCurrentDay()+" "+checkTimes[0]).getTime();
                    endTime = DateUtil.stringToDate(DateUtil.getCurrentDay()+" "+checkTimes[1]).getTime();
                }else {
                    return ObjectRestResponse.error("参数错误!");
                }

            }
            if(tblNormalConfInfos != null && tblNormalConfInfos.size() > 0){
                for(TblNormalConfInfo tblNormalConfInfo : tblNormalConfInfos){
                    if(state != tblNormalConfInfo.getStateFlag()){
                        String times = tblNormalConfInfo.getTimes();
                        if(!times.equals("") && times != null) {
                            String[] existsTimes = times.split(",");
                            if (existsTimes.length > 0) {

                                for (String str : existsTimes) {
                                    String[] beginAndEndTime = str.split("~");
                                    if(beginAndEndTime.length == 2){
                                        Long sTime = DateUtil.stringToDate(DateUtil.getCurrentDay()+" "+beginAndEndTime[0]).getTime();
                                        Long eTime = DateUtil.stringToDate(DateUtil.getCurrentDay()+" "+beginAndEndTime[1]).getTime();
                                        //判断同一配置情况下，开关状态相反时时间是否有交集
                                        if(beginTime != null && endTime != null){
                                            if((beginTime == sTime && endTime == eTime) || (beginTime >= sTime && beginTime <= eTime) || (beginTime <= sTime && endTime >= eTime) || (endTime >= sTime && endTime <= eTime)){
                                                return ObjectRestResponse.error("选择时间段有重叠，请重新选择！");
                                            }
                                        }else{
                                            return ObjectRestResponse.error("参数错误！");
                                        }
                                    }else {
                                        return ObjectRestResponse.error("参数错误!");
                                    }
                                }
                            }
                        }
                    }
                    return ObjectRestResponse.ok();
                }
            }
        }else{//临时配置
            if(StringUtils.isNoneBlank(time)){
                String[] checkTimes = time.split(",");
                for (String str : checkTimes) {
                    int endLength = str.indexOf("~", 0);
                    beginTime = DateUtil.stringToDate(str.substring(0, endLength)).getTime();
                    endTime = DateUtil.stringToDate(str.substring(endLength + 1, str.length())).getTime();

                }
            }
            if(maxCreateTimeMap != null && !maxCreateTimeMap.isEmpty()){

                /*if(state != maxCreateTimeMap.get("stateFlag")){*/
                    String times = (String)maxCreateTimeMap.get("times");
                    if(!times.equals("") && times != null) {
                        String[] existsTimes = times.split(",");
                        if (existsTimes.length > 0) {

                            for (String str : existsTimes) {
                                int endLength = str.indexOf("~", 0);
                                Long sTime = DateUtil.stringToDate(str.substring(0, endLength)).getTime();
                                Long eTime = DateUtil.stringToDate(str.substring(endLength + 1, str.length())).getTime();

                                //判断同一配置情况下，开关状态相反时时间是否有交集
                                if(beginTime != null && endTime != null){
                                    if((beginTime == sTime && endTime == eTime) || (beginTime >= sTime && beginTime <= eTime) || (beginTime <= sTime && endTime >= eTime) || (endTime >= sTime && endTime <= eTime)){
                                        return ObjectRestResponse.error("选择时间段有重叠，请重新选择！");
                                    }
                                }else{
                                    return ObjectRestResponse.error("参数错误！");
                                }
                            }
                        }
                    }
                /*}*/
                return ObjectRestResponse.ok();
            }
        }
        return ObjectRestResponse.ok();
    }
}
