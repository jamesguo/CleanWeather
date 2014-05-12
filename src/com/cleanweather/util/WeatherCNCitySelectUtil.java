package com.cleanweather.util;

import java.util.ArrayList;
import java.util.HashMap;

import com.cleanweather.model.CityModel;

public class WeatherCNCitySelectUtil {
	public static ArrayList<CityModel> allCityListWithIndex = new ArrayList<CityModel>();
	public static HashMap<String, Integer> indexCacheMap = new HashMap<String, Integer>();
	public static ArrayList<String> indexStrCacheMap = new ArrayList<String>();

	public static ArrayList<CityModel> getCityListWithIndex() {
		if (allCityListWithIndex == null || allCityListWithIndex.size() <= 0) {
			allCityListWithIndex = getAllCityList();
		}
		return allCityListWithIndex;
	}

	/**
	 * @return
	 */
	private static ArrayList<CityModel> getAllCityList() {
		allCityListWithIndex.clear();
		indexCacheMap.clear();
		indexStrCacheMap.clear();
		ArrayList<CityModel> allArrayList = new ArrayList<CityModel>();
		ArrayList<CityModel> arrayList = DBUtil.getAllCityList();
		if(arrayList!=null){
			int count = arrayList.size();
			String firstLetter = "";
			for (int i = 0; i < count; i++) {
				if (!firstLetter.equals(arrayList.get(i).name_en.substring(0, 1).toUpperCase())) {
					firstLetter = arrayList.get(i).name_en.substring(0, 1).toUpperCase().toUpperCase();
					CityModel indexModel = new CityModel();
					indexModel.area_id = "-1";
					indexModel.name_cn = "" + firstLetter;
					allArrayList.add(indexModel);
				}
				allArrayList.add(arrayList.get(i));
			}
			for (int index = 0; index < allArrayList.size(); index++) {
				CityModel cityModel = allArrayList.get(index);
				if (cityModel.area_id.equals("-1")) {
					indexCacheMap.put(cityModel.name_cn, index);
					indexStrCacheMap.add(cityModel.name_cn);
				}
			}
		}
		return allArrayList;
	}

	public static int getPosition(int listPosition) {
		String text = indexStrCacheMap.get(listPosition);
		return indexCacheMap.get(text);
	}
	public static ArrayList<CityModel> getAllLikeCityList(String string){
		ArrayList<CityModel> allArrayList = new ArrayList<CityModel>();
		ArrayList<CityModel> arrayList = DBUtil.getAllLikeCityList(string);
		if(arrayList!=null){
			allArrayList.addAll(arrayList);
		}
		return allArrayList;
	}
	public static ArrayList<CityModel> getSubArrayList() {
		ArrayList<CityModel> subArrayList = new ArrayList<CityModel>();
		ArrayList<CityModel> arrayList = DBUtil.getSubCityList();
		if(arrayList!=null){
			subArrayList.addAll(arrayList);
		}
		return subArrayList;
	}
	public static ArrayList<String> getIndexList() {
		return indexStrCacheMap;
	}
}
