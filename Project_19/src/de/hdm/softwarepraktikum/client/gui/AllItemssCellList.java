package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import de.hdm.softwarepraktikum.shared.bo.Item;
import java_cup.version;

public class AllItemssCellList extends VerticalPanel{
	private static int counter = 1;
	
	private SingleSelectionModel<ItemDemo> selectionModel = null;
	private ItemDemoKeyProvider keyProvider= null; 
	private CellList<ItemDemo> cellList = new CellList<ItemDemo>(new ItemCell(), keyProvider);
	
	private ItemForm sif = null;
	
	private ListDataProvider<ItemDemo> dataProvider = new ListDataProvider<ItemDemo>();
	
	private ArrayList<ItemDemo> items = new ArrayList<ItemDemo>();
	ItemDemo p1 = new ItemDemo("Apfel", "St�ck", 3);
	ItemDemo p2 = new ItemDemo("Banane", "St�ck", 5);
	ItemDemo p3 = new ItemDemo("Zitrone", "St�ck", 2);
	ItemDemo p4 = new ItemDemo("Orangensaft", "Liter", 4);
	ItemDemo p5 = new ItemDemo("Zimt", "Stangen", 5);
	ItemDemo p6 = new ItemDemo("Apfel", "St�ck", 3);
	ItemDemo p7 = new ItemDemo("Banane", "St�ck", 5);
	ItemDemo p8 = new ItemDemo("Zitrone", "St�ck", 2);
	ItemDemo p9 = new ItemDemo("Orangensaft", "Liter", 4);
	ItemDemo p10 = new ItemDemo("Zimt", "Stangen", 5);
	ItemDemo p11 = new ItemDemo("Apfel", "St�ck", 3);
	ItemDemo p12 = new ItemDemo("Banane", "St�ck", 5);
	ItemDemo p13 = new ItemDemo("Zitrone", "St�ck", 2);
	ItemDemo p14 = new ItemDemo("Orangensaft", "Liter", 4);
	ItemDemo p15 = new ItemDemo("Zimt", "Stangen", 5);
	
	
	public void onLoad(){
		getAllItems();
		ItemDemoKeyProvider keyProvider = new ItemDemoKeyProvider();
		selectionModel = new SingleSelectionModel<ItemDemo>(keyProvider);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());
		cellList.setSelectionModel(selectionModel);
		
		dataProvider.addDataDisplay(cellList);
		
		cellList.setRowCount(items.size(), true);
		cellList.setRowData(0, dataProvider.getList());
		
		this.add(cellList);
	}
	
	public void getAllItems() {
		items.add(p1);
		items.add(p2);
		items.add(p3);
		items.add(p4);
		items.add(p5);
		items.add(p6);
		items.add(p7);
		items.add(p8);
		items.add(p9);
		items.add(p10);
		items.add(p11);
		items.add(p12);
		items.add(p13);
		items.add(p14);
		items.add(p15);
		
		for (ItemDemo p: items) {
			  dataProvider.getList().add(p);
		  }
	}
	
	//Cell muss evtl ausgelagert werden
	private static class ItemCell extends AbstractCell<ItemDemo>{
	    @Override
	    public void render(Context context, ItemDemo key, SafeHtmlBuilder sb) {
	    	if (context != null) {
	        sb.appendHtmlConstant(key.getName());										
	      }
	    }
	}
	
	private class ItemDemoKeyProvider implements ProvidesKey<ItemDemo>{
		@Override
		public Object getKey(ItemDemo item) {
			return (item == null) ? null: item.getId();
		}
	}
	
	private class SelectionChangeEventHandler implements SelectionChangeEvent.Handler { 
		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			ItemDemo selection = selectionModel.getSelectedObject();
			if(selection!=null) {
				setSelectedItem(selection);
			}
		}
	}
	
	public void setSelectedItem(ItemDemo i){
		RootPanel.get("Details").clear();
		Notification.show("clear Details");
		sif = new ItemForm();
		sif.setSelected(i);
		sif.setEditable(false);
		sif.setInitial(false);
		RootPanel.get("Details").add(sif);
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
	        counter++;
	        this.id = counter;
	        this.name = name;
	        this.unit = unit;
	        this.amount = amount;
	      }

	}
}