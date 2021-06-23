package com.ts.spm.bizs.rest.jzplog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.ag.core.constants.OperLogConstants;
import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.DateUtil;
import com.github.wxiaoqi.security.common.util.IpUtil;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.jzplog.TblOperationLogBiz;
import com.ts.spm.bizs.entity.jzplog.TblOperationLog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import tk.mybatis.mapper.entity.Example;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName LogController
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/8/4 11:58
 * @Version 1.0
 **/
@RestController
@RequestMapping(value = "operLog")
@CheckUserToken
@CheckClientToken
public class OperLogController extends BaseController<TblOperationLogBiz, TblOperationLog,String> {

    /**
     * 操作日志日志列表查询
     * @param limit
     * @param page
     * @param operUserName
     * @param operModule
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public TableResultResponse query(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "") String operUserName,@RequestParam(defaultValue = "") String operModule,@RequestParam(defaultValue = "") String startTime,@RequestParam(defaultValue = "") String endTime){
        Example exa = new Example(TblOperationLog.class);
        Example.Criteria criteria = exa.createCriteria();
        if(StringUtils.isNoneBlank(operUserName)){
            criteria.andEqualTo("operUserName",operUserName);
        }
        if(StringUtils.isNoneBlank(operModule)){
            criteria.andEqualTo("operModule",operModule);
        }
        if(StringUtils.isNoneBlank(startTime)&&StringUtils.isNoneBlank(endTime)){
           criteria.andBetween("operCreateTime", DateUtil.stringToDate(startTime),DateUtil.stringToDate(endTime));
        }

        Page<Object> result = PageHelper.startPage(page, limit);
        List<TblOperationLog> tblOperationLogList = baseBiz.selectByExample(exa);
        return new TableResultResponse<>(result.getTotal(), tblOperationLogList);
    }

    /**
     * 操作日志详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
    public ObjectRestResponse get(@PathVariable String id){
        return ObjectRestResponse.ok(baseBiz.selectById(id));
    }

    /**
     * 新增操作日志
     * @param className
     * @param methodName
     * @param result
     * @param operModul
     * @param operDesc
     * @param operType
     * @return
     */
    @RequestMapping(value = "/save")
    public ObjectRestResponse save(String className,String methodName,String result,String operModul,String operDesc,String operType) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);
        TblOperationLog operlog = new TblOperationLog();
        try {
            operlog.setOperId(UUIDUtils.generateUuid()); // 主键ID
            operlog.setOperModul(operModul); // 操作模块
            operlog.setOperType(operType); // 操作类型
            operlog.setOperDesc(operDesc); // 操作描述
            // 获取请求的方法名
            methodName = className + "." + methodName;
            operlog.setOperMethod(methodName); // 请求方法
            // 请求的参数
            Map<String, String> rtnMap = converMap(request.getParameterMap());
            // 将参数所在的数组转换成json
            String params = JSONObject.toJSONString(rtnMap);
            operlog.setOperRequParam(params); // 请求参数
            operlog.setOperRespParam(JSON.toJSONString(result)); // 返回结果
            operlog.setOperUserId(BaseContextHandler.getUserID()); // 请求用户ID
            operlog.setOperUserName(BaseContextHandler.getUsername()); // 请求用户名称
            operlog.setDepartId(BaseContextHandler.getDepartID());//部门id
            operlog.setTenantId(BaseContextHandler.getTenantID());//区域id
            operlog.setOperIp(IpUtil.getIpAddr(request)); // 请求IP
            operlog.setOperUri(request.getRequestURI()); // 请求URI
            operlog.setOperCreateTime(new Date()); // 创建时间
            baseBiz.insertSelective(operlog);
            return ObjectRestResponse.ok();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    /**
     * 转换request 请求参数
     *
     * @param paramMap request获取的参数数组
     */
    public Map<String, String> converMap(Map<String, String[]> paramMap) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        for (String key : paramMap.keySet()) {
            rtnMap.put(key, paramMap.get(key)[0]);
        }
        return rtnMap;
    }

}
