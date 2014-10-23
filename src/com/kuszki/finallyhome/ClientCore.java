/*! \file
    \brief Plik zawierający implementacje klasy ClientCore.
*/

package com.kuszki.finallyhome;

import java.util.HashMap;
import java.util.Map;

import android.view.View;
import android.widget.Toast;

/*! \brief Klasa główna klienta serwera.
 * 
 *  Odpowiada za komunikację powmiędzy serwerem a klientem. Stanowi rozszerzenie klasy ServerClient.
 * 
 */
public class ClientCore extends ServerClient
{
	
	protected final Map<String, Integer>		valuesSwitch	=	new HashMap<String, Integer>();	//!< Kontener na wartości powiązane z przełącznikami.
	protected final Map<String, Integer>		valuesBar		=	new HashMap<String, Integer>();	//!< Kontener na wartości powiązane z paskami przewijania.
	protected final Map<String, Integer>		valuesLabel		=	new HashMap<String, Integer>();	//!< Kontener na wartości powiązane z etykietami.
	
	protected final Map<Integer, String>		vars			=	new HashMap<Integer, String>();	//!< Mapa wiążąca zmienne wraz z identyfikatorami ich kontrolek.
	
	protected	MainActivity 				context			=	null;							//!< Referencja do głównej aktywności.

	/*! \brief Konstruktor.
	 *  \param [in] activity Referencja do instancji MainActivity.
	 * 
	 *  Wpisuje wartości do wszystkich map.
	 * 
	 */
	public ClientCore(MainActivity activity)
	{
		context = activity;
		
		valuesSwitch.put("porch.light.on", R.id.switchPorchLight);
		valuesSwitch.put("salon.light.on", R.id.switchSalonLight);
		valuesSwitch.put("salon.blinds.on", R.id.switchSalonBlinds);
		valuesSwitch.put("salon.heat.on", R.id.switchSalonHeat);
		valuesSwitch.put("doors.lock", R.id.switchDoors);
		
		valuesBar.put("porch.light.power", R.id.barPorchLight);
		valuesBar.put("salon.light.power", R.id.barSalonLight);
		valuesBar.put("salon.blinds.power", R.id.barSalonBlinds);
		valuesBar.put("salon.heat.set", R.id.barSalonHeat);
		
		valuesLabel.put("salon.heat.set", R.id.labelHeatSetValue);
		valuesLabel.put("salon.heat.current", R.id.labelHeatCurrentValue);
		
		vars.put(R.id.switchPorchLight, "porch.light.on");
		
		vars.put(R.id.barPorchLight, "porch.light.power");
		
		vars.put(R.id.switchDoors, "doors.lock");
		
		vars.put(R.id.switchSalonLight, "salon.light.on");
		vars.put(R.id.switchSalonBlinds, "salon.blinds.on");
		vars.put(R.id.switchSalonHeat, "salon.heat.on");
		
		vars.put(R.id.barSalonLight, "salon.light.power");
		vars.put(R.id.barSalonBlinds, "salon.blinds.power");
		vars.put(R.id.barSalonHeat, "salon.heat.set");
	}
	
    @Override public void onConnect()
	{
		context.runOnUiThread(new Runnable()
		{
			public void run()
			{
				MainActivity.Buttons.get(R.id.buttonConnect).setText("Rozłącz");
				MainActivity.Buttons.get(R.id.buttonSend).setEnabled(true);
				
				context.SetChildsState(MainActivity.Views.get(R.id.layoutSalon), true);
				context.SetChildsState(MainActivity.Views.get(R.id.layoutPorch), true);
			}
		});
		
		Msg("Połączono z serwerem");
		Log(" >> Połączono z serwerem\n");
		
		Send("get *\n");
	}

    @Override public void onDisconnect()
	{
		context.runOnUiThread(new Runnable()
		{
			public void run()
			{
				MainActivity.Buttons.get(R.id.buttonConnect).setText("Połącz");
				MainActivity.Buttons.get(R.id.buttonSend).setEnabled(false);
				
				context.SetChildsState(MainActivity.Views.get(R.id.layoutSalon), false);
				context.SetChildsState(MainActivity.Views.get(R.id.layoutPorch), false);
			}
		});
		
		Msg("Rozłączono z serwerem");
		Log(" >> Rozłączono z serwerem\n");
	}
	
    @Override public void onRead(String msg)
	{
		if (msg.charAt(0) < 127)
		{
			Log(msg);

			for (final String action: msg.trim().split("\n")){
				
				final String[] cmd = action.trim().split(" ");

				if (cmd.length > 0) Interpret(cmd);
				
			}
		}
	}
	
