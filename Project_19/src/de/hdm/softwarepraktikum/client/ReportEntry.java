package de.hdm.softwarepraktikum.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.gui.Notification;
import de.hdm.softwarepraktikum.client.gui.RegistrationForm;
import de.hdm.softwarepraktikum.client.gui.ShoppingListEditor;
import de.hdm.softwarepraktikum.client.gui.report.Reportform;
import de.hdm.softwarepraktikum.shared.LoginServiceAsync;
import de.hdm.softwarepraktikum.shared.bo.Person;

/**
 * Entry-Point-Klasse des Projekts.
 * @author Bruno Herceg & Niklas Oexle
 * @Version 1.0
 *
 */
public class ReportEntry implements EntryPoint {
	
	private LoginServiceAsync loginService = null;
	private Button loginButton = new Button("Login");
	private Anchor signInLink = new Anchor("Login");
	private VerticalPanel loginPanel = new VerticalPanel();


	private Label loginLabel = new Label(
			"Bitte melde dich mit deinem Google-Account an, um diesen Reportgenerator nutzen zu konnen");
	
	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		/*
		 * Über diese Methoden werden Instanzen der Asynchronen Interfaces gebildet
		 */
		loginService = ClientsideSettings.getLoginService();
		loginService.login(GWT.getHostPageBaseURL()+"ReportGenerator.html", new loginServiceCallback());	
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
					CurrentReportPerson.setPerson(p);
						if (p.isLoggedIn()) {
							if (p.getName() == null) {
								Anchor reportGeneratorLink = new Anchor("ReportGenerator");
								reportGeneratorLink.setHref(GWT.getHostPageBaseURL()+"ReportGenerator.html");
								
								RootPanel.get("Result").add(new RegistrationForm(reportGeneratorLink,p));
							}else{
								Reportform reportGenForm = new Reportform();
								reportGenForm.loadReportGenerator();
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
					Notification.show("EntryFehler" +caught.toString());
				}
			}



			/**
			 * Diese Methode wird aufgerufen, falls der User nicht am System eingeloggt ist
			 * In dieser wird die Google LoginMaske �ber den Button
			 * <code>loginButton </code> aufgerufen.
			 */
			private void loadLogin() {
				RootPanel.get("Selection").setVisible(false);
				RootPanel.get("Result").setVisible(false);
				RootPanel.get().add(loginPanel);
				
				loginLabel.setStylePrimaryName("loginLabel");
				loginButton.setStylePrimaryName("loginButton");

				loginPanel.setSpacing(10);
				loginPanel.setWidth("100vw");
				loginPanel.add(loginLabel);
				loginPanel.add(loginButton);
				loginPanel.setCellHorizontalAlignment(loginLabel,HasHorizontalAlignment.ALIGN_CENTER);
				loginPanel.setCellHorizontalAlignment(loginButton,HasHorizontalAlignment.ALIGN_CENTER);
				signInLink.setHref(CurrentReportPerson.getPerson().getLoginUrl());

				loginButton.addClickHandler((ClickHandler) new LoginClickHandler());
			}

	/**
	 * Durch einen Klick auf den loginButton wird der User auf die
	 * Google LoginMaske weitergeleitet
	 */
	private class LoginClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			Window.open(signInLink.getHref(), "_self", "");
		}
	}
  
	
/**
 * Die Klasse <code>CurrentPerson</code> repräsentiert den aktuell am System angemeldeten User.
 * Da weitere GUI-Klassen das angemeldetet Person-Objekt verwenden, muss diese jederzeit aufrufbar sein.
 */
public static class CurrentReportPerson {

	private static Person p = null;

	public static Person getPerson() {
		return p;
	}

	public static void setPerson(Person p) {
		CurrentReportPerson.p = p;
	}
}

}
