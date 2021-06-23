package com.ts.spm.bizs.entity.jzpitgcfg;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "jzp_oper.TBL_ITG_OPEN_INSPECTION_CFG")
public class TblItgOpenInspectionCfg {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "POINT_ID")
    private String pointId;

    @Column(name = "POINT_NAME")
    private String pointName;

    @Column(name = "STATION_ID")
    private String stationId;

    @Column(name = "STATION_NAME")
    private String stationName;

    @Column(name = "CODE")
    private BigDecimal code;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PARENT_ID")
    private String parentId;

    @Column(name = "TRESHOLD")
    private String treshold;

    @Column(name = "INTELLIGENT_ID")
    private String intelligentId;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "ORDER_NUM")
    private BigDecimal orderNum;

    @Column(name = "IS_DISABLED")
    private String isDisabled;

    @Column(name = "MEMO")
    private String memo;

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
     * @return POINT_ID
     */
    public String getPointId() {
        return pointId;
    }

    /**
     * @param pointId
     */
    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    /**
     * @return POINT_NAME
     */
    public String getPointName() {
        return pointName;
    }

    /**
     * @param pointName
     */
    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    /**
     * @return STATION_ID
     */
    public String getStationId() {
        return stationId;
    }

    /**
     * @param stationId
     */
    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    /**
     * @return STATION_NAME
     */
    public String getStationName() {
        return stationName;
    }

    /**
     * @param stationName
     */
    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    /**
     * @return CODE
     */
    public BigDecimal getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(BigDecimal code) {
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
     * @return PARENT_ID
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * @return TRESHOLD
     */
    public String getTreshold() {
        return treshold;
    }

    /**
     * @param treshold
     */
    public void setTreshold(String treshold) {
        this.treshold = treshold;
    }

    /**
     * @return INTELLIGENT_ID
     */
    public String getIntelligentId() {
        return intelligentId;
    }

    /**
     * @param intelligentId
     */
    public void setIntelligentId(String intelligentId) {
        this.intelligentId = intelligentId;
    }

    /**
     * @return CREATE_TIME
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return UPDATE_TIME
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
     * @return STATUS
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return ORDER_NUM
     */
    public BigDecimal getOrderNum() {
        return orderNum;
    }

    /**
     * @param orderNum
     */
    public void setOrderNum(BigDecimal orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * @return IS_DISABLED
     */
    public String getIsDisabled() {
        return isDisabled;
    }

    /**
     * @param isDisabled
     */
    public void setIsDisabled(String isDisabled) {
        this.isDisabled = isDisabled;
    }

    /**
     * @return MEMO
     */
    public String getMemo() {
        return memo;
    }

    /**
     * @param memo
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }
}