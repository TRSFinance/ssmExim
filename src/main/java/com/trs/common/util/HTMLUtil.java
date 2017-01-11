package com.trs.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * HTML 标签处理工具
 * @author 邹许红
 *
 */
public class HTMLUtil {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(HTMLUtil.class);
	 private static final String  PATTERN_C="{1}[^;；.。！!?？]{0,}";
	 private static final String  PATTERN_E="[;|；|.|。|！|!|?|？]{1}";
	/**
	 * HTML标签转义
	 * @param str
	 * @return
	 */
	public static String escapeXml(String str){
		if(str==null||StringUtils.isEmpty(str)) return str;
		String result="";
		for(int i=0;i<str.length();i++){
			char ch=str.charAt(i);
			switch(ch){
			case '<':
				result+="&lt;";
				break;
			case '>':
				result+="&gt;";
				break;
			case '&':
				result+="&amp;";
				break;
			case '\'':
				result+="&#039;";
				break;
			case '\"':
				result+="&#034;";
				break;
			default:
				result+=ch;
			}
		}
		return result;
	}

	/**
	 * 处理xml中的无效字符
	 * @param in
	 * @return
	 */
	public static String stripNonValidXMLCharacters(String in) {
	    StringBuffer out = new StringBuffer(); // Used to hold the output.
	    char current; // Used to reference the current character.

	    if (in == null || ("".equals(in)))
	        return ""; // vacancy test.
	    for (int i = 0; i < in.length(); i++) {
	        current = in.charAt(i); // NOTE: No IndexOutOfBoundsException caught
	                                // here; it should not happen.
	        if ((current == 0x9) || (current == 0xA) || (current == 0xD)
	                || ((current >= 0x20) && (current <= 0xD7FF))
	                || ((current >= 0xE000) && (current <= 0xFFFD))
	                || ((current >= 0x10000) && (current <= 0x10FFFF)))
	            out.append(current);
	    }
	    return out.toString();
	}
	/**
	 * 对链接地址进行清理，避免一些恶意攻击.
	 *
	 * @param url the url
	 * @return the string
	 */
	public static String cleanUrl(String url){
		url=StringUtils.trim(url);
		if(StringUtils.startsWithIgnoreCase(url, "http://"))
			return "";
		if(StringUtils.startsWithIgnoreCase(url, "javascript:"))
			return "";
		if(StringUtils.contains(url, ".."))
			return "";
		return url;
	}

	/*
	 * 限制表格的宽度
	 */
	public static String limitTableWidth(String content, int width) {
//		String result = content;
		String rexString = "width\\s{0,}=\\s{0,}\"(\\d{3,})\"";
		Pattern pattern = Pattern.compile(rexString,Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(content);
		StringBuilder stringBuilder = new StringBuilder();
		int index = 0;
		while (matcher.find()) {
			stringBuilder.append(content
					.substring(index, matcher.start()));
			String widthString = matcher.group(1);
			if (Integer.parseInt(widthString) > width) {
				stringBuilder.append("width=\"" + width + "\"");
			}else
				stringBuilder.append(matcher.group());
			index = matcher.end();
		}
		stringBuilder.append(content.substring(index));
		return stringBuilder.toString();
	}
	public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\\t*|\\\\r|\\\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        dest = dest.replaceAll("　", "").trim();
        return dest;
    }
	public static String limitTableWidth(String content) {
		return limitTableWidth(content, 597);
	}

	public static String formatContentAndXh(String hkeyBbsnum, String hkey, String content,
			String pClass, String imageServlet) {
		String pPrefix = "<p class=\"" + pClass + "\">";
		String pPostfix = "</p>";
		return formatContentAndXh(hkeyBbsnum, hkey, content, pPrefix, pPostfix, imageServlet);
	}
	public static String formatContentAndXhWithStyle(String hkeyBbsnum, String hkey, String content,
			String pStyle, String imageServlet) {
		String pPrefix = "<p style=\"" + pStyle + "\">";
		String pPostfix = "</p>";
		return formatContentAndXh(hkeyBbsnum, hkey, content, pPrefix, pPostfix, imageServlet);
	}
    /**
     * 
     * @param style
     *        样式
     * @param content
     *        正文
     * @param cName
     *        简称
     * @param fName
     *        全称
     * @param enPoint
     *        命中点
     * @param align    
     * @return
     */
	public static  String  formatContentStyleS(String style,String content,String cName,String align,String fName,String enPoint){
		String pPrefix ="";
		String pPostfix = "";
		String[]  impacts=null;
		String  enPoints="";
		String[]  _enPoints=null;
		List<String>  list=new ArrayList<String>();
		if(align!=null){
			pPrefix = "<p style=\"" + style + "\"  align=\""+align+"\">";
		    pPostfix = "</p>";
		}else{
			pPrefix = "<p style=\"" + style + "\">";
			pPostfix = "</p>";
		}
		if(cName!=null&&!"".equals(cName))
			impacts=cName.split(";");
		if(enPoint!=null&&!"".equals(enPoint)){
			if(enPoint.contains("_")){
				enPoints=enPoint.split("_")[1];
			}else{
				enPoints = enPoint;
			}
			
			
		}
		if(fName!=null&&!"".equals(fName))
			list.add(fName);
		if(impacts!=null&&impacts.length>0){
			for (int i = 0; i < impacts.length; i++) {
				list.add(impacts[i]);
			}
			
		}
		
		if(enPoints!=null&&!"".equals(enPoints)){
			_enPoints=enPoints.split("\\*");
			for (int i = 0; i < _enPoints.length; i++) {
				list.add(_enPoints[i]);
			}
		}
		
		
		
		return formatContentAndXh(content, pPrefix, pPostfix, impacts,list,enPoints,fName,cName);
	}
	
	
	public static String formatContentStyleTitle(String style,
			String urlTitle, String cName, String align, String fName,
			String enpoint) {
		
		String pPrefix ="";
		String pPostfix = "";
		String[]  impacts=null;
		String  enPoints="";
		String[]  _enPoints=null;
		List<String>  list=new ArrayList<String>();
		if(align!=null){
			pPrefix = "<p style=\"" + style + "\"  align=\""+align+"\">";
		    pPostfix = "</p>";
		}else{
			pPrefix = "<p style=\"" + style + "\">";
			pPostfix = "</p>";
		}
		if(cName!=null&&!"".equals(cName))
			impacts=cName.split(";");
		if(enpoint!=null&&!"".equals(enpoint))
			enPoints=enpoint.split("_")[1];
		if(fName!=null&&!"".equals(fName))
			list.add(fName);
		if(impacts!=null&&impacts.length>0){
			for (int i = 0; i < impacts.length; i++) {
				list.add(impacts[i]);
			}
			
		}
		
		if(enPoints!=null&&!"".equals(enPoints)){
			_enPoints=enPoints.split("\\*");
			for (int i = 0; i < _enPoints.length; i++) {
				list.add(_enPoints[i]);
			}
		}
		
		
		return formatContentAndXh(urlTitle+"。", pPrefix, pPostfix, impacts,list,enPoints,fName,cName).replace("。", "");
		
	}

	
	public static String  creatPattern(List<String>  list){
		String  pattern="(";
		if(list!=null&&list.size()>0){
		
			for (int i = 0; i < list.size(); i++) {
				    String jc= list.get(i);
				    if(i==0){
					pattern=pattern+StringUtil.PatternStr(jc);}
				    else {
				    pattern=pattern+"|"+StringUtil.PatternStr(jc);
					}
			}
		}else{
			
			pattern=pattern+".*";
			
		}
		
		
		
		pattern=pattern+"){1}";
		
		
		
		
		return pattern;
		
		
		
		
	}
	
	
	
