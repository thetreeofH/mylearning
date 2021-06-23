package com.ts.spm.bizs.entity.msg;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "SPM_OPER.TBL_MESSAGE")
public class MessageStatus {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "MESSAGE_ID")
    private String messageId;

    @Column(name = "RECEIVE_ID")
    private String receiveId;

    @Column(name = "RECEIVE_NAME")
    private String receiveName;

    @Column(name = "READ_STATUS")
    private String readStatus;

    @Column(name = "MESSAGE_DATE")
    private String messageDate;

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

    @Column(name = "TENANT_ID")
    private String tenantId;

    @Column(name = "CRT_DEPT_ID")
    private String crtDeptId;

    @Column(name = "UPD_DEPT_ID")
    private String updDeptId;

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
     * @return MESSAGE_ID
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * @param messageId
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     * @return RECEIVE_ID
     */
    public String getReceiveId() {
        return receiveId;
    }

    /**
     * @param receiveId
     */
    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    /**
     * @return READ_STATUS
     */
    public String getReadStatus() {
        return readStatus;
    }

    /**
     * @param readStatus
     */
    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    /**
     * @return MESSAGE_DATE
     */
    public String getMessageDate() {
        return messageDate;
    }

    /**
     * @param messageDate
     */
    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public String getCrtUserId() {
        return crtUserId;
    }

    public void setCrtUserId(String crtUserId) {
        this.crtUserId = crtUserId;
    }

    public String getCrtUserName() {
        return crtUserName;
    }

    public void setCrtUserName(String crtUserName) {
        this.crtUserName = crtUserName;
    }

    public Date getUpdTime() {
        return updTime;
    }

    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }

    public String getUpdUserId() {
        return updUserId;
    }

    public void setUpdUserId(String updUserId) {
        this.updUserId = updUserId;
    }

    public String getUpdUserName() {
        return updUserName;
    }

    public void setUpdUserName(String updUserName) {
        this.updUserName = updUserName;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getCrtDeptId() {
        return crtDeptId;
    }

    public void setCrtDeptId(String crtDeptId) {
        this.crtDeptId = crtDeptId;
    }

    public String getUpdDeptId() {
        return updDeptId;
    }

    public void setUpdDeptId(String updDeptId) {
        this.updDeptId = updDeptId;
    }
}