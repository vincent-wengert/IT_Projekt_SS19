package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Das <code>MenuPanel</code> bildet das Menü des GUI und enthält
 * die Buttons zum Anlegen von <code>Items</code> und <code>Shoppinglist</code>,
 * <code>Group</code>, <code>Store</code>. Sowie dem Logout.
 * 
 * @author Vincent Wengert
 * @version 1.0
 */

public class MenuPanel extends VerticalPanel{
	
private Button createGroupButton = new Button ();
private Button createShoppinglistButton = new Button ();
private Button createArticleButton = new Button ();
private Button createStoreButton = new Button ();
private Button logoutButton = new Button();

public MenuPanel() {
	
	/**
	 *  Den Buttons werden dem Panel hinzugefügt.
	 *  Den Buttons werden ClickHandler hinzugefügt.
	 */
	
	createGroupButton.addClickHandler(new CreateGroupClickHandler());
	this.add(createGroupButton);
	
	createShoppinglistButton.addClickHandler(new CreateShoppingListClickHandler());
	this.add(createShoppinglistButton);
	
	createArticleButton.addClickHandler(new CreateArticleClickHandler());
	this.add(createArticleButton);
	
	createStoreButton.addClickHandler(new CreateStoreClickHandler());
	this.add(createStoreButton);
	
	logoutButton.addClickHandler(new LogoutClickHandler());
	this.add(logoutButton);
}

	public void onLoad() {
		this.setWidth("15vh");
		
		this.setStylePrimaryName("MenuPanel");

		createGroupButton.setWidth("15vh");
		createGroupButton.setHeight("15vh");
		createGroupButton.setStylePrimaryName("createGroupButton");
		createGroupButton.setTitle("Gruppe erstellen");
		this.setCellHorizontalAlignment(createGroupButton, ALIGN_CENTER);
						
		createShoppinglistButton.setWidth("15vh");
		createShoppinglistButton.setHeight("15vh");
		createShoppinglistButton.setStylePrimaryName("createShoppinglistButton");
		createShoppinglistButton.setTitle("Einkaufsliste erstellen");
	
		createArticleButton.setWidth("15vh");
		createArticleButton.setHeight("15vh");
		createArticleButton.setStylePrimaryName("createArticleButton");
		createArticleButton.setTitle("Artikel erstellen");

		createStoreButton.setWidth("15vh");
		createStoreButton.setHeight("15vh");
		createStoreButton.setStylePrimaryName("createStoreButton");
		createStoreButton.setTitle("Händler erstellen");
		
		logoutButton.setWidth("15vh");
		logoutButton.setHeight("15vh");
		logoutButton.setStylePrimaryName("logoutButton");
		logoutButton.setTitle("Abmelden");
	}


	/**
	 * Clickhander zum Erstellen einer <code>Gruppe<code>
	 */
	private class CreateGroupClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			Notification.show("CreateGroup");
		}	
	}
	
	
	/**
	 * Clickhander zum Erstellen einer <code>ShoppingList<code>
	 */
	private class CreateShoppingListClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			Notification.show("createShoppingListForm");
		}	
	}
	
	
	/**
	 * Clickhander zum Erstellen eines <code>Article<code>
	 */
	private class CreateArticleClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			Notification.show("Artikel erstellen");
		}
	}
	
	/**
	 * Clickhander zum Erstellen eines <code>Store<code>
	 */
	private class CreateStoreClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			Notification.show("Store erstellen");
		}	
	}
	
	/**
	 * Durch ein Klick auf den Logout-Button wird der User auf die
	 * Begrüßungsseite weitergeleitet
	 */
	private class LogoutClickHandler  implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Notification.show("Logout");
//			
//			u.setLogoutUrl(u.getLogoutUrl());
//			Window.open(u.getLogoutUrl(), "_self", "");
		}
	}
}


