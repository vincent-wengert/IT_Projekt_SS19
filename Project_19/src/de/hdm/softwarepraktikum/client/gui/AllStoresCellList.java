package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
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
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.Store;


public class AllStoresCellList extends VerticalPanel{
	
	ShoppingListAdministrationAsync administration = ClientsideSettings.getShoppinglistAdministration();
	
	private StoreKeyProvider keyProvider = null;
	private CellList<Store> cellList = new CellList<Store>(new StoreCell(), keyProvider);
	private SingleSelectionModel<Store> selectionModel = null;
	private ListDataProvider<Store> dataProvider = new ListDataProvider<Store>();
	private ArrayList<Store> stores = new ArrayList<Store>();
	private StoreForm sf = null;

//	DemoStore s1 = new DemoStore("Lidl", 70597, "Stuttgart", "Coole Straﬂe", 31);
//	DemoStore s2 = new DemoStore("Penny", 12345, "Berlin", "Uncoole Straﬂe", 32);
//	DemoStore s3 = new DemoStore("Rewe", 40997, "Hannover", "Andere Straﬂe", 33);
//	DemoStore s4 = new DemoStore("Edeka", 69420, "Amsterdam", "van Gogh Straﬂe", 34);
	
	public void onLoad() {
		getAllStores();
		keyProvider = new StoreKeyProvider();
		selectionModel = new SingleSelectionModel<Store>();
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());
		cellList.setSelectionModel(selectionModel);
		dataProvider.addDataDisplay(cellList);
		cellList.setRowCount(stores.size(), true);
		cellList.setRowData(0, dataProvider.getList());
		this.add(cellList);
		administration.getAllStores(new GetAllStoresCallback());
	}
	
	public void setSelectedStore(Store s) {
		//selectedShoppingList = c;
		RootPanel.get("Details").clear();
		Notification.show("clear Details");
		sf = new StoreForm();
		sf.setSelected(s);
		sf.setEditable(false);
		sf.setInitial(false);
		RootPanel.get("Details").add(sf);
	}
	
	public void getAllStores() {
		for (Store s: stores) {
			  dataProvider.getList().add(s);
		  }
	}
	
	private class GetAllStoresCallback implements AsyncCallback<ArrayList<Store>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}
		
		@Override
		public void onSuccess(ArrayList<Store> result) {
			// TODO Auto-generated method stub
			stores = result;
			getAllStores();
		}
	}
	
	private class StoreKeyProvider implements ProvidesKey<Store>{
		@Override
		public Object getKey(Store item) {
			return (item == null) ? null: item.getId();
		}
		
	}
	
	private class SelectionChangeEventHandler implements SelectionChangeEvent.Handler {
		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			Store selection = selectionModel.getSelectedObject();
			if(selection!=null) {
				setSelectedStore(selection);
			}
		} 
	}
	
	private class StoreCell extends AbstractCell<Store>{
		@Override
	    public void render(Context context, Store key, SafeHtmlBuilder sb) {
	      if (context != null) {
	        sb.appendHtmlConstant(key.getName());
	        }
	    }
	}
	
//	public class DemoStore{
//		//private static int counter = 0;
//		private int id;
//	    private String name;
//	    private int postLeitZahl;
//		private String ort;
//		private String strasse;
//		private int hausNummer;
//		
//		public DemoStore(String name, int plz, String ort, String strasse, int hausNr) {
//			this.name = name;
//			this.postLeitZahl = plz;
//			this.ort = ort;
//			this.strasse = strasse;
//			this.hausNummer = hausNr;
//			
//		}
//		
//		public int getPostleitZahl() {
//			return postLeitZahl;
//		}
//
//
//		public void setPostleitZahl(int postleitZahl) {
//			this.postLeitZahl = postleitZahl;
//		}
//
//
//		public String getOrt() {
//			return ort;
//		}
//
//
//		public void setOrt(String ort) {
//			this.ort = ort;
//		}
//
//
//		public String getStrasse() {
//			return strasse;
//		}
//
//
//		public void setStrasse(String strasse) {
//			this.strasse = strasse;
//		}
//
//
//		public int getHausNummer() {
//			return hausNummer;
//		}
//
//
//		public void setHausNummer(int hausNummer) {
//			this.hausNummer = hausNummer;
//		}
//	    
//	    public String getName() {
//			return name;
//		}
//	    
//	    
//		public void setName(String name) {
//			this.name = name;
//		}
//
//	    public DemoStore(String name) {
////	    	counter++;
////	    	this.id = counter;
//	    	this.name = name;
//	    }
//
//		@Override
//		public String toString() {
//			return "Store [id=" + id + ", name=" + name + "]";
//		}
//
//		public int getId() {
//			return id;
//		}
//	}
}
