package com.trs.ser.model;

import java.util.Date;

public class ServiceInfo extends ServiceInfoKey {
    private String classification;

    private String reporter;

    private String partner;

    private String aggregateLevel;

    private String isLeafCode;

    private Long tradeValue;

    private Date crtime;

    private Date updatetime;

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification == null ? null : classification.trim();
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter == null ? null : reporter.trim();
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner == null ? null : partner.trim();
    }

    public String getAggregateLevel() {
        return aggregateLevel;
    }

    public void setAggregateLevel(String aggregateLevel) {
        this.aggregateLevel = aggregateLevel == null ? null : aggregateLevel.trim();
    }

    public String getIsLeafCode() {
        return isLeafCode;
    }

    public void setIsLeafCode(String isLeafCode) {
        this.isLeafCode = isLeafCode == null ? null : isLeafCode.trim();
    }

    public Long getTradeValue() {
        return tradeValue;
    }

    public void setTradeValue(Long tradeValue) {
        this.tradeValue = tradeValue;
    }

    public Date getCrtime() {
        return crtime;
    }

    public void setCrtime(Date crtime) {
        this.crtime = crtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}