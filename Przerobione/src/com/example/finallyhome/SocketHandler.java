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

public class SocketHandler {
	
	static Socket socket;
	static String dstAddress;
	static String dstPort;
	
	protected class ThreadHandler extends AsyncTask<Void, Void, Void>
	{
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
	
	public void listenSocket(){
		   try{
		     PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		   } catch (UnknownHostException e) {
		     System.out.println("Unknown host: kq6py");
		     System.exit(1);
		   } catch  (IOException e) {
		     System.out.println("No I/O");
		     System.exit(1);
		   }
		}
	
	SocketHandler(){
		
	}
	
	SocketHandler(String addr, int port){
		dstAddress = addr;
		dstPort = port;
	}
}
/******************************************************************************/
    