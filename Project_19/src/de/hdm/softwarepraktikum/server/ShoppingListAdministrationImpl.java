package de.hdm.softwarepraktikum.server;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.server.db.ListItemMapper;
import de.hdm.softwarepraktikum.server.db.PersonMapper;
import de.hdm.softwarepraktikum.server.db.ResponsibilityMapper;
import de.hdm.softwarepraktikum.server.db.ShoppingListMapper;
import de.hdm.softwarepraktikum.server.db.StoreMapper;
import de.hdm.softwarepraktikum.client.gui.Notification;
import de.hdm.softwarepraktikum.server.db.GroupMapper;
import de.hdm.softwarepraktikum.server.db.ItemMapper;
import de.hdm.softwarepraktikum.shared.ShoppingListAdministration;
import de.hdm.softwarepraktikum.shared.bo.BusinessObject;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.ListItem;
import de.hdm.softwarepraktikum.shared.bo.ListItem.Unit;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.bo.Responsibility;
import de.hdm.softwarepraktikum.shared.bo.ShoppingList;
import de.hdm.softwarepraktikum.shared.bo.Store;

import com.google.gwt.user.client.Window;
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
	public void init() throws IllegalArgumentException {
		this.listItemMapper = ListItemMapper.listitemMapper();
		this.itemMapper = ItemMapper.itemMapper();
		this.personMapper = PersonMapper.personMapper();
		this.shoppingListMapper = ShoppingListMapper.shoppinglistMapper();
		this.storeMapper = StoreMapper.storeMapper();
		this.groupMapper = GroupMapper.groupMapper();
		this.responsibilityMapper = ResponsibilityMapper.responsibilityMapper();
		
		
	}
	
	/**
	 * Methode um ein <code>Person</code> Objekt anzulegen.
	 * @param Name der Person und E-Mail Adresse
	 * @return das in die Datenbank gespeicherte Person Objekt wird zurückgegeben
	 */
	public Person createPerson(String gmail, String name) throws IllegalArgumentException {
		Person p = new Person();
		p.setGmail(gmail);
		p.setName(name);
		
		/**
		 * Setzen einer vorläufigen ID. Der insert Aufruf liefert anschließend
		 * ein Objekt dessen Nummer mit der Datenbank konsistent ist.
		 */
		p.setId(1);
		
		return this.personMapper.insert(p);
		
	}
	
	/**
	 * Methode um Änderungen dem zu übergebenden Person Objekt in die Datenbank 
	 * zu schreiben
	 */

	public void updatePerson(Person p) throws IllegalArgumentException {
		personMapper.update(p);
		
	}
	
	/**
	 * Methode um ein <code>Person</code> Objekt aus der Datenbank zu löschen
	 * @param p
	 * @throws IllegalArgumentException
	 */
	
	public void deletePerson(Person p) throws IllegalArgumentException {
		personMapper.delete(p);
	}
	
	/**
	 * Gibt die <code>Person</code> zurück, welcher die übergebene Id besitzt.
	 * @param id
	 * @return Das PErson Objekt wird zurückgegeben
	 * @throws IllegalArgumentException
	 */
	
	public Person getPerson(int id) throws IllegalArgumentException {
		return personMapper.findById(id);
	}
	
	/** Wird das überhaupt benötigt??
	 * Methode um eine ArrayList aller Personen einer Gruppe zu erhalten
	 * @return
	 * @throws IllegalArgumentException
	 */
	
	 // public ArrayList<Person> getAllPersons() throws IllegalArgumentException {
//		return this.personMapper.findAll();
//	}

	
	/**
	 * Methode, um ein <code>Item</code> in der Datenbank zu anzulegen.
	 * @param name
	 * @param isGlobal
	 * @return Insert 
	 * @throws IllegalArgumentException
	 */
	
	public Item createItem(String name, boolean value) throws IllegalArgumentException {
		
		Item i = new Item();
		
		i.setName(name);
		i.setIsGlobal(value);
		
		/**
		 * Setzen einer vorläufigen ID. Der insert Aufruf liefert dann ein Objekt,
		 * dessen Numme rmit de rDatenbank konsistent ist.
		 */
		
		i.setId(1);
		
		/**
		 * Objekt in Datenbank speichern.
		 */
		
		return this.itemMapper.insert(i);
	}
	
	/**
	 * Methode um Änderungen dem zu übergebenden Item Obejekt in die Datenbank zu schreiben
	 */


	public void updateItem(Item i) throws IllegalArgumentException {
		itemMapper.update(i);
	}
	
	/**
	 * Methode um ein <code>Item</code> Objekt aus der Datenbank zu löschen
	 * @param i
	 * @throws IllegalArgumentException
	 */


	public void deleteItem(Item i) throws IllegalArgumentException {
		itemMapper.delete(i);
	}
	
	/**
	 * Gibt die <code>Person</code> zurück, welcher die übergebene Id besitzt.
	 * @param id
	 * @return Das Item Objekt wird zurückgegeben
	 * @throws IllegalArgumentException
	 */

	public Item getItem(int id) throws IllegalArgumentException {
		
		Item i = itemMapper.findById(id);
		
		if (c != null) {
			return i;
		}
		return null;
	}
	
	/**
	 * Methode um alle in der Datenbank gespeicherten <code>Item</code> Objekte als ArrayList auszugeben
	 * @return ArrayList<Item>
	 */
	
	public ArrayList<Item> getAllItems() throws IllegalArgumentException{
		return this.itemMapper.findAll();
	}
	
	/**
	 * Methode, um ein <code>ListItem</code> Objekt in der Datenbank anzulegen
	 * @param zugehöriges Item, BuyerID, storeID, slID
	 * @return Das in die Datenbank gespeicherte ListITemObjekt wird zurückgegeben
	 */
	public ListItem createListItem(Item item, int buyerID, int storeID, int slID) throws IllegalArgumentException {
		
		ListItem li = new ListItem();
		
		li.setIt(item);
		li.setBuyerID(buyerID);
		li.setStoreID(storeID);
		li.setSlID(slID);
		
		/**
		 * Setzen einer vorlÃ¤ufigen ID. Der Insert Aufruf liefert dann ein Objekt,
		 * dessen Nummer mit der Datenbank konsistent ist.
		 */
		
		li.setId(1);
		
		return this.listItemMapper.insert(li);
	}
	
	/**
	 * Methode um Änderungen dem zu übergebenden ListItem Obejekt in die Datenbank zu schreiben
	 */
	
	public void updateListItem(ListItem li) throws IllegalArgumentException {
		listItemMapper.update(li);
	}
	
	/**
	 * Methode um ein <code>ListItem</code> Objekt aus der Datenbank zu löschen
	 * @param li
	 * @throws IllegalArgumentException
	 */

	public void deleteListItem(ListItem li) throws IllegalArgumentException {
		listItemMapper.delete(li);
	}

