package com.trs.commodity.service;

import java.util.List;

import com.trs.commodity.model.Procsvlogs;
import com.trs.commodity.model.ProcsvlogsKey;


public interface ProcsvlogsService{
	
	/**
	 * 添加一个对象
	 * @param record
	 */
	public void add(Procsvlogs record);
	/**
	 * 删除一个对象
	 * @param key
	 */
	public void del(ProcsvlogsKey key);
	/**
	 * 修改一个对象
	 * @param record
	 */
	public void update(Procsvlogs record);
	/**
	 * 查找一个对象
	 * @param key
	 * @return
	 */
	public Procsvlogs find(ProcsvlogsKey key);
	
	/**
	 * 判断是否存在该条记录,存在返回true,否则返回false
	 * @param key
	 * @return
	 */
	boolean isExist(ProcsvlogsKey key);
	
	/**
	 * 返回采集受限文件的对象集合
	 * @return
	 */
	public List<Procsvlogs> findFileLimited();
	
}
