package com.ts.spm.bizs.entity.gis;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "SPM_SYS.TBL_DEFENCE_AREA")
public class DefenceArea {
    @Id
    private String id;

    @Column(name = "DEPART_ID")
    private String departId;

    @Transient
    private String departName;

    @Column(name = "SEQ")
    private BigDecimal seq;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PIC_PATH")
    private String picPath;

    @Column(name = "PIC_WKT")
    private String picWkt;

    @Column(name = "MEMO")
    private String memo;

    @Column(name = "CRT_TIME")
    private Date crtTime;

    @Column(name = "UPD_TIME")
    private Date updTime;

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

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    /**
     * @return SEQ
     */
    public BigDecimal getSeq() {
        return seq;
    }

    /**
     * @param seq
     */
    public void setSeq(BigDecimal seq) {
        this.seq = seq;
    }

    /**
     * @return NAME
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return PIC_PATH
     */
    public String getPicPath() {
        return picPath;
    }

    /**
     * @param picPath
     */
    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    /**
     * @return PIC_WKT
     */
    public String getPicWkt() {
        return picWkt;
    }

    /**
     * @param picWkt
     */
    public void setPicWkt(String picWkt) {
        this.picWkt = picWkt;
    }

    /**
     * @return MEMO
     */
    public String getMemo() {
        return memo;
    }

    /**
     * @param memo
     */
    public void setMemo(String memo) {
        this.memo = memo;
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
}