package com.trs.commodity.controller;

import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.trs.commodity.model.CommodityDict;
import com.trs.commodity.model.CommodityInfo;
import com.trs.commodity.model.CommodityInfoKey;
import com.trs.commodity.model.CountryDict;
import com.trs.commodity.service.CommodityDictService;
import com.trs.commodity.service.CommodityInfoService;
import com.trs.commodity.service.CountryDictService;

public class CommodityInfoProcessFirst{

	@Autowired
	private CommodityInfoService commInfoService ;
	
	@Autowired
	private CountryDictService countryDictService ;
	
	@Autowired
	private	CommodityDictService commDictService;
	
	
	/**
	 * 插入国家中文名称
	 */
	public void addCountryName(){
	
		List<CountryDict> list = countryDictService.findAll();	
		for(int i=0;i<list.size();i++){
			String countryCode = list.get(i).getCountryCode();
			String chnName = list.get(i).getCountryChnName();
			commInfoService.updateRtName(chnName, countryCode);
			commInfoService.updatePtName(chnName, countryCode);			
		}			
	}


	
	//计算报告国贸易额占全球贸易份额
	public void rtComtradeFene(CommodityInfo comm){

		if(comm!=null){			
			CommodityInfoKey key = new CommodityInfoKey();
			key.setCommodityCode(comm.getCommodityCode());
			key.setPartnerCode("0");
			key.setReporterCode(comm.getReporterCode());
			key.setTradeFlowCode(comm.getTradeFlowCode());
			key.setYr(comm.getYr());
			CommodityInfo commAll = (CommodityInfo) commInfoService.find(key);		
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
			commInfoService.update(comm);				
		}
	}
	
	//计算贸易量占全球贸易份额
	public void baogaoguofene(){		
		List<CommodityInfo> list = commInfoService.findRtEmptyComtradeRate();		
		for(int i=0;i<list.size();i++){
			rtComtradeFene(list.get(i));
		}		
	}

	@Test
	//计算对象国贸易额占全球贸易份额
	public void ptComtradeFene(){
		//查找对象国贸易额占全球贸易份额字段为空的对象集合
		List<CommodityInfo> list = commInfoService.findPtEmptyComtradeRate();
		for(int i=0;i<list.size();i++){
			
			CommodityInfo comm = list.get(i);
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
			CommodityInfo ptComm = (CommodityInfo) commInfoService.find(key);
			//先将占报告国贸易份额字段计算完，然后取相应贸易流方向的值放到该对象中
			if(ptComm!=null){
				comm.setPartnerTradeRate(ptComm.getReporterTradeRate());
				commInfoService.update(comm);
			}	
		}
		System.out.println("字段[ partnerTradeRate ]处理完毕");	
	}
	
	@Test
	public void incrementSinceLastyear(){
		
		List<CountryDict> list_country = countryDictService.findAll();
		List<CommodityDict> list_commDict = commDictService.findAll();
		
		for(int ps=2013;ps<2014;ps++){
			for(int r=0;r<list_country.size();r++){
				for(int p=0;p<list_country.size();p++){
					for(int flow=1;flow<=4;flow++){
//						List<CommodityInfo> list_update = new ArrayList<CommodityInfo>();
						for(int cc=0;cc<list_commDict.size();cc++){						
							String rtCode = list_country.get(r).getCountryCode();
							String ptCode = list_country.get(p).getCountryCode();
							String commodityCode = list_commDict.get(cc).getCommodityCode();	
							CommodityInfoKey key = new CommodityInfoKey();
							key.setReporterCode(rtCode);
							key.setPartnerCode(ptCode);
							key.setCommodityCode(commodityCode);
							key.setTradeFlowCode(flow+"");
							key.setYr(ps);
							CommodityInfo comm1 = (CommodityInfo) commInfoService.find(key);		
							key.setYr(ps-1);
							CommodityInfo comm2 = (CommodityInfo) commInfoService.find(key);
							//初始化贸易量
							long tradeValue1 = 0;							
							long tradeValue2 = 0;
							//初始化贸易增量
							double tradeValueIncrement = 0;
							//初始化贸易增速
							double incrementRate = 0;
							if(comm1!=null){
								tradeValue1 = comm1.getTradeValue();
							}
							if(comm2!=null){
								tradeValue2 = comm2.getTradeValue();
							}							
							//计算贸易增量
							tradeValueIncrement = (double)(tradeValue1-tradeValue2);										
							//计算贸易金额较上期增速
							if(tradeValue2!=0){
								incrementRate = tradeValueIncrement/tradeValue2;		
							}													
							//如果记录不为空，则进行更新操作
							if(comm1!=null){
								comm1.setChangedSinceLastyear(tradeValueIncrement);	 			
								comm1.setRateSinceLastyear(incrementRate);	
								//添加到list中,先一个一个更新，没调试好批量更新的办法
								commInfoService.update(comm1);	
							}
							//计算本年贸易金额占全球贸易比率
							rtComtradeFene(comm1);	
						}	
					}					
				}
				
			}
			
		}
	}
	
	
	@Test
	public void test(){
			
		List<CommodityDict> list_commDict = commDictService.findAll();
		for(int cc=0;cc<list_commDict.size();cc++){		

			String rtCode = "156";
			String ptCode = "4";
			String commodityCode = list_commDict.get(cc).getCommodityCode();
			CommodityInfoKey key = new CommodityInfoKey();
			key.setReporterCode(rtCode);
			key.setPartnerCode(ptCode);
			key.setCommodityCode(commodityCode);
			key.setTradeFlowCode("2");
			key.setYr(2012);
			CommodityInfo comm1 = (CommodityInfo) commInfoService.find(key);
			key.setYr(2012-1);
			CommodityInfo comm2 = (CommodityInfo) commInfoService.find(key);
			System.out.println(comm1==null);
			long tradeValue1 = 0;							
			long tradeValue2 = 0;
			//初始化贸易增量
			double tradeValueIncrement = 0;
			//初始化贸易增速
			double incrementRate = 0;
			if(comm1!=null){
				tradeValue1 = comm1.getTradeValue();
			}
			if(comm2!=null){
				tradeValue2 = comm2.getTradeValue();
			}							
			tradeValueIncrement = (double)(tradeValue1-tradeValue2);										
			if(tradeValue2!=0){
				//计算贸易金额较上期增速
				incrementRate = tradeValueIncrement/tradeValue2;		
			}													
			//如果记录不为空，则进行更新操作
			if(comm1!=null){
				comm1.setChangedSinceLastyear(tradeValueIncrement);	 			
				comm1.setRateSinceLastyear(incrementRate);	
				//添加到list中,先一个一个更新，没调试好批量更新的办法
				commInfoService.update(comm1);	
			}
			//计算本年贸易金额占全球贸易比率
			rtComtradeFene(comm1);
		}
	}

	
	
	
}
