package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;
import java.util.Objects;

import javax.validation.constraints.Null;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
//import com.google.gwt.thirdparty.guava.common.base.Objects;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppingListAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.ListItem;
import java_cup.internal_error;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.bo.ShoppingList;
import de.hdm.softwarepraktikum.shared.bo.Store;

/**
 * Die Klasse <code>ListItemDialog</code> dient zum Hinzufugen von
 * <code>ListItems</code> einer <code>Shoppinglist</code> unter Angabe
 * unterschiedlicher Parameter.
 * 
 * @author Vincent Wengert
 * @version 1.0
 */

public class StandardListItemDialog extends PopupPanel {
	private ShowShoppingListForm sslf = null;

	private ShoppingListAdministrationAsync administration = ClientsideSettings.getShoppinglistAdministration();

	private ArrayList<Item> allItems = new ArrayList<Item>();
	private ArrayList<Store> allStores = new ArrayList<Store>();
	private ArrayList<Person> allPersons = new ArrayList<Person>();

	private Person selectedPerson = null;
	private Store selectedStore = null;
	private Item selectedItem = null;
	private ListItem selectedListItem = null;

	private Group group = null;
	private ShoppingList shoppingList = new ShoppingList();
	private Button confirmButton = new Button("\u2714");
	private Button cancelButton = new Button("\u2716");

	Grid itemGrid = new Grid(2, 2);

	private VerticalPanel verticalPanel = new VerticalPanel();
	private HorizontalPanel bottomButtonsPanel = new HorizontalPanel();

	private Label itemLabel = new Label("Artikel hinzufügen");
	private Label personLabel = new Label("Person auswählen");
	private Label storeLabel = new Label("Laden auswählen");

	private Label unitLabel = new Label("Einheit auswählen");
	private Label amountLabel = new Label("Menge eingeben");

	private ListBox itemListBox = new ListBox();
	private ListBox personListBox = new ListBox();
	private ListBox storeListBox = new ListBox();
	private ListBox unitListBox = new ListBox();

	private TextBox amountTextBox = new TextBox();
	

