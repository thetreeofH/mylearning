package com.ts.spm.bizs.rest.stat;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.LogBiz;
import com.ts.spm.bizs.biz.equ.SecurityDeviceSubBiz;
import com.ts.spm.bizs.biz.equ.SysMsgBiz;
import com.ts.spm.bizs.biz.equ.SysMsgReceiveBiz;
import com.ts.spm.bizs.biz.matter.*;
import com.ts.spm.bizs.biz.msg.MsgReceiveBiz;
import com.ts.spm.bizs.biz.person.AjyBiz;
import com.ts.spm.bizs.biz.person.TireMonitorBiz;
import com.ts.spm.bizs.biz.stat.*;
import com.ts.spm.bizs.entity.equ.SecurityDeviceSub;
import com.ts.spm.bizs.entity.equ.SysMsg;
import com.ts.spm.bizs.entity.equ.SysMsgReceive;
import com.ts.spm.bizs.entity.matter.*;
import com.ts.spm.bizs.entity.msg.MsgReceive;
import com.ts.spm.bizs.entity.person.AjyInfo;
import com.ts.spm.bizs.entity.person.TireMonitor;
import com.ts.spm.bizs.entity.stat.*;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import com.ts.spm.bizs.rest.admin.UserController;
import com.ts.spm.bizs.rest.jzpmq.HandleResultController;
import com.ts.spm.bizs.util.DateUtil;
import com.ts.spm.bizs.vo.stat.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("home")
@CheckClientToken
@CheckUserToken
@EnableScheduling
public class HomeController {
    @Autowired
    CheckPointController checkPointCtr;
    @Autowired
    UserController userCtr;
    @Autowired
    HandleResultController handleResultCtr;

    @Autowired
    DayRegisterBiz dayRegisterBiz;
    @Autowired
    DayProblemBiz dayProblemBiz;
    @Autowired
    PointDangerBiz pointDangerBiz;
    @Autowired
    PackageBiz packageBiz;
    @Autowired
    PassFlowBiz passFlowBiz;
    @Autowired
    DangerInfoBiz dangerInfoBiz;
    @Autowired
    EventInfoBiz eventInfoBiz;
    @Autowired
    AjyBiz ajyBiz;
    @Autowired
    AjyWorkBiz ajyWorkBiz;

    @Autowired
    SecurityDeviceSubBiz securityDeviceSubBiz;
    @Autowired
    SecurityLevelTimeBiz securityLevelTimeBiz;
    @Autowired
    SecurityLevelPersonBiz securityLevelPersonBiz;
    @Autowired
    SecurityLevelPointBiz securityLevelPointBiz;
    @Autowired
    SecurityLevelBiz securityLevelBiz;
    @Autowired
    TireMonitorBiz tireMonitorBiz;
    @Autowired
    InnerCheckBiz innerCheckBiz;
    @Autowired
    MsgReceiveBiz msgReceiveBiz;
    @Autowired
    DrillPlanBiz drillPlanBiz;
    @Autowired
    SysMsgBiz sysMsgBiz;
    @Autowired
    SysMsgReceiveBiz sysMsgReceiveBiz;
    @Autowired
    LogBiz logBiz;

