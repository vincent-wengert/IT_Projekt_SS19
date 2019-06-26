package de.hdm.softwarepraktikum.shared;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.ListItem;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.bo.Responsibility;
import de.hdm.softwarepraktikum.shared.bo.ShoppingList;
import de.hdm.softwarepraktikum.shared.bo.Store;

@RemoteServiceRelativePath("administration")
public interface ShoppingListAdministration extends RemoteService {
	
	public void init();
	
	public void updatePerson(Person p);
	
	public void deletePerson(Person p);
	
	public Person createPerson(String gmail, String name);
	
	public ArrayList<Person> getAllPersons();
	
	Item createItem(String name, boolean value, int ownerID);
	
	public void updateItem(Item i);
	
	public void deleteItem(Item i);
	
	public ArrayList<Item> getAllItems();
	
	public ArrayList<Item> getAllItemsByGroup(int groupId, int currentPersonId);
	
	public ArrayList<Item> getAllFavoriteListItemsbyGroup (Group g);
	
	public Item getItem(int id);
	
	public ListItem createListItem(Item item, int buyerID, int storeID, int slID, int grID, double amount, String unit, Boolean isChecked);
	
	public ListItem updateListItem(ListItem li);
	
	public Boolean checkForExistingListItems(Integer id);
	
	public void checkListItem(Integer id, Boolean checked);
	
	public void deleteListItem(ListItem li);
	
	public ListItem getListItem(int id);
	
	public Group createGroup(String title, ArrayList<Person> member);
	
	public void updateGroup(Group g);
	
	public void getGroup(ShoppingList sl);
	
	public ArrayList<Person> getAllGroupMembers(int id);
	
	public ArrayList<Group> getAllGroupsByPerson(Person p);
	
	public void addGroupMembership(Person p, Group g);
	
	public void deleteGroupMembership(Person p, Group g);
	
	public ShoppingList createShoppingList(int ownerid, String title, int groupID);
	
	public void updateShoppingList(ShoppingList sl);
	
	public void deleteShoppingList(ShoppingList sl);
	
	public ArrayList<ShoppingList> getAllShoppingListsByPerson(Person p);
	
	public ArrayList<ShoppingList> getAllShoppingListsByGroup(Group g);
	
	public ArrayList<ListItem> getAllListItemsByShoppingLists(ShoppingList sl);
	
	public ShoppingList findShoppingListbyId(int id);
	
	public void addFavoriteItem(Item i, Group g);
	
	public void removeFavoriteItem(Item i, Group g);
	
	public Boolean checkFav(Group group , Item i);
	
	public Store createStore(String name, String street, int postcode, String city, int housenumber);
	
	public Store getStore(int id);
	
	public ArrayList<Store> getAllStores();
	
	public void updateStore(Store s);
	
	public void deleteStore(Store s);
	
	public Boolean checkforExisitingStores(Integer storeId);
	
	public Responsibility createResponsibility(int buyerID, int storeID, int slID);
	
	public void updateResponsibility(Responsibility r);
	
	public void deleteResponsibility(Responsibility rs);

	public ArrayList<ListItem> getAllCheckedItemsByGroup(Group g);
	
	public void deleteGroup(Group g);

	public void setFavAutomated(Group g);
	
}
