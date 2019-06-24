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
import de.hdm.softwarepraktikum.shared.bo.ListItem.Unit;
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

public class ListItemDialog extends PopupPanel {
	private ShowShoppingListForm sslf = null;

	private ShoppingListAdministrationAsync administration = ClientsideSettings.getShoppinglistAdministration();

	private ArrayList<Item> allItems = new ArrayList<Item>();
	private ArrayList<Store> allStores = new ArrayList<Store>();
	private ArrayList<Person> allPersons = new ArrayList<Person>();

	private Person selectedPerson = null;
	private Store selectedStore = null;
	private Item selectedItem = null;
	private ListItem selectedListItem = null;

	private Group group = new Group();
	private ShoppingList shoppingList = new ShoppingList();
	private Button confirmButton = new Button("\u2714");
	private Button cancelButton = new Button("\u2716");

	Grid itemGrid = new Grid(2, 2);

	private RadioButton existingButton = new RadioButton("Bestehend");
	private RadioButton newButton = new RadioButton("Neu");

	private Label existingLabel = new Label("Bestehenden Artikel");
	private Label newLabel = new Label("Neuen Anlegen");

	private VerticalPanel verticalPanel = new VerticalPanel();
	private HorizontalPanel radioButtonPanel = new HorizontalPanel();
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
	
	private CheckBox isGlobalBox = new CheckBox("Für alle Nutzer sichtbar");

	private TextBox amountTextBox = new TextBox();
	private TextBox itemTextBox = new TextBox();
	
	private Boolean updateItem = false;

	/**
	 * Bei der Instanziierung der Dialogbox werden alle <code>Item</code>,
	 * <code>Store</code>,<code>Person</code> geladen und mitels einer SuggestBox
	 * angezeigt, um so ein <code>Listitem</code> zu erstellen.
	 */
	public ListItemDialog() {

		this.load();

		this.setTitle("Artikel hinzufugen");
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
		itemTextBox.setSize("320px", "40px");

		bottomButtonsPanel.add(confirmButton);
		bottomButtonsPanel.add(cancelButton);

		radioButtonPanel.setSpacing(5);
		radioButtonPanel.add(existingButton);
		radioButtonPanel.add(existingLabel);
		radioButtonPanel.add(newButton);
		radioButtonPanel.add(newLabel);

		verticalPanel.add(itemLabel);
		verticalPanel.add(radioButtonPanel);
		verticalPanel.add(itemListBox);
		verticalPanel.add(itemTextBox);

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
		
		verticalPanel.add(isGlobalBox);

		verticalPanel.add(bottomButtonsPanel);

		confirmButton.addClickHandler(new ConfirmClickHandler());
		cancelButton.addClickHandler(new CancelClickHandler());

		existingButton.addValueChangeHandler(new ExisitingValueChangeHandler());
		newButton.addValueChangeHandler(new NewValueChangeHandler());

		verticalPanel.setCellHorizontalAlignment(radioButtonPanel, HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setCellHorizontalAlignment(personListBox, HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setCellHorizontalAlignment(itemListBox, HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setCellHorizontalAlignment(itemTextBox, HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setCellHorizontalAlignment(storeListBox, HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setCellHorizontalAlignment(bottomButtonsPanel, HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setCellHorizontalAlignment(isGlobalBox, HasHorizontalAlignment.ALIGN_CENTER);
		this.center();
	}

	public void setShowShoppingListForm(ShowShoppingListForm sslf) {
		this.sslf = sslf;
	}
	
	public void setGroup(Group group) {
		this.group = group;
	}
	
	public void setShoppingList(ShoppingList sl) {
		this.shoppingList = sl;
	}

	/**
	 * Load Methode, damit werden alle Items and Stores mittels der
	 * shoppinglistAdministration geladen
	 */
	private void load() {
		existingButton.setValue(true);
		newButton.setValue(false);
		itemTextBox.setVisible(false);
		isGlobalBox.setVisible(false);
		administration.getAllItems(new GetAllItemsCallback());
		administration.getAllStores(new GetAllStoresCallback());
		administration.getAllPersons(new GetAllGroupMembersCallback());
		// administration.getAllGroupMembers(g, new GetAllGroupMembersCallback());
		loadListBox();
	}

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

	public void displayListItem(ListItem li, ShoppingList shoppingList, Group group, Boolean update) {
		this.group = group;
		this.shoppingList = shoppingList;
		this.updateItem = update;
		
		existingButton.setValue(false);
		newButton.setValue(false);
		this.selectedListItem = li;
		itemLabel.setText("Artikel bearbeiten");
		existingButton.setVisible(false);
		newButton.setVisible(false);
		existingLabel.setVisible(false);
		newLabel.setVisible(false);

		itemListBox.setVisible(true);
		itemTextBox.setVisible(false);
		itemTextBox.setEnabled(false);
		amountTextBox.setText(Double.toString(li.getAmount()));
		
		int indexToFind = -1;
		for (int i = 0; i < 4; i++) {
			
	        if (unitListBox.getItemText(i).equals(li.getUnit().toString())) {
					indexToFind = i;
					break;
				}
			}
			itemListBox.setSelectedIndex(indexToFind);
	}


	public void loadListBox() {
		unitListBox.addItem("KG");
		unitListBox.addItem("L");
		unitListBox.addItem("ML");
		unitListBox.addItem("ST");
	}

	
	
	/**
	 * Implementierung des ConfirmClickHandler
	 */
	private class ConfirmClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			
			if(updateItem == false) {
			if (existingButton.getValue()==true ) {
				getSelectedObjects(personListBox.getSelectedItemText(), storeListBox.getSelectedItemText(), itemListBox.getSelectedItemText());
				administration.createListItem(selectedItem, selectedPerson.getId(), selectedStore.getId(), shoppingList.getId(), group.getId(), Double.parseDouble(amountTextBox.getText()), getItemUnit(unitListBox.getSelectedItemText()), false, new createListItemCallback());
			}
			else if (newButton.getValue()==true ) {
				administration.createItem(itemTextBox.getText(), isGlobalBox.getValue(), new CreateItemListItemCallback());
			}}

			else if (updateItem == true) {
			getSelectedObjects(personListBox.getSelectedItemText(), storeListBox.getSelectedItemText(), itemListBox.getSelectedItemText());
			
			selectedListItem.setAmount(Double.parseDouble(amountTextBox.getText()));
			selectedListItem.setStoreID(selectedStore.getId());
			selectedListItem.setBuyerID(selectedPerson.getId());
			selectedListItem.setUnit(getItemUnit(unitListBox.getSelectedItemText()));
			
			
			administration.updateListItem(selectedListItem, new UpdateListItemCallback());
			}

			ListItemDialog.this.hide();
		}
	}
	
	/**
	 * Implementierung des CancelClickHandler
	 */
	private class CancelClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			ListItemDialog.this.hide();
		}
	}
	
	/**
	 * Implementierung des NewValueChangeHandler
	 */
	private class NewValueChangeHandler<Boolean> implements ValueChangeHandler{

		@Override
		public void onValueChange(ValueChangeEvent event) {
			existingButton.setValue(false);
			itemListBox.setVisible(false);
			itemTextBox.setVisible(true);
			isGlobalBox.setVisible(true);

		}
	}
	
	
	/**
	 * Implementierung des ExisitingValueChangeHandler
	 */
	private class ExisitingValueChangeHandler<Boolean> implements ValueChangeHandler{

		@Override
		public void onValueChange(ValueChangeEvent event) {
			newButton.setValue(false);
			itemListBox.setVisible(true);
			itemTextBox.setVisible(false);
			isGlobalBox.setVisible(false);
		}
	}
	
	
	
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
			
			if (ListItemDialog.this.selectedListItem != null) {
			ListItem li = ListItemDialog.this.selectedListItem;
			for (Item item : allItems) {
				if (item.getId() == li.getItemId()) {
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
			
			
			if (ListItemDialog.this.selectedListItem != null) {
				ListItem li = ListItemDialog.this.selectedListItem;
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
			}
		}
	}
}	
		
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
			
			if (ListItemDialog.this.selectedListItem != null) {
				ListItem li = ListItemDialog.this.selectedListItem;
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
	
	private class createListItemCallback implements AsyncCallback<ListItem> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}
		
		@Override
		public void onSuccess(ListItem result) {
			// TODO Auto-generated method stub
			sslf.AddListItem(result);
			Notification.show("Artikel in der Einkaufsliste wurde erstellt");

		}
	} 
	
	private class UpdateListItemCallback implements AsyncCallback<ListItem> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}
		
