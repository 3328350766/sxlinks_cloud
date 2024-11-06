/**
 * Copyright (c) 2015-2016, Chill Zhuang 庄骞 (smallchill@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sxlinks.utils;


import org.apache.commons.lang3.time.DateFormatUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {


	private static final Object lock = new Object();

	private static final Map<String, ThreadLocal<SimpleDateFormat>> pool = new HashMap<String, ThreadLocal<SimpleDateFormat>>();
	/**
	 * 获取YYYY格式
	 *
	 * @return
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	public static String getCurrentYear() {
		return formatDate(new Date(), "yyyy");
	}
	public static String getCurrentMonth() {
		return formatDate(new Date(), "MM");
	}
	public static String getCurrentDay() {
		return formatDate(new Date(), "dd");
	}
	public static String getCurrentHour() {
		return formatDate(new Date(), "HH");
	}
	/**
	 * 获取YYYY格式
	 *
	 * @return
	 */
	public static String getYear(Date date) {
		return formatDate(date, "yyyy");
	}
	public static String getMonth(Date date) {
		return formatDate(date, "MM");
	}
	/**
	 * 获取YYYY-MM-DD格式
	 *
	 * @return
	 */
	public static String getDay() {
		return formatDate(new Date(), "yyyy-MM-dd");
	}

	/**
	 * 获取YYYY-MM-DD格式
	 *
	 * @return
	 */
	public static String getDay(Date date) {
		return formatDate(date, "dd");
	}

	/**
	 * 获取YYYYMMDD格式
	 *
	 * @return
	 */
	public static String getDays() {
		return formatDate(new Date(), "yyyyMMdd");
	}

	/**
	 * 获取YYYYMMDD格式
	 *
	 * @return
	 */
	public static String getDays(Date date) {
		return formatDate(date, "yyyyMMdd");
	}

	public static String getDayAndHour(Date date) {
		return formatDate(date, "yyyyMMdd-HH");
	}
	public static String getHour(Date date) {
		return formatDate(date, "HH");
	}
	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 *
	 * @return
	 */
	public static String getTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss.SSS格式
	 *
	 * @return
	 */
	public static String getMsTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
	}

	/**
	 * 获取YYYYMMDDHHmmss格式
	 *
	 * @return
	 */
	public static String getAllTime() {
		return formatDate(new Date(), "yyyyMMddHHmmss");
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 *
	 * @return
	 */
	public static String getTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static String getMsTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss.SSS");
	}

	public static String getDate() {
		return formatDate(new Date(), "yyyy-MM-dd");
	}
	public static String formatDate(Date date, String pattern) {
		String formatDate = null;
		if (StringUtils.isNotEmpty(pattern)) {
			formatDate = DateFormatUtils.format(date, pattern);
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * @Title: compareDate
	 * @Description:(日期比较，如果s>=e 返回true 否则返回false)
	 * @param s
	 * @param e
	 * @return boolean
	 * @throws
	 * @author luguosui
	 */
	public static boolean compareDate(String s, String e) {
		if (parseDate(s) == null || parseDate(e) == null) {
			return false;
		}
		return parseDate(s).getTime() >= parseDate(e).getTime();
	}

	/**
	 * 格式化日期
	 *
	 * @return
	 */
	public static Date parseDate(String date) {
		return parse(date,"yyyy-MM-dd");
	}

	/**
	 * 格式化日期
	 *
	 * @return
	 */
	public static Date parseTime(String date) {
		return parse(date,"yyyy-MM-dd HH:mm:ss");
	}

	public static Date parseMsTime(String date) {
		return parse(date,"yyyy-MM-dd HH:mm:ss.SSS");
	}
	/**
	 * 格式化日期
	 *
	 * @return
	 */
	public static Date parse(String date, String pattern) {
		if (date != null) {
			if (pattern == null || "".equals(pattern)) {
				return null;
			}
			DateFormat format = getDFormat(pattern);
			try {
				return format.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	public static SimpleDateFormat getDFormat(String pattern) {
		ThreadLocal<SimpleDateFormat> tl = pool.get(pattern);
		if (tl == null) {
			synchronized (lock) {
				tl = pool.get(pattern);
				if (tl == null) {
					final String p = pattern;
					tl = new ThreadLocal<SimpleDateFormat>() {
						@Override
						protected synchronized SimpleDateFormat initialValue() {
							return new SimpleDateFormat(p);
						}
					};
					pool.put(p, tl);
				}
			}
		}
		return tl.get();
	}

	/**
	 * 格式化日期
	 *
	 * @return
	 */
	public static String format(Date date, String pattern) {
		return DateFormatUtils.format(date, pattern);
	}

	/**
	 * 把日期转换为Timestamp
	 *
	 * @param date
	 * @return
	 */
	public static Timestamp format(Date date) {
		return new Timestamp(date.getTime());
	}

	/**
	 * 校验日期是否合法
	 *
	 * @return
	 */
	public static boolean isValidDate(String s) {
		return parse(s, "yyyy-MM-dd HH:mm:ss") != null;
	}

	/**
	 * 校验日期是否合法
	 *
	 * @return
	 */
	public static boolean isValidDate(String s, String pattern) {
        return parse(s, pattern) != null;
	}

	public static int getDiffYear(String startTime, String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(
					startTime).getTime()) / (1000 * 60 * 60 * 24)) / 365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}

	/**
	 * <li>功能描述：时间相减得到天数
	 *
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 * @author Administrator
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd");
		Date beginDate = null;
		Date endDate = null;

		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		// System.out.println("相隔的天数="+day);

		return day;
	}

	/**
	 * <li>功能描述：时间相减得到秒数
	 *
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 * @author Administrator
	 */
	public static long getSecondSub(String beginDateStr, String endDateStr) {
		long day = 0;
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date beginDate = null;
		Date endDate = null;

		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		day = (endDate.getTime() - beginDate.getTime()) / 1000;		//除以1000毫秒
		// System.out.println("相隔的天数="+day);

		return day;
	}

	/**
	 * 得到n天之后的日期
	 *
	 * @param days
	 * @return
	 */
	public static String getAfterDayDate(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);

		return dateStr;
	}

	/**
	 * 获取几分钟的以前的时间
	 * @param val
	 * @return
	 */
	public static String getBeforeMinuteDate(String val) {
		int daysInt = Integer.parseInt(val);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.MINUTE, -daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);

		return dateStr;
	}
	/**
	 * 获取指定后的几秒之后的时间
	 * @param val
	 * @return
	 */
	public static String getAfterSecondDate(String strdate,String val) {
		int daysInt = Integer.parseInt(val);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.setTime(parseTime(strdate));
		canlendar.add(Calendar.SECOND, +daysInt); // 日期减 如果不够减会将月变动

		Date date = canlendar.getTime();
		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);

		return dateStr;
	}

	/**
	 * 获取几秒之前的时间
	 * @param val
	 * @return
	 */
	public static String getBeforeSecondDate(String val) {
		int daysInt = Integer.parseInt(val);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.SECOND, -daysInt); // 日期减 如果不够减会将表变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);

		return dateStr;
	}

	/**
	 * 获取几小时前的时间
	 * @param val
	 * @return
	 */
	public static String getBeforeHourDate(String val) {
		int daysInt = Integer.parseInt(val);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.HOUR, -daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);

		return dateStr;
	}

	/**
	 * 获取几天前的时间
	 * @param val
	 * @return
	 */
	public static String getBeforeDayDate(String val) {
		int daysInt = Integer.parseInt(val);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DAY_OF_WEEK, -daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);

		return dateStr;
	}
	/**
	 * 获取几周前的时间
	 * @param val
	 * @return
	 */
	public static String getBeforeWeekDate(String val) {
		int daysInt = Integer.parseInt(val);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.WEEK_OF_MONTH, -daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);

		return dateStr;
	}
	/**
	 * 获取几月前的时间
	 * @param val
	 * @return
	 */
	public static String getBeforeMonthDate(String val) {
		int daysInt = Integer.parseInt(val);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.MONTH, -daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);

		return dateStr;
	}
	/**
	 * 获取几年前的时间
	 * @param val
	 * @return
	 */
	public static String getBeforeYearDate(String val) {
		int daysInt = Integer.parseInt(val);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.YEAR, -daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);

		return dateStr;
	}
	/**
	 * 得到n天之后是周几
	 *
	 * @param days
	 * @return
	 */
	public static String getAfterDayWeek(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("E");
		String dateStr = sdf.format(date);

		return dateStr;
	}



	public static  String getDateTimeFromString(String str){
		String rstr="";
		int length=0;


		//时间的正则表达式列表
		String[] r={"(\\d{4}-\\d{1,2}-\\d{2})(\\s?\\d{2}:\\d{2}:\\d{2})",
				"(\\d{4}-\\d{1,2}-\\d{1,2})(\\s?\\d{1,2}:\\d{1,2}:\\d{1,2})",
				"(\\d{1,2}-\\d{1,2})(\\s?\\d{1,2}:\\d{1,2}:\\d{1,2})",
				"(\\d{4}/\\d{1,2}/\\d{1,2})(\\s?\\d{1,2}:\\d{1,2}:\\d{1,2})",
				"(\\d{4}/\\d{1,2}/\\d{1,2})(\\s?\\d{1,2}:\\d{1,2})",
				"(\\d{4}\\s?\\d{1,2}/\\d{1,2})(\\s?\\d{1,2}:\\d{1,2})",
				"(\\d{4}年\\d{1,2}月\\d{1,2}日)(\\s?\\d{2}:\\d{2}:\\d{2})",
				"(\\d{4}年\\d{1,2}月\\d{1,2}日)(\\s?\\d{2}点\\d{2}分\\d{2})",
				"(\\d{4}-\\d{1,2}-\\d{1,2})(\\s?\\d{2}:\\d{2})",
				"(\\d{4}-\\d{1,2}-\\d{1,2}-)(\\s?\\d{2}点\\d{2})",
				"(\\d{4}年\\d{1,2}月\\d{1,2}日)(\\s?\\d{2}:\\d{2})",
				"(\\d{4}年\\d{1,2}月\\d{1,2}日)(\\s?\\d{2}点\\d{2})",
				"(\\d{1,2}-\\d{1,2}-\\d{4})(\\s?\\d{2}点\\d{2})",
				"(\\d{4}-\\d{2}-\\d{2})",
				"(\\d{4}年\\d{1,2}月\\d{1,2}日)",
				"(\\d{4}\\s?年\\s?\\d{1,2}\\s?月\\s?\\d{1,2}\\s?日)",
				"(\\d{4}\\.\\d{1,2}\\.\\d{1,2})",
				"(\\d{1,2}-\\d{1,2}-\\d{4})",
				"(\\d{1,2}月\\d{1,2}日)",
				"(\\d{1,2}月\\d{1,2}日)(\\s?\\d{2}:\\d{2})",
				"(\\d{1,2}-\\d{1,2})(\\s?\\d{2}:\\d{2})",
				"(\\d{1,2}/\\d{1,2})(\\s?\\d{2}:\\d{2})",
				//"(\\d{4}年\\d{1,2}月\\d{1,2}日)((\\s?\\d{2}:\\d{2}:\\d{2})?|(\\s?\\d{2}点\\d{2}分\\d{2})?|(\\s?\\d{2}:\\d{2}))",
				"((\\d{4}|\\d{2})(\\-|\\/)\\d{1,2}\\3\\d{1,2})(\\s?\\d{2}:\\d{2})?|(\\d{4}年\\d{1,2}月\\d{1,2}日)(\\s?\\d{2}:\\d{2})",
				"((\\d{4}|\\d{2})(\\-|\\/)\\d{1,2}\\3\\d{1,2})((\\s?\\d{2}:\\d{2}:\\d{2})?|(\\s?\\d{2}点\\d{2}分\\d{2})?|(\\s?\\d{2}:\\d{2})?|(\\s?\\d{2}点\\d{2}))",
				"(\\d{1,2}-\\d{1,2}-\\d{1,2})(\\s?\\d{2}:\\d{2})",
				"(\\d{1,2}分钟前)",
				"(\\d{1,2}小时前)",
				"(\\d{1,2}天前)",
				"(\\d{1,2}秒前)",
				"(\\d{1,2}周前)"
		};

		for(int i=0;i<r.length;i++) {
			Pattern pattern = Pattern.compile(r[i]);
			Matcher matcher = pattern.matcher(str);
			while (matcher.find()) {
				int icount=matcher.group().length();
				if(icount>=length) {
					length = icount;    //时间覆盖长度过去，长度最长的为胜利方
					rstr=matcher.group();
				}

			}
		}
		if(!rstr.equals("")){		//时间不为空-转换时间格式
			//yyyy-MM-dd HH:mm:ss
			if(rstr.contains("年")&&rstr.contains("月")&&rstr.contains("日")){	//包含年,月，日
				if(rstr.contains(" ")){
					rstr=rstr.replace(" ","");
				}
				if(rstr.length()>=15) {	//带时间的
					rstr = getTime(parse(rstr, "yyyy年MM月dd日HH:mm"));
				}else{		//不带时间的
					rstr = getTime(parse(rstr, "yyyy年MM月dd日"));
				}
			}
			if(!rstr.contains("年")&&rstr.contains("月")&&rstr.contains("日")){	//只含月，日
				if(rstr.contains(" ")){
					rstr=rstr.replace(" ","");
				}
				rstr=getTime(parse(rstr,"MM月dd日"));
			}

			if(rstr.contains("分钟前")){	//分钟前
				rstr=rstr.replace("分钟前","");
				rstr=getBeforeMinuteDate(rstr);
			}
			if(rstr.contains("秒前")){	//分钟前
				rstr=rstr.replace("秒前","");
				rstr=getBeforeSecondDate(rstr);
			}
			if(rstr.contains("小时前")){	//分钟前
				rstr=rstr.replace("小时前","");
				rstr=getBeforeHourDate(rstr);
			}
			if(rstr.contains("天前")){	//分钟前
				rstr=rstr.replace("天前","");
				rstr=getBeforeDayDate(rstr);
			}
			if(rstr.contains("周前")){	//分钟前
				rstr=rstr.replace("周前","");
				rstr=getBeforeWeekDate(rstr);
			}
			if(rstr.contains("月前")){	//分钟前
				rstr=rstr.replace("月前","");
				rstr=getBeforeMonthDate(rstr);
			}
			if(rstr.contains("年前")){	//分钟前
				rstr=rstr.replace("年前","");
				rstr=getBeforeYearDate(rstr);
			}
			//还差一个2019/03/12 12:51:31时间的判断
			if(rstr.contains("/")){	// 协杠的时间
				rstr=rstr.replace("/","-");
			}
			if(rstr.contains(".")){	// 协杠的时间
				rstr=rstr.replace(".","-");
			}
			if(rstr.contains("-")){	// 协杠的时间
				if(rstr.length()>=15 && rstr.length()<=17) {	//带时间的,不带秒
					rstr = getTime(parse(rstr, "yyyy-MM-dd HH:mm"));
				}
				if(rstr.length()==10){		//不带时间的
					rstr = getTime(parse(rstr, "yyyy-MM-dd"));
				}
				if(rstr.contains(":")) {	//时间07-12 13:00:01
					if (rstr.length() == 13 || rstr.length() == 14) {
						rstr = getTime(parse(rstr, "MM-dd HH:mm:ss"));
					}
					if (rstr.length() == 11) { //时间07-12 13:00
						rstr = getTime(parse(rstr, "MM-dd HH:mm"));
					}
				}

			}

		}else{		//如果时间为空，则返回当前时间
			rstr=getTime(new Date());
		}
		return rstr;
	}

	public final static long ONE_DAY_SECONDS = 86400;
	public final static String shortFormat = "yyyyMMdd";
	public final static String longFormat = "yyyyMMddHHmmss";
	public final static String webFormat = "yyyy-MM-dd";
	public final static String webMonth = "yyyy-MM";
	public final static String webDay = "MM-dd";
	public final static String timeFormat = "HHmmss";
	public final static String monthFormat = "yyyyMM";
	public final static String chineseDtFormat = "yyyy年MM月dd日";
	public final static String newFormat = "yyyy-MM-dd HH:mm:ss";
	public final static String newDayFormat = "yyyy-MM-dd 00:00:00";
	public final static String endDayFormat = "yyyy-MM-dd 23:59:59";
	public final static String noSecondFormat = "yyyy-MM-dd HH:mm";
	public final static String dateTimeWithZone = "yyyy-MM-dd'T'HH:mm:ss.SSSX";
	public final static String dateTimeWithZoneZ = "yyyy-MM-dd'T'HH:mm:ss.SSS";
	public final static String dayFormat = "yyyy/MM/dd";
	public final static String yearFormat = "yyyy";
	public final static String month = "MM";
	public final static String second = "ss";
	public final static String timeFormat2 = "HH:mm:ss";
	public final static String timeFormat_HH_MM = "HH:mm";
	public final static String TOMORROW_CH = "明天";
	public final static long ONE_DAY_MILL_SECONDS = 86400000;
	public final static String currentTimeZone = "GMT+8";
	public final static String chinaTime = "MM时:mm分:ss秒";
	public final static String chinaDate = "yyyy年MM月dd日 HH时:mm分:ss秒";
	public final static String[] weekArr = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
	public final static Integer DAYS_A_MONTH = 30;
	public final static Integer DAYS_A_WEEK = 7;
	public final static Integer DAYS_THREE_MONTHS = DAYS_A_MONTH * 3;

	/**
	 * 日期路径 即年/月/日 如2018/08/08
	 */
	public static final String datePath()
	{
		Date now = new Date();
		return DateFormatUtils.format(now, "yyyy/MM/dd");
	}

	public static String formatDateTimeWithZone(Date date) {
		return format(date, dateTimeWithZone) + ":00";
	}
	/**
	 * 获取当前时间的指定格式
	 *
	 * @param pattern
	 * @return String
	 */
	public static String getNow(String pattern) {
		return formatDate(Calendar.getInstance(), pattern);
	}

	/**
	 * 获取当前时间
	 *
	 * @return
	 */
	public static Date currentDate() {
		return new Date();
	}

	/**
	 * 获取当前时间的指定格式
	 *
	 * @param pattern
	 * @return String
	 */
	public static Date getDate(String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return formatter.parse(pattern);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 日期转字符串
	 *
	 * @param calendar 日历类型
	 * @param pattern  格式字符串
	 * @return String 格式化后的字符串
	 */
	public static String formatDate(Calendar calendar, String pattern) {
		if (org.apache.commons.lang3.StringUtils.isEmpty(pattern)) {
			pattern = webFormat;
		}
		return DateFormatUtils.format(calendar, pattern);
	}

	public static DateFormat getNewDateFormat(String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);

		df.setLenient(false);
		return df;
	}
