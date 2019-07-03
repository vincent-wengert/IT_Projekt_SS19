
package de.hdm.softwarepraktikum.client.gui;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.RowStyles;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.CellPreviewEvent.Handler;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.client.Project_19.CurrentPerson;
import de.hdm.softwarepraktikum.shared.ShoppingListAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.ListItem;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.bo.ShoppingList;
import de.hdm.softwarepraktikum.shared.bo.Store;

/**
 * Die Klasse ShowShoppingListForm bildet die Vorlage um eine ShoppingList
 * anzuzeigen. Hier ist die Hier können neuen ListItem erzeugt und der
 * ShoppingList hinzugefügt, wie auch entfernt werden.
 * 
 * @autor Vincent Wengert
 * @version 1.0
 * @see de.hdm.softwarepraktikum.client.Project_19
 */

public class ShowShoppingListForm extends VerticalPanel {

	private Person p = CurrentPerson.getPerson();

	private ShoppingListAdministrationAsync administration = ClientsideSettings.getShoppinglistAdministration();

	private HorizontalPanel formHeaderPanel = new HorizontalPanel();
	private VerticalPanel shoppingListPanel = new VerticalPanel();
	private HorizontalPanel topButtonsPanel = new HorizontalPanel();

	private HorizontalPanel bottomButtonsPanel = new HorizontalPanel();
	private HorizontalPanel listItemButtonPanel = new HorizontalPanel();

	private Button addListItemButton = new Button();
	private Button deleteListItemButton = new Button();
	private Button editButton = new Button();
	private Button deleteButton = new Button();
	private Button confirmButton = new Button("\u2714");
	private Button cancelButton = new Button("\u2716");
	private CheckBox myItemsCheckbox = new CheckBox("Nur meine Items anzeigen");

	private Label infoTitleLabel = new Label();

	private TextBox shoppinglistNameBox = new TextBox();

	private CellTable<ListItem> cellTable = null;
	private ListItemKeyProvider keyProvider = null;
	private ListDataProvider<ListItem> dataProvider = new ListDataProvider<ListItem>();
	private MultiSelectionModel<ListItem> multiSelectionModel = null;
	private ArrayList<ListItem> productsToDisplay = null;
	private ArrayList<Store> allStores = new ArrayList<Store>();
	private ArrayList<Person> allPersons = new ArrayList<Person>();
	private ArrayList<Item> allItems = new ArrayList<Item>();
	private ArrayList<ListItem> allListItems = new ArrayList<ListItem>();

	private Grid additionalInfoGrid = new Grid(2, 2);

	private CustomTreeModel ctm = null;

	private ShoppingList shoppingListToDisplay = new ShoppingList();
	private Integer selectedListitemIndex = null;

	private Boolean loadFavorites;

	private Group group = new Group();

	/**
	 * Der Pager der benutzt wird, wenn es mehr als 15 Einträge gibt
	 */
	@UiField(provided = true)
	SimplePager pager;

	/**
	 * Bei der Instanziierung der <code>ShowShoppingListForm</code>, werden die
	 * ClickHandler den Buttons und dem Panel hinzugefügt
	 */
	public ShowShoppingListForm() {
		addListItemButton.addClickHandler(new AddListItemClickHandler());
		deleteListItemButton.addClickHandler(new DeleteListItemClickHandler());
		editButton.addClickHandler(new EditClickHandler());
		confirmButton.addClickHandler(new ConfirmClickHandler());
		cancelButton.addClickHandler(new CancelClickHandler());
		deleteButton.addClickHandler(new DeleteShoppingListClickHandler());
		myItemsCheckbox.addClickHandler(new myItemsClickHandler());
		topButtonsPanel.add(editButton);
		topButtonsPanel.add(deleteButton);
		formHeaderPanel.add(shoppinglistNameBox);
		listItemButtonPanel.add(addListItemButton);
		listItemButtonPanel.add(deleteListItemButton);
		bottomButtonsPanel.add(confirmButton);
		bottomButtonsPanel.add(cancelButton);
	}

	/**
	 * ***************************************************************************
	 * ABSCHNITT der Methoden
	 * ***************************************************************************
	 */

