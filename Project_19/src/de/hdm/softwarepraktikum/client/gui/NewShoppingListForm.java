package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppingListAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.bo.ShoppingList;
import de.hdm.softwarepraktikum.shared.bo.Store;
/**
 * Die Klasse <code>NewGroupForm</code> ist eine Form die verschiedene Methoden und Widgets zur Erstellung
 * einer neuen <code>Gruppe</code> bietet.
 * 
 * @author VincentWengert
 * @version 1.0
 */

public class NewShoppingListForm extends VerticalPanel {
	

	private ShoppingListAdministrationAsync administration = ClientsideSettings.getShoppinglistAdministration();
	private CustomTreeModel ctm = null; 
	
	private HorizontalPanel formHeaderPanel = new HorizontalPanel();
	private HorizontalPanel bottomButtonsPanel = new HorizontalPanel();

	private Label infoTitleLabel = new Label("Neue Einkaufsliste erstellen");
	private Label shoppinglistNameLabel = new Label("Name der Einkaufsliste");
	private Label groupNameLabel = new Label("Gruppe");


	private TextBox shoppinglistNameBox = new TextBox();

	private Button confirmButton = new Button("\u2714");
	private Button cancelButton = new Button("\u2716");
	private Grid shoppinglistGrid = new Grid(2, 2);
	
	private MultiWordSuggestOracle groupSearchBar = new MultiWordSuggestOracle();
	private final SuggestBox groupSuggestBox = new SuggestBox(groupSearchBar);
	
	private ArrayList<Group> groups = new ArrayList<Group>();
	private Group groupToDisplay = new Group();
	
	private Boolean editable;
	private Integer groupID;
	
	private NewShoppingListForm newShoppingListForm;
	private AllShoppingListsCellList aslcl = new AllShoppingListsCellList();

	public NewShoppingListForm() {

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
		this.load();
		shoppinglistNameLabel.setStylePrimaryName("textLabel");
		formHeaderPanel.setStylePrimaryName("formHeaderPanel");
		infoTitleLabel.setStylePrimaryName("infoTitleLabel");
		bottomButtonsPanel.setStylePrimaryName("bottomButtonsPanel");
		cancelButton.setStylePrimaryName("cancelButton");
		confirmButton.setStylePrimaryName("confirmButton");
		
		groupSuggestBox.setSize("300px", "27px");
		groupSuggestBox.getElement().setPropertyString("placeholder", "Gruppenname eingeben...");

		formHeaderPanel.setHeight("8vh");
		formHeaderPanel.setWidth("100%");
		cancelButton.setPixelSize(130, 40);
		confirmButton.setPixelSize(130, 40);

		formHeaderPanel.add(infoTitleLabel);
		formHeaderPanel.setCellVerticalAlignment(infoTitleLabel, ALIGN_BOTTOM);

		bottomButtonsPanel.setSpacing(20);

		this.add(formHeaderPanel);
		this.add(shoppinglistGrid);
		this.setCellHorizontalAlignment(shoppinglistGrid, ALIGN_CENTER);

		shoppinglistNameBox.setMaxLength(10);

		shoppinglistGrid.setCellSpacing(10);
		shoppinglistGrid.setWidget(0, 0, shoppinglistNameLabel);
		shoppinglistGrid.setWidget(0, 1, shoppinglistNameBox);
		shoppinglistGrid.setWidget(1, 0, groupNameLabel);
		shoppinglistGrid.setWidget(1, 1, groupSuggestBox);


		this.add(bottomButtonsPanel);
		this.setCellHorizontalAlignment(bottomButtonsPanel, ALIGN_CENTER);

	}
	
	/**
	 * Methode um die aktuelle <code>NewShoppingListForm</code> Instanz zu setzen.
	 * Diese Instanz wird für das Aktualisieren nach dem Anlegen einer Einkaufliste benötigt.
	 * 
	 * @param newShoppingListForm das zu setzende <code>NewShoppingListForm</code> Objekt.
	 */
	public void setNewShoppingListForm(NewShoppingListForm newShoppingListForm) {

		this.newShoppingListForm = newShoppingListForm;
	}
	
