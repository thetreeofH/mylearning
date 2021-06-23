package com.ts.spm.bizs.entity.matter;

import com.ts.spm.bizs.entity.Attachment;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Table(name = "SPM_OPER.TBL_DRILL_PLAN")
public class DrillPlan {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "PLAN_LEVEL")
    private String planLevel;

    @Column(name = "PLAN_MONTH")
    private String planMonth;

    @Column(name = "PLAN_START_TIME")
    private Date planStartTime;

    @Column(name = "PLAN_END_TIME")
    private Date planEndTime;

    @Column(name = "REAL_START_TIME")
    private Date realStartTime;

    @Column(name = "REAL_END_TIME")
    private Date realEndTime;

    @Column(name = "PLAN_PERSON_NUM")
    private Integer planPersonNum;

    @Column(name = "REAL_PERSON_NUM")
    private Integer realPersonNum;

    @Column(name = "STATE")
    private Integer state;

    @Column(name = "VERIFY")
    private Integer verify;

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

    @Column(name = "CRT_DEPT_ID")
    private String crtDeptId;

    @Column(name = "UPD_DEPT_ID")
    private String updDeptId;

    @Column(name = "TENANT_ID")
    private String tenantId;

    @Column(name = "DEPART_ID")
    private String departId;

    @Column(name = "DEPART")
    private String depart;

    @Column(name = "SCORE")
    private String score;

//    @Transient
//    private List<DrillPlanAction> actions;
    @Transient
    private List<Attachment> drillAttach;
    @Transient
    private List<Attachment> assessAttach;

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
     * @return CONTENT
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return PLAN_LEVEL
     */
    public String getPlanLevel() {
        return planLevel;
    }

    /**
     * @param planLevel
     */
    public void setPlanLevel(String planLevel) {
        this.planLevel = planLevel;
    }

    /**
     * @return PLAN_MONTH
     */
    public String getPlanMonth() {
        return planMonth;
    }

    /**
     * @param planMonth
     */
    public void setPlanMonth(String planMonth) {
        this.planMonth = planMonth;
    }

    /**
     * @return PLAN_START_TIME
     */
    public Date getPlanStartTime() {
        return planStartTime;
    }

    /**
     * @param planStartTime
     */
    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }

    /**
     * @return PLAN_END_TIME
     */
    public Date getPlanEndTime() {
        return planEndTime;
    }

    /**
     * @param planEndTime
     */
    public void setPlanEndTime(Date planEndTime) {
        this.planEndTime = planEndTime;
    }

    /**
     * @return REAL_START_TIME
     */
    public Date getRealStartTime() {
        return realStartTime;
    }

    /**
     * @param realStartTime
     */
    public void setRealStartTime(Date realStartTime) {
        this.realStartTime = realStartTime;
    }

    /**
     * @return REAL_END_TIME
     */
    public Date getRealEndTime() {
        return realEndTime;
    }

    /**
     * @param realEndTime
     */
    public void setRealEndTime(Date realEndTime) {
        this.realEndTime = realEndTime;
    }

    /**
     * @return PLAN_PERSON_NUM
     */
    public Integer getPlanPersonNum() {
        return planPersonNum;
    }

    /**
     * @param planPersonNum
     */
    public void setPlanPersonNum(Integer planPersonNum) {
        this.planPersonNum = planPersonNum;
    }

    /**
     * @return REAL_PERSON_NUM
     */
    public Integer getRealPersonNum() {
        return realPersonNum;
    }

    /**
     * @param realPersonNum
     */
    public void setRealPersonNum(Integer realPersonNum) {
        this.realPersonNum = realPersonNum;
    }

    /**
     * @return STATE
     */
    public Integer getState() {
        return state;
    }

    /**
     * @param state
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * @return VERIFY
     */
    public Integer getVerify() {
        return verify;
    }

    /**
     * @param verify
     */
    public void setVerify(Integer verify) {
        this.verify = verify;
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
     * @return CRT_DEPT_ID
     */
    public String getCrtDeptId() {
        return crtDeptId;
    }

    /**
     * @param crtDeptId
     */
    public void setCrtDeptId(String crtDeptId) {
        this.crtDeptId = crtDeptId;
    }

    /**
     * @return UPD_DEPT_ID
     */
    public String getUpdDeptId() {
        return updDeptId;
    }

    /**
     * @param updDeptId
     */
    public void setUpdDeptId(String updDeptId) {
        this.updDeptId = updDeptId;
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

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

//    public List<DrillPlanAction> getActions() {
//        return actions;
//    }
//
//    public void setActions(List<DrillPlanAction> actions) {
//        this.actions = actions;
//    }

    public List<Attachment> getDrillAttach() {
        return drillAttach;
    }

    public void setDrillAttach(List<Attachment> drillAttach) {
        this.drillAttach = drillAttach;
    }

    public List<Attachment> getAssessAttach() {
        return assessAttach;
    }

    public void setAssessAttach(List<Attachment> assessAttach) {
        this.assessAttach = assessAttach;
    }
}