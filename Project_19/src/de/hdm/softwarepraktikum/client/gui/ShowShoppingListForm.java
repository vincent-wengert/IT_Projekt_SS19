
package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.xerces.impl.dv.dtd.ListDatatypeValidator;
import org.eclipse.jdt.internal.compiler.classfmt.NonNullDefaultAwareTypeAnnotationWalker;
import org.omg.CORBA.PUBLIC_MEMBER;

import com.google.appengine.api.search.query.QueryParser.primitive_return;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.TableRowElement;
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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppingListAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.ListItem;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.bo.ShoppingList;
import de.hdm.softwarepraktikum.shared.bo.Store;

public class ShowShoppingListForm extends VerticalPanel {
	
	private ShoppingListAdministrationAsync administration = ClientsideSettings.getShoppinglistAdministration();
	
	private HorizontalPanel formHeaderPanel = new HorizontalPanel();
	private HorizontalPanel shoppingListPanel = new HorizontalPanel();
	private HorizontalPanel topButtonsPanel = new HorizontalPanel();
	private HorizontalPanel bottomButtonsPanel = new HorizontalPanel();
	private HorizontalPanel addListItemPanel = new HorizontalPanel();

	private Button addListItemButton = new Button();
	private Button editButton = new Button();
	private Button deleteButton = new Button();
	private Button confirmButton = new Button("\u2714");
	private Button cancelButton = new Button("\u2716");

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
	
	private Column<ListItem, String> editColumn;
	private Column<ListItem, String> deleteColumn;
	
	ShoppingList shoppingListToDisplay = new ShoppingList();
	Group group = new Group();
	
	

	public ShowShoppingListForm() {
		addListItemButton.addClickHandler(new AddListItemClickHandler());
		editButton.addClickHandler(new EditClickHandler());
		confirmButton.addClickHandler(new ConfirmClickHandler());
		cancelButton.addClickHandler(new CancelClickHandler());
		deleteButton.addClickHandler(new DeleteShoppingListClickHandler());
		topButtonsPanel.add(editButton);
		topButtonsPanel.add(deleteButton);
		formHeaderPanel.add(shoppinglistNameBox);
		addListItemPanel.add(addListItemButton);
		bottomButtonsPanel.add(confirmButton);
		bottomButtonsPanel.add(cancelButton);
		
		administration.getAllStores(new getAllStoresCallback());
		administration.getAllPersons(new getAllPersonsCallback());
		administration.getAllItems(new getAllItemsCallback());

		//administration.getAllCheckedItemsByShoppingList(shoppingListToDisplay, new getAllCheckedListItemsCallback());
	}

