package com.cleanweather.util;

import java.io.InputStream;
import java.util.ArrayList;

import org.xml.sax.Attributes;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.sax.StartElementListener;
import android.util.Log;
import android.util.Xml;

import com.cleanweather.model.DateWeatherModel;
import com.cleanweather.model.WeatherDetailModel;

public class WeatherXmlUtil {
	public static String ROOTELEMENTNAME = "adc_database";

	public static String CURRENTELEMENTNAME = "currentconditions";
	public static String UPDATETIMEELEMENTNAME = "observationtime";
	public static String TEMPELEMENTNAME = "temperature";
	public static String WEATHERICONELEMENTNAME = "weathericon";
	public static String WINDSPEED = "windspeed";
	public static String WINDDIRECTION = "winddirection";
	public static String WEATHERTEXT = "weathertext";
	public static String HUMIDITY = "humidity";

	public static String FORCASTELEMENTNAME = "forecast";
	public static String FORCASTDAY = "day";
	public static String OBSDATE = "obsdate";
	public static String FORCASTDAYTIME = "daytime";
	public static String FORCASTNIGHTTIME = "nighttime";
	public static String TXTSHORT = "txtshort";
	public static String HIGHTEMPERATURE = "hightemperature";
	public static String LOWTEMPERATURE = "lowtemperature";

	public static final String ATOM_NAMESPACE = "http://www.accuweather.com";
	private String number = "";
	private DateWeatherModel dateWeatherModel;
	private ArrayList<DateWeatherModel> dateWeatherModels;

