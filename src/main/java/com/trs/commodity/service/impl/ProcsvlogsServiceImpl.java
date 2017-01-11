package com.trs.commodity.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trs.commodity.dao.ProcsvlogsMapper;
import com.trs.commodity.model.Procsvlogs;
import com.trs.commodity.model.ProcsvlogsKey;
import com.trs.commodity.service.ProcsvlogsService;

@Service("procsvlogs")
public class ProcsvlogsServiceImpl implements ProcsvlogsService{

	
	@Autowired
	private ProcsvlogsMapper dao;

	
	@Override
	public void add(Procsvlogs record) {
		dao.insert(record);
	}

	@Override
	public void del(ProcsvlogsKey key) {
		dao.deleteByPrimaryKey(key);
	}

	@Override
	public void update(Procsvlogs record) {
		dao.updateByPrimaryKey(record);
	}

	@Override
	public Procsvlogs find(ProcsvlogsKey key) {
		return dao.selectByPrimaryKey(key);
	}

	@Override
	public boolean isExist(ProcsvlogsKey key) {
		Procsvlogs procsvlogs = (Procsvlogs) find(key);
		if(procsvlogs==null){
			return false;
		}
		return true;
	}


	@Override
	public List<Procsvlogs> findFileLimited() {
		return dao.findFileLimited();
	}

}
