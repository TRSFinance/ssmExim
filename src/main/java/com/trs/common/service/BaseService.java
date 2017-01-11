package com.trs.common.service;


public interface BaseService {

//操作一个对象：
	/**
	 * 添加一个对象
	 * @param obj
	 */
	public void add(Object obj);
	/**
	 * 删除一个对象
	 * @param obj
	 */
	public void del(Object obj);
	/**
	 * 修改一个对象
	 * @param obj
	 */
	public void update(Object obj);
	/**
	 * 查找一个对象
	 * @param obj
	 * @return
	 */
	public Object find(Object obj);
	
////操作多个对象：	
//	/**
//	 * 添加多个对象
//	 * @param collection
//	 */
//	public void addList(List<Object> list);
//	/**
//	 * 删除多个对象
//	 * @param obj
//	 */
//	public void delList(Object obj);
//	/**
//	 * 修改多个对象
//	 * @param obj
//	 */
//	public void updateList(Object obj);
//	/**
//	 * 查找多个对象
//	 * @param obj
//	 * @return
//	 */
//	public List<?> findList(Object obj);
	
}
