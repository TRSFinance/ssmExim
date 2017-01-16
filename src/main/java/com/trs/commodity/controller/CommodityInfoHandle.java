package com.trs.commodity.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.trs.commodity.model.CommodityDict;
import com.trs.commodity.model.CommodityInfo;
import com.trs.commodity.model.CommodityInfoKey;
import com.trs.commodity.model.CountryDict;
import com.trs.commodity.model.HandleIndexLogs;
import com.trs.commodity.service.CommodityDictService;
import com.trs.commodity.service.CommodityInfoService;
import com.trs.commodity.service.CountryDictService;
import com.trs.commodity.service.HandleIndexLogsService;

public class CommodityInfoHandle { 

	private Logger log = Logger.getLogger(CommodityInfoHandle.class);
	
	@Autowired
	private CommodityInfoService commInfoService ;
	
	@Autowired
	private CountryDictService countryDictService ;
	
	@Autowired
	private	CommodityDictService commDictService;
	
	@Autowired
	private HandleIndexLogsService handleIndexService;
		
	@Autowired
	BasicDataSource dataSource; 
		
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
	
	/** 
	 * 	数据的初次加工主方法:计算贸易额较上期(增量,增速),占报告国贸易份额
	 */
	public void handleFirst(){
		
		log.info("开始进行数据的初次加工。。。");
		//按这几个条件查询数据进行计算
		List<CountryDict> list_reporters = countryDictService.findReporters();
		List<CountryDict> list_partners = countryDictService.findPartners();  
      	List<CommodityInfo> listUpdate =  new ArrayList<CommodityInfo>();
		//查找上次处理数据的位置,保证循环时继续上一次处理
		HandleIndexLogs handleIndex = handleIndexService.find(1);
		int yrIndex = handleIndex.getYrIndex();
		int rtIndex = handleIndex.getRtIndex();
		int ptIndex = handleIndex.getPtIndex();
      	int t=0;
		for(int yr=yrIndex;yr<2016;yr++){
			for (int r = 0; r < list_reporters.size(); r++) {
				for (int p = 0; p < list_partners.size(); p++) {
					r = rtIndex+r;
					p = ptIndex+p;
					rtIndex = 0;
					ptIndex = 0;
					CommodityInfoKey key = new CommodityInfo();
					key.setYr(yr);
					key.setReporterCode(list_reporters.get(r).getCountryCode());
					key.setPartnerCode(list_partners.get(p).getCountryCode());
					List<CommodityInfo> list_custom = commInfoService.findCustom(key);		
					for(CommodityInfo comm : list_custom){	
						//处理字段：占报告国贸易份额
			      		comm = commInfoService.rtComtradeFene(comm);
			      		//处理字段：贸易金额较上期的增量和增速
			      		comm = commInfoService.zengliangAndzengsu(comm);			      		
			      		listUpdate.add(comm);
						t++;
						if(t%3000==0){//如果对象存放对象达到3000个，执行一次入库操作
							log.debug("开始处理3000条数据。。。");
							commInfoService.updateList_handleFirst(listUpdate);
							log.debug("完成处理3000条数据。。。");
							//处理完后,清空存放待处理对象的集合
							listUpdate =  new ArrayList<CommodityInfo>();
						}
					}
					//记录本次处理数据位置
					log.info("本次处理数据条件:年份["+yr+"]报告国集合中索引["+r+"]对象国集合中索引["+p+"]");
					handleIndex.setYrIndex(yr);
					handleIndex.setRtIndex(r);
					handleIndex.setPtIndex(p);
					handleIndexService.update(handleIndex);	
				}
			}
		}
      	//循环结束后,将集合中剩下的对象进行更新操作
      	commInfoService.updateList_handleFirst(listUpdate);
		log.info("初次数据加工完成。。。");				
	}
	