	/**
	 * In dieser Methode werden die Widgets der Form hinzugefügt. Außerdem findet
	 * hier die Formatierungen der Widgets statt.
	 */
	public void onLoad() {
		loadListitems();
		this.setWidth("100%");
		formHeaderPanel.setStylePrimaryName("formHeaderPanel");
		infoTitleLabel.setStylePrimaryName("infoTitleLabel");
		bottomButtonsPanel.setStylePrimaryName("bottomButtonsPanel");
		listItemButtonPanel.setStylePrimaryName("addListItemPanel");
		additionalInfoGrid.setStylePrimaryName("additionalInfoGrid");

		topButtonsPanel.setStylePrimaryName("topButtonsPanel2");
		bottomButtonsPanel.setSpacing(20);

		addListItemButton.setStylePrimaryName("addListItemButton");
		editButton.setStylePrimaryName("editButton");
		deleteButton.setStylePrimaryName("deleteButton");
		deleteListItemButton.setStylePrimaryName("removeListItemButton");
		addListItemButton.setVisible(true);
		myItemsCheckbox.setStylePrimaryName("myItemsCheckbox");

		shoppinglistNameBox.setMaxLength(10);
		shoppinglistNameBox.setVisible(false);
		shoppinglistNameBox.setStylePrimaryName("shoppinglistNameBox");

		confirmButton.setVisible(false);
		cancelButton.setVisible(false);
		cancelButton.setStylePrimaryName("cancelButton");
		confirmButton.setStylePrimaryName("confirmButton");
		cancelButton.setPixelSize(130, 40);
		confirmButton.setPixelSize(130, 40);

		formHeaderPanel.add(infoTitleLabel);
		formHeaderPanel.add(topButtonsPanel);

		this.add(formHeaderPanel);

		additionalInfoGrid.setCellSpacing(5);
		this.add(additionalInfoGrid);
		this.setCellHorizontalAlignment(additionalInfoGrid, ALIGN_CENTER);

		keyProvider = new ListItemKeyProvider();
		multiSelectionModel = new MultiSelectionModel<ListItem>(keyProvider);
		cellTable = new CellTable<ListItem>();
		dataProvider.addDataDisplay(cellTable);
		cellTable.setStylePrimaryName("cellTable");
		cellTable.setSelectionModel(multiSelectionModel,
				DefaultSelectionEventManager.<ListItem>createCheckboxManager());

		// Eventhandler um die aktuell ausgewählte Zeile zu setzen
		cellTable.addCellPreviewHandler(new CellPreviewEvent.Handler<ListItem>() {
			@Override
			public void onCellPreview(CellPreviewEvent<ListItem> event) {
				if (BrowserEvents.CLICK.equalsIgnoreCase(event.getNativeEvent().getType())) {
					ShowShoppingListForm.this.selectedListitemIndex = event.getIndex();
				}
			}
		});

		// Doppelklick Event um das ausgewählte ListItem zu bearbeiten
		cellTable.addDomHandler(new DoubleClickHandler() {
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				ListItemDialog lid = new ListItemDialog(shoppingListToDisplay);
				if (selectedListitemIndex != null) {
					lid.setGroup(group);
					lid.setShoppingList(shoppingListToDisplay);
					lid.setShowShoppingListForm(ShowShoppingListForm.this);
					lid.displayListItem(allListItems.get(selectedListitemIndex), shoppingListToDisplay, group, true);
					lid.show();
				}
			}
		}, DoubleClickEvent.getType());

		/**
		 * In diesem Abschnitt werden alle Reihen der Tabelle befüllt
		 */
		TextColumn<ListItem> nameColumn = new TextColumn<ListItem>() {
			@Override
			public String getValue(ListItem i) {
				String itemName = new String("leer");
				for (Item item : allItems) {
					if (item.getId() == i.getItemId()) {
						itemName = item.getName();
					}
				}
				return itemName;
			}
		};

		TextColumn<ListItem> amountColumn = new TextColumn<ListItem>() {
			@Override
			public String getValue(ListItem i) {
				return Double.toString(i.getAmount()) + " " + i.getUnit();

			}
		};

		TextColumn<ListItem> storeColumn = new TextColumn<ListItem>() {
			@Override
			public String getValue(ListItem i) {
				String storeName = new String("leer");
				for (Store s : allStores) {
					if (s.getId() == i.getStoreID()) {
						storeName = s.getName() + String
								.valueOf(" " + s.getPostcode() + " " + s.getStreet() + " " + s.getHouseNumber());
					}
				}
				return storeName;
			}
		};

		TextColumn<ListItem> personColumn = new TextColumn<ListItem>() {
			@Override
			public String getValue(ListItem i) {
				String personName = new String("leer");
				for (Person p : allPersons) {

					if (p.getId() == i.getBuyerID()) {
						personName = p.getName();
					}
				}
				return personName;
			}
		};

