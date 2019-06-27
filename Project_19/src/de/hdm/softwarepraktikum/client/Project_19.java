package de.hdm.softwarepraktikum.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm.softwarepraktikum.client.gui.Notification;
import de.hdm.softwarepraktikum.client.gui.RegistrationForm;
import de.hdm.softwarepraktikum.client.gui.ShoppingListEditor;
import de.hdm.softwarepraktikum.shared.LoginService;
import de.hdm.softwarepraktikum.shared.LoginServiceAsync;
import de.hdm.softwarepraktikum.shared.bo.Person;

/**
 * Entry-Point-Klasse des Editors.
 * @author Vincent Wengert
 * @Version 1.0
 *
 */
public class Project_19 implements EntryPoint {
		 
		private LoginServiceAsync loginService = null;
		
		private Button loginButton = new Button("Login");
		
		private Anchor signInLink = new Anchor("Login");

		private VerticalPanel loginPanel = new VerticalPanel();
		
		private Label loginLabel = new Label("Bitte melde dich mit deinem Google-Account an, um im Shoppinglist Editor....!");


		/**
		 * Da diese Klasse das Interface <code>EntryPoint</code> implementiert,
		 * benstäigen wir die Methode <code>public void onModuleLoad()</code>. Diese
		 * Methode wird zu Beginn des Seitenaufrufs abgerufen
		 */
		public void onModuleLoad() {

			/*
			 * Über diese Methoden werden Instanzen der Asynchronen Interfaces gebildet
			 */
			loginService = ClientsideSettings.getLoginService();
			
			loginService.login(GWT.getHostPageBaseURL()+"Project_19.html" , new loginServiceCallback());
	
		}
		
		
		/**
		 * Bei erfolgreichem RPC callback wird zu Beginn die 
		 * <code>CurrentPerson<code> gesetzt. Anschließend erfolt eine
		 * Abfrage ob die Person bereits im System eingeloggt ist Falls dies zutrifft,
		 * wird die Shoppinglistadministration Maske aufgerufen Falls die nicht zutrifft,
		 * wird die <code>loadLogin()<code> aufgerufen, indem sich die Person am System
		 * anmelden kann
		 */
		private class loginServiceCallback implements AsyncCallback<Person>{

			@Override
			public void onSuccess(Person p) {				
				CurrentPerson.setPerson(p);

				if (p.isLoggedIn()) {
					if (p.getName() == null) {
					Anchor shoppingListEditorLink = new Anchor();
					shoppingListEditorLink.setHref(GWT.getHostPageBaseURL() + "Project_19.html");
					RootPanel.get("Navigator").setVisible(false);
					RootPanel.get("Menu").setVisible(false);
					RootPanel.get("Details").add(new RegistrationForm(shoppingListEditorLink,p));
			

					}else{
						ShoppingListEditor editor = new ShoppingListEditor();
						editor.loadForms();
						}
				}else{
					loadLogin();
					}	

				}
	  
	  
		
		/*
		 * Bei fehlerhaftem RPC Callback wird eine Fehlermeldung geworfen
		 */
		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
			}
		}


		
		/**
		 * Diese Methode wird aufgerufen, falls der User nicht am System eingeloggt ist
		 * In dieser wird die Google LoginMaske über den Button
		 * <code>loginButton </code> aufgerufen
		 */
		private void loadLogin() {
			
			RootPanel.get("Details").setVisible(false);
			RootPanel.get("Navigator").setVisible(false);
			RootPanel.get("Menu").setVisible(false);
			RootPanel.get("container").add(loginPanel);
			
			loginLabel.setStylePrimaryName("loginLabel");
			loginButton.setStylePrimaryName("loginButton");

			loginPanel.setSpacing(10);
			loginPanel.setWidth("100vw");
			loginPanel.add(loginLabel);
			loginPanel.add(loginButton);
			loginPanel.setCellHorizontalAlignment(loginLabel,HasHorizontalAlignment.ALIGN_CENTER);
			loginPanel.setCellHorizontalAlignment(loginButton,HasHorizontalAlignment.ALIGN_CENTER);
			signInLink.setHref(CurrentPerson.getPerson().getLoginUrl());

			/**
			 * Durch einen Klick auf den <code>loginButton</code> wird der User auf die
			 * Google LoginMaske weitergeleitet
			 */
			loginButton.addClickHandler(new LoginClickHandler());
		}
		

		private class LoginClickHandler  implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
			Window.open(signInLink.getHref(), "_self", "");
			
			}
		}
		
		
		/**
		 * Die Klasse <code>CurrentPerson</code> repräsentiert den aktuell am System angemeldeten User.
		 * Da weitere GUI-Klassen das angemeldetet User-Objekt verwenden, muss diese jederzeit aufrufbar sein.
		 */
	  	public static class CurrentPerson {
			
			private static Person p = null;

			public static Person getPerson() {
				return p;
			}

			public static void setPerson(Person p) {
				CurrentPerson.p = p;
			}
		
	  	}
	}	
