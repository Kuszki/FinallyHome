package com.example.finallyhome;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
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
Socket socket = null;
boolean connectOn;
String my_set_ip_preference;
String my_set_port_preference;
InputStream inputStream;
byte[] buffer;

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
	public class MyClientTask extends AsyncTask<Void, Void, Void> {
		
		String dstAddress;
		int dstPort;
		String response = "";
		
		MyClientTask(String addr, int port){
			dstAddress = addr;
			dstPort = port;
		}
		
		@Override
		protected Void doInBackground(Void... arg0) {
			
			try {
				socket = new Socket(dstAddress, dstPort);
				
				ByteArrayOutputStream byteArrayOutputStream = 
		                new ByteArrayOutputStream(1024);
				byte[] buffer = new byte[1024];
				
				int bytesRead;
				InputStream inputStream = socket.getInputStream();
				
				/*
				 * notice:
				 * inputStream.read() will block if no data return
				 */
	            while ((bytesRead = inputStream.read(buffer)) != -1){
	                byteArrayOutputStream.write(buffer, 0, bytesRead);
	                response += byteArrayOutputStream.toString("UTF-8");          
	            } 
	                  

			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response = "UnknownHostException: " + e.toString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response = "IOException: " + e.toString();
			}finally{
				if(socket != null){
					try {
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}	
			}
		    connectOn = socket.isBound();
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			textResponse.setText(response);
			super.onPostExecute(result);
		}

}
	
		
/*		public void listenSocket(){
			   try{
			     PrintWriter out = new PrintWriter(socket.getOutputStream(), 
			                 true);
			     BufferedReader in = new BufferedReader(new InputStreamReader(
			                socket.getInputStream()));
			   } catch (UnknownHostException e) {
			     System.out.println("Unknown host: kq6py");
			     System.exit(1);
			   } catch  (IOException e) {
			     System.out.println("No I/O");
			     System.exit(1);
			   }
			} */
	
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
		    	MyClientTask myClientTask = new MyClientTask(
						ipEditText.getText().toString(),
						Integer.parseInt(portEditText.getText().toString()));
				myClientTask.execute();	
		 
				if(connectOn)
					Toast.makeText(MainActivity.this, "Application is connected", Toast.LENGTH_LONG).show();
			
		      break;
		    case R.id.action_disconnect:

		    	try {
					socket.close();		            	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();				
				}
		    	
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
		  
/******************************************************************************/	

}
