package com.ts.spm.bizs.entity.jzpitgcfg;

import com.ts.spm.bizs.vo.jzpitgcfg.ForbiddenTree;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @ClassName IntelligentJudgePictureCfg
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/8/25 11:56
 * @Version 1.0
 **/
public class IntelligentJudgePictureCfg {
    private String id;


    private String pointId;


    private String pointName;


    private String stationId;


    private String stationName;


    private BigDecimal ifIntelligentJudgePicture;


    private String personReviewProportion;


    List<TblTimePeriodCfg> timePeriodList;//高峰时段配置列表

    List<ForbiddenTree> list;//智能开检策略列表树

    public IntelligentJudgePictureCfg(String id, String pointId,String pointName,String stationId,String stationName,BigDecimal ifIntelligentJudgePicture,String personReviewProportion,List<TblTimePeriodCfg> timePeriodList,List<ForbiddenTree> list){
        this.id = id;
        this.pointId = pointId;
        this.pointName = pointName;
        this.stationId = stationId;
        this.stationName = stationName;
        this.ifIntelligentJudgePicture = ifIntelligentJudgePicture;
        this.personReviewProportion = personReviewProportion;
        this.timePeriodList = timePeriodList;
        this.list = list;
    }
    public List<ForbiddenTree> getList() {
        return list;
    }

    public void setList(List<ForbiddenTree> list) {
        this.list = list;
    }

    public void setTimePeriodList(List<TblTimePeriodCfg> timePeriodList) {
        this.timePeriodList = timePeriodList;
    }

    public List<TblTimePeriodCfg> getTimePeriodList() {
        return timePeriodList;
    }

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

}
