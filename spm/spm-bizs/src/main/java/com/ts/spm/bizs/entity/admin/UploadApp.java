package com.ts.spm.bizs.entity.admin;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "SPM_SYS.UPLOAD_APP")
public class UploadApp {
    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "VERSION_NAME")
    private String versionName;

    @Column(name = "VERSION_NUMBER")
    private String versionNumber;

    @Column(name = "FILE_SIZE")
    private BigDecimal fileSize;

    @Column(name = "IF_FORCE")
    private BigDecimal ifForce;

    @Column(name = "UPLOAD_NOTE")
    private String uploadNote;

    @Column(name = "UPLOAD_DATE")
    private Date uploadDate;

    @Column(name = "UPLOAD_USER")
    private String uploadUser;

    @Column(name = "FILE_URL")
    private String fileUrl;

    @Column(name = "FILE_TYPE")
    private String fileType;

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
     * @return VERSION_NAME
     */
    public String getVersionName() {
        return versionName;
    }

    /**
     * @param versionName
     */
    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    /**
     * @return VERSION_NUMBER
     */
    public String getVersionNumber() {
        return versionNumber;
    }

    /**
     * @param versionNumber
     */
    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    /**
     * @return FILE_SIZE
     */
    public BigDecimal getFileSize() {
        return fileSize;
    }

    /**
     * @param fileSize
     */
    public void setFileSize(BigDecimal fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * @return IF_FORCE
     */
    public BigDecimal getIfForce() {
        return ifForce;
    }

    /**
     * @param ifForce
     */
    public void setIfForce(BigDecimal ifForce) {
        this.ifForce = ifForce;
    }

    /**
     * @return UPLOAD_NOTE
     */
    public String getUploadNote() {
        return uploadNote;
    }

    /**
     * @param uploadNote
     */
    public void setUploadNote(String uploadNote) {
        this.uploadNote = uploadNote;
    }

    /**
     * @return UPLOAD_DATE
     */
    public Date getUploadDate() {
        return uploadDate;
    }

    /**
     * @param uploadDate
     */
    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    /**
     * @return UPLOAD_USER
     */
    public String getUploadUser() {
        return uploadUser;
    }

    /**
     * @param uploadUser
     */
    public void setUploadUser(String uploadUser) {
        this.uploadUser = uploadUser;
    }

    /**
     * @return FILE_URL
     */
    public String getFileUrl() {
        return fileUrl;
    }

    /**
     * @param fileUrl
     */
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    /**
     * @return FILE_TYPE
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * @param fileType
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}