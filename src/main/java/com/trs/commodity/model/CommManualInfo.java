package com.trs.commodity.model;

import java.util.Date;

public class CommManualInfo extends CommManualInfoKey {
    private String classification;

    private String reporter;

    private String partner;

    private Long tradeValue;

    private Long netweight;

    private String qtyunitcode;

    private Long altqtyunit;

    private Double changedSinceLastyear;

    private Double rateSinceLastyear;

    private Double reporterTradeRate;

    private Double partnerTradeRate;

    private Integer reporterRateOrder;

    private Double reporterRateChanged;

    private Integer partnerRateOrder;

    private Double partnerRateChanged;

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

    public Long getTradeValue() {
        return tradeValue;
    }

    public void setTradeValue(Long tradeValue) {
        this.tradeValue = tradeValue;
    }

    public Long getNetweight() {
        return netweight;
    }

    public void setNetweight(Long netweight) {
        this.netweight = netweight;
    }

    public String getQtyunitcode() {
        return qtyunitcode;
    }

    public void setQtyunitcode(String qtyunitcode) {
        this.qtyunitcode = qtyunitcode == null ? null : qtyunitcode.trim();
    }

    public Long getAltqtyunit() {
        return altqtyunit;
    }

    public void setAltqtyunit(Long altqtyunit) {
        this.altqtyunit = altqtyunit;
    }

    public Double getChangedSinceLastyear() {
        return changedSinceLastyear;
    }

    public void setChangedSinceLastyear(Double changedSinceLastyear) {
        this.changedSinceLastyear = changedSinceLastyear;
    }

    public Double getRateSinceLastyear() {
        return rateSinceLastyear;
    }

    public void setRateSinceLastyear(Double rateSinceLastyear) {
        this.rateSinceLastyear = rateSinceLastyear;
    }

    public Double getReporterTradeRate() {
        return reporterTradeRate;
    }

    public void setReporterTradeRate(Double reporterTradeRate) {
        this.reporterTradeRate = reporterTradeRate;
    }

    public Double getPartnerTradeRate() {
        return partnerTradeRate;
    }

    public void setPartnerTradeRate(Double partnerTradeRate) {
        this.partnerTradeRate = partnerTradeRate;
    }

    public Integer getReporterRateOrder() {
        return reporterRateOrder;
    }

    public void setReporterRateOrder(Integer reporterRateOrder) {
        this.reporterRateOrder = reporterRateOrder;
    }

    public Double getReporterRateChanged() {
        return reporterRateChanged;
    }

    public void setReporterRateChanged(Double reporterRateChanged) {
        this.reporterRateChanged = reporterRateChanged;
    }

    public Integer getPartnerRateOrder() {
        return partnerRateOrder;
    }

    public void setPartnerRateOrder(Integer partnerRateOrder) {
        this.partnerRateOrder = partnerRateOrder;
    }

    public Double getPartnerRateChanged() {
        return partnerRateChanged;
    }

    public void setPartnerRateChanged(Double partnerRateChanged) {
        this.partnerRateChanged = partnerRateChanged;
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