	/** 
	 * 商品数据的第二次加工主方法:计算占报告国贸易份额(排名,较上期变化)
	 */	 
	public void handleSecond(){
		
		log.info("开始进行数据的第二次加工。。。");
      	List<CommodityInfo> listUpdate =  new ArrayList<CommodityInfo>();
		List<CountryDict> list_reporters = countryDictService.findReporters();
		List<CommodityDict> list_commDict = commDictService.findAll();
		//查找上次处理数据的位置
		HandleIndexLogs handleIndex = handleIndexService.find(2);
		int yrIndex = handleIndex.getYrIndex();
		int rtIndex = handleIndex.getRtIndex();
		int commIndex = handleIndex.getCommIndex();
		int flowIndex =  handleIndex.getFolwIndex();
      	int t=0;	
		for(int yr=yrIndex;yr<2016;yr++){
			for (int r = 0; r < list_reporters.size(); r++) {
				for(int cc=0;cc<list_commDict.size();cc++){
					for(int rg=1;rg<=4;rg++){	
						r += rtIndex;
						cc += commIndex;
						rg += flowIndex-1;
						//下面的操作保证这rtIndex，commIndex，flowIndex这几个参数只对第一次启动该方法时起作用
						rtIndex = 0;
						commIndex = 0;
						flowIndex = 1;
						String reporterCode = list_reporters.get(r).getCountryCode();
						String commodityCode = list_commDict.get(cc).getCommodityCode();
						//处理字段：占报告国贸易份额排名
						List<CommodityInfo> listCommInfo = commInfoService.rtRateOrder(yr,reporterCode,commodityCode,rg+"");
						for(int i=0;i<listCommInfo.size();i++){
							//处理字段：占报告国贸易份额变化
							CommodityInfo comm = commInfoService.rtRateChange(listCommInfo.get(i));
				      		listUpdate.add(comm);
							t++;
							if(t%3000==0){//如果对象存放对象达到3000个，执行一次入库操作
								log.debug("开始处理3000条数据。。。");
								commInfoService.updateList_handleSecond(listUpdate);
								log.debug("完成处理3000条数据。。。");
								//处理完后,清空存放待处理对象的集合
								listUpdate =  new ArrayList<CommodityInfo>();
							}
						}
						//记录本次处理数据位置
						log.info("本次处理数据条件:年份["+yr+"]报告国集合中索引["+r+"]商品分类集合中索引["+cc+"]贸易方向["+rg+"]");
						handleIndex.setYrIndex(yr);
						handleIndex.setRtIndex(r);
						handleIndex.setCommIndex(cc);
						handleIndex.setFolwIndex(rg);
						handleIndexService.update(handleIndex);	
					}	
				}
			}	
		}
      	//循环结束后,将集合中剩下的对象进行更新操作
		commInfoService.updateList_handleSecond(listUpdate);
		log.info("第二次数据加工完成。。。");		
	}
	
	/**
	 * 商品数据的第三次加工主方法:计算占对象国(贸易份额,贸易份额排名,贸易份额较上期变化)
	 */
	public void handleThird(){
		
		log.info("开始进行数据的第三次加工。。。");
      	List<CommodityInfo> listUpdate =  new ArrayList<CommodityInfo>();
		//暂时按这个规则进行计算
		List<CountryDict> list_reporters = countryDictService.findReporters();
		List<CountryDict> list_partners = countryDictService.findPartners();      	     	
		//查找上次处理数据的位置
		HandleIndexLogs handleIndex = handleIndexService.find(3);
		int yrIndex = handleIndex.getYrIndex();
		int rtIndex = handleIndex.getRtIndex();
		int ptIndex = handleIndex.getPtIndex();
		int t=0;
		for(int yr=yrIndex;yr<2016;yr++){
			for (int r = 0; r < list_reporters.size(); r++) {
				for (int p = 0; p < list_partners.size(); p++) {
					r = rtIndex+r;
					p = ptIndex+p;
					rtIndex = 0;
					ptIndex = 0;	
					CommodityInfoKey key = new CommodityInfo();
					key.setYr(yr);
					key.setReporterCode(list_reporters.get(r).getCountryCode());
					key.setPartnerCode(list_partners.get(p).getCountryCode());
					List<CommodityInfo> list_custom = commInfoService.findCustom(key);
					for(int i=0;i<list_custom.size();i++){
						//处理字段：占对象国(贸易份额,贸易份额排名,贸易份额较上期变化)
						//ps:如果对象国不是报告国,则占对象国贸易份额排名设为999,防止影响后期前端做查询
						CommodityInfo comm = commInfoService.ptRateOrder(list_custom.get(i));
			      		listUpdate.add(comm);
						t++;
						if(t%3000==0){//如果对象存放对象达到3000个，执行一次入库操作
							log.debug("开始处理3000条数据。。。");
							commInfoService.updateList_handleThird(listUpdate);
							log.debug("完成处理3000条数据。。。");
							//处理完后,清空存放待处理对象的集合
							listUpdate =  new ArrayList<CommodityInfo>();
						}
					}
					//记录本次处理数据位置
					log.info("本次处理数据条件:年份["+yr+"]报告国集合中索引["+r+"]对象国集合中索引["+p+"]");
					handleIndex.setYrIndex(yr);
					handleIndex.setRtIndex(r);
					handleIndex.setPtIndex(p);
					handleIndexService.update(handleIndex);	
				}
			}
		}
      	//循环结束后,将集合中剩下的对象进行更新操作
      	commInfoService.updateList_handleThird(listUpdate);
      	log.info("第三次数据加工完成。。。");		
	}
	
	
}
