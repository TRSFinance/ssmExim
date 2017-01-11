package com.trs.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 字符串处理工具
 * @author zxh
 *
 */
public class StringUtil {
	private  static  String[]  TRS_STR_S={"(",")","[","]",",","/","@","=",">","<","!","&","*","^","-","+"};
    
	private  static  String[]  PATTERN_STR={"\\=\\u005C","$=\\u0024", "(=\\u0028",")=\\u0029", "*=\\u002A", "+=\\u002B", ".=\\u002E", "[=\\u005B", "?=\\u003F",  "^=\\u005E", "{=\\u007B", "|=\\u007C"};
	private  static  String[]  PATTERN_STRS={"\\","$", "(", ")",  "+", ".", "[", "]", "?", "^", "{", "}", "|"};
	
	public  static final String   CENTER="****";
	/**
	 * 转义海贝查询字符
	 * @param value
	 *        要转义的字符串
	 * @return
	 */
	public  static   String    HBFigStr(String   value){
		if(value!=null){
		for (int i = 0; i < TRS_STR_S.length; i++) {
			value=value.replace(TRS_STR_S[i], "\\"+TRS_STR_S[i]);
		}}
		return value;
	}
	
	public  static   String    HBFigStrs(String   value){
		if(value!=null){
		for (int i = 0; i < PATTERN_STRS.length; i++) {
			value=value.replace(PATTERN_STRS[i], "\\"+PATTERN_STRS[i]);
		}}
		return value;
	}
	
	/**
	 * 转义正则表达式字符
	 * @param value
	 * @return
	 */
	public  static  String   PatternStr(String value){
		for (int i = 0; i < PATTERN_STR.length; i++) {
			String[]  partStr=PATTERN_STR[i].split("=");
			value=value.replace(partStr[0], partStr[1]);
		}
		return  value;
	}
	
	/**
	 * 判断字符串是否为NULL 是NULL返回空字符串
	 * @param value
	 * @return
	 */
	public  static  String   isNullBackStr(String value){
		if(!StringUtils.isNotBlank(value))
			return "";
		return value;
	}
	
	/**
	 * 去掉正文中的 IMAGE 标签
	 * @param value
	 * @return
	 */
	public  static  String   removeIMG(String   value){
		if(!StringUtils.isNotBlank(value))
			return "";
		Pattern pattern = Pattern
				.compile("(<IMAGE( IDX=(x?)(\\d+))?(\\s+SRC=\"?([^>\"]+)\"?)?\\s*>)",Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(value);
			value = matcher.replaceAll("");
		return value	;}
	/**
	 * 身份证隐私处理
	 * @param ID
	 *        身份证号
	 * @return
	 */
	public  static  String   hideCode(String  ID){
		if(ID==null||"".equals(ID))
			return "*";
		if(ID.length()!=19)
			return ID;
		String begin=ID.substring(0, 11);
		String end=ID.substring(15, 19);
		return begin+CENTER+end;
	}
	

	/*
	 * 转换字符串到int类型
	 */
	public static int convertStr2Int(String srcStr, int defaultVal) {
		if (srcStr == null || srcStr.trim().length() == 0)
			return defaultVal;
		try {
			return Integer.valueOf(srcStr.trim()).intValue();
		} catch (ClassCastException ex) {
			System.out.println("convert " + srcStr + " to int exception!"
					+ ex.getMessage());
			return defaultVal;
		}
	}

	
}



