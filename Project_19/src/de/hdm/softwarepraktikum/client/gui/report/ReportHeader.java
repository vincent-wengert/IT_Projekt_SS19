package de.hdm.softwarepraktikum.client.gui.report;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.gui.Notification;
import de.hdm.softwarepraktikum.client.ReportEntry.CurrentReportPerson;
import de.hdm.softwarepraktikum.client.gui.Header.HomeClickHandler;
import de.hdm.softwarepraktikum.client.gui.Header.ReportGeneratorClickHandler;
import de.hdm.softwarepraktikum.shared.bo.Person;



/**
 * Die <code>Header</code>-Klasse ist der Kopfbereich des Reports. 
 * Der Header wird �ber alle Seiten des Tools gleich angezeigt. 
 * 
 * @author Niklas Oexle, Bruno Herceg
 * @version 1.0
 */

public class ReportHeader extends HorizontalPanel {
	
	private Person currentPerson = CurrentReportPerson.getPerson();
	
	
	private HorizontalPanel homeButtonPanel = new HorizontalPanel();
 	private VerticalPanel personPanel = new VerticalPanel();
 	private HorizontalPanel topPanel = new HorizontalPanel();
 	

 	private Button editorButton = new Button ("Editor");
 	private Button reportGeneratorButton = new Button("Reportgenerator");
 	private Image logo = new Image ();
 	private Label userLabel = new Label();
	private Anchor shoppingListEditorLink = new Anchor("Shoppinglisst-Editor");


 	/**
 	 * Im Konstruktor dieser Klasse werden die Buttons in die Panels
 	 * und zu den Buttons die ClickHandler hinzugefügt.
 	 */
 	public ReportHeader() {
 		
 		personPanel.add(userLabel);
 		topPanel.add(homeButtonPanel);
 		topPanel.add(personPanel);
 		topPanel.add(personPanel);
 		
 		this.add(topPanel);

 		editorButton.addClickHandler(new HomeClickHandler());
 		reportGeneratorButton.addClickHandler(new ReportGeneratorClickHandler() );
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
 		logoutMenu.addItem("Angemeldet als: " + currentPerson.getGmail(), new Command() {
 	         @Override
 	         public void execute() {
 	        	 //TODO was geschiehen soll wenn current person ausgew�hlt wird
 	        	 
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
 		
 		menu.addItem(new MenuItem("Editor", new Command() {
 			@Override
 			public void execute() {
 				shoppingListEditorLink.setHref(GWT.getHostPageBaseURL()+"Project_19.html");
	 			Window.open(shoppingListEditorLink.getHref(), "_self", "");
 			}
 		}));

 		menu.addSeparator();
 		
 		menu.addItem(new MenuItem("Report Generator", new Command() {
 			@Override
 			public void execute() {
 				Window.Location.reload();
 			}
 		}));

 		menu.addSeparator();
 		
 		menu.addItem(new MenuItem(currentPerson.getName(), logoutMenu));
 		
 		this.add(menu);

 		this.setStylePrimaryName("headerPanel");
 		this.setHeight("10vh");
 		this.setWidth("100%");
 		
// 		homeButtonPanel.add(editorButton);
// 		homeButtonPanel.add(reportGeneratorButton);
 		homeButtonPanel.setWidth("26vw");
 		
 		editorButton.setWidth("15vw");
 		editorButton.setHeight("10vh");

 		
 		reportGeneratorButton.setWidth("15vw");
 		reportGeneratorButton.setHeight("10vh");
 		
 		homeButtonPanel.setStylePrimaryName("homeButtonPanel");
 		editorButton.setStylePrimaryName("editorButton");
 		reportGeneratorButton.setStylePrimaryName("reportGeneratorButton");
 		personPanel.setStylePrimaryName("userPanel");

 		homeButtonPanel.setCellHorizontalAlignment(editorButton, ALIGN_LEFT);
 		homeButtonPanel.setCellHorizontalAlignment(reportGeneratorButton, ALIGN_RIGHT);
 		topPanel.setCellHorizontalAlignment(personPanel, ALIGN_RIGHT);
 		topPanel.setCellVerticalAlignment(personPanel,ALIGN_MIDDLE);
 		this.setCellHorizontalAlignment(topPanel, ALIGN_RIGHT);
 		this.setCellVerticalAlignment(topPanel, ALIGN_MIDDLE);
 		this.setCellVerticalAlignment(logo, ALIGN_MIDDLE);
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
