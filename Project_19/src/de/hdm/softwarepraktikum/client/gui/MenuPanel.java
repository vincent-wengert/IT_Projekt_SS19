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
 * Das <code>MenuPanel</code> bildet das Menü der GUI und enthält die Buttons
 * zum Anlegen von <code>Items</code> und <code>Shoppinglist</code>,
 * <code>Group</code>, <code>Store</code>. Sowie dem Logout.
 * 
 * @author Vincent Wengert
 * @version 1.0
 */

public class MenuPanel extends VerticalPanel {
	private Person p = CurrentPerson.getPerson();

	private AllItemsCellList allItemsCellList;
	private AllStoresCellList allStoresCellList;
	private GroupForm groupForm;

	private ItemForm itemForm;
	private StoreForm storeForm;

	private NavigatorPanel navigator;

	private NewShoppingListForm newShoppingListForm;
	private CustomTreeModel ctm = null;

	private Button createGroupButton = new Button();
	private Button createShoppinglistButton = new Button();
	private Button createArticleButton = new Button();
	private Button createStoreButton = new Button();

	public MenuPanel() {
		/**
		 * Den Buttons werden dem Panel hinzugefügt. Den Buttons werden ClickHandler
		 * hinzugefügt.
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

	/**
	 * Befüllt das <code>Menupanel</code> mit Widgets. Diese werden auf der Form passend angeordnet.
	 */
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

	}

	/**
	 * Setzen der <code>AllItemsCellList</code> innerhalb des MenuPanels
	 * 
	 * @param allItemsCellList die zu setzende <code>AllItemsCellList</code>
	 */
	public void setAllItemsCelllist(AllItemsCellList allItemsCellList) {
		this.allItemsCellList = allItemsCellList;
	}

	/**
	 * Setzen der <code>AllStoresCellList</code> innerhalb des MenuPanels
	 * 
	 * @param allStoresCellList die zu setzende <code>AllStoresCellList</code>
	 */
	public void setAllStoresCelllist(AllStoresCellList allStoresCellList) {
		this.allStoresCellList = allStoresCellList;
	}

	/**
	 * Setzen des <code>Navigators</code> innerhalb des MenuPanels
	 * 
	 * @param das zu setzende <code>NavigatorPanel</code>
	 */
	public void setNavigator(NavigatorPanel navigator) {
		this.navigator = navigator;
	}

	/**
	 * Setzen des <code>NewStoreForm</code> innerhalb des <code>MenuPanel</code>
	 * 
	 * @param das zu setzende <code>NewStoreForm</code>
	 */
	public void setNewStoreForm(StoreForm storeForm) {
		this.storeForm = storeForm;
	}

	/**
	 * Setzen der <code>ItemForm</code> innerhalb des <code>MenuPanel</code>
	 * 
	 * @param die zu setzende <code>ItemForm</code>
	 */
	public void setItemForm(ItemForm itemForm) {
		this.itemForm = itemForm;
	}

	/**
	 * Setzen des </code>NewShoppingListForm</code> innerhalb des MenuPanel</code>
	 * 
	 * @param das zu setzende <code>NewShoppingListForm</code>
	 */
	public void setNewShoppingListForm(NewShoppingListForm newShoppingListForm) {
		this.newShoppingListForm = newShoppingListForm;
	}

	/**
	 * Setzen der <code>NewGroupForm</code> innerhalb des <code>MenuPanels</code>
	 * 
	 * @param die zu setzende <code>NewGroupForm</code>
	 */
	public void setNewGroupForm(GroupForm newGroupForm) {
		this.groupForm = newGroupForm;
	}
	
	/**
	 * Setzen der <code>NewGroupForm</code> innerhalb des <code>MenuPanels</code>
	 * 
	 * @param ctm die zu setzende <code>CustomTreeModel</code>
	 */
	public void setCtm(CustomTreeModel ctm) {
		this.ctm = ctm;
	}

	/**
	 * ***************************************************************************
	 * ABSCHNITT der ClickHandler/Events
	 * ***************************************************************************
	 */
	
	/**
	 * Clickhander zum Erstellen einer <code>Gruppe<code>
	 */
	private class CreateGroupClickHandler implements ClickHandler {

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
	private class CreateShoppingListClickHandler implements ClickHandler {

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
	private class CreateItemClickHandler implements ClickHandler {

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
	private class CreateStoreClickHandler implements ClickHandler {

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