	/**
	 * Methode um die aktuelle <code>AllShoppingListsCellList</code> Instanz zu setzen.
	 * Diese Instanz wird für das Aktualisieren nach dem Anlegen einer Einkaufliste benötigt.
	 * 
	 * @param newShoppingListForm das zu setzende <code>AllShoppingListsCellList</code> Objekt.
	 */
	public void setAllShoppingListCelllist(AllShoppingListsCellList allShoppingListsCellList) {

		this.aslcl = aslcl;
	}
	
	public void setCtm(CustomTreeModel ctm) {
		this.ctm = ctm;
	}
	
	/**
	 * Setzt die aktuell ausgewählte Gruppe
	 * 
	 * @param gMail Die Email-Adresse des selektierten <code>Group</code>
	 */
	private void setSelectedGroup(String value) {
		// TODO Auto-generated method stub
		for (Group g : groups) {

			if (g.getTitle().equals(value)) {
				groupID = g.getId();
				groupToDisplay = g;
			}
		}
	}
	
	/**
	 * Load Methode, damit werden alle Groups  mittels der shoppinglistAdministration
	 * geladen
	 */
	private void load() {
		Person person = new Person();
		person.setId(1);
		administration.getAllGroupsByPerson(person, new getAllGroupsByPersonCallback());
	}
	
	public void loadSearchbar() {
		for (Group g : groups) {

			groupSearchBar.add(g.getTitle());
		}

		groupSuggestBox.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {

			@Override
			public void onSelection(SelectionEvent<Suggestion> arg0) {
				setSelectedGroup(groupSuggestBox.getValue());
			}
		});
	}
	/**
	 * ***************************************************************************
	 * ABSCHNITT der ClickHandler
	 * ***************************************************************************
	 */
	
	/**
	 * Hiermit wird der Erstellvorgang eines neuer Einkaufsliste abbgebrochen.
	 */
	private class CancelClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("Details").clear();
		}
	}

	
	/**
	 * Sobald die Textfelder für den Namen und die Auswahl der Artikel ausgefüllt wurden, wird
	 * ein neuer <code>ShoppingList</code> nach dem Klicken des Bestätigungsbutton erstellt.
	 */
	private class CreateClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if(groupID != null && shoppinglistNameBox.getText() != null) {
			RootPanel.get("Details").clear();
			administration.createShoppingList(1, shoppinglistNameBox.getText(), groupID, new CreateShoppinglistCallback());
			
			}
		}
	}
	
	/**
	 * Nachdem ein neues <code>Shoppinglist</code> Objekt erstellt wurde, wird dieses der Liste der aktuellen
	 *  <code>AllShoppinglistsCelllist</code> Instanz hinzugefügt.
	 */
	private class CreateShoppinglistCallback implements AsyncCallback<ShoppingList> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Einkaufsliste konnte leider nicht erstellt werden:\n" + caught.toString());
		}

		@Override
		public void onSuccess(ShoppingList shoppingList) {
			//add item to cellist
			Notification.show("Einkaufsliste wurde erstellt");
			ctm.updateShoppingListToGroup(shoppingList, groupToDisplay);
		}
	}
	
	/**
	 * Nachdem ein neues <code>Shoppinglist</code> Objekt erstellt wurde, wird dieses der Liste der aktuellen
	 *  <code>AllShoppinglistsCelllist</code> Instanz hinzugefügt.
	 */
	private class getAllGroupsByPersonCallback implements AsyncCallback <ArrayList<Group>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Einkaufsliste konnte leider nicht erstellt werden:\n" + caught.toString());
		}

		@Override
		public void onSuccess(ArrayList<Group> result) {
			//add item to cellist
			groups = result;
			loadSearchbar();
		}
	}
}
