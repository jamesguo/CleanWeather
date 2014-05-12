package com.cleanweather.fragment;

import java.util.ArrayList;

import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.StateSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cleanweather.R;
import com.cleanweather.Listener.WeatherNetCallBack;
import com.cleanweather.anim.ExpandAnimation;
import com.cleanweather.application.CleanWeatherApplication;
import com.cleanweather.model.DateWeatherModel;
import com.cleanweather.model.Icon;
import com.cleanweather.model.WeatherCNDetailModel;
import com.cleanweather.model.WeatherDetailModel;
import com.cleanweather.network.AccuWeatherThread;
import com.cleanweather.network.NetConnectThreadPool;
import com.cleanweather.network.WeatherCNThread;
import com.cleanweather.util.WeatherIconUtil;
import com.cleanweather.widget.HeightLessView;
import com.cleanweather.widget.IconicFontDrawable;
import com.cleanweather.widget.PullDownView.OnAnimationCallBack;

public class WeatherDetailFragment extends Fragment implements WeatherNetCallBack, OnAnimationCallBack {
	public WeatherDetailModel detailModel;
	private TextView currentTempView, updateTime;
	// currentWeather;
	private Animation animation;
	private View weatherView, updateWeather,loadMore;
	private IconicFontDrawable iconicFontDrawable;
	private TextView todayWeather, todayTemp;
	private HeightLessView heightLessView;
	private LinearLayout linearLayout1, linearLayout2, linearLayout3, linearLayout4, linearLayout5, linearLayout6;
	private OnClickListener updateListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			updateWeather.startAnimation(animation);
			WeatherCNDetailModel weatherCNDetailModel = new WeatherCNDetailModel();
			weatherCNDetailModel.cityModel = detailModel.cityModel;
			WeatherCNThread weatherCNThread = new WeatherCNThread();
			weatherCNThread.cityModel = detailModel.cityModel;
			weatherCNThread.weatherDetailModel = detailModel;
			weatherCNThread.weatherCNDetailModel = weatherCNDetailModel;
			NetConnectThreadPool.getInstance("net", NetConnectThreadPool.NET_POOL).excuteThread(weatherCNThread);
		}
	};
	private OnClickListener closeClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			closeViewCallBack();
		}
	};
	public static WeatherDetailFragment newInstance(Bundle bundle) {
		WeatherDetailFragment f = new WeatherDetailFragment();
		f.setArguments(bundle);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			detailModel = getArguments().getParcelable("WeatherDetailModel");
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.weather_detail_layout, null);
		Log.e("onCreateView", this + "" + detailModel.cityModel.name_cn);
		currentTempView = (TextView) view.findViewById(R.id.current_temp);
		// currentWeather = (TextView) view.findViewById(R.id.current_weather);
		// tempView = (SVGView) view.findViewById(R.id.temp_icon);
		todayTemp = (TextView) view.findViewById(R.id.today_temp);
		todayWeather = (TextView) view.findViewById(R.id.today_weather);
		updateWeather = view.findViewById(R.id.update_weather);
		updateTime = (TextView) view.findViewById(R.id.update_time);
		weatherView = view.findViewById(R.id.weather_icon);
		loadMore = view.findViewById(R.id.more_layout);
		heightLessView = (HeightLessView) view.findViewById(R.id.weather_info_content);
		heightLessView.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, CleanWeatherApplication.weatherInfoHeight));
		linearLayout1 = (LinearLayout) view.findViewById(R.id.forcast_1);
		linearLayout2 = (LinearLayout) view.findViewById(R.id.forcast_2);
		linearLayout3 = (LinearLayout) view.findViewById(R.id.forcast_3);
		linearLayout4 = (LinearLayout) view.findViewById(R.id.forcast_4);
		linearLayout5 = (LinearLayout) view.findViewById(R.id.forcast_5);
		linearLayout6 = (LinearLayout) view.findViewById(R.id.forcast_6);
		updateWeather.setOnClickListener(updateListener);
		updateTime.setOnClickListener(updateListener);
		loadMore.setOnClickListener(closeClickListener);
		IconicFontDrawable loadingDrawble = new IconicFontDrawable(inflater.getContext());
		loadingDrawble.setIcon(WeatherIconUtil.getLodingIcon());
		IconicFontDrawable moreDrawble = new IconicFontDrawable(inflater.getContext());
		moreDrawble.setIcon(WeatherIconUtil.getMoreIcon());
		moreDrawble.setIconColor(Color.parseColor("#e00065ca"));
		iconicFontDrawable = new IconicFontDrawable(inflater.getContext());
		if (Build.VERSION.SDK_INT >= 16) {
			weatherView.setBackground(iconicFontDrawable);
			updateWeather.setBackground(loadingDrawble);
			loadMore.setBackground(moreDrawble);
		} else {
			weatherView.setBackgroundDrawable(iconicFontDrawable);
			updateWeather.setBackgroundDrawable(loadingDrawble);
			loadMore.setBackgroundDrawable(moreDrawble);
		}
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		animation = AnimationUtils.loadAnimation(getActivity(), R.anim.process);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (detailModel.needRefreash) {
//			WeatherCNDetailModel weatherCNDetailModel = new WeatherCNDetailModel();
//			weatherCNDetailModel.cityModel = detailModel.cityModel;
//			WeatherCNThread weatherCNThread = new WeatherCNThread();
//			weatherCNThread.cityModel = detailModel.cityModel;
//			weatherCNThread.weatherDetailModel = detailModel;
//			weatherCNThread.weatherCNDetailModel = weatherCNDetailModel;
//			NetConnectThreadPool.getInstance("net", NetConnectThreadPool.NET_POOL).excuteThread(weatherCNThread);

			AccuWeatherThread accuWeatherThread = new AccuWeatherThread();
			accuWeatherThread.weatherDetailModel = detailModel;
			NetConnectThreadPool.getInstance("net", NetConnectThreadPool.NET_POOL).excuteThread(accuWeatherThread);
			// CurrentWeatherThread currentWeatherThread = new
			// CurrentWeatherThread();
			// currentWeatherThread.cityModel = detailModel.cityModel;
			// currentWeatherThread.detailModel = detailModel;
			// NetConnectThreadPool.getInstance("net",
			// NetConnectThreadPool.NET_POOL).excuteThread(currentWeatherThread);
			// DateWeatherThread dateWeatherThread = new DateWeatherThread();
			// dateWeatherThread.cityModel = detailModel.cityModel;
			// dateWeatherThread.detailModel = detailModel;
			// NetConnectThreadPool.getInstance("net",
			// NetConnectThreadPool.NET_POOL).excuteThread(dateWeatherThread);
			// WeekWeatherThread weekWeatherThread = new WeekWeatherThread();
			// weekWeatherThread.cityModel = detailModel.cityModel;
			// weekWeatherThread.detailModel = detailModel;
			// NetConnectThreadPool.getInstance("net",
			// NetConnectThreadPool.NET_POOL).excuteThread(weekWeatherThread);
		}
		loadData();
		if (detailModel != null) {
			detailModel.weatherNetCallBack = this;
		}
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		detailModel.weatherNetCallBack = null;
		heightLessView.animationCallBack = null;
		Log.e("onPause", this + "" + detailModel.cityModel.name_cn);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Log.e("onDestroyView", this + "" + detailModel.cityModel.name_cn);
	}

	private void loadData() {
		// TODO Auto-generated method stub
		Icon icon = WeatherIconUtil.getIconFromID(detailModel.weatherStrInt);
		if (icon != null) {
			iconicFontDrawable.setIcon(icon);
		}
		heightLessView.animationCallBack = this;
		todayTemp.setText(detailModel.getTempStr());
		todayWeather.setText(detailModel.weatherInfo);
		currentTempView.setText(detailModel.currentTemp);
		updateTime.setText(detailModel.updateTime);
		// currentWeather.setText(detailModel.weatherStr);
		loadForcast();
	}

	private void loadForcast() {
		// TODO Auto-generated method stub
		ArrayList<DateWeatherModel> arrayList = detailModel.dateWeahterList;
		linearLayout1.setVisibility(View.INVISIBLE);
		linearLayout2.setVisibility(View.INVISIBLE);
		linearLayout3.setVisibility(View.INVISIBLE);
		linearLayout4.setVisibility(View.INVISIBLE);
		linearLayout5.setVisibility(View.INVISIBLE);
		linearLayout6.setVisibility(View.INVISIBLE);
		if (arrayList != null && arrayList.size() > 0) {
			int size = arrayList.size();
			for (int index = 0; index < size; index++) {
				DateWeatherModel dateWeatherModel = arrayList.get(index);
				switch (index) {
				case 0:
					loadForcastDetail(linearLayout1, dateWeatherModel);
					break;
				case 1:
					loadForcastDetail(linearLayout2, dateWeatherModel);
					break;
				case 2:
					loadForcastDetail(linearLayout3, dateWeatherModel);
					break;
				case 3:
					loadForcastDetail(linearLayout4, dateWeatherModel);
					break;
				case 4:
					loadForcastDetail(linearLayout5, dateWeatherModel);
					break;
				case 5:
					loadForcastDetail(linearLayout6, dateWeatherModel);
					break;
				default:
					break;
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void loadForcastDetail(ViewGroup group, DateWeatherModel dateWeatherModel) {
		group.setVisibility(View.VISIBLE);
		IconicFontDrawable iconicFontDrawable1 = new IconicFontDrawable(group.getContext());
		iconicFontDrawable1.setIcon(WeatherIconUtil.getIconFromID(dateWeatherModel.weatherInt1));
		iconicFontDrawable1.setIconColor(Color.parseColor("#ffffff"));
		IconicFontDrawable iconicFontDrawable2 = new IconicFontDrawable(group.getContext());
		iconicFontDrawable2.setIcon(WeatherIconUtil.getIconFromID(dateWeatherModel.weatherInt2));
		iconicFontDrawable2.setIconColor(Color.parseColor("#ffffff"));
		if (Build.VERSION.SDK_INT >= 16) {
			group.findViewById(R.id.forcast_weather_icon).setBackground(iconicFontDrawable1);
			group.findViewById(R.id.forcast_weather_icon_2).setBackground(iconicFontDrawable2);
		} else {
			group.findViewById(R.id.forcast_weather_icon).setBackgroundDrawable(iconicFontDrawable1);
			group.findViewById(R.id.forcast_weather_icon_2).setBackgroundDrawable(iconicFontDrawable2);
		}
		((TextView) group.findViewById(R.id.date)).setText(dateWeatherModel.dateStr);
		((TextView) group.findViewById(R.id.forcast_weather_str)).setText(dateWeatherModel.weather);
		((TextView) group.findViewById(R.id.forcast_weather_temp)).setText(dateWeatherModel.getTempStr());
		// ((TextView)
		// group.findViewById(R.id.forcast_wind)).setText(dateWeatherModel.windDirction+dateWeatherModel.windPower);
	}

	@Override
	public void updateSuccess() {
		// TODO Auto-generated method stub
		detailModel.needRefreash = false;
		Log.e("updateSuccess", this + "%" + detailModel.hashCode());
		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				loadData();
				updateWeather.clearAnimation();
			}
		});
	}

	@Override
	public void updateFail() {
		// TODO Auto-generated method stub
		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				updateWeather.clearAnimation();
			}
		});
	}

	@Override
	public void openViewCallBack() {
		// TODO Auto-generated method stub
		// 向下
		ExpandAnimation expandAni = new ExpandAnimation(heightLessView, 400, false);
		heightLessView.startAnimation(expandAni);
	}

	@Override
	public void closeViewCallBack() {
		// TODO Auto-generated method stub
		// 向上
		ExpandAnimation expandAni = new ExpandAnimation(heightLessView, 400, true);
		heightLessView.startAnimation(expandAni);
	}

}
