package com.ts.spm.bizs.vo.admin;

import com.ts.spm.bizs.entity.admin.Employee;

/**
 * @Author luoyu
 * @Date 2020/5/12 14:32
 * @Version 1.0
 */
public class EmployVO extends Employee {

    private String depart;

    private String levelName;

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }
}
