package com.ts.spm.bizs.entity.admin;

import javax.persistence.*;

@Table(name = "SPM_SYS.TBL_APP_BIND")
public class AppBind {
    @Id
    @GeneratedValue(generator = "UUID")
    private String id;

    @Column(name = "DEVICE_ID")
    private String deviceId;

    @Column(name = "USER_ID")
    private String userId;

    @Transient
    private String username;

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
     * @return DEVICE_ID
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId
     */

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * @return USER_ID
     */
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}