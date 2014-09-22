package com.kuszki.finallyhome;

import java.util.HashMap;
import java.util.Map;

import android.app.ActionBar;
import android.content.pm.ActivityInfo;
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

/*! \brief Główna aktywność
 * 
 *  Odpowiada za przechowywanie wszystkich używanych widgetów, wyświetlanie głównego pagera oraz obsługę zdarzeń widgetów. Działa w trybie singletona i posiada statyczne pole reprezentujące swoją unikatową instancję.
 * 
 */ public class MainActivity extends FragmentActivity
{

	public static	MainActivity 			Context			=	null;								//!< Referencja instancji klasy.
	
	public static	Map<Integer, EditText>	Edits			=	new HashMap<Integer, EditText>();	//!< Kontener na pola tekstowe.
	public static	Map<Integer, Button>	Buttons			=	new HashMap<Integer, Button>();		//!< Kontener na przyciski.
	public static	Map<Integer, Switch>	Switches		=	new HashMap<Integer, Switch>();		//!< Kontener na przełączniki.
	public static	Map<Integer, ViewGroup>	Views			=	new HashMap<Integer, ViewGroup>();	//!< Kontener na sizery.
	public static	Map<Integer, TextView>	Labels			=	new HashMap<Integer, TextView>();	//!< Kontener na etykiety.
	public static	Map<Integer, SeekBar>	Bars			=	new HashMap<Integer, SeekBar>();	//!< Kontener na paski przewijania.
	
	private			String[]				titles			=	null;								//!< Tablica przechowująca tytuły zakładek.
	
    private			ViewPager				pager			=	null;								//!< Instancja pagera.
    private			ActionBar				bar				=	null;								//!< Instancja paska akcji.
    private 		TabsWidget				taber			=	null;								//!< Instancja tabera.
    
    private 		ClientCore				client			=	null;								//!< Instancja klienta.
    
    public 			OnClickListener 					clickListener	=	new OnClickListener()
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
				default: client.onChange(v.getId(), ((Switch) v).isChecked() ? 1 : 0);
			}
	    }
    	
	}; //!< Obiekt nasłuchujący zdarzeń kliknięcia.
	
	public			OnCheckedChangeListener 			switchListener	=	new OnCheckedChangeListener()
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
	
	}; //!< Obiekt nasłuchujący zdarzeń zmiany stanu przełącznika.
	
	public			OnKeyListener						editListener	=	new OnKeyListener()
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
	
	}; //!< Obiekt nasłuchujący zdarzeń naciśnięcia klawisza.
	
	public			SeekBar.OnSeekBarChangeListener		seekListener	=	new SeekBar.OnSeekBarChangeListener() {

        public void onProgressChanged(SeekBar v, int progress, boolean fromUser) {}

        public void onStartTrackingTouch(SeekBar v) {}

        public void onStopTrackingTouch(SeekBar v)
        {
        	client.onChange(v.getId(), v.getProgress());
        }

    }; //!< Obiekt nasłuchujący zmiany położenia paska przewijania.
	
/*! \brief Ustawienie obiektu słuchającego dla zdarzeń kliknięcia.
 *  \param [in] v Instancja widgetu do nastawienia.
 * 
 *  Ustala nowy obiekt słuchający na instancję obiektu MainActivity::clickListener.
 * 
 */ public void SetClickListener(View v)
	{
		if (v != null) v.setOnClickListener(clickListener);
	}
	
 /*! \brief Ustawienie obiektu słuchającego dla zdarzeń zmiany stanu przełącznika.
  *  \param [in] v Instancja widgetu do nastawienia.
  * 
  *  Ustala nowy obiekt słuchający na instancję obiektu MainActivity::switchListener.
  * 
  */ public void SetSwitchListener(Switch v)
	{
		if (v != null) v.setOnCheckedChangeListener(switchListener);
	}
	
/*! \brief Ustawienie obiektu słuchającego dla zdarzeń wciśnięcia klawisza.
 *  \param [in] v Instancja widgetu do nastawienia.
 * 
 *  Ustala nowy obiekt słuchający na instancję obiektu MainActivity::editListener.
 * 
 */ public void SetEditListener(EditText v)
	{
		if (v != null) v.setOnKeyListener(editListener);
	}
	
/*! \brief Ustawienie obiektu słuchającego dla zdarzeń zmiany stanu paska przewijania.
 *  \param [in] v Instancja widgetu do nastawienia.
 * 
 *  Ustala nowy obiekt słuchający na instancję obiektu MainActivity::seekListener.
 * 
 */ public void SetSeekListener(SeekBar v)
	{
		if (v != null) v.setOnSeekBarChangeListener(seekListener);
	}
	
/*! \brief Ustala stan wszystkich kontrolek w danym layoucie na wybrany w parametrze.
 *  \param [in] v Instancja widgetu do nastawienia.
 *  \param [in] state Stan kontrolki:
 *                    true - aktywna,
 *                    false - niekatywna.
 * 
 *  Ustala nowy obiekt słuchający na instancję obiektu MainActivity::switchListener.
 * 
 */ public void SetChildsState(ViewGroup v, boolean state)
	{
		
		for (int i = 0; i < v.getChildCount(); i++)
		{
			if (v.getChildAt(i) instanceof ViewGroup) SetChildsState((ViewGroup) v.getChildAt(i), state);
			else v.getChildAt(i).setEnabled(state);
		}
	}
    
/*! \brief Zdarzenie wywoływane przy tworzeniu aktywności.
 *  \param [in] savedInstanceState Parametry zapisanej wcześniej instancji.
 * 
 *  Ustala nowy obiekt słuchający na instancję obiektu MainActivity::switchListener.
 * 
 */ @Override protected void onCreate(Bundle savedInstanceState)
    {

    	super.onCreate(savedInstanceState);
    	
    	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    	
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