package com.trs.commodity.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.trs.commodity.model.CommodityInfo;
import com.trs.commodity.model.CommodityInfoKey;

public interface CommodityInfoMapper {
	/**
	 * 通过年份，报告国，对象国，贸易方向，商品编码来删除指定的一条记录
	 * @param key
	 */
    void deleteByPrimaryKey(CommodityInfoKey key);

    /**
     * 通过年份，报告国，对象国三个主键来删除指定的多条记录
     * @param key
     */
    void deleteByPartPrimaryKey(CommodityInfoKey key);
    
    int insert(CommodityInfo record);

    int insertSelective(CommodityInfo record);

    CommodityInfo selectByPrimaryKey(CommodityInfoKey key);
    
    List<CommodityInfo> findAll();
    
    List<CommodityInfo> selectCustom(CommodityInfoKey key);

    int updateByPrimaryKeySelective(CommodityInfo record);

    int updateByPrimaryKey(CommodityInfo record);
    
//    void add(CommodityInfo record);
    
    void insertList(List<CommodityInfo> list);
    
    @Update("update commodity_info set reporter=#{rtName} where yr=#{yr} and reporter_code=#{rtCode} and partner_code=#{ptCode}")
    void updateRtName(@Param("rtName")String rtName,@Param("yr")int yr,@Param("rtCode")String rtCode,@Param("ptCode")String ptCode);
    
    @Update("update commodity_info set partner=#{ptName} where yr=#{yr} and reporter_code=#{rtCode} and partner_code=#{ptCode}")
    void updatePtName(@Param("ptName")String ptName,@Param("yr")int yr,@Param("rtCode")String rtCode,@Param("ptCode")String ptCode);

    @Select("select * from commodity_info  where reporter_trade_rate is null ")
    @ResultMap("BaseResultMap")
    public List<CommodityInfo> findRtEmptyComtradeRate();
    
    @Select("select * from commodity_info  where partner_trade_rate is null ")
    @ResultMap("BaseResultMap")
    public List<CommodityInfo> findPtEmptyComtradeRate();
 
    public List<CommodityInfo> findRateOrderList(CommodityInfoKey key);
    
    @Select("select * from commodity_info where yr!=2012")
    @ResultMap("BaseResultMap")
    public List<CommodityInfo> findNo2012List();
    
    @Update("update commodity_info set reporter_rate_changed=reporter_trade_rate where yr=2013")
    public void process2013RateChange();
    
    public void updateList_handleFirst(List<CommodityInfo> list);
    
    public void updateList_handleSecond(List<CommodityInfo> list);
    
    public void updateList_handleThird(List<CommodityInfo> list);
    @Select("select * from commodity_info  where reporter_rate_order is null ")
    @ResultMap("BaseResultMap")
    public List<CommodityInfo> findRateOrderList_noHandle();
    
    
}