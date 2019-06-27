package de.hdm.softwarepraktikum.server.report;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.softwarepraktikum.server.ShoppingListAdministrationImpl;
import de.hdm.softwarepraktikum.server.db.ListItemMapper;
import de.hdm.softwarepraktikum.shared.ShoppingListAdministration;
import de.hdm.softwarepraktikum.shared.ReportGenerator;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.ListItem;
import de.hdm.softwarepraktikum.shared.bo.Responsibility;
import de.hdm.softwarepraktikum.shared.bo.ShoppingList;
import de.hdm.softwarepraktikum.shared.bo.Store;
import de.hdm.softwarepraktikum.shared.report.ItemsByGroupReport;
import de.hdm.softwarepraktikum.shared.report.ItemsByPersonReport;
import de.hdm.softwarepraktikum.shared.report.Report;
import de.hdm.softwarepraktikum.shared.report.Column;
import de.hdm.softwarepraktikum.shared.report.CompositeParagraph;
import de.hdm.softwarepraktikum.shared.report.SubColumn;
import java_cup.internal_error;
import de.hdm.softwarepraktikum.shared.report.Row;
import de.hdm.softwarepraktikum.shared.report.SimpleParagraph;

/**
 * Die Klasse <code>ReportGeneratorImpl</code> implementiert das Interface
 * ReportGenerator. In der Klasse ist neben ShoppingListAdministrationImpl saemtliche
 * Applikationslogik vorhanden.
 * 
 * @author Niklas �xle
 * @version 1.0
 */

