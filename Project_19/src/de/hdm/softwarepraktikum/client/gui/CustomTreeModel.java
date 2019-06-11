package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppingListAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.ListItem;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.bo.ShoppingList;
import de.hdm.softwarepraktikum.shared.bo.ListItem.Unit;

public class CustomTreeModel implements TreeViewModel {
	
	private ShoppingListAdministrationAsync administration = ClientsideSettings.getShoppinglistAdministration();

	private ObjectKeyProvider boKeyProvider = new ObjectKeyProvider();
	private SingleSelectionModel<Object> selectionModel = new SingleSelectionModel<Object>(boKeyProvider);

	private GroupForm gf;
	private ShowShoppingListForm sslf;

	private ShoppingList shoppingListToDisplay = null;
	private Group groupToDisplay = null;
	private ArrayList<Group> groups = new ArrayList<Group>();

	/**
	 * This selection model is shared across all leaf nodes. A selection model can
	 * also be shared across all nodes in the tree, or each set of child nodes can
	 * have its own instance. This gives you flexibility to determine how nodes are
	 * selected.
	 */

	public CustomTreeModel() {
		this.loadGroups();
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());
		Person p = new Person();
		p.setId(1);
		administration.getAllGroupsByPerson(p, new getAllGroupsByPersonCallback());
	}
	
	public void loadGroups(){
		Person p = new Person();
		p.setId(1);
		administration.getAllGroupsByPerson(p, new getAllGroupsByPersonCallback());

	}

	public void setSelectedGroup(Group g) {
		groupToDisplay = g;
		gf = new GroupForm();
		gf.setEditable(false);
		gf.setInitial(false);
		RootPanel.get("Details").clear();
		gf.setSelected(g);
		RootPanel.get("Details").add(gf);
	}
	
	public void setSelectedShoppingList(ShoppingList sl) {
		//shoppingListToDisplay = sl;
		//sslf = new ShowShoppingListForm();
		RootPanel.get("Details").clear();
		sslf = new ShowShoppingListForm();
		sslf.setSelected(sl);
		Notification.show(sl.getTitle());
		RootPanel.get("Details").add(sslf);
	}

//	public void setSelectedShoppingList(ShoppingList sl) {
//		shoppingListToDisplay = sl;
//	}

	/**
	 * Check if the specified value represents a leaf node. Leaf nodes cannot be
	 * opened.
	 */
	@Override
	public boolean isLeaf(Object value) {
		// The leaf nodes are the songs, which are Strings.
		if (value instanceof Person) {
			return true;
		}
		return false;
	}

	/**
	 * Get the {@link NodeInfo} that provides the children of the specified value.
	 */
	public <T> NodeInfo<?> getNodeInfo(T value) {
		if (value == null) {
			// LEVEL 0.
			// We passed null as the root value. Return the groups.

			// Create a data provider that contains the list of groups.
			ListDataProvider<Group> dataProvider = new ListDataProvider<Group>(groups);
			// Create a cell to display a group.
			AbstractCell<Group> cell = new AbstractCell<Group>() {
				@Override
				public void render(Context context, Group value, SafeHtmlBuilder sb) {
					if (value != null) {
						sb.appendEscaped(value.getTitle());
					}
				}
			};

			// Return a node info that pairs the data provider and the cell.
			return new DefaultNodeInfo<Group>(dataProvider, cell, selectionModel, null);

		} else if (value instanceof Group) {

			// LEVEL 1.
			// We want the children of the composer. Return the Person in the Group, later
			// the Shoppinglists.
			ListDataProvider<Person> dataProvider = new ListDataProvider<Person>(
					((Group) value).getMember());

			Cell<Person> cell = new AbstractCell<Person>() {
				@Override
				public void render(Context context, Person value, SafeHtmlBuilder sb) {
					// TODO Auto-generated method stub
					if (value != null) {
						sb.appendEscaped(value.getName());
					}
				}
			};
			return new DefaultNodeInfo<Person>(dataProvider, cell, selectionModel, null);
		}
		return null;
	}

	private class ObjectKeyProvider implements ProvidesKey<Object> {

		@Override
		public Integer getKey(Object object) {
			if (object == null) {
				return null;
			}
			if (object instanceof Group) {
				return ((Group) object).getTempID();
			}
			if (object instanceof Person) {
				return ((Person) object).getId();
			}
			return null;
		}
	}

	/*
	 * Abschnitt der EventHandler
	 */
	class SelectionChangeEventHandler implements SelectionChangeEvent.Handler {

		@Override
		public void onSelectionChange(SelectionChangeEvent event) {

			Object selection = selectionModel.getSelectedObject();

			if (selection instanceof Group) {
				setSelectedGroup((Group) selection);

			} else if (selection instanceof Person) {
				//setSelectedShoppingList((Person) selection);
			}
		}
	}
	


	private class getAllGroupsByPersonCallback implements AsyncCallback<ArrayList<Group>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Person konnte leider nicht erstellt werden:\n" + caught.toString());
		}

		@Override
		public void onSuccess(ArrayList<Group> result) {
			// add item to cellist
			// aicl.updateCellList();
			groups = result;
			Notification.show("Gruppe wurde erstellt");

		}
	}
}