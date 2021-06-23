package com.ts.spm.bizs.entity.jzpitgcfg;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "jzp_oper.TBL_INTELLIGENT_CFG")
public class TblIntelligentCfg {
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

    @Column(name = "IF_INTELLIGENT_JUDGE_PICTURE")
    private BigDecimal ifIntelligentJudgePicture;

    @Column(name = "PERSON_REVIEW_PROPORTION")
    private String personReviewProportion;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

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
     * @return IF_INTELLIGENT_JUDGE_PICTURE
     */
    public BigDecimal getIfIntelligentJudgePicture() {
        return ifIntelligentJudgePicture;
    }

    /**
     * @param ifIntelligentJudgePicture
     */
    public void setIfIntelligentJudgePicture(BigDecimal ifIntelligentJudgePicture) {
        this.ifIntelligentJudgePicture = ifIntelligentJudgePicture;
    }

    /**
     * @return PERSON_REVIEW_PROPORTION
     */
    public String getPersonReviewProportion() {
        return personReviewProportion;
    }

    /**
     * @param personReviewProportion
     */
    public void setPersonReviewProportion(String personReviewProportion) {
        this.personReviewProportion = personReviewProportion;
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
}