package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppingListAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Person;

/**
 * Die Klasse <code>NewGroupForm</code> ist eine Form die verschiedene Methoden und Widgets zur Erstellung
 * einer neuen <code>Group</code> bietet.
 * 
 * @author VincentWengert
 * @version 1.0
 */

public class NewGroupForm extends VerticalPanel {

	ShoppingListAdministrationAsync administration = ClientsideSettings.getShoppinglistAdministration();
	
	private HorizontalPanel formHeaderPanel = new HorizontalPanel();
	private HorizontalPanel bottomButtonsPanel = new HorizontalPanel();

	private Label infoTitleLabel = new Label("Neue Gruppe erstellen");
	private Label groupNameLabel = new Label("Name der Gruppe");
	private Label groupMembersLabel = new Label("Mitglieder");
	private Label addedGroupMembersLabel = new Label("Mitglieder der Gruppe");


	private TextBox groupNameBox = new TextBox();

	private Button confirmButton = new Button("\u2714");
	private Button cancelButton = new Button("\u2716");
	private Grid groupGrid = new Grid(3, 3);

	private NewGroupForm newGroupForm;

	public NewGroupForm() {

		confirmButton.addClickHandler(new CreateClickHandler());
		bottomButtonsPanel.add(confirmButton);

		cancelButton.addClickHandler(new CancelClickHandler());
		bottomButtonsPanel.add(cancelButton);
	}
	
	
	/**
	 * In dieser Methode werden die Widgets der Form hinzugefügt.
	 * Außerdem findet hier die Formatierungen der Widgets statt.
	 */
	public void onLoad() {

		this.setWidth("100%");
		//groupNameLabel.setStylePrimaryName("textLabel");
		formHeaderPanel.setStylePrimaryName("formHeaderPanel");
		infoTitleLabel.setStylePrimaryName("infoTitleLabel");
		bottomButtonsPanel.setStylePrimaryName("bottomButtonsPanel");
		cancelButton.setStylePrimaryName("cancelButton");
		confirmButton.setStylePrimaryName("confirmButton");

		formHeaderPanel.setHeight("8vh");
		formHeaderPanel.setWidth("100%");
		cancelButton.setPixelSize(130, 40);
		confirmButton.setPixelSize(130, 40);

		formHeaderPanel.add(infoTitleLabel);
		formHeaderPanel.setCellVerticalAlignment(infoTitleLabel, ALIGN_BOTTOM);

		bottomButtonsPanel.setSpacing(20);

		this.add(formHeaderPanel);
		this.add(groupGrid);
		this.setCellHorizontalAlignment(groupGrid, ALIGN_CENTER);

		groupNameBox.setMaxLength(10);
	
		groupGrid.setCellSpacing(10);
		groupGrid.setWidget(0, 0, groupNameLabel);
		groupGrid.setWidget(0, 1, groupNameBox);
		groupGrid.setWidget(1, 0, groupMembersLabel);
		
		SearchFormPersons sfp = new SearchFormPersons();
		groupGrid.setWidget(1, 1, sfp);
		groupGrid.setWidget(2, 0, addedGroupMembersLabel);
		
		this.add(bottomButtonsPanel);
		this.setCellHorizontalAlignment(bottomButtonsPanel, ALIGN_CENTER);
	}
	
	/**
	 * Methode um die aktuelle <code>NewGroupForm</code> Instanz zu setzen.
	 * 
	 * @param NewGroupForm die zu setzende <code>Group</code> Objekt.
	 */
	public void setNewGroupForm(NewGroupForm newGroupForm) {

		this.newGroupForm = newGroupForm;
	}
	
	/**
	 * ***************************************************************************
	 * ABSCHNITT der ClickHandler
	 * ***************************************************************************
	 */

	/**
	 * Hiermit wird der Erstellvorgang eines neuer Gruppe abbgebrochen.
	 */
	private class CancelClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("Details").clear();
		}
	}

	
	/**
	 * Sobald die Textfelder für den Namen der Gruppe ausgefüllt wurden, wird
	 * ein neuer <code>Group</code> nach dem Klicken des Bestätigungsbutton erstellt.
	 */
	private class CreateClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
		RootPanel.get("Details").clear();
		}
	}
	
	
	
	private class SearchFormPersons extends VerticalPanel {

		private Grid searchGridPersons = new Grid(1, 3);

	
		private Button addButton = new Button("\u271A");
		private Button cancelButton = new Button("\u2716");
		
		private MultiWordSuggestOracle searchbar = new MultiWordSuggestOracle();
		private SuggestBox searchTextBox = new SuggestBox(searchbar);

	@SuppressWarnings("deprecation")
	public void onLoad() {
		

		
		
	
		searchTextBox.setSize("300px", "27px");
		cancelButton.setPixelSize(30, 30);
		searchTextBox.getElement().setPropertyString("placeholder", "Mitgliedernamen eingeben...");
		cancelButton.setStylePrimaryName("cancelSearchButton");	
		addButton.setStylePrimaryName("addPersonButton");
		
		searchGridPersons.setWidget(0, 0, searchTextBox);
		searchGridPersons.setWidget(0, 1, addButton);
		searchGridPersons.setWidget(0, 2, cancelButton);
		
		cancelButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				administration.createPerson("Thomas.muller@gmx.de", "Hans Muller", new CreatePersonCallback());
//				administration.createPerson("hans.wurst@gmx.de", "Hans Wurst", new CreatePersonCallback());
//				administration.createPerson("Mark.muster@gmx.de", "Mark Muster", new CreatePersonCallback());
			}
		});
		
		this.add(searchGridPersons);
	}
}
	
	/**
	 * Hiermit kann <code>Item</code> Objekt geloscht werden und aus der 
	 *  <code>AllItemsCelllist</code> Instanz entfernt werden.
	 */
	private class CreatePersonCallback implements AsyncCallback<Person> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Person konnte leider nicht erstellt werden:\n" + caught.toString());
		}

		@Override
		public void onSuccess(Person person) {
			//add item to cellist
			//aicl.updateCellList();
			Notification.show("Person wurde erstellt");

		}
	}
}

