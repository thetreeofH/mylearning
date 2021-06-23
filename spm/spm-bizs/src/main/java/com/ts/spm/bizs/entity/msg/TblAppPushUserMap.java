package com.ts.spm.bizs.entity.msg;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "SPM_SYS.TBL_APP_PUSH_USER_MAP")
public class TblAppPushUserMap {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "PUSH_CODE")
    private String pushCode;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "APP_TYPE")
    private String appType;

    @Column(name = "DEV_CODE")
    private String devCode;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    public String getDevCode() {
        return devCode;
    }

    public void setDevCode(String devCode) {
        this.devCode = devCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

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