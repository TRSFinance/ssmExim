package com.trs.commodity.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.csvreader.CsvReader;

/**
 * 自定义文件处理类
 * @author Ant
 *
 */
public class CustomFileUtil {

	private Logger log = Logger.getLogger(CustomFileUtil.class);
	
	/**
	 * 读取csv文件数据，返回list集合，集合中第一个map为<"isLimit", "false">或者<"isLimit", "true">用来判断是否是受限文件
	 * @param csvFile
	 * @return
	 */
	public List<Map<String,String>> csvInfoList(File csvFile){
		
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		Map<String,String> baseMap = new HashMap<String,String>();
		//加一个用来判断是否是受限文件的map，默认是false
		baseMap.put("isLimit", "false");
		//开始解析csv文件数据
        CsvReader reader = null; 
		 try { 
	        //一般用这编码读就可以了  
        	reader = new CsvReader(csvFile.getAbsolutePath(),',',Charset.forName("UTF-8"));  
//        	reader.readHeaders(); // 跳过表头   如果需要表头的话，不要写这句。
        	//移动游标到第一行数据
	       	reader.readRecord();
	       	//获取第一行的数据
	       	String[] dataHeader = reader.getValues(); 
	       	//判断是否采集时受限，如果是受限文件，即1小时内访问联合国网址超过规定次数生成的问题文件，则需要重新采集
	       	if(dataHeader[0].length()>15){
	       		//说明该文件是采集时访问受限的文件
	       		baseMap.put("isLimit", "true");
	       		list.add(baseMap);
		       	log.info("文件["+csvFile.getName()+"]采集时受限，需要重新采集");
	       		return list;
	       	}
	       	list.add(baseMap);
           //逐行读入除表头的数据
             while(reader.readRecord()){    
            	 Map<String,String> map = new HashMap<String,String>();               	
            	 String[] data = (reader.getValues());                     
            	//判断是否有数据
            	if(data[0].length()>2){	
            		log.info("文件["+csvFile.getName()+"]无数据");
            		return list;
            	}
            	try{
		            map.put("classification", data[0].trim().length()==0?"":data[0].trim());
		            map.put("yr", data[1].trim().length()==0?"":data[1].trim());
		            map.put("aggregateLevel", data[4].trim().length()==0?"":data[4].trim());
		            map.put("isLeafCode", data[5].trim().length()==0?"":data[5].trim());
		            map.put("tradeFlowCode", data[6].trim().length()==0?"":data[6].trim());
		            map.put("reporterCode", data[8].trim().length()==0?"":data[8].trim());
		            map.put("partnerCode", data[11].trim().length()==0?"":data[11].trim());
		            map.put("commodityCode", data[21].trim().length()==0?"":data[21].trim());
		            map.put("qtyunitcode", data[23].trim().length()==0?"":data[23].trim());
		            map.put("altqtyunit", data[27].trim().length()==0?"":data[27].trim());
		            map.put("netweight", data[29].trim().length()==0?"":data[29].trim());
		            map.put("tradeValue", data[31].trim().length()==0?"":data[31].trim());
		            list.add(map);
            	}catch(Exception e){
            		e.printStackTrace();
            		System.out.println("该条数据格式有问题，跳过该条数据...");
            		continue;
            	}
             }   
		}catch (FileNotFoundException e) {  
	        e.printStackTrace();  
	        log.info(e.getMessage(),e);
	    }catch (IOException e) {
			e.printStackTrace();
	        log.info(e.getMessage(),e);
		} finally {  
	        if(reader != null) {  
	            reader.close();  
	        }  
	    }  
		return list;
	}

	
	
}
