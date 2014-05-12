package com.cleanweather.network;

import java.io.File;

import com.cleanweather.application.CleanWeatherApplication;
import com.cleanweather.model.WeatherDetailModel;
import com.cleanweather.util.AccuWeatherConnectURLUtil;

public class AccuWeatherThread extends Thread {
	public WeatherDetailModel weatherDetailModel;
	@Override
	public void run() {
		String url = AccuWeatherConnectURLUtil.getConnectUrl(weatherDetailModel.cityModel);
		boolean success  = AccuWeatherConnectURLUtil.getWeatherInfo(url,weatherDetailModel);
		if (weatherDetailModel.weatherNetCallBack != null) {
			if(success){
				weatherDetailModel.weatherNetCallBack.updateSuccess();
			}else{
				weatherDetailModel.weatherNetCallBack.updateFail();
			}
		}
	}
}
