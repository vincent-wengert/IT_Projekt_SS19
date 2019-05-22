package de.hdm.softwarepraktikum.server;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.server.db.ListItemMapper;
import de.hdm.softwarepraktikum.server.db.PersonMapper;
import de.hdm.softwarepraktikum.server.db.ResponsibilityMapper;
import de.hdm.softwarepraktikum.server.db.ShoppingListMapper;
import de.hdm.softwarepraktikum.server.db.StoreMapper;
import de.hdm.softwarepraktikum.server.db.GroupMapper;
import de.hdm.softwarepraktikum.server.db.ItemMapper;
import de.hdm.softwarepraktikum.shared.ShoppingListAdministration;
import de.hdm.softwarepraktikum.shared.bo.BusinessObject;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.ListItem;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.bo.Responsibility;
import de.hdm.softwarepraktikum.shared.bo.ShoppingList;
import de.hdm.softwarepraktikum.shared.bo.Store;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Diese Klasse implementiert das Interface ShoppingListAdministration.
 * Hier befindet sich, bis auf ReportGeneratorImpl, die komplette
 * Applikationslogik.
 * @author Jakob Schaal & Luca Randecker
 * @version 1.0
 */

public class ShoppingListAdministrationImpl extends RemoteServiceServlet implements ShoppingListAdministration  {
	
	private static final long serialVersionUID = 1L;
	
/*
 * Referenz auf den listItemMapper, welcher ListItem Objekte mit der Datenbank
 * abgleicht.
 */
private ListItemMapper listItemMapper = null;

/*
 * Referenz auf den itemMapper, welcher item Objekte mit der Datenbank
 * abgleicht.
 */
private ItemMapper itemMapper = null;

/*
 * Referenz auf den personMapper, welcher Person Objekte mit der Datenbank
 * abgleicht.
 */
private PersonMapper personMapper = null;

/*
 * Referenz auf den shoppingListMapper, welcher ShoppingList Objekte mit der Datenbank
 * abgleicht.
 */
private ShoppingListMapper shoppingListMapper = null;

/*
 * Referenz auf den StoreMapper, welcher Store Objekte mit der Datenbank
 * abgleicht.
 */
private StoreMapper storeMapper = null;

/*
 * Referenz auf den groupMapper, welcher Group Objekte mit der Datenbank
 * abgleicht.
 */
private GroupMapper groupMapper = null;

/*
 * Referenz auf den responsibilityMapper, welcher Responsibility Objekte mit der Datenbank
 * abgleicht.
 */
private ResponsibilityMapper responsibilityMapper = null;


/**
 * Ein <code>RemoteServiceServlet</code> wird unter GWT mittels
 * <code>GWT.create(Klassenname.class)</code> Client-seitig erzeugt. Hierzu ist
 * ein solcher No-Argument-Konstruktor anzulegen.
 * 
 * @see #init()
 * @throws IllegalArgumentException
 */
	public ShoppingListAdministrationImpl() throws IllegalArgumentException {

	}
	
	
	//Initialisierung aller Mapper in der Klasse
	public void InitMapper() throws IllegalArgumentException {
		this.listItemMapper = ListItemMapper.listitemMapper();
		this.itemMapper = ItemMapper.itemMapper();
		this.personMapper = PersonMapper.personMapper();
		this.shoppingListMapper = ShoppingListMapper.shoppinglistMapper();
		this.storeMapper = StoreMapper.storeMapper();
		this.groupMapper = GroupMapper.groupMapper();
		this.responsibilityMapper = ResponsibilityMapper.responsibilityMapper();
		
		
	}
	


	


	@Override
	public void updatePerson(String name) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void createPerson(Date creationDate, String gmail, String name) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Item createStore(String name, Enum unit) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void updateItem(Item i) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void deleteItem(Item i) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public ArrayList<Item> getAllItems() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Item getItem(int id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ListItem createListItem(Item item, Person buyer, Store store, ShoppingList sl) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void updateListItem(ListItem li, Person buyer, Store store, ShoppingList sl) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void checkListItem(ListItem li) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void deleteListItem(ListItem li) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void getListItem(int id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void createGroup(String title) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateGroup(String title) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void getGroup(ShoppingList sl) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public ArrayList<Person> getAllGroupMembers(Group g) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ShoppingList createShoppingList(Person owner, String title, Group p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void updateShoppingList(Person owner, String title, Group p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void deleteShoppingList(ShoppingList sl) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void getAllShoppingListsByPerson(Person p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void getAllShoppingListsByGroup(Group g) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void addFavoriteItem(Item i, Person p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void removeFavoriteItem(Item i, Person p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Store createStore(String name, String street, int postcode, String city) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Store getStore(int id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void updateStore(String name, String street, int postcode, String city) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void deleteStore(Store s) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Responsibility createResponsibility(Person buyer, Store s, ShoppingList sl) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void updateResponsibility(Person buyer, Store s, ShoppingList sl) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void deleteResponsibility(Person buyer, Store s, ShoppingList sl) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void initMapper() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void deleteGroupMembership(Person p, Group g) {
		// TODO Auto-generated method stub
		
	}
	
	
}	
