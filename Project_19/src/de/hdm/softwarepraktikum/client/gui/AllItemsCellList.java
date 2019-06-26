package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.aria.client.AlertdialogRole;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;


import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.client.Project_19.CurrentPerson;
import de.hdm.softwarepraktikum.server.ShoppingListAdministrationImpl;
import de.hdm.softwarepraktikum.shared.ShoppingListAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.Person;
import java_cup.version;

public class AllItemsCellList extends VerticalPanel{
	private NavigatorPanel navigator;
	
	Person currentPerson = CurrentPerson.getPerson();
	
	ShoppingListAdministrationAsync administration = ClientsideSettings.getShoppinglistAdministration();
	
	private SingleSelectionModel<Item> selectionModel = null;
	private ItemDemoKeyProvider keyProvider= null; 
	private CellList<Item> cellList = new CellList<Item>(new ItemCell(), keyProvider);
	private SearchFormArticles sfa  = new SearchFormArticles();
	
	private ItemForm itemForm = new ItemForm();
	
	private ListDataProvider<Item> dataProvider = new ListDataProvider<Item>();
	
	private ArrayList<Item> items = new ArrayList<Item>();
	
	private Item itemToDisplay = null;
	private Group selectedGroup = null;
	
	private Grid itemsGrid = new Grid(2,2);
	
