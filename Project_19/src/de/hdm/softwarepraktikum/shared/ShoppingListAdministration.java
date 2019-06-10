package de.hdm.softwarepraktikum.shared;

import java.sql.Date;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.ListItem;
import de.hdm.softwarepraktikum.shared.bo.ListItem.Unit;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.bo.Responsibility;
import de.hdm.softwarepraktikum.shared.bo.ShoppingList;
import de.hdm.softwarepraktikum.shared.bo.Store;

@RemoteServiceRelativePath("administration")
public interface ShoppingListAdministration extends RemoteService {
	
	public void init();
	
	public void updatePerson(String name);
	
	public Person createPerson(String gmail, String name);
	
	public ArrayList<Person> getAllPersons();
	
	public Item createItem(String name, boolean value);
	
	public void updateItem(Item i);
	
	public void deleteItem(Item i);
	
	public ArrayList<Item> getAllItems();
	
	public Item getItem(int id);
	
	public ListItem createListItem(Item item, int buyerID, int storeID, int slID);
	
	public void updateListItem(ListItem li);
	
	public void checkListItem(ListItem li);
	
	public void deleteListItem(ListItem li);
	
	public ListItem getListItem(int id);
	
	public void createGroup(String title, ArrayList<Person> member);
	
	public void updateGroup(Group g);
	
	public void getGroup(ShoppingList sl);
	
	public ArrayList<Person> getAllGroupMembers(Group g);
	
	public ArrayList<Group> getAllGroupsByPerson(Person p);
	
	public void deleteGroupMembership(Person p, Group g);
	
	public ShoppingList createShoppingList(int ownerid, String title, int groupID);
	
	public void updateShoppingList(ShoppingList sl);
	
	public void deleteShoppingList(ShoppingList sl);
	
	public ArrayList<ShoppingList> getAllShoppingListsByPerson(Person p);
	
	public ArrayList<ShoppingList> getAllShoppingListsByGroup(Group g);
	
	public void addFavoriteItem(Item i, Person p);
	
	public void removeFavoriteItem(Item i, Person p);
	
	public Store createStore(String name, String street, int postcode, String city, int housenumber);
	
	public Store getStore(int id);
	
	public ArrayList<Store> getAllStores();
	
	public void updateStore(Store s);
	
	public void deleteStore(Store s);
	
	public Responsibility createResponsibility(int buyerID, int storeID, int slID);
	
	public void updateResponsibility(Responsibility r);
	
	public void deleteResponsibility(Responsibility rs);

}
