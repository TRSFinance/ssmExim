package com.trs.commodity.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.trs.commodity.dao.CommodityInfoMapper;
import com.trs.commodity.model.CommodityInfo;
import com.trs.commodity.model.CommodityInfoKey;
import com.trs.commodity.model.Procsvlogs;
import com.trs.commodity.service.CommodityInfoService;

@Service("commodityInfo")
public class CommodityInfoServiceImpl implements CommodityInfoService{

	private Logger log = Logger.getLogger(CommodityInfoServiceImpl.class);
	
    @Autowired
	protected CommodityInfoMapper dao;

	@Override
	public void add(CommodityInfo record) {
		dao.insert(record);
	}

	@Override
	public void del(CommodityInfoKey key) {
		dao.deleteByPrimaryKey(key);
	}

	@Override
	public void update(CommodityInfo record) {
		dao.updateByPrimaryKey(record);
	}

	@Override
	public CommodityInfo find(CommodityInfoKey key) {
		return dao.selectByPrimaryKey(key);
	}

	@Override
	public List<CommodityInfo> findAll() {		
		return dao.findAll();
	}

	@Override
	public void addList(List<CommodityInfo> list) {
		dao.insertList(list);
	}

	@Override
	public void delByProcsvlogs(Procsvlogs procsvlogs) {
		String[] partnerCodes = procsvlogs.getPartnerCode().split("&");		
		for(int i=0;i<partnerCodes.length;i++){
			CommodityInfoKey comm = new CommodityInfoKey();
			comm.setYr(procsvlogs.getYr());
			comm.setReporterCode(procsvlogs.getReporterCode());
			comm.setPartnerCode(partnerCodes[i]);
			dao.deleteByPartPrimaryKey(comm);
		}
	}

	@Override
	public List<CommodityInfo> findCustom(CommodityInfoKey key) {
		return dao.selectCustom(key);
	}

	@Override
	public void updateRtName(String rtName,int yr,String rtCode,String ptCode) {
		dao.updateRtName(rtName,yr,rtCode,ptCode);
	}
	
	@Override
	public void updatePtName(String ptName,int yr,String rtCode,String ptCode) {
		dao.updatePtName(ptName,yr,rtCode,ptCode);
	}

	@Override
	public List<CommodityInfo> findRtEmptyComtradeRate() {
		return dao.findRtEmptyComtradeRate();
	}

	@Override
	public List<CommodityInfo> findPtEmptyComtradeRate() {
		return dao.findPtEmptyComtradeRate();
	}

	@Override
	public List<CommodityInfo> findRateOrderList(CommodityInfoKey key) {
		return dao.findRateOrderList(key);
	}

	@Override
	public List<CommodityInfo> findNo2012List() {
		return dao.findNo2012List();
	}

	@Override
	public void process2013RateChange() {
		dao.process2013RateChange();		
	}


	//初次加工数据的方法-----------action------------
	/**
	 * 返回处理后的对象(处理字段：贸易金额较上期的增量和增速)
	 * @param comm	传入的贸易数据对象
	 * @return	处理后的贸易数据对象
	 */
	@Override
	public CommodityInfo zengliangAndzengsu(CommodityInfo comm){	
		
		if(comm==null){
//			log.info("对象为空,请检查传入对象。。。");
			return null;
		}
		//如果对象为2012的数据,则将贸易增量和增速设置为0
		if(comm.getYr()==2012){
			comm.setChangedSinceLastyear(0L);	 			
			comm.setRateSinceLastyear((double)0);
//			log.debug("对象为2012年的数据,设置默认值0(贸易金额较上期的增量和增速),返回。。。");
			return comm;	
		}
		//如果不是2012年的数据,则进行下面的计算
		CommodityInfoKey key = new CommodityInfoKey();
		key.setCommodityCode(comm.getCommodityCode());
		key.setPartnerCode(comm.getPartnerCode());
		key.setReporterCode(comm.getReporterCode());
		key.setTradeFlowCode(comm.getTradeFlowCode());
		key.setYr(comm.getYr()-1);
		CommodityInfo comm2 = find(key);//查找去年的记录
		//初始化贸易量
		long tradeValue1 = comm.getTradeValue();							
		long tradeValue2 = 0;
		//初始化贸易增量
		long tradeValueIncrement = 0;
		//初始化贸易增速
		double incrementRate = 0;
		if(comm2!=null){
			tradeValue2 = comm2.getTradeValue();
		}							
		//计算较去年的贸易增量
		tradeValueIncrement = tradeValue1-tradeValue2;										
		//计算贸易金额较去年的增速
		if(tradeValue2!=0){
			incrementRate = ((double)tradeValueIncrement)/tradeValue2;		
		}	
		comm.setChangedSinceLastyear(tradeValueIncrement);	 			
		comm.setRateSinceLastyear(incrementRate);
		return comm;		
	}
	
