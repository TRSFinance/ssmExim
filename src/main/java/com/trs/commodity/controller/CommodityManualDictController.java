package com.trs.commodity.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.trs.commodity.model.CommodityManualDict;
import com.trs.commodity.service.CommodityManualDictService;



@Controller
@RequestMapping("/commManual")
public class CommodityManualDictController{

	@Autowired
	CommodityManualDictService service;
	
	@RequestMapping("list")
	public String list(HttpServletRequest request,HttpServletResponse response,
			ModelMap model){
		List<CommodityManualDict> list = service.findAll();
		model.addAttribute("data", list);
		return "/commodity/manual/list";
		
	}
	
	@RequestMapping("view")
	public String view(String  key,
			HttpServletRequest request,HttpServletResponse response,
			ModelMap model){
		
		CommodityManualDict record = (CommodityManualDict) service.find(key);
		model.addAttribute("commManual",record);
		return "/commodity/manual/view";
	}
	
	@RequestMapping("v_edit")
	public String v_edit(String  key,
			HttpServletRequest request,HttpServletResponse response,
			ModelMap model){
		
		CommodityManualDict record = (CommodityManualDict) service.find(key);
		model.addAttribute("commManual",record);
		return "/commodity/manual/edit";
	}
	
	@RequestMapping(value={"/edit"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
	public String edit(String  key,
			String comManualName,
			String comCodeList,
			HttpServletRequest request,HttpServletResponse response,
			ModelMap model){
		
		CommodityManualDict record = (CommodityManualDict) service.find(key);
		record.setComManualName(comManualName);
		record.setComCodeList(comCodeList);
		service.update(record);
		return "redirect:/commManual/list";
	}
	
	@RequestMapping(value={"/del"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	public String delete(HttpServletRequest request,HttpServletResponse response,String key){

		service.del(key);	
		
		return "redirect:/commManual/list";
	}
	
	@ResponseBody
	@RequestMapping(value="/test", produces = "application/json; charset=UTF-8")
	public  String test(){
		
		List<CommodityManualDict> list = service.findAll();
		Gson gson = new Gson();
		String json = gson.toJson(list);
		return json;
	}
	
	
}
