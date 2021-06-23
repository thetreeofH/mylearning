package com.ts.spm.bizs.vo.up;

/**
 * Created by zhoukun on 2021/4/30.
 */
public class OpenInspectionVo {
    //报警数据的eventId
    private String id;
    //处置结果枚举
    private String status;
    //身份证
    private String violatorCardId;
    //姓名
    private String violatorName;
    //性别
    private int violatorSex;
    //生日
    private String violatorBirthday;
    //身份证地址
    private String violatorNativePlace;
    //电话
    private String violatorTelNo;
    //类型
    private String[] contrabandTypes;
    //01[检人(01)or检物(02)
    private String violatorType;
    //处理备注
    private String alarmContent;
    //现场照片
    private String[] goodsUrls;
    //民族
    private String violatorNation;
    //智元汇-1；声讯-2；同方-3
    private String source;
    //标记0-新线(新线图片以base64上传)
    private String tag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getViolatorCardId() {
        return violatorCardId;
    }

    public void setViolatorCardId(String violatorCardId) {
        this.violatorCardId = violatorCardId;
    }

    public String getViolatorName() {
        return violatorName;
    }

    public void setViolatorName(String violatorName) {
        this.violatorName = violatorName;
    }

    public int getViolatorSex() {
        return violatorSex;
    }

    public void setViolatorSex(int violatorSex) {
        this.violatorSex = violatorSex;
    }

    public String getViolatorBirthday() {
        return violatorBirthday;
    }

    public void setViolatorBirthday(String violatorBirthday) {
        this.violatorBirthday = violatorBirthday;
    }

    public String getViolatorNativePlace() {
        return violatorNativePlace;
    }

    public void setViolatorNativePlace(String violatorNativePlace) {
        this.violatorNativePlace = violatorNativePlace;
    }

    public String getViolatorTelNo() {
        return violatorTelNo;
    }

    public void setViolatorTelNo(String violatorTelNo) {
        this.violatorTelNo = violatorTelNo;
    }

    public String[] getContrabandTypes() {
        return contrabandTypes;
    }

    public void setContrabandTypes(String[] contrabandTypes) {
        this.contrabandTypes = contrabandTypes;
    }

    public String getViolatorType() {
        return violatorType;
    }

    public void setViolatorType(String violatorType) {
        this.violatorType = violatorType;
    }

    public String getAlarmContent() {
        return alarmContent;
    }

    public void setAlarmContent(String alarmContent) {
        this.alarmContent = alarmContent;
    }

    public String[] getGoodsUrls() {
        return goodsUrls;
    }

    public void setGoodsUrls(String[] goodsUrls) {
        this.goodsUrls = goodsUrls;
    }

    public String getViolatorNation() {
        return violatorNation;
    }

    public void setViolatorNation(String violatorNation) {
        this.violatorNation = violatorNation;
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
