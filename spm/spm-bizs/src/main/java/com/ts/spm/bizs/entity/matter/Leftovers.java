package com.ts.spm.bizs.entity.matter;

import com.ts.spm.bizs.entity.Attachment;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Table(name = "TBL_LEFTOVERS")
public class Leftovers {
    /*
    主键
     */
    @Id
    @Column(name = "ID")
    private String id;
    /*
   遗留物品名称
    */
    @Column(name = "leftovers_Name")
    private String leftoversName;
    /*
     遗留物品类型,(如包裹、贵重饰品等)，由字典中得到
     */
   @Column(name = "leftovers_type")
   private String leftoversType;
    /*
     遗留所在车站ID
     */
   @Column(name = "leaveover_station_id")
   private String leaveoverStationId;

    /*
     安检点ID
     */
    @Column(name = "security_checkpoints_id")
    private String securityCheckpointsId;

    /*
    领取状态
    */
    @Column(name = "receive_status")
    private String receiveStatus;

   @Column(name = "CRT_TIME")
   //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
   private Date crtTime;

   @Column(name = "CRT_USER_ID")
   private String crtUserId;

    @Column(name = "CRT_DEPT_ID")
    private String crtDeptId;

   @Column(name = "CRT_USER_NAME")
   private String crtUserName;

    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
   @Column(name = "UPD_TIME")
   private Date updTime;

   @Column(name = "UPD_USER_ID")
   private String updUserId;

    @Column(name = "UPD_DEPT_ID")
    private String updDeptId;

   @Column(name = "UPD_USER_NAME")
   private String updUserName;

    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "DEL_TIME")
    private Date delTime;

    @Column(name = "DEL_USER_ID")
    private String delUserId;

    @Column(name = "DEL_DEPT_ID")
    private String delDeptId;

    @Column(name = "DEL_USER_NAME")
    private String delUserName;

   @Column(name = "DEL_FLAG")
   private Integer delFlag;

   @Column(name = "MEMO")
   private String memo;

    @Column(name = "DEPART_ID")
    private String departId;

    @Column(name = "LINE_ID")
    private String lineId;

    /*
    失主姓名
     */
    @Column(name = "lost_name")
    private String lostName;
    /*
    失主身份证号
     */
    @Column(name = "lost_id_card")
    private String lostIdCard;
    /*
    失主联系电话
     */
    @Column(name = "lost_telephone_number")
    private Integer lostTelephoneNumber;

    /*
    领取时间
     */
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "lost_receive_time")
    private Date lostReceiveTime;
    /*
     处理人ID
    */
    @Column(name = "handler_user_id")
    private String handlerUserId;

    /*
    处理人姓名
     */
    @Column(name = "handler_user_name")
    private String handlerUserName;
    /*
    处理人部门ID
     */
    @Column(name = "handler_dept_id")
    private String handlerDeptId;

   @Transient
   private List<Attachment> attachments;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLeftoversName() {
        return leftoversName;
    }

    public void setLeftoversName(String leftoversName) {
        this.leftoversName = leftoversName;
    }

    public String getLeftoversType() {
        return leftoversType;
    }

    public void setLeftoversType(String leftoversType) {
        this.leftoversType = leftoversType;
    }

    public String getLeaveoverStationId() {
        return leaveoverStationId;
    }

    public void setLeaveoverStationId(String leaveoverStationId) {
        this.leaveoverStationId = leaveoverStationId;
    }

    public String getSecurityCheckpointsId() {
        return securityCheckpointsId;
    }

    public void setSecurityCheckpointsId(String securityCheckpointsId) {
        this.securityCheckpointsId = securityCheckpointsId;
    }

    public String getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(String receiveStatus) {
        this.receiveStatus = receiveStatus;
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

    public String getCrtDeptId() {
        return crtDeptId;
    }

    public void setCrtDeptId(String crtDeptId) {
        this.crtDeptId = crtDeptId;
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

    public String getUpdDeptId() {
        return updDeptId;
    }

    public void setUpdDeptId(String updDeptId) {
        this.updDeptId = updDeptId;
    }

    public String getUpdUserName() {
        return updUserName;
    }

    public void setUpdUserName(String updUserName) {
        this.updUserName = updUserName;
    }

    public Date getDelTime() {
        return delTime;
    }

    public void setDelTime(Date delTime) {
        this.delTime = delTime;
    }

    public String getDelUserId() {
        return delUserId;
    }

    public void setDelUserId(String delUserId) {
        this.delUserId = delUserId;
    }

    public String getDelDeptId() {
        return delDeptId;
    }

    public void setDelDeptId(String delDeptId) {
        this.delDeptId = delDeptId;
    }

    public String getDelUserName() {
        return delUserName;
    }

    public void setDelUserName(String delUserName) {
        this.delUserName = delUserName;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getLostName() {
        return lostName;
    }

    public void setLostName(String lostName) {
        this.lostName = lostName;
    }

    public String getLostIdCard() {
        return lostIdCard;
    }

    public void setLostIdCard(String lostIdCard) {
        this.lostIdCard = lostIdCard;
    }

    public Integer getLostTelephoneNumber() {
        return lostTelephoneNumber;
    }

    public void setLostTelephoneNumber(Integer lostTelephoneNumber) {
        this.lostTelephoneNumber = lostTelephoneNumber;
    }

    public Date getLostReceiveTime() {
        return lostReceiveTime;
    }

    public void setLostReceiveTime(Date lostReceiveTime) {
        this.lostReceiveTime = lostReceiveTime;
    }

    public String getHandlerUserId() {
        return handlerUserId;
    }

    public void setHandlerUserId(String handlerUserId) {
        this.handlerUserId = handlerUserId;
    }

    public String getHandlerUserName() {
        return handlerUserName;
    }

    public void setHandlerUserName(String handlerUserName) {
        this.handlerUserName = handlerUserName;
    }

    public String getHandlerDeptId() {
        return handlerDeptId;
    }

    public void setHandlerDeptId(String handlerDeptId) {
        this.handlerDeptId = handlerDeptId;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

}