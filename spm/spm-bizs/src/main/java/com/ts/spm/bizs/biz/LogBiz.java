package com.ts.spm.bizs.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.ts.spm.bizs.rpc.LogRest;
import com.ts.spm.bizs.vo.admin.api.LogInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Author luoyu
 * @Date 2020/11/28 16:33
 * @Version 1.0
 */
@Service
public class LogBiz {

    @Autowired
    LogRest logRest;

    @Autowired
    protected HttpServletRequest request;

    public void saveLog(String menu, String action, String uri) {
        LogInfo logInfo = new LogInfo();
        logInfo.setMenu(menu);
        logInfo.setOpt(action);
        logInfo.setUri(uri);
        logInfo.setCrtHost(request.getRemoteHost());
        logInfo.setCrtUser(BaseContextHandler.getUserID());
        logInfo.setCrtName(BaseContextHandler.getName());
        logInfo.setCrtTime(new Date());
        logInfo.setTenantId(BaseContextHandler.getTenantID());
        logRest.saveLog(logInfo);
    }
}
