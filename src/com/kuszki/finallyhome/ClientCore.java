package com.kuszki.finallyhome;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

public class ClientCore extends ServerClient
{
	
	protected	Map<String, Integer>		values		=	new HashMap<String, Integer>();
	
	protected	MainActivity 				context		=	null;

	@Override
	public void onConnect()
	{
		context.runOnUiThread(new Runnable()
		{
			public void run()
			{
				Toast.makeText(context, "Połączono z serwerem", Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	public void onDisconnect()
	{
		context.runOnUiThread(new Runnable()
		{
			public void run()
			{
				Toast.makeText(context, "Rozłączono z serwerem", Toast.LENGTH_LONG).show();
			}
		});
	}
	
	@Override
	public void onRead(String msg)
	{
		context.runOnUiThread(new Runnable()
		{
			public void run()
			{
				//final String[] cmd = msg.split(" ");
				//final int params = cmd.length - 1;
					
				//if (cmd[0] == "set") if (params == 2) SetVar(cmd[1], Integer.parseInt(cmd[2]));
						
				//Toast.makeText(context, "parse command: " + cmd[0], Toast.LENGTH_LONG).show();
			}
		});
	}
	
	@Override
	public void onError(final Exception e)
	{
		context.runOnUiThread(new Runnable()
		{
			public void run()
			{
				Toast.makeText(context, e.getClass().getName() + ": " + e.getMessage(), Toast.LENGTH_LONG).show();
			}
		});
	}
	
	public ClientCore(MainActivity activity)
	{
		context = activity;
		
		values.put("porch.light.on", 0);
		values.put("porch.light.power", 0);
		
		values.put("doors.lock", 0);
		
		values.put("salon.light.on", 0);
		values.put("salon.light.power", 0);
		
		values.put("salon.blinds.on", 0);
		values.put("salon.blinds.power", 0);
		
		values.put("salon.heat.on", 0);
		values.put("salon.heat.power", 0);
	}
	
	public void onConnectButtonClick(View view)
	{
		//final String adress = context.editAdress.getText().toString();
		//final int port = Integer.parseInt(context.editPort.getText().toString());
		
		//if (port > 65535 || port < 1024) Toast.makeText(context, "Niepoprawny numer portu", Toast.LENGTH_LONG).show();
		//else link.Connect(adress, port);
		
		//Connect("10.0.0.100", 9096);
		
		Connect("10.0.0.100", 9096);
	}
	
	protected void SetVar(String var, Integer value)
	{
	  	if (values.containsKey(var)) values.put(var, value);
	}
	
	protected void RefreshData()
	{  	
	  	Send("get *");
	}

}
