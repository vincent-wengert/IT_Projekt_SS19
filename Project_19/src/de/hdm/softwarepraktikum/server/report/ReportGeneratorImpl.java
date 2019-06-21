package de.hdm.softwarepraktikum.server.report;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import de.hdm.softwarepraktikum.shared.report.Row;
import de.hdm.softwarepraktikum.shared.report.SimpleParagraph;

/**
 * Die Klasse <code>ReportGeneratorImpl</code> implementiert das Interface
 * ReportGenerator. In der Klasse ist neben ShoppingListAdministrationImpl sÃ¤mtliche
 * Applikationslogik vorhanden.
 * 
 * @author Luca Randecker
 * @version 1.0
 */

@SuppressWarnings("serial")
public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGenerator{
	
	/**
	 * Der ReportGenerator benÃ¶tigt Zugriff auf die ContactAdministation,
	 * da dort wichtige Methoden fÃ¼r die Koexistenz von Datenobjekten enthalten sind.
	 */
    private ShoppingListAdministration administration = null;

    /*
     * Referenz auf den listItemMapper, welcher ListItem Objekte mit der Datenbank
     * abgleicht.
     */
    private ListItemMapper listItemMapper = null;
    
    
    
	public ReportGeneratorImpl() throws IllegalArgumentException {	
	}

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
	
	protected ShoppingListAdministration getShoppingListAdministration() {
		// TODO Auto-generated method stub
		return this.administration;
	}
	

//	@Override
//	public ItemsByGroupReport createGroupStatisticsReport(Group selectedG, Store selectedS, Date selectedD) throws IllegalArgumentException {
//		
//		if(this.getShoppingListAdministration() == null) {
//			return null;
//		}
//			//Anlegen eines leeren Reports.
//			ItemsByGroupReport result = new ItemsByGroupReport();
//			//Titel des Reports
//			result.setTitle("Gruppenreport der Gruppe " + selectedG.getTitle());
//			
//			//Impressum hinzufï¿½gen
//			this.AddImprint(result);
//			/*
//			 * Erstellungsdatum des Reports durch einen Timestamp hinzufï¿½gen. 
//			 */
//			result.setCreationDate(new Timestamp(System.currentTimeMillis()));
//			
//			//Methode getAllItemsByGroup kommt noch
//			
//			
//			/*
//			 * Zusammenstellung der Kopfdaten des Reports
//			 */
//			CompositeParagraph header = new CompositeParagraph();
//			
//			//Name der Gruppe aufnehmen
//			header.addSubParagraph(new SimpleParagraph("Gruppe: " + selectedG.getTitle()));
//			
//			//Hinzufï¿½gen der Kopfdaten zum Report
//			result.setHeaderData(header);
//			
//			//Anlegen der Kopfzeile fï¿½r die Statistik-Tabelle.
//			Row headline = new Row();
//			
//			/**
//			 * Im Report werden fï¿½nf Spalten benï¿½tigt: die ID des Produkts,
//			 * die Produktbezeichnung, die Einheit, die Eingekaufte Menge und das
//			 * Einkaufsdatum.
//			 */
//			headline.addColumn(new Column("ID"));
//			headline.addColumn(new Column("Produktbezeichnung"));
//			headline.addColumn(new Column("Einheit"));
//			headline.addColumn(new Column("Eingekaufte Menge"));
//			headline.addColumn(new Column("Hï¿½ndler"));
//			
//			
//			result.addRow(headline);
//			
//			/*
//			 * Auslesen sï¿½mtlicher abgehakten <code>ListItem</code>-Objekte, die dem Report 
//			 * hinzugefï¿½gt werden. Methode "getListItem 
//			 * 
//			 * 
//			 */
//			ArrayList<ListItem> li = this.administration.getAllCheckedItemsByGroup(g);
//			for(ListItem l : li) {
//				
//				//Eine leere Zeile anlegen.
//				Row listItemRow = new Row();
//				
//				//erste Spalte: ID hinzufï¿½gen
//				listItemRow.addColumn(new Column(String.valueOf(l.getId())));
//				
//				//zweite Spalte: Bezeichnung eintragen
//				listItemRow.addColumn(new Column(String.valueOf(l.getId().getName())));
//				
//				//dritte Spalte: Einheit eintragen
//				listItemRow.addColumn(new Column(String.valueOf(l.getUnit())));
//				
//				//vierte Spalte: Anzahl, wie oft das Produkt gekauft wurde.
//				//listItemRow.addColumn(new Column(String.valueOf()));
//				
//				//fï¿½nfte Spalte: Store
//				listItemRow.addColumn(new Column(String.valueOf(l.getStore())));
//				
//				result.addRow(listItemRow);
//				
//				
//			}
//			
//			return result;
//		
//		
//	}

//	@Override
//	public ShoppingListAdministration getShoppingListAdministration(int id) throws IllegalArgumentException {
//		// TODO Auto-generated method stub
//		return null;
//	}


	@Override
	public ArrayList<Item> getAllItems(int id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Store> getAllStores(int id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Store getStore(int id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ArrayList<ShoppingList> getAllParticipations(Person p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	
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

	@Override
	public ItemsByGroupReport getReportOfGroupBetweenDates(Person p, Group g, Store s, Timestamp from, Timestamp to) throws IllegalArgumentException {
	  	if(this.getShoppingListAdministration() == null) {
    		return null;
    		}
    	
    	//Anlegen eines leeren Reports
    	ItemsByGroupReport result = new ItemsByGroupReport();
    	
  
    	// Jeder Report erh�lt einen Titel (�berschrift)
    	result.setTitle("AlleItems");
    	
    	/*
         * Datum der Erstellung hinzuf�gen. new Timestamp() erzeugt autom. einen
         * "Timestamp" des Zeitpunkts der Instantiierung des Objekts.
         */
    	result.setCreationDate(new Timestamp(System.currentTimeMillis()));
    	
    	ArrayList<ListItem> items = this.listItemMapper.getCheckedListItemsOfGroupBetweenDates(g.getId(), from, to);
    	
    	//Wenn ein Store angegeben wird, wird nach diesem gefiltert.
    			if(s != null) {
    				for(ListItem li: items) {
    					if(li.getStoreID() != s.getId()) {
    						items.remove(li);
    					}
    				}
    			}
    	
    			if(p!=null) {
    				for(ListItem li: items) {
    					if(li.getBuyerID() != p.getId()) {
    						items.remove(li);
    					}
    				}
    			}
    	//Sicherheitsabfrage
    	if(items !=null) {
  
        // Zusammenstellung der Kopfdaten (Headline) des Reports 
    	Row headline = new Row();
    	headline.addColumn(new Column("ListItem_ID"));
    	headline.addColumn(new Column("Unit"));
    	headline.addColumn(new Column("Amount"));
    	headline.addColumn(new Column("Item_ID"));
    	headline.addColumn(new Column("Responsibility_ID"));
    	headline.addColumn(new Column("Person_ID"));
    	headline.addColumn(new Column("Store_ID"));
    	//headline.addColumn(new Column("Name"));
    	
   	 		
   	 	result.addRow(headline);
		  	
   	    /*
   	     * Auslesen s�mtlicher Contact Objekte und deren PropertyValues, welche dem Report hinzugef�gt werden.
   	     */
    	for(ListItem i : items) {
  
				
				//Eine leere Zeile anlegen.
				Row listItemRow = new Row();
				
				//erste Spalte: ID hinzuf�gen
				listItemRow.addColumn(new Column(String.valueOf(i.getId())));
				
				//erste Spalte: ID hinzuf�gen
				listItemRow.addColumn(new Column(String.valueOf(i.getUnit())));
				
				//erste Spalte: ID hinzuf�gen
				listItemRow.addColumn(new Column(String.valueOf(i.getAmount())));
				
				//erste Spalte: ID hinzuf�gen
				listItemRow.addColumn(new Column(String.valueOf(i.getItemId())));
				
				//erste Spalte: ID hinzuf�gen
				listItemRow.addColumn(new Column(String.valueOf(i.getResID())));
				
				//erste Spalte: ID hinzuf�gen
				listItemRow.addColumn(new Column(String.valueOf(i.getBuyerID())));
				
				//erste Spalte: ID hinzuf�gen
				listItemRow.addColumn(new Column(String.valueOf(i.getStoreID())));
//				//zweite Spalte: Bezeichnung eintragen
//				listItemRow.addColumn(new Column(String.valueOf(i.getName())));
			
				
				
				result.addRow(listItemRow);
    	
    		}
    	//R�ckgabe des fertigen Reports
    	return result;
    	}
    	
    return null;
	}

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
			for(ListItem li: items) {
				if(li.getStoreID() != s.getId()) {
					items.remove(li);
				}
			}
		}
		
		//Wenn eine Gruppe angegeben wird, wird nach dieser gefiltert.
		if(g != null) {
			for(ListItem li: items) {
				if(li.getGrID() != g.getId()) {
					items.remove(li);
				}
			}
		}
		
		
		//Sicherheitsabfrage
    	if(items !=null) {
  
        // Zusammenstellung der Kopfdaten (Headline) des Reports 
    	Row headline = new Row();
    	headline.addColumn(new Column("ListItem_ID"));
    	headline.addColumn(new Column("Unit"));
    	headline.addColumn(new Column("Amount"));
    	headline.addColumn(new Column("Item_ID"));
    	headline.addColumn(new Column("Responsibility_ID"));
    	headline.addColumn(new Column("Person_ID"));
    	headline.addColumn(new Column("Store_ID"));
    	//headline.addColumn(new Column("Name"));
    	
   	 		
   	 	result.addRow(headline);
		  	
   	    /*
   	     * Auslesen s�mtlicher Contact Objekte und deren PropertyValues, welche dem Report hinzugef�gt werden.
   	     */
    	for(ListItem i : items) {
  
				
				//Eine leere Zeile anlegen.
				Row listItemRow = new Row();
				
				//erste Spalte: ID hinzuf�gen
				listItemRow.addColumn(new Column(String.valueOf(i.getId())));
				
				//erste Spalte: ID hinzuf�gen
				listItemRow.addColumn(new Column(String.valueOf(i.getUnit())));
				
				//erste Spalte: ID hinzuf�gen
				listItemRow.addColumn(new Column(String.valueOf(i.getAmount())));
				
				//erste Spalte: ID hinzuf�gen
				listItemRow.addColumn(new Column(String.valueOf(i.getItemId())));
				
				//erste Spalte: ID hinzuf�gen
				listItemRow.addColumn(new Column(String.valueOf(i.getResID())));
				
				//erste Spalte: ID hinzuf�gen
				listItemRow.addColumn(new Column(String.valueOf(i.getBuyerID())));
				
				//erste Spalte: ID hinzuf�gen
				listItemRow.addColumn(new Column(String.valueOf(i.getStoreID())));
//				//zweite Spalte: Bezeichnung eintragen
//				listItemRow.addColumn(new Column(String.valueOf(i.getName())));
			
				
				
				result.addRow(listItemRow);
    	
    		}
    	//R�ckgabe des fertigen Reports
    	return result;
    	}
    	return null;
	}
	
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
					for(ListItem li: items) {
						if(li.getStoreID() != s.getId()) {
							items.remove(li);
						}
					}
				}
				
				//Wenn eine Gruppe angegeben wird, wird nach dieser gefiltert.
				if(g != null) {
					for(ListItem li: items) {
						if(li.getGrID() != g.getId()) {
							items.remove(li);
						}
					}
				}
		//Sicherheitsabfrage
    	if(items !=null) {
  
        // Zusammenstellung der Kopfdaten (Headline) des Reports 
    	Row headline = new Row();
    	headline.addColumn(new Column("ListItem_ID"));
    	headline.addColumn(new Column("Unit"));
    	headline.addColumn(new Column("Amount"));
    	headline.addColumn(new Column("Item_ID"));
    	headline.addColumn(new Column("Responsibility_ID"));
    	headline.addColumn(new Column("Person_ID"));
    	headline.addColumn(new Column("Store_ID"));
    	//headline.addColumn(new Column("Name"));
    	
   	 		
   	 	result.addRow(headline);
		  	
   	    /*
   	     * Auslesen s�mtlicher Contact Objekte und deren PropertyValues, welche dem Report hinzugef�gt werden.
   	     */
    	for(ListItem i : items) {
  
				
				//Eine leere Zeile anlegen.
				Row listItemRow = new Row();
				
				//erste Spalte: ID hinzuf�gen
				listItemRow.addColumn(new Column(String.valueOf(i.getId())));
				
				//erste Spalte: ID hinzuf�gen
				listItemRow.addColumn(new Column(String.valueOf(i.getUnit())));
				
				//erste Spalte: ID hinzuf�gen
				listItemRow.addColumn(new Column(String.valueOf(i.getAmount())));
				
				//erste Spalte: ID hinzuf�gen
				listItemRow.addColumn(new Column(String.valueOf(i.getItemId())));
				
				//erste Spalte: ID hinzuf�gen
				listItemRow.addColumn(new Column(String.valueOf(i.getResID())));
				
				//erste Spalte: ID hinzuf�gen
				listItemRow.addColumn(new Column(String.valueOf(i.getBuyerID())));
				
				//erste Spalte: ID hinzuf�gen
				listItemRow.addColumn(new Column(String.valueOf(i.getStoreID())));
//				//zweite Spalte: Bezeichnung eintragen
//				listItemRow.addColumn(new Column(String.valueOf(i.getName())));
			
				
				
				result.addRow(listItemRow);
    	
    		}
    	//R�ckgabe des fertigen Reports
    	return result;
    	}
    	return null;
	}

	@Override
	public ItemsByGroupReport getReportOfGroup(Person p, Group g, Store s) throws IllegalArgumentException {
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
    				for(ListItem li: items) {
    					if(li.getStoreID() != s.getId()) {
    						System.out.println("store not null");
    						items.remove(li);
    					}
    				}
    			}
    	
    			if(p!=null) {
    				for(ListItem li: items) {
    					if(li.getBuyerID() != p.getId()) {
    						System.out.println("person not null");
    						items.remove(li);
    					}
    				}
    			}
    	//Sicherheitsabfrage
    	if(items !=null) {
  
        // Zusammenstellung der Kopfdaten (Headline) des Reports 
    	Row headline = new Row();
    	headline.addColumn(new Column("ListItem_ID"));
    	headline.addColumn(new Column("Unit"));
    	headline.addColumn(new Column("Amount"));
    	headline.addColumn(new Column("Item_ID"));
    	headline.addColumn(new Column("Responsibility_ID"));
    	headline.addColumn(new Column("Person_ID"));
    	headline.addColumn(new Column("Store_ID"));
    	//headline.addColumn(new Column("Name"));
    	
   	 		
   	 	result.addRow(headline);
		  	
   	    /*
   	     * Auslesen s�mtlicher Contact Objekte und deren PropertyValues, welche dem Report hinzugef�gt werden.
   	     */
    	for(ListItem i : items) {
  
				
				//Eine leere Zeile anlegen.
				Row listItemRow = new Row();
				
				//erste Spalte: ID hinzuf�gen
				listItemRow.addColumn(new Column(String.valueOf(i.getId())));
				
				//erste Spalte: ID hinzuf�gen
				listItemRow.addColumn(new Column(String.valueOf(i.getUnit())));
				
				//erste Spalte: ID hinzuf�gen
				listItemRow.addColumn(new Column(String.valueOf(i.getAmount())));
				
				//erste Spalte: ID hinzuf�gen
				listItemRow.addColumn(new Column(String.valueOf(i.getItemId())));
				
				//erste Spalte: ID hinzuf�gen
				listItemRow.addColumn(new Column(String.valueOf(i.getResID())));
				
				//erste Spalte: ID hinzuf�gen
				listItemRow.addColumn(new Column(String.valueOf(i.getBuyerID())));
				
				//erste Spalte: ID hinzuf�gen
				listItemRow.addColumn(new Column(String.valueOf(i.getStoreID())));
//				//zweite Spalte: Bezeichnung eintragen
//				listItemRow.addColumn(new Column(String.valueOf(i.getName())));
			
				
				
				result.addRow(listItemRow);
    	
    		}
    	//R�ckgabe des fertigen Reports
    	return result;
    	}
    	
    return null;
	}
/*
	@Override
	public ItemsbyGroupReport getReportOfGroupBetweenDates(Group g) throws IllegalArgumentException {
		if(this.getShoppingListAdministration() == null) {
    		return null;
    		}
		return this.listItemMapper.getCheckedListItemsOfGroupBetweenDates(groupId, start, end);
	}
*/

	
	
	
	
}
