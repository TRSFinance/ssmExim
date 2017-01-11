package com.trs.commodity.service;

import java.util.List;

import com.trs.commodity.model.CommodityInfo;
import com.trs.commodity.model.CommodityInfoKey;
import com.trs.commodity.model.Procsvlogs;

public interface CommodityInfoService{

	
	/**
	 * 添加一个对象
	 * @param record
	 */
	public void add(CommodityInfo record);
	/**
	 * 删除一个对象
	 * @param key
	 */
	public void del(CommodityInfoKey key);
	/**
	 * 修改一个对象
	 * @param record
	 */
	public void update(CommodityInfo record);
	/**
	 * 查找一个对象
	 * @param key
	 * @return
	 */
	public CommodityInfo find(CommodityInfoKey key);

	/**
	 * 查询所有的记录
	 * @return
	 */
	public List<CommodityInfo> findAll();
	/**
	 * 批量插入多条记录
	 * @param list
	 */
	public void addList(List<CommodityInfo> list);

	/**
	 * 通过年份，报告国，对象国三个主键来删除指定的多条记录
	 * @param procsvlogs
	 */
	public void delByProcsvlogs(Procsvlogs procsvlogs);
	

	public List<CommodityInfo> findCustom(CommodityInfoKey key);
	
	/** 修改报告国名称
	 * 
	 * @param rtName
	 * @param rtCode
	 */
	public void updateRtName(String rtName,String rtCode);
	/** 修改对象国名称
	 * 
	 * @param ptName
	 * @param ptCode
	 */
	public void updatePtName(String ptName,String ptCode);
	
	/**
	 * 返回还没有计算占报告国国贸易份额的对象集合
	 * @return
	 */
	public List<CommodityInfo> findRtEmptyComtradeRate();
	
	/**
	 * 返回还没有计算占对象国贸易份额的对象集合
	 * @return
	 */
	public List<CommodityInfo> findPtEmptyComtradeRate();
	
	/**
	 * 返回按贸易份额排名的对象集合
	 * @return
	 */
	public List<CommodityInfo> findRateOrderList(CommodityInfoKey key);
	
	/**
	 * 返回没有2012年的数据集合
	 * @return
	 */
	public List<CommodityInfo> findNo2012List();
	
	
	public void process2013RateChange();
	
	public void updateCommCode(int yr,String flow,String rtCode,String ptCode,String commCode,String commCode2);
	
}
