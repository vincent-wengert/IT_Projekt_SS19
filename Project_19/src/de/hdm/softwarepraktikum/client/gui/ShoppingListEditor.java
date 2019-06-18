package de.hdm.softwarepraktikum.client.gui;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

public class ShoppingListEditor {
	private Header header = null;
	private MenuPanel menuPanel = null;
	private NavigatorPanel navigator = null;
	private Footer footer = null;

			

	public void loadForms() {
		header = new Header ();
		navigator = new NavigatorPanel();
		menuPanel= new MenuPanel();
		footer= new Footer();
	
		//Divs laden
		RootPanel.get("footer").add(footer);
		RootPanel.get("Menu").add(menuPanel);
		RootPanel.get("Navigator").add(navigator);
		RootPanel.get("Header").add(header);
		
		
		
		/**
		 * Hiermit kann im Create-Panel die Celllist aktualisiert werden, damit neu
		 * erstelle Item und Shoppinglisten angezeigt werden können. 
		 */
		menuPanel.setCtm(navigator.getCtm());
		
		menuPanel.setAllShoppinglistCelllist(navigator.getAllShoppingListsCellList());
		menuPanel.setAllStoresCelllist(navigator.getAllStoresCellList());
		menuPanel.setAllItemsCelllist(navigator.getAllItemsCellList());
		menuPanel.setNavigator(navigator);
	}

}
