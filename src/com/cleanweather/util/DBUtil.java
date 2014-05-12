package com.cleanweather.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.cleanweather.application.CleanWeatherApplication;
import com.cleanweather.model.CityModel;

public class DBUtil {
	/**
	 * @param area_id
	 * @param addSub
	 */
	public static void updateCity(String area_id, boolean addSub) {
		DBHelper helper = new DBHelper(CleanWeatherApplication.getInstance());
		int sub = 0;
		if (addSub) {
			Map<String, Object> map = helper.executeForMap("getMaxSub", new HashMap<String, Object>());
			sub = Integer.valueOf((String) map.get("MAX(isSub)")) + 1;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("area_id", area_id);
		param.put("isSub", sub);
		int success = helper.execute("updateCitySub", param);
		Log.e("updateCity", "updateCity Success==" + (success > 0 ? true : false));
	}

	/**
	 * @return
	 */
	public static ArrayList<CityModel> getSubCityList() {
		DBHelper helper = new DBHelper(CleanWeatherApplication.getInstance());
		ArrayList<CityModel> arrayList = helper.executeForBeanList("getSubCityList", new HashMap<String, Object>(),CityModel.class);
		return arrayList;
	}
	
	/**
	 * @return
	 */
	public static ArrayList<CityModel> getAllCityList(){
		DBHelper helper = new DBHelper(CleanWeatherApplication.getInstance());
		ArrayList<CityModel> arrayList = helper.executeForBeanList("getCityList", new HashMap<String, Object>(),CityModel.class);
		return arrayList;
	}
	
	/**
	 * @return
	 */
	public static ArrayList<CityModel> getAllLikeCityList(String string){
		DBHelper helper = new DBHelper(CleanWeatherApplication.getInstance());
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("input", string);
		ArrayList<CityModel> arrayList = helper.executeForBeanList("getLikeCityList",hashMap,CityModel.class);
		return arrayList;
	}
	
	public static String getVersionInfo(){
		String version = "";
		DBHelper helper = new DBHelper(CleanWeatherApplication.getInstance());
		Map<String, Object> map = helper.executeForMap("getVersionInfo",new HashMap<String, Object>());
		if(map!=null&&map.get("version_info")!=null){
			version = (String) map.get("version_info");
		}
		return version;
	}
	
	public static String getExpiresInfo(){
		String expires = "";
		DBHelper helper = new DBHelper(CleanWeatherApplication.getInstance());
		Map<String, Object> map = helper.executeForMap("getExpiresTime", new HashMap<String, Object>());
		if(map!=null&&map.get("version_info")!=null){
			expires = (String) map.get("version_info");
		}
		return expires;
	}
	public static void updateExpiresInfo(String expires){
		DBHelper helper = new DBHelper(CleanWeatherApplication.getInstance());
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("version_info", expires);
		helper.execute("updateExpiresTime", param);
	}
	public static String getKey(){
		String key = "";
		DBHelper helper = new DBHelper(CleanWeatherApplication.getInstance());
		Map<String, Object> map = helper.executeForMap("getKey", new HashMap<String, Object>());
		if(map!=null&&map.get("version_info")!=null){
			key = (String) map.get("version_info");
		}
		return key;
	}
	public static void updateKey(String key){
		DBHelper helper = new DBHelper(CleanWeatherApplication.getInstance());
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("version_info", key);
		helper.execute("updateKey", param);
	}
}
