package com.ts.spm.bizs.vo.admin;

import com.ts.spm.bizs.entity.admin.Depart;

import java.util.List;

public class DepartVo extends Depart {
    List<String> policeIds;

    List<String> types;

    public List<String> getPoliceIds() {
        return policeIds;
    }

    public void setPoliceIds(List<String> policeIds) {
        this.policeIds = policeIds;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }
}
