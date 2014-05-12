package com.cleanweather.model;

import java.io.Serializable;

import android.os.Binder;

import com.cleanweather.util.StringUtil;


public class DateWeatherModel extends Binder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4379164165250749542L;
	public String dateStr="";
	public int    weatherInt1=-1;
	public int    weatherInt2=-1;
	public String weather="";
	public String highTemp="";
	public String lowTemp="";
	public String windDirction="";
	public String windPower="";
	public String getTempStr(){
		String tempStr="";
		if(!StringUtil.isEmpty(highTemp)&&!StringUtil.isEmpty(lowTemp)){
			tempStr = highTemp+"��\n"+lowTemp+"��";
		}else if(!StringUtil.isEmpty(highTemp)){
			tempStr = "���"+highTemp+"��";
		}else if(!StringUtil.isEmpty(lowTemp)){
			tempStr = "���"+lowTemp+"��";
		}else{
			tempStr = "N/A";
		}
		return tempStr;
	}
}
