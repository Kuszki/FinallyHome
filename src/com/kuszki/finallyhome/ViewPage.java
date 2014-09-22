package com.kuszki.finallyhome;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;


/*! \brief Implementacja klasy pojedyńczej strony.
 * 
 *  Klasa stanowi wzór dla strony layoutu umieszczonej w pagerze.
 * 
 */ public class ViewPage extends Fragment
{
	private	final	int			view;	//!< Indeks strony.
	
/*! \brief Zdarzenie wywoływane przy odznaczeniu zakładki.
 *  \param [in] index Numer strony.
 * 
 *  Ustala indeks strony na podstawie numeru zasobu.
 * 
 */ public ViewPage(int index)
	{
		switch (index)
		{
			case 0:
				view = R.layout.fragment_link;
			break;
			case 1:
				view = R.layout.fragment_salon;
			break;
			case 2:
				view = R.layout.fragment_porch;
			break;
			default: view = 0;
		}
	}
	
/*! \brief Zdarzenie wywoływane przy tworzeniu strony.
 *  \param [in] inflater Inflater layoutu.
 *  \param [in] container Kontener nadrzędny.
 *  \param [in] savedInstanceState Zapisany stan instancji.
 * 
 *  Inicjuje wszystkie kontenery na widgety w instancji klasy MainActivity.
 * 
 */ @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(view, container, false);
        
        View tmp = null;
        
        // ----------------------------------------- BUTTONS -----------------------------------------
        if ((tmp = v.findViewById(R.id.buttonSend)) != null) AddButton((Button) tmp);
        if ((tmp = v.findViewById(R.id.buttonConnect)) != null) AddButton((Button) tmp);
        // ----------------------------------------- SWITCHES ----------------------------------------
        if ((tmp = v.findViewById(R.id.switchPorchLight)) != null) AddSwitch((Switch) tmp);
        if ((tmp = v.findViewById(R.id.switchDoors)) != null) AddSwitch((Switch) tmp);
        if ((tmp = v.findViewById(R.id.switchSalonLight)) != null) AddSwitch((Switch) tmp);
        if ((tmp = v.findViewById(R.id.switchSalonHeat)) != null) AddSwitch((Switch) tmp);
        if ((tmp = v.findViewById(R.id.switchSalonBlinds)) != null) AddSwitch((Switch) tmp);
        if ((tmp = v.findViewById(R.id.switchConsole)) != null) AddSwitch((Switch) tmp);
        // ------------------------------------------ EDITS ------------------------------------------
        if ((tmp = v.findViewById(R.id.editSocket)) != null) AddEdit((EditText) tmp);
        if ((tmp = v.findViewById(R.id.editCommand)) != null) AddEdit((EditText) tmp);
        if ((tmp = v.findViewById(R.id.editLog)) != null) AddEdit((EditText) tmp);
        // ------------------------------------------ VIEWS ------------------------------------------
        if ((tmp = v.findViewById(R.id.layoutConsole)) != null) AddView((ViewGroup) tmp);
        if ((tmp = v.findViewById(R.id.layoutSalon)) != null) AddView((ViewGroup) tmp);
        if ((tmp = v.findViewById(R.id.layoutPorch)) != null) AddView((ViewGroup) tmp);
        // ------------------------------------------ LABELS -----------------------------------------
        if ((tmp = v.findViewById(R.id.labelHeatCurrentValue)) != null) AddLabel((TextView) tmp);
        if ((tmp = v.findViewById(R.id.labelHeatSetValue)) != null) AddLabel((TextView) tmp);
        // ------------------------------------------- BARS ------------------------------------------
        if ((tmp = v.findViewById(R.id.barPorchLight)) != null) AddBar((SeekBar) tmp);
        if ((tmp = v.findViewById(R.id.barSalonBlinds)) != null) AddBar((SeekBar) tmp);
        if ((tmp = v.findViewById(R.id.barSalonHeat)) != null) AddBar((SeekBar) tmp);
        if ((tmp = v.findViewById(R.id.barSalonLight)) != null) AddBar((SeekBar) tmp);

        return v;
    }
	
/*! \brief Dodaje nowy przycisk do kontenera MainActivity.
 *  \param [in] v Widget do dodania.
 * 
 *  Dodaje nowy przycisk do kontenera przechowującego widgety i ustala jego podstawowe parametry.
 * 
 */ protected void AddButton(Button v)
	{
		MainActivity.Buttons.put(v.getId(), v);
    	MainActivity.Context.SetClickListener(v);
    	
    	switch (v.getId())
    	{
    		case R.id.buttonSend:
    			v.setEnabled(false);
    		break;
    	}
	}
	
 /*! \brief Dodaje nowy przycisk do kontenera MainActivity.
  *  \param [in] v Widget do dodania.
  * 
  *  Dodaje nowy przycisk do kontenera przechowującego widgety i ustala jego podstawowe parametry.
  * 
  */ protected void AddSwitch(Switch v)
	{
		MainActivity.Switches.put(v.getId(), v);
    	
		if (v.getId() != R.id.switchConsole){
			
			MainActivity.Context.SetClickListener(v);
			
		} else {
			
			MainActivity.Context.SetSwitchListener(v);
			
		}
	}
	
/*! \brief Dodaje nowe pole tekstowe do kontenera MainActivity.
 *  \param [in] v Widget do dodania.
 * 
 *  Dodaje nowe pole tekstowe do kontenera przechowującego widgety i ustala jego podstawowe parametry.
 * 
 */ protected void AddEdit(EditText v)
	{
		MainActivity.Edits.put(v.getId(), v);
		
		switch (v.getId())
    	{
    		case R.id.editCommand:
    			MainActivity.Context.SetEditListener(v);
            	v.setImeActionLabel("Wyślij", KeyEvent.KEYCODE_ENTER);
    		break;
    		case R.id.editLog:
    			v.setKeyListener(null);
    		break;
    	}
	}
	
/*! \brief Dodaje nowy layout do kontenera MainActivity.
 *  \param [in] v Widget do dodania.
 * 
 *  Dodaje nowy layout do kontenera przechowującego widgety i ustala jego podstawowe parametry.
 * 
 */ protected void AddView(ViewGroup v)
	{
		MainActivity.Views.put(v.getId(), v);
		
		if (v.getId() != R.id.layoutConsole) MainActivity.Context.SetChildsState(v, false);
	}
	
/*! \brief Dodaje nową etykietę do kontenera MainActivity.
 *  \param [in] v Widget do dodania.
 * 
 *  Dodaje nową etykietę do kontenera przechowującego widgety i ustala jej podstawowe parametry.
 * 
 */ protected void AddLabel(TextView v)
	{
		MainActivity.Labels.put(v.getId(), v);
	}
	
/*! \brief Dodaje nowy pasek do kontenera MainActivity.
 *  \param [in] v Widget do dodania.
 * 
 *  Dodaje nowy pasek do kontenera przechowującego widgety i ustala jego podstawowe parametry.
 * 
 */ protected void AddBar(SeekBar v)
	{
		MainActivity.Bars.put(v.getId(), v);
		
		MainActivity.Context.SetSeekListener(v);
	}
};