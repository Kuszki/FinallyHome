package com.kuszki.finallyhome;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;


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
        
        if (v.findViewById(R.id.buttonSend) != null) MainActivity.Context.SetClickListener(v.findViewById(R.id.buttonSend));
        if (v.findViewById(R.id.buttonConnect) != null)
        {
        	MainActivity.buttonConnect = (Button) v.findViewById(R.id.buttonConnect);
        	MainActivity.Context.SetClickListener(v.findViewById(R.id.buttonConnect));
        }
        
        if (v.findViewById(R.id.editSocket) != null) MainActivity.editSocket = (EditText) v.findViewById(R.id.editSocket);
        if (v.findViewById(R.id.editCommand) != null) MainActivity.editCmd = (EditText) v.findViewById(R.id.editCommand);
        if (v.findViewById(R.id.editLog) != null)
        {
        	MainActivity.editLog = (EditText) v.findViewById(R.id.editLog);
        	MainActivity.editLog.setKeyListener(null);
        }

        return v;
    }
};