package de.hdm.softwarepraktikum.client.gui;

import org.apache.bcel.generic.IREM;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.TreeViewModel;


public class NavigatorPanel extends TabPanel {
	

private VerticalPanel contentPanelGroups = new VerticalPanel();
private VerticalPanel contentPanelStores = new VerticalPanel();
private VerticalPanel contentPanelArticles = new VerticalPanel();


private AllItemsCellList aicl = new AllItemsCellList();
private ItemForm itemForm = new ItemForm();
private AllShoppingListsCellList aslcl = new AllShoppingListsCellList();
private AllStoresCellList ascl = new AllStoresCellList();

private Grid itemsGrid = new Grid(2,2);
private Grid storesGrid = new Grid(2,2);

		
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

	
    // Create a model for the tree.
    TreeViewModel model = new CustomTreeModel();

    /*
     * Create the tree using the model. We use <code>null</code> as the default
     * value of the root node. The default value will be passed to
     * CustomTreeModel#getNodeInfo();
     */
    CellTree tree = new CellTree(model, null);
    tree.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

    // Add the tree to the root layout panel.
    contentPanelGroups.add(tree);
   
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

/**
* In dieser Methode wird das Design des NavigatorPanels und der Buttons festgelegt.
* Ebenso wird die searchbar mit <code>Item</code> Suggestions befüllt.
* Diese Methode wird aufgerufen, sobald eine Instanz der Klasse <code> NavigationPanel</code> aufgerufen wird. 
*/
private class SearchFormArticles extends VerticalPanel {

	private Grid searchGridArticles = new Grid(1, 3);

	private Button cancelButton = new Button("\u2716");
	private MultiWordSuggestOracle searchbar = new MultiWordSuggestOracle();
	private SuggestBox searchTextBox = new SuggestBox(searchbar);
	//private ShoppingListAdministrationAsync shoppinglistAdministration = ClientsideSettings.getshoppingListAdministration();

@SuppressWarnings("deprecation")
public void onLoad() {
	
	cancelButton.addClickHandler(new CancelClickHandler());
	searchTextBox.addKeyDownHandler(new EnterKeyDownHandler());
	searchTextBox.addClickListener(new RefreshClickHandler());
	
	searchTextBox.setSize("440px", "27px");
	cancelButton.setPixelSize(30, 30);
	searchTextBox.getElement().setPropertyString("placeholder", "Suchbegriff eingeben...");
	cancelButton.setStylePrimaryName("cancelSearchButton");	
	
	searchGridArticles.setWidget(0, 0, searchTextBox);
	searchGridArticles.setWidget(0, 1, cancelButton);

	this.add(searchGridArticles);
	
    // Create a model for the tree.
//    TreeViewModel model = new CustomTreeModel();
//
//    /*
//     * Create the tree using the model. We specify the default value of the
//     * hidden root node as "Item 1".
//     */
//    CellTree tree = new CellTree(model, "Gruppe");
//
//    // Add the tree to the root layout panel.
//    contentPanelGroups.add(tree);
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
	//private ShoppingListAdministrationAsync shoppinglistAdministration = ClientsideSettings.getshoppingListAdministration();



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

