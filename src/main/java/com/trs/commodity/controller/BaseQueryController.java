package com.trs.commodity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 基本数据查询
 * @作者    邹许红
 * @日期  2017/01/13
 */
@Controller
@RequestMapping("/query")
public class BaseQueryController {
	
	
   @RequestMapping("/basequery")
   public  String   query(){
	   System.out.println("测试");
	return "commodity/query/basequery";
	   
   }
}
