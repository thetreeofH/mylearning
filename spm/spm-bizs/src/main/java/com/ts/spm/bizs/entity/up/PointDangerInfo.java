package com.ts.spm.bizs.entity.up;

/**
 * Created by zhoukun on 2021/4/19.
 * 违禁品、金属门报警信息
 */
public class PointDangerInfo {
    //事件id
    private String eventId;
    //一级报警类型
    private String alarmTypeRootCode;
    //二级报警类型
    private String alarmTypeParentCode;
    //三级报警类型
    private String alarmTypeCode;
    //报警等级
    private String alarmLevel;
    //一级报警名称
    private String alarmParentRootName;
    //二级报警名称
    private String alarmParentName;
    //三级报警名称
    private String alarmName;
    //报警时间
    private long alarmTime;
    //X光图像
    private String alarmImgUrl;
    //线路编码
    private String lineCode;
    //线路名称
    private String lineName;
    //站点编码
    private String stationCode;
    //站点名称
    private String stationName;
    //安检点编码
    private String securityCheckChannelId;
    //安检点名称
    private String securityCheckChannelName;
    //设备编码
    private String deviceId;
    //设备名称
    private String deviceName;
    //处置结果枚举
    private String status;
    //智元汇-1；声讯-2；同方-3
    private String source;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getAlarmTypeRootCode() {
        return alarmTypeRootCode;
    }

    public void setAlarmTypeRootCode(String alarmTypeRootCode) {
        this.alarmTypeRootCode = alarmTypeRootCode;
    }

    public String getAlarmTypeParentCode() {
        return alarmTypeParentCode;
    }

    public void setAlarmTypeParentCode(String alarmTypeParentCode) {
        this.alarmTypeParentCode = alarmTypeParentCode;
    }

    public String getAlarmTypeCode() {
        return alarmTypeCode;
    }

    public void setAlarmTypeCode(String alarmTypeCode) {
        this.alarmTypeCode = alarmTypeCode;
    }

    public String getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(String alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public String getAlarmParentRootName() {
        return alarmParentRootName;
    }

    public void setAlarmParentRootName(String alarmParentRootName) {
        this.alarmParentRootName = alarmParentRootName;
    }

    public String getAlarmParentName() {
        return alarmParentName;
    }

    public void setAlarmParentName(String alarmParentName) {
        this.alarmParentName = alarmParentName;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public long getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(long alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getAlarmImgUrl() {
        return alarmImgUrl;
    }

    public void setAlarmImgUrl(String alarmImgUrl) {
        this.alarmImgUrl = alarmImgUrl;
    }

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

    public String getSecurityCheckChannelId() {
        return securityCheckChannelId;
    }

    public void setSecurityCheckChannelId(String securityCheckChannelId) {
        this.securityCheckChannelId = securityCheckChannelId;
    }

    public String getSecurityCheckChannelName() {
        return securityCheckChannelName;
    }

    public void setSecurityCheckChannelName(String securityCheckChannelName) {
        this.securityCheckChannelName = securityCheckChannelName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}