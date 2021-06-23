package com.ts.spm.bizs.entity.stat;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "TBC_AJY_WORK_INFO")
public class AjyWork {
    @Column(name = "ID")
    private Integer id;

    @Column(name = "AJY_IDNO")
    private String ajyIdno;

    @Column(name = "AJY_NAME")
    private String ajyName;

    @Column(name = "STIME")
    private Date stime;

    @Column(name = "ETIME")
    private Date etime;

    @Column(name = "POINT_ID")
    private String pointId;

    @Column(name = "TO_POINT")
    private BigDecimal toPoint;

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

    @Column(name = "CARD_FLAG")
    private Integer cardFlag;

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
     * @return AJY_IDNO
     */
    public String getAjyIdno() {
        return ajyIdno;
    }

    /**
     * @param ajyIdno
     */
    public void setAjyIdno(String ajyIdno) {
        this.ajyIdno = ajyIdno;
    }

    /**
     * @return AJY_NAME
     */
    public String getAjyName() {
        return ajyName;
    }

    /**
     * @param ajyName
     */
    public void setAjyName(String ajyName) {
        this.ajyName = ajyName;
    }

    /**
     * @return STIME
     */
    public Date getStime() {
        return stime;
    }

    /**
     * @param stime
     */
    public void setStime(Date stime) {
        this.stime = stime;
    }

    /**
     * @return ETIME
     */
    public Date getEtime() {
        return etime;
    }

    /**
     * @param etime
     */
    public void setEtime(Date etime) {
        this.etime = etime;
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
     * @return TO_POINT
     */
    public BigDecimal getToPoint() {
        return toPoint;
    }

    /**
     * @param toPoint
     */
    public void setToPoint(BigDecimal toPoint) {
        this.toPoint = toPoint;
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

    /**
     * @return CARD_FLAG
     */
    public Integer getCardFlag() {
        return cardFlag;
    }

    /**
     * @param cardFlag
     */
    public void setCardFlag(Integer cardFlag) {
        this.cardFlag = cardFlag;
    }
}