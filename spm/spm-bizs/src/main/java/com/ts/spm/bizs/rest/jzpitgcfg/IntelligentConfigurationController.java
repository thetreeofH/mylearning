package com.ts.spm.bizs.rest.jzpitgcfg;

import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.DateUtil;
import com.github.wxiaoqi.security.common.util.TreeUtil;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.jzpitgcfg.TblIntelligentCfgBiz;
import com.ts.spm.bizs.biz.jzpitgcfg.TblItgOpenInspectionCfgBiz;
import com.ts.spm.bizs.biz.jzpitgcfg.TblTimePeriodCfgBiz;
import com.ts.spm.bizs.entity.jzpitgcfg.IntelligentJudgePictureCfg;
import com.ts.spm.bizs.entity.jzpitgcfg.TblIntelligentCfg;
import com.ts.spm.bizs.entity.jzpitgcfg.TblItgOpenInspectionCfg;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import com.ts.spm.bizs.vo.jzpitgcfg.ForbiddenTree;
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

/**
 * @ClassName IntelligentConfigurationController
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/8/21 13:42
 * @Version 1.0
 **/
@RestController
@RequestMapping(value = "intelligentConfiguration")
@CheckClientToken
@CheckUserToken
public class IntelligentConfigurationController extends BaseController<TblIntelligentCfgBiz, TblIntelligentCfg,String> {
    @Autowired
    TblIntelligentCfgBiz tblIntelligentCfgBiz;
    @Autowired
    TblTimePeriodCfgBiz tblTimePeriodCfgBiz;
    @Autowired
    TblItgOpenInspectionCfgBiz tblItgOpenInspectionCfgBiz;
    @Autowired
    CheckPointController checkPointCtr;
//    AdminFeign adminFeign;

    /**
     * 新增智能判图配置
     * @param tblIntelligentCfg
     * @return
     */
    @RequestMapping(value = "/information/add",method = RequestMethod.POST)
    public ObjectRestResponse add(@RequestBody TblIntelligentCfg tblIntelligentCfg){
        tblIntelligentCfg.setId(UUIDUtils.generateUuid());
        tblIntelligentCfg.setCreateTime(new Date());
        int result = baseBiz.insertSelective2(tblIntelligentCfg);
        if(result == 1){
            return ObjectRestResponse.ok();
        }else{
            return ObjectRestResponse.error("保存失败！");
        }
    }

    /**
     * 更新智能判图配置
     * @param tblIntelligentCfg
     * @return
     */
    @RequestMapping(value = "/information/update",method = RequestMethod.PUT)
    public ObjectRestResponse update(@RequestBody TblIntelligentCfg tblIntelligentCfg){
        tblIntelligentCfg.setUpdateTime(new Date());
        int result = baseBiz.updateSelectiveById2(tblIntelligentCfg);
        if(result == 1){
            return ObjectRestResponse.ok();
        }else{
            return ObjectRestResponse.error("保存失败！");
        }
    }

    /**
     * 智能判图配置列表查询
     * @param limit
     * @param page
     * @param startTime
     * @param endTime
     * @param departId
     * @param pointId
     * @return
     */
    @RequestMapping(value = "/information/query",method = RequestMethod.GET)
    public TableResultResponse query(@RequestParam(defaultValue = "10") int limit,
                                     @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "") String startTime,
                                     @RequestParam(defaultValue = "") String endTime,
                                     @RequestParam(defaultValue = "") String departId,
                                     @RequestParam(defaultValue = "") String pointId){

        List<Map<String, Object>> points=checkPointCtr.getpoint(departId, BaseContextHandler.getUserID());
        if(CollectionUtils.isEmpty(points)) {
            return new TableResultResponse().error("该组织无安检点");
        }
        List<String>  pointIds = points.stream().map(u->u.get("id").toString()).collect(Collectors.toList());

        //构建智能开检策略树
        List<TblItgOpenInspectionCfg> result = tblItgOpenInspectionCfgBiz.selectListAll();
        List<ForbiddenTree> trees = new ArrayList<ForbiddenTree>();
        result.forEach(TblItgOpenInspectionCfg -> {
            trees.add(new ForbiddenTree(TblItgOpenInspectionCfg.getId(), TblItgOpenInspectionCfg.getName(), TblItgOpenInspectionCfg.getParentId(), TblItgOpenInspectionCfg.getCode().toString(), TblItgOpenInspectionCfg.getType(), TblItgOpenInspectionCfg.getStatus(), TblItgOpenInspectionCfg.getCreateTime(), Integer.parseInt(TblItgOpenInspectionCfg.getOrderNum().toString()),Boolean.getBoolean(TblItgOpenInspectionCfg.getIsDisabled()),TblItgOpenInspectionCfg.getTreshold()));
        });

        //查询智能开检配置
        Example example = new Example(TblIntelligentCfg.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
            criteria.andBetween("createTime", DateUtil.stringToDate(startTime),DateUtil.stringToDate(endTime));
        }
        if(pointIds != null){
            criteria.andIn("pointId",pointIds);
        }
        if(StringUtils.isNotBlank(pointId)){
            criteria.andEqualTo("pointId",pointId);
        }
        Page<TblIntelligentCfg> itgCfgPage = PageHelper.startPage(page,limit);
        List<TblIntelligentCfg> tblIntelligentCfgList = baseBiz.selectByExample(example);
        //构建智能判图配置列表
        List<IntelligentJudgePictureCfg> intelligentJudgePictureCfgList = new ArrayList<>();
        tblIntelligentCfgList.forEach(tblIntelligentCfg -> intelligentJudgePictureCfgList.add(new IntelligentJudgePictureCfg(tblIntelligentCfg.getId(),tblIntelligentCfg.getPointId(),tblIntelligentCfg.getPointName(),tblIntelligentCfg.getStationId(),tblIntelligentCfg.getStationName(),tblIntelligentCfg.getIfIntelligentJudgePicture(),tblIntelligentCfg.getPersonReviewProportion(),
                tblTimePeriodCfgBiz.query(pointIds,pointId),TreeUtil.bulid(trees, "-1", null))));

        return new TableResultResponse(itgCfgPage.getTotal(),intelligentJudgePictureCfgList);
    }
}
