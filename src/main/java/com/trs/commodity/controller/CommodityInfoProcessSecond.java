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



public class CommodityInfoProcessSecond {

	
	@Autowired
	private CommodityInfoService commInfoService ;
	
	@Autowired
	private CountryDictService countryDictService ;
	
	@Autowired
	private	CommodityDictService commDictService;
	
	@Test
	public void rtRateOrder(){
				
		List<CountryDict> listCountry = countryDictService.findAll();		
		List<CommodityDict> list_commDict = commDictService.findAll();		
		for(int ps=2012;ps<2016;ps++){
			for(int r=0;r<listCountry.size();r++){
				for(int flow=1;flow<=4;flow++){
					for(int cc=0;cc<list_commDict.size();cc++){
						CommodityInfoKey key = new CommodityInfoKey();
						key.setYr(ps);
						key.setReporterCode(listCountry.get(r).getCountryCode());
						key.setCommodityCode(list_commDict.get(cc).getCommodityCode());
						key.setTradeFlowCode(flow+"");
						List<CommodityInfo> listCommInfo = commInfoService.findRateOrderList(key);
						System.out.println(listCommInfo.size());
						//可能需要处理下当全部贸易量只是一个国家的情况，此时，当对象国为0时，是否需要强制设置为1，待续。。。。。
						if(listCommInfo!=null){
							for(int i=0;i<listCommInfo.size();i++){							
								CommodityInfo comm = listCommInfo.get(i);		
								comm.setReporterRateOrder(i+1);
								commInfoService.update(comm);	
							}		
						}					
					}					
				}
			}
		}	
	}
	

	@Test
	//计算对象国贸易份额变化
	public void rtRateChange(){
		
		//2012年的数据该字段REPORTER_RATE_CHANGED不需要处理，因为没有上一年的数据
		//2013年的数据该字段REPORTER_RATE_CHANGED可与本年贸易份额REPORTER_TRADE_RATE相等，该注释有问题，待修改。。。
//		commInfoService.process2013RateChange();
		List<CommodityInfo> listCommInfo = commInfoService.findNo2012List();
		for (int i = 0; i < listCommInfo.size(); i++) {
			CommodityInfo comm = listCommInfo.get(i);		
			CommodityInfo commLastYear = comm;
			commLastYear.setYr(comm.getYr()-1);
			commLastYear = (CommodityInfo) commInfoService.find(commLastYear);
			double changeValue = 0;
			if(commLastYear!=null){
				changeValue = comm.getReporterTradeRate()-commLastYear.getReporterTradeRate();
			}
			comm.setReporterRateChanged(changeValue);
			commInfoService.update(comm);
		}
	}
	

		
	@Test
	//这个方法应该与处理对象国贸易份额的方法合成一块。。。。。。。。。。	
	public void ptRateOrder(){
		
		//查询每一条记录,待改进，最好不要一次性查处所有记录
		List<CommodityInfo> listCommInfo = commInfoService.findAll();
		//循环
		for(int i=0;i<listCommInfo.size();i++){
			//判断对象国是否属于报告国，暂时不做这步操作。。。。。
			//如果是，则查询报告国为对象国时，贸易份额的排名
			CommodityInfo comm = listCommInfo.get(i);
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
			System.out.println(tradeFlowCode2);
			CommodityInfo ptComm = (CommodityInfo) commInfoService.find(key);
			//将排名插入到当前的记录中
			if(ptComm!=null){
				System.out.println(ptComm.getReporterCode());
				comm.setPartnerRateOrder(ptComm.getReporterRateOrder());
				comm.setPartnerRateChanged(ptComm.getReporterRateChanged());
				commInfoService.update(comm);
			}				
		}
	}	
		

	@Test
	public void test(){
		
		CommodityInfoKey key = new CommodityInfoKey();
		key.setYr(2012);
		key.setReporterCode("156");
		key.setCommodityCode("12");
		key.setTradeFlowCode("1");
		List<CommodityInfo> listCommInfo = commInfoService.findRateOrderList(key);
		System.out.println(listCommInfo.size());
		for(int i=0;i<listCommInfo.size();i++){
			CommodityInfo comm = listCommInfo.get(i);		
			comm.setReporterRateOrder(i+1);
			commInfoService.update(comm);
		}	
	}
	
		
	
}
