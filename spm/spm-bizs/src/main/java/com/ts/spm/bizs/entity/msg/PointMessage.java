package com.ts.spm.bizs.entity.msg;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "SPM_OPER.TBL_POINT_MESSAGE_INFO")
public class PointMessage {
    @Id
    @Column(name = "PM_ID")
    private String pmId;

    @Column(name = "PM_NAME")
    private String pmName;

    @Column(name = "POINT_ID")
    private String pointId;

    @Column(name = "PM_CONTENT")
    private String pmContent;

    @Column(name = "PM_DATE")
    private Date pmDate;

    @Column(name = "POINT_NAME")
    private String pointName;

    @Column(name = "STATION")
    private String station;

    @Column(name = "STATION_ID")
    private String stationId;

    /**
     * @return PM_ID
     */
    public String getPmId() {
        return pmId;
    }

    /**
     * @param pmId
     */
    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    /**
     * @return PM_NAME
     */
    public String getPmName() {
        return pmName;
    }

    /**
     * @param pmName
     */
    public void setPmName(String pmName) {
        this.pmName = pmName;
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
     * @return PM_CONTENT
     */
    public String getPmContent() {
        return pmContent;
    }

    /**
     * @param pmContent
     */
    public void setPmContent(String pmContent) {
        this.pmContent = pmContent;
    }

    /**
     * @return PM_DATE
     */
    public Date getPmDate() {
        return pmDate;
    }

    /**
     * @param pmDate
     */
    public void setPmDate(Date pmDate) {
        this.pmDate = pmDate;
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
     * @return STATION
     */
    public String getStation() {
        return station;
    }

    /**
     * @param station
     */
    public void setStation(String station) {
        this.station = station;
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
}