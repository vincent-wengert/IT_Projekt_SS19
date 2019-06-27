package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm.softwarepraktikum.client.Project_19.CurrentPerson;
import de.hdm.softwarepraktikum.shared.bo.Person;

/**
 * Das <code>MenuPanel</code> bildet das Menü des GUI und enthält
 * die Buttons zum Anlegen von <code>Items</code> und <code>Shoppinglist</code>,
 * <code>Group</code>, <code>Store</code>. Sowie dem Logout.
 * 
 * @author Vincent Wengert
 * @version 1.0
 */

public class MenuPanel extends VerticalPanel{
	private Person p= CurrentPerson.getPerson();
	
	private AllItemsCellList allItemsCellList;
	private AllStoresCellList allStoresCellList;
	private GroupForm groupForm;

	private ItemForm itemForm;
	private StoreForm storeForm;

	private NavigatorPanel navigator;
	
	private NewShoppingListForm newShoppingListForm;
	private CustomTreeModel ctm = null;
	
	private Button createGroupButton = new Button ();
	private Button createShoppinglistButton = new Button ();
	private Button createArticleButton = new Button ();
	private Button createStoreButton = new Button ();
	
	public MenuPanel() {
	
	/**
	 *  Den Buttons werden dem Panel hinzugefügt.
	 *  Den Buttons werden ClickHandler hinzugefügt.
	 */
	
	createGroupButton.addClickHandler(new CreateGroupClickHandler());
	this.add(createGroupButton);
	
	createShoppinglistButton.addClickHandler(new CreateShoppingListClickHandler());
	this.add(createShoppinglistButton);
	
	createArticleButton.addClickHandler(new CreateItemClickHandler());
	this.add(createArticleButton);
	
	createStoreButton.addClickHandler(new CreateStoreClickHandler());
	this.add(createStoreButton);
	
}

	public void onLoad() {
		
		this.setStylePrimaryName("MenuPanel");

		createGroupButton.setStylePrimaryName("createGroupButton");
		createGroupButton.setTitle("Gruppe erstellen");

		createShoppinglistButton.setStylePrimaryName("createShoppinglistButton");
		createShoppinglistButton.setTitle("Einkaufsliste erstellen");

		createArticleButton.setStylePrimaryName("createArticleButton");
		createArticleButton.setTitle("Artikel erstellen");

		createStoreButton.setStylePrimaryName("createStoreButton");
		createStoreButton.setTitle("Händler erstellen");
		
	}
	
	
	
	
	
	/**
	 * Setzen des NewStoreForm innerhalb des MenuPanels
	 * 
	 * @param das zu setzende NewStoreForm
	 */
	public void setAllItemsCelllist(AllItemsCellList allItemsCellList) {
		this.allItemsCellList = allItemsCellList;
	}
	
	
	/**
	 * Setzen des NewStoreForm innerhalb des MenuPanels
	 * 
	 * @param das zu setzende NewStoreForm
	 */
	public void setAllStoresCelllist(AllStoresCellList allStoresCellList) {
		this.allStoresCellList = allStoresCellList;
	}
	/**
	 * Setzen des NavigatorPanels innerhalb des MenuPanels
	 * 
	 * @param das zu setzende NavigatorPanel
	 */
	public void setNavigator(NavigatorPanel navigator) {
		this.navigator = navigator;
	}
	/**
	 * Setzen des NewStoreForm innerhalb des MenuPanels
	 * 
	 * @param das zu setzende NewStoreForm
	 */
	public void setNewStoreForm(StoreForm storeForm) {
		this.storeForm = storeForm;
	}
	
	/**
	 * Setzen der itemForm innerhalb des MenuPanels
	 * 
	 * @param die zu setzende itemForm
	 */
	public void setItemForm(ItemForm itemForm) {
		this.itemForm = itemForm;
	}
	
	/**
	 * Setzen des NewShoppingListForm innerhalb des MenuPanels
	 * 
	 * @param das zu setzende NewShoppingListForm
	 */
	public void setNewShoppingListForm(NewShoppingListForm newShoppingListForm) {
		this.newShoppingListForm = newShoppingListForm;
	}
	
	/**
	 * Setzen der NewGroupForm innerhalb des MenuPanels
	 * 
	 * @param die zu setzende NewGroupForm
	 */
	public void setNewGroupForm(GroupForm newGroupForm) {
		this.groupForm = newGroupForm;
	}
	
	public void setCtm(CustomTreeModel ctm) {
		this.ctm = ctm;
	}
	
	/**
	 * Clickhander zum Erstellen einer <code>Gruppe<code>
	 */
	private class CreateGroupClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("Details").clear();
			navigator.selectTab(0);
			groupForm = new GroupForm();
			
			groupForm.setCtm(ctm);
			groupForm.setEditable(true);
			groupForm.setInitial(true);
			RootPanel.get("Details").add(groupForm);
		}	
	}
	
	
	/**
	 * Clickhander zum Erstellen einer <code>ShoppingList<code>
	 */
	private class CreateShoppingListClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("Details").clear();
			newShoppingListForm = new NewShoppingListForm();
			
			newShoppingListForm.setCtm(ctm);
			RootPanel.get("Details").add(newShoppingListForm);
			navigator.selectTab(0);
		}	
	}
	
	
	/**
	 * Clickhander zum Erstellen eines <code>Article<code>
	 */
	private class CreateItemClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			navigator.selectTab(1);
			RootPanel.get("Details").clear();
			itemForm = new ItemForm();
			itemForm.setAllItemsCelllist(allItemsCellList);
			allItemsCellList.setItemForm(itemForm);
			
			itemForm.setEditable(true);
			itemForm.setInitial(true);
			RootPanel.get("Details").add(itemForm);	
		}
	}
	
	/**
	 * Clickhander zum Erstellen eines <code>Store<code>
	 */
	private class CreateStoreClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			navigator.selectTab(2);
			RootPanel.get("Details").clear();
			
			storeForm = new StoreForm();
			storeForm.setAllStoresCellList(allStoresCellList);
			storeForm.setEditable(true);
			storeForm.setInitial(true);
			RootPanel.get("Details").add(storeForm);
		}	
	}
}


