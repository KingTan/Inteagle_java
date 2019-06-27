package com.inteagle.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 时间工具类
 * 
 * @author IVAn
 * @CreateDate 2018年7月12日 下午3:05:59
 */
public class DateUtil {

	private static Map<String, ThreadLocal<DateFormat>> dateFormatMap;

	public static final String DATE_PATTERN = "yyyy-MM-dd";

	public static final String TIME_PATTERN = "HH:mm:ss";

	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static final String DATE_TIME_NUMBER = "yyyyMMddHHmmss";

	private DateUtil() {
		throw new RuntimeException("new DateUtil instance error");
	}

	/**
	 * 获得dateFormat,没有则新建一个
	 * 
	 * @return dateFormat：日期格式类
	 * @author IVAn
	 * @CreateDate 2017年11月14日 10:40:10
	 */
	private static DateFormat getSimpleDateFormat(final String pattern) {
		ThreadLocal<DateFormat> dateFormat = getDateFormatMap().get(pattern);
		if (dateFormat == null) {
			dateFormat = new ThreadLocal<DateFormat>() {
				@Override
				protected DateFormat initialValue() {
					return new SimpleDateFormat(pattern);
				}
			};
			dateFormatMap.put(pattern, dateFormat);
		}
		return dateFormat.get();
	}

	/**
	 * 获得dateFormatMap,没有则新建一个
	 * 
	 * @return dateFormatMap：日期格式Map
	 * @author IVAn
	 * @CreateDate 2017年11月14日 10:14:22
	 */
	private static Map<String, ThreadLocal<DateFormat>> getDateFormatMap() {
		if (dateFormatMap == null) {
			synchronized (DateUtil.class) {
				if (dateFormatMap == null) {
					dateFormatMap = new ConcurrentHashMap<>();
				}
			}
		}
		return dateFormatMap;
	}

	/**
	 * 获取当前系统日期字符串
	 * 
	 * @return String：日期字符串
	 * @author IVAn
	 * @CreateDate 2017年11月13日 16:04:08
	 */
	public static String getDateStr() {
		return formatDate(DATE_PATTERN);
	}

	/**
	 * 获取当前系统时间字符串
	 * 
	 * @return String：时间字符串
	 * @author IVAn
	 * @CreateDate 2017年11月13日 16:04:08
	 */
	public static String getTimeStr() {
		return formatDate(TIME_PATTERN);
	}

	/**
	 * 获取当前系统时间字符串
	 * 
	 * @return String：时间字符串
	 * @author IVAn
	 * @CreateDate 2017年11月13日 16:04:08
	 */
	public static String getDateTimeStr() {
		return formatDate(DATE_TIME_PATTERN);
	}

	/**
	 * 获取当前时间数字
	 * 
	 * @return String：时间数字
	 * @author IVAn
	 * @createDate 2018年8月24日 下午5:40:59
	 */
	public static String getTimeNumber() {
		return formatDate(DATE_TIME_NUMBER);
	}

	/**
	 * 
	 * 获取现在的日期
	 * 
	 * @return Date
	 * @author IVAn
	 * @createDate 2018年5月7日 下午8:53:30
	 */
	public static Date getDate() {
		return parseDate(DATE_PATTERN, getDateStr());
	}

	/**
	 * 获取现在的时间
	 * 
	 * @return Date
	 * @author IVAn
	 * @createDate 2018年5月7日 下午8:53:30
	 */
	public static Date getTime() {
		return parseDate(TIME_PATTERN, getTimeStr());
	}

	/**
	 * 传入时间格式和时间获取字符串
	 * 
	 * @param pattern:时间格式
	 * @param date:时间
	 * @return String:时间字符串
	 * @author IVAn
	 * @createDate 2018年5月15日 上午10:46:56
	 */
	public static String formatDate(String pattern, Date date) {
		return getSimpleDateFormat(pattern).format(date);
	}

	/**
	 * 传入时间格式，获取当前系统时间
	 * 
	 * @param pattern：时间格式
	 * @return String：时间字符串
	 * @author IVAn
	 * @CreateDate 2017年11月13日 16:04:08
	 */
	public static String formatDate(String pattern) {
		return getSimpleDateFormat(pattern).format(new Date());
	}

	/**
	 * 传入时间格式字符串和时间字符串，获取Date
	 * 
	 * @param pattern：时间格式
	 * @param dateStr：时间字符串
	 * @return Date：日期类
	 * @author IVAn
	 * @CreateDate 2017年11月13日 16:27:55
	 */
	public static Date parseDate(String pattern, String dateStr) {
		try {
			return getSimpleDateFormat(pattern).parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取某一天的周一,参数为空则取本周
	 * 
	 * @param date：某一天
	 * @return Date：周一
	 * @author IVAn
	 * @createDate 2018年9月4日 下午7:49:21
	 */
	public static Date getMonday(Date... date) {
		Calendar calendar = Calendar.getInstance();
		if (date != null && date.length > 0) {
			calendar.setTime(date[0]);
		}
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek == 0) {
			dayOfWeek = 7;
		}
		calendar.add(Calendar.DATE, -dayOfWeek + 1);
		return calendar.getTime();
	}

	/**
	 * 获取某一天的周日,参数为空则取本周
	 * 
	 * @param date：某一天
	 * @return Date：周日
	 * @author IVAn
	 * @createDate 2018年9月4日 下午7:49:21
	 */
	public static Date getSunday(Date... date) {
		Calendar calendar = Calendar.getInstance();
		if (date != null && date.length > 0) {
			calendar.setTime(date[0]);
		}
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek == 0) {
			dayOfWeek = 7;
		}
		calendar.add(Calendar.DATE, -dayOfWeek + 7);
		return calendar.getTime();
	}

	/**
	 * 根据小时获取上午、中午、下午
	 * 
	 * @param hour
	 * @return String
	 * @author IVAn
	 * @createDate 2018年9月12日 下午4:26:45
	 */
	@SuppressWarnings("deprecation")
	public static String getDuringDay(Date... date) {
		if (date == null || date.length <= 0) {
			date = new Date[1];
			date[0] = new Date();
		}
		return getDuringDay(date[0].getHours());
	}

	/**
	 * 根据小时获取上午、中午、下午
	 * 
	 * @param hour
	 * @return String
	 * @author IVAn
	 * @createDate 2018年9月12日 下午4:26:45
	 */
	private static String getDuringDay(int hour) {
		if (hour >= 0 && hour < 8) {
			return "凌晨";
		} else if (hour >= 8 && hour < 12) {
			return "上午";
		} else if (hour >= 12 && hour < 14) {
			return "中午";
		} else if (hour >= 14 && hour < 18) {
			return "下午";
		} else if (hour >= 18 && hour < 23) {
			return "晚上";
		}
		return null;
	}

	/**
	 * 获取某天处于本周星期几
	 * 
	 * @param date
	 * @return
	 * @author IVAn
	 * @createDate 2018年9月12日 下午4:34:53
	 */
	public static int getDayOfWeek(Date... date) {
		if (date == null || date.length <= 0) {
			date = new Date[1];
			date[0] = new Date();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date[0]);
		return calendar.get(Calendar.DAY_OF_WEEK) + 1;
	}

}

