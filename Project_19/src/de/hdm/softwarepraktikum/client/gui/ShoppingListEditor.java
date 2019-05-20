package de.hdm.softwarepraktikum.client.gui;
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
	}

}
