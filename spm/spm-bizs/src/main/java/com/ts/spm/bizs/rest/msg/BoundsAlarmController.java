package com.ts.spm.bizs.rest.msg;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ts.spm.bizs.biz.msg.BoundsAlarmBiz;
import com.ts.spm.bizs.entity.msg.BoundsAlarm;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by zhoukun on 2020/4/17.
 */
@RestController
@RequestMapping("boundsAlarm")
@CheckClientToken
@CheckUserToken
public class BoundsAlarmController extends BaseController<BoundsAlarmBiz, BoundsAlarm, Integer> {
    @Autowired
    CheckPointController checkPointCtr;

    @RequestMapping(value = "/getpage", method = RequestMethod.GET)
    public TableResultResponse page(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "") String lineId, @RequestParam(defaultValue = "-1") Integer status,
                                    @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date start, @RequestParam(required = false)  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date end,
                                    @RequestParam(defaultValue = "") String ip, @RequestParam(defaultValue = "") String port) {
        Page<BoundsAlarm> result = PageHelper.startPage(page, limit);
        List<BoundsAlarm> list = baseBiz.selectByLine(lineId,status,start,end,ip,port);
        return new TableResultResponse<>(result.getTotal(),list);
    }

    @RequestMapping(value = "/chartTime", method = RequestMethod.GET)
    public ObjectRestResponse chartByTime(@RequestParam(defaultValue = "") String lineId, @RequestParam(defaultValue = "-1") Integer status,
                                          @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date start,
                                          @RequestParam(required = false)  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date end,
                                          @RequestParam(defaultValue = "") String ip, @RequestParam(defaultValue = "") String port,
                                          @RequestParam(defaultValue = "0") int chart) {

//        Date st= org.apache.commons.lang.time.DateUtils.parseDate(start, new String[]{"yyyy-MM-dd HH:mm:ss"});
//        Date ed= org.apache.commons.lang.time.DateUtils.parseDate(end, new String[]{"yyyy-MM-dd HH:mm:ss"});
//        long diff=(ed.getTime()-st.getTime())/(60*60*24*1000);

        long diff=(end.getTime()-start.getTime())/(60*60*24*1000);

        List<Map<String, Object>> result=new ArrayList<>();
        List<Map<String, Object>> query;
//        List<String> pointIds=points.stream().map(u->u.get("id")).collect(Collectors.toList());
        if(chart==0){
            for (int i = 0; i < diff + 1; i++) {
                Date d= DateUtils.addDays(start,i);
                for (int j=0; j<24; j++){
                    d= DateUtils.addHours(d, 1);
                    Map m=new HashMap();
                    m.put("STAT", DateFormatUtils.format(d, "yyyy-MM-dd HH"));
                    m.put("CNT", "0");
                    result.add(m);
                }
            }
            query=baseBiz.statis1(lineId,status,start,end,ip,port);
        }else{
            for (int i = 0; i < diff + 1; i++) {
                Date d= DateUtils.addDays(start,i);
                Map m=new HashMap();
                m.put("STAT", DateFormatUtils.format(d, "yyyy-MM-dd"));
                m.put("CNT", "0");
                result.add(m);
            }
            query=baseBiz.statis2(lineId,status,start,end,ip,port);
        }
        //check list
        for (Map<String, Object> res:result){
            for (Map<String, Object> item:query){
                if(res.get("STAT").equals(item.get("STAT"))){
                    res.put("CNT", item.get("CNT"));
                    break;
                }
            }
        }
        return ObjectRestResponse.ok(result);
    }

    @RequestMapping(value = "/chartSite", method = RequestMethod.GET)
    public ObjectRestResponse chartBySite(@RequestParam(defaultValue = "") String lineId, @RequestParam(defaultValue = "-1") Integer status,
                                          @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date start,
                                          @RequestParam(required = false)  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date end,
                                          @RequestParam(defaultValue = "") String ip, @RequestParam(defaultValue = "") String port) {
        List<Map<String, Object>> result;
//        if(chart==0){
////            result=baseBiz.chartSite1(pointIds, st, ed, type);
////            for (Map<String, Object> item:result){
////                String pointId=item.get("DEPT").toString();
////                Map<String, String> depart=checkPointCtr.getDepart(pointId);
////                item.put("NAME",depart.get("name")+"-"+item.get("NAME"));
////            }
//        }else{
//            result=baseBiz.chartSite2(lineId,status,start,end,ip,port);
//            for (Map<String, Object> item:result){
//                String stationId=item.get("DEPT").toString();
//                Map<String, String> depart=checkPointCtr.departDetail(stationId);
//                item.put("NAME",depart.get("name"));
//            }
//        }
        result=baseBiz.chartSite2(lineId,status,start,end,ip,port);
        for (Map<String, Object> item:result){
            String stationId=item.get("DEPT").toString();
            Map<String, String> depart=checkPointCtr.departDetail(stationId);
            item.put("NAME",depart.get("name"));
        }
        return ObjectRestResponse.ok(result);
    }

    @RequestMapping(value = "/chartType", method = RequestMethod.GET)
    public ObjectRestResponse chartByType(@RequestParam(defaultValue = "") String lineId, @RequestParam(defaultValue = "-1") Integer status,
                                          @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date start,
                                          @RequestParam(required = false)  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date end,
                                          @RequestParam(defaultValue = "") String ip, @RequestParam(defaultValue = "") String port) {
        List<Map<String, Object>> result;
        result=baseBiz.chartType(lineId,status,start,end,ip,port);
        return ObjectRestResponse.ok(result);
    }

}
