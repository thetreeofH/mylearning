package com.ts.spm.bizs.vo.admin;


import com.ts.spm.bizs.entity.admin.GateLog;

public class GateLogVo extends GateLog {

  private String departName;

  public String getDepartName() {
    return departName;
  }

  public void setDepartName(String departName) {
    this.departName = departName;
  }
}
