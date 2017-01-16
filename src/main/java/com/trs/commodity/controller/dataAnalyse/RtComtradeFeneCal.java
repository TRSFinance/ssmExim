package com.trs.commodity.controller.dataAnalyse;

import com.trs.commodity.controller.dataAnalyse.ProRtComtradeFene;
import com.trs.commodity.model.CommodityInfo;
import com.trs.commodity.service.CommodityInfoService;

public class RtComtradeFeneCal  extends Thread{

	CommodityInfoService service;
	 
	 public RtComtradeFeneCal(){
		 
	 }
	 public RtComtradeFeneCal(CommodityInfoService service){
		 this.service=service;
	 }
	 /**
	  * 处理占报告国贸易份额，较上一年的贸易增量和贸易增速三个字段数据
	  */
	public void run(){
		
		System.out.println("开始进行数据分析处理。。。");
		while (!ProRtComtradeFene.queueBegin.isEmpty()) {			
			CommodityInfo comm = service.rtComtradeFene(ProRtComtradeFene.queueBegin.poll()); //计算占报告国贸易份额
			comm = service.zengliangAndzengsu(comm);//计算较上一年的贸易增量和贸易增速
			if(comm!=null){
				ProRtComtradeFene.queueEnd.offer(comm);//将处理后的数据放入到线程管理集合中	
			}					
		}
		System.out.println("数据分析处理完成。。。");
		ProRtComtradeFene.latch.countDown();//进程完成，计数器减一 
	}

	public CommodityInfoService getService() {
		return service;
	}
	public void setService(CommodityInfoService service) {
		this.service = service;
	}
}
