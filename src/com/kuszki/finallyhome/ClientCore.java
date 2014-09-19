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
				MainActivity.Edits.get(R.id.editLog).append(" >> Połączono z serwerem\n");
				
				MainActivity.Buttons.get(R.id.buttonConnect).setText("Rozłącz");
				MainActivity.Buttons.get(R.id.buttonSend).setEnabled(true);
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
				MainActivity.Edits.get(R.id.editLog).append(" >> Rozłączono z serwerem\n");
				
				MainActivity.Buttons.get(R.id.buttonConnect).setText("Połącz");
				MainActivity.Buttons.get(R.id.buttonSend).setEnabled(false);
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
						MainActivity.Edits.get(R.id.editLog).append(msg);
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
	}
	
	public void onConnectButtonClick(View view)
	{
		
		if (socket == null) try {
		
			final String[] params = MainActivity.Edits.get(R.id.editSocket).getText().toString().split(":");
			
			Connect(params[0], Integer.parseInt(params[1]));
			
		} catch (Exception e) {
			
			onError(e);
		
		} else Disconnect();

	}
	
	public void onSendButtonClick(View view)
	{
		if (socket != null) try {
			
			final String cmd = MainActivity.Edits.get(R.id.editCommand).getText().toString() + "\n";
			
			Send(cmd);
			
			MainActivity.Edits.get(R.id.editCommand).setText("");
			
		} catch (Exception e) {
			
			onError(e);
		
		}
	}
	
	protected void SetVar(String var, Integer value)
	{
	  	values.put(var, value);
	}
	
	protected void RefreshData()
	{  	
	  	Send("get *");
	}

}
