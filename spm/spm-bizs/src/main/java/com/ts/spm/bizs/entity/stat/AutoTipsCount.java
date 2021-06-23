package com.ts.spm.bizs.entity.stat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "TBL_AUTO_TIPS_COUNT")
public class AutoTipsCount {
    @Column(name = "ID")
    private Integer id;

    @Column(name = "POINT_ID")
    private String pointId;

    @Column(name = "INSERT_COUNT")
    private Integer insertCount;

    @Column(name = "DETECT_COUNT")
    private Integer detectCount;

    @Column(name = "TIME")
    private Date time;

    @Column(name = "CRT_TIME")
    private Date crtTime;

    @Column(name = "CRT_USER_ID")
    private String crtUserId;

    @Column(name = "CRT_USER_NAME")
    private String crtUserName;

    @Column(name = "UPD_TIME")
    private Date updTime;

    @Column(name = "UPD_USER_ID")
    private String updUserId;

    @Column(name = "UPD_USER_NAME")
    private String updUserName;

    @Column(name = "TENANT_ID")
    private String tenantId;

    /**
     * @return ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return POINT_ID
     */
    public String getPointId() {
        return pointId;
    }

    /**
     * @param pointId
     */
    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    /**
     * @return INSERT_COUNT
     */
    public Integer getInsertCount() {
        return insertCount;
    }

    /**
     * @param insertCount
     */
    public void setInsertCount(Integer insertCount) {
        this.insertCount = insertCount;
    }

    /**
     * @return DETECT_COUNT
     */
    public Integer getDetectCount() {
        return detectCount;
    }

    /**
     * @param detectCount
     */
    public void setDetectCount(Integer detectCount) {
        this.detectCount = detectCount;
    }

    /**
     * @return TIME
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * @return CRT_TIME
     */
    public Date getCrtTime() {
        return crtTime;
    }

    /**
     * @param crtTime
     */
    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    /**
     * @return CRT_USER_ID
     */
    public String getCrtUserId() {
        return crtUserId;
    }

    /**
     * @param crtUserId
     */
    public void setCrtUserId(String crtUserId) {
        this.crtUserId = crtUserId;
    }

    /**
     * @return CRT_USER_NAME
     */
    public String getCrtUserName() {
        return crtUserName;
    }

    /**
     * @param crtUserName
     */
    public void setCrtUserName(String crtUserName) {
        this.crtUserName = crtUserName;
    }

    /**
     * @return UPD_TIME
     */
    public Date getUpdTime() {
        return updTime;
    }

    /**
     * @param updTime
     */
    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }

    /**
     * @return UPD_USER_ID
     */
    public String getUpdUserId() {
        return updUserId;
    }

    /**
     * @param updUserId
     */
    public void setUpdUserId(String updUserId) {
        this.updUserId = updUserId;
    }

    /**
     * @return UPD_USER_NAME
     */
    public String getUpdUserName() {
        return updUserName;
    }

    /**
     * @param updUserName
     */
    public void setUpdUserName(String updUserName) {
        this.updUserName = updUserName;
    }

    /**
     * @return TENANT_ID
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * @param tenantId
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}