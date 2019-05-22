package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;



public class AllStoresCellList extends VerticalPanel{
	
	private StoreKeyProvider keyProvider = null;
	private CellList<DemoStore> cellList = new CellList<DemoStore>(new StoreCell(), keyProvider);
	private SingleSelectionModel<DemoStore> selectionModel = null;
	private ListDataProvider<DemoStore> dataProvider = new ListDataProvider<DemoStore>();
	private ArrayList<DemoStore> stores = new ArrayList<DemoStore>();

	DemoStore s1 = new DemoStore("Lidl");
	DemoStore s2 = new DemoStore("Penny");
	DemoStore s3 = new DemoStore("Rewe");
	DemoStore s4 = new DemoStore("Edeka");
	
	public void onLoad() {
		getAllStores();
		keyProvider = new StoreKeyProvider();
		selectionModel = new SingleSelectionModel<DemoStore>();
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());
		cellList.setSelectionModel(selectionModel);
		dataProvider.addDataDisplay(cellList);
		cellList.setRowCount(stores.size(), true);
		cellList.setRowData(0, dataProvider.getList());
		this.add(cellList);
	}
	
	public void getAllStores() {
		stores.add(s1);
		stores.add(s2);
		stores.add(s3);
		stores.add(s4);
		
		for (DemoStore s: stores) {
			  dataProvider.getList().add(s);
		  }
	}
	
	private class StoreKeyProvider implements ProvidesKey<DemoStore>{
		@Override
		public Object getKey(DemoStore item) {
			return (item == null) ? null: item.getId();
		}
		
	}
	
	private class SelectionChangeEventHandler implements SelectionChangeEvent.Handler {
		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			DemoStore selection = selectionModel.getSelectedObject();
			if(selection!=null) {
			//implement what happens if object gets selected in cell at a later date
			}
		} 
	}
	
	private class StoreCell extends AbstractCell<DemoStore>{
		@Override
	    public void render(Context context, DemoStore key, SafeHtmlBuilder sb) {
	      if (context != null) {
	        sb.appendHtmlConstant(key.getName());
	        }
	    }
	}
	
	private class DemoStore{
		//private static int counter = 0;
		private int id;
	    private String name;

	    
	    public String getName() {
			return name;
		}
	    
	    
		public void setName(String name) {
			this.name = name;
		}

	    public DemoStore(String name) {
//	    	counter++;
//	    	this.id = counter;
	    	this.name = name;
	    }

		@Override
		public String toString() {
			return "Store [id=" + id + ", name=" + name + "]";
		}

		public int getId() {
			return id;
		}
	}
}