	/**
	 * 拼接正则表达式
	 * @param cata
	 *        以*分割的事件 打标字符串
	 * @param fName
	 *        全称
	 * @param  cName
	 *        简称
	 * @return
	 */
	public  static String  creatPattern(String  cata,String  fName,String cName){
		
		String  pattern="("+StringUtil.PatternStr(fName);
		if(StringUtils.isNotBlank(cName)){
			String[]  jcnames=cName.split(";");
			for (int i = 0; i < jcnames.length; i++) {
				String jc= jcnames[i];
					pattern=pattern+"|"+StringUtil.PatternStr(jc);
			}
		}
		
		
		
		pattern=pattern+")"+PATTERN_C;
		String[] catas=cata.split("\\*");
		for (int i = 0; i < catas.length; i++) {
			pattern=pattern+"("+StringUtil.PatternStr(catas[i])+")"+PATTERN_C;
			if(i==(catas.length-1))
				pattern=pattern+PATTERN_E;
		}
		
		return pattern;
	}
	
	
	/**
	 * 
	 * @param enPointPattern
	 *        命中句号
	 * @param p
	 *        段落
	 * @param redPattern
	 *        标红正则表达式
	 * @return
	 */
	public  static String  formatEnPointContent(String enPointPattern,String p,String redPattern){
		
		Pattern pattern=Pattern.compile(enPointPattern);
		Matcher enMatcher = pattern.matcher(p);
		int  num=0;
		while (enMatcher.find()) {
			String enPointContent=enMatcher.group();
			p=p.replace( enPointContent,enPointContent.replaceAll(redPattern, "<span style=\"color:red\">$1</span>"));
			num++;
		}
		if(num==0)
			p=p.replaceAll(redPattern, "<span style=\"color:red\">$1</span>");
		
		return p;
		
	}
	
	
	
	public static String formatContentAndXh(String content,String pPrefix, 
			String pPostfix,String[] impacts,List<String> list,
			String enPoints,String fName,String cName){
		// 获取所有段落
					Pattern pPattern = Pattern
							.compile("[\\t\\f\\x0B\\u3000\\u0020\\u00A0\\uE5D9]*([^\\n\\r]+)[\\t\\f\\x0B\\u3000\\u0020\\u00A0\\uE5D9]*([\\n\\r]|$)");
					
					
					//命中点段落
					String   enPointPattern=creatPattern(enPoints, fName, cName);
					//需要标红的关键词
					String   redPattern=creatPattern(list);
					Matcher matcher = pPattern.matcher(content);
					StringBuilder stringBuilder = new StringBuilder(content.length());
					while (matcher.find()) {
						String p = matcher.group(1);
						stringBuilder.append(pPrefix);
						stringBuilder.append(formatEnPointContent(enPointPattern, p, redPattern));
						stringBuilder.append(pPostfix);
						
						
					}
					
					return stringBuilder.toString();
					
		
		
		
	}
	public  static String   replaceRed(String  content,String[] impacts){
		if(impacts==null)
			return content;
		for (int i = 0; i < impacts.length; i++) {
			String impact=impacts[i];
			String repImpact="<span style=\"color:red\">"+impact+"</span>";
			
			content=content.replace(impact, repImpact);

		}
		
		
		
		return content;
	}
	
	
	
	

