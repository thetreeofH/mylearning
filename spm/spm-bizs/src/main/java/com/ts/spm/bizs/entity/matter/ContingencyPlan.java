package com.ts.spm.bizs.entity.matter;

import com.ts.spm.bizs.entity.Attachment;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "SPM_OPER.TBL_CONTINGENCY_PLAN")
public class ContingencyPlan {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "PRINCIPLE")
    private String principle;

    @Column(name = "MEASURE")
    private String measure;

    @Column(name = "MAIN_PERSON")
    private String mainPerson;

    @Column(name = "SUB_PERSON")
    private String subPerson;

    @Column(name = "OTHER_PERSON")
    private String otherPerson;

    @Column(name = "PLAN_LEVEL")
    private String planLevel;

    @Column(name = "FILE_PATH")
    private String filePath;

    @Column(name = "COMAND")
    private String comand;

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

    @Column(name = "PLAN_ID")
    private String planId;

    @Column(name = "CRT_DEPT_ID")
    private String crtDeptId;

    @Column(name = "UPD_DEPT_ID")
    private String updDeptId;

    @Column(name = "CODE")
    private String code;

    @Column(name = "DEPART")
    private String depart;

    @Column(name = "DEPART_ID")
    private String departId;

    @Transient
    private List<Attachment> attachments;

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
     * @return TITLE
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return TYPE
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return PRINCIPLE
     */
    public String getPrinciple() {
        return principle;
    }

    /**
     * @param principle
     */
    public void setPrinciple(String principle) {
        this.principle = principle;
    }

    /**
     * @return MEASURES
     */
    public String getMeasure() {
        return measure;
    }

    /**
     * @param measure
     */
    public void setMeasure(String measure) {
        this.measure = measure;
    }

    /**
     * @return MAIN_PERSON
     */
    public String getMainPerson() {
        return mainPerson;
    }

    /**
     * @param mainPerson
     */
    public void setMainPerson(String mainPerson) {
        this.mainPerson = mainPerson;
    }

    /**
     * @return SUB_PERSON
     */
    public String getSubPerson() {
        return subPerson;
    }

    /**
     * @param subPerson
     */
    public void setSubPerson(String subPerson) {
        this.subPerson = subPerson;
    }

    /**
     * @return OTHER_PERSON
     */
    public String getOtherPerson() {
        return otherPerson;
    }

    /**
     * @param otherPerson
     */
    public void setOtherPerson(String otherPerson) {
        this.otherPerson = otherPerson;
    }

    /**
     * @return PLAN_LEVEL
     */
    public String getPlanLevel() {
        return planLevel;
    }

    /**
     * @param planLevel
     */
    public void setPlanLevel(String planLevel) {
        this.planLevel = planLevel;
    }

    /**
     * @return FILE_PATH
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @return COMAND
     */
    public String getComand() {
        return comand;
    }

    /**
     * @param comand
     */
    public void setComand(String comand) {
        this.comand = comand;
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
     * @return PLAN_ID
     */
    public String getPlanId() {
        return planId;
    }

    /**
     * @param planId
     */
    public void setPlanId(String planId) {
        this.planId = planId;
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

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
}