package com.cleanweather;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Properties;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.cleanweather.activity.WeatherDisplayActivity;
import com.cleanweather.application.CleanWeatherApplication;
import com.cleanweather.model.CityModel;
import com.cleanweather.util.ConstantValue;
import com.cleanweather.util.DBUtil;
import com.cleanweather.util.WeatherCNCitySelectUtil;

public class SplashActivity extends Activity {
	private Thread thread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		try {
			Properties souceIdPro = new Properties();
			InputStream souceInput;
			souceInput = this.getAssets().open("channel.properties");
			if (souceInput != null) {
				souceIdPro.load(souceInput);
				if (souceIdPro == null || souceIdPro.size() == 0) {
					
				}else{
					String sourceId = souceIdPro.getProperty("channel").trim();
					StatService.setAppChannel(sourceId);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StatService.setSendLogStrategy(this,SendStrategyEnum.APP_START,1);
		// CurrentWeatherThread currentWeatherThread = new
		// CurrentWeatherThread();
		// CityModel cityModel = new CityModel();
		// cityModel.area_id = "101010100";
		// cityModel.name_cn = "±±¾©";
		// currentWeatherThread.cityModel = cityModel;
		// currentWeatherThread.detailModel = new WeatherDetailModel();
		// NetConnectThreadPool.getInstance("net",
		// NetConnectThreadPool.NET_POOL).excuteThread(currentWeatherThread);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		StatService.onResume(this);
		thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.e("onResume1", "" + SystemClock.uptimeMillis());
				initDB();
				CleanWeatherApplication.weather_key = DBUtil.getKey();
				WeatherCNCitySelectUtil.getCityListWithIndex();
				ArrayList<CityModel> subList = WeatherCNCitySelectUtil.getSubArrayList();
				final Intent intent = new Intent(getApplicationContext(), WeatherDisplayActivity.class);
				intent.putExtra("SUBLIST", subList);
				Handler handler = new Handler(getMainLooper());
				handler.postAtTime(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						startActivity(intent);
						finish();
					}
				}, SystemClock.uptimeMillis() + 1000);
				Log.e("onResume2", "" + SystemClock.uptimeMillis());
			}
		});
		thread.run();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		StatService.onPause(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (thread.isAlive()) {
			thread.interrupt();
		}
		thread = null;
	}

	public boolean isNewVersion() {
		File dbFile = new File(ConstantValue.DB_PATH + "/" + ConstantValue.DB_FILE);
		if (dbFile.exists()) {
			String version = DBUtil.getVersionInfo();
			return !version.equals(ConstantValue.VERSION_INFO);
		}
		return true;
	}

	private boolean initDB() {
		try {
			File dbDir = new File(ConstantValue.DB_PATH);
			if (!dbDir.exists()) {
				dbDir.mkdir();
			}
			if (isNewVersion()) {
				wirteDB();
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private void wirteDB() {
		// TODO Auto-generated method stub
		try {
			File dbFile = new File(ConstantValue.DB_PATH + "/" + ConstantValue.DB_FILE);
			InputStream myInput = null;
			OutputStream myOutput;
			myOutput = new FileOutputStream(dbFile);
			myInput = getResources().openRawResource(R.raw.base);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = myInput.read(buffer)) > 0) {
				myOutput.write(buffer, 0, length);
			}
			myOutput.flush();
			myInput.close();
			myOutput.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
