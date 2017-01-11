package com.trs.commodity.dao;

import java.util.List;

import com.trs.commodity.model.CommodityManualDict;

public interface CommodityManualDictMapper {
    void deleteByPrimaryKey(String comManualCode);

    void insert(CommodityManualDict record);

    void insertSelective(CommodityManualDict record);

    CommodityManualDict selectByPrimaryKey(String comManualCode);

    void updateByPrimaryKeySelective(CommodityManualDict record);

    void updateByPrimaryKey(CommodityManualDict record);
    
    List<CommodityManualDict> findAll();
}