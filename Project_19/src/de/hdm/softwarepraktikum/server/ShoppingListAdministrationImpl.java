package de.hdm.softwarepraktikum.server;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.http.impl.NoConnectionReuseStrategy;

import de.hdm.softwarepraktikum.server.db.ListItemMapper;
import de.hdm.softwarepraktikum.server.db.PersonMapper;
import de.hdm.softwarepraktikum.server.db.ResponsibilityMapper;
import de.hdm.softwarepraktikum.server.db.ShoppingListMapper;
import de.hdm.softwarepraktikum.server.db.StoreMapper;
import de.hdm.softwarepraktikum.client.gui.Notification;
import de.hdm.softwarepraktikum.server.db.FavoriteItemMapper;
import de.hdm.softwarepraktikum.server.db.GroupMapper;
import de.hdm.softwarepraktikum.server.db.ItemMapper;
import de.hdm.softwarepraktikum.shared.ShoppingListAdministration;
import de.hdm.softwarepraktikum.shared.bo.BusinessObject;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.ListItem;
import java_cup.internal_error;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.bo.Responsibility;
import de.hdm.softwarepraktikum.shared.bo.ShoppingList;
import de.hdm.softwarepraktikum.shared.bo.Store;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Diese Klasse implementiert das Interface ShoppingListAdministration. Hier
 * befindet sich, bis auf ReportGeneratorImpl, die komplette Applikationslogik.
 * 
 * @author Jakob Schaal & Luca Randecker
 * @version 1.0
 */

public class ShoppingListAdministrationImpl extends RemoteServiceServlet implements ShoppingListAdministration {

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
	 * Referenz auf den shoppingListMapper, welcher ShoppingList Objekte mit der
	 * Datenbank abgleicht.
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
	 * Referenz auf den responsibilityMapper, welcher Responsibility Objekte mit der
	 * Datenbank abgleicht.
	 */
	private ResponsibilityMapper responsibilityMapper = null;

	/*
	 * Referenz auf den favoriteItemMapper
	 */

	private FavoriteItemMapper favoriteItemMapper = null;

	/*
	 * ***************************************************************************
	 * ABSCHNITT, Beginn: Initialisierung
	 * ***************************************************************************
	 */

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

	// Initialisierung aller Mapper in der Klasse
	public void init() throws IllegalArgumentException {
		this.listItemMapper = ListItemMapper.listitemMapper();
		this.itemMapper = ItemMapper.itemMapper();
		this.personMapper = PersonMapper.personMapper();
		this.shoppingListMapper = ShoppingListMapper.shoppinglistMapper();
		this.storeMapper = StoreMapper.storeMapper();
		this.groupMapper = GroupMapper.groupMapper();
		this.responsibilityMapper = ResponsibilityMapper.responsibilityMapper();
		this.favoriteItemMapper = FavoriteItemMapper.favoriteItemMapper();

	}

	/*
	 * ***************************************************************************
	 * ABSCHNITT, Ende: Initialisierung
	 * ***************************************************************************
	 */

	/*
	 * ***************************************************************************
	 * ABSCHNITT, Beginn: Methoden für Person-Objekte
	 * ***************************************************************************
	 */

	/**
	 * Methode um ein <code>Person</code> Objekt anzulegen.
	 * 
	 * @param gmail, name
	 * @return insert
	 */

	public Person createPerson(String gmail, String name) throws IllegalArgumentException {
		Person p = new Person();
		p.setGmail(gmail);
		p.setName(name);

		/**
		 * Setzen einer vorläufigen ID. Der insert Aufruf liefert anschließend ein
		 * Objekt dessen Nummer mit der Datenbank konsistent ist.
		 */
		p.setId(1);

		return this.personMapper.insert(p);

	}

	/**
	 * Methode um Änderungen dem zu übergebenden Person Objekt in die Datenbank zu
	 * schreiben
	 * 
	 * @param p
	 */

	public void updatePerson(Person p) throws IllegalArgumentException {
		personMapper.update(p);

	}

	/**
	 * Methode um ein <code>Person</code> Objekt aus der Datenbank zu löschen
	 * Referenzen zu Person-Objekt werden in der Reihenfolge Responsibility,
	 * Listitems, Participation gelöscht, anschließend das Person-Objekt
	 * 
	 * @param p
	 * @throws IllegalArgumentException
	 */

