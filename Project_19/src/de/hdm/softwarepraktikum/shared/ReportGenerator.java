package de.hdm.softwarepraktikum.shared;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.ListItem;
import de.hdm.softwarepraktikum.shared.bo.Store;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.bo.Responsibility;
import de.hdm.softwarepraktikum.shared.bo.ShoppingList;
import de.hdm.softwarepraktikum.shared.report.ItemsByGroupReport;
import de.hdm.softwarepraktikum.shared.report.ItemsByPersonReport;



/**
 * Der ReportGenerator ist angelegt, um verschiende Reports zu erstellen, die
 * eine bestimmte Anzahl von Daten des Systems zweckspezifisch darstellen.
 * @author Luca Randecker
 * @version 1.0
 * @see ReportGeneratorAsync
 */

@RemoteServiceRelativePath("reportGenerator")
public interface ReportGenerator extends RemoteService {
	
	/**
	 * @see de.hdmsoftwarepraktikum.server.report.ReportGeneratorImpl#init();
	 */
	
	public void init() throws IllegalArgumentException;
	
	
	/**
	 * @see 
	 * @param id
	 * @return
	 * @throws IllegalArgumentException
	 */
	
	//public ShoppingListAdministration getShoppingListAdministration(int id) throws IllegalArgumentException;
	
	/**
	 * Diese Methode gibt die Einkaufsstatistik f�r eine Gruppe aus.
	 * @param a
	 * @return alle eingekauften Produkte einer Gruppe 
	 * @throws IllegalArgumentException
	 */
	ArrayList<Item> getAllItems() throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.softwarepraktikum.server.report.ReportGeneratorImpl#getAllStores
	 * @param id
	 * @return
	 * @throws IllegalArgumentException
	 */
	
	public ArrayList<Store> getAllStores() throws IllegalArgumentException;
	
	public ArrayList<Group> getAllGroups(Person p) throws IllegalArgumentException;
	
	public ArrayList<Person> getAllPersons() throws IllegalArgumentException;
	
	
	/**
	 * @see de.hdm.softwarepraktikum.server.report.ReportGeneratorImpl#getAllParticipations
	 * @param p
	 * @return
	 * @throws IllegalArgumentException
	 */
	
	public ArrayList<ShoppingList> getAllParticipations(Person p) throws IllegalArgumentException;


	void AddImprint();

	public ItemsByPersonReport getReportOfPerson(Person p, Store s, Group g) throws IllegalArgumentException;
	
	public ItemsByPersonReport getReportOfPersonBetweenDates(Person p, Store s, Group g, Timestamp from, Timestamp to) throws IllegalArgumentException;
	
	public ItemsByGroupReport getReportOfGroup(Boolean filterPerson, Person p, Group g, Store s) throws IllegalArgumentException;


	ItemsByGroupReport getReportOfGroupBetweenDates(Boolean filterPerson, Person p,Group g, Store s, Timestamp from, Timestamp to)
			throws IllegalArgumentException;
	
}
