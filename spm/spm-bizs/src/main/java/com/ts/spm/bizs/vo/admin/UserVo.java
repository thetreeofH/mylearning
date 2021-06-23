package com.ts.spm.bizs.vo.admin;

import com.ts.spm.bizs.entity.admin.User;

public class UserVo extends User {
    //岗位code
    private  String jobCategoryCode;

    //岗位ID
    private  String positionid;

    private String groupid;


    private  String pname;

    private  String gname;

    //部门名称
    private  String depatName;

    //部门类型（公安还是教育）
    private String code;


    //部门id
    private String depatId;



    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getPositionid() {
        return positionid;
    }

    public void setPositionid(String positionid) {
        this.positionid = positionid;
    }

    public String getDepatId() {
        return depatId;
    }

    public void setDepatId(String depatId) {
        this.depatId = depatId;
    }

    public String getDepatName() {
        return depatName;
    }

    public void setDepatName(String depatName) {
        this.depatName = depatName;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getJobCategoryCode() {
        return jobCategoryCode;
    }

    public void setJobCategoryCode(String jobCategoryCode) {
        this.jobCategoryCode = jobCategoryCode;
    }

    String mobile;

    String employName;

    String loginName;

    String depart;

    String station;
    String area;
    String line;

    String groups;

    String isDisabled;
    
    String tenantId;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmployName() {
        return employName;
    }

    public void setEmployName(String employName) {
        this.employName = employName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    @Override
    public String getIsDisabled() {
        return isDisabled;
    }

    @Override
    public void setIsDisabled(String isDisabled) {
        this.isDisabled = isDisabled;
    }

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
    
}
