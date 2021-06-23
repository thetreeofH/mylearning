package com.ts.spm.bizs.entity.msg;

import com.ts.spm.bizs.entity.Attachment;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "SPM_OPER.TBL_MSG_INFO")
public class MsgInfo {
    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TYPE")
    private int type;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "DEPART_ID")
    private String departId;

    @Column(name = "DEPART_NAME")
    private String departName;

    @Column(name = "CRT_TIME")
    private Date crtTime;

    @Column(name = "CRT_USER_ID")
    private String crtUserId;

    @Column(name = "CRT_USER_NAME")
    private String crtUserName;

    @Column(name = "CRT_DEPT_ID")
    private String crtDeptId;

    @Column(name = "UPD_TIME")
    private Date updTime;

    @Column(name = "UPD_USER_ID")
    private String updUserId;

    @Column(name = "UPD_USER_NAME")
    private String updUserName;

    @Column(name = "UPD_DEPT_ID")
    private String updDeptId;

    @Column(name = "DEL_USER_ID")
    private String delUserId;

    @Column(name = "DEL_USER_NAME")
    private String delUserName;

    @Column(name = "DEL_TIME")
    private Date delTime;

    @Column(name = "DEL_FLAG")
    private int delFlag;

    @Column(name = "TENANT_ID")
    private String tenantId;

    @Column(name = "RECEIVE_DEPT_ID")
    private String receiveDeptId;

    @Column(name = "RECEIVE_DEPT")
    private String receiveDept;

    @Column(name = "FINISH_TIME")
    private Date finishTime;

    @Column(name = "REPLY_FLAG")
    private Integer replyFlag;

    @Transient
    private List<MsgReceive> msgReceives;
    @Transient
    private List<Attachment> attachments;
    @Transient
    private List<MsgReply> replies;

    @Transient
    private Date completeTime;

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
     * @return TYPE
     */
    public int getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(int type) {
        this.type = type;
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

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getReplyFlag() {
        return replyFlag;
    }

    public void setReplyFlag(Integer replyFlag) {
        this.replyFlag = replyFlag;
    }

    public String getReceiveDeptId() {
        return receiveDeptId;
    }

    public void setReceiveDeptId(String receiveDeptId) {
        this.receiveDeptId = receiveDeptId;
    }

    public String getReceiveDept() {
        return receiveDept;
    }

    public void setReceiveDept(String receiveDept) {
        this.receiveDept = receiveDept;
    }

    public List<MsgReceive> getMsgReceives() {
        return msgReceives;
    }

    public void setMsgReceives(List<MsgReceive> msgReceives) {
        this.msgReceives = msgReceives;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public List<MsgReply> getReplies() {
        return replies;
    }

    public void setReplies(List<MsgReply> replies) {
        this.replies = replies;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }
}