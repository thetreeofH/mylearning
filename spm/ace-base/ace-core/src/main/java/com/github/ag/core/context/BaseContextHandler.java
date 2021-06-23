package com.github.ag.core.context;


import com.github.ag.core.constants.CommonConstants;
import com.github.ag.core.util.StringHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * 后台当前上下文
 *
 * @author ace
 * @version 2017/9/8
 */
public class BaseContextHandler {
    public static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    public static Object get(String key) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        return map.get(key);
    }

    public static String getDepartID(){
        Object value = get(CommonConstants.CONTEXT_KEY_DEPART_ID);
        return returnObjectValue(value);
    }

    public static void setDepartID(String departID){
        set(CommonConstants.CONTEXT_KEY_DEPART_ID, departID);
    }

    public static String getTenantID(){
        Object value = get(CommonConstants.CONTEXT_KEY_TENANT_ID);
        return returnObjectValue(value);
    }

    public static void setTenantID(String tenantID){
        set(CommonConstants.CONTEXT_KEY_TENANT_ID, tenantID);
    }

    public static String getUserID() {
        Object value = get(CommonConstants.CONTEXT_KEY_USER_ID);
        return returnObjectValue(value);
    }

    public static String getUsername() {
        Object value = get(CommonConstants.CONTEXT_KEY_USERNAME);
        return returnObjectValue(value);
    }


    public static String getName() {
        Object value = get(CommonConstants.CONTEXT_KEY_USER_NAME);
        return StringHelper.getObjectValue(value);
    }

    public static String getToken() {
        Object value = get(CommonConstants.CONTEXT_KEY_USER_TOKEN);
        return StringHelper.getObjectValue(value);
    }

    public static void setToken(String token) {
        set(CommonConstants.CONTEXT_KEY_USER_TOKEN, token);
    }

    public static void setName(String name) {
        set(CommonConstants.CONTEXT_KEY_USER_NAME, name);
    }

    public static void setUserID(String userID) {
        set(CommonConstants.CONTEXT_KEY_USER_ID, userID);
    }

    public static void setUsername(String username) {
        set(CommonConstants.CONTEXT_KEY_USERNAME, username);
    }

    private static String returnObjectValue(Object value) {
        return value == null ? null : value.toString();
    }

    public static void remove() {
        threadLocal.remove();
    }

    public static String getIsSuperAdmin(){
        Object value = get(CommonConstants.JWT_KEY_IS_SUPER_ADMIN);
        if(value == null)
            return "0";
        return returnObjectValue(value);
    }

    public static void setIsSuperAdmin(String isSuperAdmin){
        set(CommonConstants.JWT_KEY_IS_SUPER_ADMIN, isSuperAdmin);
    }
}
