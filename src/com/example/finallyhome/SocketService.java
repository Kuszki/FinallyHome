package com.example.finallyhome;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class SocketService extends Service {

	public static final String SERVERIP = "192.168.1.23"; //your computer IP address should be written here
	public static final int SERVERPORT = 9096;
	PrintWriter out;
	Socket socket;
	InetAddress serverAddr;
	
	@Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
		System.out.println("I am in Ibinder onBind method");
        return myBinder;
    }

	private final IBinder myBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public SocketService getService() {
            return SocketService.this;
        }
    }
    
    public Socket getSocket() {
        return socket;
    }
	
    @Override
    public void onCreate() {
        super.onCreate();
        socket = new Socket();
        System.out.println("I am in on create");    
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId){
    	super.onStartCommand(intent, flags, startId);
        System.out.println("I am in on start");

 //       IpAddress = intent.getStringExtra("IPADDRESS");

        new Thread(new SocketThread()).start();
        return START_STICKY;
    }

    public class SocketThread implements Runnable {

        public void run() {
            try {
                InetAddress serverAddr = InetAddress.getByName(SERVERIP);
                socket = new Socket(serverAddr, SERVERPORT);
            } catch (SocketException e1) {
                e1.printStackTrace();
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
    
	class connectSocket implements Runnable {

        @Override
        public void run() {


            try { 
                 //here you must put your computer's IP address.
                serverAddr = InetAddress.getByName(SERVERIP);
                Log.e("TCP Client", "C: Connecting...");
                //create a socket to make the connection with the server

                socket = new Socket(serverAddr, SERVERPORT);

                 try {


                     //send the message to the server
                     out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);


                     Log.e("TCP Client", "C: Sent.");

                     Log.e("TCP Client", "C: Done.");


                    } 
                 catch (Exception e) {

                     Log.e("TCP", "S: Error", e);

                 }
            } catch (Exception e) {

                Log.e("TCP", "C: Error", e);

            }

        }

    }
    
    
    
    public void IsBoundable(){
        Toast.makeText(this,"I bind like butter", Toast.LENGTH_LONG).show();
    }
	
	public void sendMessage(String message){
        if (out != null && !out.checkError()) {
            System.out.println("in sendMessage"+message);
            out.println(message);
            out.flush();
        }
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
          try {
              socket.close();
          } catch (Exception e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
          socket = null;
      }

	
}