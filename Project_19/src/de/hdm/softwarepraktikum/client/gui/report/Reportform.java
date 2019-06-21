package de.hdm.softwarepraktikum.client.gui.report;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.layout.client.Layout.Alignment;
import com.google.gwt.resources.converter.ElseNodeCreator;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.client.gui.Footer;
import de.hdm.softwarepraktikum.client.gui.ListItemDialog;
import de.hdm.softwarepraktikum.client.gui.Notification;
import de.hdm.softwarepraktikum.shared.ReportGeneratorAsync;
import de.hdm.softwarepraktikum.shared.ShoppingListAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.ListItem;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.bo.Store;
import de.hdm.softwarepraktikum.shared.report.HTMLReportWriter;
import de.hdm.softwarepraktikum.shared.report.ItemsByGroupReport;
import de.hdm.softwarepraktikum.shared.report.ItemsByPersonReport;
import java_cup.internal_error;

/**
 * Diese Klasse bildet die Hauptform des ReportGenerator Clients. Hier werden alle relevanten HTML-Layout Elemente
 * zu einer Form zusammengef�hrt.
 * 
 * @autor Niklas �xle
 * @version 1.0
 
 */

public class Reportform {
	    private ShoppingListAdministrationAsync administration = ClientsideSettings.getShoppinglistAdministration();
	    private ReportGeneratorAsync reportadministration = ClientsideSettings.getReportGenerator();
	    private HorizontalPanel menu = new HorizontalPanel();
		private HorizontalPanel formHeaderPanel = new HorizontalPanel();
		
		private ReportHeader header = new ReportHeader();
		private Footer footer = new Footer();
		
		private Label infoTitleLabel = new Label("Report auswählen");

		private Grid selectionGrid = new Grid(3,5);
		
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
		private Person userPerson = null;
		
		private Timestamp fromDate = null;
		private Timestamp toDate = null;
		
		
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
			
		
			storeListBox.setWidth("15vw");
			groupListBox.setWidth("15vw");
			
			menu.add(selectionGrid);
//			menu.setCellHorizontalAlignment(selectionGrid, ALIGN_CENTER);
			menu.setWidth("100%");
			menu.setStylePrimaryName("menue");
						
			los.addClickHandler(new getInformationClickHandler());
			
			

			RootPanel.get("Selection").add(formHeaderPanel);
			RootPanel.get("Selection").add(menu);
			menu.setWidth("100%");
			menu.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			
			administration.getAllStores(new GetAllStoresCallback());
			Person p = new Person();
			p.setId(1);
			administration.getAllGroupsByPerson(p, new GetAllGroupCallback());
			
	  }
	  
	private void getSelectedValues(){
		
		if(groupListBox.getSelectedItemText() != "") {
		for (Group g : allGroups) {
			if (g.getTitle().equals(groupListBox.getSelectedItemText())) {
				selectedGroup = g;
				Window.alert(selectedGroup.getTitle());
			}
		}
	}else {
		selectedGroup = null;
	}
		
		if (storeListBox.getSelectedItemText() != "") {
		for (Store s : allStores) {
			if (s.getName().equals(storeListBox.getSelectedItemText())) {
				selectedStore = s;
				Window.alert(selectedStore.getName());
			}	
		}
	}else{
		selectedStore = null;
	}
	  }
	
	private Boolean getIntervallDefined() {
		if(fromDateBox.getValue() == null && toDateBox.getValue() == null) {
			return false ;
		}
		else if (fromDateBox.getValue() != null && toDateBox.getValue() == null || fromDateBox.getValue() == null && toDateBox.getValue() != null){
			Notification.show("Bitte wählen sie Start- und Endzeitpunkt aus");
			return false;
		}else if(fromDateBox.getValue() != null && toDateBox.getValue() != null){

			fromDate = new Timestamp(fromDateBox.getValue().getTime());		
			toDate = new Timestamp(toDateBox.getValue().getTime());		
			return true;
		}
		return false;
	}
	  
	  /**
		 * ClickHandler Klasse zum Aufrufen der <code>AllContactReportForm</code>.
		 */
		
		private class getInformationClickHandler implements ClickHandler {

			public void onClick(ClickEvent event) {
				getSelectedValues();
				Window.alert("LOS");
				if(selectedGroup == null && selectedStore == null && getIntervallDefined() == false) {
					Person person = new Person();
					person.setId(1);
					reportadministration.getReportOfPerson(person, selectedStore, selectedGroup, new getPersonOfGroupCallback());
				} else if(groupListBox.getSelectedItemText() != "" && storeListBox.getSelectedItemText() =="" && getIntervallDefined() ==true) {
				reportadministration.getReportOfGroupBetweenDates(userPerson, selectedGroup, selectedStore, fromDate, toDate, new getReportOfGroupCallback());
				}
			}
		}
		
private class getReportOfGroupCallback implements AsyncCallback<ItemsByGroupReport>{

	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		Notification.show("Report konnte nicht geladen werden" + caught.toString());
	}

	@Override
	public void onSuccess(ItemsByGroupReport result) {
		// TODO Auto-generated method stub
		Notification.show("Report mit Intervall und Gruppe wurde erstellt");
		HTMLReportWriter writer = new HTMLReportWriter();
		writer.process(result);
		HTML content = new HTML(writer.getReportText());
		RootPanel.get("Result").clear();
		RootPanel.get("Result").add(content); 
	}
	
	
}

private class getPersonOfGroupCallback implements AsyncCallback<ItemsByPersonReport>{

	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		Notification.show("Report konnte nicht geladen werden" + caught.toString());
	}

	@Override
	public void onSuccess(ItemsByPersonReport result) {
		// TODO Auto-generated method stub
		Notification.show("Report mit Intervall und Gruppe wurde erstellt");
		HTMLReportWriter writer = new HTMLReportWriter();
		writer.process(result);
		HTML content = new HTML(writer.getReportText());
		RootPanel.get("Result").clear();
		RootPanel.get("Result").add(content); 
	}
	
	
}

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
				for(Store store: allStores) {
					storeListBox.addItem(store.getName());
					}
				
				
		}
	}	
		
		
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
				for(Group group: allGroups) {
					groupListBox.addItem(group.getTitle());
					}
			}
		}	
	}