	public void onLoad() {
		this.setWidth("100%");

		formHeaderPanel.setStylePrimaryName("formHeaderPanel");
		infoTitleLabel.setStylePrimaryName("infoTitleLabel");
		bottomButtonsPanel.setStylePrimaryName("bottomButtonsPanel");
		addListItemPanel.setStylePrimaryName("addListItemPanel");
		
		bottomButtonsPanel.setSpacing(20);

		addListItemButton.setStylePrimaryName("addListItemButton");
		editButton.setStylePrimaryName("editButton");
		deleteButton.setStylePrimaryName("deleteButton");
		addListItemButton.setHeight("8vh");
		addListItemButton.setWidth("8vh");
		addListItemButton.setVisible(false);
		
		editButton.setWidth("8vh");
		editButton.setHeight("8vh");
		
		deleteButton.setWidth("8vh");
		deleteButton.setHeight("8vh");

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

		topButtonsPanel.setCellHorizontalAlignment(editButton, ALIGN_LEFT);
		topButtonsPanel.setCellHorizontalAlignment(deleteButton, ALIGN_RIGHT);
		
		this.add(formHeaderPanel);

		keyProvider = new ListItemKeyProvider();
		multiSelectionModel = new MultiSelectionModel<ListItem>(keyProvider);
		cellTable = new CellTable<ListItem>();
		dataProvider.addDataDisplay(cellTable);

		cellTable.setSelectionModel(multiSelectionModel,
				DefaultSelectionEventManager.<ListItem>createCheckboxManager());

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
				return i.getUnit().toString();
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
		
		ButtonCell editCell = new ButtonCell();
		
		editColumn = new Column<ListItem, String>(editCell) {
		  @Override
		  public String getValue(ListItem object) {
		    // The value to display in the button.
		    return "Bearbeiten";
		  }
		};
		//table.addColumn(buttonColumn, "Action");
		
		
		editColumn.setFieldUpdater(new FieldUpdater<ListItem, String>() {
			  @Override
			  public void update(int index, ListItem object, String value) {
				  
			    // The user clicked on the button for the passed auction.
				  	ListItemDialog lid = new ListItemDialog();
				  	lid.setGroup(group);
					lid.setShoppingList(shoppingListToDisplay);
					lid.setShowShoppingListForm(ShowShoppingListForm.this);
					lid.displayListItem(allListItems.get(index), shoppingListToDisplay, group, true);
					lid.show();
			  }
			});
		
		ButtonCell deleteCell = new ButtonCell();
		
		deleteColumn = new Column<ListItem, String>(deleteCell) {
		  @Override
		  public String getValue(ListItem object) {
		    // The value to display in the button.
		    return "Löschen";
		  }
		};
		
		
		deleteColumn.setFieldUpdater(new FieldUpdater<ListItem, String>() {
			  @Override
			  public void update(int index, ListItem object, String value) {
				  Window.alert("deleteCallback");
				  administration.deleteListItem(object, new deleteListItemCallback());
				  //cellTable.removeColumn(object);
			  }
			});
		
		
		Column<ListItem, Boolean> checkColumn = new Column<ListItem, Boolean>( new CheckboxCell(true,false)) {
			@Override
			public Boolean getValue(ListItem object) {
				// Get the value from the selection model
				return multiSelectionModel.isSelected(object);
				//return object.getChecked();
				
			}
		};
		checkColumn.setFieldUpdater(new FieldUpdater<ListItem, Boolean>() {
	        @Override
	        public void update(int index, ListItem object, Boolean value) {
	        object.setChecked(value);
	        administration.checkListItem(object.getId(), value, new CheckListItemCallback());
	        }
	    });
		
		
		
			
//			Dann, um einen Gegenstand zu wählen (und das zugehörige Markierungsfeld automatisch geprüft), müssen Sie einfach tun:

//			multiselectionModel.setSelected(item, true);
//			und Sie können in ähnlicher Weise erhalten Sie die Menge aller ausgewählten Elemente mit selectionModel.getSelectedSet()
		
		cellTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		cellTable.setColumnWidth(checkColumn, 40, Unit.PX);
		cellTable.addColumn(nameColumn, "Artikel");
		cellTable.addColumn(amountColumn, "Menge");
		cellTable.addColumn(unitColumn, "Einheit");
		cellTable.addColumn(storeColumn, "Laden");
		cellTable.addColumn(personColumn, "Verantwortlicher");
		

		nameColumn.setSortable(true);

		ListHandler<ListItem> columnSortHandler = new ListHandler<ListItem>(dataProvider.getList());
		columnSortHandler.setComparator(nameColumn, new Comparator<ListItem>() {
			@Override
			public int compare(ListItem o1, ListItem o2) {
				if (o1 == o2) {
					return 0;
				}

				// Compare the name columns.
				if (o1 != null) {
					if (o2 != null) {
						return o1.getName().compareTo(o2.getName());
					} else {
						return 1;
					}
				}
				return -1;
			}
		});
		
		cellTable.addColumnSortHandler(columnSortHandler);

		cellTable.getColumnSortList().push(nameColumn);

		shoppingListPanel.add(cellTable);

		this.add(shoppingListPanel);
			
		this.add(addListItemPanel);
		this.setCellHorizontalAlignment(addListItemPanel, ALIGN_CENTER);
		
		this.add(bottomButtonsPanel);
		this.setCellHorizontalAlignment(bottomButtonsPanel, ALIGN_CENTER);
	}
	

	private void loadListitems() {
		administration.getAllListItemsByShoppingLists(shoppingListToDisplay, new getAllListItemsbyShoppingListCallback());
	}


