package com.ts.spm.bizs.entity.msg;


/**
 * @ClassName AppPushUserMap
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/11/6 15:49
 * @Version 1.0
 **/
public class AppPushUserMap {



    private String id;


    private String pushCode;


    private String userId;


    private String appType;

    /**
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return PUSH_CODE
     */
    public String getPushCode() {
        return pushCode;
    }

    /**
     * @param pushCode
     */
    public void setPushCode(String pushCode) {
        this.pushCode = pushCode;
    }

    /**
     * @return USER_ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return APP_TYPE
     */
    public String getAppType() {
        return appType;
    }

    /**
     * @param appType
     */
    public void setAppType(String appType) {
        this.appType = appType;
    }

}
