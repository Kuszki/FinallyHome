package com.example.finallyhome;

// TEST

import java.io.InputStream;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MainActivity extends Activity {

TextView ipEditText;
TextView portEditText;
TextView textResponse;

boolean connectOn, mIsBound;
String my_set_ip_preference;
String my_set_port_preference;
InputStream inputStream;
byte[] buffer;

ConnectSocket client = new ConnectSocket(this);

/******************************************************************************/
@Override
protected void onCreate(Bundle savedInstanceState) {
	
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);	
	
	ipEditText = (TextView)findViewById(R.id.ipSettings_edittext);
	portEditText = (TextView)findViewById(R.id.portSettings_edittext);
	textResponse = (TextView)findViewById(R.id.response);
	 
	loadPref();
}
/******************************************************************************/

	//Open another Activity
	public void OpenPorchActivity (View view) {
		Intent intent = new Intent (this, PorchActivity.class);
		startActivity(intent);
	}
	
	public void OpenSalonActivity (View view) {
		Intent intent = new Intent (this, SalonActivity.class);
		startActivity(intent);
	}
	
	public void OpenToiletActivity (View view) {
		Intent intent = new Intent (this, ToiletActivity.class);
		startActivity(intent);
	}
	
	public void OpenKitchenActivity (View view) {
		Intent intent = new Intent (this, KitchenActivity.class);
		startActivity(intent);
	}

	/******************************************************************************/

		@Override
		 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  // TODO Auto-generated method stub
		  //super.onActivityResult(requestCode, resultCode, data);
		  
		  /*
		   * To make it simple, always re-load Preference setting.
		   */
		  
		  loadPref();
		 }
		

/******************************************************************************/	
	 
			private void loadPref(){
				  SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
				  
				  my_set_ip_preference = mySharedPreferences.getString("set_ip_preference", "");
					ipEditText.setText(my_set_ip_preference);
					
				  my_set_port_preference = mySharedPreferences.getString("set_port_preference", "");
				  portEditText.setText(my_set_port_preference);

				 }
			
/******************************************************************************/
		//Create an Action Provider
		  @Override
		  public boolean onCreateOptionsMenu(Menu menu) {
		    MenuInflater inflater = getMenuInflater();
		    inflater.inflate(R.menu.menuconnect, menu);
		    return true;
		  }
		  
		  @Override
		  public boolean onOptionsItemSelected(MenuItem item) {
		    switch (item.getItemId()) {
		    case R.id.action_connect:
		    	//Connect the socket with server
		 
				if(connectOn)
					Toast.makeText(MainActivity.this, "Application is connected", Toast.LENGTH_LONG).show();
			
		      break;
		    case R.id.action_disconnect:
		    	
		    	if(!connectOn)
					Toast.makeText(MainActivity.this, "Application is disconnected", Toast.LENGTH_LONG).show();
		    	break;
		    case R.id.action_settings:
		    	Intent intent = new Intent();
		           intent.setClass(MainActivity.this, SetPreferenceActivity.class);
		           startActivityForResult(intent, 0); 
		    	break;
		    case R.id.action_clear:
		    	textResponse.setText("");
		    	break;
		    }
			return true;     
		  }
		  
}
