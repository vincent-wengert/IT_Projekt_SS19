package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;

import org.apache.bcel.generic.IREM;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.TreeViewModel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.client.Project_19.CurrentPerson;
import de.hdm.softwarepraktikum.shared.ShoppingListAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.bo.Store;

public class NavigatorPanel extends TabPanel {

	private Person currentPerson = CurrentPerson.getPerson();
	private ShoppingListAdministrationAsync administration = ClientsideSettings.getShoppinglistAdministration();

	private VerticalPanel contentPanelGroups = new VerticalPanel();
	private VerticalPanel contentPanelStores = new VerticalPanel();
	private VerticalPanel contentPanelArticles = new VerticalPanel();

	private AllStoresCellList ascl = new AllStoresCellList();
	private AllItemsCellList aicl = new AllItemsCellList();

	private ItemForm itemForm = new ItemForm();
	private StoreForm storeForm = new StoreForm();
	private GroupForm gf = new GroupForm();

	private ShowShoppingListForm sslf = new ShowShoppingListForm();
	private NewShoppingListForm nslf = new NewShoppingListForm();

	private Grid storesGrid = new Grid(2, 2);

	private CustomTreeModel model = new CustomTreeModel();
	private CellTree tree = new CellTree(model, null);

	private Group selectedGroup;

	/**
	 * ***************************************************************************
	 * ABSCHNITT der Methoden
	 * ***************************************************************************
	 */

	/**
	 * In dieser Methode wird das Design des <code>NavigatorPanels</code> und der
	 * Widgets festgelegt. Diese Methode wird aufgerufen, sobald ein Objekt dieser
	 * Klasse instanziert wird.
	 */
	public void onLoad() {

		storesGrid.setWidget(0, 0, ascl);
		contentPanelStores.add(storesGrid);

		this.setWidth("35vw");
		this.setAnimationEnabled(true);

		this.add(contentPanelGroups, "Gruppen");
		this.selectTab(0);
		this.add(contentPanelArticles, "Alle Artikel");
		this.add(contentPanelStores, "Alle Händler");

		aicl.setNavigator(this);
		contentPanelArticles.add(aicl);

		tree.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		contentPanelGroups.add(tree);

		// Alle Forms in den jeweiligen Klassen werden gesetzt
		model.setGroupForm(gf);
		gf.setCtm(model);
		gf.setAllItemsCelllist(aicl);
		model.setShoppingListForm(sslf);
		sslf.setCtm(model);
		model.setNewShoppingListForm(nslf);
		nslf.setCtm(model);
		model.setTree(tree);
		aicl.setItemForm(itemForm);
		itemForm.setAllItemsCelllist(aicl);
		ascl.setStoreForm(storeForm);
		storeForm.setAllStoresCellList(ascl);

		// Selectionhandler dem Tabpanel hinzufügen
		this.addSelectionHandler(new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				int tabID = event.getSelectedItem();
				Widget tabWidget = getWidget(tabID);

				if (tabWidget != null) {
					ascl.getSelectionModel().clear();
					aicl.getSelectionModel().clear();
					model.getSelectionModel().clear();
				}
			}
		});
	}

	/**
	 * Methode um die <code>AllItemsCellList</code> abzufragen.
	 * 
	 * @return Die <code>AllItemsCellList</code> wird zurückgegeben.
	 */
	public AllItemsCellList getAllItemsCellList() {
		return aicl;
	}

	/**
	 * Methode um die <code>AllStoresCellList</code> abzufragen.
	 * 
	 * @return Die <code>AllStoresCellList</code> wird zurückgegeben.
	 */
	public AllStoresCellList getAllStoresCellList() {
		return ascl;
	}

	/**
	 * Methode um das <code>CustomTreeModel</code> abzufragen.
	 * 
	 * @return Die <code>CustomTreeModel</code> wird zurückgegeben.
	 */
	public CustomTreeModel getCtm() {
		return model;
	}

	/**
	 * ***************************************************************************
	 * ABSCHNITT der ClickHandler/Events
	 * ***************************************************************************
	 */
	/**
	 * Implementierung des KeyDownHandler Events. In diesem wird nach dem Betätigen
	 * der ENTER Taste der Suchvorgang gestartet.
	 */
	private class EnterKeyDownHandler implements KeyDownHandler {
		public void onKeyDown(KeyDownEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				Notification.show("Enter");
			}
		}
	}
}
