package de.hdm.softwarepraktikum.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.ListItem.Unit;

public interface ShoppingListAdministrationAsync {
	
	public void initMapper(AsyncCallback<Void> callback);

	public void createItem(String name, Unit unit, AsyncCallback<Item> callback) throws IllegalArgumentException;
}