	public void deletePerson(Person p) throws IllegalArgumentException {

		listItemMapper.deleteListItemByPersonID(p);
		responsibilityMapper.deleteByPersonID(p);
		personMapper.deleteparticipationByPersonID(p);
		personMapper.delete(p);
	}

	/**
	 * Gibt die <code>Person</code> zurück, welcher die übergebene Id besitzt.
	 * 
	 * @param id
	 * @return findById
	 * @throws IllegalArgumentException
	 */

	public Person getPerson(int id) throws IllegalArgumentException {
		return this.personMapper.findById(id);
	}

	/**
	 * Methode um die Gruppen einer Person anzuzeigen
	 * 
	 * @param p
	 * @return groups<Group>
	 * @throws IllegalArgumentException
	 */

	public ArrayList<Group> getGroupsOf(Person p) throws IllegalArgumentException {
		ArrayList<Group> groups = personMapper.getGroupsOf(p);

		return groups;
	}

	/**
	 *Methode um eine ArrayList aller Personen zu erhalten
	 * @return findAll
	 * @throws IllegalArgumentException
	 */

	public ArrayList<Person> getAllPersons() throws IllegalArgumentException {
		return this.personMapper.findAll();
	}

	/*
	 * ***************************************************************************
	 * ABSCHNITT, Ende: Methoden für Person-Objekte
	 * ***************************************************************************
	 */

	/*
	 * ***************************************************************************
	 * ABSCHNITT, Beginn: Methoden für Item-Objekte
	 * ***************************************************************************
	 */

	/**
	 * Methode, um ein <code>Item</code> in der Datenbank zu anzulegen.
	 * 
	 * @param name, value, ownerID
	 * @return insert
	 * @throws IllegalArgumentException
	 */

	public Item createItem(String name, boolean value, int ownerID) throws IllegalArgumentException {

		Item i = new Item();

		i.setName(name);
		i.setIsGlobal(value);
		i.setOwnerID(ownerID);

		/**
		 * Objekt in Datenbank speichern.
		 */

		return this.itemMapper.insert(i);

	}

	/**
	 * Methode um Änderungen dem zu übergebenden Item Obejekt in die Datenbank zu
	 * schreiben
	 * 
	 * @param i
	 */
	public void updateItem(Item i) throws IllegalArgumentException {
		itemMapper.update(i);
	}

	/**
	 * Methode um ein <code>Item</code> Objekt aus der Datenbank zu löschen
	 * 
	 * @param i
	 * @throws IllegalArgumentException
	 */
	public void deleteItem(Item i, Group g) throws IllegalArgumentException {
		listItemMapper.deleteListItemByItemID(i);
		favoriteItemMapper.delete(i, g);
		itemMapper.delete(i);
	}

	/**
	 * Gibt die <code>Person</code> zurück, welcher die übergebene Id besitzt.
	 * 
	 * @param id
	 * @return Das Item Objekt wird zurückgegeben
	 * @throws IllegalArgumentException
	 */
	public Item getItem(int id) throws IllegalArgumentException {

		Item i = itemMapper.findById(id);

		if (i != null) {
			return i;
		}
		return null;
	}

	/**
	 * Methode um alle in der Datenbank gespeicherten <code>Item</code> Objekte
	 * einer Gruppe als ArrayList auszugeben
	 * 
	 * @param groupId, currentPersonId
	 * @return ArrayList<Item>
	 */
	public ArrayList<Item> getAllItemsByGroup(int groupId, int currentPersonId) throws IllegalArgumentException {

		Group group = new Group();
		group.setId(groupId);
		ArrayList<Item> allItems = this.itemMapper.findAllByGroup(currentPersonId);

		for (Item item : allItems) {
			item.setFavorite(this.favoriteItemMapper.checkFav(item, group));
		}

		return allItems;

	}

	/**
	 * Methode, um alle in der Datenbank gepeicherten <code>Item</code> -Objekte als
	 * ArrayList auszugeben
	 * 
	 * @return ArrayList<Item>
	 */
	public ArrayList<Item> getAllItems() throws IllegalArgumentException {

		ArrayList<Item> allItems = this.itemMapper.findAll();

		return allItems;

	}

	/**
	 * Methode, um zu prüfen, ob <code>ListItem</code>-Objekte zu einem
	 * <code>Item</code>-Objekt existieren.
	 * 
	 * @param id
	 * @return checkForExistingListitems
	 */
	public Boolean checkForExistingListItems(Integer id) throws IllegalArgumentException {
		Item i = new Item();
		i.setId(id);

		return itemMapper.checkForExistingListitems(i);
	}