	/**
	 * 返回处理后的对象(处理字段：占报告国贸易份额)
	 * @param comm	传入的贸易数据对象
	 * @return	处理后的贸易数据对象
	 */
	@Override
	public CommodityInfo rtComtradeFene(CommodityInfo comm){

		if(comm==null){
			log.info("对象为空,请检查传入对象。。。");
			return null;
		}
		CommodityInfoKey key = new CommodityInfoKey();
		key.setCommodityCode(comm.getCommodityCode());
		key.setPartnerCode("0");
		key.setReporterCode(comm.getReporterCode());
		key.setTradeFlowCode(comm.getTradeFlowCode());
		key.setYr(comm.getYr());
		CommodityInfo commAll = find(key);		
		long tradeValue = 0;
		long tradeValueAll = 0;	
		double reporterTradeRate = 0;
		tradeValue = comm.getTradeValue();			
		if(commAll!=null){
			tradeValueAll = commAll.getTradeValue();
			if(tradeValueAll!=0){
				reporterTradeRate = ((double)tradeValue)/tradeValueAll;		
			}
		}
		comm.setReporterTradeRate(reporterTradeRate);				
		return comm;
	}
	
	@Override
	public void updateList_handleFirst(List<CommodityInfo> list) {
		dao.updateList_handleFirst(list);
	}
	//初次加工数据的方法-----------end------------
	
	//第二次加工数据的方法-----------action------------
	//如果是2013年的数据该字段REPORTER_RATE_CHANGED可与本年贸易份额REPORTER_TRADE_RATE相等,该注释有问题,待修改。。。
	/**
	 * 返回处理后的对象(处理字段：占报告国贸易份额变化)
	 * @param comm	传入的贸易数据对象
	 * @return	处理后的贸易数据对象
	 */
	@Override
	public CommodityInfo rtRateChange(CommodityInfo comm){
		
		if(comm==null){
			log.info("对象为空,请检查传入对象。。。");
			return null;
		}
		//如果对象为2012的数据,则将贸易增量和增速设置为0
		if(comm.getYr()==2012){
			comm.setReporterRateChanged((double)0);	 			
			log.debug("对象为2012年的数据,设置默认值0(占报告国贸易份额变化),返回。。。");
			return comm;	
		}
		//如果不是2012年的数据,则进行下面的计算		
		CommodityInfoKey key = new CommodityInfoKey();
		key.setCommodityCode(comm.getCommodityCode());
		key.setReporterCode(comm.getPartnerCode());
		key.setPartnerCode(comm.getReporterCode());
		key.setTradeFlowCode(comm.getTradeFlowCode());
		key.setYr(comm.getYr()-1);
		//查询去年的数据
		CommodityInfo commLastYear = find(key);
		double changeValue = 0;
		if(commLastYear!=null){
			changeValue = comm.getReporterTradeRate()-commLastYear.getReporterTradeRate();
		}
		comm.setReporterRateChanged(changeValue);
		return comm;
	}
	
