package com.ts.spm.bizs.rest.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.admin.BaseAppBindBiz;
import com.ts.spm.bizs.biz.admin.UserBiz;
import com.ts.spm.bizs.entity.admin.AppBind;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author ace
 */
@Slf4j
@RestController
@RequestMapping("app")
@CheckClientToken
@CheckUserToken
public class AppBindController extends BaseController<BaseAppBindBiz, AppBind, String> {

    private UserBiz userBiz;
    @Autowired
    private BaseAppBindBiz bindBiz;

    @Autowired
    public void setUserBiz(UserBiz userBiz) {
        this.userBiz = userBiz;
    }

    @RequestMapping(value = "/binding/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse delDictValue(@PathVariable String id) {
        String[] ids=id.split(",");
        for(String str:ids)
            baseBiz.deleteById(str);
        return ObjectRestResponse.ok();
    }

    @RequestMapping(value = "/binding/page", method = RequestMethod.GET)
    public TableResultResponse page(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "") String name){
        Page<Object> result = PageHelper.startPage(page, limit);
        List<AppBind> list = bindBiz.getpage(name);
        return  new TableResultResponse<>(result.getTotal(),list);
    }

    @IgnoreUserToken
    @RequestMapping(value = "/binding/byuser", method = RequestMethod.GET)
    public ObjectRestResponse getAppBindingInfoByUserId(@RequestParam("userId") String userId ){
        AppBind appBind  = null;
        Example example = new Example(AppBind.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        List<AppBind> appBinds = bindBiz.selectByExample(example);
        if(appBinds!=null && appBinds.size()>0){
            appBind = appBinds.get(0);
        }
        return ObjectRestResponse.ok(appBind);
    }


    @IgnoreUserToken
    @RequestMapping(value = "/binding/add", method = RequestMethod.POST)
    public ObjectRestResponse appBindingAdd(@RequestParam("deviceId") String deviceId,@RequestParam("userId") String userId){
        if(  StringUtils.isEmpty(deviceId)  ||  StringUtils.isEmpty(userId)){
            log.warn("参数有误！");
            return ObjectRestResponse.ok(Boolean.valueOf(false));
        }
        Example example = new Example(AppBind.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        List<AppBind> appBinds = bindBiz.selectByExample(example);
        if(appBinds!=null && appBinds.size()>0){
            log.warn("用户已存在绑定设备，添加失败！");
            return ObjectRestResponse.ok(Boolean.valueOf(false));
        }
        AppBind bind = new AppBind();
        bind.setUserId(userId);
        bind.setDeviceId(deviceId);
        bind.setId(UUIDUtils.generateUuid());
        if(bindBiz.insertSelective2(bind)>0){
            log.debug("用户设备绑定成功！");
            return ObjectRestResponse.ok(Boolean.valueOf(true));
        }else{
            log.warn("用户设备绑定失败！");
            return ObjectRestResponse.ok(Boolean.valueOf(false));
        }

    }

}
