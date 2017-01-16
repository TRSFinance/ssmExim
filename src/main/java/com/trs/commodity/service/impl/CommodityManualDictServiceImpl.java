package com.trs.commodity.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.trs.commodity.dao.CommodityManualDictMapper;
import com.trs.commodity.model.CommodityManualDict;
import com.trs.commodity.service.CommodityManualDictService;

@Service("commodityManualDict")
public class CommodityManualDictServiceImpl implements CommodityManualDictService{

	@Resource
	public CommodityManualDictMapper dao;
	
	@Override
	public void add(CommodityManualDict record) {
		dao.insert(record);
	}

	@Override
	public void del(String key) {
		dao.deleteByPrimaryKey(key);
	}

	@Override
	public void update(CommodityManualDict record) {
		dao.updateByPrimaryKey(record);
	}

	@Override
	public CommodityManualDict find(String key) {
		return dao.selectByPrimaryKey(key);
	}
	
	
	@Override
	public List<CommodityManualDict> findAll() {
		return dao.findAll();
	}

	
	
}
