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
	
	private ItemForm itemForm = null;
	
	private ListDataProvider<Item> dataProvider = new ListDataProvider<Item>();
	
	private ArrayList<Item> items = new ArrayList<Item>();
	
	
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
	
		
	public void setItemForm(ItemForm itemForm) {
		this.itemForm = itemForm;
	}
	
	public void updateCellList() {
		dataProvider.getList().clear();
		administration.getAllItems(new GetAllItemsCallback());
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
			getAllItems();
			dataProvider.refresh();

		}
	}
	
	//Cell muss evtl ausgelagert werden
	private static class ItemCell extends AbstractCell<Item>{
	    @Override
	    public void render(Context context, Item key, SafeHtmlBuilder sb) {
	    	if (context != null) {
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
		RootPanel.get("Details").clear();
		ItemForm itemForm = new ItemForm();
		itemForm.setSelected(i);
		itemForm.setEditable(false);
		itemForm.setInitial(false);
		RootPanel.get("Details").add(itemForm);
	}
	
	public void updateCelllist(Item i) {
		Notification.show("liste aktualisieren");
//		this.remove(cellList);
//		
//		administration.getAllItems(new GetAllItemsCallback());
//		dataProvider.addDataDisplay(cellList);
//		cellList.setRowData(0, dataProvider.getList());
//		cellList.setRowCount(items.size(), true);
		navigator.selectTab(1);

		
	}

}