		Column<ListItem, Boolean> checkColumn = new Column<ListItem, Boolean>(new CheckboxCell(true, true)) {
			@Override
			public Boolean getValue(ListItem object) {
				// Get the value from the selection model
				return multiSelectionModel.isSelected(object);
			}
		};
		checkColumn.setFieldUpdater(new FieldUpdater<ListItem, Boolean>() {

			@Override
			public void update(int arg0, ListItem arg1, Boolean arg2) {
				// TODO Auto-generated method stub
				administration.checkListItem(arg1.getId(), arg2, new CheckListItemCallback());
			}
		});

		// Die Spalten werden der Tabelle hinzugefügt
		cellTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		cellTable.setColumnWidth(checkColumn, 20, Unit.PX);
		cellTable.addColumn(nameColumn, "Artikel");
		cellTable.addColumn(amountColumn, "Menge");
		cellTable.addColumn(storeColumn, "Laden");
		cellTable.addColumn(personColumn, "Verantwortlicher");

		/*
		 * Store Column sortierbarkeit aktivieren.
		 */
		storeColumn.setSortable(true);

		/*
		 * ColumnSortEvent.ListHandler hinzufügen
		 */
		ListHandler<ListItem> columnSortHandler2 = new ListHandler<ListItem>(dataProvider.getList());
		columnSortHandler2.setComparator(storeColumn, new Comparator<ListItem>() {
			@Override
			public int compare(ListItem o1, ListItem o2) {
				if (o1 == o2) {
					return 0;
				}

				// Die Store columns vergleichen.
				String a = String.valueOf(o1.getStoreID());

				String b = String.valueOf(o2.getStoreID());

				if (o1 != null) {
					if (o2 != null) {
						return a.compareTo(b);
					}
				}
				return -1;
			}
		});

		cellTable.addColumnSortHandler(columnSortHandler2);

		cellTable.getColumnSortList().push(storeColumn);

		shoppingListPanel.add(cellTable);

		// Create a Pager to control the table.
		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
		pager.setDisplay(cellTable);
		pager.setPageSize(10);
		shoppingListPanel.add(pager);
		shoppingListPanel.setCellHorizontalAlignment(pager, ALIGN_CENTER);

		this.add(shoppingListPanel);
		this.setCellHorizontalAlignment(shoppingListPanel, ALIGN_CENTER);

		this.add(listItemButtonPanel);
		this.setCellHorizontalAlignment(listItemButtonPanel, ALIGN_CENTER);

		this.add(bottomButtonsPanel);

