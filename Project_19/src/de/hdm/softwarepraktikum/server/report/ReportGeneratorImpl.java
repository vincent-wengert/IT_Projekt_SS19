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
	
	

	@Override
	public ItemsByGroupReport createGroupStatisticsReport(Group g) throws IllegalArgumentException {
		
		if(this.getShoppingListAdministration() == null) {
			return null;
		}
			//Anlegen eines leeren Reports.
			ItemsByGroupReport result = new ItemsByGroupReport();
			//Titel des Reports
			result.setTitle("Einkaufsstatistik der Gruppe");
			
			//Impressum hinzufügen
			this.AddImprint(result);
			
			/*
			 * Erstellungsdatum des Reports durch einen Timestamp hinzufügen. 
			 */
			result.setCreationDate(new Timestamp(System.currentTimeMillis()));
			
			//Methode getAllItemsByGroup kommt noch
			//ArrayList<ListItem> listItems = this.administration.getAllItemsByGroup();
			
			/*
			 * Zusammenstellung der Kopfdaten des Reports
			 */
			CompositeParagraph header = new CompositeParagraph();
			
			//Name der Gruppe aufnehmen
			header.addSubParagraph(new SimpleParagraph("Gruppe: " + g.getTitle()));
			
			//Hinzufügen der Kopfdaten zum Report
			result.setHeaderData(header);
			
			//Anlegen der Kopfzeile für die Statistik-Tabelle.
			Row headline = new Row();
			
			/**
			 * Im Report werden fünf Spalten benötigt: die ID des Produkts,
			 * die Produktbezeichnung, die Einheit, die Eingekaufte Menge und das
			 * Einkaufsdatum.
			 */
			headline.addColumn(new Column("ID"));
			headline.addColumn(new Column("Produktbezeichnung"));
			headline.addColumn(new Column("Einheit"));
			headline.addColumn(new Column("Eingekaufte Menge"));
			headline.addColumn(new Column("gekauft am:"));
			
			result.addRow(headline);
			
			/*
			 * Auslesen sämtlicher abgehakten <code>ListItem</code>-Objekte, die dem Report 
			 * hinzugefügt werden. Methode "getListItem 
			 * 
			 * ToDo: Methode in ShoppingListAdministration, die alle Checked ITems einer SL/Gruppe 
			 * ermittelt.
			 */
			ArrayList<ListItem> li = this.administration.getCheckedLi(g);
			for(ListItem l : li) {
				
				//Eine leere Zeile anlegen.
				Row listItemRow = new Row();
				
				//erste Spalte: ID hinzufügen
				listItemRow.addColumn(new Column(String.valueOf(l.getId())));
				
				//zweite Spalte: Bezeichnung eintragen
				listItemRow.addColumn(new Column(String.valueOf(l.getIt().getName())));
				
				//dritte Spalte: Einheit eintragen
				listItemRow.addColumn(new Column(String.valueOf(l.getUnit())));
				
				//vierte Spalte: Anzahl, wie oft das Produkt gekauft wurde.
				//listItemRow.addColumn(new Column(String.valueOf()));
				
				//fünfte Spalte: Einkaufsdatum
				//listItemRow.addColumn(new Column());
				
				result.addRow(listItemRow);
				
				
			}
			
			return result;
		
		
	}

	@Override
	public ShoppingListAdministration getShoppingListAdministration(int id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemsByPersonReport createUserStatisticsReport(Person p) throws IllegalArgumentException {
		if(this.getShoppingListAdministration() == null) {
			return null;
		}
			//Anlegen eines leeren Reports.
			ItemsByPersonReport result = new ItemsByPersonReport();
			
			//Titel des Reports
			result.setTitle("Einkaufsstatistik von" + p.getName());
			
			//Impressum hinzufügen
			this.AddImprint(result);
			
			/*
			 * Erstellungsdatum des Reports durch einen Timestamp hinzufügen. 
			 */
			result.setCreationDate(new Timestamp(System.currentTimeMillis()));
			
			//Methode getAllItemsByGroup kommt noch
			//ArrayList<ListItem> listItems = this.administration.getAllItemsByGroup();
			
			/*
			 * Zusammenstellung der Kopfdaten des Reports
			 */
			CompositeParagraph header = new CompositeParagraph();
			
			//Name der Gruppe aufnehmen
			header.addSubParagraph(new SimpleParagraph("Person: " + p.getName()));
			
			//Hinzufügen der Kopfdaten zum Report
			result.setHeaderData(header);
			
			//Anlegen der Kopfzeile für die Statistik-Tabelle.
			Row headline = new Row();
			
			/**
			 * Im Report werden fünf Spalten benötigt: die ID des Produkts,
			 * die Produktbezeichnung, die Einheit, die Eingekaufte Menge und das
			 * Einkaufsdatum.
			 */
			headline.addColumn(new Column("ID"));
			headline.addColumn(new Column("Produktbezeichnung"));
			headline.addColumn(new Column("Einheit"));
			headline.addColumn(new Column("Eingekaufte Menge"));
			
			
			result.addRow(headline);
			
			/*
			 * Auslesen sämtlicher abgehakten <code>ListItem</code>-Objekte, die dem Report 
			 * hinzugefügt werden. Methode "getListItem 
			 * 
			 * ToDo: Methode in ShoppingListAdministration, die alle Checked ITems einer SL/Gruppe 
			 * ermittelt.
			 */
			ArrayList<ListItem> li = this.administration.getCheckedLi(p);
			for(ListItem l : li) {
				
				//Eine leere Zeile anlegen.
				Row listItemRow = new Row();
				
				//erste Spalte: ID hinzufügen
				listItemRow.addColumn(new Column(String.valueOf(l.getId())));
				
				//zweite Spalte: Bezeichnung eintragen
				listItemRow.addColumn(new Column(String.valueOf(l.getIt().getName())));
				
				//dritte Spalte: Einheit eintragen
				listItemRow.addColumn(new Column(String.valueOf(l.getUnit())));
				
				//vierte Spalte: Anzahl, wie oft das Produkt gekauft wurde.
				//listItemRow.addColumn(new Column(String.valueOf()));
				
				
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
		imprint.addSubParagraph(new SimpleParagraph("Nobelstraße 10 70569 Stuttgart"));
		imprint.addSubParagraph(new SimpleParagraph("Telefon: 0711 8923-3242"));
		imprint.addSubParagraph(new SimpleParagraph("E-Mail: info-wi7@hdm-stuttgart.de"));
		imprint.addSubParagraph(new SimpleParagraph("Website: https://www.hdm-stuttgart.de/"));
		
		//Hinzufügen des Impressums zum Report.
		r.setImprint(imprint);
		
	}
	
	
}
