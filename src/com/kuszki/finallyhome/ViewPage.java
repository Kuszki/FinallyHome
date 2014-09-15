package com.kuszki.finallyhome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class ViewPage extends Fragment
{
	private	final	int		view;
	
	public ViewPage(int index)
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
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(view, container, false);
        
        if (v.findViewById(R.id.buttonConnect) != null) MainActivity.Context.SetClickListener(v.findViewById(R.id.buttonConnect));
        if (v.findViewById(R.id.buttonSend) != null) MainActivity.Context.SetClickListener(v.findViewById(R.id.buttonSend));
        
        if (v.findViewById(R.id.editAdress) != null) MainActivity.editAdress = (EditText) v.findViewById(R.id.editAdress);
        if (v.findViewById(R.id.editPort) != null) MainActivity.editPort = (EditText) v.findViewById(R.id.editPort);
        if (v.findViewById(R.id.editCommand) != null) MainActivity.editCmd = (EditText) v.findViewById(R.id.editCommand);
        if (v.findViewById(R.id.editLog) != null)
        {
        	MainActivity.editLog = (EditText) v.findViewById(R.id.editLog);
        	MainActivity.editLog.setKeyListener(null);
        }

        return v;
    }
};