package com.ts.spm.bizs.entity.msg;


import com.ts.spm.bizs.entity.Attachment;

import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class TbcPointDanger {

    private Integer id;

    private String dno;

    private String mtype;

    private Long stype;

    private String ajyType;

    private String ajyName;

    private String ajyIdno;

    private String psgName;

    private String psgIdno;

    private BigDecimal dangerCount;

    private String pceName;

    private String pceIdno;

    private Date time;

    private String picPath;

    private String pointId;

    private String xPicPath;

    private BigDecimal itemResult;

    private BigDecimal itemWay;

    private BigDecimal personResult;

    private Integer uploadFlag;

    private Date crtTime;

    private String crtUserId;

    private String crtDeptId;

    private String crtUserName;

    private Date updTime;

    private String updUserId;

    private String updUserName;

    private String updDeptId;

    private String tenantId;

    private int forbidType;

    private String note;

    private int inputType;

    public int getInputType() {
        return inputType;
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
    }

    @Transient
    private List<Attachment> attachs;

    @Transient
    private String stationId;


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
    public Long getStype() {
        return stype;
    }

    /**
     * @param stype
     */
    public void setStype(Long stype) {
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
     * @return UPLOAD_FLAG
     */
    public Integer getUploadFlag() {
        return uploadFlag;
    }

    /**
     * @param uploadFlag
     */
    public void setUploadFlag(Integer uploadFlag) {
        this.uploadFlag = uploadFlag;
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

    public List<Attachment> getAttachs() {
        return attachs;
    }

    public void setAttachs(List<Attachment> attachs) {
        this.attachs = attachs;
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

}
