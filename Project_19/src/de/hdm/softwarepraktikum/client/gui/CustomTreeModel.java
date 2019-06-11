package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.ListItem;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.bo.ShoppingList;
import de.hdm.softwarepraktikum.shared.bo.ListItem.Unit;

public class CustomTreeModel implements TreeViewModel {

	private ObjectKeyProvider boKeyProvider = new ObjectKeyProvider();
	private SingleSelectionModel<Object> selectionModel = new SingleSelectionModel<Object>(boKeyProvider);

	private GroupForm gf;
	private ShowShoppingListForm sslf;

	private ShoppingList shoppingListToDisplay = null;
	private Group groupToDisplay = null;
	private final List<Group> groups;

	/**
	 * This selection model is shared across all leaf nodes. A selection model can
	 * also be shared across all nodes in the tree, or each set of child nodes can
	 * have its own instance. This gives you flexibility to determine how nodes are
	 * selected.
	 */

	public CustomTreeModel() {
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());

		// Create a database of information.
		groups = new ArrayList<Group>();
		// add demo groups and persons
		Group group1 = new Group();
		Group group2 = new Group();
		Group group3 = new Group();
		Group group4 = new Group();

		ArrayList<ShoppingList> lists = new ArrayList<ShoppingList>();
		ShoppingList sl1 = new ShoppingList("Einkauf");
		ListItem i1 = new ListItem("Apfel", Unit.ST, 12, false);
		sl1.getShoppinglist().add(i1);
		lists.add(sl1);

		ArrayList<Person> persons = new ArrayList<Person>();

		Person p1 = new Person();
		p1.setName("Thomas Muller");

		Person p2 = new Person();
		p2.setName("Hans Schneider");

		Person p3 = new Person();
		p3.setName("Markus Eiersalat");

		Person p4 = new Person();
		p4.setName("Wurst Kartofell");

		Person p5 = new Person();
		p5.setName("Munkel Maknel");

		persons.add(p1);
		persons.add(p2);
		persons.add(p3);
		persons.add(p4);
		persons.add(p5);

		group1.setTitle("Familie");
		group2.setTitle("Uni");
		group3.setTitle("WG");
		group4.setTitle("Kindergarten");

		group1.setMember(persons);
		group2.setMember(persons);
		group3.setMember(persons);
		group4.setMember(persons);

		group1.setShoppingLists(lists);
		group2.setShoppingLists(lists);
		group3.setShoppingLists(lists);
		group4.setShoppingLists(lists);

		groups.add(group1);
		groups.add(group2);
		groups.add(group3);
		groups.add(group4);

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
		if (value instanceof ShoppingList) {
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
			ListDataProvider<ShoppingList> dataProvider = new ListDataProvider<ShoppingList>(
					((Group) value).getShoppingLists());

			Cell<ShoppingList> cell = new AbstractCell<ShoppingList>() {
				@Override
				public void render(Context context, ShoppingList value, SafeHtmlBuilder sb) {
					// TODO Auto-generated method stub
					if (value != null) {
						sb.appendEscaped(value.getTitle());
					}
				}
			};
			return new DefaultNodeInfo<ShoppingList>(dataProvider, cell, selectionModel, null);
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
			if (object instanceof ShoppingList) {
				return ((ShoppingList) object).getId();
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

			} else if (selection instanceof ShoppingList) {
				setSelectedShoppingList((ShoppingList) selection);
			}
		}
	}
}