package com.ts.spm.bizs.vo.stat;

import java.util.List;

/**
 * @Author luoyu
 * @Date 2020/6/4 17:06
 * @Version 1.0
 */
public class StatVO {

    private double problemRate; // 问题率
    private double seizedRate; // 查获率
    private double undetectedRate; // 漏检率
    private double onJobRate; // 在岗率
    private double certifiedRate; // 持证率
    private String departId;
    private String depart;
    private String pointId;
    private String pointName;

    List<StatVO> departStat;

    public double getProblemRate() {
        return problemRate;
    }

    public void setProblemRate(double problemRate) {
        this.problemRate = problemRate;
    }

    public double getSeizedRate() {
        return seizedRate;
    }

    public void setSeizedRate(double seizedRate) {
        this.seizedRate = seizedRate;
    }

    public double getUndetectedRate() {
        return undetectedRate;
    }

    public void setUndetectedRate(double undetectedRate) {
        this.undetectedRate = undetectedRate;
    }

    public double getOnJobRate() {
        return onJobRate;
    }

    public void setOnJobRate(double onJobRate) {
        this.onJobRate = onJobRate;
    }

    public double getCertifiedRate() {
        return certifiedRate;
    }

    public void setCertifiedRate(double certifiedRate) {
        this.certifiedRate = certifiedRate;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public List<StatVO> getDepartStat() {
        return departStat;
    }

    public void setDepartStat(List<StatVO> departStat) {
        this.departStat = departStat;
    }

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
}
