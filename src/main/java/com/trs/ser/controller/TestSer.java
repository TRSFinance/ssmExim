package com.trs.ser.controller;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.trs.common.controller.BaseTest;
import com.trs.ser.model.ServiceDict;
import com.trs.ser.service.ServiceDictService;

public class TestSer extends BaseTest{

	
	@Autowired
	ServiceDictService service;
	
	
	@Test
	public void test(){
				
		ServiceDict serviceDict = service.find("0");
		System.out.println(serviceDict.getSerName());
		
	}
	
	
}
