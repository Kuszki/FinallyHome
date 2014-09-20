package com.kuszki.finallyhome;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public abstract class ServerClient {
	
	protected		Socket 			socket		=	null;
	
	protected		PrintWriter		out			=	null;
	protected		BufferedReader	in			=	null;
	
	protected		SockThread		thread		=	null;
	
	protected final	String			prompt		=	"\r\n$: ";
	
	protected class	SockThread extends Thread
	{
		
		private			boolean	bContinue = true;
		
		private final	String	sAddr;
		private final	int		uPort;
		
		public SockThread(String adress, int port)
		{
			sAddr = adress;
			uPort = port;
		}
		
		public void run()
		{
			
			try {
				
				socket	=	new Socket(InetAddress.getByName(sAddr), uPort);
				
				out		=	new PrintWriter(socket.getOutputStream());
				in		=	new BufferedReader(new InputStreamReader(socket.getInputStream()));

				out.flush();
				
				onConnect();

				while (bContinue) try {
					
						char[] buf = new char[512];
					
						if (in.read(buf) > 0) 
						{

							final String msg = (new String(buf)).replace(prompt, "");
					
							if (!msg.isEmpty()) onRead(msg);
						
						} else bContinue = false;
					
				} catch (Exception e) {

					bContinue = false;
					
				}
				
				onDisconnect();

			} catch (Exception e) {
				
				onError(e);
				
			}
			
	    };
	};

	public void Connect(String addr, int port)
	{		
		if (thread == null)
		{
			thread = new SockThread(addr, port);
			
			thread.start();
		}
	}
  
	public void Disconnect()
	{
		new Thread()
		{
			public synchronized void run()
			{
				try {
					
					socket.close();
					
				} catch (Exception e) {
					
					onError(e);
					
				} finally {
					
					socket = null;
					thread = null;
					
				}
			}
		}.start();
  	}

	public void Send(final String message)
	{
		new Thread()
		{
			public synchronized void run()
			{
				if (socket != null){
				
					out.write(message);

					out.flush();
					
					if (out.checkError()) Disconnect();
					
				}
			}
		}.start();
	}
	
	public abstract void onConnect();
	
	public abstract void onDisconnect();
	
	public abstract void onRead(String msg);
	
	public abstract void onError(Exception e);
  
}

