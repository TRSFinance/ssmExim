package com.trs.commodity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.trs.commodity.dao.CommManualInfoMapper;
import com.trs.commodity.model.CommManualInfo;
import com.trs.commodity.model.CommManualInfoKey;
import com.trs.commodity.service.CommManualInfoService;

@Service("commManualInfo")
public class CommManualInfoServiceImpl implements CommManualInfoService {

	@Autowired
	CommManualInfoMapper dao;

	@Override
	public void add(CommManualInfo record) {
		dao.insert(record);
	}

	@Override
	public void del(CommManualInfoKey key) {
		dao.deleteByPrimaryKey(key);
	}

	@Override
	public void update(CommManualInfo record) {
		dao.updateByPrimaryKey(record);
	}

	@Override
	public CommManualInfo find(CommManualInfoKey key) {
		return dao.selectByPrimaryKey(key);
	}
	
	
	
	
}
