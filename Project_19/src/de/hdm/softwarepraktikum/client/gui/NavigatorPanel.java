package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.hdm.softwarePraktikum.client.gui.NavigatorPanel.SearchForm.GetContactListCallBack;
import de.hdm.softwarePraktikum.client.gui.NavigatorPanel.SearchForm.GetContactMapCallBack;
import de.hdm.softwarePraktikum.client.gui.NavigatorPanel.SearchForm.GetPropertyValuesCallBack;
import de.hdm.softwarepraktikum.shared.ShoppingListAdministrationAsync;

public class NavigatorPanel extends TabPanel {
	

private VerticalPanel contentPanelGroups = new VerticalPanel();
private VerticalPanel contentPanelShoppinglists = new VerticalPanel();
private VerticalPanel contentPanelStores = new VerticalPanel();
private VerticalPanel contentPanelArticles = new VerticalPanel();

private Grid articlesGrid = new Grid(2,2);
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
	articlesGrid.setWidget(0, 0, sfa);
	contentPanelArticles.add(articlesGrid);
	
	SearchFormStores sfs = new SearchFormStores();
	storesGrid.setWidget(0, 0, sfs);
	contentPanelStores.add(storesGrid);

	this.setWidth("35vw");
	this.setAnimationEnabled(true);

	this.add(contentPanelGroups, "Gruppen");
	this.selectTab(0);
	this.add(contentPanelShoppinglists, "Einkaufslisten");
	this.add(contentPanelStores, "Alle Händler");
	this.add(contentPanelArticles, "Alle Artikel");
	}



/**
* In dieser Methode wird das Design des NavigatorPanels und der Buttons festgelegt.
* Ebenso wird die searchbar mit <code>Items</code> Suggestions befüllt.
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

