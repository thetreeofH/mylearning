package com.ts.spm.bizs.entity.admin;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "SPM_SYS.TBL_SUBWAY_SECURITY_COMPANY")
public class SecurityCompany {
    @Id
    @GeneratedValue(generator = "UUID")
    private String id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CRT_USER_ID")
    private String crtUserId;

    @Column(name = "CRT_USER")
    private String crtUser;

    @Column(name = "CRT_DEPT_ID")
    private String crtDeptId;

    @Column(name = "CRT_DEPT")
    private String crtDept;

    @Column(name = "CRT_TIME")
    private Date crtTime;

    @Column(name = "UPD_USER_ID")
    private String updUserId;

    @Column(name = "UPD_USER")
    private String updUser;

    @Column(name = "UPD_TIME")
    private Date updTime;

    @Column(name = "UPD_DEPT_ID")
    private String updDeptId;

    @Column(name = "UPD_DEPT")
    private String updDept;

    @Column(name = "DEL_USER_ID")
    private String delUserId;

    @Column(name = "DEL_USER")
    private String delUser;

    @Column(name = "DEL_DEPT_ID")
    private String delDeptId;

    @Column(name = "DEL_DEPT")
    private String delDept;

    @Column(name = "DEL_TIME")
    private Date delTime;

    @Column(name = "DEL_FLAG")
    private Integer delFlag;

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
     * @return NAME
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
     * @return CRT_USER
     */
    public String getCrtUser() {
        return crtUser;
    }

    /**
     * @param crtUser
     */
    public void setCrtUser(String crtUser) {
        this.crtUser = crtUser;
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
     * @return CRT_DEPT
     */
    public String getCrtDept() {
        return crtDept;
    }

    /**
     * @param crtDept
     */
    public void setCrtDept(String crtDept) {
        this.crtDept = crtDept;
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
     * @return UPD_USER
     */
    public String getUpdUser() {
        return updUser;
    }

    /**
     * @param updUser
     */
    public void setUpdUser(String updUser) {
        this.updUser = updUser;
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
     * @return UPD_DEPT
     */
    public String getUpdDept() {
        return updDept;
    }

    /**
     * @param updDept
     */
    public void setUpdDept(String updDept) {
        this.updDept = updDept;
    }

    /**
     * @return DEL_USER_ID
     */
    public String getDelUserId() {
        return delUserId;
    }

    /**
     * @param delUserId
     */
    public void setDelUserId(String delUserId) {
        this.delUserId = delUserId;
    }

    /**
     * @return DEL_USER
     */
    public String getDelUser() {
        return delUser;
    }

    /**
     * @param delUser
     */
    public void setDelUser(String delUser) {
        this.delUser = delUser;
    }

    /**
     * @return DEL_DEPT_ID
     */
    public String getDelDeptId() {
        return delDeptId;
    }

    /**
     * @param delDeptId
     */
    public void setDelDeptId(String delDeptId) {
        this.delDeptId = delDeptId;
    }

    /**
     * @return DEL_DEPT
     */
    public String getDelDept() {
        return delDept;
    }

    /**
     * @param delDept
     */
    public void setDelDept(String delDept) {
        this.delDept = delDept;
    }

    /**
     * @return DEL_TIME
     */
    public Date getDelTime() {
        return delTime;
    }

    /**
     * @param delTime
     */
    public void setDelTime(Date delTime) {
        this.delTime = delTime;
    }

    /**
     * @return DEL_FLAG
     */
    public Integer getDelFlag() {
        return delFlag;
    }

    /**
     * @param delFlag
     */
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}