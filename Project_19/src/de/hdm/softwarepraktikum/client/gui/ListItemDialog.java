package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppingListAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.ListItem;
import de.hdm.softwarepraktikum.shared.bo.Store;

public class ListItemDialog extends PopupPanel{

	private ShoppingListAdministrationAsync administration = ClientsideSettings.getShoppinglistAdministration();
	
	private ArrayList<Item> allItems = new ArrayList<Item>();
	private ArrayList<Store> allStores = new ArrayList<Store>();

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
		amountTextBox.setSize("160px", "40px");

		
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
		
		itemGrid.setWidget(0, 0, unitLabel);
		itemGrid.setWidget(0, 1, amountLabel);
		itemGrid.setWidget(1, 0, amountTextBox);
		itemGrid.setWidget(1, 1, unitListBox);
		verticalPanel.add(itemGrid);
		
		verticalPanel.add(storeLabel);
		verticalPanel.add(storeListBox);
		
		verticalPanel.add(personLabel);
		verticalPanel.add(personListBox);
		
		verticalPanel.add(bottomButtonsPanel);

		verticalPanel.setCellHorizontalAlignment(radioButtonPanel, HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setCellHorizontalAlignment(personListBox,HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setCellHorizontalAlignment(itemListBox,HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setCellHorizontalAlignment(storeListBox,HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setCellHorizontalAlignment(bottomButtonsPanel, HasHorizontalAlignment.ALIGN_CENTER);
		this.center();
	}

	
	/**
	 * Load Methode, damit werden alle User mittels der contactAdministration
	 * geladen
	 */
	private void load() {
		administration.getAllItems(new GetAllItemsCallback());
		administration.getAllStores(new GetAllStoresCallback());
		//loadListBox();
	}
	
	

	
	/**
	 * Implementierung der ListBox, wird bei der Instanziierung augfgerufen
	 */
	public void loadListBox() {
		//itemListBox.addItem("");
		for (Item i : allItems) {
		itemListBox.addItem(i.getName());
			}
		
		unitListBox.addItem("");
		unitListBox.addItem("KG");
		unitListBox.addItem("L");
		unitListBox.addItem("ML");
		unitListBox.addItem("ST");
		
		for(Store store: allStores) {
			storeListBox.addItem(store.getName());
			};
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
			//loadListBox();

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
			loadListBox();

		}
	}
}
