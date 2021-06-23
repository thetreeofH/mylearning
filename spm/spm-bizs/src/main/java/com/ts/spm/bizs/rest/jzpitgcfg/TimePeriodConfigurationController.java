package com.ts.spm.bizs.rest.jzpitgcfg;

import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.jzpitgcfg.TblTimePeriodCfgBiz;
import com.ts.spm.bizs.entity.jzpitgcfg.TblTimePeriodCfg;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName TimePeriodConfigurationController
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/8/24 11:11
 * @Version 1.0
 **/
@RestController
@RequestMapping("timePeriod")
@CheckClientToken
@CheckUserToken
public class TimePeriodConfigurationController extends BaseController<TblTimePeriodCfgBiz, TblTimePeriodCfg,String> {

    @Autowired
    CheckPointController checkPointCtr;

    @Autowired
    TblTimePeriodCfgBiz tblTimePeriodCfgBiz;
    /**
     * 新增高峰时段配置
     * @param tblTimePeriodCfg
     * @return
     */
    @RequestMapping(value = "/information/add",method = RequestMethod.POST)
    public ObjectRestResponse add(@RequestBody TblTimePeriodCfg tblTimePeriodCfg){
        tblTimePeriodCfg.setId(UUIDUtils.generateUuid());
        tblTimePeriodCfg.setCreateTime(new Date());
        int result = baseBiz.insertSelective2(tblTimePeriodCfg);
        if(result == 1){
            return ObjectRestResponse.ok();
        }else {
            return ObjectRestResponse.error("保存失败");
        }

    }

    /**
     * 更新高分时段配置
     * @param tblTimePeriodCfg
     * @return
     */
    @RequestMapping(value = "/information/update",method = RequestMethod.PUT)
    public ObjectRestResponse update(@RequestBody TblTimePeriodCfg tblTimePeriodCfg){
        tblTimePeriodCfg.setUpdateTime(new Date());
        int result = baseBiz.updateSelectiveById2(tblTimePeriodCfg);
        if(result == 1){
            return ObjectRestResponse.ok();
        }else{
            return ObjectRestResponse.error("更新失败");
        }
    }

    /**
     * 删除高峰时段配置
     * @param id
     * @return
     */
    @RequestMapping(value = "/information/delete/{id}",method = RequestMethod.DELETE)
    public ObjectRestResponse delete(@PathVariable String id){
        int result = baseBiz.deleteById2(id);
        if(result == 1){
            return ObjectRestResponse.ok();
        }else{
            return ObjectRestResponse.error("删除失败");
        }
    }

    /**
     * 高峰时段配置列表查询
     * @param limit
     * @param page
     * @param departId
     * @param pointId
     * @return
     */
    @RequestMapping(value = "/information/query",method = RequestMethod.GET)
    public TableResultResponse query(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "") String departId, @RequestParam(defaultValue = "") String pointId){
        List<Map<String, Object>> points=checkPointCtr.getpoint(departId, BaseContextHandler.getUserID());
        if(CollectionUtils.isEmpty(points)) {
            return new TableResultResponse().error("该组织无安检点");
        }
        List<String>  pointIds = points.stream().map(u->u.get("id").toString()).collect(Collectors.toList());
        Page<Object> result = PageHelper.startPage(page, limit);
        List<TblTimePeriodCfg> timePeriodList = tblTimePeriodCfgBiz.query(pointIds,pointId);
        return new TableResultResponse(result.getTotal(),timePeriodList);

    }
}
