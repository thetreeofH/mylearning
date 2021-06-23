package com.ts.spm.bizs.entity.msg;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "SPM_OPER.TBL_POINT_WARN_PUSH_LIST")
public class PointWarnPush {
    @Column(name = "WARN_ID")
    private String warnId;

    @Column(name = "POINT_ID")
    private String pointId;

    @Column(name = "IS_SEND")
    private Integer isSend;

    @Column(name = "IS_READ")
    private Integer isRead;

    @Column(name = "POINT")
    private String point;

    /**
     * @return WARN_ID
     */
    public String getWarnId() {
        return warnId;
    }

    /**
     * @param warnId
     */
    public void setWarnId(String warnId) {
        this.warnId = warnId;
    }

    /**
     * @return POINT_ID
     */
    public String getPointId() {
        return pointId;
    }

    /**
     * @param pointId
     */
    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    /**
     * @return IS_SEND
     */
    public Integer getIsSend() {
        return isSend;
    }

    /**
     * @param isSend
     */
    public void setIsSend(Integer isSend) {
        this.isSend = isSend;
    }

    /**
     * @return IS_READ
     */
    public Integer getIsRead() {
        return isRead;
    }

    /**
     * @param isRead
     */
    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
}