package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;


import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppingListAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Item;
import java_cup.version;

public class AllItemssCellList extends VerticalPanel{
	private static int counter = 1;
	
	ShoppingListAdministrationAsync administration = ClientsideSettings.getShoppinglistAdministration();
	
	private SingleSelectionModel<Item> selectionModel = null;
	private ItemDemoKeyProvider keyProvider= null; 
	private CellList<Item> cellList = new CellList<Item>(new ItemCell(), keyProvider);
	
	private ItemForm sif = null;
	
	private ListDataProvider<Item> dataProvider = new ListDataProvider<Item>();
	
	private ArrayList<Item> items = new ArrayList<Item>();
//	ItemDemo p1 = new ItemDemo("Apfel", "St�ck", 3);
//	ItemDemo p2 = new ItemDemo("Banane", "St�ck", 5);
//	ItemDemo p3 = new ItemDemo("Zitrone", "St�ck", 2);
//	ItemDemo p4 = new ItemDemo("Orangensaft", "Liter", 4);
//	ItemDemo p5 = new ItemDemo("Zimt", "Stangen", 5);
//	ItemDemo p6 = new ItemDemo("Apfel", "St�ck", 3);
//	ItemDemo p7 = new ItemDemo("Banane", "St�ck", 5);
//	ItemDemo p8 = new ItemDemo("Zitrone", "St�ck", 2);
//	ItemDemo p9 = new ItemDemo("Orangensaft", "Liter", 4);
//	ItemDemo p10 = new ItemDemo("Zimt", "Stangen", 5);
//	ItemDemo p11 = new ItemDemo("Apfel", "St�ck", 3);
//	ItemDemo p12 = new ItemDemo("Banane", "St�ck", 5);
//	ItemDemo p13 = new ItemDemo("Zitrone", "St�ck", 2);
//	ItemDemo p14 = new ItemDemo("Orangensaft", "Liter", 4);
//	ItemDemo p15 = new ItemDemo("Zimt", "Stangen", 5);
	
	
	public void onLoad(){
		getAllItems();
		ItemDemoKeyProvider keyProvider = new ItemDemoKeyProvider();
		selectionModel = new SingleSelectionModel<Item>(keyProvider);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());
		cellList.setSelectionModel(selectionModel);
		
		dataProvider.addDataDisplay(cellList);
		
		cellList.setRowCount(items.size(), true);
		cellList.setRowData(0, dataProvider.getList());
		
		this.add(cellList);
		administration.getAllItems(new GetAllItemsCallback());
	}
	
	public void getAllItems() {
	
		for (Item p: items) {
			  dataProvider.getList().add(p);
		  }
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
		sif = new ItemForm();
		sif.setSelected(i);
		sif.setEditable(false);
		sif.setInitial(false);
		RootPanel.get("Details").add(sif);
	}

}