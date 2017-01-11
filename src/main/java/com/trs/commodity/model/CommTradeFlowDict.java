package com.trs.commodity.model;

public class CommTradeFlowDict {
    private String tradeFlowCode;

    private String tradeFlowName;

    private String tradeFlowChnName;

    public String getTradeFlowCode() {
        return tradeFlowCode;
    }

    public void setTradeFlowCode(String tradeFlowCode) {
        this.tradeFlowCode = tradeFlowCode == null ? null : tradeFlowCode.trim();
    }

    public String getTradeFlowName() {
        return tradeFlowName;
    }

    public void setTradeFlowName(String tradeFlowName) {
        this.tradeFlowName = tradeFlowName == null ? null : tradeFlowName.trim();
    }

    public String getTradeFlowChnName() {
        return tradeFlowChnName;
    }

    public void setTradeFlowChnName(String tradeFlowChnName) {
        this.tradeFlowChnName = tradeFlowChnName == null ? null : tradeFlowChnName.trim();
    }
}