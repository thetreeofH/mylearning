package com.ts.spm.bizs.entity.jzpitgcfg;

import java.util.Date;
import javax.persistence.*;

@Table(name = "jzp_oper.TBL_TIME_PERIOD_CFG")
public class TblTimePeriodCfg {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "POINT_ID")
    private String pointId;

    @Column(name = "START_TIME")
    private String startTime;

    @Column(name = "END_TIME")
    private String endTime;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @Column(name = "POINT_NAME")
    private String pointName;

    @Column(name = "STATION_ID")
    private String stationId;

    @Column(name = "STATION_NAME")
    private String stationName;

    @Column(name = "INTELLIGENT_ID")
    private String intelligentId;

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
     * @return START_TIME
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return END_TIME
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @param endTime
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * @return CREATE_TIME
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return UPDATE_TIME
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return POINT_NAME
     */
    public String getPointName() {
        return pointName;
    }

    /**
     * @param pointName
     */
    public void setPointName(String pointName) {
        this.pointName = pointName;
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
     * @return STATION_NAME
     */
    public String getStationName() {
        return stationName;
    }

    /**
     * @param stationName
     */
    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    /**
     * @return INTELLIGENT_ID
     */
    public String getIntelligentId() {
        return intelligentId;
    }

    /**
     * @param intelligentId
     */
    public void setIntelligentId(String intelligentId) {
        this.intelligentId = intelligentId;
    }
}