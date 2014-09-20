package com.kuszki.finallyhome;

import java.util.HashMap;
import java.util.Map;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends FragmentActivity
{

	public static	MainActivity 			Context			=	null;
	
	public static	Map<Integer, EditText>	Edits			=	new HashMap<Integer, EditText>();
	public static	Map<Integer, Button>	Buttons			=	new HashMap<Integer, Button>();
	public static	Map<Integer, Switch>	Switches		=	new HashMap<Integer, Switch>();
	public static	Map<Integer, ViewGroup>	Views			=	new HashMap<Integer, ViewGroup>();
	public static	Map<Integer, SeekBar>	Bars			=	new HashMap<Integer, SeekBar>();
	public static	Map<Integer, TextView>	Labels			=	new HashMap<Integer, TextView>();
	
	private			String[]				titles			=	null;
	
    private			ViewPager				pager			=	null;
    private			ActionBar				bar				=	null;
    private 		TabsWidget				taber			=	null;
    
    private 		ClientCore				client			=	null;
    
    public 			OnClickListener 		clickListener	=	new OnClickListener()
    {
		
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
	
	public			OnCheckedChangeListener switchListener	=	new OnCheckedChangeListener()
	{
		 
		@Override
		public void onCheckedChanged(CompoundButton v, boolean checked)
		{
			switch(v.getId())
			{
				case R.id.switchConsole:
					Views.get(R.id.layoutConsole).setVisibility(checked ? View.VISIBLE : View.INVISIBLE);
				break;
			}
		}
	
	};
	
	public			OnKeyListener			editListener	=	new OnKeyListener()
	{
		
		public boolean onKey(View v, int keyCode, KeyEvent event)
		{
	        if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
	        {
	        	if (Buttons.get(R.id.buttonSend).isEnabled()) client.onSendButtonClick(v);
	        	
	        	return true;
	        }
	        
	        return false;
	    }
	
	};
	
	public void SetClickListener(Button v)
	{
		if (v != null) v.setOnClickListener(clickListener);
	}
	
	public void SetSwitchListener(Switch v)
	{
		if (v != null) v.setOnCheckedChangeListener(switchListener);
	}
	
	public void SetEditListener(EditText v)
	{
		if (v != null) v.setOnKeyListener(editListener);
	}
	
	public void SetChildsState(ViewGroup v, boolean state)
	{
		
		for (int i = 0; i < v.getChildCount(); i++)
		{
			if (v.getChildAt(i) instanceof ViewGroup) SetChildsState((ViewGroup) v.getChildAt(i), state);
			else v.getChildAt(i).setEnabled(state);
		}
	}
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

    	super.onCreate(savedInstanceState);
    	
    	//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    	
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

        pager.setOffscreenPageLimit(5);
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