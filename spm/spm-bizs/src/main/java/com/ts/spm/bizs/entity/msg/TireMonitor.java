package com.ts.spm.bizs.entity.msg;


/**
 * @ClassName TireMonitor
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/11/13 15:14
 * @Version 1.0
 **/
public class TireMonitor {
    private String pointId;

    private String pointName;

    private String stationId;

    private String stationName;

    private Integer hitType;

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Integer getHitType() {
        return hitType;
    }

    public void setHitType(Integer hitType) {
        this.hitType = hitType;
    }

    public String getHitPic() {
        return hitPic;
    }

    public void setHitPic(String hitPic) {
        this.hitPic = hitPic;
    }

    public String getHitTime() {
        return hitTime;
    }

    public void setHitTime(String hitTime) {
        this.hitTime = hitTime;
    }

    private String hitPic;

    private String hitTime;


}
