package com.ts.spm.bizs.entity.jzpmq;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "jzp_oper.TBL_VIDEO_ON_DUTY_CHECK")
public class TblVideoOnDutyCheck {
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 判图员ID
     */
    @Column(name = "JUDGE_PICTURE_USER_ID")
    private String judgePictureUserId;

    /**
     * 判图员姓名
     */
    @Column(name = "JUDGE_PICTURE_USER_NAME")
    private String judgePictureUserName;

    /**
     * 检测点
     */
    @Column(name = "POINT_ID")
    private String pointId;

    /**
     * 抓拍照片URL
     */
    @Column(name = "CAPTURE_PICTURE_URL")
    private String capturePictureUrl;

    /**
     * 抓拍描述
     */
    @Column(name = "CAPTURE_DESCRIBE")
    private String captureDescribe;

    /**
     * 抓拍时间
     */
    @Column(name = "CAPTURE_TIME")
    private Date captureTime;

    /**
     * 插入时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /**
     * 备注
     */
    @Column(name = "MEMO")
    private String memo;

    @Column(name = "CAPTURE_ADDR")
    private BigDecimal captureAddr;

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
     * @return CAPTURE_ADDR
     */
    public BigDecimal getCaptureAddr() {
        return captureAddr;
    }

    /**
     * @param captureAddr
     */
    public void setCaptureAddr(BigDecimal captureAddr) {
        this.captureAddr = captureAddr;
    }
}