package com.ts.spm.bizs.entity.msg;

/**
 * Created by Administrator on 2020/6/22.
 */
public class IpCall {
    private String toId;
    private String remoteUrl;
    private String[] departId;

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getRemoteUrl() {
        return remoteUrl;
    }

    public void setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }

    public String[] getDepartId() {
        return departId;
    }

    public void setDepartId(String[] departId) {
        this.departId = departId;
    }
}
