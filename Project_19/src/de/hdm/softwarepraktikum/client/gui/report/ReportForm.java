package de.hdm.softwarepraktikum.client.gui.report;


import java.sql.Timestamp;
import java.util.ArrayList;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.client.ReportEntry.CurrentReportPerson;
import de.hdm.softwarepraktikum.client.gui.Footer;
import de.hdm.softwarepraktikum.client.gui.Notification;
import de.hdm.softwarepraktikum.shared.ReportGeneratorAsync;
import de.hdm.softwarepraktikum.shared.ShoppingListAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.bo.Store;
import de.hdm.softwarepraktikum.shared.report.HTMLReportWriter;
import de.hdm.softwarepraktikum.shared.report.ItemsByGroupReport;
import de.hdm.softwarepraktikum.shared.report.ItemsByPersonReport;


/**
 * Diese Klasse bildet die Hauptform des ReportGenerator Clients. Hier werden
 * alle relevanten HTML-Layout Elemente zu einer Form zusammengef�hrt.
 * 
 * @autor Niklas �xle
 * @version 1.0
 * 
 */

public class ReportForm {
	
	private ShoppingListAdministrationAsync administration = ClientsideSettings.getShoppinglistAdministration();
	private ReportGeneratorAsync reportadministration = ClientsideSettings.getReportGenerator();
	private HorizontalPanel menu = new HorizontalPanel();
	private HorizontalPanel formHeaderPanel = new HorizontalPanel();

	private ReportHeader header = new ReportHeader();
	private Footer footer = new Footer();

	private Label infoTitleLabel = new Label("Report auswählen");

	private Grid selectionGrid = new Grid(3, 5);

	private Label fromLabel = new Label("Zeitraum von");
	private Label toLabel = new Label("bis");
	private Label storeLabel = new Label("Laden auswählen");
	private Label groupLabel = new Label("Gruppe auswählen");
	private Label personLabel = new Label("Einkäufe auf mich beschränken");

	private DateBox fromDateBox = new DateBox();
	private DateBox toDateBox = new DateBox();
	private ListBox storeListBox = new ListBox();
	private ListBox groupListBox = new ListBox();

	private Button los = new Button("Los");
	private CheckBox personCheckBox = new CheckBox();

	private ArrayList<Store> allStores;
	private ArrayList<Group> allGroups;

	private Group selectedGroup = null;
	private Store selectedStore = null;
	private Person userPerson = CurrentReportPerson.getPerson();

	private Timestamp fromDate = null;
	private Timestamp toDate = null;
	
		
	/**
	 * Durch diese Methode , wird nach  erfolgreichem Login des Users, der Report-
	 * Generator Client aufgerufen
	 * 
	 */

	public void loadReportGenerator() {
		
		
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

		selectionGrid.setWidget(0, 0, groupLabel);
		selectionGrid.setWidget(1, 0, groupListBox);

		selectionGrid.setWidget(0, 1, storeLabel);
		selectionGrid.setWidget(1, 1, storeListBox);

		selectionGrid.setWidget(0, 2, fromLabel);
		selectionGrid.setWidget(1, 2, fromDateBox);

		selectionGrid.setWidget(0, 3, toLabel);
		selectionGrid.setWidget(1, 3, toDateBox);

		selectionGrid.setWidget(2, 0, personLabel);
		selectionGrid.setWidget(2, 1, personCheckBox);

		selectionGrid.setWidget(1, 4, los);
		
		personLabel.setVisible(false);
		personCheckBox.setVisible(false);

		storeListBox.setWidth("20vw");
		storeListBox.setHeight("2.5vw");
		groupListBox.setWidth("20vw");
		groupListBox.setHeight("2.5vw");
		

		menu.add(selectionGrid);
		menu.setWidth("100%");
		menu.setStylePrimaryName("menue");

		los.addClickHandler(new getInformationClickHandler());
		personCheckBox.addClickHandler(new getInformationClickHandler());
		groupListBox.addChangeListener(new GroupValueChangeHandler());

		RootPanel.get("Selection").add(formHeaderPanel);
		RootPanel.get("Selection").add(menu);
		menu.setWidth("100%");
		menu.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		administration.getAllStores(new GetAllStoresCallback());
		
		administration.getAllGroupsByPerson(userPerson, new GetAllGroupCallback());

	}
	
	
	
