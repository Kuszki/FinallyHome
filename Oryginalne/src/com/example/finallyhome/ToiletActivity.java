package com.example.finallyhome;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ToiletActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.toilet);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cellar, menu);
		return true;
	}

}
