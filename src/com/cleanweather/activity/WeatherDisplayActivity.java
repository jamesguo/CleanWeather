package com.cleanweather.activity;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.cleanweather.R;
import com.cleanweather.adpater.GuideAdapter;
import com.cleanweather.adpater.WeatherListAdapter;
import com.cleanweather.model.CityModel;
import com.cleanweather.model.WeatherDetailModel;
import com.cleanweather.util.WeatherCNCitySelectUtil;
import com.cleanweather.widget.PagerSlidingTabStrip;
import com.slidingmenu.lib.SlidingMenu;

public class WeatherDisplayActivity extends BaseWeatherActivity {
	private ViewPager viewPager;
	private WeatherListAdapter weatherListAdapter;
	private GuideAdapter guideAdapter;
	private PagerSlidingTabStrip slidingTabStrip;
	private Button citySelect, setting;
	private ArrayList<CityModel> subCityList;
	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_city_select:
				getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
				getSlidingMenu().showMenu();
				break;
			case R.id.btn_set:
				getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
				getSlidingMenu().showSecondaryMenu();
				break;
			default:
				break;
			}
			updateShadow(viewPager.getCurrentItem());
		}
	};
	private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			updateShadow(position);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}
	};

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		weatherListAdapter = new WeatherListAdapter(getSupportFragmentManager());
		guideAdapter = new GuideAdapter(getSupportFragmentManager());
		if (getIntent() != null) {
			subCityList = (ArrayList<CityModel>) getIntent().getSerializableExtra("SUBLIST");
		} else {
			subCityList = WeatherCNCitySelectUtil.getSubArrayList();
		}
		viewPager = (ViewPager) findViewById(R.id.pager);
		slidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		creatWeatherDetailModel();
		slidingTabStrip.setViewPager(viewPager);
		slidingTabStrip.setOnPageChangeListener(onPageChangeListener);
		slidingTabStrip.setIndicatorColor(Color.parseColor("#FF33B5E6"));
		slidingTabStrip.setUnderlineColor(Color.parseColor("#FF33B5E6"));
		citySelect = (Button) findViewById(R.id.btn_city_select);
		setting = (Button) findViewById(R.id.btn_set);
		citySelect.setOnClickListener(clickListener);
		setting.setOnClickListener(clickListener);
//		try {
//			Field mField = ViewPager.class.getDeclaredField("mScroller");
//			mField.setAccessible(true);
//			// 设置加速度
//			// ，通过改变FixedSpeedScroller这个类中的mDuration来改变动画时间（如mScroller.setmDuration(mMyDuration);）
//			Scroller mScroller = new FixedSpeedScroller(viewPager.getContext(), new DecelerateInterpolator());
//			mField.set(viewPager, mScroller);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (getSlidingMenu().isMenuShowing()) {
				showContent();
				return true;
			} else {
				finish();
				return true;
			}
		}
		return super.onKeyUp(keyCode, event);
	}

	public void creatWeatherDetailModel() {
		if (subCityList == null) {
			subCityList = new ArrayList<CityModel>();
		}
		ArrayList<WeatherDetailModel> arrayList = new ArrayList<WeatherDetailModel>();
		for (int index = 0; index < subCityList.size(); index++) {
			WeatherDetailModel detailModel = new WeatherDetailModel();
			detailModel.cityModel = subCityList.get(index);
			arrayList.add(detailModel);
		}
		weatherListAdapter.detailModels = arrayList;
		if (subCityList.size() == 0) {
			viewPager.setAdapter(guideAdapter);
			slidingTabStrip.setVisibility(View.GONE);
			getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		} else {
			viewPager.setAdapter(weatherListAdapter);
			slidingTabStrip.setVisibility(View.VISIBLE);
			updateShadow(viewPager.getCurrentItem());
		}
	}

	public void updateWeatherDetailModel(CityModel cityModel, boolean add) {
		// TODO Auto-generated method stub
		if(add){
			WeatherDetailModel detailModel = new WeatherDetailModel();
			detailModel.cityModel = cityModel;
			weatherListAdapter.detailModels.add(detailModel);
		}else{
			ArrayList<WeatherDetailModel>arrayList = weatherListAdapter.detailModels;
			int size=arrayList.size();
			int macthIndex=-1;
			for(int index=0;index<size;index++){
				WeatherDetailModel detailModel = arrayList.get(index);
				if(detailModel.cityModel!=null){
					if(detailModel.cityModel.area_id.equals(cityModel.area_id)){
						macthIndex = index;
					}
				}
			}
			if(macthIndex>=0){
				weatherListAdapter.detailModels.remove(macthIndex);
			}
		}
		if (subCityList.size() == 0) {
			viewPager.setAdapter(guideAdapter);
			slidingTabStrip.setVisibility(View.GONE);
			getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		} else {
			viewPager.setAdapter(weatherListAdapter);
			slidingTabStrip.setVisibility(View.VISIBLE);
			updateShadow(viewPager.getCurrentItem());
		}
	}
	public void updateAdapter(CityModel cityModel, boolean add) {
		// TODO Auto-generated method stub
		subCityList = WeatherCNCitySelectUtil.getSubArrayList();
		updateWeatherDetailModel(cityModel,add);
		slidingTabStrip.notifyDataSetChanged();
		weatherListAdapter.notifyDataSetChanged();
	}
	protected void updateShadow(int position) {
		// TODO Auto-generated method stub
		switch (position) {
		case 0:
			if (weatherListAdapter.getCount() - 1 != position) {
				getSlidingMenu().setMode(SlidingMenu.LEFT);
			} else {
				getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
			}
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			break;
		default:
			if (weatherListAdapter.getCount() - 1 == position) {
				getSlidingMenu().setMode(SlidingMenu.RIGHT);
				getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			} else {
				getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
			}
			break;
		}
	}

}