	@Override public void onError(final Exception e)
	{
		Msg(e.getClass().getName() + ": " + e.getMessage());
	}
	
	/*! \brief Zdarzenie wywoływane przy wciśnięciu przycisku "połącz".
	 *  \param [in] view Kontrolka wywołująca zdarzenie.
	 * 
	 *  Przetwarza wpisane parametry połączenia i usiłuje połączyć się z serwerem.
	 * 
	 */
	public void onConnectButtonClick(View view)
	{
		
		if (socket == null) try {
		
			final String[] params = MainActivity.Edits.get(R.id.editSocket).getText().toString().split(":");
			
			Connect(params[0], Integer.parseInt(params[1]));
			
		} catch (Exception e) {
			
			onError(e);
		
		} else Disconnect();

	}
	
	/*! \brief Zdarzenie wywoływane przy wciśnięciu przycisku "wyślij".
	 *  \param [in] view Kontrolka wywołująca zdarzenie.
	 * 
	 *  Pobiera polecenie z pola tekstowego i wysyła je do serwera, a następnie czyści pole tekstowe.
	 * 
	 */
	public void onSendButtonClick(View view)
	{
		if (socket != null)
		{
			
			final String cmd = MainActivity.Edits.get(R.id.editCommand).getText().toString() + "\n";
			
			Send(cmd);
			
			MainActivity.Edits.get(R.id.editCommand).setText("");
			
		}
	}
	
	/*! \brief Zdarzenie wywoływane przy edycji zmiennej ze strony klienta.
	 *  \param [in] id Nazwa zmiennej.
	 *  \param [in] value Nowa wartość zmiennej.
	 * 
	 *  Generuje wiadomość do serwera i wysyła ją.
	 * 
	 */
	public void onChange(int id, int value)
	{
		if (vars.keySet().contains(id)) Send("set " + vars.get(id) + " " + value + "\n");
	}
	
	/*! \brief Zdarzenie wywoływane przy edycji zmiennej ze strony serwera.
	 *  \param [in] var Nazwa zmiennej.
	 *  \param [in] value Nowa wartość zmiennej.
	 * 
	 *  Przeszukuje powiązane ze zmienną kontrolki i ustala ich stan.
	 * 
	 */
	protected void onSetVar(final String var, final Integer value)
	{
		context.runOnUiThread(new Runnable()
		{
			public void run()
			{
				if (valuesSwitch.keySet().contains(var)) MainActivity.Switches.get(valuesSwitch.get(var)).setChecked(value == 1);
		
				if (valuesBar.keySet().contains(var)) MainActivity.Bars.get(valuesBar.get(var)).setProgress(valuesBar.get(var) != R.id.barSalonHeat ? value - 1 : value - 15);
		
				if (valuesLabel.keySet().contains(var)) MainActivity.Labels.get(valuesLabel.get(var)).setText(value.toString() + " °C");
			}
		});
	}
	
	/*! \brief Tworzy wpis w logu konsoli.
	 *  \param [in] msg Wiadomość do wyświetlenia.
	 * 
	 *  Wpisuje do pola konsoli nową wiadomość w wątku UI.
	 * 
	 */
	protected void Log(final String msg)
	{
		context.runOnUiThread(new Runnable()
		{
			public void run()
			{
				MainActivity.Edits.get(R.id.editLog).append(msg);
			}
		});
	}
	
	/*! \brief Tworzy nowe powiadomienie i wyświetla je na ekranie.
	 *  \param [in] msg Wiadomość do wyświetlenia.
	 * 
	 *  Wyświetla tekst używając wątku UI.
	 * 
	 */
	protected void Msg(final String msg)
	{
		context.runOnUiThread(new Runnable()
		{
			public void run()
			{
				Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
			}
		});
	}
	
	/*! \brief Interpretuje polecenie.
	 *  \param [in] msg Kolejne składniki polecenia.
	 * 
	 *  Interpretuje wiadomość używając kolejnych słów z jej treści.
	 * 
	 */
	protected void Interpret(final String[] msg)
	{
		final int count = msg.length - 1;
		
		if (msg[0].contentEquals("set") && count == 2) try {
			
			onSetVar(msg[1], Integer.parseInt(msg[2]));
			
		} catch (Exception e) {
			
			Log("Nieudana interpretacja 'set'");
			
		}
	}
	

}
