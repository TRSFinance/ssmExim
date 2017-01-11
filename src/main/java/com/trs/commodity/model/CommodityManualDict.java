package com.trs.commodity.model;

public class CommodityManualDict {
    private String comManualCode;

    private String comManualName;

    private String comCodeList;

    public String getComManualCode() {
        return comManualCode;
    }

    public void setComManualCode(String comManualCode) {
        this.comManualCode = comManualCode == null ? null : comManualCode.trim();
    }

    public String getComManualName() {
        return comManualName;
    }

    public void setComManualName(String comManualName) {
        this.comManualName = comManualName == null ? null : comManualName.trim();
    }

    public String getComCodeList() {
        return comCodeList;
    }

    public void setComCodeList(String comCodeList) {
        this.comCodeList = comCodeList == null ? null : comCodeList.trim();
    }
}