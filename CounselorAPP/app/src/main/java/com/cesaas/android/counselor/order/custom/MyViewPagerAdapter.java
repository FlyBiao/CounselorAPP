package com.cesaas.android.counselor.order.custom;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * 
 * @author fgb
 *
 */
public class MyViewPagerAdapter extends FragmentStatePagerAdapter {
	private ArrayList<Fragment> fragments;

	public MyViewPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	/**
	 * 
	 * @param fm
	 * @param fragments
	 */
	public MyViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}
}