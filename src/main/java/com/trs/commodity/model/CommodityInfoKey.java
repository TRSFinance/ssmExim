package com.trs.commodity.model;

public class CommodityInfoKey {
    private Integer yr;

    private String reporterCode;

    private String partnerCode;

    private String tradeFlowCode;

    private String commodityCode;

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

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode == null ? null : commodityCode.trim();
    }

	@Override
	public String toString() {
		return "CommodityInfoKey [yr=" + yr + ", reporterCode=" + reporterCode
				+ ", partnerCode=" + partnerCode + ", tradeFlowCode="
				+ tradeFlowCode + ", commodityCode=" + commodityCode + "]";
	}
    
    
}