package com.trs.commodity.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.trs.commodity.model.CommodityInfo;
import com.trs.commodity.model.CommodityInfoKey;
import com.trs.commodity.model.CountryDict;
import com.trs.commodity.model.HandleIndexLogs;
import com.trs.commodity.service.CommodityInfoService;
import com.trs.commodity.service.CountryDictService;
import com.trs.commodity.service.HandleIndexLogsService;

public class CommodityInfoHandleTest {

	private Logger log = Logger.getLogger(CommodityInfoHandleTest.class);
	
	@Autowired
	private CommodityInfoService commInfoService ;
	
	@Autowired
	private CountryDictService countryDictService ;
	
	@Autowired
	private HandleIndexLogsService handleIndexService;
		
	@Autowired
	BasicDataSource dataSource;
	
	@Test
	/**
	 * 处理报告国和对象国中文名称字段
	 */
	public void addCountryChnName(){
	
		log.info("开始处理[报告国和对象国的中文名称]。。。");
		List<CountryDict> list_repoter = countryDictService.findReporters();
		List<CountryDict> list_partner = countryDictService.findPartners();	
		for(int yr=2013;yr<2014;yr++){			
			for(int r=0;r<list_repoter.size();r++){
				for(int p=0;p<list_partner.size();p++){
					String rtChnName = list_repoter.get(r).getCountryChnName();
					String ptChnName = list_partner.get(p).getCountryChnName();
					String  rtCode = list_repoter.get(r).getCountryCode();
					String ptCode = list_partner.get(p).getCountryCode();
					log.debug("开始添加报告国中文名称:年份["+yr+"]报告国["+rtChnName+"]对象国["+ptChnName+"]。。。");
					commInfoService.updateRtName(rtChnName,yr,rtCode,ptCode);
					log.debug("开始添加对象国中文名称:年份["+yr+"]报告国["+rtChnName+"]对象国["+ptChnName+"]。。。");
					commInfoService.updatePtName(ptChnName,yr,rtCode,ptCode);	
				}
			}
		}
		log.info("完成处理[报告国和对象国的中文名称]。。。");
	}
	
	@Test
	//计算贸易额较上期(增量,增速),占报告国贸易份额		
	public void handleFirstTest(){
		
		long time1 = System.currentTimeMillis();
		System.out.println("开始进行数据的初次加工。。。");
		//暂时先查询出所有记录来处理，正式计算时，需要细分来进行，不然跑不动
      	List<CommodityInfo> list = commInfoService.findRtEmptyComtradeRate();
      	List<CommodityInfo> listUpdate =  new ArrayList<CommodityInfo>();
      	int t=0;
      	for(CommodityInfo comm : list){	      		
      		comm = commInfoService.rtComtradeFene(comm);
      		comm = commInfoService.zengliangAndzengsu(comm);
      		listUpdate.add(comm);
			t++;
			if(t%3000==0){//如果对象存放对象达到3000个，执行一次入库操作
				System.out.println("进来了。。。");
				commInfoService.updateList_handleFirst(listUpdate);
				//处理完后,清空存放待处理对象的集合
				listUpdate =  new ArrayList<CommodityInfo>();
			}	
		}
      	//循环结束后,将集合中剩下的对象进行更新操作
      	commInfoService.updateList_handleFirst(listUpdate);
		System.out.println("初次数据加工完成。。。");
				
		long time2 = System.currentTimeMillis();
		System.out.println("初次数据加工共花费时间["+(time2-time1)/1000/60+"]分钟");
	}
	
	
	@Test
	/** 
	 * 商品数据的第二次加工主方法:计算占报告国贸易份额(排名,较上期变化)
	 */	 
	public void handleSecondTest(){
		
		List<CommodityInfo> list = commInfoService.findRateOrderList_noHandle();		
      	List<CommodityInfo> listUpdate =  new ArrayList<CommodityInfo>();
      	int t=0;	
		for(int j=0;j<list.size();j++){
			int yr = list.get(j).getYr();
			String reporterCode = list.get(j).getReporterCode();
			String commodityCode = list.get(j).getCommodityCode();
			String rg = list.get(j).getTradeFlowCode();
			List<CommodityInfo> listCommInfo = commInfoService.rtRateOrder(yr,reporterCode,commodityCode,rg+"");

			for(int i=0;i<listCommInfo.size();i++){
				CommodityInfo comm = commInfoService.rtRateChange(listCommInfo.get(i));
	      		listUpdate.add(comm);
				t++;
				if(t%3000==0){//如果对象存放对象达到3000个，执行一次入库操作
					System.out.println("进来了。。。");
					commInfoService.updateList_handleSecond(listUpdate);
					System.out.println("处理完毕。。。");
					//从集合list中删除已处理过的listUpdate集合					
					for(int h=0;h<listUpdate.size();h++){
						for(int f=0;f<list.size();f++){
							if(listUpdate.get(h).getUnionKey().equals(list.get(f).getUnionKey())){	
//								System.out.println("相等"+f);
								list.remove(f);
								j=0;								
								break;
							}
						}
					}
					System.out.println(list.size());
					//处理完后,清空存放待处理对象的集合
					listUpdate =  new ArrayList<CommodityInfo>();
				}			
			}
		}
      	//循环结束后,将集合中剩下的对象进行更新操作
		commInfoService.updateList_handleSecond(listUpdate);
		System.out.println("第二次数据加工完成。。。");				
	}
	
