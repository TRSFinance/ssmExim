package com.trs.common.mapper;

public interface BaseDao {

//操作单个对象
	/**
	 * 添加一个对象
	 * @param obj
	 */
	public void insert(Object obj);
	/**
	 * 删除一个对象
	 * @param obj
	 */
    public void deleteByPrimaryKey(Object obj);    
	/**
	 * 修改一个对象
	 * @param obj
	 */
    public void updateByPrimaryKey(Object obj);    
	/**
	 * 查找一个对象
	 * @param obj
	 * @return
	 */
    public Object selectByPrimaryKey(Object obj);
    
////操作多个对象    
//	/**
//	 * 添加多个对象
//	 * @param List
//	 */
//	public void addList(List<Object> List);
//
//	/**
//	 * 删除多个对象
//	 * @param obj
//	 */
//    public void delList(Object obj);
//
//	/**
//	 * 修改多个对象
//	 * @param obj
//	 */
//    public void updateList(Object obj);
//
//	/**
//	 * 查找多个对象
//	 * @param obj
//	 * @return
//	 */
//    public List<?> findList(Object obj);
    
}
