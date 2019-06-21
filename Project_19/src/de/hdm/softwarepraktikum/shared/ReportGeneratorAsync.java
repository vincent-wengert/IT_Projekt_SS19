package de.hdm.softwarepraktikum.shared;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.ListItem;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.bo.ShoppingList;
import de.hdm.softwarepraktikum.shared.bo.Store;
import de.hdm.softwarepraktikum.shared.report.ItemsByGroupReport;
import de.hdm.softwarepraktikum.shared.report.ItemsByPersonReport;



public interface ReportGeneratorAsync {
	
	public void init(AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void getReportOfGroupBetweenDates(Group g, Store s, Timestamp from, Timestamp to,
			AsyncCallback<ItemsByGroupReport> callback);
 	
	public void getAllItems(int id, AsyncCallback<ArrayList<Item>> callback) throws IllegalArgumentException;
	
	public void getAllStores(int id, AsyncCallback<ArrayList<Store>> callback) throws IllegalArgumentException;;
	
	public void getStore(int id, AsyncCallback<Store> callback) throws IllegalArgumentException;
	
	public void AddImprint(AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void getAllParticipations(Person p, AsyncCallback<ArrayList<ShoppingList>> callback) throws IllegalArgumentException;

	void getReportOfPerson(Person p, Store s, Group g, AsyncCallback<ItemsByPersonReport> callback);

	void getReportOfGroup(Group g, Store s, AsyncCallback<ItemsByGroupReport> callback);

	void getReportOfPersonBetweenDates(Person p, Store s, Group g, Timestamp from, Timestamp to, AsyncCallback<ItemsByPersonReport> callback);

	//void getReportOfGroupBetweenDates(Group g, AsyncCallback<ItemsByGroupReport> callback);
	
	
}
