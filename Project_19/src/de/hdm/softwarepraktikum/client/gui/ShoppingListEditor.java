package de.hdm.softwarepraktikum.client.gui;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Diese Klasse bildet die Hauptform des Editor Clients. Hierin werden alle relevanten HTML-Layout Elemente
 * zu einer Form zusammengeführt.
 * 
 * @autor Vincent Wengert
 * @version 1.0
 * @see de.hdm.softwarepraktikum.client.Project_19
 */

public class ShoppingListEditor {
	private Header header = null;
	private MenuPanel menuPanel = null;
	private NavigatorPanel navigator = null;
	private Footer footer = null;

			
	/**
	 * Durch diese Methode wird nach erfolgreichem Login des Users, die Formen des
	 * Client Shoppinglistadministration zusammengeführt.
	 */
	public void loadForms() {
		header = new Header();
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
		menuPanel.setAllStoresCelllist(navigator.getAllStoresCellList());
		menuPanel.setAllItemsCelllist(navigator.getAllItemsCellList());
		menuPanel.setNavigator(navigator);
	}

}