		this.setCellHorizontalAlignment(bottomButtonsPanel, ALIGN_CENTER);
	}

	/**
	 * Setzt das aktuelle <code>CustomTreeModel</code>
	 * 
	 * @param model Das zu setzende <code>CustomTreeModel</code>
	 */
	public void setCtm(CustomTreeModel model) {
		this.ctm = model;
	}

	/**
	 * Setzt die aktuell ausgewählte <code>Group</code>
	 * 
	 * @param g Die zu setzende <code>Group</code>
	 */
	public void setGroup(Group g) {
		this.group = g;
	}

	/**
	 * Methode um die aktuell ausgewählte Gruppe zu erhalten
	 * 
	 * return die Gruppe in der die Einkaufsliste sich befindet
	 */
	public Group returnGroup() {
		return this.group;
	}

	/**
	 * Methode um alle Listitems in ShoppingListForm zu bekommen
	 * 
	 * return Alle Artikel werden zurückgegeben
	 */
	public ArrayList<ListItem> getAllListItems() {
		return this.allListItems;
	}

	/**
	 * Load Methode, um wenn die List das erste mal angelegt wird und die in der
	 * Gruppe selektierten Favoriten hinzugefügt werden sollen.
	 */
	public void loadFavoriteItems() {
		ctm.setLoadFavoriteItems(false);
		administration.getAllFavoriteListItemsbyGroup(group, new getAllFavoriteListItemsCallback());
	}

	/**
	 * Load Methode, um alle <code>Store</code>, <code>Person</code> und
	 * <code>Item</code> Instanzen aus der Datenbank zu erhalten
	 */
	private void loadListitems() {
		administration.getAllStores(new getAllStoresCallback());
		administration.getAllPersons(new getAllPersonsCallback());
		administration.getAllItems(new getAllItemsCallback());
	}

	/**
	 * Methode, um den DataProvider zu leeren
	 */
	public void updateRemovedListItem() {
		dataProvider.getList().clear();
	}

	/**
	 * Methode, um das <code>ListItem</code> zu aktualisieren
	 */
	public void updateListItem() {
		selectedListitemIndex = null;
		dataProvider.getList().clear();
		administration.getAllListItemsByShoppingLists(shoppingListToDisplay,
				new getAllListItemsbyShoppingListCallback());
	}

	/**
	 * Setzt die aktuell ausgewählte <code>ShoppingList</code>
	 * 
	 * @param shoppinglist Die zu setzende <code>ShoppingList</code>
	 * @param initial      Setzt ob die Einkaufsliste initial angelegt wird
	 */
	public void setSelected(ShoppingList shoppinglist, Boolean initial) {
		if (shoppinglist != null) {
			selectedListitemIndex = null;
			ShowShoppingListForm.this.shoppingListPanel.clear();
			dataProvider.getList().clear();
			shoppingListToDisplay = shoppinglist;
			shoppingListToDisplay.setGroupID(shoppinglist.getGroupID());
			infoTitleLabel.setText(shoppinglist.getTitle());

			infoTitleLabel.setVisible(true);
			editButton.setVisible(true);

			if (initial == true) {
				loadFavoriteItems();
			}

			additionalInfoGrid.setVisible(true);
			additionalInfoGrid.setWidget(0, 0,
					new Label("Erstelldatum: " + shoppingListToDisplay.getCreationDateString()));
			additionalInfoGrid.setWidget(0, 1,
					new HTML("Änderungsdatum: " + shoppingListToDisplay.getChangeDateString()));
			additionalInfoGrid.setWidget(1, 0, myItemsCheckbox);
		} else {
			this.clear();
		}
	}

	/**
	 * Fügt das <code>ListItem</code> der <code>ShoppingList</code> hinzu
	 * 
	 * @param li Das zu setzenden <code>ListItem</code> Objekt
	 */
	public void AddListItem(ListItem li, Boolean createNewListItem) {
		shoppingListToDisplay.setChangedate(new Timestamp(System.currentTimeMillis()));
		administration.updateShoppingList(shoppingListToDisplay, new UpdateShoppinglistCallback());

		if (createNewListItem == true) {
			administration.getAllItems(new AsyncCallback<ArrayList<Item>>() {
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					Notification.show("Artikel konnten leider nicht geladen werden" + caught.toString());
				}

				@Override
				public void onSuccess(ArrayList<Item> result) {
					// TODO Auto-generated method stub
					allItems = result;
					dataProvider.getList().add(li);
					dataProvider.refresh();
				}
			});
		} else {
			dataProvider.getList().add(li);
			dataProvider.refresh();
		}
	}

	/**
	 * Methode um alle Artikel auf die aktuell angemeldete Person zu filtern
	 */
	private ArrayList<ListItem> myListItems() {
		ArrayList<ListItem> myItems = new ArrayList<ListItem>();
		for (ListItem listitem : allListItems) {
			if (listitem.getBuyerID() == p.getId()) {
				myItems.add(listitem);
			}
		}
		return myItems;
	}

	// KeyProvider für die Celltable
	private class ListItemKeyProvider implements ProvidesKey<ListItem> {
		@Override
		public Object getKey(ListItem item) {
			return (item == null) ? null : item.getId();
		}
	}

	/**
	 * Sobald der Button ausgewahlt wird ein <code>ListItemDialog</code> erstellt,
	 * durch den neue <code>ListItem</code> Objekte erzeugt und der Shoppinglist
	 * hinzugefugt werden.
	 */
	private class AddListItemClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			ListItemDialog lid = new ListItemDialog(shoppingListToDisplay);
			lid.setGroup(group);
			lid.setShoppingList(shoppingListToDisplay);
			lid.setShowShoppingListForm(ShowShoppingListForm.this);
			lid.show();
		}
	}

	/**
	 * Sobald der Button ausgewahlt wird werden ein <code>ListItem</code> Objekt dem
	 * System und der Shoppinglist entfernt.
	 */
	private class DeleteListItemClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (selectedListitemIndex != null) {
				if (Window.confirm("Wollen Sie den Artikel wirklich entfernen?") == true) {
					administration.deleteListItem(allListItems.get(selectedListitemIndex),
							new deleteListItemCallback());
				} else {
					selectedListitemIndex = null;
				}
			} else {
				Window.alert("Bitte selektieren sie einen Artikel in der Liste");
			}
		}
	}

	/**
	 * Sobald der Button ausgewahlt wird werden ein <code>ListItem</code> Objekt dem
	 * System und der Shoppinglist entfernt.
	 */
	private class EditClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			additionalInfoGrid.setVisible(false);
			shoppinglistNameBox.setVisible(true);
			shoppinglistNameBox.setText(infoTitleLabel.getText());
			infoTitleLabel.setVisible(false);

			listItemButtonPanel.setVisible(false);
			confirmButton.setVisible(true);
			cancelButton.setVisible(true);
			editButton.setVisible(false);
			myItemsCheckbox.setVisible(false);

			formHeaderPanel.setCellVerticalAlignment(shoppinglistNameBox, ALIGN_BOTTOM);
			formHeaderPanel.setCellHorizontalAlignment(shoppinglistNameBox, ALIGN_LEFT);

		}
	}

	/**
	 * Sobald der Button ausgewahlt wird wird die <code>ShoppingList</code> nach
	 * einer Bestätigung gelöscht
	 */
	private class DeleteShoppingListClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			if (Window.confirm("Wollen Sie wirklich entfernen?") == true) {
				administration.deleteShoppingList(shoppingListToDisplay, new DeleteShoppinglistCallback());
			}
		}
	}

	/**
	 * Clickhandler um die Änderungen am Titel der Einkaufsliste zu übernehmen
	 */
	private class ConfirmClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			infoTitleLabel.setText(shoppinglistNameBox.getText());
			shoppinglistNameBox.setVisible(false);
			infoTitleLabel.setVisible(true);
			confirmButton.setVisible(false);
			cancelButton.setVisible(false);
			editButton.setVisible(true);
			listItemButtonPanel.setVisible(true);
			myItemsCheckbox.setVisible(true);
			shoppingListToDisplay.setTitle(shoppinglistNameBox.getText());
			shoppingListToDisplay.setChangedate(new Timestamp(System.currentTimeMillis()));

			administration.updateShoppingList(shoppingListToDisplay, new UpdateShoppinglistCallback());
		}
	}

	/**
	 * Clickhandler um die Änderungen am Titel der Einkaufsliste abzubrechen
	 */
	private class CancelClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			shoppinglistNameBox.setVisible(false);
			infoTitleLabel.setVisible(true);
			confirmButton.setVisible(false);
			cancelButton.setVisible(false);
			editButton.setVisible(true);
			listItemButtonPanel.setVisible(true);
			myItemsCheckbox.setVisible(true);

			additionalInfoGrid.setVisible(true);
			additionalInfoGrid.setWidget(0, 0,
					new Label("Erstelldatum: " + shoppingListToDisplay.getCreationDateString()));
			additionalInfoGrid.setWidget(0, 1,
					new HTML("Änderungsdatum: " + shoppingListToDisplay.getChangeDateString()));
			additionalInfoGrid.setWidget(1, 0, myItemsCheckbox);

		}
	}

	/**
	 * Clickhandler um die Artikel auf die angemeldete Person zu beschränken
	 */
	private class myItemsClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			dataProvider.getList().clear();
			if (myItemsCheckbox.getValue() == true) {
				for (ListItem l : myListItems()) {
					dataProvider.getList().add(l);
				}
			} else {
				for (ListItem l : allListItems) {
					dataProvider.getList().add(l);
				}
			}
			dataProvider.refresh();
		}
	}

	/**
	 * ***************************************************************************
	 * ABSCHNITT der Callbacks
	 * ***************************************************************************
	 */

	/**
	 * Private Klasse des Callback um alle <code>Store</code> Instanzen aus dem
	 * System zu bekommen.
	 */
	private class getAllStoresCallback implements AsyncCallback<ArrayList<Store>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(ArrayList<Store> result) {
			// TODO Auto-generated method stub
			allStores = result;
		}
	}

	/**
	 * Private Klasse des Callback um alle <code>Items</code> Instanzen aus dem
	 * System zu bekommen.
	 */
	private class getAllItemsCallback implements AsyncCallback<ArrayList<Item>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(ArrayList<Item> result) {
			// TODO Auto-generated method stub
			allItems = result;
			administration.getAllListItemsByShoppingLists(shoppingListToDisplay,
					new getAllListItemsbyShoppingListCallback());
		}
	}

	/**
	 * Private Klasse des Callback um alle <code>Person</code> Instanzen aus dem
	 * System zu bekommen.
	 */
	private class getAllPersonsCallback implements AsyncCallback<ArrayList<Person>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(ArrayList<Person> result) {
			// TODO Auto-generated method stub
			allPersons = result;

		}
	}

	/**
	 * Private Klasse des Callback um eine <code>ShoppingList</code> Instanzen aus
	 * dem System zu löschen.
	 */
	private class DeleteShoppinglistCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(Void result) {
			Notification.show("Einkaufsliste wurde erfolgreich entfernt");
			ctm.updateRemovedShoppingList(shoppingListToDisplay);
		}
	}

	/**
	 * Private Klasse des Callback um ein <code>ListItem</code> Instanz aus dem
	 * System abzuhaken.
	 */
	private class CheckListItemCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Der Artikel konnte leider nicht abgehakt werden:" + caught.toString());
		}

		@Override
		public void onSuccess(Void result) {
			// TODO Auto-generated method stub
			shoppingListToDisplay.setChangedate(new Timestamp(System.currentTimeMillis()));
			administration.updateShoppingList(shoppingListToDisplay, new UpdateShoppinglistCallback());
			// Notification.show("Artikel wurde erfolgreich abgehakt");
		}
	}

	/**
	 * Private Klasse des Callback um eine <code>ShoppingList</code> Instanzen aus
	 * dem System zu aktualisieren.
	 */
	private class UpdateShoppinglistCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(Void result) {
			administration.findShoppingListbyId(shoppingListToDisplay.getId(), new findShoppingListbyIdCallback());
			ctm.updateShoppingList(shoppingListToDisplay);
		}
	}

	/**
	 * Private Klasse des Callback um eine <code>ShoppingList</code> Instanzen aus
	 * dem System zu bekommen.
	 */
	private class findShoppingListbyIdCallback implements AsyncCallback<ShoppingList> {
		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onSuccess(ShoppingList result) {
			// TODO Auto-generated method stub
			additionalInfoGrid.setVisible(true);
			additionalInfoGrid.setWidget(0, 0, new Label("Erstelldatum: " + result.getCreationDateString()));
			additionalInfoGrid.setWidget(0, 1, new HTML("Änderungsdatum: " + result.getChangeDateString()));
			additionalInfoGrid.setWidget(1, 0, myItemsCheckbox);
		}
	}

	/**
	 * Private Klasse des Callback um alle <code>Items</code> Instanzen aus einer
	 * ShoppingList aus System zu bekommen.
	 */
	private class getAllListItemsbyShoppingListCallback implements AsyncCallback<ArrayList<ListItem>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(ArrayList<ListItem> result) {
			// TODO Auto-generated method stub
			allListItems = result;
			for (ListItem l : result) {
				dataProvider.getList().add(l);
				if (l.getChecked() == true) {
					multiSelectionModel.setSelected(l, true);
				}
			}
			dataProvider.refresh();
		}
	}

	/**
	 * Private Klasse des Callback um alle <code>Item</code> Instanzen aus einer
	 * ShoppingList aus System zu bekommen die als Favorit markiert sind.
	 */
	private class getAllFavoriteListItemsCallback implements AsyncCallback<ArrayList<Item>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Notification.show("Standardartikel konnten nicht geladen werden" + caught.toString());
		}

		@Override
		public void onSuccess(ArrayList<Item> result) {
			// TODO Auto-generated method stub
			if (result.isEmpty() == false) {

				for (Item i : result) {
					StandardListItemDialog slid = new StandardListItemDialog();
					slid.setShoppingList(shoppingListToDisplay);
					slid.setShowShoppingListForm(ShowShoppingListForm.this);
					slid.displayListItem(i, shoppingListToDisplay, group);
					slid.show();
				}
				Notification.show("Favorisierte Artikel wurden hinzugefügt");
			}
		}
	}

	/**
	 * Private Klasse des Callback um eine <code>ListItem</code> Instanzen aus einer
	 * ShoppingList aus System zu bekommen zu entfernen.
	 */
	private class deleteListItemCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(Void result) {
			shoppingListToDisplay.setChangedate(new Timestamp(System.currentTimeMillis()));
			administration.updateShoppingList(shoppingListToDisplay, new UpdateShoppinglistCallback());
			int index = selectedListitemIndex;
			if (selectedListitemIndex != null) {
				dataProvider.getList().remove(index);
				allListItems.remove(index);
				dataProvider.refresh();
				cellTable.redraw();
				selectedListitemIndex = null;
			}
			Notification.show("Artikel wurde entfernt.");
		}
	}
}
