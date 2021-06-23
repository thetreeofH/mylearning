package com.ts.spm.bizs.vo.admin;


import com.ts.spm.bizs.entity.admin.AppBind;

public class AppBindVo extends AppBind {
    private String userName;
    private String fullName;
    private String deptName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
