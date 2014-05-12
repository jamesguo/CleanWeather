package com.cleanweather.util;

import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cleanweather.model.DateWeatherModel;
import com.cleanweather.model.WeatherCNDetailModel;
import com.cleanweather.model.WeatherDetailModel;

public class WeatherModelUtil {
	public static void convertModel(WeatherCNDetailModel weatherCNDetailModel, WeatherDetailModel weatherDetailModel) {
		JSONObject localJSONObject1 = weatherCNDetailModel.weatherFactInfo;
//		Date localDate1 = DateUtil.parseForecastTime(weatherCNDetailModel.forecastTime);
//		Date localDate2 = DateUtil.parseFactTime(weatherCNDetailModel.factTime);
		if(localJSONObject1==null){
			return;
		}
		ArrayList<String> tempList = StringUtil.formatData((String) localJSONObject1.opt("l1"));
		if(tempList!=null&&tempList.size()>0){
			weatherDetailModel.currentTemp = tempList.get(tempList.size()-1);
		}
		ArrayList<String> humidityList = StringUtil.formatData((String) localJSONObject1.opt("l2"));
		if(humidityList!=null&&humidityList.size()>0){
			weatherDetailModel.humidity = humidityList.get(humidityList.size()-1)+"%";
		}
		ArrayList<String> windPowerList = StringUtil.formatData((String) localJSONObject1.opt("l3"));
		if(windPowerList!=null&&windPowerList.size()>0){
			weatherDetailModel.windPower = StringUtil.getWindPowerText(windPowerList.get(windPowerList.size()-1));
		}
		ArrayList<String> windDirectionList = StringUtil.formatData((String) localJSONObject1.opt("l4"));
		if(windDirectionList!=null&&windDirectionList.size()>0){
			weatherDetailModel.windDirection = StringUtil.getWindText(windDirectionList.get(windDirectionList.size()-1));
		}
		ArrayList<String> phenomenonList = StringUtil.formatData((String) localJSONObject1.opt("l5"));
		if(phenomenonList!=null&&phenomenonList.size()>0){
			weatherDetailModel.weatherStrInt =  StringUtil.getIntFromString(phenomenonList.get(phenomenonList.size()-1));
			weatherDetailModel.weatherStr = StringUtil.getWeatherText(phenomenonList.get(phenomenonList.size()-1));
		}
		ArrayList<String> precipitationList = StringUtil.formatData((String) localJSONObject1.opt("l6"));
		if(precipitationList!=null&&precipitationList.size()>0){
			try {
				weatherDetailModel.precipitation = 100*Float.valueOf(precipitationList.get(precipitationList.size()-1))+"%";
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		JSONObject localJSONObject2 = weatherCNDetailModel.airQualityInfo;
		if(localJSONObject2!=null){
			ArrayList<String> aireTextList = StringUtil.formatData((String)localJSONObject2.opt("k3"));
			if(aireTextList!=null&&aireTextList.size()>0){
				weatherDetailModel.airtext = aireTextList.get(aireTextList.size()-1);
			}
			ArrayList<String> pmtextList = StringUtil.formatData((String)localJSONObject2.opt("k2"));
			if(pmtextList!=null&&pmtextList.size()>0){
				weatherDetailModel.pmtext = pmtextList.get(pmtextList.size()-1);
			}
		}
		weatherDetailModel.updateTime = DateUtil.formatTime(DateUtil.getCurrentDate());
		JSONArray forecasetJSONObject = weatherCNDetailModel.weatherForecastInfo;
		JSONArray timeInfoJSONObject = weatherCNDetailModel.timeInfo;
		int forecastSize =forecasetJSONObject.length();
		if(forecastSize>0){
			try {
				JSONObject todayJSONObject = forecasetJSONObject.getJSONObject(0);
				if(todayJSONObject!=null){
					weatherDetailModel.highTemp = todayJSONObject.getString("fc");
					weatherDetailModel.lowTemp = todayJSONObject.getString("fd");
					String weather1 = StringUtil.getWeatherText(todayJSONObject.getString("fa"));
					String weather2 = StringUtil.getWeatherText(todayJSONObject.getString("fb"));
					if(StringUtil.isEmpty(weather1)&&StringUtil.isEmpty(weather2)){
						weatherDetailModel.weatherInfo="N/A";
					}else if(StringUtil.isEmpty(weather1)){
						weatherDetailModel.weatherInfo = weather2;
					}else if(StringUtil.isEmpty(weather2)){
						weatherDetailModel.weatherInfo = weather1;
					}else{
						weatherDetailModel.weatherInfo = weather1.equals(weather2)?weather1:(weather1+"ת"+weather2);
					}
					
					if(StringUtil.isEmpty(weatherDetailModel.highTemp)){
						weatherDetailModel.highTemp="N/A";
					}
					if(StringUtil.isEmpty(weatherDetailModel.lowTemp)){
						weatherDetailModel.lowTemp="N/A";
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ArrayList<DateWeatherModel> dateWeatherModels = new ArrayList<DateWeatherModel>();
		for(int index = 1;index<forecastSize;index++){
			try {
				JSONObject dateJSONObject = forecasetJSONObject.getJSONObject(index);
				if(dateJSONObject!=null){
					DateWeatherModel dateWeatherModel = new DateWeatherModel();
					if(timeInfoJSONObject!=null){
						JSONObject timeJSONObject = timeInfoJSONObject.getJSONObject(index);
						if(timeJSONObject!=null){
							dateWeatherModel.dateStr =DateUtil.formatDisplayDate(DateUtil.parserDate((timeJSONObject.getString("t1"))));
						}
					}
					if(StringUtil.isEmpty(dateWeatherModel.dateStr)){
						Calendar date = DateUtil.getCurrentCalendar();
						date.add(Calendar.DAY_OF_YEAR, index + 1);
						dateWeatherModel.dateStr = DateUtil.getCalendarStrBySimpleDateFormat(date, DateUtil.SIMPLEFORMATTYPE2);
					}
					String weather1 = StringUtil.getWeatherText(dateJSONObject.getString("fa"));
					String weather2 = StringUtil.getWeatherText(dateJSONObject.getString("fb"));
					dateWeatherModel.weatherInt1= StringUtil.getIntFromString(dateJSONObject.getString("fa"));
					dateWeatherModel.weatherInt2= StringUtil.getIntFromString(dateJSONObject.getString("fb"));
					if(StringUtil.isEmpty(weather1)&&StringUtil.isEmpty(weather2)){
						dateWeatherModel.weather="N/A";
					}else if(StringUtil.isEmpty(weather1)){
						dateWeatherModel.weather = weather2;
					}else if(StringUtil.isEmpty(weather2)){
						dateWeatherModel.weather = weather1;
					}else{
						dateWeatherModel.weather = weather1.equals(weather2)?weather1:(weather1+"ת"+weather2);
					}
					dateWeatherModel.highTemp=dateJSONObject.getString("fc");
					dateWeatherModel.lowTemp=dateJSONObject.getString("fd");
					dateWeatherModel.windDirction = StringUtil.getWindText(dateJSONObject.getString("fe"));
					dateWeatherModel.windPower = StringUtil.getWindPowerText(dateJSONObject.getString("ff"));
					dateWeatherModels.add(dateWeatherModel);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		weatherDetailModel.dateWeahterList = dateWeatherModels;
	}
}
