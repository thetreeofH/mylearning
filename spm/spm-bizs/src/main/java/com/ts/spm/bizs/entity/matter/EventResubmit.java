package com.ts.spm.bizs.entity.matter;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "SPM_OPER.TBL_EVENT_RESUBMIT")
public class EventResubmit {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "EVT_ID")
    private String evtId;

    @Column(name = "CONTENT")
    private String content;

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

    @Column(name = "TENANT_ID")
    private String tenantId;

    @Column(name = "TYPE")
    private Integer type;

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
     * @return EVT_ID
     */
    public String getEvtId() {
        return evtId;
    }

    /**
     * @param evtId
     */
    public void setEvtId(String evtId) {
        this.evtId = evtId;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}