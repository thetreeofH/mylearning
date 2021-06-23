package com.ts.spm.bizs.entity.equ;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "SPM_OPER.TBC_DEVICE_SUB_INFO")
public class SecurityDeviceSub {
    @Id
    @Column(name = "DEV_ID")
    private String devId;

    @Column(name = "DEV_NAME")
    private String devName;

    @Column(name = "DEV_TYPE")
    private String devType;

    @Column(name = "DEV_IP")
    private String devIp;

    @Column(name = "DEV_PORT")
    private Integer devPort;

    @Column(name = "DEV_USER")
    private String devUser;

    @Column(name = "DEV_PSWD")
    private String devPswd;

    @Column(name = "DEV_CHL")
    private Integer devChl;

    @Column(name = "ISVIDEO")
    private Integer isvideo;

    @Column(name = "USE_TYPE")
    private Integer useType;

    @Column(name = "REC_TYPE")
    private Integer recType;

    @Column(name = "POINT_ID")
    private String pointId;

    @Column(name = "IS_ONLINE")
    private Integer isOnline;

    @Column(name = "FAULT_TYPE")
    private Integer faultType;

    @Column(name = "CRT_TIME")
    private Date crtTime;

    @Column(name = "CRT_USER_ID")
    private String crtUserId;

    @Column(name = "CRT_USER_NAME")
    private String crtUserName;

    @Column(name = "CRT_DEPT_ID")
    private String crtDeptId;

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

    @Column(name = "LAST_ONLINE_TIME")
    private Date lastOnlineTime;

    @Column(name = "CODE")
    private String code;

    @Column(name = "DEVICE_MODEL")
    private String deviceModel;

    @Column(name = "DEVICE_SOURCE")
    private Integer deviceSource;

    @Transient
    private String point;
    @Transient
    private String station;
    @Transient
    private String stationId;

    /**
     * @return DEV_ID
     */
    public String getDevId() {
        return devId;
    }

    /**
     * @param devId
     */
    public void setDevId(String devId) {
        this.devId = devId;
    }

    /**
     * @return DEV_NAME
     */
    public String getDevName() {
        return devName;
    }

    /**
     * @param devName
     */
    public void setDevName(String devName) {
        this.devName = devName;
    }

    /**
     * @return DEV_TYPE
     */
    public String getDevType() {
        return devType;
    }

    /**
     * @param devType
     */
    public void setDevType(String devType) {
        this.devType = devType;
    }

    /**
     * @return DEV_IP
     */
    public String getDevIp() {
        return devIp;
    }

    /**
     * @param devIp
     */
    public void setDevIp(String devIp) {
        this.devIp = devIp;
    }

    /**
     * @return DEV_PORT
     */
    public Integer getDevPort() {
        return devPort;
    }

    /**
     * @param devPort
     */
    public void setDevPort(Integer devPort) {
        this.devPort = devPort;
    }

    /**
     * @return DEV_USER
     */
    public String getDevUser() {
        return devUser;
    }

    /**
     * @param devUser
     */
    public void setDevUser(String devUser) {
        this.devUser = devUser;
    }

    /**
     * @return DEV_PSWD
     */
    public String getDevPswd() {
        return devPswd;
    }

    /**
     * @param devPswd
     */
    public void setDevPswd(String devPswd) {
        this.devPswd = devPswd;
    }

    /**
     * @return DEV_CHL
     */
    public Integer getDevChl() {
        return devChl;
    }

    /**
     * @param devChl
     */
    public void setDevChl(Integer devChl) {
        this.devChl = devChl;
    }

    /**
     * @return ISVIDEO
     */
    public Integer getIsvideo() {
        return isvideo;
    }

    /**
     * @param isvideo
     */
    public void setIsvideo(Integer isvideo) {
        this.isvideo = isvideo;
    }

    /**
     * @return USE_TYPE
     */
    public Integer getUseType() {
        return useType;
    }

    /**
     * @param useType
     */
    public void setUseType(Integer useType) {
        this.useType = useType;
    }

    /**
     * @return REC_TYPE
     */
    public Integer getRecType() {
        return recType;
    }

    /**
     * @param recType
     */
    public void setRecType(Integer recType) {
        this.recType = recType;
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
     * @return IS_ONLINE
     */
    public Integer getIsOnline() {
        return isOnline;
    }

    /**
     * @param isOnline
     */
    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    /**
     * @return FAULT_TYPE
     */
    public Integer getFaultType() {
        return faultType;
    }

    /**
     * @param faultType
     */
    public void setFaultType(Integer faultType) {
        this.faultType = faultType;
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
     * @return CRT_DEPT_ID
     */
    public String getCrtDeptId() {
        return crtDeptId;
    }

    /**
     * @param crtDeptId
     */
    public void setCrtDeptId(String crtDeptId) {
        this.crtDeptId = crtDeptId;
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

    /**
     * @return UPD_DEPT_ID
     */
    public String getUpdDeptId() {
        return updDeptId;
    }

    /**
     * @param updDeptId
     */
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

    /**
     * @return LAST_ONLINE_TIME
     */
    public Date getLastOnlineTime() {
        return lastOnlineTime;
    }

    /**
     * @param lastOnlineTime
     */
    public void setLastOnlineTime(Date lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
    }

    /**
     * @return CODE
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
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
     * @return DEVICE_SOURCE
     */
    public Integer getDeviceSource() {
        return deviceSource;
    }

    /**
     * @param deviceSource
     */
    public void setDeviceSource(Integer deviceSource) {
        this.deviceSource = deviceSource;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }
}