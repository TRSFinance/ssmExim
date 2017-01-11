package com.trs.commodity.model;

import java.util.Date;

public class Procsvlogs extends ProcsvlogsKey {
    private Date startTime;

    private Date finishTime;

    private String status;

    private String islimit;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getIslimit() {
        return islimit;
    }

    public void setIslimit(String islimit) {
        this.islimit = islimit == null ? null : islimit.trim();
    }
}