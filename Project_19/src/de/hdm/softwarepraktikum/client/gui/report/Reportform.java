package de.hdm.softwarepraktikum.client.gui.report;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.layout.client.Layout.Alignment;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.datepicker.client.DateBox;

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

		private Grid selectionGrid = new Grid(2,3);
		
		private Label fromLabel = new Label("Zeitraum von");
		private Label toLabel = new Label("bis");
		private Label storeLabel = new Label("Laden auswählen");
		private DateBox fromDateBox = new DateBox();
		private DateBox toDateBox = new DateBox();
		private ListBox storeListBox = new ListBox();
		
		private Button reportperson = new Button("Alle Artikel");
		
		private ReportbyPerson rbp;
		
		
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
			
			selectionGrid.setWidget(0, 0, storeLabel);
			selectionGrid.setWidget(1, 0, storeListBox);
			selectionGrid.setWidget(0, 1, fromLabel);
			selectionGrid.setWidget(1, 1, fromDateBox);
			selectionGrid.setWidget(0, 2, toLabel);
			selectionGrid.setWidget(1, 2, toDateBox);
			
			
			storeListBox.setWidth("20vw");
			
			menu.add(selectionGrid);
//			menu.setCellHorizontalAlignment(selectionGrid, ALIGN_CENTER);
			menu.setWidth("100%");
			menu.setStylePrimaryName("menue");
			menu.add(reportperson);
			
			reportperson.setStylePrimaryName("reportButton");
			reportperson.addClickHandler(new AddReportpersonClickHandler());
			reportperson.setPixelSize(80, 80);
			
			

			RootPanel.get("Selection").add(formHeaderPanel);
			RootPanel.get("Selection").add(menu);
			menu.setWidth("100%");
			menu.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			
//			RootPanel.get("footer").add(footer);
			
		  
	  }
	  
	  /**
		 * ClickHandler Klasse zum Aufrufen der <code>AllContactReportForm</code>.
		 */
		
		private class AddReportpersonClickHandler implements ClickHandler {

			public void onClick(ClickEvent event) {

				RootPanel.get("Selection").clear();
				rbp = new ReportbyPerson();

				RootPanel.get("Result").add(rbp);
			}
		}

	
}