// setcheck?
	public void checkListItem(ListItem li) throws IllegalArgumentException {
		this.listItemMapper.checkListItem(li);
	}

	/**
	 * Gibt das <code>ListItem</code> zurück, welcher die übergebene Id besitzt.
	 * @param id
	 * @return Das ListItem Objekt wird zurückgegeben
	 * @throws IllegalArgumentException
	 */

	public ListItem getListItem(int id) throws IllegalArgumentException {
		return this.listItemMapper.findById(id);
	}
	
	public ArrayList<ListItem> getAllCheckedItemsByGroup(Group g) throws IllegalArgumentException {
		return this.groupMapper.
	}
	
	

	public void createGroup(String title) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		Group g = new Group();
		
		g.setTitle(title);
		
		/**
		 * Setzen einer vorlÃ¤ufigen ID. Der Insert Aufruf liefert dann ein Objekt,
		 * dessen Nummer mit der Datenbank konsistent ist.
		 */
		
		g.setId(1);
		
	}

	public void updateGroup(Group g) throws IllegalArgumentException {
		groupMapper.update(g);
	}


	public void getGroup(ShoppingList sl) throws IllegalArgumentException {
		
	}


	@Override
	public ArrayList<Person> getAllGroupMembers(Group g) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.personMapper.findAllGroupMembers();
	}


	@Override
	public ShoppingList createShoppingList(int ownerid, String title, int groupID) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		ShoppingList sl = new ShoppingList();
		sl.setTitle(title);
		sl.setOwnerID(ownerid);
		sl.setGroupID(groupID);
		
		/**
		 * Setzen einer vorlÃ¤ufigen ID. Der Insert Aufruf liefert dann ein Objekt,
		 * dessen Nummer mit der Datenbank konsistent ist.
		 */
		
		sl.setId(1);
		
		return this.shoppingListMapper.insert(sl);
	}


	@Override
	public void updateShoppingList(ShoppingList sl) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		shoppingListMapper.update(sl);
	}


	@Override
	public void deleteShoppingList(ShoppingList sl) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		shoppingListMapper.delete(sl);
	}


	@Override
	public ArrayList<ShoppingList> getAllShoppingListsByPerson(Person p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.shoppingListMapper.findByMember(p);
	}


	@Override
	public ShoppingList getAllShoppingListsByGroup(Group g) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
		return this.shoppingListMapper.findByGroup(g);
		
	}


	@Override
	public void addFavoriteItem(Item i, Person p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
		personMapper.findByName(p.getName());
		
		ArrayList<Item> favItem = personMapper.allFavoriteItems();
		favItem.add(i);
		
		personMapper.update(p);
	}


	@Override
	public void removeFavoriteItem(Item i, Person p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		personMapper.removeFavoriteItem();
	}

	public ArrayList<Item> getFavItems(Person p) throws IllegalArgumentException {
		return this.personMapper.findFav(p);
	}

	@Override
	public Store createStore(String name, String street, int postcode, String city, int housenumber) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
		Store s = new Store();
		
		s.setName(name);
		s.setStreet(street);
		s.setPostcode(postcode);
		s.setCity(city);
		s.setHouseNumber(housenumber);
		
		
		return this.storeMapper.insert(s);
		
	}

	public void updateStore(Store s) throws IllegalArgumentException {
		storeMapper.updateStore(s);
	}

	public void deleteStore(Store s) throws IllegalArgumentException {
		storeMapper.deleteStore(s);
	}
	
	public Store getStore(int id) throws IllegalArgumentException {
		return null;
	}
	
	public ArrayList<Store> getAllStores() throws IllegalArgumentException{
		// TODO Auto-generated method stub
		return this.storeMapper.findAllStore();
	}

	@Override
	public Responsibility createResponsibility(int buyerID, int storeID, int slID) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
		Responsibility r = new Responsibility();
		r.setBuyerID(buyerID);
		r.setStoreID(storeID);
		r.setSlID(slID);
		
		return this.responsibilityMapper.insert(r);
	}


	@Override
	public void updateResponsibility(Responsibility r) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		responsibilityMapper.updateResponsibility(r);
	} 


	@Override
	public void deleteResponsibility(Responsibility rs) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		responsibilityMapper.delete(rs);
	}




	@Override
	public void deleteGroupMembership(Person p, Group g) {
		// TODO Auto-generated method stub
		groupMapper.deleteMembership(g);
	}



}	
