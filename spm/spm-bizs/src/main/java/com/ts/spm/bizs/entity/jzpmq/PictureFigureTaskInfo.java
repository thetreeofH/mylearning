package com.ts.spm.bizs.entity.jzpmq;

import java.util.Date;
import javax.persistence.*;

@Table(name = "jzp_oper.PICTURE_FIGURE_TASK_INFO")
public class PictureFigureTaskInfo {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "FIRST_TIME")
    private Date firstTime;

    @Column(name = "MISSION_ID")
    private String missionId;

    @Column(name = "POINTID")
    private String pointid;

    @Column(name = "SN")
    private String sn;

    @Column(name = "X_RAY_MACHINE_PICTURE")
    private String xRayMachinePicture;

    @Column(name = "NATURAL_LIGHT_PICTURES")
    private String naturalLightPictures;

    @Column(name="X_AUX_RAY_MACHINE_PICTURE")
    private String XAuxRayMachinePicture;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @Column(name = "MEMO")
    private String memo;

    @Column(name = "ATTR1")
    private String attr1;

    @Column(name = "ATTR2")
    private String attr2;

    @Column(name = "ATTR3")
    private String attr3;

    @Column(name = "ATTR4")
    private String attr4;

    @Column(name = "ATTR5")
    private String attr5;

    @Column(name = "ATTR6")
    private String attr6;


    public String getXAuxRayMachinePicture() {
        return XAuxRayMachinePicture;
    }

    public void setXAuxRayMachinePicture(String XAuxRayMachinePicture) {
        this.XAuxRayMachinePicture = XAuxRayMachinePicture;
    }

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
     * @return FIRST_TIME
     */
    public Date getFirstTime() {
        return firstTime;
    }

    /**
     * @param firstTime
     */
    public void setFirstTime(Date firstTime) {
        this.firstTime = firstTime;
    }

    /**
     * @return MISSION_ID
     */
    public String getMissionId() {
        return missionId;
    }

    /**
     * @param missionId
     */
    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    /**
     * @return POINTID
     */
    public String getPointid() {
        return pointid;
    }

    /**
     * @param pointid
     */
    public void setPointid(String pointid) {
        this.pointid = pointid;
    }

    /**
     * @return SN
     */
    public String getSn() {
        return sn;
    }

    /**
     * @param sn
     */
    public void setSn(String sn) {
        this.sn = sn;
    }

    /**
     * @return X_RAY_MACHINE_PICTURE
     */
    public String getxRayMachinePicture() {
        return xRayMachinePicture;
    }

    /**
     * @param xRayMachinePicture
     */
    public void setxRayMachinePicture(String xRayMachinePicture) {
        this.xRayMachinePicture = xRayMachinePicture;
    }

    /**
     * @return NATURAL_LIGHT_PICTURES
     */
    public String getNaturalLightPictures() {
        return naturalLightPictures;
    }

    /**
     * @param naturalLightPictures
     */
    public void setNaturalLightPictures(String naturalLightPictures) {
        this.naturalLightPictures = naturalLightPictures;
    }

    /**
     * @return CREATE_TIME
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return UPDATE_TIME
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
     * @return ATTR1
     */
    public String getAttr1() {
        return attr1;
    }

    /**
     * @param attr1
     */
    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    /**
     * @return ATTR2
     */
    public String getAttr2() {
        return attr2;
    }

    /**
     * @param attr2
     */
    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    /**
     * @return ATTR3
     */
    public String getAttr3() {
        return attr3;
    }

    /**
     * @param attr3
     */
    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }

    /**
     * @return ATTR4
     */
    public String getAttr4() {
        return attr4;
    }

    /**
     * @param attr4
     */
    public void setAttr4(String attr4) {
        this.attr4 = attr4;
    }

    /**
     * @return ATTR5
     */
    public String getAttr5() {
        return attr5;
    }

    /**
     * @param attr5
     */
    public void setAttr5(String attr5) {
        this.attr5 = attr5;
    }

    /**
     * @return ATTR6
     */
    public String getAttr6() {
        return attr6;
    }

    /**
     * @param attr6
     */
    public void setAttr6(String attr6) {
        this.attr6 = attr6;
    }
}