package com.trs.commodity.service;

import java.util.List;
import com.trs.commodity.model.CountryDict;

public interface CountryDictService {
	
	/**
	 * 添加一个对象
	 * @param record
	 */
	public void add(CountryDict record);
	/**
	 * 删除一个对象
	 * @param key
	 */
	public void del(String key);
	/**
	 * 修改一个对象
	 * @param record
	 */
	public void update(CountryDict record);
	/**
	 * 查找一个对象
	 * @param key
	 * @return
	 */
	public CountryDict find(String key);
	/**
	 * 返回报告国集合
	 * @return
	 */
	public List<CountryDict> findReporters();
	/**
	 * 返回对象国集合
	 * @return
	 */
	public List<CountryDict> findPartners();
	/**
	 * 查询所有的记录
	 * @return
	 */
	public List<CountryDict> findAll();
	
	
	
	
	

	
}
