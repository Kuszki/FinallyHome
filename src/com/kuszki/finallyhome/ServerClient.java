package com.kuszki.finallyhome;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/*! \brief Klasa implementująca podstawowe mechanizmy połączenia z serwerem.
 * 
 *  Stanowi bazę dla klasy ClientCore.
 * 
 */ public abstract class ServerClient {
	
	protected		Socket 			socket		=	null;		//!< Instancja gniazda.
	
	protected		PrintWriter		out			=	null;		//!< Strumień wyjścia.
	protected		BufferedReader	in			=	null;		//!< Bufor wejścia.
	
	protected		SockThread		thread		=	null;		//!< Wątek słuchający połączenia.
	
	protected final	String			prompt		=	"\r\n$: ";	//!< Znak zachęty, używany do wycięcia go z wiadomości odczytywanych z serwera.
	
/*! \brief Klasa wątku socketa.
 * 
 *  Zawiera implementacje wątku obsługującego połączenie i nasluchiwanie odbioru danych.
 * 
 */ protected class	SockThread extends Thread
	{
		
		private			boolean	bContinue = true;	//!< Zmienna decydująca o kontynuacji nasłuchiwania.
		
		private final	String	sAddr;				//!< Adres serwera.
		private final	int		uPort;				//!< Numer portu.
		
	/*! \brief Konstruktor.
	 *  \param [in] adress Adres do połączenia.
	 *  \param [in] port Port połączenia.
	 * 
	 *  Ustala dane połączenia.
	 * 
	 */ public SockThread(String adress, int port)
		{
			sAddr = adress;
			uPort = port;
		}
		
	/*! \brief Procedura wątku.
	 * 
	 *  Parsuje wiadomość i przekazuje ją do interpreteraUruchamiana wraz z rozpoczęciem pracy wątku. Przeznaczona do odbierania i przekazywania do parsera wiadomości.
	 * 
	 */ public void run()
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
				
				socket = null;
				
				onDisconnect();

			} catch (Exception e) {
				
				onError(e);
				
			}
			
	    };
	};

/*! \brief Metoda nawiązująca połączenie.
 *  \param [in] addr Adres połączenia.
 *  \param [in] port Port połączenia.
 * 
 *  Tworzy mowy wątek słuchający o ile nie utworzono jeszcze takiego.
 * 
 */ public void Connect(String addr, int port)
	{		
		if (socket == null)
		{
			thread = new SockThread(addr, port);
			
			thread.start();
		}
	}
  
/*! \brief Zrywa połączenie.
 * 
 *  Kończy połączenie i zeruje wszystkie wykorzystywane w nim obiekty.
 * 
 */ public void Disconnect()
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

/*! \brief Wysyła wiadomość do serwera.
 *  \param [in] message Wiadomość do wysłania.
 *  \warning Znak nowej linii należy dodać ręcznie.
 *  \note Wątek wymusza synchronizację.
 * 
 *  Wysyła do serwera wiadomość o ile połączenie zostało nawiązane i jest aktywne.
 * 
 */ public void Send(final String message)
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
	
/*! \brief Zdarzenie wywoływane przy połączeniu.
 * 
 *  Ustala odpowiedni stan widgetów i wyświetla powiadomienie o połączeniu.
 * 
 */ public abstract void onConnect();
	
/*! \brief Zdarzenie wywoływane przy rozłączeniu.
 * 
 *  Ustawia odpowiedni stan widgetów i wyświatla komunikat o rozłączeniu.
 * 
 */ public abstract void onDisconnect();
	
/*! \brief Zdarzenie wywoływane przy odebraniu wiadomości.
 *  \param [in] msg Odebrana wiadomość.
 * 
 *  Parsuje wiadomość i przekazuje ją do interpretera.
 * 
 */ public abstract void onRead(String msg);
	
/*! \brief Wyświetla komunikat o wyjątku.
 *  \param [in] e Złapany wyjątek.
 * 
 *  Tworzy powiadomienie o wyłapanym wyjątku i wyświetla je na ekranie w wątku UI.
 * 
 */ public abstract void onError(Exception e);
  
}

