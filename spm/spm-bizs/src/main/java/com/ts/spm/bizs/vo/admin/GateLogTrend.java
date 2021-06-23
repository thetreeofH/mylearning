package com.ts.spm.bizs.vo.admin;

public class GateLogTrend {
    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;

    public GateLogTrend() {
    }

    public GateLogTrend(int num, String date) {
        this.num = num;
        this.date = date;
    }
}
