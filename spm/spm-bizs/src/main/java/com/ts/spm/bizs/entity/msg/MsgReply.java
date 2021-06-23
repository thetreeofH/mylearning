package com.ts.spm.bizs.entity.msg;

import com.ts.spm.bizs.entity.Attachment;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "SPM_OPER.TBL_MSG_REPLY")
public class MsgReply {
    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "MSG_ID")
    private Integer msgId;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "REPLY_USER_ID")
    private String replyUserId;

    @Column(name = "REPLY_USER_NAME")
    private String replyUserName;

    @Column(name = "READ_STATE")
    private int readState;

    @Column(name = "READ_TIME")
    private Date readTime;

    @Column(name = "CRT_TIME")
    private Date crtTime;

    @Column(name = "CRT_USER_ID")
    private String crtUserId;

    @Column(name = "CRT_USER_NAME")
    private String crtUserName;

    @Column(name = "DEL_TIME")
    private Date delTime;

    @Column(name = "DEL_USER_ID")
    private String delUserId;

    @Column(name = "DEL_USER_NAME")
    private String delUserName;

    @Column(name = "DEL_FLAG")
    private int delFlag;

    @Transient
    private List<Attachment> attaches;

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
     * @return REPLY_USER_ID
     */
    public String getReplyUserId() {
        return replyUserId;
    }

    /**
     * @param replyUserId
     */
    public void setReplyUserId(String replyUserId) {
        this.replyUserId = replyUserId;
    }

    /**
     * @return REPLY_USER_NAME
     */
    public String getReplyUserName() {
        return replyUserName;
    }

    /**
     * @param replyUserName
     */
    public void setReplyUserName(String replyUserName) {
        this.replyUserName = replyUserName;
    }

    /**
     * @return READ_STATE
     */
    public int getReadState() {
        return readState;
    }

    /**
     * @param readState
     */
    public void setReadState(int readState) {
        this.readState = readState;
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
    public int getDelFlag() {
        return delFlag;
    }

    /**
     * @param delFlag
     */
    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public List<Attachment> getAttaches() {
        return attaches;
    }

    public void setAttaches(List<Attachment> attaches) {
        this.attaches = attaches;
    }
}