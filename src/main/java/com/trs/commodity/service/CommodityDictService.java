package com.trs.commodity.service;

import java.util.List;

import com.trs.commodity.model.CommodityDict;

public interface CommodityDictService{

	/**
	 * 添加一个对象
	 * @param record
	 */
	public void add(CommodityDict record);
	/**
	 * 删除一个对象
	 * @param key
	 */
	public void del(String key);
	/**
	 * 修改一个对象
	 * @param record
	 */
	public void update(CommodityDict record);
	/**
	 * 查找一个对象
	 * @param key
	 * @return
	 */
	public CommodityDict find(String key);
	
	
	/**
	 * 查询子节点的商品编码
	 * @param record
	 * @return
	 */
	List<CommodityDict> findChildren(CommodityDict record);
	
	List<CommodityDict> findAll();	
	//测试
	List<CommodityDict> custom(String record);
	
	
	
}
