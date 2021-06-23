package com.ts.spm.bizs.vo.stat;

/**
 * @Author luoyu
 * @Date 2020/6/4 17:06
 * @Version 1.0
 */
public class DeviceVo {
    private long camera=0;
    private long cameraBreak;
    private long xray;
    private long xrayBreak;
    private long door;
    private long doorBreak;
    private long discern;
    private long discernBreak;

    public long getCamera() {
        return camera;
    }

    public void setCamera(long camera) {
        this.camera = camera;
    }

    public long getCameraBreak() {
        return cameraBreak;
    }

    public void setCameraBreak(long cameraBreak) {
        this.cameraBreak = cameraBreak;
    }

    public long getXray() {
        return xray;
    }

    public void setXray(long xray) {
        this.xray = xray;
    }

    public long getXrayBreak() {
        return xrayBreak;
    }

    public void setXrayBreak(long xrayBreak) {
        this.xrayBreak = xrayBreak;
    }

    public long getDoor() {
        return door;
    }

    public void setDoor(long door) {
        this.door = door;
    }

    public long getDoorBreak() {
        return doorBreak;
    }

    public void setDoorBreak(long doorBreak) {
        this.doorBreak = doorBreak;
    }

    public long getDiscern() {
        return discern;
    }

    public void setDiscern(long discern) {
        this.discern = discern;
    }

    public long getDiscernBreak() {
        return discernBreak;
    }

    public void setDiscernBreak(long discernBreak) {
        this.discernBreak = discernBreak;
    }
}
