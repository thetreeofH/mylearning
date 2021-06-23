package com.ts.spm.bizs.rest.gis;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.gis.DefenceDevBiz;
import com.ts.spm.bizs.entity.gis.DefenceDev;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by zhoukun on 2020/4/18.
 */
@RestController
@RequestMapping("dev")
@CheckClientToken
@CheckUserToken
@Api(tags = "防区图设备管理")
public class DefenceDevController extends BaseController<DefenceDevBiz, DefenceDev, String> {
    @Autowired
    private DefenceDevBiz defenceDevBiz;

    @ApiOperation("获取防区图设备列表")
    @RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse list(@PathVariable("id") String id) {
        try {
//            DefenceDev dev = new DefenceDev();
//            dev.setDepartId(id);
//            List<DefenceDev> list = baseBiz.selectList(dev);
            List<DefenceDev> list = defenceDevBiz.getDevInfo(id);
            return new TableResultResponse(list.size(), list);
        } catch (Exception e) {
            return TableResultResponse.error(e.getMessage());
        }
    }

    @ApiOperation("保存防区图设备列表")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public ObjectRestResponse save(@RequestBody DefenceDev[] list) {
        try {
            for (DefenceDev item : list) {
                if(item.getId().startsWith("temp")) {
                    item.setId(UUIDUtils.generateUuid());
                    baseBiz.insertSelective2(item);
                } else {
                    baseBiz.updateSelectiveById2(item);
                }
            }
            return ObjectRestResponse.ok();
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ObjectRestResponse.error(e.getMessage());
        }
    }
}
