package com.cleanweather.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.cleanweather.application.CleanWeatherApplication;
import com.cleanweather.model.CityModel;
import com.cleanweather.model.WeatherDetailModel;

public class AccuWeatherConnectURLUtil {
	public static String getConnectUrl(CityModel cityModel) {
		String url = "";
		url = "http://accuwxturbo.accu-weather.com/widget/htc2/weather-data.asp?langid=13&slat=31.215786&slon=121.30435&metric=1";
		return url;
	}

	public static boolean getWeatherInfo(String url,WeatherDetailModel weatherDetailModel) {
		// TODO Auto-generated method stub
		try {
			URL mUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) mUrl.openConnection();
			conn.setConnectTimeout(10 * 1000);
			conn.connect();
			if (conn.getResponseCode() == 200) {
				long currentTime = conn.getDate();
				DateUtil.calTime(currentTime);
				InputStream inputStream = conn.getInputStream();
//				File file = CleanWeatherApplication.getInstance().getBaseContext().getFileStreamPath("test.xml");
//				StringUtil.inputstreamtofile(inputStream,file);
				ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
				int ch;
				while ((ch = inputStream.read()) != -1) {
					bytestream.write(ch);
				}
				byte[] imgdata = bytestream.toByteArray();
				bytestream.close();
				InputStream decodedInput=new ByteArrayInputStream(imgdata);
				WeatherXmlUtil weatherXmlUtil = new WeatherXmlUtil();
				boolean success = weatherXmlUtil.parseInfo(decodedInput, weatherDetailModel);
				return success;
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return false;
	}
}
