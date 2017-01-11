package com.trs.test.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.trs.test.dao.UserMapper;
import com.trs.test.model.User;
import com.trs.test.service.IUserService;


//@Service("userService")
public class UserServiceImpl implements IUserService {  
//    @Resource  
    private UserMapper userDao;  
    @Override  
    public User getUserById(int userId) {  
        return this.userDao.selectByPrimaryKey(userId);  
    }
    
	@Override
	public void add(User user) {

		userDao.insert(user);
	}

	@Override
	public List<User> findAll() {

		return userDao.findAll();
	}

	@Override
	public void addCsvData() {
				
	}

	@Override
	public void del(int userId) {

		userDao.deleteByPrimaryKey(userId);
	}

	@Override
	public void update(User user) {

		userDao.updateByPrimaryKey(user);
	}
}