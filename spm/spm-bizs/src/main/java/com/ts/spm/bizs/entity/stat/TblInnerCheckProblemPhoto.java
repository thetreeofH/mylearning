package com.ts.spm.bizs.entity.stat;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "TBL_INNER_CHECK_PROBLEM_PHOTO")
public class TblInnerCheckProblemPhoto {
    @Column(name = "PIC_ID")
    private Integer picId;

    @Column(name = "CHECK_ID")
    private Integer checkId;

    @Column(name = "PROB_ID")
    private Integer probId;

    @Column(name = "PIC_PATH")
    private String picPath;

    @Column(name = "PIC_NAME")
    private String picName;

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

    /**
     * @return PIC_ID
     */
    public Integer getPicId() {
        return picId;
    }

    /**
     * @param picId
     */
    public void setPicId(Integer picId) {
        this.picId = picId;
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

    /**
     *
     * @return PIC_NAME
     */
    public String getPicName() {
        return picName;
    }

    /**
     *
     * @param picName
     */
    public void setPicName(String picName) {
        this.picName = picName;
    }
}