    //用户业务
    @RequestMapping(value = "/work", method = RequestMethod.GET)
    public ObjectRestResponse work(String deptId) {
        Date end=new Date();
        Date start=new Date();
        start=DateUtils.setHours(start, 0);
        start=DateUtils.setMinutes(start, 0);
        start=DateUtils.setSeconds(start, 0);
        start=DateUtils.setMilliseconds(start,0);
        end=DateUtils.setHours(end, 23);
        end=DateUtils.setMinutes(end, 59);
        end=DateUtils.setSeconds(end, 59);
        end=DateUtils.setMilliseconds(end, 999);

        List<Map<String,Object>> departs=checkPointCtr.getpoint(deptId,BaseContextHandler.getUserID());
        List<String> pointIds=departs.stream().map(o->o.get("id").toString()).collect(Collectors.toList());

        WorkVo vo=new WorkVo();
        if(pointIds.isEmpty())
            return ObjectRestResponse.ok(vo);

        vo.setPassengers(passFlowBiz.statis2(pointIds, start, end));
        logBiz.saveLog("首页","统计人身检查量", "api/stat/home/work");
        vo.setPackages(packageBiz.statis2(pointIds, start, end));
        logBiz.saveLog("首页","统计包裹检查量", "api/stat/home/work");
        return ObjectRestResponse.ok(vo);
    }
    //督导检查统计
    @RequestMapping(value = "/direct", method = RequestMethod.GET)
    public ObjectRestResponse direct(String deptId) {
        DirectVo vo=new DirectVo();

        Date end=new Date();
        Date start=new Date();
        start=DateUtils.setHours(start, 0);
        start=DateUtils.setMinutes(start, 0);
        start=DateUtils.setSeconds(start, 0);
        start=DateUtils.setMilliseconds(start,0);
        end=DateUtils.setHours(end, 23);
        end=DateUtils.setMinutes(end, 59);
        end=DateUtils.setSeconds(end, 59);
        end=DateUtils.setMilliseconds(end, 999);

        List<Map<String,Object>> departs=checkPointCtr.getpoint(deptId,BaseContextHandler.getUserID());
        List<String> pointIds=departs.stream().map(o->o.get("id").toString()).collect(Collectors.toList());
        if(pointIds.isEmpty())
            return ObjectRestResponse.ok(vo);

        Example exa = new Example(DayRegister.class);
        Example.Criteria cri = exa.createCriteria();
        cri.andBetween("time", start, end);
        cri.andIn("pointId", pointIds);
        vo.setDirect(dayRegisterBiz.selectCountByExample(exa));
        logBiz.saveLog("首页","统计督导检查量", "api/stat/home/direct");

        exa = new Example(TblInnerCheck.class);
        cri = exa.createCriteria();
        cri.andBetween("checkTime", start, end);
        cri.andIn("pointId", pointIds);
        vo.setCheck(innerCheckBiz.selectCountByExample(exa));
        logBiz.saveLog("首页","统计内部检查量", "api/stat/home/direct");
        return ObjectRestResponse.ok(vo);
    }

    //违禁品统计
    @RequestMapping(value = "/contrabandXian", method = RequestMethod.GET)
    public ObjectRestResponse contraband(String deptId) {
        logBiz.saveLog("首页","分类统计违禁品", "api/stat/home/contrabandXian");


        return handleResultCtr.todayContraband(deptId);
    }

