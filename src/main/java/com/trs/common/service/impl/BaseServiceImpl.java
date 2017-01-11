package com.trs.common.service.impl;

import com.trs.common.mapper.BaseDao;
import com.trs.common.service.BaseService;

public abstract class BaseServiceImpl implements BaseService {
		
	protected BaseDao dao;
	
	@Override
	public  void add(Object obj) {
		dao.insert(obj);
	}

	@Override
	public void del(Object obj) {
		dao.deleteByPrimaryKey(obj);
	}
	
	@Override
	public void update(Object obj) {
		dao.updateByPrimaryKey(obj);
	}
	
	@Override
	public Object find(Object obj) {
		return dao.selectByPrimaryKey(obj);
	}
	
//	@Override
//	public void addList(List<Object> List) {
//		dao.addList(List);
//	}
//
//	@Override
//	public void delList(Object obj) {
//		dao.delList(obj);
//	}
//
//	@Override
//	public void updateList(Object obj) {
//		dao.updateList(obj);
//	}
//
//	@Override
//	public List<?> findList(Object obj) {
//		return dao.findList(obj);
//	}
	
	
}
