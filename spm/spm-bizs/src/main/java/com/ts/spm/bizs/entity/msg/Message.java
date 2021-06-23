package com.ts.spm.bizs.entity.msg;

import javax.persistence.Column;
import javax.persistence.Id;

//@Table(name = "TBL_MESSAGE")
public class Message {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "MESSAGE_ID")
    private String messageId;

    @Column(name = "RECEIVE_ID")
    private String receiveId;

    @Column(name = "READ_STATUS")
    private String readStatus;

    @Column(name = "MESSAGE_DATE")
    private String messageDate;

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
}