package com.ts.spm.bizs.entity.person;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "TBC_TIRE_MONITOR")
public class TireMonitor {
    @Column(name = "ID")
    private BigDecimal id;

    @Column(name = "POINT_ID")
    private String pointId;

    @Column(name = "HIT_TYPE")
    private BigDecimal hitType;

    @Column(name = "HIT_PIC")
    private String hitPic;

    @Column(name = "HIT_TIME")
    private Date hitTime;

    @Transient
    private String pointName;

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
     * @return HIT_TYPE
     */
    public BigDecimal getHitType() {
        return hitType;
    }

    /**
     * @param hitType
     */
    public void setHitType(BigDecimal hitType) {
        this.hitType = hitType;
    }

    /**
     * @return HIT_PIC
     */
    public String getHitPic() {
        return hitPic;
    }

    /**
     * @param hitPic
     */
    public void setHitPic(String hitPic) {
        this.hitPic = hitPic;
    }

    /**
     * @return HIT_TIME
     */
    public Date getHitTime() {
        return hitTime;
    }

    /**
     * @param hitTime
     */
    public void setHitTime(Date hitTime) {
        this.hitTime = hitTime;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }
}