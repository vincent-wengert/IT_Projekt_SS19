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
			
			header.addSubParagraph(new SimpleParagraph("Gruppe: " + g.getTitle()));
			
			
			//Kopfdaten (Headline) des Reports.
			Row headline = new Row();
			headline.addColumn(new Column("ID"));
			headline.addColumn(new Column("Produktbezeichnung"));
			headline.addColumn(new Column("Einheit"));
			headline.addColumn(new Column("Eingekaufte Menge"));
			headline.addColumn(new Column("gekauft am:"));
			
			result.addRow("headline");
			
			/*
			 * Auslesen sämtlicher abgehakten <code>ListItem</code>-Objekte, die dem Report 
			 * hinzugefügt werden.
			 */
			for(ListItem listItem : listitems) {
				
				Row row = new Row();
				row.addColumn(listItem.toString());
				
				
				
			}
		
		
	}

	@Override
	public ShoppingListAdministration getShoppingListAdministration(int id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemsByPersonReport createUserStatisticsReport(int id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
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
