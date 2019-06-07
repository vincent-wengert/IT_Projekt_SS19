package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppingListAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.ListItem;
import de.hdm.softwarepraktikum.shared.bo.ListItem.Unit;
import de.hdm.softwarepraktikum.shared.bo.ShoppingList;
import de.hdm.softwarepraktikum.shared.bo.Store;


public class AllShoppingListsCellList extends VerticalPanel{
	ShoppingListAdministrationAsync administration = ClientsideSettings.getShoppinglistAdministration();
	
	private static int itemCounter = 1;
	private static int listCounter = 1;
	private ShoppingListKeyProvider keyProvider = null;
	private CellList<ShoppingList> cellList = new CellList<ShoppingList>(new ShoppingListCell(), keyProvider);
	private SingleSelectionModel<ShoppingList> selectionModel = null;
	private ListDataProvider<ShoppingList> dataProvider = new ListDataProvider<ShoppingList>();
	private ArrayList<ShoppingList> shoppingLists = new ArrayList<ShoppingList>();
	private ShowShoppingListForm sslf;
	private ListItemDialog lid;
	
	/**
	 * Erstellen von Demo-Objekten
	 */
	ShoppingList sl1 = new ShoppingList("WG-Party");
	ShoppingList sl2 = new ShoppingList("Wocheneinkauf");
	ShoppingList sl3 = new ShoppingList("Test");
	ShoppingList sl4 = new ShoppingList("Noch eine");
	ShoppingList sl5 = new ShoppingList("Noch eine");
	ShoppingList sl6 = new ShoppingList("Und noch eine");
	
	ListItem li1 = new ListItem("Bier", Unit.L, 10,1);
	ListItem li2 = new ListItem("Orangensaft", Unit.L, 12,2);
	ListItem li3 = new ListItem("Cola", Unit.L, 5,3);
	ListItem li4 = new ListItem("Croissant", Unit.ST, 4,4);
	ListItem li5 = new ListItem("Wassermelone", Unit.KG, 3.5,5);
	ListItem li6 = new ListItem("Wasser", Unit.L, 20,6);
	ListItem li7 = new ListItem("Chips", Unit.ST, 1,7);
	ListItem li8 = new ListItem("Guacamole", Unit.ST, 2,8);
	ListItem li9 = new ListItem("Pizza", Unit.ST, 8,9);
	ListItem li10 = new ListItem("Radler", Unit.L, 10,10);
	
	public void onLoad(){
		getAllShoppingLists();
		keyProvider = new ShoppingListKeyProvider();
		selectionModel = new SingleSelectionModel<ShoppingList>();
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());
		cellList.setSelectionModel(selectionModel);
		dataProvider.addDataDisplay(cellList);
		cellList.setRowCount(shoppingLists.size(), true);
		cellList.setRowData(0, dataProvider.getList());
		this.add(cellList);
		//administration.getAllShoppingListsByGroup(g, new GetAllShoppingListsCallback());
	}
	
	public void getAllShoppingLists() {
		shoppingLists.add(sl1);
		shoppingLists.add(sl2);
		shoppingLists.add(sl3);
		shoppingLists.add(sl4);
		shoppingLists.add(sl5);
		shoppingLists.add(sl6);
		
		sl1.getShoppinglist().add(li1);
		sl1.getShoppinglist().add(li2);
		sl1.getShoppinglist().add(li7);
		sl1.getShoppinglist().add(li8);
		sl1.getShoppinglist().add(li9);
		sl1.getShoppinglist().add(li10);
		
		sl2.getShoppinglist().add(li2);
		sl2.getShoppinglist().add(li5);
		sl2.getShoppinglist().add(li6);
		sl2.getShoppinglist().add(li7);
		
		sl3.getShoppinglist().add(li1);
		sl3.getShoppinglist().add(li2);
		sl3.getShoppinglist().add(li3);
		sl3.getShoppinglist().add(li4);
		sl3.getShoppinglist().add(li5);
		
		sl4.getShoppinglist().add(li5);
		sl4.getShoppinglist().add(li6);
		sl4.getShoppinglist().add(li7);
		sl4.getShoppinglist().add(li1);
		sl4.getShoppinglist().add(li2);
		
		sl5.getShoppinglist().add(li2);
		sl5.getShoppinglist().add(li3);
		sl5.getShoppinglist().add(li4);
		
		sl6.getShoppinglist().add(li7);
		sl6.getShoppinglist().add(li8);
		
		for (ShoppingList sl: shoppingLists) {
			  dataProvider.getList().add(sl);
		  }
	}
	
	public void setSelectedShoppingList(ShoppingList sl) {
		//selectedShoppingList = c;
		RootPanel.get("Details").clear();
		Notification.show("clear Details");
		sslf = new ShowShoppingListForm();
		sslf.setSelected(sl);
		Notification.show(sl.getTitle());
		RootPanel.get("Details").add(sslf);
	}
	
	private class GetAllShoppingListsCallback implements AsyncCallback<ArrayList<ShoppingList>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}
		
		@Override
		public void onSuccess(ArrayList<ShoppingList> result) {
			// TODO Auto-generated method stub
			shoppingLists = result;
			getAllShoppingLists();
		}
	}
	
	private class ShoppingListKeyProvider implements ProvidesKey<ShoppingList>{
		@Override
		public Object getKey(ShoppingList item) {
			return (item == null) ? null: item.getId();
		}
		
	}
	
	private class SelectionChangeEventHandler implements SelectionChangeEvent.Handler {
		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			ShoppingList selection = selectionModel.getSelectedObject();
			if(selection!=null) {
			setSelectedShoppingList(selection);
			}
		} 
	}
	
	private class ShoppingListCell extends AbstractCell<ShoppingList>{
		@Override
	    public void render(Context context, ShoppingList key, SafeHtmlBuilder sb) {
	      if (context != null) {
	        sb.appendHtmlConstant(key.getTitle());
	        }
	    }
	}
}
