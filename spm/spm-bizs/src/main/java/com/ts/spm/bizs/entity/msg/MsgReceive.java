package com.ts.spm.bizs.entity.msg;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "SPM_OPER.TBL_MSG_RECEIVE")
public class MsgReceive {
    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "MSG_ID")
    private Integer msgId;

    @Column(name = "DEPART_ID")
    private String departId;

    @Column(name = "DEPART_NAME")
    private String departName;

    @Column(name = "RECEIVE_USER_ID")
    private String receiveUserId;

    @Column(name = "RECEIVE_USER_NAME")
    private String receiveUserName;

    @Column(name = "READ_TIME")
    private Date readTime;

    @Column(name = "READ_STATE")
    private Integer readState;

    @Column(name = "DEL_FLAG")
    private int delFlag;

    @Column(name = "FINISH_TIME")
    private Date finishTime;

    @Column(name = "FINISH_STATE")
    private Integer finishState;

    @Transient
    private List<MsgReply> replies;

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
     * @return MSG_ID
     */
    public Integer getMsgId() {
        return msgId;
    }

    /**
     * @param msgId
     */
    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
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
     * @return DEPART_NAME
     */
    public String getDepartName() {
        return departName;
    }

    /**
     * @param departName
     */
    public void setDepartName(String departName) {
        this.departName = departName;
    }

    /**
     * @return RECEIVE_USER_ID
     */
    public String getReceiveUserId() {
        return receiveUserId;
    }

    /**
     * @param receiveUserId
     */
    public void setReceiveUserId(String receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    /**
     * @return RECEIVE_USER_NAME
     */
    public String getReceiveUserName() {
        return receiveUserName;
    }

    /**
     * @param receiveUserName
     */
    public void setReceiveUserName(String receiveUserName) {
        this.receiveUserName = receiveUserName;
    }

    /**
     * @return READ_TIME
     */
    public Date getReadTime() {
        return readTime;
    }

    /**
     * @param readTime
     */
    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    /**
     * @return READ_STATE
     */
    public Integer getReadState() {
        return readState;
    }

    /**
     * @param readState
     */
    public void setReadState(Integer readState) {
        this.readState = readState;
    }

    /**
     * @return DEL_FLAG
     */
    public int getDelFlag() {
        return delFlag;
    }

    /**
     * @param delFlag
     */
    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getFinishState() {
        return finishState;
    }

    public void setFinishState(Integer finishState) {
        this.finishState = finishState;
    }

    public List<MsgReply> getReplies() {
        return replies;
    }

    public void setReplies(List<MsgReply> replies) {
        this.replies = replies;
    }
}