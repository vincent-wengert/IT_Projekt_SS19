package de.hdm.softwarepraktikum.client.gui.report;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import de.hdm.softwarepraktikum.client.gui.Footer;

/**
 * Diese Klasse bildet die Hauptform des ReportGenerator Clients. Hier werden alle relevanten HTML-Layout Elemente
 * zu einer Form zusammengef�hrt.
 * 
 * @autor Niklas �xle
 * @version 1.0
 
 */

public class Reportform {
	
	    private HorizontalPanel menu = new HorizontalPanel();
		private HorizontalPanel formHeaderPanel = new HorizontalPanel();
		
		private ReportHeader header = new ReportHeader();
		private Footer footer = new Footer();
		
		private Label infoTitleLabel = new Label("Report auswählen");

	
	  public void loadReportGenerator() {
		//Divs laden
			RootPanel.get("Selection").clear();
			RootPanel.get("Result").clear();
			RootPanel.get("Header").clear();
			RootPanel.get("footer").clear();
			
			RootPanel.get("Header").add(header);
			
			
			formHeaderPanel.setStylePrimaryName("formHeaderPanel");
			infoTitleLabel.setStylePrimaryName("infoTitleLabel");
			
			
			formHeaderPanel.add(infoTitleLabel);
			formHeaderPanel.setWidth("100%");
			formHeaderPanel.setHeight("8vh");
			
			
			menu.setWidth("100%");
			menu.setStylePrimaryName("menue");


			menu.setSpacing(100);
			
			RootPanel.get("Selection").add(formHeaderPanel);
			RootPanel.get("Selection").add(menu);
			menu.setWidth("100%");
			menu.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			
//			RootPanel.get("footer").add(footer);
			
		  
	  }

	
}
