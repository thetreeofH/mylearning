package com.ts.spm.bizs.entity.jzpoip;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "jzp_oper.TBL_OPER_LOG")
public class TblOperLog {
    @Id
    @Column(name = "OPER_ID")
    private String operId;

    @Column(name = "OPER_MODUL")
    private String operModul;

    @Column(name = "OPER_TYPE")
    private String operType;

    @Column(name = "OPER_DESC")
    private String operDesc;

    @Column(name = "OPER_METHOD")
    private String operMethod;

    @Column(name = "OPER_REQU_PARAM")
    private String operRequParam;

    @Column(name = "OPER_RESP_PARAM")
    private String operRespParam;

    @Column(name = "OPER_USER_ID")
    private String operUserId;

    @Column(name = "OPER_USER_NAME")
    private String operUserName;

    @Column(name = "OPER_IP")
    private String operIp;

    @Column(name = "ATTR1")
    private String attr1;

    @Column(name = "ATTR2")
    private String attr2;

    @Column(name = "ATTR3")
    private String attr3;

    @Column(name = "ATTR4")
    private String attr4;

    @Column(name = "OPER_CREATE_TIME")
    private Date operCreateTime;

    @Column(name = "MEMO")
    private String memo;

    @Column(name = "OPER_URI")
    private String operUri;

    @Column(name = "DEPART_ID")
    private String departId;

    @Column(name = "TENANT_ID")
    private String tenantId;

    /**
     * @return OPER_ID
     */
    public String getOperId() {
        return operId;
    }

    /**
     * @param operId
     */
    public void setOperId(String operId) {
        this.operId = operId;
    }

    /**
     * @return OPER_MODUL
     */
    public String getOperModul() {
        return operModul;
    }

    /**
     * @param operModul
     */
    public void setOperModul(String operModul) {
        this.operModul = operModul;
    }

    /**
     * @return OPER_TYPE
     */
    public String getOperType() {
        return operType;
    }

    /**
     * @param operType
     */
    public void setOperType(String operType) {
        this.operType = operType;
    }

    /**
     * @return OPER_DESC
     */
    public String getOperDesc() {
        return operDesc;
    }

    /**
     * @param operDesc
     */
    public void setOperDesc(String operDesc) {
        this.operDesc = operDesc;
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
     * @return OPER_REQU_PARAM
     */
    public String getOperRequParam() {
        return operRequParam;
    }

    /**
     * @param operRequParam
     */
    public void setOperRequParam(String operRequParam) {
        this.operRequParam = operRequParam;
    }

    /**
     * @return OPER_RESP_PARAM
     */
    public String getOperRespParam() {
        return operRespParam;
    }

    /**
     * @param operRespParam
     */
    public void setOperRespParam(String operRespParam) {
        this.operRespParam = operRespParam;
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
     * @return ATTR1
     */
    public String getAttr1() {
        return attr1;
    }

    /**
     * @param attr1
     */
    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    /**
     * @return ATTR2
     */
    public String getAttr2() {
        return attr2;
    }

    /**
     * @param attr2
     */
    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    /**
     * @return ATTR3
     */
    public String getAttr3() {
        return attr3;
    }

    /**
     * @param attr3
     */
    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }

    /**
     * @return ATTR4
     */
    public String getAttr4() {
        return attr4;
    }

    /**
     * @param attr4
     */
    public void setAttr4(String attr4) {
        this.attr4 = attr4;
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