	/**
	 * Diese Methode wird aufgerufen, um den ausgewaehlten Store und Gruppe fuer den Report auszuwaehlen.
	 * Falls die Auswahl leer ist, wird das jeweilige Objekt auf null gesetzt.
	 * 
	 */
	private void getSelectedValues() {

		if (groupListBox.getSelectedItemText() != "") {
			for (Group g : allGroups) {
				if (g.getTitle().equals(groupListBox.getSelectedItemText())) {
					selectedGroup = g;
					personLabel.setVisible(true);
					personCheckBox.setVisible(true);
				}
			}
		} else {
			personLabel.setVisible(false);
			personCheckBox.setVisible(false);
			selectedGroup = null;
		}

		if (storeListBox.getSelectedItemText() != "") {
			for (Store s : allStores) {
				if (s.getName().equals(storeListBox.getSelectedItemText())) {
					selectedStore = s;
				}
			}
		} else {
			selectedStore = null;
		}
	}

	/**
	 * Diese Methode wird aufgerufen, um die ausgewaehlten Datumsanagben fuer den Report auszuwaehlen.
	 * 
	 * @return : boolean
	 */
	
	private Boolean getIntervallDefined() {
		if (fromDateBox.getValue() == null && toDateBox.getValue() == null) {
			return false;
		} else if (fromDateBox.getValue() != null && toDateBox.getValue() == null
				|| fromDateBox.getValue() == null && toDateBox.getValue() != null) {
			Notification.show("Bitte wählen sie Start- und Endzeitpunkt aus");
			return false;
		} else if (fromDateBox.getValue() != null && toDateBox.getValue() != null) {

			fromDate = new Timestamp(fromDateBox.getValue().getTime());
			toDate = new Timestamp(toDateBox.getValue().getTime());
			return true;
		}
		return false;
	}
	
	/**
	 * Diese Methode wird aufgerufen, um einen Report zu generieren, je nach optional 
	 * ausgew�hlten Parametern.
	 * 
	 */

	private void loadReports() {
		
		getSelectedValues();

		if (getIntervallDefined() == true) {

			// Nur Zeitraum (und Person)
			if (selectedGroup == null && selectedStore == null) {
				Window.alert("alles leer nur Zeitraum");
				reportadministration.getReportOfPersonBetweenDates(userPerson, selectedStore, selectedGroup, fromDate,
						toDate, new getReportOfPersonCallback());
			}

			// Gruppe und Zeitraum
			if (selectedGroup != null && selectedStore == null) {
				Window.alert("Gruppe und Zeitraum");
				reportadministration.getReportOfGroupBetweenDates(personCheckBox.getValue(), userPerson, selectedGroup,
						selectedStore, fromDate, toDate, new getReportOfGroupCallback());
			}

			// Store und Zeitraum (und Person)
			if (selectedGroup == null && selectedStore != null) {
				Window.alert("Store und Zeitraum");
				reportadministration.getReportOfPersonBetweenDates(userPerson, selectedStore, selectedGroup, fromDate,
						toDate, new getReportOfPersonCallback());
			}

			// Nur Gruppe
			if (selectedGroup != null && selectedStore != null) {
				Window.alert("Gruppe und Store und Zeitraum");
				reportadministration.getReportOfGroupBetweenDates(personCheckBox.getValue(), userPerson, selectedGroup,
						selectedStore, fromDate, toDate, new getReportOfGroupCallback());
			}

		} else if (getIntervallDefined() == false) {
			// Alles leer --> dann Person
			if (selectedGroup == null && selectedStore == null) {
				Window.alert("nur Person ohne Gruppe");
				reportadministration.getReportOfPerson(userPerson, selectedStore, selectedGroup,
						new getReportOfPersonCallback());
			}

			// Nur Gruppe
			if (selectedGroup != null && selectedStore == null) {
				Window.alert("nur Person in Gruppe");
				reportadministration.getReportOfGroup(personCheckBox.getValue(), userPerson, selectedGroup,
						selectedStore, new getReportOfGroupCallback());
			}

			// Nur Store (und Person)
			if (selectedGroup == null && selectedStore != null) {
				Window.alert("nur Store");
				reportadministration.getReportOfPerson(userPerson, selectedStore, selectedGroup,
						new getReportOfPersonCallback());
			}

			// Nur Gruppe
			if (selectedGroup != null && selectedStore != null) {
				Window.alert("Gruppe und Store");
				reportadministration.getReportOfGroup(personCheckBox.getValue(), userPerson, selectedGroup,
						selectedStore, new getReportOfGroupCallback());
			}
		}
	}

	
	
