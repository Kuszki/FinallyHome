package com.kuszki.finallyhome;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.support.v4.view.ViewPager;

public class TabsWidget implements ActionBar.TabListener {

	private final ViewPager pager;
	
	public TabsWidget(ViewPager viewPager)
	{
		pager = viewPager;
	}
	

	@Override
	public void onTabSelected(Tab tab, android.app.FragmentTransaction frg)
	{
		
		pager.setCurrentItem(tab.getPosition());
		
	}

	@Override
	public void onTabUnselected(Tab tab, android.app.FragmentTransaction frg) {}
	
	@Override
	public void onTabReselected(Tab tab, android.app.FragmentTransaction frg) {}
	
}
