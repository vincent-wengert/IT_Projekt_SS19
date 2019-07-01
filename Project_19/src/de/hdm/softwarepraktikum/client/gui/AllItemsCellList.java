package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.aria.client.AlertdialogRole;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.client.Project_19.CurrentPerson;
import de.hdm.softwarepraktikum.server.ShoppingListAdministrationImpl;
import de.hdm.softwarepraktikum.shared.ShoppingListAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.Person;
import java_cup.version;

/**
 * Diese CellList dient als Navigationselement in der Benutzeroberfläche für Objekte der Klasse <code>Item</code>. 
 * 
 * @author Jan Duwe, Vincent Wengert
 *
 */

public class AllItemsCellList extends VerticalPanel {
	private NavigatorPanel navigator;

	Person currentPerson = CurrentPerson.getPerson();

	ShoppingListAdministrationAsync administration = ClientsideSettings.getShoppinglistAdministration();

	private SingleSelectionModel<Item> selectionModel = null;
	private ItemDemoKeyProvider keyProvider = null;
	private CellList<Item> cellList = new CellList<Item>(new ItemCell(), keyProvider);

	private ItemForm itemForm = new ItemForm();

	private ListDataProvider<Item> dataProvider = new ListDataProvider<Item>();

	private ArrayList<Item> items = new ArrayList<Item>();

	private Item itemToDisplay = null;
	private Group selectedGroup = null;

	// ListBox der Gruppen
	private Grid groupGrid = new Grid(3, 2);
	private Label favLabel = new Label("Favoriten-Gruppe");
	private ListBox groupListBox = new ListBox();
	private Button confirmButton = new Button("Best\u00E4tigen");

	private ArrayList<Group> allGroups = new ArrayList<Group>();

	/**
	 * ************************* ABSCHNITT der Methoden *************************
	 */
	
	/**
	 * In dieser Methode werden die darzustellenden Widgets der Klasse hinzugefügt. 
	 * Die Widgets werden innerhalb dieser Methode ebenfalls formatiert.
	 * Der Methodenaufruf erfolgt beim Aufruf der Klasse.
	 */
	public void onLoad() {
		this.load();
		groupGrid.setWidget(0, 0, favLabel);
		groupGrid.setWidget(1, 0, groupListBox);
		groupGrid.setWidget(1, 1, confirmButton);

		confirmButton.addClickHandler(new ConfirmGroupFavoritesClickHandler());

		groupListBox.setWidth("10vw");
		confirmButton.setStylePrimaryName("selectGroupButton");
		favLabel.setStylePrimaryName("favLabel");
		

		ItemDemoKeyProvider keyProvider = new ItemDemoKeyProvider();
		selectionModel = new SingleSelectionModel<Item>(keyProvider);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());
		cellList.setSelectionModel(selectionModel);

		dataProvider.addDataDisplay(cellList);
		cellList.setRowData(0, dataProvider.getList());
		cellList.setRowCount(items.size(), true);

		groupGrid.setWidget(2, 0, cellList);

