package com.cleanweather.model;

import java.io.Serializable;

import android.os.Binder;

public class CityModel extends Binder implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2771651532066395886L;
	public String area_id="";
	public String name_cn="";
	public String name_en="";
	public String district_cn="";
	public String district_en="";
	public String prov_cn="";
	public String prov_en="";
	public String cityjp="";
	public int isSub=0;
	public boolean currentLocation=false;
	public CityModel(){
		
	}
	@Override
	public boolean equals(Object object) {
		// TODO Auto-generated method stub
		if(object instanceof CityModel){
			CityModel cityModel = (CityModel)object;
			return area_id.equals(cityModel.area_id);
		}else{
			return false;
		}
	}
	
}
