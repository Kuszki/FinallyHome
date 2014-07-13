package com.example.finallyhome;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.IntentService;
import android.content.Intent;

public class ConnectSocket extends IntentService {
    public static Socket socket;
 
    public ConnectSocket() {
        super("ConnectSocket");
    }
 
    @Override
    protected void onHandleIntent(Intent intent) {
        
        
				try {
					InetAddress serverAddr = InetAddress.getByName("192.168.1.23");

					socket = new Socket(serverAddr, 9096);

				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
    }
}


