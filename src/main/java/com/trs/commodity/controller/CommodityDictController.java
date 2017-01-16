package com.trs.commodity.controller;

import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.trs.commodity.model.CommodityDict;
import com.trs.commodity.service.CommodityDictService;
import com.trs.common.controller.BaseTest;

@Controller
@RequestMapping("commdict")
public class CommodityDictController extends BaseTest{
	
	@Autowired
	CommodityDictService service;
	
	/**
	 * 返回子节点商品编码对象(json格式) 
	 * @param parentCode
	 */
	@ResponseBody
	@RequestMapping(value="/listChildren", produces = "application/json; charset=UTF-8")
	public String listChildren(String parentCode){		 		
		CommodityDict comm = new CommodityDict();
		comm.setCommodityParentCode(parentCode);
		List<CommodityDict> list = service.findChildren(comm);
		Gson gson = new Gson();
		String json = gson.toJson(list);
		return json;
	}

	/**
	 * 返回商品编码的详细信息(json格式)
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/listNode", produces = "application/json; charset=UTF-8")
	public String listNodeInfo(String code){
		CommodityDict comm = (CommodityDict)service.find(code);
		Gson gson = new Gson();
		String json = gson.toJson(comm);
//		if(comm!=null){
//			
//		}
		return json;	
	}
	
	@Test
	public void update(){
		
		List<CommodityDict> comm = service.findAll();
		System.out.println(comm.size());
		System.out.println(comm.get(1).getCommodityChnName());
		
		
	}

}
