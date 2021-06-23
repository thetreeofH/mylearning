package com.ts.spm.bizs.entity.stat;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "TBC_PACKAGE_COUNT")

public class PackageCount implements Serializable {
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TIME")
    private Date time;

    @Column(name = "COUNT")
    private Integer count;

    @Column(name = "POINT_ID")
    private String pointId;

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

    @Column(name = "LINE_ID")
    private String lineId;

    @Column(name = "AREA_ID")
    private String areaId;

    @Column(name = "STATION_ID")
    private String stationId;

    @Column(name = "BAG_YEAR")
    private Integer bagYear;

    @Column(name = "BAG_MONTH")
    private Integer bagMonth;

    @Column(name = "BAG_DAY")
    private Integer bagDay;

    @Column(name = "BAG_HOUR")
    private Integer bagHour;

    @Transient
    private String pointName;

    @Transient
    private String stationName;

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
     * @return COUNT
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count
     */
    public void setCount(Integer count) {
        this.count = count;
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
     * @return LINE_ID
     */
    public String getLineId() {
        return lineId;
    }

    /**
     * @param lineId
     */
    public void setLineId(String lineId) {
        this.lineId = lineId;
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
     * @return STATION_ID
     */
    public String getStationId() {
        return stationId;
    }

    /**
     * @param stationId
     */
    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    /**
     * @return BAG_YEAR
     */
    public Integer getBagYear() {
        return bagYear;
    }

    /**
     * @param bagYear
     */
    public void setBagYear(Integer bagYear) {
        this.bagYear = bagYear;
    }

    /**
     * @return BAG_MONTH
     */
    public Integer getBagMonth() {
        return bagMonth;
    }

    /**
     * @param bagMonth
     */
    public void setBagMonth(Integer bagMonth) {
        this.bagMonth = bagMonth;
    }

    /**
     * @return BAG_DAY
     */
    public Integer getBagDay() {
        return bagDay;
    }

    /**
     * @param bagDay
     */
    public void setBagDay(Integer bagDay) {
        this.bagDay = bagDay;
    }

    /**
     * @return BAG_HOUR
     */
    public Integer getBagHour() {
        return bagHour;
    }

    /**
     * @param bagHour
     */
    public void setBagHour(Integer bagHour) {
        this.bagHour = bagHour;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}