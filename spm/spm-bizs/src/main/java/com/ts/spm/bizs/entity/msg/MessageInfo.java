package com.ts.spm.bizs.entity.msg;

import com.ts.spm.bizs.entity.Attachment;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Table(name = "SPM_OPER.TBL_MESSAGE_INFO")
public class MessageInfo {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "MESSAGE_NAME")
    private String messageName;

    @Column(name = "MESSAGE_CONTENT")
    private String messageContent;

    @Column(name = "MESSAGE_DATE")
    private Date messageDate;

    @Column(name = "RECEIVE_NAME")
    private String receiveName;

    @Column(name = "RECEIVE_ID")
    private String receiveId;

    @Column(name = "FUJIAN_URL")
    private String fujianUrl;

    @Column(name = "MESSAGE_TITLE")
    private String messageTitle;

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

    @Transient
    private List<Attachment> attachments;
    @Transient
    private List<MessageStatus> statuses;

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
     * @return MESSAGE_NAME
     */
    public String getMessageName() {
        return messageName;
    }

    /**
     * @param messageName
     */
    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    /**
     * @return MESSAGE_CONTENT
     */
    public String getMessageContent() {
        return messageContent;
    }

    /**
     * @param messageContent
     */
    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    /**
     * @return MESSAGE_DATE
     */
    public Date getMessageDate() {
        return messageDate;
    }

    /**
     * @param messageDate
     */
    public void setMessageDate(Date messageDate) {
        this.messageDate = messageDate;
    }

    /**
     * @return RECEIVE_NAME
     */
    public String getReceiveName() {
        return receiveName;
    }

    /**
     * @param receiveName
     */
    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
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

    /**
     * @return FUJIAN_URL
     */
    public String getFujianUrl() {
        return fujianUrl;
    }

    /**
     * @param fujianUrl
     */
    public void setFujianUrl(String fujianUrl) {
        this.fujianUrl = fujianUrl;
    }

    /**
     * @return MESSAGE_TITLE
     */
    public String getMessageTitle() {
        return messageTitle;
    }

    /**
     * @param messageTitle
     */
    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
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

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public List<MessageStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<MessageStatus> statuses) {
        this.statuses = statuses;
    }
}