package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
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
	private StoreForm sf = new StoreForm();
	private Store storeToDisplay = null;
	private Boolean initial = true;
	
	public void onLoad() {
		administration.getAllStores(new GetAllStoresCallback());
		keyProvider = new StoreKeyProvider();
		selectionModel = new SingleSelectionModel<Store>();
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());
		cellList.setSelectionModel(selectionModel);
		dataProvider.addDataDisplay(cellList);
		cellList.setRowCount(stores.size(), true);
		cellList.setRowData(0, dataProvider.getList());
		this.add(cellList);
	}
	
	public void setSelectedStore(Store s) {
		storeToDisplay = s;
		RootPanel.get("Details").clear();
		sf.setSelected(s);
		sf.setEditable(false);
		sf.setInitial(false);
		RootPanel.get("Details").add(sf);
	}
	
	public SingleSelectionModel<Store> getSelectionModel() {
		return this.selectionModel;
	}
	
	public void setStoreForm(StoreForm sf) {
		this.sf = sf;
	}
	
	public void setInitial(Boolean initial) {
		this.initial = initial;
	}
	
	public void updateCellList(Store store) {
		dataProvider.getList().clear();
		administration.getAllStores(new GetAllStoresCallback());
		dataProvider.refresh();
		selectionModel.setSelected(store, true);
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
			dataProvider.getList().clear();
				for (Store s: stores) {
					  dataProvider.getList().add(s);
				  }
			dataProvider.refresh();
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

}
