package com.ts.spm.bizs.entity.up;

import java.util.Date;

/**
 * Created by zhoukun on 2021/4/16.
 * 客流统计
 */
public class PassPerson {
    //线路编码
    private String lineCode;
    //线路名称
    private String lineName;
    //站点编码
    private String stationCode;
    //线路名称
    private String stationName;
    //安检点编码
    private String checkPositionCode;
    //安检点名称
    private String checkPositionName;
    //当前总人数
    private int currentTotalCount;
    //增量
    private int increaseCount;
    //最新时间
    private Date lastTime;
    //智元汇-1；声讯-2；同方-3
    private String source;

    public String getLineCode() {
        return lineCode;
    }

    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getCheckPositionCode() {
        return checkPositionCode;
    }

    public void setCheckPositionCode(String checkPositionCode) {
        this.checkPositionCode = checkPositionCode;
    }

    public String getCheckPositionName() {
        return checkPositionName;
    }

    public void setCheckPositionName(String checkPositionName) {
        this.checkPositionName = checkPositionName;
    }

    public int getCurrentTotalCount() {
        return currentTotalCount;
    }

    public void setCurrentTotalCount(int currentTotalCount) {
        this.currentTotalCount = currentTotalCount;
    }

    public int getIncreaseCount() {
        return increaseCount;
    }

    public void setIncreaseCount(int increaseCount) {
        this.increaseCount = increaseCount;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
