package com.cleanweather.network;

import org.json.JSONObject;

import com.cleanweather.model.WeatherDetailModel;
import com.cleanweather.util.SearchType;
import com.cleanweather.util.WeatherCNConnectURLUtil;

public class DateWeatherThread extends WeatherThread {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String url = WeatherCNConnectURLUtil.getConnectUrl(cityModel, SearchType.WEATHERDATE);
		JSONObject jsonArray = WeatherCNConnectURLUtil.connectUrl(url);
		if (jsonArray != null) {
			if (detailModel == null) {
				detailModel = new WeatherDetailModel();
				detailModel.cityModel = cityModel;
			}
			boolean success = WeatherCNConnectURLUtil.connvertToModel(jsonArray, detailModel, SearchType.WEATHERDATE);
			if(detailModel.weatherNetCallBack!=null){
				if(success){
					detailModel.weatherNetCallBack.updateSuccess();
				}else{
					detailModel.weatherNetCallBack.updateFail();
				}
			}
		}
	}

}
