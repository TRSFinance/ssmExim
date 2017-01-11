package com.trs.common.util;

public interface PropertiesHandler {

	boolean start();
	boolean comment(String line,String comment);
	boolean property(String line,String name,String value);
	void end();
}
