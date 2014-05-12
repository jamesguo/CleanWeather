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
	 * ��ǰ�¶�
	 */
	public String currentTemp="N/A";
	/**
	 * ����ʱ��
	 */
	public String updateTime="N/A";
	/**
	 * �����
	 */
	public String lowTemp="N/A";
	/**
	 * �����
	 */
	public String highTemp="N/A";
	/**
	 * ��ǰ����
	 */
	public String weatherStr="N/A";
	public int weatherStrInt=-1;
	/**
	 * ��������
	 */
	public String weatherInfo="N/A";
	/**
	 * ʪ��
	 */
	public String humidity="N/A%";
	/**
	 * ����
	 */
	public String windPower="N/A";
	/**
	 * ����
	 */
	public String windDirection;
	/**
	 * ����
	 */
	public String precipitation;
	/**
	 * ����
	 */
	public String airtext;
	/**
	 * PM2.5
	 */
	public String pmtext;

	/**
	 * 6��Ԥ��
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
			tempStr = ""+highTemp+"��/"+lowTemp+"��";
		}else if(!StringUtil.isEmpty(highTemp)){
			tempStr = "���"+highTemp+"��";
		}else if(!StringUtil.isEmpty(lowTemp)){
			tempStr = "���"+lowTemp+"��";
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
