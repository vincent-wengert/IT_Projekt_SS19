package de.hdm.softwarepraktikum.server.report;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

import de.hdm.softwarepraktikum.server.ShoppingListAdministrationImpl;
import de.hdm.softwarepraktikum.shared.ShoppingListAdministration;
import de.hdm.softwarepraktikum.shared.ReportGenerator;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.Responsibility;
import de.hdm.softwarepraktikum.shared.bo.ShoppingList;
import de.hdm.softwarepraktikum.shared.bo.Store;
import de.hdm.softwarepraktikum.shared.report.ItemsByGroupReport;
import de.hdm.softwarepraktikum.shared.report.ItemsByPersonReport;
import de.hdm.softwarepraktikum.shared.report.Column;
import de.hdm.softwarepraktikum.shared.report.SubColumn;
import de.hdm.softwarepraktikum.shared.report.Row;

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public ItemsByGroupReport createGroupStatisticsReport(int id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
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
	public void AddImprint() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<ShoppingList> getAllParticipations(Person p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
