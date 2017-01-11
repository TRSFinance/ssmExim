package com.trs.commodity.service;

import java.util.List;
import com.trs.commodity.model.CommodityManualDict;

public interface CommodityManualDictService{

	/**
	 * 添加一个对象
	 * @param record
	 */
	public void add(CommodityManualDict record);
	/**
	 * 删除一个对象
	 * @param key
	 */
	public void del(String key);
	/**
	 * 修改一个对象
	 * @param record
	 */
	public void update(CommodityManualDict record);
	/**
	 * 查找一个对象
	 * @param key
	 * @return
	 */
	public CommodityManualDict find(String key);
	
	
	
	/**
	 * 查询所有记录
	 * @param record
	 * @return
	 */
	public List<CommodityManualDict> findAll();
	
	
}
