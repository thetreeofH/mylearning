package com.ts.spm.bizs.entity.up;

/**
 * Created by zhoukun on 2021/5/12.
 */
public class PackageAlarmDetail {
    //视角类型（1-主视角；2-侧视角）
    private int viewType;
    //违禁品报警等级
    private int alarmLevel;
    //违禁品字典编码(见基础数据”违禁品数据”)
    private String dictValue;
    //违禁品字典名称(见基础数据”违禁品数据”)
    private String dictValueName;

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public int getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(int alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public String getDictValueName() {
        return dictValueName;
    }

    public void setDictValueName(String dictValueName) {
        this.dictValueName = dictValueName;
    }
}
