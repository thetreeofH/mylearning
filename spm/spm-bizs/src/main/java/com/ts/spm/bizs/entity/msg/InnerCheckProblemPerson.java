package com.ts.spm.bizs.entity.msg;

import java.util.Date;


public class InnerCheckProblemPerson {

    private Integer id;

    private Integer probId;

    private Integer checkId;

    private String checkerName;

    private String checkerId;

    private int ajyType;

    private String ajyTypeStr;

    private Date crtTime;

    private String crtUserId;

    private String crtUserName;

    private Date updTime;

    private String updUserId;

    private String updUserName;

    private String tenantId;

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
     * @return CHECKER_NAME
     */
    public String getCheckerName() {
        return checkerName;
    }

    /**
     * @param checkerName
     */
    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }

    /**
     * @return CHECKER_ID
     */
    public String getCheckerId() {
        return checkerId;
    }

    /**
     * @param checkerId
     */
    public void setCheckerId(String checkerId) {
        this.checkerId = checkerId;
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
}