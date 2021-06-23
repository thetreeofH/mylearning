package com.ts.spm.bizs.vo.equ;

import java.util.ArrayList;
import java.util.List;

public class StationStatVo2 {
    private String title;
    private String category;
    private List<SecurityDeviceVo> list=new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<SecurityDeviceVo> getList() {
        return list;
    }

    public void setList(List<SecurityDeviceVo> list) {
        this.list = list;
    }
}
