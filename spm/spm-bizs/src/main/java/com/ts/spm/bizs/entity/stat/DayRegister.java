package com.ts.spm.bizs.entity.stat;


import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "TBL_SUBWAY_DAY_REGISTER")
public class DayRegister {
    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "POINT_ID")
    private String pointId;

    @Transient
    private String pointName;

    @Column(name = "TIME")
    private Date time;

    //检查单位
    @Column(name = "CHECK_ORG")
    private String checkOrg;

    @Column(name = "CHECK_USER")
    private String checkUser;

    @Column(name = "OPERATING_COMPANY")
    private String operatingCompany;

    @Column(name = "SECURITY_COMPANY")
    private String securityCompany;

    //检查类型
    @Column(name = "INSPECTION_TYPE")
    private Integer inspectionType;

    @Column(name = "PROBLEM_DETAIL")
    private String problemDetail;

    @Column(name = "SIGN_PATH")
    private String signPath;

    @Column(name = "DEL_TAG")
    private Integer delTag;

    @Column(name = "DEL_USER_ID")
    private String delUserId;

    @Column(name = "DEL_TIME")
    private Date delTime;

    //运营企业值班站长签字
    @Column(name = "OPERAT_SIGN_PATH")
    private String operatSignPath;

    //安检公司安检点负责人签字
    @Column(name = "SECURITY_SIGN_PATH")
    private String securitySignPath;

    //运营值班站长
    @Column(name = "OPERAT_PERSON")
    private String operatPerson;

    //安检班长
    @Column(name = "SECURITY_PERSON")
    private String securityPerson;

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

    @Column(name = "DEL_USER_NAME")
    private String delUserName;

    @Column(name = "TENANT_ID")
    private String tenantId;

    //携带违禁品数量
    @Column(name = "INSERT_COUNT")
    private Integer insertCount;

    //查获违禁品数量
    @Column(name = "DETECT_COUNT")
    private Integer detectCount;

    //应在岗人数
    @Column(name = "PLAN_COUNT")
    private Integer planCount;

    //实际在岗人数
    @Column(name = "REAL_COUNT")
    private Integer realCount;

    //持证人数
    @Column(name = "CARD_COUNT")
    private Integer cardCount;

    @Column(name = "DEPART_ID")
    private String departId;

    @Transient
    private String departName;

    @Transient
    private List<DayProblem> dayProblemList;

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
     * @return TIME
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time
     */
    public void setTime(Date time) {
        this.time = time;
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
     * @return CHECK_USER
     */
    public String getCheckUser() {
        return checkUser;
    }

    /**
     * @param checkUser
     */
    public void setCheckUser(String checkUser) {
        this.checkUser = checkUser;
    }

    /**
     * @return OPERATING_COMPANY
     */
    public String getOperatingCompany() {
        return operatingCompany;
    }

    /**
     * @param operatingCompany
     */
    public void setOperatingCompany(String operatingCompany) {
        this.operatingCompany = operatingCompany;
    }

    /**
     * @return SECURITY_COMPANY
     */
    public String getSecurityCompany() {
        return securityCompany;
    }

    /**
     * @param securityCompany
     */
    public void setSecurityCompany(String securityCompany) {
        this.securityCompany = securityCompany;
    }

    /**
     * @return INSPECTION_TYPE
     */
    public Integer getInspectionType() {
        return inspectionType;
    }

    /**
     * @param inspectionType
     */
    public void setInspectionType(Integer inspectionType) {
        this.inspectionType = inspectionType;
    }

    /**
     * @return PROBLEM_DETAIL
     */
    public String getProblemDetail() {
        return problemDetail;
    }

    /**
     * @param problemDetail
     */
    public void setProblemDetail(String problemDetail) {
        this.problemDetail = problemDetail;
    }

    /**
     * @return SIGN_PATH
     */
    public String getSignPath() {
        return signPath;
    }

    /**
     * @param signPath
     */
    public void setSignPath(String signPath) {
        this.signPath = signPath;
    }

    /**
     * @return DEL_TAG
     */
    public Integer getDelTag() {
        return delTag;
    }

    /**
     * @param delTag
     */
    public void setDelTag(Integer delTag) {
        this.delTag = delTag;
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
     * @return OPERAT_SIGN_PATH
     */
    public String getOperatSignPath() {
        return operatSignPath;
    }

    /**
     * @param operatSignPath
     */
    public void setOperatSignPath(String operatSignPath) {
        this.operatSignPath = operatSignPath;
    }

    /**
     * @return SECURITY_SIGN_PATH
     */
    public String getSecuritySignPath() {
        return securitySignPath;
    }

    /**
     * @param securitySignPath
     */
    public void setSecuritySignPath(String securitySignPath) {
        this.securitySignPath = securitySignPath;
    }

    /**
     * @return OPERAT_PERSON
     */
    public String getOperatPerson() {
        return operatPerson;
    }

    /**
     * @param operatPerson
     */
    public void setOperatPerson(String operatPerson) {
        this.operatPerson = operatPerson;
    }

    /**
     * @return SECURITY_PERSON
     */
    public String getSecurityPerson() {
        return securityPerson;
    }

    /**
     * @param securityPerson
     */
    public void setSecurityPerson(String securityPerson) {
        this.securityPerson = securityPerson;
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
     * @return INSERT_COUNT
     */
    public Integer getInsertCount() {
        return insertCount;
    }

    /**
     * @param insertCount
     */
    public void setInsertCount(Integer insertCount) {
        this.insertCount = insertCount;
    }

    /**
     * @return DETECT_COUNT
     */
    public Integer getDetectCount() {
        return detectCount;
    }

    /**
     * @param detectCount
     */
    public void setDetectCount(Integer detectCount) {
        this.detectCount = detectCount;
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
     * @return REAL_COUNT
     */
    public Integer getRealCount() {
        return realCount;
    }

    /**
     * @param realCount
     */
    public void setRealCount(Integer realCount) {
        this.realCount = realCount;
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
     * @return DEPART_ID
     */
    public String getDepartId() {
        return departId;
    }

    /**
     * @param departId
     */
    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public List<DayProblem> getDayProblemList() {
        return dayProblemList;
    }

    public void setDayProblemList(List<DayProblem> dayProblemList) {
        this.dayProblemList = dayProblemList;
    }
}