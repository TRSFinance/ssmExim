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
	
	/**
	 * 返回按指定条件查询的对象集合
	 * 可以设置CommodityInfoKey对象中的五个主键的某几个主键来组合查询(必须包括年份)
	 * @param key	主键对象
	 * @return
	 */
	public List<CommodityInfo> findCustom(CommodityInfoKey key);
	
	/** 修改报告国名称
	 * 
	 * @param rtName
	 * @param rtCode
	 */
	public void updateRtName(String rtName,int yr,String rtCode,String ptCode);
	/** 修改对象国名称
	 * 
	 * @param ptName
	 * @param ptCode
	 */
	public void updatePtName(String ptName,int yr,String rtCode,String ptCode);
	
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
	 * 返回还按贸易份额排名的对象集合
	 * @return
	 */
	public List<CommodityInfo> findRateOrderList_noHandle();
	
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
	
	//初次加工数据的方法-----------action------------
	/**
	 * 返回处理后的对象(处理字段：贸易金额较上期的增量和增速)
	 * @param comm	传入的贸易数据对象
	 * @return	处理后的贸易数据对象
	 */
	public CommodityInfo zengliangAndzengsu(CommodityInfo comm);
	/**
	 * 返回处理后的对象(处理字段：占报告国贸易份额)
	 * @param comm	传入的贸易数据对象
	 * @return	处理后的贸易数据对象
	 */
	public CommodityInfo rtComtradeFene(CommodityInfo comm);
	
	/**
	 * 更新初次加工处理后的对象集合
	 * 更新字段(贸易额较上期(增量,增速),占报告国贸易份额),共更新三个字段
	 * @param list
	 */
	public void updateList_handleFirst(List<CommodityInfo> list);
	//初次加工数据的方法-----------end------------
	
	//第二次加工数据的方法-----------action------------
	//如果是2013年的数据该字段REPORTER_RATE_CHANGED可与本年贸易份额REPORTER_TRADE_RATE相等,该注释有问题,待修改。。。
	/**
	 * 返回处理后的对象(处理字段：占报告国贸易份额变化)
	 * @param comm	传入的贸易数据对象
	 * @return	处理后的贸易数据对象
	 */
	public CommodityInfo rtRateChange(CommodityInfo comm);
	
	/**返回处理后的对象集合(处理字段：占报告国贸易份额排名)
	 * 
	 * @param yr
	 * @param reporterCode
	 * @param commodityCode
	 * @param tradeFlowCode
	 * @return
	 */
	public List<CommodityInfo> rtRateOrder(int yr, String reporterCode,
			String commodityCode, String tradeFlowCode);
	
	/**
	 * 更新第二次加工处理后的对象集合
	 * 更新字段(占报告国贸易份额(排名,较上期变化)),共更新两个字段
	 * @param list
	 */
	public void updateList_handleSecond(List<CommodityInfo> list);
	
	//第二次加工数据的方法-----------end------------
	
	//第三次加工数据的方法-----------action------------	
	/**
	 * 返回处理后的对象(处理字段：占对象国(贸易份额,贸易份额排名,贸易份额较上期变化))
	 * (ps:如果对象国不是报告国,则占对象国贸易份额排名设为999,防止影响后期前端做查询)	
	 * @param comm	传入的贸易数据对象
	 * @return	处理后的贸易数据对象
	 */
	public CommodityInfo ptRateOrder(CommodityInfo comm);
	/**
	 * 更新第三次加工处理后的对象集合
	 * 更新字段(占对象国(贸易份额,贸易份额排名,贸易份额较上期变化))
	 * 共更新三个字段(ps:如果对象国不是报告国,则占对象国贸易份额排名设为999,防止影响后期前端做查询)
	 * @param list
	 */
	public void updateList_handleThird(List<CommodityInfo> list);

	//第三次加工数据的方法-----------end------------
	
	
	
}
