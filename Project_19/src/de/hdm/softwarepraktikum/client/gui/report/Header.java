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
/**
 * Die <code>Header</code>-Klasse ist der Kopfbereich des Kontaktverwaltungstools. 
 * Der Header wird über alle Seiten des Tools gleich angezeigt. 
 * 
 * @author Vincent Wengert
 * @version 1.0
 */

public class Header extends HorizontalPanel {
				
		private Person p = CurrentReportPerson.getPerson();
		
		private HorizontalPanel homeButtonPanel = new HorizontalPanel();
		private VerticalPanel PersonPanel = new VerticalPanel();
		private HorizontalPanel topPanel = new HorizontalPanel();
		
		private Button logoutButton = new Button("Logout");
		private Button editorButton = new Button ("Editor");
		private Button reportGeneratorButton = new Button("Reportgenerator");
		private Image logo = new Image ();
		private Label PersonLabel = new Label();
		private Anchor contactEditorLink = new Anchor("Kontakt-Editor");
		
		
		/**
		 * Im Konstruktor dieser Klasse werden die Buttons in die Panels
		 * und zu den Buttons die ClickHandler hinzugefügt.
		 */
		public Header() {
			
			this.add(logo);
			logo.setUrl("images/logoSpaceContacts.png");
			
			PersonPanel.add(logoutButton);
			PersonLabel.setText(p.getGmailAddress());
			PersonPanel.add(PersonLabel);
			topPanel.add(homeButtonPanel);
			topPanel.add(PersonPanel);
			topPanel.add(PersonPanel);

			this.add(topPanel);
			
			logoutButton.addClickHandler(new LogoutClickHandler());
			editorButton.addClickHandler(new ContactEditorClickHandler());
			reportGeneratorButton.addClickHandler(new ReportGeneratorClickHandler() );
		}
		
		
		/**
		 * In dieser Methode werden die Desings der Buttons festgelegt. Auch
		 * die Kontakt-Editor und ReportGenerator-Buttons werden zum Kopfbereich
		 * des Kontaktverwaltungstools hinzugefügt. 
		 */
		public void onLoad() {
			
			this.setStylePrimaryName("headerPanel");
			this.setHeight("15vh");
			this.setWidth("100%");
			
			homeButtonPanel.add(editorButton);
			homeButtonPanel.add(reportGeneratorButton);
			homeButtonPanel.setWidth("26vw");

			editorButton.setWidth("15vw");
			editorButton.setHeight("15vh");
		
			reportGeneratorButton.setWidth("15vw");
			reportGeneratorButton.setHeight("15vh");
			
			logoutButton.setHeight("40px");
			
			homeButtonPanel.setStylePrimaryName("homeButtonPanel");
			logoutButton.setStylePrimaryName("logoutButton");
			editorButton.setStylePrimaryName("editorButton");
			reportGeneratorButton.setStylePrimaryName("reportGeneratorButton");
			PersonPanel.setStylePrimaryName("PersonPanel");
			
			PersonPanel.setCellHorizontalAlignment(logoutButton, ALIGN_CENTER);
			homeButtonPanel.setCellHorizontalAlignment(editorButton, ALIGN_LEFT);
			homeButtonPanel.setCellHorizontalAlignment(reportGeneratorButton, ALIGN_RIGHT);
			topPanel.setCellHorizontalAlignment(PersonPanel, ALIGN_RIGHT);
			topPanel.setCellVerticalAlignment(PersonPanel,ALIGN_MIDDLE);
			this.setCellHorizontalAlignment(topPanel, ALIGN_RIGHT);
			this.setCellVerticalAlignment(topPanel, ALIGN_MIDDLE);
			this.setCellVerticalAlignment(logo, ALIGN_MIDDLE);
			
		}
		
		
		/**
		 * Durch ein Klick auf den Kontakt-Editor-Button wird man 
		 * auf die Kontakt-Editor-Seite weitergeleitet.
		 */
		private class ContactEditorClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
			
				contactEditorLink.setHref(GWT.getHostPageBaseURL() + "IT_Projekt_SS18.html");
				Window.open(contactEditorLink.getHref(), "_self", "");
				
			}
		}
		
		
		/**
		 * Durch ein Klick auf den Report-Generator-Editor-Button wird die Report-Generator-Seite
		 * aktualisiert.
		 */
		private class ReportGeneratorClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				
				Window.Location.reload();
				
			}
		}
		
		
		/**
		 * Durch ein Klick auf den Logout-Button wird der Person auf die
		 * Begrüßungsseite weitergeleitet
		 */
		private class LogoutClickHandler  implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				
				p.setLogoutUrl(p.getLogoutUrl());
				Window.open(p.getLogoutUrl(), "_self", "");
			}	
		}
	}


