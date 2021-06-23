package com.ts.spm.bizs.rest.admin;

import com.alibaba.fastjson.JSONArray;
import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.StringUtil;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.admin.EmployeeBiz;
import com.ts.spm.bizs.biz.admin.RealTimeVideoBiz;
import com.ts.spm.bizs.entity.admin.Employee;
import com.ts.spm.bizs.entity.admin.RealTimeVideo;
import com.ts.spm.bizs.vo.admin.EmployVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by lihaiping on 2021/5/31.
 */
@RestController
@RequestMapping("realTimeVideo")
//@CheckUserToken
//@CheckClientToken
@Api(tags = "实时视频配置")
public class RealTimeVideoController extends BaseController<RealTimeVideoBiz, RealTimeVideo, String> {



    @ApiOperation("实时视频配置列表")
    @RequestMapping(value = "/videoConfigPage", method = RequestMethod.GET)
    public TableResultResponse videoConfigPage(String stationId, String lineId, String pointId,
                                               @RequestParam(name = "limit", defaultValue = "10") int limit,
                                               @RequestParam(name = "page", defaultValue = "1") int page) {

        Page<Object> result = PageHelper.startPage(page, limit);
        List<RealTimeVideo> configs = baseBiz.getRealTimeVideoList(stationId, pointId, lineId);
        if(null!=configs&&configs.size()>0){
            for (RealTimeVideo rf:configs) {
                String[] devids=rf.getDevId().split(",");
                StringBuffer sb=new StringBuffer();
                if(null!=devids&&devids.length>0){
                    for (int i = 0; i <devids.length; i++) {
                        RealTimeVideo rt=baseBiz.selectDeviceByDevId(devids[i]);
                        if(i>0){
                           sb.append(",");
                        }
                        sb.append(rt.getDevName());
                    }
                }
                rf.setDevName(sb.toString());
            }
        }
        return new TableResultResponse(result.getTotal(), configs);
    }

    @ApiOperation("获取安检点对应的设备信息")
    @RequestMapping(value = "/querydeviceOfPoint", method = RequestMethod.GET)
    public ObjectRestResponse querydeviceOfPoint(String pointId) {
        List<RealTimeVideo> configs = baseBiz.selectRealTimeByPointId(pointId);
        if (null != configs && configs.size() > 0) {
            return ObjectRestResponse.ok(configs);
        }
        return ObjectRestResponse.ok(configs);
    }

    @ApiOperation("配置删除")
    @RequestMapping(value = "/deleteVideoConfig/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse deleteVideoConfig(@PathVariable String id) {
        try {
            baseBiz.delRealTimeVideo(id);
            return ObjectRestResponse.ok("删除成功!");
        }catch (Exception e){
            return ObjectRestResponse.error(e.getMessage());
        }
    }

    @ApiOperation("修改配置信息")
    @RequestMapping(value = "updateVideoConfig", method = RequestMethod.POST)
    public ObjectRestResponse updateVideoConfig(@RequestBody RealTimeVideo realTimeVideo) {

        try {

            RealTimeVideo entity = this.baseBiz.selectDeviceById(realTimeVideo.getId());
            entity.setAlarmType(realTimeVideo.getAlarmType());
            entity.setDevId(realTimeVideo.getDevId());
            entity.setLineId(realTimeVideo.getLineId());
            entity.setStationId(realTimeVideo.getStationId());
            entity.setPointId(realTimeVideo.getPointId());
            baseBiz.updateSelectiveById(entity);
            return ObjectRestResponse.ok("修改成功!");
        } catch (Exception e) {
            return ObjectRestResponse.error(e.getMessage());
        }
    }

    @ApiOperation("新增配置信息")
    @RequestMapping(value = "addVideoConfig", method = RequestMethod.POST)
    public ObjectRestResponse addVideoConfig(@RequestBody RealTimeVideo realTimeVideo) {
        try {
            Integer count=baseBiz.isDeviceExist(realTimeVideo.getPointId(),realTimeVideo.getAlarmType());
            if(count==0){
                realTimeVideo.setId(UUIDUtils.generateUuid());
                baseBiz.insertSelective2(realTimeVideo);
                return ObjectRestResponse.ok("新增成功");
            }else{
                return ObjectRestResponse.ok("该安检点已经存在此报警类型!");
            }
        } catch (Exception e) {
            return ObjectRestResponse.error(e.getMessage());
        }
    }
}