	public void onLoad() {
		itemsGrid.setWidget(0, 0, sfa);
		
		
		
		ItemDemoKeyProvider keyProvider = new ItemDemoKeyProvider();
		selectionModel = new SingleSelectionModel<Item>(keyProvider);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());
		cellList.setSelectionModel(selectionModel);
		
		
		if(selectedGroup!=null) {
			Window.alert(selectedGroup.getTitle());
		administration.getAllItemsByGroup(selectedGroup.getId(), currentPerson.getId(),  new GetAllItemsCallback());
		}
		dataProvider.addDataDisplay(cellList);
		cellList.setRowData(0, dataProvider.getList());
		cellList.setRowCount(items.size(), true);
		
		
		itemsGrid.setWidget(1, 0, cellList);
		
		
		this.add(itemsGrid);
	}
	
	
	
	public void getAllItems() {
	
		for (Item p: items) {
			  dataProvider.getList().add(p);
		  }
		dataProvider.refresh();
	}
	
	/**
	 * Setzen des NavigatorPanels innerhalb des MenuPanels
	 * 
	 * @param das zu setzende NavigatorPanel
	 */
	public void setNavigator(NavigatorPanel navigator) {
		this.navigator = navigator;
	}
	
	public SingleSelectionModel<Item> getSelectionModel(){
		return this.selectionModel;
	}
		
	public void setItemForm(ItemForm itemForm) {
		this.itemForm = itemForm;
	}
	
	public void setSelectedGroup(Group g) {
		this.selectedGroup = g;
	}
	
	public void removeItem(Item i) {
		if(selectedGroup!=null) {
		administration.getAllItemsByGroup(selectedGroup.getId(), currentPerson.getId(), new GetAllItemsCallback());
		}
		dataProvider.getList().remove(i);
		dataProvider.refresh();
	}
	
	private class GetAllItemsCallback implements AsyncCallback<ArrayList<Item>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}
		
		@Override
		public void onSuccess(ArrayList<Item> result) {
			// TODO Auto-generated method stub
			items = result;
			dataProvider.getList().clear();
			getAllItems();
			dataProvider.refresh();

		}
	}
	
	//Cell muss evtl ausgelagert werden
	private static class ItemCell extends AbstractCell<Item>{
	    @Override
	    public void render(Context context, Item key, SafeHtmlBuilder sb) {
	    	if (context != null) {
	       
	        if (key.getIsFavorite()==true) {
	        	sb.appendHtmlConstant("&#9733");
	    		 
	        } else {
	        	sb.appendHtmlConstant("&#9734");
	    	}
	        	 sb.appendHtmlConstant(key.getName());
	      }
	    }
	}
	
	private class ItemDemoKeyProvider implements ProvidesKey<Item>{
		@Override
		public Object getKey(Item item) {
			return (item == null) ? null: item.getId();
		}
	}
	
	private class SelectionChangeEventHandler implements SelectionChangeEvent.Handler { 
		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			Item selection = selectionModel.getSelectedObject();
			if(selection!=null) {
				setSelectedItem(selection);
			}
		}
	}
	
	public void setSelectedItem(Item i){
		itemToDisplay = i;
		RootPanel.get("Details").clear();
		itemForm.setSelected(i);
		itemForm.setEditable(false);
		itemForm.setInitial(false);
		itemForm.setGroup(selectedGroup);
		RootPanel.get("Details").add(itemForm);
	}
	
	public void updateCelllist(Item item) {
		dataProvider.getList().clear();
		if(selectedGroup != null) {
		administration.getAllItemsByGroup(selectedGroup.getId(), currentPerson.getId(), new GetAllItemsCallback());
		}
		dataProvider.refresh();
		selectionModel.setSelected(item, true);
		//setSelectedItem(i);
		//navigator.selectTab(1)
	}
	
	public void updateAddedGroup(Group g) {
		itemsGrid.remove(sfa);
		SearchFormArticles nsfa = new SearchFormArticles();
		itemsGrid.setWidget(0, 0, nsfa);
	}
	
	
	
	/**
	* In dieser Methode wird das Design des NavigatorPanels und der Buttons festgelegt.
	* Ebenso wird die searchbar mit <code>Item</code> Suggestions befüllt.
	* Diese Methode wird aufgerufen, sobald eine Instanz der Klasse <code> NavigationPanel</code> aufgerufen wird. 
	*/
	class SearchFormArticles extends VerticalPanel {

		private Grid groupGrid = new Grid(2, 2);
		
		private Label favLabel = new Label("Favoriten-Gruppe");
		
		private ListBox groupListBox = new ListBox();
	 	private Button confirmButton = new Button("Bestätigen");
	 	
	 	
	 	private ArrayList<Group> allGroups = new ArrayList<Group>();
	 	
	 	


	@SuppressWarnings("deprecation")
	public void onLoad() {
		
		administration.getAllGroupsByPerson(currentPerson, new AsyncCallback<ArrayList<Group>>() {
			
			@Override
			public void onSuccess(ArrayList<Group> result) {

				
					// TODO Auto-generated method stub
					allGroups = result;
					for(Group g : result) {
					groupListBox.addItem(g.getTitle());
					}
					
					for (Group g : allGroups) {
						if (g.getTitle().equals(groupListBox.getSelectedItemText())) {
							//Window.alert(g.getTitle());
							AllItemsCellList.this.selectedGroup = g;
							AllItemsCellList.this.updateCelllist(null);
						}
					}
					
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Notification.show(caught.toString());
			}
		});
		
		

		groupGrid.setWidget(0, 0, favLabel);
		groupGrid.setWidget(1, 0, groupListBox);
		groupGrid.setWidget(1, 1, confirmButton);
		
		groupListBox.setWidth("10vw");
		
		
		
		confirmButton.setStylePrimaryName("selectGroupButton");
		favLabel.setStylePrimaryName("favLabel");
		
		confirmButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				// TODO Auto-generated method stub
				for (Group g : allGroups) {
					if (g.getTitle().equals(groupListBox.getSelectedItemText())) {
						AllItemsCellList.this.selectedGroup = g;
						AllItemsCellList.this.updateCelllist(null);
					}
				}
			}
		});

		this.add(groupGrid);

		}
	
	public ArrayList<Group> getAllGroups(){
		return this.allGroups;
	}
	
	public void setAllGroups(ArrayList<Group> allGroups) {
		this.allGroups = allGroups;
	}
	
 
	public void updateListBox(Group g) {
		groupListBox.addItem(g.getTitle());
	}
}
}

