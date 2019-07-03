package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.client.Project_19.CurrentPerson;
import de.hdm.softwarepraktikum.shared.ShoppingListAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.bo.ShoppingList;

/**
 * Die Klasse <code>CustomTreeModel</code> ist eine Erweiterung der Klasse <code>TreeViewModel</code>.
 * Hierbei bietet das CustomTreeModel die Grundlage für die Navigation des im <code>NavigatorPanel</code> implementierten <code>CellTree</code>.
 * 
 * @author Jan Duwe, Vincent Wengert
 *
 */
public class CustomTreeModel implements TreeViewModel {
	
	private ShoppingListAdministrationAsync administration = ClientsideSettings.getShoppinglistAdministration();;
	
	/**
	 * SelectionModel, das für alle Knoten und Kindknoten im <code>CustomTreeModel</code> verwendet wird.
	 * 
	 */
	private ObjectKeyProvider boKeyProvider = new ObjectKeyProvider();
	private SingleSelectionModel<Object> selectionModel = new SingleSelectionModel<Object>(boKeyProvider);

	private GroupForm gf;
	private ShowShoppingListForm sslf;
	private NewShoppingListForm nslf;
	private CellTree tree = null;
	private Boolean loadFavorites = false;
	private ShoppingList selectedShoppingList = null;
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

	Person currentPerson = CurrentPerson.getPerson();

