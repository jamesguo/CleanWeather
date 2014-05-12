package com.cleanweather.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
	public static final String TIMEZONE_CN = "Asia/Shanghai";
	private static long localTime; // 本地时间
	private static long serviceTime;// 服务器时间
	private static boolean timeFlag;
	/**
	 * @see #SIMPLEFORMATTYPESTRING1
	 */
	public final static int SIMPLEFORMATTYPE1 = 0x01;
	/**
	 * @see #SIMPLEFORMATTYPESTRING1
	 */
	public final static int SIMPLEFORMATTYPE2 = 0x02;
	/**
	 * @see #SIMPLEFORMATTYPESTRING1
	 */
	public final static int SIMPLEFORMATTYPE3 = 0x03;
	/**
	 * SIMPLEFORMATTYPE1 对应类型：yyyyMMddHHmmss
	 * 
	 * @see #SIMPLEFORMATTYPE1
	 */
	public final static String SIMPLEFORMATTYPESTRING1 = "yyyyMMddHHmmss";
	/**
	 * SIMPLEFORMATTYPE1 对应类型：MMdd
	 * 
	 * @see #SIMPLEFORMATTYPE1
	 */
	public final static String SIMPLEFORMATTYPESTRING2 = "MM月dd日";
	/**
	 * SIMPLEFORMATTYPE1 对应类型：MMdd
	 * 
	 * @see #SIMPLEFORMATTYPE1
	 */
	public final static String SIMPLEFORMATTYPESTRING3 = "yyyy-MM-dd";

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日");
	private static final SimpleDateFormat dateFormat_ = new SimpleDateFormat("HHmm");
	private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final SimpleDateFormat dateTimeFormat_ = new SimpleDateFormat("yyyy-M-d HH:mm");
	private static final SimpleDateFormat liveTimeFormat1 = new SimpleDateFormat("yyyyMMddHHmm");
	private static final SimpleDateFormat liveTimeFormat2 = new SimpleDateFormat("yyyyMMddHH");
	private static String mDay;
	private static String mMonth;
	private static String mWay;
	private static String mYear;
	private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	private static final SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyyMMdd");

	public static Date formatAcquiredDate(Date paramDate) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(paramDate);
		localCalendar.add(5, 2);
		return localCalendar.getTime();
	}

	public static String formatDate(Date paramDate) {
		return yyyymmdd.format(paramDate);
	}

	public static String formatDateTime(Date paramDate) {
		return dateTimeFormat.format(paramDate);
	}

	public static String formatDateTime_(Date paramDate) {
		return dateTimeFormat_.format(paramDate);
	}

	public static String formatDate_(Date paramDate) {
		return dateFormat_.format(paramDate);
	}

	public static String formatDisplayDate(Date paramDate) {
		return dateFormat.format(paramDate);
	}

	public static String formatTime(Date paramDate) {
		return timeFormat.format(paramDate);
	}

	public static Date formatTomorrowDate(Date paramDate) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(paramDate);
		localCalendar.add(5, 1);
		return localCalendar.getTime();
	}

	public static String formateUpdateTime(Date paramDate) {
		return timeFormat.format(paramDate);
	}

	public static String getStringData() {
		Calendar localCalendar = getCurrentCalendar();
		mYear = String.valueOf(localCalendar.get(Calendar.YEAR));
		mMonth = String.valueOf(1 + localCalendar.get(Calendar.MONTH));
		mDay = String.valueOf(localCalendar.get(Calendar.DAY_OF_MONTH));
		mWay = String.valueOf(localCalendar.get(Calendar.DAY_OF_WEEK));
		if ("1".equals(mWay)) {
			mWay = "天";
		} else if ("2".equals(mWay)) {
			mWay = "一";
		} else if ("3".equals(mWay)) {
			mWay = "二";
		} else if ("4".equals(mWay)) {
			mWay = "三";
		} else if ("5".equals(mWay)) {
			mWay = "四";
		} else if ("6".equals(mWay)) {
			mWay = "五";
		} else if ("7".equals(mWay)) {
			mWay = "六";
		}
		return mYear + "年" + mMonth + "月" + mDay + "日  星期" + mWay;
	}

	public static Date parseDateTime(String paramString) {
		Date localDate1;
		try {
			Date localDate2 = dateTimeFormat.parse(paramString);
			localDate1 = localDate2;
		} catch (ParseException localParseException) {
			localDate1 = null;
		}
		return localDate1;
	}

	public static Date parseFactTime(String paramString) {
		Date localObject = null;
		try {
			if (!(paramString == null || paramString.equals("")))
				if (paramString.length() == 5) {
					Date localDate = timeFormat.parse(paramString);
					localObject = localDate;
				}
		} catch (ParseException localParseException) {
			localParseException.printStackTrace();
		}
		return localObject;
	}

	public static Date parseForecastTime(String paramString) {
		Date localObject = new Date();
		try {
			if (!(paramString == null || paramString.equals("")))
				if (paramString.length() == 12) {
					localObject = liveTimeFormat1.parse(paramString);
				} else if (paramString.length() == 10) {
					Date localDate = liveTimeFormat2.parse(paramString);
					localObject = localDate;
				}
		} catch (ParseException localParseException) {
			localParseException.printStackTrace();
		}
		return localObject;
	}

	public static Date parseTime(String paramString) {
		Date localDate1;
		try {
			Date localDate2 = timeFormat.parse(paramString);
			localDate1 = localDate2;
		} catch (ParseException localParseException) {
			localDate1 = null;
		}
		return localDate1;
	}

	public static Date parserDate(String paramString) {
		Date localDate1;
		try {
			Date localDate2 = yyyymmdd.parse(paramString);
			localDate1 = localDate2;
		} catch (ParseException localParseException) {
			localDate1 = null;
		}
		return localDate1;
	}

	/**
	 * 获取当前日期 yyyyMMddHHmmss 14位
	 * 
	 * @return String
	 * @see CtripTime#getCurrentCalendar
	 * @see #getCalendarStrBySimpleDateFormat
	 * @see #SIMPLEFORMATTYPESTRING1
	 */
	public static String getCurrentTime() {
		Calendar currentCalendar = getCurrentCalendar();
		return getCalendarStrBySimpleDateFormat(currentCalendar, SIMPLEFORMATTYPE1);
	}
	public static Date getCurrentDate() {
		Calendar currentCalendar = getCurrentCalendar();
		return new Date(currentCalendar.getTimeInMillis());
	}
	public static String getCalendarStrBySimpleDateFormat(Calendar calendar, int SimpleDateFormatType) {
		String str = "";
		String type = "";
		switch (SimpleDateFormatType) {
		case SIMPLEFORMATTYPE1:
			type = SIMPLEFORMATTYPESTRING1;
			break;
		case SIMPLEFORMATTYPE2:
			type = SIMPLEFORMATTYPESTRING2;
			break;
		case SIMPLEFORMATTYPE3:
			type = SIMPLEFORMATTYPESTRING3;
			break;
		default:
			type = SIMPLEFORMATTYPESTRING1;
			break;
		}
		if (!type.equals("") && calendar != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(type);
			dateFormat.setTimeZone(TimeZone.getTimeZone(TIMEZONE_CN));
			str = (dateFormat).format(calendar.getTime());
		}
		return str;
	}

	// 确定服务器时间和本地时间是否一致，30秒内认为一致
	public static void calTime(long inputTime) {
		serviceTime = inputTime;
		localTime = System.currentTimeMillis();
		if (Math.abs(localTime - serviceTime) > 60000) {
			timeFlag = false;
		}
	}

	/**
	 * 服务器时间获取是否成功，获取校正后的本地时间 否则获取手机本地时间
	 * 
	 * @return Calendar
	 * @see DateUtil#getLocalCalendar
	 */
	public static Calendar getCurrentCalendar() {
		Calendar currentCalendar = Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE_CN));
		if (!timeFlag) {
			currentCalendar.setTimeInMillis(serviceTime + System.currentTimeMillis() - localTime);
		}
		return currentCalendar;
	}
}