@SuppressWarnings("serial")
public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGenerator{
	
	/**
	 * Der ReportGenerator benoetigt  Zugriff auf die ShoppinglistAdministration,
	 * da dort wichtige Methoden fuerr die Koexistenz von Datenobjekten enthalten sind.
	 */
    private ShoppingListAdministration administration = null;

    /**
     * Referenz auf den listItemMapper, welcher ListItem Objekte mit der Datenbank
     * abgleicht.
     */
    private ListItemMapper listItemMapper = null;
    
    
    /**
     * <p>
     * Ein <code>RemoteServiceServlet</code> wird unter GWT mittels
     * <code>GWT.create(Klassenname.class)</code> Client-seitig erzeugt. Hierzu
     * ist ein solcher No-Argument-Konstruktor anzulegen. Ein Aufruf eines anderen
     * Konstruktors ist durch die Client-seitige Instantiierung durch
     * <code>GWT.create(Klassenname.class)</code> nach derzeitigem Stand nicht
     * m�glich.
     * </p>
     * <p>
     * Es bietet sich also an, eine separate Instanzenmethode zu erstellen, die
     * Client-seitig direkt nach <code>GWT.create(Klassenname.class)</code>
     * aufgerufen wird, um eine Initialisierung der Instanz vorzunehmen.
     * </p>
     */
    
    
	public ReportGeneratorImpl() throws IllegalArgumentException {	
	}

	
	 /**
     * Initialsierungsmethode. Siehe dazu Anmerkungen zum No-Argument-Konstruktor.
     * 
     * @see #ReportGeneratorImpl()
     */
	
	
	@Override
	public void init() throws IllegalArgumentException {
		
		/*
		 * Ein ReportGeneratorImpl-Objekt instantiiert eine
		 * ShoppingListAdministrationImpl-Instanz.
		 */
		ShoppingListAdministrationImpl s = new ShoppingListAdministrationImpl();
		s.init();
		this.administration = s;
		
		this.listItemMapper = ListItemMapper.listitemMapper();
	}
	
	/**
     * Auslesen der zugeh�rigen ShoppingListAdministration.
     * 
     * @return das ShoppingListAdministration Objekt
     */
	
	protected ShoppingListAdministration getShoppingListAdministration() {
		// TODO Auto-generated method stub
		return this.administration;
	}
	

	/**
	 * Gibt alle Items zurueck
	 * 
	 * 
	 * @return ArrayList<Item> mit allen Items
	 */  

	@Override
	public ArrayList<Item> getAllItems() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return administration.getAllItems();
	}
	
	/**
	 * Gibt alle Stores zurueck.
	 * 
	 * 
	 * @return ArrayList<Store> mit allen Stores
	 */  

	@Override
	public ArrayList<Store> getAllStores() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return administration.getAllStores();
	}
	
	
	/**
	 * Gibt alle Gruppen- Objekte der uebergebenen Person zurueck.
	 * 
	 * @param p, das �bergebene Personobjekt f�r das die ArrayList erstellt werden soll.
	 * @return ArrayList<Group> mit allen Groups
	 */ 

	@Override
	public ArrayList<Group> getAllGroups(Person p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return administration.getAllGroupsByPerson(p);
	}
	
	/**
	 * Gibt alle Persons zurueck.
	 *  
	 * @return ArrayList<Person> mit allen Person.
	 */  
	
	@Override
	public ArrayList<Person> getAllPersons() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return administration.getAllPersons();
	}

    
	@Override
	public ArrayList<ShoppingList> getAllParticipations(Person p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Methode um ein Impressum einem Report hinzuzufuegen
	 *  
	 * @param r, Report dem das Impressum hinzugefuegt werden soll..
	 */  
	
	public void AddImprint(Report r) throws IllegalArgumentException {
		/*
		 * Mehrzeiliges Impressum
		 */
		CompositeParagraph imprint = new CompositeParagraph();
		
		imprint.addSubParagraph(new SimpleParagraph("Hochschule der Medien"));
		imprint.addSubParagraph(new SimpleParagraph("Nobelstraï¿½e 10 70569 Stuttgart"));
		imprint.addSubParagraph(new SimpleParagraph("Telefon: 0711 8923-3242"));
		imprint.addSubParagraph(new SimpleParagraph("E-Mail: info-wi7@hdm-stuttgart.de"));
		imprint.addSubParagraph(new SimpleParagraph("Website: https://www.hdm-stuttgart.de/"));
		
		//Hinzufï¿½gen des Impressums zum Report.
		r.setImprint(imprint);
		
	}

	@Override
	public void AddImprint() {
		// TODO Auto-generated method stub
		
	}
	
	/**
     * Erstellen von <code>ItemsByGroupReport</code>-Objekten.
     * 
     * @param filterPerson, p, g, s, from, to : Parameter nach denen gefiltert werden soll.
     * @return der fertige Report
     */

	@Override
	public ItemsByGroupReport getReportOfGroupBetweenDates(Boolean filterPerson, Person p, Group g, Store s, Timestamp from, Timestamp to) throws IllegalArgumentException {
	  	if(this.getShoppingListAdministration() == null) {
    		return null;
    		}
    	
    	//Anlegen eines leeren Reports
    	ItemsByGroupReport result = new ItemsByGroupReport();
    	
  
    	// Jeder Report erhaelt einen Titel (Ueberschrift)
    	result.setTitle("Alle gekauften Items der Gruppe im angegebenen Zeitraum");
    	
    	/*
         * Datum der Erstellung hinzuf�gen. new Timestamp() erzeugt autom. einen
         * "Timestamp" des Zeitpunkts der Instantiierung des Objekts.
         */
    	result.setCreationDate(new Timestamp(System.currentTimeMillis()));
    	
    	ArrayList<ListItem> items = this.listItemMapper.getCheckedListItemsOfGroupBetweenDates(g.getId(), from, to);
    	
 
    			//Wenn ein Store angegeben wird, wird nach diesem gefiltert.
    			if(s != null) {
    				Iterator<ListItem> iterator;
    				for (iterator = items.iterator(); iterator.hasNext(); ) {
    				    ListItem value = iterator.next();
    				    if (value.getStoreID() != s.getId()) {
    				        iterator.remove();
    				    }
    				}
    			}
 
    			//Wenn eine Person angegeben wird, wird gefiltert
    			if(filterPerson == true) {
    				Iterator<ListItem> iterator;
    				for (iterator = items.iterator(); iterator.hasNext(); ) {
    				    ListItem value = iterator.next();
    				    if (value.getBuyerID() != p.getId()) {
    				        iterator.remove();
    				    }
    				}
    			}
    			
    			ArrayList<Integer> groupIds = new ArrayList<Integer>();
    			for (ListItem li : items) {
    				if(groupIds.contains(li.getStoreID()) != true) {
    					groupIds.add(li.getStoreID());
    					System.out.println(String.valueOf(li.getStoreID()));  
    				}
    				
    			}
    			//Sicherheitsabfrage
    	    	if(items !=null) {
    	  
    	        // Zusammenstellung der Kopfdaten (Headline) des Reports 
    	    	Row headline = new Row();
    	    	
    	    	headline.addColumn(new Column("Laden"));
    	    	headline.addColumn(new Column());
    		 		
    	   	 	result.addRow(headline);
    	   	 	
    			  	
    	    	for(Integer i : groupIds) {
    	    			
    	    			
    					
    					//Eine leere Zeile anlegen.
    					Row listItemRow = new Row();
    						
    					listItemRow.addColumn(new Column(getStoreName(i)));	
    					        
    					SubColumn subC = new SubColumn();
    					ArrayList<Row> subRow = this.getSubTable(i, p, items);
    					for (Row r : subRow) {
    						subC.addRow(r);
    					}
    					listItemRow.addColumn(subC);	
    					
    					result.addRow(listItemRow);
    	    	
    	    		}
    	    	//R�ckgabe des fertigen Reports
    	    	return result;
    	    	}
    	    	return null;
	}

	
	/**
     * Erstellen von <code>ItemsByPersonReport</code>-Objekten, ohne Datumseingrenzung.
     * 
     * @param  p, g, s, : Parameter nach denen gefiltert werden soll.
     * @return der fertige Report
     */
	
	@Override
	public ItemsByPersonReport getReportOfPerson(Person p, Store s, Group g) throws IllegalArgumentException {
		if(this.getShoppingListAdministration() == null) {
    		return null;
    		}
		
		ItemsByPersonReport result = new ItemsByPersonReport();
		
		result.setTitle("Alle eingekauften Items des Users.");
		
		ArrayList<ListItem> items = this.listItemMapper.getCheckedListItemsOfPerson(p.getId());
		//Wenn ein Store angegeben wird, wird nach diesem gefiltert.
		if(s != null) {
			Iterator<ListItem> iterator;
			for (iterator = items.iterator(); iterator.hasNext(); ) {
			    ListItem value = iterator.next();
			    if (value.getStoreID() != s.getId()) {
			        iterator.remove();
			    }
			}
		}
		
		//Wenn eine Gruppe angegeben wird, wird nach dieser gefiltert.
		if(g != null) {
			Iterator<ListItem> iterator;
			for (iterator = items.iterator(); iterator.hasNext(); ) {
			    ListItem value = iterator.next();
			    if (value.getGrID() != g.getId()) {
			        iterator.remove();
			    }
			}
		}
		
		ArrayList<Integer> groupIds = new ArrayList<Integer>();
		for (ListItem li : items) {
			if(groupIds.contains(li.getStoreID()) != true) {
				groupIds.add(li.getStoreID());
				System.out.println(String.valueOf(li.getStoreID()));  
			}
			
		}
		//Sicherheitsabfrage
    	if(items !=null) {
  
        // Zusammenstellung der Kopfdaten (Headline) des Reports 
    	Row headline = new Row();
    	
    	headline.addColumn(new Column("Laden"));
    	headline.addColumn(new Column());
	 		
   	 	result.addRow(headline);
   	 	
		
    	for(Integer i : groupIds) {
    			
    			
				
				//Eine leere Zeile anlegen.
				Row listItemRow = new Row();
					
				listItemRow.addColumn(new Column(getStoreName(i)));	
				        
				SubColumn subC = new SubColumn();
				ArrayList<Row> subRow = this.getSubTable(i, p, items);
				for (Row r : subRow) {
					subC.addRow(r);
				}
				listItemRow.addColumn(subC);	
				
				result.addRow(listItemRow);
    	
    		}
    	//Rueckgabe des fertigen Reports
    	return result;
    	}
    	return null;
	}
	
	
	/**
     * Erstellen von <code>ItemsByPersonReport</code>-Objekten, mit Datumseingrenzung.
     * 
     * @param  p, g, s, from, to : Parameter nach denen gefiltert werden soll.
     * @return der fertige Report
     */
	
	@Override
	public ItemsByPersonReport getReportOfPersonBetweenDates(Person p, Store s, Group g, Timestamp from, Timestamp to) throws IllegalArgumentException {
		if(this.getShoppingListAdministration() == null) {
    		return null;
    		}
		
		ItemsByPersonReport result = new ItemsByPersonReport();
		
		result.setTitle("Alle eingekauften Items des Users in einem bestimmten Zeitraum.");
		
		ArrayList<ListItem> items = this.listItemMapper.getCheckedListItemsOfPersonBetweenDates(p.getId(), from, to);

		//Wenn ein Store angegeben wird, wird nach diesem gefiltert.
		if(s != null) {
			Iterator<ListItem> iterator;
			for (iterator = items.iterator(); iterator.hasNext(); ) {
			    ListItem value = iterator.next();
			    if (value.getStoreID() != s.getId()) {
			        iterator.remove();
			    }
			}
		}
		
		//Wenn eine Gruppe angegeben wird, wird nach dieser gefiltert.
		if(g != null) {
			Iterator<ListItem> iterator;
			for (iterator = items.iterator(); iterator.hasNext(); ) {
			    ListItem value = iterator.next();
			    if (value.getGrID() != g.getId()) {
			        iterator.remove();
			    }
			}
		}
				
		ArrayList<Integer> groupIds = new ArrayList<Integer>();
		for (ListItem li : items) {
			if(groupIds.contains(li.getStoreID()) != true) {
				groupIds.add(li.getStoreID());
				System.out.println(String.valueOf(li.getStoreID()));  
			}
			
		}
		//Sicherheitsabfrage
    	if(items !=null) {
  
        // Zusammenstellung der Kopfdaten (Headline) des Reports 
    	Row headline = new Row();
    	
    	headline.addColumn(new Column("Laden"));
    	headline.addColumn(new Column());
	 		
   	 	result.addRow(headline);
   	 	
		  	
    	for(Integer i : groupIds) {
    			
    			
				
				//Eine leere Zeile anlegen.
				Row listItemRow = new Row();
					
				listItemRow.addColumn(new Column(getStoreName(i)));	
				        
				SubColumn subC = new SubColumn();
				ArrayList<Row> subRow = this.getSubTable(i, p, items);
				for (Row r : subRow) {
					subC.addRow(r);
				}
				listItemRow.addColumn(subC);	
				
				result.addRow(listItemRow);
    	
    		}
    	//R�ckgabe des fertigen Reports
    	return result;
    	}
    	return null;
	}
	
	
	/**
     * Erstellen von <code>ItemsByGroupReport</code>-Objekten, ohne Datumseingrenzung.
     * 
     * @param filterPerson, p, g, s: Parameter nach denen gefiltert werden soll.
     * @return der fertige Report
     */

	@Override
	public ItemsByGroupReport getReportOfGroup(Boolean filterPerson, Person p, Group g, Store s) throws IllegalArgumentException {
		if(this.getShoppingListAdministration() == null) {
    		return null;
    		}
    	
    	//Anlegen eines leeren Reports
    	ItemsByGroupReport result = new ItemsByGroupReport();
    	
  
    	// Jeder Report erh�lt einen Titel (�berschrift)
    	result.setTitle("Alle Items einer Gruppe");
    	
    	/*
         * Datum der Erstellung hinzuf�gen. new Timestamp() erzeugt autom. einen
         * "Timestamp" des Zeitpunkts der Instantiierung des Objekts.
         */
    	result.setCreationDate(new Timestamp(System.currentTimeMillis()));
    	
    	ArrayList<ListItem> items = this.listItemMapper.getCheckedListItemsOfGroup(g.getId());
    	
    			
		//Wenn ein Store angegeben wird, wird nach diesem gefiltert.
		if(s != null) {
			Iterator<ListItem> iterator;
			for (iterator = items.iterator(); iterator.hasNext(); ) {
			    ListItem value = iterator.next();
			    if (value.getStoreID() != s.getId()) {
			        iterator.remove();
			    }
			}
		}

		//Wenn eine Person angegeben wird, wird gefiltert
		if(filterPerson == true) {
			Iterator<ListItem> iterator;
			for (iterator = items.iterator(); iterator.hasNext(); ) {
			    ListItem value = iterator.next();
			    if (value.getBuyerID() != p.getId()) {
			        iterator.remove();
			    }
			}
		}
		ArrayList<Integer> groupIds = new ArrayList<Integer>();
		for (ListItem li : items) {
			if(groupIds.contains(li.getStoreID()) != true) {
				groupIds.add(li.getStoreID());
				System.out.println(String.valueOf(li.getStoreID()));  
			}
			
		}
		//Sicherheitsabfrage
    	if(items !=null) {
  
        // Zusammenstellung der Kopfdaten (Headline) des Reports 
    	Row headline = new Row();
    	
    	headline.addColumn(new Column("Laden"));
    	headline.addColumn(new Column());
	 		
   	 	result.addRow(headline);
   	 	
		  	
    	for(Integer i : groupIds) {
    			
    			
				
				//Eine leere Zeile anlegen.
				Row listItemRow = new Row();
					
				listItemRow.addColumn(new Column(getStoreName(i)));	
				        
				SubColumn subC = new SubColumn();
				ArrayList<Row> subRow = this.getSubTable(i, p, items);
				for (Row r : subRow) {
					subC.addRow(r);
				}
				listItemRow.addColumn(subC);	
				
				result.addRow(listItemRow);
    	
    		}
    	//R�ckgabe des fertigen Reports
    	return result;
    	}
    	
    return null;
	}
	
	
	/**
	 * Erstellen einer Subtable bestehend aus ListItems einer Person.
	 * Diese werden in Rows und Columns geschrieben.
	 * 
	 * @param storeID , Store welcher dem ListItem zugeordnet ist.
	 * @param p , Person welche dem ListItem zugeordnet ist.
	 * @param items , ArrayList mit ListItems
	 * @return ArrayList mit Rows, die wiederum Columns besitzen und so eine Table formen
	 */
	public ArrayList<Row> getSubTable(int storeId, Person p, ArrayList<ListItem> items) {
    	ArrayList<Row> subTableRows = new ArrayList<Row>();
	
	if (items.isEmpty() == false) {
		Row headline = new Row();
		headline.addColumn(new Column("Gruppe"));
    	headline.addColumn(new Column("Artikelname"));
    	headline.addColumn(new Column("Menge"));
    	headline.addColumn(new Column("Einheit"));
    	headline.addColumn(new Column("Verantwortlicher"));
    	headline.addColumn(new Column("Gekauft am"));
    	subTableRows.add(headline);
			for (ListItem i : items) {
				if (i.getStoreID() == storeId) {
						Row listItemRow = new Row();
						//zweite Spalte: Gruppe
						listItemRow.addColumn(new Column(getGroupName(i.getGrID(), p)));

						//dritte Spalte: Artikelname
						listItemRow.addColumn(new Column(getItemName(i.getItemId())));
						
						//vierte Spalte: Einheit hinzuf�gen
						listItemRow.addColumn(new Column(i.getUnit()));
						
						//fuenfte Spalte: Menge hinzuf�gen
						listItemRow.addColumn(new Column(String.valueOf(i.getAmount())));
						
						//sechste Spalte: Person hinzuguegen, welche den Artikel gekauft hat
						listItemRow.addColumn(new Column(getPersonName(i.getBuyerID())));
						
						//sechste Spalte: Kaufdatum hinzuf�gen
						listItemRow.addColumn(new Column(i.getChangeDateString()));
						
					
						subTableRows.add(listItemRow);
			}
	
	  	}
	}
	return subTableRows;
	
}
	
	
	/**
	 * Methode um den Name eines Items auszugeben
	 *  
	 * @param itemID, ID des auszugebenden Items
	 * @return Name des Items.
	 */  
	
	private String getItemName(int itemID) {
		ArrayList<Item> allItems = getAllItems();
		for(Item item : allItems) {
			if (item.getId() == itemID) {
				return item.getName();
			}
		}
		return String.valueOf(itemID);
	}
	
	/**
	 * Methode um den Name einer Gruppe auszugeben, die einer bestimmten Person zugeordnet ist.
	 *  
	 * @param groupID, ID der asuzugebenden Person
	 * @param p , Person die Teil der Gruppe ist,
	 * @return Name des Items.
	 */  
	
	private String getGroupName(int groupID, Person p) {
		ArrayList<Group> allGroups = getAllGroups(p);
		for(Group group : allGroups) {
			if (group.getId() == groupID) {
				return group.getTitle();
			}
		}
		return String.valueOf(groupID);
	}
	
	
	/**
	 * Methode um den Namen eines Stores auszugeben.
	 *  
	 * @param storeID, ID des auszugebenden Stores
	 * @return Name des Stores.
	 */  
	
	private String getStoreName(int storeID) {
		ArrayList<Store> allStores = getAllStores();
		for(Store store : allStores) {
			if (store.getId() == storeID) {
				return store.getName();
			}
		}
		return String.valueOf(storeID);
	}
	
	
	/**
	 * Methode um den Namen einer Person auszugeben.
	 *  
	 * @param personID, ID der auszugebenden Person
	 * @return Name der Person.
	 */  
	
	private String getPersonName(int personID) {
		ArrayList<Person> allPersons = getAllPersons();
		for(Person person : allPersons) {
			if (person.getId() == personID) {
				return person.getName();
			}
		}
		return String.valueOf(personID);
	}

	
	
}
