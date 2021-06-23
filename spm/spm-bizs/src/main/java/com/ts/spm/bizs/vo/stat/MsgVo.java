package com.ts.spm.bizs.vo.stat;

import java.util.List;

public class MsgVo {
    private Integer sysCnt=0;
    private Integer usrCnt=0;

    private List sysMsg;

    public Integer getSysCnt() {
        return sysCnt;
    }

    public void setSysCnt(Integer sysCnt) {
        this.sysCnt = sysCnt;
    }

    public Integer getUsrCnt() {
        return usrCnt;
    }

    public void setUsrCnt(Integer usrCnt) {
        this.usrCnt = usrCnt;
    }

    public List getSysMsg() {
        return sysMsg;
    }

    public void setSysMsg(List sysMsg) {
        this.sysMsg = sysMsg;
    }
}
