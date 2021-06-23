package com.ts.spm.bizs.rest.msg;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ts.spm.bizs.biz.msg.BoundsDeviceBiz;
import com.ts.spm.bizs.entity.msg.BoundsDevice;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by zhoukun on 2020/4/17.
 */
@RestController
@RequestMapping("boundsDevice")
@CheckClientToken
@CheckUserToken
public class BoundsDeviceController extends BaseController<BoundsDeviceBiz, BoundsDevice, Integer> {

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ObjectRestResponse addEntity(@RequestBody BoundsDevice o) {
        Integer id=baseBiz.getId();
        o.setId(id);
        baseBiz.insertSelective(o);
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ObjectRestResponse getEntity(@PathVariable Integer id) {
        BoundsDevice o=baseBiz.selectById(id);
        return ObjectRestResponse.ok(o);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse delEntity(@PathVariable Integer id) {
        baseBiz.deleteById(id);
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/getpage", method = RequestMethod.GET)
    public TableResultResponse page(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "") String lineId, @RequestParam(required = false) Integer code,
                                    @RequestParam(defaultValue = "") String ip, @RequestParam(defaultValue = "") String port) {
        Page<BoundsDevice> result = PageHelper.startPage(page, limit);
        List<BoundsDevice> list = baseBiz.selectByLine(lineId,code,ip,port);

        return new TableResultResponse<>(result.getTotal(),list);
    }

}
