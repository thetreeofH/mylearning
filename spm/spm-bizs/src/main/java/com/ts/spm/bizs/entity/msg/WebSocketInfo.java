package com.ts.spm.bizs.entity.msg;

/**
 * Created by Administrator on 2020/9/14.
 */
public class WebSocketInfo {
    private String type;
    private Object info;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }
}
