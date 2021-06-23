package com.ts.spm.bizs.entity.jzparrange;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Table(name = "jzp_oper.TBL_DUTY_PERSON_SCHEDULE")
public class TblDutyPersonSchedule {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_DUTY_PERSON_SCHEDULE.ID
     *
     * @mbg.generated
     */
    @Id
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_DUTY_PERSON_SCHEDULE.SCHEDULE_ID
     *
     * @mbg.generated
     */
    @Column(name = "SCHEDULE_ID")
    private String scheduleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_DUTY_PERSON_SCHEDULE.USER_ID
     *
     * @mbg.generated
     */
    @Column(name = "USER_ID")
    private String userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_DUTY_PERSON_SCHEDULE.CRT_TIME
     *
     * @mbg.generated
     */
    @Column(name = "CRT_TIME")
    private Date crtTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_DUTY_PERSON_SCHEDULE.CRT_USER_ID
     *
     * @mbg.generated
     */
    @Column(name = "CRT_USER_ID")
    private String crtUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_DUTY_PERSON_SCHEDULE.CRT_USER_NAME
     *
     * @mbg.generated
     */
    @Column(name = "CRT_USER_NAME")
    private String crtUserName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_DUTY_PERSON_SCHEDULE.DEL_TIME
     *
     * @mbg.generated
     */
    @Column(name = "DEL_TIME")
    private Date delTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_DUTY_PERSON_SCHEDULE.DEL_USER_ID
     *
     * @mbg.generated
     */
    @Column(name = "DEL_USER_ID")
    private String delUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_DUTY_PERSON_SCHEDULE.DEL_USER_NAME
     *
     * @mbg.generated
     */
    @Column(name = "DEL_USER_NAME")
    private String delUserName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_DUTY_PERSON_SCHEDULE.DEL_FLAG
     *
     * @mbg.generated
     */
    @Column(name = "DEL_FLAG")
    private Short delFlag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_DUTY_PERSON_SCHEDULE.MEMO
     *
     * @mbg.generated
     */
    @Column(name = "MEMO")
    private String memo;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_DUTY_PERSON_SCHEDULE
     *
     * @mbg.generated
     */
    public TblDutyPersonSchedule(String id, String scheduleId, String userId, Date crtTime, String crtUserId, String crtUserName, Date delTime, String delUserId, String delUserName, Short delFlag, String memo) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.userId = userId;
        this.crtTime = crtTime;
        this.crtUserId = crtUserId;
        this.crtUserName = crtUserName;
        this.delTime = delTime;
        this.delUserId = delUserId;
        this.delUserName = delUserName;
        this.delFlag = delFlag;
        this.memo = memo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_DUTY_PERSON_SCHEDULE
     *
     * @mbg.generated
     */
    public TblDutyPersonSchedule() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_DUTY_PERSON_SCHEDULE.ID
     *
     * @return the value of TBL_DUTY_PERSON_SCHEDULE.ID
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_DUTY_PERSON_SCHEDULE.ID
     *
     * @param id the value for TBL_DUTY_PERSON_SCHEDULE.ID
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_DUTY_PERSON_SCHEDULE.SCHEDULE_ID
     *
     * @return the value of TBL_DUTY_PERSON_SCHEDULE.SCHEDULE_ID
     *
     * @mbg.generated
     */
    public String getScheduleId() {
        return scheduleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_DUTY_PERSON_SCHEDULE.SCHEDULE_ID
     *
     * @param scheduleId the value for TBL_DUTY_PERSON_SCHEDULE.SCHEDULE_ID
     *
     * @mbg.generated
     */
    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId == null ? null : scheduleId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_DUTY_PERSON_SCHEDULE.USER_ID
     *
     * @return the value of TBL_DUTY_PERSON_SCHEDULE.USER_ID
     *
     * @mbg.generated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_DUTY_PERSON_SCHEDULE.USER_ID
     *
     * @param userId the value for TBL_DUTY_PERSON_SCHEDULE.USER_ID
     *
     * @mbg.generated
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_DUTY_PERSON_SCHEDULE.CRT_TIME
     *
     * @return the value of TBL_DUTY_PERSON_SCHEDULE.CRT_TIME
     *
     * @mbg.generated
     */
    public Date getCrtTime() {
        return crtTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_DUTY_PERSON_SCHEDULE.CRT_TIME
     *
     * @param crtTime the value for TBL_DUTY_PERSON_SCHEDULE.CRT_TIME
     *
     * @mbg.generated
     */
    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_DUTY_PERSON_SCHEDULE.CRT_USER_ID
     *
     * @return the value of TBL_DUTY_PERSON_SCHEDULE.CRT_USER_ID
     *
     * @mbg.generated
     */
    public String getCrtUserId() {
        return crtUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_DUTY_PERSON_SCHEDULE.CRT_USER_ID
     *
     * @param crtUserId the value for TBL_DUTY_PERSON_SCHEDULE.CRT_USER_ID
     *
     * @mbg.generated
     */
    public void setCrtUserId(String crtUserId) {
        this.crtUserId = crtUserId == null ? null : crtUserId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_DUTY_PERSON_SCHEDULE.CRT_USER_NAME
     *
     * @return the value of TBL_DUTY_PERSON_SCHEDULE.CRT_USER_NAME
     *
     * @mbg.generated
     */
    public String getCrtUserName() {
        return crtUserName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_DUTY_PERSON_SCHEDULE.CRT_USER_NAME
     *
     * @param crtUserName the value for TBL_DUTY_PERSON_SCHEDULE.CRT_USER_NAME
     *
     * @mbg.generated
     */
    public void setCrtUserName(String crtUserName) {
        this.crtUserName = crtUserName == null ? null : crtUserName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_DUTY_PERSON_SCHEDULE.DEL_TIME
     *
     * @return the value of TBL_DUTY_PERSON_SCHEDULE.DEL_TIME
     *
     * @mbg.generated
     */
    public Date getDelTime() {
        return delTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_DUTY_PERSON_SCHEDULE.DEL_TIME
     *
     * @param delTime the value for TBL_DUTY_PERSON_SCHEDULE.DEL_TIME
     *
     * @mbg.generated
     */
    public void setDelTime(Date delTime) {
        this.delTime = delTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_DUTY_PERSON_SCHEDULE.DEL_USER_ID
     *
     * @return the value of TBL_DUTY_PERSON_SCHEDULE.DEL_USER_ID
     *
     * @mbg.generated
     */
    public String getDelUserId() {
        return delUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_DUTY_PERSON_SCHEDULE.DEL_USER_ID
     *
     * @param delUserId the value for TBL_DUTY_PERSON_SCHEDULE.DEL_USER_ID
     *
     * @mbg.generated
     */
    public void setDelUserId(String delUserId) {
        this.delUserId = delUserId == null ? null : delUserId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_DUTY_PERSON_SCHEDULE.DEL_USER_NAME
     *
     * @return the value of TBL_DUTY_PERSON_SCHEDULE.DEL_USER_NAME
     *
     * @mbg.generated
     */
    public String getDelUserName() {
        return delUserName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_DUTY_PERSON_SCHEDULE.DEL_USER_NAME
     *
     * @param delUserName the value for TBL_DUTY_PERSON_SCHEDULE.DEL_USER_NAME
     *
     * @mbg.generated
     */
    public void setDelUserName(String delUserName) {
        this.delUserName = delUserName == null ? null : delUserName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_DUTY_PERSON_SCHEDULE.DEL_FLAG
     *
     * @return the value of TBL_DUTY_PERSON_SCHEDULE.DEL_FLAG
     *
     * @mbg.generated
     */
    public Short getDelFlag() {
        return delFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_DUTY_PERSON_SCHEDULE.DEL_FLAG
     *
     * @param delFlag the value for TBL_DUTY_PERSON_SCHEDULE.DEL_FLAG
     *
     * @mbg.generated
     */
    public void setDelFlag(Short delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_DUTY_PERSON_SCHEDULE.MEMO
     *
     * @return the value of TBL_DUTY_PERSON_SCHEDULE.MEMO
     *
     * @mbg.generated
     */
    public String getMemo() {
        return memo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_DUTY_PERSON_SCHEDULE.MEMO
     *
     * @param memo the value for TBL_DUTY_PERSON_SCHEDULE.MEMO
     *
     * @mbg.generated
     */
    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }
}