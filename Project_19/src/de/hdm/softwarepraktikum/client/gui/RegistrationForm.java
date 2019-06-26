package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppingListAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Person;

	/**
	 * Die Klasse <code>RegistrationForm</code> ist eine Form zur Registrierung des Nutzers im ShoppingListSystem.
	 * 
	 * @author VincentWengert
	 * @version 1.0
	 * @see de.hdm.softwarePraktikum.client.gui.Project19
	 * @see de.hdm.softwarePraktikum.client.gui.Report

	 */

	public class RegistrationForm extends VerticalPanel{
		
		private Person p;

		private ShoppingListAdministrationAsync administration = ClientsideSettings.getShoppinglistAdministration();
		
		private HorizontalPanel formHeaderPanel = new HorizontalPanel();
		private HorizontalPanel bottomButtonsPanel = new HorizontalPanel ();

		private Label infoTitleLabel = new Label("Herzlich Willkommen zu Shoppinglist24!");
		private Label registerLabel = new Label("Bitte registrieren Sie sich:");

		private Label lastNameLabel = new Label ("Name");
		private Label firstNameLabel = new Label ("Vorname");

		private DynamicTextbox firstNameTextBox = new DynamicTextbox();
		private DynamicTextbox lastNameTextBox = new DynamicTextbox();
		
		private Button registerButton = new Button("Registrieren");
		private Button cancelButton = new Button ("Abbrechen");
		private Grid grid = new Grid (2,2);

		private Anchor destinationUrl = new Anchor();

		/**
		 * Konstruktor der Klasse.
		 * @param destLink, Der URL String für die spätere Weiteleitung auf den richtigen Client,
		 * 		  p, der eingeloggte Person aus dem jeweiligen EntryPoint
		 */

		public RegistrationForm(Anchor destUrl, Person p) {
			
			this.destinationUrl =destUrl;
			this.p=p;
			
			registerButton.addClickHandler(new RegistrationClickHandler());
			bottomButtonsPanel.add(registerButton);

			cancelButton.addClickHandler(new CancleClickHandler());
			bottomButtonsPanel.add(cancelButton);

		}
		
		/**
		 * Hier werden alle Widget dem Panel hinzugefügt und angeordnet.
		 * Dise Methode wird aufgerufen, sobald ein Objekt der Klasse <code>RegistrationForm</code> intanziert wird.
		 */
		public void onLoad() {
			
			this.setWidth("100%");
			firstNameLabel.setStylePrimaryName("textLabel");
			lastNameLabel.setStylePrimaryName("textLabel");
			registerLabel.setStylePrimaryName("regiterLabel");
			formHeaderPanel.setStylePrimaryName("formHeaderPanel");
			infoTitleLabel.setStylePrimaryName("infoTitleLabel");
			bottomButtonsPanel.setStylePrimaryName("bottomButtonsPanel");
			cancelButton.setStylePrimaryName("cancelButton");
			registerButton.setStylePrimaryName("confirmButton");
			
			formHeaderPanel.setHeight("8vh");
			formHeaderPanel.setWidth("100%");
			cancelButton.setPixelSize(130, 40);
			registerButton.setPixelSize(130, 40);
			
			formHeaderPanel.add(infoTitleLabel);
			formHeaderPanel.setCellVerticalAlignment(infoTitleLabel, ALIGN_BOTTOM);
			bottomButtonsPanel.setSpacing(20);
			
			firstNameTextBox.setMaxLength(30);
			lastNameTextBox.setMaxLength(30);
			
			firstNameTextBox.setlabelText("Vorname");
			lastNameTextBox.setlabelText("Name");

			grid.setCellSpacing(10);
			

			this.add(formHeaderPanel);
			this.add(registerLabel);

			grid.setWidget(0, 0,  lastNameLabel );
			grid.setWidget(0, 1, lastNameTextBox );
			grid.setWidget(1, 0,  firstNameLabel);
			grid.setWidget(1, 1, firstNameTextBox);
			
			this.add(grid);
			this.add(bottomButtonsPanel);	

			
		}
		
		
		/**
		 * ***************************************************************************
		 * ABSCHNITT der ClickHandler
		 * ***************************************************************************
		 */
		
		
		/**
		 * Mit diesem ClickHandler wird der Erstellvorgang einer neuen Person abbgebrochen. 
		 */
		private class CancleClickHandler implements ClickHandler{

			@Override
			public void onClick(ClickEvent event) {
				RootPanel.get("Details").clear();
				Window.open(p.getLogoutUrl(), "_self", "");	

			}
			
		}
		
		
		/**
		 * Sobald die Textfelder für den Namen und Vornamen ausgefüllt wurden, wird der Name der Person gesetzt. 
		 */
		private class RegistrationClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				if(firstNameTextBox.getText() != "" && lastNameTextBox.getText() != "") {
					String uName= lastNameTextBox.getText() +" "+ firstNameTextBox.getText();
					Window.alert(uName);
					p.setName(uName);
					administration.updatePerson(p, new SavePersonCallback());
				}else {
					Window.alert("Bitte füllen sie beide Felder aus");
				}
				}
			}
		

		/**
		 * ***************************************************************************
		 * ABSCHNITT der Privaten Klassen
		 * ***************************************************************************
		 */
		
		/**
		 * 
		 * Mit der privaten Klasse <code>DynamicTextbox</code> werden dynamische Textboxen definiert,
		 * die zusätzliche Attribute besitzen.
		 * 
		 */

		private class DynamicTextbox extends TextBox {

			String labelText;
	
			public String getlabelText() {
				return labelText;
			}
			public void setlabelText(String labelText) {
				this.labelText = labelText;
			}
			
		}

		/**
		 * ***************************************************************************
		 * ABSCHNITT der Callbacks
		 * ***************************************************************************
		 */

		
		/**
		 * Implementierung der SaveUserCallback Klasse. In dieser wird nach erfolgreicher Speicherung des Users,
		 * der Editor geladen und dem User angezeigt.
		 * 
		 */
			
			private class SavePersonCallback implements AsyncCallback<Void>{
				
				@Override
				public void onFailure(Throwable caught) {
					Notification.show(caught.toString());
				}

				@Override
				public void onSuccess(Void p) {
				Window.alert(destinationUrl.getHref());
				Window.open(destinationUrl.getHref(), "_self", "");

			}			
		}
			
}
