package com.trs.common.util;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class Common {
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger
            .getLogger(Common.class);
	/**
	 * Double数值加和
	 * @param d
	 * @return
	 */
	public static Double sum(Double... d)
	{
		BigDecimal bd=new BigDecimal("0");
		for(Double obj:d)
		{
			if(obj!=null)
				bd=bd.add(new BigDecimal(obj));
		}
		return bd.doubleValue();
	}
	public static Double NullToZero(Double d)
	{
		if(d==null)
			d=new Double("0");
		return d;
	}
	public static Double StringToDouble(String s)
	{
		try
		{
			if(s!=null&&!s.equals(""))
				return new Double(s);
			else
				return null;
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	//两个时间差的天数
	public static int daysOfTwo(Date fDate, Date oDate)  {
		//fDate=new Date();
		//System.out.println("fDate:"+fDate);
		//System.out.println("oDate:"+oDate);
		try {
			Date d= new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss:SSS").parse(getDateTimeFormat(fDate, "yyyy-MM-dd  HH:mm:ss:SSS"));		
			Calendar aCalendar = Calendar.getInstance();
	       aCalendar.setTime(d);
	       int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
	       aCalendar.setTime(oDate);
	       int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
	      // System.out.println("days:"+(day2 - day1));
	       return day2 - day1;
		 } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		 }
	      return 0;
	    }
	/**
	 * 保留小数位
	 * @param dou
	 * @param i
	 * @return
	 */
	public static double toFixed(double dou , int i){
		int k=(int) Math.pow(10, i); 
		int b = (int)Math.round(dou * k); //小数点后两位前移，并四舍五入 
		double c = ((double)b/(double)k); //还原小数点后两位
		if((c*k)%5!=0){
		   int d = (int)Math.round(c); //小数点前移，并四舍五入 
		   c = ((double)d); //还原小数点
		 }
		return c;
	}
	
	/**
	 * 返回年
	 * 
	 * @param corporationBirthday
	 * @return
	 */
	public static String getYearToString() {

		// 存放日期的变量
		String dateStr = "";
		// 获取字符型当前日期
		String time = getDateTimeFormat(new Date(), "yyyy-MM-dd");
		// 如果字符型日期不为空,进行时间转换
		if (StringUtils.isNotBlank(time)) {
			// 字符型转成日期型
			Date corporationBirthday = newDate(time, "yyyy-MM-dd");
			// Calendar实体整型转换
			Calendar cal = Calendar.getInstance();
			// 如果日期型日期不为空,进行组合
			if (corporationBirthday != null) {
				// cal获取日期
				cal.setTime(corporationBirthday);
				// 获取整型的年
				int year = cal.get(Calendar.YEAR);
				// 组合
				dateStr = year + "";
			}
		}
		// 返回日期
		return dateStr;

	}
	
	//返回月初和月末
	public static Date[]  MonthFirstEnd(){
		String y=Common.getCurrentyear();
		String m=Common.getCurrentmonth();
		String first=y+"-"+m+"-"+"01";
		String end=y+"-"+m+"-"+check(Integer.parseInt(y))[Integer.parseInt(m)-1];
		Date[] dateFE=new Date[2];
		dateFE[0]=newDate(first,"yyyy-MM-dd");
		dateFE[1]=newDate(end,"yyyy-MM-dd");
		return dateFE;
	}
	//是否是闰年,返回月长度
	public static String[] check(int year)
	{
		if((year%4 == 0)&&((year%100 != 0)|(year%400 == 0))){ 
			return	new String[] {"31","29","31","30","31","30","31","31","30","31","30","31"};
		}else{ 
			return new String[] {"31","28","31","30","31","30","31","31","30","31","30","31"};
		}
	}

	public static Date  nextDay(Date  date){
		@SuppressWarnings("deprecation")
		int day=date.getDate();
		String y=Common.getCurrentyear(date);
		String m=Common.getCurrentmonth(date);
		String nextDay=y+"-"+m+"-"+(day+1);		
		Date nextDate=newDate(nextDay,"yyyy-MM-dd");
		return nextDate;
	}
	public static Date  nextDay(Date  date,int i){
		@SuppressWarnings("deprecation")
		int day=date.getDate();
		String y=Common.getCurrentyear(date);
		String m=Common.getCurrentmonth(date);
		String nextDay=y+"-"+m+"-"+(day+i);		
		Date nextDate=newDate(nextDay,"yyyy-MM-dd");
		return nextDate;
	}
	
	/**
	 * 日期处理函数
	 * 
	 * @param dt
	 * @param format
	 *            can be one of "yyyy/MM/dd/ HH:mm:ss:SSS"
	 * @return
	 */
	public static String getDateTimeFormat(Date dt, String format) {
		if (dt == null||dt.toString().equals("1970-01-01 08:00:01.899")||dt.toString().equals("1970-01-01 00:00:00.0")) {
			return "";
		}
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.format(dt);

	}
	
	/**
	 * 日期处理函数
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date newDate(String date, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		try {
			if(date!=null&&!date.equals(""))
				return df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	Log log=LogFactory.getLog(this.getClass());

	/** get current year */
	public static String getCurrentyear() {
		Date nowTime = new Date();
		SimpleDateFormat matyear = new SimpleDateFormat("yyyy");
		return String.valueOf(matyear.format(nowTime));
	}
	public static String getCurrentyear(Date date) {
		SimpleDateFormat matyear = new SimpleDateFormat("yyyy");
		return String.valueOf(matyear.format(date));
	}
	/** get current month */
	public static String getCurrentmonth() {
		Date nowTime = new Date();
		SimpleDateFormat matyear = new SimpleDateFormat("MM");
		return String.valueOf(matyear.format(nowTime));
	}
	public static String getCurrentmonth(Date date) {
		SimpleDateFormat matyear = new SimpleDateFormat("MM");
		return String.valueOf(matyear.format(date));
	}
	/** get nowdatetime */
	public static String getCurrentDateTime() {
		Date nowTime = new Date();
		SimpleDateFormat matyear = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return String.valueOf(matyear.format(nowTime));
	}
	/** get nowdate */
	public static String getCurrentdate() {
		Date nowTime = new Date();
		SimpleDateFormat matyear = new SimpleDateFormat("yyyy-MM-dd");
		return String.valueOf(matyear.format(nowTime));
	}
	public static String[] getStringTokenizer(String src,String str){
		StringTokenizer stringTokenizer = new StringTokenizer(src,str);
		String[] array = new String[stringTokenizer.countTokens()];
			for(int i=0;stringTokenizer.hasMoreTokens();i++){
				array[i] = stringTokenizer.nextToken();
			}
		
		return array;
	}

	public static Object[] getLongTokenizer(String src,String str){
		StringTokenizer stringTokenizer = new StringTokenizer(src,str);
		Object[] array = new Object[stringTokenizer.countTokens()];
			for(int i=0;stringTokenizer.hasMoreTokens();i++){
				array[i] = new Long(stringTokenizer.nextToken());
			}
		
		return array;
	}
	
	public static Object[] getIntegerTokenizer(String src,String str){
		StringTokenizer stringTokenizer = new StringTokenizer(src,str);
		Object[] array = new Object[stringTokenizer.countTokens()];
			for(int i=0;stringTokenizer.hasMoreTokens();i++){
				array[i] = new Integer(stringTokenizer.nextToken());
			}
		
		return array;
	}
	public Float F(Object o) {
		NumberFormat formatter = new DecimalFormat("0.0");
		if (o == null) {
			return 0.0F;
		} else {
			Float f = Float.parseFloat(o.toString());
			String strf = formatter.format(f);
			return Float.parseFloat(strf);
		}
	}
	public static Object convertNull(Object value,Object returnValue) {
		if (value == null) {
			return returnValue;
		} else {
			return value;
		}
	}
	/**
	 * 根据传入ID创建新的序列，最大值+1，例：传入001001，返回001002
	 * @param id
	 * @param pid	上级ID，如新ID不存在使用上级ID进行累加001
	 * @return
	 */
	public static String createSequence(String maxId,String pid,int jumpLength)
	{
		String newId="";
		if(maxId==null)
		{
			if(pid.equals("0"))
			{
				for(int i=0;i<jumpLength-1;i++)
					newId += "0";
				newId += "1";
			}
			else
			{
				newId+=pid;
				for(int i=0;i<jumpLength-1;i++)
					newId += "0";
				newId += "1";
			}
		}
		else
		{
			BigDecimal bd = new BigDecimal(maxId);
			BigDecimal sum = bd.add(new BigDecimal("1"));
			newId = sum.toString();
			newId = maxId.substring(0,maxId.length()-newId.length())+newId;
		}
		return newId;
	}
	//根据身份证找生日
	public static java.sql.Date sfzToBir(String sfz) {
		String bir="";
		if(sfz.length()>15){
			bir=sfz.substring(6,10)+"-"+sfz.substring(10,12)+"-"+sfz.substring(12,14);		
		}else{
			bir="19"+sfz.substring(6,8)+"-"+sfz.substring(8,10)+"-"+sfz.substring(10,12);
		}	
		return java.sql.Date.valueOf(bir);
	}
	/**
	 * 
	 * @param strXml
	 * @param path
	 * @param bfStr
	 * @return
	 */
	public static String  fileXml(String  strXml,String path,String bfStr){
		Document ds;
		
	
		OutputFormat format=OutputFormat.createCompactFormat();
		Date  date=new Date();
		String  filepath=path+bfStr+date.getTime()+".xml";
		
		XMLWriter xw;
		try {	
			ds = DocumentHelper.parseText(strXml);
		
			xw = new XMLWriter(new FileWriter(filepath),format);
		
		
		xw.write(ds);
		xw.close();
		} catch (IOException e) {
			logger.error("保存xml出现异常");
		} catch (DocumentException e) {
			
			logger.error("转换xml出现异常");
		}
		return  filepath;
	}
	
	
}
