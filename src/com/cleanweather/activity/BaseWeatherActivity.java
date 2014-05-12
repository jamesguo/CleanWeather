package com.cleanweather.activity;

import java.io.Serializable;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.cleanweather.R;
import com.cleanweather.fragment.WeatherCitySelectFragment;
import com.cleanweather.fragment.WeatherSettingFragment;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public class BaseWeatherActivity extends SlidingFragmentActivity {
	private WeatherCitySelectFragment weatherCitySelectFragment;
	private WeatherSettingFragment weatherSettingFragment;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (Build.VERSION.SDK_INT > 10) {
			getWindow().setFlags(0x01000000, 0x01000000);
		}
		super.onCreate(savedInstanceState);
		Bundle bundle = new Bundle();
		if(getIntent()!=null){
			Serializable serializable = getIntent().getSerializableExtra("SUBLIST");
			bundle.putSerializable("SUBLIST", serializable);
		}
		if (savedInstanceState != null){
			weatherCitySelectFragment = (WeatherCitySelectFragment) getSupportFragmentManager().getFragment(savedInstanceState, "WeatherCitySelectFragment");
			weatherSettingFragment = (WeatherSettingFragment) getSupportFragmentManager().getFragment(savedInstanceState, "WeatherSettingFragment");
		}
		if(weatherCitySelectFragment==null){
			weatherCitySelectFragment = WeatherCitySelectFragment.newInstance(bundle);
		}
		if(weatherSettingFragment==null){
			weatherSettingFragment = new WeatherSettingFragment();
		}
		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
		t.replace(R.id.menu_frame, weatherCitySelectFragment);
		t.commit();
		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setMode(SlidingMenu.LEFT_RIGHT);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.setSecondaryMenu(R.layout.menu_frame_two);
		sm.setSecondaryShadowDrawable(R.drawable.shadowright);
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame_two,weatherSettingFragment).commit();
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "WeatherCitySelectFragment", weatherCitySelectFragment);
		getSupportFragmentManager().putFragment(outState, "WeatherSettingFragment", weatherSettingFragment);
	}
	
	
	
}
