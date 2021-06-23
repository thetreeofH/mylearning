package com.ts.spm.bizs.entity.jzpitgcfg;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "jzp_oper.tbl_normal_conf_info")
public class TblNormalConfInfo {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 安检点ID
     */
    @Column(name = "point_id")
    private String pointId;

    /**
     * 时间段
     */
    @Column(name = "times")
    private String times;

    /**
     * 优先级 0：常态 1：临时
     */
    @Column(name = "level_flag")
    private Integer levelFlag;

    /**
     * 状态标志 1：生效 ;0：不生效
     */
    @Column(name = "state_flag")
    private Integer stateFlag;

    /**
     * 安检点名称
     */
    @Column(name = "point_name")
    private String pointName;

    /**
     * 机构ID
     */
    @Column(name = "depart_id")
    private String departId;

    /**
     * 机构名称
     */
    @Column(name = "depart_name")
    private String departName;

    /**
     * 标签 0：常态配置 1：临时配置
     */
    private Integer tag;

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
     * 描述
     */
    private String describes;

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
     * 获取安检点ID
     *
     * @return point_id - 安检点ID
     */
    public String getPointId() {
        return pointId;
    }

    /**
     * 设置安检点ID
     *
     * @param pointId 安检点ID
     */
    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    /**
     * 获取优先级 0：常态 1：临时
     *
     * @return level_flag - 优先级 0：常态 1：临时
     */
    public Integer getLevelFlag() {
        return levelFlag;
    }

    /**
     * 设置优先级 0：常态 1：临时
     *
     * @param levelFlag 优先级 0：常态 1：临时
     */
    public void setLevelFlag(Integer levelFlag) {
        this.levelFlag = levelFlag;
    }

    /**
     * 获取状态标志 1：生效 ;0：不生效
     *
     * @return state_flag - 状态标志 1：生效 ;0：不生效
     */
    public Integer getStateFlag() {
        return stateFlag;
    }

    /**
     * 设置状态标志 1：生效 ;0：不生效
     *
     * @param stateFlag 状态标志 1：生效 ;0：不生效
     */
    public void setStateFlag(Integer stateFlag) {
        this.stateFlag = stateFlag;
    }

    /**
     * 获取安检点名称
     *
     * @return point_name - 安检点名称
     */
    public String getPointName() {
        return pointName;
    }

    /**
     * 设置安检点名称
     *
     * @param pointName 安检点名称
     */
    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    /**
     * 获取机构ID
     *
     * @return depart_id - 机构ID
     */
    public String getDepartId() {
        return departId;
    }

    /**
     * 设置机构ID
     *
     * @param departId 机构ID
     */
    public void setDepartId(String departId) {
        this.departId = departId;
    }

    /**
     * 获取机构名称
     *
     * @return depart_name - 机构名称
     */
    public String getDepartName() {
        return departName;
    }

    /**
     * 设置机构名称
     *
     * @param departName 机构名称
     */
    public void setDepartName(String departName) {
        this.departName = departName;
    }

    /**
     * 获取标签 0：常态配置 1：临时配置
     *
     * @return tag - 标签 0：常态配置 1：临时配置
     */
    public Integer getTag() {
        return tag;
    }

    /**
     * 设置标签 0：常态配置 1：临时配置
     *
     * @param tag 标签 0：常态配置 1：临时配置
     */
    public void setTag(Integer tag) {
        this.tag = tag;
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
     * 获取描述
     *
     * @return describes - 描述
     */
    public String getDescribes() {
        return describes;
    }

    /**
     * 设置描述
     *
     * @param describes 描述
     */
    public void setDescribes(String describes) {
        this.describes = describes;
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