package com.ts.spm.bizs.entity.msg;

import javax.persistence.Transient;
import java.util.Date;
import java.util.List;


public class InnerCheck {

    private Integer checkId;

    private Date checkTime;

    private String pointId;

    private String checkOrg;

    private String checkName;

    private boolean hasProb;

    private Integer planCount;

    private Integer reanCount;

    private Integer cardCount;

    private boolean hasLackPerson;

    private String pointName;

    private String stationId;

    private int checkType;

    private Date crtTime;

    private String crtUserId;

    private String crtUserName;

    private Date updTime;

    private String updUserId;

    private String updUserName;

    private String tenantId;

    //运营公司0，安检公司1
    private Integer probSource;

    @Transient
    private String stationName;

    @Transient
    private List<InnerCheckProblem> tblInnerCheckProblemList;

    /**
     * @return CHECK_ID
     */
    public Integer getCheckId() {
        return checkId;
    }

    /**
     * @param checkId
     */
    public void setCheckId(Integer checkId) {
        this.checkId = checkId;
    }

    /**
     * @return CHECK_TIME
     */
    public Date getCheckTime() {
        return checkTime;
    }

    /**
     * @param checkTime
     */
    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
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
     * @return CHECK_ORG
     */
    public String getCheckOrg() {
        return checkOrg;
    }

    /**
     * @param checkOrg
     */
    public void setCheckOrg(String checkOrg) {
        this.checkOrg = checkOrg;
    }

    /**
     * @return CHECK_NAME
     */
    public String getCheckName() {
        return checkName;
    }

    /**
     * @param checkName
     */
    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    /**
     * @return HAS_PROB
     */
    public boolean getHasProb() {
        return hasProb;
    }

    /**
     * @param hasProb
     */
    public void setHasProb(boolean hasProb) {
        this.hasProb = hasProb;
    }

    /**
     * @return PLAN_COUNT
     */
    public Integer getPlanCount() {
        return planCount;
    }

    /**
     * @param planCount
     */
    public void setPlanCount(Integer planCount) {
        this.planCount = planCount;
    }

    /**
     * @return REAN_COUNT
     */
    public Integer getReanCount() {
        return reanCount;
    }

    /**
     * @param reanCount
     */
    public void setReanCount(Integer reanCount) {
        this.reanCount = reanCount;
    }

    /**
     * @return CARD_COUNT
     */
    public Integer getCardCount() {
        return cardCount;
    }

    /**
     * @param cardCount
     */
    public void setCardCount(Integer cardCount) {
        this.cardCount = cardCount;
    }

    /**
     * @return HAS_LACK_PERSON
     */
    public boolean getHasLackPerson() {
        return hasLackPerson;
    }

    /**
     * @param hasLackPerson
     */
    public void setHasLackPerson(boolean hasLackPerson) {
        this.hasLackPerson = hasLackPerson;
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
     * @return CHECK_TYPE
     */
    public int getCheckType() {
        return checkType;
    }

    /**
     * @param checkType
     */
    public void setCheckType(int checkType) {
        this.checkType = checkType;
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

    public Integer getProbSource() {
        return probSource;
    }

    public void setProbSource(Integer probSource) {
        this.probSource = probSource;
    }

    public List<InnerCheckProblem> getTblInnerCheckProblemList() {
        return tblInnerCheckProblemList;
    }

    public void setTblInnerCheckProblemList(List<InnerCheckProblem> tblInnerCheckProblemList) {
        this.tblInnerCheckProblemList = tblInnerCheckProblemList;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}