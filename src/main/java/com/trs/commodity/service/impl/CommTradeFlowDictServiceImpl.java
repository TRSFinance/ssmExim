package com.trs.commodity.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.trs.commodity.dao.CommTradeFlowDictMapper;
import com.trs.commodity.model.CommTradeFlowDict;
import com.trs.commodity.service.CommTradeFlowDictService;

@Service("commTradeFlow")
public class CommTradeFlowDictServiceImpl implements CommTradeFlowDictService {

	@Resource
	public CommTradeFlowDictMapper dao;

	@Override
	public void add(CommTradeFlowDict record) {
		dao.insert(record);
	}

	@Override
	public void del(String key) {
		dao.deleteByPrimaryKey(key);
	}

	@Override
	public void update(CommTradeFlowDict record) {
		dao.updateByPrimaryKey(record);
	}

	@Override
	public CommTradeFlowDict find(String key) {
		return dao.selectByPrimaryKey(key);
	}
	
	
}
