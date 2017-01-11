package com.trs.test.dao;

import java.util.List;

import com.trs.test.model.User;

public interface UserMapper {
	
    void deleteByPrimaryKey(Integer id);

    void insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(User record);

    void updateByPrimaryKey(User record);
    
    List<User> findAll();
    
    
}