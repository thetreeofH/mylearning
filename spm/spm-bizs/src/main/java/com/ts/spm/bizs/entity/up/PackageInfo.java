package com.ts.spm.bizs.entity.up;

/**
 * Created by zhoukun on 2021/5/11.
 */
public class PackageInfo {
    //事件id(全局唯一)
    private String eventId;
    //是否告警(true|false)
    private boolean isAlarm;
    //线路编码
    private String lineCode;
    //线路名称
    private String lineName;
    //站点编码
    private String stationCode;
    //站点名称
    private String stationName;
    //安检点名称
    private String securityCheck;
    //安检点编码
    private String securityCheckCode;
    //设备编码
    private String deviceSerialNumber;
    //设备名称
    private String deviceName;
    //图片唯一标识
    private String imgUuid;
    //违禁品字典
    private String dictValues;
    //主视角图片地址
    private String imgMainUrl;
    //侧视角图片地址
    private String imgSideUrl;
    //前摄像头图片地址
    private String visiableImgFrontUrl;
    //后摄像头图片地址
    private String visiableImgBackUrl;
    //报警数量(包含违禁品个数)
    private int contrabandNum;
    //是否报警(0-非报警,1-报警)
    private int hascontraband;
    //抓取时间
    private long capTime;
    //智元汇-1；声讯-2；同方-3
    private String source;
    //标记0-新线(新线图片以base64上传)
    private String tag;

    public PackageAlarmDetail[] getDetail() {
        return detail;
    }

    public void setDetail(PackageAlarmDetail[] detail) {
        this.detail = detail;
    }

    //多个违禁品多个详情
    private PackageAlarmDetail[] detail;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public boolean getIsAlarm() {
        return isAlarm;
    }

    public void setIsAlarm(boolean alarm) {
        isAlarm = alarm;
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

    public String getSecurityCheck() {
        return securityCheck;
    }

    public void setSecurityCheck(String securityCheck) {
        this.securityCheck = securityCheck;
    }

    public String getSecurityCheckCode() {
        return securityCheckCode;
    }

    public void setSecurityCheckCode(String securityCheckCode) {
        this.securityCheckCode = securityCheckCode;
    }

    public String getDeviceSerialNumber() {
        return deviceSerialNumber;
    }

    public void setDeviceSerialNumber(String deviceSerialNumber) {
        this.deviceSerialNumber = deviceSerialNumber;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getImgUuid() {
        return imgUuid;
    }

    public void setImgUuid(String imgUuid) {
        this.imgUuid = imgUuid;
    }

    public String getDictValues() {
        return dictValues;
    }

    public void setDictValues(String dictValues) {
        this.dictValues = dictValues;
    }

    public String getImgMainUrl() {
        return imgMainUrl;
    }

    public void setImgMainUrl(String imgMainUrl) {
        this.imgMainUrl = imgMainUrl;
    }

    public String getImgSideUrl() {
        return imgSideUrl;
    }

    public void setImgSideUrl(String imgSideUrl) {
        this.imgSideUrl = imgSideUrl;
    }

    public String getVisiableImgFrontUrl() {
        return visiableImgFrontUrl;
    }

    public void setVisiableImgFrontUrl(String visiableImgFrontUrl) {
        this.visiableImgFrontUrl = visiableImgFrontUrl;
    }

    public String getVisiableImgBackUrl() {
        return visiableImgBackUrl;
    }

    public void setVisiableImgBackUrl(String visiableImgBackUrl) {
        this.visiableImgBackUrl = visiableImgBackUrl;
    }

    public int getContrabandNum() {
        return contrabandNum;
    }

    public void setContrabandNum(int contrabandNum) {
        this.contrabandNum = contrabandNum;
    }

    public int getHascontraband() {
        return hascontraband;
    }

    public void setHascontraband(int hascontraband) {
        this.hascontraband = hascontraband;
    }

    public long getCapTime() {
        return capTime;
    }

    public void setCapTime(long capTime) {
        this.capTime = capTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
