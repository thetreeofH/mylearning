package com.ts.spm.bizs.entity.msg;

/**
 * Created by zhoukun on 2020/10/16.
 * 违禁品信息
 * updater 马居朝
 * 新增实体类picPath和它的方法
 */
public class MsgPointDanger {
    //报警时间
    private String AlarmTime;
    //安检点id
    private String pointId;
    //安检点名称
    private String pointName;
    //机构编号
    private String departId;
    //机构名称
    private String departName;
    //判图来源
    private Integer judgePictureSource;
    //安检员id
    private String handleUserId;
    //安检员姓名
    private String handleUserName;
    //处置结果【0放行（无违禁品）1:乘客放弃物品进站 2:转乘其他交通工具 3:报警】
    private Integer handleResult;
    //违禁品主类型
    private Integer suspectedForbiddenType;
    //违禁品主类型名称
    private String suspectedForbiddenTypeName;
    //违禁品子类型
    private Integer suspectedForbiddenSubtype;
    //违禁品子类型名称
    private String suspectedForbiddenSubtypeName;

    private String picPath;

    public String getSuspectedForbiddenTypeName() {
        return suspectedForbiddenTypeName;
    }

    public void setSuspectedForbiddenTypeName(String suspectedForbiddenTypeName) {
        this.suspectedForbiddenTypeName = suspectedForbiddenTypeName;
    }

    public String getSuspectedForbiddenSubtypeName() {
        return suspectedForbiddenSubtypeName;
    }

    public void setSuspectedForbiddenSubtypeName(String suspectedForbiddenSubtypeName) {
        this.suspectedForbiddenSubtypeName = suspectedForbiddenSubtypeName;
    }

    //乘客姓名
    private String passengerName;
    //乘客身份证号
    private String passengerIdCard;
    //违禁品X光机图片
    private String suspectedItemsPicture;
    //备注
    private String memo;
    //处置方式【0：人工处置，1：超时自动处置】
    private String handleMode;

    private Integer dangerId;


    private String videoInfo;

    private String alarmType;

    private String alarmInfo;//报警信息推送

    private String contrabandXian;//


    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getAlarmInfo() {
        return alarmInfo;
    }

    public void setAlarmInfo(String alarmInfo) {
        this.alarmInfo = alarmInfo;
    }

    public String getContrabandXian() {
        return contrabandXian;
    }

    public void setContrabandXian(String contrabandXian) {
        this.contrabandXian = contrabandXian;
    }

    public String getVideoInfo() {
        return videoInfo;
    }

    public void setVideoInfo(String videoInfo) {
        this.videoInfo = videoInfo;
    }

    public Integer getDangerId() {
        return dangerId;
    }

    public void setDangerId(Integer dangerId) {
        this.dangerId = dangerId;
    }

    public String getAlarmTime() {
        return AlarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        AlarmTime = alarmTime;
    }

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public Integer getJudgePictureSource() {
        return judgePictureSource;
    }

    public void setJudgePictureSource(Integer judgePictureSource) {
        this.judgePictureSource = judgePictureSource;
    }

    public String getHandleUserId() {
        return handleUserId;
    }

    public void setHandleUserId(String handleUserId) {
        this.handleUserId = handleUserId;
    }

    public String getHandleUserName() {
        return handleUserName;
    }

    public void setHandleUserName(String handleUserName) {
        this.handleUserName = handleUserName;
    }

    public Integer getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(Integer handleResult) {
        this.handleResult = handleResult;
    }

    public Integer getSuspectedForbiddenType() {
        return suspectedForbiddenType;
    }

    public void setSuspectedForbiddenType(Integer suspectedForbiddenType) {
        this.suspectedForbiddenType = suspectedForbiddenType;
    }

    public Integer getSuspectedForbiddenSubtype() {
        return suspectedForbiddenSubtype;
    }

    public void setSuspectedForbiddenSubtype(Integer suspectedForbiddenSubtype) {
        this.suspectedForbiddenSubtype = suspectedForbiddenSubtype;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerIdCard() {
        return passengerIdCard;
    }

    public void setPassengerIdCard(String passengerIdCard) {
        this.passengerIdCard = passengerIdCard;
    }

    public String getSuspectedItemsPicture() {
        return suspectedItemsPicture;
    }

    public void setSuspectedItemsPicture(String suspectedItemsPicture) {
        this.suspectedItemsPicture = suspectedItemsPicture;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getHandleMode() {
        return handleMode;
    }

    public void setHandleMode(String handleMode) {
        this.handleMode = handleMode;
    }
    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
}
