package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.softwarepraktikum.shared.bo.ShoppingList;

public class AllShoppingListsCellList extends VerticalPanel{
	private ShoppingListKeyProvider keyProvider = null;
	private CellList<DemoShoppingList> cellList = new CellList<DemoShoppingList>(new ShoppingListCell(), keyProvider);
	private SingleSelectionModel<DemoShoppingList> selectionModel = null;
	private ListDataProvider<DemoShoppingList> dataProvider = new ListDataProvider<DemoShoppingList>();
	private ArrayList<DemoShoppingList> shoppingLists = new ArrayList<DemoShoppingList>();
	
	/**
	 * Erstellen von Demo-Objekten
	 */
	
	DemoShoppingList sl1 = new DemoShoppingList("Liste 1");
	DemoShoppingList sl2 = new DemoShoppingList("Liste 2");		
	DemoShoppingList sl3 = new DemoShoppingList("Liste 3");
	
	public void onLoad(){
		getAllShoppingLists();
		keyProvider = new ShoppingListKeyProvider();
		selectionModel = new SingleSelectionModel<DemoShoppingList>();
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());
		cellList.setSelectionModel(selectionModel);
		dataProvider.addDataDisplay(cellList);
		cellList.setRowCount(shoppingLists.size(), true);
		cellList.setRowData(0, dataProvider.getList());
		this.add(cellList);
	}
	
	public void getAllShoppingLists() {
		shoppingLists.add(sl1);
		shoppingLists.add(sl2);
		shoppingLists.add(sl3);
		
		for (DemoShoppingList sl: shoppingLists) {
			  dataProvider.getList().add(sl);
		  }
	}
	
	private class ShoppingListKeyProvider implements ProvidesKey<DemoShoppingList>{
		@Override
		public Object getKey(DemoShoppingList item) {
			return (item == null) ? null: item.getId();
		}
		
	}
	
	private class SelectionChangeEventHandler implements SelectionChangeEvent.Handler {
		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			DemoShoppingList selection = selectionModel.getSelectedObject();
			if(selection!=null) {
			//implement what happens if object gets selected in cell at a later date
			}
		} 
	}
	
	private class ShoppingListCell extends AbstractCell<DemoShoppingList>{
		@Override
	    public void render(Context context, DemoShoppingList key, SafeHtmlBuilder sb) {
	      if (context != null) {
	        sb.appendHtmlConstant(key.getName());
	        }
	    }
	}
	
	/*
	 * Demo-Objekt um Liste zu testen
	 */
	
	private class DemoShoppingList{
		//private static int counter = 0;
		private int id;
	    private String name;

	    
	    public String getName() {
			return name;
		}
	    
	    
		public void setName(String name) {
			this.name = name;
		}

	    public DemoShoppingList(String name) {
//	    	counter++;
//	    	this.id = counter;
	    	this.name = name;
	    }

		@Override
		public String toString() {
			return "ShoppingList [id=" + id + ", name=" + name + "]";
		}

		public int getId() {
			return id;
		}
	}
}
