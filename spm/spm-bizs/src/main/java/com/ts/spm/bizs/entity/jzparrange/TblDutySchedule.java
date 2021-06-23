package com.ts.spm.bizs.entity.jzparrange;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Table(name = "jzp_oper.TBL_DUTY_SCHEDULE")
public class TblDutySchedule {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_DUTY_SCHEDULE.ID
     *
     * @mbg.generated
     */
    @Id
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_DUTY_SCHEDULE.STIME
     *
     * @mbg.generated
     */
    @Column(name = "STIME")
    private Date stime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_DUTY_SCHEDULE.ETIME
     *
     * @mbg.generated
     */
    @Column(name = "ETIME")
    private Date etime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_DUTY_SCHEDULE.BG
     *
     * @mbg.generated
     */
    @Column(name = "BG")
    private String bg;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_DUTY_SCHEDULE.DEPART_ID
     *
     * @mbg.generated
     */
    @Column(name = "DEPART_ID")
    private String departId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_DUTY_SCHEDULE.CRT_TIME
     *
     * @mbg.generated
     */
    @Column(name = "CRT_TIME")
    private Date crtTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_DUTY_SCHEDULE.CRT_USER_ID
     *
     * @mbg.generated
     */
    @Column(name = "CRT_USER_ID")
    private String crtUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_DUTY_SCHEDULE.CRT_USER_NAME
     *
     * @mbg.generated
     */
    @Column(name = "CRT_USER_NAME")
    private String crtUserName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_DUTY_SCHEDULE.UDP_TIME
     *
     * @mbg.generated
     */
    @Column(name = "UDP_TIME")
    private Date udpTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_DUTY_SCHEDULE.UDP_USER_ID
     *
     * @mbg.generated
     */
    @Column(name = "UDP_USER_ID")
    private String udpUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_DUTY_SCHEDULE.UDP_USER_NAME
     *
     * @mbg.generated
     */
    @Column(name = "UDP_USER_NAME")
    private String udpUserName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_DUTY_SCHEDULE.DEL_TIME
     *
     * @mbg.generated
     */
    @Column(name = "DEL_TIME")
    private Date delTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_DUTY_SCHEDULE.DEL_USER_ID
     *
     * @mbg.generated
     */
    @Column(name = "DEL_USER_ID")
    private String delUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_DUTY_SCHEDULE.DEL_USER_NAME
     *
     * @mbg.generated
     */
    @Column(name = "DEL_USER_NAME")
    private String delUserName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_DUTY_SCHEDULE.DEL_FLAG
     *
     * @mbg.generated
     */
    @Column(name = "DEL_FLAG")
    private Short delFlag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_DUTY_SCHEDULE.TENANT_ID
     *
     * @mbg.generated
     */
    @Column(name = "TENANT_ID")
    private String tenantId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_DUTY_SCHEDULE.MEMO
     *
     * @mbg.generated
     */
    @Column(name = "MEMO")
    private String memo;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_DUTY_SCHEDULE
     *
     * @mbg.generated
     */
    public TblDutySchedule(String id, Date stime, Date etime, String bg, String departId, Date crtTime, String crtUserId, String crtUserName, Date udpTime, String udpUserId, String udpUserName, Date delTime, String delUserId, String delUserName, Short delFlag, String tenantId, String memo) {
        this.id = id;
        this.stime = stime;
        this.etime = etime;
        this.bg = bg;
        this.departId = departId;
        this.crtTime = crtTime;
        this.crtUserId = crtUserId;
        this.crtUserName = crtUserName;
        this.udpTime = udpTime;
        this.udpUserId = udpUserId;
        this.udpUserName = udpUserName;
        this.delTime = delTime;
        this.delUserId = delUserId;
        this.delUserName = delUserName;
        this.delFlag = delFlag;
        this.tenantId = tenantId;
        this.memo = memo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_DUTY_SCHEDULE
     *
     * @mbg.generated
     */
    public TblDutySchedule() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_DUTY_SCHEDULE.ID
     *
     * @return the value of TBL_DUTY_SCHEDULE.ID
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_DUTY_SCHEDULE.ID
     *
     * @param id the value for TBL_DUTY_SCHEDULE.ID
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_DUTY_SCHEDULE.STIME
     *
     * @return the value of TBL_DUTY_SCHEDULE.STIME
     *
     * @mbg.generated
     */
    public Date getStime() {
        return stime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_DUTY_SCHEDULE.STIME
     *
     * @param stime the value for TBL_DUTY_SCHEDULE.STIME
     *
     * @mbg.generated
     */
    public void setStime(Date stime) {
        this.stime = stime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_DUTY_SCHEDULE.ETIME
     *
     * @return the value of TBL_DUTY_SCHEDULE.ETIME
     *
     * @mbg.generated
     */
    public Date getEtime() {
        return etime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_DUTY_SCHEDULE.ETIME
     *
     * @param etime the value for TBL_DUTY_SCHEDULE.ETIME
     *
     * @mbg.generated
     */
    public void setEtime(Date etime) {
        this.etime = etime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_DUTY_SCHEDULE.BG
     *
     * @return the value of TBL_DUTY_SCHEDULE.BG
     *
     * @mbg.generated
     */
    public String getBg() {
        return bg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_DUTY_SCHEDULE.BG
     *
     * @param bg the value for TBL_DUTY_SCHEDULE.BG
     *
     * @mbg.generated
     */
    public void setBg(String bg) {
        this.bg = bg == null ? null : bg.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_DUTY_SCHEDULE.DEPART_ID
     *
     * @return the value of TBL_DUTY_SCHEDULE.DEPART_ID
     *
     * @mbg.generated
     */
    public String getDepartId() {
        return departId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_DUTY_SCHEDULE.DEPART_ID
     *
     * @param departId the value for TBL_DUTY_SCHEDULE.DEPART_ID
     *
     * @mbg.generated
     */
    public void setDepartId(String departId) {
        this.departId = departId == null ? null : departId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_DUTY_SCHEDULE.CRT_TIME
     *
     * @return the value of TBL_DUTY_SCHEDULE.CRT_TIME
     *
     * @mbg.generated
     */
    public Date getCrtTime() {
        return crtTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_DUTY_SCHEDULE.CRT_TIME
     *
     * @param crtTime the value for TBL_DUTY_SCHEDULE.CRT_TIME
     *
     * @mbg.generated
     */
    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_DUTY_SCHEDULE.CRT_USER_ID
     *
     * @return the value of TBL_DUTY_SCHEDULE.CRT_USER_ID
     *
     * @mbg.generated
     */
    public String getCrtUserId() {
        return crtUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_DUTY_SCHEDULE.CRT_USER_ID
     *
     * @param crtUserId the value for TBL_DUTY_SCHEDULE.CRT_USER_ID
     *
     * @mbg.generated
     */
    public void setCrtUserId(String crtUserId) {
        this.crtUserId = crtUserId == null ? null : crtUserId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_DUTY_SCHEDULE.CRT_USER_NAME
     *
     * @return the value of TBL_DUTY_SCHEDULE.CRT_USER_NAME
     *
     * @mbg.generated
     */
    public String getCrtUserName() {
        return crtUserName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_DUTY_SCHEDULE.CRT_USER_NAME
     *
     * @param crtUserName the value for TBL_DUTY_SCHEDULE.CRT_USER_NAME
     *
     * @mbg.generated
     */
    public void setCrtUserName(String crtUserName) {
        this.crtUserName = crtUserName == null ? null : crtUserName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_DUTY_SCHEDULE.UDP_TIME
     *
     * @return the value of TBL_DUTY_SCHEDULE.UDP_TIME
     *
     * @mbg.generated
     */
    public Date getUdpTime() {
        return udpTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_DUTY_SCHEDULE.UDP_TIME
     *
     * @param udpTime the value for TBL_DUTY_SCHEDULE.UDP_TIME
     *
     * @mbg.generated
     */
    public void setUdpTime(Date udpTime) {
        this.udpTime = udpTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_DUTY_SCHEDULE.UDP_USER_ID
     *
     * @return the value of TBL_DUTY_SCHEDULE.UDP_USER_ID
     *
     * @mbg.generated
     */
    public String getUdpUserId() {
        return udpUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_DUTY_SCHEDULE.UDP_USER_ID
     *
     * @param udpUserId the value for TBL_DUTY_SCHEDULE.UDP_USER_ID
     *
     * @mbg.generated
     */
    public void setUdpUserId(String udpUserId) {
        this.udpUserId = udpUserId == null ? null : udpUserId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_DUTY_SCHEDULE.UDP_USER_NAME
     *
     * @return the value of TBL_DUTY_SCHEDULE.UDP_USER_NAME
     *
     * @mbg.generated
     */
    public String getUdpUserName() {
        return udpUserName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_DUTY_SCHEDULE.UDP_USER_NAME
     *
     * @param udpUserName the value for TBL_DUTY_SCHEDULE.UDP_USER_NAME
     *
     * @mbg.generated
     */
    public void setUdpUserName(String udpUserName) {
        this.udpUserName = udpUserName == null ? null : udpUserName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_DUTY_SCHEDULE.DEL_TIME
     *
     * @return the value of TBL_DUTY_SCHEDULE.DEL_TIME
     *
     * @mbg.generated
     */
    public Date getDelTime() {
        return delTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_DUTY_SCHEDULE.DEL_TIME
     *
     * @param delTime the value for TBL_DUTY_SCHEDULE.DEL_TIME
     *
     * @mbg.generated
     */
    public void setDelTime(Date delTime) {
        this.delTime = delTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_DUTY_SCHEDULE.DEL_USER_ID
     *
     * @return the value of TBL_DUTY_SCHEDULE.DEL_USER_ID
     *
     * @mbg.generated
     */
    public String getDelUserId() {
        return delUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_DUTY_SCHEDULE.DEL_USER_ID
     *
     * @param delUserId the value for TBL_DUTY_SCHEDULE.DEL_USER_ID
     *
     * @mbg.generated
     */
    public void setDelUserId(String delUserId) {
        this.delUserId = delUserId == null ? null : delUserId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_DUTY_SCHEDULE.DEL_USER_NAME
     *
     * @return the value of TBL_DUTY_SCHEDULE.DEL_USER_NAME
     *
     * @mbg.generated
     */
    public String getDelUserName() {
        return delUserName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_DUTY_SCHEDULE.DEL_USER_NAME
     *
     * @param delUserName the value for TBL_DUTY_SCHEDULE.DEL_USER_NAME
     *
     * @mbg.generated
     */
    public void setDelUserName(String delUserName) {
        this.delUserName = delUserName == null ? null : delUserName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_DUTY_SCHEDULE.DEL_FLAG
     *
     * @return the value of TBL_DUTY_SCHEDULE.DEL_FLAG
     *
     * @mbg.generated
     */
    public Short getDelFlag() {
        return delFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_DUTY_SCHEDULE.DEL_FLAG
     *
     * @param delFlag the value for TBL_DUTY_SCHEDULE.DEL_FLAG
     *
     * @mbg.generated
     */
    public void setDelFlag(Short delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_DUTY_SCHEDULE.TENANT_ID
     *
     * @return the value of TBL_DUTY_SCHEDULE.TENANT_ID
     *
     * @mbg.generated
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_DUTY_SCHEDULE.TENANT_ID
     *
     * @param tenantId the value for TBL_DUTY_SCHEDULE.TENANT_ID
     *
     * @mbg.generated
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId == null ? null : tenantId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_DUTY_SCHEDULE.MEMO
     *
     * @return the value of TBL_DUTY_SCHEDULE.MEMO
     *
     * @mbg.generated
     */
    public String getMemo() {
        return memo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_DUTY_SCHEDULE.MEMO
     *
     * @param memo the value for TBL_DUTY_SCHEDULE.MEMO
     *
     * @mbg.generated
     */
    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }
}