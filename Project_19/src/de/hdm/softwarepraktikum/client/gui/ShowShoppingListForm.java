package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.io.filefilter.TrueFileFilter;
import org.omg.CORBA.PUBLIC_MEMBER;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
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
import de.hdm.softwarepraktikum.client.gui.GroupForm.getAllPersonsCallback;
import de.hdm.softwarepraktikum.shared.ShoppingListAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.ListItem;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.bo.ListItem.Unit;
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
	private Button editListNameButton = new Button();
	private Button confirmButton = new Button("\u2714");
	private Button cancelButton = new Button("\u2716");

	private Label infoTitleLabel = new Label();
	
	private TextBox shoppinglistNameBox = new TextBox();

	private CellTable<ListItem> cellTable = null;
	private ProductKeyProvider keyProvider = null;
	private ListDataProvider<ListItem> dataProvider = new ListDataProvider<ListItem>();
	private SingleSelectionModel<ListItem> singleSelectionModel = null;
	private ArrayList<ListItem> productsToDisplay = null;
	private ArrayList<Store> allStores = new ArrayList<Store>();
	private ArrayList<Person> allPersons = new ArrayList<Person>();
	

	public ShowShoppingListForm() {
		Load();
		addListItemButton.addClickHandler(new AddListItemClickHandler());
		editListNameButton.addClickHandler(new EditListNameClickHandler());
		confirmButton.addClickHandler(new ConfirmClickHandler());
		cancelButton.addClickHandler(new CancelClickHandler());
		topButtonsPanel.add(editListNameButton);
		formHeaderPanel.add(shoppinglistNameBox);
		addListItemPanel.add(addListItemButton);
		bottomButtonsPanel.add(confirmButton);
		bottomButtonsPanel.add(cancelButton);
		
		administration.getAllStores(new getAllStoresCallback());
		administration.getAllPersons(new getAllPersonsCallback());
	}

	public void onLoad() {
		this.setWidth("100%");

		
		formHeaderPanel.setStylePrimaryName("formHeaderPanel");
		infoTitleLabel.setStylePrimaryName("infoTitleLabel");
		bottomButtonsPanel.setStylePrimaryName("bottomButtonsPanel");
		addListItemPanel.setStylePrimaryName("addListItemPanel");
		
		bottomButtonsPanel.setSpacing(20);

		addListItemButton.setStylePrimaryName("addListItemButton");
		editListNameButton.setStylePrimaryName("editListNameButton");
		addListItemButton.setHeight("8vh");
		addListItemButton.setWidth("8vh");
		addListItemButton.setVisible(false);
		
		editListNameButton.setWidth("8vh");
		editListNameButton.setHeight("8vh");

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

		topButtonsPanel.setCellHorizontalAlignment(editListNameButton, ALIGN_RIGHT);
		
		this.add(formHeaderPanel);

		keyProvider = new ProductKeyProvider();
		singleSelectionModel = new SingleSelectionModel<ListItem>(keyProvider);
		cellTable = new CellTable<ListItem>();
		dataProvider.addDataDisplay(cellTable);

		cellTable.setSelectionModel(singleSelectionModel,
				DefaultSelectionEventManager.<ListItem>createCheckboxManager());

		ListHandler<ListItem> sortHandler = new ListHandler<ListItem>(null);

		TextColumn<ListItem> nameColumn = new TextColumn<ListItem>() {
			@Override
			public String getValue(ListItem i) {
				return i.getName();
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
		
		
		Column<ListItem, Boolean> checkColumn = new Column<ListItem, Boolean>( new CheckboxCell(true,false)) {
			@Override
			public Boolean getValue(ListItem object) {
				// Get the value from the selection model.
				//return singleSelectionModel.isSelected(object);
				if (object.getChecked() == true){
					return true;
				} else {
				return false;
				}
			}
		};

		cellTable.addColumn(checkColumn, "Eingekauft");
		// cellTable.setColumnWidth(checkColumn, 40,);
		cellTable.setColumnWidth(checkColumn, "10");
		cellTable.addColumn(nameColumn, "Name");
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
		
		private void load() {
			administration.getL
		}


		cellTable.addCellPreviewHandler(new CellPreviewEvent.Handler<ListItem>() {
			@Override
			public void onCellPreview(CellPreviewEvent<ListItem> event) {
				if ("click".equals(event.getNativeEvent().getType())) {
					ListItem firstRow = productsToDisplay.get(cellTable.getKeyboardSelectedRow());
					singleSelectionModel.setSelected(firstRow, true);
					ListItem selected = singleSelectionModel.getSelectedObject();

					if (selected != null) {
						// DO YOUR STUFF
						ListItemDialog lid = new ListItemDialog();
						lid.displayListItem(selected);
						lid.show();
					}
				}
			}
		});
		
		this.add(addListItemPanel);
		this.setCellHorizontalAlignment(addListItemPanel, ALIGN_CENTER);
		
		this.add(bottomButtonsPanel);
		this.setCellHorizontalAlignment(bottomButtonsPanel, ALIGN_CENTER);
	}

	private class ProductKeyProvider implements ProvidesKey<ListItem> {

		@Override
		public Object getKey(ListItem item) {
			return (item == null) ? null : item.getTempID();
		}
	}

	public void setSelected(ShoppingList sl) {
		if (sl != null) {
			// shoppingListToDisplay = sl;
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
		dataProvider.refresh();
	}

	/**
	 * Sobald der Button ausgewahlt wird werden neue <code>ListItem</code> Objekte
	 * erzeugt und der Shoppinglist hinzugefugt.
	 */
	class AddListItemClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			ListItemDialog li = new ListItemDialog();
			li.setShowShoppingListForm(ShowShoppingListForm.this);
			li.show();
		}
	}
	
	
	
	public class EditListNameClickHandler implements ClickHandler{

		public void onClick(ClickEvent event) {
			shoppinglistNameBox.setVisible(true);
			shoppinglistNameBox.setText(infoTitleLabel.getText());
			infoTitleLabel.setVisible(false);
			
			addListItemButton.setVisible(true);
			confirmButton.setVisible(true);
			cancelButton.setVisible(true);
			editListNameButton.setVisible(false);
			
			formHeaderPanel.setCellVerticalAlignment(shoppinglistNameBox, ALIGN_BOTTOM);
			formHeaderPanel.setCellHorizontalAlignment(shoppinglistNameBox, ALIGN_LEFT);
			
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
			editListNameButton.setVisible(true);
		}
	}
	
	class CancelClickHandler implements ClickHandler {
		
		public void onClick(ClickEvent event) {
			shoppinglistNameBox.setVisible(false);
			infoTitleLabel.setVisible(true);
			addListItemButton.setVisible(false);
			confirmButton.setVisible(false);
			cancelButton.setVisible(false);
			editListNameButton.setVisible(true);
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
}
