package com.ts.spm.bizs.entity.admin;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "SPM_SYS.BASE_POINT")
public class BasePoint {
    @Id
    @GeneratedValue(generator = "UUID")
    private String id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ENTRY")
    private String entry;

    @Column(name = "GATESUM")
    private int gatesum;

    @Column(name = "DEPART_ID")
    private String departId;

    @Column(name = "DEPART")
    private String depart;

    @Column(name = "CRT_USER_ID")
    private String crtUserId;

    @Column(name = "CRT_USER_NAME")
    private String crtUserName;

    @Column(name = "UPD_USER_ID")
    private String updUserId;

    @Column(name = "UPD_USER_NAME")
    private String updUserName;

    @Column(name = "CRT_TIME")
    private Date crtTime;

    @Column(name = "UPD_TIME")
    private Date updTime;

    @Column(name = "DELETE_FLAG")
    private Integer deleteFlag;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "DATE_FLAG")
    private String dateFlag;

    @Column(name = "VALID_FLAG")
    private String validFlag;

    @Column(name = "AREA_ID")
    private String areaId;

    @Column(name = "AREA")
    private String area;

    @Column(name = "LINE_ID")
    private String lineId;

    @Column(name = "LINE")
    private String line;

    /**
     * @return id
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
     * @return code
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
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return entry
     */
    public String getEntry() {
        return entry;
    }

    /**
     * @param entry
     */
    public void setEntry(String entry) {
        this.entry = entry;
    }

    /**
     * @return GATESUM
     */
    public int getGatesum() {
        return gatesum;
    }

    /**
     * @param gatesum
     */
    public void setGatesum(int gatesum) {
        this.gatesum = gatesum;
    }

    /**
     * @return STATION_ID
     */
    public String getDepartId() {
        return departId;
    }

    /**
     * @param departId
     */
    public void setDepartId(String departId) {
        this.departId = departId;
    }

    /**
     * @return STATION
     */
    public String getDepart() {
        return depart;
    }

    /**
     * @param depart
     */
    public void setDepart(String depart) {
        this.depart = depart;
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
     * @return DELETE_FLAG
     */
    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * @param deleteFlag
     */
    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return DATE_FLAG
     */
    public String getDateFlag() {
        return dateFlag;
    }

    /**
     * @param dateFlag
     */
    public void setDateFlag(String dateFlag) {
        this.dateFlag = dateFlag;
    }

    public String getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}