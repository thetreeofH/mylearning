package com.ts.spm.bizs.entity.stat;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "TBL_SUBWAY_DAY_PROBLEM")
public class DayProblem implements Serializable {
    @Column(name = "PROBLEM_ID")
    private Integer problemId;

    @Column(name = "POINT_ID")
    private String pointId;

    @Column(name = "CHECK_USER")
    private String checkUser;

    @Column(name = "CHECK_DATE")
    private Date checkDate;

    @Column(name = "IS_PROB")
    private Integer isProb;

    @Column(name = "PROBLEM_TYPE_ID")
    private Integer problemTypeId;

    @Column(name = "INSPECTION_TYPE")
    private Integer inspectionType;

    @Column(name = "REGISTER_ID")
    private Integer registerId;

    @Column(name = "PROBLEM_NOTE")
    private String problemNote;

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
     * @return PROBLEM_ID
     */
    public Integer getProblemId() {
        return problemId;
    }

    /**
     * @param problemId
     */
    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
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
     * @return CHECK_USER
     */
    public String getCheckUser() {
        return checkUser;
    }

    /**
     * @param checkUser
     */
    public void setCheckUser(String checkUser) {
        this.checkUser = checkUser;
    }

    /**
     * @return CHECK_DATE
     */
    public Date getCheckDate() {
        return checkDate;
    }

    /**
     * @param checkDate
     */
    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    /**
     * @return IS_PROB
     */
    public Integer getIsProb() {
        return isProb;
    }

    /**
     * @param isProb
     */
    public void setIsProb(Integer isProb) {
        this.isProb = isProb;
    }

    /**
     * @return PROBLEM_TYPE_ID
     */
    public Integer getProblemTypeId() {
        return problemTypeId;
    }

    /**
     * @param problemTypeId
     */
    public void setProblemTypeId(Integer problemTypeId) {
        this.problemTypeId = problemTypeId;
    }

    /**
     * @return INSPECTION_TYPE
     */
    public Integer getInspectionType() {
        return inspectionType;
    }

    /**
     * @param inspectionType
     */
    public void setInspectionType(Integer inspectionType) {
        this.inspectionType = inspectionType;
    }

    /**
     * @return REGISTER_ID
     */
    public Integer getRegisterId() {
        return registerId;
    }

    /**
     * @param registerId
     */
    public void setRegisterId(Integer registerId) {
        this.registerId = registerId;
    }

    /**
     * @return PROBLEM_NOTE
     */
    public String getProblemNote() {
        return problemNote;
    }

    /**
     * @param problemNote
     */
    public void setProblemNote(String problemNote) {
        this.problemNote = problemNote;
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