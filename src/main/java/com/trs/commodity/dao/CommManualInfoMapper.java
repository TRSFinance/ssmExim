package com.trs.commodity.dao;

import com.trs.commodity.model.CommManualInfo;
import com.trs.commodity.model.CommManualInfoKey;

public interface CommManualInfoMapper{

    int deleteByPrimaryKey(CommManualInfoKey key);

    int insert(CommManualInfo record);

    int insertSelective(CommManualInfo record);

    CommManualInfo selectByPrimaryKey(CommManualInfoKey key);

    int updateByPrimaryKeySelective(CommManualInfo record);

    int updateByPrimaryKey(CommManualInfo record);

    
    
}