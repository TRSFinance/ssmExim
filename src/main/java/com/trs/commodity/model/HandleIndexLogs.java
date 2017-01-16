package com.trs.commodity.model;

public class HandleIndexLogs {
    private Integer handleId;

    private Integer yrIndex;

    private Integer rtIndex;

    private Integer ptIndex;

    private Integer commIndex;

    private Integer folwIndex;

    private String ps;

    public Integer getHandleId() {
        return handleId;
    }

    public void setHandleId(Integer handleId) {
        this.handleId = handleId;
    }

    public Integer getYrIndex() {
        return yrIndex;
    }

    public void setYrIndex(Integer yrIndex) {
        this.yrIndex = yrIndex;
    }

    public Integer getRtIndex() {
        return rtIndex;
    }

    public void setRtIndex(Integer rtIndex) {
        this.rtIndex = rtIndex;
    }

    public Integer getPtIndex() {
        return ptIndex;
    }

    public void setPtIndex(Integer ptIndex) {
        this.ptIndex = ptIndex;
    }

    public Integer getCommIndex() {
        return commIndex;
    }

    public void setCommIndex(Integer commIndex) {
        this.commIndex = commIndex;
    }

    public Integer getFolwIndex() {
        return folwIndex;
    }

    public void setFolwIndex(Integer folwIndex) {
        this.folwIndex = folwIndex;
    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps == null ? null : ps.trim();
    }
}