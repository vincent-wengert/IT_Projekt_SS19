package de.hdm.softwarepraktikum.client.gui.report;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.softwarepraktikum.client.gui.Footer;

/**
 * Diese Klasse bildet die Hauptform des ReportGenerator Clients. Hier werden alle relevanten HTML-Layout Elemente
 * zu einer Form zusammengeführt.
 * 
 * @autor Niklas Öxle
 * @version 1.0
 
 */

public class Reportform {
	
	    private HorizontalPanel menu = new HorizontalPanel();
	
		private HorizontalPanel formHeaderPanel = new HorizontalPanel();
		
		private ReportHeader header = new ReportHeader();
		private Footer footer = new Footer();
		
	
	  public void loadForms() {
		  
		//Divs laden
			RootPanel.get("footer").clear();
			RootPanel.get("Menu").clear();
			RootPanel.get("Navigator").clear();
			RootPanel.get("Header").clear();
			RootPanel.get("Header").add(header);
			
		  
	  }

	
}
