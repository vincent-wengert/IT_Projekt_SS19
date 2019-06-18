package de.hdm.softwarepraktikum.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.softwarepraktikum.client.gui.ShoppingListEditor;
import de.hdm.softwarepraktikum.client.gui.report.Reportform;
import de.hdm.softwarepraktikum.shared.LoginAsync;
import de.hdm.softwarepraktikum.shared.bo.Person;

/**
 * Entry-Point-Klasse des Projekts.
 * @author Bruno Herceg & Niklas Oexle
 * @Version 1.0
 *
 */
public class ReportEntry implements EntryPoint {
	
	private LoginAsync loginService = null;

	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		/*
		 * Über diese Methoden werden Instanzen der Asynchronen Interfaces gebildet
		 */
		loginService = ClientsideSettings.getLoginService();
		
		loginService.login(GWT.getHostPageBaseURL()+"ReportGenerator.html" , new loginServiceCallback());

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
			Window.alert("onSucess");
			
//			CurrentPerson.setPerson(p);
//
//			if (p.isLoggedIn()) {
//				if (p.getName() == null) {
//				Anchor shoppingListEditorLink = new Anchor();
//				shoppingListEditorLink.setHref(GWT.getHostPageBaseURL() + "Project_19.html");
//				RootPanel.get("Navigator").setVisible(false);
//				RootPanel.get("Menu").setVisible(false);
//				RootPanel.get("Details").add(new RegistrationForm(shoppingListEditorLink,p));
//		
//
//				}else{
//					ShoppingListEditor editor = new ShoppingListEditor();
//					editor.loadForms();
//					}
//			}else{
//				loadLogin();
//				}	

			}
  
  
	
	/*
	 * Bei fehlerhaftem RPC Callback wird eine Fehlermeldung geworfen
	 */
	@Override
	public void onFailure(Throwable caught) {
		//Notification.show(caught.toString());
		Reportform reportform = new Reportform();
		reportform.loadReportGenerator();
		}
	}

	private void loadlogin() {

	}

}
