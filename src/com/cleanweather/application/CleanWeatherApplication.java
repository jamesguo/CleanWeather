package com.cleanweather.application;

import java.lang.reflect.Field;

import android.app.Application;
import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

import com.cleanweather.R;

public class CleanWeatherApplication extends Application {
	public static CleanWeatherApplication cleanWeatherApplication;
	public static int windowHeight,windowWidth;
	public static int weatherInfoHeight;
	public static String weather_key;
	public static CleanWeatherApplication getInstance() {
		return cleanWeatherApplication;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		cleanWeatherApplication = this;
		Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		windowHeight=display.getHeight();
		windowWidth=display.getWidth();
		weatherInfoHeight = (int) (CleanWeatherApplication.windowHeight 
				- CleanWeatherApplication.getStatuBar() 
				- getResources().getDimension(R.dimen.titlePreferredHeight)
				- getResources().getDimension(R.dimen.tabPreferredHeight)
				- getResources().getDimension(R.dimen.bottomPreferredHeight))*2;
	}
	public static int getStatuBar() {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, sbar = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			sbar = cleanWeatherApplication.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return sbar;
	}
	
}
