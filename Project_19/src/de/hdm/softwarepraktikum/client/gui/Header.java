package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

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

	
	 	private HorizontalPanel homeButtonPanel = new HorizontalPanel();
	 	private VerticalPanel personPanel = new VerticalPanel();
	 	private HorizontalPanel topPanel = new HorizontalPanel();
	 	

	 	private Button editorButton = new Button ("Editor");
	 	private Button reportGeneratorButton = new Button("Reportgenerator");
	 	private Image logo = new Image ();
	 	private Label userLabel = new Label();
	 	private Anchor reportGeneratorLink = new Anchor("ReportGenerator");


	 	/**
	 	 * Im Konstruktor dieser Klasse werden die Buttons in die Panels
	 	 * und zu den Buttons die ClickHandler hinzugefügt.
	 	 */
	 	public Header() {
	 	
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


	 		this.setStylePrimaryName("headerPanel");
	 		this.setHeight("10vh");
	 		this.setWidth("100%");
	 		
	 		homeButtonPanel.add(editorButton);
	 		homeButtonPanel.add(reportGeneratorButton);
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
