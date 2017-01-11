package com.trs.ser.service;

import com.trs.ser.model.ServiceInfo;
import com.trs.ser.model.ServiceInfoKey;

public interface ServiceInfoService{

	/**
	 * 添加一个对象
	 * @param record
	 */
	public void add(ServiceInfo record);
	/**
	 * 删除一个对象
	 * @param key
	 */
	public void del(ServiceInfoKey key);
	/**
	 * 修改一个对象
	 * @param record
	 */
	public void update(ServiceInfo record);
	/**
	 * 查找一个对象
	 * @param key
	 * @return
	 */
	public ServiceInfo find(ServiceInfoKey key);
	
}
