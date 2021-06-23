package com.ts.spm.bizs.entity.jzpftgmnt;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * @ClassName OnDutyManAttend
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/10/9 19:28
 * @Version 1.0
 **/
public class OnDutyManAttend {

    private String id;


    private String scheduleId;


    private String userId;


    private String dutyDate;


    private Date stime;


    private Date etime;


    private Integer state;


    private Date crtTime;


    private String memo;


    private Integer userType;

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取主键
     *
     * @return ID - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取班次ID
     *
     * @return SCHEDULE_ID - 班次ID
     */
    public String getScheduleId() {
        return scheduleId;
    }

    /**
     * 设置班次ID
     *
     * @param scheduleId 班次ID
     */
    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    /**
     * 获取用户ID
     *
     * @return USER_ID - 用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取排班所属日期
     *
     * @return DUTY_DATE - 排班所属日期
     */
    public String getDutyDate() {
        return dutyDate;
    }

    /**
     * 设置排班所属日期
     *
     * @param dutyDate 排班所属日期
     */
    public void setDutyDate(String dutyDate) {
        this.dutyDate = dutyDate;
    }

    /**
     * 获取上班打卡时间
     *
     * @return STIME - 上班打卡时间
     */
    public Date getStime() {
        return stime;
    }

    /**
     * 设置上班打卡时间
     *
     * @param stime 上班打卡时间
     */
    public void setStime(Date stime) {
        this.stime = stime;
    }

    /**
     * 获取打卡状态（1上班打卡、2下班打卡）
     *
     * @return ETIME - 打卡状态（1上班打卡、2下班打卡）
     */
    public Date getEtime() {
        return etime;
    }

    /**
     * 设置打卡状态（1上班打卡、2下班打卡）
     *
     * @param etime 打卡状态（1上班打卡、2下班打卡）
     */
    public void setEtime(Date etime) {
        this.etime = etime;
    }

    /**
     * 获取打卡状态（1上班打卡、2下班打卡）
     *
     * @return STATE - 打卡状态（1上班打卡、2下班打卡）
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置打卡状态（1上班打卡、2下班打卡）
     *
     * @param state 打卡状态（1上班打卡、2下班打卡）
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取插入时间
     *
     * @return CRT_TIME - 插入时间
     */
    public Date getCrtTime() {
        return crtTime;
    }

    /**
     * 设置插入时间
     *
     * @param crtTime 插入时间
     */
    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    /**
     * 获取交接班留言
     *
     * @return MEMO - 交接班留言
     */
    public String getMemo() {
        return memo;
    }

    /**
     * 设置交接班留言
     *
     * @param memo 交接班留言
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * 获取用户类型 1-值机员 2-安检员
     *
     * @return USER_TYPE - 用户类型 1-值机员 2-安检员
     */
    public Integer getUserType() {
        return userType;
    }

    /**
     * 设置用户类型 1-值机员 2-安检员
     *
     * @param userType 用户类型 1-值机员 2-安检员
     */
    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}
