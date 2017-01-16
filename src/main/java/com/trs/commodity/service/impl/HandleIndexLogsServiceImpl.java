package com.trs.commodity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trs.commodity.dao.HandleIndexLogsMapper;
import com.trs.commodity.model.HandleIndexLogs;
import com.trs.commodity.service.HandleIndexLogsService;

@Service("handleIndexLogs")
public class HandleIndexLogsServiceImpl implements HandleIndexLogsService{

	@Autowired
	HandleIndexLogsMapper dao;
	
	@Override
	public void add(HandleIndexLogs record) {
		dao.insert(record);
	}

	@Override
	public void del(int key) {
		dao.deleteByPrimaryKey(key);
	}

	@Override
	public void update(HandleIndexLogs record) {
		dao.updateByPrimaryKey(record);
	}

	@Override
	public HandleIndexLogs find(int key) {
		return dao.selectByPrimaryKey(key);
	}

}