    //安检量/客流 统计
    @RequestMapping(value = "/passenger", method = RequestMethod.GET)
    public ObjectRestResponse passenger(String deptId) {
        Date end=new Date();
        Date start=new Date();
        start=DateUtils.setHours(start, 0);
        start=DateUtils.setMinutes(start, 0);
        start=DateUtils.setSeconds(start, 0);
        start=DateUtils.setMilliseconds(start,0);
        end=DateUtils.setHours(end, 23);
        end=DateUtils.setMinutes(end, 59);
        end=DateUtils.setSeconds(end, 59);
        end=DateUtils.setMilliseconds(end, 999);

        List<Map<String, String>> stations=checkPointCtr.stations(deptId);
        stations=stations.stream().filter(x->"3".equals(x.get("type"))).collect(Collectors.toList());
        for(Map<String,String> station:stations){
            List<Map<String,Object>> points=checkPointCtr.getpoint(station.get("id"),BaseContextHandler.getUserID());
            List<String> pointIds=points.stream().map(o->o.get("id").toString()).collect(Collectors.toList());
            if(pointIds.isEmpty()) {
                station.put("cnt", "0");
            }else{
                int cnt=passFlowBiz.statis2(pointIds, start, end);
                station.put("cnt",cnt+"");
            }
        }

        logBiz.saveLog("首页","当日各车站人身检查量统计", "api/stat/home/passenger");
        return ObjectRestResponse.ok(stations);
    }
    //检查物品/过包数 统计
    @RequestMapping(value = "/packages", method = RequestMethod.GET)
    public ObjectRestResponse packages(String deptId) {
        Date end=new Date();
        Date start=new Date();
        start=DateUtils.addDays(start, -14);
        start=DateUtils.setHours(start, 0);
        start=DateUtils.setMinutes(start, 0);
        start=DateUtils.setSeconds(start, 0);
        start=DateUtils.setMilliseconds(start,0);
        end=DateUtils.setHours(end, 23);
        end=DateUtils.setMinutes(end, 59);
        end=DateUtils.setSeconds(end, 59);
        end=DateUtils.setMilliseconds(end, 999);

        String[] arr=new String[15];
        for (int i = 0; i < 15; i++)
            arr[i]= DateFormatUtils.format(DateUtils.addDays(start, i),"yyyy-MM-dd");

        List<Map<String,Object>> departs=checkPointCtr.getpoint(deptId,BaseContextHandler.getUserID());
        List<String> pointIds=departs.stream().map(o->o.get("id").toString()).collect(Collectors.toList());

        List<StatDayCntVo> list=new ArrayList<>();
        if(!pointIds.isEmpty())
            list=packageBiz.statis(pointIds, start, end);

        for (String day:arr){
            boolean flag=false;
            for (StatDayCntVo map:list){
                if(day.equals(map.getDay())){
                    flag=true;
                    break;
                }
            }
            if(!flag){
                StatDayCntVo vo=new StatDayCntVo();
                vo.setDay(day);
                vo.setCnt(0);
                list.add(vo);
            }
        }
        list.sort(Comparator.comparing(o->o.getDay()));

        logBiz.saveLog("首页","15日物品检查量统计", "api/stat/home/packages");
        return ObjectRestResponse.ok(list);
    }
    //事件
    @RequestMapping(value = "/alarm", method = RequestMethod.GET)
    public ObjectRestResponse alarm(String deptId) {
        EventVo vo=new EventVo();

        Date end=new Date();
        Date start=new Date();
        start=DateUtils.setHours(start, 0);
        start=DateUtils.setMinutes(start, 0);
        start=DateUtils.setSeconds(start, 0);
        start=DateUtils.setMilliseconds(start,0);
        end=DateUtils.setHours(end, 23);
        end=DateUtils.setMinutes(end, 59);
        end=DateUtils.setSeconds(end, 59);
        end=DateUtils.setMilliseconds(end, 999);

        List<Map<String,Object>> departs=checkPointCtr.getpoint(deptId,BaseContextHandler.getUserID());
        List<String> pointIds=departs.stream().map(o->o.get("id").toString()).collect(Collectors.toList());
        if(pointIds.isEmpty())
            return ObjectRestResponse.ok(vo);

        Example exa = new Example(EventInfo.class);
        Example.Criteria cri = exa.createCriteria();
        cri.andBetween("crtTime", start, end);
        cri.andIn("pointId", pointIds);
        cri.andEqualTo("type",1);
        vo.setDanger(eventInfoBiz.selectCountByExample(exa));

        exa = new Example(EventInfo.class);
        cri = exa.createCriteria();
        cri.andBetween("crtTime", start, end);
        cri.andIn("pointId", pointIds);
        cri.andEqualTo("type",2);
        vo.setOther(eventInfoBiz.selectCountByExample(exa));

        //int countAlarm=eventInfoBiz.selectCountFromAlarm(start,end,pointIds);
        //vo.setDanger(vo.getDanger());

        logBiz.saveLog("首页","违禁品事件统计", "api/stat/home/alarm");
        return ObjectRestResponse.ok(vo);
    }
    //安检人员统计
    @RequestMapping(value = "/ajy", method = RequestMethod.GET)
    public ObjectRestResponse ajy(String deptId) {
        AjyVo vo=new AjyVo();   //vo

        Date end=new Date();
        Date start=new Date();
        start=DateUtils.setHours(start, 0);
        start=DateUtils.setMinutes(start, 0);
        start=DateUtils.setSeconds(start, 0);
        start=DateUtils.setMilliseconds(start,0);
        end=DateUtils.setHours(end, 23);
        end=DateUtils.setMinutes(end, 59);
        end=DateUtils.setSeconds(end, 59);
        end=DateUtils.setMilliseconds(end, 999);

        List<Map<String,Object>> departs=checkPointCtr.getpoint(deptId,BaseContextHandler.getUserID());
        List<String> pointIds=departs.stream().map(o->o.get("id").toString()).collect(Collectors.toList());

        if(pointIds.isEmpty())
            return ObjectRestResponse.ok(vo);

        Example exa = new Example(AjyWork.class);
        Example.Criteria cr = exa.createCriteria();
        cr.andBetween("stime", start, end);
        cr.andCondition("STIME=ETIME");
        cr.andIn("pointId", pointIds);
        List<AjyWork> works=ajyWorkBiz.selectByExample(exa);

        int sum=works.size();
        if(sum==0)
            return ObjectRestResponse.ok(vo);
        vo.setSum(sum);

        exa = new Example(AjyInfo.class);
        cr = exa.createCriteria();
        cr.andIn("idcard", works.stream().map(AjyWork::getAjyIdno).collect(Collectors.toList()));
        List<AjyInfo> ajys=ajyBiz.selectByExample(exa);

        long count=ajys.stream().filter(x->x.getSex()==0).count();
        vo.setFemale(count*1.0/sum*1.0);
        vo.setMale((sum-count)*1.0/sum*1.0);

        count=ajys.stream().filter(x->getAge(x.getBirthday())>=18).count();
        System.out.println("G18="+count);
        vo.setG18(count*1.0/sum*1.0);
        vo.setL18((sum-count)*1.0/sum*1.0);

        //持证
        count=ajys.stream().filter(x->x.getSecurityid()!= null).count();
        vo.setUncert(count*1.0/ajys.size()*1.0);
        vo.setCert((ajys.size()*1.0-count*1.0)/ajys.size()*1.0);
//        count=works.stream().filter(x->x.getCardFlag()==0).count();
//        vo.setUncert(count*1.0/sum*1.0);
//        vo.setCert((sum*1.0-count*1.0)/sum*1.0);

        //在岗率
        List<Map<String,String>> departList=checkPointCtr.stations(deptId);
        List<String> stationIds=departList.stream().filter(o->o.get("type").equals("3")).map(o->o.get("id")).collect(Collectors.toList());
        int planPersonCount=0;
        for (String stationId:stationIds){
            planPersonCount = planPersonCount + getPlanPersonCount(stationId,DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        System.out.println("planPersonCount = " + planPersonCount);
        double onjobRate=0.0;
        if(planPersonCount!=0)
            onjobRate=sum*1.0/planPersonCount*1.0;
        vo.setDuty(onjobRate);
        vo.setUnduty(1-onjobRate);

        logBiz.saveLog("首页","安检员统计", "api/stat/home/ajy");
        return ObjectRestResponse.ok(vo);
    }

    //设备运行状态统计
    @RequestMapping(value = "/device", method = RequestMethod.GET)
    public ObjectRestResponse device(String deptId) {
        DeviceVo vo=new DeviceVo();

        List<Map<String,Object>> departs=checkPointCtr.getpoint(deptId,BaseContextHandler.getUserID());
        List<String> pointIds=departs.stream().map(o->o.get("id").toString()).collect(Collectors.toList());

        if(pointIds.isEmpty())
            return ObjectRestResponse.ok(vo);

        Example exa = new Example(SecurityDeviceSub.class);
        Example.Criteria cri = exa.createCriteria();
        cri.andIn("pointId", pointIds);
        List<SecurityDeviceSub> list2=securityDeviceSubBiz.selectByExample(exa);

        List<SecurityDeviceSub> types=list2.stream().filter(o->"8".equals(o.getDevType())).collect(Collectors.toList());
        int sum=types.size();
        vo.setCamera(sum);
        long bad=types.stream().filter(o->o.getIsOnline()==0).count();
        vo.setCameraBreak(bad);

        types=list2.stream().filter(o->"150".equals(o.getDevType())).collect(Collectors.toList());
        sum=types.size();
        vo.setXray(sum);
        bad=types.stream().filter(o->o.getIsOnline()==0).count();
        vo.setXrayBreak(bad);

        types=list2.stream().filter(o->"160".equals(o.getDevType()) || "161".equals(o.getDevType()) || "162".equals(o.getDevType())).collect(Collectors.toList());
        sum=types.size();
        vo.setDoor(sum);
        bad=types.stream().filter(o->o.getIsOnline()==0).count();
        vo.setDoorBreak(bad);

        types=list2.stream().filter(o->"211".equals(o.getDevType())).collect(Collectors.toList());
        sum=types.size();
        vo.setDiscern(sum);
        bad=types.stream().filter(o->o.getIsOnline()==0).count();
        vo.setDiscernBreak(bad);

        logBiz.saveLog("首页","设备运行状态统计", "api/stat/home/device");
        return ObjectRestResponse.ok(vo);
    }

    public static int getAge(Date birthday){
        Calendar now = Calendar.getInstance();
        Calendar born = Calendar.getInstance();
        born.setTime(birthday);
        int age = now.get(Calendar.YEAR)-born.get(Calendar.YEAR);

        if (now.get(Calendar.MONTH) <= born.get(Calendar.MONTH)) {
            if (now.get(Calendar.MONTH)==born.get(Calendar.MONTH) && now.get(Calendar.DAY_OF_MONTH)<born.get(Calendar.DAY_OF_MONTH))
                age--;
            else
                age--;
        }
        return age;
    }

    //疲劳度
    @RequestMapping(value = "/tire", method = RequestMethod.GET)
    public ObjectRestResponse tire(String deptId) {
        Date end=new Date();
        Date start=new Date();
        start=DateUtils.setHours(start, 0);
        start=DateUtils.setMinutes(start, 0);
        start=DateUtils.setSeconds(start, 0);
        start=DateUtils.setMilliseconds(start,0);
        end=DateUtils.setHours(end, 23);
        end=DateUtils.setMinutes(end, 59);
        end=DateUtils.setSeconds(end, 59);
        end=DateUtils.setMilliseconds(end, 999);

        Map<String, Object> m=new HashMap<>();
        m.put("alarmCount", 0);
        List<Map<String, String>> res=new ArrayList<>();
        m.put("statis",res);

        for (int i = 0; i < 24; i++){
            String hour=DateFormatUtils.format(DateUtils.addHours(start,i),"yyyy-MM-dd HH");
            String label=DateFormatUtils.format(DateUtils.addHours(start,i+1),"yyyy-MM-dd HH");
            if(i==23)
                label= DateFormatUtils.format(start,"yyyy-MM-dd")+" 24";
            label=hour;

            Map o=new HashMap();
            o.put("label", label+":00");
            o.put("cnt",0);
            o.put("hour", hour);
            res.add(o);
        }

        List<Map<String,Object>> departs=checkPointCtr.getpoint(deptId,BaseContextHandler.getUserID());
        List<String> pointIds=departs.stream().map(o->o.get("id").toString()).collect(Collectors.toList());
        if(pointIds==null || pointIds.size()==0)
            return ObjectRestResponse.ok(m);

        Example exa=new Example(TireMonitor.class);
        Example.Criteria c=exa.createCriteria();
        c.andEqualTo("hitType", 0);
        c.andBetween("hitTime", start, end);
        c.andIn("pointId", pointIds);
        List<TireMonitor> list=tireMonitorBiz.selectByExample(exa);

        for (Map<String, String> item:res){
            String hour=item.get("hour");
            long cnt=list.stream().filter(o->DateFormatUtils.format(o.getHitTime(), "yyyy-MM-dd HH").startsWith(hour)).count();
            item.put("cnt",cnt+"");
        }

        m.put("alarmCount", list.size());

        logBiz.saveLog("首页","疲劳度统计", "api/stat/home/tire");
        return ObjectRestResponse.ok(m);
    }

    @RequestMapping(value = "/getPlanPersonCount", method = RequestMethod.GET)
    public int getPlanPersonCount(@RequestParam String stationId,@RequestParam String Time) {
        Date time= DateUtil.parse(Time,"yyyy-MM-dd HH:mm:ss");
        List<Map<String, String>> list2=userCtr.getStation(BaseContextHandler.getUserID());//[id,name]
        List<Map<String, String>> depts=checkPointCtr.stations(stationId);
        List<Map<String, Object>> points=checkPointCtr.getpoint(stationId,"");

        int personCount=0;
        if(points.size()>0){
            for(Map<String, Object> pointMap:points){
                String pointId=pointMap.get("id").toString();
                String gatesum=pointMap.get("gatesum").toString();
                int gateSum=Integer.parseInt(gatesum);

                //确认级别
                Example exa = new Example(SecurityLevelPoint.class);
                Example.Criteria crit = exa.createCriteria();
                crit.andEqualTo("pointId",pointId);
                List<SecurityLevelPoint> pointLevelList = securityLevelPointBiz.selectByExample(exa);
                List<String> levelId=new ArrayList<>();
                if(pointLevelList.size()>0){
                    List<String> levelIds=pointLevelList.stream().map(SecurityLevelPoint::getLevelId).collect(Collectors.toList());
                    String str= DateFormatUtils.format(time, "yyyy-MM-dd");

                    List<SecurityLevel> levelList=securityLevelBiz.selectCurrentDate(str);
                    if(levelList.size()>0){
                        levelId.add(levelList.get(0).getId());
                    }else{
                        exa=new Example(SecurityLevel.class);
                        crit = exa.createCriteria();
                        crit.andLike("name","%常态级%");
                        List<SecurityLevel> list =securityLevelBiz.selectByExample(exa);
                        if(null!=list&&list.size()>0){
                            levelId.add(list.get(0).getId());
                        }
                    }
                }else{
                    exa=new Example(SecurityLevel.class);
                    crit = exa.createCriteria();
                    crit.andLike("name","%默认级%");
                    List<SecurityLevel> levelList =securityLevelBiz.selectByExample(exa);
                    if(null!=levelList&&levelList.size()>0){
                        levelId.add(levelList.get(0).getId());
                    }
                }

                //确认时段类型
                String str= DateFormatUtils.format(time, "HH:mm:ss");
                List<SecurityLevelTime> levelTimes = securityLevelTimeBiz.selectCurrentTime(str,levelId);
                int rank=0;
                if(null!=levelTimes&&levelTimes.size()>0) {
                    rank = levelTimes.get(0).getRank();
                }

                //取出人员配置
               /* exa=new Example(SecurityLevelPerson.class);
                crit = exa.createCriteria();
                crit.andIn("levelId", levelId);
                crit.andEqualTo("gateSum",gateSum);*/
                List<SecurityLevelPerson> levelPersons = securityLevelPersonBiz.selectBySecrityLevelPerson(levelId,gateSum);

                //确定应在岗人数
                if(null!=levelPersons&&levelPersons.size()>0){
                    if(rank==0) {
                        personCount = personCount + levelPersons.get(0).getLowerPersonCount();
                    } else if(rank==1){
                        personCount=personCount+levelPersons.get(0).getCommonPersonCount();
                    }else if(rank==2){
                        personCount=personCount+levelPersons.get(0).getHighPersonCount();
                    }
                }

            }
        }

        logBiz.saveLog("首页","应在岗人数统计", "api/stat/home/getPlanPersonCount");
        return personCount;
    }

    public static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
    //消息统计
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ObjectRestResponse info(String deptId) {
        MsgVo vo=new MsgVo();
        //usrMsg
        MsgReceive mr=new MsgReceive();
        mr.setReceiveUserId(BaseContextHandler.getUserID());
        mr.setReadState(0);
        vo.setUsrCnt(msgReceiveBiz.selectCount(mr).intValue());
        logBiz.saveLog("首页","用户消息统计", "api/stat/home/info");

        //sysMsg
        SysMsgReceive smr=new SysMsgReceive();
        smr.setReceiveId(BaseContextHandler.getUserID());
        smr.setReadFlag(0);
        vo.setSysCnt(vo.getSysCnt()+sysMsgReceiveBiz.selectCount(smr).intValue());
        logBiz.saveLog("首页","系统消息统计", "api/stat/home/info");

        return ObjectRestResponse.ok(vo);
    }

    @RequestMapping(value = "/infoDetails", method = RequestMethod.GET)
    public ObjectRestResponse infoDetails(String deptId, String type) {
        List<SysMsg> data=new ArrayList<>();
        Example exa=new Example(SysMsg.class);
        Example.Criteria cr=exa.createCriteria();

        SysMsgReceive smr=new SysMsgReceive();
        smr.setReceiveId(BaseContextHandler.getUserID());
        smr.setReadFlag(0);
        List<SysMsgReceive> list=sysMsgReceiveBiz.selectList(smr);
        List<String> ids=list.stream().map(SysMsgReceive::getPid).collect(Collectors.toList());
        if(ids.isEmpty())
            return ObjectRestResponse.ok(data);

//        cr.andBetween("planStartTime", start,end);    //模拟演练
        cr.andIn("id", ids);
        data=sysMsgBiz.selectByExample(exa);

        logBiz.saveLog("首页","系统消息列表", "api/stat/home/infoDetails");
        return ObjectRestResponse.ok(data);
    }

    @RequestMapping(value = "/infoRead", method = RequestMethod.GET)
    public ObjectRestResponse infoRead(String id) {
        SysMsgReceive smr=new SysMsgReceive();
        smr.setPid(id);
        smr.setReceiveId(BaseContextHandler.getUserID());
        smr.setReadFlag(0);
        List<SysMsgReceive> list=sysMsgReceiveBiz.selectList(smr);
        for (SysMsgReceive o:list){
            o.setReadFlag(1);
            o.setReadTime(new Date());
            sysMsgReceiveBiz.updateById(o);
        }
        logBiz.saveLog("首页","系统消息阅读", "api/stat/home/infoRead");
        return ObjectRestResponse.ok();
    }



    @Scheduled(cron = "0 */10 * * * ?")
    public void checkSysMsg(){
        System.out.println("start check online..."+new Date());

        Date start= DateUtils.addDays(new Date(), 1);
        start = getStartTime(start);
        Date end = getEndTime(start);

        Example exa=new Example(DrillPlan.class);
        Example.Criteria cr=exa.createCriteria();
        cr.andBetween("planStartTime", start,end);

//        List<String> deptIds=new ArrayList<>();
//        if(deptId.isEmpty()) {
//            List<Map<String,String>> departs = usrCtr.dataDepartMap(BaseContextHandler.getUserID());
//            deptIds=departs.stream().map(o->o.get("id")).collect(Collectors.toList());
//        }else{
//            deptIds.add(deptId);
//        }
//        cr.andIn("departId",deptIds);
        List<DrillPlan> drillPlans=drillPlanBiz.selectByExample(exa);

        for (DrillPlan dp:drillPlans){
            exa=new Example(SysMsg.class);
            cr=exa.createCriteria();
            start=getStartTime(new Date());
            end=getEndTime(start);
            cr.andBetween("crtTime", start, end);
            cr.andEqualTo("pid", dp.getId());
            if(sysMsgBiz.selectCountByExample(exa)==0){
                SysMsg msg=new SysMsg();
                msg.setPid(dp.getId());
                msg.setType("drill");
                String msgId=UUIDUtils.generateUuid();
                msg.setId(msgId);
                msg.setContent("模拟演练【"+dp.getTitle()+"】将于【"+sdf.format(dp.getPlanStartTime())+"】开始");
                msg.setCrtTime(new Date());
                sysMsgBiz.insertSelective(msg);

                //接收用户
                List<String> users=sysMsgReceiveBiz.selectUserByDepart(dp.getDepartId());
                for (String userId:users){
                    SysMsgReceive smr=new SysMsgReceive();
                    smr.setId(UUIDUtils.generateUuid());
                    smr.setPid(msgId);
                    smr.setReceiveId(userId);
                    sysMsgReceiveBiz.insertSelective(smr);
                }
            }
        }

    }


    private Date getStartTime(Date date) {
        date=DateUtils.setHours(date, 0);
        date=DateUtils.setMinutes(date, 0);
        date=DateUtils.setSeconds(date, 0);
        date=DateUtils.setMilliseconds(date,0);
        return date;
    }

    private Date getEndTime(Date date) {
        date=DateUtils.setHours(date, 23);
        date=DateUtils.setMinutes(date, 59);
        date=DateUtils.setSeconds(date, 59);
        date=DateUtils.setMilliseconds(date,999);
        return date;
    }


}