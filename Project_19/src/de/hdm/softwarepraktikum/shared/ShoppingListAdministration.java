package de.hdm.softwarepraktikum.shared;

import java.sql.Date;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.ListItem;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.bo.Responsibility;
import de.hdm.softwarepraktikum.shared.bo.ShoppingList;
import de.hdm.softwarepraktikum.shared.bo.Store;

public interface ShoppingListAdministration {
	
	public void initMapper();
	
	public void updatePerson(String name);
	
	public void createPerson(Date creationDate, String gmail, String name);
	
	public Item createStore(String name, Enum unit);
	
	public void updateItem(Item i);
	
	public void deleteItem(Item i);
	
	public ArrayList<Item> getAllItems();
	
	public Item getItem(int id);
	
	public ListItem createListItem(Item item, Person buyer, Store store, ShoppingList sl);
	
	public void updateListItem(ListItem li, Person buyer, Store store, ShoppingList sl);
	
	public void checkListItem(ListItem li);
	
	public void deleteListItem(ListItem li);
	
	public void getListItem(int id);
	
	public void createGroup(String title);
	
	public void updateGroup(String title);
	
	public void getGroup(ShoppingList sl);
	
	public ArrayList<Person> getAllGroupMembers(Group g);
	
	public void deleteGroupMembership(Person p, Group g);
	
	public ShoppingList createShoppingList(Person owner, String title, Group p);
	
	public void updateShoppingList(Person owner, String title, Group p);
	
	public void deleteShoppingList(ShoppingList sl);
	
	public void getAllShoppingListsByPerson(Person p);
	
	public void getAllShoppingListsByGroup(Group g);
	
	public void addFavoriteItem(Item i, Person p);
	
	public void removeFavoriteItem(Item i, Person p);
	
	public Store createStore(String name, String street, int postcode, String city);
	
	public Store getStore(int id);
	
	public void updateStore(String name, String street, int postcode, String city);
	
	public void deleteStore(Store s);
	
	public Responsibility createResponsibility(Person buyer, Store s, ShoppingList sl);
	
	public void updateResponsibility(Person buyer, Store s, ShoppingList sl);
	
	public void deleteResponsibility(Person buyer, Store s, ShoppingList sl);
	
	
	
	

}
