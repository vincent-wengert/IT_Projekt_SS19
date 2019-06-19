package de.hdm.softwarepraktikum.client.gui.report;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.layout.client.Layout.Alignment;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
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

		private Grid selectionGrid = new Grid(2,5);
		
		private Label fromLabel = new Label("Zeitraum von");
		private Label toLabel = new Label("bis");
		private Label storeLabel = new Label("Laden auswählen");
		private DateBox fromDateBox = new DateBox();
		private DateBox toDateBox = new DateBox();
		private ListBox storeListBox = new ListBox();
		private Label groupLabel = new Label("Gruppe auswählen");
		private ListBox groupListBox = new ListBox();
		
		private Button reportperson = new Button("Alle Artikel");
		private Button los = new Button("Los");
		
		private ReportbyGroup rbp;
		
		private ArrayList<Store> allStores;
		private ArrayList<Group> allGroups;
		private ArrayList<Item> filteredItems;
		
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
			selectionGrid.setWidget(0, 3, groupLabel);
			selectionGrid.setWidget(1, 3, groupListBox);
			selectionGrid.setWidget(1, 4, los);
			
			
			
			storeListBox.setWidth("20vw");
			groupListBox.setWidth("20vw");
			
			menu.add(selectionGrid);
//			menu.setCellHorizontalAlignment(selectionGrid, ALIGN_CENTER);
			menu.setWidth("100%");
			menu.setStylePrimaryName("menue");
			menu.add(reportperson);
			
			reportperson.setStylePrimaryName("reportButton");              
			reportperson.addClickHandler(new AddReportpersonClickHandler());
			reportperson.setPixelSize(80, 80);
			
			los.addClickHandler(new getInformationClickHandler());
			
			

			RootPanel.get("Selection").add(formHeaderPanel);
			RootPanel.get("Selection").add(menu);
			menu.setWidth("100%");
			menu.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			
			administration.getAllStores(new GetAllStoresCallback());
			Person p = new Person();
			p.setId(1);
			administration.getAllGroupsByPerson(p, new GetAllGroupCallback());
			
//			RootPanel.get("footer").add(footer);
			
		  
	  }
	  
	  /**
		 * ClickHandler Klasse zum Aufrufen der <code>AllContactReportForm</code>.
		 */
		
		private class getInformationClickHandler implements ClickHandler {

			public void onClick(ClickEvent event) {
				storeListBox.getSelectedItemText();
				fromDateBox.getValue().toString();
				toDateBox.getValue().toString();
				groupListBox.getSelectedItemText();
				
				Group g = new Group();
				reportadministration.createGroupStatisticsReport(g, new AsyncCallback<ItemsByGroupReport>() {
					
					@Override
					public void onSuccess(ItemsByGroupReport result) {
					Notification.show("Report wurde erstellt");
						// TODO Auto-generated method stub
						HTMLReportWriter writer = new HTMLReportWriter();
						writer.process(result);
						HTML content = new HTML(writer.getReportText());
						RootPanel.get("Result").add(content); 	
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						Notification.show(caught.toString());
					}
				});
				
				
			}
		}

		
		 /**
		 * ClickHandler Klasse zum Abfragen der Daten !
		 */
		
		private class AddReportpersonClickHandler implements ClickHandler {

			public void onClick(ClickEvent event) {

				RootPanel.get("Selection").clear();
				rbp = new ReportbyGroup();

				RootPanel.get("Result").add(rbp);
				
				
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
				for(Store store: allStores) {
					storeListBox.addItem(store.getName());
					}
				
				
				//if (ListItemDialog.this.selectedListItem != null) {
					//ListItem li = ListItemDialog.this.selectedListItem;
//				for (Store store : allStores) {
//					if (store.getId() == li.getStoreID()) {
//						int indexToFind = -1;
//
//						for (int s = 0; s < allStores.size(); s++) {
//
//							if (storeListBox.getItemText(s).equals(store.getName())) {
//								indexToFind = s;
//								break;
//							}
//						}
//						storeListBox.setSelectedIndex(indexToFind);
//					}
//				}
			//}
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
				for(Group group: allGroups) {
					groupListBox.addItem(group.getTitle());
					}
				
				
				//if (ListItemDialog.this.selectedListItem != null) {
					//ListItem li = ListItemDialog.this.selectedListItem;
//				for (Store store : allStores) {
//					if (store.getId() == li.getStoreID()) {
//						int indexToFind = -1;
//
//						for (int s = 0; s < allStores.size(); s++) {
//
//							if (storeListBox.getItemText(s).equals(store.getName())) {
//								indexToFind = s;
//								break;
//							}
//						}
//						storeListBox.setSelectedIndex(indexToFind);
//					}
//				}
			//}
		}
	}	
		
		private class GetAllItemsCallback implements AsyncCallback<ArrayList<Item>> {

			@Override
			public void onFailure(Throwable caught) {
				Notification.show(caught.toString());
			}
			
			@Override
			public void onSuccess(ArrayList<Item> result) {
				// TODO Auto-generated method stub
				
				filteredItems = result;

			}
		}
	
}
