package com.cleanweather.network;

import com.cleanweather.application.CleanWeatherApplication;
import com.cleanweather.model.CityModel;
import com.cleanweather.model.WeatherCNDetailModel;
import com.cleanweather.model.WeatherDetailModel;
import com.cleanweather.util.ConstantValue;
import com.cleanweather.util.DBUtil;
import com.cleanweather.util.DateUtil;
import com.cleanweather.util.StringUtil;
import com.cleanweather.util.WeatherCNConnectURLUtil;
import com.cleanweather.util.WeatherModelUtil;

public class WeatherCNThread extends Thread {
	public CityModel cityModel;
	public WeatherCNDetailModel weatherCNDetailModel;
	public WeatherDetailModel weatherDetailModel;
	@Override
	public void run() {
		if(!StringUtil.isExpires(DBUtil.getExpiresInfo())){
			CleanWeatherApplication.weather_key = WeatherCNConnectURLUtil.getKey();
		}
		if(StringUtil.isEmpty(CleanWeatherApplication.weather_key)){
			CleanWeatherApplication.weather_key = ConstantValue.WEATHER_KEY+"|"+DateUtil.getCalendarStrBySimpleDateFormat(DateUtil.getCurrentCalendar(), DateUtil.SIMPLEFORMATTYPE3);
		}
		boolean success = WeatherCNConnectURLUtil.getWeatherInfo(cityModel.area_id, CleanWeatherApplication.weather_key, weatherCNDetailModel);
		if(success){
			WeatherModelUtil.convertModel(weatherCNDetailModel, weatherDetailModel);
		}
		if (weatherDetailModel.weatherNetCallBack != null) {
			if(success){
				weatherDetailModel.weatherNetCallBack.updateSuccess();
			}else{
				weatherDetailModel.weatherNetCallBack.updateFail();
			}
		}
	}
}
