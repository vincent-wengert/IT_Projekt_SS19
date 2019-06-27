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
	private Button confirmButton = new Button("Bestätigen");

	private ArrayList<Group> allGroups = new ArrayList<Group>();

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

	public void getAllItems() {

		for (Item p : items) {
			dataProvider.getList().add(p);
		}
		dataProvider.refresh();
	}

	/**
	 * Setzen des NavigatorPanels innerhalb des MenuPanels
	 * 
	 * @param das zu setzende NavigatorPanel
	 */
	public void setNavigator(NavigatorPanel navigator) {
		this.navigator = navigator;
	}

	public SingleSelectionModel<Item> getSelectionModel() {
		return this.selectionModel;
	}

	public void setItemForm(ItemForm itemForm) {
		this.itemForm = itemForm;
	}

	public void setSelectedGroup(Group g) {
		this.selectedGroup = g;
	}

	public void setSelectedItem(Item i) {
		itemToDisplay = i;
		RootPanel.get("Details").clear();
		itemForm.setSelected(i);
		itemForm.setEditable(false);
		itemForm.setInitial(false);
		itemForm.setGroup(selectedGroup);
		RootPanel.get("Details").add(itemForm);
	}

	public void updateCelllist(Item item) {
		dataProvider.getList().clear();
		if (selectedGroup != null) {
			administration.getAllItemsByGroup(selectedGroup.getId(), currentPerson.getId(), new GetAllItemsCallback());
		}
		dataProvider.refresh();
		selectionModel.setSelected(item, true);
	}

	public void removeItem(Item i) {
		if (selectedGroup != null) {
			administration.getAllItemsByGroup(selectedGroup.getId(), currentPerson.getId(), new GetAllItemsCallback());
		}
		dataProvider.getList().remove(i);
		dataProvider.refresh();
	}

	public void updateAddedGroup(Group g) {
		groupListBox.addItem(g.getTitle());
	}

	public ArrayList<Group> getAllGroups() {
		return this.allGroups;
	}

	public void setAllGroups(ArrayList<Group> allGroups) {
		this.allGroups = allGroups;
	}
	
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

	private class SelectionChangeEventHandler implements SelectionChangeEvent.Handler {
		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			Item selection = selectionModel.getSelectedObject();
			if (selection != null) {
				setSelectedItem(selection);
			}
		}
	}
	
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

	private class ItemDemoKeyProvider implements ProvidesKey<Item> {
		@Override
		public Object getKey(Item item) {
			return (item == null) ? null : item.getId();
		}
	}


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
