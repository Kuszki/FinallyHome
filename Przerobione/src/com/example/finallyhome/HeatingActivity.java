package com.example.finallyhome;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HeatingActivity extends Activity {

	TextView on_off;
	EditText set_temperature;
	Button ok;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.heating);
		
		on_off = (TextView) findViewById(R.id.textView_heating4);
		on_off.setTextColor(Color.RED);
		set_temperature = (EditText) findViewById(R.id.editText_heating);
		ok = (Button) findViewById(R.id.buttonOkHeating);
		
		ok.setOnClickListener(new OnClickListener () {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String actual_temperature = set_temperature.getText().toString();
				on_off.setText(actual_temperature);				
			}
			
		});
				
	}

}
