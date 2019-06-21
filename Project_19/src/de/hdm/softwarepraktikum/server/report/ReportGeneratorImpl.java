package de.hdm.softwarepraktikum.server.report;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.softwarepraktikum.server.ShoppingListAdministrationImpl;
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
 * ReportGenerator. In der Klasse ist neben ShoppingListAdministrationImpl sämtliche
 * Applikationslogik vorhanden.
 * 
 * @author Luca Randecker
 * @version 1.0
 */

@SuppressWarnings("serial")
public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGenerator{
	
	/**
	 * Der ReportGenerator benötigt Zugriff auf die ContactAdministation,
	 * da dort wichtige Methoden für die Koexistenz von Datenobjekten enthalten sind.
	 */
    private ShoppingListAdministration administration = null;

    
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
		
	}
	
	protected ShoppingListAdministration getShoppingListAdministration() {
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
//			//Impressum hinzuf�gen
//			this.AddImprint(result);
//			/*
//			 * Erstellungsdatum des Reports durch einen Timestamp hinzuf�gen. 
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
//			//Hinzuf�gen der Kopfdaten zum Report
//			result.setHeaderData(header);
//			
//			//Anlegen der Kopfzeile f�r die Statistik-Tabelle.
//			Row headline = new Row();
//			
//			/**
//			 * Im Report werden f�nf Spalten ben�tigt: die ID des Produkts,
//			 * die Produktbezeichnung, die Einheit, die Eingekaufte Menge und das
//			 * Einkaufsdatum.
//			 */
//			headline.addColumn(new Column("ID"));
//			headline.addColumn(new Column("Produktbezeichnung"));
//			headline.addColumn(new Column("Einheit"));
//			headline.addColumn(new Column("Eingekaufte Menge"));
//			headline.addColumn(new Column("H�ndler"));
//			
//			
//			result.addRow(headline);
//			
//			/*
//			 * Auslesen s�mtlicher abgehakten <code>ListItem</code>-Objekte, die dem Report 
//			 * hinzugef�gt werden. Methode "getListItem 
//			 * 
//			 * 
//			 */
//			ArrayList<ListItem> li = this.administration.getAllCheckedItemsByGroup(g);
//			for(ListItem l : li) {
//				
//				//Eine leere Zeile anlegen.
//				Row listItemRow = new Row();
//				
//				//erste Spalte: ID hinzuf�gen
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
//				//f�nfte Spalte: Store
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
	public ItemsByPersonReport createUserStatisticsReport(Person p) throws IllegalArgumentException {
		if(this.getShoppingListAdministration() == null) {
			return null;
		}
			//Anlegen eines leeren Reports.
			ItemsByPersonReport result = new ItemsByPersonReport();
			
			//Titel des Reports
			result.setTitle("Einkaufsstatistik von" + p.getName());
			
			//Impressum hinzuf�gen
			this.AddImprint(result);
			
			/*
			 * Erstellungsdatum des Reports durch einen Timestamp hinzuf�gen. 
			 */
			result.setCreationDate(new Timestamp(System.currentTimeMillis()));
		
			
			/**
			 * TO DO: Bezug zu Store und Zeitraum!
			 */
			ArrayList<ListItem> relevantitems = new ArrayList<ListItem>();
			
			ArrayList<ShoppingList> alllists = this.administration.getAllShoppingListsByPerson(p);
			ArrayList<ListItem> checkedbySL = this.administration.getAllCheckedItemsBySL(sl);
			ArrayList<Responsibility> resByPerson = this.administration.getResponsibilityByPerson(p);
			
			for(Person p1: this.administration.getAllPersons()) {
				for(ShoppingList sl : alllists) {
					for(ListItem li: checkedbySL) {
						for(Responsibility re: resByPerson) {
							if(p1.getId() == p.getId() && p.getId() == re.getBuyerID() && relevantitems.contains(li) == false ) {
								relevantitems.add(li);
							}
						}
					}
				}
			}	
			
			
			/*
			 * Zusammenstellung der Kopfdaten des Reports
			 */
			CompositeParagraph header = new CompositeParagraph();
			
			//Name der Gruppe aufnehmen
			header.addSubParagraph(new SimpleParagraph("Person: " + p.getName()));
			
			//Hinzuf�gen der Kopfdaten zum Report
			result.setHeader(header);
			
			//Anlegen der Kopfzeile f�r die Statistik-Tabelle.
			Row headline = new Row();
			
			/**
			 * Im Report werden f�nf Spalten ben�tigt: die ID des Produkts,
			 * die Produktbezeichnung, die Einheit, die Eingekaufte Menge und das
			 * Einkaufsdatum.
			 */
			headline.addColumn(new Column("ID"));
			headline.addColumn(new Column("Produktbezeichnung"));
			headline.addColumn(new Column("Einheit"));
			headline.addColumn(new Column("Eingekaufte Menge"));
			headline.addColumn(new Column("H�ndler"));
			
			
			result.addRow(headline);
			
			/*
			 * Auslesen s�mtlicher abgehakten <code>ListItem</code>-Objekte, die dem Report 
			 * hinzugef�gt werden. Methode "getListItem 
			 * 
			 * 
			 */
			ArrayList<ListItem> li = this.administration.getAllCheckedItemsByGroup(g);
			for(ListItem l : li) {
				
				//Eine leere Zeile anlegen.
				Row listItemRow = new Row();
				
				//erste Spalte: ID hinzuf�gen
				listItemRow.addColumn(new Column(String.valueOf(l.getId())));
				
				//zweite Spalte: Bezeichnung eintragen String.valueOf(l.getName()))
				listItemRow.addColumn(new Column());
				
				//dritte Spalte: Einheit eintragen
				listItemRow.addColumn(new Column(String.valueOf(l.getUnit())));
				
				//vierte Spalte: Anzahl, wie oft das Produkt gekauft wurde.
				//listItemRow.addColumn(new Column(String.valueOf()));
				
				//f�nfte Spalte: Store String.valueOf(l.getStore()
				listItemRow.addColumn(new Column());
				
				result.addRow(listItemRow);
				
				
			}
			
			return result;
	}


	

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
		imprint.addSubParagraph(new SimpleParagraph("Nobelstra�e 10 70569 Stuttgart"));
		imprint.addSubParagraph(new SimpleParagraph("Telefon: 0711 8923-3242"));
		imprint.addSubParagraph(new SimpleParagraph("E-Mail: info-wi7@hdm-stuttgart.de"));
		imprint.addSubParagraph(new SimpleParagraph("Website: https://www.hdm-stuttgart.de/"));
		
		//Hinzuf�gen des Impressums zum Report.
		r.setImprint(imprint);
		
	}

	@Override
	public void AddImprint() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ItemsByGroupReport createGroupStatisticsReport(Group g) throws IllegalArgumentException {
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
    	
    	ArrayList<Item> items = this.administration.getAllItems();
    	
    
    	//Sicherheitsabfrage
    	if(items !=null) {
    		
    	
        // Zusammenstellung der Kopfdaten (Headline) des Reports 
    	Row headline = new Row();
    	headline.addColumn(new Column("ID"));
    	headline.addColumn(new Column("Name"));
    	
   	 		
   	 	result.addRow(headline);
		  	
   	    /*
   	     * Auslesen s�mtlicher Contact Objekte und deren PropertyValues, welche dem Report hinzugef�gt werden.
   	     */
    	for(Item i : items) {
    			System.out.println("item");
    		
				
				//Eine leere Zeile anlegen.
				Row listItemRow = new Row();
				
				//erste Spalte: ID hinzuf�gen
				listItemRow.addColumn(new Column(String.valueOf(i.getId())));
				
				//zweite Spalte: Bezeichnung eintragen
				listItemRow.addColumn(new Column(String.valueOf(i.getName())));
			
				
				
				result.addRow(listItemRow);
    	
    		}
    	//R�ckgabe des fertigen Reports
    	return result;
    	}
    	
    return null;
	}
	
	
	
	
}
