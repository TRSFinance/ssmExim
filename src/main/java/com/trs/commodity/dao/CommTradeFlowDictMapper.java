package com.trs.commodity.dao;

import com.trs.commodity.model.CommTradeFlowDict;

public interface CommTradeFlowDictMapper{

    int deleteByPrimaryKey(String tradeFlowCode);

    int insert(CommTradeFlowDict record);

    int insertSelective(CommTradeFlowDict record);

    CommTradeFlowDict selectByPrimaryKey(String tradeFlowCode);

    int updateByPrimaryKeySelective(CommTradeFlowDict record);

    int updateByPrimaryKey(CommTradeFlowDict record);
    
    
}