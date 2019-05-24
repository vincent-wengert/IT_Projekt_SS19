package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;


public class AllShoppingListsCellList extends VerticalPanel{
	private static int itemCounter = 1;
	private static int listCounter = 1;
	private ShoppingListKeyProvider keyProvider = null;
	private CellList<DemoShoppingList> cellList = new CellList<DemoShoppingList>(new ShoppingListCell(), keyProvider);
	private SingleSelectionModel<DemoShoppingList> selectionModel = null;
	private ListDataProvider<DemoShoppingList> dataProvider = new ListDataProvider<DemoShoppingList>();
	private ArrayList<DemoShoppingList> shoppingLists = new ArrayList<DemoShoppingList>();
	private ShowShoppingListForm sslf;
	
	/**
	 * Erstellen von Demo-Objekten
	 */
	
	DemoShoppingList sl1 = new DemoShoppingList("Liste 1");
	DemoShoppingList sl2 = new DemoShoppingList("Liste 2");		
	DemoShoppingList sl3 = new DemoShoppingList("Liste 3");

	ItemDemo i1 = new ItemDemo("Apfel", "Kilo", 1);
	ItemDemo i2 = new ItemDemo("Birne", "Kilo", 2);
	ItemDemo i3 = new ItemDemo("Zitrone", "Kilo", 1);
	ItemDemo i4 = new ItemDemo("Wassermelone", "Kilo", 100);
	ItemDemo i5 = new ItemDemo("Orange", "Kilo", 4);
	
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
		
		sl1.addToList(i1);
		sl1.addToList(i2);
		sl2.addToList(i3);
		sl3.addToList(i4);
		sl3.addToList(i5);
		
		for (DemoShoppingList sl: shoppingLists) {
			  dataProvider.getList().add(sl);
		  }
	}
	
	public void setSelectedShoppingList(DemoShoppingList sl) {
		//selectedShoppingList = c;
		
		RootPanel.get("Details").clear();
		Notification.show("clear Details");
		sslf = new ShowShoppingListForm();
		sslf.setSelected(sl);
		Notification.show(sl.getName());
		RootPanel.get("Details").add(sslf);
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
			setSelectedShoppingList(selection);
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
	
	public class DemoShoppingList{
		//private static int counter = 0;
		private int id;
	    private String name;
	    private ArrayList<ItemDemo> items;

	    
	    public String getName() {
			return name;
		}
	    
	    public void addToList(ItemDemo p) {
	    	this.items.add(p);
	    }
	    
		public void setName(String name) {
			this.name = name;
		}

	    public DemoShoppingList(String name) {
	    	listCounter++;
	    	this.id = listCounter;
	    	this.name = name;
	    	items = new ArrayList<ItemDemo>();
	    }

		@Override
		public String toString() {
			return "ShoppingList [id=" + id + ", name=" + name + "]";
		}

		public int getId() {
			return id;
		}
		
		public ArrayList<ItemDemo> getProductsByList() {
			return this.items;
		}
	}
	
	public class ItemDemo {
			
		private int id;
		private String name;
		private String unit;
		private int amount;
	

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getId() {
			return id;
		}
	    
	    public String getUnit() {
			return unit;
		}

		public void setUnit(String unit) {
			this.unit = unit;
		}

		public int getAmount() {
			return amount;
		}

		public void setAmount(int amount) {
			this.amount = amount;
		}

		public void setId(int id) {
			this.id = id;
		}

		public ItemDemo(String name, String unit, int amount) {
	        itemCounter++;
	        this.id = itemCounter;
	        this.name = name;
	        this.unit = unit;
	        this.amount = amount;
	      }

	}
}
