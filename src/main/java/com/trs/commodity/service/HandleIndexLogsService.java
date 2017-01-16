package com.trs.commodity.service;

import com.trs.commodity.model.HandleIndexLogs;

public interface HandleIndexLogsService {

	/**
	 * 添加一个对象
	 * @param record
	 */
	public void add(HandleIndexLogs record);
	/**
	 * 删除一个对象
	 * @param key
	 */
	public void del(int key);
	/**
	 * 修改一个对象
	 * @param record
	 */
	public void update(HandleIndexLogs record);
	/**
	 * 查找一个对象
	 * @param key
	 * @return
	 */
	public HandleIndexLogs find(int key);
	
	
	
}