		this.add(groupGrid);
	}

	private void load() {
		if (selectedGroup != null) {
			administration.getAllItemsByGroup(selectedGroup.getId(), currentPerson.getId(), new GetAllItemsCallback());
		}
		administration.getAllGroupsByPerson(currentPerson, new GetAllGroupsCallback());
	}
	
	/**
	 * Fügt alle <code>Item</code> Objekte dem ListDataProvider hinzu.
	 */
	public void getAllItems() {

		for (Item p : items) {
			dataProvider.getList().add(p);
		}
		dataProvider.refresh();
	}

	/**
	 * Methode zum Setzen des NavigatorPanels innerhalb des MenuPanels.
	 * 
	 * @param das zu setzende NavigatorPanel.
	 */
	public void setNavigator(NavigatorPanel navigator) {
		this.navigator = navigator;
	}
	
	/**
	 * Methode zum Auslesen des SingleSelectionModel innerhalb des NavigatorPanels.
	 * @return das ausgelesene SingleSelectionModel.
	 */
	public SingleSelectionModel<Item> getSelectionModel() {
		return this.selectionModel;
	}
	
	/**
	 * Methode zum Setzen der <code>ItemForm</code> innerhalb des NavigatorPanels.
	 * @param itemForm zu setzende ItemForm.
	 */
	public void setItemForm(ItemForm itemForm) {
		this.itemForm = itemForm;
	}
	
	/**
	 * Methode zum Setzen der ausgewählten <code>Group</code>.
	 * @param g die zu setzende <code>Group</code>.
	 */
	public void setSelectedGroup(Group g) {
		this.selectedGroup = g;
	}
	
	/**
	 * Methode zur Darstellung des in der CellList ausgewählten <code>Item</code> Objekts.
	 * @param i das ausgewählte <code>Item</code>.
	 */
	public void setSelectedItem(Item i) {
		itemToDisplay = i;
		RootPanel.get("Details").clear();
		itemForm.setSelected(i);
		itemForm.setEditable(false);
		itemForm.setInitial(false);
		itemForm.setGroup(selectedGroup);
		RootPanel.get("Details").add(itemForm);
	}
	
	/**
	 * Methode zum Aktualisieren des ListDataProvider der Klasse <code>AllItemsCellList</code>. Anschließend erfolgt die Auswahl auf das übergebene <code>Item</code> Objekt.
	 * @param item <code>Item</code> Objekt, das nach dem Aktualisieren ausgewählt wird.
	 */
	public void updateCelllist(Item item) {
		dataProvider.getList().clear();
		if (selectedGroup != null) {
			administration.getAllItemsByGroup(selectedGroup.getId(), currentPerson.getId(), new GetAllItemsCallback());
		}
		dataProvider.refresh();
		selectionModel.setSelected(item, true);
	}

	/**
	 * Methode zum Aktualisieren des ListDataProvider der Klasse <code>AllItemsCellList</code> nachdem ein <code>Item</code> Objekt gelöscht wird.
	 * @param i zu löschendes <code>Item</code> Objekt.
	 */
	public void removeItem(Item i) {
		if (selectedGroup != null) {
			administration.getAllItemsByGroup(selectedGroup.getId(), currentPerson.getId(), new GetAllItemsCallback());
		}
		dataProvider.getList().remove(i);
		dataProvider.refresh();
	}
	
	/**
	 * Methode zum Aktualisieren des ListBox Objekts innerhalb der Klasse <code>GroupForm</code> nachdem eine neue Instanz eines <code>Group</code> Objekts erstellt wird.
	 * @param g die neu hinzugefügte <code>Group</code>.
	 */
	public void updateAddedGroup(Group g) {
		groupListBox.addItem(g.getTitle());
	}

	/**
	 * Methode zum Auslesen aller <code>Group</code> Objekte innerhalb der ArrayList allGroups.
	 * @return ArrayList befüllt mit allen <code>Group</code> Objekten.
	 */
	public ArrayList<Group> getAllGroups() {
		return this.allGroups;
	}

	/**
	 * Methode zum Setzen aller <code>Group</code> Objekte innerhalb der ArrayList allGroups.
	 * @param allGroups ArrayList befüllt mit zu setzenden <code>Group</code> Objekten.
	 */
	public void setAllGroups(ArrayList<Group> allGroups) {
		this.allGroups = allGroups;
	}
	
	/**
	 * ************************* ABSCHNITT der Click-/EventHandler *************************
	 */
	
	/**
	 * Ein ClickHandler der die in der ListBox ausgewählte <code>Group</code> zur Auswahl der favorisierten <code>Item</code> Objekte setzt.
	 * Aktualisiert anschließend die <code>AllItemsCellList</code>.
	 */
	
	private class ConfirmGroupFavoritesClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			for (Group g : allGroups) {
				if (g.getTitle().equals(groupListBox.getSelectedItemText())) {
					AllItemsCellList.this.selectedGroup = g;
					AllItemsCellList.this.updateCelllist(null);
				}
			}
		}
	}
	
	/**
	 * Ein SelectionHandler der das in dem SingleSelectionModel der <code>AllItemsCellList</code> ausgewählte <code>Item</code> Objekt als ausgewählt setzt.
	 */
	private class SelectionChangeEventHandler implements SelectionChangeEvent.Handler {
		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			Item selection = selectionModel.getSelectedObject();
			if (selection != null) {
				setSelectedItem(selection);
			}
		}
	}
	
	/**
	 * ************************* ABSCHNITT der Cells *************************
	 */
	
	/**
	 * <code>ItemCell</code> Objekt zum Rendern der anzuzeigenden <code>Item</code> Objekte.
	 * Wird mit dem Namen des <code>Item</code> befüllt.
	 */
	private static class ItemCell extends AbstractCell<Item> {
		@Override
		public void render(Context context, Item key, SafeHtmlBuilder sb) {
			if (context != null) {

				if (key.getIsFavorite() == true) {
					sb.appendHtmlConstant("&#9733");

				} else {
					sb.appendHtmlConstant("&#9734");
				}
				sb.appendHtmlConstant(key.getName());
			}
		}
	}
	
	/**
	 * ************************* ABSCHNITT der KeyProvider *************************
	 */
	
	/**
	 * Versieht jedes darzustellende <code>Item</code> Objekt mit einer ID.
	 */
	private class ItemDemoKeyProvider implements ProvidesKey<Item> {
		@Override
		public Object getKey(Item item) {
			return (item == null) ? null : item.getId();
		}
	}
	
	/**
	 * ************************* ABSCHNITT der Callbacks *************************
	 */
	
	/** 
	 * CallBack mit dem alle <code>Item</code> Einträge aus der Datenbank geladen werden.
	 * Anschließend werden alle geladenen Objekte dem ListDataProvider hinzugefügt.
	 */
	private class GetAllItemsCallback implements AsyncCallback<ArrayList<Item>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(ArrayList<Item> result) {
			// TODO Auto-generated method stub
			items = result;
			dataProvider.getList().clear();
			getAllItems();
			dataProvider.refresh();

		}
	}

	/**
	 * CallBack mit dem alle <code>Group</code> Objekte aus der Datenbank ausgelesen werden.
	 * Anschließend werden die Namen der ausgelesenen Objekte der ListBox hinzugefügt und die <code>AllItemsCellList</code> aktualisiert.
	 */
	private class GetAllGroupsCallback implements AsyncCallback<ArrayList<Group>> {
		@Override
		public void onSuccess(ArrayList<Group> result) {

			// TODO Auto-generated method stub
			allGroups = result;
			for (Group g : result) {
				groupListBox.addItem(g.getTitle());
			}

			for (Group g : allGroups) {
				if (g.getTitle().equals(groupListBox.getSelectedItemText())) {
					selectedGroup = g;
					updateCelllist(null);
				}
			}

		}

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Notification.show(caught.toString());
		}
	}

}