	public static String formatContentAndXh(String hkeyBbsnum, String hkey, String content,
			String pPrefix, String pPostfix, String imageServlet) {
		if (content == null)
			return "";
	
	
			// 获取所有段落
			Pattern pPattern = Pattern
					.compile("[\\t\\f\\x0B\\u3000\\u0020\\u00A0\\uE5D9]*([^\\n\\r]+)[\\t\\f\\x0B\\u3000\\u0020\\u00A0\\uE5D9]*([\\n\\r]|$)");
			
			Matcher matcher = pPattern.matcher(content);
			StringBuilder stringBuilder = new StringBuilder(content.length());
			while (matcher.find()) {
				@SuppressWarnings("unused")
				String p = matcher.group(1);
				// 处理每个段落(图片显示、表格显示、转义、首行缩进)
				stringBuilder.append(pPrefix);
				
				
				stringBuilder.append(pPostfix);
			}
			return stringBuilder.toString();
		
	}

	public static String escapeHitString(String abs) {
		if (abs == null)
			return null;
		String tempAbs = trimStringHead(abs);
		Pattern pattern = Pattern
				.compile("(<font color=[a-zA-Z]+\\>)|(</font>)|(<span class=\"keyword\">)|(</span>)",Pattern.CASE_INSENSITIVE);
		int index = 0;
		Matcher matcher = pattern.matcher(tempAbs);
		StringBuilder stringBuilder = new StringBuilder();
		while (matcher.find()) {
			stringBuilder.append(HTMLUtil.escapeXml(tempAbs
					.substring(index, matcher.start())));
			stringBuilder.append(matcher.group());
			index = matcher.end();
		}
		stringBuilder.append(HTMLUtil.escapeXml(tempAbs
				.substring(index)));
		return stringBuilder.toString();
	}

	public static String removeHitTags(String str) {
		if (str == null)
			return str;
		Pattern pattern = Pattern
				.compile("(<font color=[a-zA-Z]+\\>)|(</font>)|(<span class=\"keyword\">)|(</span>)",Pattern.CASE_INSENSITIVE);
		Matcher matcher=pattern.matcher(str);
		return matcher.replaceAll("");
	}

	/**
	 * 对带有trs命中点描红的字符串进行HTML转义和截取处理.
	 * <p>截取的原则：</p>
	 * <ul>
	 * <li>尽量保留描红的命中点；</li>
	 * <li>非命中点字符串省略中间的字符，保留开头和结尾的字符；</li>
	 * </ul>
	 *
	 * @param str 带有trs命中点描红的字符串
	 * @param limit 要截取的字符串长度
	 * @param suffix 省略字符的替代符号，一般是"..."
	 * @return 处理完的字符串
	 */
	public static String escapeAndTruncTrsHitString(String str,int limit,String suffix) {
		try {
			if (str == null)
				return null;
			String trimedStr = trimStringHead(str);
			StringBuilder strBuilder=new StringBuilder();
			strBuilder.append(trimedStr);
			Integer[] posArray=parseHitpoints(strBuilder, new String[]{"<font color=red>","<span class=\"keyword\">"}, new String[]{"</font>","</span>"},true);
			return escapeAndTruncHitString(strBuilder.toString(), posArray, false, limit, suffix);
		} catch (Exception e) {
			LOGGER.error("程序BUG，方法调用参数如下：str="+str+",limit="+limit+"suffix="+suffix,e);
			return str;
		}
	}

	/**
	 * 对带有命中点的字符串进行HTML转义和截取处理.
	 * <p>截取的原则：</p>
	 * <ul>
	 * <li>尽量保留描红的命中点；</li>
	 * <li>非命中点字符串省略中间的字符，保留开头和结尾的字符；</li>
	 * </ul>
	 *
	 * @param str 带有命中点描红的字符串
	 * @param posArray 命中点信息
	 * @param hitOnce 相同关键词是否只描红一次
	 * @param limit 要截取的字符串长度
	 * @param suffix 省略字符的替代符号，一般是"..."
	 * @return 处理完的字符串
	 */
	public static String escapeAndTruncHitString(String str,Integer[] posArray,boolean hitOnce,int limit,String suffix) {
		try {
			if (str == null)
				return null;
			StringBuilder resultBuilder=new StringBuilder();
			if(posArray.length==0){
				if(str.length()>limit)
					resultBuilder.append(HTMLUtil.escapeXml(str.substring(0, limit))).append(suffix);
				else
					resultBuilder.append(HTMLUtil.escapeXml(str.toString()));
			}else{
				StringBuilder strBuilder=new StringBuilder();
				strBuilder.append(str);
				if(hitOnce){
					// 去掉重复描红的关键词
					HashSet<String> keywords=new HashSet<String>();
					ArrayList<Integer> posList=new ArrayList<Integer>(posArray.length);
					for(int i=0;i<posArray.length;i+=2){
						String keyword=str.substring(posArray[i], posArray[i+1]);
						if(keywords.contains(keyword))
							continue;
						keywords.add(keyword);
						posList.add(posArray[i]);
						posList.add(posArray[i+1]);
					}
					posArray=posList.toArray(new Integer[posList.size()]);
				}
				int keywordsCount=0;//统计所有关键词的长度，在limit范围内的最大长度
				int i=0;
				ArrayList<Integer> holes=new ArrayList<Integer>();//记录关键词分隔后的各个关键词和非关键词的长度
				int holesLen=0;
				for(;i<posArray.length;i+=2){
					int len=posArray[i+1]-posArray[i];//关键词长度
					if(keywordsCount+len>=limit)
						break;
					keywordsCount+=len;
					if(posArray[i]>0){
						if(i==0)
							holes.add(posArray[i]);
						else
							holes.add(posArray[i]-posArray[i-1]);
						holesLen+=holes.get(holes.size()-1);
					}
				}
				if(i==0){//一开始就超过limit限制了
					resultBuilder.append(str.substring(0,limit)).append("...");
				}else{
					i-=2;
					Integer[] holeLimits=trimHoles(holes.toArray(new Integer[holes.size()]), limit-keywordsCount);
					int holeIdx=0;
					for(int j=0;j<=i;j+=2){
						if(posArray[j]>0){
							resultBuilder.append(midTrunc(strBuilder.substring(posArray[j]-holes.get(holeIdx), posArray[j]), holeLimits[holeIdx], suffix));
							holeIdx++;
						}
						resultBuilder.append("<font color=red>")
							.append(HTMLUtil.escapeXml(strBuilder.substring(posArray[j], posArray[j+1])))
							.append("</font>");
					}
					if(strBuilder.length()<=limit)
						resultBuilder.append(strBuilder.substring(posArray[i+1]));
					else if(posArray[i+1]<strBuilder.length()){
						if(keywordsCount+holesLen<limit){
							resultBuilder.append(strBuilder.substring(posArray[i+1],posArray[i+1]+limit-(keywordsCount+holesLen)));
						}
						resultBuilder.append(suffix);
					}
				}
			}
			return resultBuilder.toString();
		} catch (Exception e) {
			StringBuilder sb=new StringBuilder();
			for(Integer i:posArray){
				if(sb.length()>0)
					sb.append(",");
				sb.append(i);
			}
			LOGGER.error("程序BUG，方法调用参数如下：str="+str+",limit="+limit+",suffix="+suffix+",posArray=["+sb.toString()+"],hitOnce="+hitOnce,e);
			return str;
		}
	}

