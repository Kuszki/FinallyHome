package com.example.finallyhome;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Switch;
import android.widget.TextView;

public class LightActivity extends Activity {

	// The room button is used to select the room you come from
	SeekBar sb;
	TextView text, text2, textvalue, room, response;
	Switch sw;
	Button ok;
	boolean sw_state;
	String message;
	Socket socket;
	PrintWriter printwriter;
	BufferedReader in;
	String value;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// Retrieve information about the origin of the date
		Intent intent = getIntent();
		String messageSalon = intent.getStringExtra(SalonActivity.EXTRA_MESSAGE);
		String messagePorch = intent.getStringExtra(PorchActivity.EXTRA_MESSAGE);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.light);
		
		sb = (SeekBar) findViewById(R.id.SeekBar_light_intensity);
		text = (TextView) findViewById(R.id.textView_light);
		text2 = (TextView) findViewById(R.id.textView2);
		textvalue = (TextView) findViewById(R.id.textView_light_intensity);
		sw = (Switch) findViewById(R.id.switch_light);
		room = (TextView) findViewById(R.id.textView_room);
		ok = (Button) findViewById(R.id.buttonOkLight);
		response = (TextView) findViewById(R.id.textView_response);
		
		if (intent.getStringExtra(SalonActivity.EXTRA_MESSAGE) == "Salon") room.setText(messageSalon);
		else room.setText(messagePorch);
		
		
		// Actually progress
		int progress = sb.getProgress(); // check the actually progress
		
		if(progress == 0) {
			lightOff();
			sw.setChecked(false);
		}
		
		else if(progress == 100) {
			lightOn();
			sw.setChecked(true);
		}
		
		else {
			textvalue.setText("" +progress);
			textVisible ();
		}
		
		
		// Reaction to SWITCH
		sw.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        sw_state = isChecked;
		    	if (isChecked) {
		            // The switch is enabled
		        	lightOn();
		        	sb.setProgress(100);
		        } else {
		            // The switch is disabled
		        	lightOff();
		        	sb.setProgress(0);
		        }
		    }
		});

		// Reaction to SEEKBAR
		sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				
				if(progress == 0) {
					lightOff();
					sw.setChecked(false);
				}
				
				else if(progress == 100) {
					lightOn();
					sw.setChecked(true);
				}
				
				else {
					textvalue.setText("" +progress);
					textVisible ();
				}				
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}			
			
		});
		
		// Send the value of the light intensity
		ok.setOnClickListener(new OnClickListener () {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				value = textvalue.getText().toString();	
				room.setText(value);
				
				SendMessage sendMessageTask = new SendMessage();
				sendMessageTask.execute();
				
			}
			
		});

		
		
	}

/*************************************************************************/
	private class SendMessage extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {

				socket = new Socket("192.168.1.23", 9096); // connect to the server
				printwriter = new PrintWriter(socket.getOutputStream(), true);
				printwriter.write(value); // write the message to output stream

				printwriter.flush();
				printwriter.close();
//				socket.close(); // closing the connection

			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

	}
	
/*************************************************************************/	
	public void lightOn () {
		
		textvalue.setText("ON");
    	text.setVisibility(View.INVISIBLE);
		text2.setVisibility(View.INVISIBLE);
	}
	
	public void lightOff () {
		textvalue.setText("OFF");
    	text.setVisibility(View.INVISIBLE);
		text2.setVisibility(View.INVISIBLE);
	}
	
	public void textVisible () {
		text.setVisibility(View.VISIBLE);
		text2.setVisibility(View.VISIBLE);
	}
	
}