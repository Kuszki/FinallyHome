package com.example.finallyhome;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Dictionary;

import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;

public class ConnectSocket {
	
	/* TODO ogarnac sposob komunikacji tego z aktywnosciami
	 * 
	 * tez w sumie juz mam pomysl jak, ale pokminie by bylo
	 * superancko fajnie i z dyni w pizdu. w zasadzie to
	 * w tym stylu jak to teraz jest, to mozna ta klase
	 * instancjowac na legalu wiele razy, a mozna tez 
	 * na spoko zrobic jeden globalny obiekt - bez znaczenia
	 */
	
	// gniazdo
	protected static Socket socket = null;
	
	//dane do polaczenia
	protected static String sAddr = "10.0.0.100";
	protected static int iPort = 9096;
	
	// to beda strumienie na wejscie i wyjscie
	protected static PrintWriter out = null;
	protected static BufferedReader in = null;
	
	// mapka na wartosci naszych pol - klucz String i pola Float
	protected static Dictionary<String, Float> values = null;
	
	// DEBUG, a moze i zostanie na stale
	protected static Activity context = null;
	
	// do przetwarzania instrukcji po odebraniu
	protected static Handler handler = new Handler();
	
	// watek obslugujacy polaczenie
	protected static Thread sockThread = new Thread()
	{
		public void run()
		{
			try {
		    	
	    		socket = new Socket(InetAddress.getByName(sAddr), iPort);
	    		out = new PrintWriter(socket.getOutputStream());
	    		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

	    		out.flush();
	    		
	    	} catch (UnknownHostException e) {
	    		
	    		e.printStackTrace();
	    		Log.d("connection", "Unknown host");
	    		
	    	} catch (IOException e) {
			
				e.printStackTrace();
				Log.d("io", "Unknown IO exeption");
	    	
	    	}
			
			while (socket.isConnected()){
				
				String msg = null;
				
				try {
					
					msg = in.readLine();
					Log.d("connection","recv message: \"" + msg + "\"");
					
				} catch (IOException e) {
					
					e.printStackTrace();
					Log.d("io", "Unknown IO exeption");
					
				}
				
	    		if (msg != null) Parse(msg);
	    		
			}
			
	    };
	};
	
	// konstruktor, on ma zbudowac liste gdyby jej nie bylo
	public ConnectSocket(Activity c)
	{
		
		//if (values.isEmpty()) {
		
		/* TODO trzeba tutaj zrobic liste zmiennych wystepujacych w 
		 * ustawieniach programu
		 * 
		 * ona bedzie dokladnie identyczna jak ta lista na
		 * serwerze. Obczaje sobie, czy da sie jakos fajnie zrobic
		 * jakies zdarzenie typu onModify dla tej mapy, ale jak sie
		 * nie da, to ogarne to inaczej. z tej mapy maja korzystac
		 * wszystkie kontrolki w apce - ona bedzie miala w sobie
		 * totalnie cale info o stanie rzeczy. w sumie, to mozna
		 * zrobic takiego CallBacka, ze nie liste sie bedzie zmieniac
		 * tylko w ramach potwierdzenia sie bedzie pobierac znow z
		 * servera nowe info - niby juz zmodyfikowane. calosc bedzie
		 * globalna i robiona tylko przy pierwszym instancjowaniu
		 */
			
		//}
		
		context = c;
		
	}

	// do polaczenia
    public static void Connect()
    {

    	/* TODO ogarnac motyw zeby sie sam zrobil watek sluchajacy
    	 * 
    	 * a jak juz sie zrobi, to ogarnac by sie sam odrobil jak
    	 * polaczenie pierdyknie, albo ogarnac cos fajnego...
    	 * 
    	 * jak sie naucze wiecej o tym mechanizmie to to zrobie
    	 * na legalu, bo dokumentacja jest calkiem fajnie
    	 * zrobiona i da sie z tego wiele wywnioskowac
    	 */
    	
    	if (!sockThread.isAlive()) sockThread.start();
    	
    	// DEBUG
    	Toast.makeText(context, "polaczony!", Toast.LENGTH_LONG).show();
    	
    }
    
    // do wysylania polecen
    public static void Send(String str)
    {
		final String message = str;
    	
    	new Thread(new Runnable()
    	{
			public void run()
			{
				out.write(message);
				Log.d("connection", "message \"" + message + "\" send");
				
				out.flush();
			}
    	}).start();

    }
    
    protected static void Parse(String msg)
	{ 
		final String command = msg;
	    handler.post(new Runnable() {
			
			@Override
			public void run() {
				Toast.makeText(context, command, Toast.LENGTH_LONG).show();
			}
			
		});
		
	}
}

