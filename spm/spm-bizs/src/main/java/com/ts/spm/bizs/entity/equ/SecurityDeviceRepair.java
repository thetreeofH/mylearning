package com.ts.spm.bizs.entity.equ;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "TBL_SECURITY_DEVICE_REPAIR")
public class SecurityDeviceRepair {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "DEVICE_TYPE")
    private String deviceType;

    @Column(name = "DEVICE_MODEL")
    private String deviceModel;

    @Column(name = "DEVICE_CODE")
    private String deviceCode;

    @Column(name = "DEVICE_ID")
    private String deviceId;

    @Column(name = "POINT_ID")
    private String pointId;

    @Column(name = "POINT")
    private String point;

    @Column(name = "DEVICE_TYPE_ID")
    private String deviceTypeId;

    @Column(name = "DEVICE_MODEL_ID")
    private String deviceModelId;

    @Column(name = "CALL_PERSON")
    private String callPerson;

    @Column(name = "RECORD_PERSON")
    private String recordPerson;

    @Column(name = "MENDING_TIME")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date mendingTime;

    @Column(name = "MENDING_TYPE")
    private String mendingType;

    @Column(name = "REPAIR_REASON")
    private String repairReason;

    @Column(name = "REPAIR_TIME")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date repairTime;

    @Column(name = "CRT_TIME")
    private Date crtTime;

    @Column(name = "CRT_USER_ID")
    private String crtUserId;

    @Column(name = "CRT_USER_NAME")
    private String crtUserName;

    @Column(name = "UPD_TIME")
    private Date updTime;

    @Column(name = "UPD_USER_ID")
    private String updUserId;

    @Column(name = "UPD_USER_NAME")
    private String updUserName;


    @Column(name = "UPD_DEPT_ID")
    private String updDeptId;

    @Column(name = "TENANT_ID")
    private String tenantId;

    @Column(name = "STATION_ID")
    private String stationId;

    @Column(name = "STATION")
    private String station;

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
     * @return DEVICE_TYPE
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * @param deviceType
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * @return DEVICE_MODEL
     */
    public String getDeviceModel() {
        return deviceModel;
    }

    /**
     * @param deviceModel
     */
    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    /**
     * @return DEVICE_CODE
     */
    public String getDeviceCode() {
        return deviceCode;
    }

    /**
     * @param deviceCode
     */
    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    /**
     * @return DEVICE_ID
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    /**
     * @return DEVICE_TYPE_ID
     */
    public String getDeviceTypeId() {
        return deviceTypeId;
    }

    /**
     * @param deviceTypeId
     */
    public void setDeviceTypeId(String deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    /**
     * @return DEVICE_MODEL_ID
     */
    public String getDeviceModelId() {
        return deviceModelId;
    }

    /**
     * @param deviceModelId
     */
    public void setDeviceModelId(String deviceModelId) {
        this.deviceModelId = deviceModelId;
    }

    /**
     * @return CALL_PERSON
     */
    public String getCallPerson() {
        return callPerson;
    }

    /**
     * @param callPerson
     */
    public void setCallPerson(String callPerson) {
        this.callPerson = callPerson;
    }

    /**
     * @return RECORD_PERSON
     */
    public String getRecordPerson() {
        return recordPerson;
    }

    /**
     * @param recordPerson
     */
    public void setRecordPerson(String recordPerson) {
        this.recordPerson = recordPerson;
    }

    /**
     * @return MENDING_TIME
     */
    public Date getMendingTime() {
        return mendingTime;
    }

    /**
     * @param mendingTime
     */
    public void setMendingTime(Date mendingTime) {
        this.mendingTime = mendingTime;
    }

    /**
     * @return MENDING_TYPE
     */
    public String getMendingType() {
        return mendingType;
    }

    /**
     * @param mendingType
     */
    public void setMendingType(String mendingType) {
        this.mendingType = mendingType;
    }

    /**
     * @return REPAIR_REASON
     */
    public String getRepairReason() {
        return repairReason;
    }

    /**
     * @param repairReason
     */
    public void setRepairReason(String repairReason) {
        this.repairReason = repairReason;
    }

    /**
     * @return REPAIR_TIME
     */
    public Date getRepairTime() {
        return repairTime;
    }

    /**
     * @param repairTime
     */
    public void setRepairTime(Date repairTime) {
        this.repairTime = repairTime;
    }

    /**
     * @return CRT_TIME
     */
    public Date getCrtTime() {
        return crtTime;
    }

    /**
     * @param crtTime
     */
    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    /**
     * @return CRT_USER_ID
     */
    public String getCrtUserId() {
        return crtUserId;
    }

    /**
     * @param crtUserId
     */
    public void setCrtUserId(String crtUserId) {
        this.crtUserId = crtUserId;
    }

    /**
     * @return CRT_USER_NAME
     */
    public String getCrtUserName() {
        return crtUserName;
    }

    /**
     * @param crtUserName
     */
    public void setCrtUserName(String crtUserName) {
        this.crtUserName = crtUserName;
    }

    /**
     * @return UPD_TIME
     */
    public Date getUpdTime() {
        return updTime;
    }

    /**
     * @param updTime
     */
    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }

    /**
     * @return UPD_USER_ID
     */
    public String getUpdUserId() {
        return updUserId;
    }

    /**
     * @param updUserId
     */
    public void setUpdUserId(String updUserId) {
        this.updUserId = updUserId;
    }

    /**
     * @return UPD_USER_NAME
     */
    public String getUpdUserName() {
        return updUserName;
    }

    /**
     * @param updUserName
     */
    public void setUpdUserName(String updUserName) {
        this.updUserName = updUserName;
    }

    public String getUpdDeptId() {
        return updDeptId;
    }

    public void setUpdDeptId(String updDeptId) {
        this.updDeptId = updDeptId;
    }

    /**
     * @return TENANT_ID
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * @param tenantId
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
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
}