	/**
	 * ClickHandler Klasse um Abzufragen ob eine Gruppe selektiert wurde,
	 * um damit eine Chechbox anzuzeigen mit der man seine Ergebnisse auf sich eingrenzen kann
	 */
	private class GroupValueChangeHandler implements ChangeListener {

		@Override
		public void onChange(Widget sender) {
			// TODO Auto-generated method stub
			if (groupListBox.getSelectedItemText()!=""){
				personLabel.setVisible(true);
				personCheckBox.setVisible(true);
			}else {
				personLabel.setVisible(false);
				personCheckBox.setVisible(false);
			}
		}	
	}
	
	/**
	 * ClickHandler Klasse zum Aufrufen der loadReports() Methode.
	 */

	private class getInformationClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			loadReports();
		}
		
	}
	
	
	/**
	  * Diese innere Klasse wird als Callback f�r das Laden des ItemsByGroupReport ben�tigt.
	  */

	private class getReportOfGroupCallback implements AsyncCallback<ItemsByGroupReport> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Notification.show("Report konnte nicht geladen werden" + caught.toString());
		}

		@Override
		public void onSuccess(ItemsByGroupReport result) {
			// TODO Auto-generated method stub
			Notification.show("Report wurde erstellt");
			HTMLReportWriter writer = new HTMLReportWriter();
			writer.process(result);
			HTML content = new HTML(writer.getReportText());
			RootPanel.get("Result").clear();
			RootPanel.get("Result").add(content);
		}

	}
	
	
	
	/**
	  * Diese innere Klasse wird als Callback f�r das Laden des ItemsByPersonReport ben�tigt.
	  */

	private class getReportOfPersonCallback implements AsyncCallback<ItemsByPersonReport> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Notification.show("Report konnte nicht geladen werden" + caught.toString());
		}

		@Override
		public void onSuccess(ItemsByPersonReport result) {
			// TODO Auto-generated method stub
			Notification.show("Report wurde erstellt");
			HTMLReportWriter writer = new HTMLReportWriter();
			writer.process(result);
			HTML content = new HTML(writer.getReportText());
			RootPanel.get("Result").clear();
			RootPanel.get("Result").add(content);
		}

	}

	/**
	  * Diese innere Klasse wird als Callback f�r das Laden der angelegten Stores benoetigt.
	  */
	
	private class GetAllStoresCallback implements AsyncCallback<ArrayList<Store>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(ArrayList<Store> result) {
			// TODO Auto-generated method stub
			allStores = result;
			storeListBox.addItem("");
			for (Store store : allStores) {
				storeListBox.addItem(store.getName());
			}

		}
	}

	
	/**
	  * Diese innere Klasse wird als Callback f�r das Laden der angelegten Stores benoetigt.
	  */
	
	
	private class GetAllGroupCallback implements AsyncCallback<ArrayList<Group>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(ArrayList<Group> result) {
			// TODO Auto-generated method stub
			allGroups = result;
			groupListBox.addItem("");
			for (Group group : allGroups) {
				groupListBox.addItem(group.getTitle());
			}
		}
	}
}