	@Test
	/**
	 * 商品数据的第三次加工主方法:计算占对象国(贸易份额,贸易份额排名,贸易份额较上期变化)
	 */
	public void handleThirdTest(){
		
		String[] list_reporters = {"8","12"};		
      	List<CommodityInfo> listUpdate =  new ArrayList<CommodityInfo>();
		List<CountryDict> list_partners = countryDictService.findPartners();      	     	
      	//查找上次处理数据的位置
		HandleIndexLogs handleIndex = handleIndexService.find(3);
		int yrIndex = handleIndex.getYrIndex();
		int rtIndex = handleIndex.getRtIndex();
		int ptIndex = handleIndex.getPtIndex();
      	int t=0;	
		for(int yr=yrIndex;yr<2014;yr++){
			for(int r=0;r<list_reporters.length;r++){
				for (int p = 0; p < list_partners.size(); p++) {
					r = rtIndex+r;
					p = ptIndex+p;
					rtIndex = 0;
					ptIndex = 0;	
					CommodityInfoKey key = new CommodityInfo();
					key.setYr(yr);
					key.setReporterCode(list_reporters[r]);
					key.setPartnerCode(list_partners.get(p).getCountryCode());
					List<CommodityInfo> list_custom = commInfoService.findCustom(key);
					for(int i=0;i<list_custom.size();i++){
						CommodityInfo comm = commInfoService.ptRateOrder(list_custom.get(i));
			      		listUpdate.add(comm);
						t++;
						if(t%3000==0){//如果对象存放对象达到3000个，执行一次入库操作
							log.info("开始处理3000条数据");
							commInfoService.updateList_handleThird(listUpdate);
							System.out.println("完成处理3000条数据。。。");
							//处理完后,清空存放待处理对象的集合
							listUpdate =  new ArrayList<CommodityInfo>();
						}
					}
					System.out.println(yr+"_"+r+"_"+p);
					handleIndex.setYrIndex(yr);
					handleIndex.setRtIndex(r);
					handleIndex.setPtIndex(p);
					handleIndexService.update(handleIndex);	
				}
			}			
		}	
      	//循环结束后,将集合中剩下的对象进行更新操作
      	commInfoService.updateList_handleThird(listUpdate);
		System.out.println("第三次数据加工完成。。。");		
	}
	
	
	
	
}
