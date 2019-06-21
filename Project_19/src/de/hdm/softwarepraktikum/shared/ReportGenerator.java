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
	 * Diese Methode gibt die Einkaufsstatistik für eine Gruppe aus.
	 * @param a
	 * @return alle eingekauften Produkte einer Gruppe 
	 * @throws IllegalArgumentException
	 */

 	
	/**
	 * @see de.hdm.softwarepraktikum.server.report.ReportGeneratorImpl#getAllItems
	 * @param id
	 * @return
	 * @throws IllegalArgumentException
	 */
	
	public ArrayList<Item> getAllItems(int id) throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.softwarepraktikum.server.report.ReportGeneratorImpl#getAllStores
	 * @param id
	 * @return
	 * @throws IllegalArgumentException
	 */
	
	public ArrayList<Store> getAllStores(int id) throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.softwarepraktikum.server.report.ReportGeneratorImpl#getStore
	 * @param id
	 * @return
	 * @throws IllegalArgumentException
	 */
	
	public Store getStore(int id) throws IllegalArgumentException;
	
	
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
	
	public ItemsByGroupReport getReportOfGroup(Group g, Store s) throws IllegalArgumentException;


	ItemsByGroupReport getReportOfGroupBetweenDates(Group g, Store s, Timestamp from, Timestamp to)
			throws IllegalArgumentException;
	
	//public ItemsByGroupReport getReportOfGroupBetweenDates(Group g) throws IllegalArgumentException;
	
	
	/**
	 * @see de.hdm.softwarepraktikum.server.report.ReportGeneratorImpl#createItemsByPersonReport(Person p)
	 * @param p
	 * @return
	 * @throws IllegalArgumentException
	 */
	
	// public ArrayList<Item> getAllItemsByPerson(Person p) throws IllegalArgumentException;
	
	// public ArrayList<Store> getAllStoreByPerson(Person p) throws IllegalArgumentException;
	
	// public ArrayList<Item> getAllItemsByGroup(Group g) throws IllegalArgumentException;
	
	// public ArrayList<Store> getAllStoreByGroup(Group g) throws IllegalArgumentException;
	
	// public ArrayList<Responsibility> getAllResponsibilities(Person p) throws IllegalArgumentException;

}
