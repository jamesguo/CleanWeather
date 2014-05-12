package com.cleanweather.util;

import java.util.Calendar;

import com.cleanweather.model.WeatherIcon;

public class WeatherIconUtil {
	public static WeatherIcon getLodingIcon(){
		return WeatherIcon.shuaxin;
	}
	public static WeatherIcon getMoreIcon(){
		return WeatherIcon.more;
	}
	public static WeatherIcon getIconFromID(int type){
		WeatherIcon weatherIcon = null;
		switch (type) {
		case 0:
			if(DateUtil.getCurrentCalendar().get(Calendar.HOUR_OF_DAY)>=5&&DateUtil.getCurrentCalendar().get(Calendar.HOUR_OF_DAY)<18){
				weatherIcon = WeatherIcon.baitianqing;
			}else{
				weatherIcon = WeatherIcon.yejianqing;
			}
			break;
		case 1:
			if(DateUtil.getCurrentCalendar().get(Calendar.HOUR_OF_DAY)>=5&&DateUtil.getCurrentCalendar().get(Calendar.HOUR_OF_DAY)<18){
				weatherIcon = WeatherIcon.baitianduoyun;
			}else{
				weatherIcon = WeatherIcon.yejianduoyun;
			}
			break;
		case 2:
			weatherIcon = WeatherIcon.yin;
			break;
		case 3:
			weatherIcon = WeatherIcon.zhenyu;
			break;
		case 4:
			weatherIcon = WeatherIcon.leizhenyu;
			break;
		case 5:
			weatherIcon = WeatherIcon.leizhenyubanyoubingbao;
			break;
		case 6:
			weatherIcon = WeatherIcon.yujiaxue;
			break;
		case 7:
			weatherIcon = WeatherIcon.xiaoyu;
			break;
		case 8:
			weatherIcon = WeatherIcon.zhongyu;
			break;
		case 9:
			weatherIcon = WeatherIcon.dayu;
			break;
		case 10:
			weatherIcon = WeatherIcon.baoyu;
			break;
		case 11:
			weatherIcon = WeatherIcon.dabaoyu;
			break;
		case 12:
			weatherIcon = WeatherIcon.tedaboyu;
			break;
		case 13:
			weatherIcon = WeatherIcon.zhenxue;
			break;
		case 14:
			weatherIcon = WeatherIcon.xiaoxue;
			break;
		case 15:
			weatherIcon = WeatherIcon.zhongxue;
			break;
		case 16:
			weatherIcon = WeatherIcon.daxue;
			break;
		case 17:
			weatherIcon = WeatherIcon.baoxue;
			break;
		case 18:
			weatherIcon = WeatherIcon.wu;
			break;
		case 19:
			weatherIcon = WeatherIcon.dongyu;
			break;
		case 20:
			weatherIcon = WeatherIcon.shachenbao;
			break;
		case 21:
			weatherIcon = WeatherIcon.xiaodaozhongyu;
			break;
		case 22:
			weatherIcon = WeatherIcon.zhongdaodayu;
			break;
		case 23:
			weatherIcon = WeatherIcon.dabaoyuzhuantedabaoyu;
			break;
		case 24:
			weatherIcon = WeatherIcon.baoyuzhuanbaoyu;
			break;
		case 25:
			weatherIcon = WeatherIcon.dabaoyuzhuantedabaoyu;
			break;
		case 26:
			weatherIcon = WeatherIcon.xiaodaozhongxue;
			break;
		case 27:
			weatherIcon = WeatherIcon.zhongdaodaxue;
			break;
		case 28:
			weatherIcon = WeatherIcon.dadaobaoxue;
			break;
		case 29:
			weatherIcon = WeatherIcon.fuchen;
			break;
		case 30:
			weatherIcon = WeatherIcon.yangsha;
			break;
		case 31:
			weatherIcon = WeatherIcon.qiangshachenbao;
			break;
		case 32:
			weatherIcon = WeatherIcon.zhongyu;
			break;
		case 33:
			weatherIcon = WeatherIcon.zhongxue;
			break;
		case 34:
			weatherIcon = WeatherIcon.yin;
			break;
		case 35:
			weatherIcon = WeatherIcon.zhenyu;
			break;
		case 36:
			weatherIcon = WeatherIcon.zhenyu;
			break;
		case 37:
			weatherIcon = WeatherIcon.zhenyu;
			break;
		case 38:
			weatherIcon = WeatherIcon.zhenyu;
			break;
		case 39:
			weatherIcon = WeatherIcon.yin;
			break;
		case 40:
			weatherIcon = WeatherIcon.yin;
			break;
		case 53:
			weatherIcon = WeatherIcon.mai;
			break;
		default:
			break;
		}
		return weatherIcon;
	}
}
