package com.trs.commodity.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.trs.commodity.model.CommodityDict;

public interface CommodityDictMapper{
	
    int deleteByPrimaryKey(String commodityCode);

    int insert(CommodityDict record);

    int insertSelective(CommodityDict record);

    CommodityDict selectByPrimaryKey(String commodityCode);

    int updateByPrimaryKeySelective(CommodityDict record);

    int updateByPrimaryKey(CommodityDict record);
	
	public List<CommodityDict> findChildren(CommodityDict record);
	
	@Select("select * from commodity_dict where is_leaf_code=#{commodityCode}")
    @ResultMap("BaseResultMap")
	public List<CommodityDict> custom(@Param("commodityCode")String commodityCode);
	
	@Select("select * from Commodity_dict")
	@ResultMap("BaseResultMap")
	public List<CommodityDict> findAll();
	
}