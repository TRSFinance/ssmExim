package com.trs.commodity.dao;

import com.trs.commodity.model.HandleIndexLogs;

public interface HandleIndexLogsMapper {
    int deleteByPrimaryKey(Integer handleId);

    int insert(HandleIndexLogs record);

    int insertSelective(HandleIndexLogs record);

    HandleIndexLogs selectByPrimaryKey(Integer handleId);

    int updateByPrimaryKeySelective(HandleIndexLogs record);

    int updateByPrimaryKey(HandleIndexLogs record);
}