package com.ts.spm.bizs.entity.msg;

import java.util.List;

public class SwitchConfPush {
    private String pointId;
    private Integer state;
    private Integer tag;
    private List<TimeConf> times;

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public List<TimeConf> getTimes() {
        return times;
    }

    public void setTimes(List<TimeConf> times) {
        this.times = times;
    }
}
