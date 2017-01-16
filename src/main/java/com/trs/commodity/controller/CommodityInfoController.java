package com.trs.commodity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.trs.commodity.model.CommodityInfo;
import com.trs.commodity.service.CommodityInfoService;

@Controller
@RequestMapping("commodityInfo")
public class CommodityInfoController {

	@Autowired
	CommodityInfoService commInfoService;
	
	@ResponseBody
	@RequestMapping(value="/list", produces = "application/json; charset=UTF-8")
	public String list(){
		
		String json="";		 
		List<CommodityInfo> list = commInfoService.findAll();
		Gson gson = new Gson();
		json = gson.toJson(list);
		return json;
	}
	
	
	
	
}
