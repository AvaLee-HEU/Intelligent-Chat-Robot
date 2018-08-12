package com.example.irobot;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

public class IntroduceViewPagerAdapter extends FragmentPagerAdapter {
	private List<Fragment> introducefrags;

	public IntroduceViewPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}
	public IntroduceViewPagerAdapter(FragmentManager fm, List<Fragment> introducefrags) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.introducefrags = introducefrags;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return introducefrags.get(arg0);
	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return ((PagerAdapter) introducefrags).getItemPosition(object);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return introducefrags.size();
	}

}