		@Override
		public void onSuccess(ListItem result) {
			// TODO Auto-generated method stub^
			Notification.show("Artikel in der Einkaufsliste wurde aktualisiert");
			sslf.updateListItem();

		}
	}
	
	private class CreateItemListItemCallback implements AsyncCallback<Item> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}
		
		@Override
		public void onSuccess(Item result) {
			// TODO Auto-generated method stub
			getSelectedObjects(personListBox.getSelectedItemText(), storeListBox.getSelectedItemText(), itemListBox.getSelectedItemText());
			administration.createListItem(result, selectedPerson.getId(), selectedStore.getId(), shoppingList.getId(), group.getId(), Double.parseDouble(amountTextBox.getText()), getItemUnit(unitListBox.getSelectedItemText()), false, new AsyncCallback<ListItem>() {
			//administration.createListItem(result, selectedPerson.getId(), selectedStore.getId(), 1, 1, Integer.parseInt(amountTextBox.getText()), getItemUnit(unitListBox.getSelectedItemText()), false, new AsyncCallback<ListItem>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(ListItem result) {
					// TODO Auto-generated method stub
					sslf.AddListItem(result);
					Notification.show("Ein neuer Artikel wurde erstellt und der Einkaufsliste hinzugefügt");	
				}
			});
			

		}
	}
	
	public Unit getItemUnit (String unit) {
		if (Objects.equals(unit.trim(), "L")) {
			return Unit.L;
		}
		else if(Objects.equals(unit.trim(), "KG")) {
			return Unit.KG;
		} 
		else if(Objects.equals(unit.trim(), "ST")){
			return Unit.ST;
		}
		else if(Objects.equals(unit.trim(), "ML")) {
			return Unit.ML;
		} else {
			return null;
		}
	}
}
