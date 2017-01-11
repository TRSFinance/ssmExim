package com.trs.commodity.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.trs.commodity.dao.CommodityInfoMapper;
import com.trs.commodity.model.CommodityInfo;
import com.trs.commodity.model.CommodityInfoKey;
import com.trs.commodity.model.Procsvlogs;
import com.trs.commodity.service.CommodityInfoService;

@Service("commodityInfo")
public class CommodityInfoServiceImpl implements CommodityInfoService{

//	private Logger log = Logger.getLogger(CommodityInfoServiceImpl.class);
	
    @Resource
	public CommodityInfoMapper dao;

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
	public void updateRtName(String rtName,String rtCode) {
		dao.updateRtName(rtName,rtCode);
	}
	
	@Override
	public void updatePtName(String ptName,String ptCode) {
		dao.updatePtName(ptName,ptCode);
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

	@Override
	public void updateCommCode(int yr, String flow, String rtCode,
			String ptCode, String commCode, String commCode2) {
		dao.updateCommCode(yr,flow,rtCode,ptCode,commCode,commCode2);
	}
	
}
