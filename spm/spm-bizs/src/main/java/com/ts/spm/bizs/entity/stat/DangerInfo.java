package com.ts.spm.bizs.entity.stat;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "TBL_SUBWAY_DANGERS_INFO")
public class DangerInfo {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "POINT_ID")
    private String pointId;

    @Column(name = "XOPT_NAME")
    private String xoptName;

    @Column(name = "FOUND_TIME")
    private Date foundTime;

    @Column(name = "CHECKER_NAME")
    private String checkerName;

    @Column(name = "CHECKER_RESULT")
    private Integer checkerResult;

    @Column(name = "DANGER_TYPE_ID")
    private String dangerTypeId;

    @Column(name = "DANGER_SUM")
    private Integer dangerSum;

    @Column(name = "DANGER_NOTE")
    private String dangerNote;

    @Column(name = "DEAL_RESULT")
    private Integer dealResult;

    @Column(name = "DEAL_NOTE")
    private String dealNote;

    @Column(name = "PASSANGER")
    private String passanger;

    @Column(name = "IDCARD")
    private String idcard;

    @Column(name = "DETENTIONDAY")
    private Integer detentionday;

    @Column(name = "REGISTER_ID")
    private String registerId;

    @Column(name = "DEAL_TYPE")
    private Long dealType;

    @Column(name = "XPIC_PATH")
    private String xpicPath;

    @Column(name = "PIC_PATH")
    private String picPath;

    @Column(name = "EVENT_ID")
    private String eventId;

    @Column(name = "IS_READ")
    private Integer isRead;

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
     * @return XOPT_NAME
     */
    public String getXoptName() {
        return xoptName;
    }

    /**
     * @param xoptName
     */
    public void setXoptName(String xoptName) {
        this.xoptName = xoptName;
    }

    /**
     * @return FOUND_TIME
     */
    public Date getFoundTime() {
        return foundTime;
    }

    /**
     * @param foundTime
     */
    public void setFoundTime(Date foundTime) {
        this.foundTime = foundTime;
    }

    /**
     * @return CHECKER_NAME
     */
    public String getCheckerName() {
        return checkerName;
    }

    /**
     * @param checkerName
     */
    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }

    /**
     * @return CHECKER_RESULT
     */
    public Integer getCheckerResult() {
        return checkerResult;
    }

    /**
     * @param checkerResult
     */
    public void setCheckerResult(Integer checkerResult) {
        this.checkerResult = checkerResult;
    }

    /**
     * @return DANGER_TYPE_ID
     */
    public String getDangerTypeId() {
        return dangerTypeId;
    }

    /**
     * @param dangerTypeId
     */
    public void setDangerTypeId(String dangerTypeId) {
        this.dangerTypeId = dangerTypeId;
    }

    /**
     * @return DANGER_SUM
     */
    public Integer getDangerSum() {
        return dangerSum;
    }

    /**
     * @param dangerSum
     */
    public void setDangerSum(Integer dangerSum) {
        this.dangerSum = dangerSum;
    }

    /**
     * @return DANGER_NOTE
     */
    public String getDangerNote() {
        return dangerNote;
    }

    /**
     * @param dangerNote
     */
    public void setDangerNote(String dangerNote) {
        this.dangerNote = dangerNote;
    }

    /**
     * @return DEAL_RESULT
     */
    public Integer getDealResult() {
        return dealResult;
    }

    /**
     * @param dealResult
     */
    public void setDealResult(Integer dealResult) {
        this.dealResult = dealResult;
    }

    /**
     * @return DEAL_NOTE
     */
    public String getDealNote() {
        return dealNote;
    }

    /**
     * @param dealNote
     */
    public void setDealNote(String dealNote) {
        this.dealNote = dealNote;
    }

    /**
     * @return PASSANGER
     */
    public String getPassanger() {
        return passanger;
    }

    /**
     * @param passanger
     */
    public void setPassanger(String passanger) {
        this.passanger = passanger;
    }

    /**
     * @return IDCARD
     */
    public String getIdcard() {
        return idcard;
    }

    /**
     * @param idcard
     */
    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    /**
     * @return DETENTIONDAY
     */
    public Integer getDetentionday() {
        return detentionday;
    }

    /**
     * @param detentionday
     */
    public void setDetentionday(Integer detentionday) {
        this.detentionday = detentionday;
    }

    /**
     * @return REGISTER_ID
     */
    public String getRegisterId() {
        return registerId;
    }

    /**
     * @param registerId
     */
    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    /**
     * @return DEAL_TYPE
     */
    public Long getDealType() {
        return dealType;
    }

    /**
     * @param dealType
     */
    public void setDealType(Long dealType) {
        this.dealType = dealType;
    }

    /**
     * @return XPIC_PATH
     */
    public String getXpicPath() {
        return xpicPath;
    }

    /**
     * @param xpicPath
     */
    public void setXpicPath(String xpicPath) {
        this.xpicPath = xpicPath;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    /**
     * @return EVENT_ID
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * @param eventId
     */
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    /**
     * @return IS_READ
     */
    public Integer getIsRead() {
        return isRead;
    }

    /**
     * @param isRead
     */
    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
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