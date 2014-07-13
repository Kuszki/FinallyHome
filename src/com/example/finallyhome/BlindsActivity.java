package com.example.finallyhome;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class BlindsActivity extends Activity {

	SeekBar sb;
	TextView text, text2, textvalue, room;
	Switch sw;
	Button ok;
	boolean sw_state;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blinds);
		
		sb = (SeekBar) findViewById(R.id.SeekBar_blinds_state);
		sw = (Switch) findViewById(R.id.switch_blinds);
		ok = (Button) findViewById(R.id.buttonOkBlinds);
		textvalue = (TextView) findViewById(R.id.textView_blinds_state2);
		text = (TextView) findViewById(R.id.textView_blinds);
		text2 = (TextView) findViewById(R.id.textView_blinds0);
		room = (TextView) findViewById(R.id.textView_room);
		
		// Actually progress
		int progress = sb.getProgress(); // check the actually progress
				
		if(progress == 0) {
			blindsClosed();
			sw.setChecked(false);
		}
				
		else if(progress == 100) {
			blindsOpen();
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
				        	blindsOpen();
				        	sb.setProgress(100);
				        } else {
				            // The switch is disabled
				        	blindsClosed();
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
					blindsClosed();
					sw.setChecked(false);
				}
						
				else if(progress == 100) {
					blindsOpen();
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
				String value = textvalue.getText().toString();
				room.setText(value);
			}
					
		});
				
	}

	public void textVisible () {
		text.setVisibility(View.VISIBLE);
		text2.setVisibility(View.VISIBLE);
	}
	
	public void blindsOpen () {
		
		textvalue.setText("Open");
    	text.setVisibility(View.INVISIBLE);
		text2.setVisibility(View.INVISIBLE);
	}
	
	public void blindsClosed () {
		textvalue.setText("Closed");
    	text.setVisibility(View.INVISIBLE);
		text2.setVisibility(View.INVISIBLE);
	}

}
