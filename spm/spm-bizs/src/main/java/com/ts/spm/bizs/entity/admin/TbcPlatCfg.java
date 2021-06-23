package com.ts.spm.bizs.entity.admin;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author luoyu
 * @Date 2020/9/10 15:55
 * @Version 1.0
 */
@Table(name = "SPM_OPER.TBC_PLAT_CFG")
public class TbcPlatCfg {
    @Id
    private String id;

    @Column(name="XS_ACT_PORT")
    private Integer xsActPort;

    @Column(name="AJY_PIC_HOME")
    private  String ajyPicHome;
    @Column(name="DANGER_LIB_HOME")
    private String dangerLibHome;
    @Column(name="EVENT_PIC_HOME")
    private String eventPicHome;
    @Column(name="CS_SVR_IP")
    private String csSvrIp;
    @Column(name="CS_SVR_PORT")
    private Integer csSvrPort;
    @Column(name="SHARE_PIC_HOME")
    private String sharePicHome;
    @Column(name="WEB_IP")
    private String webIp;
    @Column(name="WEB_PORT")
    private Integer webPort;
    @Column(name="KHS_IP")
    private String khsIp;
    @Column(name="KHS_PORT")
    private Integer khsPort;
    @Column(name="CS_SVR_OUT_IP")
    private String csSvrOutIp;
    @Column(name="CS_SVR_OUT_PORT")
    private Integer csSvrOutPort;
    @Column(name="WEB_OUT_IP")
    private String webOutIp;
    @Column(name="WEB_OUT_PORT")
    private Integer webOutPort;
    @Column(name="MEDIA_SVR_IP")
    private String mediaSvrIp;
    @Column(name="MEDIA_SVR_PORT")
    private Integer mediaSvrPort;

    //是否每日重启服务器，0否、1是
    @Column(name="START_PER_DAY")
    private Integer startPerDay;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getXsActPort() {
        return xsActPort;
    }

    public void setXsActPort(Integer xsActPort) {
        this.xsActPort = xsActPort;
    }

    public String getAjyPicHome() {
        return ajyPicHome;
    }

    public void setAjyPicHome(String ajyPicHome) {
        this.ajyPicHome = ajyPicHome;
    }

    public String getDangerLibHome() {
        return dangerLibHome;
    }

    public void setDangerLibHome(String dangerLibHome) {
        this.dangerLibHome = dangerLibHome;
    }

    public String getEventPicHome() {
        return eventPicHome;
    }

    public void setEventPicHome(String eventPicHome) {
        this.eventPicHome = eventPicHome;
    }

    public String getCsSvrIp() {
        return csSvrIp;
    }

    public void setCsSvrIp(String csSvrIp) {
        this.csSvrIp = csSvrIp;
    }

    public Integer getCsSvrPort() {
        return csSvrPort;
    }

    public void setCsSvrPort(Integer csSvrPort) {
        this.csSvrPort = csSvrPort;
    }

    public String getSharePicHome() {
        return sharePicHome;
    }

    public void setSharePicHome(String sharePicHome) {
        this.sharePicHome = sharePicHome;
    }

    public String getWebIp() {
        return webIp;
    }

    public void setWebIp(String webIp) {
        this.webIp = webIp;
    }

    public Integer getWebPort() {
        return webPort;
    }

    public void setWebPort(Integer webPort) {
        this.webPort = webPort;
    }

    public String getKhsIp() {
        return khsIp;
    }

    public void setKhsIp(String khsIp) {
        this.khsIp = khsIp;
    }

    public Integer getKhsPort() {
        return khsPort;
    }

    public void setKhsPort(Integer khsPort) {
        this.khsPort = khsPort;
    }

    public String getCsSvrOutIp() {
        return csSvrOutIp;
    }

    public void setCsSvrOutIp(String csSvrOutIp) {
        this.csSvrOutIp = csSvrOutIp;
    }

    public Integer getCsSvrOutPort() {
        return csSvrOutPort;
    }

    public void setCsSvrOutPort(Integer csSvrOutPort) {
        this.csSvrOutPort = csSvrOutPort;
    }

    public String getWebOutIp() {
        return webOutIp;
    }

    public void setWebOutIp(String webOutIp) {
        this.webOutIp = webOutIp;
    }

    public Integer getWebOutPort() {
        return webOutPort;
    }

    public void setWebOutPort(Integer webOutPort) {
        this.webOutPort = webOutPort;
    }

    public String getMediaSvrIp() {
        return mediaSvrIp;
    }

    public void setMediaSvrIp(String mediaSvrIp) {
        this.mediaSvrIp = mediaSvrIp;
    }

    public Integer getMediaSvrPort() {
        return mediaSvrPort;
    }

    public void setMediaSvrPort(Integer mediaSvrPort) {
        this.mediaSvrPort = mediaSvrPort;
    }

    public Integer getStartPerDay() {
        return startPerDay;
    }

    public void setStartPerDay(Integer startPerDay) {
        this.startPerDay = startPerDay;
    }
}
