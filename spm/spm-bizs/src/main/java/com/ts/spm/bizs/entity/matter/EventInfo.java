package com.ts.spm.bizs.entity.matter;

import com.ts.spm.bizs.entity.Attachment;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "SPM_OPER.TBL_EVENT_INFO")
public class EventInfo {
    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TYPE")
    private Integer type;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "POINT_ID")
    private String pointId;

    @Column(name = "STATION_ID")
    private String stationId;

    @Column(name = "SOURCE")
    private String source;

    @Column(name = "EVT_TYPE_ID")
    private String evtTypeId;

    @Column(name = "EVT_TYPE")
    private String evtType;

    @Column(name = "EVT_LEVEL_ID")
    private String evtLevelId;

    @Column(name = "EVT_LEVEL")
    private String evtLevel;

    @Column(name = "FOUND_TIME")
    private Date foundTime;

    @Column(name = "CHECKER_NAME")
    private String checkerName;

    @Column(name = "CHECKER_RESULT")
    private Integer checkerResult;

    @Column(name = "DANGER_TYPE")
    private String dangerType;

    @Column(name = "DANGER_TYPE_ID")
    private String dangerTypeId;

    @Column(name = "DANGER_SUB")
    private String dangerSub;

    @Column(name = "DANGER_SUB_ID")
    private String dangerSubId;

    @Column(name = "DANGER_SUM")
    private Integer dangerSum;

    @Column(name = "DANGER_NOTE")
    private String dangerNote;

    @Column(name = "PASSANGER")
    private String passanger;

    @Column(name = "IDCARD")
    private String idcard;

    @Column(name = "DETENTIONDAY")
    private Integer detentionday;

    @Column(name = "REGISTER_ID")
    private String registerId;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "MEASURE")
    private String measure;

    @Column(name = "DEAL_RESULT")
    private Integer dealResult;

    @Column(name = "DEAL_TYPE")
    private String dealType;

    @Column(name = "XPIC_PATH")
    private String xpicPath;

    @Column(name = "PIC_PATH")
    private String picPath;

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

    @Column(name = "DEL_TIME")
    private Date delTime;

    @Column(name = "DEL_USER_ID")
    private String delUserId;

    @Column(name = "DEL_USER_NAME")
    private String delUserName;

    @Column(name = "DEL_FLAG")
    private Integer delFlag;

    @Column(name = "TENANT_ID")
    private String tenantId;

    @Column(name = "PROCESS_EVT_INFO")
    private String processEvtInfo;

    @Column(name = "DANGER_ID")
    private Integer dangerId;

    @Column(name = "XOPT_NAME")
    private String xoptName;

    @Column(name = "READED")
    private Integer readed;

    @Transient
    private String point;
    @Transient
    private String station;

    @Transient
    private PointDanger danger;
    @Transient
    private Integer inputType;
    @Transient
    private String mtype;
    @Transient
    private String stype;

    @Transient
    private List<Attachment> infoAttachs;

    @Transient
    private List<Attachment> dangerAttachs;

    @Transient
    private List<Attachment> processAttachs;

    @Transient
    private List<EventResubmit> resubmits;

    /**
     * @return ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return TYPE
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return TITLE
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
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
     * @return SOURCE
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return EVT_TYPE_ID
     */
    public String getEvtTypeId() {
        return evtTypeId;
    }

    /**
     * @param evtTypeId
     */
    public void setEvtTypeId(String evtTypeId) {
        this.evtTypeId = evtTypeId;
    }

    /**
     * @return EVT_TYPE
     */
    public String getEvtType() {
        return evtType;
    }

    /**
     * @param evtType
     */
    public void setEvtType(String evtType) {
        this.evtType = evtType;
    }

    /**
     * @return EVT_LEVEL_ID
     */
    public String getEvtLevelId() {
        return evtLevelId;
    }

    /**
     * @param evtLevelId
     */
    public void setEvtLevelId(String evtLevelId) {
        this.evtLevelId = evtLevelId;
    }

    /**
     * @return EVT_LEVEL
     */
    public String getEvtLevel() {
        return evtLevel;
    }

    /**
     * @param evtLevel
     */
    public void setEvtLevel(String evtLevel) {
        this.evtLevel = evtLevel;
    }

    /**
     * @return XOPT_NAME
     */
    public String getXoptName() {
        return xoptName;
    }

    /**
     * @param xoptName
     */
    public void setXoptName(String xoptName) {
        this.xoptName = xoptName;
    }

    /**
     * @return FOUND_TIME
     */
    public Date getFoundTime() {
        return foundTime;
    }

    /**
     * @param foundTime
     */
    public void setFoundTime(Date foundTime) {
        this.foundTime = foundTime;
    }

    /**
     * @return CHECKER_NAME
     */
    public String getCheckerName() {
        return checkerName;
    }

    /**
     * @param checkerName
     */
    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }

    /**
     * @return CHECKER_RESULT
     */
    public Integer getCheckerResult() {
        return checkerResult;
    }

    /**
     * @param checkerResult
     */
    public void setCheckerResult(Integer checkerResult) {
        this.checkerResult = checkerResult;
    }

    /**
     * @return DANGER_TYPE
     */
    public String getDangerType() {
        return dangerType;
    }

    /**
     * @param dangerType
     */
    public void setDangerType(String dangerType) {
        this.dangerType = dangerType;
    }

    /**
     * @return DANGER_TYPE_ID
     */
    public String getDangerTypeId() {
        return dangerTypeId;
    }

    /**
     * @param dangerTypeId
     */
    public void setDangerTypeId(String dangerTypeId) {
        this.dangerTypeId = dangerTypeId;
    }

    /**
     * @return DANGER_SUB
     */
    public String getDangerSub() {
        return dangerSub;
    }

    /**
     * @param dangerSub
     */
    public void setDangerSub(String dangerSub) {
        this.dangerSub = dangerSub;
    }

    /**
     * @return DANGER_SUB_ID
     */
    public String getDangerSubId() {
        return dangerSubId;
    }

    /**
     * @param dangerSubId
     */
    public void setDangerSubId(String dangerSubId) {
        this.dangerSubId = dangerSubId;
    }

    /**
     * @return DANGER_SUM
     */
    public Integer getDangerSum() {
        return dangerSum;
    }

    /**
     * @param dangerSum
     */
    public void setDangerSum(Integer dangerSum) {
        this.dangerSum = dangerSum;
    }

    /**
     * @return DANGER_NOTE
     */
    public String getDangerNote() {
        return dangerNote;
    }

    /**
     * @param dangerNote
     */
    public void setDangerNote(String dangerNote) {
        this.dangerNote = dangerNote;
    }

    /**
     * @return PASSANGER
     */
    public String getPassanger() {
        return passanger;
    }

    /**
     * @param passanger
     */
    public void setPassanger(String passanger) {
        this.passanger = passanger;
    }

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
     * @return DETENTIONDAY
     */
    public Integer getDetentionday() {
        return detentionday;
    }

    /**
     * @param detentionday
     */
    public void setDetentionday(Integer detentionday) {
        this.detentionday = detentionday;
    }

    /**
     * @return REGISTER_ID
     */
    public String getRegisterId() {
        return registerId;
    }

    /**
     * @param registerId
     */
    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    /**
     * @return REMARK
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return MEASURE
     */
    public String getMeasure() {
        return measure;
    }

    /**
     * @param measure
     */
    public void setMeasure(String measure) {
        this.measure = measure;
    }

    /**
     * @return DEAL_RESULT
     */
    public Integer getDealResult() {
        return dealResult;
    }

    /**
     * @param dealResult
     */
    public void setDealResult(Integer dealResult) {
        this.dealResult = dealResult;
    }

    /**
     * @return DEAL_TYPE
     */
    public String getDealType() {
        return dealType;
    }

    /**
     * @param dealType
     */
    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    /**
     * @return XPIC_PATH
     */
    public String getXpicPath() {
        return xpicPath;
    }

    /**
     * @param xpicPath
     */
    public void setXpicPath(String xpicPath) {
        this.xpicPath = xpicPath;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    /**
     * @return CRT_TIME
     */
    public Date getCrtTime() {
        return crtTime;
    }

    /**
     * @param crtTime
     */
    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    /**
     * @return CRT_USER_ID
     */
    public String getCrtUserId() {
        return crtUserId;
    }

    /**
     * @param crtUserId
     */
    public void setCrtUserId(String crtUserId) {
        this.crtUserId = crtUserId;
    }

    /**
     * @return CRT_USER_NAME
     */
    public String getCrtUserName() {
        return crtUserName;
    }

    /**
     * @param crtUserName
     */
    public void setCrtUserName(String crtUserName) {
        this.crtUserName = crtUserName;
    }

    /**
     * @return UPD_TIME
     */
    public Date getUpdTime() {
        return updTime;
    }

    /**
     * @param updTime
     */
    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }

    /**
     * @return UPD_USER_ID
     */
    public String getUpdUserId() {
        return updUserId;
    }

    /**
     * @param updUserId
     */
    public void setUpdUserId(String updUserId) {
        this.updUserId = updUserId;
    }

    /**
     * @return UPD_USER_NAME
     */
    public String getUpdUserName() {
        return updUserName;
    }

    /**
     * @param updUserName
     */
    public void setUpdUserName(String updUserName) {
        this.updUserName = updUserName;
    }

    /**
     * @return DEL_TIME
     */
    public Date getDelTime() {
        return delTime;
    }

    /**
     * @param delTime
     */
    public void setDelTime(Date delTime) {
        this.delTime = delTime;
    }

    /**
     * @return DEL_USER_ID
     */
    public String getDelUserId() {
        return delUserId;
    }

    /**
     * @param delUserId
     */
    public void setDelUserId(String delUserId) {
        this.delUserId = delUserId;
    }

    /**
     * @return DEL_USER_NAME
     */
    public String getDelUserName() {
        return delUserName;
    }

    /**
     * @param delUserName
     */
    public void setDelUserName(String delUserName) {
        this.delUserName = delUserName;
    }

    /**
     * @return DEL_FLAG
     */
    public Integer getDelFlag() {
        return delFlag;
    }

    /**
     * @param delFlag
     */
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * @return TENANT_ID
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * @param tenantId
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * @return PROCESS_EVT_INFO
     */
    public String getProcessEvtInfo() {
        return processEvtInfo;
    }

    /**
     * @param processEvtInfo
     */
    public void setProcessEvtInfo(String processEvtInfo) {
        this.processEvtInfo = processEvtInfo;
    }

    /**
     * @return DANGER_ID
     */
    public Integer getDangerId() {
        return dangerId;
    }

    /**
     * @param dangerId
     */
    public void setDangerId(Integer dangerId) {
        this.dangerId = dangerId;
    }

    public Integer getReaded() {
        return readed;
    }

    public void setReaded(Integer readed) {
        this.readed = readed;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public PointDanger getDanger() {
        return danger;
    }

    public void setDanger(PointDanger danger) {
        this.danger = danger;
    }

    public Integer getInputType() {
        return inputType;
    }

    public void setInputType(Integer inputType) {
        this.inputType = inputType;
    }

    public String getMtype() {
        return mtype;
    }

    public void setMtype(String mtype) {
        this.mtype = mtype;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public List<Attachment> getInfoAttachs() {
        return infoAttachs;
    }

    public void setInfoAttachs(List<Attachment> infoAttachs) {
        this.infoAttachs = infoAttachs;
    }

    public List<Attachment> getDangerAttachs() {
        return dangerAttachs;
    }

    public void setDangerAttachs(List<Attachment> dangerAttachs) {
        this.dangerAttachs = dangerAttachs;
    }

    public List<Attachment> getProcessAttachs() {
        return processAttachs;
    }

    public void setProcessAttachs(List<Attachment> processAttachs) {
        this.processAttachs = processAttachs;
    }

    public List<EventResubmit> getResubmits() {
        return resubmits;
    }

    public void setResubmits(List<EventResubmit> resubmits) {
        this.resubmits = resubmits;
    }
}