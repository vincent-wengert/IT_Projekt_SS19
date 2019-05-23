package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.RootPanel;
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
	private StoreForm sf = null;

	DemoStore s1 = new DemoStore("Lidl", 70597, "Stuttgart", "Coole Straﬂe", 31);
	DemoStore s2 = new DemoStore("Penny", 12345, "Berlin", "Uncoole Straﬂe", 32);
	DemoStore s3 = new DemoStore("Rewe", 40997, "Hannover", "Andere Straﬂe", 33);
	DemoStore s4 = new DemoStore("Edeka", 69420, "Amsterdam", "van Gogh Straﬂe", 34);
	
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
	
	public void setSelectedStore(DemoStore s) {
		//selectedShoppingList = c;
		RootPanel.get("Details").clear();
		Notification.show("clear Details");
		sf = new StoreForm();
		sf.setSelected(s);
		RootPanel.get("Details").add(sf);
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
				setSelectedStore(selection);
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
	
	public class DemoStore{
		//private static int counter = 0;
		private int id;
	    private String name;
	    private int postLeitZahl;
		private String ort;
		private String strasse;
		private int hausNummer;
		
		public DemoStore(String name, int plz, String ort, String strasse, int hausNr) {
			this.name = name;
			this.postLeitZahl = plz;
			this.ort = ort;
			this.strasse = strasse;
			this.hausNummer = hausNr;
			
		}
		
		public int getPostleitZahl() {
			return postLeitZahl;
		}


		public void setPostleitZahl(int postleitZahl) {
			this.postLeitZahl = postleitZahl;
		}


		public String getOrt() {
			return ort;
		}


		public void setOrt(String ort) {
			this.ort = ort;
		}


		public String getStrasse() {
			return strasse;
		}


		public void setStrasse(String strasse) {
			this.strasse = strasse;
		}


		public int getHausNummer() {
			return hausNummer;
		}


		public void setHausNummer(int hausNummer) {
			this.hausNummer = hausNummer;
		}
	    
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
