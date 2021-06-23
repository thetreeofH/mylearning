package com.ts.spm.bizs.vo.equ;

import java.util.ArrayList;
import java.util.List;

public class StationStatVo {
    private String title;
    private String category;
    private List<Object> list=new ArrayList<>();

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

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }
}
