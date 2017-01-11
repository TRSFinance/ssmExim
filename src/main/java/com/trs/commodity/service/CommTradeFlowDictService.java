package com.trs.commodity.service;

import com.trs.commodity.model.CommTradeFlowDict;


public interface CommTradeFlowDictService{

	/**
	 * 添加一个对象
	 * @param record
	 */
	public void add(CommTradeFlowDict record);
	/**
	 * 删除一个对象
	 * @param key
	 */
	public void del(String key);
	/**
	 * 修改一个对象
	 * @param record
	 */
	public void update(CommTradeFlowDict record);
	/**
	 * 查找一个对象
	 * @param key
	 * @return
	 */
	public CommTradeFlowDict find(String key);
	
}