//
//	public static String format(Date date, String format) {
//		if (date == null) {
//			return "";
//		}
//
//		return new SimpleDateFormat(format).format(date);
//	}

	public static Date parseDateNoTime(String sDate) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(shortFormat);

		if ((sDate == null) || (sDate.length() < shortFormat.length())) {
			throw new ParseException("length too little", 0);
		}

		if (!org.apache.commons.lang3.StringUtils.isNumeric(sDate)) {
			throw new ParseException("not all digit", 0);
		}

		return dateFormat.parse(sDate);
	}

	public static Date parseDateNoTime(String sDate, String format) throws ParseException {
		if (org.apache.commons.lang3.StringUtils.isBlank(format)) {
			throw new ParseException("Null format. ", 0);
		}

		DateFormat dateFormat = new SimpleDateFormat(format);

		if ((sDate == null) || (sDate.length() < format.length())) {
			throw new ParseException("length too little", 0);
		}

		return dateFormat.parse(sDate);
	}

	public static Date parseDateNoTimeWithDelimit(String sDate, String delimit)
			throws ParseException {
		sDate = sDate.replaceAll(delimit, "");

		DateFormat dateFormat = new SimpleDateFormat(shortFormat);

		if ((sDate == null) || (sDate.length() != shortFormat.length())) {
			throw new ParseException("length not match", 0);
		}

		return dateFormat.parse(sDate);
	}

	public static Date parseDateLongFormat(String sDate) {
		DateFormat dateFormat = new SimpleDateFormat(longFormat);
		Date d = null;

		if ((sDate != null) && (sDate.length() == longFormat.length())) {
			try {
				d = dateFormat.parse(sDate);
			} catch (ParseException ex) {
				return null;
			}
		}

		return d;
	}

	public static Date parseDateNewFormat(String sDate) {
		DateFormat dateFormat = new SimpleDateFormat(newFormat);
		Date d = null;
		if ((sDate != null) && (sDate.length() == newFormat.length())) {
			try {
				d = dateFormat.parse(sDate);
			} catch (ParseException ex) {
				return null;
			}
		}
		return d;
	}



	public static String getStringByFormat(Date date, String format) {
		try {
			return new SimpleDateFormat(format).format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 计算当前时间几小时之后的时间
	 *
	 * @param date
	 * @param hours
	 * @return
	 */
	public static Date addHours(Date date, long hours) {
		return addMinutes(date, hours * 60);
	}

	/**
	 * 计算当前时间几分钟之后的时间
	 *
	 * @param date
	 * @param minutes
	 * @return
	 */
	public static Date addMinutes(Date date, long minutes) {
		return addSeconds(date, minutes * 60);
	}

	/**
	 * @param date1
	 * @param secs
	 * @return
	 */

	public static Date addSeconds(Date date1, long secs) {
		return new Date(date1.getTime() + (secs * 1000));
	}

	/**
	 * 判断输入的字符串是否为合法的小时
	 *
	 * @param hourStr
	 * @return true/false
	 */
	public static boolean isValidHour(String hourStr) {
		if (!org.apache.commons.lang3.StringUtils.isEmpty(hourStr) && org.apache.commons.lang3.StringUtils.isNumeric(hourStr)) {
			int hour = new Integer(hourStr).intValue();

			if ((hour >= 0) && (hour <= 23)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 判断输入的字符串是否为合法的分或秒
	 *
	 * @param
	 * @return true/false
	 */
	public static boolean isValidMinuteOrSecond(String str) {
		if (!org.apache.commons.lang3.StringUtils.isEmpty(str) && org.apache.commons.lang3.StringUtils.isNumeric(str)) {
			int hour = new Integer(str).intValue();

			if ((hour >= 0) && (hour <= 59)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 取得新的日期
	 *
	 * @param date1 日期
	 * @param days  天数
	 * @return 新的日期
	 */
	public static Date addDays(Date date1, long days) {
		return addSeconds(date1, days * ONE_DAY_SECONDS);
	}

	public static String getTomorrowDateString(String sDate) throws ParseException {
		Date aDate = parseDateNoTime(sDate);

		aDate = addSeconds(aDate, ONE_DAY_SECONDS);

		return getDateString(aDate);
	}

	public static String getLongDateString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(longFormat);

		return getDateString(date, dateFormat);
	}

	public static String getNewFormatDateString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(newFormat);
		return getDateString(date, dateFormat);
	}

	public static String getNewDayFormatDateString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(newDayFormat);
		return getDateString(date, dateFormat);
	}

	public static String getDateString(Date date, DateFormat dateFormat) {
		if (date == null || dateFormat == null) {
			return null;
		}

		return dateFormat.format(date);
	}

	public static String getYesterDayDateString(String sDate) throws ParseException {
		Date aDate = parseDateNoTime(sDate);

		aDate = addSeconds(aDate, -ONE_DAY_SECONDS);

		return getDateString(aDate);
	}

	/**
	 * @return 当天的时间格式化为"yyyyMMdd"
	 */
	public static String getDateString(Date date) {
		DateFormat df = getNewDateFormat(shortFormat);

		return df.format(date);
	}

	/**
	 * 转换为YYYY-MM-DD
	 *
	 * @param date
	 * @return
	 */
	public static String getWebDateString(Date date) {
		DateFormat dateFormat = getNewDateFormat(webFormat);

		return getDateString(date, dateFormat);
	}

	/**
	 * 转换为YYYY-MM-
	 *
	 * @param date
	 * @return
	 */
	public static String getWebMonthString(Date date) {
		DateFormat dateFormat = getNewDateFormat(webMonth);

		return getDateString(date, dateFormat);
	}


	/**
	 * 取得“X年X月X日”的日期格式
	 *
	 * @param date
	 * @return
	 */
	public static String getChineseDateString(Date date) {
		DateFormat dateFormat = getNewDateFormat(chineseDtFormat);

		return getDateString(date, dateFormat);
	}

	public static String getTodayString() {
		DateFormat dateFormat = getNewDateFormat(shortFormat);

		return getDateString(new Date(), dateFormat);
	}

	public static String getTimeString(Date date) {
		DateFormat dateFormat = getNewDateFormat(timeFormat);

		return getDateString(date, dateFormat);
	}

	public static String getBeforeDayString(int days) {
		Date date = new Date(System.currentTimeMillis() - (ONE_DAY_MILL_SECONDS * days));
		DateFormat dateFormat = getNewDateFormat(shortFormat);

		return getDateString(date, dateFormat);
	}

	/**
	 * 取得两个日期间隔秒数（日期1-日期2）
	 *
	 * @param one 日期1
	 * @param two 日期2
	 * @return 间隔秒数
	 */
	public static long getDiffSeconds(Date one, Date two) {
		Calendar sysDate = new GregorianCalendar();

		sysDate.setTime(one);

		Calendar failDate = new GregorianCalendar();

		failDate.setTime(two);
		return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / 1000;
	}

	public static long getDiffMinutes(Date one, Date two) {
		Calendar sysDate = new GregorianCalendar();

		sysDate.setTime(one);

		Calendar failDate = new GregorianCalendar();

		failDate.setTime(two);
		return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / (60 * 1000);
	}

	/**
	 * 取得两个日期的间隔天数
	 *
	 * @param one
	 * @param two
	 * @return 间隔天数
	 */
	public static long getDiffDays(Date one, Date two) {
		Calendar sysDate = new GregorianCalendar();

		sysDate.setTime(one);

		Calendar failDate = new GregorianCalendar();

		failDate.setTime(two);
		return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / (24 * 60 * 60 * 1000);
	}

	public static String getBeforeDayString(String dateString, int days) {
		Date date;
		DateFormat df = getNewDateFormat(shortFormat);

		try {
			date = df.parse(dateString);
		} catch (ParseException e) {
			date = new Date();
		}

		date = new Date(date.getTime() - (ONE_DAY_MILL_SECONDS * days));

		return df.format(date);
	}

	public static boolean isValidShortDateFormat(String strDate) {
		if (strDate.length() != shortFormat.length()) {
			return false;
		}

		try {
			//---- 避免日期中输入非数字 ----
			Integer.parseInt(strDate);
		} catch (Exception NumberFormatException) {
			return false;
		}

		DateFormat df = getNewDateFormat(shortFormat);

		try {
			df.parse(strDate);
		} catch (ParseException e) {
			return false;
		}

		return true;
	}

	public static boolean isValidShortDateFormat(String strDate, String delimiter) {
		String temp = strDate.replaceAll(delimiter, "");

		return isValidShortDateFormat(temp);
	}

	/**
	 * 判断表示时间的字符是否为符合yyyyMMddHHmmss格式
	 *
	 * @param strDate
	 * @return
	 */
	public static boolean isValidLongDateFormat(String strDate) {
		if (strDate.length() != longFormat.length()) {
			return false;
		}

		try {
			//---- 避免日期中输入非数字 ----
			Long.parseLong(strDate);
		} catch (Exception NumberFormatException) {
			return false;
		}

		DateFormat df = getNewDateFormat(longFormat);

		try {
			df.parse(strDate);
		} catch (ParseException e) {
			return false;
		}

		return true;
	}

	/**
	 * 判断表示时间的字符是否为符合yyyyMMddHHmmss格式
	 *
	 * @param strDate
	 * @param delimiter
	 * @return
	 */
	public static boolean isValidLongDateFormat(String strDate, String delimiter) {
		String temp = strDate.replaceAll(delimiter, "");

		return isValidLongDateFormat(temp);
	}

	public static String getShortDateString(String strDate) {
		return getShortDateString(strDate, "-|/");
	}

	public static String getShortDateString(String strDate, String delimiter) {
		if (org.apache.commons.lang3.StringUtils.isBlank(strDate)) {
			return null;
		}

		String temp = strDate.replaceAll(delimiter, "");

		if (isValidShortDateFormat(temp)) {
			return temp;
		}

		return null;
	}

	public static String getShortFirstDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		Date dt = new Date();

		cal.setTime(dt);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		DateFormat df = getNewDateFormat(shortFormat);

		return df.format(cal.getTime());
	}

	/**
	 * 根据日期获取 当月第一天的时间格式
	 *
	 * @param date
	 * @return
	 */
	public static String getShortFirstDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		DateFormat df = getNewDateFormat(shortFormat);

		return df.format(cal.getTime());
	}

	public static String getWebTodayString() {
		DateFormat df = getNewDateFormat(webFormat);

		return df.format(new Date());
	}

	public static String getWebFirstDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		Date dt = new Date();

		cal.setTime(dt);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		DateFormat df = getNewDateFormat(webFormat);

		return df.format(cal.getTime());
	}

	public static String convert(String dateString, DateFormat formatIn, DateFormat formatOut) {
		try {
			Date date = formatIn.parse(dateString);

			return formatOut.format(date);
		} catch (ParseException e) {
			return "";
		}
	}

	public static String convert2WebFormat(String dateString) {
		DateFormat df1 = getNewDateFormat(shortFormat);
		DateFormat df2 = getNewDateFormat(webFormat);

		return convert(dateString, df1, df2);
	}

	public static String convert2ChineseDtFormat(String dateString) {
		DateFormat df1 = getNewDateFormat(shortFormat);
		DateFormat df2 = getNewDateFormat(chineseDtFormat);

		return convert(dateString, df1, df2);
	}

	public static String convertFromWebFormat(String dateString) {
		DateFormat df1 = getNewDateFormat(shortFormat);
		DateFormat df2 = getNewDateFormat(webFormat);

		return convert(dateString, df2, df1);
	}

	public static boolean webDateNotLessThan(String date1, String date2) {
		DateFormat df = getNewDateFormat(webFormat);

		return dateNotLessThan(date1, date2, df);
	}

	/**
	 * @param date1
	 * @param date2
	 * @param
	 * @return
	 */
	public static boolean dateNotLessThan(String date1, String date2, DateFormat format) {
		try {
			Date d1 = format.parse(date1);
			Date d2 = format.parse(date2);

			if (d1.before(d2)) {
				return false;
			} else {
				return true;
			}
		} catch (ParseException e) {
			return false;
		}
	}

	public static String getEmailDate(Date today) {
		String todayStr;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");

		todayStr = sdf.format(today);
		return todayStr;
	}

	public static String getSmsDate(Date today) {
		String todayStr;
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日HH:mm");

		todayStr = sdf.format(today);
		return todayStr;
	}

	public static String formatMonth(Date date) {
		if (date == null) {
			return null;
		}

		return new SimpleDateFormat(monthFormat).format(date);
	}

	/**
	 * 获取系统日期的前一天日期，返回Date
	 *
	 * @return
	 */
	public static Date getBeforeDate() {
		Date date = new Date();

		return new Date(date.getTime() - (ONE_DAY_MILL_SECONDS));
	}

	/**
	 * 获取参数的前一天日期，返回Date
	 *
	 * @return
	 */
	public static String getBeforeDateStr(String dateStr) {
		DateFormat df = new SimpleDateFormat(longFormat);
		Date date = null;
		try {
			date = df.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar sysDate = new GregorianCalendar();
		sysDate.setTime(date);
		sysDate.add(Calendar.DAY_OF_YEAR, -1);
		return df.format(sysDate.getTime());
	}

	/**
	 * 获得指定时间当天起点时间
	 *
	 * @param date
	 * @return
	 */
	public static Date getDayBegin(Date date) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		df.setLenient(false);

		String dateString = df.format(date);

		try {
			return df.parse(dateString);
		} catch (ParseException e) {
			return date;
		}
	}

	/**
	 * 判断参date上min分钟后，是否小于当前时间
	 *
	 * @param date
	 * @param min
	 * @return
	 */
	public static boolean dateLessThanNowAddMin(Date date, long min) {
		return addMinutes(date, min).before(new Date());

	}

	public static boolean isBeforeNow(Date date) {
		if (date == null) {
			return false;
		}

		return date.compareTo(new Date()) < 0;
	}

	public static Date parseNoSecondFormat(String sDate) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(noSecondFormat);

		if ((sDate == null) || (sDate.length() < noSecondFormat.length())) {
			throw new ParseException("length too little", 0);
		}

		if (!org.apache.commons.lang3.StringUtils.isNumeric(sDate)) {
			throw new ParseException("not all digit", 0);
		}

		return dateFormat.parse(sDate);
	}

	public static Date addMonth(Date date, int months) {
		Calendar sysDate = new GregorianCalendar();
		sysDate.setTime(date);
		sysDate.add(Calendar.MONTH, months);
		return sysDate.getTime();
	}

	public static Date getNewDateByFormate(Date date, String formate) {
		DateFormat df = new SimpleDateFormat(formate);

		String dateString = df.format(date);

		try {
			return df.parse(dateString);
		} catch (ParseException e) {
			return date;
		}
	}

	/**
	 * 获取N个月后的年月格式日期
	 *
	 * @param date
	 * @param months
	 * @return
	 */
	public static String addMonthExtend(Date date, int months) {
		Calendar sysDate = new GregorianCalendar();
		sysDate.setTime(date);
		sysDate.add(Calendar.MONTH, months);
		return formatMonth(sysDate.getTime());
	}

	public static String addYearExtend(Date date, int year) {
		Calendar sysDate = new GregorianCalendar();
		sysDate.setTime(date);
		sysDate.add(Calendar.YEAR, year);
		return formatYear(sysDate.getTime());
	}

	public static String formatYear(Date date) {
		if (date == null) {
			return null;
		}

		return new SimpleDateFormat(yearFormat).format(date);
	}

	/**
	 * 获取指定相隔N周的星期一
	 *
	 * @return
	 */
	public static String getWeekMonday(int temp, Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		//n为推迟的周数，0本周，-1向前推迟一周，1下周，依次类推
		String monday;
		cal.add(Calendar.DATE, temp * 7);
		//想周几，这里就传几Calendar.MONDAY（TUESDAY...）
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		monday = new SimpleDateFormat(shortFormat).format(cal.getTime());
		return monday;
	}

	/**
	 * 获取指定相隔N周的周日
	 *
	 * @return
	 */
	public static String getWeekSunday(int temp, Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setFirstDayOfWeek(Calendar.SUNDAY);
		//n为推迟的周数，0本周，-1向前推迟一周，1下周，依次类推
		String monday;
		cal.add(Calendar.DATE, temp * 7);
		//想周几，这里就传几Calendar.MONDAY（TUESDAY...）
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		monday = new SimpleDateFormat(shortFormat).format(cal.getTime());
		return monday;
	}

	/**
	 * @param date      基准时间
	 * @param dayOfWeek 星期几
	 * @return
	 */
	public static String getWeekTargetDay(int weekOfYeah, Date date, int dayOfWeek) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		//n为推迟的周数，0本周，-1向前推迟一周，1下周，依次类推
		String monday;
		cal.add(Calendar.DATE, weekOfYeah * 7);
		//周内时间星期几对应的date
		cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
		monday = new SimpleDateFormat(shortFormat).format(cal.getTime());
		return monday;
	}

	/**
	 * 计算两个日期之间月份的间隔数
	 */
	public static int getDifferenceMonth(Date start, Date end) {
		if (start.after(end)) {
			Date t = start;
			start = end;
			end = t;
		}
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(start);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(end);
		Calendar temp = Calendar.getInstance();
		temp.setTime(end);
		temp.add(Calendar.DATE, 1);
		int year = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		int month = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
		if ((startCalendar.get(Calendar.DATE) == 1) && (temp.get(Calendar.DATE) == 1)) {
			return year * 12 + month + 1;
		} else if ((startCalendar.get(Calendar.DATE) != 1) && (temp.get(Calendar.DATE) == 1)) {
			return year * 12 + month;
		} else if ((startCalendar.get(Calendar.DATE) == 1) && (temp.get(Calendar.DATE) != 1)) {
			return year * 12 + month;
		} else {
			return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);
		}
	}

	/**
	 * 将PHP中时间转换成java中的date.
	 *
	 * @param time    Long类型的时间
	 * @param pattern 时间格式
	 * @return Date
	 */
	public static Date convertDate(Long time, String pattern) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			date = sdf.parse(sdf.format(new Date(time * 1000)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	public static String isOverTime(String beginDate, String endDate, String formate) {
		if (org.apache.commons.lang3.StringUtils.isBlank(formate)) {
			return "日期格式错误";
		}
		if (org.apache.commons.lang3.StringUtils.isBlank(beginDate) || org.apache.commons.lang3.StringUtils.isBlank(endDate)) {
			return "日期错误";
		}

		try {
			Date begin = parseDateNoTime(beginDate, formate);
			if (getDiffSeconds(new Date(), begin) < 0) {
				return "未开始";
			}
		} catch (Exception e) {
			return "日期转换错误";
		}


		try {
			Date end = parseDateNoTime(endDate, formate);
			if (getDiffSeconds(new Date(), end) > 0) {
				return "已结束";
			}
		} catch (Exception e) {
			return "日期转换错误";
		}


		try {
			Date begin = DateUtil.parseDateNoTime(beginDate, formate);
			Date end = DateUtil.parseDateNoTime(endDate, formate);
			if (getDiffSeconds(new Date(), begin) >= 0 && getDiffSeconds(new Date(), end) <= 0) {
				return "进行中";
			}
		} catch (Exception e) {
			return "日期转换错误";
		}

		return "";
	}

	public static String getPastDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
		Date today = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String result = format.format(today);
		return result;
	}

	public static boolean verifyStartEndTime(String startTime, String endTime) {
		// 如果传有时间参数进行判断
		if (!org.apache.commons.lang3.StringUtils.isEmpty(startTime) && !org.apache.commons.lang3.StringUtils.isEmpty(endTime)) {
			Date start = parseDateNewFormat(startTime);
			Date end = parseDateNewFormat(endTime);
			if (start == null || end == null || end.getTime() < start.getTime()) {

				throw new RuntimeException("时间区间有误");
			}
		}
		return true;
	}

	/**
	 * 将LocalDate转化为Date
	 *
	 * @param localDate
	 * @return
	 */
	public static Date localDateToDate(LocalDate localDate) {
		ZoneId zoneId = ZoneId.systemDefault();
		ZonedDateTime zonedDateTime = localDate.atStartOfDay(zoneId);
		return Date.from(zonedDateTime.toInstant());
	}

	/**
	 * 将Date转化为LocalDate
	 *
	 * @param date
	 * @return
	 */
	public static LocalDate dateToLocalDate(Date date) {
		ZoneId zoneId = ZoneId.systemDefault();
		Instant instant = date.toInstant();
		ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, zoneId);
		return zonedDateTime.toLocalDate();

	}

	/**
	 * 将LocalDateTime转化为Date
	 *
	 * @param localDateTime
	 * @return
	 */
	public static Date localDateTimeToDate(LocalDateTime localDateTime) {
		ZoneId zoneId = ZoneId.systemDefault();
		ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
		return Date.from(zonedDateTime.toInstant());
	}

	/**
	 * 将Date转化为LocalDate
	 *
	 * @param date
	 * @return
	 */
	public static LocalDateTime dateToLocalDateTime(Date date) {
		ZoneId zoneId = ZoneId.systemDefault();
		Instant instant = date.toInstant();
		return LocalDateTime.ofInstant(instant, zoneId);

	}

	/**
	 * 获取一天开始时间 如 2014-12-12 00:00:00
	 *
	 * @return 一天开始时间
	 */
	public static Date getDayStart(Date date) {
		if (null == date) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取一天结束时间 如 2014-12-12 23:59:59
	 *
	 * @return 一天结束时间
	 */
	public static Date getDayEnd(Date date) {
		if (null == date) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	/**
	 * 判断两个日期是否是同一天
	 *
	 * @author: gaojing [gaojing1996@vip.qq.com]
	 */
	public static boolean inSameDay(Date one, Date two) {
		return format(one, webFormat).equals(format(two, webFormat));
	}



	public static String getHttpDateHeaderValue(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return simpleDateFormat.format(date);
	}

	public static boolean isEvening() {
		Date now = new Date();
		int[] hours = {21, 22, 23, 0, 1, 2, 3, 4};
		for (int hour : hours) {
			if (hour == now.getHours()) {
				return true;
			}
		}
		return false;
	}

	public static int getSecondsToEndOfToday() {
		Date now = new Date();
		long diffSeconds = getDiffSeconds(getDayEnd(now), now);
		if (diffSeconds < 0) {
			return 0;
		}
		return (int) diffSeconds;
	}
	/**存入es的数据都是UTC标准时间*/
	public static String formatForEs(Date date) {
		if(null == date){
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtil.dateTimeWithZoneZ);
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		String dateStr = dateFormat.format(date) + "Z";
		return dateStr;
	}
	/**存入es的数据都是UTC标准时间*/
	public static String formatForEsGMT8(Date date) {
		if(null == date){
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtil.dateTimeWithZoneZ);
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		String dateStr = dateFormat.format(date) + "Z";
		return dateStr;
	}
	public static void main(String[] args) {
		System.out.println(getTime(new Date()));
		System.out.println(getAfterDayWeek("3"));

		System.out.println(getCurrentYear()+getCurrentMonth()+getCurrentDay());
		String str = "测试试顶替顶替顶替顶替将于2015 年 5 月 20 日前上映 7-25 13:21:00";
		str="7-25 13:21:00";
		String date=getDateTimeFromString(str);
		System.out.println("提取的时间为:"+date);
		date="2019-06-11 13:00:23";
		long val=getDaySub(date,getTime());
		System.out.println("val="+val);

		System.out.println("两个时间之间的差距（秒）="+getSecondSub("2023-4-3 3:5:00",getTime()));
		System.out.println("若干秒后的时间："+getAfterSecondDate("2019-09-27 00:33:01","3500"));
//		ArticleVO v = new ArticleVO();
//
//		v.setAuthor("test");
//		v.setCommtcount("1");
//		v.setId(MD5.EncoderByMd5("http://www.163.com"));      //生成id
//		v.setPubdate(getTime(new Date()));
//		v.setReadcount("10");
//		v.setServerIp(MachineUtils.getInnetIp());       //给外网ip
//		v.setContent("content内容");
//		v.setTitle("title");
//		v.setUrl("http://www.163.com");
//		v.setCollectProcess("zhuque");
//		v.setDataSource(UrlUtils.getHost("http://www.163.com"));      //将域名写入
//		v.setUpdateTime(DateUtil.getTime(new Date()));  //赋当前时间
//		String topic="test";
//		//ClientConfig.getClientConfig().getKafkaDao().sendArticle(topic, id, v);
//		//ClientConfig.getClientConfig().getKafkaDao().send(topic,new Random().nextInt(3),id, JSON.toJSON(v));        //随机0-3端口发送
//		System.out.println("kafka 向topic("+topic+")添加消息'"+JSON.toJSON(v)+"'.");
	}

}