	/**
	 * Methode, um zu prüfen, ob ein <code>Item</code>-Objekt bereits existiert.
	 * 
	 * @param name
	 * @return checkForExistingListitems
	 */
	public Boolean checkForExistingItemByName(String name) {
		return this.itemMapper.checkForExistingItemByName(name);
	}

	/*
	 * ***************************************************************************
	 * ABSCHNITT, Ende: Methoden für Item-Objekte
	 * ***************************************************************************
	 */

	/*
	 * ***************************************************************************
	 * ABSCHNITT, Beginn: Methoden für ListItem-Objekte
	 * ***************************************************************************
	 */

	/**
	 * Methode, um ein <code>ListItem</code> Objekt in der Datenbank anzulegen
	 * 
	 * @param zugehöriges Item, BuyerID, storeID, slID
	 * @return Das in die Datenbank gespeicherte ListItem Objekt wird zurückgegeben
	 */

	public ListItem createListItem(Item item, int buyerID, int storeID, int slID, int grID, double amount, String unit,
			Boolean isChecked) throws IllegalArgumentException {

		Responsibility res = new Responsibility();
		res.setBuyerID(buyerID);
		res.setStoreID(storeID);
		res.setSlID(slID);

		this.responsibilityMapper.insert(res);

		ListItem li = new ListItem();

		li.setItemId(item.getId());
		li.setBuyerID(buyerID);
		li.setStoreID(storeID);
		li.setSlID(slID);
		li.setGrID(grID);
		li.setAmount(amount);
		li.setUnit(unit);
		li.setName(item.getName());
		li.setChecked(isChecked);

		/**
		 * Setzen einer vorläufigen ID. Der Insert Aufruf liefert dann ein Objekt,
		 * dessen Nummer mit der Datenbank konsistent ist.
		 */

		return this.listItemMapper.insert(li, res);

	}

	/**
	 * Methode um Änderungen dem zu übergebenden <code>ListItem</code> Objekt in die
	 * Datenbank zu schreiben
	 * 
	 * @param li
	 * @return update
	 */
	public ListItem updateListItem(ListItem li) throws IllegalArgumentException {
		Responsibility r = new Responsibility();
		r.setBuyerID(li.getBuyerID());
		r.setStoreID(li.getStoreID());
		r.setSlID(li.getSlID());
		responsibilityMapper.updateResponsibility(r);

		return listItemMapper.update(li);
	}

	/**
	 * Methode um ein <code>ListItem</code> Objekt aus der Datenbank zu löschen
	 * 
	 * @param ListItem li
	 * @throws IllegalArgumentException
	 */
	public void deleteListItem(ListItem li) throws IllegalArgumentException {
		listItemMapper.delete(li);
		int a = li.getResID();
		responsibilityMapper.deletebyID(a);
	}

	/**
	 * Methode um ListItems als eingekauft zu merkieren
	 * 
	 * @param ListItem li
	 */
	@Override
	public void checkListItem(Integer id, Boolean isChecked) {
		// TODO Auto-generated method stub
		ListItem li = new ListItem();
		li.setId(id);
		li.setChecked(isChecked);
		this.listItemMapper.checkListItem(li);
	}

	/**
	 * Gibt das <code>ListItem</code> zurück, welcher die übergebene Id besitzt.
	 * 
	 * @param id
	 * @return findById
	 * @throws IllegalArgumentException
	 */

	public ListItem getListItem(int id) throws IllegalArgumentException {
		return this.listItemMapper.findById(id);
	}

	/**
	 * Methode um alle Artikel eines <code>ShoppingList</code> Objekts auszugeben.
	 * 
	 * @param sl
	 * @return findAllListItemsby
	 */
	@Override
	public ArrayList<ListItem> getAllListItemsByShoppingLists(ShoppingList sl) {
		return this.listItemMapper.findAllListItemsby(sl);
	}

	/**
	 * Methode, um alle <code>ListItem</code> Objekte einer
	 * <code>ShoppingList</code> auszugeben.
	 * 
	 * @param sl
	 * @return ArrayList<ListItem>
	 * @throws IllegalArgumentException
	 */
	public ArrayList<ListItem> getAllCheckedItemsBySL(ShoppingList sl) throws IllegalArgumentException {

		ArrayList<ListItem> allCheckedItems = listItemMapper.findAllCheckedListItems(sl);

		return allCheckedItems;
	}

	/*
	 * ***************************************************************************
	 * ABSCHNITT, Ende: Methoden für ListItem-Objekte
	 * ***************************************************************************
	 */

