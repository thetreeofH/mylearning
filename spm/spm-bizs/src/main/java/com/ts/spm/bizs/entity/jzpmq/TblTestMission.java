package com.ts.spm.bizs.entity.jzpmq;

import java.util.Date;
import javax.persistence.*;

@Table(name = "jzp_oper.TBL_TEST_MISSION")
public class TblTestMission {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "MISSION_ID")
    private String missionId;

    @Column(name = "POINT_ID")
    private String pointId;

    @Column(name = "X_RAY_MACHINE_PICTURES")
    private String xRayMachinePictures;

    @Column(name = "NATURAL_LIGHT_PICTURES")
    private String naturalLightPictures;

    @Column(name = "JUDGE_PICTURE_USER_ID")
    private String judgePictureUserId;

    @Column(name = "JUDGE_PICTURE_USER_NAME")
    private String judgePictureUserName;

    @Column(name = "TEST_MISSION_NAME")
    private String testMissionName;

    /**
     * 是否识别，0未识别，1已识别
     */
    @Column(name = "IF_DISTINGUISH")
    private Integer ifDistinguish;

    @Column(name = "ATTR1")
    private String attr1;

    @Column(name = "ATTR2")
    private String attr2;

    @Column(name = "ATTR3")
    private String attr3;

    @Column(name = "ATTR4")
    private String attr4;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /**
     * 备注
     */
    @Column(name = "MEMO")
    private String memo;

    /**
     * 是否发送，0未发送，1已发送
     */
    @Column(name = "IF_SEND")
    private Integer ifSend;

    /**
     * 疑似违禁品主类型
     */
    @Column(name = "SUSPECTED_FORBIDDEN_TYPE")
    private Integer suspectedForbiddenType;

    /**
     * 疑似违禁品子类型
     */
    @Column(name = "SUSPECTED_FORBIDDEN_SUBTYPE")
    private Integer suspectedForbiddenSubtype;

    @Column(name = "CAPTURE_PICTURE_URL")
    private String capturePictureUrl;

    @Column(name = "CAPTURE_DESCRIBE")
    private String captureDescribe;

    @Column(name = "CAPTURE_TIME")
    private Date captureTime;

    /**
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return MISSION_ID
     */
    public String getMissionId() {
        return missionId;
    }

    /**
     * @param missionId
     */
    public void setMissionId(String missionId) {
        this.missionId = missionId;
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
     * @return X_RAY_MACHINE_PICTURES
     */
    public String getxRayMachinePictures() {
        return xRayMachinePictures;
    }

    /**
     * @param xRayMachinePictures
     */
    public void setxRayMachinePictures(String xRayMachinePictures) {
        this.xRayMachinePictures = xRayMachinePictures;
    }

    /**
     * @return NATURAL_LIGHT_PICTURES
     */
    public String getNaturalLightPictures() {
        return naturalLightPictures;
    }

    /**
     * @param naturalLightPictures
     */
    public void setNaturalLightPictures(String naturalLightPictures) {
        this.naturalLightPictures = naturalLightPictures;
    }

    /**
     * @return JUDGE_PICTURE_USER_ID
     */
    public String getJudgePictureUserId() {
        return judgePictureUserId;
    }

    /**
     * @param judgePictureUserId
     */
    public void setJudgePictureUserId(String judgePictureUserId) {
        this.judgePictureUserId = judgePictureUserId;
    }

    /**
     * @return JUDGE_PICTURE_USER_NAME
     */
    public String getJudgePictureUserName() {
        return judgePictureUserName;
    }

    /**
     * @param judgePictureUserName
     */
    public void setJudgePictureUserName(String judgePictureUserName) {
        this.judgePictureUserName = judgePictureUserName;
    }

    /**
     * @return TEST_MISSION_NAME
     */
    public String getTestMissionName() {
        return testMissionName;
    }

    /**
     * @param testMissionName
     */
    public void setTestMissionName(String testMissionName) {
        this.testMissionName = testMissionName;
    }

    /**
     * @return IF_DISTINGUISH
     */
    public Integer getIfDistinguish() {
        return ifDistinguish;
    }

    /**
     * @param ifDistinguish
     */
    public void setIfDistinguish(Integer ifDistinguish) {
        this.ifDistinguish = ifDistinguish;
    }

    /**
     * @return ATTR1
     */
    public String getAttr1() {
        return attr1;
    }

    /**
     * @param attr1
     */
    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    /**
     * @return ATTR2
     */
    public String getAttr2() {
        return attr2;
    }

    /**
     * @param attr2
     */
    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    /**
     * @return ATTR3
     */
    public String getAttr3() {
        return attr3;
    }

    /**
     * @param attr3
     */
    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }

    /**
     * @return ATTR4
     */
    public String getAttr4() {
        return attr4;
    }

    /**
     * @param attr4
     */
    public void setAttr4(String attr4) {
        this.attr4 = attr4;
    }

    /**
     * @return CREATE_TIME
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return UPDATE_TIME
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return MEMO
     */
    public String getMemo() {
        return memo;
    }

    /**
     * @param memo
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * @return IF_SEND
     */
    public Integer getIfSend() {
        return ifSend;
    }

    /**
     * @param ifSend
     */
    public void setIfSend(Integer ifSend) {
        this.ifSend = ifSend;
    }

    /**
     * @return SUSPECTED_FORBIDDEN_TYPE
     */
    public Integer getSuspectedForbiddenType() {
        return suspectedForbiddenType;
    }

    /**
     * @param suspectedForbiddenType
     */
    public void setSuspectedForbiddenType(Integer suspectedForbiddenType) {
        this.suspectedForbiddenType = suspectedForbiddenType;
    }

    /**
     * @return SUSPECTED_FORBIDDEN_SUBTYPE
     */
    public Integer getSuspectedForbiddenSubtype() {
        return suspectedForbiddenSubtype;
    }

    /**
     * @param suspectedForbiddenSubtype
     */
    public void setSuspectedForbiddenSubtype(Integer suspectedForbiddenSubtype) {
        this.suspectedForbiddenSubtype = suspectedForbiddenSubtype;
    }

    /**
     * @return CAPTURE_PICTURE_URL
     */
    public String getCapturePictureUrl() {
        return capturePictureUrl;
    }

    /**
     * @param capturePictureUrl
     */
    public void setCapturePictureUrl(String capturePictureUrl) {
        this.capturePictureUrl = capturePictureUrl;
    }

    /**
     * @return CAPTURE_DESCRIBE
     */
    public String getCaptureDescribe() {
        return captureDescribe;
    }

    /**
     * @param captureDescribe
     */
    public void setCaptureDescribe(String captureDescribe) {
        this.captureDescribe = captureDescribe;
    }

    /**
     * @return CAPTURE_TIME
     */
    public Date getCaptureTime() {
        return captureTime;
    }

    /**
     * @param captureTime
     */
    public void setCaptureTime(Date captureTime) {
        this.captureTime = captureTime;
    }
}