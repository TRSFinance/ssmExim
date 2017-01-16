package com.trs.commodity.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.trs.commodity.dao.CountryDictMapper;
import com.trs.commodity.model.CountryDict;
import com.trs.commodity.service.CountryDictService;

@Service("CountryDict")
public class CountryDictServiceImpl implements CountryDictService{

	@Resource
	public CountryDictMapper dao;

	
	@Override
	public void add(CountryDict record) {
		dao.insert(record);
	}

	@Override
	public void del(String key) {
		dao.deleteByPrimaryKey(key);
	}

	@Override
	public void update(CountryDict record) {
		dao.updateByPrimaryKey(record);
	}

	@Override
	public CountryDict find(String key) {
		return dao.selectByPrimaryKey(key);
	}
	
	@Override
	public List<CountryDict> findReporters() {
		return dao.findReporters();
	}

	@Override
	public List<CountryDict> findPartners() {
		return dao.findPartners();
	}
	
	@Override
	public List<CountryDict> findAll() {
		return dao.findAll();
	}



}
