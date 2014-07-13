package com.example.finallyhome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class PorchActivity extends Activity implements OnCheckedChangeListener {
	
	ToggleButton DoorButton;
	boolean t = true; // Purpose of the variable t is triggering the LightButton state.
					  // It have to be checked until the start PorchActivity.

	public final static String EXTRA_MESSAGE = "room";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.porch);
		
		DoorButton = (ToggleButton) findViewById(R.id.toggleButton_door);		
		DoorButton.setChecked(t); // Set the ToggleButton state.
		
	}


	public void PorchLight (View view) {
		Intent intent = new Intent (this, LightActivity.class);
		String message = "Porch";
    	intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);
	}
	
	@Override
	public void onCheckedChanged (CompoundButton compoundButton, boolean b) {
		

	}

}
