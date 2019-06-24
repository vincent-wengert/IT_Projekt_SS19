package de.hdm.softwarepraktikum.shared;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.ListItem;
import de.hdm.softwarepraktikum.shared.bo.ListItem.Unit;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.bo.Responsibility;
import de.hdm.softwarepraktikum.shared.bo.ShoppingList;
import de.hdm.softwarepraktikum.shared.bo.Store;

public interface ShoppingListAdministrationAsync {
	
	public void init(AsyncCallback<Void> callback);

	public void createItem(String name, boolean value, AsyncCallback<Item> callback) throws IllegalArgumentException;

	void updatePerson(Person p, AsyncCallback<Void> callback);

	void getAllPersons(AsyncCallback<ArrayList<Person>> callback);

	void addFavoriteItem(Item i, Group g, AsyncCallback<Void> callback);

	void checkListItem(Integer id, Boolean checked, AsyncCallback<Void> callback);

	void createGroup(String title, ArrayList<Person> member, AsyncCallback<Group> callback);

	void createListItem(Item item, int buyerID, int storeID, int slID, int grID, double amount, Unit unit,
			Boolean isChecked, AsyncCallback<ListItem> callback);

	void createPerson(String gmail, String name, AsyncCallback<Person> callback);

	void createResponsibility(int buyerID, int storeID, int slID, AsyncCallback<Responsibility> callback);

	void createShoppingList(int ownerid, String title, int groupID, AsyncCallback<ShoppingList> callback);

	void createStore(String name, String street, int postcode, String city, int housenumber, AsyncCallback<Store> callback);

	void addGroupMembership(Person p, Group g, AsyncCallback<Void> callback);
	
	void deleteGroupMembership(Person p, Group g, AsyncCallback<Void> callback);

	void deleteItem(Item i, AsyncCallback<Void> callback);

	void deleteStore(Store s, AsyncCallback<Void> callback);

	void deleteListItem(ListItem li, AsyncCallback<Void> callback);

	void deleteResponsibility(Responsibility rs, AsyncCallback<Void> callback);

	void deleteShoppingList(ShoppingList sl, AsyncCallback<Void> callback);

	void getAllShoppingListsByGroup(Group g, AsyncCallback<ArrayList<ShoppingList>> callback);

	void getItem(int id, AsyncCallback<Item> callback);

	void getGroup(ShoppingList sl, AsyncCallback<Void> callback);

	void updateGroup(Group g, AsyncCallback<Void> callback);

	void removeFavoriteItem(Item i, Group g, AsyncCallback<Void> callback);

	void updateItem(Item i, AsyncCallback<Void> callback);
	
	void checkFav(Group group, Item i, AsyncCallback<Boolean> callback);

	void getListItem(int id, AsyncCallback<ListItem> callback);

	void getStore(int id, AsyncCallback<Store> callback);

	void getAllShoppingListsByPerson(Person p, AsyncCallback<ArrayList<ShoppingList>> callback);

	void updateListItem(ListItem li, AsyncCallback<ListItem> callback);

	void updateResponsibility(Responsibility r, AsyncCallback<Void> callback);

	void updateShoppingList(ShoppingList sl, AsyncCallback<Void> callback);
	
	void findShoppingListbyId(int id, AsyncCallback<ShoppingList> callback);

	void getAllItems(AsyncCallback<ArrayList<Item>> callback);

	void updateStore(Store s, AsyncCallback<Void> callback);

	void getAllGroupMembers(int id, AsyncCallback<ArrayList<Person>> callback);
	
	void getAllStores(AsyncCallback<ArrayList<Store>> callback);

	void getAllGroupsByPerson(Person p, AsyncCallback<ArrayList<Group>> callback);

	void getAllListItemsByShoppingLists(ShoppingList sl, AsyncCallback<ArrayList<ListItem>> callback);

	void getAllCheckedItemsByGroup(Group g, AsyncCallback<ArrayList<ListItem>> callback);

	void deleteGroup(Group g, AsyncCallback<Void> callback);
	
	void setFavAutomated(Group g, AsyncCallback<Void> callback);

	void getAllItemsByGroup(int groupId, int currentPersonId, AsyncCallback<ArrayList<Item>> callback);

	void getAllFavoriteListItemsbyGroup(Group g, Person p, ShoppingList sl,
			AsyncCallback<ArrayList<ListItem>> callback);

}
