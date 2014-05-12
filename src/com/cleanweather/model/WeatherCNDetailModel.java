package com.cleanweather.model;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class WeatherCNDetailModel extends ParcelableModel {
	public CityModel cityModel;
	public JSONArray adInfo;
	public JSONObject airQualityInfo;
	public String airQualityInfoTime;
	public JSONObject cityInfo;
	public String factTime;
	public JSONArray fineForecast;
	public String fineForecastTime;
	public String forecastTime;
	public JSONObject indexInfo;
	public JSONArray timeInfo;
	public JSONArray warningInfo;
	public JSONObject weatherFactInfo;
	public JSONArray weatherForecastInfo;
	public JSONObject weatherText;
	public WeatherCNDetailModel(Parcel source) {
		// TODO Auto-generated constructor stub
		cityModel = (CityModel) source.readParcelable(CityModel.class.getClassLoader());
	}

	public WeatherCNDetailModel() {
		// TODO Auto-generated constructor stub
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

	public static final Parcelable.Creator<WeatherCNDetailModel> CREATOR = new Creator<WeatherCNDetailModel>() {

		@Override
		public WeatherCNDetailModel[] newArray(int size) {
			return new WeatherCNDetailModel[size];
		}

		@Override
		public WeatherCNDetailModel createFromParcel(Parcel source) {
			return new WeatherCNDetailModel(source);
		}
	};

}
