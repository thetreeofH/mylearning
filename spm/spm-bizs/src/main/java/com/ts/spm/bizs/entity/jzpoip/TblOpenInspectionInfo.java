package com.ts.spm.bizs.entity.jzpoip;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "jzp_oper.TBL_OPEN_INSPECTION_INFO")
public class TblOpenInspectionInfo {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "FIRST_TIME")
    private Date firstTime;

    @Column(name = "POINTID")
    private String pointid;

    @Column(name = "SN")
    private String sn;

    @Column(name = "HANDLE_USER_ID")
    private String handleUserId;

    @Column(name = "CONFIRM_FORBIDDEN_TYPE")
    private Integer confirmForbiddenType;

    @Column(name = "LOCATION_OF_SUSPECTED_ITEMS")
    private String locationOfSuspectedItems;

    @Column(name = "PICTURES")
    private String pictures;

    @Column(name = "PASSENGER_NAME")
    private String passengerName;

    @Column(name = "PASSENGER_ID_CARD")
    private String passengerIdCard;

    @Column(name = "OPERATOR_USER_NAME")
    private String operatorUserName;

    @Column(name = "OPERATOR_USER_ID")
    private String operatorUserId;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

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

    @Column(name = "MEMO")
    private String memo;

    @Column(name = "MISSION_ID")
    private String missionId;

    @Column(name = "HANDLE_RESULT")
    private Short handleResult;

    @Column(name = "IF_CHECK")
    private Short ifCheck;

    @Column(name = "IF_CONTRABAND")
    private Short ifContraband;

    @Column(name = "PASSENGER_PHONE_NUM")
    private String passengerPhoneNum;

    @Column(name = "CONFIRM_FORBIDDEN_SUBTYPE")
    private BigDecimal confirmForbiddenSubtype;

    @Column(name="OPEN_INSPECTION_MODE")
    private String openInspectionMode;

    @Column(name="CHECK_SOURCE")
    private String checkSource;

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
     * @return CONFIRM_FORBIDDEN_TYPE
     */
    public Integer getConfirmForbiddenType() {
        return confirmForbiddenType;
    }

    /**
     * @param confirmForbiddenType
     */
    public void setConfirmForbiddenType(Integer confirmForbiddenType) {
        this.confirmForbiddenType = confirmForbiddenType;
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
     * @return PICTURES
     */
    public String getPictures() {
        return pictures;
    }

    /**
     * @param pictures
     */
    public void setPictures(String pictures) {
        this.pictures = pictures;
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
     * @return OPERATOR_USER_NAME
     */
    public String getOperatorUserName() {
        return operatorUserName;
    }

    /**
     * @param operatorUserName
     */
    public void setOperatorUserName(String operatorUserName) {
        this.operatorUserName = operatorUserName;
    }

    /**
     * @return OPERATOR_USER_ID
     */
    public String getOperatorUserId() {
        return operatorUserId;
    }

    /**
     * @param operatorUserId
     */
    public void setOperatorUserId(String operatorUserId) {
        this.operatorUserId = operatorUserId;
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
     * @return HANDLE_RESULT
     */
    public Short getHandleResult() {
        return handleResult;
    }

    /**
     * @param handleResult
     */
    public void setHandleResult(Short handleResult) {
        this.handleResult = handleResult;
    }

    /**
     * @return IF_CHECK
     */
    public Short getIfCheck() {
        return ifCheck;
    }

    /**
     * @param ifCheck
     */
    public void setIfCheck(Short ifCheck) {
        this.ifCheck = ifCheck;
    }

    /**
     * @return IF_CONTRABAND
     */
    public Short getIfContraband() {
        return ifContraband;
    }

    /**
     * @param ifContraband
     */
    public void setIfContraband(Short ifContraband) {
        this.ifContraband = ifContraband;
    }

    /**
     * @return PASSENGER_PHONE_NUM
     */
    public String getPassengerPhoneNum() {
        return passengerPhoneNum;
    }

    /**
     * @param passengerPhoneNum
     */
    public void setPassengerPhoneNum(String passengerPhoneNum) {
        this.passengerPhoneNum = passengerPhoneNum;
    }

    /**
     * @return CONFIRM_FORBIDDEN_SUBTYPE
     */
    public BigDecimal getConfirmForbiddenSubtype() {
        return confirmForbiddenSubtype;
    }

    /**
     * @param confirmForbiddenSubtype
     */
    public void setConfirmForbiddenSubtype(BigDecimal confirmForbiddenSubtype) {
        this.confirmForbiddenSubtype = confirmForbiddenSubtype;
    }

    public String getOpenInspectionMode() {
        return openInspectionMode;
    }

    public void setOpenInspectionMode(String openInspectionMode) {
        this.openInspectionMode = openInspectionMode;
    }

    public String getCheckSource() {
        return checkSource;
    }

    public void setCheckSource(String checkSource) {
        this.checkSource = checkSource;
    }
}