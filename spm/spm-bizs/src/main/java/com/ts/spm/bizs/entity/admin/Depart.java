package com.ts.spm.bizs.entity.admin;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @author Mr.AG
 * @version 2018-02-04 19:06:43
 * @email 463540703@qq.com
 */
@Table(name = "SPM_SYS.base_depart")
public class Depart implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    @Id
    @GeneratedValue(generator = "UUID")
    private String id;

    //组织名称
    @Column(name = "name")
    private String name;

    //编码
    @Column(name = "code")
    private String code;

    //路劲
    @Column(name = "path")
    private String path;

    //办别名称
    @Column(name = "name_of_office")
    private String nameOfOffice;

    //部门类型
    @Column(name = "type")
    private String type;

    //组织简称
    @Column(name = "organization_abbreviation")
    private String organizationAbbreviation;

    //组织编码
    @Column(name = "tissue_coding")
    private String tissueCoding;

    //地址
    @Column(name = "address")
    private String address;

    //经度
    @Column(name = "x")
    private Double x;

    //维度
    @Column(name = "y")
    private Double y;

    //备注
    @Column(name = "remark")
    private String remark;

    //排序
    @Column(name = "sort")
    private Integer sort;

    //创建人
    @Column(name = "crt_user_name")
    private String crtUserName;

    //创建人ID
    @Column(name = "crt_user_id")
    private String crtUserId;

    //创建时间
    @Column(name = "crt_time")
    private Date crtTime;

    //最后更新人
    @Column(name = "upd_user_name")
    private String updUserName;

    //最后更新人ID
    @Column(name = "upd_user_id")
    private String updUserId;

    //最后更新时间
    @Column(name = "upd_time")
    private Date updTime;

    //
    @Column(name = "parent_id")
    private String parentId;

    //
    @Column(name = "attr1")
    private String attr1;

    //
    @Column(name = "attr2")
    private String attr2;

    //
    @Column(name = "attr3")
    private String attr3;

    //
    @Column(name = "attr4")
    private String attr4;

    @Column(name = "attr5")
    private String attr5;

    @Column(name = "tenant_id")
    private String tenantId;

    @Column(name = "police_id")
    private String policeId;

    @Transient
    private boolean disabled=false;

    //0：可用，1：停用
    @Column(name = "STATUS")
    private String status;

    @Column(name = "ORDER_NUM")
    private Integer orderNum;


    @Column(name = "COMPANY_ID")
    private String companyId;

    @Column(name = "DUTY_PERSON")
    private String dutyPerson;

    @Column(name = "TEL")
    private String tel;

    @Column(name="RUN_COMPANY_ID")
    private String runCompanyId;

    public String getPoliceId() {
        return policeId;
    }

    public void setPoliceId(String policeId) {
        this.policeId = policeId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

  public String getAttr5() {
    return attr5;
  }

  public void setAttr5(String attr5) {
    this.attr5 = attr5;
  }

  /**
     * 设置：主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取：主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置：组织名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：组织名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置：上级节点
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取：上级节点
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 设置：编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取：编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置：路劲
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取：路劲
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置：部门类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取：部门类型
     */
    public String getType() {
        return type;
    }

    public String getCrtUserName() {
        return crtUserName;
    }

    public void setCrtUserName(String crtUserName) {
        this.crtUserName = crtUserName;
    }

    public String getCrtUserId() {
        return crtUserId;
    }

    public void setCrtUserId(String crtUserId) {
        this.crtUserId = crtUserId;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public String getUpdUserName() {
        return updUserName;
    }

    public void setUpdUserName(String updUserName) {
        this.updUserName = updUserName;
    }

    public String getUpdUserId() {
        return updUserId;
    }

    public void setUpdUserId(String updUserId) {
        this.updUserId = updUserId;
    }

    public Date getUpdTime() {
        return updTime;
    }

    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public String getAttr2() {
        return attr2;
    }

    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    public String getAttr3() {
        return attr3;
    }

    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }

    public String getAttr4() {
        return attr4;
    }

    public void setAttr4(String attr4) {
        this.attr4 = attr4;
    }

    public String getNameOfOffice() {
        return nameOfOffice;
    }

    public void setNameOfOffice(String nameOfOffice) {
        this.nameOfOffice = nameOfOffice;
    }

    public String getOrganizationAbbreviation() {
        return organizationAbbreviation;
    }

    public void setOrganizationAbbreviation(String organizationAbbreviation) {
        this.organizationAbbreviation = organizationAbbreviation;
    }

    public String getTissueCoding() {
        return tissueCoding;
    }

    public void setTissueCoding(String tissueCoding) {
        this.tissueCoding = tissueCoding;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getDutyPerson() {
        return dutyPerson;
    }

    public void setDutyPerson(String dutyPerson) {
        this.dutyPerson = dutyPerson;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRunCompanyId() {
        return runCompanyId;
    }

    public void setRunCompanyId(String runCompanyId) {
        this.runCompanyId = runCompanyId;
    }
}
