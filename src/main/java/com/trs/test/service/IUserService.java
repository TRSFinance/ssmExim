package com.trs.test.service;

import java.util.List;

import com.trs.test.model.User;

public interface IUserService {  
    public User getUserById(int userId); 
    
    public List<User> findAll();
    
    public void add(User user);
    
    public void del(int userId);
    
    public void update(User user);
    
    public void addCsvData();
    

    
    
    
    
}