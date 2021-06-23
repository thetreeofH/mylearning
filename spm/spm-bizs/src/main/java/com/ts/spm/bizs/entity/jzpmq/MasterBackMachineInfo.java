package com.ts.spm.bizs.entity.jzpmq;

import java.util.Date;

public class MasterBackMachineInfo {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MASTER_BACK_MACHINE_INFO.ID
     *
     * @mbg.generated
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MASTER_BACK_MACHINE_INFO.MASTER_IP
     *
     * @mbg.generated
     */
    private String masterIp;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MASTER_BACK_MACHINE_INFO.CREATE_TIME
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MASTER_BACK_MACHINE_INFO.UPDATE_TIME
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MASTER_BACK_MACHINE_INFO.MEMO
     *
     * @mbg.generated
     */
    private String memo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MASTER_BACK_MACHINE_INFO.ATTR1
     *
     * @mbg.generated
     */
    private String attr1;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MASTER_BACK_MACHINE_INFO.ATTR2
     *
     * @mbg.generated
     */
    private String attr2;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MASTER_BACK_MACHINE_INFO.ATTR3
     *
     * @mbg.generated
     */
    private String attr3;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MASTER_BACK_MACHINE_INFO.ATTR4
     *
     * @mbg.generated
     */
    private String attr4;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MASTER_BACK_MACHINE_INFO.ATTR5
     *
     * @mbg.generated
     */
    private String attr5;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MASTER_BACK_MACHINE_INFO.ATTR6
     *
     * @mbg.generated
     */
    private String attr6;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MASTER_BACK_MACHINE_INFO
     *
     * @mbg.generated
     */
    public MasterBackMachineInfo(String id, String masterIp, Date createTime, Date updateTime, String memo, String attr1, String attr2, String attr3, String attr4, String attr5, String attr6) {
        this.id = id;
        this.masterIp = masterIp;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.memo = memo;
        this.attr1 = attr1;
        this.attr2 = attr2;
        this.attr3 = attr3;
        this.attr4 = attr4;
        this.attr5 = attr5;
        this.attr6 = attr6;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MASTER_BACK_MACHINE_INFO
     *
     * @mbg.generated
     */
    public MasterBackMachineInfo() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MASTER_BACK_MACHINE_INFO.ID
     *
     * @return the value of MASTER_BACK_MACHINE_INFO.ID
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MASTER_BACK_MACHINE_INFO.ID
     *
     * @param id the value for MASTER_BACK_MACHINE_INFO.ID
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MASTER_BACK_MACHINE_INFO.MASTER_IP
     *
     * @return the value of MASTER_BACK_MACHINE_INFO.MASTER_IP
     *
     * @mbg.generated
     */
    public String getMasterIp() {
        return masterIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MASTER_BACK_MACHINE_INFO.MASTER_IP
     *
     * @param masterIp the value for MASTER_BACK_MACHINE_INFO.MASTER_IP
     *
     * @mbg.generated
     */
    public void setMasterIp(String masterIp) {
        this.masterIp = masterIp == null ? null : masterIp.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MASTER_BACK_MACHINE_INFO.CREATE_TIME
     *
     * @return the value of MASTER_BACK_MACHINE_INFO.CREATE_TIME
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MASTER_BACK_MACHINE_INFO.CREATE_TIME
     *
     * @param createTime the value for MASTER_BACK_MACHINE_INFO.CREATE_TIME
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MASTER_BACK_MACHINE_INFO.UPDATE_TIME
     *
     * @return the value of MASTER_BACK_MACHINE_INFO.UPDATE_TIME
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MASTER_BACK_MACHINE_INFO.UPDATE_TIME
     *
     * @param updateTime the value for MASTER_BACK_MACHINE_INFO.UPDATE_TIME
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MASTER_BACK_MACHINE_INFO.MEMO
     *
     * @return the value of MASTER_BACK_MACHINE_INFO.MEMO
     *
     * @mbg.generated
     */
    public String getMemo() {
        return memo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MASTER_BACK_MACHINE_INFO.MEMO
     *
     * @param memo the value for MASTER_BACK_MACHINE_INFO.MEMO
     *
     * @mbg.generated
     */
    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MASTER_BACK_MACHINE_INFO.ATTR1
     *
     * @return the value of MASTER_BACK_MACHINE_INFO.ATTR1
     *
     * @mbg.generated
     */
    public String getAttr1() {
        return attr1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MASTER_BACK_MACHINE_INFO.ATTR1
     *
     * @param attr1 the value for MASTER_BACK_MACHINE_INFO.ATTR1
     *
     * @mbg.generated
     */
    public void setAttr1(String attr1) {
        this.attr1 = attr1 == null ? null : attr1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MASTER_BACK_MACHINE_INFO.ATTR2
     *
     * @return the value of MASTER_BACK_MACHINE_INFO.ATTR2
     *
     * @mbg.generated
     */
    public String getAttr2() {
        return attr2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MASTER_BACK_MACHINE_INFO.ATTR2
     *
     * @param attr2 the value for MASTER_BACK_MACHINE_INFO.ATTR2
     *
     * @mbg.generated
     */
    public void setAttr2(String attr2) {
        this.attr2 = attr2 == null ? null : attr2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MASTER_BACK_MACHINE_INFO.ATTR3
     *
     * @return the value of MASTER_BACK_MACHINE_INFO.ATTR3
     *
     * @mbg.generated
     */
    public String getAttr3() {
        return attr3;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MASTER_BACK_MACHINE_INFO.ATTR3
     *
     * @param attr3 the value for MASTER_BACK_MACHINE_INFO.ATTR3
     *
     * @mbg.generated
     */
    public void setAttr3(String attr3) {
        this.attr3 = attr3 == null ? null : attr3.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MASTER_BACK_MACHINE_INFO.ATTR4
     *
     * @return the value of MASTER_BACK_MACHINE_INFO.ATTR4
     *
     * @mbg.generated
     */
    public String getAttr4() {
        return attr4;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MASTER_BACK_MACHINE_INFO.ATTR4
     *
     * @param attr4 the value for MASTER_BACK_MACHINE_INFO.ATTR4
     *
     * @mbg.generated
     */
    public void setAttr4(String attr4) {
        this.attr4 = attr4 == null ? null : attr4.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MASTER_BACK_MACHINE_INFO.ATTR5
     *
     * @return the value of MASTER_BACK_MACHINE_INFO.ATTR5
     *
     * @mbg.generated
     */
    public String getAttr5() {
        return attr5;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MASTER_BACK_MACHINE_INFO.ATTR5
     *
     * @param attr5 the value for MASTER_BACK_MACHINE_INFO.ATTR5
     *
     * @mbg.generated
     */
    public void setAttr5(String attr5) {
        this.attr5 = attr5 == null ? null : attr5.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MASTER_BACK_MACHINE_INFO.ATTR6
     *
     * @return the value of MASTER_BACK_MACHINE_INFO.ATTR6
     *
     * @mbg.generated
     */
    public String getAttr6() {
        return attr6;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MASTER_BACK_MACHINE_INFO.ATTR6
     *
     * @param attr6 the value for MASTER_BACK_MACHINE_INFO.ATTR6
     *
     * @mbg.generated
     */
    public void setAttr6(String attr6) {
        this.attr6 = attr6 == null ? null : attr6.trim();
    }
}