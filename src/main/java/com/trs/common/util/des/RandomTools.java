package com.trs.common.util.des;

/**
*
* ＩＤ生成。
* <P>
*
*/

//导入
import java.io.*;
import java.util.*;
import java.text.*;

/**
* ＩＤ生成。<P>
*
* 该对象在生成统一ＩＤ，以保证ＩＤ在整个系统内的唯一性。
* <P>
*
* @auther
*/
public final class RandomTools implements Serializable{
	/**
	 * @Fields serialVersionUID ： TODO(用一句话描述这个变量表示什么)
	 */
	    
	private static final long serialVersionUID = -498964155745251369L;
	// 类变量
	private static volatile int seq = 0;
	private static final int max = Integer.parseInt("zz", 36);

	/**
	 * 唯一构造器。<P>
	 */
	public RandomTools() {
	}

	/**
	 * 取ＩＤ。
	 * ＩＤ生成。<P>
	 * 生成字符串长度为 13 字节。
	 * @return ＩＤ
	 * @exception IOException 通信输入输出错误
	 */
	public static String get() throws IOException {
		Date now = new Date();		// 现在时间的取得

		// 日期数据的设置
		String date_format = Integer.toString(Integer.parseInt((new SimpleDateFormat("yyyyMMdd")).format(now)), 36);

		// 时间数据的设置
		String time_format = Integer.toString(Integer.parseInt((new SimpleDateFormat("HHmmssSSS")).format(now)), 36);
		time_format = ("000000").substring(time_format.length()).concat(time_format);

		// ID 生成与返回
//		//  ?date_format: 现在时间的年月时间分秒以及毫秒数据
//		//                 数值转换为36 进数表现字符串
//		//                  11byte 字符串
//		//                    1900/01/01 00:00:00:000 = 572z1wfbv9c
//		//                    2100/12/31 23:59:59:999 = 5qsbcfu62m7
		//  ?date_format: 现在时间的年月时间分秒以及毫秒数据
		//                 数值转换为36 进数表现字符串
		//                  5 byte 字符串
		//                    1900/01/01 = bb8kl
		//                    2100/12/31 = ci4nj
		//  ?time_format: 现在时间年月时间分秒以及毫秒数据
		//                 数值转换为36 进数表现字符串
		//                  6 byte 字符串
		//                    00:00:00:000 = 00000
		//                    23:59:59:999 = whfwf
		//  ?sequencer(): 加上顺序号 2byte 字符串
		return date_format.concat(time_format).concat(sequencer());
	}

	/**************************************************************************
	*<PRE>
	*    取顺序号
	*        【 类型 】Class Method
	*        【 输入 】无
	*        【返回值】顺序号（字符串）
	*        【 异常 】无
	*        【编写过程】
	*        【 概要 】取顺序返回。
	*                  返回值，３６进数2位字符串。
	*</PRE>
	**************************************************************************/
	private static synchronized String sequencer() {
		// 最大值检查
		if(seq > max) { seq = 0; }

		// 转换为３６进数返回
		if(seq < 36) {
			return ("0").concat(Integer.toString(seq++, 36));
		}
		else {
			return Integer.toString(seq++, 36);
		}
	}
}

