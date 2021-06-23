package com.ts.spm.bizs.vo.matter;

import com.ts.spm.bizs.entity.matter.PointDanger;

public class PointDangerVo extends PointDanger {
    private String mtypeName;
    private String stypeName;
    private String ajyTypeName;

    public String getMtypeName() {
        return mtypeName;
    }

    public void setMtypeName(String mtypeName) {
        this.mtypeName = mtypeName;
    }

    public String getStypeName() {
        return stypeName;
    }

    public void setStypeName(String stypeName) {
        this.stypeName = stypeName;
    }

    public String getAjyTypeName() {
        return ajyTypeName;
    }

    public void setAjyTypeName(String ajyTypeName) {
        this.ajyTypeName = ajyTypeName;
    }
}
