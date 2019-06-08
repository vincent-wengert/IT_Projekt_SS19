package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
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
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.ListItem;
import de.hdm.softwarepraktikum.shared.bo.ListItem.Unit;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.bo.Store;


/**
 * Die Klasse <code>ListItemDialog</code> dient zum Hinzufugen von <code>ListItems</code> einer 
 * <code>Shoppinglist</code> unter Angabe unterschiedlicher Parameter.
 * 
 * @author Vincent Wengert
 * @version 1.0
 */


public class ListItemDialog extends PopupPanel{
	private ShowShoppingListForm sslf;

	private ShoppingListAdministrationAsync administration = ClientsideSettings.getShoppinglistAdministration();
	
	private ArrayList<Item> allItems = new ArrayList<Item>();
	private ArrayList<Store> allStores = new ArrayList<Store>();
	private ArrayList<Person> allPersons = new ArrayList<Person>();

	private Button confirmButton = new Button("\u2714");
	private Button cancelButton = new Button ("\u2716");
	
	Grid itemGrid = new Grid(2,2);
	
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
	
	private	ListBox itemListBox = new ListBox();
	private	ListBox personListBox = new ListBox();
	private	ListBox storeListBox = new ListBox();
	private ListBox unitListBox = new ListBox();
	
	private TextBox amountTextBox = new TextBox();
	private TextBox itemTextBox = new TextBox();
	
	/**
	 * Bei der Instanziierung der Dialogbox werden alle <code>Item</code>, <code>Store</code>,<code>Person</code> geladen und mitels
	 * einer SuggestBox angezeigt, um so ein <code>Listitem</code> zu erstellen.
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
		personListBox.setSize("320px"," 40px");
		storeListBox.setSize("320px"," 40px");
		itemListBox.setSize("320px"," 40px");
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
		
		verticalPanel.add(bottomButtonsPanel);
		
		confirmButton.addClickHandler(new ConfirmClickHandler());
		cancelButton.addClickHandler(new CancelClickHandler());
		
		existingButton.addValueChangeHandler(new ExisitingValueChangeHandler());
		newButton.addValueChangeHandler(new NewValueChangeHandler());

		verticalPanel.setCellHorizontalAlignment(radioButtonPanel, HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setCellHorizontalAlignment(personListBox,HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setCellHorizontalAlignment(itemListBox,HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setCellHorizontalAlignment(itemTextBox,HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setCellHorizontalAlignment(storeListBox,HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setCellHorizontalAlignment(bottomButtonsPanel, HasHorizontalAlignment.ALIGN_CENTER);
		this.center();
	}

	public void setShowShoppingListForm (ShowShoppingListForm sslf) {
		this.sslf =sslf;
	}
	
	/**
	 * Load Methode, damit werden alle Items and Stores mittels der shoppinglistAdministration
	 * geladen
	 */
	private void load() {
		existingButton.setValue(true);
		newButton.setValue(false);
		itemTextBox.setVisible(false);
		administration.getAllItems(new GetAllItemsCallback());
		administration.getAllStores(new GetAllStoresCallback());
		//administration.getAllGroupMembers(g, new GetAllGroupMembersCallback());
		loadListBox();
	}
	
	public void displayListItem(ListItem li) {
		
		itemLabel.setText("Artikel bearbeiten");
		existingButton.setVisible(false);
		newButton.setVisible(false);
		existingLabel.setVisible(false);
		newLabel.setVisible(false);
		
		itemListBox.setVisible(false);
		itemTextBox.setVisible(true);
		itemTextBox.setEnabled(false);
		
		itemTextBox.setText(li.getName());
		amountTextBox.setText(Double.toString(li.getAmount()));	
	}
	/**
	 * Implementierung der ListBox, wird bei der Instanziierung augfgerufen
	 */
	public void loadListBox() {
		unitListBox.addItem("");
		unitListBox.addItem("KG");
		unitListBox.addItem("L");
		unitListBox.addItem("ML");
		unitListBox.addItem("ST");
		}
	
	private class GetAllItemsCallback implements AsyncCallback<ArrayList<Item>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}
		
		@Override
		public void onSuccess(ArrayList<Item> result) {
			// TODO Auto-generated method stub
			allItems = result;
			for (Item i : allItems) {
				itemListBox.addItem(i.getName());
					}
				}
			}
	
	
	/**
	 * Implementierung des ConfirmClickHandler
	 */
	private class ConfirmClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			if (existingButton.getValue()==true) {
				ListItem li = new ListItem(itemListBox.getSelectedItemText(), getItemUnit(unitListBox.getSelectedItemText()) , Integer.parseInt(amountTextBox.getText()), false);	
				sslf.AddListItem(li);
			}
			else {
				ListItem li = new ListItem(itemTextBox.getText(), getItemUnit(unitListBox.getSelectedItemText()) ,
						Integer.parseInt(amountTextBox.getText()), false);	
				sslf.AddListItem(li);
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

		}
	}
	
	public Unit getItemUnit (String unit) {
		if (unit == "L") {
			return Unit.L;
				}
		else if(unit == "KG") {
			return Unit.KG;
			} else {
		return Unit.ST;
			}
		}
}
