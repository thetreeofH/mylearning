package com.ts.spm.bizs.entity.msg;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Table(name = "SPM_OPER.TBL_BOUNDS_ALARM")
public class BoundsAlarm {
    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "DEVICE_ID")
    private Integer deviceId;

    @Column(name = "ALARM_STATUS")
    private Integer alarmStatus;

    @Column(name = "ALARM_TIME")
    private Date alarmTime;

    @Transient
    private String ip;
    @Transient
    private String port;
    @Transient
    private String lineId;
    @Transient
    private String line;

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
     * @return DEVICE_ID
     */
    public Integer getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId
     */
    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * @return ALARM_STATUS
     */
    public Integer getAlarmStatus() {
        return alarmStatus;
    }

    /**
     * @param alarmStatus
     */
    public void setAlarmStatus(Integer alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    /**
     * @return ALARM_TIME
     */
    public Date getAlarmTime() {
        return alarmTime;
    }

    /**
     * @param alarmTime
     */
    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}