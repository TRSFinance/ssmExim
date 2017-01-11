package com.trs.ser.model;

public class ServiceDict {
    private String serCode;

    private String serName;

    private String serChnName;

    private String serParentCode;

    public String getSerCode() {
        return serCode;
    }

    public void setSerCode(String serCode) {
        this.serCode = serCode == null ? null : serCode.trim();
    }

    public String getSerName() {
        return serName;
    }

    public void setSerName(String serName) {
        this.serName = serName == null ? null : serName.trim();
    }

    public String getSerChnName() {
        return serChnName;
    }

    public void setSerChnName(String serChnName) {
        this.serChnName = serChnName == null ? null : serChnName.trim();
    }

    public String getSerParentCode() {
        return serParentCode;
    }

    public void setSerParentCode(String serParentCode) {
        this.serParentCode = serParentCode == null ? null : serParentCode.trim();
    }
}