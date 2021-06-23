package com.ts.spm.bizs.entity.jzpftgmnt;

import java.util.Date;
import javax.persistence.*;

@Table(name = "jzp_oper.tbl_test_mission_on_duty_check")
public class TblTestMissionOnDutyCheck {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 检测点
     */
    @Column(name = "point_id")
    private String pointId;

    /**
     * 判图员ID
     */
    @Column(name = "judge_picture_user_id")
    private String judgePictureUserId;

    /**
     * 判图员姓名
     */
    @Column(name = "judge_picture_user_name")
    private String judgePictureUserName;

    /**
     * 测试任务ID
     */
    @Column(name = "test_mission_id")
    private String testMissionId;

    /**
     * 测试任务名称
     */
    @Column(name = "test_mission_name")
    private String testMissionName;

    /**
     * 疑似违禁品主类型
     */
    @Column(name = "suspected_forbidden_type")
    private Integer suspectedForbiddenType;

    /**
     * 疑似违禁品子类型
     */
    @Column(name = "suspected_forbidden_subtype")
    private Integer suspectedForbiddenSubtype;

    /**
     * 是否识别
     */
    @Column(name = "if_distinguish")
    private Integer ifDistinguish;

    /**
     * X光机图片地址
     */
    @Column(name = "x_ray_machine_pictures")
    private String xRayMachinePictures;

    /**
     * 自然光图片地址
     */
    @Column(name = "nature_light_pictures")
    private String natureLightPictures;

    /**
     * 测试时间
     */
    @Column(name = "test_time")
    private Date testTime;

    /**
     * 插入时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 备注
     */
    private String memo;

    /**
     * 获取主键
     *
     * @return id - 主键
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
     * 获取检测点
     *
     * @return point_id - 检测点
     */
    public String getPointId() {
        return pointId;
    }

    /**
     * 设置检测点
     *
     * @param pointId 检测点
     */
    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    /**
     * 获取判图员ID
     *
     * @return judge_picture_user_id - 判图员ID
     */
    public String getJudgePictureUserId() {
        return judgePictureUserId;
    }

    /**
     * 设置判图员ID
     *
     * @param judgePictureUserId 判图员ID
     */
    public void setJudgePictureUserId(String judgePictureUserId) {
        this.judgePictureUserId = judgePictureUserId;
    }

    /**
     * 获取判图员姓名
     *
     * @return judge_picture_user_name - 判图员姓名
     */
    public String getJudgePictureUserName() {
        return judgePictureUserName;
    }

    /**
     * 设置判图员姓名
     *
     * @param judgePictureUserName 判图员姓名
     */
    public void setJudgePictureUserName(String judgePictureUserName) {
        this.judgePictureUserName = judgePictureUserName;
    }

    /**
     * 获取测试任务ID
     *
     * @return test_mission_id - 测试任务ID
     */
    public String getTestMissionId() {
        return testMissionId;
    }

    /**
     * 设置测试任务ID
     *
     * @param testMissionId 测试任务ID
     */
    public void setTestMissionId(String testMissionId) {
        this.testMissionId = testMissionId;
    }

    /**
     * 获取测试任务名称
     *
     * @return test_mission_name - 测试任务名称
     */
    public String getTestMissionName() {
        return testMissionName;
    }

    /**
     * 设置测试任务名称
     *
     * @param testMissionName 测试任务名称
     */
    public void setTestMissionName(String testMissionName) {
        this.testMissionName = testMissionName;
    }

    /**
     * 获取疑似违禁品主类型
     *
     * @return suspected_forbidden_type - 疑似违禁品主类型
     */
    public Integer getSuspectedForbiddenType() {
        return suspectedForbiddenType;
    }

    /**
     * 设置疑似违禁品主类型
     *
     * @param suspectedForbiddenType 疑似违禁品主类型
     */
    public void setSuspectedForbiddenType(Integer suspectedForbiddenType) {
        this.suspectedForbiddenType = suspectedForbiddenType;
    }

    /**
     * 获取疑似违禁品子类型
     *
     * @return suspected_forbidden_subtype - 疑似违禁品子类型
     */
    public Integer getSuspectedForbiddenSubtype() {
        return suspectedForbiddenSubtype;
    }

    /**
     * 设置疑似违禁品子类型
     *
     * @param suspectedForbiddenSubtype 疑似违禁品子类型
     */
    public void setSuspectedForbiddenSubtype(Integer suspectedForbiddenSubtype) {
        this.suspectedForbiddenSubtype = suspectedForbiddenSubtype;
    }

    /**
     * 获取是否识别
     *
     * @return if_distinguish - 是否识别
     */
    public Integer getIfDistinguish() {
        return ifDistinguish;
    }

    /**
     * 设置是否识别
     *
     * @param ifDistinguish 是否识别
     */
    public void setIfDistinguish(Integer ifDistinguish) {
        this.ifDistinguish = ifDistinguish;
    }

    /**
     * 获取X光机图片地址
     *
     * @return x_ray_machine_pictures - X光机图片地址
     */
    public String getxRayMachinePictures() {
        return xRayMachinePictures;
    }

    /**
     * 设置X光机图片地址
     *
     * @param xRayMachinePictures X光机图片地址
     */
    public void setxRayMachinePictures(String xRayMachinePictures) {
        this.xRayMachinePictures = xRayMachinePictures;
    }

    /**
     * 获取自然光图片地址
     *
     * @return nature_light_pictures - 自然光图片地址
     */
    public String getNatureLightPictures() {
        return natureLightPictures;
    }

    /**
     * 设置自然光图片地址
     *
     * @param natureLightPictures 自然光图片地址
     */
    public void setNatureLightPictures(String natureLightPictures) {
        this.natureLightPictures = natureLightPictures;
    }

    /**
     * 获取测试时间
     *
     * @return test_time - 测试时间
     */
    public Date getTestTime() {
        return testTime;
    }

    /**
     * 设置测试时间
     *
     * @param testTime 测试时间
     */
    public void setTestTime(Date testTime) {
        this.testTime = testTime;
    }

    /**
     * 获取插入时间
     *
     * @return create_time - 插入时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置插入时间
     *
     * @param createTime 插入时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取备注
     *
     * @return memo - 备注
     */
    public String getMemo() {
        return memo;
    }

    /**
     * 设置备注
     *
     * @param memo 备注
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }
}