	/**
	 * Bei der Instanziierung der Dialogbox werden alle <code>Item</code>,
	 * <code>Store</code>,<code>Person</code> geladen und mitels einer SuggestBox
	 * angezeigt, um so ein <code>Listitem</code> zu erstellen.
	 */
	public StandardListItemDialog() {
		itemLabel.setText("Eigenschaften anlegen");

		this.setGlassEnabled(true);
		this.add(verticalPanel);

		verticalPanel.setSpacing(20);

		cancelButton.setStylePrimaryName("cancelButton");
		confirmButton.setStylePrimaryName("confirmButton");
		itemLabel.setStylePrimaryName("textLabel");
		personLabel.setStylePrimaryName("textLabel");
		storeLabel.setStylePrimaryName("textLabel");

		cancelButton.setPixelSize(130, 40);
		confirmButton.setPixelSize(130, 40);
		bottomButtonsPanel.setSpacing(20);
		personListBox.setSize("320px", " 40px");
		storeListBox.setSize("320px", " 40px");
		itemListBox.setSize("320px", " 40px");
		unitListBox.setSize("160px", "40px");
		amountTextBox.setSize("100px", "40px");

		bottomButtonsPanel.add(confirmButton);
		bottomButtonsPanel.add(cancelButton);


		verticalPanel.add(itemLabel);
		verticalPanel.add(itemListBox);

		itemGrid.setWidget(0, 1, unitLabel);
		itemGrid.setWidget(0, 0, amountLabel);
		itemGrid.setWidget(1, 0, amountTextBox);
		itemGrid.setWidget(1, 1, unitListBox);
		itemGrid.setCellSpacing(5);
		verticalPanel.add(itemGrid);

		verticalPanel.add(storeLabel);
		verticalPanel.add(storeListBox);

		verticalPanel.add(personLabel);
		verticalPanel.add(personListBox);
		
		verticalPanel.add(bottomButtonsPanel);

		confirmButton.addClickHandler(new ConfirmClickHandler());
		cancelButton.addClickHandler(new CancelClickHandler());

		verticalPanel.setCellHorizontalAlignment(personListBox, HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setCellHorizontalAlignment(itemListBox, HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setCellHorizontalAlignment(storeListBox, HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setCellHorizontalAlignment(bottomButtonsPanel, HasHorizontalAlignment.ALIGN_CENTER);
		this.center();
	}

	/**
	 * ***************************************************************************
	 * ABSCHNITT der Methoden
	 * ***************************************************************************
	 */
	
	/**
	 * Setzen der <code>ShowShoppingListForm</code> Instanz innerhalb des
	 * ListItemDialog. Diese wird für die Aktualisierung nach einer Änderung
	 * benötigt.
	 * 
	 * @param sslf das zu setzende <code>ShowShoppingListForm</code> Objekt
	 */
	public void setShowShoppingListForm(ShowShoppingListForm sslf) {
		this.sslf = sslf;
	}
	
	/**
	 * Setzen der <code>Group</code> Instanz innerhalb des ListItemDialog. Diese
	 * wird für die Aktualisierung nach einer Änderung benötigt.
	 * 
	 * @param group das zu setzende <code>Group</code> Objekt
	 */
	public void setGroup(Group group) {
		this.group = group;
	}
	
	/**
	 * Setzen der <code>ShoppingList</code> Instanz innerhalb des ListItemDialog.
	 * Diese wird für die Aktualisierung nach einer Änderung benötigt.
	 * 
	 * @param sl das zu setzende <code>ShoppingList</code> Objekt
	 */
	public void setShoppingList(ShoppingList sl) {
		this.shoppingList = sl;
	}

	/**
	 * Load Methode, damit werden alle Items and Stores mittels der
	 * shoppinglistAdministration geladen
	 */
	private void load() {
		administration.getAllItems(new GetAllItemsCallback());
		administration.getAllStores(new GetAllStoresCallback());
		administration.getAllPersons(new GetAllGroupMembersCallback());
		// administration.getAllGroupMembers(g, new GetAllGroupMembersCallback());
		loadListBox();
	}


	/**
	 * Setzen der ausgewählten Objekte in dem Listboxen, um diese dem zu
	 * erstellenden ListItem zu übergeben.
	 * 
	 * @param person das zu setzende <code>String</code> Objekt
	 * @param store  das zu setzende <code>String</code> Objekt
	 * @param item   das zu setzende <code>String</code> Objekt
	 */
	public void getSelectedObjects(String person, String store, String item) {

		for (Person p : allPersons) {
			if (p.getName().equals(person)) {
				selectedPerson = p;
			}
		}

		for (Store s : allStores) {
			if (s.getName().equals(store)) {
				selectedStore = s;
			}
		}

		for (Item i : allItems) {
			if (i.getName().equals(item)) {
				selectedItem = i;
			}
		}
	}

	/**
	 * Anzeigen des ausgewählten <code>ListItem</code> in der Celltable in der
	 * <code>ShowShoppingListForm</code>
	 * 
	 * @param li           das zu setzende <code>ListItem</code> Objekt
	 * @param shoppingList das zu setzende <code>ShoppingList</code> Objekt
	 * @param group        das zu setzende <code>Group</code> Objekt
	 * @param update       der zu Boolean Wert ob das ListItem upgedatet wird
	 * 
	 */
	public void displayListItem(Item itemDisplay, ShoppingList shoppingList, Group group) {
		this.load();
		
		this.group = group;
		this.shoppingList = shoppingList;
		this.selectedItem = itemDisplay;
		
		itemListBox.setVisible(true);
	}

	/**
	 * Anzeigen der möglichen Einheiten die ausgewählt werden können, in der
	 * Einheiten ListBox
	 */
	public void loadListBox() {
		unitListBox.addItem("KG");
		unitListBox.addItem("L");
		unitListBox.addItem("ML");
		unitListBox.addItem("ST");
	}

	/**
	 * ***************************************************************************
	 * ABSCHNITT der ClickHandler/Events
	 * ***************************************************************************
	 */
	
	/**
	 * Implementierung des ConfirmClickHandler, dient zur Bestätigung im
	 * Bearbeitungsprozess
	 */
	private class ConfirmClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			getSelectedObjects(personListBox.getSelectedItemText(), storeListBox.getSelectedItemText(), itemListBox.getSelectedItemText());
			administration.createListItem(selectedItem, selectedPerson.getId(), selectedStore.getId(), shoppingList.getId(), group.getId(), Double.parseDouble(amountTextBox.getText()), unitListBox.getSelectedItemText(), false, new createListItemCallback());			
			
		}
	}
	
	/**
	 * Implementierung des CancelClickHandler, dient zum Abbrechen des Bearbeitens
	 */
	private class CancelClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			StandardListItemDialog.this.hide();
		}
	}
	

	/**
	 * ***************************************************************************
	 * ABSCHNITT der Callbacks
	 * ***************************************************************************
	 */
	
	/**
	 * Private Klasse des Callback um alle <code>Items<code> Instanzen aus dem
	 * System zu bekommen.
	 */
	private class GetAllItemsCallback implements AsyncCallback<ArrayList<Item>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}
		
		@Override
		public void onSuccess(ArrayList<Item> result) {
			allItems = result;
			for (Item i : allItems) {
				itemListBox.addItem(i.getName());
					}
			
			if (StandardListItemDialog.this.selectedItem != null) {
			Item li = StandardListItemDialog.this.selectedItem;
			for (Item item : allItems) {
				if (item.getId() == li.getId()) {
					int indexToFind = -1;
					
					for (int i = 0; i < allItems.size(); i++) {
				
				        if (itemListBox.getItemText(i).equals(item.getName())) {
								indexToFind = i;
								break;
							}
						}
						itemListBox.setSelectedIndex(indexToFind);
						itemListBox.setEnabled(false);
					}
				}
			}
		}
	}
		
	/**
	 * Private Klasse des Callback um alle <code>Store<code> Instanzen aus dem
	 * System zu bekommen.
	 */
	private class GetAllStoresCallback implements AsyncCallback<ArrayList<Store>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}
		
		@Override
		public void onSuccess(ArrayList<Store> result) {
			// TODO Auto-generated method stub
			allStores = result;
			for(Store store: allStores) {
				storeListBox.addItem(store.getName());
				}
			
			
			if (StandardListItemDialog.this.selectedListItem != null) {
				ListItem li = StandardListItemDialog.this.selectedListItem;
			for (Store store : allStores) {
				if (store.getId() == li.getStoreID()) {
					int indexToFind = -1;

					for (int s = 0; s < allStores.size(); s++) {

						if (storeListBox.getItemText(s).equals(store.getName())) {
							indexToFind = s;
							break;
						}
					}
					storeListBox.setSelectedIndex(indexToFind);
				}	
			}}}
		}	
		
	/**
	 * Private Klasse des Callback um alle <code>Person<code> Instanzen aus der Gruppe aus dem System zu bekommen.
	 */
	private class GetAllGroupMembersCallback implements AsyncCallback<ArrayList<Person>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}
		
		@Override
		public void onSuccess(ArrayList<Person> result) {
			// TODO Auto-generated method stub
			allPersons = result;
			for(Person person : allPersons) {
				personListBox.addItem(person.getName());
				}
			
			if (StandardListItemDialog.this.selectedListItem != null) {
				ListItem li = StandardListItemDialog.this.selectedListItem;
			for (Person person : allPersons) {
				if (person.getId() == li.getBuyerID()) {
					int indexToFind = -1;

					for (int p = 0; p < allPersons.size(); p++) {

						if (personListBox.getItemText(p).equals(person.getName())) {
							indexToFind = p;
							break;
						}
					}
					personListBox.setSelectedIndex(indexToFind);
						}
					}
				}
			}
		}
	
	/**
	 * Private Klasse des Callback um eine <code>ListItem<code> Instanz im System zu erstellen.
	 */
	private class createListItemCallback implements AsyncCallback<ListItem> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}
		
		@Override
		public void onSuccess(ListItem result) {
			// TODO Auto-generated method stub
			StandardListItemDialog.this.hide();
			sslf.AddListItem(result);
			Notification.show("Artikel in der Einkaufsliste wurde erstellt");
		}
	} 
}
