package de.hdm.softwarepraktikum.client.gui.report;

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

import de.hdm.softwarepraktikum.client.gui.Header.HomeClickHandler;
import de.hdm.softwarepraktikum.client.gui.Header.ReportGeneratorClickHandler;



/**
 * Die <code>Header</code>-Klasse ist der Kopfbereich des Reports. 
 * Der Header wird �ber alle Seiten des Tools gleich angezeigt. 
 * 
 * @author Niklas Oexle, Bruno Herceg
 * @version 1.0
 */

public class ReportHeader extends HorizontalPanel {
	
	//private User u = CurrentReportUser.getUser();
	
	
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
