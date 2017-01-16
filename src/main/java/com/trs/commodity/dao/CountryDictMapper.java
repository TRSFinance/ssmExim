package com.trs.commodity.dao;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.trs.commodity.model.CountryDict;

public interface CountryDictMapper {
    int deleteByPrimaryKey(String countryCode);

    int insert(CountryDict record);

    int insertSelective(CountryDict record);

    CountryDict selectByPrimaryKey(String countryCode);

    int updateByPrimaryKeySelective(CountryDict record);

    int updateByPrimaryKey(CountryDict record);
    
	@Select("select * from country_dict where is_reporter='1'")
    @ResultMap("BaseResultMap")
    public List<CountryDict> findReporters();
	
	@Select("select * from country_dict where is_partner='1'")
    @ResultMap("BaseResultMap")
    public List<CountryDict> findPartners();

	@Select("select * from country_dict")
    @ResultMap("BaseResultMap")
    List<CountryDict> findAll();
}