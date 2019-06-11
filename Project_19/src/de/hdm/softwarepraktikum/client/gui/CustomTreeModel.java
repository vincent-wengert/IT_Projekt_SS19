package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.cell.client.AbstractCell;
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
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.bo.ShoppingList;

public class CustomTreeModel implements TreeViewModel {
	
	private ShoppingListAdministrationAsync administration = null;

	private ObjectKeyProvider boKeyProvider = new ObjectKeyProvider();
	private SingleSelectionModel<Object> selectionModel = new SingleSelectionModel<Object>(boKeyProvider);

	private GroupForm gf;
	private ShowShoppingListForm sslf;

	private ShoppingList shoppingListToDisplay = null;
	private Group groupToDisplay = null;
	private ArrayList<Group> groups = new ArrayList<Group>();
	private ArrayList<ShoppingList> shoppinglists = new ArrayList<ShoppingList>();
	
	ListDataProvider<Group> groupsDataProvider = new ListDataProvider<Group>();
	/**
	 * Wird eine <code>Group</code> innerhalb des <code>CustomTreeModel</code> expandiert, 
	 * so werden die darin enthaltenen <code>ShoppingList</code> hier vermerkt. 
	 * Sie müssen bei erneutem erweitern des Baumes somit nicht erneut generiert werden.
	*/

	private Map<Group, ListDataProvider<ShoppingList>> shoppingListHolderDataProviders = null;

	Person p = new Person();
	
	/**
	 * This selection model is shared across all leaf nodes. A selection model can
	 * also be shared across all nodes in the tree, or each set of child nodes can
	 * have its own instance. This gives you flexibility to determine how nodes are
	 * selected.
	 */

	public CustomTreeModel() {
		p.setId(1);
		administration = ClientsideSettings.getShoppinglistAdministration();
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());
		shoppingListHolderDataProviders = new HashMap<Group, ListDataProvider<ShoppingList>>();
	}
	
	/**
	 * Auslesen aller <code>Group</code> der <code>Person</code> innerhalb des <code>CustomTreeModel</code>
	 * 
	 *  @return Alle <code>Group</code> des <code>Person</code>
	 */
	public ArrayList<Group> getPersonGroups() {
		return groups;
	}
	
	/**
	 * Auslesen aller <code>Group</code> der <code>Person</code> innerhalb des <code>CustomTreeModel</code>
	 * 
	 *  @return Alle <code>Group</code> des <code>Person</code>
	 */
	public ArrayList<ShoppingList> getShoppingListsInGroups() {
		return shoppinglists;
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
	
	public void getGroupShoppingLists(Group g) {
		administration.getAllShoppingListsByGroup(g, new getGroupShoppingListsCallback());
	}
	
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
			
			administration.getAllGroupsByPerson(p, new AsyncCallback<ArrayList<Group>>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					Notification.show("Es ist ein Fehler aufgetreten! +\n"+ caught.toString());
				}

				@Override
				public void onSuccess(ArrayList<Group> groups) {
					// TODO Auto-generated method stub
					for (Group g: groups) {
						CustomTreeModel.this.getPersonGroups().add(g);
						groupsDataProvider.getList().add(g);
					}
				}
			});
			// Return a node info that pairs the data provider and the cell.
			return new DefaultNodeInfo<Group>(groupsDataProvider, new GroupListCell(), selectionModel, null);

		} 
		
		if (value instanceof Group) {
			
			final ListDataProvider<ShoppingList> shoppinglistProvider = new ListDataProvider<ShoppingList>();
			
			shoppingListHolderDataProviders.put((Group)value, shoppinglistProvider); 
			
			administration.getAllShoppingListsByGroup((Group)value, new AsyncCallback<ArrayList<ShoppingList>>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					Notification.show("Fehler: " + caught.toString());
				}

				@Override
				public void onSuccess(ArrayList<ShoppingList> shoppinglists) {
					// TODO Auto-generated method stub
					for (ShoppingList sl : shoppinglists ) {
						ShoppingList shoppingList = new ShoppingList();
						shoppingList.setTitle(sl.getTitle());
						shoppingList.setId(sl.getId());
						shoppinglistProvider.getList().add(sl);
					}
				}
			});
			   
			return new DefaultNodeInfo<ShoppingList>(shoppinglistProvider, new ShoppingListCell(), selectionModel, null);
		}
		return null;
	}

	private class ObjectKeyProvider implements ProvidesKey<Object> {

		@Override
		public Integer getKey(Object object) {
			if (object == null) {
				return null;
			}
			else if (object instanceof Group) {
				return ((Group) object).getId();
			}
			else if (object instanceof ShoppingList) {
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
	


	private class getAllGroupsByPersonCallback implements AsyncCallback<ArrayList<Group>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Es wurden keine Gruppen gefunden:\n" + caught.toString());
		}

		@Override
		public void onSuccess(ArrayList<Group> result) {
			// add item to cellist
			// aicl.updateCellList();
			groups = result;
			Notification.show("Gruppen wurden fur die ausgewahlte Einkaufsliste hinzugefugt");

		}
	}
	private class getGroupShoppingListsCallback implements AsyncCallback<ArrayList<ShoppingList>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Es wurden keine Gruppen gefunden:\n" + caught.toString());
		}

		@Override
		public void onSuccess(ArrayList<ShoppingList> sl) {
			// add item to cellist
			// aicl.updateCellList();
			shoppinglists = sl;
			Notification.show("Gruppen wurden gefunden");

		}
	}
	
	public class GroupListCell extends AbstractCell<Group>{

		@Override
		public void render(Context context, Group g, SafeHtmlBuilder sb) {
			if (g != null) {
				
				sb.appendHtmlConstant("<div>");
				sb.appendHtmlConstant(g.getTitle());
				sb.appendHtmlConstant("</div>");
			
			}
		}	
	}	
	
	
	public class ShoppingListCell extends AbstractCell<ShoppingList>{

		@Override
		public void render(Context context, ShoppingList s, SafeHtmlBuilder sb) {
			if (s != null) {
				
				sb.appendHtmlConstant("<div>");
				sb.appendHtmlConstant(s.getTitle());
				sb.appendHtmlConstant("</div>");
			
			}
		}	
	}
	
}