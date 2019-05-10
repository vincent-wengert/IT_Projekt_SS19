package de.hdm.softwarepraktikum.server;

import java.sql.Timestamp;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.server.db.ListItemMapper;
import de.hdm.softwarepraktikum.server.db.PersonMapper;
import de.hdm.softwarepraktikum.server.db.ResponsibilityMapper;
import de.hdm.softwarepraktikum.server.db.ShoppingListMapper;
import de.hdm.softwarepraktikum.server.db.StoreMapper;
import de.hdm.softwarepraktikum.server.db.GroupMapper;
import de.hdm.softwarepraktikum.server.db.ItemMapper;
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
	
	/**
	 *  ***************
	 *  ABSCHNITT, Beginn: Methoden für Group Objekte
	 *  
	 *  ***************
	 */
	
	/**
	 * Anlegen eines Group Objektes
	 * @param name
	 * @return
	 * @throws IllegalArgumentException
	 */
	
	public Group createGroup(String name) throws IllegalArgumentException {
		
		Group g = new Group();
		g.setName(name);
		
		return g;
		
	}
	
	/**
	 * Schreibt Änderungen des Namens der Gruppe in die Datenbank.
	 * @param g
	 * @param name
	 * @throws IllegalArgumentException
	 */
	
public Group updateGroup(Group g, String name) throws IllegalArgumentException {
		
		g.setName(name);
		
		return null;
		
	}

/**
 * Auslesen der zugehörigen Gruppe zur ShoppingList
 * @param sl
 * @return
 * @throws IllegalArgumentException
 */

public Group getGroup(ShoppingList sl) throws IllegalArgumentException {
	
}

public Group getAllGroupMembers(Group g) throws IllegalArgumentException {
	
	ArrayList<Person> persons = this.get
			// getGroupMembers im Mapper hinzufügen
	
}
	
	
	
	
}
