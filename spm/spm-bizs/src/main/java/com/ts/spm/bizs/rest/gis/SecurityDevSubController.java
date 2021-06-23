package com.ts.spm.bizs.rest.gis;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ts.spm.bizs.biz.equ.SecurityDeviceSubBiz;
import com.ts.spm.bizs.entity.equ.SecurityDeviceSub;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by zhoukun on 2020/10/9.
 */
@RestController
@RequestMapping("cam")
@CheckClientToken
@CheckUserToken
public class SecurityDevSubController extends BaseController<SecurityDeviceSubBiz, SecurityDeviceSub, String> {

    @RequestMapping(value = "/getpage", method = RequestMethod.GET)
    public TableResultResponse page(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "") String stationId) {

        Page<SecurityDeviceSub> result = PageHelper.startPage(page, limit);
        List<SecurityDeviceSub> list = baseBiz.gis_selectList(stationId);
        return new TableResultResponse<>(result.getTotal(), list);
    }

}
