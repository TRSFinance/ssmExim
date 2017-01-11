package com.trs.common.util;

import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 读取保存在WEB-INF/config.ini中的系统配置。
 * @author zxh
 *
 */
public class SystemPropertyManager {

	//fields	---------------------------------------------------------------------
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SystemPropertyManager.class);
	private File configFile;
	private String configFilePath;
	private ConfigManager configManager;
	

	//methods	---------------------------------------------------------------------
	public void init() throws IOException {
		configManager = ConfigManager.getInstance(configFile);
		configFilePath = configFile.getPath();
	}

	/**
	 * 取配置参数值。
	 *
	 * @param name
	 *            要取的参数名。
	 * @return 参数值。
	 */
	public String getPropertiesValue(String name) {
		String strValue = null;
		if (name == null)
			return null;
		try {
			strValue = (String) configManager.getConfigItem(name);
		} catch (IOException e) {
			LOGGER.error("取配置参数值失败", e);
		}
		if (strValue == null) {
			LOGGER.debug("从{}配置文件取{}参数的值时为空", configFilePath, name);
			return strValue;
		}
		
		return strValue;
	}

	//accessors	---------------------------------------------------------------------
	public File getConfigFile() {
		return configFile;
	}

	public void setConfigFile(File configFile) {
		this.configFile = configFile;
	}



}
