package com.trs.commodity.dao;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.trs.commodity.model.CountryDict;

public interface CountryDictMapper{
	
    void deleteByPrimaryKey(String countryCode);

    void insert(CountryDict record);

    void insertSelective(CountryDict record);

    CountryDict selectByPrimaryKey(String countryCode);

    void updateByPrimaryKeySelective(CountryDict record);

    void updateByPrimaryKey(CountryDict record);
    
    @Select("select * from country_dict")
    @ResultMap("BaseResultMap")
    List<CountryDict> findAll();
}