	public static String midTrunc(String str,int limit,String suffix){
		if(str==null||str.length()==0||str.length()<=limit) return str;
		if(limit==0) return suffix;
		int mid=str.length()/2;
		int half=(str.length()-limit-1)/2;
		int start=mid-half;
		int end=mid+half;
		if((half*2+1)!=(str.length()-limit)){
			if(start>str.length()-end-1){
				start--;
			}else{
				end++;
			}
		}
		return str.substring(0, start)+suffix+str.substring(end+1);

	}

	/**
	 *解析qq的聊天记录
	 * @param abs
	 * @param imageUrl
	 * @return
	 */
	public static String escapeQQHitString(String abs,String imageUrl) {
		if (abs == null)
			return null;
		String tempAbs = trimStringHead(abs);
		Pattern pattern = Pattern
				.compile("(<IMAGE IDX=(\\d+)>)|(<font color=[a-zA-Z]+\\>)|(</font>)|(<span class=\"keyword\">)|(</span>)",Pattern.CASE_INSENSITIVE);
		int index = 0;
		Matcher matcher = pattern.matcher(tempAbs);
		StringBuilder stringBuilder = new StringBuilder();
		while (matcher.find()) {
			stringBuilder.append(HTMLUtil.escapeXml(tempAbs
					.substring(index, matcher.start())));
			if(StringUtils.isNotBlank(matcher.group(1)))
			{
				int oIndex = Integer.valueOf(matcher.group(2)) - 1;//取index,group已由3改为4
				stringBuilder.append("<img src=\"").append(imageUrl).append(oIndex).append("\"/>");
			}
			else
			{
				stringBuilder.append(matcher.group());
			}
			index = matcher.end();
		}
		stringBuilder.append(HTMLUtil.escapeXml(tempAbs
				.substring(index)));
		return stringBuilder.toString();
	}
/**
 * 删除abs中包含的HitString描红标记
 * */
	public static String escapeFontColor(String abs) {
		if (abs == null)
			return null;
		String tempAbs = trimStringHead(abs);
		Pattern pattern = Pattern
				.compile("(<font color=[a-zA-Z]+\\>)|(</font>)",Pattern.CASE_INSENSITIVE);
		int index = 0;
		Matcher matcher = pattern.matcher(tempAbs);
		StringBuilder stringBuilder = new StringBuilder();
		while (matcher.find()) {
			stringBuilder.append(HTMLUtil.escapeXml(tempAbs
					.substring(index, matcher.start())));
			//stringBuilder.append(matcher.group());
			index = matcher.end();
		}
		stringBuilder.append(HTMLUtil.escapeXml(tempAbs
				.substring(index)));
		return stringBuilder.toString();
	}
	public static String escapeTableAndImg(String abs) {
		if (abs == null)
			return null;
		String tempAbs = trimStringHead(abs);
		Pattern pattern = Pattern
				.compile("(<TABLE IDX=(x?)(\\d+)>)|(<IMAGE( IDX=(x?)(\\d+))?(\\s+SRC=\"?([^>\"]+)\"?)?\\s*>)",Pattern.CASE_INSENSITIVE);
		int index = 0;
		Matcher matcher = pattern.matcher(tempAbs);
		StringBuilder stringBuilder = new StringBuilder();
		while (matcher.find()) {
			stringBuilder.append(tempAbs
					.substring(index, matcher.start()));
			//stringBuilder.append(matcher.group());
			index = matcher.end();
		}
		stringBuilder.append(tempAbs
				.substring(index));
		return stringBuilder.toString();
	}

	public static String trimStringHead(String content) {
		if (content == null)
			return null;
		String patternString = "^[\\t\\f\\x0B\\u3000\\u0020\\u00A0]*";
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(content);
		StringBuilder stringBuilder = new StringBuilder(content.length());
		if (matcher.find()) {
			stringBuilder.append(content.substring(matcher.end()));
		} else
			stringBuilder.append(content);
		return stringBuilder.toString();
	}

	public static String removeHtml(String htmlContent) {
		if(htmlContent==null) return null;
		Pattern p1 = Pattern.compile("<[^>]*>");
		Matcher m1 = p1.matcher(htmlContent);
		return m1.replaceAll("");
	}

	private static final Pattern SCRIPT_PATTERN = Pattern
			.compile("[\\\"\\'\\\\\\%\\r\\n]");

