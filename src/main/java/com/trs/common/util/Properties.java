package com.trs.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;



public class Properties implements Cloneable {
	private HashMap<String,String> map=new HashMap<String,String>();
	public void load(InputStream in) throws IOException {
		load(new BufferedReader(new InputStreamReader(in)));
	}
	public void load(Reader reader) throws IOException {
		load(new BufferedReader(reader));
	}
	public void save(Writer writer){
		PrintWriter printWriter=new PrintWriter(writer);
		for(Map.Entry<String, String> entry:map.entrySet()){
			printWriter.append(entry.getKey())
				.append('=')
				.append(StringUtils.defaultString(entry.getValue()))
				.println();
		}
		printWriter.flush();
	}
	protected void load(BufferedReader reader) throws IOException {
		PropertiesParser parser=new PropertiesParserImpl();
		PropertiesHandler handler=new PropertiesHandler(){

			public boolean comment(String line, String comment) {
				//do nothing
				return true;
			}

			public boolean property(String line, String name, String value) {
				//保存属性
				map.put(name, value);
				return true;
			}

			public void end() {
				// do nothing
			}

			public boolean start() {
				// do nothing
				return true;
			}

		};
		parser.parse(reader, handler);
	}
	public String getProperty(String key){
		return map.get(key);
	}
	public Set<String> listPropertyKeys(){
		return map.keySet();
	}
	public void clear(){
		map.clear();
	}
	@Override
	public Object clone() {
		Properties properties=new Properties();
		properties.map=(HashMap<String, String>) this.map.clone();
		return properties;
	}
	@Override
	public boolean equals(Object obj) {
		if(this==obj) return true;
		if(null==obj) return false;
		if(!(obj instanceof Properties)) return false;
		Properties other=(Properties) obj;
		return this.map.equals(other.map);
	}
	@Override
	public int hashCode() {
		return map.hashCode();
	}
}
