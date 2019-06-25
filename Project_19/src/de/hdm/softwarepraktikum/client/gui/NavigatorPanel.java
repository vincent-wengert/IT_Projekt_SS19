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

	private Person p = CurrentPerson.getPerson();
	
	private ShoppingListAdministrationAsync administration = ClientsideSettings.getShoppinglistAdministration();
	
	private VerticalPanel contentPanelGroups = new VerticalPanel();
	private VerticalPanel contentPanelStores = new VerticalPanel();
	private VerticalPanel contentPanelArticles = new VerticalPanel();
	
	
	private AllItemsCellList aicl = new AllItemsCellList();
	private ItemForm itemForm = new ItemForm();
	private StoreForm storeForm = new StoreForm();
	
	private AllShoppingListsCellList aslcl = new AllShoppingListsCellList();
	private AllStoresCellList ascl = new AllStoresCellList();
	private GroupForm gf = new GroupForm();
	private ShowShoppingListForm sslf = new ShowShoppingListForm();
	private NewShoppingListForm nslf = new NewShoppingListForm();
	private Group selectedGroup = new Group();
	
	private Grid itemsGrid = new Grid(2,2);
	private Grid storesGrid = new Grid(2,2);
	
	//Create a model for the tree.
	private CustomTreeModel model = new CustomTreeModel();
	
	/*
	 * Create the tree using the model. We use <code>null</code> as the default
	 * value of the root node. The default value will be passed to
	 * CustomTreeModel#getNodeInfo();
	 */
	private CellTree tree = new CellTree(model, null);
		
/**
 * ***************************************************************************
 * ABSCHNITT der Methoden
 * ***************************************************************************
 */


 /**
  * In dieser Methode wird das Design des NavigatorPanels und der Widgets festgelegt. 
  * Diese Methode wird aufgerufen, sobald ein Objekt dieser Klasse instanziert wird. 
  */
public void onLoad() {
	SearchFormArticles sfa = new SearchFormArticles();
	itemsGrid.setWidget(0, 0, sfa);
	
	SearchFormStores sfs = new SearchFormStores();
	storesGrid.setWidget(0, 0, sfs);
	storesGrid.setWidget(1, 0, ascl);
	contentPanelStores.add(storesGrid);

	this.setWidth("35vw");
	this.setAnimationEnabled(true);

	this.add(contentPanelGroups, "Gruppen");
	this.selectTab(0);
	this.add(contentPanelArticles, "Alle Artikel");
	this.add(contentPanelStores, "Alle Händler");
	
	itemsGrid.setWidget(1, 0, aicl);
	contentPanelArticles.add(itemsGrid);

    
    tree.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

    // Add the tree to the root layout panel.
    contentPanelGroups.add(tree);
    
    model.setGroupForm(gf);
    gf.setCtm(model);
    model.setShoppingListForm(sslf);
    sslf.setCtm(model);
    model.setNewShoppingListForm(nslf);
    nslf.setCtm(model);
    model.setTree(tree);

    aicl.setItemForm(itemForm);
    itemForm.setAllItemsCelllist(aicl);
    ascl.setStoreForm(storeForm);
    storeForm.setAllStoresCellList(ascl);
    
    this.addSelectionHandler(new SelectionHandler<Integer>()
	{
		@Override
		public void onSelection(SelectionEvent<Integer> event) {
			int tabID = event.getSelectedItem();
			Widget tabWidget = getWidget(tabID);
			
			if(tabWidget != null) {
				ascl.getSelectionModel().clear();
				aicl.getSelectionModel().clear();
				model.getSelectionModel().clear();
			}
		}
	}
	);
	}

/**
 * Methode um die AllItemsCellList abzufragen.
 * 
 * @return Die AllItemsCellList wird zurückgegeben.
 */
public AllItemsCellList getAllItemsCellList() {
	return aicl;
}

/**
 * Methode um die AllStoresCellList abzufragen.
 * 
 * @return Die AllStoresCellList wird zurückgegeben.
 */
public AllStoresCellList getAllStoresCellList() {
	return ascl;
}

 /**
 * Methode um die AllShoppingListsCellList abzufragen.
 * 
 * @return Die AllShoppingListsCellList wird zurückgegeben.
 */
public AllShoppingListsCellList getAllShoppingListsCellList() {
	return aslcl;

}

public CustomTreeModel getCtm() {
	return model;
}

private void setSelectedGroup(Group g) {
	this.selectedGroup = g;
}

