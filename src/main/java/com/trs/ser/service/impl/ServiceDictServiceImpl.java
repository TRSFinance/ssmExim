package com.trs.ser.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.trs.ser.dao.ServiceDictMapper;
import com.trs.ser.model.ServiceDict;
import com.trs.ser.service.ServiceDictService;

@Service("serviceDict")
public class ServiceDictServiceImpl implements ServiceDictService{

	@Autowired
	ServiceDictMapper dao;
	
	@Override
	public void add(ServiceDict record) {
		dao.insert(record);
	}

	@Override
	public void del(String key) {
		dao.deleteByPrimaryKey(key);
	}

	@Override
	public void update(ServiceDict record) {
		dao.updateByPrimaryKey(record);
	}

	@Override
	public ServiceDict find(String key) {
		return dao.selectByPrimaryKey(key);
	}
	
}
