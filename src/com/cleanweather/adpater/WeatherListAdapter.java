package com.cleanweather.adpater;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.cleanweather.fragment.WeatherDetailFragment;
import com.cleanweather.model.WeatherDetailModel;

public class WeatherListAdapter extends FragmentPagerAdapter {
	public ArrayList<WeatherDetailModel> detailModels = new ArrayList<WeatherDetailModel>();

	public WeatherListAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		if (arg0 >= 0 && arg0 < getCount()) {
			Bundle bundle = new Bundle();
			bundle.putParcelable("WeatherDetailModel", detailModels.get(arg0));
			return WeatherDetailFragment.newInstance(bundle);
		} else {
			return null;
		}
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		WeatherDetailFragment fragment = (WeatherDetailFragment) super.instantiateItem(container, position);
		fragment.detailModel = detailModels.get(position);
		Log.e("instantiateItem", fragment + "" + detailModels.get(position).cityModel.name_cn);
		return fragment;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		WeatherDetailFragment fragment = (WeatherDetailFragment) object;
		Log.e("destroyItem", fragment+"-"+fragment.detailModel);
		super.destroyItem(container, position, fragment);
	}

	@Override
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		super.setPrimaryItem(container, position, object);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position + 365;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		if (position >= 0 && position < getCount()) {
			return detailModels.get(position).cityModel.name_cn;
		} else {
			return "Index=" + position;
		}
		// return detailModels.get(position).cityModel.name_cn;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return detailModels.size();
	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return PagerAdapter.POSITION_NONE;
	}

	
}
