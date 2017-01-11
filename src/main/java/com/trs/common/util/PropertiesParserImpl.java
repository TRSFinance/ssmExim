package com.trs.common.util;

import java.io.BufferedReader;
import java.io.IOException;


public class PropertiesParserImpl implements PropertiesParser {

	public void parse(BufferedReader source, PropertiesHandler handler) throws IOException {
		String sLine=null;
		int lineNum=0;
		if(!handler.start())
			return;
		while((sLine=source.readLine())!=null){
			String line=sLine;
			lineNum++;
			line=line.trim();
			if(line.length()==0)
				continue;
			//以#号开始的为注释
			if('#'==line.charAt(0)){
				if(handler.comment(sLine, line.substring(1)))
					continue;
				else
					return;
			}
			int index=line.indexOf('=');
			//未附值的属性
			if(index==-1){
				if(handler.property(sLine, line, null))
					continue;
				else
					return;
			}
			String key=line.substring(0, index);
			String value=line.substring(index+1);
			key=key.trim();
			value=value.trim();
			//语法错误，属性名不能为空串
			if(key.length()==0){
				throw new IOException("第"+lineNum+"行：属性名不能为空串:"+line);
			}
			//未赋值的属性
			if(value.length()==0){
				if(handler.property(sLine, key, null))
					continue;
				else
					return;
			}
			//属性赋值
			if(handler.property(sLine, key, value))
				continue;
			else
				return;
		}
		handler.end();
	}

}
