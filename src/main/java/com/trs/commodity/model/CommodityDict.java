package com.trs.commodity.model;

public class CommodityDict {
    private String commodityCode;

    private String commodityName;

    private String commodityChnName;

    private String commodityParentCode;

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode == null ? null : commodityCode.trim();
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName == null ? null : commodityName.trim();
    }

    public String getCommodityChnName() {
        return commodityChnName;
    }

    public void setCommodityChnName(String commodityChnName) {
        this.commodityChnName = commodityChnName == null ? null : commodityChnName.trim();
    }

    public String getCommodityParentCode() {
        return commodityParentCode;
    }

    public void setCommodityParentCode(String commodityParentCode) {
        this.commodityParentCode = commodityParentCode == null ? null : commodityParentCode.trim();
    }
}