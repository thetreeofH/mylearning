package com.ts.spm.bizs.rest.jzpmq;

import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ts.spm.bizs.biz.jzpmq.PictureFigureTaskInfoBiz;
import com.ts.spm.bizs.entity.jzpmq.PictureFigureTaskInfo;
import com.ts.spm.bizs.rest.admin.CheckPointController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName NewMissionController
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/9/23 20:37
 * @Version 1.0
 **/
@RestController
@RequestMapping("newMission")
@IgnoreClientToken
@CheckUserToken
public class NewMissionController extends BaseController<PictureFigureTaskInfoBiz, PictureFigureTaskInfo,String> {
    @Autowired
    PictureFigureTaskInfoBiz pictureFigureTaskInfoBiz;
    @Autowired
    CheckPointController checkPointCtr;

    /**
     * 新判图任务列表
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
                                     @RequestParam(value = "startTime", required = false) String startTime,
                                     @RequestParam(value = "endTime", required = false) String endTime,
                                     @RequestParam(defaultValue = "") String departId,
                                     @RequestParam(defaultValue = "") String pointId){
        if(StringUtils.isNoneBlank(startTime)){
            startTime = startTime + " 00:00:00";
        }
        if(StringUtils.isNoneBlank(endTime)){
            endTime = endTime + " 23:59:59";
        }


        List<Map<String, Object>> points=checkPointCtr.getpoint(departId, BaseContextHandler.getUserID());
        if(CollectionUtils.isEmpty(points)) {
            return new TableResultResponse<>(0, points);
        }
        List<String>  pointIds = points.stream().map(u->u.get("id").toString()).collect(Collectors.toList());

        Map<String,Object> map=new HashMap<>();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("pointIds",pointIds);
        map.put("pointId",pointId);
        map.put("page",(page-1)*limit);
        map.put("pagesize",limit);
        Integer count=pictureFigureTaskInfoBiz.selectTotalCount(map);
        List<Map<String,String>> pictureFigureTaskInfoList = pictureFigureTaskInfoBiz.query(map);
        return new TableResultResponse(count,pictureFigureTaskInfoList);
    }
}
