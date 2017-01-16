package com.trs.commodity.service;

import com.trs.commodity.model.CommManualInfo;
import com.trs.commodity.model.CommManualInfoKey;


public interface CommManualInfoService{

	/**
	 * 添加一个对象
	 * @param record
	 */
	public void add(CommManualInfo record);
	/**
	 * 删除一个对象
	 * @param key
	 */
	public void del(CommManualInfoKey key);
	/**
	 * 修改一个对象
	 * @param record
	 */
	public void update(CommManualInfo record);
	/**
	 * 查找一个对象
	 * @param key
	 * @return
	 */
	public CommManualInfo find(CommManualInfoKey key);
	
	
}
