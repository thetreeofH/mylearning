package com.ts.spm.bizs.entity.person;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "TBC_AJY_LIST")
public class AjyInfo {
    @Id
    @Column(name="ID")
    private Integer id;

    @Column(name = "IDCARD")
    private String idcard;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SEX")
    private Long sex;

    /**
     * 民族
     */
    @Column(name = "FORK")
    private String fork;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PIC_PATH")
    private String picPath;

    /**
     * 第一次打卡时间
     */
    @Column(name = "F_CHECK")
    private Date fCheck;

    /**
     * 最后一次打卡时间
     */
    @Column(name = "L_CHECK")
    private Date lCheck;

    @Column(name = "BIRTHDAY")
    private Date birthday;

    @Column(name = "SECURITYID")
    private String securityid;

    @Column(name = "AJCOM")
    private String ajcom;

    @Column(name = "W_TIME")
    private Date wTime;

    /**
     * 籍贯
     */
    @Column(name = "NATIVE_PLACE")
    private String nativePlace;

    /**
     * 角色:安检员 中队长 大队长
     */
    @Column(name = "ROLE")
    private String role;

    /**
     * 学历
     */
    @Column(name = "DEGREE")
    private String degree;

    @Column(name = "SCHOOL")
    private String school;

    /**
     * 政治面貌
     */
    @Column(name = "POLITICAL")
    private String political;

    /**
     * 所属中队
     */
    @Column(name = "TEAM")
    private String team;

    @Column(name = "DEPART_ID")
    private String departId;

    @Column(name = "BLACK_FLAG")
    private Integer blackFlag;

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
     * @return IDCARD
     */
    public String getIdcard() {
        return idcard;
    }

    /**
     * @param idcard
     */
    public void setIdcard(String idcard) {
        this.idcard = idcard;
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
     * @return SEX
     */
    public Long getSex() {
        return sex;
    }

    /**
     * @param sex
     */
    public void setSex(Long sex) {
        this.sex = sex;
    }

    /**
     * @return FORK
     */
    public String getFork() {
        return fork;
    }

    /**
     * @param fork
     */
    public void setFork(String fork) {
        this.fork = fork;
    }

    /**
     * @return ADDRESS
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
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
     * @return F_CHECK
     */
    public Date getfCheck() {
        return fCheck;
    }

    /**
     * @param fCheck
     */
    public void setfCheck(Date fCheck) {
        this.fCheck = fCheck;
    }

    /**
     * @return L_CHECK
     */
    public Date getlCheck() {
        return lCheck;
    }

    /**
     * @param lCheck
     */
    public void setlCheck(Date lCheck) {
        this.lCheck = lCheck;
    }

    /**
     * @return BIRTHDAY
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * @return SECURITYID
     */
    public String getSecurityid() {
        return securityid;
    }

    /**
     * @param securityid
     */
    public void setSecurityid(String securityid) {
        this.securityid = securityid;
    }

    /**
     * @return AJCOM
     */
    public String getAjcom() {
        return ajcom;
    }

    /**
     * @param ajcom
     */
    public void setAjcom(String ajcom) {
        this.ajcom = ajcom;
    }

    /**
     * @return W_TIME
     */
    public Date getwTime() {
        return wTime;
    }

    /**
     * @param wTime
     */
    public void setwTime(Date wTime) {
        this.wTime = wTime;
    }

    /**
     *
     * @return NATIVE_PLACE
     */
    public String getNativePlace() {
        return nativePlace;
    }

    /**
     *
     * @param nativePlace
     */
    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    /**
     *
     * @return ROLE
     */
    public String getRole() {
        return role;
    }

    /**
     *
     * @param role
     */
    public void setRole(String role) {
        this.role = role;
    }


    /**
     *
     * @return DEGREE
     */
    public String getDegree() {
        return degree;
    }

    /**
     *
     * @param degree
     */
    public void setDegree(String degree) {
        this.degree = degree;
    }

    /**
     *
     * @return SCHOOL
     */
    public String getSchool() {
        return school;
    }

    /**
     *
     * @param school
     */
    public void setSchool(String school) {
        this.school = school;
    }

    /**
     *
     * @return POLITICAL
     */
    public String getPolitical() {
        return political;
    }

    /**
     *
     * @param political
     */
    public void setPolitical(String political) {
        this.political = political;
    }

    /**
     *
     * @return TEAM
     */
    public String getTeam() {
        return team;
    }

    /**
     *
     * @param team
     */
    public void setTeam(String team) {
        this.team = team;
    }

    /**
     *
     * @return DEPART_ID
     */
    public String getDepartId() {
        return departId;
    }

    /**
     *
     * @param departId
     */
    public void setDepartId(String departId) {
        this.departId = departId;
    }

    /**
     *
     * @return ID
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBlackFlag() {
        return blackFlag;
    }

    public void setBlackFlag(Integer blackFlag) {
        this.blackFlag = blackFlag;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public String getCrtUserId() {
        return crtUserId;
    }

    public void setCrtUserId(String crtUserId) {
        this.crtUserId = crtUserId;
    }

    public String getCrtUserName() {
        return crtUserName;
    }

    public void setCrtUserName(String crtUserName) {
        this.crtUserName = crtUserName;
    }

    public Date getUpdTime() {
        return updTime;
    }

    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }

    public String getUpdUserId() {
        return updUserId;
    }

    public void setUpdUserId(String updUserId) {
        this.updUserId = updUserId;
    }

    public String getUpdUserName() {
        return updUserName;
    }

    public void setUpdUserName(String updUserName) {
        this.updUserName = updUserName;
    }


    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

}