package com.ts.spm.bizs.entity.jzplog;

import java.util.Date;
import javax.persistence.*;

@Table(name = "jzp_oper.TBL_EXCEPTION_LOG")
public class TblExecLog {
    @Id
    @Column(name = "EXC_ID")
    private String excId;

    @Column(name = "EXC_REQU_PARAM")
    private String excRequParam;

    @Column(name = "OPER_METHOD")
    private String operMethod;

    @Column(name = "EXC_NAME")
    private String excName;

    @Column(name = "EXC_MESSAGE")
    private String excMessage;

    @Column(name = "OPER_USER_ID")
    private String operUserId;

    @Column(name = "OPER_USER_NAME")
    private String operUserName;

    @Column(name = "OPER_URI")
    private String operUri;

    @Column(name = "OPER_IP")
    private String operIp;

    @Column(name = "OPER_CREATE_TIME")
    private Date operCreateTime;

    @Column(name = "DEPART_ID")
    private String departId;

    @Column(name = "TENANT_ID")
    private String tenantId;

    /**
     * @return EXC_ID
     */
    public String getExcId() {
        return excId;
    }

    /**
     * @param excId
     */
    public void setExcId(String excId) {
        this.excId = excId;
    }

    /**
     * @return EXC_REQU_PARAM
     */
    public String getExcRequParam() {
        return excRequParam;
    }

    /**
     * @param excRequParam
     */
    public void setExcRequParam(String excRequParam) {
        this.excRequParam = excRequParam;
    }

    /**
     * @return OPER_METHOD
     */
    public String getOperMethod() {
        return operMethod;
    }

    /**
     * @param operMethod
     */
    public void setOperMethod(String operMethod) {
        this.operMethod = operMethod;
    }

    /**
     * @return EXC_NAME
     */
    public String getExcName() {
        return excName;
    }

    /**
     * @param excName
     */
    public void setExcName(String excName) {
        this.excName = excName;
    }

    /**
     * @return EXC_MESSAGE
     */
    public String getExcMessage() {
        return excMessage;
    }

    /**
     * @param excMessage
     */
    public void setExcMessage(String excMessage) {
        this.excMessage = excMessage;
    }

    /**
     * @return OPER_USER_ID
     */
    public String getOperUserId() {
        return operUserId;
    }

    /**
     * @param operUserId
     */
    public void setOperUserId(String operUserId) {
        this.operUserId = operUserId;
    }

    /**
     * @return OPER_USER_NAME
     */
    public String getOperUserName() {
        return operUserName;
    }

    /**
     * @param operUserName
     */
    public void setOperUserName(String operUserName) {
        this.operUserName = operUserName;
    }

    /**
     * @return OPER_URI
     */
    public String getOperUri() {
        return operUri;
    }

    /**
     * @param operUri
     */
    public void setOperUri(String operUri) {
        this.operUri = operUri;
    }

    /**
     * @return OPER_IP
     */
    public String getOperIp() {
        return operIp;
    }

    /**
     * @param operIp
     */
    public void setOperIp(String operIp) {
        this.operIp = operIp;
    }

    /**
     * @return OPER_CREATE_TIME
     */
    public Date getOperCreateTime() {
        return operCreateTime;
    }

    /**
     * @param operCreateTime
     */
    public void setOperCreateTime(Date operCreateTime) {
        this.operCreateTime = operCreateTime;
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
}