	/*
	 * ***************************************************************************
	 * ABSCHNITT, Beginn: Methoden für Group-Objekte
	 * ***************************************************************************
	 */

	/**
	 * Methode, um ein <code>Group</code> Objekt anzulegen.
	 * 
	 * @param title, member
	 * @return g
	 */
	public Group createGroup(String title, ArrayList<Person> member) throws IllegalArgumentException {
		Group g = new Group();
		g.setMember(member);
		g.setTitle(title);
		groupMapper.insert(g);

		for (Person m : member) {

			groupMapper.addMembership(m, g);
		}

		return g;
	}

	/**
	 * Methode, um ein <code>Group</code> Objekt zu modifizieren.
	 */
	public void updateGroup(Group g) throws IllegalArgumentException {
		groupMapper.update(g);
	}

	/**
	 * Methode, um alle Gruppen auszulesen, in welchen eine Person Mitglied ist.
	 * 
	 * @param p
	 * @return findByMember
	 */
	public ArrayList<Group> getAllGroupsByPerson(Person p) throws IllegalArgumentException {
		Integer id = p.getId();
		return this.groupMapper.findByMember(id);
	}

	/**
	 * Methode, um alle Mitglieder einer Gruppe auszulesen.
	 * 
	 * @param id
	 * @return findAllGroupMembers
	 */
	@Override
	public ArrayList<Person> getAllGroupMembers(int id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		Group g = new Group();
		g.setId(id);
		ArrayList<Person> members = this.personMapper.findAllGroupMembers(g);
		for (Person p : members) {
			Person person = personMapper.findById(p.getId());
			p.setName(person.getName());
		}
		return members;
	}

	/*
	 * ***************************************************************************
	 * ABSCHNITT, Ende: Methoden für Group-Objekte
	 * ***************************************************************************
	 */

	/*
	 * ***************************************************************************
	 * ABSCHNITT, Beginn: Methoden für ShoppingList-Objekte
	 * ***************************************************************************
	 */

	/**
	 * Methode um ein <code>ShoppingList</code> Objekt anzulegen.
	 * 
	 * @param ownerid, title, groupID
	 * @return insert
	 */
	@Override
	public ShoppingList createShoppingList(int ownerid, String title, int groupID) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		ShoppingList sl = new ShoppingList();
		sl.setTitle(title);
		sl.setOwnerID(ownerid);
		sl.setGroupID(groupID);

		/**
		 * Setzen einer vorläufigen ID. Der Insert Aufruf liefert dann ein Objekt,
		 * dessen Nummer mit der Datenbank konsistent ist.
		 */

		sl.setId(1);

