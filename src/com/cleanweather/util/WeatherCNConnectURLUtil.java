package com.cleanweather.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.cleanweather.model.CityModel;
import com.cleanweather.model.DateWeatherModel;
import com.cleanweather.model.WeatherCNDetailModel;
import com.cleanweather.model.WeatherDetailModel;

public class WeatherCNConnectURLUtil {
	/**
	 * @param cityModel
	 * @param searchType
	 * @return
	 */
	public static String getConnectUrl(CityModel cityModel, SearchType searchType) {
		String url = "";
		if (searchType == SearchType.WEATHERCURRENT) {
			url = "http://www.weather.com.cn/data/sk/" + cityModel.area_id + ".html";
		} else if (searchType == SearchType.WEATHERDATE) {
			url = "http://www.weather.com.cn/data/cityinfo/" + cityModel.area_id + ".html";
		} else if (searchType == SearchType.WEATHERWEEK) {
			url = "http://m.weather.com.cn/data/" + cityModel.area_id + ".html";
		}
		return url;
	}

	/**
	 * @param url
	 */
	public static JSONObject connectUrl(String url) {
		URL mUrl;
		JSONObject jsonObject = null;
		try {
			mUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) mUrl.openConnection();
			conn.setConnectTimeout(10 * 1000);
			conn.connect();
			if (conn.getResponseCode() == 200) {
				long currentTime = conn.getDate();
				DateUtil.calTime(currentTime);
				InputStream in = conn.getInputStream();
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = in.read(buffer)) != -1) {
					outputStream.write(buffer, 0, len);
				}
				outputStream.close();
				in.close();
				StringBuilder builder = new StringBuilder(outputStream.toString());
				String string = builder.toString();
				jsonObject = new JSONObject(string);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

	public static boolean connvertToModel(JSONObject jsonObject, WeatherDetailModel detailModel, SearchType searchType) {
		// TODO Auto-generated method stub
		try {
			if (jsonObject != null) {
				if (searchType == SearchType.WEATHERCURRENT) {
					JSONObject weather = jsonObject.getJSONObject("weatherinfo");
					if (weather != null) {
						String currentTemp = weather.getString("temp");
						String windDirection = weather.getString("WD");
						String windPower = weather.getString("WS");
						detailModel.currentTemp = currentTemp;
						detailModel.windDirection = windDirection;
						detailModel.windPower = windPower;
						return true;
					}
				} else if (searchType == SearchType.WEATHERDATE) {
					JSONObject weather = jsonObject.getJSONObject("weatherinfo");
					if (weather != null) {
						String lowTemp = weather.getString("temp1");
						String highTemp = weather.getString("temp2");
						String weatherStr = weather.getString("weather");
						detailModel.lowTemp = lowTemp;
						detailModel.highTemp = highTemp;
						detailModel.weatherStr = weatherStr;
						return true;
					}
				} else if (searchType == SearchType.WEATHERWEEK) {
					JSONObject weather = jsonObject.getJSONObject("weatherinfo");
					ArrayList<DateWeatherModel> arrayList = new ArrayList<DateWeatherModel>();
					if (weather != null) {
						for (int index = 0; index < 6; index++) {
							Calendar date = DateUtil.getCurrentCalendar();
							date.add(Calendar.DAY_OF_YEAR, index + 1);
							DateWeatherModel dateWeatherModel = new DateWeatherModel();
							dateWeatherModel.dateStr = DateUtil.getCalendarStrBySimpleDateFormat(date, DateUtil.SIMPLEFORMATTYPE2);
							dateWeatherModel.weather = weather.getString("weather" + (index + 1));
							arrayList.add(dateWeatherModel);
						}
						detailModel.dateWeahterList = arrayList;
						return true;
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static String getKey() {
		String key = "";
		try {
			String str = ConstantValue.WEATHER_KEY;
			JSONObject localJSONObject1 = new JSONObject();
			localJSONObject1.put("method", "authorize");
			JSONObject localJSONObject2 = new JSONObject();
			localJSONObject2.put("appKey", str);
			localJSONObject1.put("param", localJSONObject2);
			String str2 = NetWorkUtil.post(ConstantValue.WEATHER_KEY_URL, localJSONObject1.toString(), false, true, true, false);
			Log.e("getKey",str2);
			JSONObject localJSONObject3 = new JSONObject(str2);
			if ("SUCCESS".equals(localJSONObject3.optString("status"))) {
				key = localJSONObject3.optJSONObject("data").optString("upload");
				DBUtil.updateKey(key);
				String expires = localJSONObject3.optJSONObject("data").optString("expiration");
				DBUtil.updateExpiresInfo(expires);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return key;
	}

	public static boolean getWeatherInfo(String cityId, String key,WeatherCNDetailModel detailModel) {
		boolean success=false;
		try {
			StringBuffer localStringBuffer = new StringBuffer();
			localStringBuffer.append("http://data.weather.com.cn/cwapidata/");
			localStringBuffer.append("zh_cn");
			localStringBuffer.append("/");
			localStringBuffer.append(cityId);
			localStringBuffer.append(".html?uk=");
			localStringBuffer.append(Base64Encoder.encode(key.getBytes("UTF-8")));
			String str = NetWorkUtil.get(localStringBuffer.toString(), true, true);
			if (!"error".equals(str)) {
				JSONObject localJSONObject1 = new JSONObject(str);
				detailModel.timeInfo=localJSONObject1.optJSONArray("t");
				detailModel.cityInfo=localJSONObject1.optJSONObject("c");
				detailModel.weatherFactInfo=localJSONObject1.optJSONObject("l");
				detailModel.indexInfo=localJSONObject1.optJSONObject("i");
				detailModel.warningInfo=localJSONObject1.optJSONArray("w");
				detailModel.adInfo=localJSONObject1.optJSONArray("a");
				detailModel.weatherText=localJSONObject1.optJSONObject("r");
				detailModel.airQualityInfo=localJSONObject1.optJSONObject("k");
		        JSONObject localJSONObject2 = localJSONObject1.optJSONObject("f");
		        if (localJSONObject2 != null)
		        {
		        	detailModel.weatherForecastInfo=localJSONObject2.optJSONArray("f1");
		        	detailModel.forecastTime=localJSONObject2.optString("f0");
		        }
		        JSONObject localJSONObject3 = localJSONObject1.optJSONObject("j");
		        if (localJSONObject3 != null)
		        {
		        	detailModel.fineForecast=localJSONObject3.optJSONArray("j1");
		        	detailModel.fineForecastTime=localJSONObject3.optString("j0");
		        }
		        JSONObject localJSONObject4 = localJSONObject1.optJSONObject("l");
		        if (localJSONObject4 != null){
		        	detailModel.factTime=localJSONObject4.optString("l7");
		        }
		        JSONObject localJSONObject5 = localJSONObject1.optJSONObject("k");
		        if (localJSONObject5 != null){
		        	detailModel.airQualityInfoTime=localJSONObject5.optString("k4");
		        }
		        success=true;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;
	}
}