	public static final String escapeScript(String script) {
		if (script == null)
			return null;
		Matcher matcher = SCRIPT_PATTERN.matcher(script);
		StringBuilder builder = new StringBuilder(script.length());
		int lastIndex = 0;
		while (matcher.find()) {
			builder.append(script.substring(lastIndex, matcher.start()));
			String hitString = script.substring(matcher.start(), matcher.end());
			for (int i = 0; i < hitString.length(); i++) {
				char ch = hitString.charAt(i);
				if (ch == '\r')
					builder.append("\\r");
				else if (ch == '\n')
					builder.append("\\n");
				else
					builder.append('\\').append(ch);
			}
			lastIndex = matcher.end();
		}
		builder.append(script.substring(lastIndex));
		return builder.toString();
	}

	public static void main(String[] args){
		String a=HTMLUtil.escapeXml("<p class=\"detail-p\">根据《广东省生活垃圾无害化处理设施建设“十二五”规划》和《中山市生活垃圾无害化处理设施建设“十二五”规划》文件，中山市正在规划建设一座餐厨垃圾无害化处理厂。到2015年，中山市城镇垃圾源头减量化率提高到15％；垃圾资源化率（包括焚烧发电、资源回收、堆肥或其他生化处理方式）提高到80％。（新闻资讯来源：新民网 时代环保网）<p>");;//.escapeAndTruncHitString("河南信阳儿童福利院被举报遗弃儿童并包庇强奸",new Integer[]{4,16,19,21},false,8,"...");
		System.out.println(a);
	}

	/**
	 * 删除字符串中的命中点标记并返回命中点的位置信息.
	 *
	 * @param str 带有命中点标记的字符串
	 * @param startTags 命中点的开始标记
	 * @param endTags 命中点的结束标记，顺序必须与开始标记一一对应
	 * @param merge 在返回命中点的位置信息时是否合并相邻的命中点
	 * @return the integer[] 命中点的位置信息，每个命中点有一个开始位置和结束位置，如[0,3,6,10]，则str.substring(0,3)为第一个命中点,str.substring(6,10)为第二个命中点。
	 */
	public static Integer[] parseHitpoints(StringBuilder str,String[] startTags,String[] endTags,boolean merge){
		StringBuilder regexBuilder=new StringBuilder();
		StringBuilder regexBuilder2=new StringBuilder();
		for(int i=0;i<startTags.length;i++){
			if(i>0){
				regexBuilder.append('|');
				regexBuilder2.append('|');
			}
			regexBuilder.append('(').append(Pattern.quote(startTags[i])).append(")(.*?)(")
				.append(Pattern.quote(endTags[i])).append(')');
			regexBuilder2.append(Pattern.quote(startTags[i])).append('|').append(Pattern.quote(endTags[i]));
		}
		String replaceRegex=regexBuilder2.toString();
		Pattern pattern=Pattern.compile(regexBuilder.toString());

		Matcher matcher=pattern.matcher(str);
		StringBuilder result=new StringBuilder();
		ArrayList<Integer> posArray=new ArrayList<Integer>();
		int index=0;
		while(matcher.find()){
			result.append(str.substring(index, matcher.start()));
			posArray.add(result.length());
			for(int i=0;i<startTags.length;i++){
				String content=matcher.group(i*3+2);
				if(content!=null){
					result.append(content.replaceAll(replaceRegex, ""));
					break;
				}
			}
			posArray.add(result.length());
			index=matcher.end();
		}
		result.append(str.substring(index));
		str.delete(0, str.length());
		str.append(result);
		if(!merge)
			return posArray.toArray(new Integer[posArray.size()]);
		// 合并相邻的命中点
		ArrayList<Integer> posArray2=new ArrayList<Integer>();
		for(int i=0;i<posArray.size();i+=2){
			int start=posArray.get(i);
			int end=posArray.get(i+1);
			if(posArray2.isEmpty()){
				posArray2.add(start);
				posArray2.add(end);
			}else{
				if(posArray2.get(posArray2.size()-1)==start){
					posArray2.set(posArray2.size()-1, end);
				}else{
					posArray2.add(start);
					posArray2.add(end);
				}
			}
		}
		return posArray2.toArray(new Integer[posArray2.size()]);
	}

	public static Integer[] trimHoles(Integer[] holes,int limit){
		int total=0;
		for(int i=0;i<holes.length;i++){
			total+=holes[i];
		}
		if(total<=limit)
			return holes;
		Integer[] holeLimits=new Integer[holes.length];
		double[] holeLimitRests=new double[holes.length];
		int totalLess=0;
		for(int i=0;i<holes.length;i++){
			double holeLimit=limit*holes[i]*1.0/total;
			holeLimits[i]=(int) holeLimit;
			holeLimitRests[i]=holeLimit-holeLimits[i];
			totalLess+=holeLimits[i];
		}
		if(totalLess==limit)
			return holeLimits;
		double[] holeLimitRestsSort=new double[holes.length];
		System.arraycopy(holeLimitRests, 0, holeLimitRestsSort, 0, holeLimitRests.length);
		Arrays.sort(holeLimitRestsSort);
		int totalRest=limit-totalLess;
		HashSet<Integer> excludeSet=new HashSet<Integer>();
		for(int i=0;i<totalRest;i++){
			for(int j=0;j<holeLimitRests.length;j++){
				if(holeLimitRests[j]==holeLimitRestsSort[holeLimitRestsSort.length-i-1]&&!excludeSet.contains(j)){
					holeLimits[j]=(holeLimits[j]+1);
					excludeSet.add(j);
					break;
				}
			}
		}
		return holeLimits;
	}

