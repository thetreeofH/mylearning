package com.ts.spm.bizs.rest.jzpitgcfg;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.TreeUtil;
import com.ts.spm.bizs.biz.jzpitgcfg.TblItgOpenInspectionCfgBiz;
import com.ts.spm.bizs.entity.jzpitgcfg.TblItgOpenInspectionCfg;
import com.ts.spm.bizs.vo.jzpitgcfg.ForbiddenTree;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName OpenInspectionConfigurationController
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/8/24 10:56
 * @Version 1.0
 **/
@RestController
@RequestMapping("itgOpenInspection")
@CheckClientToken
@CheckUserToken
public class ItgOpenInspectionConfigurationController extends BaseController<TblItgOpenInspectionCfgBiz, TblItgOpenInspectionCfg,String> {
//    @Autowired
//    AdminFeign adminfeign;

    /**
     * 新增智能开检策略树
     * @param tblItgOpenInspectionCfg
     * @return
     */
    @RequestMapping(value = "/information/addTree",method = RequestMethod.POST)
    public ObjectRestResponse addTree(@RequestBody TblItgOpenInspectionCfg tblItgOpenInspectionCfg){
        tblItgOpenInspectionCfg.setCreateTime(new Date());
        int result = baseBiz.insertSelective2(tblItgOpenInspectionCfg);
        if(result == 1){
            return ObjectRestResponse.ok();
        }else{
            return ObjectRestResponse.error("保存失败！");
        }

    }

    /**
     *
       *
       * @Description 获取与用户权限无关智能开检策略整颗树
       * @Author fengzheng
       * @Date 2020/8/25 10:08
       * @Param
       * @Return
       */
    @RequestMapping(value = "/information/itgOipCfgTree",method = RequestMethod.GET)
    public ObjectRestResponse itgOipCfgTree(){
        Example example = new Example(TblItgOpenInspectionCfg.class);
        example.setOrderByClause("order_num asc");
        List<TblItgOpenInspectionCfg> result = baseBiz.selectByExample(example);
        List<ForbiddenTree> trees = new ArrayList<ForbiddenTree>();
        result.forEach(TblItgOpenInspectionCfg -> {
            trees.add(new ForbiddenTree(TblItgOpenInspectionCfg.getId(), TblItgOpenInspectionCfg.getName(), TblItgOpenInspectionCfg.getParentId(), TblItgOpenInspectionCfg.getCode().toString(), TblItgOpenInspectionCfg.getType(), TblItgOpenInspectionCfg.getStatus(), TblItgOpenInspectionCfg.getCreateTime(), Integer.parseInt(TblItgOpenInspectionCfg.getOrderNum().toString()),Boolean.getBoolean(TblItgOpenInspectionCfg.getIsDisabled()),TblItgOpenInspectionCfg.getTreshold()));
        });
        List<ForbiddenTree> list= TreeUtil.bulid(trees, "-1", null);
        return ObjectRestResponse.ok(list);
    }

    /**
     * 更新智能开检策略树
     * @param tblItgOpenInspectionCfg
     * @return
     */
    @RequestMapping(value = "/information/updateTree",method = RequestMethod.PUT)
    public ObjectRestResponse updateTree(@RequestBody TblItgOpenInspectionCfg tblItgOpenInspectionCfg){
        tblItgOpenInspectionCfg.setUpdateTime(new Date());
        int result = baseBiz.updateSelectiveById2(tblItgOpenInspectionCfg);
        if(result == 1){
            return ObjectRestResponse.ok();
        }else {
            return ObjectRestResponse.error("更新失败！");
        }
    }

    /**
     * 删除智能开检策略树
     * @param id
     * @return
     */
    @RequestMapping(value = "/information/deleteTree/{id}",method = RequestMethod.DELETE)
    public ObjectRestResponse deleteTree(@PathVariable String id){
        int result = baseBiz.deleteById2(id);
        if(result == 1){
            return ObjectRestResponse.ok();
        }else {
            return ObjectRestResponse.error("删除失败！");
        }
    }
}
