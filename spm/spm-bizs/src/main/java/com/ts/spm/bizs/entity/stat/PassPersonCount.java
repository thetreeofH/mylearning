package com.ts.spm.bizs.entity.stat;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "TBC_PASS_PERSON_COUNT")
public class PassPersonCount implements Serializable {
    @Column(name = "ID")
    private Integer id;

    @Column(name = "POINT_ID")
    private String pointId;

    @Column(name = "INC_COUNT")
    private Integer incCount;

    @Column(name="INC_FCOUNT")
    private Integer incFcount;

    @Column(name="INC_BCOUNT")
    private Integer incBcount;

    @Column(name = "INC_ACOUNT")
    private Integer incAcount;

    @Column(name = "COUNT_DATE")
    private Date countDate;

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

    @Column(name = "STATION_ID")
    private String stationId;

    @Column(name = "LINE_ID")
    private String lineId;

    @Column(name = "AREA_ID")
    private String areaId;

    @Column(name = "PASS_YEAR")
    private Integer passYear;

    @Column(name = "PASS_MONTH")
    private Integer passMonth;

    @Column(name = "PASS_DAY")
    private Integer passDay;

    @Column(name = "PASS_HOUR")
    private Integer passHour;

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
     * @return INC_COUNT
     */
    public Integer getIncCount() {
        return incCount;
    }

    /**
     * @param incCount
     */
    public void setIncCount(Integer incCount) {
        this.incCount = incCount;
    }

    /**
     * @return INC_ACOUNT
     */
    public Integer getIncAcount() {
        return incAcount;
    }


    /**
     *
     * @return INC_FCOUNT
     */
    public Integer getIncFcount() {
        return incFcount;
    }

    /**
     *
     * @param incFcount
     */
    public void setIncFcount(Integer incFcount) {
        this.incFcount = incFcount;
    }

    /**
     *
     * @return INC_BCOUNT
     */
    public Integer getIncBcount() {
        return incBcount;
    }

    /**
     *
     * @param incBcount
     */
    public void setIncBcount(Integer incBcount) {
        this.incBcount = incBcount;
    }

    /**
     * @param incAcount
     */
    public void setIncAcount(Integer incAcount) {
        this.incAcount = incAcount;
    }

    /**
     * @return COUNT_DATE
     */
    public Date getCountDate() {
        return countDate;
    }

    /**
     * @param countDate
     */
    public void setCountDate(Date countDate) {
        this.countDate = countDate;
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
     * @return PASS_YEAR
     */
    public Integer getPassYear() {
        return passYear;
    }

    /**
     * @param passYear
     */
    public void setPassYear(Integer passYear) {
        this.passYear = passYear;
    }

    /**
     * @return PASS_MONTH
     */
    public Integer getPassMonth() {
        return passMonth;
    }

    /**
     * @param passMonth
     */
    public void setPassMonth(Integer passMonth) {
        this.passMonth = passMonth;
    }

    /**
     * @return PASS_DAY
     */
    public Integer getPassDay() {
        return passDay;
    }

    /**
     * @param passDay
     */
    public void setPassDay(Integer passDay) {
        this.passDay = passDay;
    }

    /**
     * @return PASS_HOUR
     */
    public Integer getPassHour() {
        return passHour;
    }

    /**
     * @param passHour
     */
    public void setPassHour(Integer passHour) {
        this.passHour = passHour;
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