package com.trs.ser.model;

public class ServiceInfoKey {
    private Integer yr;

    private String reporterCode;

    private String partnerCode;

    private String tradeFlowCode;

    private String serviceCode;

    public Integer getYr() {
        return yr;
    }

    public void setYr(Integer yr) {
        this.yr = yr;
    }

    public String getReporterCode() {
        return reporterCode;
    }

    public void setReporterCode(String reporterCode) {
        this.reporterCode = reporterCode == null ? null : reporterCode.trim();
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode == null ? null : partnerCode.trim();
    }

    public String getTradeFlowCode() {
        return tradeFlowCode;
    }

    public void setTradeFlowCode(String tradeFlowCode) {
        this.tradeFlowCode = tradeFlowCode == null ? null : tradeFlowCode.trim();
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode == null ? null : serviceCode.trim();
    }
}