	public CustomTreeModel() {
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());
		shoppingListHolderDataProviders = new HashMap<Group, ListDataProvider<ShoppingList>>();
	}
	
	/**
	 * ************************* ABSCHNITT der Methoden *************************
	 */
	
	/**
	 * Methode zum Setzen des Boolean loadFavorites. Dieser Wert definiert
	 * ob bei einer neuen Instanz eines <code>ShoppingList</code> Objekts die
	 * favorisierten <code>Item</code> als <code>ListItem</code> hinzufügt.
	 * 
	 * @param loadFavorites Boolean Wert um <code>Item</code> Objekte die als
	 * Favorit markiert wurden beim erstellen einer <code>ShoppingList</code>
	 * direkt zu laden.
	 */
	public void setLoadFavoriteItems(Boolean loadFavorites) {
		this.loadFavorites = loadFavorites;
	}
	
	/**
	 * Methode zum Auslesen des Boolean loadFavorites. Dieser Wert definiert
	 * ob bei einer neuen Instanz eines <code>ShoppingList</code> Objekts die
	 * favorisierten <code>Item</code> als <code>ListItem</code> hinzufügt.
	 * 
	 * @return Boolean Wert um <code>Item</code> Objekte die als
	 * Favorit markiert wurden beim erstellen einer <code>ShoppingList</code>.
	 * direkt zu laden.
	 */
	private Boolean getLoadFavoriteItems() {
		return this.loadFavorites;
	}
	
	/**
	 * Methode zum Auslesen aller <code>Group</code> der <code>Person</code> innerhalb des <code>CustomTreeModel</code>.
	 * 
	 *  @return Alle <code>Group</code> des <code>Person</code>.
	 */
	public ArrayList<Group> getPersonGroups() {
		return groups;
	}
	
	/**
	 * Methode zum Auslesen aller <code>ShoppingList</code> der <code>Group</code> innerhalb des <code>CustomTreeModel</code>.
	 * 
	 *  @return Alle <code>ShoppingList</code> der <code>Group</code>.
	 */
	public ArrayList<ShoppingList> getShoppingListsInGroups() {
		return shoppinglists;
	}
	
	/**
	 * Methode zum Auslesen des SingleSelectionModel innerhalb des <code>CustomTreeModel</code>.
	 * 
	 * @return
	 */
	public SingleSelectionModel<Object> getSelectionModel(){
		return this.selectionModel;
	}
	
	/**
	 * Methode zum Setzen der <code>GroupForm</code> innerhalb des <code>CustomTreeModel</code>.
	 * 
	 * @param groupForm die zu setzende <code>GroupForm</code>
	 */
	public void setGroupForm(GroupForm groupForm) {
		this.gf = groupForm;
	}
	
	/**
	 * Methode zum Setzen der <code>ShowShoppingListForm</code> um die im <code>CellTree</code> ausgewählte 
	 * <code>ShoppingList</code> darzustellen. 
	 * 
	 * @param sslf die <code>ShowShoppingListForm</code> um <code>ShoppingList</code> Objekt anzuzeigen.
	 */
	public void setShoppingListForm(ShowShoppingListForm sslf) {
		this.sslf = sslf;
	}
	
	/**
	 * Methode zum Setzen der <code>NewShoppingListForm</code> innerhalb des <code>CustomTreeModel</code>.
	 * 
	 * @param nslf die zu setzende <code>NewShoppingListForm</code>.
	 */
	public void setNewShoppingListForm(NewShoppingListForm nslf) {
		this.nslf = nslf;
	}
	
	/**
	 * Methode zum Darstellen des im <code>CellTree</code> ausgewählten <code>Group</code> Objekts.
	 * 
	 * @param g das darzustellende <code>Group</code> Objekts.
	 */
	public void setSelectedGroup(Group g) {
		groupToDisplay = g;
		gf.setEditable(false);
		gf.setInitial(false);
		RootPanel.get("Details").clear();
		gf.setSelected(g);
		RootPanel.get("Details").add(gf);
		
	}
	
	/**
	 * Methode zum Darstellen des im <code>CellTree</code> ausgewählten <code>ShoppingList</code> Objekts.
	 * 
	 * @param sl das darzustellende <code>ShoppingList</code> Objekts.
	 */
	public void setSelectedShoppingList(ShoppingList sl) {
		RootPanel.get("Details").clear();
//		selectedShoppingList = sl;
		sslf.setSelected(sl, loadFavorites);
		RootPanel.get("Details").add(sslf);
		selectedShoppingList=null;
	}
	
	/**
	 * Methode zum Setzen des <code>CellTree</code> Objekts innerhalb des <code>CustomTreeModel</code>.
	 * 
	 * @param tree der zu setzende <code>CellTree</code>.
	 */
	public void setTree(CellTree tree) {
		this.tree = tree;
	}
	
	/**
	 * Methode zum Auslesen des im <code>CellTree</code> ausgewählten <code>ShoppingList</code> Objekts.
	 * 
	 * @return die ausgewählte <code>ShoppingList</code>.
	 */
	public ShoppingList getSelectedShoppingList() {
		return selectedShoppingList;
	}
	
	/**
	 * Methode zum Auslesen aller <code>ShoppingList</code> Objekte eines <code>Group</code> Objekts.
	 * Zugriff erfolgt über innere Klasse <code>GetGroupShoppingListsCallback</code>
	 * 
	 * @param g <code>Group</code> Objekt für das die ausgewählten <code>ShoppingList</code>
	 * Objekte geladen werden sollen.
	 */
	public void getGroupShoppingLists(Group g) {
		administration.getAllShoppingListsByGroup(g, new GetGroupShoppingListsCallback());
	}
	
	/**
	 * Methode zum aktualisieren des ListDataProvider von <code>Group</code> Objekte wenn ein neues <code>Group</code> Objekt 
	 * instanziiert wird. 
	 * 
	 * @param g die neu hinzugefügte <code>Group</code>.
	 */
	public void updateAddedGroup(Group g) { 
		this.getPersonGroups().add(g);
		groupsDataProvider.setList(this.getPersonGroups());
		groupsDataProvider.refresh();
		selectionModel.setSelected(g, true);
	}
	
	/**
	 * Methode zum aktualisieren des ListDataProvider von <code>ShoppingList</code> Objekten wenn ein
	 * neues <code>ShoppingList</code> Objekt instanziiert und zu einem <code>Group</code> Objekt hinzugefügt wird.
	 * 
	 * @param sl die neu hinzugefügte <code>ShoppingList</code>.
	 * @param g die <code>Group</code> in die hinzugefügt wird.
	 */
	public void updateShoppingListToGroup(ShoppingList sl, Group g) {
		sslf.setGroup(g);
		//node des celltrees öffnen
		tree.getRootTreeNode().setChildOpen(groupsDataProvider.getList().indexOf(g), true);
		
		//alle dataprovider f�r shoppinglists aktualisieren
		for(Group group: shoppingListHolderDataProviders.keySet()) {
			final Group tempg = group;
			final ShoppingList tempsl = sl;
			administration.getAllShoppingListsByGroup(group, new AsyncCallback<ArrayList<ShoppingList>>() {

				@Override
				public void onFailure(Throwable caught) {
					Notification.show("failed");
				}

				@Override
				public void onSuccess(ArrayList<ShoppingList> result) {
					shoppingListHolderDataProviders.get(tempg).getList().clear();
					shoppingListHolderDataProviders.get(tempg).getList().addAll(result);
					shoppingListHolderDataProviders.get(tempg).refresh();
					selectionModel.setSelected(tempsl, true);
				}
			});
		}
	}
	
	/**
	 * Methode zum aktualisieren des ListDataProvider von <code>Group</code> Objekten wenn ein
	 * <code>Group</code> Objekt gelöscht wird.
	 * 
	 * @param g die zu entfernende <code>Group</code>.
	 */
	public void updateRemovedGroup(Group g) {
		groupsDataProvider.getList().remove(g);
		this.getPersonGroups().remove(g);
		groupsDataProvider.setList(this.getPersonGroups());
		groupsDataProvider.refresh();
	}
	
	/**
	 * Methode zum Aktualisieren des ListDataProvider von <code>ShoppingList</code> Objekten
	 * wenn ein <code>ShoppingList</code> Objekt gelöscht wird.
	 * 
	 * @param sl
	 */
	public void updateRemovedShoppingList(ShoppingList sl) {
		for(Group group: shoppingListHolderDataProviders.keySet()) {
			final Group tempg = group;
			administration.getAllShoppingListsByGroup(group, new AsyncCallback<ArrayList<ShoppingList>>() {

				@Override
				public void onFailure(Throwable caught) {
					Notification.show("failed");
				}

				@Override
				public void onSuccess(ArrayList<ShoppingList> result) {
					shoppingListHolderDataProviders.get(tempg).getList().clear();
					shoppingListHolderDataProviders.get(tempg).getList().addAll(result);
					shoppingListHolderDataProviders.get(tempg).refresh();
					selectionModel.setSelected(null, false);
					RootPanel.get("Details").clear();
				}
			});
		}
	}
	
	/**
	 * Methode zum aktualisieren des ListDataProvider für <code>Group</code> Objekte nachdem
	 * ein Objekt der Klasse <code>Group</code> aktualisiert wurde.
	 * 
	 * @param g die aktualisierte <code>Group</code>.
	 */
	public void updateGroup(Group g) {
		groupsDataProvider.setList(this.getPersonGroups());
		groupsDataProvider.refresh();
	}
	
	/**
	 * Methode zum aktualisieren des ListDataProvider für <code>ShoppingList</code> Objekte nachdem
	 * ein Objekt der Klasse <code>ShoppingList</code> aktualisiert wurde.
	 * 
	 * @param sl die aktualisierte <code>ShoppingList</code>.
	 */
	public void updateShoppingList(ShoppingList sl) {
		for(Group group: shoppingListHolderDataProviders.keySet()) {
			final Group tempg = group;
			final ShoppingList tempsl = sl;
			administration.getAllShoppingListsByGroup(group, new AsyncCallback<ArrayList<ShoppingList>>() {

				@Override
				public void onFailure(Throwable caught) {
					Notification.show("failed");
				}

				@Override
				public void onSuccess(ArrayList<ShoppingList> result) {
					shoppingListHolderDataProviders.get(tempg).getList().clear();
					shoppingListHolderDataProviders.get(tempg).getList().addAll(result);
					shoppingListHolderDataProviders.get(tempg).refresh();
					selectionModel.setSelected(tempsl, true);
				}
			});
		}
	}
	
	/**
	 * Prüfen ob das angezeigte Objekt ein Blattknoten ist. 
	 * Blattknoten lassen sich nicht ausklappen.
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
	 * Auslesen der {@link NodeInfo} die die Werte für Kindknoten bereitstellt.
	 */
	public <T> NodeInfo<?> getNodeInfo(T value) {
		if (value == null) {
			
			administration.getAllGroupsByPerson(currentPerson, new AsyncCallback<ArrayList<Group>>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					Notification.show("Es ist ein Fehler aufgetreten! +\n"+ caught.toString());
				}

				@Override
				public void onSuccess(ArrayList<Group> groups) {
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

	/**
	 * ************************* ABSCHNITT der Keyprovider *************************
	 */
	
	/**
	 * Versieht jedes darzustellende <code>Group</code> und <code>ShoppingList</code> Objekt
	 * mit einer eindeutigen ID.
	 *
	 */
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

	/**
	 * ************************* ABSCHNITT der EventHandler *************************
	 */
	
	/**
	 * Ein SelectionHandler der das in dem SingleSelectionModel des <code>CustomTreeModel</code> ausgewählte
	 * <code>Group</code> oder <code>ShoppingList</code> Objekt als ausgewählt setzt.
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
	
	/**
	 * ************************* ABSCHNITT der Callbacks *************************
	 */
	
	/**
	 *CallBack mit dem alle <code>ShoppingList</code> Einträge einer <code>Group</code> aus der Datenbank geladen werden.
	 * Anschließend werden alle geladenen Objekte der Variable sl hinzugefügt.
	 */
	private class GetGroupShoppingListsCallback implements AsyncCallback<ArrayList<ShoppingList>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Es wurden keine Einkaufslisten in den Gruppen gefunden:\n" + caught.toString());
		}

		@Override
		public void onSuccess(ArrayList<ShoppingList> sl) {
			// add item to cellist
			// aicl.updateCellList();
			shoppinglists = sl;

		}
	}
	
	/**
	 * ************************* ABSCHNITT der Cells *************************
	 */
	
	/**
	 * <code>GroupListCell</code> Objekt zum Rendern der anzuzeigenden <code>Group</code> Objekte.
	 * Wird mit dem Namen der <code>Group</code> befüllt.
	 */
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
	
	/**
	 * <code>ShoppingListCell</code> Objekt zum Rendern der anzuzeigenden <code>ShoppingList</code> Objekte.
	 * Wird mit dem Namen des <code>ShoppingList</code> befüllt.
	 */
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