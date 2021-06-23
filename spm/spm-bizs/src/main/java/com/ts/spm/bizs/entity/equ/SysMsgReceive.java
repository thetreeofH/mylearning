package com.ts.spm.bizs.entity.equ;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "SPM_OPER.TBL_SYSMSG_RECEIVE")
public class SysMsgReceive {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "PID")
    private String pid;

    @Column(name = "RECEIVE_ID")
    private String receiveId;

    @Column(name = "READ_FLAG")
    private Integer readFlag;

    @Column(name = "READ_TIME")
    private Date readTime;

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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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
     * @return READ_FLAG
     */
    public Integer getReadFlag() {
        return readFlag;
    }

    /**
     * @param readFlag
     */
    public void setReadFlag(Integer readFlag) {
        this.readFlag = readFlag;
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
}