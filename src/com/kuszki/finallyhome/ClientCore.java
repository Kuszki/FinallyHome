package com.kuszki.finallyhome;

import java.util.HashMap;
import java.util.Map;

import android.view.View;
import android.widget.Toast;

public class ClientCore extends ServerClient
{
	
	protected	Map<String, Integer>		valuesSwitch	=	new HashMap<String, Integer>();
	protected	Map<String, Integer>		valuesBar		=	new HashMap<String, Integer>();
	protected	Map<String, Integer>		valuesLabel		=	new HashMap<String, Integer>();
	
	protected	Map<Integer, String>		vars			=	new HashMap<Integer, String>();
	
	protected	MainActivity 				context			=	null;

	public ClientCore(MainActivity activity)
	{
		context = activity;
		
		valuesSwitch.put("porch.light.on", R.id.switchPorchLight);
		valuesSwitch.put("salon.light.on", R.id.switchSalonLight);
		valuesSwitch.put("salon.blinds.on", R.id.switchSalonBlinds);
		valuesSwitch.put("salon.heat.on", R.id.switchSalonHeat);
		valuesSwitch.put("doors.lock", R.id.switchDoors);
		
		valuesBar.put("porch.light.power", R.id.barPorchLight);
		valuesBar.put("salon.light.power", R.id.barSalonLight);
		valuesBar.put("salon.blinds.power", R.id.barSalonBlinds);
		valuesBar.put("salon.heat.set", R.id.barSalonHeat);
		
		valuesLabel.put("salon.heat.set", R.id.labelHeatSetValue);
		valuesLabel.put("salon.heat.current", R.id.labelHeatCurrentValue);
		
		vars.put(R.id.switchPorchLight, "porch.light.on");
		
		vars.put(R.id.barPorchLight, "porch.light.power");
		
		vars.put(R.id.switchDoors, "doors.lock");
		
		vars.put(R.id.switchSalonLight, "salon.light.on");
		vars.put(R.id.switchSalonBlinds, "salon.blinds.on");
		vars.put(R.id.switchSalonHeat, "salon.heat.on");
		
		vars.put(R.id.barSalonLight, "salon.light.power");
		vars.put(R.id.barSalonBlinds, "salon.blinds.power");
		vars.put(R.id.barSalonHeat, "salon.heat.set");
	}
	
	@Override
	public void onConnect()
	{
		context.runOnUiThread(new Runnable()
		{
			public void run()
			{
				MainActivity.Buttons.get(R.id.buttonConnect).setText("Rozłącz");
				MainActivity.Buttons.get(R.id.buttonSend).setEnabled(true);
				
				context.SetChildsState(MainActivity.Views.get(R.id.layoutSalon), true);
				context.SetChildsState(MainActivity.Views.get(R.id.layoutPorch), true);
			}
		});
		
		Msg("Połączono z serwerem");
		Log(" >> Połączono z serwerem\n");
		
		Send("get *\n");
	}

	@Override
	public void onDisconnect()
	{
		context.runOnUiThread(new Runnable()
		{
			public void run()
			{
				MainActivity.Buttons.get(R.id.buttonConnect).setText("Połącz");
				MainActivity.Buttons.get(R.id.buttonSend).setEnabled(false);
				
				context.SetChildsState(MainActivity.Views.get(R.id.layoutSalon), false);
				context.SetChildsState(MainActivity.Views.get(R.id.layoutPorch), false);
			}
		});
		
		Msg("Rozłączono z serwerem");
		Log(" >> Rozłączono z serwerem\n");
	}
	
	@Override
	public void onRead(String msg)
	{
		if (msg.charAt(0) < 127)
		{
			Log(msg);

			for (final String action: msg.trim().split("\n")){
				
				final String[] cmd = action.trim().split(" ");

				if (cmd.length > 0) Interpret(cmd);
				
			}
		}
	}
	
	@Override
	public void onError(final Exception e)
	{
		Msg(e.getClass().getName() + ": " + e.getMessage());
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
		if (socket != null)
		{
			
			final String cmd = MainActivity.Edits.get(R.id.editCommand).getText().toString() + "\n";
			
			Send(cmd);
			
			MainActivity.Edits.get(R.id.editCommand).setText("");
			
		}
	}
	
	public void onChange(int id, int value)
	{
		if (vars.keySet().contains(id)) Send("set " + vars.get(id) + " " + (id != R.id.barSalonHeat ? value : (value + 15)) + "\n");
	}
	
	protected void onSetVar(final String var, final Integer value)
	{
		context.runOnUiThread(new Runnable()
		{
			public void run()
			{
				if (valuesSwitch.keySet().contains(var)) MainActivity.Switches.get(valuesSwitch.get(var)).setChecked(value == 1);
		
				if (valuesBar.keySet().contains(var)) MainActivity.Bars.get(valuesBar.get(var)).setProgress(valuesBar.get(var) != R.id.barSalonHeat ? value : value - 15);
		
				if (valuesLabel.keySet().contains(var)) MainActivity.Labels.get(valuesLabel.get(var)).setText(value.toString() + " °C");
			}
		});
	}
	
	protected void Log(final String msg)
	{
		context.runOnUiThread(new Runnable()
		{
			public void run()
			{
				MainActivity.Edits.get(R.id.editLog).append(msg);
			}
		});
	}
	
	protected void Msg(final String msg)
	{
		context.runOnUiThread(new Runnable()
		{
			public void run()
			{
				Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
			}
		});
	}
	
	protected void Interpret(final String[] msg)
	{
		final int count = msg.length - 1;
		
		if (msg[0].contentEquals("set") && count == 2) try {
			
			onSetVar(msg[1], Integer.parseInt(msg[2]));
			
		} catch (Exception e) {
			
			Log("Nieudana interpretacja 'set'");
			
		}
	}
	

}