private Group getSelectedGroup() {
	return this.selectedGroup;
}
/**
* In dieser Methode wird das Design des NavigatorPanels und der Buttons festgelegt.
* Ebenso wird die searchbar mit <code>Item</code> Suggestions befüllt.
* Diese Methode wird aufgerufen, sobald eine Instanz der Klasse <code> NavigationPanel</code> aufgerufen wird. 
*/
private class SearchFormArticles extends VerticalPanel {

	private Grid groupGrid = new Grid(2, 2);
	
	private Label favLabel = new Label("Favoriten-Gruppe");
	
	private ListBox groupListBox = new ListBox();
 	private Button confirmButton = new Button("Bestätigen");
 	
 	
 	private ArrayList<Group> allGroups = new ArrayList<Group>();
 	
 	


@SuppressWarnings("deprecation")
public void onLoad() {
	
	
	groupGrid.setWidget(0, 0, favLabel);
	groupGrid.setWidget(1, 0, groupListBox);
	groupGrid.setWidget(1, 1, confirmButton);
	
	groupListBox.setWidth("10vw");
	
	
	
	confirmButton.setStylePrimaryName("selectGroupButton");
	favLabel.setStylePrimaryName("favLabel");
	
	confirmButton.addClickHandler(new groupListBoxSelectionClickHandler());
	
	
	administration.getAllGroupsByPerson(p, new AsyncCallback<ArrayList<Group>>() {
		
		@Override
		public void onSuccess(ArrayList<Group> result) {

				// TODO Auto-generated method stub
				allGroups = result;
				for(Group g : result) {
				groupListBox.addItem(g.getTitle());
				}
		}
		
		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Notification.show(caught.toString());
		}
	});
	
	
	this.add(groupGrid);
	
	
	
	}


private class groupListBoxSelectionClickHandler implements ClickHandler{

	@Override
	public void onClick(ClickEvent arg0) {
		// TODO Auto-generated method stub
		for (Group g : allGroups) {
			if (g.getTitle().equals(groupListBox.getSelectedItemText())) {
				NavigatorPanel.this.setSelectedGroup(g);
				Window.alert(selectedGroup.getTitle());
			}
		}
	}	
	}
}


/**
* In dieser Methode wird das Design des NavigatorPanels und der Buttons festgelegt.
* Ebenso wird die searchbar mit <code>Store</code> Suggestions befüllt.
* Diese Methode wird aufgerufen, sobald eine Instanz der Klasse <code> NavigationPanel</code> aufgerufen wird. 
*/
private class SearchFormStores extends VerticalPanel {

	private Grid searchGridStores = new Grid(1, 3);

	private Button cancelButton = new Button("\u2716");
	private MultiWordSuggestOracle searchbar = new MultiWordSuggestOracle();
	private SuggestBox searchTextBox = new SuggestBox(searchbar);


@SuppressWarnings("deprecation")
public void onLoad() {
	
	cancelButton.addClickHandler(new CancelClickHandler());
	searchTextBox.addKeyDownHandler(new EnterKeyDownHandler());
	searchTextBox.addClickListener(new RefreshClickHandler());
	
	searchTextBox.setSize("440px", "27px");
	cancelButton.setPixelSize(30, 30);
	searchTextBox.getElement().setPropertyString("placeholder", "Suchbegriff eingeben...");
	cancelButton.setStylePrimaryName("cancelSearchButton");	
	
	searchGridStores.setWidget(0, 0, searchTextBox);
	searchGridStores.setWidget(0, 1, cancelButton);

	this.add(searchGridStores);

	
		  }
	}

/**
 * Implementierung des KeyDownHandler Events. In diesem wird nach dem Betätigen der ENTER Taste 
 * der Suchvorgang gestartet.
 */
private class EnterKeyDownHandler implements KeyDownHandler {
 public void onKeyDown(KeyDownEvent event) {
	 if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
		 Notification.show("Enter");
		}
	}
 }
 
 

/**
 * Implementierung des CancelClickHandlers. In diesem werden die Suchwerte 
 * nach einem Klick auf dem CancelButton zurückgesetzt.
 */
private class CancelClickHandler implements ClickHandler {
	@Override
	public void onClick(ClickEvent event) {
	}
}

/**
 * Implementierung des RefreshClickHandler.In diesem wird die SearchBar aktualisiert, 
 * sobald  in das Textfeld geklickt wird.
 */
@SuppressWarnings("deprecation")
private class RefreshClickHandler implements ClickListener {

	@Override
	public void onClick(Widget sender) {
		
		}
	}



}

