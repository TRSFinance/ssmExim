package com.trs.commodity.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trs.commodity.dao.CommodityDictMapper;
import com.trs.commodity.model.CommodityDict;
import com.trs.commodity.service.CommodityDictService;

@Service("commdict")
public class CommodityDictServiceImpl implements CommodityDictService{
	
	@Autowired
	CommodityDictMapper dao;

	@Override
	public void add(CommodityDict record) {
		dao.insert(record);
	}

	@Override
	public void del(String key) {
		dao.deleteByPrimaryKey(key);
	}

	@Override
	public void update(CommodityDict record) {
		dao.updateByPrimaryKey(record);
	}

	@Override
	public CommodityDict find(String key) {
		return dao.selectByPrimaryKey(key);
	}
	
	
	@Override
	public List<CommodityDict> findChildren(CommodityDict record) {
		return dao.findChildren(record);
	}

	@Override
	public List<CommodityDict> custom(String record) {
		return dao.custom(record);
	}

	@Override
	public List<CommodityDict> findAll() {
		return dao.findAll();
	}

		
	
}
