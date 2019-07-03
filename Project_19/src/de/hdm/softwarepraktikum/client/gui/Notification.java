package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;


/**
 * Die Klasse <code>ErrorMessage</code>, instanziiert neue Objekte einer Errormessage, welche während der 
 * Laufzeit im Header angezeigt werden. Diese Klasse kann störende Window-alerts ersetzen.
 * 
 * @author VincentWengert
 * @version 1.0
 */

public class Notification {
	

	private static Label notificationLabel = new Label();
	private static boolean isActive=false;
	
	/**
	 * Default Konstruktor
	 */
	public Notification (){
		
	}
	
	
	public static void clear (){
		if(isActive == true) {
			RootPanel.get("Header").remove(RootPanel.get("Header").getWidgetIndex(notificationLabel));
		}
	}
	


	/**
	 * Die Methode show zeigt die Fehlermeldung im Header an, welche nach 10s entfernt wird.
	 * 
	 * @param message Die Nachricht die den Fehler beschreibt wird übergeben.
	 */
	public static void show (String message){
		isActive=true;
		notificationLabel.setText(message);
		notificationLabel.setStyleName("errorLabel");
		RootPanel.get("Header").add(notificationLabel);
		
		//Timer welcher die Anzeigezeit des Fehlers definiert.
		final Timer timer = new Timer() {
			public void run(){
				RootPanel.get("Header").remove(RootPanel.get("Header").getWidgetIndex(notificationLabel));
			}
		};
		//Definert die Sekunden nachdem die Methode aufgerufen wird.
		timer.schedule(5000);
	}
}
