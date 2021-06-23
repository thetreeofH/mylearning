package com.ts.spm.bizs.entity.matter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ts.spm.bizs.entity.Attachment;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Table(name = "TBL_STORAGE_ROOM")
public class StorageRoom {
    /*
    主键
     */
    @Id
    @Column(name = "ID")
    private String id;
    /*
   标题
    */
    @Column(name = "TITLE")
    private String title;
    /*
     资料分类/类别,(如法律法规、学习资料等)，由字典中得到
     */
   @Column(name = "TYPE")
   private String type;
    /*
     文件名称
     */
   @Column(name = "FILE_NAME")
   private String fileName;

    /*
     文件类型(PDF、Word、PNG等)
     */
    @Column(name = "FILE_TYPE")
    private String fileType;

    /*
    文件大小
    */
    @Column(name = "FILE_SIZE")
    private String fileSize;

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

    @Column(name = "STATION_ID")
    private String stationId;

    @Column(name = "IS_SHARE")
    private String isShare;

   @Transient
   private List<Attachment> attachments;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
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

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getIsShare() {
        return isShare;
    }

    public void setIsShare(String isShare) {
        this.isShare = isShare;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

}