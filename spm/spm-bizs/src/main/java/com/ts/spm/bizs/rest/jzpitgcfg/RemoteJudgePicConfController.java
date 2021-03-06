package com.ts.spm.bizs.rest.jzpitgcfg;

import com.alibaba.fastjson.JSONObject;
import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.DateUtil;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.jzpitgcfg.TblNormalConfInfoBiz;
import com.ts.spm.bizs.entity.jzpitgcfg.SwitchConfPush;
import com.ts.spm.bizs.entity.jzpitgcfg.TblNormalConfInfo;
import com.ts.spm.bizs.entity.jzpitgcfg.TimeConf;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import com.ts.spm.bizs.rest.msg.PushRemoteJudgePicConfController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("remoteJudgePicConf")
@CheckUserToken
@CheckClientToken
public class RemoteJudgePicConfController extends BaseController<TblNormalConfInfoBiz, TblNormalConfInfo,String> {
    @Autowired
    TblNormalConfInfoBiz tblNormalConfInfoBiz;

    @Autowired
    CheckPointController checkPointCtr;

    @Autowired
    PushRemoteJudgePicConfController PushRemoteJudgePicConf;


    @RequestMapping(value = "/information/add",method = RequestMethod.POST)
    public ObjectRestResponse add(@RequestParam(value = "times",required = false) String times,
                                  @RequestParam(defaultValue = "") String departId,
                                  @RequestParam(defaultValue = "") String pointId,
                                  @RequestParam(defaultValue = "") Integer tag,
                                  @RequestParam(defaultValue = "") Integer state){
        try {
            List<String>  existsPointIds = new ArrayList<>();
            if(!departId.equals("") && departId != null){
                List<Map<String, Object>> points=checkPointCtr.getpoint(departId,BaseContextHandler.getUserID());
                if(CollectionUtils.isEmpty(points)) {
                    return ObjectRestResponse.error("??????id???"+departId+"???????????????");
                }
                List<String>  pointIds = points.stream().map(u->u.get("id").toString()).collect(Collectors.toList());
                if(pointIds != null && pointIds.size() > 0){
                    for(String pId : pointIds){
                        if(tag == 0){//????????????
                            //??????????????????????????????????????????????????????id???????????????????????????????????????????????????????????????
                            Example example = new Example(TblNormalConfInfo.class);
                            Example.Criteria criteria = example.createCriteria();
                            criteria.andEqualTo("pointId",pId);
                            criteria.andEqualTo("tag",tag);

                            List<TblNormalConfInfo> tblNormalConfInfos = baseBiz.selectByExample(example);
                            if(tblNormalConfInfos != null && tblNormalConfInfos.size() > 0){
                                existsPointIds.add(pId);
                                //return ObjectRestResponse.error("?????????id??????"+pId+"?????????????????????????????????????????????????????????");
                            }else{
                                Map<String, String> point = tblNormalConfInfoBiz.selectByPointId(pId);
                                if(point != null && !point.isEmpty()){
                                    TblNormalConfInfo tblNormalConfInfo = new TblNormalConfInfo();
                                    tblNormalConfInfo.setId(UUIDUtils.generateUuid());
                                    tblNormalConfInfo.setPointId(point.get("id"));
                                    tblNormalConfInfo.setPointName(point.get("name"));
                                    tblNormalConfInfo.setDepartId(point.get("departId"));
                                    tblNormalConfInfo.setDepartName(point.get("departName"));
                                    tblNormalConfInfo.setTimes(times);
                                    tblNormalConfInfo.setTag(tag);//??????1??????????????? 0???????????????
                                    tblNormalConfInfo.setStateFlag(state);//???????????? 0?????? 1??????

                                    tblNormalConfInfo.setDescribes("????????????");
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
                                        //??????
                                        SwitchConfPush info = new SwitchConfPush();
                                        info.setPointId(pId);
                                        info.setState(state);
                                        info.setTag(tag);
                                        info.setTimes(timeConfs);
                                        /*try {
                                            WebSocketUtil.sendMessage(JSON.toJSONString(info), pId);

                                        } catch (IOException e) {
                                            e.printStackTrace();

                                        }*/
                                        PushRemoteJudgePicConf.send(info,pId);
                                    }
                                }
                            }
                        }else{//????????????
                            Map<String, String> point = tblNormalConfInfoBiz.selectByPointId(pId);
                            if(point != null && !point.isEmpty()){
                                TblNormalConfInfo tblNormalConfInfo = new TblNormalConfInfo();
                                tblNormalConfInfo.setId(UUIDUtils.generateUuid());
                                tblNormalConfInfo.setPointId(point.get("id"));
                                tblNormalConfInfo.setPointName(point.get("name"));
                                tblNormalConfInfo.setDepartId(point.get("departId"));
                                tblNormalConfInfo.setDepartName(point.get("departName"));
                                tblNormalConfInfo.setTimes(times);
                                tblNormalConfInfo.setTag(tag);//??????1??????????????? 0???????????????
                                tblNormalConfInfo.setStateFlag(state);//???????????? 0?????? 1??????

                                tblNormalConfInfo.setDescribes("????????????");
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
                                    //??????
                                    SwitchConfPush info = new SwitchConfPush();
                                    info.setPointId(pId);
                                    info.setState(state);
                                    info.setTag(tag);
                                    info.setTimes(timeConfs);
                                    /*try {
                                        WebSocketUtil.sendMessage(JSON.toJSONString(info), pId);

                                    } catch (IOException e) {
                                        e.printStackTrace();

                                    }*/
                                    PushRemoteJudgePicConf.send(info,pId);
                                }
                            }
                        }
                    }
                }

            }else{
                if(!pointId.equals("") && pointId != null){
                    if(tag == 0){//????????????
                        //??????????????????????????????????????????????????????id???????????????????????????????????????????????????????????????
                        Example example = new Example(TblNormalConfInfo.class);
                        Example.Criteria criteria = example.createCriteria();
                        criteria.andEqualTo("pointId",pointId);
                        criteria.andEqualTo("tag",tag);
                        List<TblNormalConfInfo> tblNormalConfInfos = baseBiz.selectByExample(example);
                        if(tblNormalConfInfos != null && tblNormalConfInfos.size() > 0){

                            return ObjectRestResponse.error("?????????id??????"+pointId+"???????????????????????????????????????????????????????????????");

                        }else{
                            Map<String, String> point = tblNormalConfInfoBiz.selectByPointId(pointId);
                            if(point != null && !point.isEmpty()){
                                TblNormalConfInfo tblNormalConfInfo = new TblNormalConfInfo();
                                tblNormalConfInfo.setId(UUIDUtils.generateUuid());
                                tblNormalConfInfo.setPointId(point.get("id"));
                                tblNormalConfInfo.setPointName(point.get("name"));
                                tblNormalConfInfo.setDepartId(point.get("departId"));
                                tblNormalConfInfo.setDepartName(point.get("departName"));
                                tblNormalConfInfo.setTag(tag);//??????1??????????????? 0???????????????
                                tblNormalConfInfo.setStateFlag(state);//???????????? 0?????? 1??????
                                tblNormalConfInfo.setTimes(times);

                                tblNormalConfInfo.setDescribes("????????????");
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
                                    //??????
                                    SwitchConfPush info = new SwitchConfPush();
                                    info.setPointId(pointId);
                                    info.setState(state);
                                    info.setTag(tag);
                                    info.setTimes(timeConfs);
                                    /*try {
                                        WebSocketUtil.sendMessage(JSON.toJSONString(info),pointId);

                                    } catch (IOException e) {
                                        e.printStackTrace();

                                    }*/
                                    PushRemoteJudgePicConf.send(info,pointId);
                                }
                            }
                        }
                    }else{//????????????
                        Map<String, String> point = tblNormalConfInfoBiz.selectByPointId(pointId);
                        if(point != null && !point.isEmpty()){
                            TblNormalConfInfo tblNormalConfInfo = new TblNormalConfInfo();
                            tblNormalConfInfo.setId(UUIDUtils.generateUuid());
                            tblNormalConfInfo.setPointId(point.get("id"));
                            tblNormalConfInfo.setPointName(point.get("name"));
                            tblNormalConfInfo.setDepartId(point.get("departId"));
                            tblNormalConfInfo.setDepartName(point.get("departName"));
                            tblNormalConfInfo.setTag(tag);//??????1??????????????? 0???????????????
                            tblNormalConfInfo.setStateFlag(state);//???????????? 0?????? 1??????
                            tblNormalConfInfo.setTimes(times);

                            tblNormalConfInfo.setDescribes("????????????");
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
                                //??????
                                SwitchConfPush info = new SwitchConfPush();
                                info.setPointId(pointId);
                                info.setState(state);
                                info.setTag(tag);
                                info.setTimes(timeConfs);
                                /*try {
                                    WebSocketUtil.sendMessage(JSON.toJSONString(info),pointId);

                                } catch (IOException e) {
                                    e.printStackTrace();

                                }*/
                                PushRemoteJudgePicConf.send(info,pointId);
                            }
                        }
                    }
                }
            }
            if(existsPointIds.size() > 0){

                return ObjectRestResponse.error("?????????id??????"+ JSONObject.toJSONString(existsPointIds) +"???????????????????????????????????????????????????????????????");

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
                                     @RequestParam(defaultValue = "") String departId,
                                     @RequestParam(defaultValue = "") Integer tag,
                                     @RequestParam(defaultValue = "") Integer state){

        if(StringUtils.isNoneBlank(startTime)){
            startTime = startTime + " 00:00:00";
        }
        if(StringUtils.isNoneBlank(endTime)){
            endTime = endTime + " 23:59:59";
        }
        List<Map<String, Object>> points = checkPointCtr.getpoint(departId, BaseContextHandler.getUserID());
        if (CollectionUtils.isEmpty(points)) {
            return new TableResultResponse().error("??????id???" + departId + "???????????????");
        }
        List<String> pointIds = points.stream().map(u -> u.get("id").toString()).collect(Collectors.toList());

        Example example = new Example(TblNormalConfInfo.class);
        Example.Criteria criteria = example.createCriteria();

        /*if(!startTime.equals("") && startTime != null && !endTime.equals("") && endTime != null){
            criteria.andBetween("createTime",DateUtil.stringToDate(startTime),DateUtil.stringToDate(endTime));
        }*/
        if(pointIds != null && pointIds.size() > 0){
            criteria.andIn("pointId",pointIds);
        }
        if(tag != null){
            criteria.andEqualTo("tag",tag);
        }
        if(state != null){
            criteria.andEqualTo("stateFlag",state);
        }
        example.setOrderByClause("tag asc,create_time desc");

        Page<TblNormalConfInfo> result = PageHelper.startPage(page, limit);
        List<TblNormalConfInfo> normalConfInfos = new ArrayList<>();
        List<TblNormalConfInfo> normalConfInfoList = tblNormalConfInfoBiz.selectByExample(example);
        if(tag != null && !"".equals(tag)){
            if(tag == 1){
                if(normalConfInfoList != null && normalConfInfoList.size() > 0){
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
                                        //??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????

                                        if (DateUtil.stringToDate(startTime).getTime() <= DateUtil.stringToDate(sTime).getTime() && DateUtil.stringToDate(endTime).getTime() >= DateUtil.stringToDate(eTime).getTime()) {
                                            normalConfInfos.add(tblNormalConfInfo);
                                        }


                                    }
                                } else if (time.length > 0 && time.length > 1) {
                                    int l = time.length;
                                    //??????????????????????????????????????????????????????????????????????????????
                                    String first = time[0];
                                    String end = time[l - 1];
                                    String[] firstTemporaryTime = first.split("~");
                                    String[] endTemporaryTime = end.split("~");
                                    if (firstTemporaryTime.length > 0 && endTemporaryTime.length > 0) {
                                        //?????????????????????????????????????????????
                                        String sTime = firstTemporaryTime[0];
                                        //????????????????????????????????????????????????
                                        String eTime = endTemporaryTime[1];
                                        //???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????

                                        if (DateUtil.stringToDate(startTime).getTime() <= DateUtil.stringToDate(sTime).getTime() && DateUtil.stringToDate(endTime).getTime() >= DateUtil.stringToDate(eTime).getTime()) {
                                            normalConfInfos.add(tblNormalConfInfo);
                                        }
                                    }
                                }
                            }
                        }
                        return new TableResultResponse<>(normalConfInfos.size(),normalConfInfos);
                    }else{
                        return new TableResultResponse<>(result.getTotal(),normalConfInfoList);
                    }
                }
                return new TableResultResponse<>(result.getTotal(),normalConfInfoList);

            }else{
                return new TableResultResponse<>(result.getTotal(),normalConfInfoList);
            }
        }
        return TableResultResponse.error("???????????????tag??????????????????");
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
        return ObjectRestResponse.error("??????????????????????????????????????????");
    }
    @RequestMapping(value = "/information/update",method = RequestMethod.PUT)
    public ObjectRestResponse update(@RequestBody TblNormalConfInfo tblNormalConfInfo){
        if(tblNormalConfInfo != null){
            List<TimeConf> timeConfs = null;
            String times = tblNormalConfInfo.getTimes();
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
            //??????
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
            return ObjectRestResponse.error("???????????????");
        }
        return ObjectRestResponse.error("???????????????");
    }
    @RequestMapping(value = "/timesCheck",method = RequestMethod.GET)
    public ObjectRestResponse timesCheck(@RequestParam("pointId") String pointId,
                                         @RequestParam("time") String time,
                                         @RequestParam("tag") Integer tag,
                                         @RequestParam("state") Integer state){
        Example example = new Example(TblNormalConfInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("pointId",pointId);
        criteria.andEqualTo("tag",tag);//?????????0??????????????? 1???????????????
        List<TblNormalConfInfo> tblNormalConfInfos = null;
        Map<String,Object> maxCreateTimeMap = null;
        if(tag == 1){//????????????
            maxCreateTimeMap = tblNormalConfInfoBiz.selectMaxCreateTime(pointId,tag);
        }else{
            tblNormalConfInfos = baseBiz.selectByExample(example);
        }

        Long beginTime = null;//?????????????????????
        Long endTime = null;//?????????????????????
        if(tag == 0){//????????????
            if(StringUtils.isNoneBlank(time)){

                String[] checkTimes = time.split("~");
                if(checkTimes.length == 2){
                    beginTime = DateUtil.stringToDate(DateUtil.getCurrentDay()+" "+checkTimes[0]).getTime();
                    endTime = DateUtil.stringToDate(DateUtil.getCurrentDay()+" "+checkTimes[1]).getTime();
                }else {
                    return ObjectRestResponse.error("????????????!");
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
                                        //????????????????????????????????????????????????????????????????????????
                                        if(beginTime != null && endTime != null){
                                            if((beginTime == sTime && endTime == eTime) || (beginTime >= sTime && beginTime <= eTime) || (beginTime <= sTime && endTime >= eTime) || (endTime >= sTime && endTime <= eTime)){
                                                return ObjectRestResponse.error("?????????????????????????????????????????????");
                                            }
                                        }else{
                                            return ObjectRestResponse.error("???????????????");
                                        }
                                    }else {
                                        return ObjectRestResponse.error("????????????!");
                                    }
                                }
                            }
                        }
                    }
                    return ObjectRestResponse.ok();
                }
            }
        }else{//????????????
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

                                    //????????????????????????????????????????????????????????????????????????
                                    if(beginTime != null && endTime != null){
                                        if((beginTime == sTime && endTime == eTime) || (beginTime >= sTime && beginTime <= eTime) || (beginTime <= sTime && endTime >= eTime) || (endTime >= sTime && endTime <= eTime)){
                                            return ObjectRestResponse.error("?????????????????????????????????????????????");
                                        }
                                    }else{
                                        return ObjectRestResponse.error("???????????????");
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
