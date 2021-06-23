package com.ts.spm.bizs.entity.matter;


import com.ts.spm.bizs.entity.Attachment;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
@Table(name = "SPM_OPER.TBC_POINT_DANGER")
public class PointDanger {
    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "DNO")
    private String dno;

    @Column(name = "MTYPE")
    private String mtype;

    @Column(name = "STYPE")
    private String stype;

    @Column(name = "AJY_TYPE")
    private String ajyType;

    @Column(name = "AJY_NAME")
    private String ajyName;

    @Column(name = "AJY_IDNO")
    private String ajyIdno;

    @Column(name = "PSG_NAME")
    private String psgName;

    @Column(name = "PSG_IDNO")
    private String psgIdno;

    @Column(name = "DANGER_COUNT")
    private BigDecimal dangerCount;

    @Column(name = "PCE_NAME")
    private String pceName;

    @Column(name = "PCE_IDNO")
    private String pceIdno;

    @Column(name = "TIME")
    private Date time;

    @Column(name = "POINT_ID")
    private String pointId;

    @Column(name = "X_PIC_PATH")
    private String xPicPath;

    @Column(name = "PIC_PATH")
    private String picPath;

    @Column(name = "ITEM_RESULT")
    private BigDecimal itemResult;

    @Column(name = "ITEM_WAY")
    private BigDecimal itemWay;

    @Column(name = "PERSON_RESULT")
    private BigDecimal personResult;

    @Column(name = "CRT_TIME")
    private Date crtTime;

    @Column(name = "CRT_USER_ID")
    private String crtUserId;

    @Column(name = "CRT_DEPT_ID")
    private String crtDeptId;

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

    @Column(name = "FORBID_TYPE")
    private int forbidType;

    @Column(name = "NOTE")
    private String note;

    //违禁品录入类型(1.开包精检 2.手检处置 3.查获登记 4.一键报警(统计时不显示) 5.web录入 6.app录入)
    @Column(name = "INPUT_TYPE")
    private int inputType;

    @Transient
    private List<Attachment> attachs;

    @Transient
    private String departId;

    @Transient
    private String departName;

    @Transient
    private String stationId;

    @Transient
    private String stationName;

    @Transient
    private String pointName;


    /**
     * @return ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getDangerCount() {
        return dangerCount;
    }

    public void setDangerCount(BigDecimal dangerCount) {
        this.dangerCount = dangerCount;
    }

    /**
     * @return DNO
     */
    public String getDno() {
        return dno;
    }

    /**
     * @param dno
     */
    public void setDno(String dno) {
        this.dno = dno;
    }

    /**
     * @return MTYPE
     */
    public String getMtype() {
        return mtype;
    }

    /**
     * @param mtype
     */
    public void setMtype(String mtype) {
        this.mtype = mtype;
    }

    /**
     * @return STYPE
     */
    public String getStype() {
        return stype;
    }

    /**
     * @param stype
     */
    public void setStype(String stype) {
        this.stype = stype;
    }

    /**
     * @return AJY_TYPE
     */
    public String getAjyType() {
        return ajyType;
    }

    /**
     * @param ajyType
     */
    public void setAjyType(String ajyType) {
        this.ajyType = ajyType;
    }

    /**
     * @return AJY_NAME
     */
    public String getAjyName() {
        return ajyName;
    }

    /**
     * @param ajyName
     */
    public void setAjyName(String ajyName) {
        this.ajyName = ajyName;
    }

    /**
     * @return AJY_IDNO
     */
    public String getAjyIdno() {
        return ajyIdno;
    }

    /**
     * @param ajyIdno
     */
    public void setAjyIdno(String ajyIdno) {
        this.ajyIdno = ajyIdno;
    }

    /**
     * @return PSG_NAME
     */
    public String getPsgName() {
        return psgName;
    }

    /**
     * @param psgName
     */
    public void setPsgName(String psgName) {
        this.psgName = psgName;
    }

    /**
     * @return PSG_IDNO
     */
    public String getPsgIdno() {
        return psgIdno;
    }

    /**
     * @param psgIdno
     */
    public void setPsgIdno(String psgIdno) {
        this.psgIdno = psgIdno;
    }

    /**
     * @return TIME
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * @return PIC_PATH
     */
    public String getPicPath() {
        return picPath;
    }

    /**
     * @param picPath
     */
    public void setPicPath(String picPath) {
        this.picPath = picPath;
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
     * @return X_PIC_PATH
     */
    public String getxPicPath() {
        return xPicPath;
    }

    /**
     * @param xPicPath
     */
    public void setxPicPath(String xPicPath) {
        this.xPicPath = xPicPath;
    }

    /**
     * @return ITEM_RESULT
     */
    public BigDecimal getItemResult() {
        return itemResult;
    }

    /**
     * @param itemResult
     */
    public void setItemResult(BigDecimal itemResult) {
        this.itemResult = itemResult;
    }

    /**
     * @return PERSON_RESULT
     */
    public BigDecimal getPersonResult() {
        return personResult;
    }

    /**
     * @param personResult
     */
    public void setPersonResult(BigDecimal personResult) {
        this.personResult = personResult;
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

    public int getForbidType() {
        return forbidType;
    }

    public void setForbidType(int forbidType) {
        this.forbidType = forbidType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getInputType() {
        return inputType;
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
    }

    public List<Attachment> getAttachs() {
        return attachs;
    }

    public void setAttachs(List<Attachment> attachs) {
        this.attachs = attachs;
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

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getPceName() {
        return pceName;
    }

    public void setPceName(String pceName) {
        this.pceName = pceName;
    }

    public String getPceIdno() {
        return pceIdno;
    }

    public void setPceIdno(String pceIdno) {
        this.pceIdno = pceIdno;
    }

    public BigDecimal getItemWay() {
        return itemWay;
    }

    public void setItemWay(BigDecimal itemWay) {
        this.itemWay = itemWay;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }
}
