package com.cleanweather.adpater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cleanweather.fragment.GuideFragment;

public class GuideAdapter extends FragmentPagerAdapter {

	public GuideAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return new GuideFragment();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return "Ö¸Òý";
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 1;
	}

}
