package com.ts.spm.bizs.entity.matter;

import java.util.Date;
import javax.persistence.*;

@Table(name = "SPM_OPER.TBL_SECURITY_LEVEL_PERSON")
public class SecurityLevelPerson {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "LEVEL_ID")
    private String levelId;

    @Column(name = "GATE_SUM")
    private Integer gateSum;

    @Column(name = "HIGH_PERSON_COUNT")
    private Integer highPersonCount;

    @Column(name = "COMMON_PERSON_COUNT")
    private Integer commonPersonCount;

    @Column(name = "LOWER_PERSON_COUNT")
    private Integer lowerPersonCount;

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
     * @return LEVEL_ID
     */
    public String getLevelId() {
        return levelId;
    }

    /**
     * @param levelId
     */
    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    /**
     * @return GATE_SUM
     */
    public Integer getGateSum() {
        return gateSum;
    }

    /**
     * @param gateSum
     */
    public void setGateSum(Integer gateSum) {
        this.gateSum = gateSum;
    }

    /**
     * @return HIGH_PERSON_COUNT
     */
    public Integer getHighPersonCount() {
        return highPersonCount;
    }

    /**
     * @param highPersonCount
     */
    public void setHighPersonCount(Integer highPersonCount) {
        this.highPersonCount = highPersonCount;
    }

    /**
     * @return COMMON_PERSON_COUNT
     */
    public Integer getCommonPersonCount() {
        return commonPersonCount;
    }

    /**
     * @param commonPersonCount
     */
    public void setCommonPersonCount(Integer commonPersonCount) {
        this.commonPersonCount = commonPersonCount;
    }

    /**
     * @return LOWER_PERSON_COUNT
     */
    public Integer getLowerPersonCount() {
        return lowerPersonCount;
    }

    /**
     * @param lowerPersonCount
     */
    public void setLowerPersonCount(Integer lowerPersonCount) {
        this.lowerPersonCount = lowerPersonCount;
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

}