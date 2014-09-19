package com.kuszki.finallyhome;

import java.util.HashMap;
import java.util.Map;

import android.app.ActionBar;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class MainActivity extends FragmentActivity
{

	public static	MainActivity 			Context			=	null;
	
	public static	Map<Integer, EditText>	Edits			=	new HashMap<Integer, EditText>();
	public static	Map<Integer, Button>	Buttons			=	new HashMap<Integer, Button>();
	public static	Map<Integer, Switch>	Switches		=	new HashMap<Integer, Switch>();
	
	private			String[]				titles			=	null;
	
    private			ViewPager				pager			=	null;
    private			ActionBar				bar				=	null;
    private 		TabsWidget				taber			=	null;
    
    private 		ClientCore				client			=	null;
    
    public 			OnClickListener clickListener	=	new OnClickListener(){
		
    	@Override
	    public void onClick(final View v)
		{
			switch(v.getId())
			{
				case R.id.buttonConnect:
					client.onConnectButtonClick(v);
				break;
				case R.id.buttonSend:
					client.onSendButtonClick(v);
				break;
			}
	    }
    	
	};
	
	public void SetClickListener(View v)
	{
		if (v != null) v.setOnClickListener(clickListener);
	}
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

    	super.onCreate(savedInstanceState);
    	
    	setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    	
    	setContentView(R.layout.activity_main);
    	
    	Context	=	this;

    	pager	=	(ViewPager) findViewById(R.id.pager);
    	titles	=	getResources().getStringArray(R.array.pages);
    	bar		=	getActionBar();
    	taber	=	new TabsWidget(pager);
    	client	=	new ClientCore(this);
    	
    	bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        bar.setDisplayShowTitleEnabled(false);
        bar.setDisplayShowHomeEnabled(false);
        
        for (String title : titles) bar.addTab(bar.newTab().setText(title).setTabListener(taber));

        pager.setAdapter(new PagerWidget(getSupportFragmentManager()));
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
        {
            @Override
            public void onPageSelected(int position)
            {
                bar.setSelectedNavigationItem(position);
            }
        });
        
    }
    
}