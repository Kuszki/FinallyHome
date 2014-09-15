package com.kuszki.finallyhome;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;
import android.app.Activity;

public abstract class ServerClient {
	
	protected		Socket 			socket		=	null;
	
	protected		PrintWriter		out			=	null;
	protected		BufferedReader	in			=	null;
	
	protected		String			adress		=	"10.0.0.100";
	protected		int				port		=	9096;
	
	protected		Thread			thread		=	new Thread()
	{
		
		public void run()
		{
			
			try {
				
				socket	=	new Socket(InetAddress.getByName(adress), port);
				
				out		=	new PrintWriter(socket.getOutputStream());
				in		=	new BufferedReader(new InputStreamReader(socket.getInputStream()));

				out.flush();
				
				onConnect();

				while (true) try {
					
						final String msg = in.readLine();
						
						Log.d("connection","recv message: \"" + msg + "\"");
					
						if (msg != null) onRead(msg);
					
				} catch (Exception e){ onError(e); }

			} catch (Exception e){ onError(e); }
			
	    };
	};

	public void Connect(String addr, int port)
	{		
		try {
			thread.start();
		} catch (Exception e){ onError(e); }
	}
  
	public void Disconnect(String addr, int port)
	{
		new Thread(new Runnable()
		{
			public void run()
			{
				try {
					
					socket.close();
					
				} catch (IOException e) {

					e.printStackTrace();
					Log.d("io", "Unknown IO exeption");
					
				}
			}
		}).start();
  	}

	public void Send(String str)
	{
		final String message = str;
  	
		new Thread(new Runnable()
		{
			public void run()
			{
				if (socket.isConnected()){
				
					out.write(message);
					Log.d("connection", "message \"" + message + "\" send");
				
					out.flush();
					
				}
			}
		}).start();
	}
	
	public abstract void onConnect();
	
	public abstract void onDisconnect();
	
	public abstract void onRead(String msg);
	
	public abstract void onError(Exception e);
  
}

