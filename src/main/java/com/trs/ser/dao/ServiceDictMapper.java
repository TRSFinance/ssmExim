package com.trs.ser.dao;

import com.trs.ser.model.ServiceDict;

public interface ServiceDictMapper{
    int deleteByPrimaryKey(String serCode);

    int insert(ServiceDict record);

    int insertSelective(ServiceDict record);

    ServiceDict selectByPrimaryKey(String serCode);

    int updateByPrimaryKeySelective(ServiceDict record);

    int updateByPrimaryKey(ServiceDict record);
}