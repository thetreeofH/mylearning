package com.ts.spm.bizs.entity.person;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "TBC_AJY_WORK_INFO")
public class AjyWorkInfo {
    @Column(name = "ID")
    private BigDecimal id;

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

    /**
     * @return ID
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(BigDecimal id) {
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
}