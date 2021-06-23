package com.ts.spm.bizs.entity.msg;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Table(name = "SPM_OPER.TBL_POINT_TASK_INFO")
public class PointTask {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "SEND_USER")
    private String sendUser;

    @Column(name = "SEND_DEPT")
    private String sendDept;

    @Column(name = "SEND_TIME")
    private Date sendTime;

    @Column(name = "RF_TIME")
    private Date rfTime;

    @Column(name = "TASK_TEXT")
    private String taskText;

    @Transient
    private List<PointTaskPush> pushes;

    /**
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return SEND_USER
     */
    public String getSendUser() {
        return sendUser;
    }

    /**
     * @param sendUser
     */
    public void setSendUser(String sendUser) {
        this.sendUser = sendUser;
    }

    /**
     * @return SEND_DEPT
     */
    public String getSendDept() {
        return sendDept;
    }

    /**
     * @param sendDept
     */
    public void setSendDept(String sendDept) {
        this.sendDept = sendDept;
    }

    /**
     * @return SEND_TIME
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * @param sendTime
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * @return RF_TIME
     */
    public Date getRfTime() {
        return rfTime;
    }

    /**
     * @param rfTime
     */
    public void setRfTime(Date rfTime) {
        this.rfTime = rfTime;
    }

    /**
     * @return TASK_TEXT
     */
    public String getTaskText() {
        return taskText;
    }

    /**
     * @param taskText
     */
    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public List<PointTaskPush> getPushes() {
        return pushes;
    }

    public void setPushes(List<PointTaskPush> pushes) {
        this.pushes = pushes;
    }
}