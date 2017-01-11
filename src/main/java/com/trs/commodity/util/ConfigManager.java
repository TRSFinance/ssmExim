package com.trs.commodity.util;


import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;


/**
 * 读取配置文件
 * @author Ant
 *
 */
public class ConfigManager {
	
	public static String   configName="config.ini";

	 //存储配置值
    public static   Properties props = new Properties();
    
	/**
	 * 
	 * @Title getValueByKey
	 * @Description TODO(获取配置文件的信息)
	 * @param key
	 * @return
	 */
	public static String  getValueByKey(String  key){
		try {
			props.load(new InputStreamReader(ConfigManager.class.getResourceAsStream("/" + configName),"UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("找不到配置文件:"+configName);
		}
		return props.getProperty(key);		
	}
	


}
