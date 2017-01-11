package com.trs.commodity.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.trs.commodity.model.CommodityInfo;
import com.trs.commodity.model.Procsvlogs;
import com.trs.commodity.service.CommodityInfoService;
import com.trs.commodity.service.ProcsvlogsService;
import com.trs.commodity.util.ConfigManager;
import com.trs.commodity.util.CustomFileUtil;
import com.trs.common.controller.BaseTest;


public class ReadCsv2DbController extends BaseTest{

	private Logger log = Logger.getLogger(ReadCsv2DbController.class);

	@Autowired
	private CommodityInfoService service ;
	
	@Autowired
	private ProcsvlogsService proService;
	
	@Test
	public void insertList(){
		
		String folder = ConfigManager.getValueByKey("CSV_FOLDER");
		readCsv(folder);
	
	}	

	
	/**
	 * 读取指定目录中所有的csv文件入库
	 * @param csv_path
	 */
	public void readCsv(String csv_path){

		File file = new File(csv_path);
		if(file.exists()){
			File[] files = file.listFiles();
			if(files.length==0){
				System.out.println("文件夹为空,可修改config.ini文件中CSV_FOLDER参数来指定读取目录");
				log.info("文件夹为空,可修改config.ini文件中CSV_FOLDER参数来指定读取目录");
				return;
			}else{
				for(File csvFile : files){
					//如果是文件夹
					if(csvFile.isDirectory()){
						System.out.println("开始遍历文件夹："+csvFile.getAbsolutePath());
						log.info("开始遍历文件夹："+csvFile.getAbsolutePath());
						//递归来读取文件
						readCsv(csvFile.getAbsolutePath());
						//如果文件夹为空,删除
						if(csvFile.listFiles().length==0){
							csvFile.delete();
						}
					}else if(csvFile.getName().contains(".csv")){						
						readSingleCsv(csvFile);
						//入库后删除文件
						csvFile.delete();						
					}
				}				
			}
		}else{
			System.out.println("文件夹或文件不存在,请修改config.ini文件中CSV_FOLDER参数");
			log.info("文件夹或文件不存在,请修改config.ini文件中CSV_FOLDER参数");
		}
	}

	
	/**
	 * 读取单个csv文件,数据一次性入库
	 */
	public void readSingleCsv(File csvFile){

       	List<CommodityInfo> list = new ArrayList<CommodityInfo>();		
		//带扩展名的csv名称
		String csvNameExtention = csvFile.getName();
		//不带扩展名的csv名称
		String csvName = csvNameExtention.substring(0,csvNameExtention.length()-4);
		System.out.println("开始读取文件"+csvFile.getAbsolutePath());
		log.info("开始读取文件"+csvFile.getAbsolutePath());
		String[] proArray = csvName.split("_");
		String yr = proArray[0];
		String rtCode = proArray[1];
		String ptCode = proArray[2];
		Procsvlogs pro = new Procsvlogs();
		pro.setYr(Integer.parseInt(yr));
		pro.setReporterCode(rtCode);
		pro.setPartnerCode(ptCode);
		pro.setStatus("0");
		pro.setIslimit("0");
		pro.setStartTime(new Date());
		//如果日志表中存在该年份,报告国和对象国的数据,删除日志表和商品信息表中的数据。重新来进行插入操作
		if(proService.isExist(pro)){
			//删除日志表中数据
			proService.del(pro);
			//删除商品信息表中对应的数据
			service.delByProcsvlogs(pro);
			System.out.println("删除之前的商品数据，重新进行入库操作。。。");
			log.info("删除之前的商品数据，重新进行入库操作。。。");
		}
		//日志表中记录开始读取的信息
		proService.add(pro);
		//获取csv文件中的数据集合
		CustomFileUtil fileUtil = new CustomFileUtil();
		List<Map<String,String>> csvInfo_list = fileUtil.csvInfoList(csvFile);
		//判断是否采集时受限，如果是受限文件，即1小时内访问联合国网址超过规定次数生成的问题文件，则需要重新采集
		if(csvInfo_list.get(0).get("isLimit").equals("true")){
			System.out.println("文件["+csvNameExtention+"]采集时受限，需要重新采集");
			//设置日志表中文件受限状态为1
			pro.setIslimit("1");
			pro.setFinishTime(new Date());
			proService.update(pro);
			return;
		}    
         //逐条读取除了第一个map的数据
         for(int i=1;i<csvInfo_list.size();i++){                           		
			CommodityInfo comm = new CommodityInfo();
//    			System.out.println("开始插入第"+i+"条记录");
		    try {	             	
				comm.setClassification(csvInfo_list.get(i).get("classification"));
				comm.setYr(Integer.parseInt(csvInfo_list.get(i).get("yr")));
		    	comm.setAggregateLevel(csvInfo_list.get(i).get("aggregateLevel"));
		    	comm.setIsLeafCode(csvInfo_list.get(i).get("isLeafCode"));
				comm.setTradeFlowCode(csvInfo_list.get(i).get("tradeFlowCode"));
				comm.setReporterCode(csvInfo_list.get(i).get("reporterCode"));
				comm.setPartnerCode(csvInfo_list.get(i).get("partnerCode"));
		    	comm.setCommodityCode(csvInfo_list.get(i).get("commodityCode"));
		    	comm.setQtyunitcode(csvInfo_list.get(i).get("qtyunitcode"));
		    	comm.setAltqtyunit(csvInfo_list.get(i).get("altqtyunit").length()==0?0:new BigDecimal(csvInfo_list.get(i).get("altqtyunit")).longValue());	
		    	comm.setNetweight(csvInfo_list.get(i).get("netweight").length()==0?0:new BigDecimal(csvInfo_list.get(i).get("netweight")).longValue());
		    	comm.setTradeValue(csvInfo_list.get(i).get("tradeValue").length()==0?0:new BigDecimal(csvInfo_list.get(i).get("tradeValue")).longValue());
		    	comm.setCrtime(new Date());   		    	
    			//处理 QTYUNITCODE 长度过长的问题
    			if((comm.getQtyunitcode()!=null)&&comm.getQtyunitcode().length()>1){
    				continue;	
    			}		    	
		    	list.add(comm);       		    	
		    	//如果list集合有5000条数据，则执行一次入库操作
		    	if((list.size()%3000)==0){
		    		//将数据插入数据库
		    		try{
		    			service.addList(list);
		    			//将list清空
		    			list = new ArrayList<CommodityInfo>();
		    		}catch(Exception e){
		    			e.printStackTrace();
		    			//如果报错，说明某条数据存在问题，则改成逐条插入数据的方式
		    			readCsvOneByOne(list);
		    			//将list清空
		    			list = new ArrayList<CommodityInfo>();
		    		}
		    	}		    	
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("第["+i+"]行数据有问题,跳过");
				log.info("第["+i+"]行数据有问题,跳过");
				continue;
			}
		 }
		//如果list不为空，则执行入库操作
        if(list.size()>0){
        	try{
    			service.addList(list);
    		}catch(Exception e){
    			e.printStackTrace();
    			//如果报错，说明某条数据存在问题，则改成逐条插入数据的方式
    			readCsvOneByOne(list);			
    		}
        }
        //设置日志表中入库状态为1
		pro.setStatus("1");
		pro.setFinishTime(new Date());
		proService.update(pro);	
		System.out.println("文件["+csvFile.getAbsolutePath()+"]处理完毕,花费时间["+(pro.getFinishTime().getTime()-pro.getStartTime().getTime())/1000+"秒]");		 
		log.info("文件["+csvFile.getAbsolutePath()+"]处理完毕,花费时间["+(pro.getFinishTime().getTime()-pro.getStartTime().getTime())/1000+"秒]");
	}
	
