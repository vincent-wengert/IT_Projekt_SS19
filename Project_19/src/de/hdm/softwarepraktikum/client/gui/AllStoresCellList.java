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

/**
 * Diese CellList dient als Navigationselement in der Benutzeroberfläche für Objekte der Klasse <code>Store</code>. 
 * 
 * @author Jan Duwe, Vincent Wengert
 *
 */
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
	
	
	/**
	 * In dieser Methode werden die darzustellenden Widgets der Klasse hinzugefügt. 
	 * Die Widgets werden innerhalb dieser Methode ebenfalls formatiert.
	 * Der Methodenaufruf erfolgt beim Aufruf der Klasse.
	 */
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
	
	/**
	 * ************************* ABSCHNITT der Methoden *************************
	 */
	
	/**
	 * Darstellung des in der CellList ausgewählten <code>Store</code> Objekts.
	 * @param s das ausgewählte <code>Store</code> Objekt. 
	 */
	public void setSelectedStore(Store s) {
		storeToDisplay = s;
		RootPanel.get("Details").clear();
		sf.setSelected(s);
		sf.setEditable(false);
		sf.setInitial(false);
		RootPanel.get("Details").add(sf);
	}
	
	/**
	 * Methode zum Auslesen des SingleSelectionModel Objekts innerhalb der Klasse <code>NavigatorPanel</code>.
	 * @return das SingleSelectionModel der Klasse <code>AllStoresCellList</code>.
	 */
	public SingleSelectionModel<Store> getSelectionModel() {
		return this.selectionModel;
	}
	
	/**
	 * Methode zum Setzen der <code>StoreForm</code> innerhalb der Klasse <code>NavigatorPanel</code>.
	 * @param sf die zu setzende <code>StoreForm</code>.
	 */
	public void setStoreForm(StoreForm sf) {
		this.sf = sf;
	}
	
	/**
	 * Methode zum Setzen des Boolean Werts initial. 
	 * @param initial der Boolean Wert, der bei dem Wert true zum Anlegen eines <code>Store</code> Objekt benutzt wird oder dem Wert false ein bestehendes <code>Store</code> aktualisiert.
	 */
	public void setInitial(Boolean initial) {
		this.initial = initial;
	}
	
	/**
	 * Methode zum Aktualisieren des ListDataProvider der Klasse <code>AllStoresCellList</code>. Anschließend erfolgt die Auswahl auf das übergebene <code>Store</code> Objekt.
	 * @param store <code>Store</code> Objekt, das nach dem Aktualisieren ausgewählt wird.
	 */
	public void updateCellList(Store store) {
		dataProvider.getList().clear();
		administration.getAllStores(new GetAllStoresCallback());
		dataProvider.refresh();
		selectionModel.setSelected(store, true);
	}
	
	/**
	 * ************************* ABSCHNITT der Callbacks *************************
	 */
	
	/**
	 * CallBack mit dem alle <code>Store</code> Einträge aus der Datenbank geladen werden.
	 * Anschließend werden alle geladenen Objekte dem ListDataProvider hinzugefügt.
	 */
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
	
	/**
	 * ************************* ABSCHNITT der KeyProvider *************************
	 */
	
	/**
	 * Versieht jedes darzustellende <code>Store</code> Objekt mit einer eindeutigen ID.
	 */
	private class StoreKeyProvider implements ProvidesKey<Store>{
		@Override
		public Object getKey(Store item) {
			return (item == null) ? null: item.getId();
		}
		
	}
	
	/**
	 * ************************* ABSCHNITT der EventHandler *************************
	 */
	
	/**
	 * Ein SelectionHandler der das in dem SingleSelectionModel der <code>AllStoresCellList</code> ausgewählte <code>Store</code> Objekt als ausgewählt setzt.
	 */
	private class SelectionChangeEventHandler implements SelectionChangeEvent.Handler {
		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			Store selection = selectionModel.getSelectedObject();
			if(selection!=null) {
				setSelectedStore(selection);
			}
		} 
	}
	
	/**
	 * ************************* ABSCHNITT der Cells *************************
	 */
	
	/**
	 * <code>StoreCell</code> Objekt zum Rendern der anzuzeigenden <code>Store</code> Objekte.
	 * Wird mit dem Namen des <code>Store</code> befüllt.
	 */
	private class StoreCell extends AbstractCell<Store>{
		@Override
	    public void render(Context context, Store key, SafeHtmlBuilder sb) {
	      if (context != null) {
	        sb.appendHtmlConstant(key.getName());
	        }
	    }
	}

}
