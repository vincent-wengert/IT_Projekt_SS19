package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.aria.client.AlertdialogRole;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;


import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.server.ShoppingListAdministrationImpl;
import de.hdm.softwarepraktikum.shared.ShoppingListAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Item;
import java_cup.version;

public class AllItemsCellList extends VerticalPanel{
	private NavigatorPanel navigator;
	
	ShoppingListAdministrationAsync administration = ClientsideSettings.getShoppinglistAdministration();
	
	private SingleSelectionModel<Item> selectionModel = null;
	private ItemDemoKeyProvider keyProvider= null; 
	private CellList<Item> cellList = new CellList<Item>(new ItemCell(), keyProvider);
	
	private ItemForm itemForm = new ItemForm();
	
	private ListDataProvider<Item> dataProvider = new ListDataProvider<Item>();
	
	private ArrayList<Item> items = new ArrayList<Item>();
	
	private Item itemToDisplay = null;
	
	
	public void onLoad() {
		
		ItemDemoKeyProvider keyProvider = new ItemDemoKeyProvider();
		selectionModel = new SingleSelectionModel<Item>(keyProvider);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());
		cellList.setSelectionModel(selectionModel);
		
		
		
		administration.getAllItems(new GetAllItemsCallback());
		dataProvider.addDataDisplay(cellList);
		cellList.setRowData(0, dataProvider.getList());
		cellList.setRowCount(items.size(), true);
		
		this.add(cellList);
		
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
	
	public void removeItem(Item i) {
		administration.getAllItems(new GetAllItemsCallback());
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
	       
//	        if (key.isFavroite==true) {
//	    		 sb.appendHtmlConstant("&#9733");
	        	//9734 fur leeres
	        //} else {
//	    		 sb.appendHtmlConstant("&#9734");
//	    	}
	        	 sb.appendHtmlConstant(key.getName());
	        	 sb.appendHtmlConstant("&#9733");
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
		RootPanel.get("Details").add(itemForm);
	}
	
	public void updateCelllist(Item item) {
		dataProvider.getList().clear();
		administration.getAllItems(new GetAllItemsCallback());
		dataProvider.refresh();
		selectionModel.setSelected(item, true);
		//setSelectedItem(i);
		//navigator.selectTab(1)
	}
	
}