	/**
	 * 逐条插入数据，遇到问题数据，跳过
	 * @param list
	 */
	public void readCsvOneByOne(List<CommodityInfo> list){
		
		System.out.println("开始逐条插入数据库。。。");
		log.info("开始逐条插入数据库。。。");
		for(CommodityInfo comm:list){			
			try{
				service.add(comm);
			}catch(Exception e){
				e.printStackTrace();
				continue;
			}
		}			
	}
	
	@Test
	//将采集受限文件的信息以json格式存入到txt文件中
	public void createTxtFilesLimited(){
		
		String filePath = ConfigManager.getValueByKey("FILESLIMITEDINFOPATH");
		//获取采集受限的文件信息
		List<Procsvlogs> list = proService.findFileLimited();
		//将信息以json的形式存入到txt中
		Gson gson = new Gson();
		String json = gson.toJson(list);
		File directory = new File(filePath);
		if(!directory.exists()){
			directory.mkdirs();
		}		
		File file = new File(directory+"/filesLimited.txt");
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
		try {    
            FileOutputStream fos = new FileOutputStream(file);  
            Writer os = new OutputStreamWriter(fos, "UTF-8");  
            os.write(json);  
            os.flush();  
            fos.close();  
        } catch (FileNotFoundException e) {    
            e.printStackTrace();    
        } catch (IOException e) {    
            e.printStackTrace();    
        }    		
	}
	
	
	
}