		return this.shoppingListMapper.insert(sl);
	}

	/**
	 * Methode um ein <code>ShoppingList</code> Objekt zu modifizieren
	 * 
	 * @param sl
	 */
	@Override
	public void updateShoppingList(ShoppingList sl) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		shoppingListMapper.update(sl);
	}

	/**
	 * Methode um ein <code>ShoppingList</code> Objekt anhand der ID zu finden.
	 * 
	 * @param id
	 * @return findById
	 */
	@Override
	public ShoppingList findShoppingListbyId(int id) {
		// TODO Auto-generated method stub
		return this.shoppingListMapper.findById(id);
	}

	/**
	 * Methode, um ein <code>ShoppingList</code> Objekt zu löschen.
	 * 
	 * @param sl
	 */
	@Override
	public void deleteShoppingList(ShoppingList sl) throws IllegalArgumentException {
		// TODO Auto-generated method stub

		ArrayList<ListItem> listitems = listItemMapper.findAllListItemsby(sl);

		for (ListItem li : listitems) {
			listItemMapper.delete(li);

		}
		int shoppinglist = sl.getId();
		responsibilityMapper.deletebySLID(shoppinglist);

		shoppingListMapper.delete(sl);

	}

	/*
	 * ***************************************************************************
	 * ABSCHNITT, Ende: Methoden für ShoppingList-Objekte
	 * ***************************************************************************
	 */

	/*
	 * ***************************************************************************
	 * ABSCHNITT, Beginn: Methoden für FavoriteItem-Objekte
	 * ***************************************************************************
	 */

	/**
	 * Methode, um einen Gruppen-Favoriten hinzuzufügen
	 * 
	 * @param i, g
	 */
	@Override
	public void addFavoriteItem(Item i, Group g) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		favoriteItemMapper.insert(i, g);
	}

	/**
	 * Methode, um einen favorisierten Artikel zu entfernen.
	 * 
	 * @param i, g
	 */
	@Override
	public void removeFavoriteItem(Item i, Group g) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		favoriteItemMapper.delete(i, g);
	}

	/**
	 * Methode, um favorisierte Artikel eines <code>Group</code> Objekts auszulesen.
	 * 
	 * @param g
	 * @return ArrayList<Item>
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Item> getFavItems(Group g) throws IllegalArgumentException {

		ArrayList<Item> favItems = favoriteItemMapper.findFavItems(g);
		return favItems;
	}

	/**
	 * Methode um zu prüfen, ob ein <code>Item</code> als Favorit gesetzt ist
	 * 
	 * @param group, i
	 * @return isFav
	 */
	@Override
	public Boolean checkFav(Group group, Item i) throws IllegalArgumentException {
		Boolean isFav = favoriteItemMapper.checkFav(i, group);
		return isFav;
	}

	/**
	 * Methode setzt Artikel automatisiert als Favoriten falls dieser mehr als 4x
	 * eingekauft wurde
	 * 
	 * @param g
	 */
	public void setFavAutomated(Group g) throws IllegalArgumentException {

		ArrayList<Integer> fav = listItemMapper.autoSetFav(g);

		for (int a = 0; a < fav.size(); a++) {

			int item_id = fav.get(a);
			Boolean available = favoriteItemMapper.checkById(item_id);

			if (available == false) {

				Item i = new Item();
				i.setId(item_id);
				favoriteItemMapper.insert(i, g);

			}

		}

	}

	/**
	 * Methode die Gruppenfavoriten eines <code>Group</code> Objekts zurückgibt.
	 * 
	 * @param g
	 * @return findFavItems
	 */
	public ArrayList<Item> getAllFavoriteListItemsbyGroup(Group g) {

		return this.favoriteItemMapper.findFavItems(g);

	}

	/*
	 * ***************************************************************************
	 * ABSCHNITT, Ende: Methoden für FavoriteItem-Objekte
	 * ***************************************************************************
	 */

	/*
	 * ***************************************************************************
	 * ABSCHNITT, Beginn: Methoden für Store-Objekte
	 * ***************************************************************************
	 */

	/**
	 * Methode, um ein <code>Store</code> Objekt anzulegen
	 * 
	 * @param name, street, postcode, city, housenumber
	 * @return insert
	 */
	@Override
	public Store createStore(String name, String street, int postcode, String city, int housenumber)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub

		Store s = new Store();

		s.setName(name);
		s.setStreet(street);
		s.setPostcode(postcode);
		s.setCity(city);
		s.setHouseNumber(housenumber);

		return this.storeMapper.insert(s);

	}

	/**
	 * Methode, um ein <code>Store</code> Objekt zu modifizieren.
	 * 
	 * @param s
	 */
	public void updateStore(Store s) throws IllegalArgumentException {
		storeMapper.updateStore(s);
	}

	/**
	 * Methode, um ein <code>Store</code> Objekt zu löschen.
	 * 
	 * @param s
	 */
	public void deleteStore(Store s) throws IllegalArgumentException {
		storeMapper.deleteStore(s);
	}

	/**
	 * Methode, um ein <code>Store</code> Objekt anhand der ID zu finden.
	 * 
	 * @param id
	 * @return findByID
	 */
	public Store getStore(int id) throws IllegalArgumentException {
		return this.storeMapper.findByID(id);
	}

	/**
	 * Methode, um alle <code>Store</code> Objekte zu finden.
	 * 
	 * @return findAllStore
	 */
	public ArrayList<Store> getAllStores() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.storeMapper.findAllStore();
	}

	/**
	 * Methode, um zu prüfen, ob <code>Store</code> Objekte vorhanden sind.
	 * 
	 * @param storeId
	 * @return checkforExisitingStores
	 */
	public Boolean checkforExisitingStores(Integer storeId) {
		Store store = new Store();
		store.setId(storeId);

		return this.storeMapper.checkforExisitingStores(store);
	}

	public Boolean checkForExistingStoreByName(String name) {
		return this.storeMapper.checkForExistingStoreByName(name);
	}
	/*
	 * ***************************************************************************
	 * ABSCHNITT, Ende: Methoden für Store-Objekte
	 * ***************************************************************************
	 */

	/*
	 * ***************************************************************************
	 * ABSCHNITT, Beginn: Methoden für Responsibility-Objekte
	 * ***************************************************************************
	 */

	/**
	 * Methode, um ein <code>Responsibility</code> Objekt anzulegen
	 * 
	 * @param buyerID, storeID, slID
	 * @return insert
	 */
	@Override
	public Responsibility createResponsibility(int buyerID, int storeID, int slID) throws IllegalArgumentException {
		// TODO Auto-generated method stub

		Responsibility r = new Responsibility();
		r.setBuyerID(buyerID);
		r.setStoreID(storeID);
		r.setSlID(slID);

		return this.responsibilityMapper.insert(r);
	}

	/**
	 * Methode, um ein <code>Responsibility</code> Objekt zu ändern.
	 * 
	 * @param r
	 */
	@Override
	public void updateResponsibility(Responsibility r) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		responsibilityMapper.updateResponsibility(r);
	}

	/**
	 * Methode, um ein <code>Responsibility</code> Objekt zu löschen.
	 */
	@Override
	public void deleteResponsibility(Responsibility rs) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		responsibilityMapper.delete(rs);
	}

	/**
	 * Methode, um ein <code>Responsibility</code> Objekt anhand eines
	 * <code>Person</code> Objekts zu finden.
	 * 
	 * @param p
	 * @return findbyPerson
	 */
	public ArrayList<Responsibility> getResponsibilityByPerson(Person p) {
		return this.responsibilityMapper.findByPerson(p);

	}

	/*
	 * ***************************************************************************
	 * ABSCHNITT, Ende: Methoden für Responsibility-Objekte
	 * ***************************************************************************
	 */

	/*
	 * ***************************************************************************
	 * ABSCHNITT, Beginn: Sonstiges
	 * ***************************************************************************
	 */

	/**
	 * Methode, um eine Mitgliedschaft zu erstellen.
	 * 
	 * @param p, g
	 */
	@Override
	public void addGroupMembership(Person p, Group g) {
		// TODO Auto-generated method stub
		groupMapper.addMembership(p, g);
	}

	/**
	 * Methode, um eine Mitgliedschaft zu löschen.
	 * 
	 * @param p, g
	 */
	@Override
	public void deleteGroupMembership(Person p, Group g) {
		// TODO Auto-generated method stub
		groupMapper.deleteMembership(p, g);
	}

	/**
	 * Methode, um alle <code>ShoppingList</code> Objekte eines <code>Group</code>
	 * Objekts zu finden.
	 * 
	 * @param g
	 */
	@Override
	public ArrayList<ShoppingList> getAllShoppingListsByGroup(Group g) {
		return this.shoppingListMapper.findByGroup(g);
	}

	/**
	 * Methode, um die Gruppe eines <code>ShoppingList</code> Objekts zu finden.
	 * 
	 * @param sl
	 */
	@Override
	public void getGroup(ShoppingList sl) {

	}

	/**
	 * Methode, um alle <code>Item</code> Objekte einer Gruppe auszulesen
	 * 
	 * @param g
	 * @return null
	 */
	@Override
	public ArrayList<ListItem> getAllCheckedItemsByGroup(Group g) {

		return null;
	}

	/**
	 * Methode, um ein <code>Group</code> Objekt zu löschen
	 * 
	 * @param g
	 */
	@Override
	public void deleteGroup(Group g) {

		// loeschen der zugehörigen Shoppinglists, Responsibilities, ListItems

		ArrayList<ShoppingList> result = groupMapper.getShoppingListsPerGroup(g);

		for (ShoppingList sl : result) {
			ArrayList<ListItem> listitems = listItemMapper.findAllListItemsby(sl);
			for (ListItem li : listitems) {
				listItemMapper.delete(li);
			}
			int shoppinglist = sl.getId();

			responsibilityMapper.deletebySLID(shoppinglist);

			shoppingListMapper.delete(sl);
		}

		// loeschen der Participants
		ArrayList<Person> persons = personMapper.findAllGroupMembers(g);

		for (Person p : persons) {
			groupMapper.deleteMembership(p, g);
		}

		// loeschen der Gruppe
		groupMapper.delete(g);
	}

	/**
	 * Methode um alle Einkaufslisten eines <code>Person</code> Objekts zu finden.
	 * 
	 * @param p
	 */
	@Override
	public ArrayList<ShoppingList> getAllShoppingListsByPerson(Person p) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * ***************************************************************************
	 * ABSCHNITT, Ende: Sonstiges
	 * ***************************************************************************
	 */

}
