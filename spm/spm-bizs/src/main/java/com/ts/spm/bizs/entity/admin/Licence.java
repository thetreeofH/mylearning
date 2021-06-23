package com.ts.spm.bizs.entity.admin;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "SPM_SYS.TBL_LICENCE")
public class Licence {
    @Id
    @GeneratedValue(generator = "UUID")
    private String id;

    @Column(name = "AUTHORIZER")
    private String authorizer;

    @Column(name = "TYPE")
    private Long type;

    @Column(name = "USER_NUMBER")
    private BigDecimal userNumber;

    @Column(name = "KEY")
    private String key;

    @Column(name = "STATUS")
    private Long status;

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

    @Column(name = "UPD_DEPT_ID")
    private String updDeptId;

    @Column(name = "UPD_DEPT")
    private String updDept;

    @Column(name = "UPD_TIME")
    private Date updTime;

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
    private Long delFlag;

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
     * @return AUTHORIZER
     */
    public String getAuthorizer() {
        return authorizer;
    }

    /**
     * @param authorizer
     */
    public void setAuthorizer(String authorizer) {
        this.authorizer = authorizer;
    }

    /**
     * @return TYPE
     */
    public Long getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(Long type) {
        this.type = type;
    }

    /**
     * @return USER_NUMBER
     */
    public BigDecimal getUserNumber() {
        return userNumber;
    }

    /**
     * @param userNumber
     */
    public void setUserNumber(BigDecimal userNumber) {
        this.userNumber = userNumber;
    }

    /**
     * @return KEY
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return STATUS
     */
    public Long getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Long status) {
        this.status = status;
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
    public Long getDelFlag() {
        return delFlag;
    }

    /**
     * @param delFlag
     */
    public void setDelFlag(Long delFlag) {
        this.delFlag = delFlag;
    }
}