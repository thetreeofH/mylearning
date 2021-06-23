package com.ts.spm.bizs.entity.stat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "TBL_INNER_CHECK_PROBLEM")
public class TblInnerCheckProblem {
    @Column(name = "PROB_ID")
    private Integer probId;

    @Column(name = "CHECK_ID")
    private Integer checkId;

    @Column(name = "PROB_TYPE")
    private Integer probType;

    @Column(name = "PROB_SUB_TYPE")
    private Integer probSubType;

    @Column(name = "PROB_DESP")
    private String probDesp;

    @Column(name = "AJY_TYPE")
    private int ajyType;

    @Column(name = "AJY_TYPE_STR")
    private String ajyTypeStr;

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

    @Column(name = "TENANT_ID")
    private String tenantId;

    @Transient
    private List<TblInnerCheckProblemPerson> tblInnerCheckProblemPersonList;

    @Transient
    private List<TblInnerCheckProblemPhoto> tblInnerCheckProblemPhotoList;

    /**
     * @return PROB_ID
     */
    public Integer getProbId() {
        return probId;
    }

    /**
     * @param probId
     */
    public void setProbId(Integer probId) {
        this.probId = probId;
    }

    /**
     * @return CHECK_ID
     */
    public Integer getCheckId() {
        return checkId;
    }

    /**
     * @param checkId
     */
    public void setCheckId(Integer checkId) {
        this.checkId = checkId;
    }

    /**
     * @return PROB_TYPE
     */
    public Integer getProbType() {
        return probType;
    }

    /**
     * @param probType
     */
    public void setProbType(Integer probType) {
        this.probType = probType;
    }

    /**
     * @return PROB_SUB_TYPE
     */
    public Integer getProbSubType() {
        return probSubType;
    }

    /**
     * @param probSubType
     */
    public void setProbSubType(Integer probSubType) {
        this.probSubType = probSubType;
    }

    /**
     * @return PROB_DESP
     */
    public String getProbDesp() {
        return probDesp;
    }

    /**
     * @param probDesp
     */
    public void setProbDesp(String probDesp) {
        this.probDesp = probDesp;
    }

    /**
     * @return AJY_TYPE
     */
    public int getAjyType() {
        return ajyType;
    }

    /**
     * @param ajyType
     */
    public void setAjyType(int ajyType) {
        this.ajyType = ajyType;
    }

    /**
     * @return AJY_TYPE_STR
     */
    public String getAjyTypeStr() {
        return ajyTypeStr;
    }

    /**
     * @param ajyTypeStr
     */
    public void setAjyTypeStr(String ajyTypeStr) {
        this.ajyTypeStr = ajyTypeStr;
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

    public List<TblInnerCheckProblemPerson> getTblInnerCheckProblemPersonList() {
        return tblInnerCheckProblemPersonList;
    }

    public void setTblInnerCheckProblemPersonList(List<TblInnerCheckProblemPerson> tblInnerCheckProblemPersonList) {
        this.tblInnerCheckProblemPersonList = tblInnerCheckProblemPersonList;
    }

    public List<TblInnerCheckProblemPhoto> getTblInnerCheckProblemPhotoList() {
        return tblInnerCheckProblemPhotoList;
    }

    public void setTblInnerCheckProblemPhotoList(List<TblInnerCheckProblemPhoto> tblInnerCheckProblemPhotoList) {
        this.tblInnerCheckProblemPhotoList = tblInnerCheckProblemPhotoList;
    }
}