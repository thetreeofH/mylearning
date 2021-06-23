package com.ts.spm.bizs.entity.msg;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Table(name = "SPM_OPER.TBL_POINT_WARN_INFO")
public class PointWarn {
    @Id
    @Column(name = "WARN_ID")
    private String warnId;

    @Column(name = "SEND_USER")
    private String sendUser;

    @Column(name = "SEND_DEPT")
    private String sendDept;

    @Column(name = "SEND_TIME")
    private Date sendTime;

    @Column(name = "WARN_TIME")
    private Date warnTime;

    @Column(name = "WARN_PLACE")
    private String warnPlace;

    @Column(name = "WARN_RANGE")
    private String warnRange;

    @Column(name = "WARN_CONTENT")
    private String warnContent;

    @Column(name = "WARN_STEP")
    private String warnStep;

    @Column(name = "WARN_NEXT")
    private String warnNext;

    @Column(name = "STATION")
    private String station;

    @Column(name = "STATION_ID")
    private String stationId;

    @Column(name = "LINE_ID")
    private String lineId;

    @Column(name = "LINE")
    private String line;

    @Transient
    private List<PointWarnPush> pushes;

    /**
     * @return WARN_ID
     */
    public String getWarnId() {
        return warnId;
    }

    /**
     * @param warnId
     */
    public void setWarnId(String warnId) {
        this.warnId = warnId;
    }

    /**
     * @return SEND_USER
     */
    public String getSendUser() {
        return sendUser;
    }

    /**
     * @param sendUser
     */
    public void setSendUser(String sendUser) {
        this.sendUser = sendUser;
    }

    /**
     * @return SEND_DEPT
     */
    public String getSendDept() {
        return sendDept;
    }

    /**
     * @param sendDept
     */
    public void setSendDept(String sendDept) {
        this.sendDept = sendDept;
    }

    /**
     * @return SEND_TIME
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * @param sendTime
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * @return WARN_TIME
     */
    public Date getWarnTime() {
        return warnTime;
    }

    /**
     * @param warnTime
     */
    public void setWarnTime(Date warnTime) {
        this.warnTime = warnTime;
    }

    /**
     * @return WARN_PLACE
     */
    public String getWarnPlace() {
        return warnPlace;
    }

    /**
     * @param warnPlace
     */
    public void setWarnPlace(String warnPlace) {
        this.warnPlace = warnPlace;
    }

    /**
     * @return WARN_RANGE
     */
    public String getWarnRange() {
        return warnRange;
    }

    /**
     * @param warnRange
     */
    public void setWarnRange(String warnRange) {
        this.warnRange = warnRange;
    }

    /**
     * @return WARN_CONTENT
     */
    public String getWarnContent() {
        return warnContent;
    }

    /**
     * @param warnContent
     */
    public void setWarnContent(String warnContent) {
        this.warnContent = warnContent;
    }

    /**
     * @return WARN_STEP
     */
    public String getWarnStep() {
        return warnStep;
    }

    /**
     * @param warnStep
     */
    public void setWarnStep(String warnStep) {
        this.warnStep = warnStep;
    }

    /**
     * @return WARN_NEXT
     */
    public String getWarnNext() {
        return warnNext;
    }

    /**
     * @param warnNext
     */
    public void setWarnNext(String warnNext) {
        this.warnNext = warnNext;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
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

    public List<PointWarnPush> getPushes() {
        return pushes;
    }

    public void setPushes(List<PointWarnPush> pushes) {
        this.pushes = pushes;
    }
}