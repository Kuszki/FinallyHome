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
    
	/* TODO ogarnac motyw czy by tego nie zrobic public
	 * 
	 * moze i by mozna, bo wtedy by bylo mniej metod
	 * do napisania, ale tak bedzie lepsza enkapsulacja
	 * 
	 * to znaczy "niech nikt mi ryja do moich streamow
	 * nie wsadza bo jak pizne to sie naprostuje"
	 */
	
	// to beda strumienie na wejscie i wyjscie
	protected static PrintWriter out = null;
	protected static BufferedReader in = null;
	
	// mapka na wartosci naszych pol - klucz String i pola Float
	protected static Dictionary<String, Float> Values = null;
	
	protected static Context context = null;
	
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
	    };
	};
	
	protected static Thread recvThread = new Thread()
	{
		public void run()
		{
			//if (socket != null) while (socket.isConnected()) {
			while (true){
				
				String msg = null;
				
				try {
					
					msg = in.readLine();
					Log.d("connection","recv message: \"" + msg + "\"");
					
				} catch (IOException e) {
					
					e.printStackTrace();
					Log.d("io", "Unknown IO exeption");
					
				}
				
	    		if (msg != null) {

	    			//Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	    			
	    			/* TODO tutaj ma byc obsluga zdarzen przychodzacych
					 * 
					 * trzeba tutaj zrobic parser i interpreter calego tego syfu
					 * w sumie to to zadani ma hulac caly czas, jak tylko jest
					 * polaczenie nawiazane i ma odbierac wszystko co serwer wysle.
					 * 
					 * TODO ogarnac czy to sie da jakos zatrzymac jak serwer padnie
					 * 
					 * trzeba ogarnac jak to sie zatrzymuje i kontroluje gdyby dajmy
					 * na to server padl. mam juz w sumie plan, ale pomysle jak 
					 * to obadac dokladnie by bylo najfajniej i kozacko
					 */
	    			
	    		}
			}
		};
	};
	
	// konstruktor, on ma zbudowac liste gdyby jej nie bylo
	public ConnectSocket(Context c)
	{
		
		//if (Values.isEmpty()) {
		
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
    public void Connect()
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
    	
    	if (!sockThread.isAlive()){
    		sockThread.start();
    		recvThread.start();
    	}
    	
    	Send("siema szmato");
    	
    	//Toast.makeText(context, "polaczony!", Toast.LENGTH_LONG).show();
    	
    }
    
    // do wysylania polecen
    public void Send(String str)
    {
		final String message = str;
    	
    	new Thread(new Runnable()
    	{
			public void run()
			{
				out.print(message);
				Log.d("connection", "message \"" + message + "\" send");
		    	
		    	out.flush();
			}
    	}).start();

    }
}