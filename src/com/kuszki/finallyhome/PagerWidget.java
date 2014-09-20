package com.kuszki.finallyhome;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerWidget extends FragmentPagerAdapter
{
	
	protected static final int count = 3;
	
	public PagerWidget(FragmentManager fm)
	{
        super(fm);
    }

    @Override
    public Fragment getItem(int pos)
    {
    	return new ViewPage(pos);
    }

    @Override
    public int getCount()
    {
        return count;
    }

}