	/**
	 * 返回处理后的对象集合(处理字段：占报告国贸易份额排名)
	 * @param comm	传入的贸易数据对象
	 * @return	处理后的贸易数据对象
	 */
	@Override
	public List<CommodityInfo> rtRateOrder(int yr,String reporterCode,String commodityCode,String tradeFlowCode){
		
		//初始化商品信息对象
		List<CommodityInfo> list = new ArrayList<CommodityInfo>();
		//初始化查询条件
		CommodityInfoKey key = new CommodityInfoKey();
		key.setYr(yr);
		key.setReporterCode(reporterCode);
		key.setCommodityCode(commodityCode);
		key.setTradeFlowCode(tradeFlowCode);
		//查找指定年份,报告国,贸易方向,商品编码的对象集合(由于报告国共有292个国家和地区,所以集合的size()就是292个)
		List<CommodityInfo> listCommInfo = findRateOrderList(key);
		
//		System.out.println(listCommInfo.size());//验证下
		
		//可能需要处理下当全部贸易量只是一个国家的情况,此时,当对象国为0时,设置为1还是设置为别的值,待续。。。。。
		if(listCommInfo!=null){
			for(int i=0;i<listCommInfo.size();i++){							
				CommodityInfo comm = listCommInfo.get(i);		
				comm.setReporterRateOrder(i+1);	
				list.add(comm);
			}		
		}	
		return list;
	}

	@Override
	public void updateList_handleSecond(List<CommodityInfo> list) {
		dao.updateList_handleSecond(list);
	}
	//第二次加工数据的方法-----------end------------
	
	//第三次加工数据的方法-----------action------------
	/**
	 * 返回处理后的对象(处理字段：占对象国(贸易份额,贸易份额排名,贸易份额较上期变化))
	 * (ps:如果对象国不是报告国,则占对象国贸易份额排名设为999,防止影响后期前端做查询)	
	 * @param comm	传入的贸易数据对象
	 * @return	处理后的贸易数据对象
	 */
	@Override
	public CommodityInfo ptRateOrder(CommodityInfo comm){
		
		if(comm==null){
			log.info("对象为空,请检查传入对象。。。");
			return null;
		}
		CommodityInfoKey key = new CommodityInfoKey();
		key.setYr(comm.getYr());
		key.setCommodityCode(comm.getCommodityCode());
		key.setReporterCode(comm.getPartnerCode());
		key.setPartnerCode(comm.getReporterCode());
		String tradeFlowCode= comm.getTradeFlowCode();
		String tradeFlowCode2="";
		if(tradeFlowCode.equals("1")){
			tradeFlowCode2="2";
		}else if(tradeFlowCode.equals("2")){
			tradeFlowCode2="1";
		}else if(tradeFlowCode.equals("3")){
			tradeFlowCode2="4";
		}else if(tradeFlowCode.equals("4")){
			tradeFlowCode2="3";
		}
		key.setTradeFlowCode(tradeFlowCode2);
		//查找相对贸易方式,报告国和对象国对象
		CommodityInfo ptComm = find(key);
		//如果相对的对象不为空,将相对对象字段的值插入到当前对象
		if(ptComm!=null){
			comm.setPartnerTradeRate(ptComm.getReporterTradeRate());
			comm.setPartnerRateOrder(ptComm.getReporterRateOrder());
			comm.setPartnerRateChanged(ptComm.getReporterRateChanged());
		}else{
			//如果相对的对象为空,将当前对象字段设置默认值
			comm.setPartnerTradeRate((double)0);
			//此处设置为999,防止影响后期前端做查询
			comm.setPartnerRateOrder(999);
			comm.setPartnerRateChanged((double)0);			
		}				
		return comm;
	}
	
	@Override
	public void updateList_handleThird(List<CommodityInfo> list) {
		dao.updateList_handleThird(list);
	}
	//第三次加工数据的方法-----------end------------
	
	public List<CommodityInfo> findRateOrderList_noHandle(){
		return dao.findRateOrderList_noHandle();		
	}
	
}