	private class ListItemKeyProvider implements ProvidesKey<ListItem> {
		@Override
		public Object getKey(ListItem item) {
			return (item == null) ? null : item.getId();
		}
	}

	public void setGroup(Group g) {
		this.group = g;
	}
	
	public Group returnGroup() {
		return this.group;
	}
	
	public void setSelected(ShoppingList sl) {
		if (sl != null) {
			shoppingListToDisplay = sl;
			productsToDisplay = sl.getShoppinglist();
			List<ListItem> list = dataProvider.getList();
			infoTitleLabel.setText(sl.getTitle());
			for (ListItem p : productsToDisplay) {
				list.add(p);
			}
		}
	}

	public void AddListItem(ListItem li) {
		dataProvider.getList().add(li);
		allItems=null;
		administration.getAllItems(new AsyncCallback<ArrayList<Item>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(ArrayList<Item> result) {
				// TODO Auto-generated method stub
				allItems = result;
				dataProvider.refresh();
			}
		});
	}

	/**
	 * Sobald der Button ausgewahlt wird werden neue <code>ListItem</code> Objekte
	 * erzeugt und der Shoppinglist hinzugefugt.
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

	
	public class EditClickHandler implements ClickHandler{

		public void onClick(ClickEvent event) {
			shoppinglistNameBox.setVisible(true);
			shoppinglistNameBox.setText(infoTitleLabel.getText());
			infoTitleLabel.setVisible(false);
			
			addListItemButton.setVisible(true);
			confirmButton.setVisible(true);
			cancelButton.setVisible(true);
			editButton.setVisible(false);
			
			formHeaderPanel.setCellVerticalAlignment(shoppinglistNameBox, ALIGN_BOTTOM);
			formHeaderPanel.setCellHorizontalAlignment(shoppinglistNameBox, ALIGN_LEFT);
			
			//cellTable.setColumnWidth(editColumn, 0, Unit.PX);
			
			cellTable.addColumn(editColumn);
			cellTable.addColumn(deleteColumn);
		}
		
	}
	
	class DeleteShoppingListClickHandler implements ClickHandler {
		
		public void onClick(ClickEvent event) {
			//administration.deleteShoppingList(shoppingListToDisplay, new DeleteShoppinglistCallback());
		}
	}
	
	
	class ConfirmClickHandler implements ClickHandler {
		
		public void onClick(ClickEvent event) {
			infoTitleLabel.setText(shoppinglistNameBox.getText());
			shoppinglistNameBox.setVisible(false);
			infoTitleLabel.setVisible(true);
			addListItemButton.setVisible(false);
			confirmButton.setVisible(false);
			cancelButton.setVisible(false);
			editButton.setVisible(true);
			
			cellTable.removeColumn(editColumn);
			cellTable.removeColumn(deleteColumn);
			
		}
	}
	
	class CancelClickHandler implements ClickHandler {
		
		public void onClick(ClickEvent event) {
			shoppinglistNameBox.setVisible(false);
			infoTitleLabel.setVisible(true);
			addListItemButton.setVisible(false);
			confirmButton.setVisible(false);
			cancelButton.setVisible(false);
			editButton.setVisible(true);
			
			cellTable.removeColumn(editColumn);
			cellTable.removeColumn(deleteColumn);
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
			// TODO Auto-generated method stub
			Notification.show("Einkaufsliste wurde erfolgreich entfernt");
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
			loadListitems();
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
			Notification.show(caught.toString());
		}
		
		@Override
		public void onSuccess(Void result) {
			// TODO Auto-generated method stub
			Notification.show("Artikel wurde erfolgreich abgehakt");
		}
	}
	
	
	
	/**
	 * ListHandler mit dem in der CellTable die Liste sortiert wird
	 */
	private class getAllCheckedListItemsCallback implements AsyncCallback<ArrayList<ListItem>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}
		
		@Override
		public void onSuccess(ArrayList<ListItem> result) {
			// TODO Auto-generated method stub
			checkedListItems = result;
//			.alert(msg);
			
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
			Window.alert("Wegda");
			}
		}
	}
