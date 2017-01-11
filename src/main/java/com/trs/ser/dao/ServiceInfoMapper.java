package com.trs.ser.dao;

import com.trs.ser.model.ServiceInfo;
import com.trs.ser.model.ServiceInfoKey;

public interface ServiceInfoMapper{

    int deleteByPrimaryKey(ServiceInfoKey key);

    int insert(ServiceInfo record);

    int insertSelective(ServiceInfo record);

    ServiceInfo selectByPrimaryKey(ServiceInfoKey key);

    int updateByPrimaryKeySelective(ServiceInfo record);

    int updateByPrimaryKey(ServiceInfo record);

    
    
    
}