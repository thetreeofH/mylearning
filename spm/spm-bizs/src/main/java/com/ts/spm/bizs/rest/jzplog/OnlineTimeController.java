package com.ts.spm.bizs.rest.jzplog;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.DateUtil;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.jzplog.TblOnlineTimeBiz;
import com.ts.spm.bizs.entity.jzplog.TblOnlineTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName OnlineTimeController
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/8/6 17:21
 * @Version 1.0
 **/
@RestController
@RequestMapping(value = "onlineTime")
@CheckClientToken
@CheckUserToken
public class OnlineTimeController extends BaseController<TblOnlineTimeBiz, TblOnlineTime,String> {

    @Autowired
    TblOnlineTimeBiz tblOnlineTimeBiz;

    /**
     * 新增用户登录时间
     * @param
     * @return
     */
    @PostMapping(value = "/loginAndLogout/add",produces = "application/json;charset=UTF-8")
    public ObjectRestResponse add(@RequestParam(defaultValue = "") String userId,@RequestParam(defaultValue = "userName") String userName){
        try {
            String id = UUIDUtils.generateUuid();
            TblOnlineTime tblOnlineTime = new TblOnlineTime();
            tblOnlineTime.setId(id);
            tblOnlineTime.setUserId(BaseContextHandler.getUserID());
            tblOnlineTime.setUserName(BaseContextHandler.getUsername());
            tblOnlineTime.setLoginTime(new Date());
            tblOnlineTime.setCreateTime(new Date());
            int result =  baseBiz.insertSelective2(tblOnlineTime);
            if(result == 1){
                return ObjectRestResponse.ok(id);
            }else {
                return ObjectRestResponse.error("保存失败");
            }

        }catch (Exception e){
            e.printStackTrace();
            return ObjectRestResponse.error(e.getMessage());
        }
    }

    /**
     * 更新用户登出时间
     * @param tblOnlineTime
     * @return
     */
    @PostMapping(value = "/loginAndLogout/update",produces = "application/json;charset=UTF-8")
    public ObjectRestResponse update(@RequestBody TblOnlineTime tblOnlineTime){
        try {
            tblOnlineTime.setLogoutTime(new Date());
            tblOnlineTime.setUpdateTime(new Date());
            int result =  baseBiz.updateSelectiveById2(tblOnlineTime);
            if(result == 1){
                return ObjectRestResponse.ok();
            }else {
                return ObjectRestResponse.error("更新失败");
            }

        }catch (Exception e){
            e.printStackTrace();
            return ObjectRestResponse.error(e.getMessage());
        }
    }
    /**
     * 统计用户在线时长
     * @return
     */
    @GetMapping(value = "/loginAndLogout/statistics")
    public ObjectRestResponse statistics(){
        try {
            TblOnlineTime tot = tblOnlineTimeBiz.selectMaxUpdateTime(BaseContextHandler.getUserID());
            if(tot != null){
                if(tot.getLogoutTime() == null){
                    tot.setLogoutTime(new Date());
                    tot.setUpdateTime(new Date());
                    int result =  baseBiz.updateSelectiveById2(tot);
                    if(result == 1){
                        List<Map<String,String>> map = tblOnlineTimeBiz.statistics(DateUtil.getCurrentDay()+" 00:00:00",DateUtil.date2Str(new Date()),BaseContextHandler.getUserID());
                        return ObjectRestResponse.ok(map);
                    }else {
                        return ObjectRestResponse.error("统计用户在线时长失败，请联系管理员");
                    }
                }else{
                    List<Map<String,String>> map = tblOnlineTimeBiz.statistics(DateUtil.getCurrentDay()+" 00:00:00",DateUtil.date2Str(new Date()),BaseContextHandler.getUserID());
                    return ObjectRestResponse.ok(map);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            return ObjectRestResponse.error(e.getMessage());
        }
        return null;
    }
}
