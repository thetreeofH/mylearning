package com.ts.spm.bizs.rest.gis;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ts.spm.bizs.biz.gis.DefenceAreaBiz;
import com.ts.spm.bizs.entity.gis.DefenceArea;
import com.ts.spm.bizs.rest.admin.DepartController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by zhoukun on 2020/4/17.
 */
@RestController
@RequestMapping("da")
@CheckClientToken
@CheckUserToken
@Api(tags = "防区图管理")
public class DefenceAreaController extends BaseController<DefenceAreaBiz, DefenceArea, String> {

    @Autowired
    private DepartController departCtr;

    @ApiOperation("获取防区图列表")
    @RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse list(@PathVariable("id") String id) {
        DefenceArea area = new DefenceArea();
        area.setDepartId(id);
//        ObjectRestResponse info = adminFeign.gis_getDepart(id);
        ObjectRestResponse info=departCtr.get(id);
        if(info.getData() != null) {
            String name = ((LinkedHashMap) info.getData()).get("name").toString();
            List<DefenceArea> list = baseBiz.selectList(area);
            for(DefenceArea item : list) {
                item.setDepartName(name);
            }
            return new TableResultResponse(list.size(), list);
        }
        return TableResultResponse.error("查找不到部门信息");
    }
}
