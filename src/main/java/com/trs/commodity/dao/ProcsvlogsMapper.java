package com.trs.commodity.dao;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.trs.commodity.model.Procsvlogs;
import com.trs.commodity.model.ProcsvlogsKey;

public interface ProcsvlogsMapper {
    void deleteByPrimaryKey(ProcsvlogsKey key);

    void insert(Procsvlogs record);

    void insertSelective(Procsvlogs record);

    Procsvlogs selectByPrimaryKey(ProcsvlogsKey key);

    void updateByPrimaryKeySelective(Procsvlogs record);

    void updateByPrimaryKey(Procsvlogs record);

	@Select("select * from procsvlogs where islimit='1'")
	@ResultMap("BaseResultMap")
	public List<Procsvlogs> findFileLimited();
	
	
}	
	