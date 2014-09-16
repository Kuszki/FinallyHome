package com.kuszki.finallyhome;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.view.LayoutInflater;

public class MainActivity extends FragmentActivity
{

	public static	MainActivity 	Context			=	null;
	
	public static	EditText		editAdress		=	null;
	public static	EditText		editPort		=	null;
	public static	EditText		editCmd			=	null;
	public static	EditText		editLog			=	null;
	
	public static	Button			buttonConnect	=	null;
	
	private			String[]		titles			=	null;
	
    private			ViewPager		pager			=	null;
    private			ActionBar		bar				=	null;
    private 		TabsWidget		taber			=	null;
    
    private 		ClientCore		client			=	null;
    
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
    	
    	setContentView(R.layout.activity_main);
    	
    	Context	=	this;

    	pager	=	(ViewPager) findViewById(R.id.pager);
    	titles	=	getResources().getStringArray(R.array.pages);
    	bar		=	getActionBar();
    	taber	=	new TabsWidget(pager);
    	client	=	new ClientCore(this);
    	
    	bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        for (int i = 0; i < titles.length; i++) bar.addTab(bar.newTab().setText(titles[i]).setTabListener(taber));

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