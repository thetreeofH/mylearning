package com.ts.spm.bizs.rest.jzplog;

import com.alibaba.fastjson.JSON;
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
import com.ts.spm.bizs.biz.jzplog.TblExecLogBiz;
import com.ts.spm.bizs.entity.jzplog.TblExecLog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import tk.mybatis.mapper.entity.Example;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ExecLogController
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/8/4 15:39
 * @Version 1.0
 **/
@RestController
@RequestMapping(value = "execLog")
@CheckClientToken
@CheckUserToken
public class ExecLogController extends BaseController<TblExecLogBiz, TblExecLog,String> {
    /**
     * 异常日志列表查询
     * @param limit
     * @param page
     * @param operUserName
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "/query")
    public TableResultResponse query(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "") String operUserName,@RequestParam(defaultValue = "") String startTime,@RequestParam(defaultValue = "") String endTime){
        Example example = new Example(TblExecLog.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNoneBlank(operUserName)){
            criteria.andEqualTo("operUserName",operUserName);
        }
        if(StringUtils.isNoneBlank(startTime)&&StringUtils.isNoneBlank(endTime)){
            criteria.andBetween("operCreateTime", DateUtil.stringToDate(startTime),DateUtil.stringToDate(endTime));
        }
        Page<Object> result = PageHelper.startPage(page, limit);
        List<TblExecLog> tblExecLogList = baseBiz.selectByExample(example);
        return new TableResultResponse<>(result.getTotal(),tblExecLogList);
    }

    /**
     * 异常日志详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}")
    public ObjectRestResponse get(@PathVariable String id){
        return ObjectRestResponse.ok(baseBiz.selectById(id));
    }

    /**
     * 新增异常日志
     * @param e
     * @param className
     * @param methodName
     * @return
     */
    @RequestMapping(value = "/save")
    public ObjectRestResponse save(Throwable e,String className,String methodName) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

        TblExecLog excepLog = new TblExecLog();
        try {
            excepLog.setExcId(UUIDUtils.generateUuid());
            // 获取请求的方法名
            methodName = className + "." + methodName;
            // 请求的参数
            Map<String, String> rtnMap = converMap(request.getParameterMap());
            // 将参数所在的数组转换成json
            String params = JSON.toJSONString(rtnMap);
            excepLog.setExcRequParam(params); // 请求参数
            excepLog.setOperMethod(methodName); // 请求方法名
            excepLog.setExcName(e.getClass().getName()); // 异常名称
            //excepLog.setExcMessage(stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace())); // 异常信息
            excepLog.setOperUserId(BaseContextHandler.getUserID()); // 操作员ID
            excepLog.setOperUserName(BaseContextHandler.getUsername()); // 操作员名称
            excepLog.setDepartId(BaseContextHandler.getDepartID());//部门id
            excepLog.setTenantId(BaseContextHandler.getTenantID());//区域id
            excepLog.setOperUri(request.getRequestURI()); // 操作URI
            excepLog.setOperIp(IpUtil.getIpAddr(request)); // 操作员IP
            excepLog.setOperCreateTime(new Date()); // 发生异常时间
            baseBiz.insertSelective(excepLog);
            return ObjectRestResponse.ok();
        } catch (Exception e2) {
            e2.printStackTrace();
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

    /**
     * 转换异常信息为字符串
     *
     * @param exceptionName    异常名称
     * @param exceptionMessage 异常信息
     * @param elements         堆栈信息
     */
    public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuffer strbuff = new StringBuffer();
        for (StackTraceElement stet : elements) {
            strbuff.append(stet + "\n");
        }
        String message = exceptionName + ":" + exceptionMessage + "\n\t" + strbuff.toString();
        return message;
    }
}
