package com.ts.spm.bizs.entity.jzpmq;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "jzp_oper.DEAL_RESULT_INFO")
public class DealResultInfo {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "FIRST_TIME")
    private Date firstTime;

    @Column(name = "MISSION_ID")
    private String missionId;

    @Column(name = "POINTID")
    private String pointid;

    @Column(name = "SN")
    private String sn;

    @Column(name = "JUDGE_PICTURE_SOURCE")
    private BigDecimal judgePictureSource;

    @Column(name = "HANDLE_USER_ID")
    private String handleUserId;

    @Column(name = "HANDLE_RESULT")
    private BigDecimal handleResult;

    @Column(name = "LOCATION_OF_SUSPECTED_ITEMS")
    private String locationOfSuspectedItems;

    @Column(name = "SUSPECTED_FORBIDDEN_TYPE")
    private BigDecimal suspectedForbiddenType;

    @Column(name = "PASSENGER_NAME")
    private String passengerName;

    @Column(name = "PASSENGER_ID_CARD")
    private String passengerIdCard;

    @Column(name = "SUSPECTED_ITEMS_PICTURE")
    private String suspectedItemsPicture;

    @Column(name = "JUDGE_USER_ID")
    private String judgeUserId;



    @Column(name = "AUX_SUSPECTED_ITEMS_PICTURE")
    private String auxSuspectedItemsPicture;


    @Column(name = "SUSPECTED_ITEMS_VISUAL_PICTURE")
    private String suspectedItemsVisualPicture;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @Column(name = "MEMO")
    private String memo;

    @Column(name = "ATTR1")
    private String attr1;

    @Column(name = "ATTR2")
    private String attr2;

    @Column(name = "ATTR3")
    private String attr3;

    @Column(name = "ATTR4")
    private String attr4;

    @Column(name = "ATTR5")
    private String attr5;

    @Column(name = "ATTR6")
    private String attr6;

    @Column(name = "IF_CHECK")
    private BigDecimal ifCheck;

    @Column(name = "SUSPECTED_FORBIDDEN_SUBTYPE")
    private BigDecimal suspectedForbiddenSubtype;

    @Column(name = "HANDLE_MODE")
    private String handleMode;

    public String getJudgeUserId() {
        return judgeUserId;
    }

    public void setJudgeUserId(String judgeUserId) {
        this.judgeUserId = judgeUserId;
    }

    public String getAuxSuspectedItemsPicture() {
        return auxSuspectedItemsPicture;
    }

    public void setAuxSuspectedItemsPicture(String auxSuspectedItemsPicture) {
        this.auxSuspectedItemsPicture = auxSuspectedItemsPicture;
    }

    public String getSuspectedItemsVisualPicture() {
        return suspectedItemsVisualPicture;
    }

    public void setSuspectedItemsVisualPicture(String suspectedItemsVisualPicture) {
        this.suspectedItemsVisualPicture = suspectedItemsVisualPicture;
    }

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
     * @return FIRST_TIME
     */
    public Date getFirstTime() {
        return firstTime;
    }

    /**
     * @param firstTime
     */
    public void setFirstTime(Date firstTime) {
        this.firstTime = firstTime;
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
     * @return POINTID
     */
    public String getPointid() {
        return pointid;
    }

    /**
     * @param pointid
     */
    public void setPointid(String pointid) {
        this.pointid = pointid;
    }

    /**
     * @return SN
     */
    public String getSn() {
        return sn;
    }

    /**
     * @param sn
     */
    public void setSn(String sn) {
        this.sn = sn;
    }

    /**
     * @return JUDGE_PICTURE_SOURCE
     */
    public BigDecimal getJudgePictureSource() {
        return judgePictureSource;
    }

    /**
     * @param judgePictureSource
     */
    public void setJudgePictureSource(BigDecimal judgePictureSource) {
        this.judgePictureSource = judgePictureSource;
    }

    /**
     * @return HANDLE_USER_ID
     */
    public String getHandleUserId() {
        return handleUserId;
    }

    /**
     * @param handleUserId
     */
    public void setHandleUserId(String handleUserId) {
        this.handleUserId = handleUserId;
    }

    /**
     * @return HANDLE_RESULT
     */
    public BigDecimal getHandleResult() {
        return handleResult;
    }

    /**
     * @param handleResult
     */
    public void setHandleResult(BigDecimal handleResult) {
        this.handleResult = handleResult;
    }

    /**
     * @return LOCATION_OF_SUSPECTED_ITEMS
     */
    public String getLocationOfSuspectedItems() {
        return locationOfSuspectedItems;
    }

    /**
     * @param locationOfSuspectedItems
     */
    public void setLocationOfSuspectedItems(String locationOfSuspectedItems) {
        this.locationOfSuspectedItems = locationOfSuspectedItems;
    }

    /**
     * @return SUSPECTED_FORBIDDEN_TYPE
     */
    public BigDecimal getSuspectedForbiddenType() {
        return suspectedForbiddenType;
    }

    /**
     * @param suspectedForbiddenType
     */
    public void setSuspectedForbiddenType(BigDecimal suspectedForbiddenType) {
        this.suspectedForbiddenType = suspectedForbiddenType;
    }

    /**
     * @return PASSENGER_NAME
     */
    public String getPassengerName() {
        return passengerName;
    }

    /**
     * @param passengerName
     */
    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    /**
     * @return PASSENGER_ID_CARD
     */
    public String getPassengerIdCard() {
        return passengerIdCard;
    }

    /**
     * @param passengerIdCard
     */
    public void setPassengerIdCard(String passengerIdCard) {
        this.passengerIdCard = passengerIdCard;
    }

    /**
     * @return SUSPECTED_ITEMS_PICTURE
     */
    public String getSuspectedItemsPicture() {
        return suspectedItemsPicture;
    }

    /**
     * @param suspectedItemsPicture
     */
    public void setSuspectedItemsPicture(String suspectedItemsPicture) {
        this.suspectedItemsPicture = suspectedItemsPicture;
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
     * @return ATTR5
     */
    public String getAttr5() {
        return attr5;
    }

    /**
     * @param attr5
     */
    public void setAttr5(String attr5) {
        this.attr5 = attr5;
    }

    /**
     * @return ATTR6
     */
    public String getAttr6() {
        return attr6;
    }

    /**
     * @param attr6
     */
    public void setAttr6(String attr6) {
        this.attr6 = attr6;
    }

    /**
     * @return IF_CHECK
     */
    public BigDecimal getIfCheck() {
        return ifCheck;
    }

    /**
     * @param ifCheck
     */
    public void setIfCheck(BigDecimal ifCheck) {
        this.ifCheck = ifCheck;
    }

    /**
     * @return SUSPECTED_FORBIDDEN_SUBTYPE
     */
    public BigDecimal getSuspectedForbiddenSubtype() {
        return suspectedForbiddenSubtype;
    }

    /**
     * @param suspectedForbiddenSubtype
     */
    public void setSuspectedForbiddenSubtype(BigDecimal suspectedForbiddenSubtype) {
        this.suspectedForbiddenSubtype = suspectedForbiddenSubtype;
    }

    /**
     * @return HANDLE_MODE
     */
    public String getHandleMode() {
        return handleMode;
    }

    /**
     * @param handleMode
     */
    public void setHandleMode(String handleMode) {
        this.handleMode = handleMode;
    }
}