	/**
	 * 获取字符串中出现的所有关键词的位置信息.
	 *
	 * @param str 字符串
	 * @param keywords 关键词集合
	 * @return 表示所有关键词位置的整形数组
	 */
	public static Integer[] getKeywordHits(String str,Set<String> keywords){
		if(keywords==null||keywords.isEmpty()) return new Integer[0];
		StringBuilder regex=new StringBuilder();
		for(String keyword:keywords){
			if(regex.length()>0) regex.append('|');
			regex.append(Pattern.quote(keyword));
		}
		Pattern pattern=Pattern.compile(regex.toString(), Pattern.CASE_INSENSITIVE);
		Matcher matcher=pattern.matcher(str);
		LinkedList<Integer> list=new LinkedList<Integer>();
		while(matcher.find()){
			list.add(matcher.start());
			list.add(matcher.end());
		}
		return list.toArray(new Integer[list.size()]);
	}

	/**
	 * 获取字符串中出现的IMAGE和TABLE标记的位置信息.
	 *
	 * @param str 字符串
	 * @return 表示IMAGE和TABLE标记位置的整形数组
	 */
	public static Integer[] getImageTableHits(String str){
		Pattern pattern=Pattern.compile("(<TABLE IDX=(x?)(\\d+)>)|(<IMAGE( IDX=(x?)(\\d+))?(\\s+SRC=\"?([^>\"]+)\"?)?\\s*>)", Pattern.CASE_INSENSITIVE);
		Matcher matcher=pattern.matcher(str);
		LinkedList<Integer> list=new LinkedList<Integer>();
		while(matcher.find()){
			list.add(matcher.start());
			list.add(matcher.end());
		}
		return list.toArray(new Integer[list.size()]);
	}

	/**
	 * 合并多组命中点位置信息.
	 *
	 * @param hitsMap 多组的命中点位置信息，如{keywrod:[0,11],query:[2,5,5,8],search:[1,5,5,8]}
	 * @return the object[] 合并后的命中的位置信息，如[[0, 1, keyword],[1, 2, keyword, search],[2, 5, keyword, search, query],[5, 8, keyword, search, query],[8, 11, keyword]]
	 */
	public static Object[] mergeHits(Map<String,Integer[]> hitsMap){
		ArrayList<Object[]> hitsArray=new ArrayList<Object[]>();
		for(Map.Entry<String, Integer[]> entry:hitsMap.entrySet()){
			for(int i=0;i<entry.getValue().length;i+=2){
				hitsArray.add(new Object[]{entry.getValue()[i],entry.getValue()[i+1],entry.getKey()});
			}
		}
		Collections.sort(hitsArray, new Comparator<Object[]>() {

			@Override
			public int compare(Object[] o1, Object[] o2) {
				return ((Integer)o1[0]).compareTo(((Integer)o2[0]));
			}
		});
		ArrayList<Object[]> resultList=new ArrayList<Object[]>();
		int maxFlags=hitsMap.keySet().size();
		for(int index=0;index<hitsArray.size();){
			int start=(Integer)hitsArray.get(index)[0];
			int end=(Integer)hitsArray.get(index)[1];
			if(start==end) {
				index++;
				continue;
			}
			Object[] span=new Object[2+maxFlags];
			int spanLength=2;
			span[spanLength++]=hitsArray.get(index)[2];
			int prev=index;
			for(int i=index+1;i<=hitsArray.size();i++){
				if(i==hitsArray.size()){
					hitsArray.get(prev)[0]=end;
					break;
				}
				int nextStart=(Integer)hitsArray.get(i)[0];
				int nextEnd=(Integer)hitsArray.get(i)[1];
				if(nextStart==nextEnd) continue;
				if(nextStart>=end){
					hitsArray.get(prev)[0]=hitsArray.get(prev)[1];
					break;
				}else if(nextStart>start){
					hitsArray.get(prev)[0]=nextStart;
					end=nextStart;
					break;
				}else if(nextEnd<=end){
					hitsArray.get(prev)[0]=nextEnd;
					end=nextEnd;
					span[spanLength++]=hitsArray.get(i)[2];
				}else{
					hitsArray.get(i)[0]=end;
					span[spanLength++]=hitsArray.get(i)[2];
				}
				prev=i;
			}
			span[0]=start;
			span[1]=end;
			resultList.add(Arrays.copyOf(span, spanLength));
			hitsArray.get(index)[0]=end;
		}
		return resultList.toArray(new Object[resultList.size()]);
	}

	public static Integer[] excludeHits(Object[] mergedHits,Integer[] excludedHits){
		ArrayList<Integer> resultHits=new ArrayList<Integer>(mergedHits.length);
		int excludeIndex=0;
		for(int i=0;i<mergedHits.length;i++){
			Integer start=(Integer) ((Object[])mergedHits[i])[0];
			Integer end=(Integer) ((Object[])mergedHits[i])[1];
			boolean find=false;
			for(int j=excludeIndex;j<excludedHits.length;j+=2){
				if(excludedHits[j+1]<=start){
					excludeIndex+=2;
					continue;
				}else if(excludedHits[j]>=end){
					continue;
				}else{
					find=true;
				}
			}
			if(!find){
				resultHits.add(start);
				resultHits.add(end);
			}
		}
		return resultHits.toArray(new Integer[resultHits.size()]);
	}

