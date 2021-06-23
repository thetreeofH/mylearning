package com.ts.spm.bizs.vo.matter;

import io.swagger.models.auth.In;

import javax.persistence.Id;
import java.util.List;


public class LeftoversQuShiDto {
    @Id
    Integer sumCount;
    String occurredTime;
    private String leftoversType;
    int [] list;

    public Integer getSumCount() {
        return sumCount;
    }

    public void setSumCount(Integer sumCount) {
        this.sumCount = sumCount;
    }

    public String getOccurredTime() {
        return occurredTime;
    }

    public void setOccurredTime(String occurredTime) {
        this.occurredTime = occurredTime;
    }

    public String getLeftoversType() {
        return leftoversType;
    }

    public void setLeftoversType(String leftoversType) {
        this.leftoversType = leftoversType;
    }

    public int[] getList() {
        return list;
    }

    public void setList(int[] list) {
        this.list = list;
    }
}
