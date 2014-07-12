package com.example.finallyhome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SalonActivity extends Activity {
	
	public final static String EXTRA_MESSAGE = "room";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.salon);
	}
	
	public void SalonLight (View view) {
		Intent intent = new Intent (this, LightActivity.class);
		String message = "Salon";
    	intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);
	}
	
	public void SalonHeating (View view) {
		Intent intent = new Intent (this, HeatingActivity.class);
		String message = "Salon";
    	intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);
	}
	
	public void SalonBlinds (View view) {
		Intent intent = new Intent (this, BlindsActivity.class);
		startActivity(intent);
	}

}