	public static String escapeAndTruncTitle(String title,Integer[] titleHits,Map<String,String> keywordMap,boolean hitOnce,int limit,String suffix){
		if(title==null)
			return title;
		HashMap<String,Integer[]> hitsMap=new HashMap<String, Integer[]>();
		if(titleHits!=null&&titleHits.length>0)
			hitsMap.put("search", titleHits);
		if(keywordMap!=null&&!keywordMap.isEmpty()){
			Integer[] keywordHits=getKeywordHits(title, keywordMap.keySet());
			if(keywordHits!=null&&keywordHits.length>0)
				hitsMap.put("keyword",keywordHits);
		}
		Object[] mergedHits=mergeHits(hitsMap);
		Integer[] posArray=new Integer[mergedHits.length*2];
		for(int i=0;i<mergedHits.length;i++){
			posArray[i*2]=(Integer) ((Object[])mergedHits[i])[0];
			posArray[i*2+1]=(Integer) ((Object[])mergedHits[i])[1];
		}
		return escapeAndTruncHitString(title, posArray, hitOnce, limit, suffix);
	}
	/**
	 *统计文章字数
	 */
	public static int countWords(String content){
		return escapeTableAndImg(content).replaceAll("\\s*|\n", "").length();
	}
	public static String escapeAndTruncContent(String content,Integer[] contentHits,Map<String,String> keywordMap,boolean hitOnce){
		if(content==null)
			return content;
		HashMap<String,Integer[]> hitsMap=new HashMap<String, Integer[]>();
		if(contentHits!=null&&contentHits.length>0)
			hitsMap.put("search", contentHits);
		if(keywordMap!=null&&!keywordMap.isEmpty()){
			Integer[] keywordHits=getKeywordHits(content, keywordMap.keySet());
			if(keywordHits!=null&&keywordHits.length>0)
				hitsMap.put("keyword",keywordHits);
		}
		Object[] mergedHits=mergeHits(hitsMap);
		Integer[] excludedHits=getImageTableHits(content);
		Integer[] posArray=excludeHits(mergedHits, excludedHits);
		if(hitOnce){
			// 去掉重复描红的关键词
			HashSet<String> keywords=new HashSet<String>();
			ArrayList<Integer> posList=new ArrayList<Integer>(posArray.length);
			for(int i=0;i<posArray.length;i+=2){
				String keyword=content.substring(posArray[i], posArray[i+1]);
				if(keywords.contains(keyword))
					continue;
				keywords.add(keyword);
				posList.add(posArray[i]);
				posList.add(posArray[i+1]);
			}
			posArray=posList.toArray(new Integer[posList.size()]);
		}
		StringBuilder resultBuilder=new StringBuilder();
		int prev=0;
		for(int i=0;i<posArray.length;i+=2){
			resultBuilder.append(content.substring(prev, posArray[i]))
				.append("<font color=red>")
				.append(content.substring(posArray[i], posArray[i+1]))
				.append("</font>");
			prev=posArray[i+1];
		}
		resultBuilder.append(content.substring(prev));
		return resultBuilder.toString();
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String companyRiskHitsTitle(String title,String companys,String hits,boolean hitOnce){
		if(title==null)
			return title;
		title=StringEscapeUtils.unescapeHtml(title);
		HashMap<String,Integer[]> hitsMap=new HashMap<String, Integer[]>();
		Set<String> hitgzs=new HashSet<String>();
		if(StringUtils.isNotBlank(hits)){
			hits=hits.replaceAll("\n", "");
			String[] hitsarry=hits.split(";");
			String gzs="";
			for(String gz:hitsarry){
				String[] gz_risk=gz.split("_");
				if(StringUtils.isNotBlank(gzs)){
					if(gz_risk.length>1)
					gzs+=";"+gz_risk[1];
				}else{
					if(gz_risk.length>1)
					gzs+=gz_risk[1];
				}
			}
			gzs=gzs.replace("+", ";");
			gzs=gzs.replace("(", ";");
			gzs=gzs.replace(")", ";");
			gzs=gzs.replace("*", ";");
			String[] keywords=gzs.split(";");
			for(String keyword:keywords){
				if(StringUtils.isNotBlank(keyword))
				hitgzs.add(keyword);
			}
		}
		if(StringUtils.isNotBlank(companys)){
			String[] companyarry=companys.split(";");
			hitgzs.addAll(new HashSet(Arrays.asList(companyarry)));
		}
		if(hitgzs!=null&&!hitgzs.isEmpty()){
			Integer[] keywordHits=getKeywordHits(title, hitgzs);
			if(keywordHits!=null&&keywordHits.length>0)
				hitsMap.put("keyword",keywordHits);
		}
		Object[] mergedHits=mergeHits(hitsMap);
		Integer[] posArray=new Integer[mergedHits.length*2];
		for(int i=0;i<mergedHits.length;i++){
			posArray[i*2]=(Integer) ((Object[])mergedHits[i])[0];
			posArray[i*2+1]=(Integer) ((Object[])mergedHits[i])[1];
		}
			if(hitOnce){
				// 去掉重复描红的关键词
				HashSet<String> keywords=new HashSet<String>();
				ArrayList<Integer> posList=new ArrayList<Integer>(posArray.length);
				for(int i=0;i<posArray.length;i+=2){
					String keyword=title.substring(posArray[i], posArray[i+1]);
					if(keywords.contains(keyword))
						continue;
					//公司描红所有不是只描红首次出现
					if(!new HashSet<String>(Arrays.asList(companys.split(";"))).contains(keyword))
					keywords.add(keyword);
					posList.add(posArray[i]);
					posList.add(posArray[i+1]);
				}
				posArray=posList.toArray(new Integer[posList.size()]);
			}
			StringBuilder resultBuilder=new StringBuilder();
			int prev=0;
			for(int i=0;i<posArray.length;i+=2){
				resultBuilder.append(title.substring(prev, posArray[i]))
					.append("<font color=red>")
					.append(title.substring(posArray[i], posArray[i+1]))
					.append("</font>");
				prev=posArray[i+1];
			}
			resultBuilder.append(title.substring(prev));
		return resultBuilder.toString();
	}
	/**
	 * 客户风险命中点描红
	 * @param content
	 * @param hits
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String companyRiskHitsContent(String content,String hits,String companys,boolean hitOnce,String risk){
		if(content==null)
			return content;

		Integer[] hitStrings=getPositionFromCotent(content,risk);
		if(hitStrings==null)return content;
		Set<String> hitgzs=new HashSet<String>();
		if(StringUtils.isNotBlank(hits)){
			hits=hits.replaceAll("\n", "");
			String[] hitsarry=hits.split(";");
			String gzs="";
			for(String gz:hitsarry){
				String[] gz_risk=gz.split("_");
				if(StringUtils.isNotBlank(gzs)){
					if(gz_risk.length>1)
					gzs+=";"+gz_risk[1];
				}else{
					if(gz_risk.length>1)
					gzs+=gz_risk[1];
				}
			}
			gzs=gzs.replace("+", ";");
			gzs=gzs.replace("(", ";");
			gzs=gzs.replace(")", ";");
			gzs=gzs.replace("*", ";");
			String[] keywords=gzs.split(";");
			for(String keyword:keywords){
				if(StringUtils.isNotBlank(keyword))
				hitgzs.add(keyword);
			}
		}
		if(StringUtils.isNotBlank(companys)){
			String[] companyarry=companys.split(";");
			hitgzs.addAll(new HashSet(Arrays.asList(companyarry)));
		}
		Pattern pattern = Pattern
			.compile("(<IMAGE( IDX=(x?)(\\d+))?(\\s+SRC=\"?([^>\"]+)\"?)?\\s*>)",Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(content);
		content = matcher.replaceAll("");
		HashMap<String,Integer[]> hitsMap=new HashMap<String, Integer[]>();
		if(hitgzs!=null&&!hitgzs.isEmpty()){
			Integer[] keywordHits=getKeywordHits(content, hitgzs);
			List<Integer> mzjvzikeyhits=new ArrayList<Integer>();
			for(int i=0;i<hitStrings.length;i+=2){
				int begin=hitStrings[i];
				int end=hitStrings[i+1];
				for(int j=0;j<keywordHits.length;j+=2){
					if(keywordHits[j]>=begin&&keywordHits[j+1]<=end){
						mzjvzikeyhits.add(keywordHits[j]);
						mzjvzikeyhits.add(keywordHits[j+1]);
					}
				}
			}
			keywordHits=mzjvzikeyhits.toArray(new Integer[mzjvzikeyhits.size()]);
			if(keywordHits!=null&&keywordHits.length>0)
				hitsMap.put("keyword",keywordHits);
		}
		Object[] mergedHits=mergeHits(hitsMap);
		Integer[] excludedHits=getImageTableHits(content);
		Integer[] posArray=excludeHits(mergedHits, excludedHits);
		if(hitOnce){
			// 去掉重复描红的关键词
			HashSet<String> keywords=new HashSet<String>();
			ArrayList<Integer> posList=new ArrayList<Integer>(posArray.length);
			for(int i=0;i<posArray.length;i+=2){
				String keyword=content.substring(posArray[i], posArray[i+1]);
				if(keywords.contains(keyword))
					continue;
				if(!new HashSet<String>(Arrays.asList(companys.split(";"))).contains(keyword))
				keywords.add(keyword);
				posList.add(posArray[i]);
				posList.add(posArray[i+1]);
			}
			posArray=posList.toArray(new Integer[posList.size()]);
		}
		StringBuilder resultBuilder=new StringBuilder();
		int prev=0;
		for(int i=0;i<posArray.length;i+=2){
			resultBuilder.append(content.substring(prev, posArray[i]))
				.append("<font color=red>")
				.append(content.substring(posArray[i], posArray[i+1]))
				.append("</font>");
			prev=posArray[i+1];
		}
		resultBuilder.append(content.substring(prev));
		return resultBuilder.toString();
	}
/**
 * 获取命中句子的位置
 * @param content
 * @param risk
 * @return
 */
	public static Integer[] getPositionFromCotent(String content,String risk){
		//建立分句用 的符号集合，目前暂时用这几个作为分句标准
		Set<String> biaodianset=new HashSet<String>();
		biaodianset.add("。");
		biaodianset.add("！");
		biaodianset.add("；");
		biaodianset.add("？；");
		biaodianset.add("?；");
		biaodianset.add("!");
		Set<Integer> jvzi=new HashSet<Integer>();
		String[] risk_poss=risk.split(";");
		String[] poss=new String[risk_poss.length];
		Pattern pattern = Pattern.compile("[0-9]*");
		for(int i=0;i<risk_poss.length;i++){
			String risk_pos=risk_poss[i];
			poss[i]= risk_pos.split("_")[risk_pos.split("_").length-1];
		}
		for(int i=0;i<poss.length;i++){
			String num=poss[i];
			String[] position=num.split("#");
			for(String p:position){
				Matcher isNum = pattern.matcher(p);
				if(!isNum.matches())return null;
				jvzi.add(Integer.parseInt(p));
			}
		}
		Integer[] biaodianHits=getKeywordHits(content,biaodianset );
		int prev=0;
		List<String> cutcontents=new ArrayList<String>();
		for(int i=0;i<biaodianHits.length;i+=2){
			cutcontents.add(content.substring(prev, biaodianHits[i]));
			prev=biaodianHits[i+1];
		}
		Set<String> keyjvzi=new HashSet<String>();
		//便利命中句子
		for(Integer obj:jvzi){
			if(cutcontents.size()>=obj)
			keyjvzi.add(cutcontents.get(obj-1));
		}
		return getKeywordHits(content,keyjvzi);
	}



}
