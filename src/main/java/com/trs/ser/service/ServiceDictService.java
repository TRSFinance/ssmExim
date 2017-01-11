package com.trs.ser.service;

import com.trs.ser.model.ServiceDict;

public interface ServiceDictService{

	
	/**
	 * 添加一个对象
	 * @param record
	 */
	public void add(ServiceDict record);
	/**
	 * 删除一个对象
	 * @param key
	 */
	public void del(String key);
	/**
	 * 修改一个对象
	 * @param record
	 */
	public void update(ServiceDict record);
	/**
	 * 查找一个对象
	 * @param key
	 * @return
	 */
	public ServiceDict find(String key);
	
	
	
	
}
