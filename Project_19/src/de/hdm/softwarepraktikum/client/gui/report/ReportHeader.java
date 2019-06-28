package de.hdm.softwarepraktikum.client.gui.report;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.gui.EditPersonDialog;
import de.hdm.softwarepraktikum.client.gui.Header;
import de.hdm.softwarepraktikum.client.gui.Notification;
import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.client.ReportEntry.CurrentReportPerson;
import de.hdm.softwarepraktikum.client.gui.Header.HomeClickHandler;
import de.hdm.softwarepraktikum.client.gui.Header.ReportGeneratorClickHandler;
import de.hdm.softwarepraktikum.shared.ShoppingListAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Person;



/**
 * Die <code>Header</code>-Klasse ist der Kopfbereich des Reports. 
 * Der Header wird ueber alle Seiten des Tools gleich angezeigt. 
 * 
 * @author Niklas Oexle, Bruno Herceg
 * @version 1.0
 */

public class ReportHeader extends HorizontalPanel {
	
	private Person currentPerson = CurrentReportPerson.getPerson();
	private ShoppingListAdministrationAsync administration = ClientsideSettings.getShoppinglistAdministration();
	
	private HorizontalPanel homeButtonPanel = new HorizontalPanel();
 	private HorizontalPanel topPanel = new HorizontalPanel();

 	private Button editorButton = new Button ("Editor");
 	private Button reportGeneratorButton = new Button("Reportgenerator");
	private Anchor shoppingListEditorLink = new Anchor("Shoppinglisst-Editor");

	private MenuItem logout;

 	/**
 	 * Im Konstruktor dieser Klasse werden die Buttons in die Panels
 	 * und zu den Buttons die ClickHandler hinzugefuegt.
 	 */
 	public ReportHeader() {
 		topPanel.add(homeButtonPanel);
 		this.add(topPanel);

 		editorButton.addClickHandler(new HomeClickHandler());
 		reportGeneratorButton.addClickHandler(new ReportGeneratorClickHandler() );
 	}

 	
 	/**
 	 * In dieser Methode werden die Desings der Buttons festgelegt. Auch
 	 * die ShoppingList-Editor und ReportGenerator-Buttons werden zum Kopfbereich
 	 * des Shoppinglisttool hinzugefuegt. 
 	 */
 	public void onLoad() {
 		MenuBar menu = new MenuBar();
 		
 		MenuBar logoutMenu = new MenuBar(true);
 		logoutMenu.setAnimationEnabled(true);
 		
 		logoutMenu.addItem("Konto l\u00F6schen", new Command() {
 			@Override
 			public void execute() {
 				if(Window.confirm("Konto wirklich l\u00F6schen?") == true){
 					if(Window.confirm("Diese Aktion kann nicht r\u00FCckg\u00E4ngig gemacht werden. Sind Sie sicher?") == true) {
 		 				administration.deletePerson(currentPerson, new AsyncCallback<Void>() {

							@Override
							public void onFailure(Throwable arg0) {
								Notification.show(arg0.toString());
							}

							@Override
							public void onSuccess(Void arg0) {
								// TODO Auto-generated method stub
								currentPerson.setLogoutUrl(currentPerson.getLogoutUrl());
		 		 				Window.open(currentPerson.getLogoutUrl(), "_self", "");
							}
 		 				});
 					}
 				}
 			}
 		});
 		
 		logoutMenu.addSeparator();
 		
 		
 		/**
 		 * Durch ein Klick auf den Logout-Button wird der User auf die
 		 * Begrue�ungsseite weitergeleitet
 		 */
 		
 		logoutMenu.addItem("Logout", new Command() {
 			@Override
 			public void execute() {
 				Notification.show("Logout");
 				
 				currentPerson.setLogoutUrl(currentPerson.getLogoutUrl());
 				Window.open(currentPerson.getLogoutUrl(), "_self", "");
 				
 			}
 		});
 		
 		logout  = new MenuItem("Angemeldet als: " + currentPerson.getName(), logoutMenu);
 		
 		menu.addItem(logout);
 		
 		this.setStylePrimaryName("Header");
 		
 		homeButtonPanel.add(editorButton);
 		homeButtonPanel.add(reportGeneratorButton);
 		homeButtonPanel.add(menu);
 		
 		
 		homeButtonPanel.setStylePrimaryName("homeButtonPanel");
 		editorButton.setStylePrimaryName("editorButton");
 		reportGeneratorButton.setStylePrimaryName("reportGeneratorButton");
 		
 		menu.setStylePrimaryName("menuBar");

 		homeButtonPanel.setCellHorizontalAlignment(editorButton, ALIGN_LEFT);
 		homeButtonPanel.setCellHorizontalAlignment(reportGeneratorButton, ALIGN_RIGHT);
 		this.setCellHorizontalAlignment(topPanel, ALIGN_RIGHT);
 		this.setCellVerticalAlignment(topPanel, ALIGN_MIDDLE);
 	
 	}
 	
 	public MenuItem getLogoutMenu() {
 		return this.logout;
 		
 	}
 	
 	
 	/**
 	 * Durch ein Klick auf den ReportGenerator-Button wird man 
 	 * auf die ReportGenerator-Seite weitergeleitet.
 	 */
 	private class ReportGeneratorClickHandler implements ClickHandler {

 		@Override
 		public void onClick(ClickEvent event) {
 			Window.Location.reload();
 		}

 	}
 	
 	
 	/**
 	 * Durch ein Klick auf den Shoppinglist-Editor-Button wird die Editorseite
 	 * aktualisiert.
 	 */
 	private class HomeClickHandler implements ClickHandler{
 		
 		@Override
 		public void onClick(ClickEvent event) {
 			shoppingListEditorLink.setHref(GWT.getHostPageBaseURL() + "Project_19.html");
			Window.open(shoppingListEditorLink.getHref(), "_self", "");
 		}
 	}
 	

}
