package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.client.Project_19.CurrentPerson;
import de.hdm.softwarepraktikum.shared.ShoppingListAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Person;

/**
 * Die <code>Header</code>-Klasse ist der Kopfbereich des Shoppinglisttool. 
 * Der Header wird über alle Seiten des Tools gleich angezeigt. 
 * Dieser ermöglicht die Navigation zwischen den Clients <code>ShoppingListEntry</code>
 * und <code>ShoppingListReportEntry</code> und den Logout aus dem System.
 * 
 * @author Vincent Wengert
 * @version 1.0
 */

public class Header extends HorizontalPanel{
		
		private Person currentPerson = CurrentPerson.getPerson();
		private ShoppingListAdministrationAsync administration = ClientsideSettings.getShoppinglistAdministration();
	
	 	private HorizontalPanel homeButtonPanel = new HorizontalPanel();
	 	
	 	private Button editorButton = new Button ("Editor");
	 	private Button reportGeneratorButton = new Button("Reportgenerator");
	 	private Anchor reportGeneratorLink = new Anchor("ReportGenerator");

	 	private MenuItem logout;

	 	/**
	 	 * Im Konstruktor dieser Klasse werden die Buttons in die Panels
	 	 * und zu den Buttons die ClickHandler hinzugefügt.
	 	 */
	 	public Header() {
	 		this.add(homeButtonPanel);


	 		editorButton.addClickHandler(new HomeClickHandler());
	 		reportGeneratorButton.addClickHandler(new ReportGeneratorClickHandler());
	 	}

	 	
	 	/**
	 	 * In dieser Methode werden die Desings der Buttons festgelegt. Auch
	 	 * die ShoppingList-Editor und ReportGenerator-Buttons werden zum Kopfbereich
	 	 * des Shoppinglisttool hinzugefügt. 
	 	 */
	 	
	 	public void onLoad() {
	 		MenuBar menu = new MenuBar();
	 		menu.setAutoOpen(true);
	 		
	 		MenuBar logoutMenu = new MenuBar(true);
	 		logoutMenu.setAnimationEnabled(true);
	 		logoutMenu.addItem("Kontoeinstellungen", new Command() {
	 	         @Override
	 	         public void execute() {
	 	        	 EditPersonDialog epd = new EditPersonDialog();
	 	        	 epd.setHeader(Header.this);
	 	         }
	 	      });
	 		
	 		logoutMenu.addSeparator();
	 		
	 		logoutMenu.addItem("Konto l\u00F6schen", new Command() {
	 			@Override
	 			public void execute() {
	 				if(Window.confirm("Konto wirklich l\u00F6schen?") == true){
	 					if(Window.confirm("Diese Aktion kann nicht r\u00FCckg\u00E4ngig gemacht werden. Sind Sie sicher?") == true) {
	 		 				administration.deletePerson(currentPerson, new AsyncCallback<Void>() {

								@Override
								public void onFailure(Throwable arg0) {
									// TODO Auto-generated method stub
									
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
	 		 * Begrüßungsseite weitergeleitet
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

	 	}
	 	
	 	public MenuItem getLogoutMenu() {
	 		return this.logout;
	 	}
	 	
	 	/**
	 	 * Durch ein Klick auf den ReportGenerator-Button wird man 
	 	 * auf die ReportGenerator-Seite weitergeleitet.
	 	 */
	 	public class ReportGeneratorClickHandler implements ClickHandler {

	 		@Override
	 		public void onClick(ClickEvent event) {
	 			
	 			reportGeneratorLink.setHref(GWT.getHostPageBaseURL()+"ReportGenerator.html");
	 			Window.open(reportGeneratorLink.getHref(), "_self", "");
	 			
	 		}

	 	}
	 	
	 	
	 	/**
	 	 * Durch ein Klick auf den Shoppinglist-Editor-Button wird die Editorseite
	 	 * aktualisiert.
	 	 */
	 	public class HomeClickHandler implements ClickHandler{
	 		
	 		@Override
	 		public void onClick(ClickEvent event) {
	 			Window.Location.reload();
	 		}
	 	}
	 	
	 }
