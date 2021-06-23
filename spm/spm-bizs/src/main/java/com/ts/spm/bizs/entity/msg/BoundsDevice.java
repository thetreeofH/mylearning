package com.ts.spm.bizs.entity.msg;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "SPM_OPER.TBL_BOUNDS_DEVICE")
public class BoundsDevice {
    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "IP")
    private String ip;

    @Column(name = "PORT")
    private String port;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "LAST_STATUS_TIME")
    private Date lastStatusTime;

    @Column(name = "LINE_ID")
    private String lineId;

    @Column(name = "LINE")
    private String line;

    @Column(name = "STATION_ID")
    private String stationId;

    @Column(name = "STATION")
    private String station;

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
     * @return IP
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return PORT
     */
    public String getPort() {
        return port;
    }

    /**
     * @param port
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * @return STATUS
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return LAST_STATUS_TIME
     */
    public Date getLastStatusTime() {
        return lastStatusTime;
    }

    /**
     * @param lastStatusTime
     */
    public void setLastStatusTime(Date lastStatusTime) {
        this.lastStatusTime = lastStatusTime;
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
     * @return LINE
     */
    public String getLine() {
        return line;
    }

    /**
     * @param line
     */
    public void setLine(String line) {
        this.line = line;
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
}