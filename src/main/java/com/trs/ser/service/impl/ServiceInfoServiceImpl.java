package com.trs.ser.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.trs.ser.dao.ServiceInfoMapper;
import com.trs.ser.model.ServiceInfo;
import com.trs.ser.model.ServiceInfoKey;
import com.trs.ser.service.ServiceInfoService;

@Service("serviceInfo")
public class ServiceInfoServiceImpl implements ServiceInfoService{

	@Autowired
	ServiceInfoMapper dao;

	@Override
	public void add(ServiceInfo record) {
		dao.insert(record);
	}

	@Override
	public void del(ServiceInfoKey key) {
		dao.deleteByPrimaryKey(key);
	}

	@Override
	public void update(ServiceInfo record) {
		dao.updateByPrimaryKey(record);
	}

	@Override
	public ServiceInfo find(ServiceInfoKey key) {
		return dao.selectByPrimaryKey(key);
	}
	
}
