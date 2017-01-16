package com.trs.commodity.controller.dataAnalyse;

import java.util.List;
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.trs.commodity.controller.dataAnalyse.RtComtradeFeneCal;
import com.trs.commodity.controller.dataAnalyse.RtComtradeFeneToDB;
import com.trs.commodity.model.CommodityInfo;
import com.trs.commodity.service.CommodityInfoService;
import com.trs.common.controller.BaseTest;

//@Controller
//@RequestMapping("testThread")
public class ProRtComtradeFene extends BaseTest{	

	@Autowired
	private CommodityInfoService commInfoService ;
	
	@Autowired
	BasicDataSource dataSource;
	 /**
	  * 线程管理（存放处理前的数据集合）
	  */
	 public static ConcurrentLinkedQueue<CommodityInfo> queueBegin = new ConcurrentLinkedQueue<CommodityInfo>();
	 /**
	  * 线程管理（存放处理后的数据集合）
	  */
	 public static ConcurrentLinkedQueue<CommodityInfo> queueEnd = new ConcurrentLinkedQueue<CommodityInfo>();
	 /**
	 * 线程辅助类
	 */
	 public static CountDownLatch latch=new CountDownLatch(2);  
	 /**
	  * 入库线程中断标志
	  */
	 public static boolean   isBreak=false;
	 /**
	  * 数据分析线程运行标志
	  */
	 public static	boolean  isRun=false;
	 /**
	  * 数据入库计时器
	  */
	 public static	Timer  timer=new Timer();
	 
	 
//	 @RequestMapping(value="/test")
	 @Test
	 public void test2(){
			//如果处理数据的进程正在运行，则返回
	        if(isRun){
	        	return;
	        }
	        isRun=true;  
			isBreak=false; 
	      	List<CommodityInfo> list = commInfoService.findRtEmptyComtradeRate();
	      	for(CommodityInfo item : list){
	      		queueBegin.add(item);
	      	}      	
	      	RtComtradeFeneCal thread1 = new RtComtradeFeneCal(commInfoService);
	      	RtComtradeFeneCal thread2 = new RtComtradeFeneCal(commInfoService);
	      	RtComtradeFeneCal thread3 = new RtComtradeFeneCal(commInfoService);
	      	RtComtradeFeneCal thread4 = new RtComtradeFeneCal(commInfoService);
	      	thread1.start();//开启数据分析进程1
	      	thread2.start();//开启数据分析进程2
	      	thread3.start();//开启数据分析进程3
	      	thread4.start();//开启数据分析进程4
			//开始定时操作入库
	      	timer.schedule(new RtComtradeFeneToDB(dataSource), 0, 1000); 
		    try {

		    	latch.await();//使得主线程(main)阻塞直到latch.countDown()为零才继续执行
				isBreak=true;
		        isRun=false;
//		        Thread.sleep(100000000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		 
		 
		 
		 
		 
	 }
	
	
	
	
	
	
	
	
}
