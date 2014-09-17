package com.kuszki.finallyhome;

import java.util.HashMap;
import java.util.Map;

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
				MainActivity.editLog.append(" >> Połączono z serwerem\n");
				
				MainActivity.buttonConnect.setText("Rozłącz");
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
				MainActivity.editLog.append(" >> Rozłączono z serwerem\n");
				
				MainActivity.buttonConnect.setText("Połącz");
			}
		});
	}
	
	@Override
	public void onRead(final String msg)
	{
		context.runOnUiThread(new Runnable()
		{
			public void run()
			{
				if (msg.charAt(0) < 127){
				
					try {
						MainActivity.editLog.append(msg);
					} catch (Exception e) {
						onError(e);
					}
					
				}
				
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
		
		if (socket == null) try {
		
			final String[] params = MainActivity.editSocket.getText().toString().split(":");
			
			Connect(params[0], Integer.parseInt(params[1]));
			
		} catch (Exception e) {
			
			onError(e);
		
		} else Disconnect();

	}
	
	public void onSendButtonClick(View view)
	{
		if (socket != null) try {
			
			final String cmd = MainActivity.editCmd.getText().toString() + "\n";
			
			Send(cmd);
			
			MainActivity.editCmd.setText("");
			
		} catch (Exception e) {
			
			onError(e);
		
		}
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
