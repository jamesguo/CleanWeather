package com.cleanweather.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

import com.cleanweather.Listener.WeatherNetCallBack;
import com.cleanweather.util.StringUtil;

public class WeatherDetailModel extends ParcelableModel{
	public CityModel cityModel;
	public boolean needRefreash=true;
	/**
	 * 当前温度
	 */
	public String currentTemp="N/A";
	/**
	 * 更新时间
	 */
	public String updateTime="N/A";
	/**
	 * 最低温
	 */
	public String lowTemp="N/A";
	/**
	 * 最高温
	 */
	public String highTemp="N/A";
	/**
	 * 当前天气
	 */
	public String weatherStr="N/A";
	public int weatherStrInt=-1;
	/**
	 * 天气趋势
	 */
	public String weatherInfo="N/A";
	/**
	 * 湿度
	 */
	public String humidity="N/A%";
	/**
	 * 风力
	 */
	public String windPower="N/A";
	/**
	 * 风向
	 */
	public String windDirection;
	/**
	 * 降雨
	 */
	public String precipitation;
	/**
	 * 空气
	 */
	public String airtext;
	/**
	 * PM2.5
	 */
	public String pmtext;

	/**
	 * 6天预报
	 * */
	public ArrayList<DateWeatherModel> dateWeahterList;
	
	public WeatherNetCallBack weatherNetCallBack;
	public WeatherDetailModel(Parcel source) {
		// TODO Auto-generated constructor stub
		cityModel = (CityModel)source.readParcelable(CityModel.class.getClassLoader());
	}

	public WeatherDetailModel() {
		// TODO Auto-generated constructor stub
	}
	public String getTempStr(){
		String tempStr="";
		if(!StringUtil.isEmpty(highTemp)&&!StringUtil.isEmpty(lowTemp)){
			tempStr = ""+highTemp+"℃/"+lowTemp+"℃";
		}else if(!StringUtil.isEmpty(highTemp)){
			tempStr = "最高"+highTemp+"℃";
		}else if(!StringUtil.isEmpty(lowTemp)){
			tempStr = "最低"+lowTemp+"℃";
		}else{
			tempStr = "N/A";
		}
		return tempStr;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeValue(cityModel);
	}
	public static final Parcelable.Creator<WeatherDetailModel> CREATOR = new Creator<WeatherDetailModel>() {

		@Override
		public WeatherDetailModel[] newArray(int size) {
			return new WeatherDetailModel[size];
		}

		@Override
		public WeatherDetailModel createFromParcel(Parcel source) {
			return new WeatherDetailModel(source);
		}
	};
}
