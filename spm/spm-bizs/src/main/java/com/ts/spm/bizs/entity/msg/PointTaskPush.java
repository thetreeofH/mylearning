package com.ts.spm.bizs.entity.msg;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "SPM_OPER.TBL_POINT_TASK_PUSH_LIST")
public class PointTaskPush {
    @Column(name = "ID")
    @Id
    private String id;

    @Column(name = "TASK_ID")
    private String taskId;

    @Column(name = "POINT_ID")
    private String pointId;

    @Column(name = "IS_SEND")
    private Integer isSend;

    @Column(name = "IS_FINISH")
    private Integer isFinish;

    @Column(name = "FINISH_TIME")
    private Date finishTime;

    @Column(name = "FEEDBACK")
    private String feedback;

    @Column(name = "POINT")
    private String point;

    @Column(name = "STATION_ID")
    private String stationId;

    @Column(name = "STATION")
    private String station;

    @Column(name = "LINE_ID")
    private String lineId;

    @Column(name = "LINE")
    private String line;

    @Column(name = "FEEDPERSON")
    private String feedperson;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return TASK_ID
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * @param taskId
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
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
     * @return IS_SEND
     */
    public Integer getIsSend() {
        return isSend;
    }

    /**
     * @param isSend
     */
    public void setIsSend(Integer isSend) {
        this.isSend = isSend;
    }

    /**
     * @return IS_FINISH
     */
    public Integer getIsFinish() {
        return isFinish;
    }

    /**
     * @param isFinish
     */
    public void setIsFinish(Integer isFinish) {
        this.isFinish = isFinish;
    }

    /**
     * @return FINISH_TIME
     */
    public Date getFinishTime() {
        return finishTime;
    }

    /**
     * @param finishTime
     */
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    /**
     * @return FEEDBACK
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * @param feedback
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
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

    public String getFeedperson() {
        return feedperson;
    }

    public void setFeedperson(String feedperson) {
        this.feedperson = feedperson;
    }
}