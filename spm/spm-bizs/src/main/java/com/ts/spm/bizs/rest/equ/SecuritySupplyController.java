package com.ts.spm.bizs.rest.equ;

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
import com.ts.spm.bizs.biz.LogBiz;
import com.ts.spm.bizs.biz.equ.SecuritySupplyBiz;
import com.ts.spm.bizs.biz.equ.SecuritySupplyInoutBiz;
import com.ts.spm.bizs.biz.equ.SysMsgBiz;
import com.ts.spm.bizs.biz.equ.SysMsgReceiveBiz;
import com.ts.spm.bizs.entity.equ.SecuritySupply;
import com.ts.spm.bizs.entity.equ.SecuritySupplyInout;
import com.ts.spm.bizs.entity.equ.SysMsg;
import com.ts.spm.bizs.entity.equ.SysMsgReceive;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import com.ts.spm.bizs.rest.admin.DepartController;
import com.ts.spm.bizs.rest.dict.DictTypeController;
import com.ts.spm.bizs.rest.dict.DictValueController;
import com.ts.spm.bizs.util.PoiUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by zhoukun on 2020/4/17.
 */
@RestController
@RequestMapping("supply")
@CheckClientToken
@CheckUserToken
public class SecuritySupplyController extends BaseController<SecuritySupplyBiz, SecuritySupply, String> {
    @Autowired
    SecuritySupplyInoutBiz supplyInoutBiz;
    @Autowired
    SysMsgBiz sysMsgBiz;
    @Autowired
    SysMsgReceiveBiz sysMsgReceiveBiz;
    @Autowired
    LogBiz logBiz;
    @Autowired
    private DictValueController dictValueCtr;
    @Autowired
    private DictTypeController dictTypeCtr;
    @Autowired
    CheckPointController checkPointCtr;
    @Autowired
    DepartController departCtr;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ObjectRestResponse addEntity(@RequestBody SecuritySupply o){
        SecuritySupply entity=new SecuritySupply();
        entity.setCode(o.getCode());
        entity.setDeptId(o.getDeptId());
        List list=baseBiz.selectList(entity);

        if(!list.isEmpty()){
            return ObjectRestResponse.error("物资已存在！");
        }

        o.setId(UUIDUtils.generateUuid());
        o.setCrtTime(new Date());
        o.setCrtUserId(BaseContextHandler.getUserID());
        o.setCrtUserName(BaseContextHandler.getUsername());
        baseBiz.insertSelective(o);

        logBiz.saveLog("安防物资管理","添加物资", "api/equ/supply/add");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ObjectRestResponse getEntity(@PathVariable String id) {
        logBiz.saveLog("安防物资管理","物资详情", "api/equ/supply/get");
        return ObjectRestResponse.ok(baseBiz.selectById(id));
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ObjectRestResponse updateEntity(@PathVariable String id,@RequestBody SecuritySupply o) {
        o.setUpdTime(new Date());
        o.setUpdUserId(BaseContextHandler.getUserID());
        o.setUpdUserName(BaseContextHandler.getUsername());
        o.setUpdDeptId(BaseContextHandler.getDepartID());
        baseBiz.updateSelectiveById(o);
        logBiz.saveLog("安防物资管理","物资更新", "api/equ/supply/update");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse delEntity(@PathVariable String id) {
        String[] arr=id.split(",");
        for (String str:arr){
            baseBiz.deleteById(str);
        }
        logBiz.saveLog("安防物资管理","物资删除", "api/equ/supply/delete");
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/getpage", method = RequestMethod.GET)
    public TableResultResponse page(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "")  String code, @RequestParam(defaultValue = "") String name,
                                    @RequestParam(defaultValue = "")  String typeId, @RequestParam(defaultValue = "") String deptId) {
        Example exa = new Example(SecuritySupply.class);
        Example.Criteria crit = exa.createCriteria();
        if(!code.equals(""))
            crit.andLike("code", "%"+code+"%");
        if(!name.equals(""))
            crit.andLike("name", "%"+name+"%");
        if(!typeId.equals(""))
            crit.andEqualTo("typeId", typeId);

        List<Map<String, String>> departs=checkPointCtr.stations(deptId);
        if(departs.isEmpty())
            return  new TableResultResponse<>(departs.size(),departs);

        crit.andIn("deptId", departs.stream().map(o->o.get("id")).collect(Collectors.toList()));

        Page<SecuritySupply> result = PageHelper.startPage(page, limit);
        List<SecuritySupply> list = baseBiz.selectByExample(exa);

        for (SecuritySupply o:list){
            List<Map<String,String>> up_departs=departCtr.getUpDepart(o.getDeptId());
            for(Map<String,String> map:up_departs){
                if("1".equals(map.get("type")))
                    o.setLine(map.get("name"));
            }
        }
        logBiz.saveLog("安防物资管理","物资分页查询", "api/equ/supply/getpage");
        return  new TableResultResponse<>(result.getTotal(),list);
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(HttpServletResponse res,
                       @RequestParam(defaultValue = "")  String code, @RequestParam(defaultValue = "") String name,
                       @RequestParam(defaultValue = "")  String typeId, @RequestParam(defaultValue = "") String deptId) throws IOException {
        Example exa = new Example(SecuritySupply.class);
        Example.Criteria crit = exa.createCriteria();
        if(!code.equals(""))
            crit.andLike("code", "%"+code+"%");
        if(!name.equals(""))
            crit.andLike("name", "%"+name+"%");
        if(!typeId.equals(""))
            crit.andEqualTo("typeId", typeId);

        List<Map<String, String>> departs=checkPointCtr.stations(deptId);
        if(departs.isEmpty())
            return;

        crit.andIn("deptId", departs.stream().map(o->o.get("id")).collect(Collectors.toList()));

        List<SecuritySupply> data = baseBiz.selectByExample(exa);

        for (SecuritySupply o:data){
            List<Map<String,String>> up_departs=departCtr.getUpDepart(o.getDeptId());
            for(Map<String,String> map:up_departs){
                if("1".equals(map.get("type")))
                    o.setLine(map.get("name"));
            }
        }
        String colHeads[] = { "序号", "物资编号", "物资名称", "物资类型", "状态", "数量", "预警值", "负责人", "联系电话", "线路", "车站" };
        String keys[] = { "index", "code", "name", "type", "state", "sum", "warningNum", "person", "phone", "line", "dept" };

        List<Map> list=new ArrayList(data.size());
        for (int i = 0; i < data.size(); i++) {
            BeanMap bm=BeanMap.create(data.get(i));
            Map hm=new HashMap();
            hm.putAll(bm);
            hm.put("index",(i+1));
            list.add(hm);
        }
        PoiUtil.start_download(request,res,System.currentTimeMillis()+".xls", list, colHeads, keys);

        logBiz.saveLog("安防物资管理","物资导出", "api/equ/supply/export");
    }

    @RequestMapping(value = "/dictType/{code}", method = RequestMethod.GET)
    public ObjectRestResponse getDictTypes(@PathVariable("code") String code) {
        List<Map<String,Object>> maps=dictTypeCtr.getDictTypeByCode(code);
        return ObjectRestResponse.ok(maps);
    }

    @RequestMapping(value = "/dictValue/{code}", method = RequestMethod.GET)
    public ObjectRestResponse getDictValues(@PathVariable("code") String code) {
        List<Map<String,Object>> maps=dictValueCtr.dictValueByCode(code);
        return ObjectRestResponse.ok(maps);
    }

    @RequestMapping(value = "/inout/getpage", method = RequestMethod.GET)
    public TableResultResponse page(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "")  String code, @RequestParam(defaultValue = "") String name,
                                    @RequestParam(defaultValue = "")  String typeId, @RequestParam(defaultValue = "") String deptId,
                                    @RequestParam(defaultValue = "-1") int action, @RequestParam(defaultValue = "") String start, @RequestParam(defaultValue = "") String end) throws ParseException {
        Example exa = new Example(SecuritySupplyInout.class);
        Example.Criteria crit = exa.createCriteria();
        if(action!=-1)
            crit.andEqualTo("action", action);
        if(!code.equals(""))
            crit.andLike("code", "%"+code+"%");
        if(!name.equals(""))
            crit.andLike("name", "%"+name+"%");
        if(!typeId.equals(""))
            crit.andEqualTo("typeId", typeId);

        List<Map<String, String>> departs=checkPointCtr.stations(deptId);
        if(departs.isEmpty())
            return  new TableResultResponse<>(departs.size(),departs);

        crit.andIn("deptId", departs.stream().map(o->o.get("id")).collect(Collectors.toList()));

        if(!start.isEmpty() && !end.isEmpty()){
            Date st=DateUtils.parseDate(start, "yyyy-MM-dd HH:mm:ss");
            Date ed=DateUtils.parseDate(end, "yyyy-MM-dd HH:mm:ss");
            crit.andBetween("actionDate", st, ed);
        }
        exa.setOrderByClause("crt_time desc");

        Page<SecuritySupplyInout> result = PageHelper.startPage(page, limit);
        List<SecuritySupplyInout> list = supplyInoutBiz.selectByExample(exa);

        for (SecuritySupplyInout o:list){
            List<Map<String,String>> up_departs=departCtr.getUpDepart(o.getDeptId());
            for(Map<String,String> map:up_departs){
                if("1".equals(map.get("type")))
                    o.setLine(map.get("name"));
            }
        }
        logBiz.saveLog("安防物资管理","物资出入库查询", "api/equ/supply/inout/getpage");
        return  new TableResultResponse<>(result.getTotal(),list);
    }

    @RequestMapping(value = "/inout/export", method = RequestMethod.GET)
    public void inoutExport(HttpServletResponse res,
                                    @RequestParam(defaultValue = "")  String code, @RequestParam(defaultValue = "") String name,
                                    @RequestParam(defaultValue = "")  String typeId, @RequestParam(defaultValue = "") String deptId,
                                    @RequestParam(defaultValue = "-1") int action, @RequestParam(defaultValue = "") String start, @RequestParam(defaultValue = "") String end) throws ParseException, IOException {
        Example exa = new Example(SecuritySupplyInout.class);
        Example.Criteria crit = exa.createCriteria();
        if(action!=-1)
            crit.andEqualTo("action", action);
        if(!code.equals(""))
            crit.andLike("code", "%"+code+"%");
        if(!name.equals(""))
            crit.andLike("name", "%"+name+"%");
        if(!typeId.equals(""))
            crit.andEqualTo("typeId", typeId);

        List<Map<String, String>> departs=checkPointCtr.stations(deptId);
        if(departs.isEmpty())
            return;

        crit.andIn("deptId", departs.stream().map(o->o.get("id")).collect(Collectors.toList()));

        if(!start.isEmpty() && !end.isEmpty()){
            Date st=DateUtils.parseDate(start, "yyyy-MM-dd HH:mm:ss");
            Date ed=DateUtils.parseDate(end, "yyyy-MM-dd HH:mm:ss");
            crit.andBetween("actionDate", st, ed);
        }
        exa.setOrderByClause("crt_time desc");

        List<SecuritySupplyInout> data = supplyInoutBiz.selectByExample(exa);

        for (SecuritySupplyInout o:data){
            List<Map<String,String>> up_departs=departCtr.getUpDepart(o.getDeptId());
            for(Map<String,String> map:up_departs){
                if("1".equals(map.get("type")))
                    o.setLine(map.get("name"));
            }
        }

        String colHeads[] = { "序号", "物资编号", "物资名称", "物资类型", "线路", "车站", "数量", "出入库日期", "出入库类型" };
        String keys[] = { "index", "code", "name", "type", "line", "dept", "num", "actionDate", "actionPerson", "action" };

        List<Map> list=new ArrayList(data.size());
        for (int i = 0; i < data.size(); i++) {
            BeanMap bm=BeanMap.create(data.get(i));
            Map hm=new HashMap();
            hm.putAll(bm);
            hm.put("index",(i+1));
            list.add(hm);
        }
        PoiUtil.start_download(request,res,System.currentTimeMillis()+".xls", list, colHeads, keys);

        logBiz.saveLog("安防物资管理","物资出入库导出", "api/equ/supply/inout/export");
    }

    @RequestMapping(value = "/inout", method = RequestMethod.POST)
    @Transactional(rollbackFor=Exception.class)
    public ObjectRestResponse addSupply(@RequestBody SecuritySupplyInout o){
        String pid=o.getPid();
        SecuritySupply supply=baseBiz.selectById(pid);
        if(o.getAction()==0) {
            supply.setSum(supply.getSum() + o.getNum());
        }else{
            if(supply.getSum()-o.getNum()<0)
                return ObjectRestResponse.error(500,"库存不足");
            supply.setSum(supply.getSum()-o.getNum());
        }
        supply.setUpdTime(new Date());
        supply.setUpdUserId(BaseContextHandler.getUserID());
        supply.setUpdUserName(BaseContextHandler.getUsername());
        supply.setUpdDeptId(BaseContextHandler.getDepartID());
        baseBiz.updateSelectiveById(supply);

        if(supply.getSum()<=supply.getWarningNum()){
            createSysMsg(supply);
        }

        o.setId(UUIDUtils.generateUuid());
        o.setCode(supply.getCode());
        o.setName(supply.getName());
        o.setTypeId(supply.getTypeId());
        o.setType(supply.getType());
        o.setPerson(supply.getPerson());
        o.setDeptId(supply.getDeptId());
        o.setDept(supply.getDept());
        o.setCrtTime(new Date());
        o.setCrtUserId(BaseContextHandler.getUserID());
        o.setCrtUserName(BaseContextHandler.getUsername());
        o.setUpdDeptId(BaseContextHandler.getDepartID());
        supplyInoutBiz.insertSelective(o);

        logBiz.saveLog("安防物资管理","物资出库入库", "api/equ/supply/inout");
        return ObjectRestResponse.ok();
    }

    private void createSysMsg(SecuritySupply supply) {
        SysMsg msg=new SysMsg();
        msg.setPid(supply.getId());
        msg.setType("supply");
        String msgId= UUIDUtils.generateUuid();
        msg.setId(msgId);
        msg.setContent("安防物资【"+supply.getName()+"】库存告警，剩余【"+supply.getSum()+"】");
        msg.setCrtTime(new Date());
        sysMsgBiz.insertSelective(msg);

        //接收用户
        List<String> users=sysMsgReceiveBiz.selectUserByDepart(supply.getDeptId());
        for (String userId:users){
            SysMsgReceive smr=new SysMsgReceive();
            smr.setId(UUIDUtils.generateUuid());
            smr.setPid(msgId);
            smr.setReceiveId(userId);
            sysMsgReceiveBiz.insertSelective(smr);
        }
    }
}