	public boolean parseInfo(InputStream inputStream, final WeatherDetailModel weatherDetailModel) {
		if (weatherDetailModel == null) {
			return false;
		}

		RootElement root = new RootElement(ATOM_NAMESPACE,ROOTELEMENTNAME);
		Element currentEntry = root.getChild(ATOM_NAMESPACE,CURRENTELEMENTNAME);
		currentEntry.setStartElementListener(new StartElementListener() {
			@Override
			public void start(Attributes attributes) {
				// TODO Auto-generated method stub
			}
		});
		currentEntry.setEndElementListener(new EndElementListener() {
			@Override
			public void end() {
				// TODO Auto-generated method stub
				weatherDetailModel.dateWeahterList=dateWeatherModels;
			}
		});
		currentEntry.getChild(ATOM_NAMESPACE,TEMPELEMENTNAME).setEndTextElementListener(new EndTextElementListener() {

			@Override
			public void end(String arg0) {
				// TODO Auto-generated method stub
				weatherDetailModel.currentTemp = arg0;
			}
		});
		currentEntry.getChild(ATOM_NAMESPACE,WEATHERICONELEMENTNAME).setEndTextElementListener(new EndTextElementListener() {

			@Override
			public void end(String body) {
				// TODO Auto-generated method stub
				weatherDetailModel.weatherStrInt = StringUtil.getIntFromString(body);
			}
		});
		currentEntry.getChild(ATOM_NAMESPACE,WINDSPEED).setEndTextElementListener(new EndTextElementListener() {

			@Override
			public void end(String body) {
				// TODO Auto-generated method stub
				weatherDetailModel.windPower = body + "Km/h";
			}
		});
		currentEntry.getChild(ATOM_NAMESPACE,WINDDIRECTION).setEndTextElementListener(new EndTextElementListener() {

			@Override
			public void end(String body) {
				// TODO Auto-generated method stub
				weatherDetailModel.windDirection = "" + (int) (StringUtil.getFloatFromString(body) / 45);
			}
		});
		currentEntry.getChild(ATOM_NAMESPACE,WEATHERTEXT).setEndTextElementListener(new EndTextElementListener() {

			@Override
			public void end(String body) {
				// TODO Auto-generated method stub
				weatherDetailModel.weatherStr = body;
			}
		});
		currentEntry.getChild(ATOM_NAMESPACE,HUMIDITY).setEndTextElementListener(new EndTextElementListener() {

			@Override
			public void end(String body) {
				// TODO Auto-generated method stub
				weatherDetailModel.humidity = body;
			}
		});
		dateWeatherModels = new ArrayList<DateWeatherModel>();
		Element forcastEntry = root.getChild(ATOM_NAMESPACE,FORCASTELEMENTNAME);
		Element dateEntry = forcastEntry.getChild(ATOM_NAMESPACE,FORCASTDAY);
		dateEntry.setStartElementListener(new StartElementListener() {
			@Override
			public void start(Attributes attributes) {
				// TODO Auto-generated method stub
				number = attributes.getValue("number");
				if (!number.equals("1")) {
					dateWeatherModel = new DateWeatherModel();
				}
			}
		});
		dateEntry.setEndElementListener(new EndElementListener() {

			@Override
			public void end() {
				// TODO Auto-generated method stub
				if (!number.equals("1")) {
					dateWeatherModels.add(dateWeatherModel);
				}
			}
		});
		dateEntry.getChild(ATOM_NAMESPACE,OBSDATE).setEndTextElementListener(new EndTextElementListener() {

			@Override
			public void end(String arg0) {
				// TODO Auto-generated method stub
				if (!number.equals("1")) {
					dateWeatherModel.dateStr = arg0;
				}
			}
		});
		Element dayTime = dateEntry.getChild(ATOM_NAMESPACE,FORCASTDAYTIME);
		dayTime.getChild(ATOM_NAMESPACE,TXTSHORT).setEndTextElementListener(new EndTextElementListener() {

			@Override
			public void end(String arg0) {
				// TODO Auto-generated method stub
				if (!number.equals("1")) {
					dateWeatherModel.weather = arg0;
				}else{
					weatherDetailModel.weatherInfo = arg0;
				}
			}
		});
		dayTime.getChild(ATOM_NAMESPACE,HIGHTEMPERATURE).setEndTextElementListener(new EndTextElementListener() {

			@Override
			public void end(String arg0) {
				// TODO Auto-generated method stub
				if (!number.equals("1")) {
					dateWeatherModel.highTemp = arg0;
				} else {
					weatherDetailModel.highTemp = arg0;
				}
			}
		});
		dayTime.getChild(ATOM_NAMESPACE,LOWTEMPERATURE).setEndTextElementListener(new EndTextElementListener() {

			@Override
			public void end(String arg0) {
				// TODO Auto-generated method stub
				if (!number.equals("1")) {
					dateWeatherModel.lowTemp = arg0;
				} else {
					weatherDetailModel.lowTemp = arg0;
				}
			}
		});
		dayTime.getChild(ATOM_NAMESPACE,WEATHERICONELEMENTNAME).setEndTextElementListener(new EndTextElementListener() {

			@Override
			public void end(String arg0) {
				// TODO Auto-generated method stub
				if (!number.equals("1")) {
					dateWeatherModel.weatherInt1 = StringUtil.getIntFromString(arg0);
				}
			}
		});
		dayTime.getChild(ATOM_NAMESPACE,WINDDIRECTION).setEndTextElementListener(new EndTextElementListener() {

			@Override
			public void end(String arg0) {
				// TODO Auto-generated method stub
				if (!number.equals("1")) {
					dateWeatherModel.windDirction = "" + (int) (StringUtil.getFloatFromString(arg0) / 45);
				}
			}
		});
		dayTime.getChild(ATOM_NAMESPACE,WINDSPEED).setEndTextElementListener(new EndTextElementListener() {

			@Override
			public void end(String arg0) {
				// TODO Auto-generated method stub
				if (!number.equals("1")) {
					dateWeatherModel.windPower = arg0+"Km/h";
				}
			}
		});
		Element nightTime = dateEntry.getChild(ATOM_NAMESPACE,FORCASTNIGHTTIME);
		nightTime.getChild(ATOM_NAMESPACE,WEATHERICONELEMENTNAME).setEndTextElementListener(new EndTextElementListener() {

			@Override
			public void end(String arg0) {
				// TODO Auto-generated method stub
				if (!number.equals("1")) {
					dateWeatherModel.weatherInt2 = StringUtil.getIntFromString(arg0);
				}
			}
		});
		
		
		try {
			Xml.parse(inputStream, Xml.Encoding.UTF_8, root.getContentHandler());
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
