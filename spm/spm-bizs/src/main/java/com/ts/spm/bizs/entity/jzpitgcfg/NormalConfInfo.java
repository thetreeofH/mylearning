package com.ts.spm.bizs.entity.jzpitgcfg;

import java.util.Date;
import java.util.List;

public class NormalConfInfo {
    /**
     * 主键
     */
    private String id;

    /**
     * 安检点ID
     */
    private String pointId;


    private List<TimeConf> times;

    /**
     * 优先级 0：常态 1：临时
     */
    private Integer levelFlag;

    /**
     * 状态标志 1：生效 ;0：不生效
     */
    private Integer stateFlag;

    /**
     * 安检点名称
     */
    private String pointName;

    /**
     * 机构ID
     */
    private String departId;

    /**
     * 机构名称
     */
    private String departName;

    /**
     * 标签 0：常态配置 1：临时配置
     */
    private Integer tag;

    /**
     * 插入时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 描述
     */
    private String describes;

    /**
     * 备注
     */
    private String memo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public List<TimeConf> getTimes() {
        return times;
    }

    public void setTimes(List<TimeConf> times) {
        this.times = times;
    }

    public Integer getLevelFlag() {
        return levelFlag;
    }

    public void setLevelFlag(Integer levelFlag) {
        this.levelFlag = levelFlag;
    }

    public Integer getStateFlag() {
        return stateFlag;
    }

    public void setStateFlag(Integer stateFlag) {
        this.stateFlag = stateFlag;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
