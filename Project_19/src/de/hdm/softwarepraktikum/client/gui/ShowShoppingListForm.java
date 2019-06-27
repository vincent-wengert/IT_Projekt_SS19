
package de.hdm.softwarepraktikum.client.gui;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
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

public class ShowShoppingListForm extends VerticalPanel {
	private Person p = CurrentPerson.getPerson();
	
	private ShoppingListAdministrationAsync administration = ClientsideSettings.getShoppinglistAdministration();
	private Person currentPerson = CurrentPerson.getPerson();
	
	private HorizontalPanel formHeaderPanel = new HorizontalPanel();
	private HorizontalPanel shoppingListPanel = new HorizontalPanel();
	private HorizontalPanel topButtonsPanel = new HorizontalPanel();

	private HorizontalPanel deleteListItemPanel = new HorizontalPanel();
	private HorizontalPanel bottomButtonsPanel = new HorizontalPanel();
	private HorizontalPanel addListItemPanel = new HorizontalPanel();

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
	private ArrayList<ListItem> checkedListItems = new ArrayList<ListItem>();
	
	private Grid additionalInfoGrid = new Grid(2, 2);

	private CustomTreeModel ctm = null;
	
	private Boolean loadFavorites;
	
	ShoppingList shoppingListToDisplay = null;
	Integer selectedListitemIndex = null;
	Group group = new Group();
	

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
		addListItemPanel.add(addListItemButton);
		deleteListItemPanel.add(deleteListItemButton);
		bottomButtonsPanel.add(confirmButton);
		bottomButtonsPanel.add(cancelButton);
	}

	public void onLoad() {
		
		loadListitems();
		this.setWidth("100%");
		deleteListItemPanel.setStylePrimaryName("bottomButtonsPanel");
		
		formHeaderPanel.setStylePrimaryName("formHeaderPanel");
		infoTitleLabel.setStylePrimaryName("infoTitleLabel");
		bottomButtonsPanel.setStylePrimaryName("bottomButtonsPanel");
		addListItemPanel.setStylePrimaryName("addListItemPanel");
		additionalInfoGrid.setStylePrimaryName("additionalInfoGrid");
		
		bottomButtonsPanel.setSpacing(20);

		addListItemButton.setStylePrimaryName("addListItemButton");
		editButton.setStylePrimaryName("editButton");
		deleteButton.setStylePrimaryName("deleteButton");
		deleteListItemButton.setStylePrimaryName("deleteButton");
		addListItemButton.setHeight("8vh");
		addListItemButton.setWidth("8vh");
		addListItemButton.setVisible(true);
		myItemsCheckbox.setStylePrimaryName("myItemsCheckbox");
		
		editButton.setWidth("8vh");
		editButton.setHeight("8vh");
		
		deleteButton.setWidth("8vh");
		deleteButton.setHeight("8vh");
		
		deleteListItemButton.setWidth("8vh");
		deleteListItemButton.setHeight("8vh");

		formHeaderPanel.setHeight("8vh");
		formHeaderPanel.setWidth("100%");
		
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

		formHeaderPanel.setCellVerticalAlignment(infoTitleLabel, ALIGN_BOTTOM);
		formHeaderPanel.setCellVerticalAlignment(topButtonsPanel, ALIGN_BOTTOM);
		formHeaderPanel.setCellHorizontalAlignment(topButtonsPanel, ALIGN_RIGHT);
		
		bottomButtonsPanel.setCellHorizontalAlignment(myItemsCheckbox, ALIGN_CENTER);
		
		deleteListItemPanel.setCellHorizontalAlignment(deleteListItemButton, ALIGN_CENTER);
		
		topButtonsPanel.setCellHorizontalAlignment(editButton, ALIGN_LEFT);
		topButtonsPanel.setCellHorizontalAlignment(deleteButton, ALIGN_RIGHT);
		
		this.add(formHeaderPanel);
		
		additionalInfoGrid.setCellSpacing(5);
		this.add(additionalInfoGrid);
		this.setCellHorizontalAlignment(additionalInfoGrid, ALIGN_CENTER);

		keyProvider = new ListItemKeyProvider();
		multiSelectionModel = new MultiSelectionModel<ListItem>(keyProvider);
		cellTable = new CellTable<ListItem>();
		dataProvider.addDataDisplay(cellTable);
	
		cellTable.setSelectionModel(multiSelectionModel,
				DefaultSelectionEventManager.<ListItem>createCheckboxManager());
		
		//Get the selected Row and set the Index for updating or deleting the Listitem in this row
		  cellTable.addCellPreviewHandler(event -> {
		    if (BrowserEvents.CLICK.equalsIgnoreCase(event.getNativeEvent().getType())) {
		        this.selectedListitemIndex = event.getIndex();
		    }
		});
		  
		  //Set a Double ClickHandler for editing
		    cellTable.addDomHandler(new DoubleClickHandler() {

		        @Override
		        public void onDoubleClick(DoubleClickEvent event) {
				  	ListItemDialog lid = new ListItemDialog();
				  	if(selectedListitemIndex!=null) {
				  	lid.setGroup(group);
					lid.setShoppingList(shoppingListToDisplay);
					lid.setShowShoppingListForm(ShowShoppingListForm.this);
					lid.displayListItem(allListItems.get(selectedListitemIndex), shoppingListToDisplay, group, true);
					lid.show();
				  	}
		        }
		    }, DoubleClickEvent.getType());
		    

		ListHandler<ListItem> sortHandler = new ListHandler<ListItem>(null);

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
				return Double.toString(i.getAmount());
			}
		};

		TextColumn<ListItem> unitColumn = new TextColumn<ListItem>() {
			@Override
			public String getValue(ListItem i) {
				return i.getUnit();
			}
		};

		TextColumn<ListItem> storeColumn = new TextColumn<ListItem>() {
			@Override
			public String getValue(ListItem i) {
				String storeName = new String("leer");
				for (Store s : allStores) {
					if (s.getId() == i.getStoreID()) {
						storeName = s.getName();
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
		
		
		Column<ListItem, Boolean> checkColumn = new Column<ListItem, Boolean>( new CheckboxCell(true,true)) {
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
		
		
		cellTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		cellTable.setColumnWidth(checkColumn, 40, Unit.PX);
		cellTable.addColumn(nameColumn, "Artikel");
		cellTable.addColumn(amountColumn, "Menge");
		cellTable.addColumn(unitColumn, "Einheit");
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
				
				//Die Store columns vergleichen.
				
				String a = new String();
				a = Integer.toString(o1.getStoreID());
				
				String b = new String();
				b = Integer.toString(o2.getStoreID());
				
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
		
		/*
		 * Person Column sortierbarkeit aktivieren.
		 */
		
		personColumn.setSortable(true);
		
		/*
		 * ColumnSortEvent.ListHandler hinzufügen
		 */
		
		ListHandler<ListItem> columnSortHandler3 = new ListHandler<ListItem>(dataProvider.getList());
		columnSortHandler3.setComparator(personColumn, new Comparator<ListItem>() {
			@Override
			public int compare(ListItem o1, ListItem o2) {
				if (o1 == o2) {
					return 0;
				}
				
				//Die Person columns vergleichen.
				
				String a = String.valueOf(o1.getBuyerID());
				
				String b = String.valueOf(o2.getBuyerID());
				
				if (o1 != null) {
					if (o2 != null) {
						return a.compareTo(b);
					
				}
				}
				return -1;
			}	
		});
		
		cellTable.addColumnSortHandler(columnSortHandler3);
		
		cellTable.getColumnSortList().push(personColumn);

		shoppingListPanel.add(cellTable);

		this.add(shoppingListPanel);
		this.setCellHorizontalAlignment(shoppingListPanel, ALIGN_CENTER);
			
		this.add(addListItemPanel);
		this.setCellHorizontalAlignment(addListItemPanel, ALIGN_CENTER);
		
		this.add(deleteListItemPanel);
		this.setCellHorizontalAlignment(deleteListItemPanel, ALIGN_CENTER);
		deleteListItemPanel.setVisible(false);
		
		this.add(bottomButtonsPanel);

		this.setCellHorizontalAlignment(bottomButtonsPanel, ALIGN_CENTER);
	}

	public ArrayList<ListItem> getAllListItems() {
		return this.allListItems;
	}
	
	public void loadFavoriteItems() {
		ctm.setLoadFavoriteItems(false);	
		administration.getAllFavoriteListItemsbyGroup(group, new getAllFavoriteListItemsCallback());
	}

	private void loadListitems() {
		administration.getAllStores(new getAllStoresCallback());
		administration.getAllPersons(new getAllPersonsCallback());
		administration.getAllItems(new getAllItemsCallback());
		}


	private class ListItemKeyProvider implements ProvidesKey<ListItem> {
		@Override
		public Object getKey(ListItem item) {
			return (item == null) ? null : item.getId();
		}
	}
	
	public void updateRemovedListItem() {
		dataProvider.getList().clear();
	}
	
	public void updateListItem() {
		selectedListitemIndex = null;
		dataProvider.getList().clear();
		administration.getAllListItemsByShoppingLists(shoppingListToDisplay, new getAllListItemsbyShoppingListCallback());	
	}


	public void setGroup(Group g) {
		this.group = g;
	}
	
	public Group returnGroup() {
		return this.group;
	}
	
	public void setSelected(ShoppingList sl, Boolean initial) {
		if (sl != null) {
			selectedListitemIndex = null;
			ShowShoppingListForm.this.shoppingListPanel.clear();
			dataProvider.getList().clear();
			shoppingListToDisplay = sl;
			infoTitleLabel.setText(sl.getTitle());
			
			infoTitleLabel.setVisible(true);
			editButton.setVisible(true);
			
			if(initial == true) {
				loadFavoriteItems();
			}
			
			additionalInfoGrid.setVisible(true);
			additionalInfoGrid.setWidget(0, 0, new Label("Erstelldatum: " + shoppingListToDisplay.getCreationDateString()));
			additionalInfoGrid.setWidget(0, 1, new HTML("Änderungsdatum: " + shoppingListToDisplay.getChangeDateString()));
			additionalInfoGrid.setWidget(1, 0, myItemsCheckbox);
		} else {
			this.clear();
		}
	}

	public void AddListItem(ListItem li) {
		shoppingListToDisplay.setChangedate(new Timestamp(System.currentTimeMillis()));
		administration.updateShoppingList(shoppingListToDisplay, new UpdateShoppinglistCallback());
		dataProvider.getList().add(li);
		allItems=null;
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
				dataProvider.refresh();
			}
		});
	}
	
	public ArrayList<ListItem> myListItems() {
		
		ArrayList<ListItem> myItems = new ArrayList<ListItem>();
		
		for (ListItem listitem: allListItems) {
			if (listitem.getBuyerID() == p.getId()) {
				myItems.add(listitem);
				}
		}
		return myItems;
	}
		

	/**
	 * Sobald der Button ausgewahlt wird ein <code>ListItemDialog</code> erstellt, durch den neue <code>ListItem</code> Objekte
	 * erzeugt und der Shoppinglist hinzugefugt werden.
	 */
	class AddListItemClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			ListItemDialog lid = new ListItemDialog();
			lid.setGroup(group);
			lid.setShoppingList(shoppingListToDisplay);
			lid.setShowShoppingListForm(ShowShoppingListForm.this);
			lid.show();
		}
	}
	
	/**
	 * Sobald der Button ausgewahlt wird werden ein <code>ListItem</code> Objekt
	 * dem System und der Shoppinglist entfernt.
	 */
	class DeleteListItemClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
		  	if(selectedListitemIndex!=null) {
		  		if(Window.confirm("Wollen Sie diesen Artikel wirklich entfernen?") == true) {
		  			administration.deleteListItem(allListItems.get(selectedListitemIndex), new deleteListItemCallback());				
		  			}else {
		  			selectedListitemIndex = null;
		  			}
			  	}else {
			  	Window.alert("Bitte selektieren sie ein Artikel in der Liste");
			  	}
		}
	}

	
	public class EditClickHandler implements ClickHandler{

		public void onClick(ClickEvent event) {
			additionalInfoGrid.setVisible(false);
			shoppinglistNameBox.setVisible(true);
			shoppinglistNameBox.setText(infoTitleLabel.getText());
			infoTitleLabel.setVisible(false);
			
			addListItemButton.setVisible(false);
			deleteListItemPanel.setVisible(true);
			confirmButton.setVisible(true);
			cancelButton.setVisible(true);
			editButton.setVisible(false);
			myItemsCheckbox.setVisible(false);
			
			formHeaderPanel.setCellVerticalAlignment(shoppinglistNameBox, ALIGN_BOTTOM);
			formHeaderPanel.setCellHorizontalAlignment(shoppinglistNameBox, ALIGN_LEFT);
			
		}
		
	}
	
	class DeleteShoppingListClickHandler implements ClickHandler {
		
		public void onClick(ClickEvent event) {
			if(Window.confirm("Wollen Sie wirklich entfernen?") == true) {
				administration.deleteShoppingList(shoppingListToDisplay, new DeleteShoppinglistCallback());
			}
		}
	}
	
	
	class ConfirmClickHandler implements ClickHandler {
		
		public void onClick(ClickEvent event) {
			infoTitleLabel.setText(shoppinglistNameBox.getText());
			shoppinglistNameBox.setVisible(false);
			infoTitleLabel.setVisible(true);
			confirmButton.setVisible(false);
			cancelButton.setVisible(false);
			editButton.setVisible(true);
			addListItemButton.setVisible(true);
			deleteListItemPanel.setVisible(false);
			myItemsCheckbox.setVisible(true);
			
			shoppingListToDisplay.setTitle(shoppinglistNameBox.getText());
			shoppingListToDisplay.setChangedate(new Timestamp(System.currentTimeMillis()));
			
			administration.updateShoppingList(shoppingListToDisplay, new UpdateShoppinglistCallback());
		}
	}
	
	class CancelClickHandler implements ClickHandler {
		
		public void onClick(ClickEvent event) {
			shoppinglistNameBox.setVisible(false);
			infoTitleLabel.setVisible(true);
			confirmButton.setVisible(false);
			cancelButton.setVisible(false);
			editButton.setVisible(true);
			addListItemButton.setVisible(true);
			myItemsCheckbox.setVisible(true);
			
			additionalInfoGrid.setVisible(true);
			additionalInfoGrid.setWidget(0, 0, new Label("Erstelldatum: " + shoppingListToDisplay.getCreationDateString()));
			additionalInfoGrid.setWidget(0, 1, new HTML("Änderungsdatum: " + shoppingListToDisplay.getChangeDateString()));
			additionalInfoGrid.setWidget(1, 0, myItemsCheckbox);
	
		}
	}
	
	class myItemsClickHandler implements ClickHandler {
		
		public void onClick(ClickEvent event) {
			dataProvider.getList().clear();
			if(myItemsCheckbox.getValue()== true) {
			for(ListItem l : myListItems()) {
				dataProvider.getList().add(l);
				}
			}else {
				for(ListItem l : allListItems) {
					dataProvider.getList().add(l);
					}
			}
			dataProvider.refresh();
		}
	}


	/**
	 * ListHandler mit dem in der CellTable die Liste sortiert wird
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
	 * ListHandler mit dem in der CellTable die Liste sortiert wird
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
	 * ListHandler mit dem in der CellTable die Liste sortiert wird
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
			administration.getAllListItemsByShoppingLists(shoppingListToDisplay, new getAllListItemsbyShoppingListCallback());	
		}
	}
	
	/**
	 * ListHandler mit dem in der CellTable die Liste sortiert wird
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
			Notification.show("Artikel wurde erfolgreich abgehakt");
		}
	}
	
	

	/**
	 * ListHandler mit dem in der CellTable die Liste sortiert wird
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
	
	
	private class findShoppingListbyIdCallback implements AsyncCallback<ShoppingList>{
			@Override
			public void onFailure(Throwable arg0) {
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
	 * ListHandler mit dem in der CellTable die Liste sortiert wird
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
			for(ListItem l : result) {
				dataProvider.getList().add(l);
				if(l.getChecked() == true) {
					multiSelectionModel.setSelected(l, true);
				}
			}
			dataProvider.refresh();
		}
	}

	private class getAllFavoriteListItemsCallback implements AsyncCallback<ArrayList<Item>>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Notification.show("Favorite Items konnten nicht geladen werden" + caught.toString());
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
	 * ListHandler mit dem in der CellTable die Liste sortiert wird
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
				dataProvider.refresh();
				cellTable.redraw();
				selectedListitemIndex = null;
			}
			Notification.show("Artikel wurde entfernt.");
			}
		}
	


	public void setCtm(CustomTreeModel model) {
		this.ctm = model;
	}
}

