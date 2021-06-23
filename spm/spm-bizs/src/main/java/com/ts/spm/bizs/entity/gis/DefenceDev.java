package com.ts.spm.bizs.entity.gis;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "SPM_SYS.TBL_DEFENCE_DEV")
public class DefenceDev {
    @Id
    private String id;

    @Column(name = "AREA_ID")
    private String areaId;

    @Column(name = "DEV_ID")
    private String devId;

    @Transient
    private String devName;

    @Column(name = "DEPART_ID")
    private String departId;

    @Column(name = "X")
    private BigDecimal x;

    @Column(name = "Y")
    private BigDecimal y;

    @Column(name = "RADIUS")
    private BigDecimal radius;

    @Column(name = "ANGLE")
    private BigDecimal angle;

    @Column(name = "SCOPE")
    private BigDecimal scope;

    @Transient
    private String type;

    @Column(name = "MEMO")
    private String memo;

    @Column(name = "CRT_TIME")
    private Date crtTime;

    @Column(name = "UPD_TIME")
    private Date updTime;

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
     * @return AREA_ID
     */
    public String getAreaId() {
        return areaId;
    }

    /**
     * @param areaId
     */
    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    /**
     * @return DEV_ID
     */
    public String getDevId() {
        return devId;
    }

    /**
     * @param devId
     */
    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    /**
     * @return DEPART_ID
     */
    public String getDepartId() {
        return departId;
    }

    /**
     * @param departId
     */
    public void setDepartId(String departId) {
        this.departId = departId;
    }

    /**
     * @return X
     */
    public BigDecimal getX() {
        return x;
    }

    /**
     * @param x
     */
    public void setX(BigDecimal x) {
        this.x = x;
    }

    /**
     * @return Y
     */
    public BigDecimal getY() {
        return y;
    }

    /**
     * @param y
     */
    public void setY(BigDecimal y) {
        this.y = y;
    }

    /**
     * @return RADIUS
     */
    public BigDecimal getRadius() {
        return radius;
    }

    /**
     * @param radius
     */
    public void setRadius(BigDecimal radius) {
        this.radius = radius;
    }

    /**
     * @return ANGLE
     */
    public BigDecimal getAngle() {
        return angle;
    }

    /**
     * @param angle
     */
    public void setAngle(BigDecimal angle) {
        this.angle = angle;
    }

    /**
     * @return SCOPE
     */
    public BigDecimal getScope() {
        return scope;
    }

    /**
     * @param scope
     */
    public void setScope(BigDecimal scope) {
        this.scope = scope;
    }

    /**
     * @return TYPE
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return MEMO
     */
    public String getMemo() {
        return memo;
    }

    /**
     * @param memo
     */
    public void setMemo(String memo) {
        this.memo = memo;
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
}